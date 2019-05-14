package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.LabTransactionDatum;
import com.mcura.jaideep.queuemanagement.Model.PharmacyTransactionDatum;
import com.mcura.jaideep.queuemanagement.R;

import java.util.List;

/**
 * Created by mCURA1 on 8/18/2017.
 */

public class LabSavedDataAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    Context mContext;
    List<LabTransactionDatum> labTransactionDataList;
    public LabSavedDataAdapter(Context context, List<LabTransactionDatum> labTransactionDataList) {
        mContext = context;
        this.labTransactionDataList = labTransactionDataList;


        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return labTransactionDataList.size();
    }

    @Override
    public LabTransactionDatum getItem(int position) {
        return labTransactionDataList.get(position);
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

        LabTransactionDatum labTransactionDatum = getItem(position);
        holder.tv_orderby.setText("Presc. By: "+labTransactionDatum.getDocName());
        holder.tv_order_id.setText("Ord. Id."+labTransactionDatum.getOrderId().toString());
        int orderedAmount = 0;
        for(int i=0;i<labTransactionDatum.getTransactions().size();i++){
            orderedAmount += labTransactionDatum.getTransactions().get(i).getOrderedAmount();
        }
        holder.tv_total_order_amount.setText(orderedAmount+"");
        holder.tv_ordered_date.setText(labTransactionDatum.getPrescribedDate().toString());
        holder.pharmacy_saved_data_rd_select_data.setChecked(labTransactionDatum.isStatus());
        return convertView;
    }

    public class ViewHolder {
        RadioButton pharmacy_saved_data_rd_select_data;
        TextView tv_orderby, tv_order_id, tv_total_order_amount, tv_ordered_date;
    }
}

