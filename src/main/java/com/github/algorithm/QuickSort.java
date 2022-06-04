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

/**
 * 快速排序
 * <p>
 * 实际开发过程中高频使用的一种排序算法.在待排序数组中,首先找出一个数值作为基准数,然后把数组中小于基准数的元素移动到左边,
 * 大于基准数的元素移动至右边,形成2个分区,这样就相对有序,然后再把2个分区元素按照这种方式继续分区并找出基准数,然后移动,
 * 直至各个分区都只有一个数值为止.
 * <p>
 * 时间复杂度O(nlogn)
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/6/2 18:50
 */
public class QuickSort {
    int[] array;

    QuickSort(int[] array) {
        this.array = array;
    }

    void sort() {
        sort(array, 0, array.length - 1);
    }

    void sort(int[] array, int begin, int end) {
        if (begin > end) {
            return;
        }
        var l = begin;
        var r = end;
        var temp = array[l];
        while (l < r) {
            while (l < r && array[r] > temp) {
                r--;
            }
            if (l < r) {
                array[l++] = array[r];
            }
            while (l < r && array[l] < temp) {
                l++;
            }
            if (l < r) {
                array[r--] = array[l];
            }
            if (l == r) {
                array[l] = temp;
            }
            sort(array, begin, l - 1);
            sort(array, l + 1, end);
        }
    }

    public static void main(String[] args) {
        var array = new int[]{3, 1, 2, 4, 5};
        new QuickSort(array).sort();
        Arrays.stream(array).forEach(System.out::println);
    }
}
