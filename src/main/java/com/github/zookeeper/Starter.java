package com.github.zookeeper;

import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter {
    private static Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
        var curator = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 2000, 10000, new ExponentialBackoffRetry(1000, 3));
        curator.start();
        try {
            // 阻塞直至链接成功
            curator.blockUntilConnected();
            logger.info("connection zk success...");
            System.out.println(curator.checkExists().forPath("/test"));
            System.out.println(curator.create().forPath("/test", "test".getBytes()));
            System.out.println(new String(curator.getData().forPath("/test")));
            System.out.println(curator.checkExists().forPath("/test"));
            System.out.println(curator.delete().forPath("/test"));
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            curator.close();
        }
    }
}
