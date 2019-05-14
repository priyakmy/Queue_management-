package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mcura.jaideep.queuemanagement.Model.DoctorNature;
import com.mcura.jaideep.queuemanagement.R;

import java.util.List;

/**
 * Created by mCURA1 on 10/5/2016.
 */
public class DoctorNatureAdapter  extends BaseAdapter {
    Context context;
    private
    List<DoctorNature> values;
    LayoutInflater inflater;

    public DoctorNatureAdapter(Context context, List<DoctorNature> doctorNatureList) {
        this.context = context;
        this.values = doctorNatureList;
        DoctorNature doctorNature = new DoctorNature();
        doctorNature.setApp_nature_id("0");
        doctorNature.setApp_nature("Select Nature");
        values.add(0, doctorNature);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public DoctorNature getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            //inflate your customlayout for the textview
            row = inflater.inflate(R.layout.nature_raw_layout, parent, false);
        }
        //put the data in it
        String item = values.get(position).getApp_nature().toString();
        Log.d("item", item);
        TextView text1 = (TextView) row.findViewById(R.id.tv_doctorName);
        text1.setText(item.toString());
        return row;
    }
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            //inflate your customlayout for the textview
            row = inflater.inflate(R.layout.nature_raw_layout, parent, false);
        }
        //put the data in it
        String item = values.get(position).getApp_nature().toString();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.tv_doctorName);
            text1.setText(item);
        }

        return row;
    }
}
