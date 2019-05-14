package com.mcura.jaideep.queuemanagement.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Model.DoctorNature;
import com.mcura.jaideep.queuemanagement.Model.FillCashCardModel;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mCURA1 on 9/21/2017.
 */

public class FillCashCardAdapter extends BaseAdapter {
    Context context;
    private
    ArrayList<FillCashCardModel> fillCashCardModelArrayList;
    LayoutInflater inflater;

    public FillCashCardAdapter(Context context, ArrayList<FillCashCardModel> fillCashCardModelArrayList) {
        this.context = context;
        this.fillCashCardModelArrayList = fillCashCardModelArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fillCashCardModelArrayList.size();
    }

    @Override
    public FillCashCardModel getItem(int position) {
        return fillCashCardModelArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            //inflate your customlayout for the textview
            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = fillCashCardModelArrayList.get(position).getFillType();
        Log.d("item", item);
        TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
        text1.setText(item.toString());
        return row;
    }
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            //inflate your customlayout for the textview
            row = inflater.inflate(R.layout.layout_third_page_spinner_item_row, parent, false);
        }
        //put the data in it
        String item = fillCashCardModelArrayList.get(position).getFillType();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.txt_third_page_spinner_item);
            text1.setText(item);
        }

        return row;
    }
}

