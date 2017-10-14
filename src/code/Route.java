package code;

/**
 * Represents a square route. The bus must pass through 4 limit points:
 * (0,0) (0,xMax) (xMax, yMax) (0, yMax)
 * @author Rodrigo
 *
 */
public class Route {
	private Point xMax;
	private Point yMax;

	public Route(Point xMax, Point yMax) {
		super();
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	public Point getxMax() {
		return xMax;
	}
	public void setxMax(Point xMax) {
		this.xMax = xMax;
	}
	public Point getyMax() {
		return yMax;
	}
	public void setyMax(Point yMax) {
		this.yMax = yMax;
	}
	
	
	
}
