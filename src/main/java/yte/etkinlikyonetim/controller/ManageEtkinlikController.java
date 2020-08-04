package yte.etkinlikyonetim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yte.etkinlikyonetim.entity.*;
import yte.etkinlikyonetim.mapper.EtkinlikMapper;
import yte.etkinlikyonetim.service.ManageEtkinlikService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class ManageEtkinlikController {

    private final ManageEtkinlikService manageEtkinlikService;
    private final EtkinlikMapper etkinlikMapper;

    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }

    @PostMapping("/auth")
    public String auth(User user){
        return manageEtkinlikService.auth(user) ? "Giriş Başarılı " :  "Giriş Başarısız" ;
    }

    @GetMapping("/etkinlikler")
    public List<Etkinlik> listAllEtkinlik(){
        return manageEtkinlikService.listAllEtkinlikToMisafir();
    }

    @GetMapping("/etkinlikler/{id}")
    public Etkinlik showEtkinlik(@PathVariable Long id){
        return manageEtkinlikService.getEtkinlik(id);
    }

    @GetMapping("/etkinlikler/{id}/konum")
    public String seeHaritaKonum(@PathVariable Long id){
        return "https://www.google.com/maps/search/?api=1&query=" + manageEtkinlikService.getAdresUrl(id);
    }

    @GetMapping("/adresler")
    public List<Adres> listAllAdres(){
        return manageEtkinlikService.listAllAdres();
    }

    @PostMapping("/etkinlikler/{id}/kaydol")
    public String etkinlikKaydol(@Valid @RequestBody Katilimci katilimci, @PathVariable Long id) throws Exception{
        return manageEtkinlikService.etkinlikKaydol(katilimci, id);
    }

    @PostMapping("/karekodPath/{id}")
    public String getPath(@RequestBody Katilimci katilimci, @PathVariable Long id){
        System.out.println("id: " + id);
        return manageEtkinlikService.getQRpath(katilimci, id);
    }




}
