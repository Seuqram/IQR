package code;

import lombok.Getter;

/**
 * Represents a bus
 * 
 * @author rodrigo
 *
 */
@Getter
public class Bus
{
	private String identifier;
	private Line line;
	private Point position;

	/**
	 * Bus constructor, receives a identifier that should be unique
	 * and a line that contains a route. The start position of the bus
	 * is the first point of the route of the line
	 * @param identifier unique string to identify bus
	 * @param line object from Line class
	 */
	public Bus(String identifier, Line line)
	{
		this.identifier = identifier;
		this.line = line;
		
		//gets route first point and set this point as the start position of the bus
		double latitude = this.line.getRoute().getPointAtIndex(0).getLatitude();
		double longitude = this.line.getRoute().getPointAtIndex(0).getLongitude();
		this.position = new Point(latitude, longitude);
	}

	public boolean move(float distance)
	{
		return false;
	}
}