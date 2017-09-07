package pl.michalkruczek.tradehelper.login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mikr on 03/09/17.
 */

public interface UserAPI {

    @GET("checkLogin/login={login}&pass={pass}")
    Call<Boolean> checkAccess(@Path("login") String login, @Path("pass") String pass);

    @GET("all")
    Call<List<User>> allUser();

    @GET("login={login}")
    Call<User> userByLogin(@Path("login") String login);

}
