package cn.alpha2j.schedule.app.ui.data;

import java.io.Serializable;

/**
 * @author alpha
 */
public abstract class AbstractDataProvider implements Serializable {

    public static abstract class Data implements Serializable {

        public abstract long getId();

        public abstract String getText();

        public abstract void setPinned(boolean pinned);

        public abstract boolean isPinned();
    }

    public abstract int getCount();

    public abstract Data getItem(int index);

    public abstract void removeItem(int position);

    public abstract void moveItem(int fromPosition, int toPosition);

    public abstract void swapItem(int fromPosition, int toPosition);

    public abstract int undoLastRemoval();

    public abstract Data getLastRemoval();
}
