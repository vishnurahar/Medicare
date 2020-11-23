package com.example.designproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class weekly_report extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    int login_id;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    DatabaseHelper db;
    ListView replistView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        login_id = getIntent().getIntExtra("ID",0);
        db=new DatabaseHelper(this);
        this.configureDrawerLayout();
        this.configureNavigationView();
        replistView =findViewById(R.id.weeklistview);
        MyAdapter_week adapter_week=new MyAdapter_week(this, getreportData());

        replistView.setAdapter(adapter_week);
    }

    private ArrayList<Data_week> getreportData() {
        ArrayList<Data_week> data=new ArrayList<>();

        ArrayList<String> medreport= new ArrayList<>();
        medreport=db.getmedfromreport(login_id);

        ArrayList<String> datereport= new ArrayList<>();
        datereport=db.getdatefromreport(login_id);

        ArrayList<String> timereport= new ArrayList<>();
        timereport=db.gettimefromreport(login_id);

        ArrayList<String> swareport= new ArrayList<>();
        swareport=db.getswafromreport(login_id);

        for(int i=0;i<medreport.size();i++){
            Data_week d = new Data_week(medreport.get(i),datereport.get(i),timereport.get(i),swareport.get(i));
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
                customType(weekly_report.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.wr:
                intent=new Intent(getApplicationContext(),weekly_report.class);
                intent.putExtra("ID", login_id);
                customType(weekly_report.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.doctors:
                intent=new Intent(getApplicationContext(),Doctors.class);
                intent.putExtra("ID", login_id);
                customType(weekly_report.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.appointments:
                intent=new Intent(getApplicationContext(), appointment.class);
                intent.putExtra("ID", login_id);
                customType(weekly_report.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.medstock:
                intent=new Intent(getApplicationContext(), stock.class);
                intent.putExtra("ID", login_id);
                customType(weekly_report.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.settings:
                intent=new Intent(getApplicationContext(),Settings.class);
                intent.putExtra("ID", login_id);
                customType(weekly_report.this,"right-to-left");
                startActivity(intent);
                break;
            //ONE MORE PAGE OF THE MEDICAL STORE PAGE
            case R.id.medstore:
                intent=new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=nearby medical store" ));
                Intent chooser = Intent.createChooser(intent, "Nearby Medical Store");
                startActivity(chooser);
                break;
            case R.id.logout :
                intent=new Intent(getApplicationContext(),MainActivity.class);
                customType(weekly_report.this,"right-to-left");
                finish();
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

}