package com.example.shopplusss;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Buy extends AppCompatActivity {

    SearchView searchView; //搜尋引擎
    ListView listView; //listview
    List my_list; //test資料容器
    CustomAdapter mCustomAdapter;
    String result;
    private JSONArray jsonArray;
    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        listView = (ListView)findViewById(R.id.listview);
        my_list = new ArrayList<Map<String,String>>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2/Buy.php"); // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // 建立 Google 比較挺的 HttpURLConnection 物件
                    connection.setRequestMethod("POST"); // 設定連線方式為 POST
                    connection.setDoOutput(true); // 允許輸出
                    connection.setDoInput(true); // 允許讀入
                    connection.setUseCaches(false); // 不使用快取
                    connection.connect(); // 開始連線
                    Log.d("msg","讀取資料");

                    int responseCode = connection.getResponseCode(); // 建立取得回應的物件
                    if(responseCode == HttpURLConnection.HTTP_OK){  // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                        // 取得輸入串流
                        InputStream inputStream = connection.getInputStream();
                        //BufferedReader 可以用來讀取鍵盤輸入和檔案內容
                        BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8); // //將實體in丟到讀取器buf
                        String box = ""; // 宣告存放用字串
                        String line = null; // 宣告讀取用的字串
                        while((line = bufReader.readLine()) != null) { // 每當讀取出一列，就加到存放字串後面
                            box += line + "\n";
                            Log.d("msg",box);
                        }
                        inputStream.close(); // 關閉輸入串流
                        Log.d("msg",box);
                        result = box; // 把存放用字串放到全域變數
                        jsonArray = new JSONArray(result);
                        Log.d("msg",result);
                        Log.d("msg","jsonArray"+jsonArray.toString());
                        Log.d("msg","1");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Map<String,Object> map = new HashMap<String, Object>();
                            String pic =  new JSONArray(result).getJSONObject(i).getString("pic");
                            String productname = new JSONArray(result).getJSONObject(i).getString("productname");
                            String price = new JSONArray(result).getJSONObject(i).getString("price");

                            byte[] bitmapArray;
                            bitmapArray = Base64.decode(pic, Base64.DEFAULT);
                            Log.d("msg",bitmapArray.toString());
                            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                            Log.d("msg1",pic);
                            Log.d("msg1",bitmap.toString());
                            Log.e("TAG", "try_b--" + bitmap);

                            map.put("pic", pic);
                            map.put("productname", productname);
                            map.put("price", price);
                            //convertStringToIcon(pic);
                            my_list.add(map);
                            Log.d("msg", "map = " + map);
                            Log.d("msg", my_list.toString());
                        }
                    }
                } catch(Exception e) {
                    result = e.toString(); // 如果出事，回傳錯誤訊息
                }
            }
        }).start();

        mCustomAdapter = new CustomAdapter(this, (ArrayList<Map<String,String>>) my_list);
        listView.setAdapter(mCustomAdapter);

        //點選listview 顯示 事件

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Buy.this, "You Choose "+ position+" listItem", Toast.LENGTH_SHORT).show();
            }
        });



    }

}