package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.AppointmentNature;
import com.mcura.jaideep.queuemanagement.Model.GenerateTokenResultModel;
import com.mcura.jaideep.queuemanagement.Model.GetNatureByUserRoleModel;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mCURA1 on 8/30/2016.
 */
public class GetNatureByUserRoleIdAdapter extends ArrayAdapter<GetNatureByUserRoleModel> {
    Context context;
    private ArrayList<GetNatureByUserRoleModel> values;
    LayoutInflater inflater;
    public GetNatureByUserRoleIdAdapter(Context context, int textViewResourceId,
                         GetNatureByUserRoleModel[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = new ArrayList<>(Arrays.asList(values));

        GetNatureByUserRoleModel natureRecord = new GetNatureByUserRoleModel();
        natureRecord.setAppNatureID(0);
        natureRecord.setAppNature("Select Nature");

        this.values.add(0, natureRecord);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public GetNatureByUserRoleModel getItem(int position){
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

            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = values.get(position).getAppNature();
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
            row = inflater.inflate(R.layout.layout_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = values.get(position).getAppNature();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(item);
        }

        return row;
    }
}
