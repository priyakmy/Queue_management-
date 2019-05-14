package com.mcura.jaideep.queuemanagement.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Adapter.DoctorInfoAdapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.AvialbaleTokenListbydate;
import com.mcura.jaideep.queuemanagement.Model.Doctor;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DoctorInfoActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener /*implements SearchView.OnQueryTextListener*/ {
    AvialbaleTokenListbydate[] avialbaleTokenListbydatesArray;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    ProgressDialog progressDialog;
    public MCuraApplication mCuraApplication;
    int user_role_id, subTanentId;
    private SharedPreferences mSharedPreference;
    private ListView mDoctorListView;
    private DoctorInfoAdapter doctorInfoAdapter;
    int year,month,date;
    String completeDate;
    EditText search_by_doctor;
    private TextWatcher mSearchTw;
    private ImageView iv_calandar;
    private TextView tv_date;
    Calendar now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        user_role_id = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        subTanentId = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH)+1;
        date = now.get(Calendar.DATE);
        completeDate = year+"-"+month+"-"+date; //"2016-05-09"


        initView();

        search_by_doctor.addTextChangedListener(this);
    }

    public void initView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        getAvailDoctorDetail();
        TextView appointment = (TextView) findViewById(R.id.appointment);
        TextView queue_mgmt=(TextView)findViewById(R.id.queue_mgmt);

        TextView billing = (TextView) findViewById(R.id.billing);
        TextView tv_fillcard = (TextView) findViewById(R.id.tv_fillcard);

        ImageView hospital_logo = (ImageView) mToolbar.findViewById(R.id.hospital_logo);
        iv_calandar = (ImageView) findViewById(R.id.iv_calandar);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setText(dateFormatter.format(now.getTime()));
        iv_calandar.setOnClickListener(this);
        String subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        Picasso.with(DoctorInfoActivity.this).load(subtanentImagePath).into(hospital_logo);
        /*if(!subtanentImagePath.equals("default")){
            Picasso.with(DoctorInfoActivity.this).load(subtanentImagePath).into(hospital_logo);
        }else{
            Picasso.with(DoctorInfoActivity.this).load(R.drawable.kims_logo).into(hospital_logo);
        }*/
        /*if (subTanentId == 500) {
            hospital_logo.setImageResource(R.drawable.blk_logo);
        } else if (subTanentId == 243) {
            hospital_logo.setImageResource(R.drawable.sgrh);
        }*/
        mDoctorListView = (ListView) findViewById(R.id.doc_info_list);
        search_by_doctor = (EditText) findViewById(R.id.search_by_doctor);
        setDateTimeField();
        //search_by_doctor.setOnQueryTextListener(DoctorInfoActivity.this);
        appointment.setOnClickListener(this);
        queue_mgmt.setOnClickListener(this);
        billing.setOnClickListener(this);
        tv_fillcard.setOnClickListener(this);
    }
public void getAvailDoctorDetail(){
    showLoadingDialog();
    mCuraApplication.getInstance().mCuraEndPoint.avialbale_TokenList_bydate(subTanentId, completeDate, new Callback<AvialbaleTokenListbydate[]>() {
        @Override
        public void success(AvialbaleTokenListbydate[] avialbaleTokenListbydates, Response response) {
            avialbaleTokenListbydatesArray = avialbaleTokenListbydates;
            doctorInfoAdapter = new DoctorInfoAdapter(DoctorInfoActivity.this,avialbaleTokenListbydatesArray);
            mDoctorListView.setAdapter(doctorInfoAdapter);
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
            progressDialog = new ProgressDialog(DoctorInfoActivity.this);
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        doctorInfoAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_calandar:
                datePickerDialog.show();
                break;
            case R.id.appointment:
                startActivity(new Intent(DoctorInfoActivity.this, CalendarActivity.class));
                break;
            case R.id.queue_mgmt:
                startActivity(new Intent(DoctorInfoActivity.this,QueueStatusActivity.class));
                break;
            case R.id.billing:
                startActivity(new Intent(DoctorInfoActivity.this, CentralizedBillingActivity.class));
                break;
            case R.id.tv_fillcard:
                startActivity(new Intent(DoctorInfoActivity.this, FillCashCardActivity.class));
                break;
        }
    }

    /*@Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Filter filter = doctorInfoAdapter.getFilter();
        if (TextUtils.isEmpty(newText)) {
            filter.filter("");
        } else {
            filter.filter(newText);
        }
        return true;
    }*/
    private void setDateTimeField() {
        iv_calandar.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                year = newDate.get(Calendar.YEAR);
                month = newDate.get(Calendar.MONTH)+1;
                date = newDate.get(Calendar.DATE);
                completeDate = year+"-"+month+"-"+date;
                tv_date.setText(dateFormatter.format(newDate.getTime()));
                getAvailDoctorDetail();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

}
