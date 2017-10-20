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
	
	public int getDistanceToPoint(double latitude, double longitude) {
		//FORMULA: X^2 = ((xa - xb)^2 + (ya - yb)^2)^(1/2)
		int distance = (int)Math.sqrt(
				(Math.pow(this.latitude - latitude, 2.0)
						+
				 Math.pow(this.longitude - longitude,  2.0)));
		return distance;
	}
	
}