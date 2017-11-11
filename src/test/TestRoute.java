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
	public void testAddPointValidPoint() {
		Point expectedPoint = new Point(0, 0);
		assertEquals(0, testRoute.getPointsQuantity()); //NO POINT IN THE ROUTE'S LIST
		assertTrue(testRoute.addPoint(expectedPoint.getLatitude(), expectedPoint.getLongitude())); //SUSCEFULLY ADDED POINT TO LIST
		assertEquals(1, testRoute.getPointsQuantity()); //THE ADDED POIN IS IN THE ROUTE'S LIST
		assertTrue(expectedPoint.equals(testRoute.getPointAtIndex(0)));
	}
	
	@Test
	public void testAddPointInvalidPoint() {
		Point testPoint = new Point(0, 0);
		assertEquals(0, testRoute.getPointsQuantity());
		assertTrue(testRoute.addPoint(testPoint.getLatitude(), testPoint.getLongitude()));
		assertEquals(1, testRoute.getPointsQuantity());
		assertFalse(testRoute.addPoint(testPoint));
		assertEquals(1, testRoute.getPointsQuantity());
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
	public void testGetNextPointNotCirculating() {
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
		Point resultPoint = testRoute.getNextPoint(firstTestPoint);
		assertTrue(secondTestPoint.equals(resultPoint));
	}
	
	@Test
	public void testGetNextPointCirculating() {
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
		Point resultPoint = testRoute.getNextPoint(thirdTestPoint);
		assertTrue(firstTestPoint.equals(resultPoint));
	}
	
	@Test
	public void testGetRouteSizeStraightLineLatitudeAxis() {
		testRoute.addPoint(0, 1);
		testRoute.addPoint(0, 4);
		testRoute.addPoint(0, 8);
		testRoute.addPoint(0, 12);
		assertEquals(22, testRoute.getRouteSize(), 0);
	}
	
	@Test
	public void testGetRouteSizeStraightLineLongitudeAxis() {
		testRoute.addPoint(1, 0);
		testRoute.addPoint(4, 0);
		testRoute.addPoint(8, 0);
		testRoute.addPoint(12, 0);
		assertEquals(22, testRoute.getRouteSize(), 0);
	}
	
	@Test
	public void testGetRouteSizeDiagonalLine() {
		testRoute.addPoint(1, 1);
		testRoute.addPoint(4, 4);
		assertEquals(8, testRoute.getRouteSize(), 1);
	}

}
