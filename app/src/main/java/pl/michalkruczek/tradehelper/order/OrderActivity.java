package pl.michalkruczek.tradehelper.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.Company;

public class OrderActivity extends AppCompatActivity {

    //TODO - połaczenie company z produktami
    //TODO - order recyclerView
    //TODO lista ordersów

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);

        long companyId = getIntent().getLongExtra("company", 666);
        long productId = getIntent().getLongExtra("product", 666);

        TextView order_tv = (TextView) findViewById(R.id.order_tv);

        order_tv.setText("company Id: " + String.valueOf(companyId) + "\nproductId: " + String.valueOf(productId));
    }
}
