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

	Bus bus;
	@Before
	public void setData(){
		bus = new Bus("id", new Line(107, new Route(new Point(10, 10))));
	}
	
	@After // tearDown()
	public void after() throws Exception {
		bus = null;
	}
	
	@Test
	public void testNewBusPosition(){
		Point expectedPoint = new Point(0, 0);
		assertEquals(expectedPoint.getX(), bus.getPosition().getX(), 0);
		assertEquals(expectedPoint.getY(), bus.getPosition().getY(), 0);
	}
	
	@Test
	public void testAssertThatAInvalidBusXAxisMovementIsNotMade() {
		assertFalse(bus.move(100, 0));
		assertTrue(bus.move(5, 0));
	}
	
	@Test
	public void testAssertThatAInvalidBusXAxisMovmentIsNotMadeAfterAValidOne(){
		assertTrue(bus.move(5, 0));
		assertFalse(bus.move(6, 0));
	}
	
	@Test
	public void testAssertThatAValidBusXAxisMovementIsMade() {
		Point expectedBusPosition = new Point(10, 0);
		assertTrue(bus.move(10, 0));
		assertEquals(expectedBusPosition.getX(), bus.getPosition().getX(), 0);
	}
	
	@Test
	public void testAssertThatTwoValidBusXAxisMovementsAreMade() {
		Point expectedBusPosition = new Point(10, 0);
		assertTrue(bus.move(5, 0));
		assertEquals(5, bus.getPosition().getX(), 0);
		assertTrue(bus.move(5, 0));
		assertEquals(expectedBusPosition.getX(), bus.getPosition().getX(), 0);
	}
	
	@Test
	public void testAssertThatAInvalidBusYAxisMovementIsNotMade() {
		assertFalse(bus.move(0, 100));
		assertTrue(bus.move(0, 5));
	}
	
	@Test
	public void testAssertThatAInvalidBusYAxisMovmentIsNotMadeAfterAValidOne(){
		assertTrue(bus.move(0, 5));
		assertFalse(bus.move(0, 6));
	}
	
	@Test
	public void testAssertThatAValidBusYAxisMovementIsMade() {
		Point expectedBusPosition = new Point(0, 10);
		assertTrue(bus.move(0, 10));
		assertEquals(expectedBusPosition.getX(), bus.getPosition().getX(), 0);
	}
	
	@Test
	public void testAssertThatTwoValidBusYAxisMovementsAreMade() {
		Point expectedBusPosition = new Point(0, 10);
		assertTrue(bus.move(0, 5));
		assertEquals(5, bus.getPosition().getY(), 0);
		assertTrue(bus.move(0, 5));
		assertEquals(expectedBusPosition.getY(), bus.getPosition().getY(), 0);
	}
	
	@Test
	public void assertThatDoesNotMoveOnDiagionals(){
		assertFalse(bus.move(5, 5));
	}
}
