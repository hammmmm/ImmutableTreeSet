package com.mycompany.app;

import java.util.Random;
import java.lang.Integer;
import java.lang.System.*;
import java.util.Iterator;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Random r = new Random();
//        for (int j = 1; j < 11; j++) {
//        RBTreeSet<Integer> tree = new RBTreeSet<Integer>();
//
//        for (int i = j; i > -1; i--) {
//            int x = r.nextInt(50);
//            tree.add(new Integer(i));
//        }
//        System.out.print("{");
//        tree.print();
//        System.out.println("}");
//
//        System.out.println(maxDepth(tree.root));
//        }

        RBTreeSet<Integer> tree = new RBTreeSet<Integer>();
        tree.add(new Integer(6));
        tree.add(new Integer(5));
        tree.add(new Integer(22));
        tree.add(new Integer(5));
        tree.add(new Integer(0));
        tree.add(new Integer(44));
        tree.add(new Integer(75));
        tree.add(new Integer(33));
        tree.add(new Integer(101));
        tree.add(new Integer(17));
        tree.add(new Integer(19));

        Iterator I = tree.iterator();

        System.out.print("{");
        while (I.hasNext()) {
            System.out.print(I.next() + ", ");
        }
        System.out.println("}");

        System.out.println(maxDepth(tree.root));

    }

    private static int maxDepth(Node<Integer> root) {
        if (root == null) {
            return 0;
        }

        int ldepth = maxDepth(root.left);
        int rdepth = maxDepth(root.right);

        if (ldepth > rdepth) {
            return ldepth + 1;
        } else {
            return rdepth + 1;
        }
    }
}
