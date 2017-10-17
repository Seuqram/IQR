package code;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a square route. The bus must pass through 4 limit points: (0,0)
 * (0,xMax) (xMax, yMax) (0, yMax)
 * 
 * @author Rodrigo
 * 
 */
public class Route
{
	private List<Point> points;

	public Route()
	{
		this.points = new ArrayList<Point>();
	}

	public void addPoint(float latitude, float longitude)
	{
		this.points.add(new Point(latitude, longitude));
	}
	
	public int size()
	{
		return points.size();
	}

	public Point getPoint(int index)
	{
		return this.points.get(index);
	}
}