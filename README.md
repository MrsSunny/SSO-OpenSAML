##SAML简介##

----------
>SAML为SSO提供了一个安全的协议。SAML（读作"sam-ell"）是允许Web站点安全地共享身份信息的一个规范，它来自ebxml和其他XML标准背后的国际性联盟OASIS。站点使用SAML的XML词汇表和请求/应答模式，通过HTTP交换身份信息。这种信息共享标准化能帮助Web站点与多个合作伙伴集成，避免由于为不同合作伙伴设计和维护各自私有的集成通道而引起的争论


##项目简介##

----------
SSO-OpenSAML项目既可以作为应用服务器，也可以作为SSO服务器，做为SSO服务器遵循SAML协议，该项目用ARTIFACT-BIND方式实现。


##SSO服务##

----------
###流程简介###


1.*用户访问SP的受保护资源*

2.*SP检查是否有用户的Session，如果用则直接访问*

3.*如果没有Session上下文，则跳转到IDP的登陆页面*

4.*输入用户名密码进行登录验证*

5.*如果验证成功，IPD会通过POST的方式发送Artifact到SP*

6.*SP接受Artifact，然后用HTTP POST方式来请求IDP的Artifact-response接口(参数为Artifact)*

7.*IDP接受到SP传过来的Artifact ，根据Artifact 把关联的AuthnRespoons返回给SP*

8.*SP进行验证，包括验签操作，以及根据response中的Subject组装上下文对象。*

9.*验证SAML协议和验签操作成功后会跳转至相应的页面.



## 部署项目

```
项目有两部分组成：
    client：client为SP服务器
    server：server为IDP服务器
需要在mysql中执行sql目录下面的sql文件。

然后分别启动两个项目，域名以soaer.com为例子（所以需要改动host配置）

client： IDP-IP passport.soaer.com
server:  SP-IP  soaer.com

然后可以访问client的受限制的链接地址，这样就可以跳转到IDP的登陆页面（domain@163.com/111111）
```

```

```

###OpenSAML2 接口

全部接口定义：

可以在这个文件中配置相应的接口

```
SSO-OpenSAML/src/main/java/org/sms/SysConstants.java
```

验证及生成接口：

```
SSO-OpenSAML/src/main/java/org/sms/opensaml/service/impl/SamlServiceImpl.java
```

公钥证书配置位置：

```
SSO-OpenSAML/src/main/resources/opensaml/SPSSODescriptor.xml
```

私钥证书配置位置

```
SSO-OpenSAML/src/main/resources/opensaml/IDPSSODescriptor.xml	
```

⚠️需要生成自己的证书（使用openssl即可）。

##做为应用服务器简介

###主键ID生成方案（数据库集群方式）



ID生成流程图如下：

1.*服务器启动加载DataSource*

2.*每个Source为一个Node*

3.*虚拟节点可以调整（默认为10个）*

4.*可以根据表名来获取当前表的最大ID值*

5.*利用mysql的ID自增特性，使用replace into来获取自增ID的最新值。*



###配置ID生成器集群

⚠️ ID生成服务器可以有多台数据库服务器组成，假如有1台服务器，那么久不需要设置增长的步长了，假如有N台服务器的话需要设置服务器自增ID的时候的步长是N。MYSQL 中 AUTO_INCRMENT_OFFSET 和
AUTO_INCREMENT_INCREMENT 这两个参数变量,就是用来控制 AUTO_INCREMENT 列的列的起点值和插入新纪录时的增量值。


这样ID就不回重复，而且一致性hash也能实现高可用，即使一台ID集群服务器宕机也能马上找到下个节点来获取ID，而且不会重复。
###基于Zookeeper的分布式锁

>在有些场景下面我们必须用到分布式锁的服务，比如在线医生，一个医生在某个时间段内只能给一个病人看病，但在我们的分布式以及集群上面，都是多进程的，这个时候需要对这个医生的资源进行加锁。再比如某个资源的数量服务，当进行减法操作的时候同样可能需要分布式锁的服务功能

###流程说明

1.创建DistributedLock lock对象

2.检查根目录和需要锁的目录是否存在，如果不存在则在zk中创建

3.因为Zookeeper可以实现对每个目录名称自增的特性，所以可以创建自己的节点名称，创建方式CreateMode.EPHEMERAL_SEQUENTIAL

4.然后获取该lockNode目录中的所有的字目录

5.对所有的字目录进行排序

6.然后获取比自己小的目录列表，选取最后一个进行watch注册，（当最后一个释放锁的时候会自动通知该节点的）

7.如果没有比自己小的节点，那么自己本身就可以获取该资源的锁

8.完成后，释放锁

