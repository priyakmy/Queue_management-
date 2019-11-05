package com.mcura.jaideep.queuemanagement.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.Activity.CalendarActivity;
import com.mcura.jaideep.queuemanagement.Activity.CheckInActivity;
import com.mcura.jaideep.queuemanagement.Activity.QueueStatusActivity;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.PostActivityTrackerModel.PostActivityTrackerModel;
import com.mcura.jaideep.queuemanagement.Model.QueueStatus;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.EnumType;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mcura on 5/6/2016.
 */
public class Queue_Adapter extends BaseAdapter {

    private final int scheduleId;
    private int frontOfficeUserRoleId;
    private String buildVersionName;
    private int subTanentId;
    private int user_role_id;
    private SharedPreferences mSharedPreference;
    ArrayList<Object> itemList;

    public Context context;
    public LayoutInflater inflater;
    QueueStatus[] queueStatusArray;
    List<QueueStatus> list;
    private ProgressDialog progressDialog;
    private String tokenStatus="";
    private MCuraApplication mCuraApplication;
    private int actTransactId=0;
    private AlertDialog successAlertDialog;
    private String isDelete_Unblock;
    private int tokenNo=0;

    public Queue_Adapter(Context context, List<QueueStatus> list,int scheduleId) {
        this.context = context;
        this.list = list;
        this.scheduleId = scheduleId;
        Log.d("list_size",list.size()+"");
        mSharedPreference = context.getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        user_role_id = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        buildVersionName = mSharedPreference.getString(Constant.BUILD_VERSION_NAME, "");
        subTanentId=mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY,0);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
    }


    static class ViewHolder {
        ImageView statusImg;
        TextView txtViewNo;
        TextView txtViewName;
        TextView txtViewMrno;
        TextView txtViewStatus;
        TextView txtViewHospitalNo;
        public ViewHolder(View v) {
            statusImg = (ImageView) v.findViewById(R.id.Chk_img);
            txtViewNo = (TextView) v.findViewById(R.id.tv_No);
            txtViewName = (TextView) v.findViewById(R.id.tv_Name);
            txtViewMrno = (TextView) v.findViewById(R.id.tv_Mrno);
            txtViewStatus = (TextView) v.findViewById(R.id.tv_status);
            txtViewHospitalNo = (TextView) v.findViewById(R.id.tv_hospital_no);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public QueueStatus getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final QueueStatus model = list.get(position);
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.items, null);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.txtViewNo.setText(model.getTokenNo().toString());
        if (model.getHospitalNo() != null) {
            if (!model.getHospitalNo().toString().equals("")) {
                holder.txtViewHospitalNo.setText(model.getHospitalNo() + "");
            } else {
                holder.txtViewHospitalNo.setVisibility(View.GONE);
            }
        } else {
            holder.txtViewHospitalNo.setVisibility(View.GONE);
        }
        if (model.getPatName() != null) {
            holder.txtViewName.setText(model.getPatName().toString());
        }
        if (model.getMRNo() != null) {
            holder.txtViewMrno.setText(model.getMRNo().toString());
        }
        if (model.getTokenStatus() != null) {
            String tokenStatus = model.getTokenStatus().toString();
            //holder.txtViewStatus.setText(tokenStatus);
            if (tokenStatus.equals("CHECK_IN")) {
                holder.statusImg.setImageResource(R.drawable.check_in);
                holder.txtViewStatus.setText("CHECK IN");
            }
            if (tokenStatus.equals("PRE_BOOKED")) {
                holder.statusImg.setImageResource(R.drawable.pre_booked);
                holder.txtViewStatus.setText("PRE BOOKED");
            }
            if (tokenStatus.equals("CURRENTLY_VISITING")) {
                holder.statusImg.setImageResource(R.drawable.visiting);
                holder.txtViewStatus.setText("CURRENTLY VISITING");
            }
            if (tokenStatus.equals("CHECK_OUT")) {
                holder.statusImg.setImageResource(R.drawable.complete);
                holder.txtViewStatus.setText("CHECK OUT");
            }
            if (tokenStatus.equals("BLOCKED")) {
                holder.statusImg.setImageResource(R.drawable.status_block);
                holder.txtViewStatus.setText("BLOCKED");
                row.setBackgroundColor(context.getResources().getColor(R.color.bg_norm));
            }
            if (tokenStatus.equals("NO_SHOW")) {
                holder.statusImg.setImageResource(R.mipmap.ic_no_show);
                holder.txtViewStatus.setText("NO SHOW");
            }
        }
            final ViewHolder finalHolder = holder;
            holder.txtViewNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model.getTokenStatus() != null) {
                        tokenStatus = model.getTokenStatus().toString();
                        if (tokenStatus.equals("BLOCKED")) {
                            finalHolder.txtViewNo.setClickable(true);
                            tokenNo = Integer.parseInt(finalHolder.txtViewNo.getText().toString());
                            isDelete_Unblock = "true";
                            //Toast.makeText(Queue_status.this,txtViewNo.getText().toString(),Toast.LENGTH_LONG).show();
                            int appId = model.getAppId();
                            actTransactId = EnumType.ActTransactMasterEnum.Unblock_APT.getActTransactMasterTypeId();
                            setBlockApi(appId);
                        }
                    } else {
                        finalHolder.txtViewNo.setClickable(true);
                        tokenNo = Integer.parseInt(finalHolder.txtViewNo.getText().toString());
                        isDelete_Unblock = "false";
                        int appId = model.getAppId();
                        //Toast.makeText(Queue_status.this,txtViewNo.getText().toString(),Toast.LENGTH_LONG).show();
                        actTransactId = EnumType.ActTransactMasterEnum.Block_APT.getActTransactMasterTypeId();
                        setBlockApi(appId);
                    }
                }
            });
            return row;
    }
    public void setBlockApi(final int appId) {
        JsonObject blockPatient = new JsonObject();
        blockPatient.addProperty("appointmentid", appId);
        blockPatient.addProperty("UserRoleID", user_role_id);
        blockPatient.addProperty("isDelete_Unblock", isDelete_Unblock);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.updateAppointment_Block(blockPatient, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                if(isDelete_Unblock.equals("true")){
                    postActivityTrackerFromAPI(0,appId);
                }else if(isDelete_Unblock.equals("false")){
                    showSuccessDialog("Appointment Blocked Successfully.",appId);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showSuccessDialog(String msg, final int appId) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Success");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                postActivityTrackerFromAPI(0,appId);
                successAlertDialog.dismiss();
            }
        });
        successAlertDialog = builder.show();
    }
    private void postActivityTrackerFromAPI(int deliveryStatus, final int appointmentid) {
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("actBuildVersion",buildVersionName);
        obj.addProperty("delivered",deliveryStatus);
        obj.addProperty("actUserRoleId",frontOfficeUserRoleId);
        obj.addProperty("actSubTenantId",subTanentId);
        obj.addProperty("actScheduleId",scheduleId);
        obj.addProperty("actAppId",appointmentid);
        obj.addProperty("actUserMediumId",9);
        obj.addProperty("drUserRoleId",user_role_id);
        obj.addProperty("actRemarks","");
        obj.addProperty("actTransMasterId",actTransactId);
        obj.addProperty("patMrno",0);
        obj.addProperty("actOthers","");

        mCuraApplication.getInstance().mCuraEndPoint.postActivityTracker(obj, new Callback<PostActivityTrackerModel>() {
            @Override
            public void success(PostActivityTrackerModel postActivityTrackerModel, Response response) {
                if(isDelete_Unblock.equals("true")){
                    Intent in=new Intent(context,CheckInActivity.class).putExtra("TokenNo",tokenNo).putExtra("appId",appointmentid);
                    context.startActivity(in);
                }else if(isDelete_Unblock.equals("false")){
                    ((QueueStatusActivity)context).getQueueData();
                }
                dismissLoadingDialog();
            }
            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }
    /**
     *
     */
    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(context.getString(R.string.loading_message));
        }
        progressDialog.show();
    }

    /**
     *
     */
    public void dismissLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
