package code;

import java.util.ArrayList;

import org.decimal4j.util.DoubleRounder;

public class QualityCalculator {
	private static QualityCalculator qualityCalculator = null;

	private QualityCalculator() {
	};

	public static QualityCalculator getInstance() {
		if (qualityCalculator == null)
			qualityCalculator = new QualityCalculator();
		return qualityCalculator;
	}

	public double getRouteQuality(LinhaIqr line) {
		double expectedDistance = line.getExpectedBusDistance();
		int busQuantity = line.getBusQuantity();
		double totalDistance = 0;
		double distanceQuantity = busQuantity - 1;
		if (line.busesOnSamePosition())
			return 0;
		line.sortBusList();
		for (int index = 0; index < busQuantity - 1; index++) {
			OnibusIqr busOne = line.getBusAtIndex(index);
			OnibusIqr busTwo = line.getBusAtIndex(index + 1);
			double distanceBetweenBuses = getDistanceBetweenBuses(busOne, busTwo);
			if (distanceBetweenBuses == expectedDistance) {
				totalDistance += 1;
			} else {
				if (distanceBetweenBuses < expectedDistance) {
					totalDistance += distanceBetweenBuses / expectedDistance;
				} else {
					totalDistance += 1 - ((distanceBetweenBuses - expectedDistance)
							/ (line.getRotaIqr().getRouteSize() - expectedDistance));
				}
			}
		}
		return DoubleRounder.round((totalDistance / distanceQuantity) * 100, 2);
	}

	public double getDistanceBetweenBuses(OnibusIqr busOne, OnibusIqr busTwo) {
		double busOneLongitude = busOne.getDistancePosition().getLongitude();
		double busTwoLongitude = busTwo.getDistancePosition().getLongitude();
		double distance = busOneLongitude - busTwoLongitude;
		return Math.abs(distance);
	}

	public ArrayList<OnibusIqr> getBusesInOrder(ArrayList<OnibusIqr> busesOutOfOrder) {
		ArrayList<OnibusIqr> busesInOrder = new ArrayList<>();
		return busesInOrder;
	}
}
