package com.example.designproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import static maes.tech.intentanim.CustomIntent.customType;

public class Doctors extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    int login_id;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        login_id = getIntent().getIntExtra("ID",0);
        this.configureDrawerLayout();
        this.configureNavigationView();

        Button button1=findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=near by doctors" ));
                Intent chooser = Intent.createChooser(intent, "Near By Doctors");
                startActivity(chooser);
            }
        });

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
                customType(Doctors.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.wr:
                intent=new Intent(getApplicationContext(),weekly_report.class);
                intent.putExtra("ID", login_id);
                customType(Doctors.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.doctors:
                intent=new Intent(getApplicationContext(),Doctors.class);
                intent.putExtra("ID", login_id);
                customType(Doctors.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.appointments:
                intent=new Intent(getApplicationContext(), appointment.class);
                intent.putExtra("ID", login_id);
                customType(Doctors.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.medstock:
                intent=new Intent(getApplicationContext(), stock.class);
                intent.putExtra("ID", login_id);
                customType(Doctors.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.settings:
                intent=new Intent(getApplicationContext(),Settings.class);
                intent.putExtra("ID", login_id);
                customType(Doctors.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.medstore:
                intent=new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=nearby medical store" ));
                Intent chooser = Intent.createChooser(intent, "Nearby Medical Store");
                startActivity(chooser);
                break;
            //ONE MORE PAGE OF THE MEDICAL STORE PAGE
            case R.id.logout :
                intent=new Intent(getApplicationContext(),MainActivity.class);
                customType(Doctors.this,"right-to-left");
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
