package pl.michalkruczek.tradehelper.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.Company;
import pl.michalkruczek.tradehelper.company.CompanyAPI;
import pl.michalkruczek.tradehelper.company.CompanyActivity;
import pl.michalkruczek.tradehelper.mainmenu.MainMenuActivity;
import pl.michalkruczek.tradehelper.product.Product;
import pl.michalkruczek.tradehelper.product.ProductAPI;
import pl.michalkruczek.tradehelper.product.ProductActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    public static final String BASE_USER_URL = "http://80.211.196.76:8081/user/";
    public static final String USER_NAME = "userName";
    public static final String USER_SURNAME = "userSurname";
    public static final String USER_ID = "userId";

    public static User user;
    public static List<Product> productList;
    public static List<Company> companyList;

    private Context context;


    private EditText login_login;
    private EditText login_pass;
    private LinearLayout login_go;

    private Retrofit retrofit;
    private UserAPI userAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = LoginActivity.this;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_USER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);

        login_login = (EditText) findViewById(R.id.login_login);
        login_pass = (EditText) findViewById(R.id.login_pass);
        login_go = (LinearLayout) findViewById(R.id.login_go);

        //TODO wywalic
        login_login.setText("mikr");
        login_pass.setText("qwerty");


        Retrofit retrofitProduct = new Retrofit.Builder()
                .baseUrl(ProductActivity.BASE_PRODUCT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductAPI productAPI = retrofitProduct.create(ProductAPI.class);
        final Call<List<Product>> callProductList = productAPI.listProduct();
        callProductList.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        Retrofit retrofitCompany = new Retrofit.Builder()
                .baseUrl(CompanyActivity.BASE_COMPANY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CompanyAPI companyAPI = retrofitCompany.create(CompanyAPI.class);
        final Call<List<Company>> callCompanyList = companyAPI.listCompany();
        callCompanyList.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                companyList = response.body();
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {

            }
        });

        login_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String login = login_login.getText().toString();
                final String pass = login_pass.getText().toString();

                Call<User> call = userAPI.userByLogin(login);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();

                        if (user != null && user.getLogin().equals(login) && user.getPassword().equals(pass)) {
                            Intent intent = new Intent(context, MainMenuActivity.class);
                            intent.putExtra("user", user);
                            context.startActivity(intent);
                        } else {
                            AlertDialog ad = new AlertDialog.Builder(context)
                                    .setTitle("Access Denied")
                                    .setMessage("Please check your login and password.\nThen try again.")
                                    .show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }

                });

            }
        });

    }
}
