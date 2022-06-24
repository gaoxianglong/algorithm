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
package com.github.data_structure;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

/**
 * 有界队列
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/6/4 11:15
 */
@Data
public class Queue<T> {
    /**
     * 读指针
     */
    int read;
    /**
     * 写指针
     */
    int write;
    Object[] array;

    Queue(int initSize) {
        array = new Object[initSize];
    }

    /**
     * 入队
     *
     * @param value
     */
    void push(T value) {
        Objects.requireNonNull(value);
        var index = (write + 1) % array.length;
        if (read == index) {
            return;
        }
        array[write] = value;
        write = index;
    }

    /**
     * 获取队列第一个元素,不出队
     *
     * @return
     */
    T peek() {
        if (read == write) {
            return null;
        }
        return (T) array[read];
    }

    /**
     * 队列头元素出队
     *
     * @return
     */
    T poll() {
        var temp = peek();
        if (Objects.isNull(temp)) {
            return null;
        }
        read = (read + 1) % array.length;
        return temp;
    }

    /**
     * 可读有效元素
     *
     * @return
     */
    int size() {
        return write > read ? write - read : array.length - read + write;
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>(6);
        for (int i = 0; i < 100; i++) {
            queue.push(UUID.randomUUID().toString());
        }
        System.out.println(String.format("size:%s,first:%s,last:%s", queue.size(), queue.getRead(), queue.getWrite()));
        queue.peek();
        queue.poll();
        System.out.println(String.format("size:%s,first:%s,last:%s", queue.size(), queue.getRead(), queue.getWrite()));
    }
}
