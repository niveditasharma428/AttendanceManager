package com.example.info3.attendancemanager;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LoginSuccess extends AppCompatActivity {

    String Title[]={"Home","Holidays","Add Holiday","Contact Us","Email Us"};
    int Icons[]={R.drawable.ic_home_black_24dp,R.drawable.ic_holiday_black_24dp,R.drawable.ic_add_black_24dp,R.drawable.ic_contact_phone_black_24dp,
    R.drawable.ic_contact_mail_black_24dp};

    String Name = "Nivi sharma";
    String Email="abc@gmail.com";
    int Profile = R.drawable.profile;

    private Toolbar toolbar;

    RecyclerView nRecycleview;
    RecyclerView.Adapter nAdapter;
    RecyclerView.LayoutManager nLayoutManager;
    DrawerLayout drawer;

    ActionBarDrawerToggle nDrawerToggle;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        nRecycleview = (RecyclerView) findViewById(R.id.Recycleview);
        nRecycleview.setHasFixedSize(true);

        nAdapter = new MyAdapter(Title,Icons,Name,Email,Profile);

        nRecycleview.setAdapter(nAdapter);

        nLayoutManager = new LinearLayoutManager(this);

        nRecycleview.setLayoutManager(nLayoutManager);

        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        nDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.addDrawerListener(nDrawerToggle);
        nDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_settings)
        {
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}


