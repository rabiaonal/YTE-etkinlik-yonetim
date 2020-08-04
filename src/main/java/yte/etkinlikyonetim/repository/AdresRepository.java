package yte.etkinlikyonetim.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import yte.etkinlikyonetim.entity.Adres;

import java.util.List;

public interface AdresRepository extends JpaRepository<Adres, Long> {

    List<Adres> findAll();
}
