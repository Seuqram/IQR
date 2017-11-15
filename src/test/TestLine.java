package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import code.Bus;
import code.Line;

public class TestLine {
	
	Line testLine;
	@Before
	public void setData(){
		testLine = new Line(107);
		testLine.getRoute().addPoint(0, 1);
		testLine.getRoute().addPoint(0, 4);
		testLine.getRoute().addPoint(0, 8);
		testLine.getRoute().addPoint(0, 12);
	}
	
	@After // tearDown()
	public void after() throws Exception {
		testLine = null;
	}

	@SuppressWarnings("unused")
	@Test
	public void testGetExpectedBusDistance() {
		Bus busOne = new Bus("one", testLine);
		Bus busTwo = new Bus("two", testLine);
		assertEquals(11, testLine.getExpectedBusDistance(), 0);
	}
	
	@Test
	public void testGetLineBusesOnOrder() {
		Bus busOne = new Bus("one", testLine);
		Bus busTwo = new Bus("two", testLine);
		Bus busThree = new Bus("three", testLine);
		assert(busOne.move(5));
		assert(busTwo.move(7));
		testLine.sortBusList();
		assertEquals(busThree, testLine.getBusAtIndex(0));
		assertEquals(busOne, testLine.getBusAtIndex(1));
		assertEquals(busTwo, testLine.getBusAtIndex(2));
	}

}
