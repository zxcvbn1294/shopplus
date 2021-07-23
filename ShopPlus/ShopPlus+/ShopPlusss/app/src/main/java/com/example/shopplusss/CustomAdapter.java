package com.example.shopplusss;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Map;

public class CustomAdapter extends BaseAdapter {
    ArrayList<Map<String,String>> item;

    private LayoutInflater mLayout;
    private Bitmap bitmap;

    public  CustomAdapter(Context context, ArrayList<Map<String,String>> mList){

        mLayout = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.item = mList;

    }
    @Override
    public int getCount() {
        return item.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // R.layout.custom_layout 自訂layout
        View v = mLayout.inflate(R.layout.listview_item,parent,false);
        TextView title = (TextView)v.findViewById(R.id.tv);
        TextView content = (TextView)v.findViewById(R.id.tv1);
        ImageView pic = (ImageView) v.findViewById(R.id.imageView);
        //TextView picc = (TextView)v.findViewById(R.id.textView2);


        byte[] bitmapArray;
        bitmapArray = Base64.decode(item.get(position).get("pic"), Base64.DEFAULT);
        Log.d("msg2",bitmapArray.toString());
        bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        Log.d("msg2",item.get(position).get("pic"));

        pic.setImageBitmap(bitmap);
        //picc.setText(item.get(position).get("pic"));
        title.setText(item.get(position).get("productname"));

        content.setText(item.get(position).get("price"));

        return v;
    }

}
