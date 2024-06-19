package project.batdongsan.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import project.batdongsan.model.entity.PersistentLogin;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RememberMeTokenRepository extends JpaRepository<PersistentLogin,String> {

    Optional<PersistentLogin> findBySeries(String series);
    List<PersistentLogin> findByUsername(String username);
    Iterable<PersistentLogin> findByLastUseAfter(Date expiration);
}
