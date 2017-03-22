package com.example.info3.attendancemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by info3 on 22-02-2017.
 */
public class splash extends Activity
{
    UserSessionManager userSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        userSessionManager = new UserSessionManager(this);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                   if(userSessionManager.IsUserLoggedIn()){
                       //if session already exits
                       HashMap<String,String> userdetails = userSessionManager.getUserSession();

                       String email = userdetails.get(UserSessionManager.EMAIL);
                       String Name = userdetails.get(UserSessionManager.NAME);
                       Intent i = new Intent(splash.this,LoginSuccess.class);
                       i.putExtra("email",email);
                       i.putExtra("fullname",Name);
                       startActivity(i);
                   }
                   else{
                       //session does not exists

                       Intent intent = new Intent(splash.this,MainActivity.class);
                       Log.i("Vishnu","running");
                       startActivity(intent);
                       Log.i("Vishnu","running");
                   }
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }




}



