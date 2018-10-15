package modelo;

import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
public class LinhaIqr {

    private String identificador;

    private List<ResultadoIQR> iqrs;

    private List<Bairro> bairros;

    private static final String COMMA_DELIMITER = ",";

    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "coordenada, nome";

    public void writeBairrosInCsv() throws IOException {
        System.out.println(this.identificador);
        String filePath = "bairros/bairro_" + this.identificador + ".csv";
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.append("Linha, Bairro");
        fileWriter.append(NEW_LINE_SEPARATOR);

        if (bairros != null) {
            for (Bairro bairro : bairros) {
                fileWriter.append(this.getIdentificador());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(bairro.getNome());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public void writeBairrosInCsv(String filePath) throws IOException {
        System.out.println(this.identificador);
        FileWriter fileWriter = new FileWriter(filePath, true);

        if (bairros != null) {
            for (Bairro bairro : bairros) {
                fileWriter.append(this.getIdentificador());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(bairro.getNome());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public void writeIqrInCsv() throws IOException {
        String pattern = "### ###.###";
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat(pattern, otherSymbols);
        System.out.println(this.identificador);
        String filePath = "bairros/iqr_" + this.identificador + ".csv";
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.append("Linha, DataHora, Resultado");
        fileWriter.append(NEW_LINE_SEPARATOR);

        for (ResultadoIQR iqr : iqrs) {
            fileWriter.append(this.getIdentificador());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(iqr.getDataHora().toString());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(df.format(iqr.getValor() * 100));
            fileWriter.append(NEW_LINE_SEPARATOR);
        }

        fileWriter.flush();
        fileWriter.close();
    }

    public void writeIqrInCsv(String filePath) throws IOException {
        String pattern = "### ###.###";
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat(pattern, otherSymbols);
        System.out.println(this.identificador);
        FileWriter fileWriter = new FileWriter(filePath, true);

        for (ResultadoIQR iqr : iqrs) {
            fileWriter.append(this.getIdentificador());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(iqr.getDataHora().toString());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(df.format(iqr.getValor() * 100d));
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

    public void addResultado(ResultadoIQR resultado) {
        if (this.iqrs == null) {
            this.iqrs = new ArrayList<>();
        }
        this.iqrs.add(resultado);
    }
}