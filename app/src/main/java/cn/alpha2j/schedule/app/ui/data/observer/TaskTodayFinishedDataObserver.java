package cn.alpha2j.schedule.app.ui.data.observer;

/**
 * @author alpha
 */
public class TaskTodayFinishedDataObserver extends AbstractTaskTodayDataObserver {

    public TaskTodayFinishedDataObserver(RecyclerViewAdapterGetter recyclerViewAdapterGetter) {
        super(recyclerViewAdapterGetter);
    }

    @Override
    public int getType() {
        return TaskTodayDataObserverType.TYPE_FINISHED;
    }
}
