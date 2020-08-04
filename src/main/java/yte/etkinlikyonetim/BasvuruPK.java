package yte.etkinlikyonetim;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class BasvuruPK implements Serializable {

    @Column(name = "ETKINLIK_ID")
    private Long etkinlik_id;

    @Column(name = "KATILIMCI_ID")
    private Long katilimci_id;
}
