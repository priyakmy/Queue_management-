package com.mcura.jaideep.queuemanagement.SqliteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.mcura.jaideep.queuemanagement.Model.DepartmentModel;
import com.mcura.jaideep.queuemanagement.Model.DoctorList;
import com.mcura.jaideep.queuemanagement.Model.DoctorNature;
import com.mcura.jaideep.queuemanagement.Model.Doctor_Fee;
import com.mcura.jaideep.queuemanagement.Model.LocalBillModel;
import com.mcura.jaideep.queuemanagement.helper.Helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SqlLiteDbHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mcura_db.sqlite";
    // Contacts table name
    private static final String DB_PATH_SUFFIX = "/databases/";
    //    private static final String TABLE_CONTACT = "contact";
//    private SQLiteDatabase db;
//    // Contacts Table Columns names
//    private static final String KEY_ID = "id";
//    private static final String KEY_NAME = "name";
//    private static final String KEY_MOBILENO = "mobile_no";
    static Context ctx;

    public SqlLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    public boolean insertBillDetail(String doctor_name, String user_role_id, String date, String mrno, String patient_name, String fee, String fee_nature, String bill_status, String bill_no, String e_wallet_id, String department, String hospital_id, String collected_by, String temp_billno, String subtanent_id, String appnature_id, String payment_mode, String service_type,int serial_no,String time,String flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("doctor_name", doctor_name);
        cv.put("user_role_id", user_role_id);
        cv.put("date", date);
        cv.put("mrno", mrno);
        cv.put("patient_name", patient_name);
        cv.put("fee", fee);
        cv.put("fee_nature", fee_nature);
        cv.put("bill_status", bill_status);
        cv.put("bill_no", bill_no);
        cv.put("e_wallet_id", e_wallet_id);
        cv.put("department", department);
        cv.put("hospital_id", hospital_id);
        cv.put("collected_by", collected_by);
        cv.put("temp_billno", temp_billno);
        cv.put("subtanent_id", subtanent_id);
        cv.put("appnature_id", appnature_id);
        cv.put("payment_mode", payment_mode);
        cv.put("service_type", service_type);
        cv.put("serial_no",serial_no);
        cv.put("time",time);
        cv.put("flag",flag);
        long rowInserted = db.insert("bill_detail", null, cv);
        //Toast.makeText(ctx, "" + rowInserted, Toast.LENGTH_SHORT).show();
        boolean status;
        if (rowInserted != -1) {
            status = true;
            Toast.makeText(ctx, "Successfully paid", Toast.LENGTH_SHORT).show();
        } else {
            status = false;
            Toast.makeText(ctx, "Something wrong", Toast.LENGTH_SHORT).show();
        }
        db.close();
        return status;
    }

    public boolean updatePatientBillRecord(String user_role_id, String date, String mrno, String patient_name, String fee, String bill_status, String bill_no, String e_wallet_id,String appNatureId,String flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Log.d("db_data", bill_status);
        Log.d("db_data", bill_no);
        Log.d("db_data", e_wallet_id);
        cv.put("bill_status", bill_status);
        cv.put("bill_no", bill_no);
        cv.put("e_wallet_id", e_wallet_id);
        cv.put("flag",flag);
        //db.rawQuery("UPDATE bill_detail set bill_status = '"+bill_status+"',bill_no = '"+bill_no+"',e_wallet_id = '"+e_wallet_id+"', department ='"+department+"'where user_role_id ='"+user_role_id+"' and date = '"+date+"' and mrno ='"+mrno+"' and patient_name = '"+patient_name+"' and fee = '"+fee+"'",null);
        long rowInserted = db.update("bill_detail", cv, "user_role_id = ? and date = ? and mrno = ? and patient_name = ? and fee = ? and appnature_id = ?", new String[]{user_role_id, date, mrno, patient_name, fee,appNatureId});
        boolean status;
        if (rowInserted != -1) {
            status = true;
            Toast.makeText(ctx, "Successfully paid", Toast.LENGTH_SHORT).show();
        } else {
            status = false;
            Toast.makeText(ctx, "Something wrong", Toast.LENGTH_SHORT).show();
        }
        db.close();
        return status;
    }
    public boolean updatePatientBillFlag(String user_role_id, String date, String mrno, String patient_name, String fee,String appNatureId,String flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("flag", flag);
        //db.rawQuery("UPDATE bill_detail set bill_status = '"+bill_status+"',bill_no = '"+bill_no+"',e_wallet_id = '"+e_wallet_id+"', department ='"+department+"'where user_role_id ='"+user_role_id+"' and date = '"+date+"' and mrno ='"+mrno+"' and patient_name = '"+patient_name+"' and fee = '"+fee+"'",null);
        long rowInserted = db.update("bill_detail", cv, "user_role_id = ? and date = ? and mrno = ? and patient_name = ? and fee = ? and appnature_id = ?", new String[]{user_role_id, date, mrno, patient_name, fee,appNatureId});
        boolean status;
        if (rowInserted != -1) {
            status = true;
            //Toast.makeText(ctx, "Successfully paid", Toast.LENGTH_SHORT).show();
        } else {
            status = false;
            //Toast.makeText(ctx, "Something wrong", Toast.LENGTH_SHORT).show();
        }
        db.close();
        return status;
    }
    public LocalBillModel patBillStatus(String user_role_id, String date, String mrno, String pat_name, String fee, String appNatureId) {
        String bill_status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        LocalBillModel localBillModel = null;
        List<LocalBillModel> localBillModelList = new ArrayList<>();
        String query = "SELECT * FROM bill_detail where user_role_id ='" + user_role_id + "' and date = '" + date + "' and mrno ='" + mrno + "' and patient_name = '" + pat_name + "' and fee = '" + fee + "' and appnature_id = '"+appNatureId+"'";
        Log.d("query", query);
        Cursor cursor = db.rawQuery("SELECT * FROM bill_detail where user_role_id ='" + user_role_id + "' and date = '" + date + "' and mrno ='" + mrno + "' and patient_name = '" + pat_name + "' and fee = '" + fee + "' and appnature_id = '" + appNatureId + "'", null);
        boolean exist = (cursor.getCount() > 0);
        if (exist) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                localBillModel = new LocalBillModel();
                localBillModel.setDoctor_name(cursor.getString(0));
                localBillModel.setUser_role_id(cursor.getString(1));
                localBillModel.setDate(cursor.getString(2));
                localBillModel.setMrno(cursor.getString(3));
                localBillModel.setPatient_name(cursor.getString(4));
                localBillModel.setFee(cursor.getString(5));
                localBillModel.setFee_nature(cursor.getString(6));
                localBillModel.setBill_status(cursor.getString(7));
                localBillModel.setBill_no(cursor.getString(8));
                localBillModel.setE_wallet_id(cursor.getString(9));
                localBillModel.setDepartment(cursor.getString(10));
                localBillModel.setHospital_id(cursor.getString(11));
                localBillModel.setCollected_by(cursor.getString(12));
                localBillModel.setTempBillNo(cursor.getString(13));
                localBillModel.setSubTanentId(cursor.getString(14));
                localBillModel.setAppNatureId(cursor.getString(15));
                localBillModel.setPaymentMode(cursor.getString(16));
                localBillModel.setServiceType(cursor.getString(17));
                localBillModel.setSerial_no(cursor.getInt(18));
                localBillModel.setTime(cursor.getString(19));
                //localBillModelList.add(localBillModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return localBillModel;
    }
    public LocalBillModel patBillStatusLocal(String user_role_id, String date, String hospital_id, String pat_name, String fee, String appNatureId) {
        String bill_status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        LocalBillModel localBillModel = null;
        List<LocalBillModel> localBillModelList = new ArrayList<>();
        String query = "SELECT * FROM bill_detail where user_role_id ='" + user_role_id + "' and date = '" + date + "' and hospital_id ='" + hospital_id + "' and patient_name = '" + pat_name + "' and fee = '" + fee + "' and appnature_id = '"+appNatureId+"'";
        Log.d("query", query);
        Cursor cursor = db.rawQuery("SELECT * FROM bill_detail where user_role_id ='" + user_role_id + "' and date = '" + date + "' and hospital_id ='" + hospital_id + "' and patient_name = '" + pat_name + "' and fee = '" + fee + "' and appnature_id = '" + appNatureId + "'", null);
        boolean exist = (cursor.getCount() > 0);
        if (exist) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                localBillModel = new LocalBillModel();
                localBillModel.setDoctor_name(cursor.getString(0));
                localBillModel.setUser_role_id(cursor.getString(1));
                localBillModel.setDate(cursor.getString(2));
                localBillModel.setMrno(cursor.getString(3));
                localBillModel.setPatient_name(cursor.getString(4));
                localBillModel.setFee(cursor.getString(5));
                localBillModel.setFee_nature(cursor.getString(6));
                localBillModel.setBill_status(cursor.getString(7));
                localBillModel.setBill_no(cursor.getString(8));
                localBillModel.setE_wallet_id(cursor.getString(9));
                localBillModel.setDepartment(cursor.getString(10));
                localBillModel.setHospital_id(cursor.getString(11));
                localBillModel.setCollected_by(cursor.getString(12));
                localBillModel.setTempBillNo(cursor.getString(13));
                localBillModel.setSubTanentId(cursor.getString(14));
                localBillModel.setAppNatureId(cursor.getString(15));
                localBillModel.setPaymentMode(cursor.getString(16));
                localBillModel.setServiceType(cursor.getString(17));
                localBillModel.setSerial_no(cursor.getInt(18));
                localBillModel.setTime(cursor.getString(19));
                //localBillModelList.add(localBillModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return localBillModel;
    }
    public List<LocalBillModel> getvalidateBillDetail(String user_role_id, String mrno, String date) {
        String bill_status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        LocalBillModel localBillModel = null;
        List<LocalBillModel> localBillModelList = new ArrayList<>();
        String query = "SELECT * FROM bill_detail where user_role_id ='" + user_role_id + "' and date = '" + date + "' and mrno ='" + mrno + "'";
        Log.d("query", query);
        Cursor cursor = db.rawQuery("SELECT * FROM bill_detail where user_role_id ='" + user_role_id + "' and date = '" + date + "' and mrno ='" + mrno + "'", null);
        boolean exist = (cursor.getCount() > 0);
        if (exist) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                localBillModel = new LocalBillModel();
                localBillModel.setDoctor_name(cursor.getString(0));
                localBillModel.setUser_role_id(cursor.getString(1));
                localBillModel.setDate(cursor.getString(2));
                localBillModel.setMrno(cursor.getString(3));
                localBillModel.setPatient_name(cursor.getString(4));
                localBillModel.setFee(cursor.getString(5));
                Log.d("refund_amount", cursor.getString(5));
                localBillModel.setFee_nature(cursor.getString(6));
                localBillModel.setBill_status(cursor.getString(7));
                localBillModel.setBill_no(cursor.getString(8));
                localBillModel.setE_wallet_id(cursor.getString(9));
                localBillModel.setDepartment(cursor.getString(10));
                localBillModel.setHospital_id(cursor.getString(11));
                localBillModel.setCollected_by(cursor.getString(12));
                localBillModel.setTempBillNo(cursor.getString(13));
                localBillModel.setSubTanentId(cursor.getString(14));
                localBillModel.setAppNatureId(cursor.getString(15));
                localBillModel.setPaymentMode(cursor.getString(16));
                localBillModel.setServiceType(cursor.getString(17));
                localBillModel.setSerial_no(cursor.getInt(18));
                localBillModel.setTime(cursor.getString(19));
                localBillModelList.add(localBillModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return localBillModelList;
    }
    public List<LocalBillModel> getNotUploadedBillDetail() {
        String bill_status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        LocalBillModel localBillModel = null;
        List<LocalBillModel> localBillModelList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM bill_detail where date = '" + Helper.getCurrentDataAndTime()+ "' and bill_status ='" + 0 + "' and flag = 'OFF'", null);
        boolean exist = (cursor.getCount() > 0);
        if (exist) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                localBillModel = new LocalBillModel();
                localBillModel.setDoctor_name(cursor.getString(0));
                localBillModel.setUser_role_id(cursor.getString(1));
                localBillModel.setDate(cursor.getString(2));
                localBillModel.setMrno(cursor.getString(3));
                localBillModel.setPatient_name(cursor.getString(4));
                localBillModel.setFee(cursor.getString(5));
                localBillModel.setFee_nature(cursor.getString(6));
                localBillModel.setBill_status(cursor.getString(7));
                localBillModel.setBill_no(cursor.getString(8));
                localBillModel.setE_wallet_id(cursor.getString(9));
                localBillModel.setDepartment(cursor.getString(10));
                localBillModel.setHospital_id(cursor.getString(11));
                localBillModel.setCollected_by(cursor.getString(12));
                localBillModel.setTempBillNo(cursor.getString(13));
                localBillModel.setSubTanentId(cursor.getString(14));
                localBillModel.setAppNatureId(cursor.getString(15));
                localBillModel.setPaymentMode(cursor.getString(16));
                localBillModel.setServiceType(cursor.getString(17));
                localBillModel.setSerial_no(cursor.getInt(18));
                localBillModel.setTime(cursor.getString(19));
                localBillModel.setFlag(cursor.getString(20));
                localBillModelList.add(localBillModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return localBillModelList;
    }
    public List<LocalBillModel> getCompleteBillStatus(String date) {
        String bill_status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        LocalBillModel localBillModel = null;
        List<LocalBillModel> localBillModelList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM bill_detail where date ='" + date + "'", null);
        boolean exist = (cursor.getCount() > 0);
        if (exist) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                localBillModel = new LocalBillModel();
                localBillModel.setDoctor_name(cursor.getString(0));
                localBillModel.setUser_role_id(cursor.getString(1));
                localBillModel.setDate(cursor.getString(2));
                localBillModel.setMrno(cursor.getString(3));
                localBillModel.setPatient_name(cursor.getString(4));
                localBillModel.setFee(cursor.getString(5));
                localBillModel.setFee_nature(cursor.getString(6));
                localBillModel.setBill_status(cursor.getString(7));
                localBillModel.setBill_no(cursor.getString(8));
                localBillModel.setE_wallet_id(cursor.getString(9));
                localBillModel.setDepartment(cursor.getString(10));
                localBillModel.setHospital_id(cursor.getString(11));
                localBillModel.setCollected_by(cursor.getString(12));
                localBillModel.setTempBillNo(cursor.getString(13));
                localBillModel.setSubTanentId(cursor.getString(14));
                localBillModel.setAppNatureId(cursor.getString(15));
                localBillModel.setPaymentMode(cursor.getString(16));
                localBillModel.setServiceType(cursor.getString(17));
                localBillModel.setSerial_no(cursor.getInt(18));
                localBillModel.setTime(cursor.getString(19));
                localBillModelList.add(localBillModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return localBillModelList;
    }

    // Getting Doctor Department
    public String Get_DoctorDepartment(String userroleid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String departmentName = "";
        Log.d("query","SELECT dept_name from doc_department inner join doc_list on doc_department.dept_id = doc_list.dept_id where doc_list.user_role_id = '"+userroleid+"' limit 1");
        Cursor cursor = db.rawQuery("SELECT dept_name from doc_department inner join doc_list on doc_department.dept_id = doc_list.dept_id where doc_list.user_role_id = '"+userroleid+"' limit 1", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            departmentName = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return departmentName;
    }

    // Getting Doctor Fee
    public List<DepartmentModel> Get_DoctorDepartmentLocal(String deptId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DepartmentModel> departmentModelList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM doc_department where dept_id = "+deptId, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            DepartmentModel departmentModel = new DepartmentModel();
            departmentModel.setDept_id(cursor.getString(0));
            departmentModel.setDept_name(cursor.getString(1));
            departmentModelList.add(departmentModel);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return departmentModelList;
    }
    public List<LocalBillModel> getdummyCompleteBillStatus(int l, int h) {
        String bill_status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        LocalBillModel localBillModel = null;
        List<LocalBillModel> localBillModelList = new ArrayList<>();
        String query = "SELECT * FROM bill_detail limit " + l + "," + h;
        Log.d("query", query);
        Cursor cursor = db.rawQuery("SELECT * FROM bill_detail limit " + l + "," + h, null);
        boolean exist = (cursor.getCount() > 0);
        if (exist) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                localBillModel = new LocalBillModel();
                localBillModel.setDoctor_name(cursor.getString(0));
                localBillModel.setUser_role_id(cursor.getString(1));
                localBillModel.setDate(cursor.getString(2));
                localBillModel.setMrno(cursor.getString(3));
                localBillModel.setPatient_name(cursor.getString(4));
                localBillModel.setFee(cursor.getString(5));
                localBillModel.setFee_nature(cursor.getString(6));
                localBillModel.setBill_status(cursor.getString(7));
                localBillModel.setBill_no(cursor.getString(8));
                localBillModel.setE_wallet_id(cursor.getString(9));
                localBillModel.setDepartment(cursor.getString(10));
                localBillModel.setHospital_id(cursor.getString(11));
                localBillModel.setCollected_by(cursor.getString(12));
                localBillModel.setTempBillNo(cursor.getString(13));
                localBillModel.setSubTanentId(cursor.getString(14));
                localBillModel.setAppNatureId(cursor.getString(15));
                localBillModel.setPaymentMode(cursor.getString(16));
                localBillModel.setServiceType(cursor.getString(17));
                localBillModel.setSerial_no(cursor.getInt(18));
                localBillModel.setTime(cursor.getString(19));
                localBillModelList.add(localBillModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return localBillModelList;
    }

    // Getting Nature List
    public List<DoctorNature> Get_DoctorNature(String userRoleId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DoctorNature> doctorNatureList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT doc_app_nature.app_nature_id,doc_app_nature.app_nature FROM doc_app_nature inner join doc_fee on doc_fee.app_nature_id = doc_app_nature.app_nature_id where doc_fee.user_role_id = '"+userRoleId+"'", null);
        Log.d("query","SELECT doc_app_nature.app_nature_id,doc_app_nature.app_nature FROM doc_app_nature inner join doc_fee on doc_fee.app_nature_id = doc_app_nature.app_nature_id where user_role_id = '"+userRoleId+"'");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            DoctorNature doctorNature = new DoctorNature();
            doctorNature.setApp_nature_id(cursor.getString(0));
            doctorNature.setApp_nature(cursor.getString(1));
            doctorNatureList.add(doctorNature);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return doctorNatureList;
    }
    // Getting Doctor Fee
    public List<Doctor_Fee> Get_DoctorFee(String userRoleId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Doctor_Fee> doctorFeeList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM doc_fee where user_role_id = "+userRoleId, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Doctor_Fee doctorFee = new Doctor_Fee();
            doctorFee.setFee_id(cursor.getString(0));
            doctorFee.setUser_Role_id(cursor.getString(1));
            doctorFee.setApp_Nature_id(cursor.getString(2));
            doctorFee.setFee_Amount(cursor.getString(3));
            doctorFeeList.add(doctorFee);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return doctorFeeList;
    }

    /*// Getting Doctor Department
    public List<DepartmentModel> Get_DoctorDepartmentLocal(String deptId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DepartmentModel> departmentModelList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM doc_department where dept_id = "+deptId, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            DepartmentModel departmentModel = new DepartmentModel();
            departmentModel.setDept_id(cursor.getString(0));
            departmentModel.setDept_name(cursor.getString(1));
            departmentModelList.add(departmentModel);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return departmentModelList;
    }*/

    // Getting Doctor List
    public List<DoctorList> Get_ContactDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DoctorList> doctorLists = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM doc_list", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            DoctorList doctorList = new DoctorList();
            doctorList.setUser_role_id(cursor.getString(0));
            doctorList.setDocName(cursor.getString(1));
            doctorList.setDept_id(cursor.getString(2));
            doctorLists.add(doctorList);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return doctorLists;
    }
    public int getMaxSerial_no(){
        int max = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        final SQLiteStatement stmt = db
                .compileStatement("SELECT MAX(serial_no) FROM bill_detail");
        int value = (int) stmt.simpleQueryForLong();
        db.close();
        return value;

    }
/*    private void CopyDataBaseFromAsset() throws IOException {
        // Open your local db as the input stream
        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = getDatabaseName();
        Log.d("outFileName",outFileName);
        // Open the empty db as the output stream
        new File(outFileName).createNewFile();
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private static String getDatabasePath() {
        *//*return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;*//*
        return Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+"mcura_db.sqlite";
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = new File(getDatabasePath());
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
        db.close();
        return db;
    }*/


    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = getDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}

