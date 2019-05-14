package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.LocalBillModel;
import com.mcura.jaideep.queuemanagement.R;

import java.util.List;

/**
 * Created by mCURA1 on 10/17/2016.
 */
public class LocalBillStatusAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    private Context mContext;
    List<LocalBillModel> localBillModels;
    public LocalBillStatusAdapter(Context context,List<LocalBillModel> localBillModels) {
        this.mContext = context;
        this.localBillModels = localBillModels;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("listsizeAdapter",localBillModels.size()+"");
    }

    @Override
    public int getCount() {
        return localBillModels.size();
    }

    @Override
    public LocalBillModel getItem(int position) {
        return localBillModels.get(position);
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
            convertView = mInflater.inflate(R.layout.bill_status_layout, null);
            //holder.patient_pic = (ImageView) convertView.findViewById(R.id.patient_pic);
            holder.bill_no = (TextView) convertView.findViewById(R.id.bill_no);
            holder.patient_name = (TextView) convertView.findViewById(R.id.patient_name);
            holder.doctor_name = (TextView) convertView.findViewById(R.id.doctor_name);
            holder.doctor_department = (TextView) convertView.findViewById(R.id.doc_department);
            holder.nature = (TextView) convertView.findViewById(R.id.nature_name);
            holder.patient_bill = (TextView) convertView.findViewById(R.id.doctor_fee);
            holder.img_upload_status = (ImageView) convertView.findViewById(R.id.img_upload_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LocalBillModel model = localBillModels.get(position);
        holder.doctor_name.setText(model.getDoctor_name());
        holder.doctor_department.setText(model.getDepartment());
        holder.bill_no.setText(model.getBill_no());
        holder.patient_name.setText(model.getPatient_name());
        holder.nature.setText(model.getFee_nature());
        if(model.getFee_nature().equals("Refund")){
            holder.patient_bill.setText("-" + model.getFee());
        }else{
            holder.patient_bill.setText(model.getFee());
        }

        if (model.getBill_status().equals("1")){
            holder.img_upload_status.setImageResource(R.drawable.upload_blue);
        }else if (model.getBill_status().equals("0")){
            holder.img_upload_status.setImageResource(R.drawable.upload_red);
        }
        return convertView;
    }
    public class ViewHolder{
        ImageView img_upload_status;
        TextView doctor_name,doctor_department,patient_name,nature,patient_bill,bill_no;
    }
}
