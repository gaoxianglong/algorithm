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

import lombok.Data;
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
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
     * <p>
     * 算法的时间复杂度应该为 O(log (m+n)) 。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        var nn = new int[nums1.length + nums2.length];
        var size = nn.length;
        for (int i = 0, j = 0; i < nn.length; i++) {
            nn[i] = i < nums1.length ? nums1[i] : nums2[j++];
        }
        Arrays.sort(nn);
        return (size & 1) != 0 ? nn[size >> 1] : ((nn[(size >> 1) - 1] + nn[size >> 1]) / 2d);
    }

    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        var map = new ConcurrentHashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode h = null, t = null;
        var c = 0;
        while (Objects.nonNull(l1) || Objects.nonNull(l2)) {
            var v1 = Objects.nonNull(l1) ? l1.val : 0;
            var v2 = Objects.nonNull(l2) ? l2.val : 0;
            var s = v1 + v2 + c;
            if (Objects.isNull(h)) {
                // 仅保留个位数
                h = new ListNode(s % 10);
                t = h;
            } else {
                t.next = new ListNode(s % 10);
                t = t.next;
            }
            // 取进位数
            c = s / 10;
            l1 = Objects.nonNull(l1) ? l1.next : null;
            l2 = Objects.nonNull(l2) ? l2.next : null;
        }
        if (c > 0) {
            t.next = new ListNode(c);
        }
        return h;
    }

    static class ListNode {
        int val;
        ListNode next;

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

    public static int lengthOfLongestSubstring(String s) {
        var max = 0;
        if (Objects.isNull(s) && s.length() < 1) {
            return max;
        }
        var set = new HashSet<Character>();
        for (var i = 0; i < s.length(); i++) {
            for (var j = i; j < s.length(); j++) {
                if (set.contains(s.charAt(j))) {
                    break;
                }
                set.add(s.charAt(j));
            }
            max = max < set.size() ? set.size() : max;
            set.clear();
        }
        return max;
    }

    public static boolean isValid(String s) {
        if ((s.length() & 1) != 0) {
            return false;
        }
        var map = new ConcurrentHashMap<Character, Character>() {
            {
                put('}', '{');
                put(')', '(');
                put(']', '[');
            }
        };
        var stack = new LinkedList<Character>();
        for (var i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                if (stack.peek() != map.get(s.charAt(i))) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(s.charAt(i));
            }
        }
        return stack.isEmpty();
    }

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
        return x == temp || x == temp / 10;
    }

    public static int removeElement(int[] nums, int val) {
        int l = 0, r = nums.length;
        while (l < r) {
            if (nums[l] == val) {
                nums[l] = nums[--r];
            } else {
                l++;
            }
        }
        return l;
    }

//    public static int searchInsert(int[] nums, int target) {
//        for (var i = 0; i < nums.length; i++) {
//            if (nums[i] == target || target < nums[i]) {
//                return i;
//            }
//        }
//        return nums.length;
//    }

    public static int searchInsert(int[] nums, int target) {
        var rlt = nums.length;
        if (Objects.isNull(nums) || nums.length < 1) {
            return rlt;
        }
        int l = 0, r = rlt - 1;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if (nums[mid] < target) {
                l = mid + 1;
            } else if (nums[mid] > target) {
                r = mid - 1;
                rlt = mid;
            } else {
                rlt = mid;
                break;
            }
        }
        return rlt;
    }

    public static int romanToInt(String s) {
        var map = new ConcurrentHashMap<Character, Integer>() {
            {
                put('I', 1);
                put('V', 5);
                put('X', 10);
                put('L', 50);
                put('C', 100);
                put('D', 500);
                put('M', 1000);
            }
        };
        int r = 0, l = s.length();
        for (var i = 0; i < l; i++) {
            var temp = map.get(s.charAt(i));
            // 如果当前数小于下一个则减法
            if (i < l - 1 && temp < map.get(s.charAt(i + 1))) {
                r -= temp;
            } else {
                r += temp;
            }
        }
        return r;
    }

    /**
     * 最长回文串,中心扩展算法
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        if (Objects.isNull(s) || s.length() < 1) {
            return "";
        }
        int l = 0, r = 0;
        for (var i = 0; i < s.length(); i++) {
            // 回文串为基数
            var l1 = expandAroundCenter(s, i, i);
            // 回文串为偶数
            var l2 = expandAroundCenter(s, i, i + 1);
            var ml = Math.max(l1, l2);
            if (ml > r - l) {
                l = i - ((ml - 1) >> 1);
                r = i + (ml >> 1);
            }
        }
        return s.substring(l, r + 1);
    }

    public static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return (right - 1) - (left + 1) + 1;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        var r = new ArrayList<List<Integer>>();
        if (Objects.isNull(nums) || nums.length < 1) {
            return r;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            var k = nums.length - 1;
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                while (k > j && nums[i] + nums[j] + nums[k] > 0) {
                    k--;
                }
                if (k == j) {
                    break;
                }
                var v1 = nums[i];
                var v2 = nums[j];
                var v3 = nums[k];
                if (v1 + v2 + v3 == 0) {
                    r.add(new ArrayList<>() {{
                        add(v1);
                        add(v2);
                        add(v3);
                    }});
                }
            }
        }
        return r;
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        // 添加一个哑结点
        var dn = new ListNode(0, head);
        var c = dn;
        var stack = new LinkedList<ListNode>();
        while (Objects.nonNull(c)) {
            stack.push(c);
            c = c.next;
        }
        for (var i = 0; i < n; i++) {
            stack.pop();
        }
        var temp = stack.peek();
        temp.next = temp.next.next;
        return dn.next;
    }

    public static String firstPalindrome(String[] words) {
        if (Objects.isNull(words) || words.length < 1) {
            return "";
        }
        var temp = new StringBuilder();
        for (var s : words) {
            temp.append(s);
            if (s.equals(temp.reverse().toString())) {
                break;
            }
            temp.delete(0, temp.length());
        }
        return temp.toString();
    }

    public static int majorityElement(int[] nums) {
        var candidate = -1;
        var count = 0;
        for (var num : nums) {
            if (count == 0) {
                candidate = num;
            }
            if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        count = 0;
        var length = nums.length;
        for (var num : nums) {
            if (num == candidate) {
                count++;
            }
        }
        return count << 1 > length ? candidate : -1;
    }

    public static int minCostToMoveChips(int[] position) {
        int odd = 0, even = 0;
        for (var i : position) {
            if ((i & 1) != 1) {
                even++;
                continue;
            }
            odd++;
        }
        return Math.min(odd, even);
    }

    public static int fixedPoint(int[] arr) {
        var r = -1;
        if (Objects.isNull(arr) || arr.length < 1) {
            return r;
        }
        for (var i = 0; i < arr.length; i++) {
            if (i == arr[i]) {
                r = i;
                break;
            }
        }
        return r;
    }

    public static int fixedPoint2(int[] arr) {
        var rlt = -1;
        if (Objects.isNull(arr) || arr.length < 1) {
            return rlt;
        }
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if (arr[mid] < mid) {
                l = mid + 1;
            } else if (arr[mid] > mid) {
                r = mid - 1;
            } else {
                rlt = mid;
                r = mid - 1;
            }
        }
        return rlt;
    }

    public static int partitionArray(int[] nums, int k) {
        if (Objects.isNull(nums) || nums.length < 1) {
            return 0;
        }
        Arrays.sort(nums);
        var min = nums[0];
        var c = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - min > k) {
                c++;
                min = nums[i];
            }
        }
        return c;
    }

    public static int maxAreaOfIsland(int[][] grid) {
        var r = 0;
        for (var i = 0; i < grid.length; i++) {
            for (var j = 0; j < grid[i].length; j++) {
                r = Math.max(r, dfs(grid, i, j));
            }
        }
        return r;
    }

    public static int dfs(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != 1) {
            return 0;
        }
        var c = 1;
        grid[i][j] = 0;
        var t1 = new int[]{0, 0, 1, -1};
        var t2 = new int[]{-1, 1, 0, 0};
        for (var k = 0; k < 4; k++) {
            c += dfs(grid, i + t1[k], j + t2[k]);
        }
        return c;
    }

    public static int countConsistentStrings(String allowed, String[] words) {
        var count = 0;
        for (var i = 0; i < words.length; i++) {
            var len = 0;
            for (var j = 0; j < words[i].length(); j++) {
                if (allowed.indexOf(words[i].charAt(j)) != -1) {
                    len++;
                } else {
                    break;
                }
            }
            if (len != words[i].length()) {
                continue;
            }
            count++;
        }
        return count;
    }

    static class Transaction {
        public String t;
        public String name;
        public int time;
        public int amount;
        public String city;
        public boolean valid;

        Transaction(String t, String name, int time, int amount, String city) {
            this.t = t;
            this.name = name;
            this.time = time;
            this.amount = amount;
            this.city = city;
            this.valid = amount > 1000;
        }

        void check(Transaction transaction) {
            var dt = time > transaction.time ? time - transaction.time : transaction.time - time;
            if (name.equals(transaction.name) && !city.equals(transaction.city) && dt <= 60) {
                valid = true;
                transaction.valid = true;
            }
        }
    }

    public static List<String> invalidTransactions(String[] transactions) {
        var rlt = new ArrayList<String>();
        var transactionList = new ArrayList<Transaction>();
        Arrays.stream(transactions).forEach(x -> {
            var temp = x.split(",");
            transactionList.add(new Transaction(x, temp[0],
                    Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), temp[3]));
        });
        for (var i = 0; i < transactionList.size(); i++) {
            var transaction = transactionList.get(i);
            if (transaction.valid) {
                continue;
            }
            for (var j = 0; j < transactionList.size(); j++) {
                if (i == j) {
                    continue;
                }
                transaction.check(transactionList.get(j));
            }
        }
        transactionList.stream().filter(x -> x.valid).forEach(x -> rlt.add(x.t));
        return rlt;
    }

    public static int longestSubarray(int[] nums) {
        var rlt = 0;
        for (var i = 0; i < nums.length; i++) {
            if (nums[i] < 1) {
                continue;
            }
            int c = 1, k = 0;
            var valid = false;
            for (var j = i + 1; j < nums.length; j++) {
                if (nums[j] < 1) {
                    if (!valid) {
                        k = j;
                        valid = true;
                    } else {
                        i = k;
                        break;
                    }
                } else {
                    c++;
                }
            }
            rlt = Math.max(rlt, c);
        }
        return rlt == nums.length ? --rlt : rlt;
    }

    public static int longestSubarray2(int[] nums) {
        int rlt = 0, p0 = 0, p1 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 1) {
                p1 = p0;
                p0 = 0;
            } else {
                p0++;
                p1++;
                rlt = Math.max(rlt, p1);
            }
        }
        return rlt == nums.length ? --rlt : rlt;
    }

    public static boolean checkOnesSegment(String s) {
        for (var i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                if (s.substring(i, s.length()).indexOf("1") != -1) {
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    public static int maxArea(int[] height) {
        // 水的宽d=r-l
        int rlt = 0, l = 0, r = height.length - 1;
        while (l <= r) {
            rlt = Math.max(rlt, Math.min(height[l], height[r]) * (r - l));
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return rlt;
    }

    public static int search(int[] nums, int target) {
        int rlt = -1, l = 0, r = nums.length - 1;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if (nums[mid] < target) {
                l = mid + 1;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                rlt = mid;
                break;
            }
        }
        return rlt;
    }


    public static int climbStairs(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    public static int climbStairs2(int n) {
        int r = 1, p = 0, q = 0;
        for (int i = 0; i < n; i++) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }


    public static String intToRoman(int num) {
        var v1 = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        var v2 = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        var builder = new StringBuilder();
        for (int i = 0; i < v1.length; i++) {
            while (num >= v1[i]) {
                builder.append(v2[i]);
                num -= v1[i];
            }
            if (num == 0) {
                break;
            }
        }
        return builder.toString();
    }

    public static int threeSumClosest(int[] nums, int target) {
        int min = Integer.MAX_VALUE, rlt = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                var sum = nums[i] + nums[l] + nums[r];
                if (sum == target) {
                    return target;
                }
                var dv = Math.abs(sum - target);
                if (min > dv) {
                    min = dv;
                    rlt = sum;
                }
                if (sum > target) {
                    r--;
                } else {
                    l++;
                }
            }
        }
        return rlt;
    }

    public static boolean canJump(int[] nums) {
        var max = 0;
        for (var i = 0; i < nums.length; i++) {
            if (i > max) {
                return false;
            }
            // 记录每一个格子能够跳到的最大索引位的最大值
            max = Math.max(max, i + nums[i]);
        }
        return true;
    }

    public static boolean isPalindrome(ListNode head) {
        if (Objects.isNull(head)) {
            return true;
        }
        var temp = getSubsequent(head);
        temp = reverse(temp);
        while (Objects.nonNull(temp)) {
            if (head.val != temp.val) {
                return false;
            }
            head = head.next;
            temp = temp.next;
        }
        return true;
    }

    /**
     * 单项链表反转
     *
     * @param head
     * @return
     */
    public static ListNode reverse(ListNode head) {
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
     * 获取中间元素后面的元素
     *
     * @param head
     * @return
     */
    private static ListNode getSubsequent(ListNode head) {
        ListNode l1 = head, l2 = head;
        while (Objects.nonNull(l2.next) && Objects.nonNull(l2.next.next)) {
            l2 = l2.next.next;
            l1 = l1.next;
        }
        return l1.next;
    }

    public static int jump(int[] nums) {
        int count = 0, max = 0, end = 0;
        for (var i = 0; i < nums.length; i++) {
            max = Math.max(max, nums[i] + i);
            if (i == end) {
                end = max;
                count++;
            }
        }
        return count;
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        var rlt = new ArrayList<List<Integer>>();
        var list = new ArrayList<Integer>();
        dfs(candidates, target, 0, rlt, list);
        return rlt;
    }

    public static void dfs(int[] candidates, int target, int begin, List<List<Integer>> rlt, List<Integer> list) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            rlt.add(new ArrayList<>(list));
            return;
        }
        for (var i = begin; i < candidates.length; i++) {
            var temp = candidates[i];
            list.add(temp);
            dfs(candidates, target - temp, i, rlt, list);
            list.remove(list.size() - 1);
        }
    }

    public static List<String> letterCombinations(String digits) {
        var rlt = new ArrayList<String>();
        if (Objects.isNull(digits) || digits.length() < 1) {
            return rlt;
        }
        // 建立每一个数字对应的字符关系
        var map = new ConcurrentHashMap<Character, String>() {
            {
                put('2', "abc");
                put('3', "def");
                put('4', "ghi");
                put('5', "jkl");
                put('6', "mno");
                put('7', "pqrs");
                put('8', "tuv");
                put('9', "wxyz");
            }
        };
        dfs(map, rlt, digits, new StringBuilder(), 0);
        return rlt;
    }

    public static void dfs(Map<Character, String> map, List<String> rlt, String digits, StringBuilder path, int index) {
        if (index >= digits.length()) {
            rlt.add(path.toString());
            return;
        }
        var v = map.get(digits.charAt(index));
        for (var i = 0; i < v.length(); i++) {
            path.append(v.charAt(i));
            dfs(map, rlt, digits, path, index + 1);
            path.delete(path.length() - 1, path.length());
        }
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        var rlt = new ArrayList<List<Integer>>();
        var path = new LinkedList<Integer>();
        Arrays.sort(candidates);
        dfs(rlt, path, candidates, target, 0);
        return rlt;
    }

    public static void dfs(List<List<Integer>> rlt, LinkedList<Integer> path, int[] candidates, int target, int index) {
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
            dfs(rlt, path, candidates, target - candidates[i], i + 1);
            path.removeLast();
        }
    }

    public static String multiply(String num1, String num2) {
        var rlt = "0";
        if (num1.equals("0") || num2.equals("0")) {
            return rlt;
        }
        var l1 = num1.length() - 1;
        var l2 = num2.length() - 1;
        for (var i = l1; i >= 0; i--) {
            var t1 = num1.charAt(i) - '0';
            int carry = 0;
            var builder = new StringBuilder();
            for (var j = l1; j > i; j--) {
                builder.append("0");
            }
            for (var k = l2; k >= 0; k--) {
                var t2 = num2.charAt(k) - '0';
                var t3 = t1 * t2 + carry;
                builder.append(t3 % 10);
                carry = t3 / 10;
            }
            if (carry > 0) {
                builder.append(carry);
            }
            rlt = addStrings(rlt, builder.reverse().toString());
        }
        return rlt;
    }

    public static String addStrings(String num1, String num2) {
        var rlt = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            var t1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            var t2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            var t3 = t1 + t2 + carry;
            rlt.append(t3 % 10);
            carry = t3 / 10;
            i--;
            j--;
        }
        return rlt.reverse().toString();
    }

    public static void nextPermutation(int[] nums) {
        var i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i < 0) {
            reverse(nums, i + 1);
            return;
        }
        var j = nums.length - 1;
        while (j >= 0 && nums[i] >= nums[j]) {
            j--;
        }
        swap(nums, i, j);
        reverse(nums, i + 1);
    }

    public static void swap(int[] nums, int i, int j) {
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }

    public static void reverse(int[] nums, int start) {
        var end = nums.length - 1;
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    public static int search2(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            var mid = ((r - l) >> 1) + l;
            if (target == nums[mid]) {
                return mid;
            }
            if (nums[mid] >= nums[0]) {
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
        return -1;
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        var rlt = new ArrayList<List<Integer>>();
        if (Objects.isNull(nums) || nums.length < 4) {
            return rlt;
        }
        Arrays.sort(nums);
        for (var i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (var j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                var l = nums.length - 1;
                for (var k = j + 1; k < nums.length; k++) {
                    if (k > j + 1 && nums[k] == nums[k - 1]) {
                        continue;
                    }
                    while (l > k && (nums[i] + nums[j] + nums[k] + nums[l]) > target) {
                        l--;
                    }
                    if (l == k) {
                        break;
                    }
                    var t1 = nums[i];
                    var t2 = nums[j];
                    var t3 = nums[k];
                    var t4 = nums[l];
                    if (((long) t1 + t2 + t3 + t4) == target) {
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

    public static List<String> generateParenthesis(int n) {
        var rlt = new ArrayList<String>();
        dfs(rlt, new StringBuilder(), 0, 0, n);
        return rlt;
    }

    public static void dfs(List<String> rlt, StringBuilder builder, int i, int j, int n) {
        if (builder.length() == n << 1) {
            rlt.add(builder.toString());
            return;
        }
        if (i < n) {
            builder.append("(");
            dfs(rlt, builder, i + 1, j, n);
            builder.delete(builder.length() - 1, builder.length());
        }
        if (j < i) {
            builder.append(")");
            dfs(rlt, builder, i, j + 1, n);
            builder.delete(builder.length() - 1, builder.length());
        }
    }

    public static ListNode swapPairs(ListNode head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }
        var next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }

    public static int trap(int[] height) {
        int rlt = 0, l = 0, r = height.length - 1, lm = 0, rm = 0;
        while (l < r) {
            lm = Math.max(lm, height[l]);
            rm = Math.max(rm, height[r]);
            if (height[l] < height[r]) {
                rlt += lm - height[l];
                l++;
                continue;
            }
            rlt += rm - height[r];
            r--;
        }
        return rlt;
    }

    public static void main(String[] args) {
        System.out.println(trap(new int[]{1, 2, 0, 3}));
        //System.out.println(swapPairs(new ListNode(1, new ListNode(2, new ListNode(3)))));
        //System.out.println(generateParenthesis(2));
        //System.out.println(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
        //System.out.println(fourSum(new int[]{1000000000, 1000000000, 1000000000, 1000000000, 1000000000}, -294967296));


//        System.out.println(search2(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
        //System.out.println(search2(new int[]{3, 1}, 1));


//        var temp = new int[]{1, 5, 1};
//        nextPermutation(temp);
//        Arrays.stream(temp).forEach(System.out::println);

        //System.out.println(multiply("12", "7"));
//        System.out.println(addStrings("12", "9"));
//        System.out.println(multiply("12", "24"));
        //System.out.println(combinationSum2(new int[]{1, 2, 2, 2, 5}, 5));
        //System.out.println(letterCombinations("23"));
        //System.out.println(combinationSum(new int[]{1, 2}, 3));
//        System.out.println(jump(new int[]{2, 5, 1, 1, 1, 1}));
        //System.out.println(jump(new int[]{1, 2, 0, 0, 5}));
        //System.out.println(isPalindrome(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(2, new ListNode(1)))))));
//        System.out.println(canJump(new int[]{3, 2, 1, 1, 4}));
        //System.out.println(canJump(new int[]{1, 0, 1, 0}));
        //System.out.println(threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
        //System.out.println(intToRoman(3));
//        System.out.println(climbStairs(49));
//        System.out.println(climbStairs(49));
        //System.out.println(search(new int[]{-1, 0, 3, 5, 9, 12}, 30));
        //System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        //System.out.println(checkOnesSegment("1001"));
//        var temp = new int[]{1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1};
//        System.out.println(longestSubarray2(temp));
//        System.out.println(longestSubarray(temp));
        //["bob,689,1910,barcelona","alex,696,122,bangkok","bob,832,1726,barcelona","bob,820,596,bangkok","chalicefy,217,669,barcelona","bob,175,221,amsterdam"]
        //System.out.println(invalidTransactions(new String[]{
//        "alex,689,100,barcelona", "alex,689,100,barcelona", "alex,696,100,bangkok"
//    }));
        //["bob,627,1973,amsterdam","alex,387,885,bangkok","alex,355,1029,barcelona","alex,587,402,bangkok","chalicefy,973,830,barcelona","alex,932,86,bangkok","bob,188,989,amsterdam"]
        // System.out.println(countConsistentStrings("ab", new String[]{"ad", "bd", "aaab", "baa", "badab"}));
//        System.out.println(maxAreaOfIsland(new int[][]{
//                {0, 0},
//                {1, 1}}));
        //System.out.println(partitionArray(new int[]{1, 2, 4}, 1));
        //System.out.println(fixedPoint2(new int[]{0, 1, 19, 20, 26}));
//        System.out.println(new Solution().findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
        //Arrays.stream(new Solution().twoSum(new int[]{1, 1}, 2)).forEach(System.out::println);
//        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3, null)));
//        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4, null)));
//        System.out.println(new Solution().addTwoNumbers(l1, l2));

        //System.out.println(new Solution().addTwoNumbers());
//        Map<String, Integer> map = new ConcurrentHashMap();
//        map.put("1", 1);
//        map.put("2", 2);
//        System.out.println(map.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getValue());

        //System.out.println(lengthOfLongestSubstring("abcabcbb"));
        //System.out.println(isValid("(("));
        //System.out.println(mergeTwoLists(new ListNode(1, new ListNode(2)), new ListNode(3, new ListNode(4))));
        //System.out.println(isPalindrome(121));
//        var temp = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
//        System.out.println(removeElement(temp, 2) + "\n");
//        Arrays.stream(temp).forEach(System.out::println);

        //System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 5));
        //System.out.println(romanToInt("IV"));
        //System.out.println(longestPalindrome("aaaa"));
//        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        System.out.println(removeNthFromEnd(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2));
//        System.out.println(removeNthFromEnd(new ListNode(1), 1));
//        System.out.println(firstPalindrome(new String[]{"ada"}));
//        System.out.println(minCostToMoveChips(new int[]{3, 3, 1, 2, 2}));
//        System.out.println(majorityElement(new int[]{1, 1, 2, 2, 2, 5, 5, 5, 5}));
    }
}
