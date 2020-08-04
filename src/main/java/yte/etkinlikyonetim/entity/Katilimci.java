package yte.etkinlikyonetim.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import yte.etkinlikyonetim.validator.TcKimlikNo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Katilimci {
    @Id
    @GeneratedValue
    private Long id;

    @TcKimlikNo
    private String tcKimlikNo;

    @NotEmpty(message = "İsim bilgisi zorunludur")
    private String ad;

    @NotEmpty(message = "Soyisim bilgisi zorunludur")
    private String soyad;

    @NotEmpty(message = "E-posta bilgisi zorunludur")
    @Email
    private String email;


    @OneToMany(mappedBy = "etkinlik")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Basvuru> basvurular;


    public void addBasvuru(Basvuru basvuru){
        System.out.println(basvuru);
        basvurular.add(basvuru);
    }
}
