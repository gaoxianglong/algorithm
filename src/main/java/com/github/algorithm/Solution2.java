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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2022/7/2 19:31
 */
public class Solution2 {
    public static String firstPalindrome(String[] words) {
        for (var i = 0; i < words.length; i++) {
            var temp = words[i];
            var begin = 0;
            var end = temp.length() - 1;
            while (begin <= end) {
                if (temp.charAt(begin) != temp.charAt(end)) {
                    break;
                } else {
                    begin++;
                    end--;
                }
                if (begin >= end) {
                    return temp;
                }
            }
        }
        return "";
    }

    public static int minCostToMoveChips(int[] position) {
        int odd = 0, even = 0;
        for (var i = 0; i < position.length; i++) {
            if ((position[i] & 1) == 1) {
                odd++;
            } else {
                even++;
            }
        }
        return Math.min(odd, even);
    }

    public static int[] twoSum(int[] nums, int target) {
        var map = new ConcurrentHashMap<Integer, Integer>();
        for (var i = 0; i < nums.length; i++) {
            var temp = target - nums[i];
            if (map.containsKey(temp)) {
                return new int[]{i, map.get(temp)};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

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

    public static List<List<Integer>> threeSum(int[] nums) {
        var rlt = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
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
                if (j == k) {
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

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        var carry = 0;
        ListNode head = null, temp = null;
        while (Objects.nonNull(l1) || Objects.nonNull(l2) || carry > 0) {
            var t1 = Objects.nonNull(l1) ? l1.val : 0;
            var t2 = Objects.nonNull(l2) ? l2.val : 0;
            var t3 = t1 + t2 + carry;
            if (Objects.isNull(temp)) {
                temp = new ListNode(t3 % 10);
                head = temp;
            } else {
                temp.next = new ListNode(t3 % 10);
                temp = temp.next;
            }
            carry = t3 / 10;
            l1 = Objects.nonNull(l1) ? l1.next : null;
            l2 = Objects.nonNull(l2) ? l2.next : null;
        }
        return head;
    }

    public static int lengthOfLongestSubstring(String s) {
        var rlt = 0;
        var set = new HashSet<Character>();
        for (var i = 0; i < s.length(); i++) {
            var count = 1;
            set.add(s.charAt(i));
            for (var j = i + 1; j < s.length(); j++) {
                if (set.contains(s.charAt(j))) {
                    break;
                }
                count++;
                set.add(s.charAt(j));
            }
            set.clear();
            rlt = Math.max(count, rlt);
        }
        return rlt;
    }

    public static String longestPalindrome(String s) {
        int maxLen = 0, l = 0, r = 0;
        for (var i = 0; i < s.length(); i++) {
            var t1 = expandAroundCenter(s, i, i);
            var t2 = expandAroundCenter(s, i, i + 1);
            var t3 = t1[2] > t2[2] ? t1 : t2;
            var len = t3[2];
            if (maxLen < len) {
                maxLen = len;
                l = t3[0];
                r = t3[1] + 1;
            }
        }
        return s.substring(l, r);
    }

    public static int[] expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return new int[]{left + 1, right - 1, (right - 1) - (left + 1) + 1};
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

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        var n1 = new ListNode(0, head);
        var n2 = n1;
        var stack = new LinkedList<ListNode>();
        while (Objects.nonNull(n2)) {
            stack.push(n2);
            n2 = n2.next;
        }
        for (var i = 0; i < n; i++) {
            stack.pop();
        }
        var prev = stack.peek();
        prev.next = prev.next.next;
        return n1.next;
    }

    public static boolean isValid(String s) {
        if ((s.length() & 1) == 1) {
            return false;
        }
        var map = new ConcurrentHashMap<Character, Character>() {{
            put('}', '{');
            put(']', '[');
            put(')', '(');
        }};
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

    public static void main(String[] args) {
        System.out.println("-----");
        //System.out.println(isValid("){"));
//        System.out.println(removeNthFromEnd(new ListNode(1, new ListNode(2)), 1));
        // 5\4\3\2\1\0
        //System.out.println(removeNthFromEnd(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 4));
        //System.out.println(romanToInt("IV"));
        //System.out.println(firstPalindrome(new String[]{"abc", "ea", "ada", "racecar", "cool"}));
        //Arrays.stream(twoSum(new int[]{2, 7, 11, 15}, 9)).forEach(System.out::println);
        //System.out.println(searchInsert(new int[]{1, 3, 5, 7}, 0));
        //System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        System.out.println(addTwoNumbers(new ListNode(2, new ListNode(4, new ListNode(3))),
//                new ListNode(5, new ListNode(6, new ListNode(4)))));
//        System.out.println(lengthOfLongestSubstring("a"));
        //System.out.println(longestPalindrome("aaaaabba"));
        //System.out.println(isPalindrome(121));
    }
}
