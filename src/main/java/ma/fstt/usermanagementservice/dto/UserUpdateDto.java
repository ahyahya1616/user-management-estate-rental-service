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
}
