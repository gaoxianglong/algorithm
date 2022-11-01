package com.github.bookkeeper.use;

import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.api.DigestType;
import org.apache.bookkeeper.conf.ClientConfiguration;


public class Starter2 {
    public static void main(String[] args) {
//        ClientConfiguration cfg = new ClientConfiguration();
//        cfg.setAddEntryTimeout(2000);
//        cfg.setZkServers("127.0.0.1:2181");
//        try {
//            BookKeeper bk = BookKeeper.forConfig(cfg).build();
//            var wh = bk.newCreateLedgerOp()
//                    .withPassword("123456".getBytes())
//                    .withDigestType(DigestType.CRC32)
//                    .makeAdv()
//                    .withLedgerId(41)
//                    .execute()
//                    .get();
//            long id = wh.write(1, "Hello".getBytes());
//            System.out.println(id);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
        
    }
}
