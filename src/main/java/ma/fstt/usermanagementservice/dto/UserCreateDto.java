package ma.fstt.usermanagementservice.dto;

import lombok.Data;

@Data
public class UserCreateDto {

    private String wallet;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String description;

    private Double targetRent;
    private Integer minTotalRooms;
    private Double targetSqft;
    private Double searchLatitude;
    private Double searchLongitude;
    private String preferredPropertyType;
    private String preferredRentalType;


}
