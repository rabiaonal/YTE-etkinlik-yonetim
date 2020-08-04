package yte.etkinlikyonetim.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EtkinlikDTO {

    @NotNull(message = "İsim bilgisi zorunludur.")
    @NotEmpty(message = "İsim bilgisi zorunludur.")
    private String isim;

    @NotNull(message = "Başlangıç tarihi bilgisi zorunludur.")
    @FutureOrPresent
    private LocalDate baslangicTarihi;

    @NotNull(message = "Bitiş tarihi bilgisi zorunludur.")
    private LocalDate bitisTarihi;

    private LocalDate eklenmeTarihi;

    @NotNull(message = "Konum bilgisi zorunludur.")
    private Long konum_id;

    @NotNull(message = "Etkinlik tipi bilgisi zorunludur.")
    private String etkinlikTipi;

    @NotNull(message = "Kontenjan bilgisi zorunludur.")
    @PositiveOrZero
    private int kontenjan;


    @AssertTrue(message = "Baslangıç tarihi bitiş tarihinden önce olmak zorundadır.")
    public boolean isBitisTarihiValid(){
        return !bitisTarihi.isBefore(baslangicTarihi);
    }

}
