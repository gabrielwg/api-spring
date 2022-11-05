package br.com.futurodev.apispring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRepresentation {
    private Long id;
    private String nome;
    private String cpf;
    private String rg;
}
