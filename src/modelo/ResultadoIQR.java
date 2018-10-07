package modelo;

import br.unirio.onibus.api.model.Linha;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResultadoIQR {

    private Linha linha;

    private double valor;

    private LocalDateTime dataHora;
}
