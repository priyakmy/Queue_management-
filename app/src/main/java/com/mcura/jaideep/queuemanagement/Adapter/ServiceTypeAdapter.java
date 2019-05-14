package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.ServiceType;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceTypeAdapter extends ArrayAdapter<ServiceType> {
    private LayoutInflater inflater;
    private ArrayList<ServiceType> values;
    private Context context;

    public ServiceTypeAdapter(Context context, int textViewResourceId, List<ServiceType> subtenantDetailDataList) {
        super(context,textViewResourceId,subtenantDetailDataList);
        this.context = context;
        this.values = (ArrayList<ServiceType>) subtenantDetailDataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount(){
        return values.size();
    }

    public ServiceType getItem(int position){
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
        String item = values.get(position).getServiceTypeName();
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
        String item = values.get(position).getServiceTypeName();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(item);
        }

        return row;
    }
}
