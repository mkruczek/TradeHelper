package pl.michalkruczek.tradehelper.product;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;

import pl.michalkruczek.tradehelper.R;
import pl.michalkruczek.tradehelper.company.CompanyAPI;
import pl.michalkruczek.tradehelper.company.CompanyActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdd extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST = 2;
    private static String resources;
    private Context context;

    private EditText productAdd_name;
    private EditText productAdd_description;
    private EditText productAdd_price;
    private ImageView productAdd_image;
    private TextView productAdd_Save;
    private TextView productAdd_Cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_add_activity);

        context = ProductAdd.this;

        productAdd_name = (EditText) findViewById(R.id.productAdd_name);
        productAdd_description = (EditText) findViewById(R.id.productAdd_description);
        productAdd_price = (EditText) findViewById(R.id.productAdd_price);
        productAdd_image = (ImageView) findViewById(R.id.productAdd_image);
        productAdd_Save = (TextView) findViewById(R.id.productAdd_Save);
        productAdd_Cancel = (TextView) findViewById(R.id.productAdd_Cancel);

        productAdd_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product newProduct = new Product();
                newProduct.setName(productAdd_name.getText().toString());
                newProduct.setDescription(productAdd_description.getText().toString());
                newProduct.setPrice(BigDecimal.valueOf(Double.valueOf(productAdd_price.getText().toString())));



                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ProductActivity.BASE_PRODUCT_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ProductAPI productAPI = retrofit.create(ProductAPI.class);
                Call<String> call = productAPI.addProduct(newProduct);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                productAdd_name.setText("");
                productAdd_description.setText("");
                productAdd_price.setText("");

                Intent intent = new Intent(context, ProductActivity.class);
                context.startActivity(intent);
            }
        });

        productAdd_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });


        productAdd_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View productAddImageLayout = View.inflate(context, R.layout.product_add_image, null);

                final android.support.v7.app.AlertDialog productAddImageAlertDialog = new android.support.v7.app.AlertDialog.Builder(context)
                        .setView(productAddImageLayout )
                        .setTitle(R.string.chose_options)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                TextView productAdd_image_gallery = (TextView) productAddImageLayout.findViewById(R.id.productAdd_image_gallery);
                productAdd_image_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            if ((ActivityCompat.shouldShowRequestPermissionRationale(ProductAdd.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
                                showExplanation("Need permission", "If You want load image from gallery, You mast agree.",
                                        Manifest.permission.READ_EXTERNAL_STORAGE, RESULT_LOAD_IMAGE);
                            } else {
                                requestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, RESULT_LOAD_IMAGE);
                            }
                        } else {
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(Intent.createChooser(i, "Chose sources"), RESULT_LOAD_IMAGE);
                        }

                        productAddImageAlertDialog.dismiss();
                    }
                });

                TextView productAdd_image_camera = (TextView) productAddImageLayout.findViewById(R.id.productAdd_image_camera);
                productAdd_image_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);

                        productAddImageAlertDialog.dismiss();
                    }
                });

            }
        });

    }

    private void showExplanation(String title, String message, final String permission, final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                requestPermissions(permission, permissionRequestCode);
            }
        });
        builder.show();
    }

    private void requestPermissions(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permissionName}, permissionRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            resources = "file://" + picturePath;
            Glide.with(getApplicationContext()).load(resources).override(900, 475).into(productAdd_image);


        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            String photoPath = "";

            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.ImageColumns.ORIENTATION}, MediaStore.Images.Media.DATE_ADDED, null, "date_added ASC");
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                    photoPath = uri.toString();
                } while (cursor.moveToNext());
                cursor.close();
            }
            resources = "file://" + photoPath;
            Glide.with(getApplicationContext()).load(resources).override(900, 475).into(productAdd_image);

        }

    }
}
