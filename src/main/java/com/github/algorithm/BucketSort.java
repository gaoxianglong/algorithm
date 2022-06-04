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

import java.util.Arrays;
import java.util.Objects;

/**
 * 桶排序
 * <p>
 * 桶排序是最简单的一种排序算法.假设一个长度为m的桶,然后把每一个元素放入桶时,
 * 插入元素与索引位对应,如果碰撞数组元素值+1.
 * <p>
 * 典型的空间换时间,如果数据跨度较大会造成资源浪费
 * <p>
 * 时间复杂度O(m+n)
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/6/2 18:19
 */
public class BucketSort {
    int[] source;
    int[] target;

    BucketSort(int[] array) {
        var length = Arrays.stream(array).max().getAsInt() + 1;
        target = new int[length];
        this.source = array;
    }

    BucketSort sort() {
        for (var i = 0; i < source.length; i++) {
            target[source[i]]++;
        }
        return this;
    }

    void print() {
        for (var i = 0; i < target.length; i++) {
            for (var j = 0; j < target[i]; j++) {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        var array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 1000};
        new BucketSort(array).sort().print();
    }
}
