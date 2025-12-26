package ma.fstt.usermanagementservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String wallet;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String description;
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;

    private Double targetRent;
    private Integer minTotalRooms;
    private Double targetSqft;
    private Double searchLatitude;
    private Double searchLongitude;
    private String preferredPropertyType;
    private String preferredRentalType;
}
