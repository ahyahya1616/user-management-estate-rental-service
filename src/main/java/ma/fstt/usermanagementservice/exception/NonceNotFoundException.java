package ma.fstt.usermanagementservice.exception;

public class NonceNotFoundException extends RuntimeException {
    public NonceNotFoundException(String wallet) {
        super("Nonce not found or expired for wallet: " + wallet);
    }
}
