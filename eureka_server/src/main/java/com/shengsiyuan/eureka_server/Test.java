package com.shengsiyuan.eureka_server;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(null);
        System.out.println(list.size());
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
