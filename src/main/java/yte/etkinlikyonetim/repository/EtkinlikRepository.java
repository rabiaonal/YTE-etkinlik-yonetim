package yte.etkinlikyonetim.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yte.etkinlikyonetim.entity.Etkinlik;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EtkinlikRepository extends JpaRepository<Etkinlik, Long> {
    List<Etkinlik> findAll();
    List<Etkinlik> findAll(Sort sort);
    Etkinlik findByIsim(String isim);
    Optional<Etkinlik> findById(Long id);
    boolean existsByIsim(String isim);
    boolean existsById(Long id);

    List<Etkinlik> findByBaslangicTarihiAfter(LocalDate today);

    @Transactional
    void deleteByIsim(String isim);

    @Transactional
    void deleteById(Long id);

}
