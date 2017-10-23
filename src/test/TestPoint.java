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
		assertEquals(5, testPoint.getDistanceToPoint(5, 0));
		assertEquals(9, testPoint.getDistanceToPoint(0, 9));
		assertEquals(10, testPoint.getDistanceToPoint(5, 9));
	}
	
	@Test
	public void testGetDistanceToSamePoint() {
		assertEquals(0, testPoint.getDistanceToPoint(testPoint.getLatitude(), testPoint.getLongitude()));
	}
	
	@Test
	public void testGetPointUp(){
		Point nextPoint = new Point(0, 10);
		
		//GOING UP FROM ZERO
		Point resultPoint = Point.calculatePoint(5, testPoint, nextPoint);
		assertEquals(0, resultPoint.getLatitude(), 0);
		assertEquals(5, resultPoint.getLongitude(), 0);
		
		//GOIN UP FROM VALUE DIFFERENT FROM ZERO (5 IN THIS CASE)
		testPoint.setLongitude(5);
		resultPoint = Point.calculatePoint(5, testPoint, nextPoint);
		assertEquals(0, resultPoint.getLatitude(), 0);
		assertEquals(10, resultPoint.getLongitude(), 0);
	}
	
	@Test
	public void testGetPointForward(){
		Point nextPoint = new Point(10,0);
		
		//GOING FORWARD FROM ZERO
		Point resultPoint = Point.calculatePoint(5, testPoint, nextPoint);
		assertEquals(5, resultPoint.getLatitude(), 0);
		assertEquals(0, resultPoint.getLongitude(), 0);
		
		//GOIN UP FROM VALUE DIFFERENT FROM ZERO (5 IN THIS CASE)
		testPoint.setLatitude(5);
		resultPoint = Point.calculatePoint(5, testPoint, nextPoint);
		assertEquals(10, resultPoint.getLatitude(), 0);
		assertEquals(0, resultPoint.getLongitude(), 0);
	}
	
	@Test
	public void testGetPointDown(){
		Point nextPoint = new Point(0,10);
		
		//GOING DOWN
		Point resultPoint = Point.calculatePoint(5, nextPoint, testPoint);
		assertEquals(0, resultPoint.getLatitude(), 0);
		assertEquals(5, resultPoint.getLongitude(), 0);
	}
	
	@Test
	public void testGetPointBackward(){
		Point nextPoint = new Point(10, 0);
		
		//GOIN BACK
		Point resultPoint = Point.calculatePoint(5, nextPoint, testPoint);
		assertEquals(5, resultPoint.getLatitude(), 0);
		assertEquals(0, resultPoint.getLongitude(), 0);
	}
	
	@Test
	public void testGetPointUpForward(){
		// POINT 9, 12 WILL FORM THE TRIANGLE WITH SIDES 9 12 15
		Point nextPoint = new Point(9, 12);
		
		// GOING UP FORWARD FROM ZERO
		Point resultPoint = Point.calculatePoint(5, testPoint, nextPoint);
		assertEquals(3, resultPoint.getLatitude(), 0);
		
		// GOING UP FORWARD FROM POINT DIFFERENT FROM ZERO
		testPoint = new Point(3, 4);
		resultPoint = Point.calculatePoint(5, testPoint, nextPoint);
		assertEquals(6, resultPoint.getLatitude(), 0);
		assertEquals(8, resultPoint.getLongitude(), 0);
		
	}
	
	@Test
	public void testGetPointDownForward(){
		Point startPoint = new Point(5, 14);
		Point endPoint = new Point(14, 2);
		Point resultPoint = (Point.calculatePoint(5, startPoint, endPoint));
		assertEquals(8, resultPoint.getLatitude(), 0);
		assertEquals(10, resultPoint.getLongitude(), 0);
		
	}
	
	@Test
	public void testGetPointDownBackward(){
		Point startPoint = new Point(12, 16);
		Point endPoint = new Point(3, 4);
		Point resultPoint = Point.calculatePoint(5, startPoint, endPoint);
		assertEquals(9, resultPoint.getLatitude(), 0);
		assertEquals(12, resultPoint.getLongitude(), 0);
	}
	
	@Test
	public void testGetPoindUpBackward(){
		Point startPoint = new Point(13, 7);
		Point endPoint = new Point(4, 19);
		Point resultPoint = Point.calculatePoint(5, startPoint, endPoint);
		assertEquals(10, resultPoint.getLatitude(), 0);
		assertEquals(11, resultPoint.getLongitude(), 0);
	}
	
//	@Test
//	public void testGetPointWithInvalidDistance(){
//		Point startPoint = new Point(0, 0);
//		Point endPoint = new Point(3, 4);
//		Point resultPoint = Point.calculatePoint(6, startPoint, endPoint);
//		assertEquals(startPoint.getLatitude(), resultPoint.getLatitude(), 0);
//		assertEquals(startPoint.getLongitude(), resultPoint.getLongitude(), 0);
//	}
	
	@Test
	public void testGetPointWithInvalidDistance(){
		Point startPoint = new Point (3,4);
		Point endPoint = new Point (6,8);
		Point resultPoint = Point.calculatePoint(12, startPoint, endPoint);
		assertEquals(startPoint.getLatitude(), resultPoint.getLatitude(), 0);
		assertEquals(startPoint.getLongitude(), resultPoint.getLongitude(), 0);
	}
	
}
