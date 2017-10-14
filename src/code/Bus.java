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
		this.position = null;
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
