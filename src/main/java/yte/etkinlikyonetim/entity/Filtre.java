package yte.etkinlikyonetim.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Filtre {

    private String filtre;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean konferans;
    private boolean fuar;
    private boolean yarisma;
    private boolean senlik;
    private boolean calistay;
    private String sort;
}
