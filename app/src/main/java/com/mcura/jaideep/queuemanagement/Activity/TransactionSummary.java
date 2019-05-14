package com.mcura.jaideep.queuemanagement.Activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mcura.jaideep.queuemanagement.Adapter.BillDetailAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.ServiceListAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.TransactionSummaryAdapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.GetServiceListModel;
import com.mcura.jaideep.queuemanagement.Model.ServiceType;
import com.mcura.jaideep.queuemanagement.Model.TransactionDetail;
import com.mcura.jaideep.queuemanagement.Model.TransactionSummaryModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.mcura.jaideep.queuemanagement.helper.Helper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TransactionSummary extends AppCompatActivity implements View.OnClickListener {
    public static final int SEARCHBY_NAME = 1;
    public static final int SEARCHBY_SERVICE = 2;
    public static int SEARCHBY;
    private DatePickerDialog fromDatePickerDialog, toDatePickerDialog;
    private ImageView logout;
    private TextView appointment, queue_mgmt, billing, tv_fillcard;
    private SharedPreferences mSharedPreference;
    private int sub_tanent_id;
    private ImageView hospital_logo;
    Intent intent;
    private ListView transaction_ListView;
    private SwipeRefreshLayout refresh_explv;
    private TextView tv_amount;
    private TextView total_amount;
    private TextView tvFromTime;
    private TextView tvToTime;
    private SimpleDateFormat dateFormatter;
    Calendar now, fromDate, toDate;
    private Spinner spSearchBillingType;
    private MCuraApplication mCuraApplication;
    private ProgressDialog progressDialog;
    private ServiceListAdapter serviceListAdapter;
    private String fDate, tDate;
    private SimpleDateFormat dateFormat;
    private TransactionSummaryAdapter transactionSummaryAdapter;
    private TextView totalBills;
    private GetServiceListModel getServiceListModel;
    private int serviceType=0;
    private List<TransactionSummaryModel> filteredTransactionData;
    private double totalAmount = 0.0;
    private ImageView bill_printer;
    private String htmlString="";
    private WebView myWebView;
    private String subtanentImagePath;
    private double totalCollection=0.0;
    private EditText etSearchByPatName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_summary);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        initView();
    }

    private void initView() {
        filteredTransactionData = new ArrayList<>();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        now = Calendar.getInstance();
        subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        sub_tanent_id = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        hospital_logo = (ImageView) findViewById(R.id.hospital_logo);
        logout = (ImageView) findViewById(R.id.logout);
        etSearchByPatName = findViewById(R.id.etSearchByPatName);
        appointment = (TextView) findViewById(R.id.appointment);
        queue_mgmt = (TextView) findViewById(R.id.queue_mgmt);
        billing = (TextView) findViewById(R.id.billing);
        tv_fillcard = (TextView) findViewById(R.id.tv_fillcard);
        refresh_explv = (SwipeRefreshLayout) findViewById(R.id.refresh_explv);
        transaction_ListView = (ListView) findViewById(R.id.transaction_ListView);
        Picasso.with(TransactionSummary.this).load(subtanentImagePath).into(hospital_logo);
        spSearchBillingType = (Spinner) findViewById(R.id.spSearchBillingType);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        total_amount = (TextView) findViewById(R.id.total_amount);
        tvFromTime = (TextView) findViewById(R.id.tv_fromTime);
        tvFromTime.setText(dateFormatter.format(now.getTime()));
        fDate = dateFormat.format(now.getTime());
        tvToTime = (TextView) findViewById(R.id.tv_toTime);
        tvToTime.setText(dateFormatter.format(now.getTime()));
        tDate = dateFormat.format(now.getTime());
        totalBills = (TextView) findViewById(R.id.tv_total_bills);
        bill_printer= (ImageView) findViewById(R.id.bill_printer);
        tvFromTime.setOnClickListener(this);
        tvToTime.setOnClickListener(this);
        bill_printer.setOnClickListener(this);
        logout.setOnClickListener(this);
        appointment.setOnClickListener(this);
        queue_mgmt.setOnClickListener(this);
        billing.setOnClickListener(this);
        tv_fillcard.setOnClickListener(this);
        spSearchBillingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getServiceListModel = serviceListAdapter.getItem(i);
                serviceType = getServiceListModel.getServiceTypeId();
                etSearchByPatName.setText("");
                SEARCHBY = SEARCHBY_SERVICE;
                Log.d("serviceType", serviceType + "");
                if (transactionSummaryAdapter != null) {
                    transactionSummaryAdapter.getFilter().filter(serviceType + "");
                    int size = 0;
                    if (serviceType > 0) {
                        totalAmount = 0.0;
                        for (int j = 0; j < filteredTransactionData.size(); j++) {
                            if (serviceType == Integer.parseInt(filteredTransactionData.get(j).getServiceTypeId())) {
                                size++;
                                totalAmount += filteredTransactionData.get(j).getTxnAmount();
                            }
                        }
                        totalBills.setText(size + "");
                        total_amount.setText(totalAmount + "");
                    } else {
                        totalAmount = 0.0;
                        for (int j = 0; j < filteredTransactionData.size(); j++) {
                            totalAmount += filteredTransactionData.get(j).getTxnAmount();
                        }
                        totalBills.setText(filteredTransactionData.size() + "");
                        total_amount.setText(totalAmount + "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        refresh_explv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                if (Helper.isInternetConnected(TransactionSummary.this)) {
                    refresh_explv.setRefreshing(false);
                    getTransactionSummary();
                } else {
                    Toast.makeText(TransactionSummary.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
        etSearchByPatName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SEARCHBY = SEARCHBY_NAME;

                if (transactionSummaryAdapter != null) {
                    transactionSummaryAdapter.getFilter().filter(s + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setFromDateTimeField();
        setToDateTimeField();
        getServiceListFromApi();
    }

    private void getServiceListFromApi() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getServiceList(sub_tanent_id, new Callback<GetServiceListModel[]>() {
            @Override
            public void success(GetServiceListModel[] getServiceListModels, Response response) {
                if (getServiceListModels.length > 0) {
                    serviceListAdapter = new ServiceListAdapter(TransactionSummary.this, android.R.layout.simple_spinner_item, getServiceListModels);
                    spSearchBillingType.setAdapter(serviceListAdapter);
                }
                getTransactionSummary();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void getTransactionSummary() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPointConsumer.transactionDetailBySubTenant_v1(sub_tanent_id, fDate, tDate, new Callback<TransactionDetail>() {
            @Override
            public void success(TransactionDetail transactionDetail, Response response) {
                if(transactionDetail!=null){
                    spSearchBillingType.setSelection(0);
                    filteredTransactionData = transactionDetail.getTransactions();
                    if (transactionDetail.getTransactions().size() > 0) {
                        totalAmount = 0.0;
                        totalBills.setText(transactionDetail.getTransactions().size() + "");
                        for (int i = 0; i < transactionDetail.getTransactions().size(); i++) {
                            totalAmount += transactionDetail.getTransactions().get(i).getTxnAmount();
                        }
                        total_amount.setText(totalAmount + "");
                        transactionSummaryAdapter = new TransactionSummaryAdapter(TransactionSummary.this, transactionDetail.getTransactions());
                        transaction_ListView.setAdapter(transactionSummaryAdapter);
                    } else {
                        totalAmount = 0.0;
                        total_amount.setText(totalAmount + "");
                        totalBills.setText(transactionDetail.getTransactions().size() + "");
                        transactionSummaryAdapter = new TransactionSummaryAdapter(TransactionSummary.this, transactionDetail.getTransactions());
                        transaction_ListView.setAdapter(transactionSummaryAdapter);
                        //patientListView.removeAllViews();
                        Toast.makeText(TransactionSummary.this, "Detail not available", Toast.LENGTH_LONG).show();
                    }
                    refresh_explv.setRefreshing(false);
                    dismissLoadingDialog();
                }else{
                    dismissLoadingDialog();
                    Toast.makeText(TransactionSummary.this,"Something went wrong. Please try again.",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
                Toast.makeText(TransactionSummary.this,"Something went wrong. Please try again.",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bill_printer:
                setTransactionSummaryData();
                break;
            case R.id.tv_fromTime:
                fromDatePickerDialog.show();
                break;
            case R.id.tv_toTime:
                toDatePickerDialog.show();
                break;
            case R.id.logout:
                SharedPreferences.Editor editor = mSharedPreference.edit();
                editor.remove(Constant.USER_ROLE_ID_KEY);
                editor.remove(Constant.SUB_TANENT_IMAGE_PATH);
                editor.apply();
                intent = new Intent(TransactionSummary.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.appointment:
                intent = new Intent(TransactionSummary.this, CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.queue_mgmt:
                intent = new Intent(TransactionSummary.this, QueueStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.billing:
                intent = new Intent(TransactionSummary.this, CentralizedBillingActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_fillcard:
                intent = new Intent(TransactionSummary.this, FillCashCardActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void setTransactionSummaryData() {
        totalCollection = 0.0;
        ArrayList<String> arr = new ArrayList<String>();
        if(filteredTransactionData.size()>0){
            if(serviceType>0){
                for (int i = 0,sno=1; i < filteredTransactionData.size(); i++,sno++) {
                    if(serviceType==Integer.parseInt(filteredTransactionData.get(i).getServiceTypeId())){
                        totalCollection += filteredTransactionData.get(i).getTxnAmount();
                        String gender = null;
                        if (filteredTransactionData.get(i).getGenderId() == 1) {
                            gender = "M";
                        } else if (filteredTransactionData.get(i).getGenderId() == 2) {
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
                        arr.add(filteredTransactionData.get(i).getHospitalNo()+"<br>");
                        arr.add("</div>");
                        arr.add("</td>");
                        arr.add("<td>");
                        arr.add("<div class=\"patientDetails\">");
                        arr.add("<strong>" + filteredTransactionData.get(i).getPatname() + "</strong><br>");
                        arr.add(filteredTransactionData.get(i).getAge() + " " + gender+"<br>");
                        arr.add(filteredTransactionData.get(i).getTxnDate());
                        arr.add("</div>");
                        arr.add("</td>");
                        arr.add("<td>" + filteredTransactionData.get(i).getTxnDesc() + "</td>");
                        arr.add("<td>" + filteredTransactionData.get(i).getBillNo() + "</td>");
                        arr.add("<td><div class=\"fee\">Rs. " + filteredTransactionData.get(i).getTxnAmount() + "</div>");
                        arr.add("</td>");
                        arr.add("<td class = \"remarks\">");
                        arr.add("<div>");

                        arr.add("</div>");
                        arr.add("</td>");
                        arr.add("</tr>");
                    }
                }
            }else{
                for (int i = 0,sno=1; i < filteredTransactionData.size(); i++,sno++) {
                    totalCollection += filteredTransactionData.get(i).getTxnAmount();
                    String gender = null;
                    if (filteredTransactionData.get(i).getGenderId() == 1) {
                        gender = "M";
                    } else if (filteredTransactionData.get(i).getGenderId() == 2) {
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
                    arr.add(filteredTransactionData.get(i).getHospitalNo()+"<br>");
                    arr.add("</div>");
                    arr.add("</td>");
                    arr.add("<td>");
                    arr.add("<div class=\"patientDetails\">");
                    arr.add("<strong>" + filteredTransactionData.get(i).getPatname() + "</strong><br>");
                    arr.add(filteredTransactionData.get(i).getAge() + " " + gender+"<br>");
                    arr.add(filteredTransactionData.get(i).getTxnDate());
                    arr.add("</div>");
                    arr.add("</td>");
                    arr.add("<td>" + filteredTransactionData.get(i).getTxnDesc() + "</td>");
                    arr.add("<td>" + filteredTransactionData.get(i).getBillNo() + "</td>");
                    arr.add("<td><div class=\"fee\">Rs. " + filteredTransactionData.get(i).getTxnAmount() + "</div>");
                    arr.add("</td>");
                    arr.add("<td class = \"remarks\">");
                    arr.add("<div>");

                    arr.add("</div>");
                    arr.add("</td>");
                    arr.add("</tr>");
                }
            }

            htmlString = android.text.TextUtils.join("", arr);
            generatePDF();
        }else{
            Toast.makeText(TransactionSummary.this,"No Record found to be print",Toast.LENGTH_LONG).show();
        }

    }

    private void generatePDF() {
        WebView webView = new WebView(TransactionSummary.this);
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
                ".date{ width:50%; float:none; display:table; margin:0 auto}\n" +
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
                "    <td colspan=\"7\">\n" +
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

    private void setFromDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fromDate = Calendar.getInstance();
                fromDate.set(year, monthOfYear, dayOfMonth);
                fDate = dateFormat.format(fromDate.getTime());
                Log.d("fDate", fDate);
                getTransactionSummary();
                tvFromTime.setText(dateFormatter.format(fromDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setToDateTimeField() {
        final Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                toDate = Calendar.getInstance();
                toDate.set(year, monthOfYear, dayOfMonth);
                tDate = dateFormat.format(toDate.getTime());
                getTransactionSummary();
                tvToTime.setText(dateFormatter.format(toDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(TransactionSummary.this);
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

}
