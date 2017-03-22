package com.example.info3.attendancemanager;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Employees_Frag extends Fragment {

    ListView listView;
    Database database;
    Cursor cursor;


    public Employees_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employees, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.emp_listview);
        database = new Database(getActivity());
        cursor = database.empGetListDetails();
        String columns[]={database.helper.Emp_COLUMN_ID,database.helper.Emp_COLUMN_NAME,database.helper.Emp_COLUMN_PROFILE,database.helper.Emp_COLUMN_DATE,database.helper.Emp_COLUMN_MOB};
        int viewid[] = {R.id.emp_listview_sno,R.id.emp_listview_name,R.id.emp_listview_profile,R.id.emp_listview_date,R.id.emp_listview_mob,
        };


        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),R.layout.emp_listview_item,cursor,columns,viewid,0);
        listView.setAdapter(simpleCursorAdapter);

    }
}
