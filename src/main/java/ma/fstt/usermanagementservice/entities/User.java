package ma.fstt.usermanagementservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Instant;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 42)
    private String wallet;

    @Column(length = 100)
    private String username;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 200)
    private String email;

    @Column(length = 200)
    private String role;

    private String description;

    //data added by 3ezi for the ai recommendation engin

    private Double targetRent;
    private Integer minTotalRooms;
    private Double targetSqft;
    private Double searchLatitude;
    private Double searchLongitude;
    private String preferredPropertyType;
    private String preferredRentalType;


    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    // Nonce temporaire pour l'authentification MetaMask
    @Column(length = 100)
    private String nonce;

    @Column(name = "nonce_expiration")
    private Instant nonceExpiration;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}