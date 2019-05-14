package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.DocAccountListModel;
import com.mcura.jaideep.queuemanagement.R;


/**
 * Created by mCURA1 on 7/29/2016.
 */
public class BillDetailAdapter extends BaseAdapter {
    Context context;
    LayoutInflater mInflater;
    DocAccountListModel[] docAccountListModels;
    public BillDetailAdapter(Context context, DocAccountListModel[] docAccountListModels) {
        this.context = context;
        this.docAccountListModels = docAccountListModels;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return docAccountListModels.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.bill_detail_row_layout, null);
            //holder.patient_pic = (ImageView) convertView.findViewById(R.id.patient_pic);
            holder.bill_sr_no = (TextView) convertView.findViewById(R.id.bill_sr_no);
            holder.patient_name = (TextView) convertView.findViewById(R.id.patient_name);
            holder.patient_age = (TextView) convertView.findViewById(R.id.patient_age);
            holder.patient_sex = (TextView) convertView.findViewById(R.id.patient_sex);
            holder.patient_checkup_date = (TextView) convertView.findViewById(R.id.patient_checkup_date);
            holder.nature = (TextView) convertView.findViewById(R.id.nature);
            holder.patient_bill_payment = (TextView) convertView.findViewById(R.id.patient_bill_payment);
            holder.is_bill_paid = (TextView) convertView.findViewById(R.id.is_bill_paid);
            holder.txtViewHospitalNo = (TextView) convertView.findViewById(R.id.tv_hospital_no);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DocAccountListModel model = docAccountListModels[position];
        holder.patient_name.setText(model.getPatName());
        holder.patient_age.setText(model.getAge().toString()+"Yr");
        int genderId = model.getGenderId();
        if(genderId == 1){
            holder.patient_sex.setText("M");
        }else if(genderId == 2){
            holder.patient_sex.setText("F");
        }
        holder.bill_sr_no.setText(position+1+"");
        holder.patient_checkup_date.setText(model.getBillDate().toString());
        holder.patient_bill_payment.setText(model.getAmount().toString());
        holder.nature.setText(model.getPymtNature());
        if (model.getHospitalNo() != null) {
            if (!model.getHospitalNo().toString().equals("")) {
                holder.txtViewHospitalNo.setText(model.getHospitalNo() + "");
            }else{
                holder.txtViewHospitalNo.setVisibility(View.GONE);
            }
        }else{
            holder.txtViewHospitalNo.setVisibility(View.GONE);
        }
        int paymentStatus = model.getPaymentStatus();
        if(model.getPymtNature()!=null) {
            if (model.getPymtNature().equals("Refund")) {
                if (paymentStatus == 1) {
                    holder.is_bill_paid.setText("Refund");
                    holder.is_bill_paid.setBackgroundResource(R.drawable.paid_bg);
                } else if (paymentStatus == 0) {
                    holder.is_bill_paid.setText("Due");
                    holder.is_bill_paid.setBackgroundResource(R.drawable.paid_bg);
                }
            } else {
                if (paymentStatus == 1) {
                    holder.is_bill_paid.setText("Paid");
                    holder.is_bill_paid.setBackgroundResource(R.drawable.paid_bg);
                } else if (paymentStatus == 0) {
                    holder.is_bill_paid.setText("Due");
                    holder.is_bill_paid.setBackgroundResource(R.drawable.paid_bg);
                }
            }
        }
        return convertView;
    }
    public class ViewHolder{
        //ImageView patient_pic;
        TextView patient_name,patient_age,patient_sex,patient_checkup_date,nature,patient_bill_payment,is_bill_paid,bill_sr_no;
        TextView txtViewHospitalNo;
    }
}
