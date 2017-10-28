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
		testPoint = new Point(10, 10);
	}
	
	@After // tearDown()
	public void after() throws Exception {
		testPoint = null;
	}
	
	@Test
	public void testEqualsSamePoint() {
		assertTrue(testPoint.equals(testPoint));
	}
	
	@Test
	public void testGetDistanceToSamePoint() {
		assertEquals(0, testPoint.getDistanceToPoint(testPoint), 0);
	}
	
	@Test
	public void testGetDistanceToDifferentPoint() {
		Point differentPoint = new Point(0, 0);
		assertTrue(testPoint.getLatitude()!= differentPoint.getLatitude());
		assertTrue(testPoint.getLongitude() != differentPoint.getLongitude());
		assertFalse(testPoint.equals(differentPoint));
	}
	
	@Test
	public void testGetDistanceToUpPoint() {
		//TOP (SAME LATITUDE GREATHER LONGITUDE)
		Point finishPoint = new Point(10, 11);
		assertEquals(testPoint.getLatitude(), finishPoint.getLatitude(), 0);
		assertTrue(finishPoint.getLongitude() > testPoint.getLongitude());
		assertEquals(1, testPoint.getDistanceToPoint(finishPoint), 0);
	}
	
	@Test
	public void testGetDistanceToRightPoint() {
		//TOP (GREATHER LATITUDE SAME LONGITUDE)
		Point finishPoint = new Point(11, 10);
		assertEquals(testPoint.getLongitude(), finishPoint.getLongitude(), 0);
		assertTrue(finishPoint.getLatitude() > testPoint.getLatitude());
		assertEquals(1, testPoint.getDistanceToPoint(finishPoint), 0);
	}
	
	@Test
	public void testGetDistanceToDownPoint() {
		//TOP (SAME LATITUDE MINOR LONGITUDE)
		Point finishPoint = new Point(10, 9);
		assertEquals(testPoint.getLatitude(), finishPoint.getLatitude(), 0);
		assertTrue(finishPoint.getLongitude() < testPoint.getLongitude());
		assertEquals(1, testPoint.getDistanceToPoint(finishPoint), 0);
	}
	
	@Test
	public void testGetDistanceToLeftPoint() {
		//TOP (GREATHER LATITUDE SAME LONGITUDE)
		Point finishPoint = new Point(9, 10);
		assertEquals(testPoint.getLongitude(), finishPoint.getLongitude(), 0);
		assertTrue(finishPoint.getLatitude() < testPoint.getLatitude());
		assertEquals(1, testPoint.getDistanceToPoint(finishPoint), 0);
	}
	
	@Test
	public void testGetDistanceToUpRightPoint() {
		//TOP (SAME LATITUDE GREATHER LONGITUDE)
		Point finishPoint = new Point(11, 11);
		assertTrue(finishPoint.getLatitude() > testPoint.getLatitude());
		assertTrue(finishPoint.getLongitude() > testPoint.getLongitude());
		assertEquals(1, testPoint.getDistanceToPoint(finishPoint), 0.5);
	}
	
	@Test
	public void testGetDistanceToDownRightPoint() {
		//TOP (SAME LATITUDE GREATHER LONGITUDE)
		Point finishPoint = new Point(11, 9);
		assertTrue(finishPoint.getLatitude() > testPoint.getLatitude());
		assertTrue(finishPoint.getLongitude() < testPoint.getLongitude());
		assertEquals(1, testPoint.getDistanceToPoint(finishPoint), 0.5);
	}
	
	@Test
	public void testGetDistanceToDownLeftPoint() {
		//TOP (SAME LATITUDE GREATHER LONGITUDE)
		Point finishPoint = new Point(9, 9);
		assertTrue(finishPoint.getLatitude() < testPoint.getLatitude());
		assertTrue(finishPoint.getLongitude() < testPoint.getLongitude());
		assertEquals(1, testPoint.getDistanceToPoint(finishPoint), 0.5);
	}
	
	@Test
	public void testGetDistanceToUpLeftPoint() {
		//TOP (SAME LATITUDE GREATHER LONGITUDE)
		Point finishPoint = new Point(9, 11);
		assertTrue(finishPoint.getLatitude() < testPoint.getLatitude());
		assertTrue(finishPoint.getLongitude() > testPoint.getLongitude());
		assertEquals(1, testPoint.getDistanceToPoint(finishPoint), 0.5);
	}
}
