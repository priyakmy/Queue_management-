package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.mcura.jaideep.queuemanagement.Model.DoctorList;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mCURA1 on 10/5/2016.
 */
public class DoctorListAdapter extends BaseAdapter implements Filterable {
    Context context;
    private
    List<DoctorList> values;
    LayoutInflater inflater;
    private List<DoctorList> orig;

    public DoctorListAdapter(Context context, List<DoctorList> doctorLists) {
        this.context = context;
        this.values = doctorLists;
        DoctorList doctorList = new DoctorList();
        doctorList.setUser_role_id("0");
        doctorList.setDocName("Select Doctor");
        doctorList.setDept_id("0");
        values.add(0, doctorList);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public DoctorList getItem(int position) {
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
        String item = values.get(position).getDocName().toString();
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
        String item = values.get(position).getDocName().toString();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.tv_doctorName);
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
                final ArrayList<DoctorList> results = new ArrayList<DoctorList>();
                if (orig == null)
                    orig = values;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final DoctorList g : orig) {
                            if (g.getDocName().toLowerCase()
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
                values = (ArrayList<DoctorList>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
