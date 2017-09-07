package pl.michalkruczek.tradehelper.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyAdd extends AppCompatActivity {

    private EditText companyAdd_name;
    private EditText companyAdd_nip;
    private EditText companyAdd_address;
    private EditText companyAdd_phone;
    private EditText companyAdd_email;

    private TextView companyAdd_Save;
    private TextView companyAdd_Cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_add_activity);


        companyAdd_name = (EditText) findViewById(R.id.companyAdd_name);
        companyAdd_nip = (EditText) findViewById(R.id.companyAdd_nip);
        companyAdd_address = (EditText) findViewById(R.id.companyAdd_address);
        companyAdd_phone = (EditText) findViewById(R.id.companyAdd_phone);
        companyAdd_email = (EditText) findViewById(R.id.companyAdd_email);

        companyAdd_Save = (TextView) findViewById(R.id.companyAdd_Save);
        companyAdd_Cancel = (TextView) findViewById(R.id.companyAdd_Cancel);

        companyAdd_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Company newCompany = new Company();
                newCompany.setName(companyAdd_name.getText().toString());
                newCompany.setNip(companyAdd_nip.getText().toString());
                newCompany.setAddress(companyAdd_address.getText().toString());
                newCompany.setPhone(companyAdd_phone.getText().toString());
                newCompany.setEmail(companyAdd_email.getText().toString());
                newCompany.setUserId(LoginActivity.user.getId());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(CompanyActivity.BASE_COMPANY_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CompanyAPI companyAPI = retrofit.create(CompanyAPI.class);
                Call<String> call = companyAPI.addCompany(newCompany);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });

                companyAdd_name.setText("");
                companyAdd_nip.setText("");
                companyAdd_address.setText("");
                companyAdd_phone.setText("");
                companyAdd_email.setText("");

                Toast.makeText(CompanyAdd.this, "Add Company", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CompanyAdd.this, CompanyActivity.class);
                CompanyAdd.this.startActivity(intent);

            }
        });

        companyAdd_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}
