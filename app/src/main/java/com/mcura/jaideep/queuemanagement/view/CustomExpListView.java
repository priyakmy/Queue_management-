package com.mcura.jaideep.queuemanagement.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;

import com.mcura.jaideep.queuemanagement.Activity.CentralizedBillingActivity;


public class CustomExpListView extends ExpandableListView
{
    Context context;
    public CustomExpListView(Context context)
    {
        super(context);
        this.context = context;
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((CentralizedBillingActivity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(20000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

