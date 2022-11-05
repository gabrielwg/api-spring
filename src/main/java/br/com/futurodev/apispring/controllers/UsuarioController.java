package br.com.futurodev.apispring.controllers;

import br.com.futurodev.apispring.dto.TelefoneRepresentation;
import br.com.futurodev.apispring.dto.UsuarioRepresentation;
import br.com.futurodev.apispring.input.UsuarioInput;
import br.com.futurodev.apispring.model.Telefone;
import br.com.futurodev.apispring.model.Usuario;
import br.com.futurodev.apispring.service.CadastroUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://app.floripa.com:9000", "http://app.saopaulo.com:9000"})
@Api(tags = "Usuários")
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private CadastroUsuarioService casdastroUsuarioService;


    @ApiOperation("Salva um usuário")
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<UsuarioRepresentation> cadastrar(@RequestBody @Valid UsuarioInput usuarioInput){
        // converte UsuarioInput em UsuarioModel
        Usuario usu = toDomainObject(usuarioInput);
        // chama nosso service para salvar o UsuarioModel no banco de dados
        casdastroUsuarioService.salvar(usu);

        // montamos o retorno com a reposta da requisição, convertemos UsuarioModel em RepresentationModel
        return new ResponseEntity<UsuarioRepresentation>(toModel(usu), HttpStatus.CREATED);

    }


    @ApiOperation("Atualiza um usuário")
    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<UsuarioRepresentation> atualizar(@RequestBody UsuarioInput usuarioInput){
        Usuario usuario = casdastroUsuarioService.salvar(toDomainObject(usuarioInput));
        return new ResponseEntity<UsuarioRepresentation>(toModel(usuario), HttpStatus.OK);

    }


    @ApiOperation("Deleta um usuário")
    @DeleteMapping(value = "/")
    @ResponseBody
    public ResponseEntity<String> delete(@ApiParam(value = "ID do usuário", example = "1") @RequestParam Long idUsuario){
        casdastroUsuarioService.delete(idUsuario);
        return new ResponseEntity<String>("Usuário deletado com sucesso!",HttpStatus.OK);
    }


    @ApiOperation("Busca um usuário por ID")
    @GetMapping(value = "/{idUsuario}", produces = "application/json")
    public ResponseEntity<UsuarioRepresentation> getUserById(@PathVariable(value = "idUsuario") Long idUsuario){
        Usuario usu =  casdastroUsuarioService.getUserById(idUsuario);

        UsuarioRepresentation usuarioRepresentation = toModel(usu);

        return new ResponseEntity<UsuarioRepresentation>(usuarioRepresentation, HttpStatus.OK);

    }

    @ApiOperation("Busca usuários por nome")
    @GetMapping(value = "/buscarPorNome", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<UsuarioRepresentation>> getUserByName(@RequestParam (name = "nome") String nome){
        // obtem a lista de usuario do tipo Model, nossas entidades
        List<Usuario> usuarios = casdastroUsuarioService.getUserByName(nome);

        // nos convertemos o objeto do tipo UsuarioModel para RepresentationModel (DTO)
        List<UsuarioRepresentation> usuariosRepresentationModel = toCollectionModel(usuarios);
        // System.out.println(usuariosRepresentationModel.get(0).getDataCadastro());
        return new ResponseEntity<List<UsuarioRepresentation>>(usuariosRepresentationModel,HttpStatus.OK);
    }


    @CrossOrigin
    @ApiOperation("Listar usuários")
    @GetMapping(value = "", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<UsuarioRepresentation>> getUsers(){
        // obtem a lista de usuario do tipo Model, nossas entidades
        List<Usuario> usuarios = casdastroUsuarioService.getUsers();

        // nos convertemos o objeto do tipo UsuarioModel para RepresentationModel (DTO)
        List<UsuarioRepresentation> usuariosRepresentationModel = toCollectionModel(usuarios);
        return new ResponseEntity<List<UsuarioRepresentation>>(usuariosRepresentationModel,HttpStatus.OK);
    }


    // converte um objeto do tipo UsuarioModel para um objeto do tipo RepresentationModel
    private UsuarioRepresentation toModel(Usuario usu) {

        UsuarioRepresentation usuarioRepresentation = new UsuarioRepresentation();
        usuarioRepresentation.setId(usu.getId());
        usuarioRepresentation.setNome(usu.getNome());
        usuarioRepresentation.setLogin(usu.getLogin());
        usuarioRepresentation.setSenha(usu.getSenha());

        // itera nos telefones do usuario e converter para DTOs
        for (int i=0; i<usu.getTelefones().size(); i++){

            TelefoneRepresentation telefoneRepresentation = new TelefoneRepresentation();
            telefoneRepresentation.setTipo(usu.getTelefones().get(i).getTipo());
            telefoneRepresentation.setNumero(usu.getTelefones().get(i).getNumero());
            telefoneRepresentation.setId(usu.getTelefones().get(i).getId());

            usuarioRepresentation.getTelefones().add(telefoneRepresentation);
        }

        return usuarioRepresentation;
    }

    // Converte uma lista de objetos do tipo UsuarioModel para uma lista de objetos do tipo UsuarioRepresentationModel
    private List<UsuarioRepresentation> toCollectionModel(List<Usuario> usuariosModel){
        return usuariosModel.stream()
                .map(usuarioModel -> toModel(usuarioModel))
                .collect(Collectors.toList());

    }

    // Converter um objeto do tipo UsuarioInput para UsuarioModel
    private Usuario toDomainObject(UsuarioInput usuarioInput){

        Usuario usuario = new Usuario();
        usuario.setId(usuarioInput.getId());
        usuario.setNome(usuarioInput.getNome());
        usuario.setLogin(usuarioInput.getLogin());
        usuario.setSenha(usuarioInput.getSenha());

        for (int i=0; i<usuarioInput.getTelefones().size(); i++){
            Telefone telefone = new Telefone();
            telefone.setTipo(usuarioInput.getTelefones().get(i).getTipo());
            telefone.setNumero(usuarioInput.getTelefones().get(i).getNumero());
            telefone.setId(usuarioInput.getTelefones().get(i).getId());
            telefone.setUsuario(usuario);

            usuario.getTelefones().add(telefone);
        }
        return usuario;

    }
}