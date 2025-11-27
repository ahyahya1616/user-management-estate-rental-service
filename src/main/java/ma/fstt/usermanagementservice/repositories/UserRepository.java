package ma.fstt.usermanagementservice.repositories;

import ma.fstt.usermanagementservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByWallet(String wallet);

    boolean existsByWallet(String wallet);

    Optional<User> findByEmail(String email);
}
