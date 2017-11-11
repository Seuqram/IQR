package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import code.Bus;
import code.Line;
import code.Point;

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
	public void testNewBusRoutePosition(){
		Point expectedPoint = new Point(
				testLine.getRoute().getPointAtIndex(0).getLatitude(), 
				testLine.getRoute().getPointAtIndex(0).getLongitude());
		assert (expectedPoint.equals(testBus.getRoutePosition()));
	}
	
	@Test 
	public void testNewBusDistancePosition() {
		Point expectedPoint = new Point(0, 0);
		assert (expectedPoint.equals(testBus.getDistancePosition()));
	}
	
	@Test
	public void testMoveBusOneMoveNotGoingBackToRouteBegin() {
		double distance = 5;
		testBus.move(distance);
		Point expectedPoint = new Point(0, distance);
		assert (expectedPoint.equals(testBus.getDistancePosition()));
	}
	
	@Test
	public void testMoveBusTwoMoveNotGoingBackToRouteBegin() {
		double distance = 5;
		testBus.move(distance);
		Point expectedPoint = new Point(0, distance);
		assert (expectedPoint.equals(testBus.getDistancePosition()));
		testBus.move(distance);
		expectedPoint.setLongitude(expectedPoint.getLongitude() + distance);
		assert (expectedPoint.equals(testBus.getDistancePosition()));
	}
	
	@Test
	public void testMoveBusOneMoveGoingBackToRouteBegin() {
		assert(testBus.move(1));
		assert(testBus.move(testBus.getLine().getRoute().getRouteSize() - 1));
		Point expectedPoint = new Point(0, 0);
		assert (expectedPoint.equals(testBus.getDistancePosition()));
	}
	
	@Test
	public void testMoveBusOneMoveGoingBackToRouteBeginAndPassingIt() {
		assert(testBus.move(2));
		assert(testBus.move(testBus.getLine().getRoute().getRouteSize() - 1));
		Point expectedPoint = new Point(0, 1);
		assert (expectedPoint.equals(testBus.getDistancePosition()));
		assert(testBus.move(2));
		assert(testBus.move(testBus.getLine().getRoute().getRouteSize() - 1));
		expectedPoint.setLongitude(2);
		assert (expectedPoint.equals(testBus.getDistancePosition()));
	}
}
