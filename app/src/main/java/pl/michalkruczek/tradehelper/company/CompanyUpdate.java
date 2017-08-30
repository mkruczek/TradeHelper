package pl.michalkruczek.tradehelper.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pl.michalkruczek.tradehelper.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyUpdate extends AppCompatActivity {

    private EditText companyUpdate_name;
    private EditText companyUpdate_nip;
    private EditText companyUpdate_address;
    private EditText companyUpdate_phone;
    private EditText companyUpdate_email;

    private TextView companyUpdate_Save;
    private TextView companyUpdate_Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_update_activity);

        companyUpdate_name = (EditText) findViewById(R.id.companyUpdate_name);
        companyUpdate_nip = (EditText) findViewById(R.id.companyUpdate_nip);
        companyUpdate_address = (EditText) findViewById(R.id.companyUpdate_address);
        companyUpdate_phone = (EditText) findViewById(R.id.companyUpdate_phone);
        companyUpdate_email = (EditText) findViewById(R.id.companyUpdate_email);

        companyUpdate_Save = (TextView) findViewById(R.id.companyUpdate_Save);
        companyUpdate_Cancel = (TextView) findViewById(R.id.companyUpdate_Cancel);

        final Company companyUpdate = (Company) getIntent().getSerializableExtra("companyUpdate");

        companyUpdate_name.setText(companyUpdate.getName());
        companyUpdate_nip.setText(companyUpdate.getNip());
        companyUpdate_address.setText(companyUpdate.getAddress());
        companyUpdate_phone.setText(companyUpdate.getPhone());
        companyUpdate_email.setText(companyUpdate.getEmail());

        companyUpdate_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Company newCompany = new Company();
                newCompany.setName(companyUpdate_name.getText().toString());
                newCompany.setNip(companyUpdate_nip.getText().toString());
                newCompany.setAddress(companyUpdate_address.getText().toString());
                newCompany.setPhone(companyUpdate_phone.getText().toString());
                newCompany.setEmail(companyUpdate_email.getText().toString());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(CompanyActivity.BASE_COMPANY_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CompanyAPI companyAPI = retrofit.create(CompanyAPI.class);
                Call<String> call = companyAPI.updateCompany(companyUpdate.getId(), newCompany);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        //TODO retrofit po @POST and @PUT nie wchodzi do onResponse!!
                        // metoeda wywalona poza onRespond tez działa
                        //można wynieść poza tą metodę, tam gdzie teraz jest ingo ze uzywac gdy nie ma sieci
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                //TODO spawdzić co z retrofitem i jego brkaiem egzekucji onResponse
                Toast.makeText(CompanyUpdate.this, "Update Company", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CompanyUpdate.this, CompanyActivity.class);
                CompanyUpdate.this.startActivity(intent);

            }
        });

        companyUpdate_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}
