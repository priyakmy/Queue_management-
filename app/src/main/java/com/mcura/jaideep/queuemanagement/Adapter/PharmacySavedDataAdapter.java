package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.AvialbaleTokenListbydate;
import com.mcura.jaideep.queuemanagement.Model.GetPharmacyTransactionsByMrnoSubtenantModel;
import com.mcura.jaideep.queuemanagement.Model.PharmacyTransactionDatum;
import com.mcura.jaideep.queuemanagement.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mCURA1 on 8/17/2017.
 */

public class PharmacySavedDataAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    Context mContext;
    List<PharmacyTransactionDatum> pharmacyTransactionDataList;
    public PharmacySavedDataAdapter(Context context, List<PharmacyTransactionDatum> pharmacyTransactionDataList) {
        mContext = context;
        this.pharmacyTransactionDataList = pharmacyTransactionDataList;


        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pharmacyTransactionDataList.size();
    }

    @Override
    public PharmacyTransactionDatum getItem(int position) {
        return pharmacyTransactionDataList.get(position);
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
            convertView = mInflater.inflate(R.layout.pharmacy_saved_data_row_layput, null);
            holder.pharmacy_saved_data_rd_select_data = (RadioButton) convertView.findViewById(R.id.pharmacy_saved_data_rd_select_data);
            holder.tv_orderby = (TextView) convertView.findViewById(R.id.tv_orderby);
            holder.tv_order_id = (TextView) convertView.findViewById(R.id.tv_order_id);
            holder.tv_total_order_amount = (TextView) convertView.findViewById(R.id.tv_total_order_amount);
            holder.tv_ordered_date = (TextView) convertView.findViewById(R.id.tv_ordered_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PharmacyTransactionDatum pharmacyTransactionDatum = getItem(position);
        holder.tv_orderby.setText("Presc. By: "+pharmacyTransactionDatum.getDocName());
        holder.tv_order_id.setText("Ord. Id."+pharmacyTransactionDatum.getOrderId().toString());
        int orderedAmount = 0;
        for(int i=0;i<pharmacyTransactionDatum.getTransactions().size();i++){
            orderedAmount += pharmacyTransactionDatum.getTransactions().get(i).getOrderedAmount();
        }
        holder.tv_total_order_amount.setText(orderedAmount+"");
        holder.tv_ordered_date.setText(pharmacyTransactionDatum.getPrescribedDate().toString());
        holder.pharmacy_saved_data_rd_select_data.setChecked(pharmacyTransactionDatum.isStatus());
        return convertView;
    }

    public class ViewHolder {
        RadioButton pharmacy_saved_data_rd_select_data;
        TextView tv_orderby, tv_order_id, tv_total_order_amount, tv_ordered_date;
    }
}
