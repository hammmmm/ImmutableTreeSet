package com.mycompany.app;

import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Every node is colored with either red or black.
// All leaf (nil) nodes are colored with black; if a node’s child is missing then we will assume that it has a nil child in that place and this nil child is always colored black.
// Both children of a red node must be black nodes.
// Every path from a node n to a descendent leaf has the same number of black nodes (not counting node n).

class RBTreeSet<T extends Comparable> {

    ArrayList<Node> pastRoots;
    Node root;
    private int size;

    RBTreeSet() {
        pastRoots = new ArrayList();
        root = null;
        size = 0;
    }

    void add(T element) {

//        pastRoots.add(0, root);
//        root = root.copy();
        Node current = root;
        boolean added = false;
        while (!added) {

            if (root == null) {
                root = new Node(element, Color.BLACK);
                added = true;
            } else if (element.compareTo(current.value) == 0) {
                //remove newest root path and return set to it's previous state
//                root = pastRoots.get(0);
//                pastRoots.remove(0);
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
//                current = current.copy();
                current = current.left;
            } else if (element.compareTo(current.value) > 0 && current.right != null) {
//                current = current.copy();
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

    private Node rightRotation(Node parent, Node grandparent, Node ancestor) {

        grandparent.left = parent.right;
//        parent.right.parent = grandparent;
        parent.parent = ancestor;
        parent.right = grandparent;
        grandparent.parent = parent;

        bindAncestor(grandparent, ancestor, parent);
        return parent;
    }

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

    private Node leftRotation(Node parent, Node grandparent, Node ancestor) {

        grandparent.right = parent.left;
        parent.parent = ancestor;
        parent.left = grandparent;
        grandparent.parent = parent;

        bindAncestor(grandparent, ancestor, parent);
        return parent;
    }

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
        while (current != null && !element.equals(current.value)) {

            if (element.compareTo(current.value) < 0) {
                current = current.left;
            } else if (element.compareTo(current.value) > 0) {
                current = current.right;
            }
        }

        if (current == null) {
            throw new NoSuchElementException();
        }

        delete(current);

//        if (current.left != null && current.right != null) {
//
//            if (current.color == Color.RED) {
//
//                current = swapSuccessor(current);
//                delete(current);
//
//            } else if (current.color == Color.BLACK) {
//
//                current = swapSuccessor(current);
//                delete(current);
//            }
//        }
    }

    private void replaceNode(Node n, Node replacement) {
//        if (n == n.parent.left) {
//            n.parent.left = replacement;
//        } else if (n == n.parent.right) {
//            n.parent.right = replacement;
//        }
//        if (replacement != null) {
//            replacement.parent = n.parent;
//        }
//        return replacement;
        if (n.parent == null) {
            root = replacement;
        } else if (n == n.parent.left) {
            n.parent.left = replacement;
        } else if (n == n.parent.right) {
            n.parent.right = replacement;
        }
        if (replacement != null) {
            if (replacement == replacement.parent.right) {
                replacement.parent.right = null;
            }else if (replacement == replacement.parent.left) {
                replacement.parent.left = null;
            }
            replacement.parent = n.parent;
            replacement.right = n.right;
            replacement.left = n.left;
            if (replacement.left != null) {
                replacement.left.parent = replacement;
            }
            if (replacement.right != null) {
                replacement.right.parent = replacement;
            }
//            replacement.color = n.color;
        }
    }

    private void delete(Node n) {

        Node y = n;
        Node x;
        Color originalColor = y.color;
        if (n.left == null) {
            x = n.right;
            replaceNode(n, n.right);
        } else if (n.right == null) {
            x = n.left;
            replaceNode(n, n.left);
        } else {
            y = n.successor();
            if (y != null) {
                originalColor = y.color;
                x = y.right;
            } else {
                originalColor = Color.BLACK;
                x = null;
            }

            if (y != null && y.parent == n && x != null) {
                x.parent = y;
            } else if (y != null) {
//                replaceNode(y, y.right);
//                y.right = n.right;
//                y.right.parent = y;
//                y.parent = n.parent;
            }
            replaceNode(n, y);
//            y.left = n.left;
//            y.left.parent = y;
//            y.right = n.right;
//            y.right.parent = y;
            if (x != null) {
                x.color = y.color;
            }
            y.color = n.color;
        }
//        if (originalColor == Color.BLACK) {
//            deleteAdjust(x);
//        }
    }


    private void deleteAdjust(Node n) {
        while (n != null && n != root && n.color != Color.BLACK) {

            if (n == n.parent.left) {

                Node w = n.parent.right;
                if (w != null && w.color == Color.RED) {
                    w.color = Color.BLACK;
                    n.parent.color = Color.RED;
                    restructure(n.parent);
                    w = n.parent.right;
                }

                if (w != null && w.left != null && w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    n = n.parent;
                } else if (w != null){
                    if (w.right.color == Color.BLACK) {
                        w.left.color = Color.BLACK;
                        w.color = Color.RED;
                        restructure(w);
                        w = n.parent.right;
                    }

                    w.color = n.parent.color;
                    n.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    restructure(n);
                    n = root;
                }
            } else {
                Node w = n.parent.left;
                if (w != null && w.color == Color.RED) {
                    w.color = Color.BLACK;
                    n.parent.color = Color.RED;
                    restructure(n.parent);
                    w = n.parent.left;
                }

                if (w != null && w.right != null && w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    n = n.parent;
                } else if (w != null){
                    if (w.left.color == Color.BLACK) {
                        w.right.color = Color.BLACK;
                        w.color = Color.RED;
                        restructure(w);
                        w = n.parent.left;
                    }
                    w.color = n.parent.color;
                    n.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    restructure(n);
                    n = root;
                }
            }
        }
    }



    //do successors need to match the color of their counterparts???
    private Node swapSuccessor(Node n) {
        Node s = n.successor();
        Node temp = s.parent;
        s.parent = n.parent;

        if (n == n.parent.left) {
            n.parent.left = s;
        } else if (n == n.parent.right) {
            n.parent.right = s;
        }

        if (s == temp.left) {
            temp.left = n;
        } else if (s == temp.right) {
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