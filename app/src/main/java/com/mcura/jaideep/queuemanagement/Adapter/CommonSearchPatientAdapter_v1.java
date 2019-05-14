package com.mcura.jaideep.queuemanagement.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mcura.jaideep.queuemanagement.Activity.AddNewAppointment;
import com.mcura.jaideep.queuemanagement.Activity.LoadNFC;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.PatientContactDetails;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mCURA1 on 3/1/2017.
 */

public class CommonSearchPatientAdapter_v1 extends RecyclerView.Adapter<CommonSearchPatientAdapter_v1.ViewHolder> {
    private ImageLoader mImageLoader;
    public MCuraApplication mCuraApplication;
    ProgressDialog progressDialog;
    Context context;
    String ageS;
    int userRoleId;
    int hospitalSubtanentId;
    SearchPatientModel patientSearchModelsArray[];
    SearchPatientModel patientSearchModel;
    PatientContactDetails patientContactDetails;
    int subTanentId;
    List<PatientSearchModel> patientSearchModelsList = new ArrayList<>();

    public CommonSearchPatientAdapter_v1(Context context, SearchPatientModel patientSearchModelsArray, int userRoleId, int hospitalSubtanentId) {
        this.hospitalSubtanentId = hospitalSubtanentId;
        this.userRoleId = userRoleId;
        this.context = context;
        this.patientSearchModel = patientSearchModelsArray;
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        subTanentId = sharedPreferences.getInt(Constant.SUB_TANENT_ID_KEY, 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, age, mrno, hospital_id;
        public ImageView profileImage, load_card, edit_patient;
        public TextView callPat;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            age = (TextView) v.findViewById(R.id.age);
            profileImage = (ImageView) v.findViewById(R.id.profile_image);
            load_card = (ImageView) v.findViewById(R.id.load_card);
            edit_patient = (ImageView) v.findViewById(R.id.edit_pat);
            callPat = (TextView) v.findViewById(R.id.call_patient);
            mrno = (TextView) v.findViewById(R.id.mrno);
            hospital_id = (TextView) v.findViewById(R.id.hospital_id);

        }
    }

    @Override
    public CommonSearchPatientAdapter_v1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        CommonSearchPatientAdapter_v1.ViewHolder vh = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_patient_activity_row_layout, null);
        vh = new CommonSearchPatientAdapter_v1.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CommonSearchPatientAdapter_v1.ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return patientSearchModel.getData().size();
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


