package br.com.futurodev.apispring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRepresentation {
    private Long id;

    private String decricao;

    private String descricaoReduzida;

    private double precoCompra;

    private double precoVenda;


}
