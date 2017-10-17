package code;

/**
 * Represents a bus
 * @author rodri
 *
 */


public class Bus {
	public enum BUSLOCATION {
		TOP, LEFT, RIGHT, BOTTON
	}
	
	private String identifier;
	private Line line;
	private Point position;
	private BUSLOCATION location;
	
	/**
	 * 
	 * @param identifier - Unique string to identify bus
	 * @param line - Object from Line class
	 */
	public Bus(String identifier, Line line) {
		this.identifier = identifier;
		this.line = line;
		this.position = new Point(0, 0);
		this.location = BUSLOCATION.BOTTON;
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
	
	public BUSLOCATION getLocation(){
		return location;
	}
	
	public boolean move(float x, float y){
		float busX = this.position.getX();
		float busY = this.position.getY();
		float maxX = this.getLine().getRoute().getMax().getX();
		float maxY = this.getLine().getRoute().getMax().getY();
		if (isValidMove(x, y)){
			if (isOnBottonOrLeftLocation()){
				this.position.setX(busX + x);
				this.position.setY(busY + y);
				this.location = getBusLocationBasedOnPosition();
				/* IF THE MOVE CHANGES THE POSITION AND KEEP MOVING THE NEW POSITION
				*  IS EQUAL: ACTUAL_POSITION + MOVEMENT - LIMIT 
				*/
				if (busX + x > maxX){
					this.position.setX((busX + x) - (this.getLine().getRoute().getMax().getX()));
				}
				
				if (this.location == BUSLOCATION.TOP){
					if (busY + y >= maxY){
						if (busX + x > maxX){
							this.position.setX(maxX - x);
						}
					}
				}
				return true;
			}else{
				this.position.setX(busX - x);
				this.position.setY(busY - y);
				this.location = getBusLocationBasedOnPosition();
				return true;
			}
		}
		return false;
	}
	
	private boolean isValidMove(float x, float y){
		float busX = this.position.getX();
		float busY = this.position.getY();
		float maxX = this.getLine().getRoute().getMax().getX();
		float maxY = this.getLine().getRoute().getMax().getY();
		
		if (this.location == BUSLOCATION.BOTTON){
			if (y > 0){
				if (busX + x < maxX)
					return false;
			}
		}
		if (isOnBottonOrLeftLocation()){ 
			if ((x == 0) || (y == 0)){
				if (busX + x <= this.getLine().getRoute().getMax().getX()) 
					if (busY + y <= this.getLine().getRoute().getMax().getY())
						return true;
			}else
				if ((busX + x == this.getLine().getRoute().getMax().getX()) ||
						(busY + y == this.getLine().getRoute().getMax().getY()))
					return true;
		}else{
			if ((x == 0) || (y == 0)){
				if (busX - x >= 0)
					if (busY - y >= 0)
						return true;
			}else
				if ((busX - x == 0) || (busY - y == 0))
					return true;
		}
		return false;
	}
	
	private BUSLOCATION getBusLocationBasedOnPosition(){
		float BusX = this.position.getX();
		float BusY = this.position.getY();
		float MaxX = this.getLine().getRoute().getMax().getX();
		float MaxY = this.getLine().getRoute().getMax().getY();
		
		if ((BusY == MaxY) && (BusX != 0))
			return BUSLOCATION.TOP;
		else
			if ((BusY != 0) && (BusX == 0))
				return BUSLOCATION.RIGHT;
			else
				if ((BusY == 0) && (BusX != MaxX))
					return BUSLOCATION.BOTTON;
				else
					return BUSLOCATION.LEFT;
	}
	
	private boolean isOnBottonOrLeftLocation(){
		return ((this.location == BUSLOCATION.BOTTON) || (this.location == BUSLOCATION.LEFT));
	}
	
}