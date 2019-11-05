package com.mcura.jaideep.queuemanagement.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.mcura.jaideep.queuemanagement.Adapter.CheckInAdapter_v1;
import com.mcura.jaideep.queuemanagement.Adapter.GetNatureByUserRoleIdAdapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.AppointmentNature;
import com.mcura.jaideep.queuemanagement.Model.CurrentTokenModel;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.FeeFetch;
import com.mcura.jaideep.queuemanagement.Model.GenerateTokenResultModel;
import com.mcura.jaideep.queuemanagement.Model.GetNatureByUserRoleModel;
import com.mcura.jaideep.queuemanagement.Model.LastBillDetailModel;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.PostActivityTrackerModel.PostActivityTrackerModel;
import com.mcura.jaideep.queuemanagement.Model.PostPaymentModel;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.Model.TokenStatusModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.SqliteHelper.SqlLiteDbHelper;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewAppointmentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private SearchPatientModel patientSearchModel;
    private int firstIndex;
    private ArrayList<Datum> dataList;
    private boolean flag_loading;
    CheckInAdapter_v1 searchPatientAdapter;
    private GetNatureByUserRoleIdAdapter getNatureByUserRoleIdAdapter;
    private TextView btn_later;
    private TextView btn_pay_now;
    private TextView tv_bill_amount;
    private AlertDialog dialog;
    private GetNatureByUserRoleModel getNatureByUserRoleModel;
    private Integer feeAmount;
    private String paymentMode = "2";
    private int frontOfficeUserRoleId;
    private String hospitalno;
    private String patMobileNumber;
    private SharedPreferences mSharedPreference;
    SqlLiteDbHelper dbHelper;
    private TextView tv_no_record_found_msg;
    private LinearLayout ll_last_record;
    private TextView tv_nature_name;
    private TextView tv_last_record_date;
    private String strSearchBy;
    private String currTime;
    private int serviceRoleId;
    private int actTransactId;
    private String buildVersionName;
    private android.support.v7.app.AlertDialog successAlertDialog;
    private android.support.v7.app.AlertDialog errorAlertDialog;

    public enum ScheduleStatus {
        kScheduleStatusChartNotGenerated(1), kScheduleStatusChartGenerated(2);
        private int id;

        ScheduleStatus(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    public enum NextAvailTokenStatus {
        kNextAvailTokenStatusNotAvailable(0), kNextAvailTokenStatusAvailable(1);
        private int id;

        NextAvailTokenStatus(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    int nextToken, currToken;
    CurrentTokenModel[] currentTokenModelsArray;
    ImageView close;
    TextView appointmentName, appointmentTime;
    ImageButton submit, cancel, search, add;
    AlertDialog.Builder alertDialog;
    AlertDialog ad;
    EditText searchBy;
    String fromTime, toTime, scheduleName, time;
    int userRoleId;
    int subTanentId;
    private ProgressDialog progressDialog;
    String searchKey = "";
    int chartGenerateStatus;
    Spinner appointmentNature;
    public MCuraApplication mCuraApplication;
    public PatientSearchModel[] patientSearchModelsArray;
    public AppointmentNature[] appointmentNaturesArray;
    int appId, avlStatusId, appNatureId, currentStatusId, mrno, others;
    HashMap<Integer, String> hmNature = new HashMap<>();
    List<String> nature = new ArrayList<String>();
    String searchData = "";
    String completeDate;
    int year, month, date;
    int scheduleId;
    String currDate;
    private CheckBox cb_mrno, cb_mobile, cb_patname, cb_hospitalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.90);
        int screenHeight = (int) (metrics.heightPixels * 0.80);
        setContentView(R.layout.activity_new_appointment);
        //getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        getWindow().setLayout(screenWidth, screenHeight);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        actTransactId = EnumType.ActTransactMasterEnum.Booking_APT.getActTransactMasterTypeId();
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        completeDate = year + "-" + month + "-" + date;
        int appointmentstatus = getIntent().getIntExtra("appointmentstatus", 0);
        if (appointmentstatus == 1) {
            mrno = getIntent().getIntExtra("patientRegistrationId", 0);
            searchData = getIntent().getStringExtra("patName") + "," + getIntent().getStringExtra("mobile") + "," + mrno;
        }
        currTime = getIntent().getStringExtra("time");
        dbHelper = new SqlLiteDbHelper(this);
        dbHelper.openDataBase();
        init();
        setAppointmentNature();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }

    private void init() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        buildVersionName = mSharedPreference.getString(Constant.BUILD_VERSION_NAME,"");
        scheduleId = mSharedPreference.getInt(Constant.SCHEDULE_ID, 0);
        userRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
        currDate = mSharedPreference.getString("currDate", "default");
        //Toast.makeText(NewAppointmentActivity.this,currDate,Toast.LENGTH_LONG).show();
        appId = mSharedPreference.getInt("appId", 0);
        avlStatusId = mSharedPreference.getInt("avlstatusid", 0);
        serviceRoleId = mSharedPreference.getInt(Constant.SERVICE_ROLE_ID, 0);

        fromTime = sharedPreferences.getString("fromTime", "6:00");
        toTime = sharedPreferences.getString("toTime", "9:00");
        time = mSharedPreference.getString("timekey", "default");
        scheduleName = sharedPreferences.getString("scheduleName", "default OPD");
        subTanentId = sharedPreferences.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        System.out.println("subTanentId-->" + subTanentId);
        close = (ImageView) findViewById(R.id.close);
        submit = (ImageButton) findViewById(R.id.submit);
        search = (ImageButton) findViewById(R.id.search);
        add = (ImageButton) findViewById(R.id.add);
        if(subTanentId==576 || subTanentId==587){
            add.setVisibility(View.GONE);
        }
        cancel = (ImageButton) findViewById(R.id.new_appointment_cancel);
        appointmentName = (TextView) findViewById(R.id.appointment_name);
        appointmentName.setText(scheduleName);
        appointmentTime = (TextView) findViewById(R.id.appointment_time);
        //appointmentTime.setText(fromTime + " To " + toTime);
        appointmentTime.setText(time);
        searchBy = (EditText) findViewById(R.id.search_by);
        searchBy.setText(searchData);
        appointmentNature = (Spinner) findViewById(R.id.appointment_nature);

        cb_mrno = (CheckBox) findViewById(R.id.cb_mrno);
        cb_mobile = (CheckBox) findViewById(R.id.cb_mobile);
        cb_patname = (CheckBox) findViewById(R.id.cb_patname);
        cb_hospitalid = (CheckBox) findViewById(R.id.cb_hospitalid);

    /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
    appointmentNature.setAdapter(adapter);*/


        add.setOnClickListener(this);
        close.setOnClickListener(this);
        search.setOnClickListener(this);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        appointmentNature.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.new_appointment_cancel:
                NewAppointmentActivity.this.finish();
                break;
            case R.id.add:
                startActivity(new Intent(NewAppointmentActivity.this, AddNewAppointment.class).putExtra("appNatureId", appNatureId).putExtra("updateStatus", "add_new_patient").putExtra("registerStatus", "appointment").putExtra("actTransactionId", EnumType.ActTransactMasterEnum.Register_patient_From_Appointment.getActTransactMasterTypeId()));
                NewAppointmentActivity.this.finish();
                break;
            case R.id.close:
                NewAppointmentActivity.this.finish();
                break;
            case R.id.search:

                ArrayList<Integer> searchByList = new ArrayList<>();
                strSearchBy = "";
                if (cb_mrno.isChecked()) {
                    searchByList.add(1);
                }
                if (cb_mobile.isChecked()) {
                    searchByList.add(2);
                }
                if (cb_patname.isChecked()) {
                    searchByList.add(3);
                }
                if (cb_hospitalid.isChecked()) {
                    searchByList.add(4);
                }
                strSearchBy = searchByList.toString();
                strSearchBy = strSearchBy.replace("[", "");
                strSearchBy = strSearchBy.replace("]", "");

                /*JsonArray objectKeyArray = new JsonArray();
                for (int i = 0; i < searchByList.size(); i++) {
                    objectKeyArray.add(new JsonPrimitive(searchByList.get(i)));
                }*/
                Log.d("strSearchBy", strSearchBy);


                searchKey = searchBy.getText().toString();
                if (!TextUtils.isEmpty(searchKey)) {
                    if (!TextUtils.isEmpty(strSearchBy)) {
                        firstIndex = 2;
                        patientSearchModel = new SearchPatientModel();
                        dataList = new ArrayList<>();
                        getPatientSearchDetail(this.searchKey);
                    } else {
                        Toast.makeText(NewAppointmentActivity.this, "Please choose search by option", Toast.LENGTH_LONG).show();
                    }
                } else {
                    searchBy.setError("Please Enter Search Key");
                }
                break;
            case R.id.submit:
                appointmentBookedOrNot();
                //insertAppointmentApi();
                break;
           /* case R.id.cancel:
                //startActivity(new Intent(NewAppointmentActivity.this, MainActivity.class));
                NewAppointmentActivity.this.finish();
                break;*/
        }
    }

    private void appointmentBookedOrNot() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.appointmentBookedOrNot(mrno + "", userRoleId + "", scheduleId + "", currTime, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatusId() == EnumType.AppointmentOrNot.mAppointmentAlreadyFixed.getID()) {
                    showDialog(postPaymentModel.getMsg());
                } else if (postPaymentModel.getStatusId() == EnumType.AppointmentOrNot.mNewAppointment.getID()) {
                    insertAppointmentApi();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showDialog(String msg) {
        AlertDialog.Builder alerBuilder = new AlertDialog.Builder(
                this, R.style.MyAlertDialogTheme);
        alerBuilder.setCancelable(false);
        alerBuilder.setTitle(msg);
        alerBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
            }
        });
        ad = alerBuilder.show();
    }

    /*private void getPatientSearchDetail(String searchKey){
        patientSearchModelsArray=new PatientSearchModel[]{};
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getSearchPatient(userRoleId, searchKey, subTanentId, new Callback<PatientSearchModel[]>() {
            @Override
            public void success(PatientSearchModel[] patientSearchModels, Response response) {
                patientSearchModelsArray = patientSearchModels;
                //System.out.println("getMRNO-->" + patientSearchModelsArray[0].getPatname());
                dismissLoadingDialog();
                if (patientSearchModelsArray.length != 0) {
                    showPatientPopup();
                } else {
                    Toast.makeText(NewAppointmentActivity.this, "Record not Found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }*/
    private void getPatientSearchDetail(String searchKey) {
        patientSearchModelsArray = new PatientSearchModel[]{};
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.searchPatient_v1(1, 20, userRoleId, strSearchBy, searchKey, subTanentId, new Callback<SearchPatientModel>() {
            @Override
            public void success(SearchPatientModel patientSearchModels, Response response) {
                //patientSearchModel = patientSearchModels;
                Log.d("list_size", patientSearchModels.getData().size() + "");
                if (patientSearchModels.getData().size() > 0) {
                    for (int i = 0; i < patientSearchModels.getData().size(); i++) {
                        dataList.add(patientSearchModels.getData().get(i));
                    }
                    patientSearchModel.setData(dataList);
                    patientSearchModel.setStatus(patientSearchModels.getStatus());
                    patientSearchModel.setTotalResultCount(patientSearchModels.getTotalResultCount());
                    showSearchPatientPopup();
                    /*mAdapter = new SearchPatientAdapter_v1(SearchPatientActivity.this, patientSearchModel, userRoleId, hospitalSubtanentId);
                    mRecyclerView.setAdapter(mAdapter);*/
                } else {
                    //Toast.makeText(BillPaymentActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void getPatientSearchDetail_v2(int fIndex, String searchKey) {
        patientSearchModelsArray = new PatientSearchModel[]{};
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.searchPatient_v1(fIndex, 20, userRoleId, strSearchBy, searchKey, subTanentId, new Callback<SearchPatientModel>() {
            @Override
            public void success(SearchPatientModel patientSearchModels, Response response) {
                if (patientSearchModels.getData().size() > 0) {
                    flag_loading = false;
                    for (int i = 0; i < patientSearchModels.getData().size(); i++) {
                        dataList.add(patientSearchModels.getData().get(i));
                    }
                    patientSearchModel.setData(dataList);
                    Log.d("size", patientSearchModel.getData().size() + "");
                    searchPatientAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(NewAppointmentActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }

                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showSearchPatientPopup() {
        /*String names[] =new String[patientSearchModelsArray.length];
        for(int i=0;i<patientSearchModelsArray.length;i++){
            names[i]=patientSearchModelsArray[i].getPatname().toString().trim()+","+patientSearchModelsArray[i].getPatientContactDetails().getMobile().toString().trim()+","+patientSearchModelsArray[i].getMRNO().toString().trim();
        }*/
        alertDialog = new AlertDialog.Builder(NewAppointmentActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.search_patient_row_layout, null);
        alertDialog.setView(convertView);
        //alertDialog.setTitle("List");

        final ListView lv = (ListView) convertView.findViewById(R.id.search_patient_listview);

        searchPatientAdapter = new CheckInAdapter_v1(NewAppointmentActivity.this,
                android.R.layout.simple_spinner_item,
                patientSearchModel);
        lv.setAdapter(searchPatientAdapter);
        ad = alertDialog.show();
        lv.setOnItemClickListener(this);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("patmodelsize", patientSearchModel.getData().size() + "");
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (flag_loading == false) {
                        flag_loading = true;
                        getPatientSearchDetail_v2(firstIndex, searchKey);
                        firstIndex = firstIndex + 1;
                    }
                }
            }
        });
    }

    /**
     *
     */
    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(NewAppointmentActivity.this);
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

    /*private void showPatientPopup() {
        String names[] =new String[patientSearchModelsArray.length];
        for(int i=0;i<patientSearchModelsArray.length;i++){
            names[i]=patientSearchModelsArray[i].getPatname().toString().trim()+","+patientSearchModelsArray[i].getPatientContactDetails().getMobile().toString().trim()+","+patientSearchModelsArray[i].getMRNO().toString().trim();
        }
        alertDialog = new AlertDialog.Builder(NewAppointmentActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.search_patient_row_layout, null);
        alertDialog.setView(convertView);
        //alertDialog.setTitle("List");
        ListView lv = (ListView) convertView.findViewById(R.id.search_patient_listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.search_patient_row_text, names);
        lv.setAdapter(adapter);
        ad = alertDialog.show();
        lv.setOnItemClickListener(this);

    }
*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Datum datumModel = searchPatientAdapter.getItem(position);
        hospitalno = datumModel.getHospitalNo();
        patMobileNumber = datumModel.getMobileNo();

        String selected = datumModel.getPatName() + "," + datumModel.getMobileNo().trim() + "," + datumModel.getMrNo();
        searchBy.setText(selected);
        mrno = datumModel.getMrNo();
        ad.dismiss();
    }

    public void setAppointmentNature() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getAppNature(new Callback<AppointmentNature[]>() {
            @Override
            public void success(AppointmentNature[] appointmentNatures, Response response) {
                appointmentNaturesArray = appointmentNatures;
                setNatureToList();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void setNatureToList() {
        for (int i = 0; i < appointmentNaturesArray.length; i++) {
            //nature.add(appointmentNaturesArray[i].getAppNature());
            hmNature.put(appointmentNaturesArray[i].getAppNatureIdProperty(), appointmentNaturesArray[i].getAppNature());
        }
        nature = new ArrayList<String>(hmNature.values());
        System.out.print("nature-->" + nature);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.search_patient_row_text, nature);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointmentNature.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String natureName = parent.getItemAtPosition(position).toString();

        for (Integer natureId : hmNature.keySet()) {
            if (hmNature.get(natureId).equals(natureName)) {
                appNatureId = natureId;
                //Toast.makeText(parent.getContext(), "key: " + appNatureId, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void insertAppointmentApi() {
        JsonObject insertAppointmentObject = new JsonObject();

        JsonObject appBookingObject = new JsonObject();
        appBookingObject.addProperty("AppNatureId", appNatureId);
        appBookingObject.addProperty("CurrentStatusId", 1);
        appBookingObject.addProperty("Mrno", mrno);
        appBookingObject.addProperty("Others", "");
        appBookingObject.addProperty("self","0");

        insertAppointmentObject.addProperty("AppId", appId);
        insertAppointmentObject.addProperty("ordTxnId", 0);
        insertAppointmentObject.addProperty("orderId", 0);
        if(serviceRoleId==1){
            insertAppointmentObject.addProperty("serviceTypeId", 1);
        }else if(serviceRoleId==2){
            insertAppointmentObject.addProperty("serviceTypeId", 5);
        }else if(serviceRoleId==3){
            insertAppointmentObject.addProperty("serviceTypeId", 4);
        }

        insertAppointmentObject.addProperty("AvlStatusId", avlStatusId);
        insertAppointmentObject.addProperty("scheduleId", scheduleId);
        insertAppointmentObject.add("appbookings", appBookingObject);

        JsonObject obj = new JsonObject();
        obj.add("_Appointments", insertAppointmentObject);
        obj.addProperty("UserRoleID", userRoleId);
        Log.d("InsertAppointments", obj.toString());
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.insertAppointments(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                if (s.equals("true")) {
                    showAppointmentSuccessDialog("Appointment Booked Successfully.");
                }else{
                    showAppointmentErrorDialog("Appointment Booked Unsuccessfully.");
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error=" + error.getMessage());
                dismissLoadingDialog();
            }
        });
    }

    private void showAppointmentErrorDialog(String msg) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(NewAppointmentActivity.this);
        builder.setTitle("Failure");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                errorAlertDialog.dismiss();
            }
        });
        errorAlertDialog = builder.show();
    }

    private void showAppointmentSuccessDialog(String msg) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(NewAppointmentActivity.this);
        builder.setTitle("Success");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String cDate = month + "/" + date + "/" + year;
                Date createdOn = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate = null;
                try {
                    createdOn = sdf.parse(cDate);
                    formattedDate = sdf.format(createdOn);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date requestDate = null, responseDate = null;
                try {
                    requestDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                            .parse(currDate);
                    responseDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                            .parse(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("format compare", requestDate.compareTo(responseDate) + "");

                if (requestDate.compareTo(responseDate) == 0) {
                    postActivityTrackerFromAPI(true);
                } else {
                    postActivityTrackerFromAPI(false);
                }
                successAlertDialog.dismiss();
            }
        });
        successAlertDialog = builder.show();
    }

    private void postActivityTrackerFromAPI(final boolean status) {
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("actBuildVersion",buildVersionName);
        obj.addProperty("delivered",0);
        obj.addProperty("actUserRoleId",frontOfficeUserRoleId);
        obj.addProperty("actSubTenantId",subTanentId);
        obj.addProperty("actScheduleId",scheduleId);
        obj.addProperty("actAppId",appId);
        obj.addProperty("actUserMediumId",9);
        obj.addProperty("drUserRoleId",userRoleId);
        obj.addProperty("actRemarks","");
        obj.addProperty("actTransMasterId",actTransactId);
        obj.addProperty("patMrno",mrno);
        obj.addProperty("actOthers","");

        mCuraApplication.getInstance().mCuraEndPoint.postActivityTracker(obj, new Callback<PostActivityTrackerModel>() {
            @Override
            public void success(PostActivityTrackerModel postActivityTrackerModel, Response response) {
                if(status){
                    getScheduleStatus();
                }else{
                    startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                }
                dismissLoadingDialog();
            }
            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }
    private void showPopupforCheckin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewAppointmentActivity.this);
        builder.setMessage("Do you want to CheckIn");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547 || subTanentId == 557) {
                    setCheckInStatusWithFee();
                } else {
                    setCheckInStatusWithoutFee();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                ad.dismiss();
            }
        });
        ad = builder.show();
    }

    public void getScheduleStatus() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.scheduleStatus(scheduleId, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                if (generateTokenResultModel != null) {
                    if (generateTokenResultModel.getStatus() == ScheduleStatus.kScheduleStatusChartNotGenerated.getID()) {
                        showDialogGenerateToken();
                    } else if (generateTokenResultModel.getStatus() == ScheduleStatus.kScheduleStatusChartGenerated.getID()) {
                        showPopupforCheckin();
                    }
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void showDialogGenerateToken() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewAppointmentActivity.this);
        builder.setMessage("Do you want to start OPD");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                postStartOPD();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                ad.dismiss();
            }
        });
        ad = builder.show();

    }

    public void setCheckInStatusWithoutFee() {
        JsonObject obj = new JsonObject();
        obj.addProperty("MRNo", mrno);
        obj.addProperty("UserRoleId", userRoleId);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("Date", completeDate);
        obj.addProperty("appId",appId);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.patient_Check_In(obj, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                String msg = null;
                if (generateTokenResultModel != null) {
                    msg = generateTokenResultModel.getMsg();
                    chartGenerateStatus = generateTokenResultModel.getStatus();
                    if (chartGenerateStatus == 1) {
                        Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                        tokenStatus();
                    } else if (chartGenerateStatus == 2) {
                        Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 3) {
                        Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 4) {
                        startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                        Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 5) {
                        showErrorDialog(msg + " Do you want to pay now.");
                    }
                    //dialog.dismiss();
                } else {
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                //Toast.makeText(CheckInActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            /*Log.d("RetrofitError", error.toString());
            startActivity(new Intent(CheckInActivity.this, Queue_status.class));*/
                dismissLoadingDialog();
            }
        });
    }

    public void setCheckInStatusWithFee() {
        //Toast.makeText(CheckInActivity.this,"subtenentid = "+subTanentId,Toast.LENGTH_LONG).show();
        JsonObject obj = new JsonObject();
        obj.addProperty("MRNo", mrno);
        obj.addProperty("UserRoleId", userRoleId);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("Date", completeDate);
        obj.addProperty("serviceRoleId", serviceRoleId);
        obj.addProperty("appId",appId);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.patient_Check_In_version1(obj, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                String msg = null;
                if (generateTokenResultModel != null) {
                    msg = generateTokenResultModel.getMsg();
                    chartGenerateStatus = generateTokenResultModel.getStatus();
                    if (chartGenerateStatus == 1) {
                        Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                        //nextAvailableToken();
                        tokenStatus();
                    } else if (chartGenerateStatus == 2) {
                        Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 3) {
                        Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 4) {
                        startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                        Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 5) {
                        showErrorDialog(msg);
                    }else if (chartGenerateStatus == 6) {
                        showErrorDialog(msg);
                    } else {
                        Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                } else {
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                //Toast.makeText(CheckInActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            /*Log.d("RetrofitError", error.toString());
            startActivity(new Intent(CheckInActivity.this, Queue_status.class));*/
                dismissLoadingDialog();
            }
        });
    }

    private void showErrorDialog(String msg) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NewAppointmentActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (serviceRoleId == EnumType.ServiceRoleId.mDoctorServiceRoleId.getID()) {
                    setAppointmentNatureByUserRoleId();
                } else if (serviceRoleId == EnumType.ServiceRoleId.mPharmacyServiceRoleId.getID()) {
                    dialog.dismiss();
                } else if (serviceRoleId == EnumType.ServiceRoleId.mLabServiceRoleId.getID()) {
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                dialog.dismiss();
            }
        });
        android.app.AlertDialog dialog = builder.show();
    }

    public void setAppointmentNatureByUserRoleId() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getNatureByUserRole(userRoleId, subTanentId, new Callback<GetNatureByUserRoleModel[]>() {
            @Override
            public void success(GetNatureByUserRoleModel[] appointmentNatures, Response response) {
                if (appointmentNatures != null && appointmentNatures.length > 0) {
                    showPaymentDialog(appointmentNatures);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void getLastBillDetail() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.lastBillDetail(userRoleId, mrno + "", new Callback<LastBillDetailModel>() {
            @Override
            public void success(LastBillDetailModel lastBillDetailModel, Response response) {
                if (lastBillDetailModel.getAmount() != 0) {
                    ll_last_record.setVisibility(View.VISIBLE);
                    tv_no_record_found_msg.setVisibility(View.GONE);
                    tv_nature_name.setText(lastBillDetailModel.getPymtNature());
                    tv_last_record_date.setText(lastBillDetailModel.getBillDate());
                } else {
                    ll_last_record.setVisibility(View.GONE);
                    tv_no_record_found_msg.setVisibility(View.VISIBLE);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showPaymentDialog(GetNatureByUserRoleModel[] appointmentNatures) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewAppointmentActivity.this);
        LayoutInflater inflater = LayoutInflater.from(NewAppointmentActivity.this);
        View convertView = (View) inflater.inflate(R.layout.custom_appointment_layout_dialog, null);
        builder.setView(convertView);
        String docProfilePic = mSharedPreference.getString(Constant.USER_PROFILE_PIC, "default");
        String docName = mSharedPreference.getString(Constant.LOGIN_NAME_KEY, "Undefine");
        String departmentName = dbHelper.Get_DoctorDepartment(userRoleId + "");
        Log.d("departmentName", departmentName);

        tv_no_record_found_msg = (TextView) convertView.findViewById(R.id.tv_no_record_found_msg);
        ll_last_record = (LinearLayout) convertView.findViewById(R.id.ll_last_record);
        tv_nature_name = (TextView) convertView.findViewById(R.id.tv_nature_name);
        tv_last_record_date = (TextView) convertView.findViewById(R.id.tv_last_record_date);
        getLastBillDetail();
        ImageView ivDoctorProfile = (ImageView) convertView.findViewById(R.id.activity_calendar_iv_doctor);
        TextView doctorName = (TextView) convertView.findViewById(R.id.docname);
        TextView docdept = (TextView) convertView.findViewById(R.id.docdept);
        docdept.setText(departmentName.toString());
        doctorName.setText(docName.toString());
        if (docProfilePic != "default") {
            Picasso.with(this).load(docProfilePic).into(ivDoctorProfile);
        }
        tv_bill_amount = (TextView) convertView.findViewById(R.id.tv_bill_amount);
        Spinner nature_spinner = (Spinner) convertView.findViewById(R.id.nature_spinner);
        getNatureByUserRoleIdAdapter = new GetNatureByUserRoleIdAdapter(NewAppointmentActivity.this,
                android.R.layout.simple_spinner_item,
                appointmentNatures);
        nature_spinner.setAdapter(getNatureByUserRoleIdAdapter);
        btn_later = (TextView) convertView.findViewById(R.id.btn_later);
        btn_pay_now = (TextView) convertView.findViewById(R.id.btn_pay_now);
        dialog = builder.show();
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width * 1.0);
        lp.alpha = 1.0f; // Transparency

        dialogWindow.setAttributes(lp);
        btn_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                dialog.dismiss();
            }
        });
        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPaymentAPI_V1();
            }
        });
        nature_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getNatureByUserRoleModel = getNatureByUserRoleIdAdapter.getItem(position);
                appNatureId = getNatureByUserRoleModel.getAppNatureID();
                if (userRoleId != 0 && appNatureId != 0) {
                    if (appNatureId == 33 || appNatureId == 32) {
                        Log.d("c_fee", appNatureId + " is 33");
                        tv_bill_amount.setText("Total Amount: " + 0 + "/-");
                    } else if (appNatureId == 25) {
                        Log.d("c_fee", appNatureId + " is 25");
                        tv_bill_amount.setText("Total Amount: " + 0 + "/-");
                    } else if (appNatureId != 25) {
                        Log.d("c_fee", appNatureId + " not 25");
                        getFeeFromAPI();
                    }
                } else {
                    tv_bill_amount.setText("Total Amount: " + 0 + "/-");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getFeeFromAPI() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.feeFetch(userRoleId, appNatureId, subTanentId, new Callback<FeeFetch>() {
            @Override
            public void success(FeeFetch feeFetch, Response response) {
                if (feeFetch.getUserRoleId() != null) {
                    feeAmount = feeFetch.getFeeAmount();
                    tv_bill_amount.setText("Rs. " + feeFetch.getFeeAmount() + "/-");
                } else {
                    Toast.makeText(NewAppointmentActivity.this, "Sorry Detail Not Available for this User", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void postPaymentAPI_V1() {
        JsonObject obj = new JsonObject();


        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", hospitalno);
        obj.addProperty("AppnatureId", appNatureId);
        obj.addProperty("UserRoleId", userRoleId);
        obj.addProperty("Description", "Doctor Fee");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", feeAmount);
        obj.addProperty("AppId", appId);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", 1);
        obj.addProperty("Mrno", mrno);
        obj.addProperty("HIS_BillNo", 0);
        obj.addProperty("MobileNo", patMobileNumber);
        obj.addProperty("orderId", 0);
        obj.addProperty("ScheduleId", scheduleId);
        JsonArray objectKeyArray = new JsonArray();
        //objectKeyArray.add(new JsonPrimitive(""));
        obj.add("OrdTxnIds", objectKeyArray);

        if (paymentMode.equals("2")) {
            postPaymentAPIForCard(obj);
        }
    }

    private void postPaymentAPIForCard(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentDocFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {

                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(NewAppointmentActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547 || subTanentId == 557) {
                        setCheckInStatusWithFee();
                    } else {
                        setCheckInStatusWithoutFee();
                    }
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(NewAppointmentActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentBlankScheduleId.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }



                /*if (postPaymentModel.getMsg().equals("Balance is less than Bill Amount. Please Add Amount")) {
                    showErrorDialog(postPaymentModel.getMsg());
                } else {
                    if(subTanentId == 515 || subTanentId== 528 || subTanentId == 547 || subTanentId == 557){
                        setCheckInStatusWithFee();
                        *//*if(userRoleId==2331 || userRoleId==2332 || userRoleId==2333){
                            setCheckInStatusWithoutFee();
                        }else{
                            setCheckInStatusWithFee();
                        }*//*
                    }else{
                        setCheckInStatusWithoutFee();
                    }
                }*/
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void tokenStatus() {
        //Toast.makeText(CheckInActivity.this,mr_no+"---mr_no", Toast.LENGTH_LONG).show();
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.token_Status(mrno, userRoleId, subTanentId, scheduleId, completeDate, new Callback<TokenStatusModel[]>() {
            @Override
            public void success(TokenStatusModel[] tokenStatusModel, Response response) {
                if (tokenStatusModel.length > 0) {
                    String tokenStatus = tokenStatusModel[0].getTokenStatus();
                    if (tokenStatus.equals("PRE_BOOKED")) {
                        //Toast.makeText(CheckInActivity.this,tokenStatusModelArray[0].getTokenNo().toString(), Toast.LENGTH_LONG).show();
                    } else if (tokenStatus.equals("CHECK_IN")) {
                        currToken = Integer.parseInt(tokenStatusModel[0].getTokenNo().toString());
                        //startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                        nextAvailableTokendialog();
                    }
                } else {
                }
                //dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void postStartOPD() {

        JsonObject obj = new JsonObject();
        obj.addProperty("UserRoleId", userRoleId);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("Date", completeDate);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.generate_Token_Chart(obj, new Callback<GenerateTokenResultModel>() {

            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                String msg = null;
                msg = generateTokenResultModel.getMsg();
                chartGenerateStatus = generateTokenResultModel.getStatus();
                if (chartGenerateStatus == 1) {
                    Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    showPopupforCheckin();
                } else if (chartGenerateStatus == 2) {
                    Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    showPopupforCheckin();
                } else if (chartGenerateStatus == 3) {
                    Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                } else {
                    Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                }
                //dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
                //Toast.makeText(Queue_status.this,error.toString(), Toast.LENGTH_LONG).show();
                /*Log.d("RetrofitError",error.toString());
                getQueueData();*/

            }
        });
    }

    public void nextAvailableTokendialog() {
        //showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.next_Availabel_Token(userRoleId, subTanentId, scheduleId, completeDate, new Callback<CurrentTokenModel[]>() {
            @Override
            public void success(CurrentTokenModel[] currentTokenModels, Response response) {
                currentTokenModelsArray = currentTokenModels;
                if (currentTokenModelsArray.length > 0) {
                    //Toast.makeText(CheckInActivity.this,currentTokenModels[0].getStatus()+"===check in status",Toast.LENGTH_LONG).show();
                    if (currentTokenModels[0].getStatus() == NextAvailTokenStatus.kNextAvailTokenStatusAvailable.getID()) {
                        nextToken = currentTokenModels[0].getTokenNo();
                        if (nextToken != 0) {
                            if (currToken > nextToken) {
                                showPopUp();
                            } else {
                                Toast.makeText(NewAppointmentActivity.this, "Token number " + currToken + " Allocated.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                            }
                        } /*else {

                        }*/
                    } else if (currentTokenModels[0].getStatus() == NextAvailTokenStatus.kNextAvailTokenStatusNotAvailable.getID()) {
                        Toast.makeText(NewAppointmentActivity.this, "Please try again", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //Toast.makeText(MainActivity.this,"OPD NOT STARTED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showPopUp() {
        alertDialog = new AlertDialog.Builder(NewAppointmentActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.alert_dialog_message, null);
        alertDialog.setView(convertView);
        TextView alert_message = (TextView) convertView.findViewById(R.id.alert_message);
        alert_message.setText("Allocated Token " + currToken + ",   But Earlier Token " + nextToken + " Available.");
        TextView alert_yes = (TextView) convertView.findViewById(R.id.alert_yes);
        TextView alert_no = (TextView) convertView.findViewById(R.id.alert_no);
        alert_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTokenList();
                ad.dismiss();
            }
        });
        alert_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                ad.dismiss();
            }
        });

        ad = alertDialog.show();
    }

    public void moveTokenList() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.move_Token_List(mrno, userRoleId, subTanentId, scheduleId, nextToken, completeDate, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                int status = generateTokenResultModel.getStatus();
                String msg = generateTokenResultModel.getMsg();
                if (status == 1) {
                    Toast.makeText(NewAppointmentActivity.this, "Token number " + nextToken + " Allocated.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(NewAppointmentActivity.this, CalendarActivity.class));
                    //Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                } else if (status == 4) {
                    Toast.makeText(NewAppointmentActivity.this, msg, Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }
}
