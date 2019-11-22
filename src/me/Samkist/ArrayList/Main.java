package me.Samkist.ArrayList;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SamsArrayList<String> names = new SamsArrayList<>();
        //Test Array Here
        names.add("Sam");
        names.add("Sam");
        names.add("Maia");
        ArrayList<String> nm = new ArrayList<>();
        nm.add("Mike");
        nm.add("Mr. Geary");
        nm.add("Sam");
        names.addAll(0, nm);
        names.add("Sam");
        System.out.println(names.removeAll(nm));
        names.forEach(System.out::println);
    }
}
