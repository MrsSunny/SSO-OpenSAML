package org.sms.core.lock;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sunny
 */
public class DistributedLock {
  
  private Logger logger = LoggerFactory.getLogger(DistributedLock.class.getName());

  private CountDownLatch latch = new CountDownLatch(1);
  
  private CountDownLatch lesslatch = new CountDownLatch(1);

  private static final int TIMEOUT = 15000;

  /**
   * 资源根目录
   */
  private static final String ROOT = "/doctor";

  //doctor/doctor11588/x-95903064886083589-0000000004
  private String id;

  private byte[] data = { 0x12, 0x34 };

  /**
   * 需要锁定的目录 doctor/1234667/
   */
  private String lockNodePath;

  private ZooKeeper zooKeeper;

  /**
   * @param serverConfig the zookeeper config
   * @param lockDir 需要锁的目录
   * @throws IOException
   * @throws KeeperException
   * @throws InterruptedException
   */
  public void connectZk(String serverConfig, String lockDir) {
    try {
      zooKeeper = new ZooKeeper(serverConfig, TIMEOUT, new Watcher() {
        public void process(WatchedEvent event) {
          if (event.getState().equals(KeeperState.SyncConnected)) {
            latch.countDown();
          }
        }
      });
      latch.await();
      this.findNode(lockDir);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (KeeperException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void findNode(String lockDir) throws KeeperException, InterruptedException {
    /**
     * 查看根节点是否存在
     */
    Stat stat = zooKeeper.exists(ROOT, false);
    /**
     * 如果不存在则创建
     */
    if (stat == null) {
      zooKeeper.create(ROOT, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
    lockNodePath = ROOT + "/" + lockDir;
    Stat lockStat = zooKeeper.exists(lockNodePath, false);
    if (lockStat == null) {
      /**
       * 如果为空则证明此锁是空闲状态 然后创建锁节点
       */
      zooKeeper.create(lockNodePath, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
  }

  public void lock() {
    try {
      this.createNode();
      List<String> names = zooKeeper.getChildren(lockNodePath, false);
      SortedSet<String> sortedNames = new TreeSet<String>();
      for (String name : names) {
        sortedNames.add(name);
      }
      int index = id.lastIndexOf("/");
      String ownerId = id.substring(index + 1);
      SortedSet<String> lessThanMes = sortedNames.headSet(ownerId);
      if (!lessThanMes.isEmpty()) {
        /**
         * 如果发现有比自己小的节点
         */
        String lessThanMe = lessThanMes.last();
        Stat waitNode = zooKeeper.exists(lockNodePath + "/" + lessThanMe, new Watcher() {
          public void process(WatchedEvent event) {
            if (lesslatch != null) {
              lesslatch.countDown();
            }
          }
        });
        if (waitNode == null) {
          /**
           * 如果waitNode 为空，则证明比自己小的节点已经释放了该锁，程序可以继续往下执行
           */
          return;
        } else {
          lesslatch.await();
          return;
        }
      } else {
        /**
         * 如果比自己小的节点列表为空，则证明自己是最小的节点，
         * 说明自己可以获取该锁，程序继续往下执行
         */
        return;
      }
    } catch (KeeperException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  private void createNode() {
    String prefix = "x-";
    try {
      if (id == null) {
        id = zooKeeper.create(lockNodePath + "/" + prefix, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
      }
      logger.debug("新建的ID为：" + id);
    } catch (KeeperException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public boolean relaseLock() {
    try {
      zooKeeper.delete(id, -1);
      logger.debug("删除当前ID：" + id);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (KeeperException e) {
      e.printStackTrace();
    }
    return Boolean.TRUE;
  }
}