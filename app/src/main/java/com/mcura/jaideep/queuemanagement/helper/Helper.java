package com.mcura.jaideep.queuemanagement.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mcura.jaideep.queuemanagement.Activity.LoginActivity;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.encryption.AESCrypt;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import android.app.ProgressDialog;

/**
 * Created by mCURA1 on 8/17/2016.
 */
public  class Helper {
    public static enum RecordNatureEnum {
        Chief_Complaints("Chief Complaints", 1),
        Lab_Report("Lab Report", 2),
        Advice("Advice", 3),
        Lab_Order("Lab Order", 4),
        Doctor_Note("Doctor Note", 5),
        Current_Visit_Image("Current Visit Image", 6),
        Doctor_Comments("Doctor Comments", 7),
        Referrals("Referrals", 8),
        Template("Template", 9),
        Plan(" Plan", 11),
        Medical_History("Medical History", 12),
        Visit_Summary("Visit Summary", 13),
        Clinical_Summary(" Clinical Summary", 14),
        Physical_Examination("Physical Examination", 15),
        Lab_Report_Finding("Lab Report Finding", 16),
        Instructions("Instructions", 18),
        Next_Follow_Up(" Next Follow Up", 19);

        String natureName;
        int natureId;

        RecordNatureEnum(String toString, int value) {
            natureName = toString;
            natureId = value;
        }

        public String getNatureName() {
            return natureName;
        }

        public int getNatureId() {
            return natureId;
        }
    }

    private ProgressDialog progressDialog;
    private Messages msg;
    public Helper() {

        msg = new Messages();
    }

    public static Date JsonDateToDate(String jsonDate) {
        int idx1 = jsonDate.indexOf("(");
        int idx2 = jsonDate.indexOf(")") - 5;
        String s = jsonDate.substring(idx1+1, idx2);
        long l = Long.parseLong(s);
        return new Date(l);
    }



    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public static String getCompleteDate(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);
        String completeDate = year + "-" + month + "-" + date; //"2016-05-09"
        return completeDate;
    }

    public void showLoadingDialog(Context self) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(self);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(msg.MSG01);
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
    private static final String[] specialNames = {
            "",
            " Thousand",
            " Million",
            " Billion",
            " Trillion",
            " Quadrillion",
            " Quintillion"
    };

    private static final String[] tensNames = {
            "",
            " Ten",
            " Twenty",
            " Thirty",
            " Forty",
            " Fifty",
            " Sixty",
            " Seventy",
            " Eighty",
            " Ninety"
    };

    private static final String[] numNames = {
            "",
            " One",
            " Two",
            " Three",
            " Four",
            " Five",
            " Six",
            " Seven",
            " Eight",
            " Nine",
            " Ten",
            " Eleven",
            " Twelve",
            " Thirteen",
            " Fourteen",
            " Fifteen",
            " Sixteen",
            " Seventeen",
            " Eighteen",
            " Nineteen"
    };
    private static String convertLessThanOneThousand(int number) {
        String current;

        if (number % 100 < 20){
            current = numNames[number % 100];
            number /= 100;
        }
        else {
            current = numNames[number % 10];
            number /= 10;

            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) return current;
        return numNames[number] + " Hundred" + current;
    }

    public static String getBuildVersion(Context context) {
        String version = null;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    public static String convert(int number) {

        if (number == 0) { return "Zero"; }

        String prefix = "";

        if (number < 0) {
            number = -number;
            prefix = "Negative";
        }

        String current = "";
        int place = 0;

        do {
            int n = number % 1000;
            if (n != 0){
                String s = convertLessThanOneThousand(n);
                current = s + specialNames[place] + current;
            }
            place++;
            number /= 1000;
        } while (number > 0);

        return (prefix + current).trim();
    }
    public static String getCurrentTimestamp(){
        java.util.Date date= new java.util.Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        String ts = timeStamp.toString();
        return ts;
    }
    public static String Convert24to12(String time) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = parseFormat.parse(time);
            convertedTime = displayFormat.format(date);
            System.out.println("convertedTime : " + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
    }
    public static String getCurrentDataAndTime(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);
        String completeDate = date + "/" + month + "/" + year;
        return completeDate;
    }
    public static boolean isInternetConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public static String getAESCryptEncodeString(){
        AESCrypt aesCrypt;
        String query="";
        SimpleDateFormat parseFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        parseFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = parseFormat.format(new Date());
        System.out.println("convertedTime : " + gmtTime);

        try {
            aesCrypt = new AESCrypt("Mcura$Secr3tKeyF0rH0sP1taL$API!1");
            String encyptedStr = aesCrypt.encrypt(gmtTime);
            Log.d("encyptedStr", encyptedStr);
            query = URLEncoder.encode(encyptedStr, "utf-8");
            Log.d("AES_Key", query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query;
    }
    public static String getCurrentDate(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);
        String completeDate = year + "-" + month + "-" + date;
        return completeDate;
    }
    public static String changeDOBDateFormat(String s){
        String convertedTime ="";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sszzz");
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime=displayFormat.format(date);
            System.out.println("registrationDate"+convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }
    public static String changeRegDateFormat(String s){
        String convertedTime ="";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sszzz");
            SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = parseFormat.parse(s);
            convertedTime=displayFormat.format(date);
            System.out.println("registrationDate"+convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }
    public static String changeDobDatesgrhFormat(String s){
        String convertedTime ="";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime=displayFormat.format(date);
            System.out.println("registrationDate"+convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }
    public static String changeDateFormat(String s){
        String convertedTime ="";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime=displayFormat.format(date);
            System.out.println("registrationDate"+convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }
    public static String changeDObDateFormat(String s){
        String convertedTime ="";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime=displayFormat.format(date);
            System.out.println("registrationDate"+convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }
}
