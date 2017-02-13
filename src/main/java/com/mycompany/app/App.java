package com.mycompany.app;

import java.util.Random;
import java.lang.Integer;
import java.lang.System.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        RBTreeSet<Integer> tree = new RBTreeSet<Integer>();
        Random r = new Random();
        for (int i = 0; i < 1; i++) {
            // int x = r.nextInt(50);
            tree.add(new Integer(i));
        }
        System.out.print("{");
        tree.print();
        System.out.println("}");
    }
}
