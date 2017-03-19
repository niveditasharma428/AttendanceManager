package com.example.info3.attendancemanager;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class LoginSuccess extends AppCompatActivity {
    UserSessionManager userSessionManager;

    private String[] NavigationDrawerItemTitles;
    private ListView DrawerList;
    Toolbar toolbar;
    private CharSequence DrawerTitle;
    private CharSequence Title;
    DrawerLayout drawer;
    ActionBarDrawerToggle nDrawerToggle;

    String Name = "Nivi sharma";
    String Email = "abc@gmail.com";
    int Profile = R.drawable.profile;
    Intent intent = null;

    //Employee Strings
    String Job_profile,Emp_name,Emp_Mob,Emp_date;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText ename, emo, date;
    Spinner spinner;
    Button ADD_Emp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new Home_Frag()).commit();

        userSessionManager = new UserSessionManager(this);

        Title = DrawerTitle = getTitle();
        NavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);

        DrawerList = (ListView) findViewById(R.id.listView);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        Data_Model[] dataModels = new Data_Model[5];

        dataModels[0] = new Data_Model((R.drawable.ic_home_black_24dp), "Home");
        dataModels[1] = new Data_Model((R.drawable.ic_person_black_24dp), "Employees");
        dataModels[2] = new Data_Model((R.drawable.ic_mail_black_24dp), "Email Us");
        dataModels[3] = new Data_Model((R.drawable.ic_contact_mail_black_24dp), "Contact Us");
        dataModels[4] = new Data_Model((R.drawable.ic_mail_black_24dp), "About Us");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        ViewGroup header = (ViewGroup) getLayoutInflater().inflate(R.layout.header, null);
        DrawerList.addHeaderView(header);

        listViewAdapter listViewAdapter = new listViewAdapter(this, R.layout.item_row, dataModels);
        DrawerList.setAdapter(listViewAdapter);
        DrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        //Drawer
        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        nDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
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

    private void selectItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Home_Frag();
                break;
            case 1:
                fragment = new Home_Frag();
                break;
            case 2:

                fragment = new Employees_Frag();
                break;
            case 3:
                fragment = new Email_UsFrag();
                break;
            case 4:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:8285517996"));
                startActivity(callIntent);
            case 5:
                fragment = new AboutUs();
                break;
            default:
                fragment = new Home_Frag();
        }

        if (fragment != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // Highlight the selected item has been done by NavigationView
            DrawerList.setItemChecked(position, true);
            DrawerList.setSelection(position);

            // Set action bar title
            getSupportActionBar().setTitle(NavigationDrawerItemTitles[position]);
            // Close the navigation drawer
            drawer.closeDrawer(DrawerList);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        nDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_Logout: {
                userSessionManager.EndSession();
                //To Remove from stack to prevent back button
                finish();
                return true;
            }
            case R.id.action_user:
                add_employee();
        }
        return super.onOptionsItemSelected(item);
    }

    void add_employee() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mview = getLayoutInflater().inflate(R.layout.add_employee, null);
        ename = (EditText) mview.findViewById(R.id.emp_name);
        emo = (EditText) mview.findViewById(R.id.empmob);

        ADD_Emp = (Button) mview.findViewById(R.id.btn_empAdd);

        date = (EditText) mview.findViewById(R.id.emp_date);
        spinner = (Spinner) mview.findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Job_profile = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinner_list));
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(LoginSuccess.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Emp_date = date.getText().toString();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        ADD_Emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Database Insert

                Emp_name = ename.getText().toString();
                Emp_Mob = emo.getText().toString();

            }
        });
        builder.setView(mview);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

