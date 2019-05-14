package com.mcura.jaideep.queuemanagement.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mcura.jaideep.queuemanagement.Adapter.OrderBoothAdapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.GetOrderBoothListModel;
import com.mcura.jaideep.queuemanagement.Model.LabPharmacyOrderBoothModel;
import com.mcura.jaideep.queuemanagement.Model.MainModel;
import com.mcura.jaideep.queuemanagement.Model.OrderBoothSearchListModel;
import com.mcura.jaideep.queuemanagement.Model.Patmedrecord;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OrderBoothActivity extends AppCompatActivity implements View.OnClickListener {
    EditText search_by_patient;
    ImageView imageData;
    AlertDialog.Builder alertBuilder;
    AlertDialog ad;
    public MCuraApplication mCuraApplication;
    private ProgressDialog progress;
    private ListView patient_listview;
    private OrderBoothAdapter pharmacyAdapter;
    private Spinner pharmacy_status;
    String[] pharmacyStatus, pharmacyStatusArray;
    private SimpleDateFormat dateFormatter;
    private TextView tvFromTime, tvToTime;
    Calendar now, fromDate, toDate;
    private DatePickerDialog fromDatePickerDialog, toDatePickerDialog;
    private SharedPreferences mSharedPreference;
    public ImageView iv_search_pat;
    public static String searchBy;
    private ImageButton iv_logout;
    ArrayAdapter spinnerArrayAdapter;
    int roleID, userRoleId, currentStatusId, subTenantId;
    String subTenantName;
    private TextView tv_subtenant_name;
    MainModel mainRetroResponses;
    private ArrayList<Patmedrecord> medRecord_Models;
    private String pdf;
    Filter filter;
    WebView lastVisitSummary_webview;
    private AlertDialog alertDialog;
    private Integer prescriptionId;
    private int prescriptionStatus;
    private AlertDialog dialog;
    private ArrayAdapter statusListArrayAdapter;
    private OrderBoothSearchListModel model;
    private int labPharmacyType;
    private String estimateBillId;
    private String totalDays;
    private String amount;
    private LabPharmacyOrderBoothModel labPharmacyOrderBoothModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_booth);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        pharmacyStatus = getResources().getStringArray(R.array.pharmacy_status);
        mSharedPreference = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        userRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
        subTenantId = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        //currentStatusId=mSharedPreference.getInt(Constants.STATUS_ID_KEY,0);
        now = Calendar.getInstance();
        initView();
    }

    private void initView() {
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US); //dd-MM-yyyy
        now = Calendar.getInstance();
        tv_subtenant_name = (TextView) findViewById(R.id.tv_subtenant_name);
        tv_subtenant_name.setText(subTenantName);
        pharmacy_status = (Spinner) findViewById(R.id.pharmacy_status);
        //iv_logout = (ImageButton) findViewById(R.id.iv_logout);
        //iv_logout.setOnClickListener(this);
        search_by_patient = (EditText) findViewById(R.id.et_search_by_patient);
        search_by_patient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //PharmacyActivity.this.labOrderAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchBy = "patient";
                filter = pharmacyAdapter.getFilter();
                filter.filter(editable);
               /*if (roleID==EnumType.loginRoleType.mLabOrderUser.getID()) {
                   Log.d("edittext", search_by_patient.getText().toString());
                   Filter filter = labOrderAdapter.getFilter();
                   filter.filter(editable);
               }
                else if (roleID==EnumType.loginRoleType.mPharmacyUser.getID())
               {
                   Filter filter=pharmacyAdapter.getFilter();
                   filter.filter(editable);
               }
*/

            }
        });

        spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                pharmacyStatus);
        pharmacy_status.setAdapter(spinnerArrayAdapter);
        pharmacy_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                search_by_patient.getText().clear();
                searchBy = "status";
                switch (position) {
                    case 0:
                        getPharmacyDataFromApi_v1();
                        break;
                    case 1:
                        filter = pharmacyAdapter.getFilter();
                        filter.filter("1");
                        break;
                    case 2:
                        filter = pharmacyAdapter.getFilter();
                        filter.filter("3");
                        break;
                    case 3:
                        filter = pharmacyAdapter.getFilter();
                        filter.filter("4");
                        break;
                    case 4:
                        filter = pharmacyAdapter.getFilter();
                        filter.filter("2");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        patient_listview = (ListView) findViewById(R.id.patient_listview);

        tvFromTime = (TextView) findViewById(R.id.tv_fromTime);
        tvFromTime.setText(dateFormatter.format(now.getTime()));
        tvToTime = (TextView) findViewById(R.id.tv_toTime);
        tvToTime.setText(dateFormatter.format(now.getTime()));
        tvFromTime.setOnClickListener(this);
        tvToTime.setOnClickListener(this);
        /*patient_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model = pharmacyAdapter.getItem(position);
                showTaskDialog();
            }
        });*/
        setFromDateTimeField();
        setToDateTimeField();
        getPharmacyDataFromApi_v1();
        /*if(roleID==EnumType.loginRoleType.mPharmacyUser.getID()){
            getPharmacyDataFromApi_v1();
        }else if(roleID==EnumType.loginRoleType.mLabOrderUser.getID()){
            labOrdersList();

        }*/
    }



    /*public void getPharmacyDataFromApi() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.orderBoothSearchList(subTenantId, userRoleId, tvFromTime.getText().toString(), tvToTime.getText().toString(), new Callback<OrderBoothSearchListModel[]>() {
            @Override
            public void success(OrderBoothSearchListModel[] pharmacyModels, Response response) {
                Log.e("UserRoleId", String.valueOf(userRoleId));
                Log.e("CurrentStatusId", String.valueOf(currentStatusId));
                // Toast.makeText(PharmacyActivity.this,"getPharmacyData_response"+response,Toast.LENGTH_LONG).show();
                pharmacyAdapter = new OrderBoothAdapter(OrderBoothActivity.this, pharmacyModels);
                patient_listview.setAdapter(pharmacyAdapter);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }*/
    public void getPharmacyDataFromApi_v1() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.GetOrderBoothList(subTenantId, userRoleId, tvFromTime.getText().toString(), tvToTime.getText().toString(), new Callback<GetOrderBoothListModel>() {
            @Override
            public void success(GetOrderBoothListModel getOrderBoothListModel, Response response) {
                ArrayList<LabPharmacyOrderBoothModel> labPharmacyOrderBoothModelsList = new ArrayList<LabPharmacyOrderBoothModel>();
                for(int i=0;i<getOrderBoothListModel.getData().size();i++){
                    for(int j=0;j<getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().size();j++){
                        labPharmacyOrderBoothModel = new LabPharmacyOrderBoothModel();
                        labPharmacyOrderBoothModel.setAgainst_sub_tenant_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getAgainstSubTenantId());
                        labPharmacyOrderBoothModel.setAmount(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getAmount());
                        labPharmacyOrderBoothModel.setDate(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getDate());
                        labPharmacyOrderBoothModel.setDays(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getDays());
                        labPharmacyOrderBoothModel.setEST_Billno(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getESTBillno());
                        labPharmacyOrderBoothModel.setLabPharmacyType(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getLabPharmacyType());
                        labPharmacyOrderBoothModel.setMrno(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getMrno());
                        labPharmacyOrderBoothModel.setPat_demoid(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getPatDemoid());
                        labPharmacyOrderBoothModel.setPatname(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getPatname());
                        labPharmacyOrderBoothModel.setPres_order_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getPresOrderId());
                        labPharmacyOrderBoothModel.setLaborder_id(0);
                        labPharmacyOrderBoothModel.setLabOrderId(0);
                        labPharmacyOrderBoothModel.setAddress_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getAddressId());
                        labPharmacyOrderBoothModel.setContact_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getContactId());
                        labPharmacyOrderBoothModel.setDob(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getDob());
                        labPharmacyOrderBoothModel.setDoctorName(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getDoctorName());
                        labPharmacyOrderBoothModel.setEntry_type_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getEntryTypeId());
                        labPharmacyOrderBoothModel.setFollowup_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getFollowupId());
                        labPharmacyOrderBoothModel.setGender_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getGenderId());
                        labPharmacyOrderBoothModel.setOrderBy_sub_tenant_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getOrderBySubTenantId());
                        labPharmacyOrderBoothModel.setPrescription_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getPrescriptionId());
                        labPharmacyOrderBoothModel.setRec_nature_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getRecNatureId());
                        labPharmacyOrderBoothModel.setRecord_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getRecordId());
                        labPharmacyOrderBoothModel.setStatus_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getStatusId());
                        labPharmacyOrderBoothModel.setUser_role_id(getOrderBoothListModel.getData().get(i).getPharmacyOrderdata().get(j).getUserRoleId());
                        labPharmacyOrderBoothModelsList.add(labPharmacyOrderBoothModel);
                    }
                    for(int j=0;j<getOrderBoothListModel.getData().get(i).getLabOrderdata().size();j++){
                        labPharmacyOrderBoothModel = new LabPharmacyOrderBoothModel();
                        labPharmacyOrderBoothModel.setAgainst_sub_tenant_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getAgainstSubTenantId());
                        labPharmacyOrderBoothModel.setAmount(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getAmount());
                        labPharmacyOrderBoothModel.setDate(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getDate());
                        labPharmacyOrderBoothModel.setDays(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getDays());
                        labPharmacyOrderBoothModel.setEST_Billno(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getESTBillno());
                        labPharmacyOrderBoothModel.setLabPharmacyType(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getLabPharmacyType());
                        labPharmacyOrderBoothModel.setMrno(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getMrno());
                        labPharmacyOrderBoothModel.setPat_demoid(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getPatDemoid());
                        labPharmacyOrderBoothModel.setPatname(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getPatname());
                        labPharmacyOrderBoothModel.setPres_order_id(0);
                        labPharmacyOrderBoothModel.setLaborder_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getLaborderId());
                        labPharmacyOrderBoothModel.setLabOrderId(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getLabOrderId());
                        labPharmacyOrderBoothModel.setAddress_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getAddressId());
                        labPharmacyOrderBoothModel.setContact_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getContactId());
                        labPharmacyOrderBoothModel.setDob(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getDob());
                        labPharmacyOrderBoothModel.setDoctorName(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getDoctorName());
                        labPharmacyOrderBoothModel.setEntry_type_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getEntryTypeId());
                        labPharmacyOrderBoothModel.setFollowup_id(0);
                        labPharmacyOrderBoothModel.setGender_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getGenderId());
                        labPharmacyOrderBoothModel.setOrderBy_sub_tenant_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getOrderBySubTenantId());
                        labPharmacyOrderBoothModel.setPrescription_id(0);
                        labPharmacyOrderBoothModel.setRec_nature_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getRecNatureId());
                        labPharmacyOrderBoothModel.setRecord_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getRecordId());
                        labPharmacyOrderBoothModel.setStatus_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getStatusId());
                        labPharmacyOrderBoothModel.setUser_role_id(getOrderBoothListModel.getData().get(i).getLabOrderdata().get(j).getUserRoleId());
                        labPharmacyOrderBoothModelsList.add(labPharmacyOrderBoothModel);
                    }
                }
                Log.d("size",labPharmacyOrderBoothModelsList.size()+"");
                pharmacyAdapter = new OrderBoothAdapter(OrderBoothActivity.this, labPharmacyOrderBoothModelsList);
                patient_listview.setAdapter(pharmacyAdapter);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fromTime:
                fromDatePickerDialog.show();
                break;
            case R.id.tv_toTime:
                toDatePickerDialog.show();
                break;
        }
    }

    private void setFromDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fromDate = Calendar.getInstance();
                fromDate.set(year, monthOfYear, dayOfMonth);
                tvFromTime.setText(dateFormatter.format(fromDate.getTime()));
                getPharmacyDataFromApi_v1();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    private void setToDateTimeField() {
        final Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                toDate = Calendar.getInstance();
                toDate.set(year, monthOfYear, dayOfMonth);
                tvToTime.setText(dateFormatter.format(toDate.getTime()));
                getPharmacyDataFromApi_v1();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(OrderBoothActivity.this);
            progress.setCancelable(false);
            progress.setMessage(getString(R.string.loading_message));
        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

}

