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
	private Point routePosition;
	private Point distancePosition;

	/**
	 * Bus constructor, receives a identifier that should be unique
	 * and a line that contains a route. The start position of the bus
	 * is the first point of the route of the line. The constructor adds
	 * the bus to the bus' line
	 * @param identifier unique string to identify bus
	 * @param line object from Line class
	 */
	public Bus(String identifier, Line line)
	{
		this.identifier = identifier;
		this.line = line;
		this.line.addBus(this);
		
		//gets route first point and set this point as the start position of the bus
		double latitude = this.line.getRoute().getPointAtIndex(0).getLatitude();
		double longitude = this.line.getRoute().getPointAtIndex(0).getLongitude();
		this.routePosition = new Point(latitude, longitude);
		this.distancePosition = new Point(0, 0);
	}
	
	/**
	 * Moves the bus in the straight line that represents the route size. The bus
	 * can not move a distance bigger than the route size
	 * @param distance
	 */
	public boolean move(double distance) {
		double currentLongitude = this.distancePosition.getLongitude();
		double routeSize = this.line.getRoute().getRouteSize();
		
		if (distance < routeSize) {
			double sumCurrentLongitudeAndDistance = currentLongitude + distance;
			if (currentLongitude + distance >= routeSize) {
				distance = routeSize > sumCurrentLongitudeAndDistance ? routeSize - sumCurrentLongitudeAndDistance : sumCurrentLongitudeAndDistance - routeSize;
				currentLongitude = 0;
			}
			this.distancePosition.setLongitude(currentLongitude + distance);
			return true;
		}else
			return false;
	}
	
	/**
	 * 
	 * @return the Longitude of the distance point
	 */
	public double getLongitudeDistancePosition() {
		return this.distancePosition.getLongitude();
	}
}