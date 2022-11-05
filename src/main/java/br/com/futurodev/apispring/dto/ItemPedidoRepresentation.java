package br.com.futurodev.apispring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoRepresentation {

    private Long id;

    private Long idProduto;

    private String descricaoProduto;

    private double valorItem;

    private double quantidade;

}
