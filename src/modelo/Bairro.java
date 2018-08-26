package modelo;

import lombok.Data;

import java.util.List;

@Data
public class Bairro {

    private String nome;

    private List<Ponto> divisas;
}
