package com.example.info3.attendancemanager;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    UserSessionManager userSessionManager;

    Database database;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencing UserEmail, Password EditText and TextView for SignIn
        final EditText _txtemail = (EditText) findViewById(R.id.email);
        final EditText _txtpass = (EditText) findViewById(R.id.paswd);
        Button _btnlogin = (Button) findViewById(R.id.btnsignin);
        TextView _btnreg = (TextView) findViewById(R.id.btnreg);

        userSessionManager = new UserSessionManager(this);
        database = new Database(this);

        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = _txtemail.getText().toString();
                String pass = _txtpass.getText().toString();

                if(email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    _txtemail.setError("invaild email");

                if (pass.isEmpty()||!(pass.length()>8&&pass.length()<13))
                  _txtpass.setError("password must be 8 and 13 characters long");

                    return ;
                }

                //Log.d("vishnu",email + " "+ pass+"Calling.... getdetails");
                //Getuserdetails
                cursor = database.User_getdetails(email,pass);

                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        //Retrieving User FullName and Email after successfull login and passing to LoginSucessActivity
                        String _fname = cursor.getString(cursor.getColumnIndex(database.helper.User_COLUMN_FULLNAME));
                        String _email = cursor.getString(cursor.getColumnIndex(database.helper.User_COLUMN_EMAIL));
                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, LoginSuccess.class);
                        intent.putExtra("fullname", _fname);
                        intent.putExtra("email", _email);
                        userSessionManager.CreateLoginSession(_fname,_email,pass);
                        startActivity(intent);
                        //Removing MainActivity[Login Screen] from the stack for preventing back button press.
                        finish();
                    } else {
                        //I am showing Alert Dialog Box here for alerting user about wrong credentials
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Alert");
                        builder.setMessage("Username or Password is wrong.");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        //-------Alert Dialog Code Snippet End Here
                    }
                }
            }
        });

        // Dialog For Opening rEGISTER DIALOG
        _btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etname, etemail, etpass;
                Button btn;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.activity_register_account, null);
                etname = (EditText) mview.findViewById(R.id.name);
                etemail = (EditText) mview.findViewById(R.id.reg_email);
                etpass = (EditText) mview.findViewById(R.id.reg_pswd);
                btn = (Button) mview.findViewById(R.id.btn_reg);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String fullname = etname.getText().toString();
                        String email = etemail.getText().toString();
                        String pass = etpass.getText().toString();

                        //Checking Input validation
                        if(fullname.isEmpty()||fullname.length()<3){
                            etname.setError("Length should be greater than 3");

                        if(email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                            etemail.setError("invaild email");

                        if (pass.isEmpty()||!(pass.length()>8&&pass.length()<13))
                            etpass.setError("password must be 8 and 13 characters long");
                            return ;
                        }

                        //Log.d("vishnu",fullname+" "+email + " "+ pass);
                        //Calling InsertData Method - Defined below
                        database.userInsertData(fullname, email, pass);
                        //Alert dialog after clicking the Register Account
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Information");
                        builder.setMessage("Your Account is Successfully Created.");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Finishing the dialog and removing Activity from stack.
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
                builder.setView(mview);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}