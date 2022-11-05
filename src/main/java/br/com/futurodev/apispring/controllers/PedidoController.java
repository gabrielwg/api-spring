package br.com.futurodev.apispring.controllers;

import br.com.futurodev.apispring.dto.ItemPedidoRepresentation;
import br.com.futurodev.apispring.dto.PedidoRepresentation;
import br.com.futurodev.apispring.input.PedidoInput;
import br.com.futurodev.apispring.model.ItemPedido;
import br.com.futurodev.apispring.model.Pedido;
import br.com.futurodev.apispring.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Pedidos")
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Autowired
    private CadastroClienteService cadastroClienteService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroItemPedidoService cadastroItemPedidoService;


    @PostMapping
    public ResponseEntity<PedidoRepresentation> cadastrar(@RequestBody PedidoInput pedidoInput){
       Pedido pedido =  cadastroPedidoService.salvar(toDomainObject(pedidoInput));
       return new ResponseEntity<PedidoRepresentation>(toRepresentatioModel(pedido), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PedidoRepresentation> atualizar(@RequestBody PedidoInput pedidoInput){
        Pedido pedido = cadastroPedidoService.salvar(toDomainObject(pedidoInput));
        return  new ResponseEntity<PedidoRepresentation>(toRepresentatioModel(pedido),HttpStatus.OK);
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long idPedido){
        cadastroPedidoService.deletePedidoById(idPedido);
        return new ResponseEntity<String>("Pedido de ID: "+idPedido+" deletado.", HttpStatus.OK);
    }

    @GetMapping(value = "/{idPedido}")
    public ResponseEntity<PedidoRepresentation> getPedidoById(@PathVariable(value = "idPedido") Long idPedido){

        PedidoRepresentation pedidoRepresentation =
                toRepresentatioModel(cadastroPedidoService.getPedidoById(idPedido));

        return new ResponseEntity<PedidoRepresentation>(pedidoRepresentation, HttpStatus.OK);

    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<PedidoRepresentation>> getPedidos(){
        List<Pedido> pedidos = cadastroPedidoService.getPedidos();

        return
                new ResponseEntity<List<PedidoRepresentation>>(toCollectionRepresentationModel(pedidos), HttpStatus.OK);
    }

    @GetMapping(value = "/cliente/{idCliente}")
    public ResponseEntity<List<PedidoRepresentation>> getPedidoByIdCliente(
            @PathVariable(name = "idCliente") Long idCliente) {

        List<Pedido> pedidos = cadastroPedidoService.getPedidoByIdCliente(idCliente);
        List<PedidoRepresentation> pedidosRM = toCollectionRepresentationModel(pedidos);

        return new ResponseEntity<List<PedidoRepresentation>>(pedidosRM, HttpStatus.OK);
    }

    // TODO Falta ajuste neste método

    @DeleteMapping(value = "/{idPedido}/item/{idItemPedido}")
    @ResponseBody
    public ResponseEntity<String> deleteItemPedidoById(@PathVariable(name = "idPedido") Long idPedido,
            @PathVariable(name = "idItemPedido") Long idItemPedido){

      //ItemPedido itemPedido = cadastroItemPedidoService.getItemPedidoById(idItemPedido);
        ItemPedido itemPedido = cadastroItemPedidoService.getItemPedido(idPedido, idItemPedido);
      //cadastroItemPedidoService.deleteItemPedido(itemPedido);
        cadastroItemPedidoService.deleteItemPedidoById(itemPedido.getId());

      return new ResponseEntity<String>("Item de ID: "+idItemPedido+" deletado.",HttpStatus.OK);
    }



    //converte de Model para DTO de Reposta
    private PedidoRepresentation toRepresentatioModel(Pedido pedido){
        PedidoRepresentation pedidoRepresentation = new PedidoRepresentation();
        pedidoRepresentation.setId(pedido.getId());
        pedidoRepresentation.setIdCliente(pedido.getCliente().getId());
        pedidoRepresentation.setNomeCliente(pedido.getCliente().getNome());
        pedidoRepresentation.setIdFormaPagamento(pedido.getFormaPagamento().getId());
        pedidoRepresentation.setFormaPagamentoDescricao(pedido.getFormaPagamento().getDescricao());

        for (int i = 0; i < pedido.getItensPedido().size(); i++) {
            ItemPedidoRepresentation itemPedidoRepresentation = new ItemPedidoRepresentation();
            itemPedidoRepresentation.setId(pedido.getItensPedido().get(i).getId());
            itemPedidoRepresentation.setIdProduto(pedido.getItensPedido().get(i).getProduto().getId());
            itemPedidoRepresentation.setDescricaoProduto(pedido.getItensPedido().get(i).getProduto().getDescricao());
            itemPedidoRepresentation.setQuantidade(pedido.getItensPedido().get(i).getQuantidade());
            itemPedidoRepresentation.setValorItem(pedido.getItensPedido().get(i).getValorItem());

            pedidoRepresentation.getItensPedidoRepresentationModel().add(itemPedidoRepresentation);

        }



        return pedidoRepresentation;
    }


    //Converte de DTO de entrada para Model
    private Pedido toDomainObject(PedidoInput pedidoInput){

        Pedido pedido = new Pedido();

        pedido.setId(pedidoInput.getIdPedido());

        pedido.setCliente(cadastroClienteService.getClienteById(pedidoInput.getIdCliente()));
        pedido.setFormaPagamento(cadastroFormaPagamentoService.getFormaPagamentoById(pedidoInput.getIdFormaPagamento()));

        for (int i = 0; i < pedidoInput.getItensPedido().size(); i++) {

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setId(pedidoInput.getItensPedido().get(i).getId());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(cadastroProdutoService.getProdutoById(pedidoInput.getItensPedido().get(i).getIdProduto()));

            // se o ID do itemPedido for null busca o preço do cadastro de produto
            if(pedidoInput.getItensPedido().get(i).getId() == null) {
                itemPedido.setValorItem(cadastroProdutoService
                        .getProdutoById(pedidoInput
                                .getItensPedido().get(i)
                                .getIdProduto()).getPrecoVenda());
            // se não pega o preco do Json de entrada
            }else{
                itemPedido.setValorItem(pedidoInput.getItensPedido().get(i).getPrecoVenda());
            }

            itemPedido.setQuantidade(pedidoInput.getItensPedido().get(i).getQuantidade());


            // adiciono a lista de itensPedido do model
            pedido.getItensPedido().add(itemPedido);

        }


        return pedido;
    }

    private List<PedidoRepresentation> toCollectionRepresentationModel(List<Pedido> pedidos){
        return pedidos.stream()
                .map(Pedido -> toRepresentatioModel(Pedido))
                .collect(Collectors.toList());
    }




}
