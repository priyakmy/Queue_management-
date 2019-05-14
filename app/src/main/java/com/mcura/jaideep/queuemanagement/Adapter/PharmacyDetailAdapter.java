package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mcura.jaideep.queuemanagement.Activity.OrderBoothActivity;
import com.mcura.jaideep.queuemanagement.Model.LabPharmacyOrderBoothModel;
import com.mcura.jaideep.queuemanagement.Model.PharmacyOrderGetModel;
import com.mcura.jaideep.queuemanagement.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by mCURA1 on 3/11/2017.
 */

public class PharmacyDetailAdapter extends BaseAdapter {
    ArrayList<PharmacyOrderGetModel> values;
    ArrayList<PharmacyOrderGetModel> orig;
    //PharmacyModel[] pharmacyModels;
    private Context mContext;
    private LayoutInflater mInflater = null;
    public PharmacyDetailAdapter(Context context, PharmacyOrderGetModel[] models) {
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
    public PharmacyOrderGetModel getItem(int position) {
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
        ViewHolder viewHolder = null;

        View v = convertView;
        if (v == null) {
            v = mInflater.inflate(R.layout.pharmacy_detail__row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_est_id = (TextView) v.findViewById(R.id.tv_pharmacy_est_id);
            viewHolder.tv_days = (TextView) v.findViewById(R.id.tv_pharmacy_days);
            viewHolder.tv_amount = (TextView) v.findViewById(R.id.tv_pharmacy_amount);
            viewHolder.tv_order_id = (TextView) v.findViewById(R.id.tv_pharmacy_order_id);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        PharmacyOrderGetModel pharmacyModels = orig.get(position);
        viewHolder.tv_est_id.setText(pharmacyModels.getESTBillno().toString());
        viewHolder.tv_days.setText(pharmacyModels.getDays().toString());
        viewHolder.tv_amount.setText(pharmacyModels.getAmount().toString());
        viewHolder.tv_order_id.setText(pharmacyModels.getPresOrderId().toString());
        // return the created view
        return v;

    }

    private class ViewHolder {
        TextView tv_est_id, tv_days, tv_amount,tv_order_id;
        Button btn_pay;
    }


}


