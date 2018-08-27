package util;

import br.unirio.onibus.api.support.geo.PosicaoMapa;
import modelo.Ponto;

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

    public boolean onSegment(Ponto p, Ponto q, Ponto r) {
        if (q.getLatitude() <= Math.max(p.getLatitude(), r.getLatitude())) {
            if (q.getLatitude() >= Math.min(p.getLatitude(), r.getLatitude())) {
                if (q.getLongitude() <= Math.max(p.getLongitude(), r.getLongitude())) {
                    if (q.getLongitude() >= Math.min(p.getLongitude(), r.getLongitude())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public long orientation(Ponto p, Ponto q, Ponto r) {
        long qx = (long) (q.getLatitude() * 1000000);
        long qy = (long) (q.getLongitude() * 1000000);

        long px = (long) (p.getLatitude() * 1000000);
        long py = (long) (p.getLongitude() * 1000000);

        long rx = (long) (r.getLatitude() * 1000000);
        long ry = (long) (r.getLongitude() * 1000000);

        long val = ((qy - py) * (rx - qx)) - ((qx - px) * (ry - qy));

        if (val == 0)
            return 0;
        return (val > 0) ? 1 : 2;
    }

    public boolean dolongersect(Ponto p1, Ponto q1, Ponto p2, Ponto q2) {

        long o1 = orientation(p1, q1, p2);
        long o2 = orientation(p1, q1, q2);
        long o3 = orientation(p2, q2, p1);
        long o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4)
            return true;

        if (o1 == 0 && onSegment(p1, p2, q1))
            return true;

        if (o2 == 0 && onSegment(p1, q2, q1))
            return true;

        if (o3 == 0 && onSegment(p2, p1, q2))
            return true;

        if (o4 == 0 && onSegment(p2, q1, q2))
            return true;

        return false;
    }

    public boolean isInside(List<Ponto> polygon, int n, Ponto p) {
        long INF = 10000;
        if (n < 3)
            return false;

        Ponto extreme = new Ponto(p.getLatitude(), INF);

        int count = 0, i = 0;
        do {
            int next = (i + 1) % n;
            if (dolongersect(polygon.get(i), polygon.get(next), p, extreme)) {
                if (orientation(polygon.get(i), p, polygon.get(next)) == 0) {
                    return onSegment(polygon.get(i), p, polygon.get(next));
                }

                count++;
            }
            i = next;
        } while (i != 0);

        return (count & 1) == 1 ? true : false;
    }

    public boolean isInside(List<Ponto> divisas, PosicaoMapa posicao) {
        Ponto p = new Ponto(posicao.getLatitude(), posicao.getLongitude());
        return this.isInside(divisas, divisas.size(), p);
    }
}
