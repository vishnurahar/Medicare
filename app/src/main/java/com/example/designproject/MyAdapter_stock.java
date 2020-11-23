package com.example.designproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MyAdapter_stock  extends BaseAdapter {
    Context context;
    ArrayList<Data_Stock> data;
    LayoutInflater inflater;

    public MyAdapter_stock(Context context, ArrayList<Data_Stock> data){
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
            view=inflater.inflate(R.layout.layout_model_stock , null);
        }

        MyViewHolder_stock myViewHolder_stock =new MyViewHolder_stock(view);
        myViewHolder_stock.t1.setText(data.get(i).getStockmedname());
        myViewHolder_stock.t2.setText(data.get(i).getStockmedleft());

        return view;
    }
}
