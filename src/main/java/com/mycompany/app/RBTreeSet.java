package com.mycompany.app;

import java.lang.Comparable;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Every node is colored with either red or black.
// All leaf (nil) nodes are colored with black; if a node’s child is missing then we will assume that it has a nil child in that place and this nil child is always colored black.
// Both children of a red node must be black nodes.
// Every path from a node n to a descendent leaf has the same number of black nodes (not counting node n).

class RBTreeSet<T extends Comparable> {

    Node root;
    private int size;

    RBTreeSet() {
        root = null;
        size = 0;
    }

    void add(T element) {

        Node current = root;
        boolean added = false;
        while (!added) {

            if (root == null) {
                root = new Node(element, Color.BLACK);
                added = true;
            } else if (element.compareTo(current.value) == 0) {
                return;
            } else if (element.compareTo(current.value) < 0 && current.left == null) {
                current.left = new Node(element, Color.RED);
                current.left.parent = current;
                insertAdjust(current.left);
                added = true;
            } else if (element.compareTo(current.value) > 0 && current.right == null) {
                current.right = new Node(element, Color.RED);
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
    }

    //Recursive algorithm. Determines whether the tree is a valid RBTree.
    //If not determines whether a recoloring or a restructuring is appropriate
    private void insertAdjust(Node node) {

        if (node != null && node != root && node.color == Color.RED) {

            Node grandparent = node.parent.parent;
            Node parent = node.parent;

            //recolors and then looks to see if anything else needs to be adjusted
            if (node.uncle() != null && node.uncle().color == Color.RED && parent.color == Color.RED) {

                node.uncle().color = Color.BLACK;
                parent.color = Color.BLACK;
                grandparent.color = Color.RED;
                insertAdjust(grandparent);

            } else if ((node.uncle() == null || node.uncle().color == Color.BLACK) && parent.color == Color.RED) {

                Node next = restructure(node);
                insertAdjust(next);
            }
        }
    }

    //Given that the tree needs restructuring this method determines what type of restructuring is appropriate
    //and calls the associated method.
    private Node restructure(Node node) {

        Node ancestor = node.parent.parent.parent;
        Node grandparent = node.parent.parent;
        Node parent = node.parent;
        Node next;

        if (node == parent.left && parent == grandparent.left) {

            next = rightRotation(parent, grandparent, ancestor);

        } else if (node == parent.right && parent == grandparent.left) {

            next = lrightRotation(node, parent, grandparent, ancestor);

        } else if (node == parent.left && parent == grandparent.right) {

            next = rleftRotation(node, parent, grandparent, ancestor);

        } else if (node == parent.right && parent == grandparent.right) {

            next = leftRotation(parent, grandparent, ancestor);

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

    /*
        case:   g
               / \
              p
             / \
            n
     */
    private Node rightRotation(Node parent, Node grandparent, Node ancestor) {

        grandparent.left = parent.right;
        parent.parent = ancestor;
        parent.right = grandparent;
        grandparent.parent = parent;

        bindAncestor(grandparent, ancestor, parent);
        return parent;
    }

    /*
        case:   g
               / \
              p
             / \
                n
     */
    private Node lrightRotation(Node node, Node parent, Node grandparent, Node ancestor) {

        node.parent = ancestor;
        grandparent.left = node.right;
        if (node.right != null) {
            node.right.parent = grandparent;
        }
        parent.right = node.left;
        node.left = parent;
        node.right = grandparent;
        parent.parent = node;
        grandparent.parent = node;

        bindAncestor(grandparent, ancestor, node);
        return node;
    }

    /*
        case:   g
               / \
                  p
                 / \
                    n
     */
    private Node leftRotation(Node parent, Node grandparent, Node ancestor) {

        grandparent.right = parent.left;
        parent.parent = ancestor;
        parent.left = grandparent;
        grandparent.parent = parent;

        bindAncestor(grandparent, ancestor, parent);
        return parent;
    }

    /*
        case:   g
               / \
                  p
                 / \
                n
     */
    private Node rleftRotation(Node node, Node parent, Node grandparent, Node ancestor) {

        node.parent = ancestor;
        grandparent.right = node.left;
        if (node.left != null) {
            node.left.parent = grandparent;
        }
        node.left = grandparent;
        parent.left = node.right;
        node.right = parent;
        grandparent.parent = node;
        parent.parent = node;

        bindAncestor(grandparent, ancestor, node);
        return node;
    }

    //helper method for the rotations
    private void bindAncestor(Node g, Node a, Node n) {
        if (a != null) {
            if (g == a.left) {
                a.left = n;

            } else if (g == a.right) {
                a.right = n;
            }
        }
    }

    Iterator iterator() {
        return new RBTIterator(root);
    }

    int size() {
        return size;
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean contains(T element) {
        Node n = root;
        while (n != null && n.value.compareTo(element) != 0) {

            if (element.compareTo(n.value) > 0) {
                n = n.right;
            } else if (element.compareTo(n.value) < 0) {
                n = n.left;
            }
        }
        return n != null;
    }
}