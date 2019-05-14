package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Activity.DoctorScheduleActivity;
import com.mcura.jaideep.queuemanagement.Model.DoctorListModel;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by GauraVachhani on 16/11/15.
 */
public class DoctorSpinnerAdapter extends ArrayAdapter<DoctorListModel> implements Filterable {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<DoctorListModel> values;
    private ArrayList<DoctorListModel> orig;
    LayoutInflater inflater;
    public DoctorSpinnerAdapter(Context context, int textViewResourceId,
                                DoctorListModel[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = new ArrayList<>(Arrays.asList(values));

        DoctorListModel mDoctorRecord = new DoctorListModel();
        mDoctorRecord.setUserRoleId(0);
        mDoctorRecord.setUserName("Select Doctor");

        this.values.add(0, mDoctorRecord);

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

    public DoctorListModel getItem(int position){
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
        String item = values.get(position).getUserName();
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
        String item = values.get(position).getUserName();
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
                    final ArrayList<DoctorListModel> results = new ArrayList<DoctorListModel>();
                    if (orig == null)
                        orig = values;
                    if (constraint != null) {
                        if (orig != null && orig.size() > 0) {
                            for (final DoctorListModel g : orig) {
                                if (g.getUserName().toLowerCase()
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
                    values = (ArrayList<DoctorListModel>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

}