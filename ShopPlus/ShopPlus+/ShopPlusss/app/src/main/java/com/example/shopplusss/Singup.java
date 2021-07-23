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

public class Singup extends AppCompatActivity {
//宣告元件
    private EditText edittextsignupid,edittextsignuppassword,edittextsignupphone,edittextsignupemail;
    private Button buttonOK,buttonCANCEL;
    private String result;

    private Context context=this;
    private Intent intent;
    private TextView textview,textviewreport;
    private String result1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        //建立元件/輸入方塊
        edittextsignupid = findViewById(R.id.editText_signupID);
        edittextsignuppassword = findViewById(R.id.editText_signuppassword);
        edittextsignupphone = findViewById(R.id.editText_singupphone);
        edittextsignupemail = findViewById(R.id.editText_signupemail);
        //建立元件/按鈕方塊
        buttonOK = findViewById(R.id.button_singupok);
        buttonCANCEL = findViewById(R.id.button_personalDELETE);
        textview = findViewById(R.id.textView_personal);
        textviewreport = findViewById(R.id.textviewreportsign);

        //BUTTON[OK] 按鈕監聽觸發事件 -->確認連線/建立JSON字串物件/outstream json物件與php連接/上傳資料/判斷輸入內容是否有空值-->條件式
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(signup);
                thread.start();
            }
            private final Runnable signup = new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://10.0.2.2/Add.php");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.setUseCaches(false);
                        connection.connect();

                        JSONArray jsonArray = new JSONArray();
                        JSONObject signupdata = new JSONObject();
                        signupdata.put("id", edittextsignupid.getText().toString());
                        signupdata.put("password", edittextsignuppassword.getText().toString());
                        signupdata.put("phone", edittextsignupphone.getText().toString());
                        signupdata.put("email", edittextsignupemail.getText().toString());
                        jsonArray.put(signupdata);
                        Log.d("signup",edittextsignupid.getText().toString());
                        Log.d("signup",edittextsignuppassword.getText().toString());
                        Log.d("signup",edittextsignupphone.getText().toString());
                        Log.d("signup",edittextsignupemail.getText().toString());
                        DataOutputStream data = new DataOutputStream(connection.getOutputStream());
                        data.writeBytes(signupdata.toString());
                        data.flush();
                        data.close();
                        result = signupdata.toString();
                        Log.d("signup",result);

                        if(edittextsignupid.getText().toString().matches("")    || edittextsignuppassword.getText().toString().matches("") ||
                           edittextsignupphone.getText().toString().matches("") || edittextsignupemail.getText().toString().matches("") )
                        {
                            textviewreport.setText("資 料 未 填 寫 完 整");
                        }
                        else
                            {
                                int responsecode = connection.getResponseCode();
                                if(responsecode == HttpURLConnection.HTTP_OK){
                                Log.d("signup","連線成功");
                                    InputStream inputStream = connection.getInputStream();
                                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                                    String box = "";
                                    String line = null;
                                    while((line = bufReader.readLine()) != null) { // 每當讀取出一列，就加到存放字串後面
                                        box += line + "\n";
                                        Log.d("signup","line"+line);
                                        Log.d("signup","box"+box);
                                    }
                                    result1 = box;; // 把存放用字串放到全域變數
                                    Log.d("signup",result1);
                                    inputStream.close(); // 關閉輸入串流

                                if(result1==""){
                                    textviewreport.setText("此 帳 號 已 被 申 請");
                                    Log.d("signup","此 帳 號 已 被 申 請");
                                }
                                else{
                                    intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                    Log.d("signup","切換頁面");
                                }



                                }

                            }

                    }catch (Exception e){
                        result = e.toString();
                    }

                }
            };
        });

        //BUTTON[CANCEL] 按鈕監聽觸發事件 -->清除輸入文字
        buttonCANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittextsignupid.setText("");
                edittextsignuppassword.setText("");
                edittextsignupphone.setText("");
                edittextsignupemail.setText("");
                textviewreport.setText("已 刪 除 您 輸 入 的 資 料");
                Log.d("signup","刪除輸入資料");

            }
        });

        //BUTTON[textview] 按鈕監聽觸發事件 -->回登入畫面
        textview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    });


    }
}