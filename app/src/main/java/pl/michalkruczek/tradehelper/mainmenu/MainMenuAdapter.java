package pl.michalkruczek.tradehelper.mainmenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.CompanyActivity;
import pl.michalkruczek.tradehelper.order.OrderActivity;
import pl.michalkruczek.tradehelper.product.ProductActivity;

/**
 * Created by mikr on 28/08/17.
 */

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {

    private Activity activity;
    private List<Icon> iconList;


    public MainMenuAdapter(Activity activity, List<Icon> iconList) {
        this.activity = activity;
        this.iconList = iconList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.icon_layout, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Icon icon = iconList.get(position);

        holder.iconButton.setImageResource(icon.getResources());
        holder.iconTextView.setText(icon.getName());

    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageButton iconButton;
        TextView iconTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            iconButton = (ImageButton) itemView.findViewById(R.id.iconButton);
            iconTextView = (TextView) itemView.findViewById(R.id.iconTextView);

            iconButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (getAdapterPosition()) {
                case 0:
                    Intent companyIntent = new Intent(activity, CompanyActivity.class);
                    activity.startActivity(companyIntent);
                    break;
                case 1:
                    Intent productsIntent = new Intent(activity, ProductActivity.class);
                    activity.startActivity(productsIntent);
                    break;
                case 2:
                    Intent orderIntent = new Intent(activity, OrderActivity.class);
                    activity.startActivity(orderIntent);
                    break;
                case 3:
                    Toast.makeText(activity, "Day Plan", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(activity, "Notes", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(activity, "Info", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
