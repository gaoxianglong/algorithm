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
import lombok.val;
import org.junit.Assert;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/7/2 19:31
 */
public class Solution2 {
    /**
     * 找出数组中第一个回文字符串
     *
     * @param words
     * @return
     */
    public static String firstPalindrome(String[] words) {
        var rlt = "";
        for (var word : words) {
            int l = 0, r = word.length();
            while (l < r) {
                if (word.charAt(l) != word.charAt(r)) {
                    break;
                }
                l++;
                r--;
            }
            if (l >= r) {
                rlt = word;
                break;
            }
        }
        return rlt;
    }

    /**
     * 玩筹码
     *
     * @param position
     * @return
     */
    public static int minCostToMoveChips(int[] position) {
        int odd = 0, event = 0;
        for (var p : position) {
            if ((p & 1) == 1) {
                odd++;
                continue;
            }
            event++;
        }
        return Math.min(odd, event);
    }

    /**
     * 两数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
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
     * 二分查找
     *
     * @param nums
     * @param target
     * @return
     */
    public static int search2(int[] nums, int target) {
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
     * 搜索插入位置
     *
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert(int[] nums, int target) {
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
     * 三数之和
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        var rlt = new ArrayList<List<Integer>>();
        for (var i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            var k = nums.length - 1;
            for (var j = i + 1; j < nums.length; j++) {
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
                    rlt.add(new ArrayList<>() {{
                        add(t1);
                        add(t2);
                        add(t3);
                    }});
                }
            }
        }
        return rlt;
    }

    public static class ListNode {
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

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }

    /**
     * 两数相加
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, temp = null;
        var carry = 0;
        while (Objects.nonNull(l1) || Objects.nonNull(l2) || carry > 0) {
            var t1 = Objects.nonNull(l1) ? l1.val : 0;
            var t2 = Objects.nonNull(l2) ? l2.val : 0;
            var sum = t1 + t2 + carry;
            if (Objects.isNull(head)) {
                temp = new ListNode(sum % 10);
                head = temp;
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
     * 无重复字符的最长子串
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        int rlt = 0;
        for (var i = 0; i < s.length(); i++) {
            var set = new HashSet<Character>();
            set.add(s.charAt(i));
            for (var j = i + 1; j < s.length(); j++) {
                if (!set.add(s.charAt(j))) {
                    break;
                }
            }
            rlt = Math.max(rlt, set.size());
        }
        return rlt;
    }

    /**
     * 最长回文子串
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        int max = 0, l = 0, r = 0;
        for (var i = 0; i < s.length(); i++) {
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

    public static int[] expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return new int[]{++left, --right, right - left};
    }

    /**
     * 回文整数
     *
     * @param x
     * @return
     */
    public static boolean isPalindrome(int x) {
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
        return temp == x || temp / 10 == x;
    }

    public static int romanToInt(String s) {
        var map = new ConcurrentHashMap<Character, Integer>() {{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }};
        int rlt = 0;
        for (var i = 0; i < s.length(); i++) {
            var temp = map.get(s.charAt(i));
            if (i + 1 < s.length() && temp < map.get(s.charAt(i + 1))) {
                rlt -= temp;
            } else {
                rlt += temp;
            }
        }
        return rlt;
    }

    /**
     * 删除链表的倒数第 N 个结点
     *
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        var rlt = new ListNode(0, head);
        var temp = rlt;
        var stack = new LinkedList<ListNode>();
        while (Objects.nonNull(temp)) {
            stack.push(temp);
            temp = temp.next;
        }
        for (var i = 0; i < n; i++) {
            stack.pop();
        }
        var prev = stack.peek();
        prev.next = prev.next.next;
        return rlt.next;
    }

    /**
     * 检查二进制字符串字段
     *
     * @param s
     * @return
     */
    public static boolean checkOnesSegment(String s) {
        return s.indexOf("01") == -1;
    }

    /**
     * 跳跃游戏
     *
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {
        var max = 0;
        for (var i = 0; i < nums.length; i++) {
            if (i > max) {
                return false;
            }
            max = Math.max(max, i + nums[i]);
        }
        return true;
    }

    /**
     * 跳跃游戏2
     *
     * @param nums
     * @return
     */
    public static int jump(int[] nums) {
        int max = 0, end = 0, rlt = 0;
        for (var i = 0; i < nums.length - 1; i++) {
            max = Math.max(max, i + nums[i]);
            if (end == i) {
                rlt++;
                end = max;
            }
        }
        return rlt;
    }

    /**
     * 有效的括号
     *
     * @param s
     * @return
     */
    public static boolean isValid(String s) {
        var map = new ConcurrentHashMap<Character, Character>() {{
            put(']', '[');
            put('}', '{');
            put(')', '(');
        }};
        var stack = new LinkedList<Character>();
        for (var c : s.toCharArray()) {
            if (map.containsKey(c)) {
                if (map.get(c) != stack.peek()) {
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
     * 组合总和2
     *
     * @param candidates
     * @param target
     * @return
     */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        var rlt = new ArrayList<List<Integer>>();
        dfs2(candidates, target, rlt, new LinkedList<>(), 0);
        return rlt;
    }

    public static void dfs2(int[] candidates, int target, List<List<Integer>> rlt, LinkedList<Integer> path, int index) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            rlt.add(new ArrayList<>(path));
            return;
        }
        for (var i = index; i < candidates.length; i++) {
            path.add(candidates[i]);
            dfs(candidates, target - candidates[i], rlt, path, i);
            path.removeLast();
        }
    }

    /**
     * 组合总和
     *
     * @param candidates
     * @param target
     * @return
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        var rlt = new ArrayList<List<Integer>>();
        dfs(candidates, target, rlt, new LinkedList<>(), 0);
        return rlt;
    }

    public static void dfs(int[] candidates, int target, List<List<Integer>> rlt, LinkedList<Integer> path, int index) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            rlt.add(new ArrayList<>(path));
            return;
        }
        for (var i = index; i < candidates.length; i++) {
            path.add(candidates[i]);
            dfs(candidates, target - candidates[i], rlt, path, i);
            path.removeLast();
        }
    }

    /**
     * 电话号码的字母组合
     *
     * @param digits
     * @return
     */
    public static List<String> letterCombinations(String digits) {
        var rlt = new ArrayList<String>();
        if (Objects.isNull(digits) || digits.isBlank()) {
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
        dfs(digits, 0, new StringBuilder(), rlt, map);
        return rlt;
    }

    public static void dfs(String digits, int digitsIndex, StringBuilder path, List<String> rlt, Map<Character, String> map) {
        if (digitsIndex >= digits.length()) {
            rlt.add(path.toString());
            return;
        }
        var temp = map.get(digits.charAt(digitsIndex));
        for (var i = 0; i < temp.length(); i++) {
            path.append(temp.charAt(i));
            dfs(digits, digitsIndex + 1, path, rlt, map);
            path.deleteCharAt(path.length() - 1);
        }
    }


    /**
     * 划分数组使最大差为 K
     *
     * @param nums
     * @param k
     * @return
     */
    public static int partitionArray(int[] nums, int k) {
        Arrays.sort(nums);
        int i = 0, rlt = 1;
        for (var j = 1; j < nums.length; j++) {
            if (nums[j] - nums[i] > k) {
                rlt++;
                i = j;
            }
        }
        return rlt;
    }

    /**
     * 字符串相乘
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String multiply(String num1, String num2) {
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
     * 字符串相加
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String addStrings(String num1, String num2) {
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

        void check(Transaction transaction) {
            var dt = Math.abs(this.time - transaction.time);
            if (this.name.equals(transaction.name) && !this.city.equals(transaction.city) && dt <= 60) {
                this.valid = true;
                transaction.valid = true;
            }
        }
    }

    /**
     * 查询无效交易
     *
     * @param transactions
     * @return
     */
    public static List<String> invalidTransactions(String[] transactions) {
        var rlt = new ArrayList<String>();
        var ts = new ArrayList<Transaction>();
        Arrays.stream(transactions).forEach(x -> {
            var temp = x.split(",");
            ts.add(new Transaction(x, temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), temp[3]));
        });
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
     * 四数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        var rlt = new ArrayList<List<Integer>>();
        for (var i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (var j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                var k = nums.length - 1;
                for (var l = j + 1; l < nums.length; l++) {
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
     * 删掉一个元素以后全为 1 的最长子数组
     *
     * @param nums
     * @return
     */
    public static int longestSubarray(int[] nums) {
        int prev = 0, count = 0, rlt = 0;
        for (var n : nums) {
            if (n == 1) {
                prev++;
                count++;
                rlt = Math.max(rlt, count);
                continue;
            }
            count = prev;
            prev = 0;
        }
        return rlt == nums.length ? --rlt : rlt;
    }

    /**
     * 找出字符串中第一个匹配项的下标
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle) {
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
     * 爬楼梯
     *
     * @param n
     * @return
     */
    public static int climbStairs(int n) {
        int i = 0, j = 0, k = 1;
        for (var l = 0; l < n; l++) {
            i = j;
            j = k;
            k = i + k;
        }
        return k;
    }

    /**
     * 下一个排列
     *
     * @param nums
     */
    public static void nextPermutation(int[] nums) {
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

    public static void swap(int[] nums, int i, int j) {
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }

    public static void reserve(int[] nums, int start) {
        var end = nums.length - 1;
        while (start < end) {
            swap(nums, start++, end--);
        }
    }

    /**
     * 搜索旋转排序数组
     *
     * @param nums
     * @param target
     * @return
     */
    public static int search(int[] nums, int target) {
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
     * 合并K个升序链表
     *
     * @param lists
     * @return
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        ListNode rlt = null;
        for (var ln : lists) {
            rlt = mergeTwoLists(rlt, ln);
        }
        return rlt;
    }

    /**
     * 合并两个有序链表
     *
     * @param list1
     * @param list2
     * @return
     */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
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
     * 两两交换链表中的节点
     *
     * @param head
     * @return
     */
    public static ListNode swapPairs(ListNode head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }
        var next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }

    /**
     * 回文链表
     *
     * @param head
     * @return
     */
    public static boolean isPalindrome(ListNode head) {
        var temp = reserve(getSubsequent(head));
        while (Objects.nonNull(temp)) {
            if (temp.val != head.val) {
                return false;
            }
            temp = temp.next;
            head = head.next;
        }
        return true;
    }

    public static ListNode reserve(ListNode node) {
        ListNode l1 = null, l2 = node;
        while (Objects.nonNull(l2)) {
            var next = l2.next;
            l2.next = l1;
            l1 = l2;
            l2 = next;
        }
        return l1;
    }

    public static ListNode getSubsequent(ListNode node) {
        ListNode l1 = node, l2 = node;
        while (Objects.nonNull(l2) && Objects.nonNull(l2.next)) {
            l1 = l1.next;
            l2 = l2.next.next;
        }
        return l1.next;
    }

    /**
     * 盛最多水的容器
     *
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {
        int max = 0, l = 0, r = height.length - 1;
        while (l < r) {
            if (height[l] < height[r]) {
                max = Math.max(max, (r - l) * height[l++]);
                continue;
            }
            max = Math.max(max, (r - l) * height[r--]);
        }
        return max;
    }

    /**
     * 最接近的三数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int rlt = 0, min = Integer.MAX_VALUE;
        l1:
        for (var i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                var sum = nums[i] + nums[j] + nums[k];
                if (sum == target) {
                    rlt = sum;
                    break l1;
                }
                var temp = Math.abs(sum - target);
                if (min > temp) {
                    min = temp;
                    rlt = sum;
                }
                if (sum > target) {
                    k--;
                } else {
                    j++;
                }
            }
        }
        return rlt;
    }

    /**
     * 两数相除
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static int divide(int dividend, int divisor) {
        if (dividend == 0) {
            return 0;
        }
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        var negative = (dividend ^ divisor) < 0;
        var dividendNew = Math.abs(dividend * 1L);
        var divisorNew = Math.abs(divisor * 1L);
        var rlt = 0;
        for (var i = 31; i >= 0; i--) {
            if ((dividendNew >> i) >= divisorNew) {
                rlt += 1 << i;
                dividendNew -= divisorNew << i;
            }
        }
        return negative ? -rlt : rlt;
    }

    /**
     * 删除有序数组中的重复项
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        var i = 0;
        for (var j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                nums[++i] = nums[j];
            }
        }
        return ++i;
    }

    /**
     * 有效的数独
     *
     * @param board
     * @return
     */
    public static boolean isValidSudoku(char[][] board) {
        var r = new int[9][9];
        var c = new int[9][9];
        var l = new int[3][3][9];
        for (var i = 0; i < board.length; i++) {
            for (var j = 0; j < board[i].length; j++) {
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

    /**
     * 岛屿的最大面积
     *
     * @param grid
     * @return
     */
    public static int maxAreaOfIsland(int[][] grid) {
        var rlt = 0;
        for (var i = 0; i < grid.length; i++) {
            for (var j = 0; j < grid[i].length; j++) {
                rlt = Math.max(rlt, dfs(grid, i, j));
            }
        }
        return rlt;
    }

    public static int dfs(int[][] grid, int i, int j) {
        if ((i < 0 || i >= grid.length) || (j < 0 || j >= grid[0].length) || grid[i][j] == 0) {
            return 0;
        }
        grid[i][j] = 0;
        var rlt = 1;
        var t1 = new int[]{0, 0, 1, -1};
        var t2 = new int[]{1, -1, 0, 0};
        for (var k = 0; k < 4; k++) {
            rlt += dfs(grid, t1[k] + i, t2[k] + j);
        }
        return rlt;
    }

    /**
     * 最长公共前缀
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        var prev = strs[0];
        for (var i = 1; i < strs.length; i++) {
            int j = 0, k = 0;
            while (j < prev.length() && k < strs[i].length()) {
                if (prev.charAt(j) != strs[i].charAt(k)) {
                    break;
                }
                j++;
                k++;
            }
            prev = prev.substring(0, j);
        }
        return prev;
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
     * 二叉树的中序遍历
     *
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        var rlt = new ArrayList<Integer>();
        inOrder(root, rlt);
        return rlt;
    }

    public static void inOrder(TreeNode root, List<Integer> rlt) {
        if (Objects.isNull(root)) {
            return;
        }
        inOrder(root.left, rlt);
        rlt.add(root.val);
        inOrder(root.right, rlt);
    }

    /**
     * 合并两个有序数组
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int size = m + n, m_ = 0, n_ = 0, index = 0;
        var temp = new int[size];
        for (var i = 0; i < size; i++) {
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
        for (var i = 0; i < size; i++) {
            nums1[i] = temp[i];
        }
    }

    /**
     * 移除元素
     *
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement(int[] nums, int val) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            if (nums[l] == val) {
                nums[l] = nums[r--];
                continue;
            }
            l++;
        }
        return l;
    }

    /**
     * 不动点
     *
     * @param arr
     * @return
     */
    public static int fixedPoint(int[] arr) {
        int l = 0, r = arr.length - 1, rlt = -1;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if (arr[mid] < mid) {
                l = ++mid;
            } else if (arr[mid] > mid) {
                r = --mid;
            }
            if (arr[mid] == mid) {
                rlt = mid;
                r = --mid;
            }
        }
        return rlt;
    }

    /**
     * 接雨水
     *
     * @param height
     * @return
     */
    public static int trap(int[] height) {
        int l = 0, r = height.length - 1, lm = 0, rm = 0, rlt = 0;
        while (l < r) {
            lm = Math.max(lm, height[l]);
            rm = Math.max(rm, height[r]);
            if (height[l] < height[r]) {
                rlt += lm - height[l++];
                continue;
            }
            rlt += rm - height[r--];
        }
        return rlt;
    }

    /**
     * 括号生成
     *
     * @param n
     * @return
     */
    public static List<String> generateParenthesis(int n) {
        var rlt = new ArrayList<String>();
        dfs(0, 0, n, rlt, new StringBuilder());
        return rlt;
    }

    public static void dfs(int i, int j, int n, List<String> rlt, StringBuilder path) {
        if (n << 1 == path.length()) {
            rlt.add(path.toString());
            return;
        }
        if (i < n) {
            path.append("(");
            dfs(i + 1, j, n, rlt, path);
            path.deleteCharAt(path.length() - 1);
        }
        if (j < i) {
            path.append(")");
            dfs(i, j + 1, n, rlt, path);
            path.deleteCharAt(path.length() - 1);
        }
    }

    /**
     * 二叉树的前序遍历
     *
     * @param root
     * @return
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        var rlt = new ArrayList<Integer>();
        beforeOrder(root, rlt);
        return rlt;
    }

    public static void beforeOrder(TreeNode treeNode, List<Integer> rlt) {
        if (Objects.isNull(treeNode)) {
            return;
        }
        rlt.add(treeNode.val);
        beforeOrder(treeNode.left, rlt);
        beforeOrder(treeNode.right, rlt);
    }

    /**
     * 二叉树的后序遍历
     *
     * @param root
     * @return
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        var rlt = new ArrayList<Integer>();
        afterOrder(root, rlt);
        return rlt;
    }

    public static void afterOrder(TreeNode treeNode, List<Integer> rlt) {
        if (Objects.isNull(treeNode)) {
            return;
        }
        afterOrder(treeNode.left, rlt);
        afterOrder(treeNode.right, rlt);
        rlt.add(treeNode.val);
    }

    /**
     * x的平方根
     *
     * @param x
     * @return
     */
    public static int mySqrt(int x) {
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
     * 位1的个数
     *
     * @param n
     * @return
     */
    public static int hammingWeight(int n) {
        var rlt = 0;
        for (var i = 31; i >= 0; i--) {
            if (((n >> i) & 1) == 1) {
                rlt++;
            }
        }
        return rlt;
    }

    /**
     * 多数元素
     *
     * @param nums
     * @return
     */
    public static int majorityElement(int[] nums) {
        int count = 0, rlt = 0;
        for (var n : nums) {
            if (count == 0) {
                rlt = n;
            }
            count += rlt == n ? +1 : -1;
        }
        return rlt;
    }

    /**
     * 统计一致字符串的数目
     *
     * @param allowed
     * @param words
     * @return
     */
    public static int countConsistentStrings(String allowed, String[] words) {
        var rlt = 0;
        for (var word : words) {
            var i = 0;
            for (; i < word.length(); i++) {
                if (allowed.indexOf(word.charAt(i)) == -1) {
                    break;
                }
            }
            if (i >= word.length()) {
                rlt++;
            }
        }
        return rlt;
    }

    /**
     * 最长有效括号
     *
     * @param s
     * @return
     */
    public static int longestValidParentheses(String s) {
        var rlt = 0;
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
            rlt = Math.max(rlt, i - stack.peek());
        }
        return rlt;
    }

    /**
     * Excel表列名称
     *
     * @param columnNumber
     * @return
     */
    public static String convertToTitle(int columnNumber) {
        var rlt = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--;
            rlt.append((char) (columnNumber % 26 + 'A'));
            columnNumber /= 26;
        }
        return rlt.reverse().toString();
    }

    /**
     * 只出现一次的数字
     *
     * @param nums
     * @return
     */
    public static int singleNumber(int[] nums) {
        var rlt = 0;
        for (var n : nums) {
            rlt ^= n;
        }
        return rlt;
    }

    /**
     * 买卖股票的最佳时机
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int rlt = 0, min = Integer.MAX_VALUE;
        for (var p : prices) {
            if (min > p) {
                min = p;
                continue;
            }
            rlt = Math.max(rlt, p - min);
        }
        return rlt;
    }

    /**
     * 删除排序链表中的重复元素
     *
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode rlt = new ListNode(0, head);
        while (Objects.nonNull(head) && Objects.nonNull(head.next)) {
            if (head.val == head.next.val) {
                head.next = head.next.next;
                continue;
            }
            head = head.next;
        }
        return rlt.next;
    }

    /**
     * 二进制求和
     *
     * @param a
     * @param b
     * @return
     */
    public static String addBinary(String a, String b) {
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
     * 加一
     *
     * @param digits
     * @return
     */
    public static int[] plusOne(int[] digits) {
        int n = digits.length, carry = 0, i = digits.length - 2, j = 0;
        var last = digits[n - 1] + 1;
        var stack = new LinkedList<Integer>() {{
            push(last % 10);
        }};
        carry = last / 10;
        while (i >= 0 || carry > 0) {
            var t1 = i >= 0 ? digits[i--] : 0;
            var sum = t1 + carry;
            stack.push(sum % 10);
            carry = sum / 10;
        }
        var rlt = new int[stack.size()];
        while (!stack.isEmpty()) {
            rlt[j++] = stack.pop();
        }
        return rlt;
    }

    /**
     * 最后一个单词的长度
     *
     * @param s
     * @return
     */
    public static int lengthOfLastWord(String s) {
        var temp = s.split(" ");
        return temp[temp.length - 1].length();
    }

    /**
     * 相同的树
     *
     * @param p
     * @param q
     * @return
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        return dfs(p, q);
    }

    public static boolean dfs(TreeNode p, TreeNode q) {
        if ((Objects.isNull(p) && Objects.nonNull(q)) || (Objects.nonNull(p) && Objects.isNull(q))) {
            return false;
        }
        if (Objects.nonNull(p) && Objects.nonNull(q)) {
            if (p.val != q.val) {
                return false;
            }
            return dfs(p.left, q.left) && dfs(p.right, q.right);
        }
        return true;
    }

    /**
     * 环形链表
     *
     * @param head
     * @return
     */
    public static boolean hasCycle(ListNode head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return false;
        }
        var l1 = head;
        var l2 = head.next;
        while (Objects.nonNull(l2) && Objects.nonNull(l2.next)) {
            if (l1 == l2) {
                return true;
            }
            l1 = l1.next;
            l2 = l2.next.next;
        }
        return false;
    }

    /**
     * 寻找两个正序数组的中位数
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        var m = merge(nums1, nums2);
        var n = m.length;
        if ((n & 1) == 1) {
            return m[n >> 1];
        }
        int l = (n >> 1) - 1, r = n >> 1;
        return (m[l] + m[r]) / 2.0d;
    }

    public static int[] merge(int[] nums1, int[] nums2) {
        var rlt = new int[nums1.length + nums2.length];
        int i = nums1.length - 1, i_ = 0, j = nums2.length - 1, j_ = 0, n = rlt.length;
        for (var k = 0; k < n; k++) {
            if (i_ > i) {
                rlt[k] = nums2[j_++];
            } else if (j_ > j) {
                rlt[k] = nums1[i_++];
            } else if (nums1[i_] < nums2[j_]) {
                rlt[k] = nums1[i_++];
            } else {
                rlt[k] = nums2[j_++];
            }
        }
        return rlt;
    }

    /**
     * Pow(x,n)
     *
     * @param x
     * @param n
     * @return
     */
    public static double myPow(double x, int n) {
        var negative = (n ^ 0) < 0;
        n = negative ? -n : n;
        var rlt = quickPower(x, n);
        return negative ? 1.0D / rlt : rlt;
    }

    public static double quickPower(double x, long n) {
        if (0 == n) {
            return 1;
        }
        var rlt = quickPower(x, n / 2);
        return (n & 1) == 0 ? rlt * rlt : rlt * rlt * x;
    }

    /**
     * 二叉树的最大深度
     *
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {
        return getHeight(root);
    }

    public static int getHeight(TreeNode root) {
        if (Objects.nonNull(root)) {
            return 1 + Math.max(getHeight(root.left), getHeight(root.right));
        }
        return 0;
    }

    /**
     * 对称二叉树
     *
     * @param root
     * @return
     */
    public static boolean isSymmetric(TreeNode root) {
        return dfs_(root.left, root.right);
    }

    public static boolean dfs_(TreeNode l, TreeNode r) {
        if ((Objects.isNull(l) && Objects.nonNull(r)) ||
                (Objects.nonNull(l) && Objects.isNull(r))) {
            return false;
        }
        if (Objects.nonNull(l) && Objects.nonNull(r)) {
            if (l.val != r.val) {
                return false;
            }
            return dfs_(l.left, r.right) && dfs(l.right, r.left);
        }
        return true;
    }

    /**
     * 二叉树的层序遍历
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        var rlt = new ArrayList<List<Integer>>();
        if (Objects.isNull(root)) {
            return rlt;
        }
        var queue = new LinkedList<TreeNode>() {{
            offer(root);
        }};
        while (!queue.isEmpty()) {
            var list = new ArrayList<Integer>();
            var size = queue.size();
            for (var i = 0; i < size; i++) {
                var temp = queue.poll();
                list.add(temp.val);
                if (Objects.nonNull(temp.left)) {
                    queue.offer(temp.left);
                }
                if (Objects.nonNull(temp.right)) {
                    queue.offer(temp.right);
                }
            }
            rlt.add(list);
        }
        return rlt;
    }

    /**
     * 第三大的数
     *
     * @param nums
     * @return
     */
    public static int thirdMax(int[] nums) {
        Arrays.sort(nums);
        int i = 0;
        for (var j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                nums[++i] = nums[j];
            }
        }
        i++;
        return i < 3 ? nums[--i] : nums[i - 3];
    }

    /**
     * Nim游戏
     *
     * @param n
     * @return
     */
    public static boolean canWinNim(int n) {
        var rlt = n % 4;
        return rlt != 0;
    }

    /**
     * 跳跃游戏III
     *
     * @param arr
     * @param start
     * @return
     */
    public static boolean canReach(int[] arr, int start) {
        return dfs(arr, start, new int[arr.length]);
    }

    public static boolean dfs(int[] arr, int start, int[] path) {
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
     * 同构字符串
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic(String s, String t) {
        var mapping = new ConcurrentHashMap<Character, Character>();
        var isMapping = new HashSet<Character>();
        for (var i = 0; i < s.length(); i++) {
            if (mapping.containsKey(s.charAt(i))) {
                if (mapping.get(s.charAt(i)) != t.charAt(i)) {
                    return false;
                }
            } else {
                if (!isMapping.add(t.charAt(i))) {
                    return false;
                }
                mapping.put(s.charAt(i), t.charAt(i));
            }
        }
        return true;
    }

    /**
     * 买卖股票的最佳时机II
     *
     * @param prices
     * @return
     */
    public static int maxProfit2(int[] prices) {
        int rlt = 0, i = 0;
        for (var j = 1; j < prices.length; i++, j++) {
            if (prices[j] > prices[i]) {
                rlt += prices[j] - prices[i];
            }
        }
        return rlt;
    }

    /**
     * 顺时针打印矩阵
     *
     * @param matrix
     * @return
     */
    public static int[] spiralOrder(int[][] matrix) {
        if (Objects.isNull(matrix) || matrix.length < 1) {
            return new int[]{};
        }
        var rlt = new int[matrix.length * matrix[0].length];
        int l = 0, r = matrix[0].length - 1, u = 0, d = matrix.length - 1, index = 0;
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
     * 整数反转
     *
     * @param x
     * @return
     */
    public static int reverse(int x) {
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
        if (rlt % 10 != first) {
            return 0;
        }
        return negative ? -rlt : rlt;
    }

    /**
     * 正则表达式匹配
     *
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch(String s, String p) {
        var cs = s.toCharArray();
        var cp = p.toCharArray();
        var dp = new boolean[cs.length + 1][cp.length + 1];
        dp[0][0] = true;
        for (var i = 1; i <= cp.length; i++) {
            if (cp[i - 1] == '*') {
                dp[0][i] = dp[0][i - 2];
            }
        }

        for (var i = 1; i <= cs.length; i++) {
            for (var j = 1; j <= cp.length; j++) {
                if (cs[i - 1] == cp[j - 1] || cp[j - 1] == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (cp[j - 1] == '*') {
                    if (cs[i - 1] == cp[j - 2] || cp[j - 2] == '.') {
                        dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
                        continue;
                    }
                    dp[i][j] = dp[i][j - 2];
                }
            }
        }
        return dp[cs.length][cp.length];
    }

    /**
     * 最长回文串
     *
     * @param s
     * @return
     */
    public static int longestPalindrome2(String s) {
        var rlt = 0;
        var nums = new int[128];
        for (var c : s.toCharArray()) {
            nums[c]++;
        }
        for (var n : nums) {
            if (n < 1) {
                continue;
            }
            rlt += n >> 1 << 1;
            if ((rlt & 1) == 0 && (n & 1) == 1) {
                rlt++;
            }
        }
        return rlt;
    }

    /**
     * 斐波那契数列
     *
     * @param n
     * @return
     */
    public static int fib(int n) {
        if (n < 1) {
            return 0;
        }
        int i = 0, j = 0, k = 1;
        for (var l = 1; l < n; l++) {
            i = j;
            j = k;
            k = i + k;
        }
        return k;
    }

    /**
     * 螺旋矩阵
     *
     * @param matrix
     * @return
     */
    public static List<Integer> spiralOrder2(int[][] matrix) {
        var rlt = new ArrayList<Integer>();
        int l = 0, r = matrix[0].length - 1, u = 0, d = matrix.length - 1;
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
     * 杨辉三角
     *
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate(int numRows) {
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
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                }
                path.add(dp[i][j]);
            }
            rlt.add(path);
        }
        return rlt;
    }

    /**
     * 杨辉三角II
     *
     * @param rowIndex
     * @return
     */
    public static List<Integer> getRow(int rowIndex) {
        rowIndex++;
        var rlt = new ArrayList<Integer>();
        var dp = new int[rowIndex][rowIndex];
        for (var i = 0; i < rowIndex; i++) {
            for (var j = 0; j <= i; j++) {
                dp[i][j] = 1;
            }
        }
        for (var i = 0; i < rowIndex; i++) {
            for (var j = 0; j <= i; j++) {
                if (i - 1 >= 0 && j - 1 >= 0) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                }
            }
            if (i == rowIndex - 1) {
                for (var n : dp[i]) {
                    rlt.add(n);
                }
                break;
            }
        }
        return rlt;
    }

    /**
     * 比特位计数
     *
     * @param n
     * @return
     */
    public static int[] countBits(int n) {
        var rlt = new int[n + 1];
        for (var i = 0; i <= n; i++) {
            if ((i & 1) == 0) {
                rlt[i] = rlt[i >> 1];
                continue;
            }
            rlt[i] = rlt[i >> 1] + 1;
        }
        return rlt;
    }

    /**
     * 判断子序列
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isSubsequence(String s, String t) {
        int j = 0;
        for (var i = 0; i < t.length() && j < s.length(); i++) {
            if (t.charAt(i) == s.charAt(j)) {
                j++;
            }
        }
        return j >= s.length();
    }

    /**
     * 第N个泰波那契数
     *
     * @param n
     * @return
     */
    public static int tribonacci(int n) {
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
     * 四数相加II
     *
     * @param nums1
     * @param nums2
     * @param nums3
     * @param nums4
     * @return
     */
    public static int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
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
     * 使用最小花费爬楼梯
     *
     * @param cost
     * @return
     */
    public static int minCostClimbingStairs(int[] cost) {
        var dp = new int[cost.length + 1];
        for (var i = 2; i < dp.length; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        return dp[cost.length];
    }

    /**
     * 获取生成数组中的最大值
     *
     * @param n
     * @return
     */
    public static int getMaximumGenerated(int n) {
        if (n < 2) {
            return n;
        }
        var nums = new int[n + 1];
        nums[1] = 1;
        var rlt = nums[1];
        for (var i = 2; i <= n; i++) {
            if ((i & 1) == 0) {
                nums[i] = nums[i >> 1];
            } else {
                nums[i] = nums[i >> 1] + nums[(i >> 1) + 1];
            }
            rlt = Math.max(nums[i], rlt);
        }
        return rlt;
    }

    /**
     * 将数组分成和相等的三个部分
     *
     * @param nums
     * @return
     */
    public static boolean canThreePartsEqualSum(int[] nums) {
        int sum = 0, count = 0, temp = 0;
        for (var n : nums) {
            sum += n;
        }
        if (sum % 3 != 0) {
            return false;
        }
        for (var n : nums) {
            temp += n;
            if (temp == sum / 3) {
                temp = 0;
                count++;
            }
        }
        return count >= 3;
    }

    /**
     * 两数相加II
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        l1 = reserve2(l1);
        l2 = reserve2(l2);
        var carry = 0;
        ListNode head = null, temp = null;
        while (Objects.nonNull(l1) || Objects.nonNull(l2) || carry > 0) {
            var t1 = Objects.nonNull(l1) ? l1.val : 0;
            var t2 = Objects.nonNull(l2) ? l2.val : 0;
            var sum = t1 + t2 + carry;
            if (Objects.isNull(head)) {
                temp = new ListNode(sum % 10);
                head = temp;
            } else {
                temp.next = new ListNode(sum % 10);
                temp = temp.next;
            }
            carry = sum / 10;
            l1 = Objects.nonNull(l1) ? l1.next : null;
            l2 = Objects.nonNull(l2) ? l2.next : null;
        }
        return reserve2(head);
    }

    public static ListNode reserve2(ListNode head) {
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
     * 螺旋矩阵II
     *
     * @param n
     * @return
     */
    public static int[][] generateMatrix(int n) {
        var nums = new int[n][n];
        int l = 0, r = n - 1, u = 0, d = n - 1, num = 1;
        while (true) {
            for (var i = l; i <= r; i++) {
                nums[u][i] = num++;
            }
            if (++u > d) {
                break;
            }
            for (var i = u; i <= d; i++) {
                nums[i][r] = num++;
            }
            if (--r < l) {
                break;
            }
            for (var i = r; i >= l; i--) {
                nums[d][i] = num++;
            }
            if (--d < u) {
                break;
            }
            for (var i = d; i >= u; i--) {
                nums[i][l] = num++;
            }
            if (++l > r) {
                break;
            }
        }
        return nums;
    }

    /**
     * 旋转链表
     *
     * @param head
     * @param k
     * @return
     */
    public static ListNode rotateRight(ListNode head, int k) {
        if (Objects.isNull(head) || Objects.isNull(head.next) || k < 1) {
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
     * 找不同
     *
     * @param s
     * @param t
     * @return
     */
    public static char findTheDifference(String s, String t) {
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
                rlt = (char) i;
                break;
            }
        }
        return rlt;
    }

    /**
     * 移动零
     *
     * @param nums
     */
    public static void moveZeroes(int[] nums) {
        var i = 0;
        for (var j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                nums[i++] = nums[j];
            }
        }
        while (i < nums.length) {
            nums[i++] = 0;
        }
    }

    /**
     * 反转字符串中的元音字母
     *
     * @param s
     * @return
     */
    public static String reverseVowels(String s) {
        var rlt = s.toCharArray();
        int l = 0, r = rlt.length - 1;
        while (l < r) {
            var t1 = rlt[l];
            if (check(t1)) {
                while (r > l) {
                    try {
                        var t2 = rlt[r];
                        if (check(t2)) {
                            rlt[l] ^= rlt[r];
                            rlt[r] ^= rlt[l];
                            rlt[l] ^= rlt[r];
                            break;
                        }
                    } finally {
                        r--;
                    }
                }
            }
            l++;
        }
        return new String(rlt);
    }

    public static boolean check(char v) {
        return "aAeEiIoOuU".indexOf(v) != -1;
    }

    /**
     * 反转字符串
     *
     * @param s
     */
    public static void reverseString(char[] s) {
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
     * 反转字符串II
     *
     * @param s
     * @param k
     * @return
     */
    public static String reverseStr(String s, int k) {
        var cs = s.toCharArray();
        var n = cs.length;
        for (var i = 0; i < n; i += k << 1) {
            var j = i + k - 1;
            if (j >= n) {
                j = n - 1;
            }
            reserve(i, j, cs);
        }
        return new String(cs);
    }

    public static void reserve(int i, int j, char[] cs) {
        while (i < j) {
            cs[i] ^= cs[j];
            cs[j] ^= cs[i];
            cs[i] ^= cs[j];
            i++;
            j--;
        }
    }

    /**
     * Z字形变换
     *
     * @param s
     * @param numRows
     * @return
     */
    public static String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        var rlt = new StringBuilder();
        var nums = new int[numRows][s.length()];
        int i = 0, c = 0, r = 0;
        while (i < s.length()) {
            while (i < s.length() && r < numRows) {
                nums[r++][c] = s.charAt(i++);
            }
            r -= 2;
            c++;
            while (i < s.length() && r > 0) {
                nums[r--][c++] = s.charAt(i++);
            }
        }
        for (var n1 : nums) {
            for (var n2 : n1) {
                if (n2 != 0) {
                    rlt.append((char) n2);
                }
            }
        }
        return rlt.toString();
    }

    /**
     * 平衡二叉树
     *
     * @param root
     * @return
     */
    public static boolean isBalanced(TreeNode root) {
        if (Objects.isNull(root)) {
            return true;
        }
        return (Math.abs(getHeight2(root.left) - getHeight2(root.right)) <= 1) && isBalanced(root.left) && isBalanced(root.right);
    }

    public static int getHeight2(TreeNode root) {
        if (Objects.nonNull(root)) {
            return 1 + Math.max(getHeight2(root.left), getHeight2(root.right));
        }
        return 0;
    }

    static class States {
        int[][] r = new int[9][9];
        int[][] c = new int[9][9];
        int[][][] l = new int[3][3][9];
        boolean valid;
    }

    /**
     * 解数独
     *
     * @param board
     */
    public static void solveSudoku(char[][] board) {
        var s = new States();
        var list = new ArrayList<int[]>();
        for (var i = 0; i < board.length; i++) {
            for (var j = 0; j < board[i].length; j++) {
                var temp = board[i][j];
                if (temp == '.') {
                    list.add(new int[]{i, j});
                    continue;
                }
                var index = temp - '0' - 1;
                s.r[i][index]++;
                s.c[j][index]++;
                s.l[i / 3][j / 3][index]++;
            }
        }
        dfs(board, 0, s, list);
    }

    public static void dfs(char[][] board, int index, States s, List<int[]> list) {
        if (index >= list.size()) {
            s.valid = true;
            return;
        }
        var temp = list.get(index);
        int i = temp[0], j = temp[1];
        for (var k = 0; !s.valid && k < 9; k++) {
            if (s.r[i][k] > 0 || s.c[j][k] > 0 || s.l[i / 3][j / 3][k] > 0) {
                continue;
            }
            s.r[i][k]++;
            s.c[j][k]++;
            s.l[i / 3][j / 3][k]++;
            board[i][j] = (char) (k + '0' + 1);
            dfs(board, index + 1, s, list);
            s.r[i][k]--;
            s.c[j][k]--;
            s.l[i / 3][j / 3][k]--;
        }
    }

    /**
     * K个一组翻转链表
     *
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        var rlt = new ListNode(0, head);
        var prev = rlt;
        while (Objects.nonNull(head)) {
            var last = prev;
            for (var i = 0; i < k; i++) {
                last = last.next;
                if (Objects.isNull(last)) {
                    return rlt.next;
                }
            }
            var temp = reserve(head, last, k);
            prev.next = temp[0];
            prev = temp[1];
            head = prev.next;
        }
        return rlt.next;
    }

    public static ListNode[] reserve(ListNode head, ListNode last, int k) {
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
     * 赎金信
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public static boolean canConstruct(String ransomNote, String magazine) {
        var nums = new int[128];
        for (var c : ransomNote.toCharArray()) {
            nums[c]++;
        }
        for (var c : magazine.toCharArray()) {
            nums[c]--;
        }
        for (var n : nums) {
            if (n > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 重复的子字符串
     *
     * @param s
     * @return
     */
    public static boolean repeatedSubstringPattern(String s) {
        var n = s.length();
        for (var i = 1; i < n; i++) {
            if (n % i != 0) {
                continue;
            }
            var j = 0;
            var k = i;
            while (k + i <= n) {
                var t1 = s.substring(j, k);
                j = k;
                k += i;
                var t2 = s.substring(j, k);
                if (!t1.equals(t2)) {
                    k -= i;
                    break;
                }
            }
            if (k >= n) {
                return true;
            }
        }
        return false;
    }

    /**
     * 唯一摩尔斯密码词
     *
     * @param words
     * @return
     */
    public static int uniqueMorseRepresentations(String[] words) {
        var nums = new String[]{".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        var map = new ConcurrentHashMap<Character, String>() {{
            for (var i = 0; i < 26; i++) {
                put((char) (i + 'a'), nums[i]);
            }
        }};
        var set = new HashSet<String>();
        for (var word : words) {
            var builder = new StringBuilder();
            for (var c : word.toCharArray()) {
                builder.append(map.get(c));
            }
            set.add(builder.toString());
        }
        return set.size();
    }

    /**
     * 无重复字符串的排列组合
     *
     * @param s
     * @return
     */
    public static String[] permutation(String s) {
        var rlt = new ArrayList<String>();
        dfs(s.toCharArray(), rlt, 0);
        return rlt.toArray(new String[rlt.size()]);
    }

    public static void dfs(char[] cs, List<String> rlt, int index) {
        if (index >= cs.length) {
            var builder = new StringBuilder();
            for (var c : cs) {
                builder.append(c);
            }
            rlt.add(builder.toString());
            return;
        }
        for (var i = index; i < cs.length; i++) {
            reserve(cs, i, index);
            dfs(cs, rlt, index + 1);
            reserve(cs, i, index);
        }
    }

    public static void reserve(char[] cs, int l, int r) {
        if (l == r) {
            return;
        }
        cs[l] ^= cs[r];
        cs[r] ^= cs[l];
        cs[l] ^= cs[r];
    }

    /**
     * 能否连接形成数组
     *
     * @param arr
     * @param pieces
     * @return
     */
    public static boolean canFormArray(int[] arr, int[][] pieces) {
        var map = new ConcurrentHashMap<Integer, Integer>() {{
            for (var i = 0; i < pieces.length; i++) {
                put(pieces[i][0], i);
            }
        }};
        var i = 0;
        while (i < arr.length) {
            if (!map.containsKey(arr[i])) {
                return false;
            }
            var index = map.get(arr[i]);
            for (var j = 0; j < pieces[index].length; j++) {
                if (pieces[index][j] != arr[i++]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 全排列II
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> permuteUnique(int[] nums) {
        var rlt = new ArrayList<List<Integer>>();
        dfs(nums, rlt, 0);
        return rlt;
    }

    public static void dfs(int[] nums, List<List<Integer>> rlt, int index) {
        if (index >= nums.length) {
            var path = new ArrayList<Integer>();
            for (var n : nums) {
                path.add(n);
            }
            rlt.add(path);
            return;
        }
        for (var i = index; i < nums.length; i++) {
            reserve(nums, i, index);
            dfs(nums, rlt, index + 1);
            reserve(nums, i, index);
        }
    }

    public static void reserve(int[] nums, int l, int r) {
        if (l == r) {
            return;
        }
        nums[l] ^= nums[r];
        nums[r] ^= nums[l];
        nums[l] ^= nums[r];
    }

    /**
     * 全排列
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute(int[] nums) {
        var rlt = new ArrayList<List<Integer>>();
        dfs(nums, 0, rlt);
        return rlt;
    }

    public static void dfs(int[] nums, int index, List<List<Integer>> rlt) {
        if (index >= nums.length) {
            var path = new ArrayList<Integer>();
            for (var n : nums) {
                path.add(n);
            }
            rlt.add(path);
            return;
        }
        for (var i = index; i < nums.length; i++) {
            reserve(i, index, nums);
            dfs(nums, index + 1, rlt);
            reserve(i, index, nums);
        }
    }

    public static void reserve(int l, int r, int[] nums) {
        if (l == r) {
            return;
        }
        nums[l] ^= nums[r];
        nums[r] ^= nums[l];
        nums[l] ^= nums[r];
    }

    /**
     * 旋转数字
     *
     * @param n
     * @return
     */
    public static int rotatedDigits(int n) {
        var rlt = 0;
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
            if (t1 < 0 || valid) {
                rlt++;
            }
        }
        return rlt;
    }

    /**
     * 对角线遍历
     *
     * @param mat
     * @return
     */
    public static int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length, n = mat[0].length, index = 0;
        var rlt = new int[m * n];
        for (var i = 0; i < m + n - 1; i++) {
            if ((i & 1) == 0) {
                var r = i < m ? i : m - 1;
                var c = i < m ? 0 : i - m + 1;
                while (r >= 0 && c < n) {
                    rlt[index++] = mat[r--][c++];
                }
                continue;
            }
            var r = i < n ? 0 : i - n + 1;
            var c = i < n ? i : n - 1;
            while (r < m && c >= 0) {
                rlt[index++] = mat[r++][c--];
            }
        }
        return rlt;
    }

    /**
     * 反转链表
     *
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
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
     * 数组中的第K个最大元素
     *
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest(int[] nums, int k) {
        sort(nums, 0, nums.length - 1);
        return nums[nums.length - k];
    }

    public static void sort(int[] nums, int l, int r) {
        if (l < r) {
            var temp = partition(nums, l, r);
            sort(nums, l, temp);
            sort(nums, temp + 1, r);
        }
    }

    public static int partition(int[] nums, int l, int r) {
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
     * 二叉树的锯齿形层序遍历
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
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
            if ((i & 1) == 1) {
                Collections.reverse(path);
            }
            i++;
            rlt.add(path);
        }
        return rlt;
    }

    /**
     * 二叉树的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (Objects.isNull(root) || root == p || root == q) {
            return root;
        }
        var l = lowestCommonAncestor(root.left, p, q);
        var r = lowestCommonAncestor(root.right, p, q);
        if (Objects.isNull(l)) {
            return r;
        } else if (Objects.isNull(r)) {
            return l;
        } else {
            return root;
        }
    }

    static class LRUCache {
        class Node {
            int key, value;
            Node prev, next;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        Map<Integer, Node> map = new ConcurrentHashMap<>();
        int size, capacity;
        Node head, last;

        public LRUCache(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            var n = map.get(key);
            if (Objects.isNull(n)) {
                return -1;
            }
            moveToHead(n);
            return n.value;
        }

        public void moveToHead(Node n) {
            if (Objects.isNull(n.prev)) {
                return;
            }
            n.prev.next = n.next;
            if (Objects.nonNull(n.next)) {
                n.next.prev = n.prev;
            }
            if (n == last) {
                last.prev.next = null;
                last = last.prev;
            }
            n.next = head;
            head.prev = n;
            head = n;
            head.prev = null;
        }

        public void put(int key, int value) {
            var n = map.get(key);
            if (Objects.isNull(n)) {
                n = new Node(key, value);
                size++;
                if (Objects.isNull(head)) {
                    head = n;
                    last = head;
                    map.put(key, n);
                } else {
                    n.next = head;
                    head.prev = n;
                    head = n;
                    head.prev = null;
                    map.put(key, n);
                    if (size > capacity) {
                        map.remove(last.key);
                        last.prev.next = null;
                        last = last.prev;
                        size--;
                    }
                }
                return;
            }
            n.value = value;
            moveToHead(n);
        }
    }

    /**
     * 最大子序和
     *
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {
        int prev = 0, max = nums[0];
        for (var n : nums) {
            prev = Math.max(n, prev + n);
            max = Math.max(max, prev);
        }
        return max;
    }

    /**
     * 二叉树的右视图
     *
     * @param root
     * @return
     */
    public static List<Integer> rightSideView(TreeNode root) {
        var rlt = new ArrayList<Integer>();
        dfs(rlt, root, 0);
        return rlt;
    }

    public static void dfs(List<Integer> rlt, TreeNode n, int i) {
        if (Objects.nonNull(n)) {
            if (i == rlt.size()) {
                rlt.add(n.val);
            }
            dfs(rlt, n.right, i + 1);
            dfs(rlt, n.left, i + 1);
        }
    }

    /**
     * 从前序与中序遍历序列构造二叉树
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (Objects.isNull(preorder) || Objects.isNull(inorder)
                || preorder.length < 1 || inorder.length < 1) {
            return null;
        }
        var rlt = new TreeNode(preorder[0]);
        var n = preorder.length;
        for (var i = 0; i < n; i++) {
            if (preorder[0] == inorder[i]) {
                var pl = Arrays.copyOfRange(preorder, 1, i + 1);
                var pr = Arrays.copyOfRange(preorder, i + 1, n);
                var il = Arrays.copyOfRange(inorder, 0, i);
                var ir = Arrays.copyOfRange(inorder, i + 1, n);
                rlt.left = buildTree(pl, il);
                rlt.right = buildTree(pr, ir);
                break;
            }
        }
        return rlt;
    }

    /**
     * 从中序与后序遍历序列构造二叉树
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public static TreeNode buildTree2(int[] inorder, int[] postorder) {
        if (Objects.isNull(inorder) || Objects.isNull(postorder) ||
                inorder.length < 1 || postorder.length < 1) {
            return null;
        }
        var n = postorder.length;
        var rlt = new TreeNode(postorder[n - 1]);
        for (var i = 0; i < n; i++) {
            if (postorder[n - 1] == inorder[i]) {
                var pl = Arrays.copyOfRange(postorder, 0, i);
                var pr = Arrays.copyOfRange(postorder, i, n - 1);
                var il = Arrays.copyOfRange(inorder, 0, i);
                var ir = Arrays.copyOfRange(inorder, i + 1, n);
                rlt.left = buildTree2(il, pl);
                rlt.right = buildTree2(ir, pr);
                break;
            }
        }
        return rlt;
    }

    /**
     * 岛屿数量
     *
     * @param grid
     * @return
     */
    public static int numIslands(char[][] grid) {
        var rlt = 0;
        for (var i = 0; i < grid.length; i++) {
            for (var j = 0; j < grid[i].length; j++) {
                rlt += dfs(grid, i, j);
            }
        }
        return rlt;
    }

    public static int dfs(char[][] grid, int i, int j) {
        if ((i < 0 || i >= grid.length) || (j < 0 || j >= grid[0].length) || grid[i][j] == '0') {
            return 0;
        }
        grid[i][j] = '0';
        var t1 = new int[]{0, 0, 1, -1};
        var t2 = new int[]{1, -1, 0, 0};
        for (var k = 0; k < 4; k++) {
            dfs(grid, t1[k] + i, t2[k] + j);
        }
        return 1;
    }

    /**
     * 环形链表II
     *
     * @param head
     * @return
     */
    public static ListNode detectCycle(ListNode head) {
        var rlt = head;
        var set = new HashSet<ListNode>();
        while (Objects.nonNull(rlt)) {
            if (!set.add(rlt)) {
                return rlt;
            }
            rlt = rlt.next;
        }
        return rlt;
    }

    /**
     * 合并区间
     *
     * @param intervals
     * @return
     */
    public static int[][] merge(int[][] intervals) {
        if (Objects.isNull(intervals) || intervals.length < 1) {
            return new int[][]{{}, {}};
        }
        Arrays.sort(intervals, (x, y) -> x[0] - y[0]);
        var rlt = new LinkedList<int[]>();
        for (var i = 0; i < intervals.length; i++) {
            int l = intervals[i][0], r = intervals[i][1];
            if (rlt.size() == 0 || rlt.getLast()[1] < l) {
                rlt.add(new int[]{l, r});
                continue;
            }
            var temp = rlt.getLast();
            temp[1] = Math.max(temp[1], r);
        }
        return rlt.toArray(new int[rlt.size()][2]);
    }

    /**
     * 最长递增子序列
     *
     * @param nums
     * @return
     */
    public static int lengthOfLIS(int[] nums) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return 0;
        }
        int rlt = 1, n = nums.length;
        var dp = new int[n];
        dp[0] = 1;
        for (var i = 1; i < n; i++) {
            dp[i] = 1;
            for (var j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            rlt = Math.max(rlt, dp[i]);
        }
        return rlt;
    }

    /**
     * 重排链表
     *
     * @param head
     */
    public static void reorderList(ListNode head) {
        if (Objects.isNull(head)) {
            return;
        }
        var mid = getMid(head);
        var l1 = head;
        var l2 = mid.next;
        mid.next = null;
        l2 = reserve3(l2);
        mergeList(l1, l2);
    }

    public static ListNode getMid(ListNode n) {
        ListNode l1 = n, l2 = n;
        while (Objects.nonNull(l2.next) && Objects.nonNull(l2.next.next)) {
            l1 = l1.next;
            l2 = l2.next.next;
        }
        return l1;
    }

    public static void mergeList(ListNode l1, ListNode l2) {
        ListNode l1_ = null, l2_ = null;
        while (Objects.nonNull(l1) && Objects.nonNull(l2)) {
            l1_ = l1.next;
            l2_ = l2.next;
            l1.next = l2;
            l1 = l1_;
            l2.next = l1;
            l2 = l2_;
        }
    }

    public static ListNode reserve3(ListNode n) {
        ListNode l1 = null, l2 = n;
        while (Objects.nonNull(l2)) {
            var next = l2.next;
            l2.next = l1;
            l1 = l2;
            l2 = next;
        }
        return l1;
    }

    static int max = 0;

    /**
     * 二叉树的直径
     *
     * @param root
     * @return
     */
    public static int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return max;
    }

    public static int depth(TreeNode n) {
        if (Objects.isNull(n)) {
            return 0;
        }
        var l = depth(n.left);
        var r = depth(n.right);
        max = Math.max(l + r, max);
        return Math.max(l, r) + 1;
    }

    /**
     * 翻转二叉树
     *
     * @param root
     * @return
     */
    public static TreeNode invertTree(TreeNode root) {
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
     * 验证二叉搜索树
     *
     * @param root
     * @return
     */
    public static boolean isValidBST(TreeNode root) {
        if (Objects.isNull(root)) {
            return false;
        }
        var path = new ArrayList<Integer>();
        inOrder2(root, path);
        int i = 0, j = 1;
        for (; j < path.size(); i++, j++) {
            if (path.get(j) <= path.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static void inOrder2(TreeNode n, List<Integer> path) {
        if (Objects.isNull(n)) {
            return;
        }
        inOrder2(n.left, path);
        path.add(n.val);
        inOrder2(n.right, path);
    }

    /**
     * 最长重复子数组
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int findLength(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        var dp = new int[n + 1][m + 1];
        var rlt = 0;
        for (var i = n - 1; i >= 0; i--) {
            for (var j = m - 1; j >= 0; j--) {
                if (nums1[i] == nums2[j]) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                }
                rlt = Math.max(rlt, dp[i][j]);
            }
        }
        return rlt;
    }

    /**
     * 旋转图像
     *
     * @param matrix
     */
    public static void rotate(int[][] matrix) {
        var n = matrix.length;
        for (var i = 0; i < n >> 1; i++) {
            for (var j = 0; j < n; j++) {
                matrix[i][j] ^= matrix[n - i - 1][j];
                matrix[n - i - 1][j] ^= matrix[i][j];
                matrix[i][j] ^= matrix[n - i - 1][j];
            }
        }
        for (var i = 0; i < n; i++) {
            for (var j = 0; j < i; j++) {
                matrix[i][j] ^= matrix[j][i];
                matrix[j][i] ^= matrix[i][j];
                matrix[i][j] ^= matrix[j][i];
            }
        }
    }

    /**
     * 子集
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsets(int[] nums) {
        var rlt = new ArrayList<List<Integer>>();
        dfs(0, nums, new LinkedList<>(), rlt);
        return rlt;
    }

    public static void dfs(int i, int[] nums, LinkedList<Integer> path, List<List<Integer>> rlt) {
        if (i >= nums.length) {
            rlt.add(new ArrayList<>(path));
            return;
        }
        path.add(nums[i]);
        dfs(i + 1, nums, path, rlt);
        path.removeLast();
        dfs(i + 1, nums, path, rlt);
    }

    /**
     * 用两个栈实现队列
     */
    static class CQueue {
        LinkedList<Integer> l1 = new LinkedList<>();
        LinkedList<Integer> l2 = new LinkedList<>();
        int size = 0;

        public void appendTail(int value) {
            l1.push(value);
            size++;
        }

        public int deleteHead() {
            if (size == 0) {
                return -1;
            }
            try {
                if (!l2.isEmpty()) {
                    return l2.pop();
                }
                while (!l1.isEmpty()) {
                    l2.push(l1.pop());
                }
                return l2.pop();
            } finally {
                size--;
            }
        }
    }

    /**
     * 二叉搜索树中第K小的元素
     *
     * @param root
     * @param k
     * @return
     */
    public static int kthSmallest(TreeNode root, int k) {
        var rlt = new ArrayList<Integer>();
        inOrder3(root, rlt);
        return rlt.get(k - 1);
    }

    public static void inOrder3(TreeNode n, List<Integer> rlt) {
        if (Objects.isNull(n)) {
            return;
        }
        inOrder3(n.left, rlt);
        rlt.add(n.val);
        inOrder3(n.right, rlt);
    }

    /**
     * 最长公共子序列
     *
     * @param text1
     * @param text2
     * @return
     */
    public static int longestCommonSubsequence(String text1, String text2) {
        var dp = new int[text1.length() + 1][text2.length() + 1];
        for (var i = 1; i <= text1.length(); i++) {
            for (var j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    continue;
                }
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[text1.length()][text2.length()];
    }

    static int min = Integer.MAX_VALUE;

    /**
     * 零钱兑换
     *
     * @param coins
     * @param amount
     * @return
     */
    public static int coinChange(int[] coins, int amount) {
        var max = amount + 1;
        var dp = new int[max];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (var i = 0; i <= amount; i++) {
            for (var j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * 链表排序
     *
     * @param head
     * @return
     */
    public static ListNode sortList(ListNode head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }
        ListNode fast = head, slow = head, prev = head;
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            slow = slow.next;
            fast = fast.next.next;
        }
        var next = slow.next;
        slow.next = null;
        ListNode l1 = sortList(head), l2 = sortList(next);
        return merge(l1, l2);
    }

    public static ListNode merge(ListNode l1, ListNode l2) {
        ListNode rlt = new ListNode(0), temp = rlt;
        while (Objects.nonNull(l1) && Objects.nonNull(l2)) {
            if (l1.val < l2.val) {
                temp.next = l1;
                l1 = l1.next;
            } else {
                temp.next = l2;
                l2 = l2.next;
            }
            temp = temp.next;
        }
        if (Objects.nonNull(l1)) {
            temp.next = l1;
        }
        if (Objects.nonNull(l2)) {
            temp.next = l2;
        }
        return rlt.next;
    }

    /**
     * 打家劫舍
     *
     * @param nums
     * @return
     */
    public static int rob(int[] nums) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return 0;
        }
        int n = nums.length;
        if (n < 2) {
            return nums[0];
        }
        int v1 = nums[0], rlt = Math.max(nums[0], nums[1]);
        for (var i = 2; i < n; i++) {
            var temp = rlt;
            rlt = Math.max(rlt, v1 + nums[i]);
            v1 = temp;
        }
        return rlt;
    }

    /**
     * 排序数组
     *
     * @param nums
     * @return
     */
    public static int[] sortArray(int[] nums) {
        sort2(nums, 0, nums.length - 1);
        return nums;
    }

    public static void sort2(int[] nums, int l, int r) {
        if (l < r) {
            var temp = partition2(nums, l, r);
            sort2(nums, l, temp);
            sort2(nums, temp + 1, r);
        }
    }

    public static int partition2(int[] nums, int l, int r) {
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
     * 字符串解码
     *
     * @param s
     * @return
     */
    public static String decodeString(String s) {
        var rlt = new StringBuilder();

        return rlt.toString();
    }

    static boolean valid;

    /**
     * 单词搜索
     *
     * @param board
     * @param word
     * @return
     */
    public static boolean exist(char[][] board, String word) {
        for (var i = 0; i < board.length; i++) {
            for (var j = 0; j < board[i].length; j++) {
                dfs(board, word, i, j, 0);
            }
        }
        return valid;
    }

    public static void dfs(char[][] board, String word, int i, int j, int index) {
        if (board[i][j] != word.charAt(index)) {
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
        for (var k = 0; k < 4; k++) {
            if (valid) {
                return;
            }
            if ((i + t1[k] < 0 || i + t1[k] >= board.length) ||
                    (j + t2[k] < 0 || j + t2[k] >= board[0].length)) {
                continue;
            }
            dfs(board, word, i + t1[k], j + t2[k], index + 1);
        }
        board[i][j] = temp;
    }

    /**
     * 二叉树的所有路径
     *
     * @param root
     * @return
     */
    public static List<String> binaryTreePaths(TreeNode root) {
        var rlt = new ArrayList<String>();
        binaryTreePaths(root, rlt, "");
        return rlt;
    }

    public static void binaryTreePaths(TreeNode n, List<String> rlt, String path) {
        if (Objects.nonNull(n)) {
            StringBuilder builder = new StringBuilder(path);
            builder.append(n.val);
            if (Objects.isNull(n.left) && Objects.isNull(n.right)) {
                rlt.add(builder.toString());
            } else {
                builder.append("->");
                binaryTreePaths(n.left, rlt, builder.toString());
                binaryTreePaths(n.right, rlt, builder.toString());
            }
        }
    }

    /**
     * 字符串的排列
     *
     * @param s
     * @return
     */
    public static String[] permutation2(String s) {
        var rlt = new HashSet<String>();
        dfs(s.toCharArray(), 0, rlt);
        return rlt.toArray(new String[rlt.size()]);
    }

    public static void dfs(char[] cs, int index, HashSet<String> rlt) {
        if (index >= cs.length) {
            var path = new StringBuilder();
            for (var c : cs) {
                path.append(c);
            }
            rlt.add(path.toString());
            return;
        }
        for (var i = index; i < cs.length; i++) {
            reserve3(cs, i, index);
            dfs(cs, index + 1, rlt);
            reserve3(cs, i, index);
        }
    }

    public static void reserve3(char[] cs, int i, int j) {
        if (i == j) {
            return;
        }
        cs[i] ^= cs[j];
        cs[j] ^= cs[i];
        cs[i] ^= cs[j];
    }

    /**
     * 二叉搜索树中的插入操作
     *
     * @param root
     * @param val
     * @return
     */
    public static TreeNode insertIntoBST(TreeNode root, int val) {
        var n = new TreeNode(val);
        if (Objects.isNull(root)) {
            return n;
        }
        insert(root, null, n);
        return root;
    }

    public static void insert(TreeNode n1, TreeNode n2, TreeNode n3) {
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

    static TreeNode firstNode = null, secondNode = null, preNode = new TreeNode(Integer.MIN_VALUE);

    /**
     * 恢复二叉搜索树
     *
     * @param root
     */
    public static void recoverTree(TreeNode root) {
        inOrder(root);
        var temp = firstNode.val;
        firstNode.val = secondNode.val;
        secondNode.val = temp;
    }

    private static void inOrder(TreeNode root) {
        if (Objects.isNull(root)) {
            return;
        }
        inOrder(root.left);
        if (Objects.isNull(firstNode) && preNode.val > root.val) {
            firstNode = preNode;
        }
        if (Objects.nonNull(firstNode) && preNode.val > root.val) {
            secondNode = root;
        }
        preNode = root;
        inOrder(root.right);
    }

    /**
     * 连续的子数组和
     *
     * @param nums
     * @param k
     * @return
     */
    public static boolean checkSubarraySum(int[] nums, int k) {
        if (nums.length < 2) {
            return false;
        }
        var map = new ConcurrentHashMap<Integer, Integer>() {{
            put(0, -1);
        }};
        var temp = 0;
        for (var i = 0; i < nums.length; i++) {
            temp = (temp + nums[i]) % k;
            if (map.containsKey(temp)) {
                var prev = map.get(temp);
                if (i - prev >= 2) {
                    return true;
                }
                continue;
            }
            map.put(temp, i);
        }
        return false;
    }

    /**
     * 寻找重复数
     *
     * @param nums
     * @return
     */
    public static int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (fast != slow);
        slow = 0;
        while (fast != slow) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    /**
     * 扑克牌中的顺子
     *
     * @param nums
     * @return
     */
    public static boolean isStraight(int[] nums) {
        var set = new HashSet<Integer>();
        int max = 0, min = 14;
        for (var i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                continue;
            }
            max = Math.max(nums[i], max);
            min = Math.min(nums[i], min);
            if (!set.add(nums[i])) {
                return false;
            }
        }
        return max - min < 5;
    }

    /**
     * 最大交换
     *
     * @param num
     * @return
     */
    public static int maximumSwap(int num) {
        var cs = String.valueOf(num).toCharArray();
        int n = cs.length, i = -1, j = -1, max = n - 1;
        for (var k = n - 1; k >= 0; k--) {
            if (cs[k] > cs[max]) {
                max = k;
            } else if (cs[k] < cs[max]) {
                i = k;
                j = max;
            }
        }
        if (i >= 0) {
            cs[i] ^= cs[j];
            cs[j] ^= cs[i];
            cs[i] ^= cs[j];
            return Integer.parseInt(new String(cs));
        }
        return num;
    }

    /**
     * 奇偶链表
     *
     * @param head
     * @return
     */
    public static ListNode oddEvenList(ListNode head) {
        if (Objects.isNull(head)) {
            return null;
        }
        ListNode odd = head, even1 = head.next, even2 = head.next;
        while (Objects.nonNull(even1) && Objects.nonNull(even1.next)) {
            odd.next = even1.next;
            odd = odd.next;
            even1.next = odd.next;
            even1 = even1.next;
        }
        odd.next = even2;
        return head;
    }

    /**
     * 移掉 K 位数字
     *
     * @param num
     * @param k
     * @return
     */
    public static String removeKdigits(String num, int k) {
        var queue = new LinkedList<Character>();
        for (var i = 0; i < num.length(); i++) {
            var c = num.charAt(i);
            while (!queue.isEmpty() && k > 0 && queue.peekLast() > c) {
                queue.pollLast();
                k--;
            }
            queue.offerLast(c);
        }
        for (var i = 0; i < k; i++) {
            queue.pollLast();
        }
        var buidler = new StringBuilder();
        var flag = true;
        while (!queue.isEmpty()) {
            var c = queue.pollFirst();
            if (flag && c == '0') {
                continue;
            }
            flag = false;
            buidler.append(c);
        }
        return buidler.length() < 1 ? "0" : buidler.toString();
    }

    /**
     * 分发糖果
     *
     * @param ratings
     * @return
     */
    public static int candy(int[] ratings) {
        var n = ratings.length;
        var nums = new int[n];
        for (var i = 0; i < n; i++) {
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                nums[i] = nums[i - 1] + 1;
                continue;
            }
            nums[i] = 1;
        }
        int right = 0, rlt = 0;
        for (var i = n - 1; i >= 0; i--) {
            if (i < n - 1 && ratings[i] > ratings[i + 1]) {
                right++;
            } else {
                right = 1;
            }
            rlt += Math.max(right, nums[i]);
        }
        return rlt;
    }

    /**
     * 矩阵中的最长递增路径
     *
     * @param matrix
     * @return
     */
    public static int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        var path = new int[matrix.length][matrix[0].length];
        var rlt = 0;
        for (var i = 0; i < m; ++i) {
            for (var j = 0; j < n; ++j) {
                rlt = Math.max(rlt, dfs(matrix, i, j, path));
            }
        }
        return rlt;
    }

    public static int dfs(int[][] matrix, int i, int j, int[][] path) {
        if (path[i][j] != 0) {
            return path[i][j];
        }
        path[i][j]++;
        var t1 = new int[]{0, 0, 1, -1};
        var t2 = new int[]{1, -1, 0, 0};
        for (var k = 0; k < 4; k++) {
            if (i + t1[k] >= 0 && j + t2[k] >= 0
                    && i + t1[k] < matrix.length && j + t2[k] < matrix[0].length
                    && matrix[i + t1[k]][j + t2[k]] > matrix[i][j]) {
                path[i][j] = Math.max(path[i][j], dfs(matrix, i + t1[k], j + t2[k], path) + 1);
            }
        }
        return path[i][j];
    }

    /**
     * 分隔链表
     *
     * @param head
     * @param x
     * @return
     */
    public static ListNode partition(ListNode head, int x) {
        var small = new ListNode(0);
        var smallHead = small;
        var large = new ListNode(0);
        var largeHead = large;
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
        small.next = largeHead.next;
        return smallHead.next;
    }

    /**
     * 单词拆分
     *
     * @param s
     * @param wordDict
     * @return
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        var set = new HashSet<String>(wordDict);
        var dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (var i = 1; i <= s.length(); i++) {
            for (var j = 0; j < i; j++) {
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    /**
     * 下一个更大元素 II
     *
     * @param nums
     * @return
     */
    public static int[] nextGreaterElements(int[] nums) {
        var n = nums.length;
        var rlt = new int[n];
        Arrays.fill(rlt, -1);
        var stack = new LinkedList<Integer>();
        for (var i = 0; i < n << 1; i++) {
            while (!stack.isEmpty() && nums[i % n] > nums[stack.peek()]) {
                rlt[stack.pop()] = nums[i % n];
            }
            stack.push(i % n);
        }
        return rlt;
    }

    /**
     * 两个数组的交集
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersection(int[] nums1, int[] nums2) {
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
        var rlt = new int[s2.size()];
        var i = 0;
        for (var n : s2) {
            rlt[i++] = n;
        }
        return rlt;
    }

    /**
     * 柱状图中最大的矩形
     *
     * @param heights
     * @return
     */
    public static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        var l = new int[n];
        var r = new int[n];
        Arrays.fill(r, n);
        var stack = new LinkedList<Integer>();
        for (var i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                r[stack.peek()] = i;
                stack.pop();
            }
            l[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        var rlt = 0;
        for (var i = 0; i < n; i++) {
            rlt = Math.max(rlt, (r[i] - l[i] - 1) * heights[i]);
        }
        return rlt;
    }

    /**
     * 数组中重复的数字
     *
     * @param nums
     * @return
     */
    public static int findRepeatNumber(int[] nums) {
        var rlt = 0;
        Arrays.sort(nums);
        for (var i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                rlt = nums[i];
                break;
            }
        }
        return rlt;
    }

    /**
     * 搜索二维矩阵
     *
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int low = 0, high = m * n - 1;
        while (low <= high) {
            var mid = ((high - low) >> 1) + low;
            var x = matrix[mid / n][mid % n];
            if (x < target) {
                low = mid + 1;
            } else if (x > target) {
                high = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 左叶子之和
     *
     * @param root
     * @return
     */
    public static int sumOfLeftLeaves(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        return dfs(root);
    }

    public static int dfs(TreeNode n) {
        var rlt = 0;
        if (Objects.nonNull(n.left)) {
            rlt += isLeafNode(n.left) ? n.left.val : dfs(n.left);
        }
        if (Objects.nonNull(n.right) && !isLeafNode(n.right)) {
            rlt += dfs(n.right);
        }
        return rlt;
    }

    public static boolean isLeafNode(TreeNode n) {
        return Objects.isNull(n.left) && Objects.isNull(n.right);
    }

    /**
     * 交错字符串
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), t = s3.length();
        if (n + m != t) {
            return false;
        }
        var dp = new boolean[m + 1];
        dp[0] = true;
        for (var i = 0; i <= n; i++) {
            for (var j = 0; j <= m; j++) {
                var p = i + j - 1;
                if (i > 0) {
                    dp[j] = dp[j] && s1.charAt(i - 1) == s3.charAt(p);
                }
                if (j > 0) {
                    dp[j] = dp[j] || (dp[j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }
        }
        return dp[m];
    }

    /**
     * 缺失的第一个正数
     *
     * @param nums
     * @return
     */
    public static int firstMissingPositive(int[] nums) {
        var n = nums.length;
        for (var i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                var temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
        }
        for (var i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }

    /**
     * 最小路径之和
     *
     * @param grid
     * @return
     */
    public static int minPathSum(int[][] grid) {
        if (Objects.isNull(grid) || grid.length < 1) {
            return 0;
        }
        int m = grid.length, n = grid[0].length;
        var dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (var i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (var j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        for (var i = 1; i < m; i++) {
            for (var j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 零钱兑换 II
     *
     * @param amount
     * @param coins
     * @return
     */
    public static int change(int amount, int[] coins) {
        var dp = new int[amount + 1];
        dp[0] = 1;
        for (var coin : coins) {
            for (var i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    /**
     * 最小栈
     */
    static class MinStack {
        LinkedList<Integer> stack1, stack2;

        public MinStack() {
            stack1 = new LinkedList<>();
            stack2 = new LinkedList<>();
            stack2.push(Integer.MAX_VALUE);
        }

        public void push(int x) {
            stack1.push(x);
            stack2.push(Math.min(stack2.peek(), x));
        }

        public void pop() {
            stack1.pop();
            stack2.pop();
        }

        public int top() {
            return stack1.peek();
        }

        public int getMin() {
            return stack2.peek();
        }
    }

    public static void main(String[] args) {
        System.out.println(sumOfLeftLeaves(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)))));
        //System.out.println(findRepeatNumber(new int[]{2, 3, 1, 0, 2, 5, 3}));
        //System.out.println(largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
        //Arrays.stream(intersection(new int[]{1, 2, 3, 4}, new int[]{1, 3})).forEach(System.out::println);
        //Arrays.stream(nextGreaterElements(new int[]{1, 2, 3, 4, 3})).forEach(System.out::println);

//        System.out.println(wordBreak("leetcode", new ArrayList<>() {{
//            add("leet");
//            add("code");
//        }}));
//        System.out.println(wordBreak("applepenapple", new ArrayList<>() {{
//            add("apple");
//            add("pen");
//        }}));
//        System.out.println(wordBreak("applepenapple", new ArrayList<>() {{
//            add("apple");
//            add("pen");
//            add("apple");
//        }}));
//        System.out.println(wordBreak("catsandog", new ArrayList<>() {{
//            add("cats");
//            add("dog");
//            add("sand");
//            add("and");
//            add("cat");
//        }}));
//        System.out.println(partition(new ListNode(1, new ListNode(4, new ListNode(3,
//                new ListNode(2, new ListNode(5, new ListNode(2)))))), 3));
//        System.out.println(longestIncreasingPath(new int[][]{
//                {3, 4, 5},
//                {3, 2, 6},
//                {2, 2, 1}}));
//        System.out.println(longestIncreasingPath(new int[][]{
//                {9, 9, 4},
//                {6, 6, 8},
//                {2, 1, 1}}));
        //System.out.println(candy(new int[]{0, 1, 2}));
        //System.out.println(candy(new int[]{1, 0, 2}));
        //System.out.println(removeKdigits("1432219", 3));
        //System.out.println(oddEvenList(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))))));
//        System.out.println(maximumSwap(9937));
//        System.out.println(maximumSwap(1));
        //System.out.println(isStraight(new int[]{0, 0, 1, 2, 5}));
        //System.out.println(findDuplicate(new int[]{1, 2, 3, 4, 4}));
        //System.out.println(checkSubarraySum(new int[]{23, 2, 4, 6, 7}, 9));
//        System.out.println(checkSubarraySum(new int[]{23, 2, 6, 4, 7}, 6));
//        System.out.println(checkSubarraySum(new int[]{23, 2, 6, 4, 7}, 13));
//        System.out.println(checkSubarraySum(new int[]{23, 2, 4, 6, 6}, 7));
        //System.out.println(checkSubarraySum(new int[]{5, 0, 0, 0}, 3));
//        var n = new TreeNode(3, new TreeNode(1), new TreeNode(4, new TreeNode(2), null));
//        recoverTree(n);

        //System.out.println(insertIntoBST(new TreeNode(4, new TreeNode(2, new TreeNode(1), new TreeNode(3)), new TreeNode(7)), 5));
//        Arrays.stream(permutation2("abc")).forEach(System.out::println);
//        System.out.println();
//        Arrays.stream(permutation2("aab")).forEach(System.out::println);
        //System.out.println(binaryTreePaths(new TreeNode(1, new TreeNode(2, null, new TreeNode(5)), new TreeNode(3))));
//        var temp = new char[][]{
//                {'A', 'B', 'C', 'E'},
//                {'S', 'F', 'C', 'S'},
//                {'A', 'D', 'E', 'E'}};
//        System.out.println(exist(temp, "ABCCED"));
        //System.out.println(exist(temp, "SEE"));
//        System.out.println(exist(temp, "ABCB"));
//        temp = new char[][]{
//                {'A'}};
//        System.out.println(exist(temp, "A"));
//        System.out.println(rob(new int[]{1, 2, 3, 1}));
//        System.out.println(rob(new int[]{2, 7, 9, 3, 1}));
        //System.out.println(sortList(new ListNode(4, new ListNode(2, new ListNode(1, new ListNode(3))))));
        //System.out.println(coinChange(new int[]{1, 2, 5}, 11));
        //System.out.println(coinChange(new int[]{1, 2, 5}, 100));
        //System.out.println(coinChange(new int[]{1}, 0));
//        System.out.println(longestCommonSubsequence("abcde", "ace"));
//        System.out.println(longestCommonSubsequence("abc", "abc"));
//        System.out.println(longestCommonSubsequence("abc", "def"));
        //System.out.println(kthSmallest(new TreeNode(3, new TreeNode(1, null, new TreeNode(2)), new TreeNode(4)), 1));
//        var cq = new CQueue();
//        cq.appendTail(10);
//        cq.appendTail(12);
//        System.out.println(cq.deleteHead());
//        System.out.println(cq.deleteHead());
//        System.out.println(cq.deleteHead());
        //System.out.println(subsets(new int[]{1, 2, 3}));
//        var nums = new int[][]{
//                {5, 1, 9, 11},
//                {2, 4, 8, 10},
//                {13, 3, 6, 7},
//                {15, 14, 12, 16}
//        };
//        rotate(nums);
//        Arrays.stream(nums).forEach(x -> {
//            for (var n : x) {
//                System.out.print(n + " ");
//            }
//            System.out.println();
//        });
//        System.out.println(findLength(new int[]{0, 1, 1, 1, 1}, new int[]{1, 0, 1, 0, 1}));
//        System.out.println(findLength(new int[]{0, 0, 0, 0, 1}, new int[]{1, 0, 0, 0, 0}));
//        System.out.println(findLength(new int[]{1, 2, 3, 2, 1}, new int[]{3, 2, 1, 4, 7}));
//        System.out.println(findLength(new int[]{0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}));
//        System.out.println(diameterOfBinaryTree(new TreeNode(1, new TreeNode(2), null)));
//        System.out.println(diameterOfBinaryTree(new TreeNode(1, new TreeNode(2, new TreeNode(4), new TreeNode(5)), new TreeNode(3))));

//        var n = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
//        reorderList(n);
//        System.out.println(n);
        //System.out.println(lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        //Arrays.stream(merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})).forEach(x -> Arrays.stream(x).forEach(x2 -> System.out.println(x2)));
//        ListNode l1 = new ListNode(3);
//        ListNode l2 = new ListNode(2);
//        ListNode l3 = new ListNode(0);
//        ListNode l4 = new ListNode(-4);
//        l1.next = l2;
//        l2.next = l3;
//        l3.next = l4;
//        l4.next = l2;
//        System.out.println(detectCycle(l1).val);
//        var l5 = new ListNode(1);
//        l5.next = l5;
//        System.out.println(detectCycle(l5) == null);

//        var grid = new char[][]{
//                {'1', '1', '1', '1', '0'},
//                {'1', '1', '0', '1', '0'},
//                {'1', '1', '0', '0', '0'},
//                {'0', '0', '0', '0', '0'},
//        };
//        System.out.println(numIslands(grid));
//        grid = new char[][]{
//                {'1', '1', '0', '0', '0'},
//                {'1', '1', '0', '0', '0'},
//                {'0', '0', '1', '0', '0'},
//                {'0', '0', '0', '1', '1'},
//        };
//        System.out.println(numIslands(grid));

//        System.out.println(rightSideView(new TreeNode(1, null, new TreeNode(2))));
//        System.out.println(rightSideView(new TreeNode(1, new TreeNode(2), null)));
//        System.out.println(rightSideView(new TreeNode(1, null, new TreeNode(3))));
//        System.out.println(rightSideView(new TreeNode(1, new TreeNode(2, null, new TreeNode(5)), new TreeNode(3, null, new TreeNode(4)))));
//        Assert.assertEquals(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}), 6);
//        Assert.assertEquals(maxSubArray(new int[]{1}), 1);
//        Assert.assertEquals(maxSubArray(new int[]{5, 4, -1, 7, 8}), 23);

//        var lRUCache = new LRUCache(2);
//        lRUCache.put(1, 1); // 缓存是 {1=1}
//        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
//        Assert.assertEquals(lRUCache.get(1), 1);    // 返回 1
//        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
//        Assert.assertEquals(lRUCache.get(2), -1);    // 返回 -1 (未找到)
//        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
//        Assert.assertEquals(lRUCache.get(1), -1);    // 返回 -1 (未找到)
//        Assert.assertEquals(lRUCache.get(3), 3);    // 返回 3
//        Assert.assertEquals(lRUCache.get(4), 4);    // 返回 4
//        for (int i = 0; i < 100; i++) {
//            lRUCache.put(1, 1); // 缓存是 {1=1}
//        }
//        for (int i = 0; i < 102; i++) {
//            lRUCache.put(2, 2); // 缓存是 {1=1}
//        }

        //System.out.println(zigzagLevelOrder(new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)))));
//        System.out.println(findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
//        System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
//        Arrays.stream(findDiagonalOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}})).forEach(x -> System.out.print(x));
//        System.out.println();
//        Arrays.stream(findDiagonalOrder(new int[][]{{1, 2}, {3, 5}, {4, 6}, {7, 8}})).forEach(x -> System.out.print(x));

        //System.out.println((char) ('A' + 25));

//        System.out.println(rotatedDigits(10));
//        System.out.println(rotatedDigits(857));
//        System.out.println(permuteUnique(new int[]{0, 1, 0, 0, 9}));
//        System.out.println(permuteUnique(new int[]{3, 3, 0, 3}));
//        System.out.println(permuteUnique(new int[]{1, 1, 2}));
//        System.out.println(permuteUnique(new int[]{1, 2, 3}));
//        System.out.println(canFormArray(new int[]{91, 4, 64, 78}, new int[][]{{78}, {4, 64}, {91}}));
//        System.out.println(canFormArray(new int[]{91, 4, 64, 78}, new int[][]{{78}, {64, 4}, {91}}));
        //Arrays.stream(permutation("aab")).forEach(System.out::println);
        //Arrays.stream(permutation("qwe")).forEach(System.out::println);
//        System.out.println(uniqueMorseRepresentations(new String[]{"gin", "zen", "gig", "msg"}));
//        System.out.println(uniqueMorseRepresentations(new String[]{"a"}));
//        System.out.println(repeatedSubstringPattern("abcabcabcabc"));
//        System.out.println(repeatedSubstringPattern("abcabcabc"));
//        System.out.println(repeatedSubstringPattern("abab"));
//        System.out.println(repeatedSubstringPattern("abc"));
//        System.out.println(repeatedSubstringPattern("ababba"));
//        System.out.println(repeatedSubstringPattern("abcabc"));
//        System.out.println(canConstruct("a", "b"));
//        System.out.println(canConstruct("aa", "ab"));
//        System.out.println(canConstruct("aa", "aab"));
//        var temp = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
//        System.out.println(reverseKGroup(temp, 2));
//        var nums = new char[][]{
//                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
//                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
//                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
//                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
//                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
//                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
//                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
//                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
//                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
//        };
//        var nums = new char[][]{
//                {'.', '.', '4', '6', '7', '8', '9', '1', '2'},
//                {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
//                {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
//                {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
//                {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
//                {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
//                {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
//                {'2', '8', '7', '4', '1', '9', '6', '3', '5'},
//                {'.', '8', '5', '2', '8', '6', '1', '7', '9'}
//        };
//        var nums = new char[][]{
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '9', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'}
//        };
//        solveSudoku(nums);
//        for (var num : nums) {
//            for (var c : num) {
//                System.out.print(c + " ");
//            }
//            System.out.println();
//        }
        //System.out.println(isBalanced(new TreeNode(1, new TreeNode(2), new TreeNode(2, new TreeNode(3, null, new TreeNode(3)), null))));
//        System.out.println(convert("PAYPALISHIRING", 3));
//        System.out.println(convert("ABCDEFGHIJ", 4));
//        System.out.println(convert("PAYPALISHIRING", 4));
//        System.out.println(convert("A", 1));
//        System.out.println(convert("ABC", 2));
//        System.out.println(reverseStr("abcd", 3));
//        System.out.println(reverseStr("abcdefg", 2));
//        System.out.println(reverseStr("abcd", 2));
//        System.out.println(reverseStr("a", 2));

//        var temp = new char[]{'h', 'e', 'l', 'l', 'o'};
//        reverseString(temp);
//        System.out.println(new String(temp));
        //System.out.println(reverseVowels("leetcode"));
        //System.out.println(reverseVowels("race car"));
//        var nums = new int[]{0, 1, 0, 3, 12};
//        moveZeroes(nums);
//        Arrays.stream(nums).forEach(System.out::println);
//        System.out.println(findTheDifference("abcd", "acbde"));
//        System.out.println(findTheDifference("abcd", "abxcd"));
//        System.out.println(rotateRight(new ListNode(0, new ListNode(1, new ListNode(2))), 4));
//        System.out.println(rotateRight(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2));
//        var nums = generateMatrix(3);
//        for (var n : nums) {
//            for (var i : n) {
//                System.out.print(i + " ");
//            }
//            System.out.println();
//        }

//        System.out.println(addTwoNumbers2(new ListNode(7, new ListNode(2, new ListNode(4, new ListNode(3)))),
//                new ListNode(5, new ListNode(6, new ListNode(4)))));
//        System.out.println(canThreePartsEqualSum(new int[]{0, 0, 0, 0}));
//        System.out.println(canThreePartsEqualSum(new int[]{1, 2, 3}));
//        System.out.println(canThreePartsEqualSum(new int[]{0, 2, 1, -6, 6, -7, 9, 1, 2, 0, 1}));
//        System.out.println(canThreePartsEqualSum(new int[]{0, 2, 1, -6, 6, 7, 9, -1, 2, 0, 1}));
//        System.out.println(getMaximumGenerated(7));
//        System.out.println(getMaximumGenerated(3));
//        System.out.println(minCostClimbingStairs(new int[]{10, 15, 20}));
//        System.out.println(minCostClimbingStairs(new int[]{15, 20, 30, 1, 5}));
//        System.out.println(fourSumCount(new int[]{1}, new int[]{0}, new int[]{0}, new int[]{1}));
//        System.out.println(fourSumCount(new int[]{1, 2}, new int[]{-2, -1}, new int[]{-1, 2}, new int[]{0, 2}));
//        System.out.println(fourSumCount(new int[]{-1, 3}, new int[]{0, 0}, new int[]{0, 1}, new int[]{1, 2}));
//        System.out.println(tribonacci(4));
//        System.out.println(tribonacci(25));
        //System.out.println(isSubsequence("abc", "ahbgdc"));
//        System.out.println(isSubsequence("acb", "ahbgdc"));
        //Arrays.stream(countBits(5)).forEach(System.out::println);
//        System.out.println(getRow(3));
//        System.out.println(getRow(0));
//        System.out.println(getRow(1));
//        System.out.println(getRow(4));
        //System.out.println(generate(5));
//        System.out.println(generate(1));
//        System.out.println(spiralOrder2(new int[][]{
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9}}));
//        System.out.println(spiralOrder2(new int[][]{
//                {1, 2, 3, 4},
//                {5, 6, 7, 8},
//                {9, 10, 11, 12}}));
//        System.out.println(fib(2));
//        System.out.println(fib(3));
//        System.out.println(fib(4));
//        System.out.println(fib(5));
//        System.out.println(longestPalindrome2("abccccdd"));
//        System.out.println(longestPalindrome2("ccc"));
//        System.out.println(longestPalindrome2("a"));
        //System.out.println(isMatch("a", "a*"));
        // System.out.println(reverse(1));
        //System.out.println(reverse(-123));
//        System.out.println(reverse(123));
//        System.out.println(reverse(120));
//        System.out.println(reverse(0));
//        System.out.println(reverse(1534236469));
//        System.out.println(reverse(1056389759));


//        Arrays.stream(spiralOrder(new int[][]{
//                {1, 2, 3, 4, 5},
//                {6, 7, 8, 9, 10},
//                {11, 12, 13, 14, 15},
//                {16, 17, 18, 19, 20}})).forEach(x -> {
//            System.out.print(x + " ");
//        });
//        System.out.println();
//        Arrays.stream(spiralOrder(new int[][]{
//                {1, 2, 3, 4},
//                {5, 6, 7, 8},
//                {9, 10, 11, 12}})).forEach(x -> {
//            System.out.print(x + " ");
//        });
//        System.out.println();
//        Arrays.stream(spiralOrder(new int[][]{
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9}})).forEach(x -> {
//            System.out.print(x + " ");
//        });
//        "bbbaaaba"
//        "aaabbbba"
//        System.out.println(isIsomorphic("badc", "baba"));
//        System.out.println(isIsomorphic("egg", "add"));
//        System.out.println(canReach(new int[]{2, 0, 2}, 2));
//        System.out.println(canReach(new int[]{4, 2, 3, 0, 3, 1, 2}, 5));
//        for (var i = 1; i <= 10; i++) {
//            System.out.println(i + ":" + canWinNim(i));
//        }
//        System.out.println(thirdMax(new int[]{2, 2, 3, 1}));
//        System.out.println(thirdMax(new int[]{1, 1, 2}));
//        System.out.println(thirdMax(new int[]{1, 2, 2, 5, 3, 5}));

//        var temp = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
//        var rlt = levelOrder(temp);
//        for (List<Integer> treeNodes : rlt) {
//            for (Integer n : treeNodes) {
//                System.out.print(n + " ");
//            }
//            System.out.println();
//        }
        //System.out.println(isSymmetric(new TreeNode(1, new TreeNode(2), new TreeNode(2))));
        //System.out.println(maxDepth(new TreeNode(1, new TreeNode(2), new TreeNode(3))));
        //System.out.println(myPow(2, 2));
//        System.out.println(myPow(2.1D, 3));
        //System.out.println(myPow(2, 100));

        //Arrays.stream(merge(new int[]{1, 2}, new int[]{1, 3})).forEach(System.out::println);
//        System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
//        System.out.println(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));

//        var t1 = new ListNode(1);
//        var t2 = new ListNode(2);
//        var t3 = new ListNode(3);
//        t1.next = t2;
//        t2.next = t3;
//        t3.next = t2;
//        System.out.println(hasCycle(t1));
//        System.out.println(isSameTree(new TreeNode(1, new TreeNode(2), new TreeNode(3)),
//                new TreeNode(1, new TreeNode(2), new TreeNode(3))));
        //System.out.println(lengthOfLastWord("Hello World zhendema"));
        //Arrays.stream(plusOne(new int[]{9})).forEach(System.out::println);
//        Arrays.stream(plusOne(new int[]{1, 9, 9})).forEach(System.out::println);
//        Arrays.stream(plusOne(new int[]{1, 2, 3})).forEach(System.out::println);
        //System.out.println(addBinary("1010", "1011"));
//        System.out.println(deleteDuplicates(new ListNode(1, new ListNode(1,
//                new ListNode(2, new ListNode(2, new ListNode(3)))))));
//        System.out.println(deleteDuplicates(new ListNode(1, new ListNode(1,
//                new ListNode(1)))));
//        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
//        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1}));
        //System.out.println(singleNumber(new int[]{2, 1, 1, 3, 3}));
        //System.out.println(convertToTitle(28));
        //System.out.println(longestValidParentheses("(()"));
        //System.out.println(longestValidParentheses(")()())"));
//        System.out.println(longestValidParentheses(""));
//        System.out.println(longestValidParentheses("((("));
//        System.out.println(longestValidParentheses("()(())"));
//        System.out.println(longestValidParentheses("()()"));
//        System.out.println(mergeKLists(new ListNode[]{
//                new ListNode(1, new ListNode(2)),
//                new ListNode(1, new ListNode(3)),
//                new ListNode(1, new ListNode(4))}));
        //System.out.println(countConsistentStrings("ab", new String[]{"ad", "bd", "aaab", "baa", "badab"}));
        //System.out.println(majorityElement(new int[]{1, 1, 2, 2, 2}));
        //System.out.println(hammingWeight(0b11111111111111111111111111111101));
        //System.out.println(mySqrt(5));
        //System.out.println(generateParenthesis(2));
        //System.out.println(letterCombinations(""));
        //System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
//        System.out.println(multiply("2", "7"));
//        System.out.println(multiply("123", "456"));
//        System.out.println(multiply("12", "12"));
        //System.out.println(search2(new int[]{-1, 0, 3, 5, 9, 12}, 9));
        //System.out.println(swapPairs(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))))));
//        System.out.println(partitionArray(new int[]{3, 6, 1, 2, 5}, 2));
//        System.out.println(partitionArray(new int[]{1, 2, 3}, 1));
//        System.out.println(partitionArray(new int[]{2, 2, 4, 5}, 0));
//        System.out.println(fixedPoint(new int[]{-10, -5, 0, 3, 7}));
//        System.out.println(fixedPoint(new int[]{0, 2, 5, 8, 17}));
//        System.out.println(fixedPoint(new int[]{-10, -5, 3, 4, 7, 9}));
//        System.out.println(removeElement(new int[]{3, 2, 2, 3}, 3));
//        System.out.println(removeElement(new int[]{1}, 1));
//        var nums1 = new int[]{1, 2, 3, 0, 0, 0};
//        var nums2 = new int[]{2, 5, 6};
//        merge(nums1, 3, nums2, 3);
//        Arrays.stream(nums1).forEach(System.out::println);
//        System.out.println(longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
//        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
//        System.out.println(longestCommonPrefix(new String[]{"a"}));
//        System.out.println(maxAreaOfIsland(new int[][]{
//                {0, 0},
//                {1, 1}}));
//        System.out.println(isValidSudoku(new char[][]{
//                {'5', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'5', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.', '.'}
//        }));
//        System.out.println(isValidSudoku(new char[][]{
//                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
//                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
//                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
//
//                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
//                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
//                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
//
//                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
//                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
//                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
//        }));
//        var temp = new int[]{1, 1, 2};
//        System.out.println(removeDuplicates(temp));
//        Arrays.stream(temp).forEach(System.out::print);
//        System.out.println(divide(10, 3));
//        System.out.println(divide(7, -3));
//        System.out.println(threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
//        System.out.println(threeSumClosest(new int[]{0, 0, 0}, 1));
        //System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
//        System.out.println(mergeTwoLists(new ListNode(1, new ListNode(2, new ListNode(4))),
//                new ListNode(1, new ListNode(3, new ListNode(4)))));
//        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
//        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
//        System.out.println(search(new int[]{1}, 0));

//        var temp = new int[]{3, 2, 1};
//        nextPermutation(temp);
//        Arrays.stream(temp).forEach(System.out::println);

        //System.out.println(longestPalindrome("babab"));
//        System.out.println(climbStairs(2));
//        System.out.println(climbStairs(3));
//        System.out.println(strStr("sadbutsad", "sad"));
//        System.out.println(strStr("aaaaahello", "hell"));
//        System.out.println(strStr("leetcode", "leeto"));
        //System.out.println(longestSubarray(new int[]{0, 1, 1, 1, 0, 1, 1, 0, 1}));
        //System.out.println(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
        //System.out.println(fourSum(new int[]{2, 2, 2, 2, 2}, 8));
//        System.out.println(invalidTransactions(new String[]{"bob,689,1910,barcelona", "bob,832,1726,barcelona", "bob,820,596,bangkok"}));
//        System.out.println(invalidTransactions(new String[]{"alice,20,800,mtv", "alice,50,100,mtv", "alice,51,100,frankfurt"}));
        //System.out.println(partitionArr`ay(new int[]{3, 6, 1, 2, 5}, 2));
        //System.out.println(combinationSum2(new int[]{2, 5, 2, 1, 2}, 5));
        //System.out.println(combinationSum(new int[]{2, 3, 6, 7}, 7));
//        System.out.println(jump(new int[]{2, 3, 0, 1, 4}));
//        System.out.println(jump(new int[]{2, 3, 1, 1, 4}));
        //System.out.println(checkOnesSegment("111101"));
        //System.out.println(addStrings("123", "117"));
        //System.out.println(isValid("()"));
//        System.out.println(removeNthFromEnd(new ListNode(1), 1));
//        System.out.println(removeNthFromEnd(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2));
        //System.out.println(romanToInt("IV"));
        System.out.println(firstPalindrome(new String[]{"abc", "ea", "ada", "racecar", "cool"}));
        //Arrays.stream(twoSum(new int[]{2, 7, 11, 15}, 9)).forEach(System.out::println);
        //System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 2));
        //System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        System.out.println(addTwoNumbers(new ListNode(2, new ListNode(4, new ListNode(3))),
//                new ListNode(5, new ListNode(6, new ListNode(4)))));
        //System.out.println(lengthOfLongestSubstring("abcabcbb"));
        //System.out.println(longestPalindrome("aaaaabba"));
        //System.out.println(isPalindrome(121));
//        System.out.println(canJump(new int[]{2, 3, 1, 1, 4}));
//        System.out.println(canJump(new int[]{3, 2, 1, 0, 4}));
        //System.out.println(isPalindrome(new ListNode(1, new ListNode(2, new ListNode(1)))));
        //System.out.println(maxProfit2(new int[]{1, 2, 3, 4, 5}));

//        System.out.println(Integer.toBinaryString(4));
//        System.out.println(Integer.toBinaryString(2));
    }
}
