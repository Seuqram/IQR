package code;

import java.time.LocalDateTime;

import br.unirio.onibus.api.model.Linha;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoIQR {
	Linha linha;
	double iqr;
	LocalDateTime dataHora;
}
