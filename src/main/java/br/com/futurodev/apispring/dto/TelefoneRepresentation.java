package br.com.futurodev.apispring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelefoneRepresentation {

    private Long id;
    private String numero;
    private String tipo;

}
