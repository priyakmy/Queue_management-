package com.mcura.jaideep.queuemanagement.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.Adapter.DoctorSpinnerAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.ScheduleSpinnerAdapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.DocAssistantMappingModel;
import com.mcura.jaideep.queuemanagement.Model.DoctorListModel;
import com.mcura.jaideep.queuemanagement.Model.GetSubTenantLogoModel;
import com.mcura.jaideep.queuemanagement.Model.GetSubtenantDetailsModel;
import com.mcura.jaideep.queuemanagement.Model.LoginModel;
import com.mcura.jaideep.queuemanagement.Model.PatientVerificationModel.PatVerificationResponseModel;
import com.mcura.jaideep.queuemanagement.Model.ScheduleModel;
import com.mcura.jaideep.queuemanagement.Model.SearchHospital;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.mcura.jaideep.queuemanagement.helper.Helper;

import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner doctor_name, doctor_schedule;
    public MCuraApplication mCuraApplication;
    private EditText loginUsername, loginPassword;
    private ImageButton loginButton, go_to_chart;
    String username, password;

    private ImageView logout;
    private SharedPreferences mSharedPreference;
    DoctorSpinnerAdapter doctorSpinnerAdapter;
    SharedPreferences.Editor editor;
    int year, month, date;
    String completeDate, from_time, to_time;
    ScheduleModel[] mScheduleArray;
    ScheduleSpinnerAdapter scheduleSpinnerAdapter;
    int userRoleId;
    DoctorListModel[] doctorArray;
    LinearLayout ll_doctor;
    int subTanentId, scheduleId;
    String subtanentImagePath;
    public static String APP_URL;
    public Helper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPreference = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = mSharedPreference.edit();
        helper = new Helper();

        if (mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0) != 0) {
            Intent i = new Intent(LoginActivity.this, DoctorScheduleActivity.class);
            startActivity(i);
            finish();
        } else {
            initView();
        }

    }


    public void initView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        loginUsername = findViewById(R.id.login_username);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_btn);
        doctor_name = findViewById(R.id.doctor_name);
        doctor_schedule = findViewById(R.id.doctor_sechedule);
        go_to_chart = findViewById(R.id.go_to_chart_btn);
        ll_doctor = findViewById(R.id.ll_doctor);
        //logout = (ImageView) findViewById(R.id.logout);
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        completeDate = year + "-" + month + "-" + date; //"2016-05-09"
        Log.d("completeDate", completeDate);
        //logout.setOnClickListener(this);
        doctor_name.setOnItemSelectedListener(this);
        doctor_schedule.setOnItemSelectedListener(this);
        loginButton.setOnClickListener(this);
        go_to_chart.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                username = loginUsername.getText().toString();
                password = loginPassword.getText().toString();
                if (Helper.isInternetConnected(LoginActivity.this)) {
                    if (username.length() > 0) {
                        if (password.length() > 0) {
                            InputMethodManager imm = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(
                                    loginPassword.getWindowToken(), 0);
                            getLoginDetail();
                        } else {
                            loginPassword.setError("Enter Password");
                        }
                    } else {
                        loginUsername.setError("Enter Username");
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.go_to_chart_btn:
                if (userRoleId != 0 && scheduleId != 0) {

                    Intent i = new Intent(LoginActivity.this, CalendarActivity.class);
                    startActivity(i);
                    finish();
                }
                break;
            /*case R.id.logout:
                SharedPreferences.Editor editor = mSharedPreference.edit();
                editor.remove(Constant.USER_ROLE_ID_KEY);
                editor.remove(Constant.USER_ROLE_ID);
                editor.apply();
                Intent intentLogout = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
                break;*/
        }


    }


    public void getLoginDetail() {
        helper.showLoadingDialog(LoginActivity.this);

        //Assistant@1
        //b7n912qj
        mCuraApplication.getInstance().mCuraEndPoint.getLogin(username, password, new Callback<LoginModel>() {
            @Override
            public void success(LoginModel loginModel, Response response) {
                if (loginModel.getLoginId() != 0) {
                    userRoleId = loginModel.getUserRoleId();
                    editor.putInt(Constant.STATUS_ID_KEY, loginModel.getCurrentStatusId());
                    editor.putString(Constant.DOMAIN_KEY, loginModel.getDomain());
                    editor.putInt(Constant.LOGIN_ID_KEY, loginModel.getLoginId());
                    editor.putInt(Constant.LOGIN_ROLE_ID, loginModel.getRoleId());
                    editor.putInt(Constant.PIN_KEY, loginModel.getPin());
                    editor.putString(Constant.PASSWORD_KEY, loginModel.getPwd());
                    editor.putInt(Constant.USER_ROLE_ID_KEY, loginModel.getUserRoleId());
                    editor.apply();
                    getSubtenantId(loginModel.getRoleId());
                    //dismissLoadingDialog();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Username and Password", Toast.LENGTH_LONG).show();
                    helper.dismissLoadingDialog();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                helper.dismissLoadingDialog();
            }
        });
    }

    public void getSubtenantId(int roleId) {
        helper.showLoadingDialog(LoginActivity.this);
        mCuraApplication.getInstance().mCuraEndPoint.getHospital(userRoleId, new Callback<SearchHospital[]>() {
            @Override
            public void success(SearchHospital[] searchHospitals, Response response) {
                if (searchHospitals != null) {
                    subTanentId = searchHospitals[0].getSubTenantId();
                    //Toast.makeText(LoginActivity.this, searchHospitals[0].getSubTenantId() + "===subtanent", Toast.LENGTH_LONG).show();
                    editor.putInt(Constant.SUB_TANENT_ID_KEY, searchHospitals[0].getSubTenantId());
                    editor.apply();
                    //getSubtanentLogoFromApi();
                    getSubtanentDatail(roleId);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Again", Toast.LENGTH_LONG).show();
                    helper.dismissLoadingDialog();
                }
                /*ll_doctor.setVisibility(View.VISIBLE);
                getDoctorDetail();*/

                //finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                helper.dismissLoadingDialog();
            }
        });
    }

    private void getSubtanentDatail(int roleId) {
        helper.showLoadingDialog(LoginActivity.this);
        mCuraApplication.getInstance().mCuraEndPoint.getSubtenantDetailsBySubTenantId(subTanentId, userRoleId, new Callback<GetSubtenantDetailsModel>() {
            @Override
            public void success(GetSubtenantDetailsModel getSubtenantDetailsModel, Response response) {
                editor.putString(Constant.SUBTANENT_NAME, getSubtenantDetailsModel.getSubTenantName().trim());
                editor.putString(Constant.SUBTANENT_ADD, getSubtenantDetailsModel.getAddress().trim());
                editor.putString(Constant.SUBTANENT_CONTACT, getSubtenantDetailsModel.getMobile().trim());
                editor.commit();
                getSubtanentLogoFromApi(roleId);
                helper.dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                helper.dismissLoadingDialog();
            }
        });
    }

    public void getSubtanentLogoFromApi(int roleId) {
        //showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getSubTenant_Logo(userRoleId, subTanentId, new Callback<GetSubTenantLogoModel>() {
            @Override
            public void success(GetSubTenantLogoModel s, Response response) {
                if (s != null) {
                    subtanentImagePath = s.getMobileLogo();
                    subtanentImagePath = subtanentImagePath.replace("\\", "");
                    editor.putString(Constant.SUB_TANENT_IMAGE_PATH, subtanentImagePath);
                    editor.commit();
                }
                postEndUserTrackingAPI(roleId);
                helper.dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                helper.dismissLoadingDialog();
            }
        });
    }

    private void postEndUserTrackingAPI(int roleId) {
        PackageInfo pInfo = null;
        try {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        helper.showLoadingDialog(LoginActivity.this);
        JsonObject obj = new JsonObject();
        obj.addProperty("actSubTenantId", subTanentId);
        obj.addProperty("actUserRoleId", userRoleId);
        obj.addProperty("actUserMediumId", 9);
        obj.addProperty("patMrno", 0);
        obj.addProperty("actTransMasterId", EnumType.ActTransactMasterEnum.User_Login.getActTransactMasterTypeId());
        obj.addProperty("actRemarks", "");
        obj.addProperty("actBuildVersion", pInfo.versionName);
        obj.addProperty("RecordId", 0);
        obj.addProperty("delivered", 0);
        mCuraApplication.getInstance().mCuraEndPoint.postEndUserTracking(obj, new Callback<PatVerificationResponseModel>() {
            @Override
            public void success(PatVerificationResponseModel patVerificationResponseModel, Response response) {
                helper.dismissLoadingDialog();
                Intent i = new Intent(LoginActivity.this, DoctorScheduleActivity.class);
                startActivity(i);
                 helper.dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                helper.dismissLoadingDialog();
//                Intent i = new Intent(LoginActivity.this, DoctorScheduleActivity.class);
//                startActivity(i);
            }
        });
    }


    public void getDoctorDetail() {
        helper.showLoadingDialog(LoginActivity.this);
        mCuraApplication.getInstance().mCuraEndPoint.list_DoctorsBySubTenantId(subTanentId, new Callback<DoctorListModel[]>() {
            @Override
            public void success(DoctorListModel[] doctors, Response response) {
                doctorArray = doctors;
                for (int i = 0; i < doctors.length; i++) {
                    Log.d("doctor name", doctors[i].getUserName());
                }
                doctorSpinnerAdapter = new DoctorSpinnerAdapter(LoginActivity.this,
                        android.R.layout.simple_spinner_item,
                        doctorArray);
                doctor_name.setAdapter(doctorSpinnerAdapter);
                helper.dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                helper.dismissLoadingDialog();
            }
        });
    }

    /**
     *
     */


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.doctor_name:
                userRoleId = doctorSpinnerAdapter.getItem(position).getUserRoleId();
                editor.putString(Constant.LOGIN_NAME_KEY, doctorSpinnerAdapter.getItem(position).getUserName());
                //Toast.makeText(LoginActivity.this,userRoleId+"===userroleid",Toast.LENGTH_LONG).show();
                //editor.putInt(Constant.ADDRESS_ID_KEY, doctorSpinnerAdapter.getItem(position).getAddressId());
                // editor.putInt(Constant.CONTACT_ID_KEY, doctorSpinnerAdapter.getItem(position).getContactId());
                //editor.putString(Constant.DOB_KEY, doctorSpinnerAdapter.getItem(position).getDob());
                //editor.putInt(Constant.GENDER_ID_KEY, doctorSpinnerAdapter.getItem(position).getGenderId());
                //editor.putString(Constant.UNAME_KEY, doctorSpinnerAdapter.getItem(position).getUname());
                editor.putInt(Constant.USER_ROLE_ID, doctorSpinnerAdapter.getItem(position).getUserRoleId());
                editor.apply();
                break;
            case R.id.doctor_sechedule:
                scheduleId = scheduleSpinnerAdapter.getItem(position).getScheduleId();
                from_time = scheduleSpinnerAdapter.getItem(position).getFromTime();
                to_time = scheduleSpinnerAdapter.getItem(position).getToTime();

                //Toast.makeText(LoginActivity.this,scheduleId+"===scheduleId",Toast.LENGTH_LONG).show();
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
        helper.showLoadingDialog(LoginActivity.this);
        mCuraApplication.getInstance().mCuraEndPoint.getSchedule_Day(userRoleId, completeDate, new Callback<ScheduleModel[]>() {
            @Override
            public void success(ScheduleModel[] scheduleModels, Response response) {

                mScheduleArray = scheduleModels;
                for (int i = 0; i < mScheduleArray.length; i++) {
                    Log.d("time", mScheduleArray[i].getFromTime() + "..." + mScheduleArray[i].getToTime());
                }
                scheduleSpinnerAdapter = new ScheduleSpinnerAdapter(LoginActivity.this,
                        android.R.layout.simple_spinner_item,
                        mScheduleArray);
                doctor_schedule.setAdapter(scheduleSpinnerAdapter);
                helper.dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                helper.dismissLoadingDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do as you please
    }
}
