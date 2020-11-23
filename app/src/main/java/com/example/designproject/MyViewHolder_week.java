package com.example.designproject;

import android.view.View;
import android.widget.TextView;

public class MyViewHolder_week {
    TextView t1,t2,t3,t4;
    public MyViewHolder_week(View view){

        t1=(TextView)view.findViewById(R.id.medicine);
        t2=(TextView)view.findViewById(R.id.dateweek);
        t3=(TextView)view.findViewById(R.id.timeweek);
        t4=(TextView)view.findViewById(R.id.swallowweek);


    }
}
