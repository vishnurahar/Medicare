package com.example.designproject;


import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

        int s3= Integer.parseInt(parent.getSelectedItem().toString());  // No. of pills  at a time
        int s4=Integer.parseInt(parent.getSelectedItem().toString());   ///  Ek din me kitni baar leni hai
//        Toast.makeText(parent.getContext(),
//                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}

