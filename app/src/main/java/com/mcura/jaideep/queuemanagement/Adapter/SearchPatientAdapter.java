package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


import com.mcura.jaideep.queuemanagement.Activity.VisitingActivity;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.R;

/**
 * Created by GauraVachhani on 16/11/15.
 */
public class SearchPatientAdapter extends ArrayAdapter<PatientSearchModel> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<PatientSearchModel> values;

    public SearchPatientAdapter(Context context, int textViewResourceId,
                                PatientSearchModel[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = new ArrayList<>(Arrays.asList(values));

        //PatientSearchModel mVitalRecord = new PatientSearchModel();
        //this.values.add(mVitalRecord);
    }


    public int getCount(){
        return values.size();
    }

    public PatientSearchModel getItem(int position){
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
            LayoutInflater inflater = ((VisitingActivity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = values.get(position).getPatname();
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
            LayoutInflater inflater = ((VisitingActivity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = values.get(position).getPatname();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(item);
        }

        return row;
    }
}