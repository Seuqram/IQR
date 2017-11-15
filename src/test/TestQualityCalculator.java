package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import code.Line;
import code.QualityCalculator;
import code.Bus;

public class TestQualityCalculator {

	Line testLine;
	ArrayList<Bus> testBusList;
	QualityCalculator calculator = QualityCalculator.getInstance();
	@Before
	public void setData(){
		testLine = new Line(107);
		testLine.getRoute().addPoint(0, 1);
		testLine.getRoute().addPoint(0, 4);
		testLine.getRoute().addPoint(0, 8);
		testLine.getRoute().addPoint(0, 12);
		testBusList = new ArrayList<>();
	}
	
	@After // tearDown()
	public void after() throws Exception {
		testLine = null;
		testBusList.clear();
	}
	
	public void addBusToList() {
		int previousBusQuantity = testBusList.size();
		testBusList.add(new Bus("id", testLine));
		int currentBusQuantity = testBusList.size();
		assert(currentBusQuantity - previousBusQuantity == 1);
	}
	
	public void addQuantityOfBusToList(int quantity) {
		int previousBusQuantity = testBusList.size();
		for (int index = 0; index < quantity; index ++)
			addBusToList();
		int currentBusQuantity = testBusList.size();
		assert(currentBusQuantity - previousBusQuantity == quantity);
	}
	
	public void moveBusAtIndex(int index, double distance) {
		Bus firstBus = testBusList.get(index);
		assert(firstBus.move(distance));
	}

	@Test
	public void testZeroQualityTwoBusesOnSamePosition() {
		addQuantityOfBusToList(2);
		QualityCalculator calculator = QualityCalculator.getInstance();
		assertEquals(0, calculator.getRouteQuality(testLine), 0);
	}
	
	@Test
	public void testTotalQualityTwoBusesOnExpectedDistance() {
		addQuantityOfBusToList(2);
		moveBusAtIndex(0, testLine.getExpectedBusDistance());
		assertEquals(100, calculator.getRouteQuality(testLine), 0);
	}
	
	@Test
	public void testDistanceBetweenThreeBusesOnSamePosition() {
		addQuantityOfBusToList(3);
		assertEquals(0, calculator.getDistanceBetweenBuses(testBusList.get(0), testBusList.get(1)), 0);
	}
	
	@Test
	public void testDistanceBetweenThreeBusesOnExpectedDistance() {
		addQuantityOfBusToList(3);
		moveBusAtIndex(0, testLine.getExpectedBusDistance() * 2);
		moveBusAtIndex(1, testLine.getExpectedBusDistance());
		assertEquals(100, calculator.getRouteQuality(testLine), 0);
	}
	
//	@Test
//	public void testDistanceBetweenThreeBusesNotOnExpectedDistance() {
//		addQuantityOfBusToList(3);
//		moveBusAtIndex(0, testLine.getExpectedBusDistance());
//		assertEquals(50, calculator.getRouteQuality(testLine), 0);
//	}
	
	@Test
	public void testDistanceBetweenBusesOnSamePosition() {
		addQuantityOfBusToList(2);
		assertEquals(0, calculator.getDistanceBetweenBuses(testBusList.get(0), testBusList.get(1)), 0);
	}
	
	@Test
	public void testDistanceBetweenBusesOnDifferentPositions() {
		addQuantityOfBusToList(2);
		Bus firstBus = testBusList.get(0);
		assert(firstBus.move(1));
		assertEquals(1, calculator.getDistanceBetweenBuses(testBusList.get(0), testBusList.get(1)), 0);
		assert(firstBus.move(15));
		assertEquals(16, calculator.getDistanceBetweenBuses(testBusList.get(0), testBusList.get(1)), 0);
	}

}
