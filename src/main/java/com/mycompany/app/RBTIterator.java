package com.mycompany.app;

import java.util.Iterator;
import java.util.Stack;
import java.lang.Comparable;

class RBTIterator<T extends Comparable> implements Iterator {

    Stack<Node> inOrder;

    RBTIterator(Node root) {
        inOrder = new Stack();
        fillStack(root);
    }

    private void fillStack(Node node) {
        if (node != null) {

            if (node.right != null) {
                fillStack(node.right);
            }
            inOrder.push(node);
            if (node.left != null) {
                fillStack(node.left);
            }
        }
    }


    @Override
    public boolean hasNext() {
        return !inOrder.empty();
    }

    @Override
    public Comparable next() {
        return inOrder.pop().value;
    }
}
