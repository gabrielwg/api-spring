package br.com.futurodev.apispring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class UsuarioRepresentation {

    private Long id;

   // @JsonIgnore //ignora o atributo na geração da represemtação de saída
    private String nome;
    private String login;

   // @JsonIgnore
    private String senha;

    private OffsetDateTime dataCadastro;

    private OffsetDateTime dataAtualizacao;


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    @JsonIgnoreProperties(value = "tipo", allowGetters = true)
    private List<TelefoneRepresentation> telefones = new ArrayList<TelefoneRepresentation>();



}
