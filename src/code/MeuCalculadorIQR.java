package code;

import br.unirio.onibus.api.model.Linha;
import br.unirio.onibus.iqr.CalculadorIQR;

public class MeuCalculadorIQR {
    private static MeuCalculadorIQR calculadorQualidade = null;

    private MeuCalculadorIQR() {
    }

    ;

    public static MeuCalculadorIQR getInstance() {
        if (calculadorQualidade == null)
            calculadorQualidade = new MeuCalculadorIQR();
        return calculadorQualidade;
    }

    public double executa(Linha linha) {
//        return new br.unirio.onibus.iqr.MeuCalculadorIQR().executa(linha);
        CalculadorIQR calculadorIQR = new CalculadorIQR();
        return calculadorIQR.executa(linha);
    }

}
