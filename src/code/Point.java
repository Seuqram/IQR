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
	
	public static Point calculatePoint(int distance, Point startPoint, Point endPoint){
		double hypotenuse = startPoint.getDistanceToPoint(endPoint.getLatitude(), endPoint.getLongitude());
		if (hypotenuse < distance)
			return startPoint;
		double oppositeLeg = endPoint.getLatitude();
		double adjacentLeg = endPoint.getLongitude();		
		double newHypotenuse = distance;
		
		if (startPoint.getLatitude() > endPoint.getLatitude()){
			if (startPoint.getLongitude() > endPoint.getLongitude()){
				newHypotenuse = hypotenuse - distance;
				oppositeLeg = startPoint.getLatitude();
				adjacentLeg = startPoint.getLongitude();
			}
		}
		double sin = oppositeLeg / hypotenuse;
		double cosin = adjacentLeg / hypotenuse;
		double newOppositeLeg = sin * newHypotenuse;
		double newAdjacentLeg = cosin * newHypotenuse;
		Point point = new Point(newOppositeLeg, newAdjacentLeg);
		return point;
	}
	
}