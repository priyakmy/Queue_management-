package com.mcura.jaideep.queuemanagement.Model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;

public class RecNatureListAdapter  extends ArrayAdapter<GetMedicalRecordNatureModel> implements Filterable {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<GetMedicalRecordNatureModel> getMedicalRecordNatureModelArrayList;
    private ArrayList<GetMedicalRecordNatureModel> orig;

    LayoutInflater inflater;

    public RecNatureListAdapter(Context context, int textViewResourceId, ArrayList<GetMedicalRecordNatureModel> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.getMedicalRecordNatureModelArrayList = values;
        //this.values.remove(0);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public int getCount() {
        return getMedicalRecordNatureModelArrayList.size();
    }

    public GetMedicalRecordNatureModel getItem(int position) {
        return getMedicalRecordNatureModelArrayList.get(position);
    }

    public long getItemId(int position) {
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
        String item = getMedicalRecordNatureModelArrayList.get(position).getRecNatureProperty();
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
        String item = getMedicalRecordNatureModelArrayList.get(position).getRecNatureProperty();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(item);
        }


        return row;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<GetMedicalRecordNatureModel> results = new ArrayList<GetMedicalRecordNatureModel>();
                if (orig == null)
                    orig = getMedicalRecordNatureModelArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (GetMedicalRecordNatureModel g : orig) {
                            if (g.getRecNatureProperty().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                getMedicalRecordNatureModelArrayList = (ArrayList<GetMedicalRecordNatureModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}
