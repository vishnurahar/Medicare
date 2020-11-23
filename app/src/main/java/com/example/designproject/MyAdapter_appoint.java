package com.example.designproject;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MyAdapter_appoint extends BaseAdapter {

    Context context;
    ArrayList<Data_Appoint> data;
    LayoutInflater inflater;

    public MyAdapter_appoint(Context context, ArrayList<Data_Appoint> data){
        this.context=context;
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(inflater==null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null){
            view=inflater.inflate(R.layout.layout_model_appoint , null);
        }

        MyViewHolder_appoint myViewHolder_appoint =new MyViewHolder_appoint(view);
        myViewHolder_appoint.t1.setText(data.get(i).getDoc_name());
        myViewHolder_appoint.t2.setText(data.get(i).getNote());
        myViewHolder_appoint.t3.setText(data.get(i).getDate());
        myViewHolder_appoint.t4.setText(data.get(i).getTime());

        return view;
    }
}
