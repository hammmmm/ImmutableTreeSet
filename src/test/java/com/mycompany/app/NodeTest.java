package com.mycompany.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.lang.Integer;
import java.util.Random;

/**
 * Unit test for simple App.
 */
public class NodeTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public NodeTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( NodeTest.class );
    }

	public void testNode() {
		assertTrue(true);
	}
}
