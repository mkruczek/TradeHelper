package pl.michalkruczek.tradehelper.mainmenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.login.LoginActivity;
import pl.michalkruczek.tradehelper.login.User;
import pl.michalkruczek.tradehelper.product.Product;
import pl.michalkruczek.tradehelper.product.ProductAPI;
import pl.michalkruczek.tradehelper.product.ProductActivity;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainMenuActivity extends AppCompatActivity {


    //TODO - w kategoriach tolbar z nazwa i wypośrodekować

    private Activity activity;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MainMenuAdapter mainMenuAdapter;

    private TextView mainmenu_login;
    private TextView mainmenu_logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        activity = MainMenuActivity.this;

        mainmenu_login = (TextView) findViewById(R.id.mainmenu_login);

        recyclerView = (RecyclerView) findViewById(R.id.iconRecyclerView);
        linearLayoutManager = new GridLayoutManager(activity, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        mainMenuAdapter = new MainMenuAdapter(activity, Icon.ICON_LIST);
        recyclerView.setAdapter(mainMenuAdapter);

        String name = LoginActivity.user.getName();
        String surname = LoginActivity.user.getSurname();
        Long userId = LoginActivity.user.getId();

        String loginAs = "Login as " + name + " " + surname + ".";
        mainmenu_login.setText(loginAs);

        mainmenu_logOut = (TextView) findViewById(R.id.mainmenu_logOut);
        mainmenu_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
            }
        });



    }


}

