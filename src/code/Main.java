package code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.unirio.onibus.api.download.BaixadorPosicaoVeiculos;
import br.unirio.onibus.api.model.ConjuntoLinhas;
import br.unirio.onibus.api.model.Linha;
import br.unirio.onibus.api.model.PosicaoVeiculo;
import br.unirio.onibus.api.model.Repositorio;
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
		System.out
				.println(CalculadorIQR.getInstance().getQualidadeRota(listaDeVeiculosDaLinha, listaPosicoesTrajetoria));
		LinePrinter.getInstance().publicaMapaHTML(listaDeVeiculosDaLinha, listaPosicoesTrajetoria);
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

	@SuppressWarnings("unused")
	private static LinhaIqr getLinhaComPosicoesDeIdaEVolta(List<Veiculo> listaDeVeiculosDaLinha,
			List<PosicaoMapa> listaPosicoesTrajetoria) {
		LinhaIqr line = new LinhaIqr(624);
		listaPosicoesTrajetoria.forEach(ponto -> {
			line.addPointToRoute(ponto.getLatitude(), ponto.getLongitude());
		});
		listaDeVeiculosDaLinha.forEach(veiculo -> {
			PosicaoVeiculo next = veiculo.getTrajetoria().getPosicoes().iterator().next();
			OnibusIqr onibusIqr = new OnibusIqr(veiculo.getNumeroSerie(), line, next.getLatitude(),
					next.getLongitude());
		});
		return line;
	}
}