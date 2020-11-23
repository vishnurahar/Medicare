package com.example.designproject;

import android.view.View;
import android.widget.TextView;

public class MyViewHolder_appoint {
    TextView t1,t2,t3,t4;
    public MyViewHolder_appoint(View view){


        t1=(TextView)view.findViewById(R.id.doctor_name);
        t2=(TextView)view.findViewById(R.id.note);
        t3=(TextView)view.findViewById(R.id.textdate);
        t4=(TextView)view.findViewById(R.id.texttime);

    }
}
