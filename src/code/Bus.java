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
		super();
		this.identifier = identifier;
		this.line = line;
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

	public Point getPosition() {
		return position;
	}	
	
}
