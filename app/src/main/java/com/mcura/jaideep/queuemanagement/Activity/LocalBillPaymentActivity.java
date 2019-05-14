package com.mcura.jaideep.queuemanagement.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mcura.jaideep.queuemanagement.Adapter.DoctorDepartmentAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.DoctorListAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.DoctorNatureAdapter;
import com.mcura.jaideep.queuemanagement.Model.DepartmentModel;
import com.mcura.jaideep.queuemanagement.Model.DoctorList;
import com.mcura.jaideep.queuemanagement.Model.DoctorNature;
import com.mcura.jaideep.queuemanagement.Model.Doctor_Fee;
import com.mcura.jaideep.queuemanagement.Model.LocalBillModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.SqliteHelper.SqlLiteDbHelper;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.Helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocalBillPaymentActivity extends AppCompatActivity implements View.OnClickListener,SearchView.OnQueryTextListener {
    String feeGenerateStatus = "OPD Consultation Fee",feeAmount;
    String serviceType = "1",time;
    String dept_Id;
    ListView lv;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    private EditText et_hospital_id,et_patname,doctor_amount;
    private Button submit;
    String paymentMode="cash";
    RadioGroup payment_mode ;
    SqlLiteDbHelper dbHelper;
    DoctorList doctorList ;
    String appNatureName;
    List<DoctorList> doctorLists;
    DoctorNature doctorNature;
    List<DoctorNature> doctorNatureLists;
    List<DepartmentModel> departmentLists;
    List<LocalBillModel> billDetailModelList;
    LocalBillModel billDetailModel;
    Doctor_Fee doctorFee;
    List<Doctor_Fee> doctorFeeLists;
    TextView spinnerDoctorList;
    Spinner spinnerNatureList,spinnerDoctorDepartment;
    DoctorListAdapter doctorListAdapter;
    DoctorNatureAdapter doctorNatureAdapter;
    String user_role_id,appNatureId,department,doctor_name;
    DoctorDepartmentAdapter doctorDepartmentAdapter;
    SharedPreferences mSharedPreferences;
    private WebView myWebView;
    private TextView tv_logout;
    String completeDate;
    int year,month,date;
    int frontOfficeUserRoleId;
    String deptId="0",billno,hospital_no,patient_name,amount;
    int subTanentId;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_bill_payment);
        initView();
        mSharedPreferences = this.getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        subTanentId = mSharedPreferences.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        frontOfficeUserRoleId = mSharedPreferences.getInt(Constant.USER_ROLE_ID_KEY, 0);
        completeDate = Helper.getCurrentDataAndTime();
        billDetailModel = new LocalBillModel();
        doctorNatureLists = new ArrayList<>();
        doctorFeeLists = new ArrayList<>();
        billDetailModelList = new ArrayList<>();
        dbHelper = new SqlLiteDbHelper(this);
        dbHelper.openDataBase();
        doctorList = new DoctorList();

        tv_logout.setOnClickListener(this);
        spinnerDoctorList.setOnClickListener(this);
        submit.setOnClickListener(this);
        doctorNature = new DoctorNature();

        getNatureList();
        getDepartmentList();
        spinnerNatureList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DoctorNature model = doctorNatureAdapter.getItem(position);
                appNatureId = model.getApp_nature_id();
                appNatureName = model.getApp_nature();
                if(appNatureId.equals("25")){
                    flag = true;
                    doctor_amount.setText("");
                }else{
                    flag = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        doctor_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (flag) {
                    int fee = 0;
                    int refundFee = 0;
                    List<LocalBillModel> modelList = dbHelper.getvalidateBillDetail(user_role_id + "", hospital_no, Helper.getCurrentDataAndTime());
                    for (int i = 0; i < modelList.size(); i++) {
                        if (modelList.get(i).getFee_nature().equals("Refund")) {
                            refundFee += Integer.parseInt(modelList.get(i).getFee());

                        } else {
                            fee += Integer.parseInt(modelList.get(i).getFee());
                        }
                    }
                    Log.d("fee", refundFee + "");
                    Log.d("fee", fee + "");
                    if (doctor_amount.getText().length() > 0) {
                        if (fee >= (refundFee + Integer.parseInt(doctor_amount.getText().toString()))) {
                            //Toast.makeText(BillPaymentActivity.this,"true",Toast.LENGTH_SHORT).show();
                        } else {
                            doctor_amount.setText("");
                            doctor_amount.setError("Amount cannot be greater than doctor fee");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        spinnerDoctorDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DepartmentModel model = doctorDepartmentAdapter.getItem(position);
                department = model.getDept_name();
                dept_Id = model.getDept_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
    }
    public void initView(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tv_logout = (TextView) mToolbar.findViewById(R.id.tv_logout);
        spinnerDoctorList = (TextView) findViewById(R.id.doctor_name);
        spinnerNatureList = (Spinner) findViewById(R.id.doctor_nature);
        spinnerDoctorDepartment = (Spinner) findViewById(R.id.doctor_department);
        payment_mode = (RadioGroup) findViewById(R.id.payment_mode);
        et_hospital_id = (EditText) findViewById(R.id.et_hospital_id);
        et_patname = (EditText) findViewById(R.id.et_patname);
        doctor_amount = (EditText) findViewById(R.id.doctor_amount);
        submit = (Button) findViewById(R.id.submit);
    }
    public void localPrintBill(LocalBillModel localBillModel){
        String billno;
        if (localBillModel.getBill_no().equals("")) {
            billno = localBillModel.getTempBillNo();
        } else {
            billno = localBillModel.getBill_no();
        }
        WebView webView = new WebView(LocalBillPaymentActivity.this);
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
                "    <td colspan=\"2\"><h4>Sir Ganga Ram Hospital</h4>\n" +
                "\t<p> Rajinder Nagar, New Delhi-60</br>\n" +
                "    Tel:+91-11-25750000, 42254000, Fax:+91-1142251755</br></p>\n" +
                "  <h3 style=\"margin-bottom:20px;\"> OPD Consultation Receipt</h3>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    \n" +
                " <tr>\n" +
                "    \t<td colspan=\"2\"><div class=\"billnoOuter\"><div class=\"billno\">Bill No.</div>\n" +
                "   \t    <div class=\"billnoValue\"><input type=\"text\" class=\"valueInput\" value = \"" + billno + "\"></div></div> <div class=\"dateOuter\"><div class=\"date\">Date</div> <div class=\"dateValue\"><input type=\"text\" class=\"valueInput\" value=\"" + completeDate +" | "+localBillModel.getTime() + "\"></div></div></td>\n" +
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
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = getString(R.string.app_name) + "Bill Print";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Filter filter = doctorListAdapter.getFilter();
        if (TextUtils.isEmpty(newText)) {
            filter.filter("");
        } else {
            filter.filter(newText);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.submit:
                dataValidation();
                break;
            case R.id.tv_logout:
                showConfirmDialog(getString(R.string.logout_msg), 2);
                break;
            case R.id.doctor_name:
                showDoctorListDialog();
                break;
        }
    }
    private void getNatureList(){
        doctorNatureLists = dbHelper.Get_DoctorNature(user_role_id);
        doctorNatureAdapter = new DoctorNatureAdapter(LocalBillPaymentActivity.this,doctorNatureLists);
        spinnerNatureList.setAdapter(doctorNatureAdapter);
    }
    private void getDepartmentList(){
        if(!deptId.equals("0")){
            departmentLists = dbHelper.Get_DoctorDepartmentLocal(deptId);
            doctorDepartmentAdapter = new DoctorDepartmentAdapter(LocalBillPaymentActivity.this,departmentLists);
            spinnerDoctorDepartment.setAdapter(doctorDepartmentAdapter);
        }else{
            departmentLists = dbHelper.Get_DoctorDepartmentLocal("0");
            doctorDepartmentAdapter = new DoctorDepartmentAdapter(LocalBillPaymentActivity.this,departmentLists);
            spinnerDoctorDepartment.setAdapter(doctorDepartmentAdapter);
        }
    }
    private void showConfirmDialog(String msg, final int flag){
        AlertDialog.Builder builder = new AlertDialog.Builder(LocalBillPaymentActivity.this);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(flag == 1){
                    storeDataAndGenerateBill();
                }else if(flag == 2){
                    logout();
                }else if(flag == 3){
                    finish();
                }
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
    private void storeDataAndGenerateBill(){
        if (!appNatureId.equals("0")) {
            if (doctor_amount.getText().length() != 0) {
                feeAmount = doctor_amount.getText().toString();
                feeGenerateStatus = "Refund Amount";
            } else {
                feeAmount = doctor_amount.getText().toString();
                feeGenerateStatus = "OPD Consultation Fee";
            }
            LocalBillModel localBillModel = dbHelper.patBillStatus(user_role_id + "", completeDate, "", patient_name, feeAmount,appNatureId+"");
            if (localBillModel != null) {
                if (localBillModel.getBill_status().equals("0")) {
                    Log.d("localBillModel", localBillModel.getHospital_id() + "");
                    localPrintBill(localBillModel);
                    Toast.makeText(LocalBillPaymentActivity.this, "Duplicate Bill", Toast.LENGTH_LONG).show();
                }
            } else {
                int serial_no = dbHelper.getMaxSerial_no()+1;
                Log.d("serial_no",serial_no+"");
                String timestamp = user_role_id+""+serial_no;
                time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                //boolean status = dbHelper.insertBillDetail(billno, hospital_no, patient_name, user_role_id, appNatureId, amount, department, paymentMode, "243", collectedbyUserRoleId, doctor_name);
                boolean insertStatus = dbHelper.insertBillDetail(doctor_name, user_role_id + "", completeDate, "", patient_name, amount, appNatureName, "0", "", "", department, hospital_no, frontOfficeUserRoleId + "", timestamp, subTanentId + "", appNatureId + "", paymentMode + "", serviceType + "",serial_no,time,"ON");
                if (insertStatus) {
                    billDetailModel = dbHelper.patBillStatusLocal(user_role_id, completeDate, hospital_no, patient_name, amount, appNatureId);
                    localPrintBill(billDetailModel);
                }
            }
        } else {
            Toast.makeText(LocalBillPaymentActivity.this, "Please Select Nature", Toast.LENGTH_LONG).show();
        }
    }

    private void logout(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        //editor.remove(Constants.FRONTOFFICE_USER_ROLE_ID);
        editor.commit();
        startActivity(new Intent(LocalBillPaymentActivity.this,LoginActivity.class));
        finish();
    }
    private void showDoctorListDialog(){
        builder = new AlertDialog.Builder(LocalBillPaymentActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.search_doctor_dialog, null);
        builder.setView(convertView);
        SearchView search_doctor = (SearchView) convertView.findViewById(R.id.search_doctor);
        search_doctor.setIconified(false);
        search_doctor.setIconifiedByDefault(false);
        search_doctor.setOnQueryTextListener(LocalBillPaymentActivity.this);
        search_doctor.setQueryHint("Search Here");
        lv = (ListView) convertView.findViewById(R.id.doctor_list);
        lv.setTextFilterEnabled(true);
        doctorLists = new ArrayList<>();
        doctorLists = dbHelper.Get_ContactDetails();
        doctorListAdapter = new DoctorListAdapter(LocalBillPaymentActivity.this,doctorLists);
        lv.setAdapter(doctorListAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoctorList model = doctorListAdapter.getItem(position);
                user_role_id = model.getUser_role_id();
                doctor_name = model.getDocName();
                deptId = model.getDept_id();
                spinnerDoctorList.setText(doctor_name);
                Log.d("DoctorName", model.getDocName());
                getNatureList();
                getDepartmentList();
                dialog.dismiss();
            }
        });

        dialog = builder.show();
    }
    private void dataValidation(){
        String timestamp = Helper.getCurrentTimestamp();
        timestamp = timestamp.replace("-","");
        timestamp = timestamp.replace(":","");
        timestamp = timestamp.replace(".","");
        timestamp = timestamp.replace(" ","");
        System.out.println(timestamp);
        billno = user_role_id+timestamp;
        hospital_no = et_hospital_id.getText().toString().trim();
        patient_name = et_patname.getText().toString().trim();
        amount = doctor_amount.getText().toString().trim();
        if(hospital_no.length()!=0){
            if(patient_name.length()!=0){
                if(!spinnerDoctorList.getText().toString().equals("Select Doctor")){
                    if(amount.length()!=0){
                        if(!appNatureId.equals("0")){
                            if(!dept_Id.equals("0")){
                                showConfirmDialog(getString(R.string.save_data_msg),1);
                            }else{
                                Toast.makeText(LocalBillPaymentActivity.this, "Select Department", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(LocalBillPaymentActivity.this,"Select Nature",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        doctor_amount.setError("Enter amount");
                    }

                }else{
                    Toast.makeText(LocalBillPaymentActivity.this,"Select Doctor",Toast.LENGTH_SHORT).show();
                }
            }else{
                et_patname.setError("Enter  Patient name");
            }
        }else{
            et_hospital_id.setError("Enter hospital Id");
        }
    }

    @Override
    public void onBackPressed() {
        showConfirmDialog(getString(R.string.exit_msg),3);
    }
}
