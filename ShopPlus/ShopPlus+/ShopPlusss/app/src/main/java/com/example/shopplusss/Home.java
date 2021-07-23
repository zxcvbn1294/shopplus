package com.example.shopplusss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity {


    private Intent intent;
    private Context context = this;
    private Button PERSONALINFORMATION,SELL,BUY;
    private TextView textview,textviewwelcome,textviewtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PERSONALINFORMATION = (Button) findViewById(R.id.PERSONAL_INFORMATION);
        SELL = (Button) findViewById(R.id.SELL1);
        BUY = (Button) findViewById(R.id.BUY1);
        textviewtitle = (TextView)findViewById(R.id.PERSONAL_INFORMATION);
        textviewwelcome = (TextView)findViewById(R.id.textView_personal1);
        textview = (TextView)findViewById(R.id.textView);
        intent = this.getIntent();
        String name = intent.getStringExtra("id");
        textview.setText(name);

        PERSONALINFORMATION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, PersonalInformation.class);
                intent.putExtra("id",name);
                startActivity(intent);
            }
        });

        SELL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Sell.class);
                intent.putExtra("id",name);
                startActivity(intent);
            }
        });

        BUY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Buy.class);
                intent.putExtra("id",name);
                startActivity(intent);
            }
        });


    }
}


