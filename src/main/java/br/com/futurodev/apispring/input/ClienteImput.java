package br.com.futurodev.apispring.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteImput {
    private Long id;

    private String nome;

    private String cpf;

    private String rg;


}
