package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

}
