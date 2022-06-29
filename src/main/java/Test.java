/*
 * Copyright 2019-2119 gao_xianglong@sina.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.openjdk.jol.info.ClassLayout;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/6/24 15:55
 */
public class Test {
    public static void main(String[] args) {
        Test test = new Test();
//        try {
//            new Test().run();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        test.test();
    }

    void test() {
        synchronized (this) {
            System.out.println(ClassLayout.parseInstance(this).toPrintable());
        }
    }

//    void run() throws InterruptedException {
//        //主线程访问，偏向锁设定
//        printLockName();
//        printLockName();
//        new Thread(() -> printLockName()).start();//异步线程访问，膨胀为轻量级锁
//        TimeUnit.SECONDS.sleep(1);
//        //主线程和异步线程竞争，锁膨胀为重量级锁
//        new Thread(() -> printLockName()).start();
//        printLockName();
//        TimeUnit.SECONDS.sleep(1);
//        synchronized (this){
//            System.out.printf("tid:%s\t%s\n", Thread.currentThread().getName(),
//                    ClassLayout.parseInstance(this).toPrintable());
//        }
//    }
//
//    synchronized void printLockName() {
//        System.out.printf("tid:%s\t%s\n", Thread.currentThread().getName(),
//                ClassLayout.parseInstance(this).toPrintable());
//    }
}
