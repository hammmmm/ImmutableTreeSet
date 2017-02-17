package com.mycompany.app;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.lang.Iterable;
import java.lang.Comparable;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Every node is colored with either red or black.
// All leaf (nil) nodes are colored with black; if a node’s child is missing then we will assume that it has a nil child in that place and this nil child is always colored black.
// Both children of a red node must be black nodes.
// Every path from a node n to a descendent leaf has the same number of black nodes (not counting node n). We call this number the black height of n, which is denoted by bh(n).

public class RBTreeSet<T extends Comparable> implements ISet<T>, Iterable {

    Node root;
    private int size;

    public RBTreeSet() {
        root = null;
        size = 0;
    }

    public void add(T element) {

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

    private Node rightRotation(Node parent, Node grandparent, Node ancestor) {

            Node next = parent;
            grandparent.left = parent.right;
            parent.parent = ancestor;
            parent.right = grandparent;
            grandparent.parent = parent;

            bindAncestor(grandparent, ancestor, parent);
            return next;
    }

    private Node lrightRotation(Node node, Node parent, Node grandparent, Node ancestor) {
        Node next = node;
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
        return next;
    }

    private Node leftRotation(Node parent, Node grandparent, Node ancestor) {
        Node next = parent;
        grandparent.right = parent.left;
        parent.parent = ancestor;
        parent.left = grandparent;
        grandparent.parent = parent;

        bindAncestor(grandparent, ancestor, parent);
        return next;
    }

    private Node rleftRotation(Node node, Node parent, Node grandparent, Node ancestor) {
        Node next = node;
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
        return next;
    }

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

        Node current = root;
        while (current != null && element != current.value) {

            if (element.compareTo(current.value) < 0) {
                current = current.left;
            } else if (element.compareTo(current.value) > 0) {
                current = current.right;
            }
        }

        if (current == null) {
            throw new NoSuchElementException();
        }

        if (current.left != null && current.right != null) {

            if (current.color == Color.RED) {

                current = swapSuccessor(current);
                delete(current);

            } else if (current.color == Color.BLACK) {

                current = swapSuccessor(current);
                delete(current);
            }
        }
    }

    private Node replaceNode(Node n, Node replacement) {
        if (n == n.parent.left) {
            n.parent.left = replacement;
        }
        else if (n == n.parent.right) {
            n.parent.right = replacement;
        }
        if (replacement != null) {
            replacement.parent = n.parent;
        }
        return replacement;
    }

    private void delete(Node n) {

        if (n.color == Color.RED && n.left == null && n.right == null) {
            replaceNode(n, null);
        }
        else if (n.color == Color.BLACK && n.right != null && n.right.color == Color.RED) {
            Node r = replaceNode(n, n.right);
            n.right.color = Color.BLACK;
        }
        else if (n.color == Color.BLACK && (n.right == null || n.right.color == Color.BLACK)
                && (n.left == null || n.left.color == Color.BLACK)) {

            Node r = replaceNode(n, new Node(Color.DOUBLEBLACK));
            r.left = n.left;
            r.right = n.right;
            dbOne(r);
        }
    }

    /** terminal
     * node is root && doubleblack
     * @param n
     */
    private void dbOne(Node n) {

        if (n == root) {

        }
        else {
            dbTwo(n);
        }
    }

    /**
     * node is doubleblack
     * sibling is red
     * parent is black
     * @param n
     */
    private void dbTwo(Node n) {

        if (n.sibling() != null && n.sibling().color == Color.RED && n.parent.color == Color.BLACK){

        }
        dbThree(n);
    }

    /**
     * node is doubleblack
     * sibling is black with black children
     * parent is black
     * @param n
     */
    private void dbThree(Node n) {

        if (n.sibling() != null && n.sibling().color == Color.BLACK && n.parent.color == Color.BLACK
                && (n.sibling().right == null || n.sibling().right.color == Color.BLACK)
                && (n.sibling().left == null || n.sibling().left.color == Color.BLACK)) {

        }
        dbFour(n);
    }


    /** terminal
     * node is double black
     * parent is red
     * sibling is black with black children
     */
    private void dbFour(Node n) {

        if (n.sibling() != null && n.sibling().color == Color.BLACK && n.parent.color == Color.RED
                && (n.sibling().right == null || n.sibling().right.color == Color.BLACK)
                && (n.sibling().left == null || n.sibling().left.color == Color.BLACK)) {

            n.sibling().color = Color.RED;
            n.parent.color = Color.BLACK;
            n.parent.left = n.right;
            n.right.parent = n.parent;
        }
        else {
            dbFive(n);
        }
    }

    /**
     * node is doublblack
     * parent is black
     * sibling is black with one red left child
     * @param n
     */
    private void dbFive(Node n) {

        if (n.sibling() != null && n.sibling().color == Color.BLACK && n.parent.color == Color.BLACK
                && n.sibling().left != null && n.left.color == Color.RED) {

        }
        dbSix(n);
    }

    /** terminal
     * node is doubleblack
     * parent is ambiguous
     * sibling is black with at least one red child
     * @param n
     */
    private void dbSix(Node n) {

        if (n.sibling() != null && n.sibling().color == Color.BLACK
                && n.sibling().right != null && n.right.color == Color.RED) {

        }
    }

    //do successors need to match the color of their counterparts???
    private Node swapSuccessor(Node n) {
        Node s = n.successor();
        Node temp = s.parent;
        s.parent = n.parent;

        if (n == n.parent.left) {
            n.parent.left = s;
        }
        else if (n == n.parent.right) {
            n.parent.right = s;
        }

        if (s == temp.left) {
            temp.left = n;
        }
        else if (s == temp.right) {
            temp.right = n;
        }

        temp = s.right;
        s.right = n.right;
        s.right.parent = s;
        s.left = n.left;
        s.left.parent = s;
        n.left = null;
        n.right = temp;
        return n;
    }

    private void deleteAdjust(Node node) {

    }

    @Override
    public Iterator iterator() {
        return new RBTIterator(root);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}