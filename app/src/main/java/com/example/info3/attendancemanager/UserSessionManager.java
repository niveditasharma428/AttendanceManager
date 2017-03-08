package com.example.info3.attendancemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Nivedita on 08-03-2017.
 */

public class UserSessionManager {

      //shared pref reference
      SharedPreferences sharedPreferences;

      //Editor reference for shared pref
      SharedPreferences.Editor editor;

      //context
      Context context;

     //shared pref mode
     int Private_Mode = 0;

    //shared pref file name
    private static  final String PREFER_NAME = "UserSession";

    //all shared pref keys
    private static  final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static  final String EMAIL = "Email";

    public static  final String PASSWORD = "Password";

    //constructor
    public UserSessionManager(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREFER_NAME,Private_Mode);
        this.editor = sharedPreferences.edit();
    }

    //create login session
    public  void  CreateLoginSession(String Email,String password)
    {
        //storing login value as true
        editor.putBoolean(IS_USER_LOGIN,true);

        //storing email as true
        editor.putString(EMAIL,Email);

        editor.putString(PASSWORD,password);

        editor.commit();
    }

    //check user login status

    public boolean IsUserLoggedIn(){
        return sharedPreferences.getBoolean(IS_USER_LOGIN,false);
    }

    public boolean checkLogin(){
        if(!IsUserLoggedIn()){
            Intent i = new Intent(context,MainActivity.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
            return   true;
        }
        return false;
    }

    public HashMap<String,String> getUserSession(){

        HashMap<String,String> user = new HashMap<String, String>();

        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));

        user.put(PASSWORD,sharedPreferences.getString(PASSWORD,null));

        return  user;
    }

    public void EndSession()
    {
        editor.clear();
        editor.commit();

        Intent I = new Intent(context,MainActivity.class);

        I.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(I);


    }

}
