package com.mcura.jaideep.queuemanagement.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mcura.jaideep.queuemanagement.Adapter.CheckInAdapter_v1;
import com.mcura.jaideep.queuemanagement.Adapter.DiscountAmountAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.DoctorScheduleAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.DoctorSpinnerAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.GetNatureByUserRoleIdAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.LabOrderDetailExpandableAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.LabOrderTxnDetailExpAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.LabSavedDataAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.LabSubtanentAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.PharmacyOrderTxnDetailExpAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.PharmacySavedDataAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.SavedLabOrderAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.ServiceListAdapter;
import com.mcura.jaideep.queuemanagement.BuildConfig;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.AppointmentModel;
import com.mcura.jaideep.queuemanagement.Model.AvialbaleTokenListbydate;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.DiscountModel;
import com.mcura.jaideep.queuemanagement.Model.DoctorListModel;
import com.mcura.jaideep.queuemanagement.Model.FeeFetch;
import com.mcura.jaideep.queuemanagement.Model.GenerateTokenResultModel;
import com.mcura.jaideep.queuemanagement.Model.GetLabIdModel;
import com.mcura.jaideep.queuemanagement.Model.GetLabTransactionsByMrnoSubtenantModel;
import com.mcura.jaideep.queuemanagement.Model.GetNatureByUserRoleModel;
import com.mcura.jaideep.queuemanagement.Model.GetPharmacyTransactionsByMrnoSubtenantModel;
import com.mcura.jaideep.queuemanagement.Model.GetServiceListModel;
import com.mcura.jaideep.queuemanagement.Model.GetappointmentModel1;
import com.mcura.jaideep.queuemanagement.Model.LabOrderDetailModel;
import com.mcura.jaideep.queuemanagement.Model.LabOrderTransactionDetail.LabOrderTransactionModel;
import com.mcura.jaideep.queuemanagement.Model.LabOrderTransactionDetail.LabTxnDetailModel;
import com.mcura.jaideep.queuemanagement.Model.LabPharmacyPostResponseModel;
import com.mcura.jaideep.queuemanagement.Model.LabTransactionDatum;
import com.mcura.jaideep.queuemanagement.Model.LastBillDetailModel;
import com.mcura.jaideep.queuemanagement.Model.LocalBillModel;
import com.mcura.jaideep.queuemanagement.Model.MainModel;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.PharmacyOrderTransactionDetail.PharmacyOrderTxnWithDetailByOrdIdModel;
import com.mcura.jaideep.queuemanagement.Model.PharmacyTransactionDatum;
import com.mcura.jaideep.queuemanagement.Model.PostActivityTrackerModel.PostActivityTrackerModel;
import com.mcura.jaideep.queuemanagement.Model.PostPaymentModel;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.SqliteHelper.SqlLiteDbHelper;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.mcura.jaideep.queuemanagement.helper.Helper;
import com.mcura.jaideep.queuemanagement.helper.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CentralizedBillingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, TextWatcher, RadioGroup.OnCheckedChangeListener, SearchView.OnQueryTextListener {

    private static int LAB_STATUS;
    private static final int SELF_LAB = 1;
    private static final int PRESC_LAB = 2;

    boolean status[] = {false, false, false};
    String orderBy[] = {"papa roa", "papa roa", "papa roa"};
    String orderId[] = {"12345", "12345", "12345"};
    String orderAmount[] = {"345", "123", "145"};
    String orderDate[] = {"17 Aug 2017", "16 Aug 2017", "15 Aug 2017"};


    private LinearLayout ll_doc_nature, ll_pharmacy_detail_layout;
    private RelativeLayout ib_search_icon;
    private EditText et_search_key;
    private int firstIndex;
    private String query;
    private SearchPatientModel patientSearchModel;
    private ArrayList<Datum> dataList;
    private CheckInAdapter_v1 searchPatientAdapter;
    private AlertDialog.Builder alertDialog;
    private AlertDialog ad;
    private boolean flag_loading;
    private ProgressDialog progressDialog;
    private MCuraApplication mCuraApplication;
    private SharedPreferences mSharedPreference;
    private int user_role_id;
    private int subTanentId;
    private Datum datumModel;
    private TextView tvHospitalId, tvMrno, tvAge, tvPatientName, tvPatientPhone, tvGender;
    private String ageS;
    private ImageView hospital_logo;
    private ImageView bill_summary;
    private ImageView iv_local_bill;
    private ImageView add_patient;
    private int selectedUserRoleId;
    private Spinner serviceListSpinner;
    private ServiceListAdapter serviceListAdapter;
    private TextView appointment;
    private TextView queue_mgmt;
    private TextView tv_fillcard;
    private SqlLiteDbHelper dbHelper;
    private EditText et_refund_amount;
    private int appNatureId;
    private RadioGroup payment_mode;
    private String paymentMode = "1";
    private String subTanantName;
    private String subTanantAddress;
    private String subTanantContact;
    private GetServiceListModel getServiceListModel;
    private Integer serviceType;
    private TextView doctor_spinner;
    private Spinner nature;
    private String consultantDoctorName;
    private GetNatureByUserRoleModel[] appointmentNaturesArray;
    private String feeAmount;
    private GetNatureByUserRoleIdAdapter getNatureByUserRoleIdAdapter;
    private ListView dcotorListView;
    private DoctorScheduleAdapter doctorScheduleAdapter;
    private AvialbaleTokenListbydate doctorListModel;
    private AvialbaleTokenListbydate[] doctorArray;
    private AlertDialog dialog;
    private String departmentName;
    private TableLayout tbl_pat_history;
    private TextView tv_no_record_status;
    private LastBillDetailModel lastBillDetail;
    TextView tv_amount, tv_nature, tv_time, tv_date, tv_DoctorName, tv_BillNo;
    private GetNatureByUserRoleModel getNatureByUserRoleModel;
    private String appNatureName;
    private TextView doctorFee;
    private RelativeLayout rl_et_refund_amount;
    private TextView activity_bill_payment_paynow, activity_bill_payment_add_discount;
    private int frontOfficeUserRoleId;
    private String printHeading = "";
    private String feeGenerateStatus;
    private String time = "";
    private int year, month, date;
    private String completeDate;
    private WebView myWebView;
    private String otp;
    int scheduleId;
    private String hId;
    private ListView pharmacy_saved_data_listview;
    private PharmacySavedDataAdapter pharmacySavedDataAdapter;
    private GetPharmacyTransactionsByMrnoSubtenantModel getPharmacyTransactionsByMrnoSubtenantData;
    private ArrayList<PharmacyTransactionDatum> pharmacyTransactionDataList;
    private ListView lab_saved_data_listview;
    private LinearLayout ll_lab_detail_layout;
    private GetLabTransactionsByMrnoSubtenantModel getLabTransactionsByMrnoSubtenantData;
    private ArrayList<LabTransactionDatum> labTransactionDataList;
    private LabSavedDataAdapter labSavedDataAdapter;
    private PharmacyTransactionDatum pharmacyTransactionDatum;
    private LabTransactionDatum labTransactionDatum;
    private String scheduleDate;
    private String currDate;
    private int appId = 0;
    private int avlStatusId;
    private String appointTime;
    private String[] data;
    private int chartGenerateStatus;
    private int serviceRoleId;
    private String patTokenNo;
    private ListView save_lab_order_listview;
    private SavedLabOrderAdapter savedLabOrderAdapter;
    private ArrayList<LabOrderDetailModel> labOrderDetailModels;
    private JsonArray labObjectKeyArray;
    private int labId;
    private String laborderId;
    private ArrayList<LabOrderDetailModel> labOrderDetailModelArrayList;
    private ImageView iv_transaction;
    private GetServiceListModel[] getServiceListModelsArray;
    private ListView listviewDiscountAmount;
    ArrayList<DiscountModel> discountModelArrayList;
    private DiscountAmountAdapter discountAmountAdapter;
    private TextView tvPayableAmount, tvDiscountedAmount;
    private double percentage = 0.0;
    private double orderedAmount = 0;
    private PharmacyOrderTxnWithDetailByOrdIdModel pharmacyOrderTxnWithDetailModel;
    private ArrayList<LabOrderTransactionModel> labOrderTransactionModelArrayList;
    private List<LabTxnDetailModel> labTxnDetailModel;
    private android.support.v7.app.AlertDialog successAlertDialog;
    private android.support.v7.app.AlertDialog errorAlertDialog;
    private int actTransactId;
    private String buildVersionName;
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private NfcAdapter mNfcAdapter;
    private int nfcMrno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centralized_billing);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        discountModelArrayList = new ArrayList<>();


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
        handleIntent(getIntent());

        dbHelper = new SqlLiteDbHelper(this);
        dbHelper.openDataBase();
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        completeDate = date + "/" + month + "/" + year;
        scheduleDate = year + "-" + month + "-" + date;
        currDate = month + "/" + date + "/" + year;
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        user_role_id = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        selectedUserRoleId = user_role_id;
        subTanentId = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        departmentName = mSharedPreference.getString(Constant.DEPT_NAME, "");
        frontOfficeUserRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
        serviceRoleId = mSharedPreference.getInt(Constant.SERVICE_ROLE_ID, 0);
        dbHelper = new SqlLiteDbHelper(this);
        dbHelper.openDataBase();
        initView();
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
            //Log.d("Mainactivity", result);
            //Toast.makeText(VisitActivity.this,result+"",Toast.LENGTH_LONG).show();
            if (result != null) {
                try {
                    JSONObject obj = new JSONObject(result);
                    nfcMrno = obj.getInt("mr_no");
                    int nfcsubTanentId = obj.getInt("sub_tenant_id");
                    String hospital_id = obj.getString("hospital_id");
                    if (subTanentId == nfcsubTanentId) {
                        new GetMainData().execute("");
                    } else {
                        Toast.makeText(CentralizedBillingActivity.this, "This Card is not for this Hospital", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
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
        mCuraApplication.getInstance().mCuraEndPoint.getPatient(nfcMrno, user_role_id, subTanentId, new Callback<MainModel>() {
            @Override
            public void success(MainModel mainModel, Response response) {
                if(mainModel!=null){
                    tvMrno.setText(nfcMrno+"");
                    et_search_key.setText(mainModel.getPatname());
                    tvHospitalId.setText(mainModel.getHospitalNo().toString());
                    tvPatientName.setText(mainModel.getPatname().toString());
                    //tvMrno.setText(mainModel.getMRNO().toString());
                    if (mainModel.getGenderId() == 1) {
                        tvGender.setText("Male");
                    } else if (mainModel.getGenderId() == 2) {
                        tvGender.setText("Female");
                    }
                    String mobileNo = mainModel.getPatientContactDetails().getMobile().toString();
                    tvPatientPhone.setText(mobileNo);
                    String dob = getDob(mainModel.getDob().toString());
                    String[] out = dob.split(",");
                    System.out.println("Year = " + out[2]);
                    System.out.println("Month = " + out[0]);
                    System.out.println("Day = " + out[1]);

                    int year = Integer.parseInt(out[2]);
                    int month = Integer.parseInt(out[0]);
                    int day = Integer.parseInt(out[1]);
                    tvAge.setText(getAge(year, month, day) + "Y");
                }else{
                    Toast.makeText(CentralizedBillingActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CentralizedBillingActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
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
    private void initView() {
        //set Hospital detail for bill
        //setSubTanentDetail();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        actTransactId = EnumType.ActTransactMasterEnum.Booking_APT.getActTransactMasterTypeId();
        String subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        consultantDoctorName = mSharedPreference.getString(Constant.UNAME_KEY, "Default");
        scheduleId = mSharedPreference.getInt(Constant.SCHEDULE_ID, 0);
        buildVersionName = mSharedPreference.getString(Constant.BUILD_VERSION_NAME,"");
        subTanantName = mSharedPreference.getString(Constant.SUBTANENT_NAME, "");
        subTanantAddress = mSharedPreference.getString(Constant.SUBTANENT_ADD, "");
        subTanantContact = mSharedPreference.getString(Constant.SUBTANENT_CONTACT, "");
        doctorFee = (TextView) findViewById(R.id.doctor_fee);
        tvPayableAmount = (TextView) findViewById(R.id.tvPayableAmount);
        tvDiscountedAmount = (TextView) findViewById(R.id.tvDiscountedAmount);
        listviewDiscountAmount = (ListView) findViewById(R.id.listviewDiscountAmount);
        discountAmountAdapter = new DiscountAmountAdapter(CentralizedBillingActivity.this, discountModelArrayList, tvPayableAmount, tvDiscountedAmount, doctorFee, listviewDiscountAmount);
        listviewDiscountAmount.setAdapter(discountAmountAdapter);
        Utility.setListViewHeightBasedOnItems(listviewDiscountAmount);
        hospital_logo = (ImageView) mToolbar.findViewById(R.id.hospital_logo);
        Picasso.with(CentralizedBillingActivity.this).load(subtanentImagePath).into(hospital_logo);
        et_refund_amount = (EditText) findViewById(R.id.et_refund_amount);
        rl_et_refund_amount = (RelativeLayout) findViewById(R.id.rl_et_refund_amount);
        tv_amount = (TextView) findViewById(R.id.tvamount);
        tv_time = (TextView) findViewById(R.id.tvtime);
        tv_nature = (TextView) findViewById(R.id.tvnature);
        tv_date = (TextView) findViewById(R.id.tvdate);
        tv_DoctorName = (TextView) findViewById(R.id.tvDoctorName);
        tv_BillNo = (TextView) findViewById(R.id.tvBillNo);
        iv_transaction = (ImageView) findViewById(R.id.iv_transaction);
        pharmacy_saved_data_listview = (ListView) findViewById(R.id.pharmacy_saved_data_listview);
        lab_saved_data_listview = (ListView) findViewById(R.id.lab_saved_data_listview);
        save_lab_order_listview = (ListView) findViewById(R.id.save_lab_order_listview);
        ib_search_icon = (RelativeLayout) findViewById(R.id.ib_search_icon);
        et_search_key = (EditText) findViewById(R.id.et_search_key);
        tvHospitalId = (TextView) findViewById(R.id.hospital_id);
        tvMrno = (TextView) findViewById(R.id.bill_payment_activity_mrno);
        tvAge = (TextView) findViewById(R.id.bill_payment_activity_patient_age);
        tvPatientName = (TextView) findViewById(R.id.bill_payment_activity_patient_name);
        tvPatientPhone = (TextView) findViewById(R.id.bill_payment_activity_phone);
        tvGender = (TextView) findViewById(R.id.bill_payment_activity_patient_gender);
        bill_summary = (ImageView) mToolbar.findViewById(R.id.bill_summary);
        iv_local_bill = (ImageView) findViewById(R.id.iv_local_bill);
        add_patient = (ImageView) mToolbar.findViewById(R.id.add_patient);
        if(subTanentId==576 || subTanentId==587){
            add_patient.setVisibility(View.GONE);
        }
        serviceListSpinner = (Spinner) findViewById(R.id.service_type_spinner);
        appointment = (TextView) findViewById(R.id.appointment);
        queue_mgmt = (TextView) findViewById(R.id.queue_mgmt);
        tv_fillcard = (TextView) findViewById(R.id.tv_fillcard);
        payment_mode = (RadioGroup) findViewById(R.id.payment_mode);
        doctor_spinner = (TextView) findViewById(R.id.doctor_spinner);
        tbl_pat_history = (TableLayout) findViewById(R.id.tbl_pat_history);
        tv_no_record_status = (TextView) findViewById(R.id.tv_no_record_status);
        ll_doc_nature = (LinearLayout) findViewById(R.id.ll_doc_nature);
        ll_pharmacy_detail_layout = (LinearLayout) findViewById(R.id.ll_pharmacy_detail_layout);
        ll_lab_detail_layout = (LinearLayout) findViewById(R.id.ll_lab_detail_layout);
        activity_bill_payment_paynow = (TextView) findViewById(R.id.activity_bill_payment_paynow);
        activity_bill_payment_add_discount = (TextView) findViewById(R.id.activity_bill_payment_add_discount);
        doctor_spinner.setText(consultantDoctorName);
        nature = (Spinner) findViewById(R.id.nature);

        setAppointmentNature();
        nature.setSelection(0);
        payment_mode.setOnCheckedChangeListener(this);
        serviceListSpinner.setOnItemSelectedListener(this);
        nature.setOnItemSelectedListener(this);
        activity_bill_payment_paynow.setOnClickListener(this);
        activity_bill_payment_add_discount.setOnClickListener(this);
        ib_search_icon.setOnClickListener(this);
        iv_local_bill.setOnClickListener(this);
        bill_summary.setOnClickListener(this);
        add_patient.setOnClickListener(this);
        appointment.setOnClickListener(this);
        queue_mgmt.setOnClickListener(this);
        tv_fillcard.setOnClickListener(this);
        et_refund_amount.addTextChangedListener(this);
        doctor_spinner.setOnClickListener(this);
        pharmacy_saved_data_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RadioButton rb = (RadioButton) view.findViewById(R.id.pharmacy_saved_data_rd_select_data);
                pharmacyTransactionDatum = pharmacySavedDataAdapter.getItem(position);
                if (!rb.isChecked()) //OFF->ON
                {
                    for (int i = 0; i < pharmacyTransactionDataList.size(); i++) {
                        pharmacyTransactionDataList.get(i).setStatus(false);
                    }
                    pharmacyTransactionDataList.get(position).setStatus(true);
                    orderedAmount = 0;
                    for (int i = 0; i < pharmacyTransactionDataList.get(position).getTransactions().size(); i++) {
                        orderedAmount += pharmacyTransactionDataList.get(position).getTransactions().get(i).getOrderedAmount();
                    }
                    /*doctorFee.setText(orderedAmount + "");*/
                    pharmacySavedDataAdapter.notifyDataSetChanged();
                }
                fetchPharmacyTxnDetailApi(pharmacyTransactionDatum.getPrescriptionId());
            }
        });
        lab_saved_data_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RadioButton rb = (RadioButton) view.findViewById(R.id.pharmacy_saved_data_rd_select_data);
                labTransactionDatum = labSavedDataAdapter.getItem(position);
                if (!rb.isChecked()) //OFF->ON
                {
                    for (int i = 0; i < labTransactionDataList.size(); i++) {
                        labTransactionDataList.get(i).setStatus(false);
                    }
                    labTransactionDataList.get(position).setStatus(true);

                    orderedAmount = 0;
                    for (int i = 0; i < labTransactionDataList.get(position).getTransactions().size(); i++) {
                        orderedAmount += labTransactionDataList.get(position).getTransactions().get(i).getOrderedAmount();
                    }
                    //doctorFee.setText(orderedAmount + "");

                    labSavedDataAdapter.notifyDataSetChanged();
                }
                new LabOrdTxnDetail(labTransactionDatum.getOrderId()).execute();
            }
        });
        getServiceListFromApi();
        iv_transaction.setOnClickListener(this);
    }

    private class LabOrdTxnDetail extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;
        int labOrderId;

        public LabOrdTxnDetail(int labOrderId) {
            this.labOrderId = labOrderId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... args) {

            StringBuilder result = new StringBuilder();

            try {
                String serviceUrl = BuildConfig.API_URL + "/GetLabOrderTxnWithDetailByOrdId?UserRoleId=" + user_role_id + "&orderId=" + labOrderId;
                Log.d("serviceUrl", serviceUrl);
                URL url = new URL(serviceUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("authkeytoken", Helper.getAESCryptEncodeString());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                dismissLoadingDialog();
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoadingDialog();

            labOrderTransactionModelArrayList = new ArrayList<>();
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonDataArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonDataArray.length(); i++) {
                        JSONObject jsonTxnData = jsonDataArray.getJSONObject(i);
                        LabOrderTransactionModel labOrderTransactionModel = new LabOrderTransactionModel();
                        labOrderTransactionModel.setOrderedDate(jsonTxnData.getString("orderedDate"));
                        labOrderTransactionModel.setOrdTxnId(jsonTxnData.getInt("ordTxnId"));
                        labOrderTransactionModel.setOrdStatus(jsonTxnData.getInt("ordStatus"));
                        labOrderTransactionModel.setOrderedAmount(jsonTxnData.getDouble("orderedAmount"));
                        labOrderTransactionModel.setBillId(jsonTxnData.getInt("billId"));
                        labOrderTransactionModel.setBillDate(jsonTxnData.getString("billDate"));
                        labOrderTransactionModel.setPymtNature(jsonTxnData.getString("pymtNature"));
                        labOrderTransactionModel.sethISBillNo(jsonTxnData.getString("HISBillNo"));
                        labOrderTransactionModel.setAppNatureId(jsonTxnData.getInt("appNatureId"));
                        labOrderTransactionModel.setPrescriptionId(jsonTxnData.getInt("prescriptionId"));
                        labOrderTransactionModel.setOrderId(jsonTxnData.getInt("orderId"));
                        labOrderTransactionModel.setAppotId(jsonTxnData.getInt("appotId"));
                        JSONArray jsonArrayTxnDetail = jsonTxnData.getJSONArray("labtxnDetails");

                        ArrayList<LabTxnDetailModel> labTxnDetailModelArrayList = new ArrayList<>();

                        JSONObject jsonObjDetail = jsonArrayTxnDetail.getJSONObject(0);
                        JSONArray jsonArrayPKG = jsonObjDetail.getJSONArray("packageDetails");
                        JSONArray jsonArrayGRP = jsonObjDetail.getJSONArray("grpDetails");
                        JSONArray jsonArrayTEST = jsonObjDetail.getJSONArray("testDetails");

                        for (int pkg = 0; pkg < jsonArrayPKG.length(); pkg++) {
                            LabTxnDetailModel labTxnDetailModel = new LabTxnDetailModel();
                            JSONObject jsonObjectPKG = jsonArrayPKG.getJSONObject(pkg);
                            labTxnDetailModel.setTxnId(jsonObjectPKG.getInt("txnId"));
                            labTxnDetailModel.setInvName(jsonObjectPKG.getString("invName"));
                            labTxnDetailModel.setInvAmount(jsonObjectPKG.getDouble("invAmount"));
                            labTxnDetailModelArrayList.add(labTxnDetailModel);
                        }
                        for (int grp = 0; grp < jsonArrayGRP.length(); grp++) {
                            LabTxnDetailModel labTxnDetailModel = new LabTxnDetailModel();
                            JSONObject jsonObjectGrp = jsonArrayGRP.getJSONObject(grp);
                            labTxnDetailModel.setTxnId(jsonObjectGrp.getInt("txnId"));
                            labTxnDetailModel.setInvName(jsonObjectGrp.getString("invName"));
                            labTxnDetailModel.setInvAmount(jsonObjectGrp.getDouble("invAmount"));
                            labTxnDetailModelArrayList.add(labTxnDetailModel);
                        }
                        for (int tst = 0; tst < jsonArrayTEST.length(); tst++) {
                            LabTxnDetailModel labTxnDetailModel = new LabTxnDetailModel();
                            JSONObject jsonObjectTst = jsonArrayTEST.getJSONObject(tst);
                            labTxnDetailModel.setTxnId(jsonObjectTst.getInt("txnId"));
                            labTxnDetailModel.setInvName(jsonObjectTst.getString("invName"));
                            labTxnDetailModel.setInvAmount(jsonObjectTst.getDouble("invAmount"));
                            labTxnDetailModelArrayList.add(labTxnDetailModel);
                        }
                        labOrderTransactionModel.setLabTxnDetailModelList(labTxnDetailModelArrayList);
                        labOrderTransactionModelArrayList.add(labOrderTransactionModel);
                    }
                    showLabTransactionDetail(labOrderTransactionModelArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showLabTransactionDetail(final ArrayList<LabOrderTransactionModel> labOrderTransactionModelArrayList) {
        alertDialog = new AlertDialog.Builder(CentralizedBillingActivity.this);
        alertDialog.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.pharmacy_order_transaction_detail_dialog, null);
        alertDialog.setView(convertView);
        TextView tvOrderId = (TextView) convertView.findViewById(R.id.tvOrderId);
        tvOrderId.setText(labTransactionDatum.getOrderId().toString());
        TextView tvOrdAmount = (TextView) convertView.findViewById(R.id.tvOrdAmount);
        tvOrdAmount.setText(orderedAmount+"");
        TextView btnSubmit = (TextView) convertView.findViewById(R.id.btnSubmit);
        TextView btnCancel = (TextView) convertView.findViewById(R.id.btnCancel);
        final ExpandableListView expListviewPharmacyTxnDetail = (ExpandableListView) convertView.findViewById(R.id.expListviewPharmacyTxnDetail);
        final int[] lastExpandedPosition = {-1};
        final LabOrderTxnDetailExpAdapter labOrderTxnDetailExpAdapter = new LabOrderTxnDetailExpAdapter(CentralizedBillingActivity.this,labOrderTransactionModelArrayList,doctorFee) {
            @Override
            public void OnIndicatorClick(boolean isExpanded, int position) {
                if (lastExpandedPosition[0] != -1 && position != lastExpandedPosition[0]) {
                    expListviewPharmacyTxnDetail.collapseGroup(lastExpandedPosition[0]);
                }
                lastExpandedPosition[0] = position;
                if (isExpanded) {
                    expListviewPharmacyTxnDetail.collapseGroup(position);
                } else {
                    expListviewPharmacyTxnDetail.expandGroup(position);
                }
            }

            @Override
            public void OnTextClick() {
                //Do whatever you want to do on text click
            }
        };
        expListviewPharmacyTxnDetail.setAdapter(labOrderTxnDetailExpAdapter);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<labOrderTransactionModelArrayList.size();i++){
                    if(labOrderTransactionModelArrayList.get(i).isStatus()){
                        labTxnDetailModel = labOrderTransactionModelArrayList.get(i).getLabTxnDetailModelList();
                        orderedAmount = labOrderTransactionModelArrayList.get(i).getOrderedAmount();
                    }
                }
                doctorFee.setText(orderedAmount+"");
                if (discountModelArrayList.size() > 0) {
                    discountModelArrayList.clear();
                    discountAmountAdapter.notifyDataSetChanged();
                    Utility.setListViewHeightBasedOnItems(listviewDiscountAmount);
                    tvDiscountedAmount.setText("0");
                    tvPayableAmount.setText(orderedAmount+"");
                } else {
                    tvPayableAmount.setText(orderedAmount+"");
                }
                ad.dismiss();
            }
        });
        ad = alertDialog.show();
    }

    private void fetchPharmacyTxnDetailApi(int prescriptionId) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getPharmacyOrderTxnWithDetailByOrdId(user_role_id, prescriptionId, new Callback<PharmacyOrderTxnWithDetailByOrdIdModel>() {
            @Override
            public void success(PharmacyOrderTxnWithDetailByOrdIdModel pharmacyOrderTxnWithDetailByOrdIdModel, Response response) {
                pharmacyOrderTxnWithDetailModel = pharmacyOrderTxnWithDetailByOrdIdModel;
                if (pharmacyOrderTxnWithDetailModel.getData().size() > 0) {
                    showPharmacyTransactionDetail(pharmacyOrderTxnWithDetailModel);
                } else {
                    Toast.makeText(CentralizedBillingActivity.this, "No record found for this order.", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CentralizedBillingActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        });
    }

    private void showPharmacyTransactionDetail(final PharmacyOrderTxnWithDetailByOrdIdModel pharmacyOrderTxnWithDetailByOrdIdModel) {
        alertDialog = new AlertDialog.Builder(CentralizedBillingActivity.this);
        alertDialog.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.pharmacy_order_transaction_detail_dialog, null);
        alertDialog.setView(convertView);
        TextView tvOrderId = (TextView) convertView.findViewById(R.id.tvOrderId);
        tvOrderId.setText(pharmacyTransactionDatum.getOrderId().toString());
        TextView tvOrdAmount = (TextView) convertView.findViewById(R.id.tvOrdAmount);
        tvOrdAmount.setText(orderedAmount + "");
        TextView btnSubmit = (TextView) convertView.findViewById(R.id.btnSubmit);
        TextView btnCancel = (TextView) convertView.findViewById(R.id.btnCancel);
        final ExpandableListView expListviewPharmacyTxnDetail = (ExpandableListView) convertView.findViewById(R.id.expListviewPharmacyTxnDetail);
        final int[] lastExpandedPosition = {-1};
        final PharmacyOrderTxnDetailExpAdapter pharmacyOrderTxnDetailExpAdapter = new PharmacyOrderTxnDetailExpAdapter(CentralizedBillingActivity.this, pharmacyOrderTxnWithDetailByOrdIdModel.getData(), doctorFee) {
            @Override
            public void OnIndicatorClick(boolean isExpanded, int position) {
                if (lastExpandedPosition[0] != -1 && position != lastExpandedPosition[0]) {
                    expListviewPharmacyTxnDetail.collapseGroup(lastExpandedPosition[0]);
                }
                lastExpandedPosition[0] = position;
                if (isExpanded) {
                    expListviewPharmacyTxnDetail.collapseGroup(position);
                } else {
                    expListviewPharmacyTxnDetail.expandGroup(position);
                }
            }

            @Override
            public void OnTextClick() {
                //Do whatever you want to do on text click
            }
        };
        expListviewPharmacyTxnDetail.setAdapter(pharmacyOrderTxnDetailExpAdapter);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < pharmacyOrderTxnWithDetailByOrdIdModel.getData().size(); i++) {
                    if (pharmacyOrderTxnWithDetailByOrdIdModel.getData().get(i).isStatus()) {
                        orderedAmount = pharmacyOrderTxnWithDetailByOrdIdModel.getData().get(i).getOrderedAmount();
                    }
                }
                doctorFee.setText(orderedAmount + "");
                if (discountModelArrayList.size() > 0) {
                    discountModelArrayList.clear();
                    discountAmountAdapter.notifyDataSetChanged();
                    Utility.setListViewHeightBasedOnItems(listviewDiscountAmount);
                    tvDiscountedAmount.setText("0");
                    tvPayableAmount.setText(orderedAmount + "");
                } else {
                    tvPayableAmount.setText(orderedAmount + "");
                }
                ad.dismiss();
            }
        });


        ad = alertDialog.show();
    }

    private void setAppointmentNature() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getNatureByUserRole(selectedUserRoleId, subTanentId, new Callback<GetNatureByUserRoleModel[]>() {
            @Override
            public void success(GetNatureByUserRoleModel[] appointmentNatures, Response response) {
                if (appointmentNatures.length > 0) {
                    appointmentNaturesArray = appointmentNatures;
                    setNatureToList();
                    feeAmount = "0";
                } else {
                    appointmentNaturesArray = appointmentNatures;
                    setNatureToList();
                    feeAmount = "0";
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void setNatureToList() {
        getNatureByUserRoleIdAdapter = new GetNatureByUserRoleIdAdapter(CentralizedBillingActivity.this,
                android.R.layout.simple_spinner_item,
                appointmentNaturesArray);
        nature.setAdapter(getNatureByUserRoleIdAdapter);
    }

    private void updateHospitalId(final Datum patientSearchModel, final String hospitalno) {
        JsonObject obj = new JsonObject();
        obj.addProperty("mrno", patientSearchModel.getMrNo());
        obj.addProperty("subTenantid", subTanentId);
        obj.addProperty("hospitalno", hospitalno);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.updateHospitalNoByMrno(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatus() == true) {
                    tvHospitalId.setText(hospitalno);
                    setHospitalPayment();
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
            case R.id.doctor_spinner:
                setDoctorList();
                break;
            case R.id.tv_fillcard:
                startActivity(new Intent(CentralizedBillingActivity.this, FillCashCardActivity.class));
                finish();
                break;
            case R.id.appointment:
                startActivity(new Intent(CentralizedBillingActivity.this, CalendarActivity.class));
                finish();
                break;
            case R.id.queue_mgmt:
                startActivity(new Intent(CentralizedBillingActivity.this, QueueStatusActivity.class));
                finish();
                break;
            case R.id.ib_search_icon:
                String searchKey = et_search_key.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        et_search_key.getWindowToken(), 0);
                firstIndex = 2;
                query = searchKey;
                patientSearchModel = new SearchPatientModel();
                dataList = new ArrayList<>();
                getPatientSearchDetail(this.query);
                break;
            case R.id.iv_local_bill:
                startActivity(new Intent(CentralizedBillingActivity.this, LocalBillStatusActivity.class));
                break;
            case R.id.add_patient:
                startActivity(new Intent(CentralizedBillingActivity.this, AddNewAppointment.class).putExtra("appNatureId", 1).putExtra("updateStatus", "add_new_patient").putExtra("registerStatus", "search"));
                break;
            case R.id.bill_summary:
                startActivity(new Intent(CentralizedBillingActivity.this, BillDetailActivity.class).putExtra("userRoleId", selectedUserRoleId));
                break;
            case R.id.activity_bill_payment_add_discount:
                if (!TextUtils.isEmpty(et_search_key.getText().toString())) {
                    if (!tvMrno.getText().toString().equals("0000")) {
                        showAddDiscountDialog();
                    } else {
                        Toast.makeText(CentralizedBillingActivity.this, "Please search any patient", Toast.LENGTH_LONG).show();
                    }
                } else {
                    et_search_key.setError("Please search patient");
                }
                break;
            case R.id.activity_bill_payment_paynow:
                //Toast.makeText(CentralizedBillingActivity.this, "pay now", Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(et_search_key.getText().toString())) {
                    if (!tvMrno.getText().toString().equals("0000")) {
                        if (tvHospitalId.getText().equals("0000") || TextUtils.isEmpty(tvHospitalId.getText().toString())) {
                            alertDialog = new AlertDialog.Builder(CentralizedBillingActivity.this);
                            alertDialog.setCancelable(false);
                            LayoutInflater inflater = getLayoutInflater();
                            View convertView = inflater.inflate(R.layout.update_hospital_id_dialog, null);
                            alertDialog.setView(convertView);
                            final EditText et_update_hospital_no = (EditText) convertView.findViewById(R.id.et_update_hospital_no);
                            TextView tv_update = (TextView) convertView.findViewById(R.id.alert_update);
                            TextView tv_cancel = (TextView) convertView.findViewById(R.id.alert_cancel);

                            tv_update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    hId = et_update_hospital_no.getText().toString();
                                    if (!TextUtils.isEmpty(et_update_hospital_no.getText().toString())) {
                                        updateHospitalId(datumModel, et_update_hospital_no.getText().toString());
                                    } else {
                                        et_update_hospital_no.setError("Enter Hospital Id.");
                                    }
                                    ad.dismiss();
                                }
                            });
                            tv_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ad.dismiss();
                                }
                            });
                            ad = alertDialog.show();
                        } else {
                            setHospitalPayment();
                        }
                    } else {
                        Toast.makeText(CentralizedBillingActivity.this, "Please search any patient", Toast.LENGTH_LONG).show();
                    }
                } else {
                    et_search_key.setError("Please search patient");
                }
                break;
            case R.id.iv_transaction:

                startActivity(new Intent(CentralizedBillingActivity.this, TransactionSummary.class));
                break;
        }
    }

    private void showAddDiscountDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CentralizedBillingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        alertDialog.setCancelable(false);
        View convertView = inflater.inflate(R.layout.add_discount_dialog, null);
        final EditText etDiscountName = (EditText) convertView.findViewById(R.id.etDiscountName);
        final EditText etPercentage = (EditText) convertView.findViewById(R.id.etPercentage);
        final EditText etDiscountAmount = (EditText) convertView.findViewById(R.id.etDiscountAmount);
        final RadioButton rbPercentage = (RadioButton) convertView.findViewById(R.id.rbPercentage);
        rbPercentage.setChecked(true);
        final RadioButton rbDiscountAmount = (RadioButton) convertView.findViewById(R.id.rbDiscountAmount);
        final TextView tvDiscountAmount = (TextView) convertView.findViewById(R.id.tvDiscountAmount);
        TextView btnCancel = (TextView) convertView.findViewById(R.id.btnCancel);
        TextView btnSubmit = (TextView) convertView.findViewById(R.id.btnSubmit);

        alertDialog.setView(convertView);
        final AlertDialog dialog = alertDialog.show();
        etPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(etPercentage.getText().toString())) {
                    double totalAmount = Double.parseDouble(doctorFee.getText().toString());
                    Log.d("totalAmount", totalAmount + "");
                    double k = (double) (totalAmount * Integer.parseInt(charSequence.toString()) / 100);
                    tvDiscountAmount.setText(k + "");
                } else {
                    tvDiscountAmount.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etDiscountAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(etDiscountAmount.getText().toString())) {
                    double totalAmount = Double.parseDouble(doctorFee.getText().toString());
                    percentage = (Integer.parseInt(charSequence.toString()) / totalAmount) * 100;
                    tvDiscountAmount.setText(charSequence);
                } else
                    tvDiscountAmount.setText("0");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        rbPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbPercentage.setChecked(true);
                etPercentage.setEnabled(true);
                rbDiscountAmount.setChecked(false);
                etDiscountAmount.setEnabled(false);
                etDiscountAmount.setText("");
                tvDiscountAmount.setText("0");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    rbPercentage.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.radio_button_tint_color)));
                    rbDiscountAmount.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                }
            }
        });
        rbDiscountAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbPercentage.setChecked(false);
                etPercentage.setEnabled(false);
                rbDiscountAmount.setChecked(true);
                etDiscountAmount.setEnabled(true);
                etPercentage.setText("");
                tvDiscountAmount.setText("0");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    rbDiscountAmount.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.radio_button_tint_color)));
                    rbPercentage.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double discountedAmount = 0.0;
                double totalDiscountAmount = 0.0;
                if (!TextUtils.isEmpty(etDiscountName.getText().toString().trim())) {
                    if (tvDiscountAmount.getText().toString() != "0") {
                        if (!TextUtils.isEmpty(etPercentage.getText().toString()) || !TextUtils.isEmpty(etDiscountAmount.getText().toString())) {
                            for (int i = 0; i < discountModelArrayList.size(); i++) {
                                totalDiscountAmount += Double.parseDouble(discountModelArrayList.get(i).getDiscountedAmount());
                            }
                            totalDiscountAmount += Double.parseDouble(tvDiscountAmount.getText().toString());
                            double fee = Double.parseDouble(doctorFee.getText().toString());
                            Log.d("fee", fee + "");
                            if (fee > totalDiscountAmount) {
                                DiscountModel discountModel = new DiscountModel();
                                discountModel.setDiscountName(etDiscountName.getText().toString());
                                if (rbPercentage.isChecked()) {
                                    discountModel.setDiscountPercantage(etPercentage.getText().toString());
                                    discountModel.setDiscountAmount(tvDiscountAmount.getText().toString());
                                }
                                if (rbDiscountAmount.isChecked()) {
                                    discountModel.setDiscountAmount(etDiscountAmount.getText().toString());
                                    discountModel.setDiscountPercantage(new DecimalFormat("##.#").format(percentage) + "");
                                }
                                discountModel.setDiscountedAmount(tvDiscountAmount.getText().toString());
                                discountModelArrayList.add(discountModel);
                                discountAmountAdapter.notifyDataSetChanged();
                                Log.d("discountlistsize", discountModelArrayList.size() + "");
                                Utility.setListViewHeightBasedOnItems(listviewDiscountAmount);
                                for (int i = 0; i < discountModelArrayList.size(); i++) {
                                    discountedAmount += Double.parseDouble(discountModelArrayList.get(i).getDiscountedAmount());
                                }
                                tvDiscountedAmount.setText(discountedAmount + "");
                                tvPayableAmount.setText((Double.parseDouble(doctorFee.getText().toString()) - discountedAmount) + "");
                                dialog.dismiss();
                            } else {
                                Toast.makeText(CentralizedBillingActivity.this, "Discount amount can not be greater than Base amount.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(CentralizedBillingActivity.this, "Please enter discount percentage or amount", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(CentralizedBillingActivity.this, "Please enter discount percentage or amount", Toast.LENGTH_LONG).show();
                    }
                } else {
                    etDiscountName.setError("Enter discount name");
                }
            }
        });
    }

    private void setHospitalPayment() {
        if (subTanentId == 243) {
            if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mDoctorFee528.getID()) {
                printHeading = "OPD Consultation Receipt";
                Log.d("printHeading", printHeading);
                appointmentBookedOrNotByMrnoGetAppId();
            } else if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mPharmacyBilling528.getID()) {
                printHeading = "Pharmacy Receipt";
                feeGenerateStatus = "Pharmacy Fee";
                feeAmount = doctorFee.getText().toString();
                callPaymentAPiforPharmacyKims();
            } else if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mLabBilling528.getID()) {
                printHeading = "Lab Receipt";
                feeGenerateStatus = "Lab Fee";
                feeAmount = doctorFee.getText().toString();
                callPaymentAPiforLabKims();
            }
        } else if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547 || subTanentId == 557) {
            if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mDoctorFee528.getID()) {
                printHeading = "OPD Consultation Receipt";
                Log.d("printHeading", printHeading);
                appointmentBookedOrNotByMrnoGetAppId();
            } else if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mPharmacyBilling528.getID()) {
                printHeading = "Pharmacy Receipt";
                feeGenerateStatus = "Pharmacy Fee";
                feeAmount = doctorFee.getText().toString();
                callPaymentAPiforPharmacyKims();
            } else if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mLabBilling528.getID()) {
                printHeading = "Lab Receipt";
                feeGenerateStatus = "Lab Fee";
                feeAmount = doctorFee.getText().toString();
                callPaymentAPiforLabKims();
            }
        } else if (subTanentId == 563 || subTanentId == 572 || subTanentId == 576) {
            if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mDoctorFee528.getID()) {
                printHeading = "OPD Consultation Receipt";
                Log.d("printHeading", printHeading);
                appointmentBookedOrNotByMrnoGetAppId();
            } else if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mLabBilling528.getID()) {
                if (LAB_STATUS == PRESC_LAB) {
                    printHeading = "Lab Receipt";
                    feeGenerateStatus = "Lab Fee";
                    String labfeeAmount = doctorFee.getText().toString();
                    /*Log.d("labfeeAmount", labfeeAmount);
                    feeAmount = labfeeAmount.split("\\.")[0];*/
                    feeAmount = labfeeAmount;
                    if (labTransactionDatum != null) {
                        callPaymentAPiforLabKims();
                    } else {
                        Toast.makeText(CentralizedBillingActivity.this, "No order found, Payment Cannot be done", Toast.LENGTH_LONG).show();
                    }
                } else if (LAB_STATUS == SELF_LAB) {
                    printHeading = "Lab Receipt";
                    feeGenerateStatus = "Lab Fee";
                    String labfeeAmount = doctorFee.getText().toString();
                    /*Log.d("SELF_LAB", labfeeAmount);
                    String labFee[] = labfeeAmount.split("\\.");
                    Log.d("SELF_LAB", labFee[0]);
                    feeAmount = labFee[0];*/
                    feeAmount = labfeeAmount;
                    prescribeLabGrpTestOrder();
                }

            }
        } else {
            appointmentBookedOrNotByMrnoGetAppId();
            //callPaymentAPiforKims();
            printHeading = "OPD Consultation Receipt";
        }
    }

    private void prescribeLabGrpTestOrder() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        JsonObject obj = new JsonObject();
        JsonArray PackageArray = new JsonArray();
        JsonArray groupArray = new JsonArray();
        JsonArray testArray = new JsonArray();
        for (int i = 0; i < labOrderDetailModels.size(); i++) {
            LabOrderDetailModel labOrderDetailModel = labOrderDetailModels.get(i);
            if (labOrderDetailModel.getLabTestNature() == EnumType.LabObjNature.kLabObjNaturePackage) {
                if (labOrderDetailModel.isSelected() == true) {
                    JsonObject packageobj = new JsonObject();
                    packageobj.addProperty("pkgId", labOrderDetailModel.getLabTestId());
                    packageobj.addProperty("cost", labOrderDetailModel.getLabTestCost());
                    packageobj.addProperty("name", labOrderDetailModel.getLabTestName());
                    PackageArray.add(packageobj);
                }
            } else if (labOrderDetailModel.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureGroup) {
                if (labOrderDetailModel.isSelected() == true) {
                    JsonObject groupobj = new JsonObject();
                    groupobj.addProperty("pkgId", "0");
                    groupobj.addProperty("LabGrpId", labOrderDetailModel.getLabTestId());
                    groupobj.addProperty("cost", labOrderDetailModel.getLabTestCost());
                    groupobj.addProperty("name", labOrderDetailModel.getLabTestName());
                    groupArray.add(groupobj);
                }
            } else if (labOrderDetailModel.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureTest) {
                if (labOrderDetailModel.isSelected() == true) {
                    JsonObject testobj = new JsonObject();
                    testobj.addProperty("pkgId", "0");
                    testobj.addProperty("LabGrpId", "0");
                    testobj.addProperty("TestId", labOrderDetailModel.getLabTestId());
                    testobj.addProperty("cost", labOrderDetailModel.getLabTestCost());
                    testobj.addProperty("name", labOrderDetailModel.getLabTestName());
                    testArray.add(testobj);
                }
            }
        }
        obj.add("LabOrderTests", testArray);
        obj.add("LabOrderPackages", PackageArray);
        obj.add("LabOrderGrps", groupArray);
        obj.addProperty("Mrno", tvMrno.getText().toString());
        obj.addProperty("laterDate", "");
        obj.addProperty("natureId", 4);
        obj.addProperty("date", date);
        obj.addProperty("LabFacility", labId);
        obj.addProperty("UserRoleID", user_role_id);
        obj.addProperty("sessionkey", "ASES" + System.currentTimeMillis());

        Log.d("lab data", obj.toString());

        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postLabGrpTestOrder_v5(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                laborderId = postPaymentModel.getID();
                postLabOrderDetailsApi();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void postLabOrderDetailsApi() {
        JsonObject obj = new JsonObject();
        obj.addProperty("labOrderId", laborderId);
        obj.addProperty("subTenantId", subTanentId);
        JsonArray jsonArray = new JsonArray();
        JsonObject ordDetailObj = null;
        for (int i = 0; i < labOrderDetailModels.size(); i++) {
            if (labOrderDetailModels.get(i).getLabTestNature() == EnumType.LabObjNature.kLabObjNatureTest) {
                if (labOrderDetailModels.get(i).isSelected()) {
                    ordDetailObj = new JsonObject();
                    ordDetailObj.addProperty("packageId", 0);
                    ordDetailObj.addProperty("grpId", 0);
                    ordDetailObj.addProperty("testId", labOrderDetailModels.get(i).getLabTestId());
                    ordDetailObj.addProperty("cost", labOrderDetailModels.get(i).getLabTestCost());
                    jsonArray.add(ordDetailObj);
                }
            } else if (labOrderDetailModels.get(i).getLabTestNature() == EnumType.LabObjNature.kLabObjNatureGroup) {
                if (labOrderDetailModels.get(i).isSelected()) {
                    ordDetailObj = new JsonObject();
                    ordDetailObj.addProperty("packageId", 0);
                    ordDetailObj.addProperty("grpId", labOrderDetailModels.get(i).getLabTestId());
                    ordDetailObj.addProperty("testId", 0);
                    ordDetailObj.addProperty("cost", labOrderDetailModels.get(i).getLabTestCost());
                    jsonArray.add(ordDetailObj);
                }
            } else if (labOrderDetailModels.get(i).getLabTestNature() == EnumType.LabObjNature.kLabObjNaturePackage) {
                if (labOrderDetailModels.get(i).isSelected()) {
                    ordDetailObj = new JsonObject();
                    ordDetailObj.addProperty("packageId", labOrderDetailModels.get(i).getLabTestId());
                    ordDetailObj.addProperty("grpId", 0);
                    ordDetailObj.addProperty("testId", 0);
                    ordDetailObj.addProperty("cost", labOrderDetailModels.get(i).getLabTestCost());
                    jsonArray.add(ordDetailObj);
                }
            }
        }
        obj.add("labordDetail", jsonArray);
        Log.d("labordDetail", obj.toString());
        if (jsonArray.size() != 0) {
            postLabData(obj);
        } else {
            Toast.makeText(CentralizedBillingActivity.this, "Please choose test.", Toast.LENGTH_LONG).show();
        }
    }

    private void postLabData(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postLabOrderdetails_v2(obj, new Callback<LabPharmacyPostResponseModel>() {
            @Override
            public void success(LabPharmacyPostResponseModel labPharmacyPostResponseModel, Response response) {
                if (labPharmacyPostResponseModel.isStatus() == true) {
                    labObjectKeyArray = new JsonArray();
                    labObjectKeyArray.add(new JsonPrimitive(labPharmacyPostResponseModel.getId().toString()));
                    callPaymentAPiforLab();
                } else if (labPharmacyPostResponseModel.isStatus() == false) {
                    Toast.makeText(CentralizedBillingActivity.this, labPharmacyPostResponseModel.getMsg(), Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void callPaymentAPiforLab() {
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", tvHospitalId.getText().toString());
        obj.addProperty("AppnatureId", 0);
        obj.addProperty("UserRoleId", user_role_id);
        obj.addProperty("Description", "Lab");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", feeAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", serviceType);
        obj.addProperty("Mrno", tvMrno.getText().toString());
        obj.addProperty("HIS_BillNo", 0);
        obj.addProperty("MobileNo", tvPatientPhone.getText().toString().trim());
        //obj.addProperty("LabOrderID", 0);
        //obj.addProperty("PharmacyOrderID", pharmacyModels.getPresOrderId());
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("AppId", 0);
        obj.addProperty("orderId", laborderId);

        obj.addProperty("finalBillAmount", tvPayableAmount.getText().toString());
        JsonArray discountArray = new JsonArray();
        if (discountModelArrayList.size() > 0) {
            for (int i = 0; i < discountModelArrayList.size(); i++) {
                JsonObject discountObj = new JsonObject();
                discountObj.addProperty("discountName", discountModelArrayList.get(i).getDiscountName());
                discountObj.addProperty("discountPercentage", discountModelArrayList.get(i).getDiscountPercantage());
                discountObj.addProperty("discountAmount", discountModelArrayList.get(i).getDiscountAmount());
                discountArray.add(discountObj);
            }
        }
        obj.add("billDiscount", discountArray);

        obj.add("OrdTxnIds", labObjectKeyArray);
        Log.d("postpaymentv3", obj.toString());


        if (paymentMode.equals("1")) {
            postPaymentForLab(obj);
        }
        if (paymentMode.equals("2")) {
            postPaymentForLab(obj);
        }
        if (paymentMode.equals("3")) {
            postPaymentForLab(obj);
        }
    }

    private void showSuccessDialog(String msg) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSupportFragmentManager().popBackStack();
                dialog.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.show();
    }

    private void appointmentBookedOrNotByMrnoGetAppId() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.appBooKedOrNotByMrnoGetAppId(tvMrno.getText().toString() + "", user_role_id + "", scheduleId + "", currDate, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatusId() == EnumType.AppointmentOrNot.mAppointmentAlreadyFixed.getID()) {
                    appId = Integer.parseInt(postPaymentModel.getID());
                    callPaymentAPiforKims();
                } else if (postPaymentModel.getStatusId() == EnumType.AppointmentOrNot.mNewAppointment.getID()) {
                    showAppointmentDialog(postPaymentModel.getMsg() + "Do you want to book appointment now ?");
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showAppointmentDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CentralizedBillingActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callGetAppointmentSlotApi();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.show();
    }

    private void callGetAppointmentSlotApi() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPointConsumer.getAppSlotsByScheduleId(selectedUserRoleId, scheduleDate, subTanentId, scheduleId, new Callback<GetappointmentModel1[]>() {
            @Override
            public void success(GetappointmentModel1[] getappointmentModels, Response response) {
                if (getappointmentModels.length > 0) {
                    showAppointmentListDialog(getappointmentModels);
                } else {
                    Toast.makeText(CentralizedBillingActivity.this, "No Slot Found", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showAppointmentListDialog(final GetappointmentModel1[] getappointmentModels) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CentralizedBillingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.doctor_schedule_layout, null);
        alertDialog.setView(convertView);
        TextView tv_txt_no_slot = (TextView) convertView.findViewById(R.id.tv_txt_no_slot);
        ImageView doctor_profile = (ImageView) convertView.findViewById(R.id.doctor_profile);
        //Picasso.with(CentralizedBillingActivity.this).load(getappointmentModels[0].getUserImg()).into(doctor_profile);

        TextView tv_doctor_name = (TextView) convertView.findViewById(R.id.tv_doctor_name);
        tv_doctor_name.setText(getappointmentModels[0].getUserName());
        TextView tv_department_name = (TextView) convertView.findViewById(R.id.tv_department_name);
        tv_department_name.setText(getappointmentModels[0].getDeptName());

        TextView appointment_slot1 = (TextView) convertView.findViewById(R.id.appointment_slot1);
        TextView appointment_slot2 = (TextView) convertView.findViewById(R.id.appointment_slot2);
        TextView appointment_slot3 = (TextView) convertView.findViewById(R.id.appointment_slot3);


        if (getappointmentModels[0].getAppointments().size() > 0) {
            tv_txt_no_slot.setVisibility(View.GONE);
            tv_doctor_name.setText(getappointmentModels[0].getUserName());
            tv_department_name.setText(getappointmentModels[0].getDeptName());
            if (getappointmentModels[0].getAppointments().size() == 1) {
                appointment_slot1.setText(Helper.Convert24to12(getappointmentModels[0].getAppointments().get(0).getFromTime()) + " - " + Helper.Convert24to12(getappointmentModels[0].getAppointments().get(0).getToTime()));
                appointment_slot2.setVisibility(View.GONE);
                appointment_slot3.setVisibility(View.GONE);
            } else if (getappointmentModels[0].getAppointments().size() == 2) {
                appointment_slot1.setText(Helper.Convert24to12(getappointmentModels[0].getAppointments().get(0).getFromTime()) + " - " + Helper.Convert24to12(getappointmentModels[0].getAppointments().get(0).getToTime()));
                appointment_slot2.setText(Helper.Convert24to12(getappointmentModels[0].getAppointments().get(1).getFromTime()) + " - " + Helper.Convert24to12(getappointmentModels[0].getAppointments().get(1).getToTime()));
                appointment_slot3.setVisibility(View.GONE);
            } else if (getappointmentModels[0].getAppointments().size() == 3) {
                appointment_slot1.setText(Helper.Convert24to12(getappointmentModels[0].getAppointments().get(0).getFromTime()) + " - " + Helper.Convert24to12(getappointmentModels[0].getAppointments().get(0).getToTime()));
                appointment_slot2.setText(Helper.Convert24to12(getappointmentModels[0].getAppointments().get(1).getFromTime()) + " - " + Helper.Convert24to12(getappointmentModels[0].getAppointments().get(1).getToTime()));
                appointment_slot3.setText(Helper.Convert24to12(getappointmentModels[0].getAppointments().get(2).getFromTime()) + " - " + Helper.Convert24to12(getappointmentModels[0].getAppointments().get(2).getToTime()));
            } /*else  if (getappointmentModels[0].getAppointments().size() > 3){
                appointment_slot1.setText(Helper.Convert24to12(getappointmentModels[0].getAppointments().get(0).getFromTime()) + " - " + Helper.Convert24to12(getappointmentModels[0].getAppointments().get(0).getToTime()));
                appointment_slot2.setText(Helper.Convert24to12(getappointmentModels[0].getAppointments().get(1).getFromTime()) + " - " + Helper.Convert24to12(getappointmentModels[0].getAppointments().get(1).getToTime()));
                appointment_slot3.setText(Helper.Convert24to12(getappointmentModels[0].getAppointments().get(2).getFromTime()) + " - " + Helper.Convert24to12(getappointmentModels[0].getAppointments().get(2).getToTime()));
            }*/
        } else {
            tv_txt_no_slot.setVisibility(View.VISIBLE);
            appointment_slot1.setVisibility(View.GONE);
            appointment_slot2.setVisibility(View.GONE);
            appointment_slot3.setVisibility(View.GONE);
        }


        TextView btn_cancel = (TextView) convertView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
        ad = alertDialog.show();


        appointment_slot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
                appId = getappointmentModels[0].getAppointments().get(0).getAppId();
                insertAppointmentApi(getappointmentModels[0].getAppointments().get(0));
                //appointmentBookedOrNot(getappointmentModels[0].getAppointments().get(0));
            }
        });
        appointment_slot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
                appId = getappointmentModels[0].getAppointments().get(1).getAppId();
                insertAppointmentApi(getappointmentModels[0].getAppointments().get(1));
                //appointmentBookedOrNot(getappointmentModels[0].getAppointments().get(1));
            }
        });
        appointment_slot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
                appId = getappointmentModels[0].getAppointments().get(2).getAppId();
                insertAppointmentApi(getappointmentModels[0].getAppointments().get(2));
                //appointmentBookedOrNot(getappointmentModels[0].getAppointments().get(2));
            }
        });
    }

    private void appointmentBookedOrNot(final AppointmentModel appointmentModels) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.appointmentBookedOrNot(tvMrno.getText().toString() + "", selectedUserRoleId + "", scheduleId + "", currDate, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatusId() == EnumType.AppointmentOrNot.mAppointmentAlreadyFixed.getID()) {
                    Log.d("mAppointmentAlready", "mAppointmentAlreadyFixed");
                } else if (postPaymentModel.getStatusId() == EnumType.AppointmentOrNot.mNewAppointment.getID()) {
                    insertAppointmentApi(appointmentModels);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void insertAppointmentApi(AppointmentModel appointmentModels) {
        /*JsonObject insertAppointmentObject = new JsonObject();

        JsonObject appBookingObject = new JsonObject();
        appBookingObject.addProperty("AppNatureId", 1);
        appBookingObject.addProperty("CurrentStatusId", 1);
        appBookingObject.addProperty("Mrno", tvMrno.getText().toString());
        appBookingObject.addProperty("Others", "");

        insertAppointmentObject.addProperty("AppId", appId);
        insertAppointmentObject.addProperty("ordTxnId", 0);
        insertAppointmentObject.addProperty("orderId", 0);
        insertAppointmentObject.addProperty("serviceTypeId", 1);

        insertAppointmentObject.add("appbookings", appBookingObject);

        JsonObject obj = new JsonObject();
        obj.add("_Appointments", insertAppointmentObject);
        obj.addProperty("UserRoleID", selectedUserRoleId);
        Log.d("InsertAppointments", obj.toString());*/


        JsonObject insertAppointmentObject = new JsonObject();

        JsonObject appBookingObject = new JsonObject();
        appBookingObject.addProperty("AppNatureId", appNatureId);
        appBookingObject.addProperty("CurrentStatusId", 1);
        appBookingObject.addProperty("Mrno", tvMrno.getText().toString());
        appBookingObject.addProperty("Others", "");
        appBookingObject.addProperty("self", "0");

        insertAppointmentObject.addProperty("AppId", appId);
        insertAppointmentObject.addProperty("ordTxnId", 0);
        insertAppointmentObject.addProperty("orderId", 0);
        if (serviceRoleId == 1) {
            insertAppointmentObject.addProperty("serviceTypeId", 1);
        } else if (serviceRoleId == 2) {
            insertAppointmentObject.addProperty("serviceTypeId", 5);
        } else if (serviceRoleId == 3) {
            insertAppointmentObject.addProperty("serviceTypeId", 4);
        }

        insertAppointmentObject.addProperty("AvlStatusId", avlStatusId);
        insertAppointmentObject.addProperty("scheduleId", scheduleId);
        insertAppointmentObject.add("appbookings", appBookingObject);

        JsonObject obj = new JsonObject();
        obj.add("_Appointments", insertAppointmentObject);
        obj.addProperty("UserRoleID", selectedUserRoleId);
        Log.d("InsertAppointments", obj.toString());

        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.insertAppointments(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                if (s.equals("true")) {
                    showAppointmentSuccessDialog("Appointment Booked Successfully.");
                }else{
                    showAppointmentErrorDialog("Appointment is not Booked.");
                }
                //postPaymentAPI_V1();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error=" + error.getMessage());
                dismissLoadingDialog();
            }
        });
    }

    private void showAppointmentErrorDialog(String msg) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CentralizedBillingActivity.this);
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
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CentralizedBillingActivity.this);
        builder.setTitle("Success");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                postActivityTrackerFromAPI();
                successAlertDialog.dismiss();
            }
        });
        successAlertDialog = builder.show();
    }
    private void postActivityTrackerFromAPI() {
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
        obj.addProperty("patMrno",tvMrno.getText().toString());
        obj.addProperty("actOthers","");

        mCuraApplication.getInstance().mCuraEndPoint.postActivityTracker(obj, new Callback<PostActivityTrackerModel>() {
            @Override
            public void success(PostActivityTrackerModel postActivityTrackerModel, Response response) {
                callPaymentAPiforKims();
                dismissLoadingDialog();
            }
            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }
    private void callPaymentAPiforLabKims() {
        String hId = "";
        if (tvHospitalId.getText().toString().equals("")) {
            hId = "0";
        } else {
            hId = tvHospitalId.getText().toString();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", hId);
        obj.addProperty("AppnatureId", appNatureId);
        obj.addProperty("UserRoleId", user_role_id);
        obj.addProperty("Description", "Lab Fee");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", feeAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", 4);
        obj.addProperty("Mrno", tvMrno.getText().toString());
        obj.addProperty("HIS_BillNo", 0);
        obj.addProperty("MobileNo", tvPatientPhone.getText().toString().trim());
        obj.addProperty("orderId", labTransactionDatum.getOrderId());
        obj.addProperty("ScheduleId", 0);
        obj.addProperty("finalBillAmount", tvPayableAmount.getText().toString());
        JsonArray discountArray = new JsonArray();
        if (discountModelArrayList.size() > 0) {
            for (int i = 0; i < discountModelArrayList.size(); i++) {
                JsonObject discountObj = new JsonObject();
                discountObj.addProperty("discountName", discountModelArrayList.get(i).getDiscountName());
                discountObj.addProperty("discountPercentage", discountModelArrayList.get(i).getDiscountPercantage());
                discountObj.addProperty("discountAmount", discountModelArrayList.get(i).getDiscountAmount());
                discountArray.add(discountObj);
            }
        }
        obj.add("billDiscount", discountArray);

        JsonArray objectKeyArray = new JsonArray();
        for (int i = 0; i < labOrderTransactionModelArrayList.size(); i++) {
            if (labOrderTransactionModelArrayList.get(i).isStatus()) {
                objectKeyArray.add(new JsonPrimitive(labOrderTransactionModelArrayList.get(i).getOrdTxnId()));
            }
        }
        obj.add("OrdTxnIds", objectKeyArray);
        Log.d("lab_json", obj.toString());

        if (paymentMode.equals("1")) {
            postPaymentForLab(obj);
        }
        if (paymentMode.equals("2")) {
            postPaymentForLab(obj);
        }
        if (paymentMode.equals("3")) {
            postPaymentForLab(obj);
        }
    }

    private void postPaymentForLab(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentLabFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(CentralizedBillingActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                    showSuccessDialog(postPaymentModel.getMsg(), data);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(CentralizedBillingActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    showSuccessDialog(postPaymentModel.getMsg(), data);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mOrderTransactionIdNull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }

                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showSuccessDialog(String msg, final String data[]) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CentralizedBillingActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                printBill(data);
                dialog.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.show();
    }

    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }

    private void callPaymentAPiforPharmacyKims() {
        String hId = "";
        if (tvHospitalId.getText().toString().equals("")) {
            hId = "0";
        } else {
            hId = tvHospitalId.getText().toString();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", hId);
        obj.addProperty("AppnatureId", appNatureId);
        obj.addProperty("UserRoleId", user_role_id);
        obj.addProperty("Description", "Pharmacy Fee");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", feeAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", 5);
        obj.addProperty("Mrno", tvMrno.getText().toString());
        obj.addProperty("HIS_BillNo", 0);
        obj.addProperty("MobileNo", tvPatientPhone.getText().toString().trim());
        obj.addProperty("orderId", pharmacyTransactionDatum.getOrderId());
        obj.addProperty("ScheduleId", 0);

        JsonArray objectKeyArray = new JsonArray();
        for (int i = 0; i < pharmacyOrderTxnWithDetailModel.getData().size(); i++) {
            if (pharmacyOrderTxnWithDetailModel.getData().get(i).isStatus()) {
                objectKeyArray.add(new JsonPrimitive(pharmacyOrderTxnWithDetailModel.getData().get(i).getOrdTxnId()));
            }

        }
        obj.addProperty("finalBillAmount", tvPayableAmount.getText().toString());
        JsonArray discountArray = new JsonArray();
        if (discountModelArrayList.size() > 0) {
            for (int i = 0; i < discountModelArrayList.size(); i++) {
                JsonObject discountObj = new JsonObject();
                discountObj.addProperty("discountName", discountModelArrayList.get(i).getDiscountName());
                discountObj.addProperty("discountPercentage", discountModelArrayList.get(i).getDiscountPercantage());
                discountObj.addProperty("discountAmount", discountModelArrayList.get(i).getDiscountAmount());
                discountArray.add(discountObj);
            }
        }
        obj.add("billDiscount", discountArray);
        obj.add("OrdTxnIds", objectKeyArray);
        Log.d("payment_json", obj.toString());

        if (paymentMode.equals("1")) {
            postPaymentForPharmacy(obj);
        }
        if (paymentMode.equals("2")) {
            postPaymentForPharmacy(obj);
        }
        if (paymentMode.equals("3")) {
            postPaymentForPharmacy(obj);
        }
    }

    private void postPaymentForPharmacy(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentPharmacyFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(CentralizedBillingActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                    showSuccessDialog(postPaymentModel.getMsg(), data);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(CentralizedBillingActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    showSuccessDialog(postPaymentModel.getMsg(), data);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mOrderTransactionIdNull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void callPaymentAPiforKims() {
        if (paymentMode.equals("1")) {
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
                        } else {
                            feeAmount = "0";
                            feeGenerateStatus = "OPD Consultation Fee";
                        }
                    } else if (appNatureId != 25) {
                        feeAmount = doctorFee.getText().toString();
                        feeGenerateStatus = "OPD Consultation Fee";
                    }
                    postPaymentAPI_V1();
                    /*LocalBillModel localBillModel = dbHelper.patBillStatus(selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureId + "");
                    if (localBillModel != null) {
                        if (localBillModel.getBill_status().equals("1")) {
                            Log.d("localBillModel", localBillModel.getHospital_id() + "");
                            localPrintBill(localBillModel);
                            Toast.makeText(CentralizedBillingActivity.this, "Duplicate Bill", Toast.LENGTH_LONG).show();
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
                    }*/
                } else {
                    Toast.makeText(CentralizedBillingActivity.this, "Please Select Nature", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CentralizedBillingActivity.this, "Please Select Patient", Toast.LENGTH_LONG).show();
            }
        } else if (paymentMode.equals("2")) {
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
                        } else {
                            feeAmount = "0";
                            feeGenerateStatus = "OPD Consultation Fee";
                        }
                    } else if (appNatureId != 25) {
                        feeAmount = doctorFee.getText().toString();
                        feeGenerateStatus = "OPD Consultation Fee";
                    }
                    postPaymentAPI_V1();
                } else {
                    Toast.makeText(CentralizedBillingActivity.this, "Please Select Nature", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CentralizedBillingActivity.this, "Please Select Patient", Toast.LENGTH_LONG).show();
            }
        }else if (paymentMode.equals("3")) {
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
                        } else {
                            feeAmount = "0";
                            feeGenerateStatus = "OPD Consultation Fee";
                        }
                    } else if (appNatureId != 25) {
                        feeAmount = doctorFee.getText().toString();
                        feeGenerateStatus = "OPD Consultation Fee";
                    }
                    postPaymentAPI_V1();
                } else {
                    Toast.makeText(CentralizedBillingActivity.this, "Please Select Nature", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CentralizedBillingActivity.this, "Please Select Patient", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void postPaymentAPI_V1() {
        JsonObject obj = new JsonObject();
        String hId = "";
        if (tvHospitalId.getText().toString().equals("")) {
            hId = "0";
        } else {
            hId = tvHospitalId.getText().toString();
        }
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", hId);
        obj.addProperty("AppnatureId", appNatureId);
        obj.addProperty("UserRoleId", selectedUserRoleId);
        obj.addProperty("Description", "Doctor Fee");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("AppId", appId);
        obj.addProperty("BillAmount", feeAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", serviceType);
        obj.addProperty("Mrno", tvMrno.getText().toString());
        obj.addProperty("HIS_BillNo", 0);
        obj.addProperty("MobileNo", tvPatientPhone.getText().toString().trim());
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("finalBillAmount", tvPayableAmount.getText().toString());
        JsonArray discountArray = new JsonArray();
        if (discountModelArrayList.size() > 0) {
            for (int i = 0; i < discountModelArrayList.size(); i++) {
                JsonObject discountObj = new JsonObject();
                discountObj.addProperty("discountName", discountModelArrayList.get(i).getDiscountName());
                discountObj.addProperty("discountPercentage", discountModelArrayList.get(i).getDiscountPercantage());
                discountObj.addProperty("discountAmount", discountModelArrayList.get(i).getDiscountAmount());
                discountArray.add(discountObj);
            }
        }
        obj.add("billDiscount", discountArray);
        Log.d("docfee_data", obj.toString());
        if (paymentMode.equals("1")) {
            postPaymentForCash(obj);
        }
        if (paymentMode.equals("2")) {
            postPaymentAPIForCard(obj);
        }
        if (paymentMode.equals("3")) {
            postPaymentForCash(obj);
        }

    }

    private void postPaymentForCash(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentDocFee_v5(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    //Toast.makeText(CentralizedBillingActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();

                    String id = postPaymentModel.getID();
                    data = id.split("-");

                    int serial_no = dbHelper.getMaxSerial_no() + 1;
                    String timestamp = selectedUserRoleId + "" + serial_no;
                    time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                    boolean insertStatus = dbHelper.insertBillDetail(consultantDoctorName, selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureName, "1", data[1], data[0], departmentName, tvHospitalId.getText().toString(), frontOfficeUserRoleId + "", timestamp, subTanentId + "", appNatureId + "", paymentMode + "", serviceType + "", serial_no, time, "ON");
                    if (insertStatus) {
                        successFullDialog(postPaymentModel.getMsg(), data);
                        //printBill(data);
                    }

                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(CentralizedBillingActivity.this, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    printBill(data);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentBlankScheduleId.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void successFullDialog(String msg, final String[] data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CentralizedBillingActivity.this);
        builder.setTitle(msg);
        builder.setMessage("Do you want to Checkin.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getScheduleStatus(data);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                printBill(data);
            }
        });
        AlertDialog dialog = builder.show();
    }

    public void getScheduleStatus(final String[] data) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.scheduleStatus(scheduleId, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                if (generateTokenResultModel != null) {
                    if (generateTokenResultModel.getStatus() == NewAppointmentActivity.ScheduleStatus.kScheduleStatusChartNotGenerated.getID()) {
                        Toast.makeText(CentralizedBillingActivity.this, generateTokenResultModel.getMsg(), Toast.LENGTH_LONG).show();
                        printBill(data);
                    } else if (generateTokenResultModel.getStatus() == NewAppointmentActivity.ScheduleStatus.kScheduleStatusChartGenerated.getID()) {
                        if (subTanentId == 515 || subTanentId == 528 || subTanentId == 547 || subTanentId == 557) {
                            setCheckInStatusWithFee(data);
                        } else {
                            setCheckInStatusWithoutFee(data);
                        }
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

    public void setCheckInStatusWithFee(final String data[]) {
        showLoadingDialog();
        //Toast.makeText(CheckInActivity.this,"subtenentid = "+subTanentId,Toast.LENGTH_LONG).show();
        JsonObject obj = new JsonObject();
        obj.addProperty("MRNo", tvMrno.getText().toString());
        obj.addProperty("UserRoleId", selectedUserRoleId);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("Date", scheduleDate);
        obj.addProperty("serviceRoleId", serviceRoleId);
        obj.addProperty("appId", appId);
        mCuraApplication.getInstance().mCuraEndPoint.patient_Check_In_version1(obj, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                String msg = null;
                if (generateTokenResultModel != null) {
                    patTokenNo = generateTokenResultModel.getTokenNo();
                    msg = generateTokenResultModel.getMsg();
                    chartGenerateStatus = generateTokenResultModel.getStatus();
                    if (chartGenerateStatus == 1) {
                        Toast.makeText(CentralizedBillingActivity.this, msg, Toast.LENGTH_LONG).show();
                        printBill(data);
                    } else if (chartGenerateStatus == 2) {
                        Toast.makeText(CentralizedBillingActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 3) {
                        Toast.makeText(CentralizedBillingActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 4) {
                        startActivity(new Intent(CentralizedBillingActivity.this, CalendarActivity.class));
                        Toast.makeText(CentralizedBillingActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 5) {
                        showErrorDialog(msg);
                    } else if (chartGenerateStatus == 6) {
                        showErrorDialog(msg);
                    } else {
                        Toast.makeText(CentralizedBillingActivity.this, msg, Toast.LENGTH_LONG).show();
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
    public void setCheckInStatusWithoutFee(final String[] data) {
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("MRNo", tvMrno.getText().toString());
        obj.addProperty("UserRoleId", selectedUserRoleId);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("Date", scheduleDate);
        obj.addProperty("appId", appId);
        mCuraApplication.getInstance().mCuraEndPoint.patient_Check_In(obj, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                String msg = null;
                if (generateTokenResultModel != null) {
                    patTokenNo = generateTokenResultModel.getTokenNo();
                    msg = generateTokenResultModel.getMsg();
                    chartGenerateStatus = generateTokenResultModel.getStatus();
                    if (chartGenerateStatus == 1) {
                        Toast.makeText(CentralizedBillingActivity.this, msg, Toast.LENGTH_LONG).show();
                        printBill(data);
                    } else if (chartGenerateStatus == 2) {
                        Toast.makeText(CentralizedBillingActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 3) {
                        Toast.makeText(CentralizedBillingActivity.this, msg, Toast.LENGTH_LONG).show();
                    } else if (chartGenerateStatus == 4) {
                        startActivity(new Intent(CentralizedBillingActivity.this, CalendarActivity.class));
                        Toast.makeText(CentralizedBillingActivity.this, msg, Toast.LENGTH_LONG).show();
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

    private void postPaymentAPIForCard(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentDocFee_v5(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    String id = postPaymentModel.getID();
                    data = id.split("-");


                    int serial_no = dbHelper.getMaxSerial_no() + 1;
                    String timestamp = selectedUserRoleId + "" + serial_no;
                    time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                    boolean insertStatus = dbHelper.insertBillDetail(consultantDoctorName, selectedUserRoleId + "", completeDate, tvMrno.getText().toString(), tvPatientName.getText().toString(), feeAmount, appNatureName, "1", data[1], data[0], departmentName, tvHospitalId.getText().toString(), frontOfficeUserRoleId + "", timestamp, subTanentId + "", appNatureId + "", paymentMode + "", serviceType + "", serial_no, time, "ON");
                    if (insertStatus) {
                        successFullDialog(postPaymentModel.getMsg(), data);
                        //printBill(data);
                    }

                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    printBill(data);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentBlankScheduleId.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showErrorDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CentralizedBillingActivity.this);
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
                    Toast.makeText(CentralizedBillingActivity.this, "failure", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
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
    public void printBill(String data[]) {
        et_search_key.setText("");
        restartActivity(CentralizedBillingActivity.this);
        WebView webView = new WebView(CentralizedBillingActivity.this);
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
        if (patTokenNo == null) {
            patTokenNo = "";
        }
        String finalPayableAmount = tvPayableAmount.getText().toString();
        String[] finalPayable = finalPayableAmount.split("\\.");
        Log.d("finalPayable", finalPayable.length + "");
        String rupeePart, paisePart;
        if (finalPayable.length == 2) {
            rupeePart = Helper.convert(Integer.parseInt(finalPayable[0]));
            paisePart = Helper.convert(Integer.parseInt(finalPayable[1]));
        } else {
            rupeePart = Helper.convert(Integer.parseInt(finalPayableAmount));
            paisePart = Helper.convert(Integer.parseInt("0"));
        }
        String discountHTML = "";
        int rowSpan = 3;
        if (discountModelArrayList.size() > 0) {
            ArrayList<String> arr = new ArrayList<String>();
            for (int i = 0; i < discountModelArrayList.size(); i++) {
                rowSpan++;
                arr.add("<tr>");
                arr.add("<td style=\"text-align:right\"><strong>" + discountModelArrayList.get(i).getDiscountName() + "</strong></td>");
                arr.add("<td style=\"text-align:center;\" ><strong> - Rs. " + Double.parseDouble(discountModelArrayList.get(i).getDiscountedAmount()) + "</strong></td>");
                arr.add("</tr>");
            }
            discountHTML = android.text.TextUtils.join("", arr);
        }


        if (subTanentId == 563 || subTanentId == 572 || subTanentId == 576) {
            if (serviceType == 4) {
                if(LAB_STATUS == SELF_LAB){
                    feeGenerateStatus += "(";
                    for (int i = 0; i < labOrderDetailModelArrayList.size(); i++) {
                        if (labOrderDetailModelArrayList.size() - 1 != i) {
                            feeGenerateStatus += labOrderDetailModelArrayList.get(i).getLabTestName() + ", ";
                        } else {
                            feeGenerateStatus += labOrderDetailModelArrayList.get(i).getLabTestName() + ")";
                        }
                    }
                }else if(LAB_STATUS == PRESC_LAB){
                    feeGenerateStatus += "(";
                    for(int i=0 ; i<labTxnDetailModel.size();i++){
                        if (labTxnDetailModel.size() - 1 != i) {
                            feeGenerateStatus += labTxnDetailModel.get(i).getInvName() + ", ";
                        } else {
                            feeGenerateStatus += labTxnDetailModel.get(i).getInvName() + ")";
                        }
                    }
                }
            }
        }
        String htmlHeading="",htmlTokenLabel="",htmlEwalletLabel="",htmlHospitalIdLabel="",htmlPatDetail="";
        if(subTanentId==572){
            htmlHeading = "<tr>\n" +
                    "    <td colspan=\"2\"><h4>Honey Well Estate Pvt. Ltd.</h4>\n" +
                    "\t<p>63, Ring Road 2nd Floor Laj Pat Nagar III\n" +
                    "    New Delhi - 110024</br>\n" +
                    "    Phone:011-43282040</br>\n" +
                    "    State Code: 7</br>\n" +
                    "    GSTIN: 07AABCH5590GIZJ</br>\n" +
                    "    PAN: AABCH5590G</br>\n" +
                    /*"    Tax Invoice: TI3174\n" +*/
                    "    </p>  \n" +
                    "    </td>\n" +
                    "    </tr>";
            htmlPatDetail = " <tr>\n" +
                    "    \t<td colspan=\"2\"><div class=\"billnoOuter\"><div class=\"billno\">Bill No.</div>\n" +
                    "   \t    <div class=\"billnoValue\"><input type=\"text\" class=\"valueInput\" value = \"" + data[1] + "\"></div></div>\n" +
                    "        <div class=\"tokennoOuter\" style=\"display:none\"><div class=\"tokenNo\">Token No.</div>\n" +
                    "        <div class=\"tokenValue\"><input type=\"text\" class=\"valueInput\" value = \"" + patTokenNo + "\"></div></div> <div class=\"dateOuter\"><div class=\"date\">Date</div> <div class=\"dateValue\"><input type=\"text\" class=\"valueInput\" value=\"" + completeDate + " | " + time + "\"></div></div></td>\n" +
                    "    </tr>\n" +
                    "    \n" +
                    "      <tr>\n" +
                    "    \t<td colspan=\"2\"><div class=\"patientnameOuter\"><div class=\"patientname\">Patient Name</div>\n" +
                    "   \t    <div class=\"patientnameValue\"><input type=\"text\" class=\"valueInput\" value=\"" + tvPatientName.getText().toString() + "\"></div></div> \n" +
                    "        \n" +
                    "        <div class=\"hospitalIdOuter\" style=\"float:right\"><div class=\"hospitalId\">Clinic ID</div>\n" +
                    "        <div class=\"hospitalIdValue\"><input type=\"text\" class=\"valueInput\" value=\"" + tvHospitalId.getText().toString() + "\"></div></div>\n" +
                    "        \n" +
                    "        <div class=\"cashcardOuter\" style=\"display:none\"><div class=\"cashcard\">E-Wallet ID</div> <div class=\"cashcardValue\"><input type=\"text\" class=\"valueInput\" value = \"" + data[0] + "\"></div></div></td>\n" +
                    "    </tr>\n" ;
        }else{
            htmlHeading = "\t<tr>\n" +
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
                    "    </tr>\n" ;

            htmlPatDetail = " <tr>\n" +
                    "    \t<td colspan=\"2\"><div class=\"billnoOuter\"><div class=\"billno\">Bill No.</div>\n" +
                    "   \t    <div class=\"billnoValue\"><input type=\"text\" class=\"valueInput\" value = \"" + data[1] + "\"></div></div>\n" +
                    "        <div class=\"tokennoOuter\"><div class=\"tokenNo\">Token No.</div>\n" +
                    "        <div class=\"tokenValue\"><input type=\"text\" class=\"valueInput\" value = \"" + patTokenNo + "\"></div></div> <div class=\"dateOuter\"><div class=\"date\">Date</div> <div class=\"dateValue\"><input type=\"text\" class=\"valueInput\" value=\"" + completeDate + " | " + time + "\"></div></div></td>\n" +
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
                    "    </tr>\n" ;
        }
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
                ".receivedwithValue, .sumrepeesValue, .towordsValue, .paymodeValue, .rupeesValue, .billnoValue,.tokenValue, .dateValue, .patientnameValue, .cashcardValue, .hospitalIdValue, .rsinwordsValue, .amountwordsValue, .consultantValue{ float:left; margin-top:5px; }\n" +
                "\n" +
                ".receivedwith, .sumrepees, .towords, .paymode, .rupees, .signature, .billno, .tokenNo, .date, .patientname, .cashcard, .hospitalId, .rsinwords, .amountWords{ font-size:14px; float:left; padding-top:12px; font-weight:normal; height:30px;}\n" +
                "\n" +
                "\n" +
                ".billno, .tokenNo, .date, .patientname, .cashcard, .hospitalId, .consultant{ height:25px; font-size:13px; float:left; padding-top:12px; font-weight:normal; }\n" +
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
                ".tokenValue{ width:80px;}\n" +
                ".dateValue{ width:150px;} \n" +
                ".patientnameValue{ width:200px;}\n" +
                ".cashcardValue{ width:98px;}\n" +
                ".hospitalIdValue{ width:105px;}\n" +
                "\n" +
                ".billno{ width:50px;}\n" +
                ".tokenNo{ width:60px;}\n" +
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
                ".tokennoOuter{ width:200px; float:left;}\n" +
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
                "    \n" +
                "    \n" + htmlHeading +
                "\n" +

                "    \n" +
                "    \n" + htmlPatDetail +
                "\n" +
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
                "     <td rowspan=" + rowSpan + " style=\"width:1px; border-left:1px #333 solid; padding:7px 0px;\">&nbsp;</td>\n" +
                "    <td style=\"width:120px; text-align:center\">Rs. " + doctorFee.getText().toString() + "</td>\n" +
                "  </tr>\n" +
                "\n" +
                "\n" +
                "    <tr>\n" +
                "    <td>&nbsp; </td>\n" +
                "    <td>&nbsp;</td>\n" +
                "  </tr>\n" +

                "    \n" +
                "    \n" + discountHTML +
                "\n" +

                "  <tr>\n" +
                "    <td style=\"border-top:1px #333 solid; text-align:right; padding:7px 10px;\"><strong>Total Payable Amount</strong></td>\n" +
                "    <td style=\"border-top:1px #333 solid; padding:7px 10px; text-align:center;\"><strong>Rs. " + finalPayableAmount + "</strong></td>\n" +
                "  </tr>\n" +
                "   \n" +
                "    <tr>\n" +
                "    <td colspan=\"3\"><div class=\"amoutnwordsOuter\">\n" +
                "    <div class=\"amountWords\">Amount in words:</div>\n" +
                "    <div class=\"amountwordsValue\"><input type=\"text\" class=\"valueInput\" value = \"" + rupeePart + " Rupee and " + paisePart + " Paise Only\"></div>\n" +
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

    public void localPrintBill(LocalBillModel localBillModel) {
        String billno;
        if (localBillModel.getBill_no().equals("")) {
            billno = localBillModel.getTempBillNo();
        } else {
            billno = localBillModel.getBill_no();
        }
        WebView webView = new WebView(CentralizedBillingActivity.this);
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
                "  <h3 style=\"margin-bottom:20px;\"> " + printHeading + "</h3>\n" +
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
                "    <td style=\"border-top:1px #333 solid; text-align:right; padding:7px 10px;\"><strong>Total Payable Amount</strong></td>\n" +
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

    private void setDoctorList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CentralizedBillingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.search_doctor_dialog, null);
        builder.setView(convertView);
        SearchView search_doctor = (SearchView) convertView.findViewById(R.id.search_doctor);
        search_doctor.setIconified(false);
        search_doctor.setIconifiedByDefault(false);
        search_doctor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Filter filter = doctorScheduleAdapter.getFilter();
                if (TextUtils.isEmpty(newText)) {
                    filter.filter("");
                } else {
                    filter.filter(newText);
                }
                return true;
            }
        });
        search_doctor.setQueryHint("Search Here");
        dcotorListView = (ListView) convertView.findViewById(R.id.doctor_list);
        dcotorListView.setTextFilterEnabled(true);
        doctorScheduleAdapter = new DoctorScheduleAdapter(CentralizedBillingActivity.this,
                android.R.layout.simple_spinner_item,
                doctorArray);
        dcotorListView.setAdapter(doctorScheduleAdapter);
        dcotorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doctorListModel = doctorScheduleAdapter.getItem(position);
                selectedUserRoleId = doctorListModel.getUserRoleId();
                departmentName = doctorListModel.getDept();
                //departmentName = dbHelper.Get_DoctorDepartment(selectedUserRoleId + "");
                consultantDoctorName = doctorListModel.getDoctorName();
                doctor_spinner.setText(consultantDoctorName);
                scheduleId = doctorListModel.getSchedule().getScheduleId();
                serviceRoleId = doctorListModel.getServiceRoleId();
                setAppointmentNature();
                if (selectedUserRoleId != 0 && !tvMrno.getText().toString().equals("0000")) {
                    getLastBillDetail();
                } else {
                    tbl_pat_history.setVisibility(View.GONE);
                }

                if (!tvMrno.getText().toString().equals("0000")) {
                    nature.setEnabled(true);
                } else {
                    nature.setEnabled(false);
                }
                dialog.dismiss();
            }
        });

        dialog = builder.show();
    }

    public void getDoctorDetail(){
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.avialbale_TokenList_bydate(subTanentId, scheduleDate, new Callback<AvialbaleTokenListbydate[]>() {
            @Override
            public void success(AvialbaleTokenListbydate[] doctors, Response response) {
                doctorArray = doctors;

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
        mCuraApplication.getInstance().mCuraEndPoint.getServiceList(subTanentId, new Callback<GetServiceListModel[]>() {
            @Override
            public void success(GetServiceListModel[] getServiceListModels, Response response) {
                getServiceListModelsArray = getServiceListModels;
                if (getServiceListModels.length > 0) {
                    serviceListAdapter = new ServiceListAdapter(CentralizedBillingActivity.this, android.R.layout.simple_spinner_item, getServiceListModels);
                    serviceListSpinner.setAdapter(serviceListAdapter);
                }
                getDoctorDetail();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void getPatientSearchDetail(String searchKey) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.searchPatient_v1(1, 20, user_role_id, "1,3,4", searchKey, subTanentId, new Callback<SearchPatientModel>() {
            @Override
            public void success(SearchPatientModel patientSearchModels, Response response) {
                if (patientSearchModels.getData().size() > 0) {
                    for (int i = 0; i < patientSearchModels.getData().size(); i++) {
                        dataList.add(patientSearchModels.getData().get(i));
                    }
                    patientSearchModel.setData(dataList);
                    patientSearchModel.setStatus(patientSearchModels.getStatus());
                    patientSearchModel.setTotalResultCount(patientSearchModels.getTotalResultCount());
                    showPatientPopup();
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
                    Toast.makeText(CentralizedBillingActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
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
        alertDialog = new AlertDialog.Builder(CentralizedBillingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.search_patient_row_layout, null);
        alertDialog.setView(convertView);
        final ListView lv = (ListView) convertView.findViewById(R.id.search_patient_listview);

        searchPatientAdapter = new CheckInAdapter_v1(CentralizedBillingActivity.this,
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

    /**
     *
     */
    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(CentralizedBillingActivity.this);
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
    public void onBackPressed() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        discountModelArrayList.clear();
        discountAmountAdapter.notifyDataSetChanged();
        nature.setSelection(0);
        serviceListSpinner.setSelection(0);
        tvDiscountedAmount.setText("0.0");
        Utility.setListViewHeightBasedOnItems(listviewDiscountAmount);
        datumModel = searchPatientAdapter.getItem(position);
        setPatientDetail(datumModel);
        ad.dismiss();
    }

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

    private String getDob(String dob) {
        String timestamp = dob.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date(Long.parseLong(timestamp));
        SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
        String formattedDate = sdf.format(createdOn);
        System.out.println("formattedDate-->" + formattedDate);
        return formattedDate;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.service_type_spinner:
                if (discountModelArrayList.size() > 0) {
                    discountModelArrayList.clear();
                    discountAmountAdapter.notifyDataSetChanged();
                    Utility.setListViewHeightBasedOnItems(listviewDiscountAmount);
                    tvDiscountedAmount.setText("0");
                    doctorFee.setText("0000");
                    tvPayableAmount.setText("0");
                } else {
                    tvPayableAmount.setText("0");
                }

                getServiceListModel = serviceListAdapter.getItem(position);

                serviceType = getServiceListModel.getServiceTypeId();
                Log.d("serviceType", serviceType + "");
                if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mDoctorFee528.getID() || getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mDoctorFee243.getID()) {
                    if (!tvMrno.getText().toString().equals("0000")) {
                        ll_doc_nature.setVisibility(View.VISIBLE);
                        ll_pharmacy_detail_layout.setVisibility(View.GONE);
                        ll_lab_detail_layout.setVisibility(View.GONE);
                        doctorFee.setText("0000");
                        tvPayableAmount.setText("0.0");
                        getLastBillDetail();
                    } else {
                        Toast.makeText(CentralizedBillingActivity.this, "Please Select Patient", Toast.LENGTH_LONG).show();
                    }
                } else if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mLabBilling528.getID()) {
                    Log.d("servicetype", "in lab");
                    if (!tvMrno.getText().toString().equals("0000")) {
                        if(subTanentId == 563 || subTanentId == 572 || subTanentId == 576){
                            showLabMessageDialog();
                        }else{
                            ll_doc_nature.setVisibility(View.GONE);
                            ll_pharmacy_detail_layout.setVisibility(View.GONE);
                            ll_lab_detail_layout.setVisibility(View.VISIBLE);
                            lab_saved_data_listview.setVisibility(View.VISIBLE);
                            doctorFee.setText("0000");
                            getLabTransactionData();
                        }
                       /* if (subTanentId != 563) {
                            ll_doc_nature.setVisibility(View.GONE);
                            ll_pharmacy_detail_layout.setVisibility(View.GONE);
                            ll_lab_detail_layout.setVisibility(View.VISIBLE);
                            lab_saved_data_listview.setVisibility(View.VISIBLE);
                            doctorFee.setText("0000");
                            getLabTransactionData();
                        }else if(subTanentId != 572){
                            ll_doc_nature.setVisibility(View.GONE);
                            ll_pharmacy_detail_layout.setVisibility(View.GONE);
                            ll_lab_detail_layout.setVisibility(View.VISIBLE);
                            lab_saved_data_listview.setVisibility(View.VISIBLE);
                            doctorFee.setText("0000");
                            getLabTransactionData();
                        } else {
                            showLabMessageDialog();
                        }*/
                    } else {
                        Toast.makeText(CentralizedBillingActivity.this, "Please Select Patient", Toast.LENGTH_LONG).show();
                    }
                } else if (getServiceListModel.getServiceTypeId() == EnumType.ServiceType.mPharmacyBilling528.getID()) {
                    if (!tvMrno.getText().toString().equals("0000")) {
                        ll_doc_nature.setVisibility(View.GONE);
                        ll_lab_detail_layout.setVisibility(View.GONE);
                        ll_pharmacy_detail_layout.setVisibility(View.VISIBLE);
                        doctorFee.setText("0000");
                        getPharmacyTransactionData();
                    } else {
                        Toast.makeText(CentralizedBillingActivity.this, "Please Select Patient", Toast.LENGTH_LONG).show();
                    }
                } else {
                    tvPayableAmount.setText("0.0");
                    doctorFee.setText("0000");
                    ll_doc_nature.setVisibility(View.GONE);
                    ll_pharmacy_detail_layout.setVisibility(View.GONE);
                    ll_lab_detail_layout.setVisibility(View.GONE);
                }

                break;
            case R.id.nature:
                getNatureByUserRoleModel = getNatureByUserRoleIdAdapter.getItem(position);
                appNatureId = getNatureByUserRoleModel.getAppNatureID();
                appNatureName = getNatureByUserRoleModel.getAppNature();
                if (selectedUserRoleId != 0 && appNatureId != 0) {
                    if (appNatureId == 33 || appNatureId == 32) {
                        Log.d("c_fee", appNatureId + " is 33");
                        rl_et_refund_amount.setVisibility(View.VISIBLE);
                        et_refund_amount.setVisibility(View.VISIBLE);
                        doctorFee.setText("0");
                        tvPayableAmount.setText("0.0");
                    } else if (appNatureId == 25) {
                        Log.d("c_fee", appNatureId + " is 25");
                        rl_et_refund_amount.setVisibility(View.VISIBLE);
                        et_refund_amount.setVisibility(View.VISIBLE);
                        doctorFee.setText("0");
                        tvPayableAmount.setText("0.0");
                    } else if (appNatureId != 25) {
                        Log.d("c_fee", appNatureId + " not 25");
                        getFeeFromAPI(selectedUserRoleId, appNatureId);
                        rl_et_refund_amount.setVisibility(View.GONE);
                        et_refund_amount.setVisibility(View.GONE);
                    }
                } else {
                    doctorFee.setText("0");
                    tvPayableAmount.setText("0.0");
                }
                break;
        }
    }

    private void showLabMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CentralizedBillingActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to select Lab Test");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (labOrderDetailModelArrayList != null) {
                    Log.d("clear", "yes");
                    labOrderDetailModelArrayList.clear();
                    savedLabOrderAdapter.notifyDataSetChanged();
                    Utility.setListViewHeightBasedOnItems(save_lab_order_listview);
                }
                getLabId();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LAB_STATUS = PRESC_LAB;
                ll_doc_nature.setVisibility(View.GONE);
                ll_pharmacy_detail_layout.setVisibility(View.GONE);
                ll_lab_detail_layout.setVisibility(View.VISIBLE);
                lab_saved_data_listview.setVisibility(View.VISIBLE);
                doctorFee.setText("0000");
                getLabTransactionData();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.show();
    }

    private void getLabId() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getLab(user_role_id, scheduleId, new Callback<GetLabIdModel[]>() {
            @Override
            public void success(GetLabIdModel[] getLabIdModels, Response response) {
                if (getLabIdModels.length != 0) {
                    showLabSsubtanentDialog(getLabIdModels);
                } else {
                    Toast.makeText(CentralizedBillingActivity.this, "No Record found for Lab", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showLabSsubtanentDialog(final GetLabIdModel[] getLabIdModels) {
        alertDialog = new AlertDialog.Builder(CentralizedBillingActivity.this);
        alertDialog.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.lab_subtanent_dialog, null);
        alertDialog.setView(convertView);
        ListView lvLabSubtanent = (ListView) convertView.findViewById(R.id.lvLabSubtanent);
        final LabSubtanentAdapter labSubtanentAdapter = new LabSubtanentAdapter(CentralizedBillingActivity.this,android.R.layout.simple_spinner_item,getLabIdModels);
        lvLabSubtanent.setAdapter(labSubtanentAdapter);
        lvLabSubtanent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                labId = labSubtanentAdapter.getItem(i).getSubTenantId();
                LAB_STATUS = SELF_LAB;
                ll_doc_nature.setVisibility(View.GONE);
                ll_pharmacy_detail_layout.setVisibility(View.GONE);
                ll_lab_detail_layout.setVisibility(View.VISIBLE);
                lab_saved_data_listview.setVisibility(View.GONE);
                doctorFee.setText("0000");
                new getLabData().execute();
                ad.dismiss();
            }
        });
        ad = alertDialog.show();
    }

    private class getLabData extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... args) {

            StringBuilder result = new StringBuilder();

            try {
                String serviceUrl = BuildConfig.API_URL + "/GetPackageLabGrpTestList?UserRoleID=" + user_role_id + "&LabID=" + labId;
                Log.d("serviceUrl", serviceUrl);
                URL url = new URL(serviceUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("authkeytoken", Helper.getAESCryptEncodeString());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                dismissLoadingDialog();
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("result", result);
            //Do something with the JSON string
            dismissLoadingDialog();
            labOrderDetailModels = new ArrayList<>();
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    JSONArray jsonTestArray = jsonData.getJSONArray("test");
                    for (int i = 0; i < jsonTestArray.length(); i++) {
                        JSONObject jsonTest = jsonTestArray.getJSONObject(i);
                        LabOrderDetailModel labOrderDetailModel = new LabOrderDetailModel();
                        labOrderDetailModel.setLabTestNature(EnumType.LabObjNature.kLabObjNatureTest);
                        labOrderDetailModel.setLabTestName(jsonTest.getString("testName"));
                        labOrderDetailModel.setLabTestId(jsonTest.getString("testId"));
                        labOrderDetailModel.setLabTestCost(jsonTest.getString("cost"));
                        labOrderDetailModel.setLabTestInstId(jsonTest.getString("testInsId"));
                        labOrderDetailModel.setLabTestInstruction(jsonTest.getString("testInstruction"));
                        labOrderDetailModel.setSelected(false);
                        labOrderDetailModels.add(labOrderDetailModel);
                    }
                    JSONArray jsonGroupArray = jsonData.getJSONArray("group");
                    for (int i = 0; i < jsonGroupArray.length(); i++) {
                        JSONObject jsonGroup = jsonGroupArray.getJSONObject(i);
                        LabOrderDetailModel labOrderDetailModel = new LabOrderDetailModel();
                        labOrderDetailModel.setLabTestNature(EnumType.LabObjNature.kLabObjNatureGroup);
                        labOrderDetailModel.setLabTestName(jsonGroup.getString("grpName"));
                        labOrderDetailModel.setLabTestId(jsonGroup.getString("grpId"));
                        labOrderDetailModel.setLabTestCost(jsonGroup.getString("cost"));
                        labOrderDetailModel.setSelected(false);
                        //labOrderDetailModel.setLabTestInstId(jsonGroup.getString("testInsId"));
                        //labOrderDetailModel.setLabTestInstruction(jsonGroup.getString("testInstruction"));
                        JSONArray jsonGroupTestArray = jsonGroup.getJSONArray("testList");
                        ArrayList<LabOrderDetailModel> groupTestList = new ArrayList<>();
                        for (int j = 0; j < jsonGroupTestArray.length(); j++) {
                            JSONObject jsonTest = jsonGroupTestArray.getJSONObject(j);
                            LabOrderDetailModel labOrderDetailModel1 = new LabOrderDetailModel();
                            labOrderDetailModel1.setLabTestNature(EnumType.LabObjNature.kLabObjNatureTest);
                            labOrderDetailModel1.setLabTestName(jsonTest.getString("testName"));
                            labOrderDetailModel1.setLabTestId(jsonTest.getString("testId"));
                            labOrderDetailModel1.setLabTestCost(jsonTest.getString("cost"));
                            labOrderDetailModel1.setLabTestInstId(jsonTest.getString("testInsId"));
                            labOrderDetailModel1.setLabTestInstruction(jsonTest.getString("testInstruction"));
                            labOrderDetailModel1.setSelected(false);
                            groupTestList.add(labOrderDetailModel1);
                        }
                        labOrderDetailModel.setChildren(groupTestList);
                        labOrderDetailModels.add(labOrderDetailModel);
                    }
                    JSONArray jsonPackageArray = jsonData.getJSONArray("package");
                    for (int i = 0; i < jsonPackageArray.length(); i++) {
                        JSONObject jsonPackage = jsonPackageArray.getJSONObject(i);
                        LabOrderDetailModel labOrderDetailModel = new LabOrderDetailModel();
                        labOrderDetailModel.setLabTestNature(EnumType.LabObjNature.kLabObjNaturePackage);
                        labOrderDetailModel.setLabTestName(jsonPackage.getString("pkgName"));
                        labOrderDetailModel.setLabTestId(jsonPackage.getString("pkgId"));
                        labOrderDetailModel.setLabTestCost(jsonPackage.getString("cost"));
                        labOrderDetailModel.setSelected(false);
                        //labOrderDetailModel.setLabTestInstId(jsonPackage.getString("testInsId"));
                        //labOrderDetailModel.setLabTestInstruction(jsonPackage.getString("testInstruction"));
                        JSONArray jsonPackageGroupArray = jsonPackage.getJSONArray("groupList");
                        ArrayList<LabOrderDetailModel> packageGroupList = new ArrayList<>();
                        for (int j = 0; j < jsonPackageGroupArray.length(); j++) {
                            JSONObject jsonGroup = jsonPackageGroupArray.getJSONObject(j);
                            LabOrderDetailModel labOrderDetailModel1 = new LabOrderDetailModel();
                            labOrderDetailModel1.setLabTestNature(EnumType.LabObjNature.kLabObjNatureGroup);
                            labOrderDetailModel1.setLabTestName(jsonGroup.getString("grpName"));
                            labOrderDetailModel1.setLabTestId(jsonGroup.getString("grpId"));
                            labOrderDetailModel1.setLabTestCost(jsonGroup.getString("cost"));
                            labOrderDetailModel1.setSelected(false);
                            //labOrderDetailModel1.setLabTestInstId(jsonGroup.getString("testInsId"));
                            //labOrderDetailModel1.setLabTestInstruction(jsonGroup.getString("testInstruction"));
                            JSONArray jsonGroupTestArray = jsonGroup.getJSONArray("testList");
                            ArrayList<LabOrderDetailModel> packageGroupTestList = new ArrayList<>();
                            for (int k = 0; k < jsonGroupTestArray.length(); k++) {
                                JSONObject jsonTest = jsonGroupTestArray.getJSONObject(k);
                                LabOrderDetailModel labOrderDetailModel2 = new LabOrderDetailModel();
                                labOrderDetailModel2.setLabTestNature(EnumType.LabObjNature.kLabObjNatureTest);
                                labOrderDetailModel2.setLabTestName(jsonTest.getString("testName"));
                                labOrderDetailModel2.setLabTestId(jsonTest.getString("testId"));
                                labOrderDetailModel2.setLabTestCost(jsonTest.getString("cost"));
                                labOrderDetailModel2.setLabTestInstId(jsonTest.getString("testInsId"));
                                labOrderDetailModel2.setLabTestInstruction(jsonTest.getString("testInstruction"));
                                labOrderDetailModel2.setSelected(false);
                                packageGroupTestList.add(labOrderDetailModel2);
                            }
                            packageGroupList.add(labOrderDetailModel1);
                            labOrderDetailModel1.setChildren(packageGroupTestList);
                        }
                        JSONArray jsonGroupTestArray = jsonPackage.getJSONArray("testList");
                        for (int l = 0; l < jsonGroupTestArray.length(); l++) {
                            JSONObject jsonTest = jsonGroupTestArray.getJSONObject(l);
                            LabOrderDetailModel labOrderDetailModel2 = new LabOrderDetailModel();
                            labOrderDetailModel2.setLabTestNature(EnumType.LabObjNature.kLabObjNatureTest);
                            labOrderDetailModel2.setLabTestName(jsonTest.getString("testName"));
                            labOrderDetailModel2.setLabTestId(jsonTest.getString("testId"));
                            labOrderDetailModel2.setLabTestCost(jsonTest.getString("cost"));
                            labOrderDetailModel2.setLabTestInstId(jsonTest.getString("testInsId"));
                            labOrderDetailModel2.setLabTestInstruction(jsonTest.getString("testInstruction"));
                            labOrderDetailModel2.setSelected(false);
                            packageGroupList.add(labOrderDetailModel2);
                        }
                        labOrderDetailModel.setChildren(packageGroupList);
                        //labOrderDetailModel.setChildren(packageTestList);
                        labOrderDetailModels.add(labOrderDetailModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("labOrderDetailModels", labOrderDetailModels.size() + "");
            }
            if (labOrderDetailModels.size() > 0) {
                showLabOrderDetailDialog(labOrderDetailModels);
            } else {
                Toast.makeText(CentralizedBillingActivity.this, "No Lab Test found", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showLabOrderDetailDialog(final ArrayList<LabOrderDetailModel> labOrderDetailModels) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CentralizedBillingActivity.this);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(CentralizedBillingActivity.this);
        View convertView = (View) inflater.inflate(R.layout.lab_main_order_dialog, null);
        builder.setView(convertView);
        final ExpandableListView lab_order_listview = (ExpandableListView) convertView.findViewById(R.id.lab_order_listview);
        TextView tvCancel = (TextView) convertView.findViewById(R.id.tvCancel);
        TextView tvSave = (TextView) convertView.findViewById(R.id.tvSave);
        final EditText etSearch = (EditText) convertView.findViewById(R.id.et_search);
        final AlertDialog mainLabDialog = builder.show();
        //PharmacyOrderDialogAdapter pharmacyOrderDialogAdapter = new PharmacyOrderDialogAdapter(mContext, pharmacyModel, followUpModelArrayList, pharmacyOrderDetailModels, tv_total_amount, btn_paynow, tv_check_uncheck, btn_ordernow,mainPresDialog);
        //pharmacy_order_listview.setAdapter(pharmacyOrderDialogAdapter);
        final int[] lastExpandedPosition = {-1};
        final LabOrderDetailExpandableAdapter labOrderDetailExpandableAdapter = new LabOrderDetailExpandableAdapter(CentralizedBillingActivity.this, labOrderDetailModels, mainLabDialog) {
            @Override
            public void OnIndicatorClick(boolean isExpanded, int position) {
                if (lastExpandedPosition[0] != -1 && position != lastExpandedPosition[0]) {
                    lab_order_listview.collapseGroup(lastExpandedPosition[0]);
                }
                lastExpandedPosition[0] = position;
                if (isExpanded) {
                    lab_order_listview.collapseGroup(position);
                } else {
                    lab_order_listview.expandGroup(position);
                }
            }

            @Override
            public void OnTextClick() {
                //Do whatever you want to do on text click
            }
        };
        lab_order_listview.setAdapter(labOrderDetailExpandableAdapter);
        mainLabDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mainLabDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        Window dialogWindow = mainLabDialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.alpha = 1.0f; // Transparency
        dialogWindow.setAttributes(lp);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainLabDialog.dismiss();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
                mainLabDialog.dismiss();
                labOrderDetailModelArrayList = new ArrayList<LabOrderDetailModel>();
                double labAmount = 0.0;
                Log.d("size",labOrderDetailModels.size()+"");
                for (int i = 0; i < labOrderDetailModels.size(); i++) {
                    if (labOrderDetailModels.get(i).isSelected()) {
                        labOrderDetailModelArrayList.add(labOrderDetailModels.get(i));
                        labAmount += Double.parseDouble(labOrderDetailModels.get(i).getLabTestCost());
                    }
                }
                doctorFee.setText(labAmount + "");
                if (discountModelArrayList.size() > 0) {
                    discountModelArrayList.clear();
                    discountAmountAdapter.notifyDataSetChanged();
                    Utility.setListViewHeightBasedOnItems(listviewDiscountAmount);
                    tvDiscountedAmount.setText("0");
                    tvPayableAmount.setText(labAmount + "");
                } else {
                    tvPayableAmount.setText(labAmount + "");
                }
                if (labOrderDetailModelArrayList.size() > 0) {
                    savedLabOrderAdapter = new SavedLabOrderAdapter(CentralizedBillingActivity.this, labOrderDetailModelArrayList);
                    save_lab_order_listview.setAdapter(savedLabOrderAdapter);
                    Utility.setListViewHeightBasedOnItems(save_lab_order_listview);
                } else {
                    Toast.makeText(CentralizedBillingActivity.this, "No Test Selected", Toast.LENGTH_LONG).show();
                }

            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                labOrderDetailExpandableAdapter.filterData(s.toString());
            }
        });
    }

    private void getLabTransactionData() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getLabTransactionsByMrnoSubtenant(user_role_id, tvMrno.getText().toString(), subTanentId, 0, new Callback<GetLabTransactionsByMrnoSubtenantModel>() {
            @Override
            public void success(GetLabTransactionsByMrnoSubtenantModel getLabTransactionsByMrnoSubtenantModel, Response response) {
                getLabTransactionsByMrnoSubtenantData = getLabTransactionsByMrnoSubtenantModel;
                labTransactionDataList = new ArrayList<>();
                for (int i = 0; i < getLabTransactionsByMrnoSubtenantData.getData().size(); i++) {
                    if (getLabTransactionsByMrnoSubtenantData.getData().get(i).getTransactions().size() != 0) {
                        labTransactionDataList.add(getLabTransactionsByMrnoSubtenantData.getData().get(i));
                    }
                }
                if (labTransactionDataList.size() > 0) {
                    labSavedDataAdapter = new LabSavedDataAdapter(CentralizedBillingActivity.this, labTransactionDataList);
                    lab_saved_data_listview.setAdapter(labSavedDataAdapter);
                    Utility.setListViewHeightBasedOnItems(lab_saved_data_listview);
                } else {
                    Toast.makeText(CentralizedBillingActivity.this, "No Pending Order Found.", Toast.LENGTH_LONG).show();
                }

                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });

    }

    private void getPharmacyTransactionData() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getPharmacyTransactionsByMrnoSubtenant(user_role_id, tvMrno.getText().toString(), subTanentId, 0, new Callback<GetPharmacyTransactionsByMrnoSubtenantModel>() {
            @Override
            public void success(GetPharmacyTransactionsByMrnoSubtenantModel getPharmacyTransactionsByMrnoSubtenantModel, Response response) {
                getPharmacyTransactionsByMrnoSubtenantData = getPharmacyTransactionsByMrnoSubtenantModel;
                pharmacyTransactionDataList = new ArrayList<>();
                for (int i = 0; i < getPharmacyTransactionsByMrnoSubtenantData.getData().size(); i++) {
                    if (getPharmacyTransactionsByMrnoSubtenantData.getData().get(i).getTransactions().size() != 0) {
                        pharmacyTransactionDataList.add(getPharmacyTransactionsByMrnoSubtenantData.getData().get(i));
                    }
                }
                if (pharmacyTransactionDataList.size() > 0) {
                    pharmacySavedDataAdapter = new PharmacySavedDataAdapter(CentralizedBillingActivity.this, pharmacyTransactionDataList);
                    pharmacy_saved_data_listview.setAdapter(pharmacySavedDataAdapter);
                    Utility.setListViewHeightBasedOnItems(pharmacy_saved_data_listview);
                } else {
                    Toast.makeText(CentralizedBillingActivity.this, "No Pending Order Found.", Toast.LENGTH_LONG).show();
                }

                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void getFeeFromAPI(int selectedUserRoleId, int appNatureId) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.feeFetch(selectedUserRoleId, appNatureId, subTanentId, new Callback<FeeFetch>() {
            @Override
            public void success(FeeFetch feeFetch, Response response) {
                if (feeFetch.getUserRoleId() != null) {
                    feeAmount = feeFetch.getFeeAmount().toString();
                    doctorFee.setText(feeAmount);
                    if (discountModelArrayList.size() > 0) {
                        discountModelArrayList.clear();
                        discountAmountAdapter.notifyDataSetChanged();
                        Utility.setListViewHeightBasedOnItems(listviewDiscountAmount);
                        tvDiscountedAmount.setText("0");
                        tvPayableAmount.setText(feeAmount);
                    } else {
                        tvPayableAmount.setText(feeAmount);
                    }
                } else {
                    doctorFee.setText("0");
                    tvPayableAmount.setText("0");
                    Toast.makeText(CentralizedBillingActivity.this, "Sorry Detail Not Available for this User", Toast.LENGTH_LONG).show();
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
        mCuraApplication.getInstance().mCuraEndPoint.lastBillDetail(selectedUserRoleId, tvMrno.getText().toString(), new Callback<LastBillDetailModel>() {
            @Override
            public void success(LastBillDetailModel lastBillDetailModel, Response response) {
                lastBillDetail = lastBillDetailModel;
                if (lastBillDetailModel.getAmount() != 0) {
                    setPatLastBillDetail(lastBillDetailModel);
                    tv_no_record_status.setVisibility(View.GONE);
                    tbl_pat_history.setVisibility(View.VISIBLE);
                } else {
                    tv_no_record_status.setVisibility(View.VISIBLE);
                    tbl_pat_history.setVisibility(View.GONE);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void setPatLastBillDetail(LastBillDetailModel lastBillDetailModel) {
        String date_time = null;
        if (lastBillDetailModel.getBillId() != null) {
            tv_BillNo.setText(lastBillDetailModel.getBillId() + "");
        } else {
            tbl_pat_history.setVisibility(View.GONE);
        }
        if (lastBillDetailModel.getPymtNature() != null) {
            tv_nature.setText(lastBillDetailModel.getPymtNature());
        } else {
            tbl_pat_history.setVisibility(View.GONE);
        }
        if (lastBillDetailModel.getAmount() != 0) {
            tv_amount.setText("Rs. " + lastBillDetailModel.getAmount());
        } else {
            tbl_pat_history.setVisibility(View.GONE);
        }
        if (lastBillDetailModel.getDocName() != null) {
            tv_DoctorName.setText(lastBillDetailModel.getDocName() + "");
        } else {
            tbl_pat_history.setVisibility(View.GONE);
        }
        if (lastBillDetailModel.getBillDate() != null) {
            date_time = lastBillDetailModel.getBillDate();
            String splitDateTime[] = date_time.split(" ");
            tv_date.setText(splitDateTime[0]);
            tv_time.setText(splitDateTime[1]);
        } else {
            tbl_pat_history.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.cash:
                paymentMode = "1";
                break;
            case R.id.card:
                paymentMode = "2";
                break;
            case R.id.cheque:
                paymentMode = "3";
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
