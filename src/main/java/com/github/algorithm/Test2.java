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
package com.github.algorithm;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.*;

public class Test2 {
    static ThreadLocal<String> tl = new ThreadLocal<>();
    static MyThreadLocal<String> tl2 = new MyThreadLocal<>();

    public static void main(String[] args) {
        System.out.println(27 % 26);
//        ScheduledExecutorService watchGroup = Executors.newScheduledThreadPool(10);
//        var f = watchGroup.scheduleAtFixedRate(new Thread(()->{
//            System.out.println("-----");
//        }),2,2, TimeUnit.SECONDS);
//
//        try {
//            TimeUnit.SECONDS.sleep(5);
//            f.cancel(true);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        var before = LocalDate.of(1989, 2, 25);
//        var after = LocalDate.now();
//        var period = Period.between(before, after);
//        System.out.printf("%s岁%s月%s天", period.getYears(), period.getMonths(), period.getDays());
//        tl.set("test");
//        System.out.println(tl.get());
//        new Thread(() -> {
//            System.out.println(tl.get());
//        }).start();

//        tl2.set("test");
//        System.out.println(tl2.get());
//        new Thread(() -> {
//            System.out.println(tl2.get());
//        }).start();
//
//        Vector vector = new Vector();
//        var list = new LinkedList<>();
//
//        list.removeLast();

//        var future = new FutureTask<String>(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                TimeUnit.SECONDS.sleep(5);
//                int a = 0 / 0;
//                return "Hello FutureTask";
//            }
//        });
//        new Thread(future).start();
//        try {
//            System.out.println(future.get());
//        } catch (Throwable e) {
//            System.out.println("e:" + e.getMessage());
//        }

//        Semaphore semaphore = new Semaphore(1);
//        try {
//            semaphore.acquire();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        new Thread(() -> {
//            try {
//                semaphore.acquire();
//                System.out.println("------");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();

//        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
//
//        for (var i = 0; i < 3; i++) {
//            for (var j = 0; j < cyclicBarrier.getParties(); j++) {
//                new Thread(() -> {
//                    System.out.printf("Thread:%s,wait...\n", Thread.currentThread().getName());
//                    try {
//                        cyclicBarrier.await();
//                        System.out.printf("Thread:%s,execute...\n", Thread.currentThread().getName());
//                    } catch (Throwable e) {
//                        e.printStackTrace();
//                    }
//                }).start();
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        int[] num1 = new int[]{1, 2, 3};
        int[] num2 = new int[]{1, 1, 7};
        int[] rlt = merge(num1, num2);
        Arrays.stream(rlt).forEach(System.out::println);
    }

    public static int[] merge(int[] num1, int[] num2) {
        int[] rlt = new int[num1.length + num2.length];
        int i = num1.length, i_ = 0, j = num2.length, j_ = 0, index = 0;
        for (int k = 0; k < rlt.length; k++) {
            if (i_ >= i) {
                rlt[index++] = num2[j_++];
            } else if (j_ >= j) {
                rlt[index++] = num1[i_++];
            } else if (num1[i_] < num2[j_]) {
                rlt[index++] = num1[i_++];
            } else {
                rlt[index++] = num2[j_++];
            }
        }
        return rlt;
    }

    static class MyThreadLocal<T> {
        private Map<String, T> map = new ConcurrentHashMap<>();

        public void set(T value) {
            map.put(Thread.currentThread().getName(), value);
        }

        public T get() {
            return map.get(Thread.currentThread().getName());
        }
    }
}
