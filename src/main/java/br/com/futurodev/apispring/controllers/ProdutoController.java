package br.com.futurodev.apispring.controllers;


import br.com.futurodev.apispring.dto.ProdutoRepresentation;
import br.com.futurodev.apispring.input.ProdutoInput;
import br.com.futurodev.apispring.model.Produto;
import br.com.futurodev.apispring.service.CadastroProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Produtos")
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @ApiOperation("Salvar um produto")
    @PostMapping
    public ResponseEntity<ProdutoRepresentation> cadastrar(@RequestBody ProdutoInput produtoInput) {
        Produto produto = toDomainObject(produtoInput);
        cadastroProdutoService.salvar(produto);
        return new ResponseEntity<ProdutoRepresentation>(toModel(produto), HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza um produto")
    @PutMapping
    public ResponseEntity<ProdutoRepresentation> atualizar(@RequestBody ProdutoInput produtoInput) {
        Produto produto = cadastroProdutoService.salvar(toDomainObject(produtoInput));
        return new ResponseEntity<ProdutoRepresentation>(toModel(produto), HttpStatus.OK);
    }


    @ApiOperation("Deleta um produto")
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<String> delete(@ApiParam(value = "ID do produto", example = "1") @RequestParam Long idProduto) {
        cadastroProdutoService.deleteById(idProduto);
        return new ResponseEntity<String>("Produto ID: " + idProduto + ": deletado com sucesso!", HttpStatus.OK);

    }

    @ApiOperation("Busca um produto por ID")
    @GetMapping(value = "/produto/{idProduto}")
    public ResponseEntity<ProdutoRepresentation> getProdutoById(@ApiParam(value = "Id do produto", example = "1")
                                                                     @PathVariable(value = "idProduto") Long idProduto) {
        ProdutoRepresentation produtoRepresentation = toModel(cadastroProdutoService.getProdutoById(idProduto));
        return new ResponseEntity<ProdutoRepresentation>(produtoRepresentation, HttpStatus.OK);
    }


    @ApiOperation("Busca produtos por descrição")
    @GetMapping(value = "/produto")
    public ResponseEntity<List<ProdutoRepresentation>> getProdutosByName(
            @RequestParam(name = "descricao") String descricao) {
        // obtem a lista de produtos do Model, nossas entidades
        List<Produto> produtos = cadastroProdutoService.getProdutosByDescricao(descricao.toUpperCase());
        // convertemos o lista Model para Representation Model nosso DTO de saída
        List<ProdutoRepresentation> produtosRepresentationModel = toCollectionModel(produtos);
        return new ResponseEntity<List<ProdutoRepresentation>>(produtosRepresentationModel, HttpStatus.OK);
    }


    @ApiOperation("Lista produtos")
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ProdutoRepresentation>> getProdutos() {

        List<Produto> produtos = cadastroProdutoService.getProdutos();

        // convertemos o lista Model para Representation Model nosso DTO de saída
        List<ProdutoRepresentation> produtosRepresentationModel = toCollectionModel(produtos);

        return new ResponseEntity<List<ProdutoRepresentation>>(produtosRepresentationModel, HttpStatus.OK);


    }


    private Produto toDomainObject(ProdutoInput produtoInput) {
        Produto produto = new Produto();
        produto.setId(produtoInput.getIdProduto());
        produto.setDescricao(produtoInput.getDescricao());
        produto.setPrecoCompra(produtoInput.getCompra());
        produto.setPrecoVenda(produtoInput.getVenda());
        return produto;
    }

    private ProdutoRepresentation toModel(Produto produto) {
        ProdutoRepresentation produtoRepresentation = new ProdutoRepresentation();
        produtoRepresentation.setId(produto.getId());
        produtoRepresentation.setDecricao(produto.getDescricao());
        produtoRepresentation.setPrecoCompra(produto.getPrecoCompra());
        produtoRepresentation.setPrecoVenda(produto.getPrecoVenda());
        return produtoRepresentation;
    }

    // Converte uma lista de objetos do tipo Produto para uma lista de objetos do tipo ProdutoRepresentationModel
    private List<ProdutoRepresentation> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(Produto -> toModel(Produto))
                .collect(Collectors.toList());

    }

}
