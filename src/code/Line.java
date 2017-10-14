package code;

public class Line {
	private int number;
	private Route route;

	/**
	 * 
	 * @param number that represents the line number
	 */
	public Line(int number, Route route) {
		this.route = route;
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public Route getRoute(){
		return route;
	}
	
	
}
