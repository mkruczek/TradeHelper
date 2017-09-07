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
import pl.michalkruczek.tradehelper.company.CompanyActivity;
import pl.michalkruczek.tradehelper.company.CompanyUpdate;
import pl.michalkruczek.tradehelper.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskUpdate extends AppCompatActivity {

    private static Long dateFromDataPicker;

    private Context context;

    private EditText taskUpdate_title;
    private EditText taskUpdate_date;
    private EditText taskUpdate_msg;
    private TextView taskUpdate_Save;
    private TextView taskUpdate_Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_update_activity);

        context = TaskUpdate.this;

        taskUpdate_title = (EditText) findViewById(R.id.taskUpdate_title);
        taskUpdate_date = (EditText) findViewById(R.id.taskUpdate_date);
        taskUpdate_msg = (EditText) findViewById(R.id.taskUpdate_msg);
        taskUpdate_Save = (TextView) findViewById(R.id.taskUpdate_Save);
        taskUpdate_Cancel = (TextView) findViewById(R.id.taskUpdate_Cancel);

        final Task taskUpdate = (Task) getIntent().getSerializableExtra("taskUpdate");

        taskUpdate_title.setText(taskUpdate.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(taskUpdate.getDate());
        taskUpdate_date.setText(sdf.format(date));
        taskUpdate_msg.setText(taskUpdate.getDescription());

        taskUpdate_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task();

                task.setName(taskUpdate_title.getText().toString());
                task.setDescription(taskUpdate_msg.getText().toString());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(taskUpdate_date.getText().toString());
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
                Call<String> call = taskAPI.updateTask(taskUpdate.getId(), task);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                Toast.makeText(context, "Update Task", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TaskActivity.class);
                context.startActivity(intent);
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
                        taskUpdate_date.setText(sdf.format(new Date(dateFromDataPicker)));

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
