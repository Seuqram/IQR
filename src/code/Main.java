package code;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.decimal4j.util.DoubleRounder;

import br.unirio.onibus.api.download.BaixadorPosicaoVeiculos;
import br.unirio.onibus.api.gmaps.dinamico.DecoradorCaminhoEstaticoLinha;
import br.unirio.onibus.api.gmaps.dinamico.DecoradorPosicaoEstaticaMarcador;
import br.unirio.onibus.api.gmaps.dinamico.GeradorMapas;
import br.unirio.onibus.api.gmaps.estatico.StaticMapGenerator;
import br.unirio.onibus.api.model.ConjuntoLinhas;
import br.unirio.onibus.api.model.Linha;
import br.unirio.onibus.api.model.PosicaoVeiculo;
import br.unirio.onibus.api.model.Repositorio;
import br.unirio.onibus.api.model.Trajetoria;
import br.unirio.onibus.api.model.Veiculo;
import br.unirio.onibus.api.support.geo.PosicaoMapa;

public class Main {

	static ArrayList<OnibusIqr> busList = new ArrayList<>();
	static String nomeArquivo = "data/iqr.txt";
	static String numeroLinha = "107";
	static String idTrajeto = "12863255";

	public static void main(String[] args) throws IOException {
		// ConjuntoLinhas linhas = new BaixadorPosicaoVeiculos().baixa();
		ConjuntoLinhas linhas = new BaixadorPosicaoVeiculos().carrega("data/instantaneo.csv");
		Linha linha = linhas.pegaLinha(numeroLinha);
		Repositorio repositorio = new Repositorio("data");
		repositorio.carregaTrajeto(linha, idTrajeto);
		List<Veiculo> listaDeVeiculosDaLinha = (List<Veiculo>) linha.getVeiculos();
		List<PosicaoMapa> listaPosicoesTrajetoria = getListaPosicoesTrajetoria(linha);
		LinhaIqr linhaIqr = getLinhaComPosicoesDeIdaEVolta(listaDeVeiculosDaLinha, listaPosicoesTrajetoria);
		RotaIqr rotaIqr = linhaIqr.getRotaIqr();

		// GeradorMapas gm = new GeradorMapas();
		StaticMapGenerator smg = new StaticMapGenerator("terrain", "1280x1280");

		Map<Integer, Ponto> map = new TreeMap<>();
		for (int index = 0; index < linhaIqr.getBusQuantity(); index++) {
			OnibusIqr bus = linhaIqr.getBusAtIndex(index);
			Ponto pontoDaRotaMaisProximo = bus.getPontoDaRotaMaisProximo();
			int indexOfPoint = rotaIqr.getIndexOfPoint(pontoDaRotaMaisProximo);
			if (!(indexOfPoint > 334 && indexOfPoint < 370)) {
				map.put(indexOfPoint, pontoDaRotaMaisProximo);
			}
		}
		Set<Integer> keySet = map.keySet();
		double expectedBusDistance = linhaIqr.getExpectedBusDistance();
		double totalDistance = 0;
		Ponto pontoAnterior = null;
		boolean b = false;

		List<String> colorList = Arrays.asList("red", "blue", "green", "pink", "black");
		int integer = 0;

		GeradorMapas gm = new GeradorMapas();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			int key = (int) iterator.next();
			Ponto pontoAtual = map.get(key);
			String color = colorList.get(integer);
			DecoradorPosicaoEstaticaMarcador decoradorAtual = new DecoradorPosicaoEstaticaMarcador(
					pontoAtual.getLatitude(), pontoAtual.getLongitude(), color, "");
			gm.adiciona(decoradorAtual);
			if (pontoAnterior != null) {
				int indexPontoAtual = rotaIqr.getIndexOfPoint(pontoAtual);
				int indexPontoAnterior = rotaIqr.getIndexOfPoint(pontoAnterior);
				RotaIqr rota = new RotaIqr();
				List<PosicaoMapa> list = new ArrayList<>();
				for (int index = indexPontoAnterior; index <= indexPontoAtual; index++) {
					Ponto pointAtIndex = rotaIqr.getPointAtIndex(index);
					rota.addPoint(pointAtIndex);
					list.add(new PosicaoMapa(pointAtIndex.getLatitude(), pointAtIndex.getLongitude()));
				}
				double distanceBetweenBuses = rota.getRouteSize();
				Trajetoria trajetoria = new Trajetoria();
				for (int index = 0; index < rota.getPointsQuantity(); index++) {
					Ponto pointAtIndex = rota.getPointAtIndex(index);
					trajetoria.adiciona(pointAtIndex.getLatitude(), pointAtIndex.getLongitude());
				}
				DecoradorCaminhoEstaticoLinha decoradorCaminho = new DecoradorCaminhoEstaticoLinha(trajetoria);
				decoradorCaminho.setCor(color);
				gm.adiciona(decoradorCaminho);
				integer = integer++ == 4 ? 0 : integer++;
				double indice = 1;
				if (distanceBetweenBuses == expectedBusDistance) {
					// totalDistance += 1;
				} else {
					if (distanceBetweenBuses < expectedBusDistance) {
						indice = distanceBetweenBuses / expectedBusDistance;
					} else {
						indice = 1 - ((distanceBetweenBuses - expectedBusDistance)
								/ (rotaIqr.getRouteSize() - expectedBusDistance));
					}
				}
				totalDistance += indice;
				DecoradorPosicaoEstaticaMarcador decoradorAnterior = new DecoradorPosicaoEstaticaMarcador(
						pontoAnterior.getLatitude(), pontoAnterior.getLongitude(), color,
						String.valueOf(DoubleRounder.round(indice * 100, 2)));
				gm.adiciona(decoradorAnterior);
			}
			pontoAnterior = pontoAtual;
		}

		// linha.getTrajetoIda().pegaPosicoes().forEach(posicao -> {
		// DecoradorPosicaoEstaticaMarcador decorador = new
		// DecoradorPosicaoEstaticaMarcador(posicao.getLatitude(),
		// posicao.getLongitude(), "green",
		// String.valueOf(linha.getTrajetoIda().pegaIndicePosicao(posicao)));
		// gm.adiciona(decorador);
		// });
		// for (int index = 0; index < line.getBusQuantity(); index++) {
		// Bus bus = line.getBusAtIndex(index);
		// DecoradorPosicaoEstaticaMarcador decorador = new
		// DecoradorPosicaoEstaticaMarcador(
		// bus.getPontoDaRotaMaisProximo().getLatitude(),
		// bus.getPontoDaRotaMaisProximo().getLongitude(),
		// "red", String.valueOf(index + 1));
		// gm.adiciona(decorador);
		// }
		// 335 370
		double distanceQuantity = linhaIqr.getBusQuantity() - 1;
		// System.out.println(DoubleRounder.round((totalDistance / distanceQuantity) *
		// 100, 2));
		System.out.println(
				CalculadorIQR.getInstance().getQualidadeRota(listaDeVeiculosDaLinha, listaPosicoesTrajetoria));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
		gm.publica("result/" + LocalDateTime.now().format(dtf));
	}

	private static List<PosicaoMapa> getListaPosicoesTrajetoria(Linha linha) {
		List<PosicaoMapa> listaPosicoesTrajetoria = new ArrayList<>();
		((List<PosicaoMapa>) linha.getTrajetoIda().pegaPosicoes()).forEach(posicao -> {
			listaPosicoesTrajetoria.add(posicao);
		});
		((List<PosicaoMapa>) linha.getTrajetoVolta().pegaPosicoes()).forEach(posicao -> {
			listaPosicoesTrajetoria.add(posicao);
		});
		return listaPosicoesTrajetoria;
	}

	private static LinhaIqr getLinhaComPosicoesDeIdaEVolta(List<Veiculo> listaDeVeiculosDaLinha,
			List<PosicaoMapa> listaPosicoesTrajetoria) {
		LinhaIqr line = new LinhaIqr(624);
		listaPosicoesTrajetoria.forEach(ponto -> {
			line.addPointToRoute(ponto.getLatitude(), ponto.getLongitude());
		});
		listaDeVeiculosDaLinha.forEach(veiculo -> {
			PosicaoVeiculo next = veiculo.getTrajetoria().getPosicoes().iterator().next();
			OnibusIqr bus = new OnibusIqr(veiculo.getNumeroSerie(), line, next.getLatitude(), next.getLongitude());
		});
		return line;
	}
}