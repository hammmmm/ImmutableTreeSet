package com.mycompany.app;

import java.lang.Comparable;

class Node<T extends Comparable<T>> {

	final T value;
	Color color;
	Node<T> left, right, parent;

	Node(T value, Color color) {
		this.value = value;
		this.color = color;
		left = right = parent = null;
	}

	//will work as long as this != root
	//may return null
	Node<T> sibling() {
		return (value.compareTo(parent.value) > 0) ? parent.right : parent.left;
	}
}