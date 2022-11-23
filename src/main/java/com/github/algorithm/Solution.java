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

import com.scurrilous.circe.Hash;
import com.sun.source.tree.Tree;
import lombok.Data;
import lombok.val;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.w3c.dom.NodeList;

import javax.crypto.spec.PSource;
import java.io.ObjectStreamException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/6/25 11:13
 */
public class Solution {
    /**
     * 1、找出数组中第一个回文字符串
     *
     * @param words
     * @return
     */
    public String firstPalindrome(String[] words) {
        for (var word : words) {
            int l = 0, r = word.length() - 1;
            while (l < r) {
                if (word.charAt(l) != word.charAt(r)) {
                    break;
                }
                l++;
                r--;
            }
            if (l >= r) {
                return word;
            }
        }
        return "";
    }

    /**
     * 2、玩筹码
     *
     * @param position
     * @return
     */
    public int minCostToMoveChips(int[] position) {
        int odd = 0, even = 0;
        for (var p : position) {
            if ((p & 1) == 1) {
                odd++;
                continue;
            }
            even++;
        }
        return Math.min(odd, even);
    }

    /**
     * 3、两数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        var map = new ConcurrentHashMap<Integer, Integer>();
        for (var i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    /**
     * 4、搜索插入位置
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        int l = 0, r = nums.length - 1, rlt = nums.length;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if (nums[mid] == target) {
                rlt = mid;
                break;
            }
            if (nums[mid] > target) {
                rlt = mid;
                r = --mid;
            } else {
                l = ++mid;
            }
        }
        return rlt;
    }

    /**
     * 5、三数之和
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        var rlt = new ArrayList<List<Integer>>();
        if (Objects.isNull(nums) || nums.length < 1) {
            return rlt;
        }
        Arrays.sort(nums);
        var n = nums.length;
        for (var i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            var k = n - 1;
            for (var j = i + 1; j < n; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                while (j < k && nums[i] + nums[j] + nums[k] > 0) {
                    k--;
                }
                if (j >= k) {
                    break;
                }
                var t1 = nums[i];
                var t2 = nums[j];
                var t3 = nums[k];
                if (t1 + t2 + t3 == 0) {
                    rlt.add(new ArrayList<Integer>() {{
                        add(t1);
                        add(t2);
                        add(t3);
                    }});
                }
            }
        }
        return rlt;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 6、两数相加
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, temp = null;
        var carry = 0;
        while (Objects.nonNull(l1) || Objects.nonNull(l2) || carry > 0) {
            var t1 = Objects.nonNull(l1) ? l1.val : 0;
            var t2 = Objects.nonNull(l2) ? l2.val : 0;
            var sum = t1 + t2 + carry;
            if (Objects.isNull(head)) {
                head = new ListNode(sum % 10);
                temp = head;
            } else {
                temp.next = new ListNode(sum % 10);
                temp = temp.next;
            }
            carry = sum / 10;
            l1 = Objects.nonNull(l1) ? l1.next : null;
            l2 = Objects.nonNull(l2) ? l2.next : null;
        }
        return head;
    }

    /**
     * 7、无重复字符的最长子串
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int max = 0, n = s.length();
        for (var i = 0; i < n; i++) {
            var set = new HashSet<Character>();
            for (var j = i; j < n; j++) {
                if (!set.add(s.charAt(j))) {
                    break;
                }
            }
            max = Math.max(max, set.size());
        }
        return max;
    }

    /**
     * 8、最长回文子串
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int n = s.length(), max = 0, l = 0, r = 0;
        for (var i = 0; i < n; i++) {
            var t1 = expandAroundCenter(s, i, i);
            var t2 = expandAroundCenter(s, i, i + 1);
            var t3 = t1[2] > t2[2] ? t1 : t2;
            if (max < t3[2]) {
                max = t3[2];
                l = t3[0];
                r = t3[1];
            }
        }
        return s.substring(l, ++r);
    }

    public int[] expandAroundCenter(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return new int[]{++l, --r, r - l};
    }


    /**
     * 9、回文数
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x == 0) {
            return true;
        }
        if (x < 0 || x % 10 == 0) {
            return false;
        }
        var temp = 0;
        while (x > temp) {
            temp = temp * 10 + x % 10;
            x /= 10;
        }
        return x == temp || x == temp / 10;
    }

    /**
     * 10、删除链表的倒数第 N 个结点
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode l1 = new ListNode(0, head), l2 = l1;
        var stack = new LinkedList<ListNode>();
        while (Objects.nonNull(l2)) {
            stack.push(l2);
            l2 = l2.next;
        }
        for (var i = 0; i < n; i++) {
            stack.pop();
        }
        var prev = stack.pop();
        prev.next = prev.next.next;
        return l1.next;
    }

    /**
     * 11、有效的括号
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if (Objects.isNull(s) || s.length() < 2) {
            return false;
        }
        var map = new ConcurrentHashMap<Character, Character>() {{
            put('}', '{');
            put(']', '[');
            put(')', '(');
        }};
        var stack = new LinkedList<Character>();
        for (var c : s.toCharArray()) {
            if (map.containsKey(c)) {
                if (stack.peek() != map.get(c)) {
                    return false;
                }
                stack.pop();
                continue;
            }
            stack.push(c);
        }
        return stack.isEmpty();
    }

    /**
     * 12、合并两个有序链表
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (Objects.isNull(list1)) {
            return list2;
        }
        if (Objects.isNull(list2)) {
            return list1;
        }
        if (list1.val < list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list2.next, list1);
            return list2;
        }
    }

    /**
     * 13、移除元素
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        int i = 0, j = 0, n = nums.length;
        for (; j < n; j++) {
            if (nums[j] != val) {
                nums[i++] = nums[j];
            }
        }
        return i;
    }

    /**
     * 14、不动点
     *
     * @param arr
     * @return
     */
    public int fixedPoint(int[] arr) {
        int l = 0, r = arr.length - 1, rlt = -1;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if (arr[mid] < mid) {
                l = ++mid;
            } else if (arr[mid] > mid) {
                r = --mid;
            } else {
                rlt = mid;
                r = --mid;
            }
        }
        return rlt;
    }

    /**
     * 15、划分数组使最大差为K
     *
     * @param nums
     * @param k
     * @return
     */
    public int partitionArray(int[] nums, int k) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return 0;
        }
        Arrays.sort(nums);
        int i = 0, j = 1, n = nums.length, rlt = 1;
        for (; j < n; j++) {
            if (nums[j] - nums[i] > k) {
                rlt++;
                i = j;
            }
        }
        return rlt;
    }

    /**
     * 16、岛屿的最大面积
     *
     * @param grid
     * @return
     */
    public int maxAreaOfIsland(int[][] grid) {
        int max = 0, m = grid.length, n = grid[0].length;
        for (var i = 0; i < m; i++) {
            for (var j = 0; j < n; j++) {
                max = Math.max(dfs(grid, i, j), max);
            }
        }
        return max;
    }

    public int dfs(int[][] grid, int i, int j) {
        if ((i < 0 || i >= grid.length) || (j < 0 || j >= grid[0].length) || grid[i][j] == 0) {
            return 0;
        }
        grid[i][j] = 0;
        var rlt = 1;
        var t1 = new int[]{0, 0, 1, -1};
        var t2 = new int[]{1, -1, 0, 0};
        for (var k = 0; k < 4; k++) {
            rlt += dfs(grid, i + t1[k], j + t2[k]);
        }
        return rlt;
    }


    /**
     * 17、统计一致字符串的数目
     *
     * @param allowed
     * @param words
     * @return
     */
    public int countConsistentStrings(String allowed, String[] words) {
        var rlt = 0;
        for (var word : words) {
            int i = 0;
            while (i < word.length()) {
                if (allowed.indexOf(word.charAt(i)) != -1) {
                    i++;
                    continue;
                }
                break;
            }
            if (i >= word.length()) {
                rlt++;
            }
        }
        return rlt;
    }

    static class Transaction {
        String t;
        String name;
        int time;
        int amount;
        String city;
        boolean valid;

        Transaction(String t, String name, int time, int amount, String city) {
            this.t = t;
            this.name = name;
            this.time = time;
            this.amount = amount;
            this.city = city;
            this.valid = amount > 1000;
        }

        public void check(Transaction transaction) {
            var dt = Math.abs(transaction.time - this.time);
            if (transaction.name.equals(this.name) && !transaction.city.equals(this.city) && dt <= 60) {
                transaction.valid = true;
                this.valid = true;
            }
        }
    }

    /**
     * 18、查询无效交易
     *
     * @param transactions
     * @return
     */
    public List<String> invalidTransactions(String[] transactions) {
        var rlt = new ArrayList<String>();
        var ts = new ArrayList<Transaction>() {{
            for (var t : transactions) {
                var temp = t.split(",");
                add(new Transaction(t, temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), temp[3]));
            }
        }};
        var n = ts.size();
        for (var i = 0; i < n; i++) {
            if (ts.get(i).valid) {
                continue;
            }
            for (var j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                ts.get(i).check(ts.get(j));
            }
        }
        ts.stream().filter(x -> x.valid).forEach(x -> rlt.add(x.t));
        return rlt;
    }

    /**
     * 19、删掉一个元素以后全为 1 的最长子数组
     *
     * @param nums
     * @return
     */
    public int longestSubarray(int[] nums) {
        int max = 0, count = 0, prev = 0;
        for (var n : nums) {
            if ((n & 1) == 1) {
                count++;
                prev++;
            } else {
                count = prev;
                prev = 0;
            }
            max = Math.max(max, count);
        }
        return max >= nums.length ? --max : max;
    }

    /**
     * 20、盛最多水的容器
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int max = 0, l = 0, r = height.length - 1;
        while (l < r) {
            if (height[l] < height[r]) {
                max = Math.max(max, (r - l) * height[l]);
                l++;
                continue;
            }
            max = Math.max(max, (r - l) * height[r]);
            r--;
        }
        return max;
    }

    /**
     * 21、检查二进制字符串字段
     *
     * @param s
     * @return
     */
    public boolean checkOnesSegment(String s) {
        return s.indexOf("01") == -1;
    }

    /**
     * 22、二分查找
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1, rlt = -1;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if (nums[mid] == target) {
                rlt = mid;
                break;
            }
            if (nums[mid] < target) {
                l = ++mid;
            } else {
                r = --mid;
            }
        }
        return rlt;
    }

    /**
     * 23、爬楼梯
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n < 1) {
            return 0;
        }
        int i = 0, j = 0, k = 1;
        for (var l = 0; l < n; l++) {
            i = j;
            j = k;
            k = i + j;
        }
        return k;
    }

    /**
     * 24、最接近的三数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {
        var rlt = 0;
        if (Objects.isNull(nums) || nums.length < 1) {
            return rlt;
        }
        Arrays.sort(nums);
        int n = nums.length, min = Integer.MAX_VALUE;
        l1:
        for (var i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int j = i + 1, k = n - 1;
            while (j < k) {
                var sum = nums[i] + nums[j] + nums[k];
                if (sum == target) {
                    rlt = sum;
                    break l1;
                }
                var dv = Math.abs(target - sum);
                if (min > dv) {
                    min = dv;
                    rlt = sum;
                }
                if (sum < target) {
                    j++;
                } else {
                    k--;
                }
            }
        }
        return rlt;
    }

    /**
     * 25、跳跃游戏
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        var max = 0;
        for (var i = 0; i < nums.length; i++) {
            if (i > max) {
                return false;
            }
            max = Math.max(max, nums[i] + i);
        }
        return true;
    }

    /**
     * 26、回文链表
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        var temp = reserve(getCenterNode(head));
        while (Objects.nonNull(temp)) {
            if (temp.val != head.val) {
                return false;
            }
            temp = temp.next;
            head = head.next;
        }
        return true;
    }

    public ListNode reserve(ListNode n) {
        ListNode l1 = null, l2 = n;
        while (Objects.nonNull(l2)) {
            var next = l2.next;
            l2.next = l1;
            l1 = l2;
            l2 = next;
        }
        return l1;
    }

    public ListNode getCenterNode(ListNode n) {
        ListNode l1 = n, l2 = n.next;
        while (Objects.nonNull(l2) && Objects.nonNull(l2.next)) {
            l2 = l2.next.next;
            l1 = l1.next;
        }
        return l1.next;
    }

    /**
     * 27、跳跃游戏2
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int rlt = 0, max = 0, end = 0, n = nums.length;
        for (var i = 0; i < n - 1; i++) {
            max = Math.max(max, i + nums[i]);
            if (i == end) {
                end = max;
                rlt++;
            }
        }
        return rlt;
    }

    /**
     * 28、组合总和
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        var rlt = new ArrayList<List<Integer>>();
        dfs(rlt, candidates, target, 0, new LinkedList<Integer>());
        return rlt;
    }

    public void dfs(List<List<Integer>> rlt, int[] candidates, int target, int index, LinkedList<Integer> path) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            rlt.add(new ArrayList<>(path));
            return;
        }
        for (var i = index; i < candidates.length; i++) {
            path.add(candidates[i]);
            dfs(rlt, candidates, target - candidates[i], i, path);
            path.removeLast();
        }
    }


    /**
     * 29、电话号码的字母组合
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        var rlt = new ArrayList<String>();
        if (Objects.isNull(digits) || digits.length() < 1) {
            return rlt;
        }
        var map = new ConcurrentHashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        dfs(digits, map, rlt, new StringBuilder(), 0);
        return rlt;
    }

    public void dfs(String digits, Map<Character, String> map, List<String> rlt, StringBuilder path, int index) {
        if (index >= digits.length()) {
            rlt.add(path.toString());
            return;
        }
        var temp = map.get(digits.charAt(index));
        for (var i = 0; i < temp.length(); i++) {
            path.append(temp.charAt(i));
            dfs(digits, map, rlt, path, index + 1);
            path.deleteCharAt(path.length() - 1);
        }
    }

    /**
     * 30、组合总和2
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        var rlt = new ArrayList<List<Integer>>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, rlt, new LinkedList<>());
        return rlt;
    }

    public void dfs(int[] candidates, int target, int index, List<List<Integer>> rlt, LinkedList<Integer> path) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            rlt.add(new ArrayList<>(path));
            return;
        }
        for (var i = index; i < candidates.length; i++) {
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }
            path.add(candidates[i]);
            dfs(candidates, target - candidates[i], i + 1, rlt, path);
            path.removeLast();
        }
    }

    /**
     * 31、字符串相乘
     *
     * @param num1
     * @param num2
     * @return
     */
    public String multiply(String num1, String num2) {
        var rlt = "0";
        if (num1.equals("0") || num2.equals("0")) {
            return rlt;
        }
        for (var i = num1.length() - 1; i >= 0; i--) {
            var t1 = num1.charAt(i) - '0';
            var builder = new StringBuilder();
            for (var j = num1.length() - 1; j > i; j--) {
                builder.append('0');
            }
            var carry = 0;
            for (var k = num2.length() - 1; k >= 0; k--) {
                var t2 = num2.charAt(k) - '0';
                var sum = t1 * t2 + carry;
                builder.append(sum % 10);
                carry = sum / 10;
            }
            if (carry > 0) {
                builder.append(carry);
            }
            rlt = addStrings(rlt, builder.reverse().toString());
        }
        return rlt;
    }

    /**
     * 32、字符串相加
     *
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings(String num1, String num2) {
        var rlt = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            var t1 = i >= 0 ? num1.charAt(i--) - '0' : 0;
            var t2 = j >= 0 ? num2.charAt(j--) - '0' : 0;
            var sum = t1 + t2 + carry;
            rlt.append(sum % 10);
            carry = sum / 10;
        }
        return rlt.reverse().toString();
    }

    /**
     * 33、下一个排列
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        var i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i < 0) {
            reserve(nums, 0);
            return;
        }
        var j = nums.length - 1;
        while (j >= 0 && nums[i] >= nums[j]) {
            j--;
        }
        swap(nums, i, j);
        reserve(nums, i + 1);
    }

    public void swap(int[] nums, int i, int j) {
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }

    public void reserve(int[] nums, int start) {
        var end = nums.length - 1;
        while (start < end) {
            swap(nums, start++, end--);
        }
    }


    /**
     * 34、搜索旋转排序数组
     *
     * @param nums
     * @param target
     * @return
     */
    public int search2(int[] nums, int target) {
        int l = 0, r = nums.length - 1, rlt = -1;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if (nums[mid] == target) {
                rlt = mid;
                break;
            }
            if (nums[0] <= nums[mid]) {
                if (target >= nums[0] && target < nums[mid]) {
                    r = --mid;
                } else {
                    l = ++mid;
                }
            } else {
                if (target > nums[mid] && target <= nums[nums.length - 1]) {
                    l = ++mid;
                } else {
                    r = --mid;
                }
            }
        }
        return rlt;
    }

    /**
     * 35、四数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        var rlt = new ArrayList<List<Integer>>();
        var n = nums.length;
        for (var i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (var j = i + 1; j < n; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                var k = n - 1;
                for (var l = j + 1; l < n; l++) {
                    if (l > j + 1 && nums[l] == nums[l - 1]) {
                        continue;
                    }
                    while (l < k && nums[i] + nums[j] + nums[k] + nums[l] > target) {
                        k--;
                    }
                    if (l >= k) {
                        break;
                    }
                    var t1 = nums[i];
                    var t2 = nums[j];
                    var t3 = nums[k];
                    var t4 = nums[l];
                    if ((long) t1 + t2 + t3 + t4 == target) {
                        rlt.add(new ArrayList<>() {{
                            add(t1);
                            add(t2);
                            add(t3);
                            add(t4);
                        }});
                    }
                }
            }
        }
        return rlt;
    }

    /**
     * 36、括号生成
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        var rlt = new ArrayList<String>();
        dfs(rlt, n, 0, 0, new StringBuilder());
        return rlt;
    }

    public void dfs(List<String> rlt, int n, int i, int j, StringBuilder path) {
        if (n << 1 == path.length()) {
            rlt.add(path.toString());
            return;
        }
        if (i < n) {
            path.append('(');
            dfs(rlt, n, i + 1, j, path);
            path.deleteCharAt(path.length() - 1);
        }
        if (j < i) {
            path.append(')');
            dfs(rlt, n, i, j + 1, path);
            path.deleteCharAt(path.length() - 1);
        }
    }


    /**
     * 37、两两交换链表中的节点
     *
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }
        var next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }

    /**
     * 38、接雨水
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int l = 0, r = height.length - 1, max = 0, lm = 0, rm = 0;
        while (l < r) {
            lm = Math.max(lm, height[l]);
            rm = Math.max(rm, height[r]);
            if (height[l] < height[r]) {
                max += lm - height[l++];
                continue;
            }
            max += rm - height[r--];
        }
        return max;
    }

    /**
     * 39、找出字符串中第一个匹配项的下标
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {
        if (Objects.isNull(needle)) {
            return 0;
        }
        for (var i = 0; i < haystack.length(); i++) {
            int j = i, k = 0;
            while (j < haystack.length() && k < needle.length()) {
                if (haystack.charAt(j) != needle.charAt(k)) {
                    break;
                }
                j++;
                k++;
            }
            if (k >= needle.length()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 40、最长公共前缀
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (Objects.isNull(strs) || strs.length < 1) {
            return "";
        }
        var prefix = strs[0];
        for (var i = 1; i < strs.length; i++) {
            int j = 0, k = 0;
            while (j < prefix.length() && k < strs[i].length()) {
                if (prefix.charAt(j) != strs[i].charAt(k)) {
                    break;
                }
                j++;
                k++;
            }
            prefix = prefix.substring(0, j);
        }
        return prefix;
    }

    /**
     * 41、两数相除
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public int divide(int dividend, int divisor) {
        if (dividend == 0) {
            return 0;
        }
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        var negative = (dividend ^ divisor) < 0;
        var dividend_new = Math.abs(dividend * 1L);
        var divisor_new = Math.abs(divisor * 1L);
        var rlt = 0;
        for (var i = 31; i >= 0; i--) {
            if (dividend_new >> i >= divisor_new) {
                rlt += 1 << i;
                dividend_new -= divisor_new << i;
            }
        }
        return negative ? -rlt : rlt;
    }

    /**
     * 42、删除有序数组中的重复项
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        int i = 0, j = 1, n = nums.length;
        for (; j < n; j++) {
            if (nums[i] != nums[j]) {
                nums[++i] = nums[j];
            }
        }
        return ++i;
    }

    /**
     * 43、有效的数独
     *
     * @param board
     * @return
     */
    public boolean isValidSudoku(char[][] board) {
        var r = new int[9][9];
        var c = new int[9][9];
        var l = new int[3][3][9];
        int m = board.length, n = board[0].length;
        for (var i = 0; i < m; i++) {
            for (var j = 0; j < n; j++) {
                var temp = board[i][j];
                if (temp == '.') {
                    continue;
                }
                var index = temp - '0' - 1;
                if (++r[i][index] > 1 || ++c[j][index] > 1 || ++l[i / 3][j / 3][index] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 44、二叉树的中序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        var rlt = new ArrayList<Integer>();
        inorderTraversal(root, rlt);
        return rlt;
    }

    public void inorderTraversal(TreeNode n, List<Integer> rlt) {
        if (Objects.isNull(n)) {
            return;
        }
        inOrder(n.left, rlt);
        rlt.add(n.val);
        inOrder(n.right, rlt);
    }

    /**
     * 45、合并两个有序数组
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int m_ = 0, n_ = 0, index = 0;
        var temp = new int[nums1.length];
        while (index < temp.length) {
            if (m_ >= m) {
                temp[index++] = nums2[n_++];
            } else if (n_ >= n) {
                temp[index++] = nums1[m_++];
            } else if (nums1[m_] < nums2[n_]) {
                temp[index++] = nums1[m_++];
            } else {
                temp[index++] = nums2[n_++];
            }
        }
        for (var i = 0; i < temp.length; i++) {
            nums1[i] = temp[i];
        }
    }

    /**
     * 46、二叉树的前序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        var rlt = new ArrayList<Integer>();
        preorderTraversal(root, rlt);
        return rlt;
    }

    public void preorderTraversal(TreeNode n, List<Integer> rlt) {
        if (Objects.isNull(n)) {
            return;
        }
        rlt.add(n.val);
        preorderTraversal(n.left, rlt);
        preorderTraversal(n.right, rlt);
    }

    /**
     * 二叉树的后序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        var rlt = new ArrayList<Integer>();
        postorderTraversal(root, rlt);
        return rlt;
    }

    public void postorderTraversal(TreeNode n, List<Integer> rlt) {
        if (Objects.isNull(n)) {
            return;
        }
        postorderTraversal(n.left, rlt);
        postorderTraversal(n.right, rlt);
        rlt.add(n.val);
    }

    /**
     * 48、x的平方根
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        int l = 0, r = x, rlt = 0;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if ((long) mid * mid <= x) {
                rlt = mid;
                l = ++mid;
            } else {
                r = --mid;
            }
        }
        return rlt;
    }

    /**
     * 49、位1的个数
     *
     * @param n
     * @return
     */
    public int hammingWeight(int n) {
        var rlt = 0;
        for (var i = 31; i >= 0; i--) {
            if (((n >> i) & 1) == 1) {
                rlt++;
            }
        }
        return rlt;
    }

    /**
     * 50、多数元素
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        int rlt = 0, count = 0;
        for (var n : nums) {
            if (count == 0) {
                rlt = n;
            }
            count += n == rlt ? 1 : -1;
        }
        return rlt;
    }

    /**
     * 51、合并K个升序链表
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode rlt = null;
        for (var l : lists) {
            rlt = mergeKLists(rlt, l);
        }
        return rlt;
    }

    public ListNode mergeKLists(ListNode l1, ListNode l2) {
        if (Objects.isNull(l1)) {
            return l2;
        }
        if (Objects.isNull(l2)) {
            return l1;
        }
        if (l1.val < l2.val) {
            l1.next = mergeKLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeKLists(l2.next, l1);
            return l2;
        }
    }

    /**
     * 52、最长有效括号
     *
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        var max = 0;
        var stack = new LinkedList<Integer>() {{
            push(-1);
        }};
        for (var i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
                continue;
            }
            stack.pop();
            if (stack.isEmpty()) {
                stack.push(i);
                continue;
            }
            max = Math.max(max, i - stack.peek());
        }
        return max;
    }

    /**
     * 53、Excel表列名称
     *
     * @param columnNumber
     * @return
     */
    public String convertToTitle(int columnNumber) {
        var rlt = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--;
            rlt.append((char) (columnNumber % 26 + 'A'));
            columnNumber /= 26;
        }
        return rlt.reverse().toString();
    }

    /**
     * 54、只出现一次的数字
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        var rlt = 0;
        for (var n : nums) {
            rlt ^= n;
        }
        return rlt;
    }

    /**
     * 55、买卖股票的最佳时机
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int max = 0, min = Integer.MAX_VALUE;
        for (var p : prices) {
            if (min > p) {
                min = p;
                continue;
            }
            max = Math.max(max, p - min);
        }
        return max;
    }

    /**
     * 56、删除排序链表中的重复元素
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        var rlt = head;
        while (Objects.nonNull(head) && Objects.nonNull(head.next)) {
            if (head.val == head.next.val) {
                head.next = head.next.next;
                continue;
            }
            head = head.next;
        }
        return rlt;
    }

    /**
     * 57、二进制求和
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        var rlt = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            var t1 = i >= 0 ? a.charAt(i--) - '0' : 0;
            var t2 = j >= 0 ? b.charAt(j--) - '0' : 0;
            var sum = t1 + t2 + carry;
            rlt.append(sum % 2);
            carry = sum / 2;
        }
        return rlt.reverse().toString();
    }

    /**
     * 58、加一
     *
     * @param digits
     * @return
     */
    public int[] plusOne(int[] digits) {
        var n = digits.length;
        var last = digits[n - 1] + 1;
        var carry = last / 10;
        last = last % 10;
        var nums = new ArrayList<Integer>();
        nums.add(last);
        var i = n - 2;
        while (i >= 0 || carry > 0) {
            var sum = (i >= 0 ? digits[i--] : 0) + carry;
            nums.add(sum % 10);
            carry = sum / 10;
        }
        Collections.reverse(nums);
        var index = 0;
        var rlt = new int[nums.size()];
        for (var num : nums) {
            rlt[index++] = num;
        }
        return rlt;
    }

    /**
     * 59、最后一个单词的长度
     *
     * @param s
     * @return
     */
    public int lengthOfLastWord(String s) {
        var temp = s.split(" ");
        return temp[temp.length - 1].length();
    }

    /**
     * 60、相同的树
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        return dfs(p, q);
    }

    public boolean dfs(TreeNode p, TreeNode q) {
        if ((Objects.isNull(p) && Objects.nonNull(q)) || (Objects.nonNull(p) && Objects.isNull(q))) {
            return false;
        }
        if (Objects.nonNull(p) && Objects.nonNull(q)) {
            if (q.val != p.val) {
                return false;
            }
            return dfs(p.left, q.left) && dfs(p.right, q.right);
        }
        return true;
    }

    /**
     * 61、环形链表
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return false;
        }
        ListNode slow = head, fast = head.next;
        while (Objects.nonNull(fast) && Objects.nonNull(fast.next)) {
            if (fast == slow) {
                return true;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        return false;
    }

    /**
     * 62、寻找两个正序数组的中位数
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        var temp = merge(nums1, nums2);
        var n = temp.length;
        if ((n & 1) == 1) {
            return temp[n >> 1];
        }
        int l = (n >> 1) - 1, r = n >> 1;
        return (temp[l] + temp[r]) / 2.0d;
    }

    public int[] merge(int[] nums1, int[] nums2) {
        int m = nums1.length, m_ = 0, n = nums2.length, n_ = 0, index = 0;
        var rlt = new int[m + n];
        while (index < m + n) {
            if (m_ >= m) {
                rlt[index++] = nums2[n_++];
            } else if (n_ >= n) {
                rlt[index++] = nums1[m_++];
            } else if (nums1[m_] < nums2[n_]) {
                rlt[index++] = nums1[m_++];
            } else {
                rlt[index++] = nums2[n_++];
            }
        }
        return rlt;
    }

    /**
     * 63、Pow(x,n)
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        var negative = (n ^ 0) < 0;
        n = negative ? -n : n;
        var rlt = quickPower(x, n);
        return negative ? 1.0d / rlt : rlt;
    }

    public double quickPower(double x, int n) {
        if (n == 0) {
            return 1;
        }
        var rlt = quickPower(x, n / 2);
        return (n & 1) == 0 ? rlt * rlt : rlt * rlt * x;
    }

    /**
     * 64、二叉树的最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        return getHeight(root);
    }

    public int getHeight(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }

    /**
     * 65、对称二叉树
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (Objects.isNull(root)) {
            return false;
        }
        return isSymmetric(root.left, root.right);
    }

    public boolean isSymmetric(TreeNode n1, TreeNode n2) {
        if ((Objects.isNull(n1) && Objects.nonNull(n2)) || (Objects.nonNull(n1) && Objects.isNull(n2))) {
            return false;
        }
        if (Objects.nonNull(n1) && Objects.nonNull(n2)) {
            if (n1.val != n2.val) {
                return false;
            }
            return isSymmetric(n1.left, n2.right) && isSymmetric(n1.right, n2.left);
        }
        return true;
    }


    /**
     * 66、二叉树的层序遍历
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        var rlt = new ArrayList<List<Integer>>();
        if (Objects.isNull(root)) {
            return rlt;
        }
        var queue = new LinkedList<TreeNode>() {{
            offer(root);
        }};
        while (!queue.isEmpty()) {
            var n = queue.size();
            var path = new ArrayList<Integer>();
            for (var i = 0; i < n; i++) {
                var temp = queue.poll();
                path.add(temp.val);
                if (Objects.nonNull(temp.left)) {
                    queue.offer(temp.left);
                }
                if (Objects.nonNull(temp.right)) {
                    queue.offer(temp.right);
                }
            }
            rlt.add(path);
        }
        return rlt;
    }

    /**
     * 67、第三大的数
     *
     * @param nums
     * @return
     */
    public int thirdMax(int[] nums) {
        Arrays.sort(nums);
        int i = 0, j = 1, n = nums.length;
        for (; j < n; j++) {
            if (nums[i] != nums[j]) {
                nums[++i] = nums[j];
            }
        }
        i++;
        return i < 3 ? nums[--i] : nums[i - 3];
    }

    /**
     * 68、Nim游戏
     *
     * @param n
     * @return
     */
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

    /**
     * 69、跳跃游戏III
     *
     * @param arr
     * @param start
     * @return
     */
    public boolean canReach(int[] arr, int start) {
        return dfs(arr, start, new int[arr.length]);
    }

    public boolean dfs(int[] arr, int start, int[] path) {
        if (start < 0 || start >= arr.length || path[start] > 0) {
            return false;
        }
        if (arr[start] == 0) {
            return true;
        }
        path[start]++;
        return dfs(arr, start + arr[start], path) || dfs(arr, start - arr[start], path);
    }

    /**
     * 70、同构字符串
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s, String t) {
        var mapping = new ConcurrentHashMap<Character, Character>();
        var isMapping = new HashSet<Character>();
        var n = s.length();
        for (var i = 0; i < n; i++) {
            var c1 = s.charAt(i);
            var c2 = t.charAt(i);
            if (mapping.containsKey(c1)) {
                if (mapping.get(c1) != c2) {
                    return false;
                }
                continue;
            }
            if (!isMapping.add(c2)) {
                return false;
            }
            mapping.put(c1, c2);
        }
        return true;
    }

    /**
     * 71、买卖股票的最佳时机II
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int max = 0, i = 0, j = 1;
        for (; j < prices.length; i++, j++) {
            if (prices[i] < prices[j]) {
                max += prices[j] - prices[i];
            }
        }
        return max;
    }

    /**
     * 72、顺时针打印矩阵
     *
     * @param matrix
     * @return
     */
    public int[] spiralOrder(int[][] matrix) {
        if (Objects.isNull(matrix) || matrix.length < 1) {
            return new int[]{};
        }
        int m = matrix.length, n = matrix[0].length, l = 0, r = n - 1, u = 0, d = m - 1, index = 0;
        var rlt = new int[m * n];
        while (true) {
            for (var i = l; i <= r; i++) {
                rlt[index++] = matrix[u][i];
            }
            if (++u > d) {
                break;
            }
            for (var i = u; i <= d; i++) {
                rlt[index++] = matrix[i][r];
            }
            if (--r < l) {
                break;
            }
            for (var i = r; i >= l; i--) {
                rlt[index++] = matrix[d][i];
            }
            if (--d < u) {
                break;
            }
            for (var i = d; i >= u; i--) {
                rlt[index++] = matrix[i][l];
            }
            if (++l > r) {
                break;
            }
        }
        return rlt;
    }

    /**
     * 73、整数反转
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        if (x == 0) {
            return x;
        }
        var negative = (x ^ 0) < 0;
        x = negative ? -x : x;
        int rlt = 0, first = 0;
        while (x > 0) {
            rlt = rlt * 10 + x % 10;
            first = x;
            x /= 10;
        }
        if (first != rlt % 10) {
            return 0;
        }
        return negative ? -rlt : rlt;
    }

    /**
     * 74、无重复字符串的排列组合
     *
     * @param s
     * @return
     */
    public String[] permutation(String s) {
        var rlt = new ArrayList<String>();
        dfs(s.toCharArray(), rlt, 0);
        return rlt.toArray(new String[rlt.size()]);
    }

    public void dfs(char[] cs, List<String> rlt, int index) {
        if (index >= cs.length) {
            var path = new StringBuilder();
            for (var c : cs) {
                path.append(c);
            }
            rlt.add(path.toString());
            return;
        }
        for (var i = index; i < cs.length; i++) {
            permutationSwap(cs, i, index);
            dfs(cs, rlt, index + 1);
            permutationSwap(cs, i, index);
        }
    }

    public void permutationSwap(char[] cs, int i, int j) {
        if (i == j) {
            return;
        }
        cs[i] ^= cs[j];
        cs[j] ^= cs[i];
        cs[i] ^= cs[j];
    }


    /**
     * 75、最长回文串
     *
     * @param s
     * @return
     */
    public int longestPalindrome2(String s) {
        var nums = new int[128];
        for (var c : s.toCharArray()) {
            nums[c]++;
        }
        var max = 0;
        for (var i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                continue;
            }
            max += nums[i] >> 1 << 1;
            if ((nums[i] & 1) == 1 && (max & 1) == 0) {
                max++;
            }
        }
        return max;
    }

    /**
     * 76、斐波那契数
     *
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n < 1) {
            return 0;
        }
        int i = 0, j = 0, k = 1;
        for (var l = 1; l < n; l++) {
            i = j;
            j = k;
            k = i + j;
        }
        return k;
    }

    /**
     * 77、螺旋矩阵
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder2(int[][] matrix) {
        var rlt = new ArrayList<Integer>();
        int m = matrix.length, n = matrix[0].length, l = 0, r = n - 1, u = 0, d = m - 1;
        while (true) {
            for (var i = l; i <= r; i++) {
                rlt.add(matrix[u][i]);
            }
            if (++u > d) {
                break;
            }
            for (var i = u; i <= d; i++) {
                rlt.add(matrix[i][r]);
            }
            if (--r < l) {
                break;
            }
            for (var i = r; i >= l; i--) {
                rlt.add(matrix[d][i]);
            }
            if (--d < u) {
                break;
            }
            for (var i = d; i >= u; i--) {
                rlt.add(matrix[i][l]);
            }
            if (++l > r) {
                break;
            }
        }
        return rlt;
    }

    /**
     * 78、杨辉三角
     *
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {
        var rlt = new ArrayList<List<Integer>>();
        var dp = new int[numRows][numRows];
        for (var i = 0; i < numRows; i++) {
            for (var j = 0; j <= i; j++) {
                dp[i][j] = 1;
            }
        }
        for (var i = 0; i < numRows; i++) {
            var path = new ArrayList<Integer>();
            for (var j = 0; j <= i; j++) {
                if (i - 1 >= 0 && j - 1 >= 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - 1];
                }
                path.add(dp[i][j]);
            }
            rlt.add(path);
        }
        return rlt;
    }

    /**
     * 79、杨辉三角II
     *
     * @param rowIndex
     * @return
     */
    public List<Integer> getRow(int rowIndex) {
        var rlt = new ArrayList<Integer>();
        var dp = new int[rowIndex + 1][rowIndex + 1];
        for (var i = 0; i <= rowIndex; i++) {
            for (var j = 0; j <= i; j++) {
                dp[i][j] = 1;
            }
        }
        for (var i = 0; i <= rowIndex; i++) {
            for (var j = 0; j <= i; j++) {
                if (i - 1 >= 0 && j - 1 >= 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - 1];
                }
                if (i == rowIndex) {
                    rlt.add(dp[i][j]);
                }
            }
        }
        return rlt;
    }

    /**
     * 80、比特位计数
     *
     * @param n
     * @return
     */
    public int[] countBits(int n) {
        var rlt = new int[n + 1];
        for (var i = 0; i <= n; i++) {
            rlt[i] = rlt[i >> 1] + (i & 1);
        }
        return rlt;
    }

    /**
     * 81、判断子序列
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;
        for (; j < t.length() && i < s.length(); j++) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
        }
        return i >= s.length();
    }

    /**
     * 82、第N个泰波那契数
     *
     * @param n
     * @return
     */
    public int tribonacci(int n) {
        if (n < 1) {
            return 0;
        }
        if (n < 2) {
            return 1;
        }
        int i = 0, j = 0, k = 1, l = 1;
        for (var z = 2; z < n; z++) {
            i = j;
            j = k;
            k = l;
            l = i + j + k;
        }
        return l;
    }

    /**
     * 83、四数相加II
     *
     * @param nums1
     * @param nums2
     * @param nums3
     * @param nums4
     * @return
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        var rlt = 0;
        var map = new ConcurrentHashMap<Integer, Integer>();
        for (var n1 : nums1) {
            for (var n2 : nums2) {
                var sum = n1 + n2;
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }
        for (var n3 : nums3) {
            for (var n4 : nums4) {
                var sum = -(n3 + n4);
                if (map.containsKey(sum)) {
                    rlt += map.get(sum);
                }
            }
        }
        return rlt;
    }

    /**
     * 84、使用最小花费爬楼梯
     *
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {
        if (Objects.isNull(cost) || cost.length < 1) {
            return 0;
        }
        var n = cost.length;
        var dp = new int[n + 1];
        for (var i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        return dp[n];
    }

    /**
     * 85、获取生成数组中的最大值
     *
     * @param n
     * @return
     */
    public int getMaximumGenerated(int n) {
        if (n < 2) {
            return n;
        }
        var nums = new int[n + 1];
        nums[1] = 1;
        var max = 0;
        for (var i = 2; i < nums.length; i++) {
            if ((i & 1) == 0) {
                nums[i] = nums[i >> 1];
            } else {
                nums[i] = nums[i >> 1] + nums[(i >> 1) + 1];
            }
            max = Math.max(max, nums[i]);
        }
        return max;
    }

    /**
     * 86、将数组分成和相等的三个部分
     *
     * @param arr
     * @return
     */
    public boolean canThreePartsEqualSum(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 1) {
            return false;
        }
        var sum = 0;
        for (var n : arr) {
            sum += n;
        }
        if (sum % 3 != 0) {
            return false;
        }
        int temp = 0, count = 0;
        for (var n : arr) {
            temp += n;
            if (temp == sum / 3) {
                temp = 0;
                count++;
            }
        }
        return count >= 3;
    }

    /**
     * 87、两数相加II
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        l1 = nodeReserve(l1);
        l2 = nodeReserve(l2);
        ListNode head = null, temp = null;
        var carry = 0;
        while (Objects.nonNull(l1) || Objects.nonNull(l2) || carry > 0) {
            var t1 = Objects.nonNull(l1) ? l1.val : 0;
            var t2 = Objects.nonNull(l2) ? l2.val : 0;
            var sum = t1 + t2 + carry;
            if (Objects.isNull(head)) {
                head = new ListNode(sum % 10);
                temp = head;
            } else {
                temp.next = new ListNode(sum % 10);
                temp = temp.next;
            }
            carry = sum / 10;
            l1 = Objects.nonNull(l1) ? l1.next : null;
            l2 = Objects.nonNull(l2) ? l2.next : null;
        }
        return nodeReserve(head);
    }

    public ListNode nodeReserve(ListNode n) {
        ListNode l1 = null, l2 = n;
        while (Objects.nonNull(l2)) {
            var next = l2.next;
            l2.next = l1;
            l1 = l2;
            l2 = next;
        }
        return l1;
    }


    /**
     * 88、螺旋矩阵II
     *
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        var rlt = new int[n][n];
        int l = 0, r = n - 1, u = 0, d = n - 1, index = 1;
        while (true) {
            for (var i = l; i <= r; i++) {
                rlt[u][i] = index++;
            }
            if (++u > d) {
                break;
            }
            for (var i = u; i <= d; i++) {
                rlt[i][r] = index++;
            }
            if (--r < l) {
                break;
            }
            for (var i = r; i >= l; i--) {
                rlt[d][i] = index++;
            }
            if (--d < l) {
                break;
            }
            for (var i = d; i >= u; i--) {
                rlt[i][l] = index++;
            }
            if (++l > r) {
                break;
            }
        }
        return rlt;
    }

    /**
     * 89、旋转链表
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (Objects.isNull(head) || k < 1) {
            return head;
        }
        var n = 1;
        var last = head;
        while (Objects.nonNull(last.next)) {
            last = last.next;
            n++;
        }
        last.next = head;
        last = head;
        k %= n;
        for (var i = 0; i < n - k - 1; i++) {
            last = last.next;
        }
        head = last.next;
        last.next = null;
        return head;
    }

    /**
     * 90、找不同
     *
     * @param s
     * @param t
     * @return
     */
    public char findTheDifference(String s, String t) {
        var nums = new int[128];
        for (var c : s.toCharArray()) {
            nums[c]++;
        }
        for (var c : t.toCharArray()) {
            nums[c]--;
        }
        char rlt = 0;
        for (var i = 0; i < nums.length; i++) {
            if (nums[i] < 0) {
                rlt = (char) (i);
                break;
            }
        }
        return rlt;
    }

    /**
     * 91、移动零
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int i = 0, j = 0, n = nums.length;
        for (; j < n; j++) {
            if (nums[j] != 0) {
                nums[i++] = nums[j];
            }
        }
        for (; i < n; i++) {
            nums[i] = 0;
        }
    }

    /**
     * 92、反转字符串中的元音字母
     *
     * @param s
     * @return
     */
    public String reverseVowels(String s) {
        var cs = s.toCharArray();
        int l = 0, r = cs.length - 1;
        while (l < r) {
            if (isVowel(cs[l])) {
                while (l < r) {
                    try {
                        if (isVowel(cs[r])) {
                            cs[l] ^= cs[r];
                            cs[r] ^= cs[l];
                            cs[l] ^= cs[r];
                            break;
                        }
                    } finally {
                        r--;
                    }
                }
            }
            l++;
        }
        return new String(cs);
    }

    public boolean isVowel(char c) {
        return "aeiouAEIOU".indexOf(c) != -1;
    }

    /**
     * 93、反转字符串
     *
     * @param s
     */
    public void reverseString(char[] s) {
        int l = 0, r = s.length - 1;
        while (l < r) {
            s[l] ^= s[r];
            s[r] ^= s[l];
            s[l] ^= s[r];
            l++;
            r--;
        }
    }

    /**
     * 94、反转字符串II
     *
     * @param s
     * @param k
     * @return
     */
    public String reverseStr(String s, int k) {
        if (Objects.isNull(s) || k < 1) {
            return s;
        }
        var cn = s.toCharArray();
        var n = cn.length;
        for (var i = 0; i < n; i += k << 1) {
            int begin = i, end = begin + k - 1;
            if (end >= n) {
                end = --n;
            }
            swap(begin, end, cn);
        }
        return new String(cn);
    }

    public void swap(int i, int j, char[] cs) {
        while (i < j) {
            cs[i] ^= cs[j];
            cs[j] ^= cs[i];
            cs[i] ^= cs[j];
            i++;
            j--;
        }
    }

    /**
     * 95、Z字形变换
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        int r = 0, c = 0, n = s.length(), index = 0;
        var nums = new int[numRows][n];
        while (index < n) {
            while (index < n && r < numRows) {
                nums[r++][c] = s.charAt(index++);
            }
            r -= 2;
            c++;
            while (index < n && r > 0) {
                nums[r--][c++] = s.charAt(index++);
            }
        }
        var rlt = new StringBuilder();
        for (var n1 : nums) {
            for (var n2 : n1) {
                if (n2 == 0) {
                    continue;
                }
                rlt.append((char) (n2));
            }
        }
        return rlt.toString();
    }

    /**
     * 96、平衡二叉树
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if (Objects.isNull(root)) {
            return true;
        }
        return Math.abs(getTreeHeight(root.left) - getTreeHeight(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }

    public int getTreeHeight(TreeNode n) {
        if (Objects.isNull(n)) {
            return 0;
        }
        return 1 + Math.max(getTreeHeight(n.left), getTreeHeight(n.right));
    }


    /**
     * 97、解数独
     */
    static class Sudoku {
        int[][] r = new int[9][9];
        int[][] c = new int[9][9];
        int[][][] l = new int[3][3][9];
        boolean valid;

        public boolean check(int i, int j, int k) {
            return r[i][k] > 0 || c[j][k] > 0 || l[i / 3][j / 3][k] > 0;
        }
    }

    public void solveSudoku(char[][] board) {
        var s = new Sudoku();
        var free = new ArrayList<int[]>();
        int m = board.length, n = board[0].length;
        for (var i = 0; i < m; i++) {
            for (var j = 0; j < n; j++) {
                var temp = board[i][j];
                if (temp == '.') {
                    free.add(new int[]{i, j});
                    continue;
                }
                var index = temp - '0' - 1;
                s.r[i][index]++;
                s.c[j][index]++;
                s.l[i / 3][j / 3][index]++;
            }
        }
        dfs(board, free, s, 0);
    }

    public void dfs(char[][] board, List<int[]> free, Sudoku s, int index) {
        if (index >= free.size()) {
            s.valid = true;
            return;
        }
        var temp = free.get(index);
        int i = temp[0], j = temp[1];
        for (var k = 0; k < 9 && !s.valid; k++) {
            if (s.check(i, j, k)) {
                continue;
            }
            board[i][j] = (char) (k + '0' + 1);
            s.r[i][k]++;
            s.c[j][k]++;
            s.l[i / 3][j / 3][k]++;
            dfs(board, free, s, index + 1);
            s.r[i][k]--;
            s.c[j][k]--;
            s.l[i / 3][j / 3][k]--;
        }
    }

    /**
     * 98、K个一组翻转链表
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        var rlt = new ListNode(0, head);
        var prev = rlt;
        l1:
        while (Objects.nonNull(head)) {
            var last = prev;
            for (var i = 0; i < k; i++) {
                last = last.next;
                if (Objects.isNull(last)) {
                    break l1;
                }
            }
            var temp = reverseKGroup(head, last, k);
            prev.next = temp[0];
            prev = temp[1];
            head = head.next;
        }
        return rlt.next;
    }

    public ListNode[] reverseKGroup(ListNode head, ListNode last, int k) {
        ListNode l1 = last.next, l2 = head;
        while (k-- > 0) {
            var next = l2.next;
            l2.next = l1;
            l1 = l2;
            l2 = next;
        }
        return new ListNode[]{last, head};
    }


    /**
     * 99、赎金信
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        var nums = new int[128];
        for (var c : ransomNote.toCharArray()) {
            nums[c]++;
        }
        for (var c : magazine.toCharArray()) {
            nums[c]--;
        }
        for (var i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 100、重复的子字符串
     *
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern(String s) {
        if (Objects.isNull(s) || s.length() < 1) {
            return false;
        }
        var n = s.length();
        for (var i = 1; i < n; i++) {
            if (n % i != 0) {
                continue;
            }
            int begin = 0, end = i;
            while (end < n) {
                var t1 = s.substring(begin, end);
                begin = end;
                end += i;
                var t2 = s.substring(begin, end);
                if (!t1.equals(t2)) {
                    end = -1;
                    break;
                }
            }
            if (end >= n) {
                return true;
            }
        }
        return false;
    }

    /**
     * 101、唯一摩尔斯密码词
     *
     * @param words
     * @return
     */
    public int uniqueMorseRepresentations(String[] words) {
        var nums = new String[]{".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.",
                "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        var map = new ConcurrentHashMap<Character, String>() {{
            for (var i = 0; i < 26; i++) {
                put((char) (i + 'a'), nums[i]);
            }
        }};
        var set = new HashSet<String>();
        for (var word : words) {
            var path = new StringBuilder();
            for (var c : word.toCharArray()) {
                path.append(map.get(c));
            }
            set.add(path.toString());
        }
        return set.size();
    }

    /**
     * 102、能否连接形成数组
     *
     * @param arr
     * @param pieces
     * @return
     */
    public boolean canFormArray(int[] arr, int[][] pieces) {
        var map = new ConcurrentHashMap<Integer, int[]>() {{
            for (var p : pieces) {
                put(p[0], p);
            }
        }};
        for (var i = 0; i < arr.length; ) {
            if (!map.containsKey(arr[i])) {
                return false;
            }
            var temp = map.get(arr[i]);
            for (var j = 0; j < temp.length; i++, j++) {
                if (arr[i] != temp[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 103、全排列
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        var rlt = new ArrayList<List<Integer>>();
        dfs(rlt, nums, 0);
        return rlt;
    }

    public void dfs(List<List<Integer>> rlt, int[] nums, int index) {
        if (index >= nums.length) {
            var path = new ArrayList<Integer>();
            for (var n : nums) {
                path.add(n);
            }
            rlt.add(path);
            return;
        }
        for (var i = index; i < nums.length; i++) {
            swap(i, index, nums);
            dfs(rlt, nums, index + 1);
            swap(i, index, nums);
        }
    }

    public void swap(int i, int j, int[] nums) {
        if (i == j) {
            return;
        }
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }


    /**
     * 104、全排列II
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        var rlt = new ArrayList<List<Integer>>();
        if (Objects.isNull(nums) || nums.length < 1) {
            return rlt;
        }
        Arrays.sort(nums);
        dfs(nums, rlt, 0);
        return rlt;
    }

    public void dfs(int[] nums, List<List<Integer>> rlt, int index) {
        if (index >= nums.length) {
            var path = new ArrayList<Integer>();
            for (var n : nums) {
                path.add(n);
            }
            if (!rlt.contains(path)) {
                rlt.add(path);
            }
            return;
        }
        for (var i = index; i < nums.length; i++) {
            if (i > index && nums[i] == nums[i - 1]) {
                continue;
            }
            swap(i, nums, index);
            dfs(nums, rlt, index + 1);
            swap(i, nums, index);
        }
    }

    public void swap(int i, int[] nums, int j) {
        if (i == j) {
            return;
        }
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }


    /**
     * 105、旋转数字
     *
     * @param n
     * @return
     */
    public int rotatedDigits(int n) {
        var count = 0;
        for (var i = 0; i <= n; i++) {
            var t1 = i;
            var valid = false;
            while (t1 > 0) {
                var t2 = t1 % 10;
                if (t2 == 3 || t2 == 4 || t2 == 7) {
                    valid = false;
                    break;
                }
                if (t2 == 2 || t2 == 5 || t2 == 6 || t2 == 9) {
                    valid = true;
                }
                t1 /= 10;
            }
            if (t1 <= 0 && valid) {
                count++;
            }
        }
        return count;
    }

    /**
     * 106、对角线遍历
     *
     * @param mat
     * @return
     */
    public int[] findDiagonalOrder(int[][] mat) {
        if (Objects.isNull(mat) || mat.length < 1) {
            return new int[]{};
        }
        int m = mat.length, n = mat[0].length, index = 0;
        var rlt = new int[m * n];
        for (var i = 0; i < m + n - 1; i++) {
            if ((i & 1) == 0) {
                var r = i >= m ? m - 1 : i;
                var c = i >= m ? i - m + 1 : 0;
                while (r >= 0 && c < n) {
                    rlt[index++] = mat[r--][c++];
                }
                continue;
            }
            var c = i >= n ? n - 1 : i;
            var r = i >= n ? i - n + 1 : 0;
            while (r < m && c >= 0) {
                rlt[index++] = mat[r++][c--];
            }
        }
        return rlt;
    }

    /**
     * 107、反转链表
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode l1 = null, l2 = head;
        while (Objects.nonNull(l2)) {
            var next = l2.next;
            l2.next = l1;
            l1 = l2;
            l2 = next;
        }
        return l1;
    }

    /**
     * 108、数组中的第K个最大元素
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        sort(nums, 0, nums.length - 1);
        return nums[nums.length - k];
    }

    public void sort(int[] nums, int i, int j) {
        if (i < j) {
            var k = partition(nums, i, j);
            sort(nums, i, k);
            sort(nums, k + 1, j);
        }
    }

    public int partition(int[] nums, int i, int j) {
        var base = nums[i];
        while (i < j) {
            while (i < j && nums[j] >= base) {
                j--;
            }
            nums[i] = nums[j];
            while (i < j && nums[i] <= base) {
                i++;
            }
            nums[j] = nums[i];
        }
        nums[i] = base;
        return i;
    }

    /**
     * 109、二叉树的锯齿形层序遍历
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        var rlt = new ArrayList<List<Integer>>();
        if (Objects.isNull(root)) {
            return rlt;
        }
        var queue = new LinkedList<TreeNode>() {{
            offer(root);
        }};
        var i = 0;
        while (!queue.isEmpty()) {
            var n = queue.size();
            var path = new ArrayList<Integer>();
            for (var j = 0; j < n; j++) {
                var temp = queue.poll();
                path.add(temp.val);
                if (Objects.nonNull(temp.left)) {
                    queue.offer(temp.left);
                }
                if (Objects.nonNull(temp.right)) {
                    queue.offer(temp.right);
                }
            }
            if ((i++ & 1) == 1) {
                Collections.reverse(path);
            }
            rlt.add(path);
        }
        return rlt;
    }

    /**
     * 110、二叉树的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (Objects.isNull(root) || root == p || root == q) {
            return root;
        }
        var l = lowestCommonAncestor(root.left, p, q);
        var r = lowestCommonAncestor(root.right, p, q);
        if (Objects.isNull(l)) {
            return r;
        }
        if (Objects.isNull(r)) {
            return l;
        }
        return root;
    }

    /**
     * LRU缓存
     */
    class LRUCache {
        class Node {
            int key, value;
            Node prev, next;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        Node head, last;
        int size, capacity;
        Map<Integer, Node> map = new ConcurrentHashMap<>();

        public LRUCache(int capacity) {
            this.capacity = capacity;
        }

        public void moveToHead(Node n) {
            if (Objects.isNull(n.prev)) {
                return;
            }
            n.prev.next = n.next;
            if (Objects.nonNull(n.next)) {
                n.next.prev = n.prev;
            }
            if (last == n) {
                last.prev.next = null;
                last = last.prev;
            }
            n.next = head;
            head.prev = n;
            head = n;
            head.prev = null;
        }

        public int get(int key) {
            var n = map.get(key);
            if (Objects.isNull(n)) {
                return -1;
            }
            moveToHead(n);
            return n.value;
        }

        public void put(int key, int value) {
            var n = map.get(key);
            if (Objects.isNull(n)) {
                n = new Node(key, value);
                map.put(key, n);
                size++;
                if (Objects.isNull(head)) {
                    head = n;
                    last = head;
                } else {
                    n.next = head;
                    head.prev = n;
                    head = n;
                    head.prev = null;
                    if (size > capacity) {
                        map.remove(last.key);
                        last.prev.next = null;
                        last = last.prev;
                        size--;
                    }
                }
                return;
            }
            moveToHead(n);
            n.value = value;
        }
    }

    /**
     * 112、最大子数组和
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return 0;
        }
        int n = nums.length, max = nums[0];
        var dp = new int[n];
        dp[0] = nums[0];
        for (var i = 1; i < n; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            max = Math.max(dp[i], max);
        }
        return max;
    }

    /**
     * 113、二叉树的右视图
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        var rlt = new ArrayList<Integer>();
        dfs(root, rlt, 0);
        return rlt;
    }

    public void dfs(TreeNode root, List<Integer> rlt, int n) {
        if (Objects.isNull(root)) {
            return;
        }
        if (n == rlt.size()) {
            rlt.add(root.val);
        }
        dfs(root.right, rlt, n + 1);
        dfs(root.left, rlt, n + 1);
    }

    /**
     * 114、从前序与中序遍历序列构造二叉树
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (Objects.isNull(preorder) || preorder.length < 1 || Objects.isNull(inorder) || inorder.length < 1) {
            return null;
        }
        var n = preorder.length;
        var head = new TreeNode(preorder[0]);
        for (var i = 0; i < n; i++) {
            if (preorder[0] == inorder[i]) {
                var preorder_l = Arrays.copyOfRange(preorder, 1, i + 1);
                var preorder_r = Arrays.copyOfRange(preorder, i + 1, n);
                var inorder_l = Arrays.copyOfRange(inorder, 0, i);
                var inorder_r = Arrays.copyOfRange(inorder, i + 1, n);
                head.left = buildTree(preorder_l, inorder_l);
                head.right = buildTree(preorder_r, inorder_r);
                break;
            }
        }
        return head;
    }

    /**
     * 115、从中序与后序遍历序列构造二叉树
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        if (Objects.isNull(inorder) || Objects.isNull(postorder) || inorder.length < 1 || postorder.length < 1) {
            return null;
        }
        var n = postorder.length;
        var head = new TreeNode(postorder[n - 1]);
        for (var i = 0; i < n; i++) {
            if (inorder[i] == postorder[n - 1]) {
                var inorder_l = Arrays.copyOfRange(inorder, 0, i);
                var inorder_r = Arrays.copyOfRange(inorder, i + 1, n);
                var postorder_l = Arrays.copyOfRange(postorder, 0, i);
                var postorder_r = Arrays.copyOfRange(postorder, i, n - 1);
                head.left = buildTree2(inorder_l, postorder_l);
                head.right = buildTree2(inorder_r, postorder_r);
                break;
            }
        }
        return head;
    }

    /**
     * 116、岛屿数量
     *
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        int rlt = 0, m = grid.length, n = grid[0].length;
        for (var i = 0; i < m; i++) {
            for (var j = 0; j < n; j++) {
                rlt += dfs(grid, i, j);
            }
        }
        return rlt;
    }

    public int dfs(char[][] grid, int i, int j) {
        if ((i < 0 || i >= grid.length) || (j < 0 || j >= grid[0].length) || grid[i][j] == '0') {
            return 0;
        }
        grid[i][j] = '0';
        var t1 = new int[]{0, 0, 1, -1};
        var t2 = new int[]{1, -1, 0, 0};
        for (var k = 0; k < 4; k++) {
            dfs(grid, i + t1[k], j + t2[k]);
        }
        return 1;
    }


    /**
     * 117、环形链表II
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        var set = new HashSet<ListNode>();
        while (Objects.nonNull(head)) {
            if (!set.add(head)) {
                return head;
            }
            head = head.next;
        }
        return null;
    }

    /**
     * 118、合并区间
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        if (Objects.isNull(intervals) || intervals.length < 1) {
            return new int[][]{{}, {}};
        }
        Arrays.sort(intervals, (x, y) -> x[0] - y[0]);
        var n = intervals.length;
        var queue = new LinkedList<int[]>();
        for (var i = 0; i < n; i++) {
            int l = intervals[i][0], r = intervals[i][1];
            if (queue.isEmpty() || queue.getLast()[1] < l) {
                queue.add(new int[]{l, r});
                continue;
            }
            var temp = queue.getLast();
            temp[1] = Math.max(temp[1], r);
        }
        return queue.toArray(new int[queue.size()][2]);
    }

    /**
     * 119、最长递增子序列
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return 0;
        }
        int max = 1, n = nums.length;
        var dp = new int[n];
        Arrays.fill(dp, 1);
        for (var i = 1; i < n; i++) {
            for (var j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }

    /**
     * 120、重排链表
     *
     * @param head
     */
    public void reorderList(ListNode head) {
        if (Objects.isNull(head)) {
            return;
        }
        var list = new ArrayList<ListNode>();
        while (Objects.nonNull(head)) {
            list.add(head);
            head = head.next;
        }
        int l = 0, r = list.size() - 1;
        while (l < r) {
            list.get(l++).next = list.get(r);
            list.get(r--).next = list.get(l);
        }
        list.get(l).next = null;
    }

    /**
     * 121、二叉树的直径
     *
     * @param root
     * @return
     */
    int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter(root);
        return max;
    }

    public int maxDiameter(TreeNode n) {
        if (Objects.isNull(n)) {
            return 0;
        }
        var l = maxDiameter(n.left);
        var r = maxDiameter(n.right);
        max = Math.max(l + r, max);
        return 1 + Math.max(l, r);
    }

    /**
     * 122、翻转二叉树
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (Objects.isNull(root)) {
            return null;
        }
        var l = invertTree(root.left);
        var r = invertTree(root.right);
        root.left = r;
        root.right = l;
        return root;
    }

    /**
     * 123、验证二叉搜索树
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        var list = new ArrayList<Integer>();
        inOrder(root, list);
        int i = 0, j = 1, n = list.size();
        for (; j < n; i++, j++) {
            if (list.get(i) >= list.get(j)) {
                return false;
            }
        }
        return true;
    }

    public void inOrder(TreeNode n, List<Integer> list) {
        if (Objects.isNull(n)) {
            return;
        }
        inOrder(n.left, list);
        list.add(n.val);
        inOrder(n.right, list);
    }

    /**
     * 124、最长重复子数组
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int findLength(int[] nums1, int[] nums2) {
        int max = 0, m = nums1.length, n = nums2.length;
        var dp = new int[m + 1][n + 1];
        for (var i = 1; i <= m; i++) {
            for (var j = 1; j <= n; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }

    /**
     * 125、旋转图像
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        for (var i = 0; i < m >> 1; i++) {
            for (var j = 0; j < n; j++) {
                matrix[i][j] ^= matrix[m - i - 1][j];
                matrix[m - i - 1][j] ^= matrix[i][j];
                matrix[i][j] ^= matrix[m - i - 1][j];
            }
        }
        for (var i = 0; i < m; i++) {
            for (var j = 0; j < i; j++) {
                matrix[i][j] ^= matrix[j][i];
                matrix[j][i] ^= matrix[i][j];
                matrix[i][j] ^= matrix[j][i];
            }
        }
    }

    /**
     * 126、子集
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        var rlt = new ArrayList<List<Integer>>();
        dfs(rlt, new LinkedList<>(), nums, 0);
        return rlt;
    }

    public void dfs(List<List<Integer>> rlt, LinkedList<Integer> path, int[] nums, int index) {
        if (index >= nums.length) {
            rlt.add(new ArrayList<>(path));
            return;
        }
        path.add(nums[index]);
        dfs(rlt, path, nums, index + 1);
        path.removeLast();
        dfs(rlt, path, nums, index + 1);
    }


    /**
     * 127、用两个栈实现队列
     */
    class CQueue {
        LinkedList<Integer> s1 = new LinkedList<>();
        LinkedList<Integer> s2 = new LinkedList<>();
        int size;

        public void appendTail(int value) {
            s1.push(value);
            size++;
        }

        public int deleteHead() {
            if (size == 0) {
                return -1;
            }
            size--;
            if (!s2.isEmpty()) {
                return s2.pop();
            }
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
            return s2.pop();
        }
    }

    /**
     * 128、二叉搜索树中第K小的元素
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
        var list = new ArrayList<Integer>();
        inOrder(list, root);
        return list.get(--k);
    }

    public void inOrder(List<Integer> list, TreeNode root) {
        if (Objects.isNull(root)) {
            return;
        }
        inOrder(list, root.left);
        list.add(root.val);
        inOrder(list, root.right);
    }

    /**
     * 129、最长公共子序列
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        var dp = new int[m + 1][n + 1];
        for (var i = 1; i <= m; i++) {
            for (var j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    continue;
                }
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[m][n];
    }

    /**
     * 130、零钱兑换
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        var dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (var i = 1; i <= amount; i++) {
            for (var j = 0; j < coins.length; j++) {
                if (i >= coins[j]) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * 131、链表排序
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }
        var t1 = getCenterListNode(head);
        var t2 = head;
        while (Objects.nonNull(t2)) {
            if (t2.next == t1) {
                t2.next = null;
                break;
            }
            t2 = t2.next;
        }
        return mergeListNode(sortList(t1), sortList(head));
    }

    public ListNode getCenterListNode(ListNode head) {
        ListNode l1 = head, l2 = head.next;
        while (Objects.nonNull(l2) && Objects.nonNull(l2.next)) {
            l2 = l2.next.next;
            l1 = l1.next;
        }
        return l1.next;
    }

    public ListNode mergeListNode(ListNode l1, ListNode l2) {
        if (Objects.isNull(l1)) {
            return l2;
        }
        if (Objects.isNull(l2)) {
            return l1;
        }
        if (l1.val < l2.val) {
            l1.next = mergeListNode(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeListNode(l2.next, l1);
            return l2;
        }
    }

    /**
     * 132、打家劫舍
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return 0;
        }
        var n = nums.length;
        if (n < 2) {
            return nums[0];
        }
        var dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (var i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[n - 1];
    }

    /**
     * 133、排序数组
     *
     * @param nums
     * @return
     */
    public int[] sortArray(int[] nums) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return new int[]{};
        }
        quick(nums, 0, nums.length - 1);
        return nums;
    }

    public void quick(int[] nums, int l, int r) {
        if (l < r) {
            var k = partition(l, r, nums);
            quick(nums, l, k);
            quick(nums, k + 1, r);
        }
    }

    public int partition(int l, int r, int[] nums) {
        var base = nums[l];
        while (l < r) {
            while (l < r && nums[r] >= base) {
                r--;
            }
            nums[l] = nums[r];
            while (l < r && nums[l] <= base) {
                l++;
            }
            nums[r] = nums[l];
        }
        nums[l] = base;
        return l;
    }

    /**
     * 134、翻转数位
     *
     * @param num
     * @return
     */
    public int reverseBits(int num) {
        int max = 0, prev = 0, count = 0;
        for (var i = 31; i >= 0; i--) {
            if (((num >> i) & 1) == 1) {
                prev++;
                count++;
            } else {
                count = ++prev;
                prev = 0;
            }
            max = Math.max(max, count);
        }
        return max;
    }

    /**
     * 135、单词搜索
     *
     * @param board
     * @param word
     * @return
     */
    boolean valid = false;

    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        for (var i = 0; i < m; i++) {
            for (var j = 0; j < n; j++) {
                dfs(board, word, 0, i, j);
            }
        }
        return valid;
    }

    public void dfs(char[][] board, String word, int index, int i, int j) {
        if ((i < 0 || i >= board.length) || (j < 0 || j >= board[0].length) || board[i][j] != word.charAt(index)) {
            return;
        }
        if (index == word.length() - 1) {
            valid = true;
            return;
        }
        var temp = board[i][j];
        board[i][j] = '.';
        var t1 = new int[]{0, 0, 1, -1};
        var t2 = new int[]{1, -1, 0, 0};
        for (var k = 0; k < 4 && !valid; k++) {
            dfs(board, word, index + 1, i + t1[k], j + t2[k]);
        }
        board[i][j] = temp;
    }

    /**
     * 136、二叉树的所有路径
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        var rlt = new ArrayList<String>();
        dfs(root, null, "", rlt);
        return rlt;
    }

    public void dfs(TreeNode n1, TreeNode n2, String path, List<String> rlt) {
        if (Objects.nonNull(n1)) {
            var temp = String.format("%s%s->", path, n1.val);
            dfs(n1.left, n1, temp, rlt);
            dfs(n1.right, n1, temp, rlt);
        } else {
            if (Objects.isNull(n2.left) && Objects.isNull(n2.right)) {
                path = path.substring(0, path.lastIndexOf("->"));
                if (!rlt.contains(path)) {
                    rlt.add(path);
                }
            }
        }
    }

    /**
     * 137、字符串的排列
     *
     * @param s
     * @return
     */
    public String[] permutation2(String s) {
        if (Objects.isNull(s) || s.length() < 1) {
            return new String[]{};
        }
        var rlt = new HashSet<String>();
        dfs(0, s.toCharArray(), rlt);
        return rlt.toArray(new String[rlt.size()]);
    }

    public void dfs(int index, char[] cs, Set<String> rlt) {
        if (index >= cs.length) {
            var path = new StringBuilder();
            for (var c : cs) {
                path.append(c);
            }
            rlt.add(path.toString());
            return;
        }
        for (var i = index; i < cs.length; i++) {
            if (i > index && cs[i] == cs[i - 1]) {
                continue;
            }
            swap(i, cs, index);
            dfs(index + 1, cs, rlt);
            swap(i, cs, index);
        }
    }

    public void swap(int i, char[] cs, int j) {
        if (i == j) {
            return;
        }
        cs[i] ^= cs[j];
        cs[j] ^= cs[i];
        cs[i] ^= cs[j];
    }

    /**
     * 138、二叉搜索树中的插入操作
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        var n = new TreeNode(val);
        if (Objects.isNull(root)) {
            root = n;
        } else {
            insert(root, null, n);
        }
        return root;
    }

    public void insert(TreeNode n1, TreeNode n2, TreeNode n3) {
        if (Objects.isNull(n1)) {
            if (n3.val < n2.val) {
                n2.left = n3;
            } else {
                n2.right = n3;
            }
        } else {
            insert(n3.val < n1.val ? n1.left : n1.right, n1, n3);
        }
    }


    /**
     * 139、恢复二叉搜索树
     *
     * @param root
     */
    TreeNode t1 = null, t2 = null, prev = new TreeNode(Integer.MIN_VALUE);

    public void recoverTree(TreeNode root) {
        inOrder(root);
        t1.val ^= t2.val;
        t2.val ^= t1.val;
        t1.val ^= t2.val;
    }

    public void inOrder(TreeNode root) {
        if (Objects.isNull(root)) {
            return;
        }
        inOrder(root.left);
        if (prev.val > root.val) {
            if (Objects.isNull(t1)) {
                t1 = prev;
            }
            t2 = root;
        }
        prev = root;
        inOrder(root.right);
    }

    /**
     * 140、连续的子数组和
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return false;
        }
        var map = new ConcurrentHashMap<Integer, Integer>() {{
            put(0, -1);
        }};
        int n = nums.length, prev = 0;
        for (var i = 0; i < n; i++) {
            prev = ((prev % k) + (nums[i] % k)) % k;
            if (map.containsKey(prev)) {
                if (i - map.get(prev) >= 2) {
                    return true;
                }
                continue;
            }
            map.put(prev, i);
        }
        return false;
    }

    /**
     * 141、寻找重复数
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    /**
     * 142、扑克牌中的顺子
     *
     * @param nums
     * @return
     */
    public boolean isStraight(int[] nums) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return false;
        }
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        var set = new HashSet<Integer>();
        for (var n : nums) {
            if (n == 0) {
                continue;
            }
            min = Math.min(min, n);
            max = Math.max(max, n);
            if (!set.add(n)) {
                return false;
            }
        }
        return max - min < 5;
    }

    /**
     * 143、最大交换
     *
     * @param num
     * @return
     */
    public int maximumSwap(int num) {
        var cs = String.valueOf(num).toCharArray();
        int n = cs.length, max = n - 1, i = -1, j = -1;
        for (var k = n - 1; k >= 0; k--) {
            if (cs[max] < cs[k]) {
                max = k;
            }
            if (cs[k] < cs[max]) {
                i = k;
                j = max;
            }
        }
        if (i >= 0 && j >= 0) {
            cs[i] ^= cs[j];
            cs[j] ^= cs[i];
            cs[i] ^= cs[j];
        }
        return Integer.parseInt(new String(cs));
    }

    /**
     * 144、奇偶链表
     *
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        if (Objects.isNull(head)) {
            return null;
        }
        ListNode odd = head, even = head.next, even_head = even;
        while (Objects.nonNull(even) && Objects.nonNull(even.next)) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = even_head;
        return head;
    }

    /**
     * 145、移掉K位数字
     *
     * @param num
     * @param k
     * @return
     */
    public String removeKdigits(String num, int k) {
        var stack = new LinkedList<Character>();
        for (var n : num.toCharArray()) {
            while (!stack.isEmpty() && k > 0 && stack.peekLast() > n) {
                stack.pollLast();
                k--;
            }
            stack.offerLast(n);
        }
        for (var i = 0; i < k; i++) {
            stack.pollLast();
        }
        var valid = true;
        var rlt = new StringBuilder();
        while (!stack.isEmpty()) {
            var temp = stack.pollFirst();
            if (valid && temp == '0') {
                continue;
            }
            valid = false;
            rlt.append(temp);
        }
        return rlt.length() < 1 ? "0" : rlt.toString();
    }

    /**
     * 146、分发糖果
     *
     * @param ratings
     * @return
     */
    public int candy(int[] ratings) {
        int max = 0, n = ratings.length, count = 0;
        var nums = new int[n];
        Arrays.fill(nums, 1);
        for (var i = 0; i < n; i++) {
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                nums[i] = nums[i - 1] + 1;
            }
        }
        for (var i = n - 1; i >= 0; i--) {
            if (i < n - 1 && ratings[i] > ratings[i + 1]) {
                count++;
            } else {
                count = 1;
            }
            nums[i] = Math.max(nums[i], count);
            max += nums[i];
        }
        return max;
    }

    /**
     * 147、矩阵中的最长递增路径
     *
     * @param matrix
     * @return
     */
    public int longestIncreasingPath2(int[][] matrix) {
        if (Objects.isNull(matrix) || matrix.length < 1) {
            return 0;
        }
        int m = matrix.length, n = matrix[0].length, rlt = 0;
        var path = new int[m][n];
        for (var i = 0; i < m; i++) {
            for (var j = 0; j < n; j++) {
                rlt = Math.max(rlt, dfs(matrix, i, j, path));
            }
        }
        return rlt;
    }

    public int dfs(int[][] matrix, int i, int j, int[][] path) {
        if (path[i][j] > 0) {
            return path[i][j];
        }
        path[i][j] = 1;
        var t1 = new int[]{0, 0, 1, -1};
        var t2 = new int[]{1, -1, 0, 0};
        for (var k = 0; k < 4; k++) {
            if ((i + t1[k] >= 0 && i + t1[k] < matrix.length) &&
                    (j + t2[k] >= 0 && j + t2[k] < matrix[0].length) &&
                    matrix[i + t1[k]][j + t2[k]] > matrix[i][j]) {
                path[i][j] = Math.max(path[i][j], dfs(matrix, i + t1[k], j + t2[k], path) + 1);
            }
        }
        return path[i][j];
    }

    /**
     * 148、分隔链表
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        var small = new ListNode(0, head);
        var small_head = small;
        var large = new ListNode(0, head);
        var large_head = large;
        while (Objects.nonNull(head)) {
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        large.next = null;
        small.next = large_head.next;
        return small_head.next;
    }

    /**
     * 149、单词拆分
     *
     * @param s
     * @param wordDict
     * @return
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        var n = s.length();
        var dp = new int[n + 1];
        dp[0] = 1;
        for (var i = 1; i <= n; i++) {
            for (var j = 0; j < i; j++) {
                if (dp[j] == 1 && wordDict.contains(s.substring(j, i))) {
                    dp[i] = 1;
                }
            }
        }
        return dp[n] == 1;
    }

    /**
     * 150、下一个更大元素II
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        var rlt = new int[n];
        Arrays.fill(rlt, -1);
        var s = new LinkedList<Integer>();
        for (var i = 0; i < n << 1; i++) {
            while (!s.isEmpty() && nums[i % n] > nums[s.peek()]) {
                rlt[s.pop()] = nums[i % n];
            }
            s.push(i % n);
        }
        return rlt;
    }

    /**
     * 158、下一个更大元素I
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElements2(int[] nums) {
        var n = nums.length;
        var stack = new LinkedList<Integer>();
        var map = new ConcurrentHashMap<Integer, Integer>() {{
            for (var i = (n - 1) << 1; i >= 0; i--) {
                var c = nums[i % n];
                while (!stack.isEmpty() && c >= stack.peek()) {
                    stack.pop();
                }
                put(i % n, stack.isEmpty() ? -1 : stack.peek());
                stack.push(c);
            }
        }};
        var rlt = new int[n];
        for (var i = 0; i < n; i++) {
            rlt[i] = map.get(i);
        }
        return rlt;
    }

    /**
     * 151、两个数组的交集
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        var s1 = new HashSet<Integer>();
        for (var n : nums1) {
            s1.add(n);
        }
        var s2 = new HashSet<Integer>();
        for (var n : nums2) {
            if (s1.contains(n)) {
                s2.add(n);
            }
        }
        var index = 0;
        var rlt = new int[s2.size()];
        for (var n : s2) {
            rlt[index++] = n;
        }
        return rlt;
    }

    /**
     * 152、柱状图中最大的矩形
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length, max = 0;
        var stack = new LinkedList<Integer>();
        var l = new int[n];
        var r = new int[n];
        Arrays.fill(r, n);
        for (var i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                r[stack.peek()] = i;
                stack.pop();
            }
            l[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        for (var i = 0; i < n; i++) {
            max = Math.max(max, (r[i] - l[i] - 1) * heights[i]);
        }
        return max;
    }

    /**
     * 153、数组中重复的数字
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber(int[] nums) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return 0;
        }
        Arrays.sort(nums);
        var n = nums.length;
        for (var i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                return nums[i];
            }
        }
        return 0;
    }

    /**
     * 154、搜索二维矩阵
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length, l = 0, r = m * n - 1;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            var num = matrix[mid / n][mid % n];
            if (num == target) {
                return true;
            }
            if (num < target) {
                l = ++mid;
            } else {
                r = --mid;
            }
        }
        return false;
    }

    /**
     * 155、左叶子之和
     *
     * @param root
     * @return
     */
    public int sumOfLeftLeaves(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        return dfs(root);
    }

    public int dfs(TreeNode n) {
        var rlt = 0;
        if (Objects.nonNull(n.left)) {
            rlt += isLeafNode(n.left) ? n.left.val : dfs(n.left);
        }
        if (Objects.nonNull(n.right) && !isLeafNode(n.right)) {
            rlt += dfs(n.right);
        }
        return rlt;
    }

    public boolean isLeafNode(TreeNode n) {
        return Objects.isNull(n.left) && Objects.isNull(n.right);
    }

    /**
     * 156、最小栈
     */
    class MinStack {
        LinkedList<Integer> s1 = new LinkedList<>();
        LinkedList<Integer> s2 = new LinkedList<>();

        public MinStack() {
            s2.push(Integer.MAX_VALUE);
        }

        public void push(int val) {
            s1.push(val);
            s2.push(Math.min(s2.peek(), val));
        }

        public void pop() {
            s1.pop();
            s2.pop();
        }

        public int top() {
            return s1.peek();
        }

        public int getMin() {
            return s2.peek();
        }
    }

    /**
     * 159、在排序数组中查找元素的第一个和最后一个位置
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if (nums[mid] == target) {
                int first = mid, last = mid;
                while (first >= 0 && nums[first] == nums[mid]) {
                    first--;
                }
                while (last <= r && nums[last] == nums[mid]) {
                    last++;
                }
                return new int[]{++first, --last};
            }
            if (nums[mid] < target) {
                l = ++mid;
            } else {
                r = --mid;
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 160、字母异位词分组
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        var map = new ConcurrentHashMap<String, List<String>>();
        for (var s : strs) {
            var cs = s.toCharArray();
            Arrays.sort(cs);
            var key = new String(cs);
            var list = map.getOrDefault(key, new ArrayList<>());
            list.add(new String(s.toCharArray()));
            if (!map.containsKey(key)) {
                map.put(key, list);
            }
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 161、反转链表II
     *
     * @param head
     * @param left
     * @param right
     * @return
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode temp = new ListNode(0, head), rlt = temp, prev = null;
        int index = 1, count = 0;
        while (Objects.nonNull(temp)) {
            if (count > 0) {
                count++;
            }
            if (index == left) {
                count = 1;
                prev = temp;
            }
            if (index == right) {
                temp = temp.next;
                break;
            }
            index++;
            temp = temp.next;
        }
        prev.next = reverseBetween(prev.next, temp, count);
        return rlt.next;
    }

    public ListNode reverseBetween(ListNode head, ListNode last, int k) {
        ListNode l1 = last.next, l2 = head;
        while (k-- > 0) {
            var next = l2.next;
            l2.next = l1;
            l1 = l2;
            l2 = next;
        }
        return last;
    }


    /**
     * 162、插入区间
     *
     * @param intervals
     * @param newInterval
     * @return
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (Objects.isNull(intervals) || intervals.length < 1) {
            return new int[][]{newInterval};
        }
        intervals = merge(intervals, newInterval);
        Arrays.sort(intervals, (x, y) -> x[0] - y[0]);
        var list = new LinkedList<int[]>();
        var n = intervals.length;
        for (var i = 0; i < n; i++) {
            int l = intervals[i][0], r = intervals[i][1];
            while (list.isEmpty() || list.getLast()[1] < l) {
                list.add(new int[]{l, r});
                continue;
            }
            var temp = list.getLast();
            temp[1] = Math.max(temp[1], r);
        }
        return list.toArray(new int[list.size()][2]);
    }

    public int[][] merge(int[][] intervals, int[] newInterval) {
        int m = intervals.length, n = intervals[0].length;
        var rlt = new int[m + 1][n];
        for (var i = 0; i < m; i++) {
            rlt[i] = intervals[i];
        }
        rlt[m] = newInterval;
        return rlt;
    }

    /**
     * 163、字符串中的最大奇数
     *
     * @param num
     * @return
     */
    public String largestOddNumber(String num) {
        var n = num.length();
        for (var i = n - 1; i >= 0; i--) {
            if ((num.charAt(i) & 1) == 1) {
                return num.substring(0, ++i);
            }
        }
        return "";
    }

    /**
     * 矩阵中的最长递增路径
     *
     * @param matrix
     * @return
     */
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length, rlt = 0;
        for (var i = 0; i < m; i++) {
            for (var j = 0; j < n; j++) {
                rlt = Math.max(rlt, dfs(matrix, new int[m][n], i, j));
            }
        }
        return rlt;
    }

    public int dfs(int[][] matrix, int[][] path, int i, int j) {
        if (path[i][j] != 0) {
            return path[i][j];
        }
        path[i][j]++;
        var t1 = new int[]{0, 0, 1, -1};
        var t2 = new int[]{1, -1, 0, 0};
        for (var k = 0; k < 4; k++) {
            if ((i + t1[k] < 0 || i + t1[k] >= matrix.length) || (j + t2[k] < 0 || j + t2[k] >= matrix[0].length) ||
                    matrix[i + t1[k]][j + t2[k]] <= matrix[i][j]) {
                continue;
            }
            path[i][j] = Math.max(path[i][j], dfs(matrix, path, i + t1[k], j + t2[k]) + 1);
        }
        return path[i][j];
    }

    /**
     * 157、根据前序和后序遍历构造二叉树
     *
     * @param preorder
     * @param postorder
     * @return
     */
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        if (Objects.isNull(preorder) || Objects.isNull(postorder) || preorder.length < 1 || postorder.length < 1) {
            return null;
        }
        if (preorder.length == 1) {
            return new TreeNode(preorder[0]);
        }
        var n = preorder.length;
        var head = new TreeNode(preorder[0]);
        for (var i = 0; i < n; i++) {
            if (preorder[1] == postorder[i]) {
                var i_ = i + 1;
                var preorder_l = Arrays.copyOfRange(preorder, 1, i_ + 1);
                var preorder_r = Arrays.copyOfRange(preorder, i_ + 1, n);
                var postorder_l = Arrays.copyOfRange(postorder, 0, i_);
                var postorder_r = Arrays.copyOfRange(postorder, i_, n - 1);
                head.left = constructFromPrePost(preorder_l, postorder_l);
                head.right = constructFromPrePost(preorder_r, postorder_r);
                break;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        var solution = new Solution();
        // 找出数组中第一个回文字符串
        Assert.assertEquals("ada", solution.firstPalindrome(new String[]{"abc", "car", "ada", "racecar", "cool"}));
        Assert.assertEquals("racecar", solution.firstPalindrome(new String[]{"notapalindrome", "racecar"}));
        Assert.assertEquals("", solution.firstPalindrome(new String[]{"def", "ghi"}));
        // 玩筹码
        Assert.assertEquals(1, solution.minCostToMoveChips(new int[]{1, 2, 3}));
        Assert.assertEquals(2, solution.minCostToMoveChips(new int[]{2, 2, 2, 3, 3}));
        Assert.assertEquals(1, solution.minCostToMoveChips(new int[]{1, 1000000000}));
        // 两数之和
        Arrays.stream(solution.twoSum(new int[]{2, 7, 11, 15}, 9)).forEach(System.out::print);
        System.out.println();
        Arrays.stream(solution.twoSum(new int[]{3, 2, 4}, 6)).forEach(System.out::print);
        System.out.println();
        Arrays.stream(solution.twoSum(new int[]{3, 3}, 6)).forEach(System.out::print);
        System.out.println();
        // 插入搜索位置
        Assert.assertEquals(2, solution.searchInsert(new int[]{1, 3, 5, 6}, 5));
        Assert.assertEquals(1, solution.searchInsert(new int[]{1, 3, 5, 6}, 2));
        Assert.assertEquals(4, solution.searchInsert(new int[]{1, 3, 5, 6}, 7));
        // 三数之和
        System.out.println("三数之和:");
        solution.threeSum(new int[]{-1, 0, 1, 2, -1, -4}).forEach(x -> {
            for (var n : x) {
                System.out.print(n + " ");
            }
            System.out.println();
        });
        solution.threeSum(new int[]{}).forEach(x -> {
            for (var n : x) {
                System.out.print(n + " ");
            }
            System.out.println();
        });
        solution.threeSum(new int[]{0}).forEach(x -> {
            for (var n : x) {
                System.out.print(n + " ");
            }
            System.out.println();
        });
        solution.threeSum(new int[]{0, 0, 0, 0}).forEach(x -> {
            for (var n : x) {
                System.out.print(n + " ");
            }
            System.out.println();
        });
        // 两数相加
        var n = solution.addTwoNumbers(new ListNode(2, new ListNode(4, new ListNode(3))),
                new ListNode(5, new ListNode(6, new ListNode(4))));
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 无重复的最长子串
        Assert.assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
        Assert.assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"));
        Assert.assertEquals(3, solution.lengthOfLongestSubstring("pwwkew"));
        Assert.assertEquals(1, solution.lengthOfLongestSubstring(" "));
        // 最长回文子串
        Assert.assertEquals("bab", solution.longestPalindrome("babad"));
        Assert.assertEquals("bb", solution.longestPalindrome("cbbd"));
        // 回文数
        Assert.assertEquals(true, solution.isPalindrome(121));
        Assert.assertEquals(false, solution.isPalindrome(-121));
        Assert.assertEquals(false, solution.isPalindrome(10));
        // 删除链表的倒数第 N 个结点
        System.out.println("删除链表的倒数第 N 个结点:");
        n = solution.removeNthFromEnd(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2);
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        n = solution.removeNthFromEnd(new ListNode(1), 1);
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 有效的括号
        Assert.assertEquals(false, solution.isValid("("));
        Assert.assertEquals(false, solution.isValid("(("));
        Assert.assertEquals(true, solution.isValid("()"));
        Assert.assertEquals(true, solution.isValid("()[]{}"));
        Assert.assertEquals(false, solution.isValid("(]"));
        // 合并两个有序链表
        n = solution.mergeTwoLists(new ListNode(1, new ListNode(2, new ListNode(4))),
                new ListNode(1, new ListNode(3, new ListNode(4))));
        while (Objects.isNull(n)) {
            System.out.print(n.val + " ");
        }
        System.out.println();
        // 移除元素
        Assert.assertEquals(2, solution.removeElement(new int[]{3, 2, 2, 3}, 3));
        Assert.assertEquals(5, solution.removeElement(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, 2));
        // 不动点
        Assert.assertEquals(3, solution.fixedPoint(new int[]{-10, -5, 0, 3, 7}));
        Assert.assertEquals(0, solution.fixedPoint(new int[]{0, 2, 5, 8, 17}));
        Assert.assertEquals(-1, solution.fixedPoint(new int[]{-10, -5, 3, 4, 7, 9}));
        // 划分数组使最大差为 K
        Assert.assertEquals(2, solution.partitionArray(new int[]{3, 6, 1, 2, 5}, 2));
        Assert.assertEquals(2, solution.partitionArray(new int[]{1, 2, 3}, 1));
        Assert.assertEquals(3, solution.partitionArray(new int[]{2, 2, 4, 5}, 0));
        // 岛屿的最大面积
        Assert.assertEquals(5, solution.maxAreaOfIsland(new int[][]{
                {0, 0, 0, 1, 0, 0},
                {1, 1, 1, 1, 0, 0}}));
        Assert.assertEquals(0, solution.maxAreaOfIsland(new int[][]{
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}}));
        // 统计一致字符串的数目
        Assert.assertEquals(2, solution.countConsistentStrings("ab", new String[]{"ad", "bd", "aaab", "baa", "badab"}));
        Assert.assertEquals(7, solution.countConsistentStrings("abc", new String[]{"a", "b", "c", "ab", "ac", "bc", "abc"}));
        Assert.assertEquals(4, solution.countConsistentStrings("cad", new String[]{"cc", "acd", "b", "ba", "bac", "bad", "ac", "d"}));
        // 查询无效交易
        System.out.println("查询无效交易:");
        var list = solution.invalidTransactions(new String[]{"alice,20,800,mtv", "alice,50,100,beijing"});
        System.out.println(list);
        list = solution.invalidTransactions(new String[]{"alice,20,800,mtv", "alice,50,1200,mtv"});
        System.out.println(list);
        list = solution.invalidTransactions(new String[]{"alice,20,800,mtv", "bob,50,1200,mtv"});
        System.out.println(list);
        // 删掉一个元素以后全为 1 的最长子数组
        Assert.assertEquals(3, solution.longestSubarray(new int[]{1, 1, 0, 1}));
        Assert.assertEquals(5, solution.longestSubarray(new int[]{0, 1, 1, 1, 0, 1, 1, 0, 1}));
        Assert.assertEquals(2, solution.longestSubarray(new int[]{1, 1, 1}));
        // 盛最多水的容器
        Assert.assertEquals(49, solution.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        Assert.assertEquals(1, solution.maxArea(new int[]{1, 1}));
        // 检查二进制字符串字段
        Assert.assertEquals(true, solution.checkOnesSegment("10"));
        Assert.assertEquals(true, solution.checkOnesSegment("110"));
        Assert.assertEquals(false, solution.checkOnesSegment("1101"));
        // 二分查找
        Assert.assertEquals(4, solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
        Assert.assertEquals(-1, solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));
        // 爬楼梯
        Assert.assertEquals(2, solution.climbStairs(2));
        Assert.assertEquals(3, solution.climbStairs(3));
        // 最接近的三数之和
        Assert.assertEquals(2, solution.threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
        Assert.assertEquals(0, solution.threeSumClosest(new int[]{0, 0, 0}, 1));
        Assert.assertEquals(2, solution.threeSumClosest(new int[]{1, 1, 1, 0}, -100));
        // 跳跃游戏
        Assert.assertEquals(true, solution.canJump(new int[]{2, 3, 1, 1, 4}));
        Assert.assertEquals(false, solution.canJump(new int[]{3, 2, 1, 0, 4}));
        // 回文链表
        Assert.assertEquals(false, solution.isPalindrome(new ListNode(1, new ListNode(2))));
        Assert.assertEquals(true, solution.isPalindrome(new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(1))))));
        Assert.assertEquals(true, solution.isPalindrome(new ListNode(-121, new ListNode(-121))));
        // 跳跃游戏2
        Assert.assertEquals(2, solution.jump(new int[]{2, 3, 1, 1, 4}));
        Assert.assertEquals(2, solution.jump(new int[]{2, 3, 0, 1, 4}));
        Assert.assertEquals(0, solution.jump(new int[]{0}));
        // 组合总和
        System.out.println("组合总和：");
        System.out.println(solution.combinationSum(new int[]{2, 3, 6, 7}, 7));
        System.out.println(solution.combinationSum(new int[]{2, 3, 5}, 8));
        System.out.println(solution.combinationSum(new int[]{2}, 1));
        // 电话号码的字母组合
        System.out.println("电话号码的字母组合：");
        System.out.println(solution.letterCombinations("23"));
        System.out.println(solution.letterCombinations(""));
        System.out.println(solution.letterCombinations("2"));
        System.out.println(solution.letterCombinations(""));
        // 组合总和2
        System.out.println("组合总和2：");
        System.out.println(solution.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8));
        System.out.println(solution.combinationSum2(new int[]{2, 5, 2, 1, 2}, 5));
        // 字符串相加
        Assert.assertEquals("134", solution.addStrings("11", "123"));
        Assert.assertEquals("533", solution.addStrings("456", "77"));
        Assert.assertEquals("0", solution.addStrings("0", "0"));
        // 字符串相乘
        Assert.assertEquals("6", solution.multiply("2", "3"));
        Assert.assertEquals("56088", solution.multiply("123", "456"));
        // 下一个排列
        System.out.println("下一个排列：");
        var nums = new int[]{1, 2, 3};
        solution.nextPermutation(nums);
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        nums = new int[]{3, 2, 1};
        solution.nextPermutation(nums);
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        // 搜索旋转排序数组
        Assert.assertEquals(4, solution.search2(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
        Assert.assertEquals(-1, solution.search2(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
        Assert.assertEquals(-1, solution.search2(new int[]{1}, 0));
        // 四数之和
        System.out.println(solution.fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
        System.out.println(solution.fourSum(new int[]{2, 2, 2, 2, 2}, 8));
        // 括号生成
        System.out.println("括号生成：");
        System.out.println(solution.generateParenthesis(3));
        System.out.println(solution.generateParenthesis(1));
        // 两两交换链表中的节点
        System.out.println("两两交换链表中的节点：");
        n = solution.swapPairs(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4)))));
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        n = solution.swapPairs(null);
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        n = solution.swapPairs(new ListNode(1));
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 接雨水
        Assert.assertEquals(6, solution.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        Assert.assertEquals(9, solution.trap(new int[]{4, 2, 0, 3, 2, 5}));
        // 找出字符串中第一个匹配项的下标
        Assert.assertEquals(2, solution.strStr("hello", "ll"));
        Assert.assertEquals(-1, solution.strStr("aaaaa", "bba"));
        // 最长公共前缀
        Assert.assertEquals("a", solution.longestCommonPrefix(new String[]{"ab", "a"}));
        Assert.assertEquals("fl", solution.longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        Assert.assertEquals("", solution.longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        // 两数相除
        Assert.assertEquals(3, solution.divide(10, 3));
        Assert.assertEquals(-2, solution.divide(7, -3));
        // 删除有序数组中的重复项
        Assert.assertEquals(2, solution.removeDuplicates(new int[]{1, 1, 2}));
        Assert.assertEquals(5, solution.removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}));
        // 有效的数独
        var board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},

                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},

                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        Assert.assertEquals(true, solution.isValidSudoku(board));
        board = new char[][]{
                {'5', '3', '.', '.', '5', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},

                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},

                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        Assert.assertEquals(false, solution.isValidSudoku(board));
        // 二叉树的中序遍历
        System.out.println(solution.inorderTraversal(new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null))));
        System.out.println(solution.inorderTraversal(null));
        System.out.println(solution.inorderTraversal(new TreeNode(1)));
        // 合并两个有序数组
        System.out.println("合并两个有序数组:");
        nums = new int[]{1, 2, 3, 0, 0, 0};
        solution.merge(nums, 3, new int[]{2, 5, 6}, 3);
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        nums = new int[]{1};
        solution.merge(nums, 1, new int[]{}, 0);
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        // 二叉树的前序遍历
        System.out.println("二叉树的前序遍历:");
        System.out.println(solution.preorderTraversal(new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null))));
        System.out.println(solution.preorderTraversal(null));
        System.out.println(solution.preorderTraversal(new TreeNode(1)));
        // 二叉树的后序遍历
        System.out.println("二叉树的后序遍历:");
        System.out.println(solution.postorderTraversal(new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null))));
        System.out.println(solution.postorderTraversal(null));
        System.out.println(solution.postorderTraversal(new TreeNode(1)));
        // x的平方根
        Assert.assertEquals(2, solution.mySqrt(4));
        Assert.assertEquals(2, solution.mySqrt(8));
        // 位1的个数
        Assert.assertEquals(3, solution.hammingWeight(0b00000000000000000000000000001011));
        Assert.assertEquals(1, solution.hammingWeight(0b00000000000000000000000010000000));
        Assert.assertEquals(31, solution.hammingWeight(0b11111111111111111111111111111101));
        // 多数元素
        Assert.assertEquals(3, solution.majorityElement(new int[]{3, 2, 3}));
        Assert.assertEquals(2, solution.majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2}));
        // 合并K个升序链表
        System.out.println("合并K个升序链表:");
        n = solution.mergeKLists(new ListNode[]{
                new ListNode(1, new ListNode(4, new ListNode(5))),
                new ListNode(1, new ListNode(3, new ListNode(4))),
                new ListNode(2, new ListNode(6))});
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 最长有效括号
        Assert.assertEquals(2, solution.longestValidParentheses("(()"));
        Assert.assertEquals(4, solution.longestValidParentheses(")()())"));
        Assert.assertEquals(0, solution.longestValidParentheses(""));
        // Excel表列名称
        Assert.assertEquals("A", solution.convertToTitle(1));
        Assert.assertEquals("AB", solution.convertToTitle(28));
        // 只出现一次的数字
        Assert.assertEquals(1, solution.singleNumber(new int[]{2, 2, 1}));
        Assert.assertEquals(4, solution.singleNumber(new int[]{4, 1, 2, 1, 2}));
        // 买卖股票的最佳时机
        Assert.assertEquals(5, solution.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        Assert.assertEquals(0, solution.maxProfit(new int[]{7, 6, 4, 3, 1}));
        // 删除排序链表中的重复元素
        System.out.println("删除排序链表中的重复元素:");
        n = solution.deleteDuplicates(new ListNode(1, new ListNode(1, new ListNode(2))));
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 二进制求和
        Assert.assertEquals("100", solution.addBinary("11", "1"));
        Assert.assertEquals("10101", solution.addBinary("1010", "1011"));
        // 加一
        System.out.println("加一:");
        nums = solution.plusOne(new int[]{9});
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        nums = solution.plusOne(new int[]{1, 2, 3});
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        nums = solution.plusOne(new int[]{4, 3, 2, 1});
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        nums = solution.plusOne(new int[]{0});
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        // 最后一个单词的长度
        Assert.assertEquals(5, solution.lengthOfLastWord("Hello World"));
        Assert.assertEquals(4, solution.lengthOfLastWord("   fly me   to   the moon  "));
        Assert.assertEquals(6, solution.lengthOfLastWord("luffy is still joyboy"));
        // 相同的树
        Assert.assertEquals(true, solution.isSameTree(new TreeNode(1, new TreeNode(2), new TreeNode(3)),
                new TreeNode(1, new TreeNode(2), new TreeNode(3))));
        Assert.assertEquals(false, solution.isSameTree(new TreeNode(1, new TreeNode(2), new TreeNode(3)),
                new TreeNode(1, new TreeNode(2), new TreeNode(4))));
        // 环形链表
        var ln1 = new ListNode(3);
        var ln2 = new ListNode(2);
        var ln3 = new ListNode(0);
        var ln4 = new ListNode(-4);
        ln1.next = ln2;
        ln2.next = ln3;
        ln3.next = ln4;
        ln4.next = ln2;
        Assert.assertEquals(true, solution.hasCycle(ln1));
        // 寻找两个正序数组的中位数
        System.out.println("寻找两个正序数组的中位数:");
        System.out.println(solution.findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
        System.out.println(solution.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
        // Pow(x,n)
        System.out.println("Pow(x,n):");
        System.out.println(solution.myPow(2.0d, 10));
        System.out.println(solution.myPow(2.1d, 3));
        System.out.println(solution.myPow(2d, -2));
        // 二叉树的最大深度
        Assert.assertEquals(3, solution.maxDepth(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)))));
        // 对称二叉树
        Assert.assertEquals(true, solution.isSymmetric(new TreeNode(1, new TreeNode(2, new TreeNode(3), null), new TreeNode(2, null, new TreeNode(3)))));
        Assert.assertEquals(false, solution.isSymmetric(new TreeNode(1, new TreeNode(4, new TreeNode(3), null), new TreeNode(2, null, new TreeNode(3)))));
        // 二叉树的层序遍历
        System.out.println("二叉树的层序遍历:");
        System.out.println(solution.levelOrder(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)))));
        System.out.println(solution.levelOrder(new TreeNode(1)));
        System.out.println(solution.levelOrder(null));
        // 第三大的数
        Assert.assertEquals(1, solution.thirdMax(new int[]{3, 2, 1}));
        Assert.assertEquals(2, solution.thirdMax(new int[]{1, 2}));
        Assert.assertEquals(1, solution.thirdMax(new int[]{2, 2, 3, 1}));
        // Nim游戏
        Assert.assertEquals(false, solution.canWinNim(4));
        Assert.assertEquals(true, solution.canWinNim(9));
        // 跳跃游戏III
        Assert.assertEquals(true, solution.canReach(new int[]{4, 2, 3, 0, 3, 1, 2}, 5));
        Assert.assertEquals(true, solution.canReach(new int[]{4, 2, 3, 0, 3, 1, 2}, 0));
        Assert.assertEquals(false, solution.canReach(new int[]{3, 0, 2, 1, 2}, 2));
        // 同构字符串
        Assert.assertEquals(true, solution.isIsomorphic("egg", "add"));
        Assert.assertEquals(false, solution.isIsomorphic("foo", "bar"));
        Assert.assertEquals(true, solution.isIsomorphic("paper", "title"));
        // 买卖股票的最佳时机II
        Assert.assertEquals(4, solution.maxProfit2(new int[]{1, 2, 3, 4, 5}));
        // 顺时针打印矩阵
        System.out.println("顺时针打印矩阵:");
        nums = solution.spiralOrder(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        nums = solution.spiralOrder(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        });
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        // 整数反转
        Assert.assertEquals(321, solution.reverse(123));
        Assert.assertEquals(-321, solution.reverse(-123));
        Assert.assertEquals(21, solution.reverse(120));
        Assert.assertEquals(0, solution.reverse(0));
        // 无重复字符串的排列组合
        System.out.println("无重复字符串的排列组合: ");
        System.out.println(Arrays.asList(solution.permutation("qwe")));
        System.out.println(Arrays.asList(solution.permutation("ab")));
        System.out.println(Arrays.asList(solution.permutation("ryawrowv")));
        // 最长回文串
        Assert.assertEquals(7, solution.longestPalindrome2("abccccdd"));
        Assert.assertEquals(1, solution.longestPalindrome2("a"));
        // 斐波那契数列
        Assert.assertEquals(1, solution.fib(2));
        Assert.assertEquals(2, solution.fib(3));
        Assert.assertEquals(3, solution.fib(4));
        // 螺旋矩阵
        System.out.println("螺旋矩阵:");
        System.out.println(solution.spiralOrder2(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        }));
        System.out.println(solution.spiralOrder2(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        }));
        // 杨辉三角
        System.out.println("杨辉三角:");
        System.out.println(solution.generate(5));
        System.out.println(solution.generate(1));
        // 杨辉三角II
        System.out.println("杨辉三角II:");
        System.out.println(solution.getRow(3));
        System.out.println(solution.getRow(0));
        System.out.println(solution.getRow(1));
        // 比特位计数
        System.out.println("比特位计数:");
        Arrays.stream(solution.countBits(2)).forEach(System.out::print);
        System.out.println();
        Arrays.stream(solution.countBits(5)).forEach(System.out::print);
        System.out.println();
        // 判断子序列
        Assert.assertEquals(true, solution.isSubsequence("abc", "ahbgdc"));
        Assert.assertEquals(false, solution.isSubsequence("axc", "ahbgdc"));
        // 第N个泰波那契数
        Assert.assertEquals(4, solution.tribonacci(4));
        Assert.assertEquals(1389537, solution.tribonacci(25));
        // 四数相加II
        Assert.assertEquals(2, solution.fourSumCount(new int[]{1, 2}, new int[]{-2, -1}, new int[]{-1, 2}, new int[]{0, 2}));
        Assert.assertEquals(1, solution.fourSumCount(new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0}));
        // 使用最小花费爬楼梯
        Assert.assertEquals(15, solution.minCostClimbingStairs(new int[]{10, 15, 20}));
        Assert.assertEquals(6, solution.minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));
        // 获取生成数组中的最大值
        Assert.assertEquals(3, solution.getMaximumGenerated(7));
        Assert.assertEquals(1, solution.getMaximumGenerated(2));
        Assert.assertEquals(2, solution.getMaximumGenerated(3));
        // 将数组分成和相等的三个部分
        Assert.assertEquals(true, solution.canThreePartsEqualSum(new int[]{0, 2, 1, -6, 6, -7, 9, 1, 2, 0, 1}));
        Assert.assertEquals(false, solution.canThreePartsEqualSum(new int[]{0, 2, 1, -6, 6, 7, 9, -1, 2, 0, 1}));
        Assert.assertEquals(true, solution.canThreePartsEqualSum(new int[]{3, 3, 6, 5, -2, 2, 5, 1, -9, 4}));
        // 两数相加II
        System.out.println("两数相加II:");
        n = solution.addTwoNumbers2(new ListNode(7, new ListNode(2, new ListNode(4, new ListNode(3)))),
                new ListNode(5, new ListNode(6, new ListNode(4))));
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 螺旋矩阵II
        System.out.println("螺旋矩阵II:");
        var matrix = solution.generateMatrix(3);
        for (var cs : matrix) {
            for (var c : cs) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
        matrix = solution.generateMatrix(1);
        for (var cs : matrix) {
            for (var c : cs) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        // 旋转链表
        System.out.println("旋转链表:");
        n = solution.rotateRight(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2);
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        n = solution.rotateRight(new ListNode(0, new ListNode(1, new ListNode(2))), 4);
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 找不同
        Assert.assertEquals('e', solution.findTheDifference("abcd", "acbde"));
        Assert.assertEquals('y', solution.findTheDifference("", "y"));
        // 移动零
        System.out.println("移动零:");
        nums = new int[]{0, 1, 0, 3, 12};
        solution.moveZeroes(nums);
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        // 反转字符串中的元音字母
        Assert.assertEquals("holle", solution.reverseVowels("hello"));
        Assert.assertEquals("leotcede", solution.reverseVowels("leetcode"));
        Assert.assertEquals("race car", solution.reverseVowels("race car"));
        // 反转字符串
        System.out.println("反转字符串:");
        var cs = new char[]{'h', 'e', 'l', 'l', 'o'};
        solution.reverseString(cs);
        for (var c : cs) {
            System.out.print(c + " ");
        }
        System.out.println();
        // 反转字符串II
        Assert.assertEquals("bacdfeg", solution.reverseStr("abcdefg", 2));
        Assert.assertEquals("bacd", solution.reverseStr("abcd", 2));
        // Z字形变换
        Assert.assertEquals("PAHNAPLSIIGYIR", solution.convert("PAYPALISHIRING", 3));
        Assert.assertEquals("PINALSIGYAHRPI", solution.convert("PAYPALISHIRING", 4));
        // 平衡二叉树
        Assert.assertEquals(true, solution.isBalanced(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)))));
        Assert.assertEquals(false, solution.isBalanced(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7, new TreeNode(8), null)))));
        // 解数独
        System.out.println("解数独:");
        board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        solution.solveSudoku(board);
        Arrays.stream(board).forEach(x -> {
            for (var c : x) {
                System.out.print(c + " ");
            }
            System.out.println();
        });
        // K个一组翻转链表
        System.out.println("K个一组翻转链表:");
        n = solution.reverseKGroup(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2);
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        n = solution.reverseKGroup(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 3);
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 赎金信
        Assert.assertEquals(false, solution.canConstruct("a", "b"));
        Assert.assertEquals(false, solution.canConstruct("aa", "ab"));
        Assert.assertEquals(true, solution.canConstruct("aa", "aab"));
        // 重复的子字符串
        Assert.assertEquals(true, solution.repeatedSubstringPattern("aaa"));
        Assert.assertEquals(false, solution.repeatedSubstringPattern("aac"));
        Assert.assertEquals(true, solution.repeatedSubstringPattern("abab"));
        Assert.assertEquals(false, solution.repeatedSubstringPattern("aba"));
        Assert.assertEquals(true, solution.repeatedSubstringPattern("abcabcabcabc"));
        // 唯一摩尔斯密码词
        Assert.assertEquals(2, solution.uniqueMorseRepresentations(new String[]{"gin", "zen", "gig", "msg"}));
        Assert.assertEquals(1, solution.uniqueMorseRepresentations(new String[]{"a"}));
        // 能否连接形成数组
        Assert.assertEquals(true, solution.canFormArray(new int[]{15, 88}, new int[][]{{88}, {15}}));
        Assert.assertEquals(false, solution.canFormArray(new int[]{49, 18, 16}, new int[][]{{16, 18, 49}}));
        Assert.assertEquals(true, solution.canFormArray(new int[]{91, 4, 64, 78}, new int[][]{{78}, {4, 64}, {91}}));
        // 全排列
        System.out.println("全排列:");
        System.out.println(solution.permute(new int[]{1, 2, 3}));
        System.out.println(solution.permute(new int[]{0, 1}));
        System.out.println(solution.permute(new int[]{1}));
        // 全排列II
        System.out.println("全排列II:");
        System.out.println(solution.permuteUnique(new int[]{1, 1, 2}));
        System.out.println(solution.permuteUnique(new int[]{1, 2, 3}));
        // 旋转数字
        Assert.assertEquals(1, solution.rotatedDigits(2));
        Assert.assertEquals(4, solution.rotatedDigits(10));
        // 对角线遍历
        System.out.println("对角线遍历:");
        nums = solution.findDiagonalOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        nums = solution.findDiagonalOrder(new int[][]{{1, 2}, {3, 5}, {4, 6}, {7, 8}});
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        // 数组中的第K个最大元素
        Assert.assertEquals(5, solution.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
        Assert.assertEquals(4, solution.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
        // 二叉树的锯齿形层序遍历
        System.out.println("二叉树的锯齿形层序遍历:");
        System.out.println(solution.zigzagLevelOrder(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)))));
        System.out.println(solution.zigzagLevelOrder(new TreeNode(1, new TreeNode(2), new TreeNode(3, new TreeNode(4), new TreeNode(5)))));
        System.out.println(solution.zigzagLevelOrder(new TreeNode(1)));
        System.out.println(solution.zigzagLevelOrder(null));
        // 二叉树的最近公共祖先
        var t1 = new TreeNode(3);
        var t2 = new TreeNode(5);
        var t3 = new TreeNode(1);
        t1.left = t2;
        t1.right = t3;
        var t4 = new TreeNode(6);
        var t5 = new TreeNode(2);
        t2.left = t3;
        t2.right = t5;
        var t6 = new TreeNode(7);
        var t7 = new TreeNode(4);
        t5.left = t6;
        t5.right = t7;
        var t8 = new TreeNode(0);
        var t9 = new TreeNode(8);
        t3.left = t8;
        t3.right = t9;
        Assert.assertEquals(3, solution.lowestCommonAncestor(t1, t2, t3).val);
        // 最大子数组和
        Assert.assertEquals(1, solution.maxSubArray(new int[]{1}));
        Assert.assertEquals(2, solution.maxSubArray(new int[]{1, -1, 2}));
        Assert.assertEquals(6, solution.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        Assert.assertEquals(1, solution.maxSubArray(new int[]{-2, 1}));
        Assert.assertEquals(23, solution.maxSubArray(new int[]{5, 4, -1, 7, 8}));
        // 二叉树的右视图
        System.out.println("二叉树的右视图:");
        System.out.println(solution.rightSideView(new TreeNode(1, new TreeNode(2, null, new TreeNode(5)), new TreeNode(3, null, new TreeNode(4)))));
        System.out.println(solution.rightSideView(new TreeNode(1, null, new TreeNode(3))));
        // 从前序与中序遍历序列构造二叉树
        solution.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
        // 岛屿数量
        var grid = new char[][]{
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'},
        };
        Assert.assertEquals(1, solution.numIslands(grid));
        grid = new char[][]{
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '1', '1'},
        };
        Assert.assertEquals(2, solution.numIslands(grid));
        // 环形链表II
        ln1 = new ListNode(3);
        ln2 = new ListNode(2);
        ln3 = new ListNode(0);
        ln4 = new ListNode(4);
        ln1.next = ln2;
        ln2.next = ln3;
        ln3.next = ln4;
        ln4.next = ln2;
        Assert.assertEquals(ln2, solution.detectCycle(ln1));
        ln4.next = null;
        Assert.assertEquals(null, solution.detectCycle(ln1));
        // 合并区间
        System.out.println("合并区间:");
        for (var m1 : solution.merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})) {
            for (var m2 : m1) {
                System.out.print(m2 + " ");
            }
            System.out.println();
        }
        // 最长递增子序列
        Assert.assertEquals(2, solution.lengthOfLIS(new int[]{10, 1, 2}));
        Assert.assertEquals(1, solution.lengthOfLIS(new int[]{10, 1}));
        Assert.assertEquals(3, solution.lengthOfLIS(new int[]{1, 2, 3}));
        Assert.assertEquals(2, solution.lengthOfLIS(new int[]{7, 7, 10}));
        Assert.assertEquals(4, solution.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        Assert.assertEquals(4, solution.lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));
        Assert.assertEquals(1, solution.lengthOfLIS(new int[]{7, 7, 7, 7, 7, 7, 7}));
        // 重排链表
        n = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        solution.reorderList(n);
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 矩阵中的最长递增路径
        Assert.assertEquals(4, solution.longestIncreasingPath(new int[][]{
                {3, 4, 5},
                {3, 2, 6},
                {2, 2, 1}
        }));
        Assert.assertEquals(4, solution.longestIncreasingPath(new int[][]{
                {9, 9, 4},
                {6, 6, 8},
                {2, 2, 1}
        }));
        Assert.assertEquals(1, solution.longestIncreasingPath(new int[][]{
                {9}
        }));
        Assert.assertEquals(4, solution.longestIncreasingPath(new int[][]{
                {3, 7},
                {2, 1}
        }));
        // 根据前序和后序遍历构造二叉树
        solution.constructFromPrePost(new int[]{1, 2, 4, 5, 3, 6, 7}, new int[]{4, 5, 2, 6, 7, 3, 1});
        // 二叉树的直径
        Assert.assertEquals(3, solution.diameterOfBinaryTree(new TreeNode(1, new TreeNode(2, new TreeNode(4), new TreeNode(5)), new TreeNode(3))));
        solution.max = 0;
        Assert.assertEquals(2, solution.diameterOfBinaryTree(new TreeNode(1, new TreeNode(2), new TreeNode(3))));
        solution.max = 0;
        Assert.assertEquals(0, solution.diameterOfBinaryTree(new TreeNode(1)));
        solution.max = 0;
        Assert.assertEquals(2, solution.diameterOfBinaryTree(new TreeNode(1, new TreeNode(2, new TreeNode(4), new TreeNode(5)), null)));
        solution.max = 0;
        Assert.assertEquals(3, solution.diameterOfBinaryTree(new TreeNode(1, new TreeNode(2, new TreeNode(3, new TreeNode(4), null), null), null)));
        solution.max = 0;
        Assert.assertEquals(5, solution.diameterOfBinaryTree(new TreeNode(1, new TreeNode(2, new TreeNode(4, new TreeNode(9), null), new TreeNode(5, new TreeNode(7, new TreeNode(8), null), new TreeNode(6))), new TreeNode(3))));
        // 翻转二叉树
        solution.invertTree(new TreeNode(2, new TreeNode(1), new TreeNode(3)));
        // 验证二叉搜索树
        Assert.assertEquals(true, solution.isValidBST(new TreeNode(2, new TreeNode(1), new TreeNode(3))));
        Assert.assertEquals(false, solution.isValidBST(new TreeNode(1, new TreeNode(2), new TreeNode(3))));
        // 最长重复子数组
        Assert.assertEquals(3, solution.findLength(new int[]{1, 2, 3, 2, 1}, new int[]{3, 2, 1, 4, 7}));
        Assert.assertEquals(5, solution.findLength(new int[]{0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}));
        // 旋转图像
        System.out.println("旋转图像:");
        matrix = new int[][]{
                {5, 1, 9, 11},
                {2, 4, 8, 10},
                {13, 3, 6, 7},
                {15, 14, 12, 16}};
        solution.rotate(matrix);
        for (var m1 : matrix) {
            for (var m2 : m1) {
                System.out.print(m2 + " ");
            }
            System.out.println();
        }
        // 子集
        System.out.println("子集:");
        System.out.println(solution.subsets(new int[]{1, 2, 3}));
        // 二叉搜索树中第K小的元素
        Assert.assertEquals(1, solution.kthSmallest(new TreeNode(3, new TreeNode(1, null, new TreeNode(2)), new TreeNode(4)), 1));
        Assert.assertEquals(3, solution.kthSmallest(new TreeNode(5, new TreeNode(3, new TreeNode(2, new TreeNode(1), null), new TreeNode(4)), new TreeNode(6)), 3));
        // 最长公共子序列
        Assert.assertEquals(2, solution.longestCommonSubsequence("aa", "aa"));
        Assert.assertEquals(2, solution.longestCommonSubsequence("acc", "abc"));
        Assert.assertEquals(3, solution.longestCommonSubsequence("abc", "abc"));
        Assert.assertEquals(3, solution.longestCommonSubsequence("abcde", "ace"));
        Assert.assertEquals(3, solution.longestCommonSubsequence("abc", "abc"));
        Assert.assertEquals(0, solution.longestCommonSubsequence("abc", "def"));
        // 零钱兑换
        Assert.assertEquals(2, solution.coinChange(new int[]{1, 2, 2}, 4));
        Assert.assertEquals(2, solution.coinChange(new int[]{1, 2, 3}, 4));
        Assert.assertEquals(3, solution.coinChange(new int[]{1, 2, 5}, 11));
        Assert.assertEquals(-1, solution.coinChange(new int[]{2}, 3));
        Assert.assertEquals(0, solution.coinChange(new int[]{1}, 0));
        // 链表排序
        System.out.println("链表排序:");
        n = solution.sortList(new ListNode(4, new ListNode(2, new ListNode(1, new ListNode(3)))));
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 打家劫舍
        Assert.assertEquals(4, solution.rob(new int[]{1, 2, 3, 1}));
        Assert.assertEquals(2, solution.rob(new int[]{1, 2}));
        Assert.assertEquals(4, solution.rob(new int[]{1, 2, 3}));
        Assert.assertEquals(12, solution.rob(new int[]{2, 7, 9, 3, 1}));
        Assert.assertEquals(4, solution.rob(new int[]{2, 1, 1, 2}));
        // 排序数组
        System.out.println("排序数组:");
        Arrays.stream(solution.sortArray(new int[]{3, 2, 5, 1, 10})).forEach(x -> {
            System.out.print(x + " ");
        });
        System.out.println();
        Arrays.stream(solution.sortArray(new int[]{5, 1, 1, 2, 0, 0})).forEach(x -> {
            System.out.print(x + " ");
        });
        System.out.println();
        // 翻转数位
        Assert.assertEquals(8, solution.reverseBits(1775));
        Assert.assertEquals(4, solution.reverseBits(7));
        // 单词搜索
        board = new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'},
        };
        Assert.assertEquals(true, solution.exist(board, "ABCCED"));
        // 二叉树的所有路径
        System.out.println("二叉树的所有路径:");
        System.out.println(solution.binaryTreePaths(new TreeNode(1, new TreeNode(2, null, new TreeNode(5)), new TreeNode(3))));
        System.out.println(solution.binaryTreePaths(new TreeNode(1)));
        // 字符串的排列
        System.out.println("字符串的排列:");
        Arrays.stream(solution.permutation2("abc")).forEach(System.out::println);
        Arrays.stream(solution.permutation2(null)).forEach(System.out::println);
        // 二叉搜索树中的插入操作
        System.out.println("二叉搜索树中的插入操作:");
        var rlt = new ArrayList<Integer>();
        solution.inOrder(solution.insertIntoBST(new TreeNode(4, new TreeNode(2, new TreeNode(1), new TreeNode(3)), new TreeNode(7)), 5), rlt);
        System.out.println(rlt);
        // 恢复二叉搜索树
        rlt = new ArrayList<Integer>();
        System.out.println("恢复二叉搜索树:");
        var tree = new TreeNode(3, new TreeNode(1), new TreeNode(4, new TreeNode(2), null));
        solution.recoverTree(tree);
        solution.inOrder(tree, rlt);
        System.out.println(rlt);
        // 连续的子数组和
//        Assert.assertEquals(true, solution.checkSubarraySum(new int[]{23, 2, 4, 6, 7}, 6));
//        Assert.assertEquals(true, solution.checkSubarraySum(new int[]{23, 2, 6, 4, 7}, 6));
//        Assert.assertEquals(false, solution.checkSubarraySum(new int[]{23, 2, 6, 4, 7}, 13));
//        Assert.assertEquals(true, solution.checkSubarraySum(new int[]{23, 2, 4, 6, 6}, 7));
        Assert.assertEquals(false, solution.checkSubarraySum(new int[]{2, 2, 0}, 7));
        Assert.assertEquals(true, solution.checkSubarraySum(new int[]{5, 2, 0}, 7));
        Assert.assertEquals(true, solution.checkSubarraySum(new int[]{5, 0, 0}, 7));
        // 寻找重复数
        Assert.assertEquals(2, solution.findDuplicate(new int[]{1, 3, 4, 2, 2}));
        Assert.assertEquals(3, solution.findDuplicate(new int[]{3, 1, 3, 4, 2}));
        // 扑克牌中的顺子
        Assert.assertEquals(true, solution.isStraight(new int[]{5, 4, 3, 2, 1}));
        Assert.assertEquals(true, solution.isStraight(new int[]{1, 2, 3, 4, 5}));
        Assert.assertEquals(true, solution.isStraight(new int[]{0, 0, 1, 2, 5}));
        Assert.assertEquals(false, solution.isStraight(new int[]{0, 0, 1, 2, 11}));
        Assert.assertEquals(false, solution.isStraight(new int[]{0, 0, 2, 2, 5}));
        // 最大交换
        Assert.assertEquals(98863, solution.maximumSwap(98368));
        Assert.assertEquals(7236, solution.maximumSwap(2736));
        Assert.assertEquals(9973, solution.maximumSwap(9973));
        Assert.assertEquals(4132, solution.maximumSwap(2134));
        // 奇偶链表
        System.out.println("奇偶链表:");
        n = solution.oddEvenList(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))));
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 移掉K位数字
        Assert.assertEquals("1219", solution.removeKdigits("1432219", 3));
        Assert.assertEquals("11", solution.removeKdigits("112", 1));
        Assert.assertEquals("200", solution.removeKdigits("10200", 1));
        Assert.assertEquals("0", solution.removeKdigits("10", 2));
        // 分发糖果
        Assert.assertEquals(5, solution.candy(new int[]{1, 0, 2}));
        Assert.assertEquals(4, solution.candy(new int[]{1, 2, 2}));
        Assert.assertEquals(7, solution.candy(new int[]{1, 3, 2, 2, 1}));
        // 矩阵中的最长递增路径
        matrix = new int[][]{
                {9, 9, 4},
                {6, 6, 8},
                {2, 1, 1}};
        Assert.assertEquals(4, solution.longestIncreasingPath2(matrix));
        matrix = new int[][]{
                {3, 4, 5},
                {3, 2, 6},
                {2, 2, 1}};
        Assert.assertEquals(4, solution.longestIncreasingPath2(matrix));
        matrix = new int[][]{
                {1}};
        Assert.assertEquals(1, solution.longestIncreasingPath2(matrix));
        // 分隔链表
        System.out.println("分隔链表:");
        n = solution.partition(new ListNode(1, new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(5, new ListNode(2)))))), 3);
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        n = solution.partition(new ListNode(2, new ListNode(1)), 2);
        while (Objects.nonNull(n)) {
            System.out.print(n.val + " ");
            n = n.next;
        }
        System.out.println();
        // 单词拆分
        Assert.assertEquals(true, solution.wordBreak("leetcode", new ArrayList<String>() {{
            add("leet");
            add("code");
        }}));
        Assert.assertEquals(true, solution.wordBreak("applepenapple", new ArrayList<String>() {{
            add("apple");
            add("pen");
        }}));
        Assert.assertEquals(false, solution.wordBreak("catsandog", new ArrayList<String>() {{
            add("cats");
            add("dog");
            add("sand");
            add("and");
            add("cat");
        }}));
        // 下一个更大元素II
        System.out.println("下一个更大元素II:");
        nums = solution.nextGreaterElements(new int[]{1, 2, 1});
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        // 下一个更大元素I
        System.out.println("下一个更大元素I:");
        nums = solution.nextGreaterElements2(new int[]{1, 2, 1});
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        nums = solution.nextGreaterElements2(new int[]{1, 2, 3, 4, 3});
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        nums = solution.nextGreaterElements2(new int[]{100, 1, 11, 1, 120, 111, 123, 1, -1, -100});
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        // 两个数组的交集
        System.out.println("两个数组的交集:");
        nums = solution.intersection(new int[]{1, 2, 2, 1}, new int[]{2, 2});
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        nums = solution.intersection(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4});
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        // 柱状图中最大的矩形
        Assert.assertEquals(3, solution.largestRectangleArea(new int[]{2, 1, 2}));
        Assert.assertEquals(10, solution.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
        Assert.assertEquals(4, solution.largestRectangleArea(new int[]{2, 4}));
        Assert.assertEquals(8, solution.largestRectangleArea(new int[]{4, 2, 3, 4}));
        Assert.assertEquals(1, solution.largestRectangleArea(new int[]{1}));
        Assert.assertEquals(9, solution.largestRectangleArea(new int[]{0, 9}));
        // 数组中重复的数字
        Assert.assertEquals(2, solution.findRepeatNumber(new int[]{2, 3, 1, 0, 2, 5, 3}));
        // 搜索二维矩阵
        matrix = new int[][]{
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 60}};
        Assert.assertEquals(true, solution.searchMatrix(matrix, 3));
        matrix = new int[][]{
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 60}};
        Assert.assertEquals(false, solution.searchMatrix(matrix, 21));
        // 左叶子之和
        Assert.assertEquals(24, solution.sumOfLeftLeaves(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)))));
        Assert.assertEquals(0, solution.sumOfLeftLeaves(new TreeNode(1)));
        // 在排序数组中查找元素的第一个和最后一个位置
        System.out.println("在排序数组中查找元素的第一个和最后一个位置:");
        nums = solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        nums = solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6);
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        nums = solution.searchRange(new int[]{}, 0);
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        nums = solution.searchRange(new int[]{3, 3, 3}, 3);
        for (var c : nums) {
            System.out.print(c + " ");
        }
        System.out.println();
        // 字母异位词分组
        System.out.println("字母异位词分组:");
        System.out.println(solution.groupAnagrams(new String[]{"abc", "cba", "bac"}));
        System.out.println(solution.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        System.out.println(solution.groupAnagrams(new String[]{""}));
        System.out.println(solution.groupAnagrams(new String[]{"a"}));
        // 反转链表II
        System.out.println("反转链表II:");
        ln1 = solution.reverseBetween(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2, 4);
        while (Objects.nonNull(ln1)) {
            System.out.println(ln1.val + " ");
            ln1 = ln1.next;
        }
        System.out.println();
        ln1 = solution.reverseBetween(new ListNode(5), 1, 1);
        while (Objects.nonNull(ln1)) {
            System.out.println(ln1.val + " ");
            ln1 = ln1.next;
        }
        System.out.println();
        ln1 = solution.reverseBetween(new ListNode(3, new ListNode(5)), 1, 2);
        while (Objects.nonNull(ln1)) {
            System.out.println(ln1.val + " ");
            ln1 = ln1.next;
        }
        System.out.println();
        // 插入区间
        System.out.println("插入区间:");
        var intervals = solution.insert(new int[][]{{1, 3}, {6, 9}}, new int[]{2, 5});
        for (var i = 0; i < intervals.length; i++) {
            for (var j = 0; j < intervals[i].length; j++) {
                System.out.print(intervals[i][j] + " ");
            }
            System.out.println();
        }
        intervals = solution.insert(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8});
        for (var i = 0; i < intervals.length; i++) {
            for (var j = 0; j < intervals[i].length; j++) {
                System.out.print(intervals[i][j] + " ");
            }
            System.out.println();
        }
        intervals = solution.insert(new int[][]{}, new int[]{5, 7});
        for (var i = 0; i < intervals.length; i++) {
            for (var j = 0; j < intervals[i].length; j++) {
                System.out.print(intervals[i][j] + " ");
            }
            System.out.println();
        }
        intervals = solution.insert(new int[][]{{1, 5}}, new int[]{2, 3});
        for (var i = 0; i < intervals.length; i++) {
            for (var j = 0; j < intervals[i].length; j++) {
                System.out.print(intervals[i][j] + " ");
            }
            System.out.println();
        }
        intervals = solution.insert(new int[][]{{1, 5}}, new int[]{2, 7});
        for (var i = 0; i < intervals.length; i++) {
            for (var j = 0; j < intervals[i].length; j++) {
                System.out.print(intervals[i][j] + " ");
            }
            System.out.println();
        }
        // 字符串中的最大奇数
        Assert.assertEquals("4237", solution.largestOddNumber("4237"));
        Assert.assertEquals("5", solution.largestOddNumber("52"));
        Assert.assertEquals("15", solution.largestOddNumber("152"));
        Assert.assertEquals("415", solution.largestOddNumber("41522"));
    }
}