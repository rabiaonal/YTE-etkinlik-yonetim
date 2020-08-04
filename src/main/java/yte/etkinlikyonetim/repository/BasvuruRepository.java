package yte.etkinlikyonetim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import yte.etkinlikyonetim.BasvuruPK;
import yte.etkinlikyonetim.entity.Basvuru;
import yte.etkinlikyonetim.entity.Etkinlik;
import yte.etkinlikyonetim.entity.Katilimci;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface BasvuruRepository extends JpaRepository<Basvuru, BasvuruPK> {

    Optional<Basvuru> findByEtkinlikAndKatilimci(Etkinlik etkinlik, Katilimci katilimci);
    boolean existsByEtkinlikAndKatilimci(Etkinlik etkinlik, Katilimci katilimci);

    int countByEtkinlikAndKatilimci(Etkinlik etkinlik, Katilimci katilimci);

    Optional<Basvuru> findById(BasvuruPK id);

    @Query("select b from Basvuru b where b.etkinlik.id= ?1")
    List<Basvuru> findByEtkinlikId(Long id);

    @Query("select count(b) from Basvuru b where b.etkinlik = ?1 and b.katilimci = ?2")
    int existsCustom(Etkinlik etkinlik, Katilimci katilimci);

    @Transactional
    @Modifying
    @Query("delete from Basvuru b where b.etkinlik.id= ?1")
    void deleteAllByEtkinlikid(Long id);

}
