package cn.alpha2j.schedule.app.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;


/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        TaskService taskService = TaskServiceImpl.getInstance();
        Button saveOrUpdateBtn = findViewById(R.id.btn_atp_saveOrUpdate);
        saveOrUpdateBtn.setOnClickListener(view -> {
            Task task = new Task();
            task.setId(50L);
            task.setDone(true);
            long id = taskService.addOrUpdateTask(task);
            Toast.makeText(this, "id: " + id, Toast.LENGTH_SHORT).show();
        });

        Button update = findViewById(R.id.btn_atp_update);
        update.setOnClickListener(view -> {
            Task task = new Task();
            task.setId(2L);
            task.setDone(true);
            taskService.setUnDone(task);
        });

        Button addWithId = findViewById(R.id.btn_atp_add_with_id);
        addWithId.setOnClickListener(view -> {
            Task task = new Task();
            task.setId(100L);
            taskService.addTask(task);
        });

    }
}
