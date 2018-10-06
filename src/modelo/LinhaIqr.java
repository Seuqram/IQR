package modelo;

import br.unirio.onibus.api.model.Linha;
import br.unirio.onibus.api.support.geo.PosicaoMapa;
import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Representa uma linha de onibus utilizada para cálculo de IQR
 *
 * @author rodrigo
 */
@Data
public class LinhaIqr {

    Linha linha;

    List<Ponto> pontosComBairro;

    private static final String COMMA_DELIMITER = ",";

    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "coordenada, nome";

    public void addPonto(Ponto ponto) {
        if (this.pontosComBairro == null) {
            this.pontosComBairro = new ArrayList<>();
        }
        this.pontosComBairro.add(ponto);
    }

    public void addPonto(PosicaoMapa posicaoMapa, Bairro bairro) {
        if (this.pontosComBairro == null) {
            this.pontosComBairro = new ArrayList<>();
        }
        Ponto pontoNovo = new Ponto(posicaoMapa.getLatitude(), posicaoMapa.getLongitude());
        boolean pontoExiste = false;
        for (Ponto pontoExistente : this.pontosComBairro) {
            if (pontoExistente.getX() == pontoNovo.getX()) {
                if (pontoExistente.getY() == pontoNovo.getY()) {
                    pontoExistente.addBairro(bairro);
                    pontoExiste = true;
                }
            }
        }
        if (!pontoExiste) {
            pontoNovo.addBairro(bairro);
            this.addPonto(pontoNovo);
        }
    }

    public void writeCsv() throws IOException {
        FileWriter fileWriter = new FileWriter("coord.csv");
        fileWriter.append(FILE_HEADER);
        fileWriter.append(NEW_LINE_SEPARATOR);
        for (Ponto ponto : this.pontosComBairro) {
            fileWriter.append(String.valueOf(ponto.getX()));
            fileWriter.append(" ");
            fileWriter.append(String.valueOf(ponto.getY()));
            fileWriter.append(COMMA_DELIMITER);
            for (Bairro bairro : ponto.getListaBairros()) {
                fileWriter.append(bairro.getNome());
                fileWriter.append(" | ");
            }
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public void addPontoSemBairro(PosicaoMapa posicao) {
        Bairro bairro = new Bairro();
        bairro.setNome("Sem Bairro");
        addPonto(posicao, bairro);
    }

    public FileWriter addToCsv(FileWriter fileWriter) throws IOException {
        if (fileWriter == null) {
            fileWriter = new FileWriter("coord.csv");
            fileWriter.append("Linha, Bairro");
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
        Set<String> bairros = new HashSet<>();
        if (this.pontosComBairro != null) {
            this.pontosComBairro.forEach(pontosComBairro -> {
                if (pontosComBairro.getListaBairros() != null) {
                    pontosComBairro.getListaBairros().forEach(bairro -> {
                        if (bairro.getNome() != null) {
                            bairros.add(bairro.getNome());
                        }
                    });
                }
            });
        }
        for (String bairro : bairros) {
            fileWriter.append(this.getLinha().getIdentificador());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(bairro);
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
        return fileWriter;
    }
}