package pl.michalkruczek.tradehelper.task;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.CompanyAPI;
import pl.michalkruczek.tradehelper.company.CompanyActivity;
import pl.michalkruczek.tradehelper.company.CompanyUpdate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mikr on 07/09/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Context context;
    List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_recyclerview, parent, false);

        return new TaskViewHolder(row);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.TaskViewHolder holder, final int position) {

        final Task task = taskList.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(task.getDate());

        holder.date.setText(sdf.format(date));
        holder.name.setText(task.getName());
        holder.msg.setText(task.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View taskAlertDialogLayout = View.inflate(context, R.layout.task_recyclerview_onclick, null);

                final AlertDialog taskAlertDialog = new AlertDialog.Builder(context)
                        .setView(taskAlertDialogLayout)
                        .setTitle(R.string.chose_options)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                TextView taskRV_onClick_update = (TextView) taskAlertDialogLayout.findViewById(R.id.taskRV_onClick_update);
                taskRV_onClick_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TaskUpdate.class);
                        intent.putExtra("taskUpdate", task);
                        context.startActivity(intent);
                        taskAlertDialog.dismiss();
                    }
                });

                TextView taskRV_onClick_delete = (TextView) taskAlertDialogLayout.findViewById(R.id.taskRV_onClick_delete);
                taskRV_onClick_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog deleteWarning = new AlertDialog.Builder(context)
                                .setTitle("You want delete task!")
                                .setMessage("Do You want continue?")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(TaskActivity.BASE_TASK_URL)
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        TaskAPI taskAPI = retrofit.create(TaskAPI.class);
                                        Call<String> call = taskAPI.deleteTask(task.getId());

                                        call.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {

                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {

                                            }
                                        });
                                        taskList.remove(position);
                                        notifyDataSetChanged();
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();

                        taskAlertDialog.dismiss();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView name;
        TextView msg;

        public TaskViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date);
            name = (TextView) itemView.findViewById(R.id.name);
            msg = (TextView) itemView.findViewById(R.id.msg);
        }
    }
}
