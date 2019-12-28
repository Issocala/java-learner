package com.student;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


public class WatcherDemo {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final CountDownLatch countDownLatch=new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182," +
                "127.0.0.1:2183", 4000, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        System.out.println("默认事件： "+event.getType());
                        if(Event.KeeperState.SyncConnected==event.getState()){
                            //如果收到了服务端的响应事件，连接成功
                            countDownLatch.countDown();
                        }
                    }
                });
        countDownLatch.await();

        zooKeeper.create("/zk-student-test1","1".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

        //通过exists绑定事件
        Stat stat=zooKeeper.exists("/zk-student-test1", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.getType()+"->"+event.getPath());
                try {
                    //因为只会监听一次，再一次去绑定事件
                    zooKeeper.exists(event.getPath(),true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //通过修改操作来触发监听事件
        stat=zooKeeper.setData("/zk-student-test1","2".getBytes(),stat.getVersion());

        Thread.sleep(1000);

        zooKeeper.delete("/zk-student-test1",stat.getVersion());

    }
}
