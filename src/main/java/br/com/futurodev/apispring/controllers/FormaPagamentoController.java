package br.com.futurodev.apispring.controllers;

import br.com.futurodev.apispring.dto.FormaPagamentoRepresentation;
import br.com.futurodev.apispring.input.FormaPagamentoInput;
import br.com.futurodev.apispring.model.FormaPagamento;
import br.com.futurodev.apispring.service.CadastroFormaPagamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api("Formas de Pagamento")
@RestController
@RequestMapping(value = "/pagamentos")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @ApiOperation("Salva pagamento")
    @PostMapping(value = "/", produces ="application/json")
    public ResponseEntity<FormaPagamentoRepresentation> cadastrar(@RequestBody FormaPagamentoInput pagamentoInput){
        FormaPagamento pagamento = toDomainObject(pagamentoInput);
        cadastroFormaPagamentoService.salvar(pagamento);
        return new ResponseEntity<FormaPagamentoRepresentation>(toModel(pagamento), HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza pagamento")
    @PutMapping(value = "/", produces ="application/json")
    public ResponseEntity<FormaPagamentoRepresentation> atualizar(@RequestBody FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoModel = cadastroFormaPagamentoService.salvar(toDomainObject(formaPagamentoInput));
        return new ResponseEntity<FormaPagamentoRepresentation>(toModel(formaPagamentoModel), HttpStatus.OK);
    }

    @ApiOperation("Deleta pagamento")
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long idFormaPagamento) {
        cadastroFormaPagamentoService.delete(idFormaPagamento);
        return new ResponseEntity<String>("Forma de pagamento ID: " + idFormaPagamento + " deletado.", HttpStatus.OK);
    }

    @ApiOperation("Listar pagamento")
    @GetMapping(value = "/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<FormaPagamentoRepresentation>> getFormaPagamentos(){
        List<FormaPagamento> pagamentos = cadastroFormaPagamentoService.getFormaPagamentos();
        List<FormaPagamentoRepresentation> formaPagamentoRepresentationModels = toCollectionModel(pagamentos);
        return new ResponseEntity<List<FormaPagamentoRepresentation>>(formaPagamentoRepresentationModels,HttpStatus.OK);
    }

    private List<FormaPagamentoRepresentation> toCollectionModel(List<FormaPagamento> pagamentosModel){
        return pagamentosModel.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput){

        FormaPagamento formaPagamentoModel = new FormaPagamento();
        formaPagamentoModel.setId(formaPagamentoInput.getId());
        formaPagamentoModel.setDescricao(formaPagamentoInput.getDescricao());

        return formaPagamentoModel;
    }

    private FormaPagamentoRepresentation toModel(FormaPagamento pagamento){
        FormaPagamentoRepresentation formaPagamentoRepresentationModel = new FormaPagamentoRepresentation();
        formaPagamentoRepresentationModel.setId(pagamento.getId());
        formaPagamentoRepresentationModel.setDescricao(pagamento.getDescricao());

        return formaPagamentoRepresentationModel;
    }
}
