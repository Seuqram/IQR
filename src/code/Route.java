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
	 * Method that creates a new point based on doubles latitute and longitude passed
	 * and add this new point to the list of points of the route
	 * @param double latitude
	 * @param double longitude
	 */
	public void addPoint(float latitude, float longitude)
	{
		this.points.add(new Point(latitude, longitude));
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
}