package cn.alpha2j.schedule.exception;

/**
 * 抛出该异常表明建造器中没有建造任何东西
 *
 * @author alpha
 */
public class NothingWasBuildException extends RuntimeException {
    public NothingWasBuildException() {

    }

    public NothingWasBuildException(String message) {
        super(message);
    }
}
