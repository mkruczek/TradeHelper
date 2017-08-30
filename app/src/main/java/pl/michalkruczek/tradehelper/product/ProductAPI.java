package pl.michalkruczek.tradehelper.product;

import java.util.List;

import pl.michalkruczek.tradehelper.company.Company;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by mikr on 27/08/17.
 */

public interface ProductAPI {

    //TODO - check all request

    @GET("{id}")
    Call<Product> singleProduct (@Path("id") long id);

    @GET("all")
    Call<List<Product>> listProduct();

    @POST("add")
    Call<String> addProduct(@Body Product product);

    @PUT("updata/{id}")
    Call<String> updataProduct(@Path("id") long id, @Body Product product);

    @DELETE("delete/{id}")
    Call<String> deleteProduct(@Path("id") long id);
}
