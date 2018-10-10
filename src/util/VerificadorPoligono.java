package util;

import br.unirio.onibus.api.support.geo.PosicaoMapa;
import com.esri.core.geometry.OperatorContains;
import com.esri.core.geometry.OperatorTouches;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;

import java.util.List;

public class VerificadorPoligono {

    private static VerificadorPoligono instance;

    private VerificadorPoligono() {
    }

    public static VerificadorPoligono getInstance() {
        if (instance == null) {
            instance = new VerificadorPoligono();
        }
        return instance;
    }

    public boolean isInside(Polygon polygon, PosicaoMapa posicaoMapa){
        Point point = new Point();
        point.setX(posicaoMapa.getLatitude());
        point.setY(posicaoMapa.getLongitude());
        return isInside(polygon, point);
    }

    private boolean isInside(Polygon polygon, Point point){
        return OperatorContains.local().execute(polygon, point, null, null) || OperatorTouches.local().execute(polygon, point, null, null);
    }
}
