package ma.fstt.usermanagementservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fstt.usermanagementservice.entities.User;
import ma.fstt.usermanagementservice.services.NonceService;
import ma.fstt.usermanagementservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final NonceService nonceService;

    /**
     * GET /api/users - Récupérer tous les utilisateurs
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * GET /api/users/wallet/{wallet} - Récupérer un utilisateur par wallet
     */
    @GetMapping("/wallet/{wallet}")
    public ResponseEntity<User> getUserByWallet(@PathVariable String wallet) {
        try {
            User user = userService.getUserByWallet(wallet);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            log.error("User not found: {}", wallet);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * POST /api/users - Créer un nouvel utilisateur
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            log.error("Error creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * PUT /api/users/{id} - Mettre à jour un utilisateur
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            log.error("Error updating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/users/{id} - Supprimer un utilisateur
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error deleting user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ==================== NONCE ENDPOINTS ====================

    /**
     * POST /api/users/nonce - Stocker un nonce
     */
    @PostMapping("/nonce")
    public ResponseEntity<Void> storeNonce(
            @RequestParam String wallet,
            @RequestParam String nonce) {
        try {
            nonceService.storeNonce(wallet, nonce);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Error storing nonce: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/users/nonce?wallet=0x... - Récupérer un nonce
     */
    @GetMapping("/nonce")
    public ResponseEntity<String> getNonce(@RequestParam String wallet) {
        try {
            String nonce = nonceService.getNonce(wallet);
            return ResponseEntity.ok(nonce);
        } catch (RuntimeException e) {
            log.error("Error getting nonce: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * DELETE /api/users/nonce?wallet=0x... - Supprimer un nonce
     */
    @DeleteMapping("/nonce")
    public ResponseEntity<Void> deleteNonce(@RequestParam String wallet) {
        try {
            nonceService.deleteNonce(wallet);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Error deleting nonce: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}