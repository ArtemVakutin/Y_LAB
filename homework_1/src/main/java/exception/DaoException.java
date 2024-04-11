package exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DaoException extends RuntimeException{
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
        log.debug("DAO Exception is : " + message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
