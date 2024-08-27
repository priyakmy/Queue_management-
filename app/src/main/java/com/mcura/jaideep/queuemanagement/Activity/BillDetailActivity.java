package com.mcura.jaideep.queuemanagement.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.mcura.jaideep.queuemanagement.Adapter.BillDetailAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.DoctorSpinnerAdapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.DocAccountListModel;
import com.mcura.jaideep.queuemanagement.Model.DoctorListModel;
import com.mcura.jaideep.queuemanagement.Model.SearchHospital;
import com.mcura.jaideep.queuemanagement.Model.UserInfoModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.squareup.picasso.Picasso;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BillDetailActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    ListView lv;
    int subTanentId;
    int AssisUserRoleId;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    private AutoCompleteTextView search_by_doctor;
    public MCuraApplication mCuraApplication;
    private ProgressDialog progressDialog;
    private Toolbar mToolbar;
    private DatePickerDialog fromDatePickerDialog,toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private SharedPreferences mSharedPreference;
    private ListView patientListView;
    private BillDetailAdapter billDetailAdapter;
    private TextView tvFromTime,tvToTime,total_amount,tv_amount;
    int userRoleId;
    Calendar now,fromDate,toDate;
    private WebView myWebView;
    DocAccountListModel[] docAccountListModelsArray;
    String docName;
    int totalCollection;
    Drawable imageUri ;
    int year,month,date;
    String completeDate;
    String htmlString;
    DoctorListModel[] doctorArray;
    TextView doctor_name;
    DoctorSpinnerAdapter doctorSpinnerAdapter;
    ImageView bill_printer;
    TextView totalBills;
    String subtanentImagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        completeDate = date + "/" + month + "/" + year;
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        if(!subtanentImagePath.equals("default")){
        }else{
            Uri path = Uri.parse("android.resource://com.mcura.jaideep.queuemanagement/" + R.drawable.kims_logo);
            subtanentImagePath = path.toString();
        }
        imageUri = this.getResources().getDrawable(R.drawable.sgrh);
        //userRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        userRoleId = getIntent().getIntExtra("userRoleId", 0);
        //docName = mSharedPreference.getString(Constant.UNAME_KEY, "Undefine");

//        Log.d("docName", docName);
        getHospitalFromAPI();

        initView();
        getDoctorInfo();

    }

    public void initView() {
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        now = Calendar.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setSubtitle("");
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(BillDetailActivity.this, CentralizedBillingActivity.class));
            }
        });
        totalBills = (TextView) findViewById(R.id.tv_total_bills);
        bill_printer= (ImageView) findViewById(R.id.bill_printer);
        doctor_name = (TextView) findViewById(R.id.doctor_name);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        total_amount = (TextView) findViewById(R.id.total_amount);
        tvFromTime = (TextView) findViewById(R.id.tv_fromTime);
        tvFromTime.setText(dateFormatter.format(now.getTime()));
        tvToTime = (TextView) findViewById(R.id.tv_toTime);
        tvToTime.setText(dateFormatter.format(now.getTime()));
        tvFromTime.setOnClickListener(this);
        tvToTime.setOnClickListener(this);
        patientListView = (ListView) findViewById(R.id.patient_listview);
        doctor_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(BillDetailActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.search_doctor_dialog, null);
                builder.setView(convertView);
                SearchView search_doctor = (SearchView) convertView.findViewById(R.id.search_doctor);
                search_doctor.setIconified(false);
                search_doctor.setIconifiedByDefault(false);
                search_doctor.setOnQueryTextListener(BillDetailActivity.this);
                search_doctor.setQueryHint("Search Here");
                lv = (ListView) convertView.findViewById(R.id.doctor_list);
                lv.setTextFilterEnabled(true);
                doctorSpinnerAdapter = new DoctorSpinnerAdapter(BillDetailActivity.this,
                        android.R.layout.simple_spinner_item,
                        doctorArray);
                lv.setAdapter(doctorSpinnerAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        userRoleId = doctorSpinnerAdapter.getItem(position).getUserRoleId();
                        //editor.putString(Constant.LOGIN_NAME_KEY, doctorSpinnerAdapter.getItem(position).getUserName());
                        //Toast.makeText(LoginActivity.this,userRoleId+"===userroleid",Toast.LENGTH_LONG).show();
                        //editor.putInt(Constant.ADDRESS_ID_KEY, doctorSpinnerAdapter.getItem(position).getAddressId());
                        // editor.putInt(Constant.CONTACT_ID_KEY, doctorSpinnerAdapter.getItem(position).getContactId());
                        //editor.putString(Constant.DOB_KEY, doctorSpinnerAdapter.getItem(position).getDob());
                        //editor.putInt(Constant.GENDER_ID_KEY, doctorSpinnerAdapter.getItem(position).getGenderId());
                        //editor.putString(Constant.UNAME_KEY, doctorSpinnerAdapter.getItem(position).getUname());
                        //editor.putInt(Constant.USER_ROLE_ID, doctorSpinnerAdapter.getItem(position).getUserRoleId());
                        //editor.apply();
                        doctor_name.setText(doctorSpinnerAdapter.getItem(position).getUserName());
                        getDoctorInfo();
                        dialog.dismiss();
                        //getScheduleData();
                    }
                });

                    dialog = builder.show();
            }
        });
        bill_printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arr = new ArrayList<String>();
                for (int i = 0,sno=1; i < docAccountListModelsArray.length; i++,sno++) {
                    String gender = null;
                    if (docAccountListModelsArray[i].getGenderId() == 1) {
                        gender = "M";
                    } else if (docAccountListModelsArray[i].getGenderId() == 2) {
                        gender = "F";
                    }
                    arr.add("<tr>");
                    arr.add("<td class = \"sno\">");
                    arr.add("<div>");
                    arr.add(sno+"");
                    arr.add("</div>");
                    arr.add("</td>");
                    arr.add("<td class = \"hospitalid\">");
                    arr.add("<div>");
                    arr.add(docAccountListModelsArray[i].getHospitalNo()+"<br>");
                    arr.add("</div>");
                    arr.add("</td>");
                    arr.add("<td>");
                    arr.add("<div class=\"patientDetails\">");
                    arr.add("<strong>" + docAccountListModelsArray[i].getPatName() + "</strong><br>");
                    arr.add(docAccountListModelsArray[i].getAge() + " " + gender+"<br>");
                    arr.add(docAccountListModelsArray[i].getBillDate());
                    arr.add("</div>");
                    arr.add("</td>");
                    arr.add("<td>" + docAccountListModelsArray[i].getPymtNature() + "</td>");
                    arr.add("<td>" + docAccountListModelsArray[i].getBillId() + "</td>");
                    arr.add("<td><div class=\"fee\">Rs. " + docAccountListModelsArray[i].getAmount() + "</div>");
                    arr.add("</td>");
                    arr.add("<td class = \"remarks\">");
                    arr.add("<div>");

                    arr.add("</div>");
                    arr.add("</td>");
                    arr.add("</tr>");
                }
                htmlString = android.text.TextUtils.join("", arr);
                generatePDF();
            }
        });
        setFromDateTimeField();
        setToDateTimeField();
    }
    public void generatePDF(){
        WebView webView = new WebView(BillDetailActivity.this);
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
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<title>PDF</title>\n" +
                "<style type=\"text/css\">\n" +
                "body{ font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif; padding-top:20px;}\n" +
                "table {\n" +
                "\tborder-spacing:0;\n" +
                "\tborder-collapse:collapse\n" +
                "}\n" +
                "\n" +
                ".table {\n" +
                "\twidth:100%;\n" +
                "\tmax-width:100%;\n" +
                "\tmargin-bottom:20px;\n" +
                "\tfont-size:13px;\n" +
                "}\n" +
                ".table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {\n" +
                "\tpadding:8px;\n" +
                "\tline-height:1.42857143;\n" +
                "\tvertical-align:top;\n" +
                "\tborder-top:1px solid #ccc\n" +
                "}\n" +
                ".table>thead>tr>th {\n" +
                "\tvertical-align:bottom;\n" +
                "\tborder-bottom:2px solid #ccc\n" +
                "}\n" +
                ".table>caption+thead>tr:first-child>td, .table>caption+thead>tr:first-child>th, .table>colgroup+thead>tr:first-child>td, .table>colgroup+thead>tr:first-child>th, .table>thead:first-child>tr:first-child>td, .table>thead:first-child>tr:first-child>th {\n" +
                "\tborder-top:0\n" +
                "}\n" +
                ".table>tbody+tbody {\n" +
                "\tborder-top:2px solid #ccc\n" +
                "}\n" +
                ".table .table {\n" +
                "\tbackground-color:#fff\n" +
                "}\n" +
                ".table-condensed>tbody>tr>td, .table-condensed>tbody>tr>th, .table-condensed>tfoot>tr>td, .table-condensed>tfoot>tr>th, .table-condensed>thead>tr>td, .table-condensed>thead>tr>th {\n" +
                "\tpadding:5px\n" +
                "}\n" +
                ".table-bordered {\n" +
                "\tborder:1px solid #ccc\n" +
                "}\n" +
                ".table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th {\n" +
                "\tborder:1px solid #ccc\n" +
                "}\n" +
                ".table-bordered>thead>tr>td, .table-bordered>thead>tr>th {\n" +
                "\tborder-bottom-width:2px\n" +
                "}\n" +
                ".table-striped>tbody>tr:nth-of-type(odd) {\n" +
                "\tbackground-color:#f9f9f9\n" +
                "}\n" +
                ".table-hover>tbody>tr:hover {\n" +
                "\tbackground-color:#f5f5f5\n" +
                "}\n" +
                "table col[class*=col-] {\n" +
                "\tposition:static;\n" +
                "\tdisplay:table-column;\n" +
                "\tfloat:none\n" +
                "}\n" +
                "table td[class*=col-], table th[class*=col-] {\n" +
                "\tposition:static;\n" +
                "\tdisplay:table-cell;\n" +
                "\tfloat:none\n" +
                "}\n" +
                ".table-bordered {\n" +
                "\tborder:1px solid #ccc\n" +
                "}\n" +
                ".table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th {\n" +
                "\tborder:1px solid #ccc\n" +
                "}\n" +
                ".table-bordered>thead>tr>td, .table-bordered>thead>tr>th {\n" +
                "\tborder-bottom-width:2px\n" +
                "}\n" +
                ".outer{ width:650px; margin:0 auto;}\n" +
                ".patientImg{ float:left; width:45px; height:45px; border-radius:50px; overflow:hidden;}\n" +
                ".patientImg img{ max-width:100%; }\n" +
                ".patientDetails{ float:left; width:100%; font-size:13px; line-height:19px; padding-left:5px;}\n" +
                ".fee{ font-size:13px; text-align:center;}\n" +
                ".status{ background-color:#6d6d6d; color:#fff; border-radius:10px; width:60px; margin:2px auto; font-size:13px; text-align:center; line-height:22px;}\n" +
                ".doctorName{ width:50%; float:left; font-weight:bold; text-align:left; font-weight:bold}\n" +
                ".date{ width:50%; float:right; display:table;}\n" +
                ".toDate{ width:50%; float:left; text-align:right; font-size:12px; padding-right:10px; box-sizing:border-box;}\n" +
                ".fromDate{ width:50%; float:left; text-align:left; font-size:12px;}\n" +
                ".text-left{ text-align:left;}\n" +
                ".text-center{ text-align:center;}\n" +
                ".developedby{ text-align: center;}\n" +
                ".developedby img{ max-width:90px; vertical-align: bottom;}\n" +
                ".totalAmount{ line-height:25px; text-align:right;}\n" +
                ".submitedby{ width:33.333333%; float:left; text-align:left;}\n" +
                ".received{ width:33.333333%; float:left; text-align:left;}\n" +
                ".developedby{ width:33.333333%; float:left; text-align:center;}\n" +
                ".developedby img{ text-align:center; }\n" +
                ".sno{width:35px}\n"+
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "\n" +
                "<body>\n" +
                "<div class=\"outer\">\n" +
                "<table class=\"table table-bordered\">\n" +
                "\t<tr>\n" +
                "    \t<th colspan=\"7\">\n" +
                "        \t<img src="+subtanentImagePath+" alt=\"logo\"/>\n" +
                "        </th>\n" +
                "     </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "    <td colspan=\"7\"><div class=\"doctorName\">"+docName+"</div>\n" +
                "\t<div class=\"date\">\n" +
                "    <div class=\"fromDate\">From: "+tvFromTime.getText().toString()+"</div>\n" +
                "\t<div class=\"toDate\">To: "+tvToTime.getText().toString()+"</div>\n" +
                "\t</div>\n" +
                "\t</td>\n" +
                "\n" +
                "    </tr> \n" +
                "\n" +
                "<tr class=\"text-left\">\n" +
                "\t<th>S.No.</th>\n" +
                "<th>Hospital Id</th>\n" +
                "<th>Patient Details</th>\n" +
                "    <th>Nature</th>\n" +
                "    <th>Bill No.</th>\n" +
                "    <th class=\"text-center\">Amount</th>\n" +
                "<th>Remarks</th>\n" +
                "</tr>\n" +
                "    \n" +
                "    \n" +htmlString+
                "\n" +
                "\n" +
                "    \n" +
                "    <tr>\n" +
                "    <th colspan=\"7\">\n" +
                "    <div class=\"totalAmount\">Total Amount: Rs. "+totalCollection+"</div>\n" +
                "    </th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "    <th colspan=\"7\">\n" +
                "    \t<div class=\"submitedby\">Submitted By: </div>\n" +
                "        <div class=\"received\">Received By: </div>\n" +
                /*"        <div class=\"developedby\">Smart OPD Enabled by: </br><img src=\"image/logo.png\"></div>\n" +*/
                "    </th>\n" +
                "    </tr>\n" +
                "    </table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
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
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent i = new Intent(BillDetailActivity.this, AccountSettingActivity.class);
                startActivity(i);
                break;
            case R.id.action_logout:
                SharedPreferences.Editor editor = mSharedPreference.edit();
                editor.remove(Constants.STATUS_ID_KEY);
                editor.remove(Constants.DOMAIN_KEY);
                editor.remove(Constants.LOGIN_ID_KEY);
                editor.remove(Constants.LOGIN_NAME_KEY);
                editor.remove(Constants.PIN_KEY);
                editor.remove(Constants.PASSWORD_KEY);
                editor.remove(Constants.USER_ROLE_ID_KEY);
                editor.remove(Constants.ADDRESS_ID_KEY);
                editor.remove(Constants.CONTACT_ID_KEY);
                editor.remove(Constants.DOB_KEY);
                editor.remove(Constants.GENDER_ID_KEY);
                editor.remove(Constants.UNAME_KEY);
                editor.remove(Constants.USER_ID_KEY);

                editor.apply();
                SharedPreferenceHelper.removeKey(Keys.SharedPreferenceKeys.USERS_LIST);
                Intent intentLogout = new Intent(BillDetailActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                break;

        }
        return true;
    }
*/
    @Override
    public void onClick(View v) {
        switch(v.getId()){
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
                getDoctorAccountFromAPI();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    private void setToDateTimeField() {
        final Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                toDate = Calendar.getInstance();
                toDate.set(year, monthOfYear, dayOfMonth);
                tvToTime.setText(dateFormatter.format(toDate.getTime()));
                getDoctorAccountFromAPI();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private void getDoctorAccountFromAPI(){

        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.docAccountList(userRoleId, tvFromTime.getText().toString(), tvToTime.getText().toString(), new Callback<DocAccountListModel[]>() {
            @Override
            public void success(DocAccountListModel[] docAccountListModels, Response response) {
                docAccountListModelsArray = docAccountListModels;
                int totalAmount = 0;

                if (docAccountListModels.length > 0) {
                    totalBills.setText(docAccountListModels.length+"");
                    for (int i = 0; i < docAccountListModels.length; i++) {
                        totalAmount += docAccountListModels[i].getAmount();
                    }
                    totalCollection = totalAmount;
                    total_amount.setText(totalAmount + "");
                    billDetailAdapter = new BillDetailAdapter(BillDetailActivity.this, docAccountListModels);
                    patientListView.setAdapter(billDetailAdapter);

                } else {
                    totalAmount = 0;
                    total_amount.setText(totalAmount + "");
                    totalBills.setText(docAccountListModels.length+"");
                    billDetailAdapter = new BillDetailAdapter(BillDetailActivity.this, docAccountListModels);
                    patientListView.setAdapter(billDetailAdapter);
                    //patientListView.removeAllViews();
                    Toast.makeText(BillDetailActivity.this, "Detail not available", Toast.LENGTH_LONG).show();
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
            progressDialog = new ProgressDialog(BillDetailActivity.this);
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
    public void getHospitalFromAPI(){
        int uId;
        if(userRoleId==0){
            uId = 1633;
        }else{
            uId=userRoleId;
        }
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getHospital(uId, new Callback<SearchHospital[]>() {
            @Override
            public void success(SearchHospital[] searchHospitals, Response response) {
                if(searchHospitals!=null) {
                    subTanentId = searchHospitals[0].getSubTenantId();
                    getDoctorDetail();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }
    public void getDoctorDetail(){
        showLoadingDialog();

        mCuraApplication.getInstance().mCuraEndPoint.list_DoctorsBySubTenantId(AssisUserRoleId, new Callback<DoctorListModel[]>() {
            @Override
            public void success(DoctorListModel[] doctors, Response response) {
                doctorArray = doctors;
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
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
    public void getDoctorInfo(){
        mCuraApplication.getInstance().mCuraEndPoint.getUserInfo(userRoleId, new Callback<UserInfoModel>() {
            @Override
            public void success(UserInfoModel userInfoModel, Response response) {


                if (userInfoModel.getUserId() != null) {

                    //SharedPreferences.Editor editor = mSharedPreference.edit();
                    /*editor.putInt(Constants.ADDRESS_ID_KEY, userInfoModel.getAddressId());
                    editor.putInt(Constants.CONTACT_ID_KEY, userInfoModel.getContactId());
                    editor.putString(Constants.DOB_KEY, userInfoModel.getDob());
                    editor.putInt(Constants.GENDER_ID_KEY, userInfoModel.getGenderId());*/
                    //editor.putString(Constant.UNAME_KEY, userInfoModel.getUname());
                    //editor.putInt(Constants.USER_ID_KEY, userInfoModel.getUserId());
                    //editor.apply();
                    docName = userInfoModel.getUname();
                    doctor_name.setText(docName);
                    getDoctorAccountFromAPI();
                    /*Intent i = new Intent(LoginActivity.this, CalendarActivity.class);
                    startActivity(i);*/
                    dismissLoadingDialog();
                    //finish();
                } else {
                    dismissLoadingDialog();
                }


            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();

            }
        });

    }
}
