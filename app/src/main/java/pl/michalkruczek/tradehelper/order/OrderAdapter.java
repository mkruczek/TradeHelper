package pl.michalkruczek.tradehelper.order;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.Company;
import pl.michalkruczek.tradehelper.login.LoginActivity;
import pl.michalkruczek.tradehelper.product.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mikr on 05/09/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_recyclerview, parent, false);

        return new OrderViewHolder(row);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {

        final Order order = orderList.get(position);

        String orderString = "order ID : " + String.valueOf(order.getId());
        holder.orderRV_orderId.setText(orderString);
        String companyString = "company : " + findCompanyName(LoginActivity.companyList, order.getCompanyId());
        holder.orderRV_companyId.setText(companyString);
        String productString = "product : " + findProductName(LoginActivity.productList, order.getProductId());
        holder.orderRV_productId.setText(productString);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialogDelete = new AlertDialog.Builder(context)
                        .setTitle("Delete Order")
                        .setMessage("You want delete order: Id: " + order.getId() + " , continue?" )
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(OrderActivity.BASE_ORDER_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                OrderAPI orderAPI = retrofit.create(OrderAPI.class);
                                Call<String> call = orderAPI.deleteOrder(order.getId());
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                                orderList.remove(position);
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public static String findProductName(List<Product> productList, Long id) {
        String result = "";
        for (int i = 0; i < productList.size(); i++) {
            Long helpId = productList.get(i).getId();
            if (helpId == id) {
                result = productList.get(i).getName();
                break;
            }
        }
        return result;
    }

    public static String findCompanyName(List<Company> companyList, Long id) {
        String result = "";
        for (int i = 0; i < companyList.size(); i++) {
            Long helpId = companyList.get(i).getId();
            if (helpId == id) {
                result = companyList.get(i).getName();
                break;
            }
        }
        return result;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView orderRV_orderId;
        TextView orderRV_companyId;
        TextView orderRV_productId;

        public OrderViewHolder(View itemView) {
            super(itemView);

            orderRV_orderId = (TextView) itemView.findViewById(R.id.orderRV_orderId);
            orderRV_companyId = (TextView) itemView.findViewById(R.id.orderRV_companyId);
            orderRV_productId = (TextView) itemView.findViewById(R.id.orderRV_productId);

        }
    }
}
