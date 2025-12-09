package ma.fstt.usermanagementservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fstt.usermanagementservice.dto.UserCreateDto;
import ma.fstt.usermanagementservice.dto.UserResponseDto;
import ma.fstt.usermanagementservice.dto.UserUpdateDto;
import ma.fstt.usermanagementservice.services.NonceService;
import ma.fstt.usermanagementservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final NonceService nonceService;

    // ==================== USER ENDPOINTS ====================

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/wallet/{wallet}")
    public ResponseEntity<UserResponseDto> getUserByWallet(@PathVariable String wallet) {
        UserResponseDto userDto = userService.getUserByWallet(wallet);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto dto) {
        UserResponseDto createdUser = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto dto) {
        UserResponseDto updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // ==================== NONCE ENDPOINTS ====================

    @PostMapping("/nonce")
    public ResponseEntity<?> storeNonce(@RequestParam String wallet, @RequestParam String nonce) {
        nonceService.storeNonce(wallet, nonce);
        return ResponseEntity.ok().body("Nonce stored successfully");
    }

    @GetMapping("/nonce")
    public ResponseEntity<String> getNonce(@RequestParam String wallet) {
        String nonce = nonceService.getNonce(wallet);
        return ResponseEntity.ok(nonce);
    }

    @DeleteMapping("/nonce")
    public ResponseEntity<?> deleteNonce(@RequestParam String wallet) {
        nonceService.deleteNonce(wallet);
        return ResponseEntity.ok().body("Nonce deleted successfully");
    }
}
