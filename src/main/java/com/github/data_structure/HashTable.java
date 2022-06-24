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

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * 散列表,寻址路由算法采用求余,解决hash碰撞采用链表
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/6/3 18:48
 */
@Data
public class HashTable<K, V> {
    @Data
    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node() {

        }

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /**
     * 缺省容量
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    /**
     * 扩容因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Node<K, V>[] table;
    /**
     * 元素数
     */
    int size;
    /**
     * 空间
     */
    int use;

    HashTable() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    HashTable(int initialCapacity) {
        table = new Node[initialCapacity];
    }

    synchronized void put(K key, V value) {
        check(key, value);
        var index = hash(key);
        if (Objects.isNull(table[index])) {
            table[index] = new Node<>();
        }
        var n = table[index];
        if (Objects.isNull(n.next)) {
            n.next = new Node<>(key, value, null);
            use++;
            size++;
            if (use >= table.length * DEFAULT_LOAD_FACTOR) {
                resize();
            }
        } else {
            for (n = n.next; Objects.nonNull(n); n = n.next) {
                if (n.key.equals(key)) {
                    n.key = key;
                    n.value = value;
                    return;
                }
            }
            table[index].next = new Node<>(key, value, table[index].next);
            size++;
        }
    }

    V get(K key) {
        check(key);
        var index = hash(key);
        var n = table[index];
        if (Objects.isNull(n) || Objects.isNull(n.next)) {
            return null;
        }
        for (n = n.next; Objects.nonNull(n); n = n.next) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    boolean remove(K key) {
        check(key);
        var index = hash(key);
        var n = table[index];
        if (Objects.isNull(n) && Objects.isNull(n.next)) {
            return false;
        }
        var prev = n;
        for (n = n.next; Objects.nonNull(n); prev = n, n = n.next) {
            if (n.key.equals(key)) {
                prev.next = n.next;                                          
                size--;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        var ht = new HashTable<String, String>(32);
        for (int i = 0; i < 100; i++) {
            ht.put(String.format("key:%s", i), UUID.randomUUID().toString());
        }
        System.out.println(String.format("size:%s,length:%s", ht.getSize(), ht.table.length));
        for (int j = 0; j < 50; j++) {
            ht.remove(String.format("key:%s", j));
        }
        System.out.println(String.format("size:%s,length:%s", ht.getSize(), ht.table.length));
        System.out.println(ht.get("key:51"));
        System.out.println(ht.remove("key:51"));
        System.out.println(ht.get("key:51"));
    }

    synchronized void resize() {
        use = 0;
        size = 0;
        var temp = table;
        table = new Node[table.length << 1];
        Arrays.stream(temp).filter(x -> Objects.nonNull(x) && Objects.nonNull(x.next)).forEach(x -> {
            for (x = x.next; Objects.nonNull(x); x = x.next) {
                put(x.key, x.value);
            }
        });
    }

    int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    void check(Object... objs) {
        Arrays.asList(objs).forEach(x -> {
            Objects.requireNonNull(x);
        });
    }
}
