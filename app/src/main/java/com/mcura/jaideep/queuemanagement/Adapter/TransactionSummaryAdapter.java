package com.mcura.jaideep.queuemanagement.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcura.jaideep.queuemanagement.Activity.CentralizedBillingActivity;
import com.mcura.jaideep.queuemanagement.Activity.TransactionSummary;
import com.mcura.jaideep.queuemanagement.Model.DoctorList;
import com.mcura.jaideep.queuemanagement.Model.DoctorListModel;
import com.mcura.jaideep.queuemanagement.Model.TransactionSummaryModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.Helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TransactionSummaryAdapter extends BaseAdapter implements Filterable {
    private String subTanantName="",subTanantAddress="",subTanantContact="";
    private SharedPreferences mSharedPreference;
    private List<TransactionSummaryModel> orig;
    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<TransactionSummaryModel> transactions;
    private ValueFilter valueFilter;
    private WebView myWebView;

    public TransactionSummaryAdapter(Context context,List<TransactionSummaryModel> transactions) {
        this.mContext = context;
        this.transactions = (ArrayList<TransactionSummaryModel>) transactions;
        orig = (ArrayList<TransactionSummaryModel>) transactions;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSharedPreference = mContext.getSharedPreferences(mContext.getString(R.string.app_name), Context.MODE_PRIVATE);
        subTanantName = mSharedPreference.getString(Constant.SUBTANENT_NAME, "");
        subTanantAddress = mSharedPreference.getString(Constant.SUBTANENT_ADD, "");
        subTanantContact = mSharedPreference.getString(Constant.SUBTANENT_CONTACT, "");
        getFilter();
    }

    @Override
    public int getCount() {
        return orig.size();
    }

    @Override
    public TransactionSummaryModel getItem(int i) {
        return orig.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.transaction_summary_row_layout, null);
            holder = new ViewHolder();
            holder.bill_sr_no = (TextView) convertView.findViewById(R.id.bill_sr_no);
            holder.patient_name = (TextView) convertView.findViewById(R.id.patient_name);
            holder.patient_age = (TextView) convertView.findViewById(R.id.patient_age);
            holder.patient_sex = (TextView) convertView.findViewById(R.id.patient_sex);
            holder.patient_checkup_date = (TextView) convertView.findViewById(R.id.patient_checkup_date);
            holder.txnDesc = (TextView) convertView.findViewById(R.id.txnDesc);
            holder.patient_bill_payment = (TextView) convertView.findViewById(R.id.patient_bill_payment);
            holder.txtViewHospitalNo = (TextView) convertView.findViewById(R.id.tv_hospital_no);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final TransactionSummaryModel transactionSummaryModel = orig.get(i);
        holder.bill_sr_no.setText(i+1+"");
        holder.patient_name.setText(transactionSummaryModel.getPatname());
        holder.patient_age.setText(transactionSummaryModel.getAge().toString()+"Yr/");
        int genderId = transactionSummaryModel.getGenderId();
        if(genderId == 1){
            holder.patient_sex.setText("M");
        }else if(genderId == 2){
            holder.patient_sex.setText("F");
        }
        holder.patient_checkup_date.setText(transactionSummaryModel.getTxnDate().toString());
        holder.patient_bill_payment.setText("Rs. "+transactionSummaryModel.getTxnAmount()+"/-");
        holder.txnDesc.setText(transactionSummaryModel.getTxnDesc()+"");
        if (transactionSummaryModel.getHospitalNo() != null) {
            if (!transactionSummaryModel.getHospitalNo().toString().equals("")) {
                holder.txtViewHospitalNo.setText(transactionSummaryModel.getHospitalNo() + "");
            }else{
                holder.txtViewHospitalNo.setVisibility(View.GONE);
            }
        }else{
            holder.txtViewHospitalNo.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printBill(transactionSummaryModel);
            }
        });
        return convertView;
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
    public void printBill(TransactionSummaryModel transactionSummaryModel) {
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

        String discountHTML = "",feeGenerateStatus="";
        int rowSpan = 3;
        double totalFee = transactionSummaryModel.getTxnAmount();
        if (transactionSummaryModel.getBillDiscount().size() > 0) {
            ArrayList<String> arr = new ArrayList<String>();
            for (int i = 0; i < transactionSummaryModel.getBillDiscount().size(); i++) {
                totalFee+=transactionSummaryModel.getBillDiscount().get(i).getDiscountAmount();
                rowSpan++;
                arr.add("<tr>");
                arr.add("<td style=\"text-align:right\"><strong>" + transactionSummaryModel.getBillDiscount().get(i).getDiscountName() + "</strong></td>");
                arr.add("<td style=\"text-align:center;\" ><strong> - Rs. " + transactionSummaryModel.getBillDiscount().get(i).getDiscountAmount() + "</strong></td>");
                arr.add("</tr>");
            }
            discountHTML = android.text.TextUtils.join("", arr);
        }
        String finalPayableAmount = transactionSummaryModel.getTxnAmount()+"";
        String[] finalPayable = finalPayableAmount.split("\\.");
        String rupeePart = Helper.convert(Integer.parseInt(finalPayable[0]));
        String paisePart = Helper.convert(Integer.parseInt(finalPayable[1]));


        if(transactionSummaryModel.getServiceTypeId()=="1"){
            feeGenerateStatus = "OPD Consultation Fee";
        }else if(transactionSummaryModel.getServiceTypeId()=="4"){
            feeGenerateStatus = "Lab Fee";
        }else if(transactionSummaryModel.getServiceTypeId()=="5"){
            feeGenerateStatus = "Pharmacy Fee";
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
                "\t<tr>\n" +
                "\t\t<td colspan=\"2\"><div class=\"consultantOuter\">\n" +
                "        <div class=\"consultantValue\"><input type=\"text\" class=\"valueInput\" value=\"" + transactionSummaryModel.getUname() + "\"></div></div>\n" +
                "        \n" +
                "        <div class=\"departmentOuter\">\n" +
                "        <div class=\"department\"><input type=\"text\" class=\"valueInput\" value=\""+transactionSummaryModel.getDeptName()+"\"></div></div>\n" +
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
                "   \t    <div class=\"billnoValue\"><input type=\"text\" class=\"valueInput\" value = \"" + transactionSummaryModel.getBillNo() + "\"></div></div>\n" +
                "        <div class=\"tokennoOuter\"><div class=\"tokenNo\">Token No.</div>\n" +
                "        <div class=\"tokenValue\"><input type=\"text\" class=\"valueInput\" value = \"\"></div></div> <div class=\"dateOuter\"><div class=\"date\">Date</div> <div class=\"dateValue\"><input type=\"text\" class=\"valueInput\" value=\"" + transactionSummaryModel.getTxnDate()+ "\"></div></div></td>\n" +
                "    </tr>\n" +
                "    \n" +
                "      <tr>\n" +
                "    \t<td colspan=\"2\"><div class=\"patientnameOuter\"><div class=\"patientname\">Patient Name</div>\n" +
                "   \t    <div class=\"patientnameValue\"><input type=\"text\" class=\"valueInput\" value=\"" + transactionSummaryModel.getPatname() + "\"></div></div> \n" +
                "        \n" +
                "        <div class=\"hospitalIdOuter\"><div class=\"hospitalId\">Hospital ID</div>\n" +
                "        <div class=\"hospitalIdValue\"><input type=\"text\" class=\"valueInput\" value=\"" + transactionSummaryModel.getHospitalNo() + "\"></div></div>\n" +
                "        \n" +
                "        <div class=\"cashcardOuter\"><div class=\"cashcard\">E-Wallet ID</div> <div class=\"cashcardValue\"><input type=\"text\" class=\"valueInput\" value = \"" + transactionSummaryModel.getCashCardId() + "\"></div></div></td>\n" +
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
                "     <td rowspan=" + rowSpan + " style=\"width:1px; border-left:1px #333 solid; padding:7px 0px;\">&nbsp;</td>\n" +
                "    <td style=\"width:120px; text-align:center\">Rs. " + totalFee + "</td>\n" +
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
                "    <td style=\"border-top:1px #333 solid; padding:7px 10px; text-align:center;\"><strong>Rs. " + transactionSummaryModel.getTxnAmount() + "</strong></td>\n" +
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
    private class ValueFilter extends Filter {
        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null) {
                if(TransactionSummary.SEARCHBY == TransactionSummary.SEARCHBY_SERVICE){
                    if(!constraint.toString().equals("0")){
                        Log.d("constraint","1");
                        ArrayList<TransactionSummaryModel> filterList = new ArrayList<TransactionSummaryModel>();
                        for (int i = 0; i < transactions.size(); i++) {
                            if (transactions.get(i).getServiceTypeId().equals(constraint)) {
                                filterList.add(transactions.get(i));
                            }
                        }
                        results.count = filterList.size();
                        results.values = filterList;
                    }else{
                        results.count = transactions.size();
                        results.values = transactions;
                    }
                }else if(TransactionSummary.SEARCHBY == TransactionSummary.SEARCHBY_NAME){
                    if(!constraint.toString().equals("")){
                        ArrayList<TransactionSummaryModel> filterList = new ArrayList<TransactionSummaryModel>();
                        for (int i = 0; i < transactions.size(); i++) {
                            if (transactions.get(i).getPatname().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                filterList.add(transactions.get(i));
                            }
                        }
                        results.count = filterList.size();
                        results.values = filterList;
                    }else{
                        results.count = transactions.size();
                        results.values = transactions;
                    }
                }
            }
            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      Filter.FilterResults results) {

            orig = (ArrayList<TransactionSummaryModel>) results.values;

            notifyDataSetChanged();
        }
    }
    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ViewHolder {
        TextView patient_name,patient_age,patient_sex,patient_checkup_date,txnDesc,patient_bill_payment,bill_sr_no;
        TextView txtViewHospitalNo;
    }
}
