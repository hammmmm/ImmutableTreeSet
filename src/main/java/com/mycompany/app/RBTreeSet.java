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
//            print();
//            System.out.println();
        }
        root.color = Color.BLACK;
        size++;
        print(root);
        System.out.println();
        System.out.println();
    }

    private void insertAdjust(Node<T> node) {

        if (node != null && node != root && node.color == Color.RED) {

            //recolors and then looks to see if anything else needs to be adjusted
            if (node.uncle() != null && node.uncle().color == Color.RED && node.parent.color == Color.RED) {

                node.uncle().color = Color.BLACK;
                node.parent.color = Color.BLACK;
                if (node.parent.parent != null && node.parent.parent != root) {
                    node.parent.parent.color = Color.RED;
                }
                insertAdjust(node.parent.parent);
            } //left rotate
            else if ((node.uncle() == null || node.uncle().color == Color.BLACK) && node.parent.color == Color.RED && node == node.parent.right) {

                rotateLeft(node);
                insertAdjust(node.parent);
            } // right rotate
            else if ((node.uncle() == null || node.uncle().color == Color.BLACK) && node.parent.color == Color.RED && node == node.parent.left) {

                rotateRight(node);
                insertAdjust(node.parent);
            }
        }
    }

    private void rotateRight(Node<T> node) {

        Node<T> p = node.parent;
        node.parent = p.parent;

        if (node.parent != null) {
            if (node.parent.left == p) {
                node.parent.left = node;
            } else if (node.parent.right == p) {
                node.parent.right = node;
            }
        }
        p.parent = node;
        p.left = node.right;
        node.right = p;

        Color temp = p.color;
        p.color = node.color;
        node.color = temp;

        if (node.parent == null) {
            this.root = node;
            node.color = Color.BLACK;
        }
    }

    private void rotateLeft(Node<T> node) {

        Node<T> p = node.parent;
        node.parent = p.parent;
        if (node.parent != null) {
            if (node.parent.left == p) {
                node.parent.left = node;
            } else if (node.parent.right == p) {
                node.parent.right = node;
            }
        }
        p.parent = node;
        p.right = node.left;
        node.left = p;

        Color temp = p.color;
        p.color = node.color;
        node.color = temp;

        if (node.parent == null) {
            this.root = node;
            node.color = Color.BLACK;
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
        return new RBTreeSetIterator(root);
    }

    RBTreeSetIterator ITERATOR() {
        return new RBTreeSetIterator(root);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void print() {
        print(root);
//        if (root == null) {
//            return;
//        }
//
//        //keep the nodes in the path that are waiting to be visited
//        Stack<Node> stack = new Stack<Node>();
//        Node node = root;
//
//        //first node to be visited will be the left one
//        while (node != null) {
//            stack.push(node);
//            node = node.left;
//        }
//
//        // traverse the tree
//        while (stack.size() > 0) {
//
//            // visit the top node
//            node = stack.pop();
//            System.out.print("(" + node.value + "," + node.color + ")");
//            if (node.right != null) {
//                node = node.right;
//
//                // the next node to be visited is the leftmost
//                while (node != null) {
//                    stack.push(node);
//                    node = node.left;
//                }
//            }
//        }
    }

    private void print(Node<T> node) {

        if (node != null) {

            print(node.left);
            System.out.print("(" + node.value + "," + node.color + ")");

            print(node.right);
        }
    }

//    private void Print(Node<T> node) {
//        if (node != null)
//    }
}
