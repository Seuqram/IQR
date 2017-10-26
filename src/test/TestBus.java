package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import code.Bus;
import code.Line;
import code.Point;
import code.Route;

public class TestBus {

	Bus testBus;
	Line testLine;
	@Before
	public void setData(){
		testLine = new Line(107);
		testLine.getRoute().addPoint(0, 1);
		testLine.getRoute().addPoint(0, 4);
		testLine.getRoute().addPoint(0, 8);
		testLine.getRoute().addPoint(0, 12);
		testBus = new Bus("id", testLine);
	}
	
	@After // tearDown()
	public void after() throws Exception {
		testBus = null;
	}
	
	@Test
	public void testNewBusPosition(){
		Point expectedPoint = new Point(
				testLine.getRoute().getPointAtIndex(0).getLatitude(), 
				testLine.getRoute().getPointAtIndex(0).getLongitude());
		assertEquals(expectedPoint.getLatitude(), testBus.getCurrentPosition().getLatitude(), 0);
		assertEquals(expectedPoint.getLongitude(), testBus.getCurrentPosition().getLongitude(), 0);
	}
	
	@Test
	public void testMoveKeepingPreviousPoint() {
		Point routeFirstPoint = testBus.getLine().getRoute().getPointAtIndex(0);
		assertTrue(testBus.move(2));
		assertFalse(testBus.getCurrentPosition().equals(routeFirstPoint));
		assertTrue(testBus.getCurrentPosition().equals(0, 3));
		assertTrue(testBus.getPreviousPoint().equals(testBus.getLine().getRoute().getPointAtIndex(0)));
	}
	
	@Test
	public void testMoveToExactlySecondPoint() {
		Route busRoute = testBus.getLine().getRoute();
		Point routeFirstPoint = busRoute.getPointAtIndex(0);
		assertTrue(testBus.move(3));
		assertFalse(testBus.getCurrentPosition().equals(routeFirstPoint));
		assertTrue(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(1)));
		assertTrue(testBus.getPreviousPoint().equals(busRoute.getPointAtIndex(1)));
	}
	
	@Test
	public void testMoveBeyondSecondPoint() {
		Route busRoute = testBus.getLine().getRoute();
		Point routeFirstPoint = busRoute.getPointAtIndex(0);
		assertTrue(testBus.move(4));
		assertFalse(testBus.getCurrentPosition().equals(routeFirstPoint));
		assertTrue(testBus.getCurrentPosition().equals(0, 5));
		assertTrue(testBus.getPreviousPoint().equals(busRoute.getPointAtIndex(1)));
	}
	
	@Test
	public void testMoveExactlyToThirdPoint() {
		Route busRoute = testBus.getLine().getRoute();
		assertTrue(testBus.move(7));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(0)));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(1)));
		assertTrue(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(2)));
		assertTrue(testBus.getPreviousPoint().equals(busRoute.getPointAtIndex(2)));
	}
	
	@Test
	public void testMoveBeyondThirdPoint() {
		Route busRoute = testBus.getLine().getRoute();
		assertTrue(testBus.move(8));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(0)));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(1)));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(2)));
		assertTrue(testBus.getCurrentPosition().equals(0, 9));
		assertTrue(testBus.getPreviousPoint().equals(busRoute.getPointAtIndex(2)));
	}
	
	@Test
	public void testMoveThroughRouteAndGetToExactlyFirstPoint() {
		Route busRoute = testBus.getLine().getRoute();
		assertTrue(testBus.move(22));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(1)));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(2)));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(3)));
		assertTrue(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(0)));
		assertTrue(testBus.getPreviousPoint().equals(busRoute.getPointAtIndex(0)));
	}
	
	@Test
	public void testMoveThroughRouteAndGetBeyoundFirstPoint() {
		Route busRoute = testBus.getLine().getRoute();
		assertTrue(testBus.move(23));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(0)));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(1)));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(2)));
		assertFalse(testBus.getCurrentPosition().equals(busRoute.getPointAtIndex(3)));
		assertTrue(testBus.getCurrentPosition().equals(0, 2));
		assertTrue(testBus.getPreviousPoint().equals(busRoute.getPointAtIndex(0)));
	}
	
}
