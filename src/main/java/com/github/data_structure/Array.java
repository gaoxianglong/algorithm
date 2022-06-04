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

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 动态数组
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/6/4 12:11
 */
public class Array<T> {
    static final int DEFAULT_CAPACITY = 10;
    int size;
    Object[] array;

    Array() {
        this(DEFAULT_CAPACITY);
    }

    Array(int initCapacity) {
        array = new Object[initCapacity];
    }

    void put(T value) {
        Objects.requireNonNull(value);
        if (size >= array.length) {
            array = Arrays.copyOf(array, array.length << 1);
        }
        array[size++] = value;
    }

    T get(int index) {
        if (index < 0 || index > size) {
            return null;
        }
        return (T) array[index];
    }

    public static void main(String[] args) {
        Array<Integer> array = new Array<>();
        for (int i = 0; i < 20; i++) {
            array.put(i);
        }
        Stream.of(array.array).forEach(x -> System.out.println(x));
    }
}
