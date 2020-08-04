package yte.etkinlikyonetim.entity;

import lombok.*;
import yte.etkinlikyonetim.BasvuruPK;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Basvuru implements Serializable {

    @EmbeddedId
    private BasvuruPK id;

    @ManyToOne
    @MapsId("etkinlik_id")
    @JoinColumn(name = "ETKINLIK_ID")
    private Etkinlik etkinlik;

    @ManyToOne
    @MapsId("katilimci_id")
    @JoinColumn(name = "KATILIMCI_ID")
    private Katilimci katilimci;

    @Column(name = "BASVURU_TARIHI")
    private LocalDate basvuruTarihi;
}
