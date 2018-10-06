package modelo;

import com.esri.core.geometry.Point;
import lombok.Data;

import java.util.List;

@Data
public class Bairro {

    private String nome;

    private List<Point> divisas;
}
