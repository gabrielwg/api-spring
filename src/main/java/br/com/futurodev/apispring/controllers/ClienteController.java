package br.com.futurodev.apispring.controllers;

import br.com.futurodev.apispring.dto.ClienteRepresentation;
import br.com.futurodev.apispring.input.ClienteImput;
import br.com.futurodev.apispring.model.Cliente;
import br.com.futurodev.apispring.service.CadastroClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

    @Api(tags = "Clientes")
    @RestController
    @RequestMapping(value = "/clientes")
    public class ClienteController {

        @Autowired
        private CadastroClienteService cadastroClienteService;

        @ApiOperation("Salva cliente")
        @PostMapping(value = "/", produces ="application/json")
        public ResponseEntity<ClienteRepresentation> cadastrar(@RequestBody ClienteImput clienteInput){
            Cliente cli = toDomainObject(clienteInput);
            cadastroClienteService.salvar(cli);
            return new ResponseEntity<ClienteRepresentation>(toModel(cli), HttpStatus.CREATED);
        }

        @ApiOperation("Atualiza cliente")
        @PutMapping(value = "/", produces ="application/json")
        public ResponseEntity<ClienteRepresentation> atualizar(@RequestBody ClienteImput clienteInput) {
            Cliente cliente = cadastroClienteService.salvar(toDomainObject(clienteInput));
            return new ResponseEntity<ClienteRepresentation>(toModel(cliente), HttpStatus.OK);
        }

        @ApiOperation("Deleta cliente")
        @DeleteMapping
        @ResponseBody
        public ResponseEntity<String> delete(@RequestParam Long idCliente) {
            cadastroClienteService.delete(idCliente);
            return new ResponseEntity<String>("Cliente de ID: " + idCliente + " deletado.", HttpStatus.OK);
        }



        @ApiOperation("Listar clientes")
        @GetMapping(value = "/", produces = "application/json")
        @ResponseBody
        public ResponseEntity<List<ClienteRepresentation>> getClientes(){
            List<Cliente> clientes = cadastroClienteService.getClientes();
            List<ClienteRepresentation> clientesRepresentationModel = toCollectionModel(clientes);
            return new ResponseEntity<List<ClienteRepresentation>>(clientesRepresentationModel,HttpStatus.OK);
        }


        private List<ClienteRepresentation> toCollectionModel(List<Cliente> clientesModel){
            return clientesModel.stream()
                    .map(this::toModel)
                    .collect(Collectors.toList());
        }

        private Cliente toDomainObject(ClienteImput clienteInput){

            Cliente clienteModel = new Cliente();
            clienteModel.setId(clienteInput.getId());
            clienteModel.setNome(clienteInput.getNome());
            clienteModel.setCpf(clienteInput.getCpf());
            clienteModel.setRg(clienteInput.getRg());

            return clienteModel;
        }

        private ClienteRepresentation toModel(Cliente cli) {
            ClienteRepresentation clienteRepresentation = new ClienteRepresentation();
            clienteRepresentation.setId(cli.getId());
            clienteRepresentation.setNome(cli.getNome());
            clienteRepresentation.setCpf(cli.getCpf());
            clienteRepresentation.setRg(cli.getRg());

            return clienteRepresentation;
        }
}
