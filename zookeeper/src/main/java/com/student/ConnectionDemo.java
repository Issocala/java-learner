package com.student;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ConnectionDemo {
    public static void main(String[] args){
        try{
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182," +
                    "127.0.0.1:2183", 4000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    // 判断是否启动成功，需要zookeeper完全启动之后在使用，不然会报错
                    if(Event.KeeperState.SyncConnected == event.getState()){
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            // 输出当前状态
            System.out.println((zooKeeper.getState()));

            // 添加持久节点
            zooKeeper.create("/zk-student-test", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            Thread.sleep(1000);
            Stat stat = new Stat();

            // 得到节点的值
            byte[] bytes = zooKeeper.getData("/zk-student-test",null,stat);
            System.out.println(new String(bytes));

            // 修改节点的值
            zooKeeper.setData("/zk-student-test", "2".getBytes(),stat.getVersion());

            // 得到修改后的值
            byte[] bytes1 = zooKeeper.getData("/zk-student-test", null, stat);
            System.out.println(new String(bytes1));

            // 删除节点
            zooKeeper.delete("/zk-student-test", stat.getVersion());

            // 关闭会话连接
            zooKeeper.close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
