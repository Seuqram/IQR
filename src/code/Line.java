package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;


public class Line
{
	@Getter private int number;
	@Getter private Route route;
	private List<Bus> busesList;

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
	public boolean addBus(Bus bus) {
		int previousBusQuantity = this.busesList.size();
		busesList.add(bus);
		int currentBusQuantity = this.busesList.size();
		return currentBusQuantity - previousBusQuantity == 1; 
	}
	
	/**
	 * @return the relation between the routeSize and the busQuantity
	 */
	public double getExpectedBusDistance() {
		double routeSize = this.route.getRouteSize();
		int busQuantity = this.busesList.size();
		return routeSize / busQuantity;
	}
	
	/**
	 * Sort the line's bus list by the proximity to the beginning of the route.
	 * The first bus is the farther from the beginning and the last is the closer. 
	 */
	public void sortBusList(){
		this.busesList.sort(Comparator.comparing(Bus::getLongitudeDistancePosition));
	}
	
	/**
	 * Return the bus at the given index
	 * @param index
	 * @return
	 */
	public Bus getBusAtIndex(int index) {
		return this.busesList.get(index);
	}

	public int getBusQuantity() {
		return this.busesList.size();
	}
	
	/**
	 * @return if all buses on the line are in the exact same position
	 */
	public boolean busesOnSamePosition() {
		Point basePoint = this.busesList.get(0).getDistancePosition();
		boolean samePosition = true;
		for (Bus bus : this.busesList)
			if (samePosition)
				if (!bus.getDistancePosition().equals(basePoint))
					samePosition = false;
		return samePosition;
	}
}