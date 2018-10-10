package code;

import br.unirio.onibus.api.download.BaixadorPosicaoVeiculos;
import br.unirio.onibus.api.model.ConjuntoLinhas;
import br.unirio.onibus.api.model.Linha;
import br.unirio.onibus.api.model.Repositorio;
import br.unirio.onibus.api.support.geo.PosicaoMapa;
import com.esri.core.geometry.Point;
import modelo.Bairro;
import modelo.LinhaIqr;
import modelo.ResultadoIQR;
import org.javatuples.Pair;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

public class Main {

    public static void main(String[] args) {

        try {

            Repositorio repositorio = new Repositorio("data");
            Map<String, Linha> identificadorLinhaMap = getLinhaMap(repositorio);

            List<LinhaIqr> linhasComBairros = pegaLinhasComBairros(identificadorLinhaMap);
            List<LinhaIqr> linhasComIqrs = getLinhasComIqr(identificadorLinhaMap);

            List<LinhaIqr> linhaIqrList = new ArrayList<>(linhasComIqrs);

            linhaIqrList.forEach(linhaIqr -> {
                linhasComBairros.stream().filter(linhaBairro -> linhaBairro.getIdentificador().equals(linhaIqr.getIdentificador())).collect(Collectors.toList())
                        .forEach(linhaBairro -> linhaIqr.setBairros(linhaBairro.getBairros()));
            });
            for (LinhaIqr linhaIqr : linhaIqrList) {
                linhaIqr.writeIqrInCsv();
                linhaIqr.writeBairrosInCsv();
            }

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    private static Map<String, Linha> getLinhaMap(Repositorio repositorio) throws FileNotFoundException {
        Map<String, Linha> map = new HashMap<>();
        Scanner arquivoTrajetos = new Scanner(new File("data/trajetos.txt"));
        Map<String, String> linhaCodigoTrajetoMap = getLinhaTrajetoMap(arquivoTrajetos);
        for (Map.Entry<String, String> entry : linhaCodigoTrajetoMap.entrySet()) {
            String identificadorLinha = entry.getKey();
            String codigoTrajeto = entry.getValue();
            Linha linha = new Linha(identificadorLinha);
            repositorio.carregaTrajeto(linha, codigoTrajeto);
            map.put(identificadorLinha, linha);
        }
        return map;
    }

    private static List<LinhaIqr> pegaLinhasComBairros(Map<String, Linha> identificadorLinhaMap) throws IOException, SAXException, ParserConfigurationException {
        List<Bairro> bairros = getListaBairros();
        List<LinhaIqr> linhasList = new ArrayList<>();
        int numeroLinhas = identificadorLinhaMap.size();
        int contadorLinhas = 0;
        for (Map.Entry<String, Linha> entry : identificadorLinhaMap.entrySet()) {
            String identificadorLinha = entry.getKey();
            Linha linha = entry.getValue();

            System.out.println("Linha " + ++contadorLinhas + "/" + numeroLinhas);

            LinhaIqr linhaIqr = new LinhaIqr();
            linhaIqr.setIdentificador(identificadorLinha);
            for (PosicaoMapa posicaoMapa : linha.getTrajetoIda().pegaPosicoes()) {
                VerificadorPoligono verificadorPoligono = VerificadorPoligono.getInstance();
                bairros.stream()
                        .filter(bairro -> verificadorPoligono.isInside(bairro.getPolygon(), posicaoMapa)).collect(Collectors.toList())
                        .forEach(linhaIqr::addBairro);
            }
            for (PosicaoMapa posicaoMapa : linha.getTrajetoVolta().pegaPosicoes()) {
                VerificadorPoligono verificadorPoligono = VerificadorPoligono.getInstance();
                bairros.stream()
                        .filter(bairro -> verificadorPoligono.isInside(bairro.getPolygon(), posicaoMapa)).collect(Collectors.toList())
                        .forEach(linhaIqr::addBairro);
            }
            linhasList.add(linhaIqr);
        }
        return linhasList;
    }

    private static List<LinhaIqr> getLinhasComIqr(Map<String, Linha> identificadorLinhaMap) {
        Map<String, List<ResultadoIQR>> linhaIqrMap = new HashMap<>();

        List<String> arquivosComPosicoesList = new ArrayList<>();
        populaListaDiretorios(new File("Marco/"), arquivosComPosicoesList);

        int quantidadeArquivosComPosicoes = arquivosComPosicoesList.size();
        MeuCalculadorIQR calculador = MeuCalculadorIQR.getInstance();
        BaixadorPosicaoVeiculos baixador = new BaixadorPosicaoVeiculos();

        for (int index = 0; index < quantidadeArquivosComPosicoes; index++) {
            String nomeArquivoComPosicoes = arquivosComPosicoesList.get(index);
            System.out.println((index + 1) + "/" + quantidadeArquivosComPosicoes + " - inicio");
            LocalDateTime dataHora = null;
            try {
                LocalDate day = LocalDate.parse(nomeArquivoComPosicoes.substring(6, nomeArquivoComPosicoes.length() - 4).substring(0, 10));
                int hora = Integer.valueOf(nomeArquivoComPosicoes.substring(17, 19));
                int minuto = Integer.valueOf(nomeArquivoComPosicoes.substring(20, 22));
                int segundo = Integer.valueOf(nomeArquivoComPosicoes.substring(23, 25));
                dataHora = day.atTime(hora, minuto, segundo);
            } catch (Exception e){
                System.out.println();
            }
            try {
                ZipFile arquivoZipado = new ZipFile(nomeArquivoComPosicoes);
                InputStream input = arquivoZipado.getInputStream(arquivoZipado.entries().nextElement());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                ConjuntoLinhas conjuntoLinhas = baixador.carrega(bufferedReader);
                for (Linha linha : conjuntoLinhas.getLinhas()) {
                    if (identificadorLinhaMap.containsKey(linha.getIdentificador())) {
                        if (linha.contaVeiculos() > 2) {
                            for (PosicaoMapa posicaoMapa : identificadorLinhaMap.get(linha.getIdentificador()).getTrajetoIda().pegaPosicoes()) {
                                linha.getTrajetoIda().adiciona(posicaoMapa);
                            }
                            for (PosicaoMapa posicaoMapa : identificadorLinhaMap.get(linha.getIdentificador()).getTrajetoVolta().pegaPosicoes()) {
                                linha.getTrajetoVolta().adiciona(posicaoMapa);
                            }
                            ResultadoIQR resultado = new ResultadoIQR();
                            resultado.setDataHora(dataHora);
                            resultado.setValor(calculador.executa(linha));
                            linhaIqrMap.putIfAbsent(linha.getIdentificador(), new ArrayList<>());
                            linhaIqrMap.get(linha.getIdentificador()).add(resultado);

                        }
                    }

                }
            } catch (IOException e) {
                System.out.println("Exceção na extração do arquivo " + nomeArquivoComPosicoes);
            }
        }
        List<LinhaIqr> linhaIqrList = new ArrayList<>();
        for (Map.Entry<String, List<ResultadoIQR>> entry : linhaIqrMap.entrySet()) {
            String identificadorLinha = entry.getKey();
            List<ResultadoIQR> iqrs = entry.getValue();
            LinhaIqr linhaIqr = new LinhaIqr();
            linhaIqr.setIdentificador(identificadorLinha);
            linhaIqr.setIqrs(iqrs);
            linhaIqrList.add(linhaIqr);
        }
        return linhaIqrList;
    }

    private static List<Bairro> getListaBairros() throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File("data/bairros.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("Placemark");
        List<Pair<String, List<Point>>> pairList = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); i++) {
            Node item = nList.item(i);
            Element eElement = (Element) item;
            String nomeBairroXml = ((Element) eElement.getChildNodes().item(3).getChildNodes().item(0)).getElementsByTagName("SimpleData").item(2).getTextContent();
            String coordenadas = eElement.getChildNodes().item(5).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getTextContent();
            Pair<String, List<Point>> pair = new Pair<>(getNomeBairroFromXml(nomeBairroXml), getPontoList(coordenadas));
            pairList.add(pair);
        }
        List<Bairro> bairros = new ArrayList<>();
        for (Pair<String, List<Point>> pair : pairList) {
            Bairro bairro = new Bairro();
            bairro.setNome(pair.getValue0());
            bairro.setDivisas(pair.getValue1());
            bairros.add(bairro);
        }
        return bairros;
    }

    private static List<Point> getPontoList(String coordenadas) {
        List<Point> pontos = new ArrayList<>();
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

    private static Point getPonto(String[] coordenada) {
        double latitude = Double.valueOf(coordenada[0]);
        double longitude = Double.valueOf(coordenada[1]);
        return new Point(latitude, longitude);
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
            pair = new Pair<>(linha.toString(), trajeto.toString());
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
            for (File subFile : listFiles) {
                if (subFile.isFile()) {
                    arquivos.add(subFile.getPath());
                } else {
                    populaListaDiretorios(subFile, arquivos);
                }
            }
        }
    }
}