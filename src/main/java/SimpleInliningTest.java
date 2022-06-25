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

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/6/24 18:22
 */
public class SimpleInliningTest {
    private int count = 0;

    public static void main(String[] args) throws Exception {
        SimpleInliningTest t = new SimpleInliningTest();
        int sum = 0;
        for (int i = 0; i < 1000000; i++) {
            sum += t.m();
        }
        System.out.println(sum);
    }

    public int m() {
        int i = count;
        i = m2(i);
        i += count;
        i *= count;
        i++;
        return i;
    }

    public int m2(int i) {
        if (i % 10 == 0) {
            i += 1;
        } else if (i % 10 == 1) {
            i += 2;
        } else if (i % 10 == 2) {
            i += 3;
        }
        return i;
    }
}
