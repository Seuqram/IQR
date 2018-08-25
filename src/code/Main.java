package code;

import br.unirio.onibus.api.download.BaixadorPosicaoVeiculos;
import br.unirio.onibus.api.model.*;
import br.unirio.onibus.api.support.geo.PosicaoMapa;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipFile;

public class Main {

	static ArrayList<OnibusIqr> busList = new ArrayList<>();
	static String nomeArquivo = "data/iqr.txt";
	static String numeroLinha = "107";
	static String idTrajeto = "12863255";

	public static void main(String[] args) throws IOException {
		// ConjuntoLinhas linhas = new BaixadorPosicaoVeiculos().baixa();
		// ConjuntoLinhas c = new BaixadorPosicaoVeiculos().carrega("data/onibus.json");
		Repositorio repositorio = new Repositorio("data");
		Scanner file = new Scanner(new File("data/trajetos.txt"));
		Map<String, String> map = new HashMap<>();
		Map<String, Boolean> mapC = new HashMap<>();
		String linhaArquivoTrajetos = file.nextLine();
		linhaArquivoTrajetos = linhaArquivoTrajetos.substring(1, linhaArquivoTrajetos.length() - 1);
		while (file.hasNextLine()) {
			String l = "";
			String t = "";
			boolean acheiLinha = false;
			boolean acheiTrajeto = false;
			for (int i = 0; i < linhaArquivoTrajetos.length(); i++) {
				if (!acheiTrajeto) {
					char charAt = linhaArquivoTrajetos.charAt(i);
					if (!Character.isLetter(charAt) && !Character.isDigit(charAt)) {
						if (acheiLinha) {
							acheiTrajeto = true;
						} else {
							acheiLinha = true;
						}
					} else {
						if (acheiLinha) {
							t += charAt;
						} else {
							l += charAt;
						}
					}
				}
			}
			map.put(l, t);
			mapC.put(l, false);
			linhaArquivoTrajetos = file.nextLine();
		}
		List<ResultadoIQR> lista = new ArrayList<>();
		// ConjuntoLinhas linhas = new
		// BaixadorPosicaoVeiculos().carrega("data/onibus.csv");
		// for (Iterator<Linha> iterator = linhas.getLinhas().iterator();
		// iterator.hasNext();) {
		// Linha linha = iterator.next();
		// String identificadorLinha = map.get(linha.getIdentificador());
		// if (identificadorLinha != null) {
		// repositorio.carregaTrajeto(linha, identificadorLinha);
		// CalculadorIQR calculador = new CalculadorIQR();
		// ResultadoIQR resultado = new ResultadoIQR();
		// resultado.setLinha(linha);
		// resultado.setDataHora(LocalDateTime.now());
		// resultado.setIqr(calculador.executa(linha));
		// System.out.println(resultado.getIqr());
		// lista.add(resultado);
		// }
		// }

		// System.out.println(lista.size());
		// file.close();
		// List<Veiculo> listaDeVeiculosDaLinha = (List<Veiculo>) linha.getVeiculos();
		// List<PosicaoMapa> listaPosicoesTrajetoria =
		// getListaPosicoesTrajetoria(linha);
		// System.out
		List<String> arquivos = new ArrayList<>();
		populaListaDiretorios(new File("Marco/"), arquivos);
		LocalDateTime now = LocalDateTime.now();
		int size = arquivos.size();
		CalculadorIQR calculador = CalculadorIQR.getInstance();
		BaixadorPosicaoVeiculos baixador = new BaixadorPosicaoVeiculos();
		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		

		JSONArray company = new JSONArray();
		for (int index = 0; index < size; index++) {
			String arquivo = arquivos.get(index);
			System.out.println((index + 1) + "/" + size + " - inicio");
			List<String> ex = new ArrayList<>();
			try {
				ZipFile zip = new ZipFile(arquivo);
				InputStream input = zip.getInputStream(zip.entries().nextElement());
				BufferedReader br = new BufferedReader(new InputStreamReader(input));
				ConjuntoLinhas linhas = baixador.carrega(br);
				for (Iterator<Linha> iterator = linhas.getLinhas().iterator(); iterator.hasNext();) {
					Linha linha = iterator.next();
					if (linha.contaVeiculos() > 2) {
						String identificadorLinha = map.get(linha.getIdentificador());
						if (identificadorLinha != null) {
							repositorio.carregaTrajeto(linha, identificadorLinha);
							ResultadoIQR resultado = new ResultadoIQR();
							resultado.setLinha(linha);
							resultado.setDataHora(now);
							try {
								resultado.setIqr(calculador.executa(linha));
								JSONObject obj = new JSONObject();
								obj.put("linha", resultado.getLinha().getIdentificador());
								obj.put("iqr", resultado.getIqr());
								company.add(obj);
//								writer.println(linha.getIdentificador() + " | " + calculador.executa(linha));
							} catch (Exception e) {
								ex.add(linha.getIdentificador());
							}
						}
					}

				}
//				System.out.println("E: " + ex.size());
//				System.out.println(lista.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println((index + 1) + "/" + size + " - fim");
		}
		try (FileWriter filee = new FileWriter("result/result.json")) {
			filee.write(company.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
		}
		// .println(CalculadorIQR.getInstance().getQualidadeRota(listaDeVeiculosDaLinha,
		// listaPosicoesTrajetoria));
		// File folder = new File("data/");
		//
		// for (int i = 0; i < folder.listFiles().length; i++) {
		// File string = folder.listFiles()[i];
		// System.out.println(string.getName());
		// }
	}

	private static void populaListaDiretorios(File file, List<String> arquivos) {
		if (!file.isFile()) {
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File subFile = listFiles[i];
				if (subFile.isFile()) {
					arquivos.add(subFile.getPath());
				} else {
					populaListaDiretorios(subFile, arquivos);
				}
			}
		}
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