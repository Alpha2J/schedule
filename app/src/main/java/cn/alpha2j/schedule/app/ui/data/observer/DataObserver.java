package cn.alpha2j.schedule.app.ui.data.observer;

import java.io.Serializable;

/**
 * @author alpha
 */
public interface DataObserver extends Serializable {

    void notifyDataAdd();

    void notifyDataDelete();

    void notifyUndoLastDataDelete();
}
