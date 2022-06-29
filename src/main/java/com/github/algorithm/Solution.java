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

import com.github.data_structure.Array;
import lombok.Data;

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

    public static void main(String[] args) {
        //System.out.println(fixedPoint2(new int[]{-10, -5, -2, 0, 4, 5, 6, 7, 8, 9, 10}));
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

        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 5));
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
