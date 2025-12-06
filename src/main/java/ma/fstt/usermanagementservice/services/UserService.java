package ma.fstt.usermanagementservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fstt.usermanagementservice.entities.User;
import ma.fstt.usermanagementservice.exception.UserAlreadyExistsException;
import ma.fstt.usermanagementservice.exception.UserNotFoundException;
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
                .orElseThrow(() -> new UserNotFoundException(wallet));
    }


    /**
     * Créer un nouvel utilisateur
     */

    @Transactional
    public User createUser(User user) {

        log.info("📥 [REGISTER] Requête reçue pour créer un utilisateur");

        if (user == null) {
            log.error("❌ User reçu est null !");
            throw new IllegalArgumentException("User object is null");
        }

        log.info("➡️ Données reçues : wallet={}, username={}, email={}, firstName={}, lastName={}, description={}",
                user.getWallet(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getDescription()
        );

        log.info("🔍 Vérification si le wallet existe déjà en base...");
        boolean exists = userRepository.existsByWallet(user.getWallet());
        log.info("➡️ existsByWallet({}) = {}", user.getWallet(), exists);

        if (exists) {
            log.warn("❌ Impossible de créer : ce wallet existe déjà !");
            throw new UserAlreadyExistsException(user.getWallet());
        }

        user.setCreatedAt(Instant.now());
        log.info("⏳ Date de création set: {}", user.getCreatedAt());

        log.info("💾 Sauvegarde de l'utilisateur en base...");
        User saved = userRepository.save(user);

        log.info("✅ [REGISTER] Utilisateur créé avec succès : id={}, wallet={}",
                saved.getId(), saved.getWallet());

        return saved;
    }


    /**
     * Mettre à jour un utilisateur
     */
    @Transactional
     public User updateUser(Long id, User userDetails) {
     User user = userRepository.findById(id)
     .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
     user.setUsername(userDetails.getUsername());
     user.setEmail(userDetails.getEmail());
     user.setRole(userDetails.getRole());
     user.setEnabled(userDetails.getEnabled());
     user.setUpdatedAt(Instant.now());
     return userRepository.save(user);
     }

     @Transactional
     public void deleteUser(Long id) {
     if (!userRepository.existsById(id)) {
     throw new UserNotFoundException("User not found with id: " + id);
     }
     userRepository.deleteById(id);
     }

}