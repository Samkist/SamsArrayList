package me.Samkist.ArrayList;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SamsArrayList<String> names = new SamsArrayList<>();
        //Test Array Here
        names.add("Ben");
        names.add("Noah");
        names.add(1, "Jerry");
        System.out.println("Index Add Section");
        for(int i = 0; i < names.size(); i++)
            System.out.println(names.get(i));
    }
}
