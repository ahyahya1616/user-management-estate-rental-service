package ma.fstt.usermanagementservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fstt.usermanagementservice.entities.User;
import ma.fstt.usermanagementservice.exception.NonceNotFoundException;
import ma.fstt.usermanagementservice.exception.UserNotFoundException;
import ma.fstt.usermanagementservice.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
@Service
@RequiredArgsConstructor
@Slf4j


public class NonceService {

    private final UserRepository userRepository;
    private static final int NONCE_EXPIRATION_MINUTES = 5;

    @Transactional
    public void storeNonce(String wallet, String nonce) {
        User user = userRepository.findByWallet(wallet)
                .orElseThrow(() -> new UserNotFoundException(wallet));

        user.setNonce(nonce);
        user.setNonceExpiration(Instant.now().plus(NONCE_EXPIRATION_MINUTES, ChronoUnit.MINUTES));
        userRepository.save(user);

        log.info("Stored nonce for wallet: {}", wallet);
    }

    public String getNonce(String wallet) {
        User user = userRepository.findByWallet(wallet)
                .orElseThrow(() -> new UserNotFoundException(wallet));

        if (user.getNonce() == null || user.getNonceExpiration() == null ||
                Instant.now().isAfter(user.getNonceExpiration())) {
            log.warn("Nonce expired or not found for wallet: {}", wallet);
            throw new NonceNotFoundException(wallet);
        }


        log.info("Retrieved nonce for wallet: {}", wallet);
        return user.getNonce();
    }

    @Transactional
    public void deleteNonce(String wallet) {
        User user = userRepository.findByWallet(wallet)
                .orElseThrow(() -> new UserNotFoundException(wallet));

        user.setNonce(null);
        user.setNonceExpiration(null);
        userRepository.save(user);

        log.info("Deleted nonce for wallet: {}", wallet);
    }
}
