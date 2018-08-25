package code;

import br.unirio.onibus.api.download.BaixadorPosicaoVeiculos;
import br.unirio.onibus.api.model.*;
import br.unirio.onibus.api.support.geo.PosicaoMapa;
import modelo.LinhaIqr;
import modelo.OnibusIqr;
import modelo.ResultadoIQR;
import org.javatuples.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipFile;

public class Main {

    public static void main(String[] args) {
        try {
            calculaIqrSalvaJson();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void calculaIqrSalvaJson() throws IOException {
        Repositorio repositorio = new Repositorio("data");
        Scanner arquivoTrajetos = new Scanner(new File("data/trajetos.txt"));

        Map<String, String> linhaCodigoTrajetoMap = getLinhaTrajetoMap(arquivoTrajetos);

        List<String> arquivosComPosicoesList = new ArrayList<>();
        populaListaDiretorios(new File("Marco/"), arquivosComPosicoesList);

        LocalDateTime agora = LocalDateTime.now();
        int quantidadeArquivosComPosicoes = arquivosComPosicoesList.size();
        CalculadorIQR calculador = CalculadorIQR.getInstance();
        BaixadorPosicaoVeiculos baixador = new BaixadorPosicaoVeiculos();

        JSONArray company = new JSONArray();
        for (int index = 0; index < quantidadeArquivosComPosicoes; index++) {
            String nomeArquivoComPosicoes = arquivosComPosicoesList.get(index);
            System.out.println((index + 1) + "/" + quantidadeArquivosComPosicoes + " - inicio");
            List<String> listaArquivosComExceptions = new ArrayList<>();
            try {
                ZipFile arquivoZipado = new ZipFile(nomeArquivoComPosicoes);
                InputStream input = arquivoZipado.getInputStream(arquivoZipado.entries().nextElement());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                ConjuntoLinhas conjuntoLinhas = baixador.carrega(bufferedReader);
                for (Linha linha : conjuntoLinhas.getLinhas()) {
                    if (linha.contaVeiculos() > 2) {
                        String identificadorLinha = linhaCodigoTrajetoMap.get(linha.getIdentificador());
                        if (identificadorLinha != null) {
                            repositorio.carregaTrajeto(linha, identificadorLinha);
                            ResultadoIQR resultado = new ResultadoIQR();
                            resultado.setLinha(linha);
                            resultado.setDataHora(agora);
                            try {
                                resultado.setIqr(calculador.executa(linha));
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("linha", resultado.getLinha().getIdentificador());
                                jsonObject.put("iqr", resultado.getIqr());
                                company.add(jsonObject);
                            } catch (Exception e) {
                                listaArquivosComExceptions.add(linha.getIdentificador());
                            }
                        }
                    }

                }
            } catch (IOException e) {
                System.out.println("Exceção na extração do arquivo " + nomeArquivoComPosicoes);
            }
            System.out.println((index + 1) + "/" + quantidadeArquivosComPosicoes + " - fim");
        }
        FileWriter fileWriter = new FileWriter("result/result.json");
        fileWriter.write(company.toJSONString());
        System.out.println("Successfully Copied JSON Object to File...");
    }

    private static Pair<String, String> getLinhaECodigoTrajeto(String linhaArquivoTrajetos) {
        Pair<String, String> pair = null;
        StringBuilder linha = new StringBuilder();
        StringBuilder trajeto = new StringBuilder();
        boolean acheiLinha = false;
        boolean acheiTrajeto = false;
        for (int indice = 0; indice < linhaArquivoTrajetos.length(); indice++) {
            if (!acheiTrajeto) {
                char charAt = linhaArquivoTrajetos.charAt(indice);
                if (!Character.isLetter(charAt) && !Character.isDigit(charAt)) {
                    if (acheiLinha) {
                        acheiTrajeto = true;
                    } else {
                        acheiLinha = true;
                    }
                } else {
                    if (acheiLinha) {
                        trajeto.append(charAt);
                    } else {
                        linha.append(charAt);
                    }
                }
            }
        }
        if (acheiLinha && acheiTrajeto) {
            pair = new Pair<String, String>(linha.toString(), trajeto.toString());
        }

        return pair;
    }

    private static Map<String, String> getLinhaTrajetoMap(Scanner arquivoTrajetos) {
        Map<String, String> linhaCodigoTrajetoMap = new HashMap<>();
        String linhaArquivoTrajetos = arquivoTrajetos.nextLine();
        linhaArquivoTrajetos = linhaArquivoTrajetos.substring(1, linhaArquivoTrajetos.length() - 1);
        while (arquivoTrajetos.hasNextLine()) {
            Pair<String, String> linhaECodigoTrajeto = getLinhaECodigoTrajeto(linhaArquivoTrajetos);
            if (linhaECodigoTrajeto != null) {
                String linha = linhaECodigoTrajeto.getValue0();
                String trajeto = linhaECodigoTrajeto.getValue1();
                linhaCodigoTrajetoMap.put(linha, trajeto);
            }
            linhaArquivoTrajetos = arquivoTrajetos.nextLine();
        }
        return linhaCodigoTrajetoMap;
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