package pl.michalkruczek.tradehelper.product;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.michalkruczek.tradehelper.MyHelpClass;
import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.Company;
import pl.michalkruczek.tradehelper.company.CompanyAPI;
import pl.michalkruczek.tradehelper.company.CompanyActivity;
import pl.michalkruczek.tradehelper.company.CompanyAdapter;
import pl.michalkruczek.tradehelper.mainmenu.MainMenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductActivity extends AppCompatActivity {

    //    public static final String BASE_PRODUCT_URL = "http://127.0.0.1:8081/product/"; //when offline
    public static final String BASE_PRODUCT_URL = "http://80.211.196.76:8081/product/"; //when online

    private Context context;

    private Retrofit retrofit;
    private ProductAPI productAPI;
    public static List<Product> productList; //TODO public & static do testów offline

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);
        context = ProductActivity.this;

        FloatingActionButton addProductFab = (FloatingActionButton) findViewById(R.id.addProductFab);
        addProductFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductAdd.class);
                context.startActivity(intent);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_PRODUCT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productAPI = retrofit.create(ProductAPI.class);
        final Call<List<Product>> call = productAPI.listProduct();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                productList = response.body();

                recyclerView = (RecyclerView) findViewById(R.id.productRV);
                llm = new LinearLayoutManager(context);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setLayoutManager(llm);
                productAdapter = new ProductAdapter(context, productList);
                recyclerView.setAdapter(productAdapter);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        if (productList == null) {//TODO - delete "if" statement when I be online

            productList = new ArrayList<>();
            productList.add(new Product(1L, "Marchew", "1 Jakiś sensowny opis dla tego warzywa/owocu. Pomyśl że jest ok :)", BigDecimal.valueOf(1.11)));
            productList.add(new Product(2L, "Jabłka", "2 Jakiś sensowny opis dla tego warzywa/owocu. Pomyśl że jest ok :)", BigDecimal.valueOf(2.22)));
            productList.add(new Product(3L, "Rzodkiew", "3 Jakiś sensowny opis dla tego warzywa/owocu. Pomyśl że jest ok :)", BigDecimal.valueOf(3.33)));
            productList.add(new Product(4L, "Rozmaryn", "4 Jakiś sensowny opis dla tego warzywa/owocu. Pomyśl że jest ok :)", BigDecimal.valueOf(4.44)));
            productList.add(new Product(5L, "Arbuz", "5 Jakiś sensowny opis dla tego warzywa/owocu. Pomyśl że jest ok :)", BigDecimal.valueOf(5.55)));
            productList.add(new Product(6L, "Cytryna", "6 Jakiś sensowny opis dla tego warzywa/owocu. Pomyśl że jest ok :)", BigDecimal.valueOf(6.66)));
            productList.add(new Product(7L, "Pieprz", "7 Jakiś sensowny opis dla tego warzywa/owocu. Pomyśl że jest ok :)", BigDecimal.valueOf(7.77)));
            productList.add(new Product(8L, "Winogrona", "8 Jakiś sensowny opis dla tego warzywa/owocu. Pomyśl że jest ok :)", BigDecimal.valueOf(8.88)));
            productList.add(new Product(9L, "Pomarańcze", "9 Jakiś sensowny opis dla tego warzywa/owocu. Pomyśl że jest ok :)", BigDecimal.valueOf(9.99)));
        }
        recyclerView = (RecyclerView) findViewById(R.id.productRV);
        llm = new LinearLayoutManager(context);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(llm);
        productAdapter = new ProductAdapter(context, productList);
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainMenuActivity.class);
        context.startActivity(intent);
    }
}