package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.DiscountModel;
import com.mcura.jaideep.queuemanagement.Model.DocAccountListModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.helper.Utility;

import java.util.ArrayList;

public class DiscountAmountAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private Context mContext;
    ArrayList<DiscountModel> discountModelArrayList;
    TextView tvPayableAmount, tvDiscountedAmount;
    TextView doctorFee;
    ListView listviewDiscountAmount;
    public DiscountAmountAdapter(Context context, ArrayList<DiscountModel> discountModelArrayList, TextView tvPayableAmount, TextView tvDiscountedAmount, TextView doctorFee, ListView listviewDiscountAmount) {
        this.mContext = context;
        this.tvPayableAmount =tvPayableAmount;
        this.tvDiscountedAmount =tvDiscountedAmount;
        this.doctorFee =doctorFee;
        this.listviewDiscountAmount = listviewDiscountAmount;
        this.discountModelArrayList = discountModelArrayList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return discountModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.discount_amount_row_layout, null);
            holder.ivDelDiscount = (ImageView) convertView.findViewById(R.id.ivDelDiscount);
            holder.tvDiscountName = (TextView) convertView.findViewById(R.id.tvDiscountName);
            holder.tvDiscountPersentage = (TextView) convertView.findViewById(R.id.tvDiscountPersentage);
            holder.tvDiscountAmount = (TextView) convertView.findViewById(R.id.tvDiscountAmount);
            holder.tvDiscountedAmount = (TextView) convertView.findViewById(R.id.tvDiscountedAmount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DiscountModel discountModel = discountModelArrayList.get(i);
        holder.tvDiscountName.setText(discountModel.getDiscountName());
        if(discountModel.getDiscountPercantage()!=null){
            holder.tvDiscountPersentage.setVisibility(View.VISIBLE);
            holder.tvDiscountAmount.setVisibility(View.GONE);
            holder.tvDiscountPersentage.setText(discountModel.getDiscountPercantage()+"%");
        }else{
            holder.tvDiscountPersentage.setVisibility(View.GONE);
            holder.tvDiscountAmount.setVisibility(View.VISIBLE);
            holder.tvDiscountAmount.setText("₹"+discountModel.getDiscountAmount());
        }
        holder.tvDiscountedAmount.setText("₹"+discountModel.getDiscountedAmount()+"/-");
        holder.ivDelDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double discountedAmount=0.0;
                discountModelArrayList.remove(i);
                for(int i=0;i<discountModelArrayList.size();i++){
                    discountedAmount+=Double.parseDouble(discountModelArrayList.get(i).getDiscountedAmount());
                }
                tvDiscountedAmount.setText(discountedAmount+"");
                tvPayableAmount.setText((Double.parseDouble(doctorFee.getText().toString())-discountedAmount)+"");
                notifyDataSetChanged();
                Utility.setListViewHeightBasedOnItems(listviewDiscountAmount);
            }
        });
        return convertView;
    }
    public class ViewHolder{
        ImageView ivDelDiscount;
        TextView tvDiscountName,tvDiscountPersentage,tvDiscountAmount,tvDiscountedAmount;
    }
}
