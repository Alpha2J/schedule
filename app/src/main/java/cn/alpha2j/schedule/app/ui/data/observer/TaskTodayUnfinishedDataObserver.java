package cn.alpha2j.schedule.app.ui.data.observer;

/**
 * @author alpha
 */
public class TaskTodayUnfinishedDataObserver extends AbstractTaskTodayDataObserver {

    public TaskTodayUnfinishedDataObserver(RecyclerViewAdapterGetter recyclerViewAdapterGetter) {
        super(recyclerViewAdapterGetter);
    }

    @Override
    public int getType() {
        return TaskTodayDataObserverType.TYPE_UNFINISHED;
    }
}
