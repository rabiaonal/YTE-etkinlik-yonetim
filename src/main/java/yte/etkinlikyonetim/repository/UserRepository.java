package yte.etkinlikyonetim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.etkinlikyonetim.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);
}
