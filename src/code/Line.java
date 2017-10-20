package code;

import lombok.Getter;

@Getter
public class Line
{
	private int number;
	private Route route;

	/**
	 * Constructor that receives a number and instantiate a new Route
	 * @param number can not be changed after being set
	 */
	public Line(int number)
	{
		this.number = number;
		this.route = new Route();
	}
}