package pl.michalkruczek.tradehelper.order;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.Company;
import pl.michalkruczek.tradehelper.company.CompanyAdapter;
import pl.michalkruczek.tradehelper.login.LoginActivity;
import pl.michalkruczek.tradehelper.mainmenu.MainMenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity {

    public static final String BASE_ORDER_URL = "http://80.211.196.76:8081/order/";

    private Context context;

    private Long userId;

    private Retrofit retrofit;
    private OrderAPI orderAPI;
    private List<Order> orderList;

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private OrderAdapter orderAdapter;

    private FloatingActionButton addOrderFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);

        userId = LoginActivity.user.getId();
        final Long companyId = getIntent().getLongExtra("company", 0L);

        context = OrderActivity.this;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_ORDER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        orderAPI = retrofit.create(OrderAPI.class);
        final Call<List<Order>> call = orderAPI.findByUserId(userId);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orderList = response.body();

                recyclerView = (RecyclerView) findViewById(R.id.orderRV);
                llm = new LinearLayoutManager(context);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setLayoutManager(llm);

                if (companyId > 0) {
                    List<Order> singleComapnyOrders = new ArrayList<Order>();

                    for (Order order : orderList) {
                        if (order.getCompanyId() == companyId) {
                            singleComapnyOrders.add(order);
                        }
                    }

                    orderAdapter = new OrderAdapter(context, singleComapnyOrders);
                    recyclerView.setAdapter(orderAdapter);

                } else {
                    orderAdapter = new OrderAdapter(context, orderList);
                    recyclerView.setAdapter(orderAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });

        addOrderFab = (FloatingActionButton) findViewById(R.id.addOrderFab);
        addOrderFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderAddActivity.class);
                context.startActivity(intent);
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
