package com.example.shopplusss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText edittextid,edittextpassword;
    private Button buttonsingup,buttonlogin;

    private Context context=this;
    private Intent intent;
    private String result;
    private TextView textview,textviewreport;
    private String result1;
    private String yy="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittextid = findViewById(R.id.editText_signupID);
        edittextpassword = findViewById(R.id.editTextText_PASSWORD);

        buttonlogin = findViewById(R.id.button_singupok);
        buttonsingup = findViewById(R.id.button_personalDELETE);
        textview = findViewById(R.id.textView_personal);
        textviewreport = findViewById(R.id.textviewreport);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Thread thread = new Thread(login);
               thread.start();
            }

private final Runnable login = new Runnable() {
    @Override
    public void run() {
        try {
            URL url = new URL("http://10.0.2.2/Login.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.connect();

            JSONArray jsonArray = new JSONArray();
            JSONObject logindata = new JSONObject();
            logindata.put("id", edittextid.getText().toString());
            logindata.put("password", edittextpassword.getText().toString());

            jsonArray.put(logindata);
            Log.d("login",edittextid.getText().toString());
            Log.d("login",edittextpassword.getText().toString());

            DataOutputStream data = new DataOutputStream(connection.getOutputStream());
            data.writeBytes(logindata.toString());
            data.flush();
            data.close();
            result = logindata.toString();
            Log.d("login",result);

            if(edittextid.getText().toString().matches("")    || edittextpassword.getText().toString().matches(""))
            {
                textviewreport.setText("帳 號 密 碼 未 填 寫 完 整");
            }
            else
            {
                int responsecode = connection.getResponseCode();
                if(responsecode == HttpURLConnection.HTTP_OK){
                    Log.d("login","連線成功");

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    String box = "";
                    String line = null;
                    while((line = bufReader.readLine()) != null) { // 每當讀取出一列，就加到存放字串後面
                        box += line;
                        Log.d("login","line:"+line);
                        Log.d("login","box:"+box);
                    }
                    result1 = box;; // 把存放用字串放到全域變數
                    Log.d("login","result1:"+result1);
                    inputStream.close(); // 關閉輸入串流
                    JSONObject success = new JSONObject();

                    if(result1!=""){
                        Log.d("login","登入成功"+result1);
                            intent = new Intent(context, Home.class);
                            intent.putExtra("id",edittextid.getText().toString());
                            startActivity(intent);

                    }else
                    {
                        textviewreport.setText("帳 號 密 碼 錯 誤");
                        Log.d("login","帳號密碼錯誤"+result1);
                    }
                }

            }

        }catch (Exception e){
            result = e.toString();
        }

    }
};
        });


        buttonsingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Singup.class);
                startActivity(intent);
            }
        });



    }
}