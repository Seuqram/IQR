package test;

import static org.junit.Assert.*;

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
		testLine.getRoute().addPoint(0, 2);
		testLine.getRoute().addPoint(0, 3);
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
	
}
