package util;

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

    public static boolean onSegment(Ponto p, Ponto q, Ponto r) {
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

    public static int orientation(Ponto p, Ponto q, Ponto r) {
        int qx = (int) q.getLatitude();
        int qy = (int) q.getLongitude();

        int px = (int) p.getLatitude();
        int py = (int) p.getLongitude();

        int rx = (int) r.getLatitude();
        int ry = (int) r.getLongitude();

        int val = ((qy - py) * (rx - qx)) - ((qx - px) * (ry - qy));

        if (val == 0)
            return 0;
        return (val > 0) ? 1 : 2;
    }

    public static boolean doIntersect(Ponto p1, Ponto q1, Ponto p2, Ponto q2) {

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

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

    public static boolean isInside(List<Ponto> polygon, int n, Ponto p) {
        int INF = 10000;
        if (n < 3)
            return false;

        Ponto extreme = new Ponto(INF, p.getLongitude());

        int count = 0, i = 0;
        do {
            int next = (i + 1) % n;
            if (doIntersect(polygon.get(i), polygon.get(next), p, extreme)) {
                if (orientation(polygon.get(i), p, polygon.get(next)) == 0)
                    return onSegment(polygon.get(i), p, polygon.get(next));

                count++;
            }
            i = next;
        } while (i != 0);

        return (count & 1) == 1 ? true : false;
    }
    
}
