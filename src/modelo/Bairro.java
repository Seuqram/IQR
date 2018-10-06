package modelo;

import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import lombok.Data;

import java.util.List;

@Data
public class Bairro {

    private String nome;

    private Polygon polygon;

    public void setDivisas(List<Point> pointList) {
        this.polygon = new Polygon();
        this.polygon.startPath(pointList.get(0));
        pointList.forEach(this.polygon::lineTo);
    }
}
