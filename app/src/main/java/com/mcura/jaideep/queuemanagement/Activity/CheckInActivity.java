package com.mcura.jaideep.queuemanagement.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.mcura.jaideep.queuemanagement.Adapter.CheckInAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.CheckInAdapter_v1;
import com.mcura.jaideep.queuemanagement.Adapter.GetNatureByUserRoleIdAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.SearchPatientAdapter_v1;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.CurrentTokenModel;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.FeeFetch;
import com.mcura.jaideep.queuemanagement.Model.GenerateTokenResultModel;
import com.mcura.jaideep.queuemanagement.Model.GetNatureByUserRoleModel;
import com.mcura.jaideep.queuemanagement.Model.LastBillDetailModel;
import com.mcura.jaideep.queuemanagement.Model.MainModel;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.PostActivityTrackerModel.PostActivityTrackerModel;
import com.mcura.jaideep.queuemanagement.Model.PostPaymentModel;
import com.mcura.jaideep.queuemanagement.Model.QueueStatus;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.Model.TokenStatusModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.SqliteHelper.SqlLiteDbHelper;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private SearchPatientModel patientSearchModel;
    private String query;
    private ArrayList<Datum> dataList;
    private int firstIndex;
    private boolean flag_loading;
    private GetNatureByUserRoleIdAdapter getNatureByUserRoleIdAdapter;
    private TextView tv_bill_amount;
    private TextView btn_later;
    private TextView btn_pay_now;
    private GetNatureByUserRoleModel getNatureByUserRoleModel;
    private Integer appNatureId=1;
    private String paymentMode = "2";
    private String hospitalno;
    private String patMobileNumber;
    private Integer feeAmount;
    private int frontOfficeUserRoleId;
    private String currDate;
    private int serviceRoleId;
    private TextView billing,tv_fillcard;
    private int actTransactId=0;
    private String buildVersionName;
    /*public enum MoveTokenStatus {
        kMoveTokenStatusSuccess(1), kMoveTokenStatusInvalidTry(3), kMoveTokenStatusError(4);
        private int id;

        MoveTokenStatus(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }*/


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

    public static int mr_no;
    public int user_role_id;
    public int subTanentId, nfcsubTanentId;
    public String hospital_id;
    String ageS;
    String tokenStatus;
    CircleImageView profile_image;
    TextView checkin_pat_name, checkin_pat_age, checkin_pat_gender, checkin_pat_mobile;
    public PatientSearchModel[] patientSearchModelsArray;
    AlertDialog.Builder alertDialog;
    AlertDialog ad;
    CheckInAdapter_v1 searchPatientAdapter;
    EditText et_search_key;
    //ImageButton ib_search_icon;
    ProgressDialog progressDialog;
    public MCuraApplication mCuraApplication;
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private NfcAdapter mNfcAdapter;
    private LinearLayout ll_checkin;
    int year, month, date;
    String completeDate;
    ImageView queue_current_status, queue_queue_status, queue_checkIn, queue_visti_entry, logout;
    ImageButton load_nfc;
    boolean status;
    CurrentTokenModel[] currentTokenModelsArray;
    TextView checkin_token_number, doctorName;
    TokenStatusModel[] tokenStatusModelArray;
    int chartGenerateStatus;
    private SharedPreferences mSharedPreference;
    SharedPreferences.Editor editor;
    int scheduleId;
    RelativeLayout ib_search_icon;
    ImageButton checkin, visiting, add;
    TextView appointment, queue_mgmt;
    String docName;
    ImageView ivDoctorProfile;
    int nextToken, currToken, tokenNo, appId;
    private TextView tv_no_record_found_msg;
    private LinearLayout ll_last_record;
    private TextView tv_nature_name;
    private TextView tv_last_record_date;
    SqlLiteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        //getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        dbHelper = new SqlLiteDbHelper(this);
        dbHelper.openDataBase();
        actTransactId = EnumType.ActTransactMasterEnum.Booking_APT.getActTransactMasterTypeId();
        tokenNo = getIntent().getIntExtra("TokenNo", 0);
        appId = getIntent().getIntExtra("appId", 0);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /*PatientSearchModel patientSearchModel = (PatientSearchModel) getIntent().getSerializableExtra("patientSearchModel");
        MainModel mainModel = (MainModel) getIntent().getSerializableExtra("mainModelData");*/
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        String docProfilePic = mSharedPreference.getString(Constant.USER_PROFILE_PIC, "default");
        buildVersionName = mSharedPreference.getString(Constant.BUILD_VERSION_NAME,"");
        scheduleId = mSharedPreference.getInt(Constant.SCHEDULE_ID, 0);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
        editor = mSharedPreference.edit();
        docName = mSharedPreference.getString(Constant.LOGIN_NAME_KEY, "Undefine");
        user_role_id = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        subTanentId = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        serviceRoleId = mSharedPreference.getInt(Constant.SERVICE_ROLE_ID, 0);
        String subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        completeDate = year + "-" + month + "-" + date; //"2016-05-09"
        currDate = month + "/" + date + "/" + year;
        /*mr_no = getIntent().getIntExtra("mrno",0);
        if(mr_no > 0){
            getPatDataFromNFC();
        }*/

        Log.d("completeDate", completeDate);
        NfcManager manager = (NfcManager) this.getSystemService(Context.NFC_SERVICE);
        try {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        } catch (NullPointerException ex) {

        }

        //nextAvailableToken();
        if (mNfcAdapter != null && mNfcAdapter.isEnabled()) {

            //Yes NFC available
        } else {
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            //Your device doesn't support NFC
        }
        /*if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC

            finish();
            return;

        }

        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(CheckInActivity.this,"NFC is disabled.",Toast.LENGTH_LONG).show();
        } else {
        }*/
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        handleIntent(getIntent());
        checkin_token_number = (TextView) findViewById(R.id.checkin_token_number);
        if (tokenNo > 0) {
            checkin_token_number.setText(tokenNo + "");
        }
        checkin_pat_gender = (TextView) findViewById(R.id.checkin_pat_gender);
        checkin_pat_age = (TextView) findViewById(R.id.checkin_pat_age);
        checkin_pat_name = (TextView) findViewById(R.id.checkin_pat_name);
        checkin_pat_mobile = (TextView) findViewById(R.id.checkin_pat_mobile);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        et_search_key = (EditText) findViewById(R.id.et_search_key);

        ib_search_icon = (RelativeLayout) findViewById(R.id.ib_search_icon);
        ll_checkin = (LinearLayout) findViewById(R.id.ll_checkin);
        logout = (ImageView) mToolbar.findViewById(R.id.logout);
        checkin = (ImageButton) findViewById(R.id.chk_in);
        visiting = (ImageButton) findViewById(R.id.visiting);
        billing = (TextView) findViewById(R.id.billing);
        tv_fillcard = (TextView) findViewById(R.id.tv_fillcard);
        //add = (ImageButton) findViewById(R.id.add_icon);
        appointment = (TextView) findViewById(R.id.appointment);
        queue_mgmt = (TextView) findViewById(R.id.queue_mgmt);
        ivDoctorProfile = (ImageView) findViewById(R.id.activity_calendar_iv_doctor);
        doctorName = (TextView) findViewById(R.id.docname);
        //set hospital logo
        ImageView hospital_logo = (ImageView) mToolbar.findViewById(R.id.hospital_logo);
        Picasso.with(CheckInActivity.this).load(subtanentImagePath).into(hospital_logo);
        /*if(!subtanentImagePath.equals("default")){
            Picasso.with(CheckInActivity.this).load(subtanentImagePath).into(hospital_logo);
        }else{
            Picasso.with(CheckInActivity.this).load(R.drawable.kims_logo).into(hospital_logo);
        }*/
        /*if(subTanentId == 500){
            hospital_logo.setImageResource(R.drawable.blk_logo);
        }else if(subTanentId == 243){
            hospital_logo.setImageResource(R.drawable.sgrh);
        }
*/
        if (docProfilePic != "default") {
            Picasso.with(this).load(docProfilePic).into(ivDoctorProfile);
        }
        //Picasso.with(this).load(docProfilePic).into(ivDoctorProfile);
        /*if(user_role_id==2104){
            ivDoctorProfile.setImageResource(R.drawable.doctor_atul_profile);
        }
        else if(user_role_id==2105){
            ivDoctorProfile.setImageResource(R.drawable.doctor_rajiv_pfofile);
        }*/
        doctorName.setText(docName.toString());
        load_nfc = (ImageButton) findViewById(R.id.load_nfc);
        load_nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckInActivity.this, SearchPatientActivity.class).putExtra("userroleid", user_role_id));
            }
        });
        /*queue_current_status = (ImageView) findViewById(R.id.checkin_current_status_tab);
        queue_queue_status=(ImageView)findViewById(R.id.checkin_queue_status);
        queue_checkIn=(ImageView)findViewById(R.id.checkin_checkIn);
        queue_visti_entry=(ImageView)findViewById(R.id.checkin_visit_entry);*/
        appointment.setOnClickListener(this);
        queue_mgmt.setOnClickListener(this);
        logout.setOnClickListener(this);
        checkin.setOnClickListener(this);
        visiting.setOnClickListener(this);
        billing.setOnClickListener(this);
        tv_fillcard.setOnClickListener(this);
        //add.setOnClickListener(this);
        /*queue_current_status.setOnClickListener(this);
        queue_queue_status.setOnClickListener(this);
        queue_checkIn.setOnClickListener(this);
        queue_visti_entry.setOnClickListener(this);*/
        ll_checkin.setOnClickListener(this);
        ll_checkin.setClickable(false);
        ib_search_icon.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         */
        try {
            setupForegroundDispatch(this, mNfcAdapter);
        } catch (NullPointerException ex) {
        }

    }

    @Override
    protected void onPause() {
        /**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
        try {
            stopForegroundDispatch(this, mNfcAdapter);
        } catch (NullPointerException ex) {
        }
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    public void showPopUp() {
        alertDialog = new AlertDialog.Builder(CheckInActivity.this);
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
                checkin_token_number.setText(currToken + "");
                ad.dismiss();
            }
        });
        //alertDialog.setMessage("Previous token number "+nextToken+" available Do you want to move to it");
        /*alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTokenList();
                ad.dismiss();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //setCheckInStatus();
                checkin_token_number.setText(currToken+"");
                ad.dismiss();
            }
        });*/
        ad = alertDialog.show();
    }

    public void moveTokenList() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.move_Token_List(mr_no, user_role_id, subTanentId, scheduleId, nextToken, completeDate, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                int status = generateTokenResultModel.getStatus();
                String msg = generateTokenResultModel.getMsg();
                if (status == 1) {
                    Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                    checkin_token_number.setText(nextToken + "");
                    appId = generateTokenResultModel.getAppId();
                    //getQueueData();
                    //setCheckInStatus();
                } else if (status == 4) {
                    Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                }
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
            case R.id.ib_search_icon:
                String searchKey = et_search_key.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        et_search_key.getWindowToken(), 0);
                //getPatientSearchDetail(searchKey);
                firstIndex = 2;
                query = searchKey;
                patientSearchModel = new SearchPatientModel();
                dataList = new ArrayList<>();
                getPatientSearchDetail(this.query);
                break;
            case R.id.ll_checkin:
                if (tokenStatus.equals("PRE_BOOKED")) {
                    //setCheckInStatus();
                    if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547) {
                        setCheckInStatusWithFee();
                        /*if(user_role_id==2331 || user_role_id==2332 || user_role_id==2333){
                            setCheckInStatusWithoutFee();
                        }else{
                            setCheckInStatusWithFee();
                        }*/
                    } else {
                        setCheckInStatusWithoutFee();
                    }
                } else if (tokenStatus.equals("CHECK_IN")) {
                    Toast.makeText(CheckInActivity.this, "You are already Checked In", Toast.LENGTH_LONG).show();
                } else if (tokenStatus.equals("NEW_USER")) {
                    appointmentBookedOrNot();
                } else if (tokenStatus.equals("BLOCK_USER")) {
                    //setCheckInStatusforBlock();
                    if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547) {
                        setCheckInStatusForBlockWithFee();
                        /*if(user_role_id==2331 || user_role_id==2332 || user_role_id==2333){
                            setCheckInStatusForBlockWithoutFee();
                        }else{
                            setCheckInStatusForBlockWithFee();
                        }*/
                    } else {
                        setCheckInStatusForBlockWithoutFee();
                    }
                }
                break;
            case R.id.chk_in:
                startActivity(new Intent(CheckInActivity.this, CheckInActivity.class));
                break;
            case R.id.visiting:
                startActivity(new Intent(CheckInActivity.this, VisitingActivity.class));
                break;
            case R.id.appointment:
                startActivity(new Intent(CheckInActivity.this, CalendarActivity.class));
                break;
            case R.id.queue_mgmt:
                startActivity(new Intent(CheckInActivity.this, QueueStatusActivity.class));
                break;
            case R.id.billing:
                startActivity(new Intent(CheckInActivity.this, CentralizedBillingActivity.class));
                break;
            case R.id.tv_fillcard:
                startActivity(new Intent(CheckInActivity.this, FillCashCardActivity.class));
                break;
            /*case R.id.checkin_current_status_tab:
                Intent intent=new Intent(CheckInActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.checkin_queue_status:
                Intent in=new Intent(CheckInActivity.this,Queue_status.class);
                startActivity(in);
                break;
            case R.id.checkin_visit_entry:
                Intent intent1=new Intent(CheckInActivity.this,VisitActivity.class);
                startActivity(intent1);
                break;*/
            case R.id.logout:
                SharedPreferences.Editor editor = mSharedPreference.edit();
                editor.remove(Constant.USER_ROLE_ID_KEY);
                editor.remove(Constant.SUB_TANENT_IMAGE_PATH);
                editor.apply();
                Intent intentLogout = new Intent(CheckInActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                break;
        }
    }

    /*private void getPatientSearchDetail(String searchKey) {
        patientSearchModelsArray = new PatientSearchModel[]{};
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getSearchPatient(user_role_id, searchKey, subTanentId, new Callback<PatientSearchModel[]>() {
            @Override
            public void success(PatientSearchModel[] patientSearchModels, Response response) {
                patientSearchModelsArray = patientSearchModels;
                //System.out.println("getMRNO-->" + patientSearchModelsArray[0].getPatname());
                dismissLoadingDialog();
                if (patientSearchModelsArray.length != 0) {
                    showPatientPopup();
                } else {
                    Toast.makeText(CheckInActivity.this, "Record not Found", Toast.LENGTH_LONG).show();
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
        mCuraApplication.getInstance().mCuraEndPoint.searchPatient_v1(1, 20, user_role_id, "1,2,3,4", searchKey, subTanentId, new Callback<SearchPatientModel>() {
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
                    showPatientPopup();
                    /*mAdapter = new SearchPatientAdapter_v1(SearchPatientActivity.this, patientSearchModel, userRoleId, hospitalSubtanentId);
                    mRecyclerView.setAdapter(mAdapter);*/
                } else {
                    //Toast.makeText(CheckInActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
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
        mCuraApplication.getInstance().mCuraEndPoint.searchPatient_v1(fIndex, 20, user_role_id, "1,2,3,4", searchKey, subTanentId, new Callback<SearchPatientModel>() {
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
                    Toast.makeText(CheckInActivity.this, "No More Record Found", Toast.LENGTH_SHORT).show();
                }

                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showPatientPopup() {
        /*String names[] =new String[patientSearchModelsArray.length];
        for(int i=0;i<patientSearchModelsArray.length;i++){
            names[i]=patientSearchModelsArray[i].getPatname().toString().trim();
        }*/
        alertDialog = new AlertDialog.Builder(CheckInActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.search_patient_row_layout, null);
        alertDialog.setView(convertView);
        //alertDialog.setTitle("List");
        final ListView lv = (ListView) convertView.findViewById(R.id.search_patient_listview);

        searchPatientAdapter = new CheckInAdapter_v1(CheckInActivity.this,
                android.R.layout.simple_spinner_item,
                patientSearchModel);
        lv.setAdapter(searchPatientAdapter);
        ad = alertDialog.show();

        ad.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ad.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        Window dialogWindow = ad.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = (int) (height * 0.7);
        lp.alpha = 1.0f; // Transparency
        dialogWindow.setAttributes(lp);

        lv.setOnItemClickListener(this);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("patmodelsize", patientSearchModel.getData().size() + "");
                /*if (lv.getLastVisiblePosition() == patientSearchModel.getData().size()-1) {
                    Log.d("firstIndex", firstIndex + "");
                    getPatientSearchDetail_v2(firstIndex, query);
                    firstIndex=firstIndex+1;
                }*/
                /*if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (flag_loading == false) {
                        flag_loading = true;
                        getPatientSearchDetail_v2(firstIndex, query);
                        firstIndex = firstIndex + 1;
                    }
                }*/
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount) && !(flag_loading)) {
                    flag_loading = true;
                    getPatientSearchDetail_v2(firstIndex, query);
                    firstIndex = firstIndex + 1;
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Datum patientSearchModel = searchPatientAdapter.getItem(position);
        hospital_id = patientSearchModel.getHospitalNo();
        patMobileNumber = patientSearchModel.getMobileNo();
        checkin_pat_name.setText(patientSearchModel.getPatName());
        mr_no = patientSearchModel.getMrNo();
        String dobEncode = patientSearchModel.getDob();
        String timestamp = dobEncode.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date(Long.parseLong(timestamp));
        SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
        String formattedDate = sdf.format(createdOn);
        System.out.println("formattedDate-->" + formattedDate);
        String[] out = formattedDate.split(",");
        System.out.println("Year = " + out[2]);
        System.out.println("Month = " + out[0]);
        System.out.println("Day = " + out[1]);

        int year = Integer.parseInt(out[2]);
        int month = Integer.parseInt(out[0]);
        int day = Integer.parseInt(out[1]);

        String patAge = getAge(year, month, day);
        checkin_pat_age.setText(patAge);
        int gender = patientSearchModel.getGenderId();
        if (gender == 1) {
            checkin_pat_gender.setText("M");
        } else {
            checkin_pat_gender.setText("F");
        }
        checkin_pat_mobile.setText(patientSearchModel.getMobileNo());
        if (tokenNo > 0) {
            tokenStatus = "BLOCK_USER";
            ll_checkin.setClickable(true);
            //setCheckInStatus();
        } else {
            tokenStatus();
        }
        ad.dismiss();
    }

    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        ageS = ageInt.toString();
        System.out.println("age-->" + age);
        return ageS;
    }

    private class GetMainData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            getPatDataFromNFC();
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoadingDialog();
        }

        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void getPatDataFromNFC() {
        mCuraApplication.getInstance().mCuraEndPoint.getPatient(mr_no, user_role_id, subTanentId, new Callback<MainModel>() {
            @Override
            public void success(MainModel mainModel, Response response) {
                checkin_pat_name.setText(mainModel.getPatname().toString());
                hospital_id = mainModel.getHospitalNo();
                patMobileNumber = mainModel.getPatientContactDetails().getMobile();
                String dobEncode = mainModel.getDob();
                //mr_no = mainModel.getMRNO();
                String timestamp = dobEncode.split("\\(")[1].split("\\+")[0];
                Date createdOn = new Date(Long.parseLong(timestamp));
                SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
                String formattedDate = sdf.format(createdOn);
                System.out.println("formattedDate-->" + formattedDate);
                String[] out = formattedDate.split(",");
                System.out.println("Year = " + out[2]);
                System.out.println("Month = " + out[0]);
                System.out.println("Day = " + out[1]);

                int year = Integer.parseInt(out[2]);
                int month = Integer.parseInt(out[0]);
                int day = Integer.parseInt(out[1]);

                String patAge = getAge(year, month, day);
                checkin_pat_age.setText(patAge + "Y ");
                int gender = mainModel.getGenderId();
                if (gender == 1) {
                    checkin_pat_gender.setText(" M");
                } else {
                    checkin_pat_gender.setText(" F");
                }
                checkin_pat_mobile.setText(mainModel.getPatientContactDetails().getMobile());
                if (tokenNo > 0) {
                    tokenStatus = "BLOCK_USER";
                    ll_checkin.setClickable(true);
                    //setCheckInStatus();
                } else {
                    tokenStatus();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

            byte[] payload = record.getPayload();

            // Get the Text Encoding
            //String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            String textEncoding;/* = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"*/ // Get the Text Encoding
            if ((payload[0] & 128) == 0) {
                textEncoding = "UTF-8";
            } else {
                textEncoding = "UTF-16";
            }
            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("NFC_Record", result);
            //Toast.makeText(CheckInActivity.this,result+"",Toast.LENGTH_LONG).show();
            if (result != null) {
                try {
                    JSONObject obj = new JSONObject(result);
                    mr_no = obj.getInt("mr_no");
                    nfcsubTanentId = obj.getInt("sub_tenant_id");
                    hospital_id = obj.getString("hospital_id");
                    if (subTanentId == nfcsubTanentId) {
                        new GetMainData().execute("");
                    } else {
                        Toast.makeText(CheckInActivity.this, "This Card is not for this Hospital", Toast.LENGTH_LONG).show();
                    }
                    //setCheckInStatus();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    /**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        } catch (NullPointerException ex) {
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    public void setCheckInStatusWithoutFee() {
        JsonObject obj = new JsonObject();
        obj.addProperty("MRNo", mr_no);
        obj.addProperty("UserRoleId", user_role_id);
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
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CheckInActivity.this, QueueStatusActivity.class));
                    } else if (chartGenerateStatus == 2) {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 3) {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 5) {
                        showErrorDialog(msg + " Do you want to pay now.");
                    } else {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
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
        obj.addProperty("MRNo", mr_no);
        obj.addProperty("UserRoleId", user_role_id);
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
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CheckInActivity.this, QueueStatusActivity.class));
                    } else if (chartGenerateStatus == 2) {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 3) {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 5) {
                        showErrorDialog(msg);
                    } else if (chartGenerateStatus == 6) {
                        showErrorDialog(msg);
                    } else {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CheckInActivity.this);
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
                dialog.dismiss();
            }
        });
        android.app.AlertDialog dialog = builder.show();
    }

    public void setAppointmentNatureByUserRoleId() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getNatureByUserRole(user_role_id, subTanentId, new Callback<GetNatureByUserRoleModel[]>() {
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

    private void showPaymentDialog(GetNatureByUserRoleModel[] appointmentNatures) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckInActivity.this);
        LayoutInflater inflater = LayoutInflater.from(CheckInActivity.this);
        View convertView = (View) inflater.inflate(R.layout.custom_appointment_layout_dialog, null);
        builder.setView(convertView);
        String docProfilePic = mSharedPreference.getString(Constant.USER_PROFILE_PIC, "default");
        String docName = mSharedPreference.getString(Constant.LOGIN_NAME_KEY, "Undefine");
        String departmentName = dbHelper.Get_DoctorDepartment(user_role_id + "");
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
        getNatureByUserRoleIdAdapter = new GetNatureByUserRoleIdAdapter(CheckInActivity.this,
                android.R.layout.simple_spinner_item,
                appointmentNatures);
        nature_spinner.setAdapter(getNatureByUserRoleIdAdapter);
        btn_later = (TextView) convertView.findViewById(R.id.btn_later);
        btn_pay_now = (TextView) convertView.findViewById(R.id.btn_pay_now);
        final AlertDialog dialog = builder.show();
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
                if (user_role_id != 0 && appNatureId != 0) {
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

    private void postPaymentAPI_V1() {
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", hospital_id);
        obj.addProperty("AppnatureId", appNatureId);
        obj.addProperty("UserRoleId", user_role_id);
        obj.addProperty("Description", "Doctor Fee");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", feeAmount);
        obj.addProperty("AppId", appId);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", 1);
        obj.addProperty("Mrno", mr_no);
        obj.addProperty("HIS_BillNo", 0);
        obj.addProperty("MobileNo", patMobileNumber);
        //obj.addProperty("orderId", 0);
        obj.addProperty("ScheduleId", scheduleId);
        /*JsonArray objectKeyArray = new JsonArray();
        objectKeyArray.add(new JsonPrimitive(""));
        obj.add("OrdTxnIds", objectKeyArray);*/
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
                    Toast.makeText(CheckInActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    if (tokenStatus.equals("BLOCK_USER")) {
                        if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547) {
                            setCheckInStatusForBlockWithFee();
                        } else {
                            setCheckInStatusForBlockWithoutFee();
                        }
                    } else {
                        if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547) {
                            setCheckInStatusWithFee();
                        } else {
                            setCheckInStatusWithoutFee();
                        }
                    }
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(CheckInActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
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
                    if (tokenStatus.equals("BLOCK_USER")) {
                        if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547 ||  subTanentId == 557) {
                            setCheckInStatusForBlockWithFee();
                            *//*if(user_role_id==2331 || user_role_id==2332 || user_role_id==2333){
                                setCheckInStatusForBlockWithoutFee();
                            }else{
                                setCheckInStatusForBlockWithFee();
                            }*//*
                        } else {
                            setCheckInStatusForBlockWithoutFee();
                        }
                    } else {
                        if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547 ||  subTanentId == 557) {
                            setCheckInStatusWithFee();
                            *//*if(user_role_id==2331 || user_role_id==2332 || user_role_id==2333){
                                setCheckInStatusWithoutFee();
                            }else{
                                setCheckInStatusWithFee();
                            }*//*
                        } else {
                            setCheckInStatusWithoutFee();
                        }
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

    private void getLastBillDetail() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.lastBillDetail(user_role_id, mr_no + "", new Callback<LastBillDetailModel>() {
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

    private void getFeeFromAPI() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.feeFetch(user_role_id, appNatureId, subTanentId, new Callback<FeeFetch>() {
            @Override
            public void success(FeeFetch feeFetch, Response response) {
                if (feeFetch.getUserRoleId() != null) {
                    feeAmount = feeFetch.getFeeAmount();
                    tv_bill_amount.setText("Rs. " + feeFetch.getFeeAmount() + "/-");
                } else {
                    Toast.makeText(CheckInActivity.this, "Sorry Detail Not Available for this User", Toast.LENGTH_LONG).show();
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
            progressDialog = new ProgressDialog(CheckInActivity.this);
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

    public void tokenStatus() {
        //Toast.makeText(CheckInActivity.this,mr_no+"---mr_no", Toast.LENGTH_LONG).show();
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.token_Status(mr_no, user_role_id, subTanentId, scheduleId, completeDate, new Callback<TokenStatusModel[]>() {
            @Override
            public void success(TokenStatusModel[] tokenStatusModel, Response response) {
                tokenStatusModelArray = tokenStatusModel;
                if (tokenStatusModelArray.length > 0) {
                    tokenStatus = tokenStatusModelArray[0].getTokenStatus();
                    appId = tokenStatusModelArray[0].getAppId();
                    if (tokenStatus.equals("PRE_BOOKED")) {
                        currToken = Integer.parseInt(tokenStatusModelArray[0].getTokenNo().toString());
                        checkin_token_number.setText(currToken+"");
                        nextAvailableTokendialog();
                        //Toast.makeText(CheckInActivity.this,tokenStatusModelArray[0].getTokenNo().toString(), Toast.LENGTH_LONG).show();
                        ll_checkin.setClickable(true);
                    } else if (tokenStatus.equals("CHECK_IN")) {
                        Toast.makeText(CheckInActivity.this, "You are already Checked In", Toast.LENGTH_LONG).show();
                        ll_checkin.setClickable(false);
                    }
                } else {
                    ll_checkin.setClickable(true);
                    nextAvailableToken();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void nextAvailableToken() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.next_Availabel_Token(user_role_id, subTanentId, scheduleId, completeDate, new Callback<CurrentTokenModel[]>() {
            @Override
            public void success(CurrentTokenModel[] currentTokenModels, Response response) {
                currentTokenModelsArray = currentTokenModels;
                if (currentTokenModelsArray.length > 0) {
                    //Toast.makeText(CheckInActivity.this,currentTokenModels[0].getStatus()+"===check in status",Toast.LENGTH_LONG).show();
                    if (currentTokenModels[0].getStatus() == NextAvailTokenStatus.kNextAvailTokenStatusAvailable.getID()) {
                        tokenStatus = "NEW_USER";
                        nextToken = currentTokenModels[0].getTokenNo();
                        appId = currentTokenModels[0].getAppId();
                        checkin_token_number.setText(currentTokenModels[0].getTokenNo().toString());
                        //Toast.makeText(CheckInActivity.this,currentTokenModels[0].getTokenNo().toString(), Toast.LENGTH_LONG).show();
                    } else if (currentTokenModels[0].getStatus() == NextAvailTokenStatus.kNextAvailTokenStatusNotAvailable.getID()) {
                        Toast.makeText(CheckInActivity.this, "Please try again", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(CheckInActivity.this, "OPD NOT STARTED", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void nextAvailableTokendialog() {
        mCuraApplication.getInstance().mCuraEndPoint.next_Availabel_Token(user_role_id, subTanentId, scheduleId, completeDate, new Callback<CurrentTokenModel[]>() {
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
                            }
                            /*else{
                                setCheckInStatus();
                            }*/
                        }/*else{
                            setCheckInStatus();
                        }*/
                    } else if (currentTokenModels[0].getStatus() == NextAvailTokenStatus.kNextAvailTokenStatusNotAvailable.getID()) {
                        Toast.makeText(CheckInActivity.this, "Please try again", Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        // Do as you please
    }

    public void insertAppointmentApi() {
        JsonObject insertAppointmentObject = new JsonObject();

        JsonObject appBookingObject = new JsonObject();
        appBookingObject.addProperty("AppNatureId", appNatureId);
        appBookingObject.addProperty("CurrentStatusId", 1);
        appBookingObject.addProperty("Mrno", mr_no);
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

        insertAppointmentObject.addProperty("AvlStatusId", 1);
        insertAppointmentObject.addProperty("scheduleId", scheduleId);
        insertAppointmentObject.add("appbookings", appBookingObject);

        JsonObject obj = new JsonObject();
        obj.add("_Appointments", insertAppointmentObject);
        obj.addProperty("UserRoleID", user_role_id);
        Log.d("InsertAppointments", obj.toString());

        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.insertAppointments(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                postActivityTrackerFromAPI("BLOCK_USER");
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error=" + error.getMessage());
                dismissLoadingDialog();
            }
        });
    }

    public void setCheckInStatusForBlockWithoutFee() {
        JsonObject obj = new JsonObject();
        obj.addProperty("MRNo", mr_no);
        obj.addProperty("UserRoleId", user_role_id);
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
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                        appointmentBookedOrNot();
                        //moveTokenListBlock(tokenNo);
                    } else if (chartGenerateStatus == 2) {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 3) {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 5) {
                        showErrorDialog(msg + " Do you want to pay now.");
                    } else {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
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

    public void setCheckInStatusForBlockWithFee() {
        JsonObject obj = new JsonObject();
        obj.addProperty("MRNo", mr_no);
        obj.addProperty("UserRoleId", user_role_id);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("Date", completeDate);
        obj.addProperty("serviceRoleId", serviceRoleId);
        obj.addProperty("appId",appId);
        Log.d("checkin json",obj.toString());
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.patient_Check_In_version1(obj, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                String msg = null;
                if (generateTokenResultModel != null) {
                    msg = generateTokenResultModel.getMsg();
                    chartGenerateStatus = generateTokenResultModel.getStatus();
                    if (chartGenerateStatus == 1) {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                        appointmentBookedOrNot();
                        //moveTokenListBlock(tokenNo);
                    } else if (chartGenerateStatus == 2) {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 3) {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 5) {
                        showErrorDialog(msg);
                    } else if (chartGenerateStatus == 6) {
                        showErrorDialog(msg);
                    } else {
                        Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
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

    public void moveTokenListBlock(final int nextTokenBlock) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.move_Token_List(mr_no, user_role_id, subTanentId, scheduleId, nextTokenBlock, completeDate, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                int status = generateTokenResultModel.getStatus();
                String msg = generateTokenResultModel.getMsg();
                if (status == 1) {
                    Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                    appointmentBookedOrNot();
                    //checkin_token_number.setText(nextToken + "");

                    //getQueueData();
                    //setCheckInStatus();
                } else if (status == 4) {
                    Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void appointmentBookedOrNot() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.appointmentBookedOrNot(mr_no + "", user_role_id + "", scheduleId + "", currDate, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatusId() == EnumType.AppointmentOrNot.mAppointmentAlreadyFixed.getID()) {
                    if (tokenStatus.equals("NEW_USER")) {
                        if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547) {
                            setCheckInStatusWithFee();
                        } else {
                            setCheckInStatusWithoutFee();
                        }
                    } else if (tokenStatus.equals("BLOCK_USER")) {
                        startActivity(new Intent(CheckInActivity.this, QueueStatusActivity.class));
                    }

                } else if (postPaymentModel.getStatusId() == EnumType.AppointmentOrNot.mNewAppointment.getID()) {
                    if (tokenStatus.equals("NEW_USER")) {
                        insertNewUserAppointmentApi();
                    } else if (tokenStatus.equals("BLOCK_USER")) {
                        insertAppointmentApi();
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

    public void insertNewUserAppointmentApi() {
        JsonObject insertAppointmentObject = new JsonObject();

        JsonObject appBookingObject = new JsonObject();
        appBookingObject.addProperty("AppNatureId", appNatureId);
        appBookingObject.addProperty("CurrentStatusId", 1);
        appBookingObject.addProperty("Mrno", mr_no);
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

        insertAppointmentObject.addProperty("AvlStatusId", 1);
        insertAppointmentObject.addProperty("scheduleId", scheduleId);
        insertAppointmentObject.add("appbookings", appBookingObject);

        JsonObject obj = new JsonObject();
        obj.add("_Appointments", insertAppointmentObject);
        obj.addProperty("UserRoleID", user_role_id);
        Log.d("InsertAppointments", obj.toString());

        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.insertAppointments(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                if (s.equals("true")) {
                    postActivityTrackerFromAPI("NEW_USER");
                }else{
                    dismissLoadingDialog();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error=" + error.getMessage());
                dismissLoadingDialog();
            }
        });
    }
    private void postActivityTrackerFromAPI(final String userType) {
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("actBuildVersion",buildVersionName);
        obj.addProperty("delivered",0);
        obj.addProperty("actUserRoleId",frontOfficeUserRoleId);
        obj.addProperty("actSubTenantId",subTanentId);
        obj.addProperty("actScheduleId",scheduleId);
        obj.addProperty("actAppId",appId);
        obj.addProperty("actUserMediumId",9);
        obj.addProperty("drUserRoleId",user_role_id);
        obj.addProperty("actRemarks","");
        obj.addProperty("actTransMasterId",actTransactId);
        obj.addProperty("patMrno",mr_no);
        obj.addProperty("actOthers","");

        mCuraApplication.getInstance().mCuraEndPoint.postActivityTracker(obj, new Callback<PostActivityTrackerModel>() {
            @Override
            public void success(PostActivityTrackerModel postActivityTrackerModel, Response response) {
                if(userType.equals("BLOCK_USER")){
                    startActivity(new Intent(CheckInActivity.this, QueueStatusActivity.class));
                }else if(userType.equals("NEW_USER")){
                    if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547) {
                        setCheckInStatusWithFee();
                    } else {
                        setCheckInStatusWithoutFee();
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
}