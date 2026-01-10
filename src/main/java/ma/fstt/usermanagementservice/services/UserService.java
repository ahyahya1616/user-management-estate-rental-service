package ma.fstt.usermanagementservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.fstt.usermanagementservice.dto.UserCreateDto;
import ma.fstt.usermanagementservice.dto.UserResponseDto;
import ma.fstt.usermanagementservice.dto.UserUpdateDto;
import ma.fstt.usermanagementservice.entities.User;
import ma.fstt.usermanagementservice.exception.UserAlreadyExistsException;
import ma.fstt.usermanagementservice.exception.UserNotFoundException;
import ma.fstt.usermanagementservice.mapper.UserMapper;
import ma.fstt.usermanagementservice.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Récupérer tous les utilisateurs (Response DTO)
     */
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer un utilisateur par wallet
     */
    public UserResponseDto getUserByWallet(String wallet) {
        User user = userRepository.findByWallet(wallet)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with this wallet : "+wallet));
        return userMapper.toResponseDto(user);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with this id : "+id));
        return userMapper.toResponseDto(user);
    }


    /**
     * Créer un nouvel utilisateur
     */
    @Transactional
    public UserResponseDto createUser(UserCreateDto dto) {

        log.info("[REGISTER] Création utilisateur via DTO");

        if (dto == null) {
            throw new IllegalArgumentException("UserCreateDto is null");
        }

        boolean exists = userRepository.existsByWallet(dto.getWallet());
        if (exists) {
            throw new UserAlreadyExistsException(dto.getWallet());
        }

        // Conversion DTO → Entity
        User user = userMapper.toEntity(dto);
        user.setRole("ROLE_USER");
        user.setCreatedAt(Instant.now());
        user.setEnabled(true);

        User saved = userRepository.save(user);
        log.info("[REGISTER] Utilisateur créé avec succès : id={}, wallet={}", saved.getId(), saved.getWallet());

        return userMapper.toResponseDto(saved);
    }

    /**
     * Mettre à jour un utilisateur
     */
    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userMapper.updateEntityFromDto(dto, user);
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);


        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    /**
     * Supprimer un utilisateur
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
