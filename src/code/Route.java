package code;

/**
 * Represents a square route. The bus must pass through 4 limit points:
 * (0,0) (0,xMax) (xMax, yMax) (0, yMax)
 * @author Rodrigo
 * 
 */
public class Route {
	private Point max;

	public Route(Point max) {
		super();
		this.max = max;
	}

	public Point getMax() {
		return max;
	}

	public void setMax(Point max) {
		this.max = max;
	}
}
