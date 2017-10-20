package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import code.Point;

public class TestPoint {

	Point testPoint;
	@Before
	public void setData(){
		testPoint = new Point(0, 0);
	}
	
	@After // tearDown()
	public void after() throws Exception {
		testPoint = null;
	}
	
	@Test
	public void testGetDistanceToDifferentPoint() {
		assertEquals(0, testPoint.getLatitude(), 0);
		assertEquals(0, testPoint.getLongitude(), 0);
		assertEquals(5, testPoint.getDistanceToPoint(5, 0));
		assertEquals(9, testPoint.getDistanceToPoint(0, 9));
		assertEquals(10, testPoint.getDistanceToPoint(5, 9));
	}
	
	@Test
	public void testGetDistanceToSamePoint() {
		assertEquals(0, testPoint.getLatitude(), 0);
		assertEquals(0, testPoint.getLongitude(), 0);
		assertEquals(0, testPoint.getDistanceToPoint(testPoint.getLatitude(), testPoint.getLongitude()));
	}

}
