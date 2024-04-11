package exception;

public class IllegalTextException extends RuntimeException{
    public IllegalTextException() {
    }

    public IllegalTextException(String message) {
        super(message);
    }

    public IllegalTextException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalTextException(Throwable cause) {
        super(cause);
    }

    public IllegalTextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
