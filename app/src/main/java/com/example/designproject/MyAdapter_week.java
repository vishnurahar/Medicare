package com.example.designproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MyAdapter_week  extends BaseAdapter  {

    Context context;
    ArrayList<Data_week> data;
    LayoutInflater inflater;

    public MyAdapter_week(Context context, ArrayList<Data_week> data){
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
            view=inflater.inflate(R.layout.layout_model_week , null);
        }

        MyViewHolder_week myViewHolderWeek =new MyViewHolder_week(view);
        myViewHolderWeek.t1.setText(data.get(i).getMed_name());
        myViewHolderWeek.t2.setText(data.get(i).getRepdate());
        myViewHolderWeek.t3.setText(data.get(i).getReptime());
        myViewHolderWeek.t4.setText(data.get(i).getSwallow());

        return view;
    }
}
