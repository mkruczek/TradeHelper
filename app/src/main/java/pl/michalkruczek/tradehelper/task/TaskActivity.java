package pl.michalkruczek.tradehelper.task;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.Company;
import pl.michalkruczek.tradehelper.company.CompanyAPI;
import pl.michalkruczek.tradehelper.company.CompanyActivity;
import pl.michalkruczek.tradehelper.company.CompanyAdapter;
import pl.michalkruczek.tradehelper.company.CompanyAdd;
import pl.michalkruczek.tradehelper.login.LoginActivity;
import pl.michalkruczek.tradehelper.mainmenu.MainMenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskActivity extends AppCompatActivity {

    public static final String BASE_TASK_URL = "http://80.211.196.76:8081/task/";

    private Context context;

    private Retrofit retrofit;
    private TaskAPI taskAPI;
    public static List<Task> taskList;

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);

        context = TaskActivity.this;

        FloatingActionButton addTaskFab = (FloatingActionButton) findViewById(R.id.addTaskFab);
        addTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskAdd.class);
                context.startActivity(intent);
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_TASK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        taskAPI = retrofit.create(TaskAPI.class);
        final Call<List<Task>> call = taskAPI.allTaskByUser(LoginActivity.user.getLogin());

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                taskList = response.body();

                Collections.sort(taskList, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });

                recyclerView = (RecyclerView) findViewById(R.id.taskRV);
                llm = new LinearLayoutManager(context);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setLayoutManager(llm);
                taskAdapter = new TaskAdapter(context, taskList);
                recyclerView.setAdapter(taskAdapter);
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainMenuActivity.class);
        context.startActivity(intent);
    }
}
