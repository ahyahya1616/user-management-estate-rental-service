package ma.fstt.usermanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.fstt.usermanagementservice.exception.ErrorCode;

@Data
@AllArgsConstructor
public class ApiError {
    private ErrorCode error;
    private String message;
    private int status;
}
