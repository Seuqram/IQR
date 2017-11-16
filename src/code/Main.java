package code;

import java.util.ArrayList;
import java.util.Random;

public class Main{
	
	static ArrayList<Bus> busList = new ArrayList<>();
	static Line line = new Line(107);
	public static void main(String[] args){
		QualityCalculator calculator = QualityCalculator.getInstance();
		LinePrinter printer = LinePrinter.getInstance();
		line.getRoute().addPoint(0, 1);
		line.getRoute().addPoint(0, 4);
		line.getRoute().addPoint(0, 8);
		line.getRoute().addPoint(0, 12);
		addQuantityOfBusToList(10);
		Random numberGenerator = new Random();
		printer.printLineRouteData(line);
		printer.printBusesOnRoute(line);
		System.out.println();
		for (int index = 0; index < 20; index ++) {
			double random = numberGenerator.nextDouble() * 20;
			double distance = (random);
			moveBusAtIndex(index % 10, distance);
			System.out.println();
			printer.printBusesOnRoute(line);
			printer.printQuality(line);
		}
		System.out.println();
		printer.printBusesOnRoute(line);
		printer.printQuality(line);
	}
	
	public static void addBusToList() {
		int previousBusQuantity = busList.size();
		Random numberGenerator = new Random();
		busList.add(new Bus(String.valueOf(numberGenerator.nextInt(100)), line));
		int currentBusQuantity = busList.size();
		assert(currentBusQuantity - previousBusQuantity == 1);
	}
	
	public static void addQuantityOfBusToList(int quantity) {
		int previousBusQuantity = busList.size();
		for (int index = 0; index < quantity; index ++)
			addBusToList();
		int currentBusQuantity = busList.size();
		assert(currentBusQuantity - previousBusQuantity == quantity);
	}
	
	public static void moveBusAtIndex(int index, double distance) {
		Bus firstBus = busList.get(index);
		firstBus.move(distance);
	}
}