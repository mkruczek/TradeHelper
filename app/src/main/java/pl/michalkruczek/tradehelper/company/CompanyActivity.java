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
import pl.michalkruczek.tradehelper.mainmenu.MainMenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyActivity extends AppCompatActivity {

    //public static final String BASE_COMPANY_URL = "http://127.0.0.1:8081/company/"; //when offline
    public static final String BASE_COMPANY_URL = "http://80.211.196.76:8081/company/"; // when online

    private Context context;

    private Retrofit retrofit;
    private CompanyAPI companyAPI;
    private List<Company> companyList;

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
        final Call<List<Company>> call = companyAPI.listCompany();

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

        if (companyList == null) {//TODO - delete "if" statement when I be online

            companyList = new ArrayList<>(Arrays.asList(
                    new Company(1L, "Wiewiórki", "111-111-11-11", "ul.Rolna 20c/1 40-555 Katowie", "123-100-68-54", "krojac@wp.pl"),
                    new Company(2L, "Niedźwiadki", "222-222-22-22", "ul.Bażantów 43 40-555 Katowie", "456-100-68-54", "kruczek@wp.pl"),
                    new Company(3L, "Łośki", "333-333-33-33", "ul.Moniuszki 7 40-555 Szczecin", "789-100-68-54", "info@contakt.pl"),
                    new Company(4L, "Żuberki", "444-444-44-44", "ul.Rolna 20c/1 40-555 Warszawa", "987-100-68-54", "krojac@zuberki.pl"),
                    new Company(5L, "Porzeczki", "555-555-55-55", "ul.Jasielska 22 40-555 Rzeszów", "654-100-68-54", "krojac@wp.pl"),
                    new Company(6L, "Porzeczki", "666-666-66-66", "ul.Jasielska 22 40-555 Rzeszów", "321-100-68-54", "krojac@wp.pl"),
                    new Company(7L, "Porzeczki", "666-666-66-66", "ul.Jasielska 22 40-555 Rzeszów", "321-100-68-54", "krojac@wp.pl"),
                    new Company(8L, "Porzeczki", "666-666-66-66", "ul.Jasielska 22 40-555 Rzeszów", "321-100-68-54", "krojac@wp.pl"),
                    new Company(9L, "Porzeczki", "666-666-66-66", "ul.Jasielska 22 40-555 Rzeszów", "321-100-68-54", "krojac@wp.pl")
            ));
            recyclerView = (RecyclerView) findViewById(R.id.companyRV);
            llm = new LinearLayoutManager(context);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setLayoutManager(llm);
            companyAdapter = new CompanyAdapter(context, companyList);
            recyclerView.setAdapter(companyAdapter);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainMenuActivity.class);
        context.startActivity(intent);
    }
}

