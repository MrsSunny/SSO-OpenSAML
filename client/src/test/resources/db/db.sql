/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/12/5 10:18:04                           */
/*==============================================================*/


drop table if exists APP;
drop table if exists ROLE_USER;
drop table if exists BLOG_TAG;
drop table if exists COMMENTS;
drop table if exists LOGIN_LOG;
drop table if exists ROLE_RESOURCE;
drop table if exists RESOURCE;
drop table if exists TAG;
drop table if exists ROLE;
drop table if exists BLOG;
drop table if exists USER;

/*==============================================================*/
/* Table: APP                                                   */
/*==============================================================*/
create table APP
(
   ID                   bigint(32) not null auto_increment,
   APP_DOMAIN           varchar(45) not null,
   CREATE_DATE          datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   USABLE_STATUS        int default 0,
   APP_INDEX            varchar(45) default 'N/A',
   primary key (ID),
   unique key app_domain_UNIQUE (APP_DOMAIN)
)
charset = UTF8;

/*==============================================================*/
/* Table: BLOG                                                  */
/*==============================================================*/
create table BLOG
(
   ID                   bigint(32) not null auto_increment,
   TITLE                varchar(200) not null,
   HTML_FILE_PATH       varchar(200) default "",
   MD_FILE_PATH         varchar(200) not null,
   CONTENT              text not null,
   READ_NUM             bigint(32) not null,
   USABLE_STATUS        int not null default 0,
   CREATE_USER_ID       bigint(32) not null default 0,
   CREATE_DATE          datetime default NULL,
   primary key (ID)
)
charset = UTF8;

/*==============================================================*/
/* Table: BLOG_TAG                                              */
/*==============================================================*/
create table BLOG_TAG
(
   ID                   bigint(32) not null auto_increment,
   BLOG_ID              bigint not null,
   TAG_ID               int not null default 0,
   CREATE_DATE          datetime default NULL,
   primary key (ID)
)
charset = UTF8;

/*==============================================================*/
/* Table: COMMENTS                                              */
/*==============================================================*/
create table COMMENTS
(
   ID                   bigint(32) not null auto_increment,
   CREATE_USER_ID       bigint(32) not null default 0,
   BLOG_ID              bigint(32) not null,
   PARENT_COMMENT_ID    bigint(32),
   CONTENT              varchar(2000) not null,
   USABLE_STATUS        int not null default 0,
   CREATE_DATE          datetime default NULL,
   primary key (ID)
)
charset = UTF8;

alter table COMMENTS comment '评论';

/*==============================================================*/
/* Table: LOGIN_LOG                                             */
/*==============================================================*/
create table LOGIN_LOG
(
   ID                   bigint(32) not null auto_increment,
   IP                   varchar(45) not null,
   LOGIN_TIME           datetime default NULL,
   LOGIN_DT             varchar(45) default "",
   USER_ID				bigint not null,
   primary key (ID)
)
charset = UTF8;

alter table LOGIN_LOG comment '登录日志';

/*==============================================================*/
/* Table: RESOURCE                                              */
/*==============================================================*/
create table RESOURCE
(
   ID                   bigint(32) not null auto_increment,
   URL                  varchar(200) not null,
   TYPE                 varchar(45) not null default 'N/A',
   NAME                 varchar(45) not null default 'N/A',
   PARENT_ID            bigint(32) default 0,
   DESCRIPTION          varchar(300) default "",
   USABLE_STATUS        int not null default 1,
   CREATE_USER_ID       bigint(32),
   CREATE_DATE          datetime default NULL,
   primary key (ID),
   unique key url_UNIQUE (URL)
)
charset = UTF8;

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table ROLE
(
   ID                   bigint(32) not null auto_increment,
   NAME                 varchar(45) not null,
   DESCRIPTION          varchar(1000) default "",
   USABLE_STATUS        int not null default 0,
   CREATE_USER_ID       bigint(32) not null default 0,
   CREATE_DATE          datetime default NULL,
   primary key (ID),
   unique key name_UNIQUE (NAME),
   key role_name (NAME),
   key role_create_user_id (CREATE_USER_ID)
)
charset = UTF8;

/*==============================================================*/
/* Table: ROLE_RESOURCE                                         */
/*==============================================================*/
create table ROLE_RESOURCE
(
   ID                   bigint(32) not null auto_increment,
   ROLE_ID              bigint(32) not null default 0,
   RESOURCE_ID          bigint(32) not null default 0,
   primary key (ID)
)
charset = UTF8;

/*==============================================================*/
/* Table: ROLE_USER                                             */
/*==============================================================*/
create table ROLE_USER
(
   ID                   bigint(32) not null auto_increment,
   USER_ID              bigint(32) not null default 0,
   ROLE_ID              bigint(32) not null default 0,
   primary key (ID)
)
charset = UTF8;

/*==============================================================*/
/* Table: TAG                                                   */
/*==============================================================*/
create table TAG
(
   ID                   int not null auto_increment,
   NAME                 varchar(45) not null,
   USABLE_STATUS        int not null default 0,
   CREATE_USER_ID       bigint(32) not null default 0,
   CREATE_DATE          datetime default NULL,
   primary key (ID)
)
charset = UTF8;

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table USER
(
   ID                   bigint(32) not null auto_increment,
   NAME                 varchar(200) not null,
   IMG_PATH             varchar(100) default "",
   PASSWORD             varchar(100) not null,
   EMAIL                varchar(45) not null,
   PHONE                varchar(45) default "",
   ADDRESS              varchar(200) default "",
   LOGIN_SUM            int(11) not null default 0,
   LAST_LOGIN_IP        varchar(100) not null default 'N/A',
   CREATE_DATE          datetime default NULL,
   USABLE_STATUS        int not null default 1 comment '0：可用
            1：不可用',
   MODIFY_DATE          datetime default NULL,
   TOKEN                varchar(50) default "",
   LOGIN_TYPE           int not null default 0 comment '1：用户名密码登陆
            2：QQ登录
            3：微信登录',
   LAST_LOGIN_DATE      datetime default NULL,
   primary key (ID),
   unique key email_UNIQUE (EMAIL),
   unique key name_UNIQUE (NAME)
)
charset = UTF8;

alter table BLOG add constraint FK_BLOG_USER foreign key (CREATE_USER_ID)
      references USER (ID) on delete restrict on update restrict;

alter table BLOG_TAG add constraint FK_Reference_8 foreign key (TAG_ID)
      references TAG (ID) on delete restrict on update restrict;

alter table BLOG_TAG add constraint FK_Reference_9 foreign key (BLOG_ID)
      references BLOG (ID) on delete restrict on update restrict;

alter table COMMENTS add constraint FK_BLOG_COMMENT foreign key (BLOG_ID)
      references BLOG (ID) on delete restrict on update restrict;

alter table COMMENTS add constraint FK_Reference_3 foreign key (CREATE_USER_ID)
      references USER (ID) on delete restrict on update restrict;

alter table ROLE_RESOURCE add constraint FK_ROLE_RESOURCE_R foreign key (RESOURCE_ID)
      references RESOURCE (ID) on delete restrict on update restrict;

alter table ROLE_RESOURCE add constraint FK_R_R_R foreign key (ROLE_ID)
      references ROLE (ID) on delete restrict on update restrict;

alter table ROLE_USER add constraint FK_ROLE_USER foreign key (USER_ID)
      references USER (ID) on delete restrict on update restrict;

alter table ROLE_USER add constraint FK_ROLE_USER_R foreign key (ROLE_ID)
      references ROLE (ID) on delete restrict on update restrict;

