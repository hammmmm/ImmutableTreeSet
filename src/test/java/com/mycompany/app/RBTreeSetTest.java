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

        for (int i = 1; i <= 100; i++) {
            int x = r.nextInt(50);
            tree.add(new Integer(x));
        }
        assertTrue(inOrder(tree.root));
    }

    private boolean inOrder(Node<Integer> root){
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

//     confirms that n <= 2^k+1 where n <- node_count and k <- maximum depth
     public void testDepth() {

         for(int j = 0; j < 100; j++) {
             Random r = new Random();
             RBTreeSet<Integer> tree = new RBTreeSet<Integer>();

             for (int i = 0; i < j; i++) {
                 int x = r.nextInt(50);
                 tree.add(new Integer(x));
             }
             assertTrue(tree.size() <= Math.pow(2, maxDepth(tree.root)+1) - 1);
         }
     }

    private int maxDepth(Node<Integer> root) {
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

//    public void isRedBLack(Node<Integer> root) {
//
//
//    }
}
