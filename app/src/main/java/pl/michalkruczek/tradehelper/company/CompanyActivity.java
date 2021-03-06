package pl.michalkruczek.tradehelper.company;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.login.LoginActivity;
import pl.michalkruczek.tradehelper.mainmenu.MainMenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyActivity extends AppCompatActivity {

    public static final String BASE_COMPANY_URL = "http://80.211.196.76:8081/company/";

    private Context context;

    private Retrofit retrofit;
    private CompanyAPI companyAPI;
    public static List<Company> companyList;

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private CompanyAdapter companyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_activity);

        context = CompanyActivity.this;

        FloatingActionButton addCompanyFab = (FloatingActionButton) findViewById(R.id.addCompanyFab);
        addCompanyFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CompanyAdd.class);
                context.startActivity(intent);
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_COMPANY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        companyAPI = retrofit.create(CompanyAPI.class);
        final Call<List<Company>> call = companyAPI.findByUser(LoginActivity.user.getLogin());

        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {

                companyList = response.body();

                recyclerView = (RecyclerView) findViewById(R.id.companyRV);
                llm = new LinearLayoutManager(context);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setLayoutManager(llm);
                companyAdapter = new CompanyAdapter(context, companyList);
                recyclerView.setAdapter(companyAdapter);

            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainMenuActivity.class);
        context.startActivity(intent);
    }
}

