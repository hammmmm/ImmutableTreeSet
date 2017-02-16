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
		if (this == parent.left){
                    return parent.right;
                }
                return parent.left;
	}
        
        Node<T> uncle() {
            if(parent != null && parent.parent != null) {
				return parent.sibling();
			}
            return null;
	}
}