package yte.etkinlikyonetim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yte.etkinlikyonetim.dto.EtkinlikDTO;
import yte.etkinlikyonetim.entity.Adres;
import yte.etkinlikyonetim.entity.Etkinlik;
import yte.etkinlikyonetim.entity.Katilimci;
import yte.etkinlikyonetim.mapper.EtkinlikMapper;
import yte.etkinlikyonetim.service.ManageEtkinlikService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final EtkinlikMapper etkinlikMapper;
    private final ManageEtkinlikService manageEtkinlikService;

    @PostMapping("etkinlikler/add")
    public String addEtkinlik(@Valid @RequestBody EtkinlikDTO etkinlikDTO) {
        Etkinlik etkinlik = etkinlikMapper.mapToEntity(etkinlikDTO);
        Long konum_id = 0L;
        konum_id = etkinlikDTO.getKonum_id();
        if(manageEtkinlikService.addEtkinlik(etkinlik, konum_id))
            return "Etkinlik başarıyla eklendi";
        return "Etkinlik eklenemedi, daha önceden var olmayan bir isim girdiğinizden emin olun.";
    }

    @GetMapping("/etkinlikler/delete")
    public String deleteEtkinlik(@RequestParam Long id){
        //baslangıç tarihi geldiyse direkt dön
        Etkinlik oldEtkinlik = manageEtkinlikService.getEtkinlik(id);
        if(oldEtkinlik.getBaslangicTarihi().isBefore(LocalDate.now()))
            return "Etkinlik başlangıç tarihinden sonra güncelleme yapılamaz";
        if(manageEtkinlikService.deleteEtkinlik(id))
            return "Etkinlik başarıyla silindi.";
        return "Etkinlik silinemedi, bu isimle bir etkinlik olduğundan emin olun.";
    }

    @PutMapping("etkinlikler/update/{id}")
    public String updateEtkinlik(@RequestParam(required = false) String isim,
                               @RequestParam(required = false) String baslangicTarihi,
                               @RequestParam(required = false) String bitisTarihi,
                               @RequestParam(defaultValue = "-1") Long adres_id,
                               @RequestParam(required = false) String etkinlikTipi,
                               @RequestParam(defaultValue = "-1") int kontenjan,
                               @PathVariable Long id){
        //baslangıç tarihi geldiyse direkt dön
        Etkinlik oldEtkinlik = manageEtkinlikService.getEtkinlik(id);
        if(oldEtkinlik.getBaslangicTarihi().isBefore(LocalDate.now()))
            return "Etkinlik başlangıç tarihinden sonra güncelleme yapılamaz";

        String newIsim = isim == null ? oldEtkinlik.getIsim() : isim;
        Adres newAdres = adres_id == -1 ? oldEtkinlik.getAdres() : manageEtkinlikService.getAdres(adres_id);
        int newKontenjan = kontenjan == -1 ? oldEtkinlik.getKontenjan() : kontenjan;
        String newEtkinlikTipi = etkinlikTipi == null ? oldEtkinlik.getEtkinlikTipi() : etkinlikTipi;
        LocalDate newBaslangicTarihi = baslangicTarihi == null ? oldEtkinlik.getBaslangicTarihi() : LocalDate.of(Integer.parseInt(baslangicTarihi.substring(0,4)),
                                                                                                                Integer.parseInt(baslangicTarihi.substring(5,7)),
                                                                                                                Integer.parseInt(baslangicTarihi.substring(8)));
        LocalDate newBitisTarihi = bitisTarihi == null ? oldEtkinlik.getBitisTarihi() : LocalDate.of(Integer.parseInt(bitisTarihi.substring(0,4)),
                                                                                                                Integer.parseInt(bitisTarihi.substring(5,7)),
                                                                                                                Integer.parseInt(bitisTarihi.substring(8)));

        Etkinlik newEtkinlik = new Etkinlik(null, newIsim, newBaslangicTarihi, newBitisTarihi,
                                        oldEtkinlik.getEklenmeTarihi(), newAdres, newEtkinlikTipi, newKontenjan, new HashSet<>(), 0);

        if(manageEtkinlikService.updateEtkinlik(oldEtkinlik, newEtkinlik))
            return "Güncelleme başarılı.";
        return "Güncelleme başarısız.";
    }

    @GetMapping("/etkinlikler")
    public List<Etkinlik> listAllEtkinlik(){
        return manageEtkinlikService.listAllEtkinlik();
    }

    @GetMapping("/katilimcilar/{id}")
    public List<Katilimci> listAllKatilimcilar(@PathVariable Long id){
        return manageEtkinlikService.listAllKatilimci(id);
    }

}
