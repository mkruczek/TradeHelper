package pl.michalkruczek.tradehelper.company;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.order.OrderActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mikr on 28/08/17.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private Context context;
    private List<Company> companyList;

    public CompanyAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList;
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_recyclerview, parent, false);

        return new CompanyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(CompanyViewHolder holder, final int position) {

        final Company company = companyList.get(position);

        holder.comapnyRV_name.setText(company.getName());
        holder.companyRV_nip.setText(company.getNip());
        holder.companyRV_phone.setText(company.getPhone());
        holder.companyRV_email.setText(company.getEmail());
        holder.companyRV_address.setText(company.getAddress());

        holder.companyRV_doPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:" + company.getPhone());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                context.startActivity(callIntent);
            }
        });

        holder.companyRV_doEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", company.getEmail(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                        "Michał Kruczek - Państwa Programista");
                context.startActivity(emailIntent);
            }
        });

        holder.companyRV_doMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL = "https://www.google.com/maps/dir/"
                        + "?api=1&travelmode=driving&dir_action=navigate&destination="
                        + company.getAddress();
                Uri location = Uri.parse(URL);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                context.startActivity(mapIntent);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View companyAlertDialogLayout = View.inflate(context, R.layout.company_recyclerview_onclick, null);

                final AlertDialog comapnyAlertDialog = new AlertDialog.Builder(context)
                        .setView(companyAlertDialogLayout)
                        .setTitle(R.string.chose_options)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                TextView companyRV_onClick_showOrder = (TextView) companyAlertDialogLayout.findViewById(R.id.companyRV_onClick_showOrder);
                companyRV_onClick_showOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, OrderActivity.class);
                        intent.putExtra("company", company.getId());
                        context.startActivity(intent);
                        comapnyAlertDialog.dismiss();
                    }
                });

                TextView companyRV_onClick_update = (TextView) companyAlertDialogLayout.findViewById(R.id.companyRV_onClick_update);
                companyRV_onClick_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CompanyUpdate.class);
                        intent.putExtra("companyUpdate", company);
                        context.startActivity(intent);
                        comapnyAlertDialog.dismiss();
                    }
                });

                TextView companyRV_onClick_delete = (TextView) companyAlertDialogLayout.findViewById(R.id.companyRV_onClick_delete);
                companyRV_onClick_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO - usówanie zamówień danej firmy
                        AlertDialog deleteWarning = new AlertDialog.Builder(context)
                                .setTitle("You want delete company!")
                                .setMessage("If You do this, you delete all order for this company too. \nDo You want continue?")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(CompanyActivity.BASE_COMPANY_URL)
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        CompanyAPI companyAPI = retrofit.create(CompanyAPI.class);
                                        Call<String> call = companyAPI.deleteCompany(company.getId());

                                        call.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {

                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {

                                            }
                                        });
                                        companyList.remove(position);
                                        notifyDataSetChanged();
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();

                        comapnyAlertDialog.dismiss();
                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {

        private TextView comapnyRV_name;
        private ImageButton companyRV_doEmail;
        private ImageButton companyRV_doPhone;
        private ImageButton companyRV_doMap;
        private TextView companyRV_nip;
        private TextView companyRV_phone;
        private TextView companyRV_email;
        private TextView companyRV_address;


        public CompanyViewHolder(View itemView) {
            super(itemView);
            comapnyRV_name = (TextView) itemView.findViewById(R.id.companyRV_name);
            companyRV_nip = (TextView) itemView.findViewById(R.id.companyRV_nip);
            companyRV_phone = (TextView) itemView.findViewById(R.id.companyRV_phone);
            companyRV_email = (TextView) itemView.findViewById(R.id.companyRV_email);
            companyRV_address = (TextView) itemView.findViewById(R.id.companyRV_address);
            companyRV_doEmail = (ImageButton) itemView.findViewById(R.id.companyRV_doEmail);
            companyRV_doPhone = (ImageButton) itemView.findViewById(R.id.companyRV_doPhone);
            companyRV_doMap = (ImageButton) itemView.findViewById(R.id.companyRV_doMap);
        }
    }

}
