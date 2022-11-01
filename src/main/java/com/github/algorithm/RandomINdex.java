package com.github.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomINdex {
    static int totle = 147;

    public static void main(String[] args) {
        var list = new ArrayList<Integer>();
        dfs(30, list);
        Collections.sort(list);
        System.out.println(list);
    }

    static void dfs(int n, List<Integer> list) {
        if (n < 1) {
            return;
        }
        var temp = ((int) (Math.random() * totle)) + 1;
        if (!(temp >= 1 && temp <= 147)) {
            dfs(n, list);
        } else if (list.contains(temp)) {
            dfs(n, list);
        } else {
            list.add(temp);
            dfs(n - 1, list);
        }
    }
}
