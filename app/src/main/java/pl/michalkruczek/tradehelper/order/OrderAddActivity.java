package pl.michalkruczek.tradehelper.order;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.Company;
import pl.michalkruczek.tradehelper.company.CompanyAPI;
import pl.michalkruczek.tradehelper.company.CompanyActivity;
import pl.michalkruczek.tradehelper.login.LoginActivity;
import pl.michalkruczek.tradehelper.mainmenu.MainMenuActivity;
import pl.michalkruczek.tradehelper.product.Product;
import pl.michalkruczek.tradehelper.product.ProductAPI;
import pl.michalkruczek.tradehelper.product.ProductActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderAddActivity extends AppCompatActivity {

    //TODO add quantity on app and server

    private Context context;

    private Spinner addOrder_spinnerCompany;
    private Spinner addOrder_spinnerProduct;
    private EditText addOrder_setQuantity;
    private TextView orderAdd_Save;
    private TextView orderAdd_Cancel;


    private Retrofit companyRetrofit;
    private Retrofit productRetrofit;
    private CompanyAPI companyAPI;
    private ProductAPI productAPI;
    private static List<Company> companyList;
    private static List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_add_activity);

        context = OrderAddActivity.this;

        addOrder_spinnerCompany = (Spinner) findViewById(R.id.addOrder_spinnerCompany);
        addOrder_spinnerProduct = (Spinner) findViewById(R.id.addOrder_spinnerProduct);
        addOrder_setQuantity = (EditText) findViewById(R.id.addOrder_setQuantity);
        orderAdd_Save = (TextView) findViewById(R.id.orderAdd_Save);
        orderAdd_Cancel = (TextView) findViewById(R.id.orderAdd_Cancel);

        final Long productIdFromIntent = getIntent().getLongExtra("product", 0L);


        companyRetrofit = new Retrofit.Builder()
                .baseUrl(CompanyActivity.BASE_COMPANY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        companyAPI = companyRetrofit.create(CompanyAPI.class);
        String userLogin = LoginActivity.user.getLogin();
        Call<List<Company>> companyCall = companyAPI.findByUser(userLogin);
        companyCall.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                companyList = response.body();
                ArrayAdapter<Company> companyArrayAdapter = new ArrayAdapter<Company>(context, android.R.layout.simple_dropdown_item_1line, companyList);
                companyArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                addOrder_spinnerCompany.setAdapter(companyArrayAdapter);

            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {

            }
        });

        productRetrofit = new Retrofit.Builder()
                .baseUrl(ProductActivity.BASE_PRODUCT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = productRetrofit.create(ProductAPI.class);
        Call<List<Product>> productCall = productAPI.listProduct();
        productCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                ArrayAdapter<Product> productArrayAdapter = new ArrayAdapter<Product>(context, android.R.layout.simple_dropdown_item_1line, productList);
                productArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                addOrder_spinnerProduct.setAdapter(productArrayAdapter);

                if (productIdFromIntent > 0) {
                    int position = findProductSpinnerPosition(productList, productIdFromIntent);
                    addOrder_spinnerProduct.setSelection(position);
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        orderAdd_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                Company company = (Company) addOrder_spinnerCompany.getSelectedItem();
                order.setCompanyId(company.getId());
                Product product = (Product) addOrder_spinnerProduct.getSelectedItem();
                order.setProductId(product.getId());
                order.setUserId(LoginActivity.user.getId());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(OrderActivity.BASE_ORDER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                OrderAPI orderAPI = retrofit.create(OrderAPI.class);
                Call<String> call = orderAPI.addOrder(order);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                addOrder_spinnerCompany.setSelection(0);
                addOrder_spinnerProduct.setSelection(0);
                addOrder_setQuantity.setText("");

                Intent intent = new Intent(context, OrderActivity.class);
                context.startActivity(intent);

            }
        });

        orderAdd_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productIdFromIntent > 0) {
                    onBackPressed();
                } else {
                    Intent intent = new Intent(context, OrderActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    public static int findProductSpinnerPosition(List<Product> productList, Long id) {
        int result = 0;
        for (int i = 0; i < productList.size(); i++) {
            Long helpId = productList.get(i).getId();
            if (helpId == id) {
                result = i;
                break;
            }
        }
        return result;
    }

}
