package cn.alpha2j.schedule.app.ui.data.generator;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cn.alpha2j.schedule.app.ui.data.decorator.TodayFinishedTaskDataProviderPersistenceDecorator;
import cn.alpha2j.schedule.app.ui.data.decorator.TodayUnfinishedTaskDataProviderPersistenceDecorator;
import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.exception.DataProviderTypeNotSupportedException;

/**
 * @author alpha
 */
public abstract class AbstractTaskDataProviderGenerator implements DataProviderGenerator {

    @NonNull
    @Override
    public TaskDataProvider generate() {

//        实际返回的使他们的装饰器类型
        TaskDataProvider taskDataProvider = null;

        TaskService taskService = TaskServiceImpl.getInstance();
        switch (getTaskDataProviderType()) {
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED:
                List<Task> unfinishedTasks = taskService.findAllUnfinishedForToday();
                taskDataProvider = new TodayUnfinishedTaskDataProviderPersistenceDecorator(new TaskDataProvider(convert(unfinishedTasks)));

                break;
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                List<Task> finishedTasks = taskService.findAllFinishedForToday();
                taskDataProvider = new TodayFinishedTaskDataProviderPersistenceDecorator(new TaskDataProvider(convert(finishedTasks)));

                break;
            default:
        }

        if (taskDataProvider == null) {
            throw new DataProviderTypeNotSupportedException("不支持该DataProviderType");
        }

        return taskDataProvider;
    }

    /**
     * 获取需要生成的DataProvider类型(不同类型持有的数据不同)
     *
     * @return TaskDataProvider.TaskDataProviderType 里面的类型
     */
    public abstract String getTaskDataProviderType();

    private List<TaskDataProvider.TaskData> convert(List<Task> tasks) {

        List<TaskDataProvider.TaskData> taskDatas = new ArrayList<>();
        for (Task task : tasks){
            TaskDataProvider.TaskData taskData = new TaskDataProvider.TaskData(task, false);
            taskDatas.add(taskData);
        }

        return taskDatas;
    }
}
