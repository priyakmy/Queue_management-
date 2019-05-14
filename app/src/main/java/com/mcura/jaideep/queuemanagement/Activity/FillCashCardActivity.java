package com.mcura.jaideep.queuemanagement.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.Adapter.CheckInAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.CheckInAdapter_v1;
import com.mcura.jaideep.queuemanagement.Adapter.FillCashCardAdapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.FillCashCardModel;
import com.mcura.jaideep.queuemanagement.Model.FillCashCardResponseModel;
import com.mcura.jaideep.queuemanagement.Model.GenerateCashCardModel;
import com.mcura.jaideep.queuemanagement.Model.GenerateCashCardModel_v1;
import com.mcura.jaideep.queuemanagement.Model.GetServiceListModel;
import com.mcura.jaideep.queuemanagement.Model.LocalBillModel;
import com.mcura.jaideep.queuemanagement.Model.MainModel;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.PostPaymentModel;
import com.mcura.jaideep.queuemanagement.Model.SearchCashCardModel;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.mcura.jaideep.queuemanagement.helper.Helper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FillCashCardActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {
    Button btn_fill_cashcard;
    RadioGroup payment_mode;
    private WebView myWebView;
    String paymentMode = "1";
    TextView tvHospitalId, tvMrno, tvAge, tvPatientName, tvPatientPhone, tvGender;
    SearchPatientModel patientSearchModel;
    AlertDialog.Builder alertDialog;
    AlertDialog ad;
    CheckInAdapter_v1 searchPatientAdapter;
    public MCuraApplication mCuraApplication;
    ProgressDialog progressDialog;
    public PatientSearchModel[] patientSearchModelsArray;
    //ImageButton ib_billing;
    TextView tv_billing;
    SharedPreferences mSharedPreference;
    String fillType[] = {"Fill CashCard", "Refund Amount"};
    int fillTypeId[] = {1, 2};
    private Spinner spinner_fillType;
    EditText et_search_key;
    RelativeLayout ib_search_icon;
    int user_role_id, subTanentId;
    private int firstIndex;
    private String query;
    private ArrayList<Datum> dataList;
    private boolean flag_loading;
    private int cashCardId = 0;
    private int frontOfficeUserRoleId;
    private EditText amount;
    private int userAmount;
    String subTanantName, subTanantAddress, subTanantContact;
    private String feeGenerateStatus = "Amount deposited in cashcard";
    private TextView appointment;
    private TextView queue_mgmt;
    private Datum datum;
    private String hId;
    FillCashCardModel fillCashCardModel;
    ArrayList<FillCashCardModel> fillCashCardModelArrayList;
    private FillCashCardAdapter fillCashCardAdapter;
    private int fillCashCardTypeId;
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private NfcAdapter mNfcAdapter;
    private int nfcMrno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_cash_card);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
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
        fillCashCardModelArrayList = new ArrayList<>();
        for(int i=0 ; i<fillType.length ; i++){
            fillCashCardModel = new FillCashCardModel();
            fillCashCardModel.setFillType(fillType[i]);
            fillCashCardModel.setFillTypeId(fillTypeId[i]);
            fillCashCardModelArrayList.add(fillCashCardModel);
        }
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
                        Toast.makeText(FillCashCardActivity.this, "This Card is not for this Hospital", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(FillCashCardActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(FillCashCardActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
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

        String subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        user_role_id = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        subTanentId = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);

        subTanantName = mSharedPreference.getString(Constant.SUBTANENT_NAME,"");
        subTanantAddress = mSharedPreference.getString(Constant.SUBTANENT_ADD,"");
        subTanantContact = mSharedPreference.getString(Constant.SUBTANENT_CONTACT,"");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        ImageView hospital_logo = (ImageView) mToolbar.findViewById(R.id.hospital_logo);
        Picasso.with(FillCashCardActivity.this).load(subtanentImagePath).into(hospital_logo);
        spinner_fillType = (Spinner) findViewById(R.id.spinner_fillType);
        et_search_key = (EditText) findViewById(R.id.et_search_key);
        ib_search_icon = (RelativeLayout) findViewById(R.id.ib_search_icon);
        amount = (EditText) findViewById(R.id.amount);
        tvHospitalId = (TextView) findViewById(R.id.hospital_id);
        tvMrno = (TextView) findViewById(R.id.bill_payment_activity_mrno);
        tvAge = (TextView) findViewById(R.id.bill_payment_activity_patient_age);
        tvPatientName = (TextView) findViewById(R.id.bill_payment_activity_patient_name);
        tvPatientPhone = (TextView) findViewById(R.id.bill_payment_activity_phone);
        tvGender = (TextView) findViewById(R.id.bill_payment_activity_patient_gender);
        payment_mode = (RadioGroup) findViewById(R.id.payment_mode);
        btn_fill_cashcard = (Button) findViewById(R.id.btn_fill_cashcard);
        //ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fillType);

        fillCashCardAdapter = new FillCashCardAdapter(FillCashCardActivity.this,fillCashCardModelArrayList);
        //aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_fillType.setAdapter(fillCashCardAdapter);
        appointment = (TextView) findViewById(R.id.appointment);
        queue_mgmt = (TextView) findViewById(R.id.queue_mgmt);
        tv_billing = (TextView) findViewById(R.id.tv_billing);
        tv_billing.setOnClickListener(this);
        appointment.setOnClickListener(this);
        queue_mgmt.setOnClickListener(this);
        ib_search_icon.setOnClickListener(this);
        payment_mode.setOnCheckedChangeListener(this);
        btn_fill_cashcard.setOnClickListener(this);
        spinner_fillType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillCashCardTypeId = fillCashCardAdapter.getItem(position).getFillTypeId();

                //Toast.makeText(FillCashCardActivity.this,fillCashCardTypeId+"",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //setHospitalDetail();
    }

    /*private void setHospitalDetail() {
        if (subTanentId == 500) {
            subTanantName = "BLK Hospital";
            subTanantAddress = "Pusa Road, Rajinder Nagar, New Delhi, Delhi 110005";
            subTanantContact = "Tel:+91-11-30403040";
        } else if (subTanentId == 243) {
            subTanantName = "Sir Ganga Ram Hospital";
            subTanantAddress = "Rajinder Nagar, New Delhi-60";
            subTanantContact = "Tel:+91-11-25750000, 42254000, Fax:+91-1142251755";
        } else if (subTanentId == 525) {
            subTanantName = "U K Nursing Home";
            subTanantAddress = "M-1, Main Road, Vikas Puri, Delhi, 110018";
            subTanantContact = "Tel:+91-11-40955555";
        } else if (subTanentId == 528) {
            subTanantName = "KIMS Hospitals";
            subTanantAddress = "1-112 / 86, Survey No 55/ EE, Kondapur Village, Serilingampally Mandal, Hyderabad, Telangana 500084";
            subTanantContact = "Tel:+91-88973 14141";
        }else if (subTanentId == 547) {
            subTanantName = "KIMS Hospitals";
            subTanantAddress = "1-112 / 86, Survey No 55/ EE, Kondapur Village, Serilingampally Mandal, Hyderabad, Telangana 500084";
            subTanantContact = "Tel:+91-88973 14141";
        } else if (subTanentId == 531) {
            subTanantName = "Fernandez Stork Home";
            subTanantAddress = "8-2-698, Road No. 12, Banjara Hills,Next to Seating World, Hyderabad, Telangana 500034";
            subTanantContact = "Tel:+91-40 4780 7300";
        }else if (subTanentId == 557) {
            subTanantName = "MVR Cancer Centre";
            subTanantAddress = "CP 7/504-A, Vellalasseri, REC (via), Calicut, Kerala 673601";
            subTanantContact = "Tel:+91-495 228 9500";
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appointment:
                startActivity(new Intent(FillCashCardActivity.this, CalendarActivity.class));
                finish();
                break;
            case R.id.queue_mgmt:
                startActivity(new Intent(FillCashCardActivity.this, QueueStatusActivity.class));
                finish();
                break;
            case R.id.btn_fill_cashcard:
                if (!TextUtils.isEmpty(amount.getText().toString())) {
                    userAmount = Integer.parseInt(amount.getText().toString().trim());
                    if (tvHospitalId.getText().equals("0000") || TextUtils.isEmpty(tvHospitalId.getText().toString())) {
                        alertDialog = new AlertDialog.Builder(FillCashCardActivity.this);
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
                                    updateHospitalId(datum,et_update_hospital_no.getText().toString());
                                }else{
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
                        getCashCardId(datum);
                    }
                } else {
                    amount.setError("Please fill amount.");
                }
                break;
            case R.id.tv_billing:
                startActivity(new Intent(FillCashCardActivity.this, CentralizedBillingActivity.class));
                finish();
                break;
            case R.id.ib_search_icon:
                String searchKey = et_search_key.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        et_search_key.getWindowToken(), 0);
                //new RequestTask().execute("http://test.tn.mcura.com/health_dev.svc/Json/SearchPatient?UserRoleID=2110&Searchkey=t&sub_tenant_id=500");
                if (searchKey.length() > 0) {
                    /*getPatientSearchDetail(searchKey);*/
                    firstIndex = 2;
                    query = searchKey;
                    patientSearchModel = new SearchPatientModel();
                    dataList = new ArrayList<>();
                    getPatientSearchDetail(this.query);
                } else {
                    Toast.makeText(FillCashCardActivity.this, "Enter any key for searching", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void postFillCashCasd() {
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("CashCardId", cashCardId);
        obj.addProperty("Description", "");
        obj.addProperty("Credit", 1);
        obj.addProperty("Debit", 0);
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("Amount", userAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("HIS_BillNo", 0);
        obj.addProperty("amountType", fillCashCardTypeId);

        mCuraApplication.getInstance().mCuraEndPoint.fillCashCard_v1(obj, new Callback<FillCashCardResponseModel>() {
            @Override
            public void success(FillCashCardResponseModel fillCashCardResponseModel, Response response) {
                if(fillCashCardResponseModel.getStatusId()==EnumType.FillCashCardType.mFillCashCardSuccessfull.getID()){
                    feeGenerateStatus = "Amount deposited in cashcard";
                    printCashCardBill();
                }else if(fillCashCardResponseModel.getStatusId()==EnumType.FillCashCardType.mRefundCashCardSuccessfull.getID()){
                    feeGenerateStatus = "Amount Refunded";
                    printCashCardBill();
                }else if(fillCashCardResponseModel.getStatusId()==EnumType.FillCashCardType.mErrorInFillingRefund.getID()){
                    showDialog(fillCashCardResponseModel.getMsg());
                }else if(fillCashCardResponseModel.getStatusId()==EnumType.FillCashCardType.mProvideData.getID()){
                    showDialog(fillCashCardResponseModel.getMsg());
                }else if(fillCashCardResponseModel.getStatusId()==EnumType.FillCashCardType.mInsufficientBalance.getID()){
                    showDialog(fillCashCardResponseModel.getMsg());
                }else if(fillCashCardResponseModel.getStatusId()==EnumType.FillCashCardType.mRemainingBalance.getID()){
                    showDialog(fillCashCardResponseModel.getMsg());
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GenerateCashCardModel", "false");
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
                    Toast.makeText(FillCashCardActivity.this, "No More Record Found", Toast.LENGTH_SHORT).show();
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
        alertDialog = new AlertDialog.Builder(FillCashCardActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.search_patient_row_layout, null);
        alertDialog.setView(convertView);
        //alertDialog.setTitle("List");
        ListView lv = (ListView) convertView.findViewById(R.id.search_patient_listview);

        searchPatientAdapter = new CheckInAdapter_v1(FillCashCardActivity.this,
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
            progressDialog = new ProgressDialog(FillCashCardActivity.this);
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
        datum = searchPatientAdapter.getItem(position);
        setPatientDetail(datum);
        ad.dismiss();
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
                    getCashCardId(patientSearchModel);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void getCashCardId(final Datum datum) {
        showLoadingDialog();
        /*if (datum.getHospitalNo().equals("")) {
            hId = "0";
        } else {
            hId = datum.getHospitalNo();
        }*/
        mCuraApplication.getInstance().mCuraEndPoint.searchCashCard(Integer.parseInt(tvMrno.getText().toString()), subTanentId, tvHospitalId.getText().toString(), new Callback<SearchCashCardModel>() {
            @Override
            public void success(SearchCashCardModel searchCashCardModel, Response response) {
                if (searchCashCardModel.getCashCardID() != 0) {
                    cashCardId = searchCashCardModel.getCashCardID();
                    postFillCashCasd();
                } else {
                    postGenerateCashCardId(datum);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void postGenerateCashCardId(Datum datum) {
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("Mrno", tvMrno.getText().toString());
        obj.addProperty("HospitalNo", tvHospitalId.getText().toString());
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("IssuedBy", frontOfficeUserRoleId);

        mCuraApplication.getInstance().mCuraEndPoint.generateCashCard(obj, new Callback<GenerateCashCardModel_v1>() {
            @Override
            public void success(GenerateCashCardModel_v1 generateCashCardModel, Response response) {
                Log.d("success", "success");
                if (generateCashCardModel.getStatus()) {
                    cashCardId = Integer.parseInt(generateCashCardModel.getID());
                    postFillCashCasd();
                } else {

                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("failure", "failure");
                dismissLoadingDialog();
            }
        });
    }

    public void setPatientDetail(Datum patientSearchModel) {
        tvHospitalId.setText(patientSearchModel.getHospitalNo().toString());
        tvPatientName.setText(patientSearchModel.getPatName().toString());
        tvMrno.setText(patientSearchModel.getMrNo().toString());
        if (patientSearchModel.getGenderId() == 1) {
            tvGender.setText("Male");
        } else if (patientSearchModel.getGenderId() == 2) {
            tvGender.setText("Female");
        }
        String mobileNo = patientSearchModel.getMobileNo().toString();
        tvPatientPhone.setText(mobileNo);
        String dob = getDob(patientSearchModel.getDob().toString());
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
        String ageS = ageInt.toString();
        System.out.println("age-->" + age);
        return ageS;
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

    public void printCashCardBill() {
        Log.d("status", "print");
        WebView webView = new WebView(FillCashCardActivity.this);
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
                "  <tr>\n" +
                "    <td colspan=\"2\"><h4>" + subTanantName + "</h4>\n" +
                "\t<p> " + subTanantAddress + "</br>\n" +
                "    " + subTanantContact + "</br></p>\n" +
                "  <h3 style=\"margin-bottom:20px;\"> CashCard Receipt</h3>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    \n" +
                " <tr>\n" +
                "    \t<td colspan=\"1\">\n" +
                "   \t    <div class=\"dateOuter\"><div class=\"date\">Date</div> <div class=\"dateValue\"><input type=\"text\" class=\"valueInput\" value=\"" + Helper.getCurrentDataAndTime() + "\"></div></div></td>\n" +
                "    </tr>\n" +
                "    \n" +
                "      <tr>\n" +
                "    \t<td colspan=\"1\"><div class=\"patientnameOuter\"><div class=\"patientname\">Patient Name</div>\n" +
                "   \t    <div class=\"patientnameValue\"><input type=\"text\" class=\"valueInput\" value=\"" + tvPatientName.getText().toString() + "\"></div></div> \n" +
                "        \n" +
                "        <div class=\"hospitalIdOuter\"><div class=\"hospitalId\">Hospital ID</div>\n" +
                "        <div class=\"hospitalIdValue\"><input type=\"text\" class=\"valueInput\" value=\"" + tvHospitalId.getText().toString() + "\"></div></div>\n" +
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
                "    <td style=\"width:120px;\">Rs. " + userAmount + "</td>\n" +
                "  </tr>\n" +
                "\n" +
                "\n" +
                "    <tr>\n" +
                "    <td>&nbsp; </td>\n" +
                "    <td>&nbsp;</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td style=\"border-top:1px #333 solid; text-align:right; padding:7px 10px;\"><strong>Total Amount</strong></td>\n" +
                "    <td style=\"border-top:1px #333 solid; padding:7px 10px;\"><strong>Rs. " + userAmount + "</strong></td>\n" +
                "  </tr>\n" +
                "   \n" +
                "    <tr>\n" +
                "    <td colspan=\"3\"><div class=\"amoutnwordsOuter\">\n" +
                "    <div class=\"amountWords\">Amount in words:</div>\n" +
                "    <div class=\"amountwordsValue\"><input type=\"text\" class=\"valueInput\" value = \"" + Helper.convert(userAmount) + " Only\"></div>\n" +
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
}
