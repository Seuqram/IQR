package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;

/**
 * Representa uma linha de onibus utilizada para cálculo de IQR
 * @author rodrigo
 *
 */
public class LinhaIqr {
	@Getter
	private int number;
	@Getter
	private Route route;
	private List<OnibusIqr> listaOnibusIqr;

	/**
	 * Constructor that receives a number and instantiate a new Route
	 * 
	 * @param number
	 *            can not be changed after being set
	 */
	public LinhaIqr(int number) {
		this.number = number;
		this.route = new Route();
		this.listaOnibusIqr = new ArrayList<>();
	}

	/**
	 * Adds a bus to the line's bus list
	 * 
	 * @param bus
	 */
	public boolean addBus(OnibusIqr bus) {
		int previousBusQuantity = this.listaOnibusIqr.size();
		listaOnibusIqr.add(bus);
		int currentBusQuantity = this.listaOnibusIqr.size();
		return currentBusQuantity - previousBusQuantity == 1;
	}

	/**
	 * @return the relation between the routeSize and the busQuantity
	 */
	public double getExpectedBusDistance() {
		double routeSize = this.route.getRouteSize();
		int busQuantity = this.listaOnibusIqr.size();
		return routeSize / busQuantity;
	}

	/**
	 * Sort the line's bus list by the proximity to the beginning of the route. The
	 * first bus is the farther from the beginning and the last is the closer.
	 */
	public void sortBusList() {
		this.listaOnibusIqr.sort(Comparator.comparing(OnibusIqr::getLongitudeDistancePosition));
	}

	/**
	 * Return the bus at the given index
	 * 
	 * @param index
	 * @return
	 */
	public OnibusIqr getBusAtIndex(int index) {
		return this.listaOnibusIqr.get(index);
	}

	public int getBusQuantity() {
		return this.listaOnibusIqr.size();
	}

	/**
	 * @return if all buses on the line are in the exact same position
	 */
	public boolean busesOnSamePosition() {
		Ponto basePoint = this.listaOnibusIqr.get(0).getDistancePosition();
		boolean samePosition = true;
		for (OnibusIqr bus : this.listaOnibusIqr) {
			if (samePosition) {
				if (!bus.getDistancePosition().equals(basePoint)) {
					samePosition = false;
				}
			}
		}
		return samePosition;
	}

	public void addPointToRoute(Ponto point) {
		addPointToRoute(point.getLatitude(), point.getLongitude());
	}

	public void addPointToRoute(Double latitude, Double longitude) {
		this.getRoute().addPoint(latitude, longitude);
	}
}