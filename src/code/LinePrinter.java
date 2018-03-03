package code;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import br.unirio.onibus.api.gmaps.dinamico.DecoradorCaminhoEstaticoLinha;
import br.unirio.onibus.api.gmaps.dinamico.DecoradorPosicaoEstaticaMarcador;
import br.unirio.onibus.api.gmaps.dinamico.GeradorMapas;
import br.unirio.onibus.api.model.Trajetoria;
import br.unirio.onibus.api.model.Veiculo;
import br.unirio.onibus.api.support.geo.PosicaoMapa;

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

	public void publicaMapaHTML(List<Veiculo> listaDeVeiculosDaLinha, List<PosicaoMapa> listaPosicoesTrajetoria) {
		CalculadorIQR calculador = CalculadorIQR.getInstance();
		GeradorMapas geradorMapa = new GeradorMapas();
		LinhaIqr linhaIqr = calculador.getLinhaComPosicoesDeIdaEVolta(listaDeVeiculosDaLinha, listaPosicoesTrajetoria);
		RotaIqr rotaIqr = linhaIqr.getRotaIqr();
		double distanciaEsperada = linhaIqr.getExpectedBusDistance();

		List<String> colorList = Arrays.asList("red", "blue", "green", "pink", "black");
		int integer = 0;

		Ponto pontoAnterior = null;
		for (Iterator<Integer> iterator = calculador.getIndicesPontosMapaOrenados(linhaIqr).iterator(); iterator
				.hasNext();) {
			int key = iterator.next();
			Ponto pontoAtual = calculador.getMapIndicePonto(linhaIqr).get(key);
			String color = colorList.get(integer);
			DecoradorPosicaoEstaticaMarcador decoradorAtual = new DecoradorPosicaoEstaticaMarcador(
					pontoAtual.getLatitude(), pontoAtual.getLongitude(), color, "");
			geradorMapa.adiciona(decoradorAtual);
			if (pontoAnterior != null) {
				int indexPontoAtual = rotaIqr.getIndexOfPoint(pontoAtual);
				int indexPontoAnterior = rotaIqr.getIndexOfPoint(pontoAnterior);
				double distanciaEntreOnibus = calculador.getDistanciaEntreOnibus(rotaIqr, indexPontoAtual,
						indexPontoAnterior);
				DecoradorCaminhoEstaticoLinha decoradorCaminho = new DecoradorCaminhoEstaticoLinha(
						getTrajetoriaLocal(indexPontoAnterior, indexPontoAtual, rotaIqr));
				decoradorCaminho.setCor(color);
				geradorMapa.adiciona(decoradorCaminho);
				integer = integer++ == 4 ? 0 : integer++;
				double indice = calculador.getIqrLocal(rotaIqr, distanciaEsperada, distanciaEntreOnibus);
				DecoradorPosicaoEstaticaMarcador decoradorAnterior = new DecoradorPosicaoEstaticaMarcador(
						pontoAnterior.getLatitude(), pontoAnterior.getLongitude(), color,
						String.valueOf(calculador.arredondaPorcentagem(indice)));
				geradorMapa.adiciona(decoradorAnterior);
			}
			pontoAnterior = pontoAtual;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
			try {
				geradorMapa.publica("result/" + LocalDateTime.now().format(dtf));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Trajetoria getTrajetoriaLocal(int indexPontoAnterior, int indexPontoAtual, RotaIqr rotaIqr) {
		RotaIqr rota = new RotaIqr();
		List<PosicaoMapa> list = new ArrayList<>();
		for (int index = indexPontoAnterior; index <= indexPontoAtual; index++) {
			Ponto pointAtIndex = rotaIqr.getPointAtIndex(index);
			rota.addPoint(pointAtIndex);
			list.add(new PosicaoMapa(pointAtIndex.getLatitude(), pointAtIndex.getLongitude()));
		}
		Trajetoria trajetoria = new Trajetoria();
		for (int index = 0; index < rota.getPointsQuantity(); index++) {
			Ponto pointAtIndex = rota.getPointAtIndex(index);
			trajetoria.adiciona(pointAtIndex.getLatitude(), pointAtIndex.getLongitude());
		}
		return trajetoria;
	}

	public void printLine(LinhaIqr line) {
		printLineRouteData(line);
		printBusesOnRoute(line);
		printQuality(line);
		printBreakLine();
	}

	public void printQuality(LinhaIqr line) {
		System.out.print("\nQualidade da Rota: ");
		// CalculadorIQR calculator = CalculadorIQR.getInstance();
		// printValue(calculator.getRouteQuality(line));
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
