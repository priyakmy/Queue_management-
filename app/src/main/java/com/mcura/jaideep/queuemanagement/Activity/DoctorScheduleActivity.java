package com.mcura.jaideep.queuemanagement.Activity;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mcura.jaideep.queuemanagement.Adapter.DoctorSpinnerAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.ScheduleSpinnerAdapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.DoctorListModel;
import com.mcura.jaideep.queuemanagement.Model.ScheduleModel;
import com.mcura.jaideep.queuemanagement.Model.UserInfoModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.Helper;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DoctorScheduleActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {
    ListView lv;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    SearchView search_doctor;
    ListView doctor_list;
    private ProgressDialog progressDialog;
    public MCuraApplication mCuraApplication;
    private Spinner doctor_schedule;
    TextView doctor_name;
    ImageButton go_to_chart_btn;
    private SharedPreferences mSharedPreference;
    int userkey, scheduleId;
    DoctorListModel[] doctorArray;
    DoctorSpinnerAdapter doctorSpinnerAdapter;
    int userRoleId, subTenantId;
    int year, month, date;
    String completeDate, from_time, to_time;
    ImageView logout;
    ScheduleModel[] mScheduleArray;
    ScheduleSpinnerAdapter scheduleSpinnerAdapter;
    SharedPreferences.Editor editor;
    int subTanentId;
    String subtanentImagePath;
    private int loginRoleId;
    private DoctorListModel doctorListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_schedule);
        //getSupportActionBar().hide();
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        editor = mSharedPreference.edit();
        subTanentId = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);

        initView();
    }

    public void initView() {
        //userkey = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //getSupportActionBar().setIcon();
        }
        loginRoleId = mSharedPreference.getInt(Constant.LOGIN_ROLE_ID, 0);
        getDoctorDetail();


        doctor_name = (TextView) findViewById(R.id.doctor_name);
        doctor_schedule = (Spinner) findViewById(R.id.doctor_sechedule);
        go_to_chart_btn = (ImageButton) findViewById(R.id.go_to_chart_btn);
        logout = mToolbar.findViewById(R.id.logout);
        //set hospital logo
        subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        ImageView hospital_logo = (ImageView) mToolbar.findViewById(R.id.hospital_logo);
        Picasso.with(DoctorScheduleActivity.this).load(subtanentImagePath).into(hospital_logo);
        /*if(!subtanentImagePath.equals("default")){
            Picasso.with(DoctorScheduleActivity.this).load(subtanentImagePath).into(hospital_logo);
        }else{
            Picasso.with(DoctorScheduleActivity.this).load(R.drawable.kims_logo).into(hospital_logo);
        }*/
        /*if(subTanentId == 500){
            hospital_logo.setImageResource(R.drawable.blk_logo);
        }else if(subTanentId == 243){
            hospital_logo.setImageResource(R.drawable.sgrh);
        }*/
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mSharedPreference.edit();
                editor.remove(Constant.USER_ROLE_ID_KEY);
                editor.remove(Constant.SUB_TANENT_IMAGE_PATH);
                editor.apply();
                Intent intentLogout = new Intent(DoctorScheduleActivity.this, LoginActivity.class);
                startActivity(intentLogout);
            }
        });
        doctor_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.isInternetConnected(DoctorScheduleActivity.this)) {
                    builder = new AlertDialog.Builder(DoctorScheduleActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View convertView = inflater.inflate(R.layout.search_doctor_dialog, null);
                    builder.setView(convertView);
                    SearchView search_doctor = convertView.findViewById(R.id.search_doctor);
                    search_doctor.setIconified(false);
                    search_doctor.setIconifiedByDefault(false);
                    search_doctor.setOnQueryTextListener(DoctorScheduleActivity.this);
                    search_doctor.setQueryHint("Search Here");
                    lv = convertView.findViewById(R.id.doctor_list);
                    lv.setTextFilterEnabled(true);
                    doctorSpinnerAdapter = new DoctorSpinnerAdapter(DoctorScheduleActivity.this,
                            android.R.layout.simple_spinner_item,
                            doctorArray);
                    lv.setAdapter(doctorSpinnerAdapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            userRoleId = doctorSpinnerAdapter.getItem(position).getUserRoleId();
                            editor.putString(Constant.LOGIN_NAME_KEY, doctorSpinnerAdapter.getItem(position).getUserName());
                            //Toast.makeText(LoginActivity.this,userRoleId+"===userroleid",Toast.LENGTH_LONG).show();
                            //editor.putInt(Constant.ADDRESS_ID_KEY, doctorSpinnerAdapter.getItem(position).getAddressId());
                            // editor.putInt(Constant.CONTACT_ID_KEY, doctorSpinnerAdapter.getItem(position).getContactId());
                            //editor.putString(Constant.DOB_KEY, doctorSpinnerAdapter.getItem(position).getDob());
                            //editor.putInt(Constant.GENDER_ID_KEY, doctorSpinnerAdapter.getItem(position).getGenderId());
                            //editor.putString(Constant.UNAME_KEY, doctorSpinnerAdapter.getItem(position).getUname());
                            Log.d("ServiceRoleId",doctorSpinnerAdapter.getItem(position).getServiceRoleId()+"");
                            editor.putString(Constant.DEPT_NAME,doctorSpinnerAdapter.getItem(position).getDeptName());
                            editor.putInt(Constant.SERVICE_ROLE_ID, doctorSpinnerAdapter.getItem(position).getServiceRoleId());
                            editor.putInt(Constant.USER_ROLE_ID, doctorSpinnerAdapter.getItem(position).getUserRoleId());
                            editor.apply();
                            doctor_name.setText(doctorSpinnerAdapter.getItem(position).getUserName());
                            dialog.dismiss();
                            getScheduleData();
                        }
                    });

                    dialog = builder.show();
                } else {
                    Toast.makeText(DoctorScheduleActivity.this, "Check Internet Connection!", Toast.LENGTH_LONG).show();

                }

            }
        });
        doctor_schedule.setOnItemSelectedListener(this);
        go_to_chart_btn.setOnClickListener(this);
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        completeDate = year + "-" + month + "-" + date; //"2016-05-09"
        Log.d("completeDate", completeDate);
    }

    private DoctorListModel getDocPosition() {
        for(int i=0;i<doctorArray.length;i++){
            if(userRoleId==doctorArray[i].getUserRoleId()){
                doctorListModel = doctorArray[i];

                break;
            }

        }
        return doctorListModel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_to_chart_btn:
                if (Helper.isInternetConnected(DoctorScheduleActivity.this)) {
                    if (userRoleId != 0 && scheduleId != 0) {
                        Intent i = new Intent(DoctorScheduleActivity.this, CalendarActivity.class);
                        startActivity(i);
                        //finish();
                    }
                } else {
                    Toast.makeText(DoctorScheduleActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }

                break;
        }

    }

    public void getDoctorDetail() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.list_DoctorsBySubTenantId(subTanentId, new Callback<DoctorListModel[]>() {
            @Override
            public void success(DoctorListModel[] doctors, Response response) {

                doctorArray = doctors;
                if(loginRoleId==7){
                    userRoleId =  mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
                    DoctorListModel model = getDocPosition();
                    editor.putString(Constant.LOGIN_NAME_KEY,model.getUserName());
                    Log.d("ServiceRoleId",model.getServiceRoleId()+"");
                    editor.putString(Constant.DEPT_NAME,model.getDeptName());
                    editor.putInt(Constant.SERVICE_ROLE_ID, model.getServiceRoleId());
                    editor.putInt(Constant.USER_ROLE_ID, model.getUserRoleId());
                    editor.apply();
                    doctor_name.setText(model.getUserName());
                    doctor_name.setClickable(false);
                    getScheduleData();
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
            progressDialog = new ProgressDialog(DoctorScheduleActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getString(R.string.loading_message));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            /*case R.id.doctor_name:
                userRoleId = doctorSpinnerAdapter.getItem(position).getUserRoleId();
                Toast.makeText(DoctorScheduleActivity.this, userRoleId + "===userroleid", Toast.LENGTH_LONG).show();
                editor.putString(Constant.LOGIN_NAME_KEY, doctorSpinnerAdapter.getItem(position).getUserName());
                //editor.putInt(Constant.ADDRESS_ID_KEY, doctorSpinnerAdapter.getItem(position).getAddressId());
                // editor.putInt(Constant.CONTACT_ID_KEY, doctorSpinnerAdapter.getItem(position).getContactId());
                //editor.putString(Constant.DOB_KEY, doctorSpinnerAdapter.getItem(position).getDob());
                //editor.putInt(Constant.GENDER_ID_KEY, doctorSpinnerAdapter.getItem(position).getGenderId());
                //editor.putString(Constant.UNAME_KEY, doctorSpinnerAdapter.getItem(position).getUname());
                editor.putInt(Constant.USER_ROLE_ID, doctorSpinnerAdapter.getItem(position).getUserRoleId());
                editor.apply();
                getScheduleData();
                break;*/
            case R.id.doctor_sechedule:
                scheduleId = scheduleSpinnerAdapter.getItem(position).getScheduleId();
                from_time = scheduleSpinnerAdapter.getItem(position).getFromTime();
                to_time = scheduleSpinnerAdapter.getItem(position).getToTime();

                //Toast.makeText(DoctorScheduleActivity.this, scheduleId + "===scheduleId", Toast.LENGTH_LONG).show();
                editor.putInt(Constant.SCHEDULE_ID, scheduleId);
                editor.putString("from_time", from_time);
                editor.putString("to_time", to_time);
                editor.apply();
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getScheduleData() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getSchedule_Day(userRoleId, completeDate, new Callback<ScheduleModel[]>() {
            @Override
            public void success(ScheduleModel[] scheduleModels, Response response) {

                mScheduleArray = scheduleModels;
                /*for (int i = 0; i < mScheduleArray.length; i++) {
                    Log.d("time", mScheduleArray[i].getFromTime() + "..." + mScheduleArray[i].getToTime());
                }*/
                scheduleSpinnerAdapter = new ScheduleSpinnerAdapter(DoctorScheduleActivity.this,
                        android.R.layout.simple_spinner_item,
                        mScheduleArray);
                doctor_schedule.setAdapter(scheduleSpinnerAdapter);

                mCuraApplication.getInstance().mCuraEndPoint.getUserInfo(userRoleId, new Callback<UserInfoModel>() {
                    @Override
                    public void success(UserInfoModel userInfoModel, Response response) {


                        if (userInfoModel.getUserId() != null) {

                            SharedPreferences.Editor editor = mSharedPreference.edit();
                            //editor.putInt(SyncStateContract.Constants.ADDRESS_ID_KEY, userInfoModel.getAddressId());
                            //editor.putInt(Constants.CONTACT_ID_KEY, userInfoModel.getContactId());
                            //editor.putString(Constants.DOB_KEY, userInfoModel.getDob());
                            //editor.putInt(Constants.GENDER_ID_KEY, userInfoModel.getGenderId());
                            editor.putString(Constant.UNAME_KEY, userInfoModel.getUname());
                            //editor.putInt(Constants.USER_ID_KEY, userInfoModel.getUserId());
                            editor.apply();


                            /*Intent i = new Intent(DoctorScheduleActivity.this, CalendarActivity.class);
                            startActivity(i);
                            dismissLoadingDialog();
                            finish();*/
                        } else {
                            dismissLoadingDialog();
                        }


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dismissLoadingDialog();

                    }
                });

                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                //Toast.makeText(CalendarActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Filter filter = doctorSpinnerAdapter.getFilter();
        if (TextUtils.isEmpty(newText)) {
            filter.filter("");
        } else {
            filter.filter(newText);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        // Do as you please
    }
}

