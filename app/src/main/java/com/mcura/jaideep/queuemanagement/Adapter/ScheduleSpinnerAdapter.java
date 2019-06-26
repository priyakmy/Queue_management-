package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Activity.DoctorScheduleActivity;
import com.mcura.jaideep.queuemanagement.Model.ScheduleModel;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by GauraVachhani on 16/11/15.
 */
public class ScheduleSpinnerAdapter extends ArrayAdapter<ScheduleModel> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<ScheduleModel> values;

    public ScheduleSpinnerAdapter(Context context, int textViewResourceId,
                                ScheduleModel[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = new ArrayList<>(Arrays.asList(values));

        ScheduleModel mDoctorRecord = new ScheduleModel();
        mDoctorRecord.setScheduleId(0);
        mDoctorRecord.setFromTime("0:00");
        mDoctorRecord.setToTime("0:00");

        this.values.add(0,mDoctorRecord);


        /*Collections.sort(this.values, new Comparator<Doctor>() {
            @Override
            public int compare(Doctor lhs, VitalRecordsModel rhs) {
                return Integer.valueOf(lhs.getVitalNatureId()).compareTo(rhs.getVitalNatureId());
            }
        });*/
    }


    public int getCount(){
        return values.size();
    }

    public ScheduleModel getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        View row = convertView;
        if (row == null) {
            //inflate your customlayout for the textview
            LayoutInflater inflater = ((DoctorScheduleActivity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        //String item = values.get(position).getUname();
        //if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(values.get(position).getFromTime()+" to "+values.get(position).getToTime());
        //}

        return row;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            //inflate your customlayout for the textview
            LayoutInflater inflater = ((DoctorScheduleActivity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        //String item = values.get(position).getUname();
        //if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(values.get(position).getFromTime()+" to "+values.get(position).getToTime());
        //}

        return row;
    }
}