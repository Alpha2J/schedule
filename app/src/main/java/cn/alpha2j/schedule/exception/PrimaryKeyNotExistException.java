package cn.alpha2j.schedule.exception;

/**
 * @author alpha
 */
public class PrimaryKeyNotExistException extends RuntimeException {

    public PrimaryKeyNotExistException() {
    }

    public PrimaryKeyNotExistException(String message) {
        super(message);
    }
}
