package com.example.info3.attendancemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by info3 on 23-02-2017.
 */
public class Database {
    SQLiteDBHelper helper;

    public Database(Context context) {

        this.helper = new SQLiteDBHelper(context);
    }


    static class SQLiteDBHelper extends SQLiteOpenHelper {
        private final Context context;

        private static final String DATABASE_NAME = "info.db";
        private static final int DATABASE_VERSION = 1;

        // USerTable Column Names
        public static final String User_TABLE_NAME = "user";
        public static final String User_COLUMN_ID = "userid";
        public static final String User_COLUMN_FULLNAME = "fullname";
        public static final String User_COLUMN_EMAIL = "email";
        public static final String User_COLUMN_PASSWORD = "password";


        private static final String USER_CREATE_TABLE_QUERY =
                "CREATE TABLE " + User_TABLE_NAME + " (" +
                        User_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        User_COLUMN_FULLNAME + " TEXT, " +
                        User_COLUMN_EMAIL + " TEXT, " +
                        User_COLUMN_PASSWORD + " TEXT" + ")";

        //Employee Table Coulmn Names and TableName
        public static final String Emp_TABLE_NAME = "employee";
        public static final String Emp_COLUMN_ID = "_id";
        public static final String Emp_COLUMN_NAME = "empname";
        public static final String Emp_COLUMN_MOB = "empmob";
        public static final String Emp_COLUMN_DATE = "empstartdate";
        public static final String Emp_COLUMN_PROFILE = "Jobprofile";

        private static final String EMP_CREATE_TABLE_QUERY =
                "CREATE TABLE " + Emp_TABLE_NAME + " (" +
                        Emp_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Emp_COLUMN_NAME + " TEXT, " +
                        Emp_COLUMN_MOB + " TEXT, " +
                        Emp_COLUMN_DATE + " TEXT," +
                        Emp_COLUMN_PROFILE + " TEXT" + ")";

        //Attendances Table
        public static final String Attendance_TABLE_NAME = "attendance";
        public static final String Attendance_COLUMN_ID = "_id";
        public static final String Attendance_COLUMN_DATE = "date";
        public static final String Attendance_COLUMN_STATUS= "status";

        private static final String Attendance_CREATE_TABLE_QUERY =
                "CREATE TABLE " + Attendance_TABLE_NAME + " (" +
                        Attendance_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Attendance_COLUMN_DATE + " TEXT, " +
                        Attendance_COLUMN_STATUS + " INTEGER " + ")";


        public SQLiteDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(USER_CREATE_TABLE_QUERY);
            db.execSQL(EMP_CREATE_TABLE_QUERY);
            db.execSQL(Attendance_CREATE_TABLE_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + User_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Emp_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS" + Attendance_TABLE_NAME);
            onCreate(db);
        }
    }

  public void userInsertData(String fullName, String email, String password) {
      //Log.d("vishnu","Called Insert()");
      SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(helper.User_COLUMN_FULLNAME, fullName);
        values.put(helper.User_COLUMN_EMAIL, email);
        values.put(helper.User_COLUMN_PASSWORD, password);
        long id = db.insert(helper.User_TABLE_NAME, null, values);
       /* if(id<0)
        {
            Log.d("vishnu","Error insert");
        }
        else Log.d("vishnu","Insert success");*/
    }

    public  Cursor User_getdetails(String email,String password){
        //Log.d("vishnu","Called getdetails()");
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={helper.User_COLUMN_FULLNAME,helper.User_COLUMN_EMAIL};
        String selectionargs[] = {email,password};
        Cursor cursor = db.query(helper.User_TABLE_NAME,columns,helper.User_COLUMN_EMAIL+"=? AND "+helper.User_COLUMN_PASSWORD+"=?",
                selectionargs,null,null,null);
        return cursor;
    }

    public void empInsertData(String eName,String eMob,String eDate,String eProfile){
        SQLiteDatabase db = helper.getWritableDatabase();
        Log.d("vishnu",eProfile+" Insert() called");
        ContentValues contentValues = new ContentValues();
        contentValues.put(helper.Emp_COLUMN_NAME,eName);
        contentValues.put(helper.Emp_COLUMN_MOB,eMob);
        contentValues.put(helper.Emp_COLUMN_DATE,eDate);
        contentValues.put(helper.Emp_COLUMN_PROFILE,eProfile);
        long id = db.insert(helper.Emp_TABLE_NAME,null,contentValues);
        if(id<0)
        {
            Log.d("vishnu","Error insert");
        }
        else {

            Log.d("vishnu","Insert success");
            Toast.makeText(helper.context,"Employee Added",Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor empGetListDetails(){
        Cursor cursor=null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={helper.Emp_COLUMN_ID,helper.Emp_COLUMN_NAME,helper.Emp_COLUMN_PROFILE,helper.Emp_COLUMN_DATE,helper.Emp_COLUMN_MOB};
        cursor = db.query(helper.Emp_TABLE_NAME,columns,null, null,null,null,null);
        return cursor;
    }

    public Cursor empGetHomeDetail(){
        Cursor cursor =null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={helper.Emp_COLUMN_ID,helper.Emp_COLUMN_NAME};
        cursor=db.query(helper.Emp_TABLE_NAME,columns,null,null,null,null,null);
        return  cursor;
    }

    public void markAttendence(int id,int status,String date){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(helper.Attendance_COLUMN_ID,id);
        contentValues.put(helper.Attendance_COLUMN_DATE,date);
        contentValues.put(helper.Attendance_COLUMN_STATUS,status);
        long aid=db.insert(helper.Attendance_TABLE_NAME,null,contentValues);
        if(aid<0)
        {
            Log.d("vishnu","not marked");
        }
        else
            Toast.makeText(helper.context,"marked",Toast.LENGTH_SHORT).show();
    }

    public Cursor getattendece(int status,String Date)
    {
        Cursor cursor = null;

        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={helper.Attendance_COLUMN_ID};
        String selectionargs[] = { String.valueOf(status),Date};
        cursor = db.query(helper.Attendance_TABLE_NAME,columns,helper.Attendance_COLUMN_STATUS+"=? AND "+helper.Attendance_COLUMN_DATE+"=?",
                selectionargs,null,null,null);
        return cursor;
    }
}