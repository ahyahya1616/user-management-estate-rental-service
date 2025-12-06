package ma.fstt.usermanagementservice.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String wallet) {
        super("User not found with wallet: " + wallet);
    }
}
