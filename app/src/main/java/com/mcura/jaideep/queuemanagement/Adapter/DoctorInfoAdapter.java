package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.AvialbaleTokenListbydate;
import com.mcura.jaideep.queuemanagement.Model.DoctorListModel;
import com.mcura.jaideep.queuemanagement.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mCURA1 on 7/25/2016.
 */
public class DoctorInfoAdapter extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater mInflater;
    AvialbaleTokenListbydate[] avialbaleTokenListbydates;
    private ArrayList<AvialbaleTokenListbydate> values;
    private ArrayList<AvialbaleTokenListbydate> orig;
    private ValueFilter valueFilter;
    public DoctorInfoAdapter(Context context, AvialbaleTokenListbydate[] avialbaleTokenListbydates) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        values = new ArrayList<>(Arrays.asList(avialbaleTokenListbydates));
        orig = new ArrayList<>(Arrays.asList(avialbaleTokenListbydates));
        getFilter();
    }

    @Override
    public int getCount() {
        return orig.size();
    }

    @Override
    public AvialbaleTokenListbydate getItem(int position) {
        return orig.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.doctor_info_row_layout, null);
            holder.doctor_pic = (ImageView) convertView.findViewById(R.id.doctor_pic);
            holder.doc_name = (TextView) convertView.findViewById(R.id.doc_name);
            holder.doc_department = (TextView) convertView.findViewById(R.id.doc_department);
            holder.doc_location = (TextView) convertView.findViewById(R.id.doc_location);
            holder.doc_schedule_time = (TextView) convertView.findViewById(R.id.doc_schedule_time);
            holder.doc_msg = (TextView) convertView.findViewById(R.id.doc_msg);
            holder.current_token_number = (TextView) convertView.findViewById(R.id.current_token_number);
            holder.next_token_number = (TextView) convertView.findViewById(R.id.next_token_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AvialbaleTokenListbydate model = orig.get(position);
        holder.doc_name.setText(model.getDoctorName());
        holder.doc_location.setText(model.getSchedule().getScheduleName());
        holder.doc_schedule_time.setText(model.getSchedule().getFromTime()+" To "+model.getSchedule().getToTime());
        holder.doc_name.setText(model.getDoctorName());
        holder.current_token_number.setText(model.getSchedule().getCurrentTokenNo()+"");
        holder.next_token_number.setText(model.getSchedule().getNextTokenNo()+"");
        if(model.getSchedule().getMessage()!=""){
            holder.doc_msg.setText(model.getSchedule().getMessage());
        }else{
            holder.doc_msg.setText("No Message Available");
        }
        if(model.getDept()!=null){
            holder.doc_department.setText(model.getDept());
        }else{
            holder.doc_department.setText("Department");
        }

        String profile_pic ="http://test.tn.mcura.com/"+model.getImage();
        Picasso.with(context).load(profile_pic).placeholder(R.drawable.doctor_img).into(holder.doctor_pic);
        return convertView;
    }

    private class ValueFilter extends Filter {
        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {

                ArrayList<AvialbaleTokenListbydate> filterList = new ArrayList<AvialbaleTokenListbydate>();

                for (int i = 0; i < values.size(); i++) {

                    if (values.get(i).getDoctorName().toLowerCase().contains(constraint)) {

                        filterList.add(values.get(i));

                    }
                }


                results.count = filterList.size();

                results.values = filterList;

            } else {

                results.count = values.size();

                results.values = values;

            }

            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      Filter.FilterResults results) {

            orig = (ArrayList<AvialbaleTokenListbydate>) results.values;

            notifyDataSetChanged();
        }


    }

    public class ViewHolder {
        ImageView doctor_pic;
        TextView doc_name,doc_department,doc_location,doc_schedule_time,doc_msg,current_token_number,next_token_number;
    }
    @Override
    public Filter getFilter() {

        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }
}
