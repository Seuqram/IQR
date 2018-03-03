package code;

import lombok.Getter;

/**
 * Represents a bus
 * 
 * @author rodrigo
 *
 */
@Getter
public class OnibusIqr {
	private String identificador;
	private Line line;
	private Ponto routePosition;
	private Ponto distancePosition;
	private Ponto pontoDaRotaMaisProximo;
	
	public OnibusIqr(String identifier, Line line) {
		this.identificador = identifier;
		this.line = line;
		this.line.addBus(this);
		
		this.routePosition = new Ponto(0, 0);
		this.distancePosition = new Ponto(0, 0);
		this.pontoDaRotaMaisProximo = getPontoDaRotaMaisProximoPosicaoAtual();
	}

	/**
	 * Bus constructor, receives a identifier that should be unique and a line that
	 * contains a route. The start position of the bus is the first point of the
	 * route of the line. The constructor adds the bus to the bus' line
	 * 
	 * @param identifier
	 *            unique string to identify bus
	 * @param line
	 *            object from Line class
	 */
	public OnibusIqr(String identifier, Line line, double latitude, double longitude) {
		this.identificador = identifier;
		this.line = line;
		this.line.addBus(this);
		
		this.routePosition = new Ponto(latitude, longitude);
		this.distancePosition = new Ponto(0, 0);
		this.pontoDaRotaMaisProximo = getPontoDaRotaMaisProximoPosicaoAtual();
	}

	/**
	 * Moves the bus in the straight line that represents the route size. The bus
	 * can not move a distance bigger than the route size
	 * 
	 * @param distance
	 */
	public boolean move(double distance) {
		double currentLongitude = this.distancePosition.getLongitude();
		double routeSize = this.line.getRoute().getRouteSize();

		if (distance < routeSize) {
			double sumCurrentLongitudeAndDistance = currentLongitude + distance;
			if (currentLongitude + distance >= routeSize) {
				distance = routeSize > sumCurrentLongitudeAndDistance ? routeSize - sumCurrentLongitudeAndDistance
						: sumCurrentLongitudeAndDistance - routeSize;
				currentLongitude = 0;
			}
			this.distancePosition.setLongitude(currentLongitude + distance);
			return true;
		} else
			return false;
	}

	/**
	 * 
	 * @return the Longitude of the distance point
	 */
	public double getLongitudeDistancePosition() {
		return this.distancePosition.getLongitude();
	}

	public void setPonto(double latitude, double longitude) {
		this.getRoutePosition().setLatitude(latitude);
		this.getRoutePosition().setLongitude(longitude);
	}

	public Ponto getPontoDaRotaMaisProximoPosicaoAtual() {
		Route route = line.getRoute();
		double distancia = route.getPointAtIndex(0).getDistanceToPoint(this.getRoutePosition());
		Ponto ponto = route.getPointAtIndex(0);
		for (int index = 1; index < route.getPointsQuantity(); index++) {
			Ponto pointAtIndex = route.getPointAtIndex(index);
			double distanciaTemporaria = pointAtIndex.getDistanceToPoint(this.getRoutePosition());
			if (distanciaTemporaria < distancia) {
				distancia = distanciaTemporaria;
				ponto = pointAtIndex;
			}
		}
		return ponto;
	}
}