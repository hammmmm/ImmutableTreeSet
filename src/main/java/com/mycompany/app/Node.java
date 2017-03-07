package com.mycompany.app;

import java.lang.Comparable;
import java.util.ArrayList;

class Node<T extends Comparable> {

    final T value;
    Color color;
    ArrayList<Node> leftLog, rightLog, parentLog;
    Node left, right, parent;

    Node(T value, Color color) {
        this.value = value;
        this.color = color;
        left = right = parent = null;
    }

    Node(Color color) {
        this.color = color;
        value = null;
        left = right = parent = null;
    }

    Node copy() {
        Node n = new Node(this.value, this.color);
        n.left = this.left;
        n.right = this.right;
        n.parent = this.parent;
        return n;
    }

    //will return sibling or null as long as this != root
    Node sibling() {
        if (this == parent.left) {
            return parent.right;
        }
        return parent.left;
    }

    //will return uncle or null as long as this.parent != root
    Node uncle() {
        if (parent != null && parent.parent != null) {
            return parent.sibling();
        }
        return null;
    }

    //returns the leftmost node in the right sub-tree of this node
    Node successor() {
        Node current = right;
        while (current != null && current.left != null) {
            current = current.left;
        }
        return current;
    }
}