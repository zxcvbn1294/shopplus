package com.example.shopplusss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Sell extends AppCompatActivity {

    private static final int requestCode = 1;
    private EditText productname,price;
    private Button ButtonUpload,ButtonOK;
    private ImageView imageview;
    private String result;
    private Context context=this;
    private String headPicture;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        productname = (EditText)findViewById(R.id.editText_productname);
        price = (EditText)findViewById(R.id.editTextprice);
        imageview = (ImageView)findViewById(R.id.imageView);

        ButtonUpload = (Button)findViewById(R.id.button_upload);
        ButtonOK = (Button)findViewById(R.id.button_oks);

        ButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
                startActivityForResult(intent, 1);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        if (resultCode == RESULT_OK) {

            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                imageview.setImageBitmap(bitmap);

                ButtonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Thread thread = new Thread(sell);
                        thread.start();
                    }


                    private final Runnable sell = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL("http://10.0.2.2/Sell.php");
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("POST");
                                connection.setDoInput(true);
                                connection.setDoOutput(true);
                                connection.setUseCaches(false);
                                connection.connect();

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                Log.d("sell","bitmap="+bitmap);
                                Log.d("sell","baos="+baos);
                                byte[] datas = baos.toByteArray();
                                Log.d("sell","datas="+datas);
                                headPicture = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                                Log.d("sell","headPicture="+headPicture);

                                JSONArray jsonArray = new JSONArray();
                                JSONObject sell = new JSONObject();
                                sell.put("pic",headPicture);
                                sell.put("productname",productname.getText().toString());
                                sell.put("price",price.getText().toString());
                                jsonArray.put(sell);
                                //Log.d("sell", datas.toString());
                                //Log.d("sell", productname.getText().toString());
                                //Log.d("sell", price.getText().toString());

                                DataOutputStream data = new DataOutputStream(connection.getOutputStream());
                                data.writeBytes(sell.toString());
                                data.flush();
                                data.close();
                                result = sell.toString();
                                Log.d("sell", result);

                                int responsecode = connection.getResponseCode();
                                if(responsecode == HttpURLConnection.HTTP_OK){
                                    Log.d("signup","連線成功");
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                }



                            }catch (Exception e){
                                result = e.toString();
                            }


                        }
                    };
                });

            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}