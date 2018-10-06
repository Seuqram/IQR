package modelo;

import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class LinhaIqr {

    private String identificador;

    private List<ResultadoIQR> iqrs;

    private List<Bairro> bairros;

    private static final String COMMA_DELIMITER = ",";

    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "coordenada, nome";

    public void writeAsCsv() throws IOException {
        System.out.println(this.identificador);
        String filePath = "bairros/" + this.identificador + "2.csv";
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.append("Linha, Bairro");

        fileWriter.append("Linha, Bairro");
        fileWriter.append(NEW_LINE_SEPARATOR);

        for (Bairro bairro : bairros) {
            fileWriter.append(this.getIdentificador());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(bairro.getNome());
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public void addBairro(Bairro bairro) {
        if (this.bairros == null) {
            this.bairros = new ArrayList<>();
        }
        if (!this.bairros.contains(bairro)) {
            this.bairros.add(bairro);
        }
    }
}