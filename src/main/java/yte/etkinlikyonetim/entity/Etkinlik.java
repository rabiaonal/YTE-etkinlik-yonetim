package yte.etkinlikyonetim.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Etkinlik {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "ISIM")
    private String isim;

    @Column(name = "BASLANGIC_TARIHI")
    private LocalDate baslangicTarihi;

    @Column(name = "BITIS_TARIHI")
    private LocalDate bitisTarihi;

    @Column(name = "EKLENME_TARIHI")
    private LocalDate eklenmeTarihi;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADRES_ID")
    private Adres adres;

    @Column(name = "ETKINLIK_TIPI")
    private String etkinlikTipi;

    @Column(name = "KONTENJAN")
    private int kontenjan;


    @OneToMany(mappedBy = "katilimci")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Basvuru> basvurular;

    @Column(name = "BASVURU_SAYISI")
    private int basvuruSayisi;


    public boolean yerVar(){
        if (basvuruSayisi < kontenjan)
            return true;
        return false;
    }

    public void addBasvuru(){
        basvuruSayisi++;
    }






}
