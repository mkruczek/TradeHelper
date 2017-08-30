package pl.michalkruczek.tradehelper.company;

import java.util.List;

import okhttp3.ResponseBody;
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

public interface CompanyAPI {

    @GET("{id}")
    Call<Company> singleCompany (@Path("id") long id);

    @GET("all")
    Call<List<Company>> listCompany();

    @POST("add")
    Call<String> addCompany(@Body Company company);

    @PUT("updata/{id}")
    Call<String> updateCompany(@Path("id") long id, @Body Company company);

    @DELETE("delete/{id}")
    Call<String> deleteCompany(@Path("id") long id);

}
