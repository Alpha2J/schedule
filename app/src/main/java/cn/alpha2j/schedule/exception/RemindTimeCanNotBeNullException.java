package cn.alpha2j.schedule.exception;

/**
 * @author alpha
 */
public class RemindTimeCanNotBeNullException extends RuntimeException {

    public RemindTimeCanNotBeNullException() {

    }

    public RemindTimeCanNotBeNullException(String message) {
        super(message);
    }
}
