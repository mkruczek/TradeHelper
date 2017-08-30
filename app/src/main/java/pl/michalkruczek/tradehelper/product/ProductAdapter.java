package pl.michalkruczek.tradehelper.product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.order.OrderActivity;


/**
 * Created by mikr on 28/08/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_recyclerview, parent, false);

        return new ProductViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {

        final Product product = productList.get(position);

        holder.productRV_name.setText(product.getName());
        holder.productRV_price.setText("$ " + String.valueOf(product.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
        holder.productRV_description.setText(product.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View productAlertDialogLayout = View.inflate(context, R.layout.product_recyclerview_onclick, null);

                final AlertDialog productAlertDialog = new AlertDialog.Builder(context)
                        .setView(productAlertDialogLayout)
                        .setTitle(R.string.chose_options)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                TextView productRV_onClick_makeOrder = (TextView) productAlertDialogLayout.findViewById(R.id.productRV_onClick_makeOrder);
                productRV_onClick_makeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO - new Intent to CreateOrder.class with extras
                        Intent intent = new Intent(context, OrderActivity.class);
                        intent.putExtra("product", product.getId());
                        context.startActivity(intent);
                        productAlertDialog.dismiss();
                    }
                });

                TextView productRV_onClick_updata = (TextView) productAlertDialogLayout.findViewById(R.id.productRV_onClick_updata);
                productRV_onClick_updata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ProductUpdate.class);
                        intent.putExtra("productUpdate", product);
                        context.startActivity(intent);
                        productAlertDialog.dismiss();
                    }
                });

                TextView productRV_onClick_delete = (TextView) productAlertDialogLayout.findViewById(R.id.productRV_onClick_delete);
                productRV_onClick_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "DELETE - Not avalible yet.", Toast.LENGTH_SHORT).show();
                        //TODO - alertDaialog z upewnieniem się + info ze all zamówienie też pójdą w chuj + @DELETE


                        // TODO póki co usune sobie z listy, zeby choc troche dzialalo
                        AlertDialog deleteWarning = new AlertDialog.Builder(context)
                                .setTitle("You want delete product!")
                                .setMessage("If You do this, you delete all order for this product too. \nDo You want continou?") //TODO continou spelling??
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        productList.remove(position);
                                        notifyDataSetChanged();
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();

                        productAlertDialog.dismiss();
                    }
                });

            }
        });



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

//        private ImageView imageView
        private TextView productRV_name;
        private TextView productRV_price;
        private TextView productRV_description;

        public ProductViewHolder(View itemView) {
            super(itemView);

            productRV_name = (TextView) itemView.findViewById(R.id.productRV_name);
            productRV_price = (TextView) itemView.findViewById(R.id.productRV_price);
            productRV_description = (TextView) itemView.findViewById(R.id.productRV_description);
        }
    }
}
