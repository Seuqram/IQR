package code;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the route that the bus travels.
 * It has a set of points and the bus must pass through this set of points
 * @author Rodrigo
 * 
 */
public class Route
{
	private List<Point> points;

	/**
	 * Constructor with no arguments that instantiate a new ArrayList of points
	 */
	public Route()
	{
		this.points = new ArrayList<>();
	}
	
	/**
	 * Method that adds a new point to the route based on a given point
	 * @param point
	 * @return
	 */
	public boolean addPoint(Point point) {
		return this.addPoint(point.getLatitude(), point.getLongitude());
	}

	/**
	 * Method that creates a new point based on doubles latitute and longitude passed
	 * and add this new point to the list of points of the route.
	 * <p>
	 * The point must be unique
	 * @param double latitude
	 * @param double longitude
	 * @return 
	 */
	public boolean addPoint(double latitude, double longitude)
	{
		if (getPointsQuantity() > 0) {
			for (int index = 0; index < this.points.size(); index++) {
				Point point = this.points.get(index);
				if (point.getLatitude() == latitude)
					if (point.getLongitude() == longitude)
						return false;
			}
		}
		this.points.add(new Point(latitude, longitude));
		return true;
	}
	
	/**
	 * 
	 * @return the quantity of points present in the route
	 */
	public int getPointsQuantity()
	{
		return points.size();
	}

	/**
	 * 
	 * @param index
	 * @return the point, of the route's list of points, at the given index 
	 */
	public Point getPointAtIndex(int index)
	{
		//return a copy of the point not the reference to the point
		Point point = this.points.get(index); 
		return point;
	}
	
	/**
	 * 
	 * @param givenPoint
	 * @return the integer that represents the index of the point
	 * on the route lists or -1 if the point doesn't exist in the list
	 */
	public int getIndexOfPoint(Point givenPoint) {
		for (int index = 0; index < this.points.size(); index++) {
			Point point = this.points.get(index);
			if (point.equals(givenPoint))
				return index;
		}
		return -1;
	}
	
	/**
	 * @param referencePoint
	 * @return the point after the referencePoint if it exists
	 */
	public Point getNextPoint(Point referencePoint) {
		int referencePointIndex = this.getIndexOfPoint(referencePoint);
		if (referencePointIndex == this.getPointsQuantity() - 1)
			return this.points.get(0);
		int nextPointIndex = referencePointIndex + 1;
		return this.points.get(nextPointIndex);
	}
	
	/**
	 * @return the total size of the route
	 */
	public double getSize() {
		double size = 0;
		for (int index = 0; index < this.getPointsQuantity(); index++) {
			Point point = this.points.get(index);
			size = size + point.getDistanceToPoint(this.getNextPoint(point));
		}
		return size;
	}
	
	/**
	 * 
	 * @param busOne
	 * @param busTwo
	 * @return
	 */
	public double getDistanceBetweenBuses(Bus busOne, Bus busTwo) {
		if (busesBetwenSamePoints(busOne, busTwo)) {
			double distance = busOne.getCurrentPosition().getDistanceToPoint(busTwo.getCurrentPosition());
			return distance;
		}
		double distance = busOne.getCurrentPosition().getDistanceToPoint(busTwo.getCurrentPosition());
		return distance;
	}
	
	/**
	 * This method receveis two buses and tells wich one
	 * is further to route's first point, this bus is the first
	 * one
	 * @param busOne
	 * @param busTwo
	 * @return
	 */
	public Bus getFirstBus(Bus busOne, Bus busTwo) {
		int busOnePreviousPointIndex = this.getIndexOfPoint(busOne.getPreviousPoint());
		int busTwoPreviousPointIndex = this.getIndexOfPoint(busTwo.getPreviousPoint());
		if (!busesBetwenSamePoints(busOne, busTwo)) {
			if (!busesOnSamePositions(busOne, busTwo)) {
				double busOneDistanceToPreviousPoint = busOne.getPreviousPoint().getDistanceToPoint(busOne.getCurrentPosition());
				double busTwoDistanceToPreviousPoint = busTwo.getPreviousPoint().getDistanceToPoint(busTwo.getCurrentPosition());
				if (busOneDistanceToPreviousPoint > busTwoDistanceToPreviousPoint)
					return busOne;
				else
					return busTwo;
			}
		}else {
			if (busOnePreviousPointIndex > busTwoPreviousPointIndex) {
				return busOne;
			}else {
				return busTwo;
			}
		}
		return busOne;
	}
	
	/**
	 * 
	 * @param busOne
	 * @param busTwo
	 * @return
	 */
	public boolean busesBetwenSamePoints(Bus busOne, Bus busTwo) {
		int busOnePreviousPointIndex = this.getIndexOfPoint(busOne.getPreviousPoint());
		int busTwoPreviousPointIndex = this.getIndexOfPoint(busTwo.getPreviousPoint());
		return (busOnePreviousPointIndex == busTwoPreviousPointIndex);
	}
	
	/**
	 * 
	 * @param busOne
	 * @param busTwo
	 * @return
	 */
	public boolean busesOnSamePositions(Bus busOne, Bus busTwo) {
		return (busOne.getCurrentPosition() == busTwo.getCurrentPosition());
	}
}