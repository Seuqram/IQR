package code;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.decimal4j.util.DoubleRounder;

import br.unirio.onibus.api.model.PosicaoVeiculo;
import br.unirio.onibus.api.model.Veiculo;
import br.unirio.onibus.api.support.geo.PosicaoMapa;

public class CalculadorIQR {
	private static CalculadorIQR calculadorQualidade = null;

	private CalculadorIQR() {
	};

	public static CalculadorIQR getInstance() {
		if (calculadorQualidade == null)
			calculadorQualidade = new CalculadorIQR();
		return calculadorQualidade;
	}

	public double getQualidadeRota(List<Veiculo> listaDeVeiculosDaLinha, List<PosicaoMapa> listaPosicoesTrajetoria) {
		LinhaIqr linhaIqr = getLinhaComPosicoesDeIdaEVolta(listaDeVeiculosDaLinha, listaPosicoesTrajetoria);
		RotaIqr rotaIqr = linhaIqr.getRotaIqr();
		Map<Integer, Ponto> mapIndicePontos = getMapIndicePonto(linhaIqr);
		Set<Integer> indicesPontosMapaOrenados = getIndicesPontosMapaOrenados(linhaIqr);
		double distanciaEsperada = linhaIqr.getExpectedBusDistance();
		double iqrTotal = 0;

		Ponto pontoAnterior = null;

		for (Iterator<Integer> iterator = indicesPontosMapaOrenados.iterator(); iterator.hasNext();) {
			int key = iterator.next();
			Ponto pontoAtual = mapIndicePontos.get(key);
			if (pontoAnterior != null) {
				int indexPontoAtual = rotaIqr.getIndexOfPoint(pontoAtual);
				int indexPontoAnterior = rotaIqr.getIndexOfPoint(pontoAnterior);
				double distanciaEntreOnibus = getDistanciaEntreOnibus(rotaIqr, indexPontoAtual, indexPontoAnterior);
				double indice = getIqrLocal(rotaIqr, distanciaEsperada, distanciaEntreOnibus);
				iqrTotal = iqrTotal + indice;
			}
			pontoAnterior = pontoAtual;
		}
		double distanceQuantity = linhaIqr.getBusQuantity() - getQuantidadeOnibusIgnorados(linhaIqr) - 1;
		double iqrMedio = DoubleRounder.round((iqrTotal / distanceQuantity) * 100, 2);
		return iqrMedio;
	}

	private double getIqrLocal(RotaIqr rotaIqr, double distanciaEsperada, double distanciaEntreOnibus) {
		if (distanciaEntreOnibus == distanciaEsperada) {
			return 1;
		} else {
			if (distanciaEntreOnibus < distanciaEsperada) {
				return distanciaEntreOnibus / distanciaEsperada;
			} else {
				return 1 - ((distanciaEntreOnibus - distanciaEsperada) / (rotaIqr.getRouteSize() - distanciaEsperada));
			}
		}
	}

	private double getDistanciaEntreOnibus(RotaIqr rotaIqr, int indexPontoAtual, int indexPontoAnterior) {
		RotaIqr rotaIqrAux = new RotaIqr();
		List<PosicaoMapa> list = new ArrayList<>();
		for (int index = indexPontoAnterior; index <= indexPontoAtual; index++) {
			Ponto pointAtIndex = rotaIqr.getPointAtIndex(index);
			rotaIqrAux.addPoint(pointAtIndex);
			list.add(new PosicaoMapa(pointAtIndex.getLatitude(), pointAtIndex.getLongitude()));
		}
		double distanciaEntreOnibus = rotaIqrAux.getRouteSize();
		return distanciaEntreOnibus;
	}

	private int getQuantidadeOnibusIgnorados(LinhaIqr linhaIqr) {
		RotaIqr rotaIqr = linhaIqr.getRotaIqr();
		int onibusIgnorados = 0;
		for (int index = 0; index < linhaIqr.getBusQuantity(); index++) {
			OnibusIqr onibusIqr = linhaIqr.getBusAtIndex(index);
			Ponto pontoDaRotaMaisProximo = onibusIqr.getPontoDaRotaMaisProximo();
			int indexOfPoint = rotaIqr.getIndexOfPoint(pontoDaRotaMaisProximo);
			if ((indexOfPoint > 334 && indexOfPoint < 370)) {
				onibusIgnorados++;
			}
		}
		return onibusIgnorados;
	}

	private Map<Integer, Ponto> getMapIndicePonto(LinhaIqr linhaIqr) {
		RotaIqr rotaIqr = linhaIqr.getRotaIqr();
		Map<Integer, Ponto> map = new TreeMap<>();
		for (int index = 0; index < linhaIqr.getBusQuantity(); index++) {
			OnibusIqr onibusIqr = linhaIqr.getBusAtIndex(index);
			Ponto pontoDaRotaMaisProximo = onibusIqr.getPontoDaRotaMaisProximo();
			int indexOfPoint = rotaIqr.getIndexOfPoint(pontoDaRotaMaisProximo);
			// PONTOS CONSIDERADOS NO PONTO FINAL
			if (!(indexOfPoint > 334 && indexOfPoint < 370)) {
				map.put(indexOfPoint, pontoDaRotaMaisProximo);
			}
		}
		return map;
	}

	private Set<Integer> getIndicesPontosMapaOrenados(LinhaIqr linhaIqr) {
		Map<Integer, Ponto> map = new TreeMap<>();
		RotaIqr rotaIqr = linhaIqr.getRotaIqr();
		for (int index = 0; index < linhaIqr.getBusQuantity(); index++) {
			OnibusIqr onibusIqr = linhaIqr.getBusAtIndex(index);
			Ponto pontoDaRotaMaisProximo = onibusIqr.getPontoDaRotaMaisProximo();
			int indexOfPoint = rotaIqr.getIndexOfPoint(pontoDaRotaMaisProximo);
			if (!(indexOfPoint > 334 && indexOfPoint < 370)) {
				map.put(indexOfPoint, pontoDaRotaMaisProximo);
			}
		}
		return map.keySet();
	}

	private LinhaIqr getLinhaComPosicoesDeIdaEVolta(List<Veiculo> listaDeVeiculosDaLinha,
			List<PosicaoMapa> listaPosicoesTrajetoria) {
		LinhaIqr linhaIqr = new LinhaIqr(624);
		listaPosicoesTrajetoria.forEach(ponto -> {
			linhaIqr.addPointToRoute(ponto.getLatitude(), ponto.getLongitude());
		});
		listaDeVeiculosDaLinha.forEach(veiculo -> {
			PosicaoVeiculo next = veiculo.getTrajetoria().getPosicoes().iterator().next();
			@SuppressWarnings("unused")
			OnibusIqr onibusIqr = new OnibusIqr(veiculo.getNumeroSerie(), linhaIqr, next.getLatitude(),
					next.getLongitude());
		});
		return linhaIqr;
	}
}
