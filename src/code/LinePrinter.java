package code;

import java.text.DecimalFormat;

public class LinePrinter {
	private static LinePrinter printer = null;
	private static char startPointSymbol = (char) 166;
	private static char endPointSymbol = startPointSymbol;
	private static DecimalFormat df2 = new DecimalFormat("0.##");

	private LinePrinter() {
	};

	public static LinePrinter getInstance() {
		if (printer == null)
			printer = new LinePrinter();
		return printer;
	}

	public void printLine(LinhaIqr line) {
		printLineRouteData(line);
		printBusesOnRoute(line);
		printQuality(line);
		printBreakLine();
	}

	public void printQuality(LinhaIqr line) {
		System.out.print("\nQualidade da Rota: ");
		QualityCalculator calculator = QualityCalculator.getInstance();
		printValue(calculator.getRouteQuality(line));
		System.out.println("%");
	}

	public double getDistanceBetweenPoints(Ponto pointOne, Ponto pointTwo) {
		return pointOne.getDistanceToPoint(pointTwo);
	}

	public void printBusesOnRoute(LinhaIqr line) {
		printStartSymbol();
		line.sortBusList();
		int busQuantity = line.getBusQuantity();
		double routeSize = line.getRotaIqr().getRouteSize();
		Ponto previousPoint = null;
		for (int index = 0; index < busQuantity; index++) {
			OnibusIqr bus = line.getBusAtIndex(index);
			if (index == 0) {
				previousPoint = new Ponto(0, 0);
				double distanceBetweenStartAndFirstBus = getDistanceBetweenPoints(previousPoint,
						bus.getDistancePosition());
				if (distanceBetweenStartAndFirstBus > 0)
					printDistance(distanceBetweenStartAndFirstBus);
				printBus(bus);
			} else {
				double distanceBetweenBuses = getDistanceBetweenPoints(previousPoint, bus.getDistancePosition());
				if (distanceBetweenBuses > 0)
					printDistance(distanceBetweenBuses);
				printBus(bus);
			}
			if (index + 1 == busQuantity) {
				double distanceToEndPoint = getDistanceBetweenPoints(bus.getDistancePosition(),
						new Ponto(0, routeSize));
				if (distanceToEndPoint > 0)
					printDistance(distanceToEndPoint);
			}
			previousPoint = bus.getDistancePosition();
		}
		printEndSymbol();
	}

	public void printLineRouteData(LinhaIqr line) {
		int busQuantity = line.getBusQuantity();
		double routeSize = line.getRotaIqr().getRouteSize();
		System.out.println("N�mero Linha: " + line.getNumber());
		System.out.println("Quantidade de Onibus: " + busQuantity);
		System.out.println("Tamanho da Rota: " + routeSize);
		System.out.print("Dist�ncia esperada: ");
		printValue(line.getExpectedBusDistance());
		System.out.println();
	}

	private void printBreakLine() {
		System.out.println("\n==============================================\n");
	}

	private void printBus(OnibusIqr bus) {
		String busIdentifier = String.valueOf(bus.getIdentificador());
		if (busIdentifier.length() == 1)
			busIdentifier = "0" + busIdentifier;

		System.out.print("|" + busIdentifier + "|");
	}

	private void printStartSymbol() {
		printSymbol(startPointSymbol);
	}

	private void printEndSymbol() {
		printSymbol(endPointSymbol);
	}

	private void printSymbol(char symbol) {
		System.out.print(symbol);
	}

	private void printValue(double value) {
		System.out.print(df2.format(value));
	}

	private void printDistanceSeparator() {
		System.out.print("---");
	}

	private void printDistance(double distance) {
		printDistanceSeparator();
		printValue(distance);
		printDistanceSeparator();
	}
}
