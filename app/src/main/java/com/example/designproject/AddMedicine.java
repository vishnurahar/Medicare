package com.example.designproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class AddMedicine extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper db;
    EditText med_name ,noofpills;
    TextView quant, tv_time_1, tv_time_2, tv_time_3;
    Button addmed, button, select_time_1_btn, select_time_2_btn, select_time_3_btn;
    private static final int PICK_IMAGE=100;
    Uri imageUri;
    int login_id;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login_id = getIntent().getIntExtra("ID",0);

        mYear= Calendar.getInstance().get(Calendar.YEAR);
        mMonth=Calendar.getInstance().get(Calendar.MONTH)+1;
        mDay=Calendar.getInstance().get(Calendar.DAY_OF_MONTH) ;
        mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) ;
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);



        db=new DatabaseHelper(this);
        button =(Button)findViewById(R.id.imageload);
        med_name=(EditText) findViewById(R.id.name_field);
        quant=(TextView) findViewById(R.id.quantity_text_view);
        noofpills =(EditText) findViewById(R.id.numberofpills);
        tv_time_1 = (TextView)findViewById(R.id.tv_time_1);
        tv_time_2 = (TextView)findViewById(R.id.tv_time_2);
        tv_time_3 = (TextView)findViewById(R.id.tv_time_3);
        addmed =(Button) findViewById(R.id.addmed);

        select_time_1_btn = findViewById(R.id.select_time_1_btn);
        select_time_2_btn = findViewById(R.id.select_time_2_btn);
        select_time_3_btn = findViewById(R.id.select_time_3_btn);

        addmed.setOnClickListener(this);
        button.setOnClickListener(this);
        select_time_1_btn.setOnClickListener(this);
        select_time_2_btn.setOnClickListener(this);
        select_time_3_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == button){
            openGallery();
        }

        if(view == addmed){
            String s1=med_name.getText().toString();    // MEdicine Name
            Integer s2=Integer.parseInt(quant.getText().toString());   /// Total No. of Medicines
            Integer s3= Integer.parseInt(noofpills.getText().toString());  // No. of pills  at a time
            String s4=tv_time_1.getText().toString();
            String s5=tv_time_2.getText().toString();
            String s6=tv_time_3.getText().toString();
//            Integer s4=Integer.parseInt(daysof.getText().toString());   ///  Ek din me kitni baar leni hai

            Boolean insertmed =  db.insert_med(login_id,s1,s3,s4,s5,s6,s2);
            if(insertmed==false){
                Toast.makeText(getApplicationContext(),"Failed to add medcine ", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Successfully added medicine", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getApplicationContext(),Nav_Page.class);
                intent1.putExtra("ID", login_id);
                startActivity(intent1);
            }
        }
        if(view == select_time_1_btn){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            tv_time_1.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if(view == select_time_2_btn){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            tv_time_2.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if(view == select_time_3_btn){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            tv_time_3.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode , Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RESULT_OK && requestCode ==PICK_IMAGE){
            imageUri = data.getData();
//            imageView.setImageURI(imageUri);
        }
    }

    public void increment(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 0) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    @SuppressLint("SetTextI18n")
    private void displayQuantity(int numberOfPills) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfPills);
    }



    public void cancelmedicine(View view) {
        Intent intent=new Intent(getApplicationContext(),Nav_Page.class);
        intent.putExtra("ID", login_id);
        startActivity(intent);
    }


}
