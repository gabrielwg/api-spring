package br.com.futurodev.apispring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoRepresentation {

    private Long id;

    private Long idCliente;

    private String nomeCliente;

    private Long idFormaPagamento;

    private String formaPagamentoDescricao;


    private List<ItemPedidoRepresentation> itensPedidoRepresentationModel = new ArrayList<ItemPedidoRepresentation>();

}
