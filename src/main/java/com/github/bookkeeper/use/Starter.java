package com.github.bookkeeper.use;

import org.apache.bookkeeper.client.BKException;
import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.api.DigestType;
import org.apache.bookkeeper.client.api.LedgerEntries;
import org.apache.bookkeeper.common.concurrent.FutureUtils;
import org.apache.bookkeeper.conf.ClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Time;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Starter {
    private BookKeeper bk;
    private BookKeeper.DigestType digestType = BookKeeper.DigestType.MAC;
    private byte[] pwd = "123456".getBytes();
    private Logger logger = LoggerFactory.getLogger(Starter.class);

    Starter() throws Throwable {
        init();
    }

    void run() throws Throwable {
        try {
            Objects.requireNonNull(bk);
            //test1();
            //test2();
            test3();
        } finally {
            bk.close();
        }
    }

    void test1() throws Throwable {
        var lh = bk.createLedger(digestType, pwd);
        var ledgerId = lh.getId();
        logger.info("ledgerId:{}", ledgerId);
        var entryId = lh.addEntry("Hello Bookkeeper...".getBytes());
        logger.info("entryId:{}", entryId);
        lh.close();
        // 打开指定ledger
        lh = bk.openLedger(ledgerId, digestType, pwd);
        var entrys = lh.readEntries(0, lh.getLastAddConfirmed());
        while (entrys.hasMoreElements()) {
            var entry = entrys.nextElement();
            logger.info("entryId:{}.entry:{}", entry.getEntryId(), new String(entry.getEntry()));
        }
        lh.close();
        //bk.deleteLedger(ledgerId);
    }

    void test2() throws Throwable {
        final var lh1 = bk.createLedger(digestType, pwd);
        var ledgerId = lh1.getId();
        // openLedger缺省为恢复模式
        final var lh2 = bk.openLedger(ledgerId, digestType, pwd);
        logger.info("ledgerId:{}", ledgerId);
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            for (var i = 1; i <= 5; i++) {
                long entryId = 0;
                try {
                    entryId = lh1.addEntry(String.format("msg:%s", i).getBytes());
                    TimeUnit.SECONDS.sleep(1);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                logger.info("entryId:{}", entryId);
            }
            latch.countDown();
            System.out.println("000000");
        }).start();

        TimeUnit.SECONDS.sleep(1);
        // 恢复模式下读取ledger
        var entrys = lh2.readEntries(0, lh2.readExplicitLastConfirmed());
        while (entrys.hasMoreElements()) {
            var entry = entrys.nextElement();
            logger.info("entryId:{}.entry:{}", entry.getEntryId(), new String(entry.getEntry()));
        }
        latch.await();
        lh1.close();
        lh2.close();
        bk.deleteLedger(ledgerId);
    }

    /**
     * polling & long polling
     *
     * @throws Throwable
     */
    void test3() throws Throwable {
        try {
            var wh = bk.newCreateLedgerOp()
                    .withDigestType(DigestType.MAC)
                    .withPassword(pwd)
                    .withAckQuorumSize(2)
                    .withEnsembleSize(3)
                    .withWriteQuorumSize(3)
                    .execute()
                    .get();
            for (var i = 1; i <= 10; i++) {
                logger.info("entryId:{}", wh.append(String.format("msg-%s", i).getBytes()));
            }
            var ledgerId = wh.getId();
            System.out.println("ledgerId:" + ledgerId);
            var rh = bk.newOpenLedgerOp()
                    .withLedgerId(ledgerId)
                    //.withRecovery(false)
                    .withPassword(pwd)
                    .withDigestType(DigestType.MAC)
                    .execute()
                    .get();
            long next = 0L;
            int batchNum = 4;
            while (!rh.isClosed() || next <= rh.getLastAddConfirmed()) {
                long lac = rh.getLastAddConfirmed();
//                if (next > lac) {
//                    TimeUnit.SECONDS.sleep(1);
//                    lac = rh.readLastAddConfirmed();
//                    System.out.println("waiting data...");
//                    continue;
//                }
//                long end = Math.min(lac, next + batchNum - 1);
//                var entrys = rh.read(next, end);
//                entrys.forEach(x -> {
//                    System.out.println(new String(x.getEntryBytes()));
//                });
//                next = end + 1;
                if (next > lac) {
                    try (var lacAndEntry = rh.readLastAddConfirmedAndEntry(next, 1000, false)) {
                        if (lacAndEntry.hasEntry()) {
                            System.out.println(new String(lacAndEntry.getEntry().getEntryBytes()));
                        }
                        next++;
                    }
                } else {
                    long end = Math.min(lac, next + batchNum - 1);
                    var entrys = rh.read(next, end);
                    entrys.forEach(x -> {
                        System.out.println(new String(x.getEntryBytes()));
                    });
                    next = end + 1;
                }
            }
            System.out.println(wh.getLastAddConfirmed());
            System.out.println(rh.readLastAddConfirmed());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void init() throws Throwable {
        var cfg = new ClientConfiguration();
        cfg.setZkServers("127.0.0.1:2181");
        cfg.setAddEntryTimeout(2000);
        bk = BookKeeper.forConfig(cfg).build();
        logger.info("connection bookie success...");
    }

    public static void main(String[] args) {
        try {
            new Starter().run();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
