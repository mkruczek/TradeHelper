package pl.michalkruczek.tradehelper.task;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.CompanyAPI;
import pl.michalkruczek.tradehelper.company.CompanyActivity;
import pl.michalkruczek.tradehelper.company.CompanyAdd;
import pl.michalkruczek.tradehelper.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskAdd extends AppCompatActivity {

    private Context context;

    private static Long dateFromDataPicker;

    private EditText taskAdd_title;
    private EditText taskAdd_date;
    private EditText taskAdd_msg;
    private TextView taskAdd_Save;
    private TextView taskAdd_Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_add_activity);

        context = TaskAdd.this;


        taskAdd_title = (EditText) findViewById(R.id.taskAdd_title);
        taskAdd_date = (EditText) findViewById(R.id.taskAdd_date);
        taskAdd_msg = (EditText) findViewById(R.id.taskAdd_msg);
        taskAdd_Save = (TextView) findViewById(R.id.taskAdd_Save);
        taskAdd_Cancel = (TextView) findViewById(R.id.taskAdd_Cancel);



        taskAdd_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task();

                task.setName(taskAdd_title.getText().toString());
                task.setDescription(taskAdd_msg.getText().toString());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(taskAdd_date.getText().toString());
                    task.setDate(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                task.setDone(false);
                task.setUserId(LoginActivity.user.getId());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(TaskActivity.BASE_TASK_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TaskAPI taskAPI = retrofit.create(TaskAPI.class);
                Call<String> call = taskAPI.addTask(task);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                taskAdd_title.setText("");
                taskAdd_date.setText("");
                taskAdd_msg.setText("");

                Toast.makeText(context, "Add Task", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TaskActivity.class);
                context.startActivity(intent);

            }
        });

        taskAdd_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void lunchDataPicker(View view) {

        LinearLayout ll = new LinearLayout(context);
        final DatePicker datePicker = new DatePicker(context);
        ll.addView(datePicker);

        AlertDialog ad = new AlertDialog.Builder(context)
                .setView(ll)
                .setTitle("Chose Date")
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int yer = (datePicker.getYear() - 1900);
                        int month = datePicker.getMonth();
                        int day = datePicker.getDayOfMonth();
                        dateFromDataPicker = new Date(yer, month, day).getTime();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        taskAdd_date.setText(sdf.format(new Date(dateFromDataPicker)));

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();

    }
}
