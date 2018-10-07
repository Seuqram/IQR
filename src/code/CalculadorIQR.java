package code;

import br.unirio.onibus.api.model.Linha;

public class CalculadorIQR {
    private static CalculadorIQR calculadorQualidade = null;

    private CalculadorIQR() {
    }

    ;

    public static CalculadorIQR getInstance() {
        if (calculadorQualidade == null)
            calculadorQualidade = new CalculadorIQR();
        return calculadorQualidade;
    }

    public double executa(Linha linha) {
//        return new br.unirio.onibus.iqr.CalculadorIQR().executa(linha);
        return 0.0;
    }
}
