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
		for (int index = 0; index < this.points.size(); index++) {
			Point point = this.points.get(index);
			if (point.getLatitude() == latitude)
				if (point.getLongitude() == longitude)
					return false;
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
	
	public int getIndexOfPoint(Point givenPoint) {
		for (int index = 0; index < this.points.size(); index++) {
			Point point = this.points.get(index);
			if (point.equals(givenPoint))
				return index;
		}
		return 0;
	}
	
	public Point getNextPoint(Point referencePoint) {
		int referencePointIndex = this.getIndexOfPoint(referencePoint);
		if (referencePointIndex == this.getPointsQuantity() - 1)
			return this.points.get(0);
		int nextPointIndex = referencePointIndex + 1;
		return this.points.get(nextPointIndex);
	}
	
	public int getSize() {
		int size = 0;
		for (int index = 0; index < this.getPointsQuantity(); index++) {
			Point point = this.points.get(index);
			size = size + point.getDistanceToPoint(this.getNextPoint(point));
		}
		return size;
	}
}