package com.mycompany.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.lang.Integer;
import java.util.Random;
import java.lang.Math;


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

    /**
     * confirms that the tree properly tracks it's own size
     */
	public void testSize() {

		RBTreeSet<Integer> tree = new RBTreeSet<Integer>();
		assertTrue(tree.isEmpty());
        
        for (int i = 1; i <= 100; i++) {
            tree.add(i);
            assertEquals(tree.size(), i);
        }
	}

    /**
     * Confirms that the tree keeps elements in order
     */
    public void testAdd() {

        Random r = new Random();
        RBTreeSet<Integer> tree = new RBTreeSet();

        for (int i = 1; i <= 100; i++) {
            int x = r.nextInt(50);
            x = r.nextInt(50) >= 25 ? x * -1 : x;
            tree.add(x);
        }
        assertTrue(inOrder(tree.root));
    }

    private boolean inOrder(Node root){
        if (root == null) {
            return true;
        }
        else if (root.left != null && root.left.value.compareTo(root.value) > 0) {
            return false;
        }
        else if (root.right != null && root.right.value.compareTo(root.value) < 0) {
            return false;
        }
        else {
            return inOrder(root.left) && inOrder(root.right);
        }
    }

    /**
     * A full and complete binary tree with n nodes and maximum depth k (the depth of the leaf nodes) has the following relationship:
        n=2^(k+1)−1
     * this confirms that n <= 2^(k+1) -1 where n is node_count and k is maximum depth
     */
    public void testDepth() {

         for(int j = 0; j < 100; j++) {
             Random r = new Random();
             RBTreeSet<Integer> tree = new RBTreeSet<Integer>();

             for (int i = -j; i <= j; i++) {
                 int x = r.nextInt(50);
                 tree.add(x);
             }
             assertTrue(tree.size() <= Math.pow(2, maxDepth(tree.root)+1) - 1);
         }
     }

    private int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }

        int ldepth = maxDepth(root.left);
        int rdepth = maxDepth(root.right);

        if (ldepth > rdepth) {
            return ldepth + 1;
        } else {
            return rdepth + 1;
        }
    }

    /**
     * verifies that the contains method returns true when appropriate
     */
    public void testContains() {
        RBTreeSet<Integer> tree = new RBTreeSet();
        for (int i = 0; i < 50; i++) {
            tree.add(i);
            assertTrue(tree.contains(i));
        }
        for (int i = 51; i < 100; i++) {
            assertTrue(!tree.contains(i));
        }
    }
}
