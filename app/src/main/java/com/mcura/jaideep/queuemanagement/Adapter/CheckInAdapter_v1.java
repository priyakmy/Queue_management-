package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mCURA1 on 2/23/2017.
 */

public class CheckInAdapter_v1 extends BaseAdapter {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<SearchPatientModel> values;
    SearchPatientModel searchPatientModel;
    LayoutInflater inflater;
    public CheckInAdapter_v1(Context context, int textViewResourceId,
                             SearchPatientModel values) {
        this.context = context;
        this.searchPatientModel = values;
        //this.values = new ArrayList<>(Arrays.asList(values));
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //PatientSearchModel mVitalRecord = new PatientSearchModel();
        //this.values.add(mVitalRecord);
    }


    public int getCount(){
        return searchPatientModel.getData().size();
    }

    public Datum getItem(int position){
        return searchPatientModel.getData().get(position);
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

            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = searchPatientModel.getData().get(position).getPatName();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(item);
        }

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
            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = searchPatientModel.getData().get(position).getPatName();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(item);
        }

        return row;
    }
}