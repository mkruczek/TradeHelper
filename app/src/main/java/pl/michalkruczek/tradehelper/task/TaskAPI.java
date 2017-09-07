package pl.michalkruczek.tradehelper.task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by mikr on 07/09/17.
 */

public interface TaskAPI {

    @POST("add")
    Call<String> addTask(@Body Task task);

    @GET("all")
    Call<List<Task>> allTask();

    @GET("user/{login}")
    Call<List<Task>> allTaskByUser(@Path("login") String login);

    @GET("{id}")
    Call<Task> singleTask(@Path("id") Long id);

    @PUT("update/{id}")
    Call<String> updateTask(@Path("id") Long id, @Body Task task );

    @DELETE("delete/{id}")
    Call<String> deleteTask(@Path("id") Long id);

}
