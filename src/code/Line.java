package code;

import java.util.ArrayList;

import lombok.Getter;


public class Line
{
	@Getter private int number;
	@Getter private Route route;
	@Getter private ArrayList<Bus> busesList;

	/**
	 * Constructor that receives a number and instantiate a new Route
	 * @param number can not be changed after being set
	 */
	public Line(int number)
	{
		this.number = number;
		this.route = new Route();
		this.busesList = new ArrayList<>();
	}
	
	/**
	 * Adds a bus to the line's bus list
	 * @param bus
	 */
	public void addBus(Bus bus) {
		busesList.add(bus);
	}
	
	public double getExpectedBusDistance() {
		double routeSize = this.route.getRouteSize();
		int busQuantity = this.busesList.size();
		return routeSize / busQuantity;
	}
}