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
	}

	//helper method to add so as not to expose root node
	private void insert(T element, Node<T> root) {

		if (root == null) {

			root = new Node<T>(element, Color.BLACK);
		}
		else if (element.compareTo(root.value) == 0) {

			return;
		}
		else if (element.compareTo(root.value) < 0 && root.left == null) {

			root.left = new Node<T>(element, Color.RED);
			root.left.parent = root;
			insertAdjust(root.left);
		}
		else if (element.compareTo(root.value) > 0 && root.right == null) {

			root.right = new Node<T>(element, Color.RED);
			root.right.parent = root;
			insertAdjust(root.right);
		}
		else if (element.compareTo(root.value) < 0 && root.left != null) {

			insert(element, root.left);
		}
		else if (element.compareTo(root.value) > 0 && root.right != null) {

			insert(element, root.right);
		}
	}

	private void insertAdjust(Node<T> node) {

		if (node != null && node != root && node.parent.color == Color.RED) {

			//recolors and then looks to see if anything else needs to be adjusted
			if (node.parent.sibling() != null && node.parent.sibling().color == Color.RED) {

				node.parent.color = Color.BLACK;
				node.parent.sibling().color = Color.BLACK;
				node.parent.parent.color = Color.RED;
				insertAdjust(node.parent.parent);
			}
			//left rotate
			else if (node == node.parent.right && (node.sibling() == null || node.sibling().color == Color.BLACK)) {
			
				rotateLeft(node);
				insertAdjust(node.right);
			}
			// right rotate
			else if (node == node.parent.left && (node.sibling() == null || node.sibling().color == Color.BLACK)) {
				
				rotateRight(node);
				insertAdjust(node.left);
			}
		}

		root.color = Color.BLACK;
	}


	private void rotateLeft(Node<T> node) {
		node.parent = node.parent.parent;
		node.parent.left.right = node.left;
		node.left = node.parent.left;
		node.left.parent = node;
	}

	private void rotateRight(Node<T> node) {
		node.parent.right.parent = node.parent.parent;
		node.parent.parent.left = node.sibling();
		node.parent.right = node.parent.right.parent;
		node.right.color = Color.RED;
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
		
		if (root == null) {
			return;
		}
		if (root.left != null) {
			print(root.left);
		}
		if (root != null) {
			System.out.print(root.value + ",");
		}
		if (root.right != null) {
			print(root.right);
		}
	}
}