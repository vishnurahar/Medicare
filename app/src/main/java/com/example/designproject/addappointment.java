package com.example.designproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;

public class addappointment extends AppCompatActivity implements View.OnClickListener{
    private int notificationId = 2;
    Button select_date_btn, select_time_btn ,addappointment,cancelbutton;
    TextView tv_date, tv_time;
    EditText doctor, note;
    DatabaseHelper db;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    int login_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login_id = getIntent().getIntExtra("ID",0);

        mYear= Calendar.getInstance().get(Calendar.YEAR);
        mMonth=Calendar.getInstance().get(Calendar.MONTH)+1;
        mDay=Calendar.getInstance().get(Calendar.DAY_OF_MONTH) ;
        mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) ;
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);

        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        note=(EditText)findViewById(R.id.note);
        db=new DatabaseHelper(this);
        addappointment =(Button)findViewById(R.id.add_appointment_btn);
        cancelbutton= (Button)findViewById(R.id.cancel_appiontment_btn);
        doctor=(EditText)findViewById(R.id.doctor_name);


        addappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1=doctor.getText().toString();
                String s2=tv_date.getText().toString();
                String s3=tv_time.getText().toString();
                String s4=note.getText().toString();

                Boolean insertappointment =   db.insert_appoint(login_id,s1,s2,s3,s4);
                if(insertappointment==false){
                    Toast.makeText(getApplicationContext(),"Failed to add Appointment to the DATABASE", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Successfully added Appointment", Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(getApplicationContext(),appointment.class);
                    intent1.putExtra("ID", login_id);
                    startActivity(intent1);
                }
            }
        });

        select_date_btn = findViewById(R.id.select_date_btn);
        select_time_btn = findViewById(R.id.select_time_btn);
        select_date_btn.setOnClickListener(this);
        select_time_btn.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        if(view == select_date_btn){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            Intent intent = new Intent(addappointment.this, AlarmReceiver.class);
                            intent.putExtra("notificationId", notificationId);
                            intent.putExtra("message", "Time to take some medicines");

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                    addappointment.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
                            );

                            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                            int timemonth = monthOfYear;
                            int timeday = dayOfMonth;

                            Calendar startTime = Calendar.getInstance();
                            startTime.set(Calendar.MONTH, monthOfYear);
                            startTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            startTime.set(Calendar.SECOND, 0);
                            long alarmStartTime = startTime.getTimeInMillis();

                            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if(view == select_time_btn){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            tv_time.setText(hourOfDay + ":" + minute);

                            Intent intent = new Intent(addappointment.this, AlarmReceiver.class);
                            intent.putExtra("notificationId", notificationId);
                            intent.putExtra("message", "Time to take some medicines");

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                    addappointment.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
                            );

                            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                            int timehour = hourOfDay;
                            int timeminute = minute;

                            Calendar startTime = Calendar.getInstance();
                            startTime.set(Calendar.HOUR_OF_DAY, timehour);
                            startTime.set(Calendar.MINUTE, timeminute);
                            startTime.set(Calendar.SECOND, 0);
                            long alarmStartTime = startTime.getTimeInMillis();

                            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

}
