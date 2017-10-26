package code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents a point in cartesian plain with
 * two atributtes, latitude and longitude
 * @author rodri
 *
 */

@Getter @Setter @AllArgsConstructor
public class Point
{
	private double latitude;
	private double longitude;
	
	public int getDistanceToPoint(Point givenPoint) {
		return this.getDistanceToPoint(givenPoint.getLatitude(), givenPoint.getLongitude());
	}
	
	public int getDistanceToPoint(double latitude, double longitude) {
		//FORMULA: X^2 = ((xa - xb)^2 + (ya - yb)^2)^(1/2)
		int distance = (int)Math.sqrt(
				(Math.pow(this.latitude - latitude, 2.0)
						+
				 Math.pow(this.longitude - longitude,  2.0)));
		return distance;
	}
	
	public boolean equals(Point givenPoint) {
		if (this.getLatitude() == givenPoint.getLatitude())
			if(this.getLongitude() == givenPoint.getLongitude())
				return true;
		return false;
	}
	
	public static Point calculatePoint(int distance, Point startPoint, Point endPoint){
		Point resultPoint = new Point(startPoint.getLatitude(), startPoint.getLongitude());
		double distanceBetweenPoints = startPoint.getDistanceToPoint(endPoint.getLatitude(), endPoint.getLongitude());
		if (distance <= distanceBetweenPoints){
			if (startPoint.getLatitude() == endPoint.getLatitude()){
				resultPoint.setLongitude(
						startPoint.getLongitude() < endPoint.getLongitude() ? 
								distance + startPoint.getLongitude() : 
									startPoint.getLongitude() - distance);
			}
			else if (startPoint.getLongitude() == endPoint.getLongitude()){
				resultPoint.setLatitude(
						startPoint.getLatitude() < endPoint.getLatitude() ?
								distance + startPoint.getLatitude() :
									startPoint.getLatitude() - distance);
			}else{
				double newOppositeLeg = 0;
				double newAdjacentLeg = 0;
				if (startPoint.getLatitude() < endPoint.getLatitude()){
					double proportionalLatitude = endPoint.getLatitude() - startPoint.getLatitude();
					double auxiliarDouble = (proportionalLatitude * distance) / distanceBetweenPoints;
					newOppositeLeg = auxiliarDouble + startPoint.getLatitude();
				}
				if (startPoint.getLatitude() > endPoint.getLatitude()){
					double proportionalLatitude = startPoint.getLatitude() - endPoint.getLatitude();
					double auxiliarDouble = (proportionalLatitude * distance) / distanceBetweenPoints;
					newOppositeLeg = startPoint.getLatitude() - auxiliarDouble;
				}
				if (startPoint.getLongitude() < endPoint.getLongitude()){
					double proportionalLongitude = endPoint.getLongitude() - startPoint.getLongitude();
					double patati = (proportionalLongitude* distance) / distanceBetweenPoints;
					newAdjacentLeg = startPoint.getLongitude() + patati;
				}
				if (startPoint.getLongitude() > endPoint.getLongitude()){
					double proportionalLongitude = startPoint.getLongitude() - endPoint.getLongitude();
					double auxiliarDouble = (proportionalLongitude* distance) / distanceBetweenPoints;
					newAdjacentLeg = startPoint.getLongitude() - auxiliarDouble;
				}
				resultPoint = new Point(newOppositeLeg, newAdjacentLeg);
			}
		}
		return resultPoint;
	}
	
}