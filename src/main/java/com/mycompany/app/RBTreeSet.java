package com.mycompany.app;

import java.lang.Iterable;
import java.lang.Comparable;
import java.util.Iterator;
import java.lang.System;
// Every node is colored with either red or black.
// All leaf (nil) nodes are colored with black; if a node’s child is missing then we will assume that it has a nil child in that place and this nil child is always colored black.
// Both children of a red node must be black nodes.
// Every path from a node n to a descendent leaf has the same number of black nodes (not counting node n). We call this number the black height of n, which is denoted by bh(n).

public class RBTreeSet<T extends Comparable<T>> implements ISet<T> {

	Node<T> root;
	private int size;

	public RBTreeSet() {
		root = null;
		size = 0;
	}

	public void add(T element) {
		insert(element, root);
		size++;
                root.color = Color.BLACK;
	}

	//helper method to add so as not to expose root node
	private void insert(T element, Node<T> root) {

		if (this.root == null) {

			this.root = new Node<T>(element, Color.BLACK);
		}
		else if (element.compareTo(root.value) < 0 && root.left == null) {

			root.left = new Node<T>(element, Color.RED);
			root.left.parent = root;
			insertAdjust(root);
		}
		else if (element.compareTo(root.value) > 0 && root.right == null) {

			root.right = new Node<T>(element, Color.RED);
			root.right.parent = root;
			insertAdjust(root);
		}
		else if (element.compareTo(root.value) < 0 && root.left != null) {

			insert(element, root.left);
		}
		else if (element.compareTo(root.value) > 0 && root.right != null) {

			insert(element, root.right);
		}
		
		
	}

	private void insertAdjust(Node<T> node) {

		if (node != null && node != root && node.color == Color.RED) {

			//recolors and then looks to see if anything else needs to be adjusted
			if (node.sibling() != null && node.sibling().color == Color.RED
                                 ) {

				node.color = Color.BLACK;
				node.sibling().color = Color.BLACK;
				node.parent.color = Color.RED;
				insertAdjust(node.parent.parent);
			}
			//left rotate
			else if (node.right != null && node.right.color == Color.RED) {
			
				rotateLeft(node);
//				insertAdjust(node.parent);
			}
			// right rotate
			else if (node.left != null && node.left.color == Color.RED) {
				
				rotateRight(node);
				insertAdjust(node.parent);
			}
		}
	}


	private void rotateRight(Node<T> node) {
		
		Node<T> p = node.parent;
		node.parent = p.parent;
		p.parent = node;
                p.left = node.right;
                node.right = p;
                if (node.parent == null) {
                    this.root = node;
                    node.color = Color.BLACK;
                }
                if (node.color == Color.RED) {
                    node.left.color = Color.BLACK;
                    node.right.color = Color.BLACK;
                }
                else {
                    p.color = Color.RED;
                }       
        }

	private void rotateLeft(Node<T> node) {

		Node<T> p = node.parent;
                Color temp = p.color;
                p.color = node.color;
                node.color = temp;
		node.parent = p.parent;
                if (node.parent != null) {
                    node.parent.right = node;
                }
		p.parent = node;
                p.right = node.left;
                node.left = p;
                if (node.parent == null) {
                    this.root = node;
                    node.color = Color.BLACK;
                }
                if (node.color == Color.RED) {
                    node.left.color = Color.BLACK;
                    node.right.color = Color.BLACK;
                }
                
              
	}

	public void remove(T element) {

	}

	private void delete(T x, Node<T> root) {

	}

	private void deleteAdjust(Node<T> node) {

	}	

	// public Iterator iterator() {

	// }

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	} 

	public void print() {
		print(root);
	}

	private void print(Node<T> root) {
		
		if (root.left != null) {
			print(root.left);
		}
		if (root != null) {
			System.out.print("(" + root.value + "," + root.color + ")");
		}
		if (root.right != null) {
			print(root.right);
		}
	}

	public void Print() {

	}
}