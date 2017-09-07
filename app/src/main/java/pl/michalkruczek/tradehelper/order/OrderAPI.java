package pl.michalkruczek.tradehelper.order;

import java.util.List;

import pl.michalkruczek.tradehelper.login.LoginActivity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mikr on 27/08/17.
 */

public interface OrderAPI {

    @GET("{id}")
    Call<Order> singleOrder(@Path("id") Long id);

    @GET("all")
    Call<List<Order>> listOrder();

    @GET("company/{id}")
    Call<List<Order>> findByComapnyId(@Path("id") Long id);

    @POST("add")
    Call<String> addOrder(@Body Order order);

    @GET("user/{id}")
    Call<List<Order>> findByUserId(@Path("id") Long id);

    @DELETE("delete/{id}")
    Call<String> deleteOrder(@Path("id") Long id);


}
