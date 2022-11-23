package com.github.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomINdex {
    static int totle = 163;

    public static void main(String[] args) {
        List<Integer> list = null;
        // 预热
        for (var i = 0; i < 100; i++) {
            list = new ArrayList<>();
            dfs(30, list);
        }
        Collections.sort(list);
        System.out.println(list);
    }

    static void dfs(int n, List<Integer> list) {
        if (n < 1) {
            return;
        }
        var temp = ((int) (Math.random() * totle)) + 1;
        if (!(temp >= 50 && temp <= totle)) {
            dfs(n, list);
        } else if (list.contains(temp)) {
            dfs(n, list);
        } else {
            list.add(temp);
            dfs(n - 1, list);
        }
    }
}
