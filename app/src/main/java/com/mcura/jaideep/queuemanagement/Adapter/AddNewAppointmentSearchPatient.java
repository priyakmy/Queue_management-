package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;

/**
 * Created by mCURA1 on 9/14/2017.
 */

public class AddNewAppointmentSearchPatient extends BaseAdapter {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<SearchPatientModel> values;
    SearchPatientModel searchPatientModel;
    LayoutInflater inflater;
    public AddNewAppointmentSearchPatient(Context context, int textViewResourceId,
                             SearchPatientModel values) {
        this.context = context;
        this.searchPatientModel = values;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

            row = inflater.inflate(R.layout.patient_list_row_layout, parent, false);
        }
        //put the data in it
        String patName = searchPatientModel.getData().get(position).getPatName();
        String patRegNumber = searchPatientModel.getData().get(position).getHospitalNo();
        if (patName != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_patient_name);
            text1.setText(patName);
            TextView text2 = (TextView) row.findViewById(R.id.txt_patient_registration_no);
            text2.setText(patRegNumber);
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
            row = inflater.inflate(R.layout.patient_list_row_layout, parent, false);
        }
        //put the data in it
        String patName = searchPatientModel.getData().get(position).getPatName();
        String patRegNumber = searchPatientModel.getData().get(position).getHospitalNo();
        if (patName != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_patient_name);
            text1.setText(patName);
            TextView text2 = (TextView) row.findViewById(R.id.txt_patient_registration_no);
            text2.setText(patRegNumber);
        }

        return row;
    }
}
