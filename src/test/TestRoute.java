package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import code.Point;
import code.Route;

public class TestRoute {

	Route route;
	@Before
	public void setData(){
		route = new Route();
	}
	
	@After // tearDown()
	public void after() throws Exception {
		route = null;
	}
	
	@Test
	public void testAddPoint() {
		Point expectedPoint = new Point(0, 0);
		assertEquals(0, route.getPointsQuantity());
		assertTrue(route.addPoint(expectedPoint.getLatitude(), expectedPoint.getLongitude()));
		assertEquals(1, route.getPointsQuantity());
		assertEquals(expectedPoint.getLatitude(), route.getPointAtIndex(0).getLatitude(), 0);
		assertEquals(expectedPoint.getLongitude(), route.getPointAtIndex(0).getLongitude(), 0);
	}
	
	@Test
	public void assertThatRouteDoesNotHaveTwoEqualsPoint() {
		Point testPoint = new Point(0, 0);
		assertEquals(0, route.getPointsQuantity());
		assertTrue(route.addPoint(testPoint.getLatitude(), testPoint.getLongitude()));
		assertEquals(1, route.getPointsQuantity());
		assertEquals(testPoint.getLatitude(), route.getPointAtIndex(0).getLatitude(), 0);
		assertEquals(testPoint.getLongitude(), route.getPointAtIndex(0).getLongitude(), 0);
		assertFalse(route.addPoint(testPoint.getLatitude(), testPoint.getLongitude()));
	}

}
