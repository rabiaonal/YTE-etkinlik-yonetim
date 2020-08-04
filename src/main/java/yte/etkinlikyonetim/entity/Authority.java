package yte.etkinlikyonetim.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    private String authority;
}
