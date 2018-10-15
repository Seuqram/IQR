package util;

import br.unirio.onibus.api.support.geo.PosicaoMapa;
import com.esri.core.geometry.*;

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
        return isInside(polygon, new Point(posicaoMapa.getLatitude(), posicaoMapa.getLongitude()));
    }

    private boolean isInside(Polygon polygon, Point point){
        Geometry simpleGeometry = OperatorSimplifyOGC.local().execute(polygon, null, true, null);
        return OperatorContains.local().execute(simpleGeometry, point, null, null) || OperatorTouches.local().execute(polygon, point, null, null);
    }
}
