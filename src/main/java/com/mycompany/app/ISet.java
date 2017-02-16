package com.mycompany.app;

import java.util.Iterator;
import java.lang.Comparable;

public interface ISet<T extends Comparable> {

	void add(T element);

	void remove(T element);

	// Iterator iterator();

	int size();

	boolean isEmpty();
}