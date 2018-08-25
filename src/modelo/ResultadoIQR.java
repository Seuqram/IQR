package modelo;

import br.unirio.onibus.api.model.Linha;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResultadoIQR {
	Linha linha;
	double iqr;
	LocalDateTime dataHora;
}
