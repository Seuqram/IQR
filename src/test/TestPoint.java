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
	
	@Test
	public void testGetPointWithInvalidDistance(){
		assertEquals(0, testPoint.getLatitude(), 0);
		assertEquals(0, testPoint.getLongitude(), 0);
		Point endPoint = new Point(9, 12);
		assertEquals(15, testPoint.getDistanceToPoint(endPoint.getLatitude(), endPoint.getLongitude()));
		Point point = Point.calculatePoint(16, testPoint, endPoint);
		assertEquals(testPoint.getLatitude(), point.getLatitude(), 0);
		assertEquals(testPoint.getLongitude(), point.getLongitude(), 0);
	}
	
	@Test
	public void testGetPointWithValidDistance(){
		assertEquals(0, testPoint.getLatitude(), 0);
		assertEquals(0, testPoint.getLongitude(), 0);
		Point endPoint = new Point(9, 12);
		assertEquals(15, testPoint.getDistanceToPoint(endPoint.getLatitude(), endPoint.getLongitude()));
		Point point = Point.calculatePoint(5, testPoint, endPoint);
		assertEquals(3, point.getLatitude(), 0);
		assertEquals(4, point.getLongitude(), 0);
	}
	
	
	@Test
	public void testGetPointWhilePointsReturn(){
		assertEquals(0, testPoint.getLatitude(), 0);
		assertEquals(0, testPoint.getLongitude(), 0);
		Point endPoint = new Point(9, 12);
		assertEquals(15, endPoint.getDistanceToPoint(testPoint.getLatitude(), testPoint.getLongitude()));
		System.out.println("patati");
		Point point = Point.calculatePoint(10, endPoint, testPoint);
		assertEquals(3, point.getLatitude(), 0);
		assertEquals(4, point.getLongitude(), 0);
	}
}
