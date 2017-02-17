package com.mycompany.app;

import java.lang.Comparable;

class Node<T extends Comparable> {

    final T value;
    Color color;
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

    Node successor() {
        Node current = right;
        while (current != null) {
            current = current.left;
        }
        return current;
    }
}