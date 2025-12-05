package ma.fstt.usermanagementservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fstt.usermanagementservice.entities.User;
import ma.fstt.usermanagementservice.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * Récupérer tous les utilisateurs
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Récupérer un utilisateur par wallet
     */
    public User getUserByWallet(String wallet) {
        return userRepository.findByWallet(wallet)
                .orElseThrow(() -> new RuntimeException("User not found with wallet: " + wallet));
    }

    /**
     * Créer un nouvel utilisateur
     */
    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByWallet(user.getWallet())) {
            throw new RuntimeException("User already exists with wallet: " + user.getWallet());
        }

        user.setCreatedAt(Instant.now());
        log.info("Creating user with wallet: {}", user.getWallet());

        return userRepository.save(user);
    }

    /**
     * Mettre à jour un utilisateur
     */
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setEnabled(userDetails.getEnabled());
        user.setUpdatedAt(Instant.now());

        return userRepository.save(user);
    }

    /**
     * Supprimer un utilisateur
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}