package com.example.designproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class appointment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    int login_id;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    DatabaseHelper db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        login_id = getIntent().getIntExtra("ID",0);
        db=new DatabaseHelper(this);
        this.configureDrawerLayout();
        this.configureNavigationView();
        listView =findViewById(R.id.listView);
        MyAdapter_appoint adapter_appoint=new MyAdapter_appoint(this, getData());

        listView.setAdapter(adapter_appoint);
    }

    private ArrayList<Data_Appoint> getData() {
        ArrayList<Data_Appoint> data=new ArrayList<>();

        ArrayList<String> doctors= new ArrayList<>();
        doctors=db.getdocname(login_id);
        ArrayList<String> notes= new ArrayList<>();
        notes=db.getnotes(login_id);
        ArrayList<String> dates= new ArrayList<>();
        dates=db.getdate(login_id);
        ArrayList<String> times= new ArrayList<>();
        times=db.gettime(login_id);
        for(int i=0;i<doctors.size();i++){
            Data_Appoint d = new Data_Appoint(doctors.get(i),notes.get(i),dates.get(i),times.get(i));
            data.add(d);
        }
        return data;
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.dash:
                intent=new Intent(getApplicationContext(),Nav_Page.class);
                intent.putExtra("ID", login_id);
                customType(appointment.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.wr:
                intent=new Intent(getApplicationContext(),weekly_report.class);
                intent.putExtra("ID", login_id);
                customType(appointment.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.doctors:
                intent=new Intent(getApplicationContext(),Doctors.class);
                intent.putExtra("ID", login_id);
                customType(appointment.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.appointments:
                intent=new Intent(getApplicationContext(), appointment.class);
                intent.putExtra("ID", login_id);
                customType(appointment.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.medstock:
                intent=new Intent(getApplicationContext(), stock.class);
                intent.putExtra("ID", login_id);
                customType(appointment.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.settings:
                intent=new Intent(getApplicationContext(),Settings.class);
                intent.putExtra("ID", login_id);
                customType(appointment.this,"right-to-left");
                startActivity(intent);
                break;
            //ONE MORE PAGE OF THE MEDICAL STORE PAGE
            case R.id.logout :
                intent=new Intent(getApplicationContext(),MainActivity.class);
                customType(appointment.this,"right-to-left");
                startActivity(intent);
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void add_appointment(View view){
        Intent intent=new Intent(getApplicationContext(),addappointment.class);
        intent.putExtra("ID", login_id);
        customType(appointment.this,"rotateout-to-rotatein");
        startActivity(intent);
    }
}