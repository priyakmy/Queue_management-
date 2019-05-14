package com.mcura.jaideep.queuemanagement.Adapter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.LabOrderDetailModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.mcura.jaideep.queuemanagement.view.CustomExpListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mCURA1 on 7/17/2017.
 */

public class LabOrderDetailExpandableAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<LabOrderDetailModel> labOrderDetailModels;
    private ArrayList<LabOrderDetailModel> originalList;
    private boolean isAllChecked;
    private SecondLevelAdapter secondLevelAdapter;
    private CheckBox cb_select_row_level_first;
    private MCuraApplication mCuraApplication;
    private ProgressDialog progress;
    AlertDialog mainLabDialog;
    private String paymentMode = "1";
    private String payableAmount;
    private int appNatureId;
    private String paymentDescription;
    private int frontOfficeUserRoleId;
    private JsonArray objectKeyArray;
    private WebView myWebView;
    private String subTanantName;
    private String subTanantAddress;
    private String subTanantContact;
    private String completeDate;
    private String time;
    private int orderStatus;
    private static final int ORDER_ONLY = 1;
    private static final int ORDER_AND_PAY = 2;

    public LabOrderDetailExpandableAdapter(Context context, final ArrayList<LabOrderDetailModel> labOrderDetailModel, AlertDialog mainLabDialog) {
        this.mContext = context;
        this.mainLabDialog = mainLabDialog;
        labOrderDetailModels = labOrderDetailModel;
        originalList = new ArrayList<>();
        for(int i=0;i<labOrderDetailModel.size();i++){
            originalList.add(labOrderDetailModel.get(i));
        }
        objectKeyArray = new JsonArray();
    }

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(mContext);
            progress.setCancelable(false);
            progress.setMessage(mContext.getString(R.string.loading_message));
        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }


    public void selectAll(LabOrderDetailModel headerTitle) {
        headerTitle.setSelected(true);
        for (int i = 0; i < headerTitle.getChildren().size(); i++) {
            headerTitle.getChildren().get(i).setSelected(true);
        }
        notifyDataSetChanged();

    }

    public void unselectAll(LabOrderDetailModel headerTitle) {
        headerTitle.setSelected(false);
        for (int i = 0; i < headerTitle.getChildren().size(); i++) {
            headerTitle.getChildren().get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }


    // This Function used to inflate parent rows view
    public void OnIndicatorClick(boolean isExpanded, int position) {

    }

    public void OnTextClick() {

    }

    @Override
    public int getGroupCount() {
        return labOrderDetailModels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.d("getChildrenCount", labOrderDetailModels.get(groupPosition).getChildren().size() + "");
        return 1;
    }

    @Override
    public LabOrderDetailModel getGroup(int groupPosition) {
        return labOrderDetailModels.get(groupPosition);
    }

    @Override
    public LabOrderDetailModel getChild(int groupPosition, int childPosition) {
        return labOrderDetailModels.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        final LabOrderDetailModel headerTitle = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_group, parent, false);
        }
        cb_select_row_level_first = (CheckBox) convertView.findViewById(R.id.cb_select_row_level_first);
        ImageButton indicator = (ImageButton) convertView.findViewById(R.id.indicator);
        int imageResourceId = isExpanded ? R.mipmap.minus
                : R.mipmap.plus;

        if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNaturePackage || headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureGroup) {
            indicator.setVisibility(View.VISIBLE);
            indicator.setImageResource(imageResourceId);
        } else if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureTest) {
            indicator.setVisibility(View.GONE);
        }

        indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIndicatorClick(isExpanded, groupPosition);
            }
        });
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        TextView tv_test_cost = (TextView) convertView.findViewById(R.id.tv_test_cost);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getLabTestName());
        tv_test_cost.setText(headerTitle.getLabTestCost().toString());

        cb_select_row_level_first.setChecked(headerTitle.isSelected());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cb_click", groupPosition + "");
                if (!headerTitle.isSelected()) {
                    headerTitle.setSelected(true);
                } else {
                    headerTitle.setSelected(false);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d("status", "true");
        final CustomExpListView secondLevelExpListView = new CustomExpListView(this.mContext);
        ArrayList<LabOrderDetailModel> parentNode = labOrderDetailModels.get(groupPosition).getChildren();
        secondLevelAdapter = new SecondLevelAdapter(this.mContext, parentNode, cb_select_row_level_first, groupPosition) {
            @Override
            public void OnIndicatorClick(boolean isExpanded, int position) {
                if (isExpanded) {
                    secondLevelExpListView.collapseGroup(position);
                } else {
                    secondLevelExpListView.expandGroup(position);
                }
            }

            @Override
            public void OnTextClick() {
                //Do whatever you want to do on text click
            }
        };
        secondLevelExpListView.setAdapter(secondLevelAdapter);
        secondLevelExpListView.setGroupIndicator(null);
        return secondLevelExpListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class SecondLevelAdapter extends BaseExpandableListAdapter {
        private final Context context;
        ArrayList<LabOrderDetailModel> parentNode;
        public int gp;
        public CheckBox cb_select_row_level_first;

        public SecondLevelAdapter(Context context, ArrayList<LabOrderDetailModel> parentNode, CheckBox cb_select_row_level_first, int gp) {

            this.context = context;
            this.parentNode = parentNode;
            this.gp = gp;
            this.cb_select_row_level_first = cb_select_row_level_first;
        }

        @Override
        public LabOrderDetailModel getChild(int groupPosition, int childPosition) {
            return parentNode.get(groupPosition).getChildren().get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            LabOrderDetailModel childText = getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.drawer_list_item, parent, false);
            }
            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);
            txtListChild.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            txtListChild.setText(childText.getLabTestName());
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            try {
                return parentNode.get(groupPosition).getChildren().size();
            } catch (Exception e) {
                return 0;
            }
        }

        @Override
        public LabOrderDetailModel getGroup(int groupPosition) {
            return parentNode.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return parentNode.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        // This Function used to inflate parent rows view
        public void OnIndicatorClick(boolean isExpanded, int position) {

        }

        public void OnTextClick() {

        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            final LabOrderDetailModel headerTitle = getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.drawer_list_group_second, parent, false);
            }
            CheckBox cb_select_row_level_second = (CheckBox) convertView.findViewById(R.id.cb_select_row_level_second);
            ImageButton indicator = (ImageButton) convertView.findViewById(R.id.indicator);
            int imageResourceId = isExpanded ? R.mipmap.minus
                    : R.mipmap.plus;

            if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNaturePackage || headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureGroup) {
                indicator.setVisibility(View.VISIBLE);
                indicator.setImageResource(imageResourceId);
            } else if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureTest) {
                indicator.setVisibility(View.GONE);
            }

            indicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnIndicatorClick(isExpanded, groupPosition);
                }
            });
            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            TextView tv_test_cost_second = (TextView) convertView.findViewById(R.id.tv_test_cost_second);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getLabTestName());
            tv_test_cost_second.setText(headerTitle.getLabTestCost().toString());
            cb_select_row_level_second.setChecked(headerTitle.isSelected());
            cb_select_row_level_second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (headerTitle.isSelected() == true) {
                        headerTitle.setSelected(false);
                        LabOrderDetailExpandableAdapter.this.labOrderDetailModels.get(gp).setSelected(false);
                    } else {
                        headerTitle.setSelected(true);
                        for (int i = 0; i < parentNode.size(); i++) {
                            if (parentNode.get(i).isSelected() == false) {
                                break;
                            } else {
                                if ((parentNode.size() - 1) == i) {
                                    LabOrderDetailExpandableAdapter.this.labOrderDetailModels.get(gp).setSelected(true);
                                }
                            }
                        }
                    }
                    LabOrderDetailExpandableAdapter.this.notifyDataSetChanged();
                }
            });

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public void filterData(String query) {
        query = query.toLowerCase();
        Log.d("found",query);
        labOrderDetailModels.clear();
        if (query.isEmpty()) {
            Log.d("found","in empty");
            labOrderDetailModels.addAll(originalList);
        } else {
            Log.d("found","in else"+originalList.size());
            for (LabOrderDetailModel continent : originalList) {
                Log.d("found","in for");
                if (continent.getLabTestName().toLowerCase().startsWith(query)) {
                    Log.d("found","yes");
                    labOrderDetailModels.add(continent);
                }
            }
        }
        notifyDataSetChanged();
    }

}
