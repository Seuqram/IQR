package code;

import br.unirio.onibus.api.download.BaixadorPosicaoVeiculos;
import br.unirio.onibus.api.model.ConjuntoLinhas;
import br.unirio.onibus.api.model.Linha;
import br.unirio.onibus.api.model.Repositorio;
import br.unirio.onibus.api.support.geo.PosicaoMapa;
import modelo.Bairro;
import modelo.LinhaIqr;
import modelo.Ponto;
import modelo.ResultadoIQR;
import org.javatuples.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.VerificadorPoligono;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipFile;

public class Main {

    public static void main(String[] args) {
//        try {
//            calculaIqrSalvaJson();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            List<Bairro> bairros = getListaBairros();
            Repositorio repositorio = new Repositorio("data");
            Scanner arquivoTrajetos = null;
            arquivoTrajetos = new Scanner(new File("data/trajetos.txt"));
            Map<String, String> linhaCodigoTrajetoMap = getLinhaTrajetoMap(arquivoTrajetos);
            for (Map.Entry<String, String> entry : linhaCodigoTrajetoMap.entrySet()) {
                Linha linha = new Linha(entry.getKey());
                LinhaIqr linhaIqr = new LinhaIqr();
                linhaIqr.setLinha(linha);
                repositorio.carregaTrajeto(linha, entry.getValue());
                for (PosicaoMapa posicao : linha.getTrajetoIda().pegaPosicoes()) {
                    VerificadorPoligono verificadorPoligono = VerificadorPoligono.getInstance();
                    for (Bairro bairro : bairros) {
                        if (verificadorPoligono.isInside(bairro.getDivisas(), posicao)) {
                            linhaIqr.addPonto(posicao, bairro);
                        }
                    }
                }
                linhaIqr.writeCsv();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    private static List<Bairro> getListaBairros() throws ParserConfigurationException, IOException, SAXException {
        File fxmlFile = new File("data/bairros.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fxmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("Placemark");
        List<Pair<String, List<Ponto>>> pairList = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); i++) {
            Node item = nList.item(i);
            Element eElement = (Element) item;
            String nomeBairroXml = ((Element) eElement.getChildNodes().item(3).getChildNodes().item(0)).getElementsByTagName("SimpleData").item(2).getTextContent();
            String coordenadas = eElement.getChildNodes().item(5).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getTextContent();
            Pair<String, List<Ponto>> pair = new Pair<String, List<Ponto>>(getNomeBairroFromXml(nomeBairroXml), getPontoList(coordenadas));
            pairList.add(pair);
        }
        List<Bairro> bairros = new ArrayList<>();
        for (Pair<String, List<Ponto>> pair : pairList) {
            Bairro bairro = new Bairro();
            bairro.setNome(pair.getValue0());
            bairro.setDivisas(pair.getValue1());
            bairros.add(bairro);
        }
        return bairros;
    }

    private static List<Ponto> getPontoList(String coordenadas) {
        List<Ponto> pontos = new ArrayList<>();
        String[] pontoSeparado = new String[2];
        int i = 0;
        for (String coordenada : coordenadas.split(",")) {
            String[] vetorCoordenadas = coordenada.split(" ");
            if (vetorCoordenadas.length > 1) {
                pontos.add(getPonto(vetorCoordenadas));
            } else {
                pontoSeparado[i] = vetorCoordenadas[0];
                i++;
            }
        }
        pontos.add(getPonto(pontoSeparado));
        return pontos;
    }

    private static Ponto getPonto(String[] coordenada) {
        double latitude = Double.valueOf(coordenada[0]);
        double longitude = Double.valueOf(coordenada[1]);
        return new Ponto(latitude, longitude);
    }

    private static String getNomeBairroFromXml(String nomeBairroXml) {
        StringBuilder nomeBairro = new StringBuilder();
        String[] nomeBairroSplit = nomeBairroXml.split(" ");
        if (nomeBairroSplit.length > 1) {
            for (String itemNomeBairro : nomeBairroSplit) {
                nomeBairro.append(itemNomeBairro);
                nomeBairro.append(" ");
            }
            nomeBairro.deleteCharAt(nomeBairro.length() - 1);
        } else {
            nomeBairro.append(nomeBairroSplit[0]);
        }
        return nomeBairro.toString();
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

//    @SuppressWarnings("unused")
//    private static LinhaIqr getLinhaComPosicoesDeIdaEVolta(List<Veiculo> listaDeVeiculosDaLinha,
//                                                           List<PosicaoMapa> listaPosicoesTrajetoria) {
//        LinhaIqr line = new LinhaIqr(624);
//        listaPosicoesTrajetoria.forEach(ponto -> {
//            line.addPontoToRoute(ponto.getLatitude(), ponto.getLongitude());
//        });
//        listaDeVeiculosDaLinha.forEach(veiculo -> {
//            PosicaoVeiculo next = veiculo.getTrajetoria().getPosicoes().iterator().next();
//            OnibusIqr onibusIqr = new OnibusIqr(veiculo.getNumeroSerie(), line, next.getLatitude(),
//                    next.getLongitude());
//        });
//        return line;
//    }
}