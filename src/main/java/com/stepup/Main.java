package com.stepup;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int N = 10;
        List<Integer> lst = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            lst.add(i);
        }

        for (int i = 0; i < lst.size(); i+=2) {
            int temp = lst.get(i);
            lst.set(i, lst.get(i+1));
            lst.set(i+1, temp);
        }

        System.out.println(lst);
    }

    public static void reverse(ArrayList<Integer> intList) {
        int n = intList.size() - 1;
        for (int i = 0; i < intList.size() / 2; i++) {
            int temp = intList.get(i);
            intList.set(i, intList.get(n - i));
            intList.set(n - i, temp);
        }
    }


}