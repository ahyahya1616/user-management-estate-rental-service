package ma.fstt.usermanagementservice.dto;

import lombok.Data;

@Data
public class UserUpdateDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private Boolean enabled;


    private Double targetRent;
    private Integer minTotalRooms;
    private Double targetSqft;
    private Double searchLatitude;
    private Double searchLongitude;
    private String preferredPropertyType;
    private String preferredRentalType;
}
