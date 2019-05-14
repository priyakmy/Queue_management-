package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mcura.jaideep.queuemanagement.Model.DepartmentModel;
import com.mcura.jaideep.queuemanagement.R;

import java.util.List;

/**
 * Created by mCURA1 on 10/5/2016.
 */
public class DoctorDepartmentAdapter  extends BaseAdapter {
    Context context;
    private
    List<DepartmentModel> values;
    LayoutInflater inflater;

    public DoctorDepartmentAdapter(Context context, List<DepartmentModel> doctorDepartmentList) {
        this.context = context;
        this.values = doctorDepartmentList;
        DepartmentModel doctorDepartment = new DepartmentModel();
        doctorDepartment.setDept_id("0");
        doctorDepartment.setDept_name("Select Department");
        values.add(0, doctorDepartment);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public DepartmentModel getItem(int position) {
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
        String item = values.get(position).getDept_name().toString();
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
        String item = values.get(position).getDept_name().toString();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.tv_doctorName);
            text1.setText(item);
        }

        return row;
    }
}
