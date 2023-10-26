package com.mcura.jaideep.queuemanagement.Adapter;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.Activity.BillPaymentActivity;
import com.mcura.jaideep.queuemanagement.Activity.OrderBoothActivity;
import com.mcura.jaideep.queuemanagement.Activity.QueueStatusActivity;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.LabPharmacyOrderBoothModel;
import com.mcura.jaideep.queuemanagement.Model.PostPaymentModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.Helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by mCURA1 on 11/15/2016.
 */
public class OrderBoothAdapter extends BaseAdapter implements Filterable {

    private final SharedPreferences mSharedPreference;
    private final int subTanentId;
    ArrayList<LabPharmacyOrderBoothModel> values;
    ArrayList<LabPharmacyOrderBoothModel> orig;
    //PharmacyModel[] pharmacyModels;
    private Context mContext;
    private LayoutInflater mInflater = null;
    private ValueFilter valueFilter;
    private ProgressDialog progress;
    private MCuraApplication mCuraApplication;
    private String paymentMode = "cash";
    private int frontOfficeUserRoleId;
    private WebView myWebView;
    private String completeDate;
    private String time;
    private String subTanantName;
    private String subTanantAddress;
    private String subTanantContact;
    private String otp;

    public OrderBoothAdapter(Context context, ArrayList<LabPharmacyOrderBoothModel> models) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.values = models;
        this.orig = models;
        mSharedPreference = mContext.getSharedPreferences(mContext.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
        subTanentId = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
    }

    @Override
    public int getCount() {
        return orig.size();
    }

    @Override
    public LabPharmacyOrderBoothModel getItem(int position) {
        return orig.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int labPharmacyType = orig.get(position).getLabPharmacyType();

        PharmacyViewHolder pharmacyViewHolder = null;

        View v = convertView;
        if (v == null) {
            v = mInflater.inflate(R.layout.pharmacy_raw_layout, parent, false);
            pharmacyViewHolder = new PharmacyViewHolder();
            pharmacyViewHolder.ll_main = (LinearLayout) v.findViewById(R.id.ll_main);
            pharmacyViewHolder.ll_bottom_layout = (LinearLayout) v.findViewById(R.id.ll_bottom_layout);
            pharmacyViewHolder.tv_doc_name = (TextView) v.findViewById(R.id.tv_doc_name);
            pharmacyViewHolder.tv_patient_name = (TextView) v.findViewById(R.id.tv_patient_name);
            pharmacyViewHolder.tv_status = (TextView) v.findViewById(R.id.tv_status);
            pharmacyViewHolder.tv_datetime = (TextView) v.findViewById(R.id.tv_datetime);
            pharmacyViewHolder.tv_est_id = (TextView) v.findViewById(R.id.tv_est_id);
            pharmacyViewHolder.tv_days = (TextView) v.findViewById(R.id.tv_days);
            pharmacyViewHolder.tv_amount = (TextView) v.findViewById(R.id.tv_amount);
            pharmacyViewHolder.btn_pay = (Button) v.findViewById(R.id.btn_pay);
            v.setTag(pharmacyViewHolder);
        } else {
            pharmacyViewHolder = (PharmacyViewHolder) v.getTag();
        }

        final LabPharmacyOrderBoothModel pharmacyModels = orig.get(position);

        // set up the list item
        pharmacyViewHolder.tv_doc_name.setText(pharmacyModels.getDoctorName().toString());
        pharmacyViewHolder.tv_patient_name.setText(pharmacyModels.getPatname().toString());
        if (labPharmacyType == 1) {
            pharmacyViewHolder.tv_est_id.setText("Est Id: " + pharmacyModels.getEST_Billno().toString());
            pharmacyViewHolder.tv_days.setText("Days: " + pharmacyModels.getDays() + "");
            pharmacyViewHolder.tv_amount.setText("Amt: " + pharmacyModels.getAmount().toString());
        } else if (labPharmacyType == 2) {
            pharmacyViewHolder.tv_est_id.setText("Est Id: " + pharmacyModels.getEST_Billno().toString());
            pharmacyViewHolder.tv_days.setVisibility(View.GONE);
            pharmacyViewHolder.tv_amount.setText("Amt: " + pharmacyModels.getAmount().toString());
        }
        if (pharmacyModels.getStatus_id() == 1) {
            pharmacyViewHolder.tv_status.setText("Pending");
        } else if (pharmacyModels.getStatus_id() == 2) {
            pharmacyViewHolder.tv_status.setText("Completed");
        } else if (pharmacyModels.getStatus_id() == 3) {
            pharmacyViewHolder.tv_status.setText("Requested");
        } else if (pharmacyModels.getStatus_id() == 4) {
            pharmacyViewHolder.tv_status.setText("Billing Done");
        } else if (pharmacyModels.getStatus_id() == 5) {
            pharmacyViewHolder.tv_status.setText("Sample Collection");
        } else if (pharmacyModels.getStatus_id() == 6) {
            pharmacyViewHolder.tv_status.setText("Report Collection");
        }
        pharmacyViewHolder.btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pharmacyModels.getAmount() == 0) {
                    Toast.makeText(mContext, "Sorry Payment can not be done for 0 amount.", Toast.LENGTH_SHORT).show();
                } else {
                    showPharmacyBillPayDialog(pharmacyModels);
                }
            }
        });
        String dobEncode = pharmacyModels.getDob();
        Date createdOn = Helper.JsonDateToDate(dobEncode);
//        String timestamp = dobEncode.split("\\(")[1].split("\\+")[0];
//        Date createdOn = new Date();
//        try {
//            createdOn  = new Date(Long.parseLong(timestamp));
//        }catch (NumberFormatException  numberFormatException){
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(createdOn);
        pharmacyViewHolder.tv_datetime.setText(formattedDate);

        // return the created view
        return v;

    }

    private class PharmacyViewHolder {
        TextView tv_order_id, tv_doc_name, tv_patient_name, tv_status, tv_datetime;
        TextView tv_est_id, tv_days, tv_amount;
        Button btn_pay;
        LinearLayout ll_main, ll_bottom_layout;
    }

    private class LabOrderViewHolder {
        TextView tv_order_id, tv_doc_name, tv_patient_name, tv_status, tv_datetime;
        TextView tv_est_id, tv_amount;
        Button btn_pay;
        LinearLayout ll_main, ll_bottom_layout;
    }

    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<LabPharmacyOrderBoothModel> filterList = new ArrayList<LabPharmacyOrderBoothModel>();
                for (int i = 0; i < values.size(); i++) {
                    if (OrderBoothActivity.searchBy.equals("status")) {
                        if (values.get(i).getStatus_id().toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filterList.add(values.get(i));
                        }
                    } else if (OrderBoothActivity.searchBy.equals("patient")) {
                        if (values.get(i).getPatname().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            filterList.add(values.get(i));

                        }
                    }

                }


                results.count = filterList.size();

                results.values = filterList;

            } else {

                results.count = values.size();

                results.values = values;

            }

            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      Filter.FilterResults results) {

            orig = (ArrayList<LabPharmacyOrderBoothModel>) results.values;

            notifyDataSetChanged();


        }
    }

    private void showPharmacyBillPayDialog(final LabPharmacyOrderBoothModel pharmacyModels) {
        final Dialog dialog = new Dialog(mContext);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pharmacy_lab_pay_dialog);
        final EditText bill_payment_activity_hIs_no = (EditText) dialog.findViewById(R.id.bill_payment_activity_hIs_no);
        RadioGroup payment_mode = (RadioGroup) dialog.findViewById(R.id.payment_mode);
        TextView activity_bill_payment_paynow = (TextView) dialog.findViewById(R.id.activity_bill_payment_paynow);
        activity_bill_payment_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(bill_payment_activity_hIs_no.getText().toString())) {
                    callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), pharmacyModels);
                }
                dialog.dismiss();
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width * 0.9);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 1f; // Transparency
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private void callPaymentAPiforPharmacyKims(String his, LabPharmacyOrderBoothModel pharmacyModels) {
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", pharmacyModels.getOrderBy_sub_tenant_id());
        obj.addProperty("HospitalNo", 0);
        obj.addProperty("AppnatureId", 0);
        obj.addProperty("UserRoleId", pharmacyModels.getUser_role_id());
        obj.addProperty("Description", "");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", pharmacyModels.getAmount());
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", 6);
        obj.addProperty("Mrno", pharmacyModels.getMrno());
        obj.addProperty("HIS_BillNo", his);
        obj.addProperty("MobileNo", "9560067752");
        obj.addProperty("LabOrderID", 0);
        obj.addProperty("PharmacyOrderID", pharmacyModels.getPres_order_id());
        if (paymentMode.equals("cash")) {
            postPaymentForPharmacyCash(obj,pharmacyModels);
        }
        if (paymentMode.equals("card")) {
            postPaymentForPharmacyCard(obj,pharmacyModels);
        }
        if (paymentMode.equals("cheque")) {
            postPaymentForPharmacyCash(obj,pharmacyModels);
        }
    }
    private void postPaymentForPharmacyCard(JsonObject obj, final LabPharmacyOrderBoothModel pharmacyModels) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postPayment_v1(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                showOTPDialog(pharmacyModels);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {

                dismissLoadingDialog();
            }
        });
    }

    private void showOTPDialog(final LabPharmacyOrderBoothModel pharmacyModels) {
        final Dialog dialog = new Dialog(mContext);
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
                if (TextUtils.isEmpty(otp)) {
                    verifyOTP(pharmacyModels);
                }
            }
        });

    }

    private void verifyOTP(final LabPharmacyOrderBoothModel pharmacyModels) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.verifyOTP(pharmacyModels.getMrno(), Integer.parseInt(otp), new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatus()) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    printBill(data,pharmacyModels);

                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }
    private void postPaymentForPharmacyCash(JsonObject obj, final LabPharmacyOrderBoothModel pharmacyModels) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postPayment_v1(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getStatus()) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    printBill(data,pharmacyModels);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {

                dismissLoadingDialog();
            }
        });
    }

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(mContext);
            progress.setCancelable(false);
            progress.setMessage(mContext.getString(R.string.loading_message));
        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    public void printBill(String data[],LabPharmacyOrderBoothModel pharmacyModels) {
        Calendar now = Calendar.getInstance();
        completeDate = now.get(Calendar.DATE) + "/" + now.get(Calendar.MONTH) + 1 + "/" + now.get(Calendar.YEAR);
        time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());

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
        }else if(subTanentId == 531){
            subTanantName = "Fernandez Stork Home";
            subTanantAddress = "8-2-698, Road No. 12, Banjara Hills,Next to Seating World, Hyderabad, Telangana 500034";
            subTanantContact = "Tel:+91-40 4780 7300";
        }
        WebView webView = new WebView(mContext);
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
                "        <div class=\"consultantValue\"><input type=\"text\" class=\"valueInput\" value=\"" + pharmacyModels.getDoctorName() + "\"></div></div>\n" +
                "        \n" +
                "        <div class=\"departmentOuter\">\n" +
                "        <div class=\"department\"><input type=\"text\" class=\"valueInput\" value=\"" + "Department" + "\"></div></div>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "  <tr>\n" +
                "    <td colspan=\"2\"><h4>" + subTanantName + "</h4>\n" +
                "\t<p> " + subTanantAddress + "</br>\n" +
                "    " + subTanantContact + "</br></p>\n" +
                "  <h3 style=\"margin-bottom:20px;\"> Pharmacy Receipt</h3>\n" +
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
                "   \t    <div class=\"patientnameValue\"><input type=\"text\" class=\"valueInput\" value=\"" + pharmacyModels.getPatname() + "\"></div></div> \n" +
                "        \n" +
                /*"        <div class=\"hospitalIdOuter\"><div class=\"hospitalId\">Hospital ID</div>\n" +
                "        <div class=\"hospitalIdValue\"><input type=\"text\" class=\"valueInput\" value=\"" + tvHospitalId.getText().toString() + "\"></div></div>\n" +*/
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
                "  \t<td>" + "Pharmacy fee" + "</td>\n" +
                "     <td rowspan=\"3\" style=\"width:1px; border-left:1px #333 solid; padding:7px 0px;\">&nbsp;</td>\n" +
                "    <td style=\"width:120px;\">Rs. " + pharmacyModels.getAmount() + "</td>\n" +
                "  </tr>\n" +
                "\n" +
                "\n" +
                "    <tr>\n" +
                "    <td>&nbsp; </td>\n" +
                "    <td>&nbsp;</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td style=\"border-top:1px #333 solid; text-align:right; padding:7px 10px;\"><strong>Total Amount</strong></td>\n" +
                "    <td style=\"border-top:1px #333 solid; padding:7px 10px;\"><strong>Rs. " + pharmacyModels.getAmount() + "</strong></td>\n" +
                "  </tr>\n" +
                "   \n" +
                "    <tr>\n" +
                "    <td colspan=\"3\"><div class=\"amoutnwordsOuter\">\n" +
                "    <div class=\"amountWords\">Amount in words:</div>\n" +
                "    <div class=\"amountwordsValue\"><input type=\"text\" class=\"valueInput\" value = \"" + Helper.convert(pharmacyModels.getAmount()) + " Only\"></div>\n" +
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

        PrintManager printManager = (PrintManager) mContext
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = mContext.getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }
}

