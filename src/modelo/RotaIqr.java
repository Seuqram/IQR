package modelo;

import modelo.Ponto;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the route that the bus travels. It has a set of points and the bus
 * must pass through this set of points
 * 
 * @author Rodrigo
 * 
 */
public class RotaIqr {
	private List<Ponto> pontos;

	/**
	 * Constructor with no arguments that instantiate a new ArrayList of points
	 */
	public RotaIqr() {
		this.pontos = new ArrayList<>();
	}

	/**
	 * Method that adds a new point to the route based on a given point
	 * 
	 * @param point
	 * @return
	 */
	public boolean addPoint(Ponto point) {
		return this.addPoint(point.getLatitude(), point.getLongitude());
	}

	/**
	 * Method that creates a new point based on doubles latitute and longitude
	 * passed and add this new point to the list of points of the route.
	 * <p>
	 * The point must be unique
	 * 
	 * @param double
	 *            latitude
	 * @param double
	 *            longitude
	 * @return
	 */
	public boolean addPoint(double latitude, double longitude) {
		if (getPointsQuantity() > 0) {
			for (int index = 0; index < this.pontos.size(); index++) {
				Ponto point = this.pontos.get(index);
				if (point.getLatitude() == latitude)
					if (point.getLongitude() == longitude)
						return false;
			}
		}
		this.pontos.add(new Ponto(latitude, longitude));
		return true;
	}

	/**
	 * 
	 * @return the quantity of points present in the route
	 */
	public int getPointsQuantity() {
		return pontos.size();
	}

	/**
	 * 
	 * @param index
	 * @return the point, of the route's list of points, at the given index
	 */
	public Ponto getPointAtIndex(int index) {
		// return a copy of the point not the reference to the point
		Ponto point = this.pontos.get(index);
		return point;
	}

	/**
	 * 
	 * @param givenPoint
	 * @return the integer that represents the index of the point on the route lists
	 *         or -1 if the point doesn't exist in the list
	 */
	public int getIndexOfPoint(Ponto givenPoint) {
		for (int index = 0; index < this.pontos.size(); index++) {
			Ponto point = this.pontos.get(index);
			if (point.equals(givenPoint))
				return index;
		}
		return -1;
	}

	/**
	 * @param referencePoint
	 * @return the point after the referencePoint if it exists
	 */
	public Ponto getNextPoint(Ponto referencePoint) {
		int referencePointIndex = this.getIndexOfPoint(referencePoint);
		if (referencePointIndex == this.getPointsQuantity() - 1)
			return this.pontos.get(0);
		int nextPointIndex = referencePointIndex + 1;
		return this.pontos.get(nextPointIndex);
	}

	/**
	 * @return the total size of the route
	 */
	public double getRouteSize() {
		double size = 0;
		for (int index = 0; index < this.getPointsQuantity(); index++) {
			Ponto point = this.pontos.get(index);
//			size = size + point.getDistanceToPoint(this.getNextPoint(point));
		}
		return size;
	}
}