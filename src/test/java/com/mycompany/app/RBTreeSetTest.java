package com.mycompany.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.lang.Integer;
import java.util.Random;

/**
 * Unit test for simple App.
 */
public class RBTreeSetTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RBTreeSetTest( String testName )
    {
        super( testName );
    }

	public static Test suite()
    {
        return new TestSuite( RBTreeSetTest.class );
    }

	public void testSize() {

		RBTreeSet<Integer> tree = new RBTreeSet<Integer>();
		assertTrue(tree.isEmpty());
        
        for (int i = 1; i <= 10; i++) {
            tree.add(new Integer(i));
            assertEquals(tree.size(), i);
        }
	}

    public void testAdd() {

        Random r = new Random();
        RBTreeSet<Integer> tree = new RBTreeSet<Integer>();
        
        for (int i = 1; i <= 10; i++) {
            int x = r.nextInt(50);
            tree.add(new Integer(x));
        }
        assertTrue(inOrder(tree.root));
    }

    private boolean inOrder(Node<Integer> root){
        if (root == null) {
            return true;
        }
        else if (root.left.value.compareTo(root.value) > 0) {
            return false;
        }
        else if (root.right.value.compareTo(root.value) < 0) {
            return false;
        }
        else {
            return inOrder(root.left) && inOrder(root.right);
        }
    }
}
