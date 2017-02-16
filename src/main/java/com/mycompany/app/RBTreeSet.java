package com.mycompany.app;

import java.lang.Iterable;
import java.lang.Comparable;
import java.util.Iterator;
import java.lang.System;
import java.util.Stack;
// Every node is colored with either red or black.
// All leaf (nil) nodes are colored with black; if a node’s child is missing then we will assume that it has a nil child in that place and this nil child is always colored black.
// Both children of a red node must be black nodes.
// Every path from a node n to a descendent leaf has the same number of black nodes (not counting node n). We call this number the black height of n, which is denoted by bh(n).

public class RBTreeSet<T extends Comparable<T>> implements ISet<T>, Iterable {

    Node<T> root;
    private int size;

    public RBTreeSet() {
        root = null;
        size = 0;
    }

    public void add(T element) {
//		insert(element, root);

        Node<T> current = root;
        boolean added = false;
        while (!added) {

            if (root == null) {
                root = new Node<T>(element, Color.BLACK);
                added = true;
            } else if (element.compareTo(current.value) == 0) {
                return;
            } else if (element.compareTo(current.value) < 0 && current.left == null) {
                current.left = new Node<T>(element, Color.RED);
                current.left.parent = current;
                insertAdjust(current.left);
                added = true;
            } else if (element.compareTo(current.value) > 0 && current.right == null) {
                current.right = new Node<T>(element, Color.RED);
                current.right.parent = current;
                insertAdjust(current.right);
                added = true;
            } else if (element.compareTo(current.value) < 0 && current.left != null) {
                current = current.left;
            } else if (element.compareTo(current.value) > 0 && current.right != null) {
                current = current.right;
            }
        }
        root.color = Color.BLACK;
        size++;
        print(root);
        System.out.println();
        System.out.println();
    }

    private void insertAdjust(Node<T> node) {

        if (node != null && node != root && node.color == Color.RED) {

            Node grandparent = node.parent.parent;
            Node parent = node.parent;

            //recolors and then looks to see if anything else needs to be adjusted
            if (node.uncle() != null && node.uncle().color == Color.RED && parent.color == Color.RED) {

                node.uncle().color = Color.BLACK;
                parent.color = Color.BLACK;
                grandparent.color = Color.RED;
                insertAdjust(grandparent);
            } //left rotate
            else if ((node.uncle() == null || node.uncle().color == Color.BLACK) && parent.color == Color.RED) {

                Node next = restructure(node);
                insertAdjust(next);
            } // right rotate
            else if ((node.uncle() == null || node.uncle().color == Color.BLACK) && parent.color == Color.RED) {

                Node next = (node);
                insertAdjust(next);
            }
        }
    }

    private Node restructure(Node<T> node) {

        Node ancestor = node.parent.parent.parent;
        Node grandparent = node.parent.parent;
        Node parent = node.parent;
        Node next;

        if (node == parent.left && parent == grandparent.left) {

            next = parent;
            grandparent.left = parent.right;
            parent.parent = ancestor;
            parent.right = grandparent;
            grandparent.parent = parent;

            bindAncestor(grandparent, ancestor, parent);
        } else if (node == parent.right && parent == grandparent.left) {

            next = node;
            node.parent = ancestor;
            grandparent.left = node.right;
            node.right.parent = grandparent;
            parent.right = node.left;
            node.left = parent;
            node.right = grandparent;
            parent.parent = node;
            grandparent.parent = node;

            bindAncestor(grandparent, ancestor, node);
        } else if (node == parent.left && parent == grandparent.right) {

            next = node;
            node.parent = ancestor;
            grandparent.right = node.left;
            node.left.parent = grandparent;
            node.left = grandparent;
            parent.left = node.right;
            node.right = parent;
            grandparent.parent = node;
            parent.parent = node;

            bindAncestor(grandparent, ancestor, node);
        } else if (node == parent.right && parent == grandparent.right) {

            next = parent;
            grandparent.right = parent.left;
            parent.parent = ancestor;
            parent.left = grandparent;
            grandparent.parent = parent;

            bindAncestor(grandparent, ancestor, parent);
        } else {
            return null;
        }

        next.color = Color.BLACK;
        next.right.color = Color.RED;
        next.left.color = Color.RED;

        if (next.parent == null) {
            root = next;
        }
        return next;
    }

    private void bindAncestor(Node g, Node a, Node n) {
        if (a != null) {
            if (g == a.left) {
                a.left = n;

            } else if (g == a.right) {
                a.right = n;
            }
        }
    }

    public void remove(T element) {

    }

    private void delete(T x, Node<T> root) {

    }

    private void deleteAdjust(Node<T> node) {

    }

    @Override
    public Iterator iterator() {
        return new RBTIterator(root);
    }

    RBTIterator ITERATOR() {
        return new RBTIterator(root);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void print() {
        print(root);
    }

    private void print(Node<T> node) {

        if (node != null) {

            print(node.left);
            System.out.print("(" + node.value + "," + node.color + ")");

            print(node.right);
        }
    }
}
