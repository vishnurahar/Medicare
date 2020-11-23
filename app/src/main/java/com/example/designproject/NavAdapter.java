package com.example.designproject;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NavAdapter  extends RecyclerView.Adapter<NavAdapter.NavViewHolder>{

    private ArrayList<NavItem> mNavList;
    private OnItemclickListener mListener;


    public interface  OnItemclickListener{
        void onItemClick(int position);
        void onTickClick(int position);
        void onCrossClick(int position);

    }

    public void setOnItemClickListener(OnItemclickListener listener){
        mListener=listener;
    }

    public static class NavViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1,mTextView2;
        public Button mtick, mcross;
        public NavViewHolder(@NonNull View itemView , OnItemclickListener listener) {
            super(itemView);
            mTextView1=itemView.findViewById(R.id.textView);
            mTextView2=itemView.findViewById(R.id.textView2);
            mtick = itemView.findViewById(R.id.tick);
            mcross=itemView.findViewById(R.id.cross);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(listener!= null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mtick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!= null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onTickClick(position);
                        }
                    }
                }
            });
            mcross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION){
                        listener.onCrossClick(position);
                    }
                }
            });

        }
    }

    public NavAdapter(ArrayList<NavItem> navList){
        mNavList=navList;
    }

    @NonNull
    @Override
    public NavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_example_item,parent,false);
       NavViewHolder nvh =new NavViewHolder(v , mListener);
       return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NavViewHolder holder, int position) {
        NavItem currentItem=mNavList.get(position);

        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());

    }

    @Override
    public int getItemCount() {
        return mNavList.size();
    }
}
