package com.example.info3.attendancemanager;

import android.content.Context;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.text.StringPrepParseException;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Date;


public class Home_Frag extends Fragment {

    ListView listView;
    Database database;
    Cursor listview_cursor,absent_cursor, present_cusror;
    TextView date,absent,present;
    String DAY,DATE;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        database = new Database(getActivity());

        listView = (ListView) getActivity().findViewById(R.id.home_frag_listview);
        absent = (TextView) getActivity().findViewById(R.id.textView6);
        present = (TextView) getActivity().findViewById(R.id.textView8);
        date = (TextView) getActivity().findViewById(R.id.date_day);

        //For Date and Day
        DAY = (String) android.text.format.DateFormat.format("EEEE", new Date());
        DATE = (String) android.text.format.DateFormat.format("dd-MM-yyyy", new Date());
        date.setText(""+DAY+" "+DATE+"");

        listview_cursor = database.empGetHomeDetail();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(getActivity(),listview_cursor,true);
        listView.setAdapter(customCursorAdapter);

        absent_cursor = database.getattendece(0,DATE);
        Log.d("vishnu",String.valueOf(absent_cursor.getCount()));
        absent.setText(String.valueOf(absent_cursor.getCount()));

        present_cusror = database.getattendece(1,DATE);
        Log.d("vishnu",String.valueOf(present_cusror.getCount()));
        present.setText(String.valueOf(present_cusror.getCount()));

    }
}

class CustomCursorAdapter extends CursorAdapter{

    public CustomCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.homefrag_listview_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final Database database = new Database(context);
        final Home_Frag home_frag = new Home_Frag();

        TextView home_id = (TextView) view.findViewById(R.id.home_emp_id);
        final TextView home_empname = (TextView) view.findViewById(R.id.home_emp_name);
        final ImageButton absent = (ImageButton) view.findViewById(R.id.imageButton5);
        final ImageButton present = (ImageButton) view.findViewById(R.id.imageButton4);

        final String string_emp_id = cursor.getString(cursor.getColumnIndexOrThrow(database.helper.Emp_COLUMN_ID));
        String string_emp_name = cursor.getString(cursor.getColumnIndexOrThrow(database.helper.Emp_COLUMN_NAME));
        final String string_date = (String) android.text.format.DateFormat.format("dd-MM-yyyy", new Date());

        home_id.setText(string_emp_id);
        home_empname.setText(string_emp_name);

        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int int_eimp_id = Integer.parseInt(string_emp_id);
                database.markAttendence(int_eimp_id,0,string_date);
                absent.setEnabled(false);
                absent.setVisibility(View.INVISIBLE);
                present.setEnabled(false);
                present.setVisibility(View.INVISIBLE);
            }
        });

        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int int_eimp_id = Integer.parseInt(string_emp_id);
                database.markAttendence(int_eimp_id,1,string_date);
                absent.setEnabled(false);
                absent.setVisibility(View.INVISIBLE);
                present.setEnabled(false);
                present.setVisibility(View.INVISIBLE);
            }
        });

    }
}
