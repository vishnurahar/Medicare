package com.example.designproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static maes.tech.intentanim.CustomIntent.customType;

public class Nav_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<NavItem> navItems;

    private ArrayList<String> showingmeds;
    //FOR DESIGN
    private RecyclerView mRecyclerView;
    private NavAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Button addmedbtn;
    int login_id;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__page);

        db=new DatabaseHelper(this);
        showingmeds=new ArrayList<>();
        login_id = getIntent().getIntExtra("ID",0);
        this.configureDrawerLayout();
        this.configureNavigationView();
        addListenerOnButton();
        createdata();
        buildRecyclerView();
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
                customType(Nav_Page.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.wr:
                intent=new Intent(getApplicationContext(),weekly_report.class);
                intent.putExtra("ID", login_id);
                customType(Nav_Page.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.doctors:
                intent=new Intent(getApplicationContext(),Doctors.class);
                intent.putExtra("ID", login_id);
                customType(Nav_Page.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.appointments:
                intent=new Intent(getApplicationContext(), appointment.class);
                intent.putExtra("ID", login_id);
                customType(Nav_Page.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.medstock:
                intent=new Intent(getApplicationContext(), stock.class);
                intent.putExtra("ID", login_id);
                customType(Nav_Page.this,"right-to-left");
                startActivity(intent);
                break;
            case R.id.settings:
              intent=new Intent(getApplicationContext(),Settings.class);
                intent.putExtra("ID", login_id);
                customType(Nav_Page.this,"right-to-left");
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
                customType(Nav_Page.this,"right-to-left");
                finish();
                startActivity(intent);
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
//    private void configureToolBar(){
//        this.toolbar =  findViewById(R.id.toolbar2);
//        setSupportActionBar(this.toolbar);
//    }

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


    // WORK OF THIS PAGE NAV_PAGE

    public void addListenerOnButton() {

        addmedbtn = (Button) findViewById(R.id.addmedbtn);

        addmedbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getApplicationContext(), AddMedicine.class);
                myIntent.putExtra("ID", login_id);
                customType(Nav_Page.this,"rotateout-to-rotatein");
                startActivity(myIntent);
            }

        });
    }

    public void createdata() {
        navItems=new ArrayList<>();
        ArrayList<String> medicines= new ArrayList<>();
        ArrayList<String> timeone= new ArrayList<>();
        ArrayList<String> timetwo= new ArrayList<>();
        ArrayList<String> timethree= new ArrayList<>();
        medicines=db.getmedicines(login_id);
        timeone=db.gettime1(login_id);
        timetwo=db.gettime2(login_id);
        timethree=db.gettime3(login_id);

        for(int i=0;i<medicines.size();i++){
            if(timeone.get(i).equals("Time")){

            }else{
                NavItem d = new NavItem(medicines.get(i),timeone.get(i));
                navItems.add(d);
                String med=medicines.get(i);
                showingmeds.add(med);
            }
        }
        for(int i=0;i<medicines.size();i++){
            if(timetwo.get(i).equals("Time")){

            }else{
                NavItem d = new NavItem(medicines.get(i),timetwo.get(i));
                navItems.add(d);
                String med=medicines.get(i);
                showingmeds.add(med);
            }
        }
        for(int i=0;i<medicines.size();i++){
            if(timethree.get(i).equals("Time")){

            }else{
                NavItem d = new NavItem(medicines.get(i),timethree.get(i));
                navItems.add(d);
                String med=medicines.get(i);
                showingmeds.add(med);
            }
        }
    }

    public void med_taken(int position){
        db=new DatabaseHelper(this);
        String tickedmed=navItems.get(position).getText1();
        String tickedmedtime=navItems.get(position).getText2();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        String check="✅";
        db.updatetakenmed(login_id,tickedmed);
        Boolean insert_report=db.insert_report(login_id,tickedmed,date,tickedmedtime,check);

        if(insert_report==false){
            Toast.makeText(getApplicationContext(),"Failed to add in report", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Yeah !! Medicine Taken", Toast.LENGTH_SHORT).show();
        }

    }

    public void med_not_taken(int position){
        db=new DatabaseHelper(this);
        String tickedmed=navItems.get(position).getText1();
        String tickedmedtime=navItems.get(position).getText2();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        String check="❌";
        Boolean insert_report=db.insert_report(login_id,tickedmed,date,tickedmedtime,check);

        if(!insert_report){
            Toast.makeText(getApplicationContext(),"Failed to add in report", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"You missed !!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new NavAdapter(navItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new NavAdapter.OnItemclickListener() {
            @Override
            public void onItemClick(int position) {

//                changeItem(position, "Clicked");
            }

            @Override
            public void onTickClick(int position) {
                med_taken(position);
            }

            @Override
            public void onCrossClick(int position) {
                med_not_taken(position);
            }

        });
    }

}