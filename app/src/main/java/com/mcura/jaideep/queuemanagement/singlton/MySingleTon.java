package com.mcura.jaideep.queuemanagement.singlton;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.LocalBillModel;
import com.mcura.jaideep.queuemanagement.Model.PostPaymentModel;
import com.mcura.jaideep.queuemanagement.SqliteHelper.SqlLiteDbHelper;
import com.mcura.jaideep.queuemanagement.helper.Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mCURA1 on 10/21/2016.
 */
public class MySingleTon {
    private static MySingleTon myObj;
    SqlLiteDbHelper dbHelper;
    Runnable m_Runnable;
    List<LocalBillModel> localBillModelList;
    Handler mHandler;
    public MCuraApplication mCuraApplication;

    /**
     * Create private constructor
     */
    private MySingleTon() {

    }

    /**
     * Create a static method to get instance.
     */
    public static MySingleTon getInstance() {
        if (myObj == null) {
            myObj = new MySingleTon();
        }
        return myObj;
    }

    public void getSomeThing() {
        dbHelper = new SqlLiteDbHelper(MCuraApplication.getContext());
        dbHelper.openDataBase();
        localBillModelList = new ArrayList<>();
        m_Runnable = new Runnable() {
            public void run() {
                //Log.d("singleton_Status", "not call");
                if (Helper.isInternetConnected(MCuraApplication.getContext())) {
                    localBillModelList = dbHelper.getNotUploadedBillDetail();
                    if (localBillModelList.size() > 0) {
                        postApi(0);
                    }
                }
                mHandler.postDelayed(m_Runnable, 5000);
            }
        };
        mHandler = new Handler();
        mHandler.postDelayed(m_Runnable, 5000);
    }

    public synchronized void postApi(final int index) {
        mHandler.removeCallbacks(m_Runnable);
        if (localBillModelList != null) {
            final LocalBillModel model = localBillModelList.get(index);
            boolean updateStatus = dbHelper.updatePatientBillFlag(model.getUser_role_id() + "", Helper.getCurrentDataAndTime(), model.getMrno(), model.getPatient_name(), model.getFee(), model.getAppNatureId() + "", "ON");
            //Log.d("singleton_Status", updateStatus + "");
            if (updateStatus) {
                if (model.getBill_status().equals("0") && model.getFlag().equals("OFF")) {
                    final JsonObject obj = new JsonObject();
                    obj.addProperty("SubtenantId", model.getSubTanentId());
                    obj.addProperty("HospitalNo", model.getHospital_id());
                    obj.addProperty("AppnatureId", model.getAppNatureId());
                    obj.addProperty("UserRoleId", model.getUser_role_id());
                    obj.addProperty("Description", "");
                    obj.addProperty("PaymentMode", model.getPaymentMode());
                    obj.addProperty("BillAmount", model.getFee());
                    obj.addProperty("CollectedBy", model.getCollected_by());
                    obj.addProperty("ServiceType", model.getServiceType());
                    obj.addProperty("Mrno", model.getMrno());
                    /*obj.addProperty("SubtenantId", model.getSubTanentId());
                    obj.addProperty("HospitalNo", model.getHospital_id());
                    obj.addProperty("AppnatureId", model.getAppNatureId());
                    obj.addProperty("UserRoleId", model.getUser_role_id());
                    obj.addProperty("Description", "Doctor Fee");
                    obj.addProperty("PaymentMode", model.getPaymentMode());
                    obj.addProperty("BillAmount", model.getFee());
                    obj.addProperty("CollectedBy", model.getCollected_by());
                    obj.addProperty("ServiceType", model.getServiceType());
                    obj.addProperty("Mrno", model.getMrno());
                    obj.addProperty("HIS_BillNo", 0);
                    obj.addProperty("MobileNo", patMobileNumber);
                    obj.addProperty("orderId", 0);
                    obj.addProperty("ScheduleId", scheduleId);
                    JsonArray objectKeyArray = new JsonArray();
                    objectKeyArray.add(new JsonPrimitive(""));
                    obj.add("OrdTxnIds", objectKeyArray);*/
                    mCuraApplication.getInstance().mCuraEndPoint.PostPaymentDocFee(obj, new Callback<PostPaymentModel>() {
                        @Override
                        public void success(PostPaymentModel postPaymentModel, Response response) {
                            Log.d("payment_response", postPaymentModel.getStatus() + "");
                            if (postPaymentModel.getStatus()) {
                                String id = postPaymentModel.getID();
                                String data[] = id.split("-");
                                boolean updateStatus = dbHelper.updatePatientBillRecord(model.getUser_role_id() + "", Helper.getCurrentDataAndTime(), model.getMrno(), model.getPatient_name(), model.getFee(), "1", data[1], data[0], model.getAppNatureId(), "ON");
                                if (updateStatus) {
                                    if (index < localBillModelList.size() - 1) {
                                        int i = index + 1;
                                        postApi(i);
                                    } else {
                                        mHandler.postDelayed(m_Runnable, 5000);
                                        Toast.makeText(MCuraApplication.getContext(), "End of bill", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            boolean updateStatus = dbHelper.updatePatientBillFlag(model.getUser_role_id() + "", Helper.getCurrentDataAndTime(), model.getMrno(), model.getPatient_name(), model.getFee(), model.getAppNatureId() + "", "OFF");
                            if (updateStatus) {
                                mHandler.postDelayed(m_Runnable, 5000);
                                //Toast.makeText(MCuraApplication.getContext(), "flag updated successfully", Toast.LENGTH_LONG).show();
                            } else {
                                mHandler.postDelayed(m_Runnable, 5000);
                                //Toast.makeText(MCuraApplication.getContext(), "failure", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Log.d("runnable", "duplicate");
                }
            }
        }
    }
}
