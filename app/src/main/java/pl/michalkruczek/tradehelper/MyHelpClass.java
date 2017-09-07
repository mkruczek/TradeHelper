package pl.michalkruczek.tradehelper;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import pl.michalkruczek.tradehelper.company.Company;
import pl.michalkruczek.tradehelper.company.CompanyAPI;
import pl.michalkruczek.tradehelper.company.CompanyActivity;
import pl.michalkruczek.tradehelper.login.LoginActivity;
import pl.michalkruczek.tradehelper.login.User;
import pl.michalkruczek.tradehelper.login.UserAPI;
import pl.michalkruczek.tradehelper.order.Order;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mikr on 27/08/17.
 */

public class MyHelpClass {

    static Boolean result = false;
    static User user;

    public static void main(String[] args) {



    }

    private static final boolean LOG_VALUE = true;

    public static void myLogd(String msg) {
        if (LOG_VALUE) {
            Log.d("kru", msg);
        }
    }

    public static List<Order> listFromJSON(String JSON) {
        List<Order> result = new ArrayList<>();
        JSON = JSON.substring(1);
        StringTokenizer st = new StringTokenizer(JSON, "{");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            str = "{" + str.substring(0, str.length() - 1);

            Gson gson = new Gson();
            Order order = gson.fromJson(str, Order.class);

            result.add(order);
        }
        return result;
    }

    public static List<Company> testCompnyList() {

        final List<Company>[] companyList = new List[]{new ArrayList<>()};

//        String all = "[{\"id\":1,\"companyId\":3,\"productId\":1},{\"id\":2,\"companyId\":3,\"productId\":2},{\"id\":3,\"companyId\":3,\"productId\":1},{\"id\":4,\"companyId\":2,\"productId\":1},{\"id\":5,\"companyId\":2,\"productId\":2},{\"id\":6,\"companyId\":1,\"productId\":2},{\"id\":7,\"companyId\":1,\"productId\":1}]";
//        final String singleOrderl = "{\"id\":1,\"companyId\":3,\"productId\":1}";
//        String company3 = "[{\"id\":1,\"companyId\":3,\"productId\":1},{\"id\":2,\"companyId\":3,\"productId\":2},{\"id\":3,\"companyId\":3,\"productId\":1}]";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CompanyActivity.BASE_COMPANY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        Company company = new Company("Wiewiórki", "634-100-68-40","Rolna 20c/1 Kato", "509-353-186", "krojac@wp.pl");
//        Company updateCompany = new Company("Nietoperki", "666-666-66-66","Jaśliska 138", "503438831", "kruczek86@gmail.com");

        CompanyAPI companyAPI = retrofit.create(CompanyAPI.class);
        Call<List<Company>> call = companyAPI.listCompany();

        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {

                companyList[0] = response.body();

                for (int i = 0; i < response.body().size(); i++) {
                    System.out.println(response.body().get(i));
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {

            }
        });


        return companyList[0];


    }


}
