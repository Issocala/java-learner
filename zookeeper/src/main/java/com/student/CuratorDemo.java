package com.student;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework=CuratorFrameworkFactory.
                builder().connectString("127.0.0.1:2181," +
                "127.0.0.1:2182,127.0.0.1:2183").
                sessionTimeoutMs(4000).retryPolicy(new
                ExponentialBackoffRetry(1000,3)).
                namespace("luoyong").build();

        curatorFramework.start();

        // 创建多级节点 /luoyong/student/node1
        // 原生api中，必须是逐层创建，也就是父节点必须存在，子节点才能创建
        curatorFramework.create().creatingParentsIfNeeded().
                withMode(CreateMode.PERSISTENT).
                forPath("/student/node1","1".getBytes());
        // 删除
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/student/node1");

        //查询
        Stat stat=new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/student/node1");

        //修改
        curatorFramework.setData().
                withVersion(stat.getVersion()).forPath("/student/node1","2".getBytes());

        curatorFramework.close();

    }
}
