package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class that represents a point in cartesian plain with
 * two atributtes, latitude and longitude
 *
 * @author Rodrigo
 */

@Data
public class Ponto {

    private double latitude;

    private double longitude;

    private List<Bairro> listaBairros;

    public Ponto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @param givenPoint
     * @return a double representing the distance
     * between the given point and the instance point
     */
    public double getDistanceToPoint(Ponto givenPoint) {
        return this.getDistanceToPoint(givenPoint.getLatitude(), givenPoint.getLongitude());
    }

    /**
     * Receives latitude and longitude (on that order) and return
     * the distance to point based on given coordinates
     *
     * @param latitude
     * @param longitude
     * @return a double representing the distance
     * between the given point and the instance point
     */
    public double getDistanceToPoint(double latitude, double longitude) {
        //FORMULA: X^2 = ((xa - xb)^2 + (ya - yb)^2)^(1/2)
        double distance = Math.sqrt(
                (Math.pow(this.latitude - latitude, 2.0)
                        +
                        Math.pow(this.longitude - longitude, 2.0)));
        return distance;
    }

    /**
     * @param givenPoint
     * @return if the givenPoint and the instance point
     * have the same latitude and longitude
     */
    public boolean equals(Ponto givenPoint) {
        return this.equals(givenPoint.getLatitude(), givenPoint.getLongitude());
    }

    /**
     * Receives latitude and longitude (in that order) and return
     * if the instance point has the same atributtes
     *
     * @param latitude
     * @param longitude
     * @return if the instance point has same latitude and longitude
     */
    public boolean equals(double givenLatitude, double givenLongitude) {
        if (this.getLatitude() == givenLatitude)
            if (this.getLongitude() == givenLongitude)
                return true;
        return false;
    }

}