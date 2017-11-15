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
		return 0;
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
