package com.mcura.jaideep.queuemanagement.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mcura.jaideep.queuemanagement.Adapter.AddNewAppointmentSearchPatient;
import com.mcura.jaideep.queuemanagement.Adapter.CheckInAdapter_v1;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.Area;
import com.mcura.jaideep.queuemanagement.Model.CheckHospitalPatModel;
import com.mcura.jaideep.queuemanagement.Model.City;
import com.mcura.jaideep.queuemanagement.Model.Country;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.GetPatientByHospitalNoModel;
import com.mcura.jaideep.queuemanagement.Model.MedantaPatInfo;
import com.mcura.jaideep.queuemanagement.Model.PatDemoGraphics;
import com.mcura.jaideep.queuemanagement.Model.PatientInfoModel;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.SearchHospital;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.Model.State;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.Utils.GraphicsUtil;
import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.helper.Helper;
import com.mcura.jaideep.queuemanagement.view.SegmentedGroup;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddNewAppointment extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {
    private boolean isExist;
    private String registrationDate = "";
    private RadioButton radio_male, radio_female;
    private SegmentedGroup dobAgeSegment;
    private EditText etAge;
    private TextView etDob;
    private Calendar patDobDate;
    private MedantaPatInfo medantaPatInfo;

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_dob:
                if (!TextUtils.isEmpty(etAge.getText().toString())) {
                    if (TextUtils.isEmpty(etDob.getText().toString())) {
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.YEAR, -Integer.parseInt(etAge.getText().toString()));
                        populateSetDate(cal.get(cal.YEAR), 01, 01);
                    }
                }
                etAge.setVisibility(View.GONE);
                etDob.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_age:
                if (!TextUtils.isEmpty(etDob.getText().toString())) {
                    DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    try {
                        cal.setTime(df.parse(etDob.getText().toString()));
                        etAge.setText(Helper.getAge(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                etAge.setVisibility(View.VISIBLE);
                etDob.setVisibility(View.GONE);
                break;
        }
    }
    //private SoapObject resultString;

    public enum CheckHospitalPatient {
        kCheckHospitalPatientSuccess(true), kCheckHospitalPatientAreadyExist(false);
        private boolean id;

        CheckHospitalPatient(boolean id) {
            this.id = id;
        }

        public boolean getID() {
            return id;
        }
    }

    boolean etAgefocus;
    private DatePickerDialog dobDatePickerDialog;
    AlertDialog msgDialog = null;
    String[] mnth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    EditText patientName, dobDate, dobMonth, dobYear, mobile, email, address1, address2, zipcode, hospital_id;
    GetPatientByHospitalNoModel getPatientByHospitalNo;
    Spinner country, state, city, area;
    AlertDialog.Builder alertDialog;
    AlertDialog ad;
    ImageView profilePic, h_id;
    TextView submit, cancel, show_detail_hide;
    SearchHospital searchHospitalArray[];
    RadioGroup genderGroup;
    RadioButton genderRadioButton;
    LinearLayout address_detail;
    Bitmap imageBitmap;
    ProgressDialog progressDialog;
    public MCuraApplication mCuraApplication;
    public Country[] countryArray;
    public State[] stateArray;
    public City[] cityArray;
    String ImagePathId;
    HashMap<Integer, String> hmHospitalName = new HashMap<>();
    List<String> hospitalNameList;
    public Area[] areaArray;
    String addressLine1 = "delhi", addressLine2 = "delhi", zipCode = "111111", mobileNumber, emailAddress = "test@mcura.com", patName, dob, encodedImage;
    int areaId = 1, userRoleId, addressId, contactId, genderId, subTanentId, appNatureId, patientRegistrationId, currentYear;
    ArrayAdapter<String> areaAdapter = null;
    int patDemoId;
    List<String> counrtyList, stateList, cityList, areaList;
    HashMap<Integer, String> hmCountry = new HashMap<Integer, String>();
    HashMap<Integer, String> hmState = new HashMap<Integer, String>();
    HashMap<Integer, String> hmCity = new HashMap<Integer, String>();
    HashMap<Integer, String> hmArea = new HashMap<Integer, String>();
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_DIRECT_REGISTRATION = 2;
    private static final int REQUEST_APPOINTMENT_REGISTRATION = 3;
    private static final int REQUEST_UPDATE_REGISTRATION = 4;
    Date currentDate, patientDob;
    String[] arr = {"Select any one"};
    SimpleDateFormat df = null;
    String updateStatus = "";
    Datum patientSearchModel;
    int mrno;
    String registerStatus;
    private SimpleDateFormat dateFormatter;
    RadioButton rbDob, rbAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        /*int screenWidth = (int) (metrics.widthPixels * 0.80);
        int screenHeight = (int) (metrics.heightPixels * 0.80);*/
        setContentView(R.layout.activity_add_new_appointment);
        //getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        updateStatus = getIntent().getStringExtra("updateStatus");
        registrationDate = Helper.getCurrentDate();
        //Toast.makeText(AddNewAppointment.this,updateStatus,Toast.LENGTH_LONG).show();
        //getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Calendar calendar = Calendar.getInstance();

        df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(calendar.getTime());
        currentYear = calendar.get(Calendar.YEAR);
        try {
            currentDate = df.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        init();
        //getHospitalFromAPI();


        setCountryAPI();
        if (updateStatus.equals("update_patient")) {
            patientSearchModel = (Datum) getIntent().getSerializableExtra("PatientSearchModel");
            mrno = patientSearchModel.getMrNo();
            //Toast.makeText(AddNewAppointment.this,patientSearchModel.getMRNO()+"",Toast.LENGTH_LONG).show();
            hospital_id.setText(patientSearchModel.getHospitalNo() + "");
            patientName.setText(patientSearchModel.getPatName());
            int genId = patientSearchModel.getGenderId();
            if (genId == 1) {
                genderGroup.check(R.id.radio_male);
            } else if (genId == 2) {
                genderGroup.check(R.id.radio_female);
            }
            mobile.setText(patientSearchModel.getMobileNo() + "");
            if (patientSearchModel.getEmailId() != "") {
                email.setText(patientSearchModel.getEmailId());
            }
            String dateOfBirth = patientSearchModel.getDob();
            String timestamp = dateOfBirth.split("\\(")[1].split("\\+")[0];
            Date createdOn = new Date(Long.parseLong(timestamp));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            String formatted = sdf.format(createdOn);
            System.out.println("formattedDate-->" + formatted);
            /*String[] out = formatted.split(",");
            System.out.println("Year = " + out[2]);
            System.out.println("Month = " + out[0]);
            System.out.println("Day = " + out[1]);*/

            /*int year = Integer.parseInt(out[2]);
            int month = Integer.parseInt(out[0]);
            int day = Integer.parseInt(out[1]);*/
            /*dobDate.setText(day + "");
            dobMonth.setText(month + "");
            dobYear.setText(year + "");*/
            etDob.setText(formatted);

        } else if (updateStatus.equals("add_new_patient")) {

        }
    }

    private void getMcuraHospitalIdforKSH() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getHospitalId(subTanentId, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                hospital_id.setText(s);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        setCountryAPI();
    }*/

    private void init() {

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        setFromDateTimeField();
        SharedPreferences mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        subTanentId = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        //Toast.makeText(AddNewAppointment.this, "subTanentId=" + subTanentId, Toast.LENGTH_LONG).show();
        userRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        appNatureId = getIntent().getIntExtra("appNatureId", 0);
        //Toast.makeText(AddNewAppointment.this, "appNatureId=" + appNatureId, Toast.LENGTH_LONG).show();
        //close = (ImageView) findViewById(R.id.close);

        hospital_id = (EditText) findViewById(R.id.hospital_id);
        //hospital_id.setText(subTanentId+"");

        registerStatus = getIntent().getStringExtra("registerStatus");

        dobAgeSegment = (SegmentedGroup) findViewById(R.id.dob_age_segment);
        dobAgeSegment.check(R.id.rb_dob);
        rbDob = (RadioButton) findViewById(R.id.rb_dob);
        rbAge = (RadioButton) findViewById(R.id.rb_age);
        etDob = (TextView) findViewById(R.id.et_dob);
        etAge = (EditText) findViewById(R.id.et_age);
        h_id = (ImageView) findViewById(R.id.h_id);
        address_detail = (LinearLayout) findViewById(R.id.address_detail);
        show_detail_hide = (TextView) findViewById(R.id.show_detail_hide);
        submit = (TextView) findViewById(R.id.submit);
        cancel = (TextView) findViewById(R.id.add_patient_cancel);
        patientName = (EditText) findViewById(R.id.patient_name);
        dobDate = (EditText) findViewById(R.id.dob_date);
        dobMonth = (EditText) findViewById(R.id.dob_month);
        dobYear = (EditText) findViewById(R.id.dob_year);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        address1 = (EditText) findViewById(R.id.address1);
        address2 = (EditText) findViewById(R.id.address2);
        zipcode = (EditText) findViewById(R.id.zipcode);
        country = (Spinner) findViewById(R.id.country);
        state = (Spinner) findViewById(R.id.state);
        city = (Spinner) findViewById(R.id.city);
        area = (Spinner) findViewById(R.id.area);
        radio_male = (RadioButton) findViewById(R.id.radio_male);
        radio_female = (RadioButton) findViewById(R.id.radio_female);
        genderGroup = (RadioGroup) findViewById(R.id.gender);
        profilePic = (ImageView) findViewById(R.id.profile_pic);
        //close.setOnClickListener(this);
        cancel.setOnClickListener(this);
        country.setOnItemSelectedListener(this);
        state.setOnItemSelectedListener(this);
        city.setOnItemSelectedListener(this);
        area.setOnItemSelectedListener(this);
        profilePic.setOnClickListener(this);
        submit.setOnClickListener(this);
        show_detail_hide.setOnClickListener(this);
        h_id.setOnClickListener(this);
        if (updateStatus.equals("update_patient")) {
            patientName.setEnabled(true);
            etDob.setEnabled(true);
            etAge.setEnabled(true);
            dobDate.setEnabled(true);
            dobMonth.setEnabled(true);
            dobYear.setEnabled(true);
            mobile.setEnabled(true);
            email.setEnabled(true);
            radio_male.setEnabled(true);
            radio_female.setEnabled(true);
            address1.setEnabled(true);
            address2.setEnabled(true);
            zipcode.setEnabled(true);
            country.setEnabled(true);
            state.setEnabled(true);
            city.setEnabled(true);
            area.setEnabled(true);
            if (subTanentId == 563) {
                hospital_id.setEnabled(false);
            }
        }
        if (updateStatus.equals("add_new_patient")) {
            if (/*subTanentId == 515 || subTanentId == 528 || */subTanentId == 547) {
                patientName.setEnabled(false);
                etDob.setEnabled(false);
                etDob.setClickable(false);
                etAge.setEnabled(false);
                dobDate.setEnabled(false);
                dobMonth.setEnabled(false);
                dobYear.setEnabled(false);
                mobile.setEnabled(false);
                email.setEnabled(false);
                radio_male.setEnabled(false);
                radio_female.setEnabled(false);
                address1.setEnabled(false);
                address2.setEnabled(false);
                zipcode.setEnabled(false);
                country.setEnabled(false);
                state.setEnabled(false);
                city.setEnabled(false);
                area.setEnabled(false);
            } else if (subTanentId == 563) {
                if (subTanentId == 563) {
                    getMcuraHospitalIdforKSH();
                }
                patientName.setEnabled(true);
                etDob.setEnabled(true);
                etDob.setClickable(true);
                etAge.setEnabled(true);
                dobDate.setEnabled(true);
                dobMonth.setEnabled(true);
                dobYear.setEnabled(true);
                mobile.setEnabled(true);
                email.setEnabled(true);
                radio_male.setEnabled(true);
                radio_female.setEnabled(true);
                address1.setEnabled(true);
                address2.setEnabled(true);
                zipcode.setEnabled(true);
                country.setEnabled(true);
                state.setEnabled(true);
                city.setEnabled(true);
                area.setEnabled(true);
                hospital_id.setEnabled(false);
            } else {
                patientName.setEnabled(true);
                etDob.setEnabled(true);
                etDob.setClickable(true);
                etAge.setEnabled(true);
                dobDate.setEnabled(true);
                dobMonth.setEnabled(true);
                dobYear.setEnabled(true);
                mobile.setEnabled(true);
                email.setEnabled(true);
                radio_male.setEnabled(true);
                radio_female.setEnabled(true);
                address1.setEnabled(true);
                address2.setEnabled(true);
                zipcode.setEnabled(true);
                country.setEnabled(true);
                state.setEnabled(true);
                city.setEnabled(true);
                area.setEnabled(true);
            }
        }
        dobAgeSegment.setOnCheckedChangeListener(this);
        etDob.setOnClickListener(this);
        etAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasfocus) {
                if (hasfocus) {
                    etAgefocus = hasfocus;
                } else {
                    etAgefocus = hasfocus;
                }
            }
        });
        etAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etAgefocus) {
                    if (!TextUtils.isEmpty(etAge.getText().toString())) {
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.YEAR, -Integer.parseInt(etAge.getText().toString()));
                        populateSetDate(cal.get(cal.YEAR), 01, 01);
                    } else {
                        Log.d("textstatus", etAge.getText().toString() + "--");
                    }
                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getPatientByHospitalNoApi(String hId) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getPatientByHospitalNo(hId, new Callback<GetPatientByHospitalNoModel>() {
            @Override
            public void success(GetPatientByHospitalNoModel getPatientByHospitalNoModel, Response response) {
                getPatientByHospitalNo = getPatientByHospitalNoModel;
                if (getPatientByHospitalNo.getHospitalNo() != null) {
                    setPatientDetail();
                } else {
                    setErrorAlert();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void setErrorAlert() {
        AlertDialog.Builder buider = new AlertDialog.Builder(AddNewAppointment.this);
        buider.setMessage("Patient Detail not Available");
        buider.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = buider.show();
    }

    private String getDob(String dob) {
        String timestamp = dob.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date(Long.parseLong(timestamp));
        SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
        String formattedDate = sdf.format(createdOn);
        System.out.println("formattedDate-->" + formattedDate);
        return formattedDate;
    }

    public void setPatientDetail() {
        hospital_id.setText(getPatientByHospitalNo.getHospitalNo().toString());
        patientName.setText(getPatientByHospitalNo.getPatName().toString());
        if (getPatientByHospitalNo.getGenderId() == 1) {
            genderGroup.check(R.id.radio_male);
        } else if (getPatientByHospitalNo.getGenderId() == 2) {
            genderGroup.check(R.id.radio_female);
        }
        String mobileNo = getDob(getPatientByHospitalNo.getdOB().toString());
        String[] out = mobileNo.split(",");
        System.out.println("Year = " + out[2]);
        System.out.println("Month = " + out[0]);
        System.out.println("Day = " + out[1]);

        int year = Integer.parseInt(out[2]);
        dobYear.setText(year + "");
        int month = Integer.parseInt(out[0]);
        dobMonth.setText(month + "");
        int day = Integer.parseInt(out[1]);
        dobDate.setText(day + "");
        mobile.setText(getPatientByHospitalNo.getMobileNo().toString());
        if (getPatientByHospitalNo.getEmail() != null) {
            email.setText(getPatientByHospitalNo.getEmail().toString());
        }
    }

    public void checkHospitalPatientAPI(String hospitalId) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.checkHospitalPat(userRoleId, subTanentId, hospitalId, new Callback<CheckHospitalPatModel>() {
            @Override
            public void success(CheckHospitalPatModel checkHospitalPatModel, Response response) {
                if (checkHospitalPatModel.getStatus() == CheckHospitalPatient.kCheckHospitalPatientSuccess.getID()) {
                } else if (checkHospitalPatModel.getStatus() == CheckHospitalPatient.kCheckHospitalPatientAreadyExist.getID()) {
                    getPatientByHospitalNoApi(hospital_id.getText().toString());
                    Toast.makeText(AddNewAppointment.this, checkHospitalPatModel.getMsg().toString(), Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void populateSetDate(int year, int month, int day) {
        String monthname = (String) android.text.format.DateFormat.format("MMM", month);
        etDob.setText(day + "-" + monthname + "-" + year);
    }

    private void setFromDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        dobDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                patDobDate = Calendar.getInstance();
                patDobDate.set(year, monthOfYear, dayOfMonth);

                etDob.setText(dateFormatter.format(patDobDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.et_dob:
                dobDatePickerDialog.show();
                break;
            case R.id.h_id:
                if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547) {
                    h_id.setClickable(true);
                    if (!TextUtils.isEmpty(hospital_id.getText().toString())) {
                        new AsyncCallWS(hospital_id.getText().toString()).execute();
                    } else {
                        hospital_id.setError("Enter Hospital Id");
                    }
                }else if(subTanentId == 579){
                    h_id.setClickable(true);
                    if (!TextUtils.isEmpty(hospital_id.getText().toString())) {
                        new MedHISAPI(hospital_id.getText().toString()).execute();
                    } else {
                        hospital_id.setError("Enter Hospital Id");
                    }
                } else {
                    h_id.setClickable(false);
                }
                //checkHospitalPatientAPI(hospital_id.getText().toString());
                //getPatientByHospitalNoApi(hospital_id.getText().toString());
                //showHospitalPopup();
                break;
            case R.id.show_detail_hide:
                if (show_detail_hide.getText().toString().equals("Add Address")) {
                    address_detail.setVisibility(View.VISIBLE);
                    show_detail_hide.setText("Hide");
                } else if (show_detail_hide.getText().toString().equals("Hide")) {
                    address_detail.setVisibility(View.GONE);
                    show_detail_hide.setText("Add Address");
                }
                break;
            case R.id.add_patient_cancel:
                AddNewAppointment.this.finish();
                break;
            /*case R.id.close:
                AddNewAppointment.this.finish();
                break;*/
            case R.id.profile_pic:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

                break;
            case R.id.submit:
                if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547) {
                    if (!TextUtils.isEmpty(hospital_id.getText().toString())) {
                        if (updateStatus.equals("update_patient")) {
                            registerNewPatient();
                        }
                        if (updateStatus.equals("add_new_patient")) {
                            isPatAlreadyExist();
                        }
                    } else {
                        hospital_id.setError("Enter Hospital Id");
                    }
                } else {
                    if (updateStatus.equals("update_patient")) {
                        registerNewPatient();
                    }
                    if (updateStatus.equals("add_new_patient")) {
                        //isPatAlreadyExist();
                        registerNewPatient();
                    }
                }
                break;
            /*case R.id.cancel:
                AddNewAppointment.this.finish();
                break;*/
        }
    }

    class MedHISAPI extends AsyncTask<String,Void,String>{
        private String hId;
        HttpURLConnection urlConnection;
        MedHISAPI(String hId){
            this.hId = hId;
            Log.d("hId", hId);
        }
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
//TransactionDetail_v1
            //TransactionDetail
            try {
                String urlPath = "http://ot.medanta.org/MedHISAPI/api/Patient/GetPatientBasicInfo";
                Log.d("url", urlPath);
                URL url = new URL(urlPath);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("MedHISAPIRead1", "MedHISAPIReadOnly");
                urlConnection.setRequestProperty("MedHISAPIReadPass1", "Lck95$Bst#21Md32*cM4Det");
                urlConnection.setRequestProperty("MedUHID", hId.toUpperCase());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoadingDialog();
            System.out.println("before result===>"+result);
            String s1 = result.substring(1,result.length()-1);
            System.out.println("after result===>"+s1);
            s1 = s1.replace("\\","");
            System.out.println("last result===>"+s1);

            Gson gson = new Gson();
            medantaPatInfo = gson.fromJson(s1, MedantaPatInfo.class);
            hospital_id.setText(medantaPatInfo.getPatientUHID());
            patientName.setText(medantaPatInfo.getPatientName());
            mobile.setText(medantaPatInfo.getContactNo());
            email.setText(medantaPatInfo.getEmailID());
            if(medantaPatInfo.getGender().equals("M")){
                radio_male.setChecked(true);
                radio_female.setChecked(false);
            }else if(medantaPatInfo.getGender().equals("F")){
                radio_male.setChecked(false);
                radio_female.setChecked(true);
            }
            etDob.setText(Helper.changeDObDateFormat(medantaPatInfo.getDateOfBirth()));
        }
    }

    private void registerNewPatient() {
        addressLine1 = address1.getText().toString();
        addressLine2 = address2.getText().toString();
        zipCode = zipcode.getText().toString();
        mobileNumber = mobile.getText().toString();
        emailAddress = email.getText().toString();
        patName = patientName.getText().toString();
        String date = dobDate.getText().toString();
        //System.out.println("date-->" + date);
        String year = dobYear.getText().toString();
        String month = dobMonth.getText().toString();
        dob = etDob.getText().toString();
        int selectedId = genderGroup.getCheckedRadioButtonId();
        genderRadioButton = (RadioButton) findViewById(selectedId);
        if (genderRadioButton.getText().toString().equals("Male")) {
            genderId = 1;
        } else if (genderRadioButton.getText().toString().equals("Female")) {
            genderId = 2;
    }

        if (patName.length() > 0) {
                if (!TextUtils.isEmpty(etDob.getText().toString())) {
                    if (mobileNumber.length() > 0 && isValidMobile(mobileNumber)) {
                        //if (mobileNumber.length() == 13) {
                            if (updateStatus.equals("update_patient")) {
                                getContactId();
                            }
                            if (updateStatus.equals("add_new_patient")) {
                                postContactDetailApi();
                            }
                        /*} else {
                            mobile.setError("Mobile Number cannot be less than 10 digit");
                        }*/

                    } else {
                        mobile.setError("Enter valid Mobile Number");
                    }

                } else {
                    if (rbDob.isChecked()) {
                        etDob.setError("Enter DOB");
                    } else if (rbAge.isChecked()) {
                        etAge.setError("Enter Age");
                    }
                }
        } else {
            patientName.setError("Patient name cannot be blank");
        }
    }

    private void showMessageDialog(final SearchPatientModel patientSearchModels) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AddNewAppointment.this);
        dialog.setCancelable(false);
        dialog.setMessage("Patient Already exists.");

        dialog.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                patName = patientSearchModels.getData().get(0).getPatName();
                patientRegistrationId = patientSearchModels.getData().get(0).getMrNo();
                mobileNumber = patientSearchModels.getData().get(0).getMobileNo().trim();
                if (registerStatus.equals("appointment")) {
                    Intent intent = new Intent(AddNewAppointment.this, LoadNFC.class).putExtra("hospital_id", hospital_id.getText().toString()).putExtra("mrnoValue", patientRegistrationId).putExtra("subTanentId", subTanentId).putExtra("id", REQUEST_APPOINTMENT_REGISTRATION);
                    startActivityForResult(intent, REQUEST_APPOINTMENT_REGISTRATION);
                } else if (registerStatus.equals("search")) {
                    Intent intent = new Intent(AddNewAppointment.this, LoadNFC.class).putExtra("hospital_id", hospital_id.getText().toString()).putExtra("mrnoValue", patientRegistrationId).putExtra("subTanentId", subTanentId).putExtra("id", REQUEST_DIRECT_REGISTRATION);
                    startActivityForResult(intent, REQUEST_DIRECT_REGISTRATION);
                }
                /*Intent intent = new Intent(AddNewAppointment.this, LoadNFC.class).putExtra("hospital_id", hospital_id.getText().toString()).putExtra("mrnoValue", patientRegistrationId).putExtra("subTanentId", subTanentId).putExtra("id", REQUEST_APPOINTMENT_REGISTRATION);
                startActivityForResult(intent, REQUEST_APPOINTMENT_REGISTRATION);*/
                msgDialog.dismiss();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                msgDialog.dismiss();
            }
        });
        msgDialog = dialog.create();
        msgDialog.show();
    }

    private void isPatAlreadyExist() {
        isExist = false;
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.searchPatient_v1(1, 1, userRoleId, "4", hospital_id.getText().toString().trim(), subTanentId, new Callback<SearchPatientModel>() {
            @Override
            public void success(SearchPatientModel patientSearchModels, Response response) {
                if (patientSearchModels.getData().size() > 0) {
                    showMessageDialog(patientSearchModels);
                } else {
                    registerNewPatient();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public String getXMLFromUrl(String url) {
        BufferedReader br = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            final StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class AsyncCallWS extends AsyncTask<String, String, String> {
        String hId;

        public AsyncCallWS(String hId) {
            this.hId = hId;
        }

        @Override
        protected void onPreExecute() {
            Log.i("result", "onPreExecute");
            showLoadingDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            String soapObject = getXMLFromUrl("http://111.93.30.214:180/mcuraintg/intg.asmx/PatientInformation?_flag=O&_input=" + hId);
            return soapObject;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("result", result);
            dismissLoadingDialog();

            try {

                /************* Read XML ************/

                BufferedReader br = new BufferedReader(new StringReader(result));
                InputSource is = new InputSource(br);

                /***********  Parse XML *************/

                XMLParser parser = new XMLParser();
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser sp = factory.newSAXParser();
                XMLReader reader = sp.getXMLReader();
                reader.setContentHandler(parser);
                reader.parse(is);

                if (parser.patInfo.patName != null) {
                    patientName.setText(parser.patInfo.patName);
                }
                System.out.println("PatientInfo UMR " + parser.patInfo.umrNo);
                hospital_id.setText(parser.patInfo.umrNo.toString());
                System.out.println("PatientInfo DOB " + parser.patInfo.dob);
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat fmt2 = new SimpleDateFormat("dd-MMM-yyyy");
                if (!TextUtils.isEmpty(parser.patInfo.regDate)) {
                    registrationDate = Helper.changeRegDateFormat(parser.patInfo.regDate);
                    System.out.println("PatientInfo registrationDate " + registrationDate);
                }


                String DOB = parser.patInfo.dob;
                if (!TextUtils.isEmpty(DOB)) {
                    etDob.setText(Helper.changeDOBDateFormat(DOB));
                } else {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.YEAR, -Integer.parseInt(etAge.getText().toString()));
                    populateSetDate(cal.get(cal.YEAR), 01, 01);
                }


                if (parser.patInfo.mobileNo != null) {
                    System.out.println("PatientInfo Mobile " + parser.patInfo.mobileNo);
                    mobile.setText(parser.patInfo.mobileNo.toString());
                }

                System.out.println("PatientInfo Gender " + parser.patInfo.gender);

                if (parser.patInfo.gender.equals("M")) {
                    genderGroup.check(R.id.radio_male);
                } else if (parser.patInfo.gender.equals("F")) {
                    genderGroup.check(R.id.radio_female);
                }
                if (parser.patInfo.emailId != null) {
                    System.out.println("PatientInfo Email " + parser.patInfo.emailId);
                    email.setText(parser.patInfo.emailId.toString());
                }

                System.out.println("PatientInfo Name " + parser.patInfo.patName);


            } catch (Exception e) {
                Log.e("Jobs", "Exception parse xml :" + e);
            }

        }

    }
    // SAX parser to parse job

    public class XMLParser extends DefaultHandler {

        // string builder acts as a buffer
        StringBuilder builder;
        PatientInfoModel patInfo;

        // Initialize the arraylist
        // @throws SAXException

        @Override
        public void startDocument() throws SAXException {


        }


        // Initialize the temp XmlValuesModel object which will hold the parsed info
        // and the string builder that will store the read characters
        // @param uri
        // @param localName ( Parsed Node name will come in localName  )
        // @param qName
        // @param attributes
        // @throws SAXException

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {

            /***  When New XML Node initiating to parse this function called ****/

            // Create StringBuilder object to store xml node value
            builder = new StringBuilder();


            if (localName.equals("PatientInfoMcura")) {
                patInfo = new PatientInfoModel();
            }
        }


        // Finished reading the login tag, add it to arraylist
        // @param uri
        // @param localName
        // @param qName
        // @throws SAXException

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {

            if (localName.equals("UMRNO")) {
                patInfo.umrNo = builder.toString();
            } else if (localName.equalsIgnoreCase("PATIENT_NAME")) {
                patInfo.patName = builder.toString();
            } else if (localName.equalsIgnoreCase("GENDER")) {
                patInfo.gender = builder.toString();
            } else if (localName.equalsIgnoreCase("DOB")) {
                patInfo.dob = builder.toString();
            } else if (localName.equalsIgnoreCase("MOBILENO")) {
                patInfo.mobileNo = builder.toString();
            } else if (localName.equalsIgnoreCase("EMAIL_ID")) {
                patInfo.emailId = builder.toString();
            } else if (localName.equalsIgnoreCase("AGE")) {
                patInfo.patAge = builder.toString();
            } else if (localName.equalsIgnoreCase("REGISTRATIONDT")) {
                patInfo.regDate = builder.toString();
            }

            builder.setLength(0);
            // Log.i("parse",localName.toString()+"========="+builder.toString());
        }


        // Read the value of each xml NODE
        // @param ch
        // @param start
        // @param length
        // @throws SAXException

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {

            /*****  Read the characters and append them to the buffer  *****/
            String tempString = new String(ch, start, length);
            builder.append(tempString);
        }
    }

    public void setCountryAPI() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getCountry(new Callback<Country[]>() {
            @Override
            public void success(Country[] countries, Response response) {
                countryArray = countries;
                setCountryList();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void setCountryList() {
        hmCountry.clear();
//        counrtyList.clear();
        for (int i = 0; i < countryArray.length; i++) {
            //counrtyList.add(countryArray[i].getCountryProperty());
            hmCountry.put(countryArray[i].getCntyId(), countryArray[i].getCountryProperty());
        }
        //convert hashmap to arraylist
        counrtyList = new ArrayList<String>(hmCountry.values());

        //set data to country spinner
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, R.layout.new_pat_spinner_row_layout, counrtyList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countryAdapter);
    }

    /**
     *
     */
    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(AddNewAppointment.this);
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
        //int ids=parent.getId();
        switch (parent.getId()) {
            case R.id.country:
                String countryName = parent.getItemAtPosition(position).toString();

                for (Integer countryId : hmCountry.keySet()) {
                    if (hmCountry.get(countryId).equals(countryName)) {
                        setStateAPI(countryId);
                        //Toast.makeText(parent.getContext(), "key: " + o, Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.state:
                String stateName = parent.getItemAtPosition(position).toString();
                System.out.print("str-->" + stateName);
                for (Integer stateId : hmState.keySet()) {
                    if (hmState.get(stateId).equals(stateName)) {
                        setCityAPI(stateId);
                    }
                }
                break;
            case R.id.city:
                String cityName = parent.getItemAtPosition(position).toString();
                System.out.println("cityNameLength==>" + cityArray.length);

                for (Integer cityId : hmCity.keySet()) {
                    if (hmCity.get(cityId).equals(cityName)) {
                        setAreaAPI(cityId);
                    }
                }
                break;
            case R.id.area:
                String areaName = parent.getItemAtPosition(position).toString();
                //Toast.makeText(AddNewAppointment.this,"areaNameLength==>"+areaArray.length,Toast.LENGTH_LONG).show();
                for (Integer areaId1 : hmArea.keySet()) {
                    if (hmArea.get(areaId1).equals(areaName)) {
                        areaId = areaId1;
                    }
                }
                break;
        }

        //Toast.makeText(parent.getContext(), "Selected: " + str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setStateAPI(final Integer countryId) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getState(countryId, new Callback<State[]>() {
            @Override
            public void success(State[] states, Response response) {
                stateArray = states;
                setStateList(countryId);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();

            }
        });
    }

    public void setStateList(Integer countryId) {
        hmState.clear();
        //stateList.clear();
        for (int i = 0; i < stateArray.length; i++) {
            //if(countryId==stateArray[i].getCntyId()){
            hmState.put(stateArray[i].getStateId(), stateArray[i].getStateProperty());
            //}
        }
        stateList = new ArrayList<String>(hmState.values());
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this, R.layout.new_pat_spinner_row_layout, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);
    }

    public void setCityAPI(final Integer stateId) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getCity(stateId, new Callback<City[]>() {
            @Override
            public void success(City[] cities, Response response) {
                cityArray = cities;
                setCityList(stateId);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();

            }
        });
    }

    public void setCityList(Integer stateId) {
        hmCity.clear();
        //cityList.clear();
        System.out.print("stateId-->" + stateId);
        for (int i = 0; i < cityArray.length; i++) {
            //if(stateId==cityArray[i].getStateId()){
            hmCity.put(cityArray[i].getCityId(), cityArray[i].getCity());
            //}
        }
        cityList = new ArrayList<String>(hmCity.values());
        //Toast.makeText(AddNewAppointment.this,"cityList = "+cityList.size(),Toast.LENGTH_LONG).show();
        ArrayAdapter<String> cityAdapter = null;
        if (cityList.size() == 0) {
            //city.setEnabled(false);
            cityAdapter = new ArrayAdapter<String>(this, R.layout.new_pat_spinner_row_layout, arr);
            area.setAdapter(null);
            //area.setEnabled(false);
            areaAdapter = new ArrayAdapter<String>(this, R.layout.new_pat_spinner_row_layout, arr);
        } else {
            cityAdapter = new ArrayAdapter<String>(this, R.layout.new_pat_spinner_row_layout, cityList);
        }
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityAdapter);
        area.setAdapter(areaAdapter);
    }

    public void setAreaAPI(final Integer cityId) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getCityArea(cityId, new Callback<Area[]>() {
            @Override
            public void success(Area[] areas, Response response) {
                areaArray = areas;
                setCityAreaList();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();

            }
        });
    }

    public void setCityAreaList() {
        hmArea.clear();
        for (int i = 0; i < areaArray.length; i++) {
            hmArea.put(areaArray[i].getAreaId(), areaArray[i].getArea());
        }
        areaList = new ArrayList<String>(hmArea.values());
        if (city.getSelectedItem().toString().trim().equals(arr[0]) || areaList.size() == 0) {
            //if(areaList.size()==0){
            areaAdapter = new ArrayAdapter<String>(this, R.layout.new_pat_spinner_row_layout, arr);
            //}
        } else {
            areaAdapter = new ArrayAdapter<String>(this, R.layout.new_pat_spinner_row_layout, areaList);
        }
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area.setAdapter(areaAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            imageBitmap = (Bitmap) extras.get("data");
            GraphicsUtil graphicUtil = new GraphicsUtil();
            profilePic.setImageBitmap(graphicUtil.getRoundedShape(imageBitmap));
        } else if (requestCode == REQUEST_APPOINTMENT_REGISTRATION) {
            startActivity(new Intent(AddNewAppointment.this, NewAppointmentActivity.class).putExtra("appointmentstatus", 1).putExtra("patName", patName).putExtra("mobile", mobileNumber).putExtra("patientRegistrationId", patientRegistrationId));
            finish();
        } else if (requestCode == REQUEST_DIRECT_REGISTRATION) {
            startActivity(new Intent(AddNewAppointment.this, SearchPatientActivity.class));
            finish();
        } else if (requestCode == REQUEST_UPDATE_REGISTRATION) {
            startActivity(new Intent(AddNewAppointment.this, SearchPatientActivity.class));
            finish();
        }
    }

    public void postContactDetailApi() {
        JsonObject contactDetail = new JsonObject();
        contactDetail.addProperty("Mobile", mobileNumber);
        contactDetail.addProperty("Optmobile", "");
        contactDetail.addProperty("FixLine", "");
        contactDetail.addProperty("FixLineExtn", "");
        contactDetail.addProperty("Skypeid", "");
        contactDetail.addProperty("Email", emailAddress);
        contactDetail.addProperty("Optemail", "");

        final JsonObject contactObject = new JsonObject();
        contactObject.add("cd", contactDetail);
        contactObject.addProperty("UserRoleID", userRoleId);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postContactDetails_v1_1(contactObject, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                dismissLoadingDialog();
                contactId = Integer.parseInt(s);
                postAddressDetailAPI(contactId);
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void postAddressDetailAPI(int conId) {
        final JsonObject addressObject = new JsonObject();
        addressObject.addProperty("Address1", addressLine1);
        addressObject.addProperty("Address2", addressLine2);
        addressObject.addProperty("AreaId", areaId);
        addressObject.addProperty("Zipcode", zipCode);

        JsonObject obj = new JsonObject();
        obj.add("_add", addressObject);
        obj.addProperty("ContactId", conId);
        obj.addProperty("UserRoleID", userRoleId);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postAddress_v1_1(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                dismissLoadingDialog();
                addressId = Integer.parseInt(s);
                postPatientUserDetail();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void postPatientUserDetail() {
        JsonObject patientDetail = new JsonObject();
        patientDetail.addProperty("Patname", patName);
        patientDetail.addProperty("Dob", dob);
        patientDetail.addProperty("GenderId", genderId);
        patientDetail.addProperty("AddressId", addressId);
        patientDetail.addProperty("ContactId", contactId);


        final JsonObject patientDetailObject = new JsonObject();
        patientDetailObject.add("Pt", patientDetail);
        patientDetailObject.addProperty("UserRoleID", userRoleId);
        patientDetailObject.addProperty("sub_tenant_id", subTanentId);
        patientDetailObject.addProperty("EntryTypeId", 1);
        patientDetailObject.addProperty("regDate", registrationDate);
        patientDetailObject.addProperty("RecNatureId", 1);//patientDetailObject.addProperty("RecNatureId", appNatureId);
        patientDetailObject.addProperty("ImagePathId", ImagePathId);
        patientDetailObject.addProperty("HospitalNo", hospital_id.getText().toString());

        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postPatientUser(patientDetailObject, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                dismissLoadingDialog();
                if (!s.equals("-1")) {
                    Log.d("response", s);
                    patientRegistrationId = Integer.parseInt(s);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    //Toast.makeText(AddNewAppointment.this, "response=" + s + "  " + response, Toast.LENGTH_LONG).show();
                    if (imageBitmap != null) {
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    } else {
                        Drawable myDrawable = getResources().getDrawable(R.drawable.patient_image);
                        imageBitmap = drawableToBitmap(myDrawable);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    }
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    requestForPatImageUpload(encodedImage);
                } else {
                    Log.d("response", s);
                    if (registerStatus.equals("appointment")) {
                        Intent intent = new Intent(AddNewAppointment.this, NewAppointmentActivity.class);
                        startActivity(intent);
                    } else if (registerStatus.equals("search")) {
                        finish();
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });

    }

    /*public void postAddressApi() {

        final JsonObject addressObject = new JsonObject();
        addressObject.addProperty("Address1", addressLine1);
        addressObject.addProperty("Address2", addressLine2);
        addressObject.addProperty("AreaId", areaId);
        addressObject.addProperty("Zipcode", zipCode);

        JsonObject obj = new JsonObject();
        obj.add("_add", addressObject);
        obj.addProperty("UserRoleID", userRoleId);


        JsonObject contactDetail = new JsonObject();
        contactDetail.addProperty("Mobile", mobileNumber);
        contactDetail.addProperty("Optmobile", "");
        contactDetail.addProperty("FixLine", "");
        contactDetail.addProperty("FixLineExtn", "");
        contactDetail.addProperty("Skypeid", "");
        contactDetail.addProperty("Email", emailAddress);
        contactDetail.addProperty("Optemail", "");

        final JsonObject contactObject = new JsonObject();
        contactObject.add("cd", contactDetail);
        contactObject.addProperty("UserRoleID", userRoleId);

        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postAddress(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                //Toast.makeText(AddNewAppointment.this, "response=" + s + "  " + response, Toast.LENGTH_LONG).show();
                addressId = Integer.parseInt(s);
                mCuraApplication.getInstance().mCuraEndPoint.postContactDetails(contactObject, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        contactId = Integer.parseInt(s);



                        dismissLoadingDialog();
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
                dismissLoadingDialog();
            }
        });
    }

    public void setPostAddressApi(int addId, int contctId) {
        JsonObject patientDetail = new JsonObject();
        patientDetail.addProperty("Patname", patName);
        patientDetail.addProperty("Dob", dob);
        patientDetail.addProperty("GenderId", genderId);
        patientDetail.addProperty("AddressId", addId);
        patientDetail.addProperty("ContactId", contctId);


        final JsonObject patientDetailObject = new JsonObject();
        patientDetailObject.add("Pt", patientDetail);
        patientDetailObject.addProperty("UserRoleID", userRoleId);
        patientDetailObject.addProperty("sub_tenant_id", subTanentId);
        patientDetailObject.addProperty("EntryTypeId", 1);
        patientDetailObject.addProperty("RecNatureId", 1);//patientDetailObject.addProperty("RecNatureId", appNatureId);
        patientDetailObject.addProperty("ImagePathId", ImagePathId);
        patientDetailObject.addProperty("HospitalNo", hospital_id.getText().toString());
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postPatient2(patientDetailObject, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                patientRegistrationId = Integer.parseInt(s);
                patDemographics(patientRegistrationId);
                //Toast.makeText(AddNewAppointment.this, "patientRegistrationId=" + patientRegistrationId, Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }*/

    public void patDemographics(int mrno) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getPatDemographics(mrno, userRoleId, new Callback<PatDemoGraphics>() {
            @Override
            public void success(PatDemoGraphics patDemoGraphics, Response response) {
                dismissLoadingDialog();
                patDemoId = patDemoGraphics.getPatDemoid();
                //Toast.makeText(AddNewAppointment.this, "patDemoId=" + patDemoId, Toast.LENGTH_LONG).show();
                requestForPatientProfileAttach();

            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    private boolean isContainAllZero(String zip) {
        for (int i = 0; i < zip.length(); i++) {
            if (!(String.valueOf(zip.charAt(i)).equals("0"))) {
                return true;
            }
        }
        return false;
    }

    public void requestForPatImageUpload(String encodedImage1) {
        JsonObject obj = new JsonObject();
        obj.addProperty("fileStream", encodedImage1);

        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.fileUploadImage(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                ImagePathId = s;
                patDemographics(patientRegistrationId);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void requestForPatientProfileAttach() {
        JsonObject obj = new JsonObject();
        obj.addProperty("PatDemoid", patDemoId);
        obj.addProperty("UserRoleID", userRoleId);
        obj.addProperty("ImagePathId", ImagePathId);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.updatePatientImage(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                dismissLoadingDialog();
                //Toast.makeText(AddNewAppointment.this, "response=" + s + "  " + response, Toast.LENGTH_LONG).show();
                if (registerStatus.equals("appointment")) {
                    Intent intent = new Intent(AddNewAppointment.this, LoadNFC.class).putExtra("hospital_id", hospital_id.getText().toString()).putExtra("mrnoValue", patientRegistrationId).putExtra("subTanentId", subTanentId).putExtra("id", REQUEST_APPOINTMENT_REGISTRATION);
                    startActivityForResult(intent, REQUEST_APPOINTMENT_REGISTRATION);
                } else if (registerStatus.equals("search")) {
                    Intent intent = new Intent(AddNewAppointment.this, LoadNFC.class).putExtra("hospital_id", hospital_id.getText().toString()).putExtra("mrnoValue", patientRegistrationId).putExtra("subTanentId", subTanentId).putExtra("id", REQUEST_DIRECT_REGISTRATION);
                    startActivityForResult(intent, REQUEST_DIRECT_REGISTRATION);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void getHospitalFromAPI() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getHospital(userRoleId, new Callback<SearchHospital[]>() {
            @Override
            public void success(SearchHospital[] searchHospitals, Response response) {
                searchHospitalArray = searchHospitals;
                setHospitalName();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void setHospitalName() {
        for (int i = 0; i < searchHospitalArray.length; i++) {
            hmHospitalName.put(searchHospitalArray[i].getSubTenantId(), searchHospitalArray[i].getSubTenantName());
        }
        hospitalNameList = new ArrayList<>(hmHospitalName.values());
    }

    private void showHospitalPopup() {
        alertDialog = new AlertDialog.Builder(AddNewAppointment.this);

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.search_patient_row_layout, null);
        alertDialog.setView(convertView);
        //alertDialog.setTitle("List");
        ListView lv = (ListView) convertView.findViewById(R.id.search_patient_listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.new_pat_spinner_row_layout, hospitalNameList);
        lv.setAdapter(adapter);
        ad = alertDialog.show();
        lv.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selected = ((TextView) view.findViewById(R.id.search_patient)).getText().toString();

        for (Integer h_Id : hmHospitalName.keySet()) {
            if (hmHospitalName.get(h_Id).equals(selected)) {
                hospital_id.setText(h_Id.toString());
            }
        }
        ad.dismiss();
    }
    // date validation using SimpleDateFormat


    public boolean isValidDate(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

        Date testDate = null;

        try {
            testDate = sdf.parse(date);
            Log.d("isValidDate", testDate.toString());
        } catch (ParseException e) {
            return false;
        }

        if (!sdf.format(testDate).equals(date)) {
            return false;
        }

        return true;
    } // end isValidDate

    public boolean isValidDate1(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy");

        Date testDate = null;

        try {
            testDate = sdf.parse(date);
            Log.d("isValidDate1", testDate.toString());
        } catch (ParseException e) {
            return false;
        }

        if (!sdf.format(testDate).equals(date)) {
            return false;
        }

        return true;
    } // end isValidDate

    public void getContactId() {
        JsonObject contactDetail = new JsonObject();
        contactDetail.addProperty("Mobile", mobileNumber);
        contactDetail.addProperty("Optmobile", "");
        contactDetail.addProperty("FixLine", "");
        contactDetail.addProperty("FixLineExtn", "");
        contactDetail.addProperty("Skypeid", "");
        contactDetail.addProperty("Email", emailAddress);
        contactDetail.addProperty("Optemail", "");
        final JsonObject contactIdObject = new JsonObject();
        contactIdObject.add("cd", contactDetail);
        contactIdObject.addProperty("UserRoleID", userRoleId);
        mCuraApplication.getInstance().mCuraEndPoint.postContactDetails(contactIdObject, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                int contactId = Integer.parseInt(s);
                updatePatientDetailsApi(contactId);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void updatePatientDetailsApi(int contactId) {
        JsonObject obj = new JsonObject();
        obj.addProperty("HospitalNo", hospital_id.getText().toString());
        obj.addProperty("subTenantId", subTanentId);
        obj.addProperty("mrno", mrno);
        obj.addProperty("UserRoleID", userRoleId);
        JsonObject ptObj = new JsonObject();
        ptObj.addProperty("ContactId", contactId);
        ptObj.addProperty("Patname", patientName.getText().toString());
        ptObj.addProperty("GenderId", genderId);
        ptObj.addProperty("Dob", dob);
        obj.add("Pt", ptObj);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.updatePatientDetails(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Intent intent = new Intent(AddNewAppointment.this, LoadNFC.class).putExtra("mrnoValue", mrno).putExtra("subTanentId", subTanentId).putExtra("id", REQUEST_UPDATE_REGISTRATION);
                startActivityForResult(intent, REQUEST_UPDATE_REGISTRATION);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }
}
