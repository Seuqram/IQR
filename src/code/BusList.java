package code;

import java.util.ArrayList;
import java.util.List;

public class BusList
{
	private List<Bus> buses;
	
	public BusList()
	{
		this.buses = new ArrayList<Bus>();
	}
	
	public void addBus(Bus bus)
	{
		this.buses.add(bus);
	}
	
	public float calculateQualityIndex()
	{
		return 0.0f;
	}
}