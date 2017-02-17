package com.mycompany.app;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Immutable/Persistent Set where the internal structure is a RBTree
 */
public class ImmutableSet<T extends Comparable> implements ISet<T>, Iterable {

    ArrayList<RBTreeSet<T>> pastVersions;
    RBTreeSet<T> current;

    /**
     * Constructs a new empty set
     */
    public ImmutableSet() {
        current = new RBTreeSet();
        pastVersions = new ArrayList();
    }

    /**
     * Adds an element to the set if the element is not already an element of the set
     * @param element a Comparable object.
     */
    public void add(T element) {

        pastVersions.add(current);
        Iterator iterator = current.iterator();
        current = new RBTreeSet();
        while (iterator.hasNext()) {
            current.add((T) iterator.next());
        }
        current.add(element);

    }

    /**
     * Removes an element from the set. Throws NoSuchElementException if element is not an element of the set
     * @param element a Comparable object
     */
    public void remove(T element) {
        pastVersions.add(current);
        Iterator iterator = current.iterator();
        current = new RBTreeSet();
        while (iterator.hasNext()) {
            T n = (T) iterator.next();
            if (!element.equals(n)) {
                current.add(n);
            }
        }
    }

    /**
     * @return An InOrder Iterator over the set
     */
    public Iterator iterator() {
        return current.iterator();
    }

    /**
     * @return The cardinality of the set
     */
    public int size() {
        return current.size();
    }

    /**
     * @return true if set has no elements, else false
     */
    public boolean isEmpty() {
        return current.size() == 0;
    }

    /**
     * Performs an a binary search on the internal RBTree
     * @param element a Comparable object
     * @return true if element is an element of the set, else false
     */
    public boolean contains(T element) {
        return current.contains(element);
    }
}
