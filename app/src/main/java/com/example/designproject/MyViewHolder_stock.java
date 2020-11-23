package com.example.designproject;

import android.view.View;
import android.widget.TextView;

public class MyViewHolder_stock {
    TextView t1,t2;
    public MyViewHolder_stock(View view){

        t1=(TextView)view.findViewById(R.id.stockmedname);
        t2=(TextView)view.findViewById(R.id.stockmedleft);
    }
}
