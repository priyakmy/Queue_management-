package com.mcura.jaideep.queuemanagement.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.Adapter.CheckInAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.CheckInAdapter_v1;
import com.mcura.jaideep.queuemanagement.Adapter.DoctorSpinnerAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.GetNatureByUserRoleIdAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.LabDetailAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.PharmacyDetailAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.ServiceListAdapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.DoctorListModel;
import com.mcura.jaideep.queuemanagement.Model.FeeFetch;
import com.mcura.jaideep.queuemanagement.Model.GetNatureByUserRoleModel;
import com.mcura.jaideep.queuemanagement.Model.GetPatientByHospitalNoModel;
import com.mcura.jaideep.queuemanagement.Model.GetServiceListModel;
import com.mcura.jaideep.queuemanagement.Model.LabOrderStatusGetModel;
import com.mcura.jaideep.queuemanagement.Model.LastBillDetailModel;
import com.mcura.jaideep.queuemanagement.Model.LocalBillModel;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.PharmacyOrderGetModel;
import com.mcura.jaideep.queuemanagement.Model.PostPaymentModel;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.SqliteHelper.SqlLiteDbHelper;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.mcura.jaideep.queuemanagement.helper.Helper;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BillPaymentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
    private ArrayList<Datum> dataList;
    private String query;
    private int firstIndex;
    private boolean flag_loading;
    private ServiceListAdapter serviceListAdapter;
    private LinearLayout ll_doc_nature, ll_pay_amount;
    private TableLayout ll_pharmacy_pat_data;
    private TableLayout ll_lab_pat_data;
    private TextView order_booth;
    private GetServiceListModel getServiceListModel;
    private String otp;
    private int pharmacyAmount;
    private int labAmount;
    private String printHeading="OPD Consultation Receipt";
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            /*case R.id.doctor_spinner:
                doctorListModel = doctorSpinnerAdapter.getItem(position);
                selectedUserRoleId = doctorListModel.getUserRoleId();
                consultantDoctorName = doctorListModel.getUserName();
                setAppointmentNature();
                break;*/
            case R.id.nature:
                getNatureByUserRoleModel = getNatureByUserRoleIdAdapter.getItem(position);
                appNatureId = getNatureByUserRoleModel.getAppNatureID();
                appNatureName = getNatureByUserRoleModel.getAppNature();
                if (selectedUserRoleId != 0 && appNatureId != 0) {
                    if (appNatureId == 33 || appNatureId == 32) {
                        Log.d("c_fee", appNatureId + " is 33");
                        et_refund_amount.setVisibility(View.VISIBLE);
                        doctorFee.setText("0");
                    } else if (appNatureId == 25) {
                        Log.d("c_fee", appNatureId + " is 25");
                        et_refund_amount.setVisibility(View.VISIBLE);
                        doctorFee.setText("0");
                    } else if (appNatureId != 25) {
                        Log.d("c_fee", appNatureId + " not 25");
                        getFeeFromAPI(selectedUserRoleId, appNatureId);
                        et_refund_amount.setVisibility(View.GONE);
                    }
                } else {
                    doctorFee.setText("0");
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        datumModel = searchPatientAdapter.getItem(position);
        setPatientDetail(datumModel);
        et_refund_amount.setText("");
        et_refund_amount.setVisibility(View.GONE);
        setAppointmentNature();
        /*if(!tvMrno.getText().toString().equals("0000")){
            nature.setEnabled(true);
        }else{
            nature.setEnabled(false);
        }*/

        nature.setSelection(0);
        nature.setEnabled(true);
        /*if (datumModel.getMrNo().intValue() != 0 && selectedUserRoleId != 0) {
            getLastBillDetail();
            //tbl_pat_history.setVisibility(View.VISIBLE);
        } else {
            //tbl_pat_history.setVisibility(View.GONE);
        }*/
        ad.dismiss();
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

    String appNatureName;
    ListView lv;
    SearchPatientModel patientSearchModel;
    Datum datumModel;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    String feeAmount = "0";
    String registrationFee = " ";
    String consultantDoctorName;
    private WebView myWebView;
    CheckInAdapter_v1 searchPatientAdapter;
    AlertDialog.Builder alertDialog;
    AlertDialog ad;
    public PatientSearchModel[] patientSearchModelsArray;
    String paymentMode = "cash";
    int appNatureId;
    GetNatureByUserRoleModel getNatureByUserRoleModel;
    TextView doctorFee;
    GetNatureByUserRoleIdAdapter getNatureByUserRoleIdAdapter;
    DoctorListModel doctorListModel;
    ImageButton ib_fillCashCard;
    GetNatureByUserRoleModel[] appointmentNaturesArray;
    DoctorSpinnerAdapter doctorSpinnerAdapter;
    DoctorListModel[] doctorArray;
    Spinner nature;
    TextView doctor_spinner;
    GetPatientByHospitalNoModel getPatientByHospitalNo;
    ProgressDialog progressDialog;
    public MCuraApplication mCuraApplication;
    private LastBillDetailModel lastBillDetail;
    ImageView hospital_logo, h_id;
    public int subTanentId, user_role_id, selectedUserRoleId, frontOfficeUserRoleId;
    SharedPreferences mSharedPreference;
    RadioGroup genderGroup, payment_mode;
    TextView tvHospitalId, tvMrno, tvAge, tvPatientName, tvPatientPhone, tvGender;
    TextView activity_bill_payment_paynow;
    EditText et_search_key, et_refund_amount, bill_payment_activity_hIs_no;
    RelativeLayout ib_search_icon;
    TextView appointment, queue_mgmt;
    String ageS, completeDate, registrationType = " ";
    ImageView iv_newcard, iv_oldcard, iv_nocard;
    int serviceType = 1, year, month, date;
    SqlLiteDbHelper dbHelper;
    String doctor_name;
    String feeGenerateStatus = "";
    String departmentName, time;
    TableLayout tbl_pat_history;
    TextView tv_amount, tv_nature, tv_time, tv_date, tv_DoctorName, tv_BillNo;
    String subTanantName, subTanantAddress, subTanantContact;
    Spinner serviceListSpinner;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RelativeLayout rl_bill_payment_activity_hIs_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        dbHelper = new SqlLiteDbHelper(this);
        dbHelper.openDataBase();
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        completeDate = date + "/" + month + "/" + year;
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        user_role_id = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        consultantDoctorName = mSharedPreference.getString(Constant.UNAME_KEY, "Default");
        selectedUserRoleId = user_role_id;
        departmentName = dbHelper.Get_DoctorDepartment(selectedUserRoleId + "");
        Log.d("departmentName", departmentName);
        subTanentId = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
        doctorListModel = new DoctorListModel();
        getNatureByUserRoleModel = new GetNatureByUserRoleModel();
        initView();
    }

    private void initView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        et_refund_amount = (EditText) findViewById(R.id.et_refund_amount);
        et_refund_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int fee = 0;
                int refundFee = 0;
                List<LocalBillModel> modelList = dbHelper.getvalidateBillDetail(selectedUserRoleId + "", tvMrno.getText().toString(), Helper.getCurrentDataAndTime());

                for (int i = 0; i < modelList.size(); i++) {
                    if (modelList.get(i).getFee_nature().equals("Refund")) {
                        refundFee += Integer.parseInt(modelList.get(i).getFee());

                    } else {
                        fee += Integer.parseInt(modelList.get(i).getFee());
                    }
                }
                Log.d("fee", refundFee + "");
                Log.d("fee", fee + "");
                if (appNatureId == 25) {
                    if (et_refund_amount.getText().length() > 0) {
                        if (fee >= (refundFee + Integer.parseInt(et_refund_amount.getText().toString()))) {
                            //Toast.makeText(BillPaymentActivity.this,"true",Toast.LENGTH_SHORT).show();
                        } else {
                            et_refund_amount.setText("");
                            et_refund_amount.setError("Amount cannot be greater than doctor fee");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        bill_payment_activity_hIs_no = (EditText) findViewById(R.id.bill_payment_activity_hIs_no);
        rl_bill_payment_activity_hIs_no = (RelativeLayout) findViewById(R.id.rl_bill_payment_activity_hIs_no);
        order_booth = (TextView) findViewById(R.id.order_booth);
        order_booth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BillPaymentActivity.this, OrderBoothActivity.class));
            }
        });
        ll_doc_nature = (LinearLayout) findViewById(R.id.ll_doc_nature);
        ll_pharmacy_pat_data = (TableLayout) findViewById(R.id.ll_pharmacy_pat_data);
        ll_lab_pat_data = (TableLayout) findViewById(R.id.ll_lab_pat_data);
        ll_pay_amount = (LinearLayout) findViewById(R.id.ll_pay_amount);
        ImageView bill_summary = (ImageView) mToolbar.findViewById(R.id.bill_summary);
        ImageView iv_local_bill = (ImageView) findViewById(R.id.iv_local_bill);
        iv_local_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BillPaymentActivity.this, LocalBillStatusActivity.class));
            }
        });
        bill_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BillPaymentActivity.this, BillDetailActivity.class).putExtra("userRoleId", selectedUserRoleId));
            }
        });
        String subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        hospital_logo = (ImageView) mToolbar.findViewById(R.id.hospital_logo);
        Picasso.with(BillPaymentActivity.this).load(subtanentImagePath).into(hospital_logo);
        /*if(!subtanentImagePath.equals("default")){
            Picasso.with(BillPaymentActivity.this).load(subtanentImagePath).into(hospital_logo);
        }else{
            Picasso.with(BillPaymentActivity.this).load(R.drawable.kims_logo).into(hospital_logo);
        }*/
        if (subTanentId == 500) {
            subTanantName = "BLK Hospital";
            subTanantAddress = "Pusa Road, Rajinder Nagar, New Delhi, Delhi 110005";
            subTanantContact = "Tel:+91-11-30403040";
            ll_doc_nature.setVisibility(View.VISIBLE);
            rl_bill_payment_activity_hIs_no.setVisibility(View.GONE);
        } else if (subTanentId == 243) {
            subTanantName = "Sir Ganga Ram Hospital";
            subTanantAddress = "Rajinder Nagar, New Delhi-60";
            subTanantContact = "Tel:+91-11-25750000, 42254000, Fax:+91-1142251755";
            ll_doc_nature.setVisibility(View.VISIBLE);
            rl_bill_payment_activity_hIs_no.setVisibility(View.GONE);
            ll_pay_amount.setVisibility(View.VISIBLE);
        } else if (subTanentId == 525) {
            subTanantName = "U K Nursing Home";
            subTanantAddress = "M-1, Main Road, Vikas Puri, Delhi, 110018";
            subTanantContact = "Tel:+91-11-40955555";
            ll_doc_nature.setVisibility(View.VISIBLE);
            rl_bill_payment_activity_hIs_no.setVisibility(View.GONE);
        } else if(subTanentId == 531){
            subTanantName = "Fernandez Stork Home";
            subTanantAddress = "8-2-698, Road No. 12, Banjara Hills,Next to Seating World, Hyderabad, Telangana 500034";
            subTanantContact = "Tel:+91-40 4780 7300";
            ll_doc_nature.setVisibility(View.VISIBLE);
            rl_bill_payment_activity_hIs_no.setVisibility(View.GONE);
        } else if (subTanentId == 528) {
            getServiceListFromApi();
            subTanantName = "KIMS Hospitals";
            subTanantAddress = "1-112 / 86, Survey No 55/ EE, Kondapur Village, Serilingampally Mandal, Hyderabad, Telangana 500084";
            subTanantContact = "Tel:+91-88973 14141";
            serviceListSpinner = (Spinner) findViewById(R.id.service_type_spinner);
            serviceListSpinner.setVisibility(View.VISIBLE);
            serviceListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getServiceListModel = serviceListAdapter.getItem(position);
                    serviceType = getServiceListModel.getServiceID();
                    if (getServiceListModel.getServiceID() == EnumType.ServiceType.mDoctorFee528.getID()) {
                        getLastBillDetail();
                        ll_doc_nature.setVisibility(View.VISIBLE);
                        rl_bill_payment_activity_hIs_no.setVisibility(View.GONE);
                        ll_pharmacy_pat_data.setVisibility(View.GONE);
                        ll_lab_pat_data.setVisibility(View.GONE);
                        ll_pay_amount.setVisibility(View.VISIBLE);
                    } else if ((getServiceListModel.getServiceID() == EnumType.ServiceType.mLabBilling528.getID())) {
                        getLabOrderBillDetail();
                        tbl_pat_history.setVisibility(View.GONE);
                        ll_doc_nature.setVisibility(View.GONE);
                        rl_bill_payment_activity_hIs_no.setVisibility(View.GONE);
                        ll_pharmacy_pat_data.setVisibility(View.GONE);
                        ll_lab_pat_data.setVisibility(View.GONE);
                        //ll_pay_amount.setVisibility(View.GONE);
                    } else if ((getServiceListModel.getServiceID() == EnumType.ServiceType.mPharmacyBilling528.getID())) {
                        getPharmacyOrderBillDetail();
                        tbl_pat_history.setVisibility(View.GONE);
                        ll_doc_nature.setVisibility(View.GONE);
                        rl_bill_payment_activity_hIs_no.setVisibility(View.GONE);
                        ll_pharmacy_pat_data.setVisibility(View.GONE);
                        ll_lab_pat_data.setVisibility(View.GONE);
                        //ll_pay_amount.setVisibility(View.GONE);
                    } else {
                        tbl_pat_history.setVisibility(View.GONE);
                        ll_doc_nature.setVisibility(View.GONE);
                        rl_bill_payment_activity_hIs_no.setVisibility(View.GONE);
                        ll_pharmacy_pat_data.setVisibility(View.GONE);
                        ll_lab_pat_data.setVisibility(View.GONE);
                        ll_pay_amount.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        ib_fillCashCard = (ImageButton) findViewById(R.id.ib_fillCashCard);
        tv_amount = (TextView) findViewById(R.id.tvamount);
        tv_time = (TextView) findViewById(R.id.tvtime);
        tv_nature = (TextView) findViewById(R.id.tvnature);
        tv_date = (TextView) findViewById(R.id.tvdate);

        tv_DoctorName = (TextView) findViewById(R.id.tvDoctorName);
        tv_BillNo = (TextView) findViewById(R.id.tvBillNo);

        tbl_pat_history = (TableLayout) findViewById(R.id.tbl_pat_history);
        doctorFee = (TextView) findViewById(R.id.doctor_fee);
        h_id = (ImageView) findViewById(R.id.h_id);
        tvHospitalId = (TextView) findViewById(R.id.hospital_id);
        tvMrno = (TextView) findViewById(R.id.bill_payment_activity_mrno);
        tvAge = (TextView) findViewById(R.id.bill_payment_activity_patient_age);
        tvPatientName = (TextView) findViewById(R.id.bill_payment_activity_patient_name);
        tvPatientPhone = (TextView) findViewById(R.id.bill_payment_activity_phone);
        tvGender = (TextView) findViewById(R.id.bill_payment_activity_patient_gender);
        payment_mode = (RadioGroup) findViewById(R.id.payment_mode);
        et_search_key = (EditText) findViewById(R.id.et_search_key);
        ib_search_icon = (RelativeLayout) findViewById(R.id.ib_search_icon);
        appointment = (TextView) findViewById(R.id.appointment);
        queue_mgmt = (TextView) findViewById(R.id.queue_mgmt);
        iv_newcard = (ImageView) findViewById(R.id.iv_newcard);
        iv_oldcard = (ImageView) findViewById(R.id.iv_oldcard);
        iv_nocard = (ImageView) findViewById(R.id.iv_nocard);

        payment_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cash:
                        paymentMode = "cash";
                        break;
                    case R.id.card:
                        paymentMode = "card";
                        break;
                    case R.id.cheque:
                        paymentMode = "cheque";
                        break;
                }
            }
        });
        doctor_spinner = (TextView) findViewById(R.id.doctor_spinner);
        doctor_spinner.setText(consultantDoctorName);
        nature = (Spinner) findViewById(R.id.nature);
        setAppointmentNature();
        /*if(!tvMrno.getText().toString().equals("0000")){
            nature.setEnabled(true);
        }else{
            nature.setEnabled(false);
        }*/

        nature.setSelection(0);
        ImageView add_patient = (ImageView) mToolbar.findViewById(R.id.add_patient);
        activity_bill_payment_paynow = (TextView) findViewById(R.id.activity_bill_payment_paynow);
        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(BillPaymentActivity.this, LocalBillPaymentActivity.class));
                startActivity(new Intent(BillPaymentActivity.this, AddNewAppointment.class).putExtra("appNatureId", 1).putExtra("updateStatus", "add_new_patient").putExtra("registerStatus", "search"));
            }
        });
        //h_id.setOnClickListener(this);
        doctor_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(BillPaymentActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.search_doctor_dialog, null);
                builder.setView(convertView);
                SearchView search_doctor = (SearchView) convertView.findViewById(R.id.search_doctor);
                search_doctor.setIconified(false);
                search_doctor.setIconifiedByDefault(false);
                search_doctor.setOnQueryTextListener(BillPaymentActivity.this);
                search_doctor.setQueryHint("Search Here");
                lv = (ListView) convertView.findViewById(R.id.doctor_list);
                lv.setTextFilterEnabled(true);
                doctorSpinnerAdapter = new DoctorSpinnerAdapter(BillPaymentActivity.this,
                        android.R.layout.simple_spinner_item,
                        doctorArray);
                lv.setAdapter(doctorSpinnerAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        doctorListModel = doctorSpinnerAdapter.getItem(position);
                        if (doctorListModel.getUserRoleId() != 0) {
                            tbl_pat_history.setVisibility(View.VISIBLE);
                        } else {
                            tbl_pat_history.setVisibility(View.GONE);
                        }

                        selectedUserRoleId = doctorListModel.getUserRoleId();
                        departmentName = dbHelper.Get_DoctorDepartment(selectedUserRoleId + "");
                        Log.d("departmentName", departmentName);
                        consultantDoctorName = doctorListModel.getUserName();
                        doctor_spinner.setText(consultantDoctorName);
                        if (selectedUserRoleId != 0 && !tvMrno.getText().toString().equals("0000")) {
                            getLastBillDetail();
                            //tbl_pat_history.setVisibility(View.VISIBLE);
                        } else {
                            tbl_pat_history.setVisibility(View.GONE);
                        }
                        setAppointmentNature();
                        if (!tvMrno.getText().toString().equals("0000")) {
                            nature.setEnabled(true);
                        } else {
                            nature.setEnabled(false);
                        }
                        dialog.dismiss();
                        //getScheduleData();
                    }
                });

                dialog = builder.show();
            }
        });
        nature.setOnItemSelectedListener(this);
        ib_fillCashCard.setOnClickListener(this);
        /*nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.isEnabled()){
                    Toast.makeText(BillPaymentActivity.this,"Please Select Patient",Toast.LENGTH_Lo)
                }
            }
        });*/
        activity_bill_payment_paynow.setOnClickListener(this);
        ib_search_icon.setOnClickListener(this);
        appointment.setOnClickListener(this);
        queue_mgmt.setOnClickListener(this);
        iv_newcard.setOnClickListener(this);
        iv_oldcard.setOnClickListener(this);
        iv_nocard.setOnClickListener(this);
        getDoctorDetail();

    }

    private void showPharmacyListDialog(final PharmacyOrderGetModel[] pharmacyOrderGetModels) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pharmacy_get_data_list_dialog);
        final EditText bill_payment_activity_hIs = (EditText) dialog.findViewById(R.id.bill_payment_activity_hIs_no);
        ListView pharmacyListView = (ListView) dialog.findViewById(R.id.pharmacy_list);
        final PharmacyDetailAdapter pharmacyDetailAdapter = new PharmacyDetailAdapter(BillPaymentActivity.this, pharmacyOrderGetModels);
        pharmacyListView.setAdapter(pharmacyDetailAdapter);
        Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        pharmacyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PharmacyOrderGetModel pharmacyOrderGetModel = pharmacyDetailAdapter.getItem(position);
                if(!TextUtils.isEmpty(bill_payment_activity_hIs.getText().toString())){
                    callPaymentAPiforPharmacyKims(pharmacyOrderGetModel);
                }else{
                    bill_payment_activity_hIs.setError("Enter HIS no.");
                }
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width * 0.9);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 1f; // Transparency

        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private void showLabListDialog(final LabOrderStatusGetModel[] labOrderStatusGetModel) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lab_get_data_list_dialog);
        final EditText bill_payment_activity_hIs = (EditText) dialog.findViewById(R.id.bill_payment_activity_hIs_no);
        ListView labListView = (ListView) dialog.findViewById(R.id.lab_list);
        final LabDetailAdapter labDetailAdapter = new LabDetailAdapter(BillPaymentActivity.this, labOrderStatusGetModel);
        labListView.setAdapter(labDetailAdapter);
        Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        labListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final LabOrderStatusGetModel labOrderGetModel = labDetailAdapter.getItem(position);
                if(!TextUtils.isEmpty(bill_payment_activity_hIs.getText().toString())){
                    callPaymentAPiforLabKims(bill_payment_activity_hIs.getText().toString(),labOrderGetModel);
                }else{
                    bill_payment_activity_hIs.setError("Enter HIS no.");
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width * 0.9);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 1f; // Transparency

        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private void getLabOrderBillDetail() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.labOrderStatusGet(user_role_id, Integer.parseInt(tvMrno.getText().toString()), subTanentId, new Callback<LabOrderStatusGetModel[]>() {
            @Override
            public void success(LabOrderStatusGetModel[] labOrderStatusGetModel, Response response) {
                if (labOrderStatusGetModel != null) {
                    showLabListDialog(labOrderStatusGetModel);
                } else {
                    Toast.makeText(BillPaymentActivity.this, "No data found for today.", Toast.LENGTH_SHORT).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void getPharmacyOrderBillDetail() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.pharmacyOrderGet(user_role_id, Integer.parseInt(tvMrno.getText().toString()), subTanentId, new Callback<PharmacyOrderGetModel[]>() {
            @Override
            public void success(PharmacyOrderGetModel[] pharmacyOrderGetModels, Response response) {
                //showPharmacyListDialog(pharmacyOrderGetModels);
                if (pharmacyOrderGetModels != null) {
                    showPharmacyListDialog(pharmacyOrderGetModels);
                } else {
                    Toast.makeText(BillPaymentActivity.this, "No data found for today.", Toast.LENGTH_SHORT).show();
                }

                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void getServiceListFromApi() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPointTest.getServiceList(subTanentId, new Callback<GetServiceListModel[]>() {
            @Override
            public void success(GetServiceListModel[] getServiceListModels, Response response) {
                if (getServiceListModels.length > 0) {
                    serviceListAdapter = new ServiceListAdapter(BillPaymentActivity.this, android.R.layout.simple_spinner_item, getServiceListModels);
                    serviceListSpinner.setAdapter(serviceListAdapter);
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
        int doctorFees;
        switch (v.getId()) {
            case R.id.ib_fillCashCard:
                //callPaymentAPiforPharmacyKims();
                startActivity(new Intent(BillPaymentActivity.this, FillCashCardActivity.class));
                finish();
                break;
            case R.id.iv_newcard:
                getDoctorDetail();
                //doctorFees = Integer.parseInt(doctorFee.getText().toString())+100;
                //doctorFee.setText(doctorFees+"");
                registrationType = "New Registration Charges";
                registrationFee = "Rs. " + 100;
                serviceType = 2;
                iv_newcard.setImageResource(R.drawable.newcard_s);
                iv_oldcard.setImageResource(R.drawable.oldcard);
                iv_nocard.setImageResource(R.drawable.none);
                break;
            case R.id.iv_oldcard:
                getDoctorDetail();
                //doctorFees = Integer.parseInt(doctorFee.getText().toString())+20;
                //doctorFee.setText(doctorFees+"");
                serviceType = 3;
                registrationFee = "Rs. " + 20;
                registrationType = "Old Registration Charges";
                iv_newcard.setImageResource(R.drawable.newcard);
                iv_oldcard.setImageResource(R.drawable.oldcard_s);
                iv_nocard.setImageResource(R.drawable.none);
                break;
            case R.id.iv_nocard:
                getDoctorDetail();
                serviceType = 1;
                registrationFee = " ";
                registrationType = " ";
                iv_newcard.setImageResource(R.drawable.newcard);
                iv_oldcard.setImageResource(R.drawable.oldcard);
                iv_nocard.setImageResource(R.drawable.none_s);
                break;
            case R.id.appointment:
                startActivity(new Intent(BillPaymentActivity.this, CalendarActivity.class));
                break;
            case R.id.queue_mgmt:
                startActivity(new Intent(BillPaymentActivity.this, QueueStatusActivity.class));
                break;
            case R.id.ib_search_icon:
                String searchKey = et_search_key.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        et_search_key.getWindowToken(), 0);
                //new RequestTask().execute("http://test.tn.mcura.com/health_dev.svc/Json/SearchPatient?UserRoleID=2110&Searchkey=t&sub_tenant_id=500");
                //getPatientSearchDetail(searchKey);
                firstIndex = 2;
                query = searchKey;
                patientSearchModel = new SearchPatientModel();
                dataList = new ArrayList<>();
                getPatientSearchDetail(this.query);
                break;
            /*case R.id.h_id:
                if (etHospitalId.getText().toString().length() != 0) {
                    //getPatientByHospitalNoApi(etHospitalId.getText().toString());
                    checkHospitalPatientAPI(etHospitalId.getText().toString());
                } else {
                    etHospitalId.setError("Please Enter hospital Id");
                }
                break;*/
            case R.id.activity_bill_payment_paynow:
                if (frontOfficeUserRoleId == EnumType.LoginType.mSGRH.getID()) {
                    callPaymentAPiforKims();
                    printHeading="OPD Consultation Receipt";
                } else if(frontOfficeUserRoleId == EnumType.LoginType.mFNDS.getID()){
                    callPaymentAPiforKims();
                    printHeading="OPD Consultation Receipt";
                }
                else if (frontOfficeUserRoleId == EnumType.LoginType.mKIMSDEVELOPMENT.getID() || frontOfficeUserRoleId == EnumType.LoginType.mKIMSPRODUCTION.getID()) {
                    if (getServiceListModel.getServiceID() == EnumType.ServiceType.mDoctorFee528.getID()) {
                        printHeading="OPD Consultation Receipt";
                        callPaymentAPiforKims();
                    } else if (getServiceListModel.getServiceID() == EnumType.ServiceType.mPharmacyBilling528.getID()) {
                        //callPaymentAPiforPharmacyKims();
                    } else if (getServiceListModel.getServiceID() == EnumType.ServiceType.mLabBilling528.getID()) {

                    }
                }
                break;
        }
    }

    private void callPaymentAPiforPharmacyKims(PharmacyOrderGetModel pharmacyOrderGetModel) {
        int hId;
        if(tvHospitalId.getText().toString().equals("")){
            hId = 0;
        }else{
            hId = Integer.parseInt(tvHospitalId.getText().toString());
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", hId);
        obj.addProperty("AppnatureId", 0);
        obj.addProperty("UserRoleId", selectedUserRoleId);
        obj.addProperty("Description", "");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", pharmacyOrderGetModel.getAmount());
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", serviceType);
        obj.addProperty("Mrno", tvMrno.getText().toString());
        obj.addProperty("HIS_BillNo", bill_payment_activity_hIs_no.getText().toString());
        obj.addProperty("MobileNo", tvPatientPhone.getText().toString().trim());
        obj.addProperty("LabOrderID", 0);
        obj.addProperty("PharmacyOrderID", pharmacyOrderGetModel.getPresOrderId());
        if (paymentMode.equals("cash")) {
            postPaymentForPharmacyCash(obj);
        }
        if (paymentMode.equals("card")) {
            postPaymentAPIForCard(obj);
        }
        if (paymentMode.equals("cheque")) {
            postPaymentForPharmacyCash(obj);
        }
    }

    private void postPaymentForPharmacyCash(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postPayment_v1(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void callPaymentAPiforLabKims(String his,LabOrderStatusGetModel labOrderGetModel) {
        JsonObject obj = new JsonObject();
        int hid;
        if(tvHospitalId.getText().toString().equals("")){
            hid=0;
        }else{
            hid= Integer.parseInt(tvHospitalId.getText().toString());
        }
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", hid);
        obj.addProperty("AppnatureId", 0);
        obj.addProperty("UserRoleId", selectedUserRoleId);
        obj.addProperty("Description", "");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", labOrderGetModel.getAmount());
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", serviceType);
        obj.addProperty("Mrno", tvMrno.getText().toString());
        obj.addProperty("HIS_BillNo", his);
        obj.addProperty("MobileNo", tvPatientPhone.getText().toString().trim());
        obj.addProperty("LabOrderID", labOrderGetModel.getLaborderId());
        obj.addProperty("PharmacyOrderID", 0);
        if (paymentMode.equals("cash")) {
            postPaymentForCash(obj);
        }
        if (paymentMode.equals("card")) {
            postPaymentForCash(obj);
        }
        if (paymentMode.equals("cheque")) {
            postPaymentForCash(obj);
        }
    }

    private void callPaymentAPiforKims() {
        if (paymentMode.equals("cash")) {
            if (!tvMrno.getText().toString().equals("0000")) {
                if (appNatureId != 0) {
                    if (appNatureId == 25) {
                        if (et_refund_amount.getText().length() != 0) {
                            feeAmount = et_refund_amount.getText().toString();
                            feeGenerateStatus = "Refund Amount";
                        }
                    } else if (appNatureId == 33 || appNatureId == 32) {
                        if (et_refund_amount.getText().length() != 0) {
                            feeAmount = et_refund_amount.getText().toString();
                            feeGenerateStatus = "OPD Consultation Fee";
                        }
                    } else if (appNatureId != 25) {
                        feeAmount = doctorFee.getText().toString();
                        feeGenerateStatus = "OPD Consultation Fee";
                    }
                    LocalBillModel localBillModel = dbHelper.patBillStatus(selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureId + "");
                    if (localBillModel != null) {
                        if (localBillModel.getBill_status().equals("1")) {
                            Log.d("localBillModel", localBillModel.getHospital_id() + "");
                            localPrintBill(localBillModel);
                            Toast.makeText(BillPaymentActivity.this, "Duplicate Bill", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        int serial_no = dbHelper.getMaxSerial_no() + 1;
                        String timestamp = selectedUserRoleId + "" + serial_no;
                        time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                        boolean insertStatus = dbHelper.insertBillDetail(consultantDoctorName, selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureName, "0", "", "", departmentName, tvHospitalId.getText().toString(), frontOfficeUserRoleId + "", timestamp, subTanentId + "", appNatureId + "", paymentMode + "", serviceType + "", serial_no, time, "ON");
                        if (insertStatus) {
                            postPaymentAPI_V1();
                        } else {
                        }
                    }
                } else {
                    Toast.makeText(BillPaymentActivity.this, "Please Select Nature", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(BillPaymentActivity.this, "Please Select Patient", Toast.LENGTH_LONG).show();
            }
        } else if (paymentMode.equals("card")) {
            if (!tvMrno.getText().toString().equals("0000")) {
                if (appNatureId != 0) {
                    if (appNatureId == 25) {
                        if (et_refund_amount.getText().length() != 0) {
                            feeAmount = et_refund_amount.getText().toString();
                            feeGenerateStatus = "Refund Amount";
                        }
                    } else if (appNatureId == 33 || appNatureId == 32) {
                        if (et_refund_amount.getText().length() != 0) {
                            feeAmount = et_refund_amount.getText().toString();
                            feeGenerateStatus = "OPD Consultation Fee";
                        }
                    } else if (appNatureId != 25) {
                        feeAmount = doctorFee.getText().toString();
                        feeGenerateStatus = "OPD Consultation Fee";
                    }
                    LocalBillModel localBillModel = dbHelper.patBillStatus(selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureId + "");
                    if (localBillModel != null) {
                        if (localBillModel.getBill_status().equals("1")) {
                            Log.d("localBillModel", localBillModel.getHospital_id() + "");
                            localPrintBill(localBillModel);
                            Toast.makeText(BillPaymentActivity.this, "Duplicate Bill", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        postPaymentAPI_V1();
                    }
                } else {
                    Toast.makeText(BillPaymentActivity.this, "Please Select Nature", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(BillPaymentActivity.this, "Please Select Patient", Toast.LENGTH_LONG).show();
            }
        }

    }


    private void callPaymentAPi() {
        if (!tvMrno.getText().toString().equals("0000")) {
            if (appNatureId != 0) {
                if (appNatureId == 25) {
                    if (et_refund_amount.getText().length() != 0) {
                        feeAmount = et_refund_amount.getText().toString();
                        feeGenerateStatus = "Refund Amount";
                    }
                } else if (appNatureId == 33 || appNatureId == 32) {
                    if (et_refund_amount.getText().length() != 0) {
                        feeAmount = et_refund_amount.getText().toString();
                        feeGenerateStatus = "OPD Consultation Fee";
                    }
                } else if (appNatureId != 25) {
                    feeAmount = doctorFee.getText().toString();
                    feeGenerateStatus = "OPD Consultation Fee";
                }
                LocalBillModel localBillModel = dbHelper.patBillStatus(selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureId + "");
                if (localBillModel != null) {
                    if (localBillModel.getBill_status().equals("1")) {
                        Log.d("localBillModel", localBillModel.getHospital_id() + "");
                        localPrintBill(localBillModel);
                        Toast.makeText(BillPaymentActivity.this, "Duplicate Bill", Toast.LENGTH_LONG).show();
                    }
                } else {
                    int serial_no = dbHelper.getMaxSerial_no() + 1;
                    String timestamp = selectedUserRoleId + "" + serial_no;
                    time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                    boolean insertStatus = dbHelper.insertBillDetail(consultantDoctorName, selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureName, "0", "", "", departmentName, tvHospitalId.getText().toString(), frontOfficeUserRoleId + "", timestamp, subTanentId + "", appNatureId + "", paymentMode + "", serviceType + "", serial_no, time, "ON");
                    if (insertStatus) {
                        postPaymentAPI_V1();
                    } else {
                    }
                }
            } else {
                Toast.makeText(BillPaymentActivity.this, "Please Select Nature", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(BillPaymentActivity.this, "Please Select Patient", Toast.LENGTH_LONG).show();
        }
    }

    private void postPaymentAPI_V1() {
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", tvHospitalId.getText().toString());
        obj.addProperty("AppnatureId", appNatureId);
        obj.addProperty("UserRoleId", selectedUserRoleId);
        obj.addProperty("Description", "");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", feeAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", serviceType);
        obj.addProperty("Mrno", tvMrno.getText().toString());
        obj.addProperty("HIS_BillNo", 0);
        obj.addProperty("MobileNo", tvPatientPhone.getText().toString().trim());
        obj.addProperty("LabOrderID", 0);
        obj.addProperty("PharmacyOrderID", 0);
        if (paymentMode.equals("cash")) {
            postPaymentForCash(obj);
        }
        if (paymentMode.equals("card")) {
            postPaymentAPIForCard(obj);
        }
        if (paymentMode.equals("cheque")) {
            postPaymentForCash(obj);
        }

    }

    private void postPaymentAPIForCard(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postPayment_v1(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if(postPaymentModel.getMsg().equals("Balance is less than Bill Amount. Please Add Amount")){
                    showErrorDialog(postPaymentModel.getMsg());
                }else{
                    showOTPDialog();
                }
                dismissLoadingDialog();
            }
            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }
    private void showErrorDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(BillPaymentActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.show();
    }
    private void showOTPDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.otp_dialog);

        final EditText et_otp = (EditText) dialog.findViewById(R.id.et_otp);
        Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width * 0.9);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 1f; // Transparency

        dialogWindow.setAttributes(lp);
        dialog.show();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = et_otp.getText().toString().trim();
                if (!TextUtils.isEmpty(otp)) {
                    verifyOTP();
                }
                dialog.dismiss();
            }
        });

    }

    private void verifyOTP() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.verifyOTP(Integer.parseInt(tvMrno.getText().toString()), Integer.parseInt(otp), new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatus()) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    int serial_no = dbHelper.getMaxSerial_no() + 1;
                    String timestamp = selectedUserRoleId + "" + serial_no;
                    time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                    boolean insertStatus = dbHelper.insertBillDetail(consultantDoctorName, selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureName, "1", data[1], data[0], data[2], tvHospitalId.getText().toString(), frontOfficeUserRoleId + "", timestamp, subTanentId + "", appNatureId + "", paymentMode + "", serviceType + "", serial_no, time, "ON");
                    if (insertStatus) {
                        printBill(data);
                    }
                } else {
                    Toast.makeText(BillPaymentActivity.this, "failure", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void postPaymentForCash(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postPayment_v1(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatus()) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    boolean updateStatus = dbHelper.updatePatientBillRecord(selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, "1", data[1], data[0], appNatureId + "", "ON");
                    if (updateStatus) {
                        printBill(data);
                    }
                } else {
                    Toast.makeText(BillPaymentActivity.this, "failure", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                boolean updateStatus = dbHelper.updatePatientBillFlag(selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureId + "", "OFF");
                if (updateStatus) {
                    //Toast.makeText(BillPaymentActivity.this, "flag updated successfully", Toast.LENGTH_LONG).show();
                    LocalBillModel localBillModel = dbHelper.patBillStatus(selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureId + "");
                    if (localBillModel != null) {
                        if (localBillModel.getBill_status().equals("0")) {
                            Log.d("serialno", localBillModel.getTempBillNo() + "");
                            localPrintBill(localBillModel);

                        }
                    } else {
                        Log.d("localBillModel", "failure");
                    }
                } else {
                    Toast.makeText(BillPaymentActivity.this, "failure", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }
        });
    }

    public void postPaymentAPI() {
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", tvHospitalId.getText().toString());
        obj.addProperty("AppnatureId", appNatureId);
        obj.addProperty("UserRoleId", selectedUserRoleId);
        obj.addProperty("Description", "");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", feeAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", serviceType);
        obj.addProperty("Mrno", tvMrno.getText().toString());

        mCuraApplication.getInstance().mCuraEndPointPostPayment.postPayment(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {

                if (postPaymentModel.getStatus()) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    boolean updateStatus = dbHelper.updatePatientBillRecord(selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, "1", data[1], data[0], appNatureId + "", "ON");
                    if (updateStatus) {
                        printBill(data);
                    }
                } else {
                    Toast.makeText(BillPaymentActivity.this, "failure", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                boolean updateStatus = dbHelper.updatePatientBillFlag(selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureId + "", "OFF");
                if (updateStatus) {
                    //Toast.makeText(BillPaymentActivity.this, "flag updated successfully", Toast.LENGTH_LONG).show();
                    LocalBillModel localBillModel = dbHelper.patBillStatus(selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureId + "");
                    if (localBillModel != null) {
                        if (localBillModel.getBill_status().equals("0")) {
                            Log.d("serialno", localBillModel.getTempBillNo() + "");
                            localPrintBill(localBillModel);

                        }
                    } else {
                        Log.d("localBillModel", "failure");
                    }
                } else {
                    Toast.makeText(BillPaymentActivity.this, "failure", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }
        });
    }

    public void localPrintBill(LocalBillModel localBillModel) {
        String billno;
        if (localBillModel.getBill_no().equals("")) {
            billno = localBillModel.getTempBillNo();
        } else {
            billno = localBillModel.getBill_no();
        }
        WebView webView = new WebView(BillPaymentActivity.this);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(view);
                myWebView = null;
            }
        });
        String htmlDocument = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<title>Pay Slip</title>\n" +
                "<style type=\"text/css\">\n" +
                "*{ margin:0px; padding:0px;}\n" +
                ".payslipOuter{ margin:10px; padding:0px; width:700px; float:left; font-family:Arial, Helvetica, sans-serif; border:1px #ccc solid; }\n" +
                "table>tr>td{ border:none; outline:none; }\n" +
                "strong{ font-weight:normal;}\n" +
                ".invoiceTable > tbody > tr > th{ font-weight:normal;}\n" +
                ".payslipOuter h4{ font-weight:normal; font-size:15px; text-align:center; margin:3px 0px; font-weight:normal; }\n" +
                ".payslipOuter p{ text-align:center; font-size:13px; line-height:20px;}\n" +
                ".payslipOuter h3{ font-size:14px;  text-align:center; text-transform:uppercase; text-decoration:underline; line-height:22px; font-weight:normal;}\n" +
                ".receivedwith{ width:153px; font-size:14px; float:left; padding-top:12px; font-weight:bold}\n" +
                ".receivedwithValue{ width:100px; float:left;  }\n" +
                ".valueInput{ width:100%; border:1px dotted #aaa;  outline:none; border-top-color: #fff; border-left-color: #fff; border-right-color: #fff; height:20px; }\n" +
                "\n" +
                ".receivedwithValue, .sumrepeesValue, .towordsValue, .paymodeValue, .rupeesValue, .billnoValue, .dateValue, .patientnameValue, .cashcardValue, .hospitalIdValue, .rsinwordsValue, .amountwordsValue, .consultantValue{ float:left; margin-top:5px; }\n" +
                "\n" +
                ".receivedwith, .sumrepees, .towords, .paymode, .rupees, .signature, .billno, .date, .patientname, .cashcard, .hospitalId, .rsinwords, .amountWords{ font-size:14px; float:left; padding-top:12px; font-weight:normal; height:30px;}\n" +
                "\n" +
                "\n" +
                ".billno, .date, .patientname, .cashcard, .hospitalId, .consultant{ height:25px; font-size:13px; float:left; padding-top:12px; font-weight:normal; }\n" +
                "\n" +
                ".department{ text-align:center; width:100%; margin:0 auto; font-size:13px; font-weight:normal;}\n" +
                ".department .valueInput{ text-align:center;  font-weight:normal; border-bottom:0px; margin-top:5px;}\n" +
                "\n" +
                ".rupesOuter{ float:left; width:160px;}\n" +
                ".signatureOuter{ float:right; width:310px; text-align:center;} \n" +
                "\n" +
                ".signature{ width:155px; float:right}\n" +
                ".signatureValue{ width:155px; float:left;}\n" +
                "\n" +
                ".rsinwords{ width:1px;}\n" +
                ".sumrepees{ width:70px;}\n" +
                ".sumrepeesValue{ width:183px}\n" +
                ".towords{ width:72px;}\n" +
                ".towordsValue{ width:298px}\n" +
                ".paymode{ width:25px;}\n" +
                ".paymodeValue{width:125px;}\n" +
                ".rupees{ width:28px;}\n" +
                ".rupeesValue{width:120px;}\n" +
                ".rsinwordsValue{ width:535px;}\n" +
                ".consultant{ width:100%;}\n" +
                ".consultantValue{width:100%;}\n" +
                "\n" +
                "\n" +
                ".amountWords{ width:130px;}\n" +
                ".amountwordsValue{ width:546px;}\n" +
                "\n" +
                "\n" +
                ".billnoValue{ width:80px;}\n" +
                ".dateValue{ width:150px;} \n" +
                ".patientnameValue{ width:200px;}\n" +
                ".cashcardValue{ width:98px;}\n" +
                ".hospitalIdValue{ width:105px;}\n" +
                "\n" +
                ".billno{ width:50px;}\n" +
                ".date{ width:45px;}\n" +
                ".patientname{ width:95px;} \n" +
                ".cashcard{ width:77px}\n" +
                ".hospitalId{ width:75px;}\n" +
                "\n" +
                "\n" +
                "\n" +
                ".receivedwithOuter{ width:260px; float:left;}\n" +
                ".sumrepeesOuter{ width:253px; float:right;}\n" +
                ".paymodeOuter{ width:160px; float:left;}\n" +
                ".towordsOuter{ width:370px; float:right;}\n" +
                ".amoutnwordsOuter{ width:100%;}\n" +
                ".consultantOuter{ width:300px; margin:0px auto;}\n" +
                ".departmentOuter{ width:200px; margin:5px auto;}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                ".billnoOuter{ width:200px; float:left;}\n" +
                ".dateOuter{ width:200px; float:right;}\n" +
                ".patientnameOuter{ width:310px; float:left;}\n" +
                ".cashcardOuter{ width:175px; float:right;}\n" +
                ".hospitalIdOuter{ width:200px; float:left;}\n" +
                "\n" +
                ".headpart{ background-color:#f2f2f2;}\n" +
                "\n" +
                ".borderLine{ width:100%; margin-top:15px; border-bottom:1px #333 dashed;}\n" +
                ".bordervLine{ border-left:1px #333 solid; height:100%; width:1px; height:250px;}\n" +
                "\n" +
                ".invoiceTable{ width:100%; font-size:14px;}\n" +
                ".invoiceTable > tbody > tr > td{ padding:7px 10px; text-align:left; }\n" +
                ".invoiceTable > tbody > tr > th{ padding:10px 10px; text-align:left; }\n" +
                "\n" +
                ".consultantValue .valueInput{ border-bottom:none; font-size:20px; text-align:center;}\n" +
                "</style>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"payslipOuter\">\n" +
                "<table width=\"100%\" style=\"padding:5px;\">\n" +
                "\t<tr>\n" +
                "\t\t<td colspan=\"2\"><div class=\"consultantOuter\">\n" +
                "        <div class=\"consultantValue\"><input type=\"text\" class=\"valueInput\" value=\"" + localBillModel.getDoctor_name() + "\"></div></div>\n" +
                "        \n" +
                "        <div class=\"departmentOuter\">\n" +
                "        <div class=\"department\"><input type=\"text\" class=\"valueInput\" value=\"" + localBillModel.getDepartment() + "\"></div></div>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "  <tr>\n" +
                "    <td colspan=\"2\"><h4>" + subTanantName + "</h4>\n" +
                "\t<p> " + subTanantAddress + "</br>\n" +
                "    " + subTanantContact + "</br></p>\n" +
                "  <h3 style=\"margin-bottom:20px;\"> "+printHeading+"</h3>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    \n" +
                " <tr>\n" +
                "    \t<td colspan=\"2\"><div class=\"billnoOuter\"><div class=\"billno\">Bill No.</div>\n" +
                "   \t    <div class=\"billnoValue\"><input type=\"text\" class=\"valueInput\" value = \"" + billno + "\"></div></div> <div class=\"dateOuter\"><div class=\"date\">Date</div> <div class=\"dateValue\"><input type=\"text\" class=\"valueInput\" value=\"" + completeDate + " | " + localBillModel.getTime() + "\"></div></div></td>\n" +
                "    </tr>\n" +
                "    \n" +
                "      <tr>\n" +
                "    \t<td colspan=\"2\"><div class=\"patientnameOuter\"><div class=\"patientname\">Patient Name</div>\n" +
                "   \t    <div class=\"patientnameValue\"><input type=\"text\" class=\"valueInput\" value=\"" + localBillModel.getPatient_name() + "\"></div></div> \n" +
                "        \n" +
                "        <div class=\"hospitalIdOuter\"><div class=\"hospitalId\">Hospital ID</div>\n" +
                "        <div class=\"hospitalIdValue\"><input type=\"text\" class=\"valueInput\" value=\"" + localBillModel.getHospital_id() + "\"></div></div>\n" +
                "        \n" +
                "        <div class=\"cashcardOuter\"><div class=\"cashcard\">E-Wallet ID</div> <div class=\"cashcardValue\"><input type=\"text\" class=\"valueInput\" value = \"" + localBillModel.getE_wallet_id() + "\"></div></div></td>\n" +
                "    </tr>\n" +
                "   \n" +
                "</table>\n" +
                "<div style=\"border-top:1px #333 solid; height:1px;\"></div>\n" +
                "<table width=\"100%\" class=\"invoiceTable\">    \n" +
                "  <tr style=\"border-bottom:1px #333 solid\">\n" +
                "  \t<th>Description</th>\n" +
                "    <th>&nbsp;</th>\n" +
                "    <th style=\"width:120px;\">Amount</th>\n" +
                "  </tr>\n" +
                " </table>\n" +
                " <div style=\"border-top:1px #333 solid; height:1px;\"></div>\n" +
                " <table width=\"100%\" class=\"invoiceTable\">\n" +
                "  <tr>\n" +
                "  \t<td>" + feeGenerateStatus + "</td>\n" +
                "     <td rowspan=\"3\" style=\"width:1px; border-left:1px #333 solid; padding:7px 0px;\">&nbsp;</td>\n" +
                "    <td style=\"width:120px;\">Rs. " + localBillModel.getFee() + "</td>\n" +
                "  </tr>\n" +
                "\n" +
                "\n" +
                "    <tr>\n" +
                "    <td>&nbsp; </td>\n" +
                "    <td>&nbsp;</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td style=\"border-top:1px #333 solid; text-align:right; padding:7px 10px;\"><strong>Total Amount</strong></td>\n" +
                "    <td style=\"border-top:1px #333 solid; padding:7px 10px;\"><strong>Rs. " + localBillModel.getFee() + "</strong></td>\n" +
                "  </tr>\n" +
                "   \n" +
                "    <tr>\n" +
                "    <td colspan=\"3\"><div class=\"amoutnwordsOuter\">\n" +
                "    <div class=\"amountWords\">Amount in words:</div>\n" +
                "    <div class=\"amountwordsValue\"><input type=\"text\" class=\"valueInput\" value = \"" + Helper.convert(Integer.parseInt(localBillModel.getFee())) + " Only\"></div>\n" +
                "     </div></td>\n" +
                "    </tr>\n" +
                "    \n" +
                "     \n" +
                "  </table>\n" +
                "  \n" +
                " <!-- <div style=\"border-top:1px #333 solid; height:1px;\"></div>-->\n" +
                "  \n" +
                "  <table width=\"100%\" style=\"padding:5px;\">\n" +
                "     <tr>     \n" +
                "    <td><div class=\"signatureOuter\">\n" +
                "      <div class=\"signature\">Authorized Signatory</br>\n" +
                "      </div>\n" +
                "     <!-- <div class=\"signatureValue\"><input type=\"text\" class=\"valueInput\"></div>--></div></td>\n" +
                "    </tr>\n" +
                "   \n" +
                "\n" +
                "  \n" +
                "</table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
        Log.d("htmlDocument", htmlDocument);
        webView.loadDataWithBaseURL(null, htmlDocument,
                "text/html", "UTF-8", null);

        myWebView = webView;
    }

    public void printBill(String data[]) {
        WebView webView = new WebView(BillPaymentActivity.this);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(view);
                myWebView = null;
            }
        });
        String htmlDocument = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<title>Pay Slip</title>\n" +
                "<style type=\"text/css\">\n" +
                "*{ margin:0px; padding:0px;}\n" +
                ".payslipOuter{ margin:10px; padding:0px; width:700px; float:left; font-family:Arial, Helvetica, sans-serif; border:1px #ccc solid; }\n" +
                "table>tr>td{ border:none; outline:none; }\n" +
                "strong{ font-weight:normal;}\n" +
                ".invoiceTable > tbody > tr > th{ font-weight:normal;}\n" +
                ".payslipOuter h4{ font-weight:normal; font-size:15px; text-align:center; margin:3px 0px; font-weight:normal; }\n" +
                ".payslipOuter p{ text-align:center; font-size:13px; line-height:20px;}\n" +
                ".payslipOuter h3{ font-size:14px;  text-align:center; text-transform:uppercase; text-decoration:underline; line-height:22px; font-weight:normal;}\n" +
                ".receivedwith{ width:153px; font-size:14px; float:left; padding-top:12px; font-weight:bold}\n" +
                ".receivedwithValue{ width:100px; float:left;  }\n" +
                ".valueInput{ width:100%; border:1px dotted #aaa;  outline:none; border-top-color: #fff; border-left-color: #fff; border-right-color: #fff; height:20px; }\n" +
                "\n" +
                ".receivedwithValue, .sumrepeesValue, .towordsValue, .paymodeValue, .rupeesValue, .billnoValue, .dateValue, .patientnameValue, .cashcardValue, .hospitalIdValue, .rsinwordsValue, .amountwordsValue, .consultantValue{ float:left; margin-top:5px; }\n" +
                "\n" +
                ".receivedwith, .sumrepees, .towords, .paymode, .rupees, .signature, .billno, .date, .patientname, .cashcard, .hospitalId, .rsinwords, .amountWords{ font-size:14px; float:left; padding-top:12px; font-weight:normal; height:30px;}\n" +
                "\n" +
                "\n" +
                ".billno, .date, .patientname, .cashcard, .hospitalId, .consultant{ height:25px; font-size:13px; float:left; padding-top:12px; font-weight:normal; }\n" +
                "\n" +
                ".department{ text-align:center; width:100%; margin:0 auto; font-size:13px; font-weight:normal;}\n" +
                ".department .valueInput{ text-align:center;  font-weight:normal; border-bottom:0px; margin-top:5px;}\n" +
                "\n" +
                ".rupesOuter{ float:left; width:160px;}\n" +
                ".signatureOuter{ float:right; width:310px; text-align:center;} \n" +
                "\n" +
                ".signature{ width:155px; float:right}\n" +
                ".signatureValue{ width:155px; float:left;}\n" +
                "\n" +
                ".rsinwords{ width:1px;}\n" +
                ".sumrepees{ width:70px;}\n" +
                ".sumrepeesValue{ width:183px}\n" +
                ".towords{ width:72px;}\n" +
                ".towordsValue{ width:298px}\n" +
                ".paymode{ width:25px;}\n" +
                ".paymodeValue{width:125px;}\n" +
                ".rupees{ width:28px;}\n" +
                ".rupeesValue{width:120px;}\n" +
                ".rsinwordsValue{ width:535px;}\n" +
                ".consultant{ width:100%;}\n" +
                ".consultantValue{width:100%;}\n" +
                "\n" +
                "\n" +
                ".amountWords{ width:130px;}\n" +
                ".amountwordsValue{ width:546px;}\n" +
                "\n" +
                "\n" +
                ".billnoValue{ width:80px;}\n" +
                ".dateValue{ width:150px;} \n" +
                ".patientnameValue{ width:200px;}\n" +
                ".cashcardValue{ width:98px;}\n" +
                ".hospitalIdValue{ width:105px;}\n" +
                "\n" +
                ".billno{ width:50px;}\n" +
                ".date{ width:45px;}\n" +
                ".patientname{ width:95px;} \n" +
                ".cashcard{ width:77px}\n" +
                ".hospitalId{ width:75px;}\n" +
                "\n" +
                "\n" +
                "\n" +
                ".receivedwithOuter{ width:260px; float:left;}\n" +
                ".sumrepeesOuter{ width:253px; float:right;}\n" +
                ".paymodeOuter{ width:160px; float:left;}\n" +
                ".towordsOuter{ width:370px; float:right;}\n" +
                ".amoutnwordsOuter{ width:100%;}\n" +
                ".consultantOuter{ width:300px; margin:0px auto;}\n" +
                ".departmentOuter{ width:200px; margin:5px auto;}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                ".billnoOuter{ width:200px; float:left;}\n" +
                ".dateOuter{ width:200px; float:right;}\n" +
                ".patientnameOuter{ width:310px; float:left;}\n" +
                ".cashcardOuter{ width:175px; float:right;}\n" +
                ".hospitalIdOuter{ width:200px; float:left;}\n" +
                "\n" +
                ".headpart{ background-color:#f2f2f2;}\n" +
                "\n" +
                ".borderLine{ width:100%; margin-top:15px; border-bottom:1px #333 dashed;}\n" +
                ".bordervLine{ border-left:1px #333 solid; height:100%; width:1px; height:250px;}\n" +
                "\n" +
                ".invoiceTable{ width:100%; font-size:14px;}\n" +
                ".invoiceTable > tbody > tr > td{ padding:7px 10px; text-align:left; }\n" +
                ".invoiceTable > tbody > tr > th{ padding:10px 10px; text-align:left; }\n" +
                "\n" +
                ".consultantValue .valueInput{ border-bottom:none; font-size:20px; text-align:center;}\n" +
                "</style>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"payslipOuter\">\n" +
                "<table width=\"100%\" style=\"padding:5px;\">\n" +
                "\t<tr>\n" +
                "\t\t<td colspan=\"2\"><div class=\"consultantOuter\">\n" +
                "        <div class=\"consultantValue\"><input type=\"text\" class=\"valueInput\" value=\"" + consultantDoctorName + "\"></div></div>\n" +
                "        \n" +
                "        <div class=\"departmentOuter\">\n" +
                "        <div class=\"department\"><input type=\"text\" class=\"valueInput\" value=\"" + departmentName + "\"></div></div>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "  <tr>\n" +
                "    <td colspan=\"2\"><h4>" + subTanantName + "</h4>\n" +
                "\t<p> " + subTanantAddress + "</br>\n" +
                "    " + subTanantContact + "</br></p>\n" +
                "  <h3 style=\"margin-bottom:20px;\"> OPD Consultation Receipt</h3>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    \n" +
                " <tr>\n" +
                "    \t<td colspan=\"2\"><div class=\"billnoOuter\"><div class=\"billno\">Bill No.</div>\n" +
                "   \t    <div class=\"billnoValue\"><input type=\"text\" class=\"valueInput\" value = \"" + data[1] + "\"></div></div> <div class=\"dateOuter\"><div class=\"date\">Date</div> <div class=\"dateValue\"><input type=\"text\" class=\"valueInput\" value=\"" + completeDate + " | " + time + "\"></div></div></td>\n" +
                "    </tr>\n" +
                "    \n" +
                "      <tr>\n" +
                "    \t<td colspan=\"2\"><div class=\"patientnameOuter\"><div class=\"patientname\">Patient Name</div>\n" +
                "   \t    <div class=\"patientnameValue\"><input type=\"text\" class=\"valueInput\" value=\"" + tvPatientName.getText().toString() + "\"></div></div> \n" +
                "        \n" +
                "        <div class=\"hospitalIdOuter\"><div class=\"hospitalId\">Hospital ID</div>\n" +
                "        <div class=\"hospitalIdValue\"><input type=\"text\" class=\"valueInput\" value=\"" + tvHospitalId.getText().toString() + "\"></div></div>\n" +
                "        \n" +
                "        <div class=\"cashcardOuter\"><div class=\"cashcard\">E-Wallet ID</div> <div class=\"cashcardValue\"><input type=\"text\" class=\"valueInput\" value = \"" + data[0] + "\"></div></div></td>\n" +
                "    </tr>\n" +
                "   \n" +
                "</table>\n" +
                "<div style=\"border-top:1px #333 solid; height:1px;\"></div>\n" +
                "<table width=\"100%\" class=\"invoiceTable\">    \n" +
                "  <tr style=\"border-bottom:1px #333 solid\">\n" +
                "  \t<th>Description</th>\n" +
                "    <th>&nbsp;</th>\n" +
                "    <th style=\"width:120px;\">Amount</th>\n" +
                "  </tr>\n" +
                " </table>\n" +
                " <div style=\"border-top:1px #333 solid; height:1px;\"></div>\n" +
                " <table width=\"100%\" class=\"invoiceTable\">\n" +
                "  <tr>\n" +
                "  \t<td>" + feeGenerateStatus + "</td>\n" +
                "     <td rowspan=\"3\" style=\"width:1px; border-left:1px #333 solid; padding:7px 0px;\">&nbsp;</td>\n" +
                "    <td style=\"width:120px;\">Rs. " + feeAmount + "</td>\n" +
                "  </tr>\n" +
                "\n" +
                "\n" +
                "    <tr>\n" +
                "    <td>&nbsp; </td>\n" +
                "    <td>&nbsp;</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td style=\"border-top:1px #333 solid; text-align:right; padding:7px 10px;\"><strong>Total Amount</strong></td>\n" +
                "    <td style=\"border-top:1px #333 solid; padding:7px 10px;\"><strong>Rs. " + feeAmount + "</strong></td>\n" +
                "  </tr>\n" +
                "   \n" +
                "    <tr>\n" +
                "    <td colspan=\"3\"><div class=\"amoutnwordsOuter\">\n" +
                "    <div class=\"amountWords\">Amount in words:</div>\n" +
                "    <div class=\"amountwordsValue\"><input type=\"text\" class=\"valueInput\" value = \"" + Helper.convert(Integer.parseInt(feeAmount)) + " Only\"></div>\n" +
                "     </div></td>\n" +
                "    </tr>\n" +
                "    \n" +
                "     \n" +
                "  </table>\n" +
                "  \n" +
                " <!-- <div style=\"border-top:1px #333 solid; height:1px;\"></div>-->\n" +
                "  \n" +
                "  <table width=\"100%\" style=\"padding:5px;\">\n" +
                "     <tr>     \n" +
                "    <td><div class=\"signatureOuter\">\n" +
                "      <div class=\"signature\">Authorized Signatory</br>\n" +
                "      </div>\n" +
                "     <!-- <div class=\"signatureValue\"><input type=\"text\" class=\"valueInput\"></div>--></div></td>\n" +
                "    </tr>\n" +
                "   \n" +
                "\n" +
                "  \n" +
                "</table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
        Log.d("htmlDocument", htmlDocument);
        webView.loadDataWithBaseURL(null, htmlDocument,
                "text/html", "UTF-8", null);

        myWebView = webView;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }
    /*public void checkHospitalPatientAPI(final String hospitalId) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.checkHospitalPat(user_role_id, subTanentId, hospitalId, new Callback<CheckHospitalPatModel>() {
            @Override
            public void success(CheckHospitalPatModel checkHospitalPatModel, Response response) {
                if (checkHospitalPatModel.getStatus() == CheckHospitalPatient.kCheckHospitalPatientSuccess.getID()) {
                    Toast.makeText(BillPaymentActivity.this, checkHospitalPatModel.getMsg().toString(), Toast.LENGTH_LONG).show();
                } else if (checkHospitalPatModel.getStatus() == CheckHospitalPatient.kCheckHospitalPatientAreadyExist.getID()) {
                    getPatientByHospitalNoApi(hospitalId);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }*/

    /**
     *
     */
    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(BillPaymentActivity.this);
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

    /*public void getPatientByHospitalNoApi(String hId) {
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
    }*/
    public void setPatientDetail(Datum datum) {
        tvHospitalId.setText(datum.getHospitalNo().toString());
        tvPatientName.setText(datum.getPatName().toString());
        tvMrno.setText(datum.getMrNo().toString());
        if (datum.getGenderId() == 1) {
            tvGender.setText("Male");
        } else if (datum.getGenderId() == 2) {
            tvGender.setText("Female");
        }
        String mobileNo = datum.getMobileNo().toString();
        tvPatientPhone.setText(mobileNo);
        String dob = getDob(datum.getDob().toString());
        String[] out = dob.split(",");
        System.out.println("Year = " + out[2]);
        System.out.println("Month = " + out[0]);
        System.out.println("Day = " + out[1]);

        int year = Integer.parseInt(out[2]);
        int month = Integer.parseInt(out[0]);
        int day = Integer.parseInt(out[1]);
        tvAge.setText(getAge(year, month, day) + "Y");

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

    public void setErrorAlert() {
        AlertDialog.Builder buider = new AlertDialog.Builder(BillPaymentActivity.this);
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

    public void getDoctorDetail() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.list_DoctorsBySubTenantId(subTanentId, new Callback<DoctorListModel[]>() {
            @Override
            public void success(DoctorListModel[] doctors, Response response) {
                doctorArray = doctors;
                /*for (int i = 0; i < doctors.length; i++) {
                    Log.d("doctor name", doctors[i].getUserName());
                }*/
                /*doctorSpinnerAdapter = new DoctorSpinnerAdapter(BillPaymentActivity.this,
                        android.R.layout.simple_spinner_item,
                        doctorArray);
                doctor_spinner.setAdapter(doctorSpinnerAdapter);*/
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void getLastBillDetail() {
        if (!tvMrno.getText().toString().equals("0000")) {
            showLoadingDialog();
            mCuraApplication.getInstance().mCuraEndPoint.lastBillDetail(selectedUserRoleId, tvMrno.getText().toString(), new Callback<LastBillDetailModel>() {
                @Override
                public void success(LastBillDetailModel lastBillDetailModel, Response response) {
                    lastBillDetail = lastBillDetailModel;
                    if (lastBillDetailModel.getAmount() != 0) {
                        setPatLastBillDetail(lastBillDetailModel);
                        tbl_pat_history.setVisibility(View.VISIBLE);
                    } else {
                        tbl_pat_history.setVisibility(View.GONE);
                    }

                    dismissLoadingDialog();
                }

                @Override
                public void failure(RetrofitError error) {
                    dismissLoadingDialog();
                }
            });
        } else {
            Toast.makeText(BillPaymentActivity.this, "Please Select Patient", Toast.LENGTH_LONG).show();
        }

    }

    private void setPatLastBillDetail(LastBillDetailModel model) {
        String date_time = null;
        if (model.getBillId() != null) {
            tv_BillNo.setText(model.getBillId() + "");
        } else {
            tbl_pat_history.setVisibility(View.GONE);
        }
        if (model.getPymtNature() != null) {
            tv_nature.setText(model.getPymtNature());
        } else {
            tbl_pat_history.setVisibility(View.GONE);
        }
        if (model.getAmount() != 0) {
            tv_amount.setText("Rs. " + model.getAmount());
        } else {
            tbl_pat_history.setVisibility(View.GONE);
        }
        if (model.getDocName() != null) {
            tv_DoctorName.setText(model.getDocName() + "");
        } else {
            tbl_pat_history.setVisibility(View.GONE);
        }
        if (model.getBillDate() != null) {
            date_time = model.getBillDate();
            String splitDateTime[] = date_time.split(" ");
            tv_date.setText(splitDateTime[0]);
            tv_time.setText(splitDateTime[1]);
        } else {
            tbl_pat_history.setVisibility(View.GONE);
        }
    }

    public void setAppointmentNature() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getNatureByUserRole(selectedUserRoleId, subTanentId, new Callback<GetNatureByUserRoleModel[]>() {
            @Override
            public void success(GetNatureByUserRoleModel[] appointmentNatures, Response response) {
                appointmentNaturesArray = appointmentNatures;
                setNatureToList();
                feeAmount = "0";
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void setNatureToList() {
        getNatureByUserRoleIdAdapter = new GetNatureByUserRoleIdAdapter(BillPaymentActivity.this,
                android.R.layout.simple_spinner_item,
                appointmentNaturesArray);
        nature.setAdapter(getNatureByUserRoleIdAdapter);
    }

    private void getFeeFromAPI(int selectedUserRoleId, int appNatureId) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.feeFetch(selectedUserRoleId, appNatureId, subTanentId, new Callback<FeeFetch>() {
            @Override
            public void success(FeeFetch feeFetch, Response response) {
                if (feeFetch.getUserRoleId() != null) {
                    feeAmount = feeFetch.getFeeAmount().toString();
                    doctorFee.setText(feeFetch.getFeeAmount().toString());
                    /*if (serviceType == 1) {
                        doctorFee.setText(feeFetch.getFeeAmount().toString());
                    } else if (serviceType == 2) {
                        doctorFee.setText(feeFetch.getFeeAmount() + 100 + "");
                    } else if (serviceType == 3) {
                        doctorFee.setText(feeFetch.getFeeAmount() + 20 + "");
                    }*/

                } else {
                    doctorFee.setText("0");
                    Toast.makeText(BillPaymentActivity.this, "Sorry Detail Not Available for this User", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            try {
                HttpURLConnection.setFollowRedirects(false);
                HttpURLConnection con = (HttpURLConnection) new URL(uri[0]).openConnection();
                //con.setRequestMethod("HEAD");

                con.setConnectTimeout(5000); //set timeout to 5 seconds

                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d("response_result", (con.getResponseCode() == HttpURLConnection.HTTP_OK) + "");
                }
            } catch (java.net.SocketTimeoutException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.d("result",result);
            Toast.makeText(BillPaymentActivity.this, result, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BillPaymentActivity.this, "Record not Found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(BillPaymentActivity.this, "Record not Found" + error.getMessage() + "", Toast.LENGTH_LONG).show();
                dismissLoadingDialog();

            }
        });
    }*/
    private void getPatientSearchDetail(String searchKey) {
        patientSearchModelsArray = new PatientSearchModel[]{};
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.searchPatient_v1(1, 20, user_role_id, "1,3,4", searchKey, subTanentId, new Callback<SearchPatientModel>() {
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
        mCuraApplication.getInstance().mCuraEndPoint.searchPatient_v1(fIndex, 20, user_role_id, "1,3,4", searchKey, subTanentId, new Callback<SearchPatientModel>() {
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
                    Toast.makeText(BillPaymentActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
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
        String names[] = new String[patientSearchModelsArray.length];
        for (int i = 0; i < patientSearchModelsArray.length; i++) {
            names[i] = patientSearchModelsArray[i].getPatname().toString().trim();
        }
        alertDialog = new AlertDialog.Builder(BillPaymentActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.search_patient_row_layout, null);
        alertDialog.setView(convertView);
        //alertDialog.setTitle("List");

        final ListView lv = (ListView) convertView.findViewById(R.id.search_patient_listview);

        searchPatientAdapter = new CheckInAdapter_v1(BillPaymentActivity.this,
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
                        getPatientSearchDetail_v2(firstIndex, query);
                        firstIndex = firstIndex + 1;
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}


