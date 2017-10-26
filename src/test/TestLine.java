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

	@Test
	public void testGetExpectedBusDistance() {
		Bus testBusOne = new Bus("1", testLine);
		Bus testBusTwo = new Bus("2", testLine);
		assertEquals(11, testLine.getExpectedBusDistance());
	}

}
