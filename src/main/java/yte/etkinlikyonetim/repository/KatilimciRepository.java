package yte.etkinlikyonetim.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yte.etkinlikyonetim.entity.Katilimci;

import java.util.Optional;

public interface KatilimciRepository extends JpaRepository<Katilimci, Long> {

    Optional<Katilimci> findByTcKimlikNo(String tcKimlikNo);

    boolean existsByTcKimlikNo(String tcKimlikNo);

    @Query("select k.id from Katilimci k where k.tcKimlikNo = ?1")
    Long findIdByTcKimlikNo(String tcKimlikNo);

}
