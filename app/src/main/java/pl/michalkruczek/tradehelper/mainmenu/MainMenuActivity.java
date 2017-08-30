package pl.michalkruczek.tradehelper.mainmenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.michalkruczek.tradehelper.R;

public class MainMenuActivity extends AppCompatActivity {

    //TODO - w kategoriach tolbar z nazwa i wypośrodekować

    //TODO - jak dodawć nowe firmy, produty i zamówienia??

    //TODO - logowanie z serwerem

    //TODO - mapa dojazdu jak w leprikonie

    private Activity activity;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private MainMenuAdapter mainMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        activity = MainMenuActivity.this;

        recyclerView = (RecyclerView) findViewById(R.id.iconRecyclerView);
        linearLayoutManager = new GridLayoutManager(activity, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        mainMenuAdapter = new MainMenuAdapter(activity, Icon.ICON_LIST);
        recyclerView.setAdapter(mainMenuAdapter);


    }
}

