package ma.fstt.usermanagementservice.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String wallet) {
        super("User already exists with wallet: " + wallet);
    }
}