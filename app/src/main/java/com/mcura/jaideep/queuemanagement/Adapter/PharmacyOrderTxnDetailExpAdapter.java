package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.PharmacyOrderTransactionDetail.Datum;
import com.mcura.jaideep.queuemanagement.Model.PharmacyOrderTransactionDetail.TxnDetail;
import com.mcura.jaideep.queuemanagement.R;

import java.util.List;

public class PharmacyOrderTxnDetailExpAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<Datum> pharmacyTxnData;
    TextView doctorFee;
    public PharmacyOrderTxnDetailExpAdapter(Context context, List<Datum> pharmacyTxnData,TextView doctorFee) {
        mContext = context;
        this.doctorFee = doctorFee;
        this.pharmacyTxnData = pharmacyTxnData;
    }

    @Override
    public int getGroupCount() {
        return pharmacyTxnData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return pharmacyTxnData.get(i).getTxnDetails().size();
    }

    @Override
    public Datum getGroup(int i) {
        return pharmacyTxnData.get(i);
    }

    @Override
    public TxnDetail getChild(int groupPosition, int childPosition) {
        return pharmacyTxnData.get(groupPosition).getTxnDetails().get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void OnIndicatorClick(boolean isExpanded, int position) {

    }

    public void OnTextClick() {

    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.pharmacy_ord_txn_detail_group_layout, viewGroup, false);
        }
        final Datum datum = getGroup(groupPosition);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        tvDate.setText(datum.getOrderedDate().toString());
        TextView tvTxnId = (TextView) convertView.findViewById(R.id.tvTxnId);
        tvTxnId.setText("Txn Id: "+datum.getOrdTxnId().toString());
        TextView tvTxnOrdAmount = (TextView) convertView.findViewById(R.id.tvTxnOrdAmount);
        tvTxnOrdAmount.setText(datum.getOrderedAmount() + "");
        ImageView ivIndicator = (ImageView) convertView.findViewById(R.id.ivIndicator);
        ivIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIndicatorClick(isExpanded, groupPosition);
            }
        });
        final RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radioButton);
        radioButton.setChecked(datum.isStatus());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!radioButton.isChecked()){
                    for(int i=0;i<pharmacyTxnData.size();i++){
                        pharmacyTxnData.get(i).setStatus(false);
                    }
                    pharmacyTxnData.get(groupPosition).setStatus(true);
                    notifyDataSetChanged();
                }
            }
        });
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for(int i=0;i<pharmacyTxnData.size();i++){
                        pharmacyTxnData.get(i).setStatus(false);
                    }
                    pharmacyTxnData.get(groupPosition).setStatus(true);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.pharmacy_ord_txn_detail_child_layout, viewGroup, false);
        }
        TxnDetail txnDetail = getChild(groupPosition, childPosition);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(txnDetail.getMedName());
        TextView tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
        tvAmount.setText(txnDetail.getMedAmount() + "");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
