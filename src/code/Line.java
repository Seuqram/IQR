package code;

public class Line
{
	private int number;
	private Route route;

	public Line(int number)
	{
		this.number = number;
		this.route = new Route();
	}

	public int getNumber()
	{
		return number;
	}

	public Route getRoute()
	{
		return route;
	}
}