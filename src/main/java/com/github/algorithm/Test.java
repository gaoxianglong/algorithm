package com.github.algorithm;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Test {
    /**
     * 前驱1-35个球
     */
    private List<Integer> front = new ArrayList<>() {{
        for (var i = 1; i <= 35; i++) {
            add(i);
        }
    }};
    /**
     * 后驱1-12个球
     */
    private List<Integer> behind = new ArrayList<>() {{
        for (var i = 1; i <= 12; i++) {
            add(i);
        }
    }};

    private void dfs(int n, List<Integer> numbers, List<Integer> path) {
        if (n < 1) {
            return;
        }
        var index = (int) (Math.random() * numbers.size());
        var number = numbers.get(index);
        path.add(number);
        numbers.remove(index);
        dfs(n - 1, numbers, path);
    }

    private void run() {
        for (int i = 0; i < 5; i++) {
            System.out.printf("%s、", (char) (i % 26 + 'A'));
            var fns = new ArrayList<Integer>();
            getResult(5, front, fns);
            System.out.print(": ");
            var bns = new ArrayList<Integer>();
            getResult(2, behind, bns);
            System.out.println();
        }
    }

    private void getResult(int n, List<Integer> numbers, List<Integer> path) {
        dfs(n, numbers, path);
        Collections.sort(path);
        path.forEach(x -> {
            System.out.printf("%02d ", x);
        });
    }

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        var stack = new LinkedList<Integer>();
        var map = new ConcurrentHashMap<Integer, Integer>() {{
            var n = nums2.length;
            for (var i = n - 1; i >= 0; i--) {
                var c = nums2[i];
                while (!stack.isEmpty() && c >= stack.peek()) {
                    stack.pop();
                }
                put(c, stack.isEmpty() ? -1 : stack.peek());
                stack.push(c);
            }
        }};
        var n = nums1.length;
        var rlt = new int[n];
        for (var i = 0; i < n; i++) {
            rlt[i] = map.get(nums1[i]);
        }
        return rlt;
    }

    public int[] nextGreaterElement2(int[] nums1) {
        var n = nums1.length;
        var stack = new LinkedList<Integer>();
        var map = new ConcurrentHashMap<Integer, Integer>() {{
            for (var i = (n - 1) << 1; i >= 0; i--) {
                var c = nums1[i % n];
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

    public static void main(String[] args) {
        //new Test().run();
//        Arrays.stream(new Test().nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2})).forEach(System.out::println);
        Arrays.stream(new Test().nextGreaterElement2(new int[]{1, 2, 3, 4, 3})).forEach(System.out::println);
        System.out.println();
        Arrays.stream(new Test().nextGreaterElement2(new int[]{5, 4, 3, 2, 1})).forEach(System.out::println);
        System.out.println();
        Arrays.stream(new Test().nextGreaterElement2(new int[]{100, 1, 11, 1, 120, 111, 123, 1, -1, -100})).forEach(System.out::println);
    }
}
