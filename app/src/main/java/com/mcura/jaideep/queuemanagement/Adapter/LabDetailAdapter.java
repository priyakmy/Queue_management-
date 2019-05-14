package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.LabOrderStatusGetModel;
import com.mcura.jaideep.queuemanagement.Model.PharmacyOrderGetModel;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mCURA1 on 3/11/2017.
 */

public class LabDetailAdapter extends BaseAdapter {
    ArrayList<LabOrderStatusGetModel> values;
    ArrayList<LabOrderStatusGetModel> orig;
    //PharmacyModel[] pharmacyModels;
    private Context mContext;
    private LayoutInflater mInflater = null;
    public LabDetailAdapter(Context context, LabOrderStatusGetModel[] models) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.values = new ArrayList<>(Arrays.asList(models));
        this.orig = new ArrayList<>(Arrays.asList(models));
    }

    @Override
    public int getCount() {
        return orig.size();
    }

    @Override
    public LabOrderStatusGetModel getItem(int position) {
        return orig.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LabDetailAdapter.ViewHolder viewHolder = null;

        View v = convertView;
        if (v == null) {
            v = mInflater.inflate(R.layout.lab_detail__row_layout, parent, false);
            viewHolder = new LabDetailAdapter.ViewHolder();
            viewHolder.tv_est_id = (TextView) v.findViewById(R.id.tv_lab_est_id);
            viewHolder.tv_amount = (TextView) v.findViewById(R.id.tv_lab_amount);
            viewHolder.tv_order_id = (TextView) v.findViewById(R.id.tv_lab_order_id);
            //viewHolder.btn_lab_pay = (Button) v.findViewById(R.id.btn_lab_pay);
            v.setTag(viewHolder);
        } else {
            viewHolder = (LabDetailAdapter.ViewHolder) v.getTag();
        }
        LabOrderStatusGetModel pharmacyModels = orig.get(position);
        viewHolder.tv_est_id.setText(pharmacyModels.getESTBillno().toString());
        viewHolder.tv_amount.setText(pharmacyModels.getAmount().toString());
        viewHolder.tv_order_id.setText(pharmacyModels.getLaborderId().toString());
        // return the created view
        return v;

    }

    private class ViewHolder {
        TextView tv_est_id, tv_amount,tv_order_id;
        Button btn_lab_pay;
    }


}


