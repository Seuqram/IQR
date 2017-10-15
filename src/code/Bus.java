package code;

/**
 * Represents a bus
 * @author rodri
 *
 */
public class Bus {
	private String identifier;
	private Line line;
	private Point position;
	
	/**
	 * 
	 * @param identifier - Unique string to identify bus
	 * @param line - Object from Line class
	 */
	public Bus(String identifier, Line line) {
		this.identifier = identifier;
		this.line = line;
		this.position = new Point(0, 0);
	}
	
	/**
	 * Constructor to create bus not related to any line
	 * 
	 * @param identifier - Unique string to identify bus
	 */
	public Bus(String identifier){
		this.identifier = identifier;
		this.line = null;
		this.position = new Point(0, 0);
	}

	public String getIdentifier() {
		return identifier;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	/**
	 * 
	 * @return the point that represents the bus position
	 */
	public Point getPosition() {
		return position;
	}	
	
	public boolean move(float x, float y){
		if (isValidMove(x, y)){
			this.position.setX(this.position.getX() + x);
			this.position.setY(this.position.getY() + y);
			return true;
		}
		return false;
	}
	
	private boolean isValidMove(float x, float y){
		if (this.getPosition().getX() + x <= this.getLine().getRoute().getMax().getX())
			if (this.getPosition().getY() + y <= this.getLine().getRoute().getMax().getY())
				return true;
		return false;
	}
	
}
