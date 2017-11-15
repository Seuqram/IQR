package code;

import java.util.ArrayList;
import java.util.HashMap;

public class QualityCalculator {
	private static QualityCalculator qualityCalculator = null;
	
	private QualityCalculator() {};
	
	public static QualityCalculator getInstance() {
		if (qualityCalculator == null)
			qualityCalculator = new QualityCalculator();
		return qualityCalculator;
	}
	
	public double getRouteQuality(Line line) {
		double expectedDistance = line.getExpectedBusDistance();
		int busQuantity = line.getBusQuantity();
		double totalDistance = 0;
		double distanceQuantity = busQuantity - 1;
		if (line.busesOnSamePosition())
			return 0;		
		line.sortBusList();
		for (int index = 0; index < busQuantity - 1; index ++) {
			Bus busOne = line.getBusAtIndex(index);
			Bus busTwo = line.getBusAtIndex(index + 1);
			totalDistance = totalDistance + getDistanceBetweenBuses(busOne, busTwo);
		}
		System.out.println(totalDistance);
		return ((totalDistance/distanceQuantity)/expectedDistance) * 100;
	}
	
	public double getDistanceBetweenBuses(Bus busOne, Bus busTwo) {
		double busOneLongitude = busOne.getDistancePosition().getLongitude();
		double busTwoLongitude = busTwo.getDistancePosition().getLongitude();
		double distance = busOneLongitude - busTwoLongitude;
		return Math.abs(distance);
	}
	
	public ArrayList<Bus> getBusesInOrder(ArrayList<Bus> busesOutOfOrder){
		ArrayList<Bus> busesInOrder = new ArrayList<>();
		return busesInOrder;
	}
}
