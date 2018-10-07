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

    private static boolean isInside(List<Point> pontoList, Ponto p) {
        Polygon polygon = new Polygon();
        polygon.startPath(pontoList.get(0));
        pontoList.forEach(polygon::lineTo);
        return OperatorContains.local().execute(polygon, p, null, null) || OperatorTouches.local().execute(polygon, p, null, null);
    }

    public boolean isInside(List<Point> pontos, PosicaoMapa posicao) {
        Ponto p = new Ponto(posicao.getLatitude(), posicao.getLongitude());
        return isInside(pontos, p);
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
