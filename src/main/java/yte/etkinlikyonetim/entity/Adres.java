package yte.etkinlikyonetim.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Adres {

    @Id
    @GeneratedValue
    private Long id;

    private String fullAdres;

    private String enlem;
    private String boylam;

    public String toString(){
        return fullAdres;
    }

}
