package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import code.Bus;
import code.Line;
import code.Point;
import code.Route;

public class TestRoute {

	Route testRoute;
	@Before
	public void setData(){
		testRoute = new Route();
	}
	
	@After // tearDown()
	public void after() throws Exception {
		testRoute = null;
	}
	
	@Test
	public void testAddPoint() {
		Point expectedPoint = new Point(0, 0);
		assertEquals(0, testRoute.getPointsQuantity());
		assertTrue(testRoute.addPoint(expectedPoint.getLatitude(), expectedPoint.getLongitude()));
		assertEquals(1, testRoute.getPointsQuantity());
		assertEquals(expectedPoint.getLatitude(), testRoute.getPointAtIndex(0).getLatitude(), 0);
		assertEquals(expectedPoint.getLongitude(), testRoute.getPointAtIndex(0).getLongitude(), 0);
	}
	
	@Test
	public void assertThatRouteDoesNotHaveTwoEqualsPoint() {
		Point testPoint = new Point(0, 0);
		assertEquals(0, testRoute.getPointsQuantity());
		assertTrue(testRoute.addPoint(testPoint.getLatitude(), testPoint.getLongitude()));
		assertEquals(1, testRoute.getPointsQuantity());
		assertEquals(testPoint.getLatitude(), testRoute.getPointAtIndex(0).getLatitude(), 0);
		assertEquals(testPoint.getLongitude(), testRoute.getPointAtIndex(0).getLongitude(), 0);
		assertFalse(testRoute.addPoint(testPoint.getLatitude(), testPoint.getLongitude()));
	}
	
	@Test
	public void testGetIndexOfPointWhenFirstPoint() {
		Point testPoint = new Point(0, 0);
		assertEquals(0, testRoute.getPointsQuantity());
		testRoute.addPoint(testPoint);
		assertEquals(1, testRoute.getPointsQuantity());
		assertEquals(0, testRoute.getIndexOfPoint(testPoint));
	}
	
	@Test
	public void testGetIndexOfPointWhenLastPoint() {
		Point firstTestPoint = new Point(0, 0);
		Point secondTestPoint = new Point(1, 1);
		Point thirdTestPoint = new Point(2, 2);
		assertEquals(0, testRoute.getPointsQuantity());
		testRoute.addPoint(firstTestPoint);
		assertEquals(1, testRoute.getPointsQuantity());
		testRoute.addPoint(secondTestPoint);
		assertEquals(2, testRoute.getPointsQuantity());
		testRoute.addPoint(thirdTestPoint);
		assertEquals(3, testRoute.getPointsQuantity());
		assertEquals(2, testRoute.getIndexOfPoint(thirdTestPoint));
	}
	
	@Test
	public void testGetIndexOfPointWhenMiddlePoint() {
		Point firstTestPoint = new Point(0, 0);
		Point secondTestPoint = new Point(1, 1);
		Point thirdTestPoint = new Point(2, 2);
		assertEquals(0, testRoute.getPointsQuantity());
		testRoute.addPoint(firstTestPoint);
		assertEquals(1, testRoute.getPointsQuantity());
		testRoute.addPoint(secondTestPoint);
		assertEquals(2, testRoute.getPointsQuantity());
		testRoute.addPoint(thirdTestPoint);
		assertEquals(3, testRoute.getPointsQuantity());
		assertEquals(1, testRoute.getIndexOfPoint(secondTestPoint));
	}
	
	@Test
	public void testGetNextPoint() {
		Point firstTestPoint = new Point(0, 0);
		Point secondTestPoint = new Point(1, 1);
		Point expectedPoint = new Point(0, 0);
		assertTrue(firstTestPoint.equals(expectedPoint));
		assertEquals(0, testRoute.getPointsQuantity());
		testRoute.addPoint(firstTestPoint);
		assertEquals(1, testRoute.getPointsQuantity());
		testRoute.addPoint(secondTestPoint);
		assertEquals(2, testRoute.getPointsQuantity());
		assertTrue(secondTestPoint.equals(testRoute.getNextPoint(firstTestPoint)));
	}
	
	@Test
	public void testGetRouteSize() {
		testRoute.addPoint(0, 1);
		testRoute.addPoint(0, 4);
		testRoute.addPoint(0, 8);
		testRoute.addPoint(0, 12);
		assertEquals(22, testRoute.getSize());
	}

}
