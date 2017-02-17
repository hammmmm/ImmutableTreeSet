package com.mycompany.app;

import java.util.Random;
import java.lang.Integer;
import java.lang.System.*;
import java.util.Iterator;

/**
 * Maven created this and stores some references to it. Don't want to delete it and accidentally screw up the project structure.
 * Right now it just prints out a set before and after a some operations
 */
public class App {

    public static void main(String[] args) {

        ImmutableSet<Integer> set = new ImmutableSet();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            set.add(r.nextInt(50));
            print(set);
        }
        for (int i = 0; i < 50; i++) {
            if (set.contains(i)) {
                set.remove(i);
                print(set);
            }
        }
    }

    static void print(ImmutableSet s) {
        Iterator iterator = s.iterator();
        System.out.print("{");
        while(iterator.hasNext()) {
            System.out.print(iterator.next() + ",");
        }
        System.out.println("}");
    }

}
