package modelo;

import br.unirio.onibus.api.model.Linha;
import br.unirio.onibus.api.support.geo.PosicaoMapa;
import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        Ponto ponto = new Ponto(posicaoMapa.getLatitude(), posicaoMapa.getLongitude());
        ponto.addBairro(bairro);
        this.addPonto(ponto);
    }

    public void writeCsv() throws IOException {
        FileWriter fileWriter = new FileWriter("coord.csv");
        fileWriter.append(FILE_HEADER);
        fileWriter.append(NEW_LINE_SEPARATOR);
        for (Ponto ponto : this.pontosComBairro) {
            fileWriter.append(String.valueOf(ponto.getLatitude()));
            fileWriter.append(" ");
            fileWriter.append(String.valueOf(ponto.getLongitude()));
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
}