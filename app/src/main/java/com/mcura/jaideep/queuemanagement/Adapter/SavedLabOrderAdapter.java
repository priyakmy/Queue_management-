package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.LabOrderDetailModel;
import com.mcura.jaideep.queuemanagement.Model.LabTransactionDatum;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;

/**
 * Created by mCURA1 on 4/24/2018.
 */

public class SavedLabOrderAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    ArrayList<LabOrderDetailModel> labOrderDetailModelArrayList;
    public SavedLabOrderAdapter(Context context,ArrayList<LabOrderDetailModel> labOrderDetailModelArrayList){
        mContext = context;
        this.labOrderDetailModelArrayList = labOrderDetailModelArrayList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return labOrderDetailModelArrayList.size();
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
            convertView = mInflater.inflate(R.layout.save_lab_order_row_layout, null);
            holder.tv_testname = (TextView) convertView.findViewById(R.id.tv_testname);
            holder.tv_test_cost = (TextView) convertView.findViewById(R.id.tv_test_cost);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LabOrderDetailModel labOrderDetailModel = labOrderDetailModelArrayList.get(position);
        holder.tv_testname.setText(labOrderDetailModel.getLabTestName());
        holder.tv_test_cost.setText(labOrderDetailModel.getLabTestCost().toString());
        return convertView;
    }
    public class ViewHolder {
        TextView tv_testname, tv_test_cost;
    }

}
