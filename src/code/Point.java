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
	
	public double getDistanceToPoint(Point givenPoint) {
		return this.getDistanceToPoint(givenPoint.getLatitude(), givenPoint.getLongitude());
	}
	
	public double getDistanceToPoint(double latitude, double longitude) {
		//FORMULA: X^2 = ((xa - xb)^2 + (ya - yb)^2)^(1/2)
		double distance = Math.sqrt(
				(Math.pow(this.latitude - latitude, 2.0)
						+
				 Math.pow(this.longitude - longitude,  2.0)));
		return distance;
	}
	
	public boolean equals(Point givenPoint) {
		return this.equals(givenPoint.getLatitude(), givenPoint.getLongitude());
	}
	
	public boolean equals(double givenLatitude, double givenLongitude) {
		if (this.getLatitude() == givenLatitude)
			if(this.getLongitude() == givenLongitude)
				return true;
		return false;
	}
	
}