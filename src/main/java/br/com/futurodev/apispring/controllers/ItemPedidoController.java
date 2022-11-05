package br.com.futurodev.apispring.controllers;

import br.com.futurodev.apispring.dto.ItemPedidoRepresentation;
import br.com.futurodev.apispring.input.ItemPedidoInput;
import br.com.futurodev.apispring.model.ItemPedido;
import br.com.futurodev.apispring.service.CadastroItemPedidoService;
import br.com.futurodev.apispring.service.CadastroPedidoService;
import br.com.futurodev.apispring.service.CadastroProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Itens do Pedido")
@RestController
@RequestMapping(value = "/itemPedidos")
public class ItemPedidoController {

    @Autowired
    private CadastroItemPedidoService cadastroItemPedidoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @ApiOperation("Salva itensPedido")
    @PostMapping(value = "/", produces ="application/json")
    public ResponseEntity<ItemPedidoRepresentation> cadastrar(@RequestBody ItemPedidoInput itemPedidoInput){
        ItemPedido item = toDomainObject(itemPedidoInput);
        cadastroItemPedidoService.salvar(item);
        return new ResponseEntity<ItemPedidoRepresentation>(toModel(item), HttpStatus.CREATED);
    }

    @ApiOperation("Lista itensPedido")
    @GetMapping(value = "/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<ItemPedidoRepresentation>> getItensPedido(){
        List<ItemPedido> itens = cadastroItemPedidoService.getItensPedido();
        List<ItemPedidoRepresentation> itemPedidoRepresentation = toCollectionModel(itens);
        return new ResponseEntity<List<ItemPedidoRepresentation>>(itemPedidoRepresentation,HttpStatus.OK);
    }

    private List<ItemPedidoRepresentation> toCollectionModel(List<ItemPedido> itemPedidoModel){
        return itemPedidoModel.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private ItemPedido toDomainObject(ItemPedidoInput itemPedidoInput){

        ItemPedido itemPedidoModel = new ItemPedido();
        itemPedidoModel.setId(itemPedidoInput.getId());
        itemPedidoModel.setQuantidade(itemPedidoInput.getQuantidade());
        itemPedidoModel.setValorItem(itemPedidoInput.getPrecoVenda());
        itemPedidoModel.setPedido(cadastroPedidoService.getPedidoById(itemPedidoInput.getIdPedido()));
        itemPedidoModel.setProduto(cadastroProdutoService.getProdutoById(itemPedidoInput.getIdProduto()));

        return itemPedidoModel;
    }

    private ItemPedidoRepresentation toModel(ItemPedido itemPedidoModel){
        ItemPedidoRepresentation itemPedidoRepresentationModel = new ItemPedidoRepresentation();
        itemPedidoRepresentationModel.setId(itemPedidoModel.getId());
        itemPedidoRepresentationModel.setQuantidade(itemPedidoModel.getQuantidade());
        itemPedidoRepresentationModel.setValorItem(itemPedidoModel.getValorItem());
        itemPedidoRepresentationModel.setIdProduto(itemPedidoModel.getProduto().getId());

        return itemPedidoRepresentationModel;
    }
}
