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
	public void testAssetThatBusMoveThroughRoleRoute(){
		//ASSERTS THAT ROUTE LIMITS ARE KNOWN
		assertEquals(10, bus.getLine().getRoute().getMax().getX(), 0);
		assertEquals(10, bus.getLine().getRoute().getMax().getY(), 0);
		
		assertEquals(Bus.BUSLOCATION.BOTTON, bus.getLocation());
		assertTrue(bus.move(5, 0));
		assertEquals(Bus.BUSLOCATION.BOTTON, bus.getLocation());
		assertTrue(bus.move(5, 0));//REACHES END OF BOTTON
		assertEquals(Bus.BUSLOCATION.LEFT, bus.getLocation());
		assertTrue(bus.move(0, 5));
		assertEquals(Bus.BUSLOCATION.LEFT, bus.getLocation());
		assertTrue(bus.move(0, 5));//REACHES END OF LEFT
		assertEquals(Bus.BUSLOCATION.TOP, bus.getLocation());
		assertTrue(bus.move(5, 0));
		assertEquals(Bus.BUSLOCATION.TOP, bus.getLocation());
		assertTrue(bus.move(5, 0));//REACHES END OF TOP
		assertEquals(Bus.BUSLOCATION.RIGHT, bus.getLocation());
		assertTrue(bus.move(0, 5));
		assertEquals(Bus.BUSLOCATION.RIGHT, bus.getLocation());
		assertTrue(bus.move(0, 5));//REACHES END OF RIGHT
		assertEquals(Bus.BUSLOCATION.BOTTON, bus.getLocation());
	}
	
	@Test
	public void testAssertThatBusNoGoBeyoundRouteInAnyDirection() {
		//ASSERTS THAT ROUTE LIMITS ARE KNOWN
		assertEquals(10, bus.getLine().getRoute().getMax().getX(), 0);
		assertEquals(10, bus.getLine().getRoute().getMax().getY(), 0);
		
		
		//BUS ON BOTTON DOES NOT GO BEYOND X AXIS
		assertEquals(Bus.BUSLOCATION.BOTTON, bus.getLocation());
		assertFalse(bus.move(11, 0));
		
		//BUS ON LEFT DOES NOT GO BEYOND Y AXIS
		assertTrue(bus.move(10, 0));// GOEST TO END OF BOTTOM
		assertEquals(Bus.BUSLOCATION.LEFT, bus.getLocation());
		assertFalse(bus.move(0, 11));
		
		//BUS ON TOP DOES NOT GO BEYOND X AXIS
		assertTrue(bus.move(0, 10));// GOEST TO END OF LEFT
		assertEquals(Bus.BUSLOCATION.TOP, bus.getLocation());
		assertFalse(bus.move(11, 0));
		
		//BUS ON RIGHT DOES NOT GO BEYOND Y AXIS
		assertTrue(bus.move(10, 0));// GOEST TO END OF TOP
		assertEquals(Bus.BUSLOCATION.RIGHT, bus.getLocation());
		assertFalse(bus.move(0, 11));
	}
	
	@Test
	public void testAssertThatBusMoveInTwoDirections(){
		//ASSERTS THAT ROUTE LIMITS ARE KNOWN
		assertEquals(10, bus.getLine().getRoute().getMax().getX(), 0);
		assertEquals(10, bus.getLine().getRoute().getMax().getY(), 0);
		assertEquals(Bus.BUSLOCATION.BOTTON, bus.getLocation());
		assertTrue(bus.move(10, 10));// GOEST TO END OF BOTTOM AND TO END OF LEFT
		assertEquals(Bus.BUSLOCATION.TOP, bus.getLocation());
		assertTrue(bus.move(10, 10));// GOEST TO END OF TOP AND TO END OF RIGHT
		assertEquals(Bus.BUSLOCATION.BOTTON, bus.getLocation());
	}
	
	@Test
	public void testAssertThatBusNoMoveDiagonal(){
		//ASSERTS THAT ROUTE LIMITS ARE KNOWN
		assertEquals(10, bus.getLine().getRoute().getMax().getX(), 0);
		assertEquals(10, bus.getLine().getRoute().getMax().getY(), 0);
		
		assertEquals(Bus.BUSLOCATION.BOTTON, bus.getLocation());
		assertFalse(bus.move(5, 5));// ASSERT THAT BUS DOES NOT MOVE ON DIAGONAL FROM BOTTON
		
		assertTrue(bus.move(10, 0));// MOVES BUS TO LEFT
		assertEquals(Bus.BUSLOCATION.LEFT, bus.getLocation());
		assertFalse(bus.move(5, 5));// ASSERT THAT BUS DOES NOT MOVE ON DIAGONAL FROM LEFT
		
		assertTrue(bus.move(0, 10));// MOVES BUS TO LEFT
		assertEquals(Bus.BUSLOCATION.TOP, bus.getLocation());
		assertFalse(bus.move(5, 5));// ASSERT THAT BUS DOES NOT MOVE ON DIAGONAL FROM LEFT
	}
	
}
