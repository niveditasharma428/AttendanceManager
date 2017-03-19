package com.example.info3.attendancemanager;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nivedita on 16-03-2017.
 */

public class listViewAdapter extends ArrayAdapter<Data_Model> {
    Context context;
    int layoutID;
    Data_Model data[] = null;

    public listViewAdapter(Context ncontext,int nlayoutID,Data_Model[] data)
    {
        super(ncontext,nlayoutID,data);
        context = ncontext;
        layoutID = nlayoutID;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitem = convertView;
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        listitem = layoutInflater.inflate(layoutID,parent,false);

        ImageView imageViewIcon = (ImageView) listitem.findViewById(R.id.rowIcon);
        TextView textViewName = (TextView) listitem.findViewById(R.id.rowText);

        Data_Model dm = data[position];

        imageViewIcon.setImageResource(dm.icons);
        textViewName.setText(dm.name);

        return listitem;

    }
}

