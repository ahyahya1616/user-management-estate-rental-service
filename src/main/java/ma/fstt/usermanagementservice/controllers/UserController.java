package ma.fstt.usermanagementservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fstt.usermanagementservice.entities.User;
import ma.fstt.usermanagementservice.exception.NonceNotFoundException;
import ma.fstt.usermanagementservice.exception.UserAlreadyExistsException;
import ma.fstt.usermanagementservice.exception.UserNotFoundException;
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

    // ==================== USER ENDPOINTS ====================

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/wallet/{wallet}")
    public ResponseEntity<?> getUserByWallet(@PathVariable String wallet) {
        try {
            User user = userService.getUserByWallet(wallet);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            log.warn("Wallet not found: {}", wallet);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "USER_NOT_FOUND", "message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (UserAlreadyExistsException e) {
            log.warn("User already exists: {}", user.getWallet());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "USER_ALREADY_EXISTS", "message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            log.warn("User not found for update: id={}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "USER_NOT_FOUND", "message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            log.warn("User not found for deletion: id={}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "USER_NOT_FOUND", "message", e.getMessage()));
        }
    }

    // ==================== NONCE ENDPOINTS ====================

    @PostMapping("/nonce")
    public ResponseEntity<?> storeNonce(@RequestParam String wallet, @RequestParam String nonce) {
        try {
            nonceService.storeNonce(wallet, nonce);
            return ResponseEntity.ok(Map.of("message", "Nonce stored"));
        } catch (UserNotFoundException e) {
            log.warn("User not found for storing nonce: {}", wallet);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "USER_NOT_FOUND", "message", e.getMessage()));
        }
    }

    @GetMapping("/nonce")
    public ResponseEntity<String> getNonce(@RequestParam String wallet) {
        try {
            String nonce = nonceService.getNonce(wallet);
            return ResponseEntity.ok(nonce); // retourne directement la chaîne
        } catch (UserNotFoundException e) {
            log.warn("Wallet not found: {}", wallet);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("USER_NOT_FOUND: " + e.getMessage());
        } catch (NonceNotFoundException e) {
            log.warn("Nonce not found or expired for wallet: {}", wallet);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("NONCE_NOT_FOUND: " + e.getMessage());
        }
    }


    @DeleteMapping("/nonce")
    public ResponseEntity<?> deleteNonce(@RequestParam String wallet) {
        try {
            nonceService.deleteNonce(wallet);
            return ResponseEntity.ok(Map.of("message", "Nonce deleted"));
        } catch (UserNotFoundException e) {
            log.warn("User not found for deleting nonce: {}", wallet);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "USER_NOT_FOUND", "message", e.getMessage()));
        }
    }
}
