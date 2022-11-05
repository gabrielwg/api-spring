package br.com.futurodev.apispring.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeRole; /* Papel, ROLE_GERENTE, ROLE_ADMINISTRADOR*/

    @Override
    public String getAuthority() {
        return this.nomeRole;
    }

}
