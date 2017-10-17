package code;

/**
 * Represents a bus
 * 
 * @author rodri
 *
 */
public class Bus
{
	private String identifier;
	private Line line;
	private Point position;

	/**
	 * 
	 * @param identifier
	 *            - Unique string to identify bus
	 * @param line
	 *            - Object from Line class
	 */
	public Bus(String identifier, Line line)
	{
		this.identifier = identifier;
		this.line = line;
		
		float latitude = line.getRoute().getPoint(0).getLatitude();
		float longitude = line.getRoute().getPoint(0).getLongitude();
		this.position = new Point(latitude, longitude);
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public Line getLine()
	{
		return line;
	}

	/**
	 * 
	 * @return the point that represents the bus position
	 */
	public Point getPosition()
	{
		return position;
	}

	public boolean move(float distance)
	{
//		float busX = this.position.getLatitude();
//		float busY = this.position.getLongitude();
//		float maxX = this.getLine().getRoute().getMax().getLatitude();
//		float maxY = this.getLine().getRoute().getMax().getLongitude();
//		if (isValidMove(x, y))
//		{
//			if (isOnBottonOrLeftLocation())
//			{
//				this.position.setLatitude(busX + x);
//				this.position.setLongitude(busY + y);
//				this.location = getBusLocationBasedOnPosition();
//				/*
//				 * IF THE MOVE CHANGES THE POSITION AND KEEP MOVING THE NEW
//				 * POSITION IS EQUAL: ACTUAL_POSITION + MOVEMENT - LIMIT
//				 */
//				if (busX + x > maxX)
//				{
//					this.position.setLatitude((busX + x)
//							- (this.getLine().getRoute().getMax().getLatitude()));
//				}
//
//				if (this.location == BUSLOCATION.TOP)
//				{
//					if (busY + y >= maxY)
//					{
//						if (busX + x > maxX)
//						{
//							this.position.setLatitude(maxX - x);
//						}
//					}
//				}
//				return true;
//			} else
//			{
//				this.position.setLatitude(busX - x);
//				this.position.setLongitude(busY - y);
//				this.location = getBusLocationBasedOnPosition();
//				return true;
//			}
//		}
		return false;
	}

//	private boolean isValidMove(float x, float y)
//	{
//		float busX = this.position.getLatitude();
//		float busY = this.position.getLongitude();
//		float maxX = this.getLine().getRoute().getMax().getLatitude();
//		float maxY = this.getLine().getRoute().getMax().getLongitude();
//
//		if (this.location == BUSLOCATION.BOTTON)
//		{
//			if (y > 0)
//			{
//				if (busX + x < maxX)
//					return false;
//			}
//		}
//		if (isOnBottonOrLeftLocation())
//		{
//			if ((x == 0) || (y == 0))
//			{
//				if (busX + x <= this.getLine().getRoute().getMax().getLatitude())
//					if (busY + y <= this.getLine().getRoute().getMax().getLongitude())
//						return true;
//			} else if ((busX + x == this.getLine().getRoute().getMax().getLatitude())
//					|| (busY + y == this.getLine().getRoute().getMax().getLongitude()))
//				return true;
//		} else
//		{
//			if ((x == 0) || (y == 0))
//			{
//				if (busX - x >= 0)
//					if (busY - y >= 0)
//						return true;
//			} else if ((busX - x == 0) || (busY - y == 0))
//				return true;
//		}
//		return false;
//	}
//
//	private BUSLOCATION getBusLocationBasedOnPosition()
//	{
//		float BusX = this.position.getLatitude();
//		float BusY = this.position.getLongitude();
//		float MaxX = this.getLine().getRoute().getMax().getLatitude();
//		float MaxY = this.getLine().getRoute().getMax().getLongitude();
//
//		if ((BusY == MaxY) && (BusX != 0))
//			return BUSLOCATION.TOP;
//		else if ((BusY != 0) && (BusX == 0))
//			return BUSLOCATION.RIGHT;
//		else if ((BusY == 0) && (BusX != MaxX))
//			return BUSLOCATION.BOTTON;
//		else
//			return BUSLOCATION.LEFT;
//	}
//
//	private boolean isOnBottonOrLeftLocation()
//	{
//		return ((this.location == BUSLOCATION.BOTTON)
//				|| (this.location == BUSLOCATION.LEFT));
//	}
}