package com.example.shopplusss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PersonalInformation extends AppCompatActivity {

    private TextView textViewShow;
    private EditText changePassword,changePhone,changeEmail;
    private Button ButtonOK,ButtonDelete   ,PERSONALINFORMATION,SELL,BUY;
    private Intent intent;
    private Context context = this;
    private String name;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);


        textViewShow = (TextView)findViewById(R.id.textView_showid);

        changePassword = (EditText)findViewById(R.id.editText_personalpassword);
        changePhone = (EditText)findViewById(R.id.editText_presonalphone);
        changeEmail = (EditText)findViewById(R.id.editText_personalemail);

        ButtonOK = (Button)findViewById(R.id.button_presonalOK);
        ButtonDelete = (Button)findViewById(R.id.button_personalDELETE);

        PERSONALINFORMATION = (Button)findViewById(R.id.PERSONAL_INFORMATION1);
        SELL = (Button)findViewById(R.id.SELL1);
        BUY = (Button)findViewById(R.id.BUY1);
        intent = this.getIntent();
        name = intent.getStringExtra("id");
        textViewShow.setText("Hello !    "+" "+name);
        Log.d("change",name);
        PERSONALINFORMATION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intent = new Intent(context, PersonalInformation.class);
//
//                name = intent.getStringExtra("id");
//
//                textViewShow.setText("Hello !  "+" "+name);
//                startActivity(intent);
            }
        });

        SELL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Sell.class);
                startActivity(intent);
            }
        });


        BUY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Buy.class);
                startActivity(intent);
            }
        });


        ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(delete);
                thread.start();
            }
            private final Runnable delete = new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://10.0.2.2/Delete.php");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.setUseCaches(false);
                        connection.connect();



                        JSONArray jsonArray = new JSONArray();
                        JSONObject changedata = new JSONObject();
                        changedata.put("id",name);
                        jsonArray.put(changedata);
                        DataOutputStream data = new DataOutputStream(connection.getOutputStream());
                        data.writeBytes(changedata.toString());
                        data.flush();
                        data.close();
                        result = changedata.toString();
                        Log.d("change",result);

                            int responsecode = connection.getResponseCode();
                            if(responsecode == HttpURLConnection.HTTP_OK){
                                Log.d("change","連線成功");
                                Log.d("change","刪除成功");
                                intent = new Intent(context, MainActivity.class);
                                startActivity(intent);

                            }



                    }catch (Exception e){
                        result = e.toString();
                    }

                }
            };

        });


        ButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(change);
                thread.start();
            }
            private final Runnable change = new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://10.0.2.2/Change.php");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.setUseCaches(false);
                        connection.connect();

                        JSONArray jsonArray = new JSONArray();
                        JSONObject changedata = new JSONObject();
                        changedata.put("id",name);
                        changedata.put("password", changePassword.getText().toString());
                        changedata.put("phone", changePhone.getText().toString());
                        changedata.put("email", changeEmail.getText().toString());
                        jsonArray.put(changedata);
                        Log.d("change",name);
                        Log.d("change",changePassword.getText().toString());
                        Log.d("change",changePhone.getText().toString());
                        Log.d("change",changeEmail.getText().toString());
                        DataOutputStream data = new DataOutputStream(connection.getOutputStream());
                        data.writeBytes(changedata.toString());
                        data.flush();
                        data.close();
                        result = changedata.toString();
                        Log.d("change",result);

                        if(changePassword.getText().toString().matches("")    || changePhone.getText().toString().matches("") ||
                           changeEmail.getText().toString().matches("") )
                        {
                            //textviewreport.setText("資 料 未 填 寫 完 整");
                            Log.d("change","資 料 未 填 寫 完 整");
                        }
                        else
                        {
                            int responsecode = connection.getResponseCode();
                            if(responsecode == HttpURLConnection.HTTP_OK){
                                Log.d("change","連線成功");
                                Log.d("change","更 新 成 功");
                                intent = new Intent(context, MainActivity.class);
                                startActivity(intent);
                            }

                        }

                    }catch (Exception e){
                        result = e.toString();
                    }

                }
            };

        });


    }

}