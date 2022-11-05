package br.com.futurodev.apispring.input;

import br.com.futurodev.apispring.dto.TelefoneRepresentation;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class UsuarioInput {

    private Long id;

    private String nome;

    @NotBlank(message = "{login.not.blank}")
    private String login;
    private String senha;

    private List<TelefoneRepresentation> telefones = new ArrayList<TelefoneRepresentation>();

    public List<TelefoneRepresentation> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneRepresentation> telefones) {
        this.telefones = telefones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
