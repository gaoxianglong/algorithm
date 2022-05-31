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

import java.util.Objects;

/**
 * 二分查找也称之为折半查找算法.简单来说,比较有序列表中一个元素与数列中间位置的元素大小,
 * 如果比中间位置的元素大,则继续在后半部分的数列中进行二分查找,反之在前半部分查找,
 * 每次比较的数列长度都会是之前数列的一半.
 * <p>
 * 时间复杂度:O(log n)
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/5/31 22:32
 */
public class BinarySearch {
    private Integer[] array;

    protected BinarySearch(Integer[] array) {
        this.array = array;
    }

    protected Integer search(Integer target) {
        Objects.requireNonNull(target);
        var begin = 0;
        var end = array.length;
        while (true) {
            // 长度折半
            var mid = (begin + end) >> 1;
            System.out.printf("mid:%s\n", mid);
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] > target) {
                end = --mid;
            } else {
                begin = ++mid;
            }
        }
    }

    public static void main(String[] args) {
        var array = new Integer[100];
        for (int i = 1; i <= array.length; i++) {
            array[i - 1] = i;
        }
        System.out.printf("\nindex:%s\n", new BinarySearch(array).search(3));
    }
}
