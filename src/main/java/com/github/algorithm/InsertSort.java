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
 * 和冒泡排序类似,就是拿排序元素和它前面的元素进行比较,如果小于前面的元素,则前后元素位置互换,
 * 直至比较完所有大于它的元素.
 * <p>
 * 时间复杂度O(N^(1-2))
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/6/1 22:46
 */
public class InsertSort {
    Integer[] array;

    InsertSort(Integer[] array) {
        this.array = array;
    }

    Integer[] sort() {
        Objects.requireNonNull(array);
        if (array.length < 1) {
            return null;
        }
        for (var i = 0; i < array.length; i++) {
            var temp = array[i];
            var j = i;
            for (; j > 0 && array[j - 1] > temp; j--) {
                array[j] = array[j - 1];
            }
            array[j] = temp;
        }
        return array;
    }

    public static void main(String[] args) {
        var array = new Integer[]{3, 2, 1};
        Arrays.stream(new InsertSort(array).sort()).forEach(System.out::println);
    }
}
