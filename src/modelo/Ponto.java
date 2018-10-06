package modelo;

import com.esri.core.geometry.Point;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a point in cartesian plain with
 * two atributtes, latitude and longitude
 *
 * @author Rodrigo
 */

@Data
public class Ponto extends Point {

    private List<Bairro> listaBairros;

    public double getLatitude() {
        return this.getX();
    }

    public double getLongitude() {
        return this.getY();
    }

    public Ponto(double latitude, double longitude) {
        super(latitude, longitude);
        this.listaBairros = new ArrayList<>();
    }


    public void addBairro(Bairro bairro) {
        if (this.listaBairros == null) {
            this.listaBairros = new ArrayList<>();
        }
        this.listaBairros.add(bairro);
    }
}