package com.mcura.jaideep.queuemanagement.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.Adapter.Queue_Adapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.AvailableTokenAdapter;
import com.mcura.jaideep.queuemanagement.Model.AvailableTokenList;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.GenerateTokenResultModel;
import com.mcura.jaideep.queuemanagement.Model.PatDemoGraphics;
import com.mcura.jaideep.queuemanagement.Model.PatientNoShowModelResponse.PostPatientNoShowModelResponse;
import com.mcura.jaideep.queuemanagement.Model.PostActivityTrackerModel.PostActivityTrackerModel;
import com.mcura.jaideep.queuemanagement.Model.QueueStatus;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class QueueStatusActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    String isDelete_Unblock = null;
    int appId;
    SwipeMenuItem item1;
    QueueStatus qStatus;
    LinearLayout bQueueHold;
    int mrno;
    AlertDialog.Builder alertDialog;
    AlertDialog ad;
    public int user_role_id;
    public int subTanentId;
    ProgressDialog progressDialog;
    public MCuraApplication mCuraApplication;
    //ListView queueList;
    Queue_Adapter adapter;
    private ArrayList<Object> itemList;
    //private itemBean bean;
    ImageButton chkin_btn, visit_entry_btn;
    QueueStatus[] queueStatusArray;
    SwipeMenuListView queueList;
    List<QueueStatus> list = new ArrayList<>();
    int year, month, date;
    String completeDate;
    ImageButton load_nfc;
    ImageView btnNextVisit,queue_current_status, queue_queue_status, queue_checkIn, queue_visting_entry, logout;
    TextView appointment, queue_mgmt, doctorName, start_opd_btn;
    AvailableTokenList[] availableTokenListsArray;
    int chartGenerateStatus;
    private SharedPreferences mSharedPreference;
    SharedPreferences.Editor editor;
    int scheduleId, token_number, tokenNo;
    AvailableTokenAdapter availableTokenAdapter;
    ImageButton checkin, visiting, add;
    String docName;
    ImageView ivDoctorProfile;
    ImageView mail_btn, btnRefresh, btnRupees;
    private TextView tv_end_opd;
    private TextView tv_opd_msg;
    private TextView billing, tv_fillcard;
    private String buildVersionName;
    private int frontOfficeUserRoleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_status);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        String docProfilePic = mSharedPreference.getString(Constant.USER_PROFILE_PIC, "default");
        scheduleId = mSharedPreference.getInt(Constant.SCHEDULE_ID, 0);
        editor = mSharedPreference.edit();
        docName = mSharedPreference.getString(Constant.LOGIN_NAME_KEY, "Undefine");
        user_role_id = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        subTanentId = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        buildVersionName = mSharedPreference.getString(Constant.BUILD_VERSION_NAME,"");
        frontOfficeUserRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
        String subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        completeDate = year + "-" + month + "-" + date; //"2016-05-09"
        Log.d("completeDate", completeDate);
        getQueueData();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        queueList = (SwipeMenuListView) findViewById(R.id.token_status_listview);
        //prepareArrayLits();
        // lview3 = (ListView) findViewById(R.id.token_status_listview);
        chkin_btn = (ImageButton) findViewById(R.id.chkin_btn);
       /* queue_current_status=(ImageView)findViewById(R.id.queue_current_status_tab);
        queue_queue_status=(ImageView)findViewById(R.id.queue_queue_status);
        queue_checkIn=(ImageView)findViewById(R.id.queue_checkIn);
        queue_visting_entry=(ImageView)findViewById(R.id.queue_visit_entry);*/
        bQueueHold = findViewById(R.id.bQueueHold);
        start_opd_btn = findViewById(R.id.start_opd_btn);
        logout = (ImageView) mToolbar.findViewById(R.id.logout);
        checkin = (ImageButton) findViewById(R.id.chk_in);
        appointment = (TextView) findViewById(R.id.appointment);
        queue_mgmt = (TextView) findViewById(R.id.queue_mgmt);

        billing = (TextView) findViewById(R.id.billing);
        tv_fillcard = (TextView) findViewById(R.id.tv_fillcard);

        tv_opd_msg = (TextView) findViewById(R.id.tv_opd_msg);
        visiting = (ImageButton) findViewById(R.id.visiting);
        //add = (ImageButton) findViewById(R.id.add_icon);
        ivDoctorProfile = (ImageView) findViewById(R.id.activity_calendar_iv_doctor);
        doctorName = (TextView) findViewById(R.id.docname);
        mail_btn = (ImageView) findViewById(R.id.mail_btn);
        btnRefresh = (ImageView) findViewById(R.id.btn_refresh);
        btnRupees = (ImageView) findViewById(R.id.btn_rupee);
        tv_end_opd = (TextView) findViewById(R.id.tv_end_opd);
        btnRefresh.setOnClickListener(this);
        btnRupees.setOnClickListener(this);
        mail_btn.setOnClickListener(this);
        billing.setOnClickListener(this);
        tv_fillcard.setOnClickListener(this);
        //set hospital logo
        ImageView hospital_logo = (ImageView) mToolbar.findViewById(R.id.hospital_logo);
        Picasso.with(QueueStatusActivity.this).load(subtanentImagePath).into(hospital_logo);
        /*if(!subtanentImagePath.equals("default")){
            Picasso.with(QueueStatusActivity.this).load(subtanentImagePath).into(hospital_logo);
        }else{
            Picasso.with(QueueStatusActivity.this).load(R.drawable.kims_logo).into(hospital_logo);
        }*/
        /*if (subTanentId == 500) {
            hospital_logo.setImageResource(R.drawable.blk_logo);
        } else if (subTanentId == 243) {
            hospital_logo.setImageResource(R.drawable.sgrh);
        }*/

        if (docProfilePic != "default") {
            Picasso.with(this).load(docProfilePic).into(ivDoctorProfile);
        }
        //Picasso.with(this).load(docProfilePic).into(ivDoctorProfile);
        /*if (user_role_id == 2104) {
            ivDoctorProfile.setImageResource(R.drawable.doctor_atul_profile);
        } else if (user_role_id == 2105) {
            ivDoctorProfile.setImageResource(R.drawable.doctor_rajiv_pfofile);
        }*/
        doctorName.setText(docName.toString());
        load_nfc = (ImageButton) findViewById(R.id.load_nfc);
        load_nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QueueStatusActivity.this, SearchPatientActivity.class).putExtra("userroleid", user_role_id));
            }
        });
        tv_end_opd.setOnClickListener(this);
        checkin.setOnClickListener(this);
        visiting.setOnClickListener(this);
        appointment.setOnClickListener(this);
        queue_mgmt.setOnClickListener(this);
        //add.setOnClickListener(this);
        start_opd_btn.setOnClickListener(this);
        /*queue_current_status.setOnClickListener(this);
        queue_queue_status.setOnClickListener(this);
        queue_checkIn.setOnClickListener(this);
        queue_visting_entry.setOnClickListener(this);*/
        visit_entry_btn = (ImageButton) findViewById(R.id.visit_entry_btn);

        logout.setOnClickListener(this);
        bQueueHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDrMessage();
            }
        });

        btnNextVisit = findViewById(R.id.btn_next);

        btnNextVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("list_size", list.size() + "");
                boolean flag = false;
                int patMRNo = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTokenStatus() != null) {
                        if (list.get(i).getTokenStatus().equals("CHECK_IN") && patMRNo == 0) {
                            patMRNo = list.get(i).getMRNo();
                            if (flag) {
                                break;
                            }
                        }
                        if (list.get(i).getTokenStatus().equals("CURRENTLY_VISITING")) {
                            patMRNo = 0;
                            flag = true;
                        }
                    }
                }
                if (patMRNo != 0) {
                    getVisitingStatus(patMRNo);
                } else {
                    Toast.makeText(QueueStatusActivity.this, "No checked In patient available to visit", Toast.LENGTH_LONG).show();
                }
            }
        });

      /*  btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("list_size", list.size() + "");
                boolean flag = false;
                int patMRNo = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTokenStatus() != null) {
                        if (list.get(i).getTokenStatus().equals("CHECK_IN") && patMRNo == 0) {
                            patMRNo = list.get(i).getMRNo();
                            if (flag) {
                                break;
                            }
                        }
                        if (list.get(i).getTokenStatus().equals("CURRENTLY_VISITING")) {
                            patMRNo = 0;
                            flag = true;
                        }
                    }
                }
                if (patMRNo != 0) {
                    getVisitingStatus(patMRNo);
                } else {
                    Toast.makeText(QueueStatusActivity.this, "No checked In patient available to visit", Toast.LENGTH_LONG).show();
                }
            }
        });*/

        chkin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QueueStatusActivity.this, CheckInActivity.class);
                startActivity(intent);
            }
        });

        visit_entry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QueueStatusActivity.this, VisitingActivity.class);
                startActivity(intent);
            }
        });

        final SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                //create an action that will be showed on swiping an item in the list
                item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.DKGRAY));
                //set width of an option (px)
                item1.setWidth(100);
                item1.setIcon(R.drawable.move);
                item1.setTitleSize(18);
                item1.setTitleColor(Color.WHITE);
                menu.addMenuItem(item1);

                SwipeMenuItem item2 = new SwipeMenuItem(
                        getApplicationContext());
                //set item background
                item2.setBackground(new ColorDrawable(Color.RED));
                item2.setWidth(100);
                item2.setTitleSize(18);
                item2.setIcon(R.drawable.delete);
                item2.setTitleColor(Color.WHITE);
                menu.addMenuItem(item2);

                SwipeMenuItem item3 = new SwipeMenuItem(
                        getApplicationContext());
                //set item background
                item3.setBackground(new ColorDrawable(Color.RED));
                item3.setWidth(100);
                item3.setTitleSize(18);
                item3.setIcon(R.drawable.ic_no_show);
                item3.setTitleColor(Color.WHITE);
                menu.addMenuItem(item3);
            }
        };


        queueList.setMenuCreator(creator);
        // set SwipeListener
        queueList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Log.d("listsize", list.size() + "");
                if (list.size() > 0) {
                    qStatus = list.get(position);
                    String tokenStatus = null;
                    if (qStatus.getTokenStatus() != null) {
                        tokenStatus = qStatus.getTokenStatus().toString();
                    }
                    //Toast.makeText(QueueStatusActivity.this,qStatus.getTokenStatus().toString(),Toast.LENGTH_LONG).show();
                    try {
                        mrno = qStatus.getMRNo();
                    } catch (NullPointerException ex) {
                        mrno = 0;
                    }

                    switch (index) {
                        case 0:
                            getAllAvailableTokenPopup();
                            /*if (mrno > 0) {
                                if (tokenStatus.equals("CHECK_IN")) {
                                    getAllAvailableTokenPopup();
                                } else if (tokenStatus.equals("PRE_BOOKED")) {
                                    getAllAvailableTokenPopup();
                                } else {
                                    Toast.makeText(QueueStatusActivity.this, "Sorry! You cannot move", Toast.LENGTH_LONG).show();
                                }
                            } else if (tokenStatus == "BLOCKED") {
                                Toast.makeText(QueueStatusActivity.this, "This Slot is blocked", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(QueueStatusActivity.this, "Wrong Selection", Toast.LENGTH_LONG).show();
                            }*/
                            break;
                        case 1:
                            showConfirmPopup(2);
                            /*if (mrno > 0) {

                                if (tokenStatus.equals("CHECK_IN")) {
                                    showConfirmPopup(2);
                                } else if (tokenStatus.equals("PRE_BOOKED")) {
                                    showConfirmPopup(1);
                                } else {
                                    Toast.makeText(QueueStatusActivity.this, "Sorry! You cannot cancel", Toast.LENGTH_LONG).show();
                                }
                            } else if (tokenStatus == "BLOCKED") {
                                Toast.makeText(QueueStatusActivity.this, "This Slot is blocked", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(QueueStatusActivity.this, "Wrong Selection", Toast.LENGTH_LONG).show();
                            }*/
                            break;
                        case 2:
                            if (mrno > 0) {
                                if (tokenStatus.equals("PRE_BOOKED")) {
                                    //Toast.makeText(QueueStatusActivity.this,qStatus.getAppId()+"",Toast.LENGTH_LONG).show();
                                    showConfirmPopup(6);
                                } else {
                                    Toast.makeText(QueueStatusActivity.this, "Wrong Selection", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(QueueStatusActivity.this, "Wrong Selection", Toast.LENGTH_LONG).show();
                            }
                            break;
                    }
                } else {
                    Toast.makeText(QueueStatusActivity.this, "Some Issue Occur", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        queueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).getMRNo() != null)
                    getPatientDetailFromApi(adapter.getItem(position).getMRNo());
                else {
                    startActivity(new Intent(QueueStatusActivity.this, AddNewAppointment.class).putExtra("appNatureId", 1).putExtra("updateStatus", "add_new_patient").putExtra("registerStatus", "queue").putExtra("actTransactionId", EnumType.ActTransactMasterEnum.Register_Patient_From_Queue.getActTransactMasterTypeId()));
                }
            }
        });
        /*queueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String tokenStatus = null;
                if (adapter.getItem(position).getTokenStatus() != null) {
                    tokenStatus = adapter.getItem(position).getTokenStatus().toString();
                }
                if (tokenStatus != null) {
                    if (tokenStatus.equals("BLOCKED")) {
                        final TextView tv_tokennumber = (TextView) view.findViewById(R.id.tv_No);
                        tv_tokennumber.setClickable(true);
                        tokenNo = Integer.parseInt(tv_tokennumber.getText().toString());
                        tv_tokennumber.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isDelete_Unblock = "true";
                                //Toast.makeText(QueueStatusActivity.this, tv_tokennumber.getText().toString(), Toast.LENGTH_LONG).show();
                                appId = adapter.getItem(position).getAppId();
                                //Toast.makeText(QueueStatusActivity.this, appId + "", Toast.LENGTH_LONG).show();
                                setBlockApi();
                            }
                        });
                    }
                } else {
                    final TextView tv_tokennumber = (TextView) view.findViewById(R.id.tv_No);
                    tv_tokennumber.setClickable(true);
                    tokenNo = Integer.parseInt(tv_tokennumber.getText().toString());
                    tv_tokennumber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isDelete_Unblock = "false";
                            appId = adapter.getItem(position).getAppId();
                            //Toast.makeText(QueueStatusActivity.this, tv_tokennumber.getText().toString(), Toast.LENGTH_LONG).show();
                            setBlockApi();
                        }
                    });
                }

            }
        });
*/
    }

    public void getVisitingStatus(int mrno) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.patient_Visit_Entry(mrno, user_role_id, subTanentId, scheduleId, completeDate, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel s, Response response) {
                int chartGenerateStatus;
                String msg = s.getMsg();
                chartGenerateStatus = s.getStatus();
                if (chartGenerateStatus == 1) {
                    showSuccessDialog(msg);
                } else {
                    showErrorDialog(msg);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }


    private void postDrMessage() {
        JsonObject obj = new JsonObject();
        obj.addProperty("UserRoleId", user_role_id);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("message", "QUEUE ON HOLD");
        obj.addProperty("status", 1);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.addDrMessage(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                if (s.equals("1")) {
                    Toast.makeText(QueueStatusActivity.this, "Queue Kept On Hold", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(QueueStatusActivity.this, "Something failed", Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                ad.dismiss();
                Toast.makeText(QueueStatusActivity.this, "Something failed", Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getQueueData();
    }

    private void getPatientDetailFromApi(final int patMrno) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getPatDemographics(patMrno, user_role_id, new Callback<PatDemoGraphics>() {
            @Override
            public void success(PatDemoGraphics patDemoGraphics, Response response) {
                Datum patDemoGraphicsData = new Datum();
                patDemoGraphicsData.setPatName(patDemoGraphics.getPatname());
                patDemoGraphicsData.setAddressId(patDemoGraphics.getAddressId());
                patDemoGraphicsData.setContactId(patDemoGraphics.getContactId());
                patDemoGraphicsData.setDob(patDemoGraphics.getDob());
                patDemoGraphicsData.setHospitalNo(patDemoGraphics.getHospitalId());
                patDemoGraphicsData.setGenderId(patDemoGraphics.getGenderId());
                patDemoGraphicsData.setEmailId(patDemoGraphics.getEmail());
                patDemoGraphicsData.setMrNo(patMrno);
                patDemoGraphicsData.setMobileNo(patDemoGraphics.getMobile().trim());
                Bundle bundle = new Bundle();
                bundle.putSerializable("PatientSearchModel",  patDemoGraphicsData);
                startActivity(new Intent(QueueStatusActivity.this, AddNewAppointment.class).putExtra("updateStatus", "update_patient").putExtras(bundle).putExtra("actTransactionId", EnumType.ActTransactMasterEnum.Edit_Patient_From_Queue.getActTransactMasterTypeId()));

                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void postPatientNoShowApi() {
        JsonObject obj = new JsonObject();
        obj.addProperty("appId", qStatus.getAppId());
        mCuraApplication.getInstance().mCuraEndPoint.postPatientNoShow(obj, new Callback<PostPatientNoShowModelResponse>() {
            @Override
            public void success(PostPatientNoShowModelResponse postPatientNoShowModelResponse, Response response) {
                JSONObject jsonObject = null;
                if (response.getStatus() == 200) {
                    try {
                        jsonObject = new JSONObject(new String(((TypedByteArray) response.getBody()).getBytes()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new Gson();
                    postPatientNoShowModelResponse = gson.fromJson(jsonObject.toString(), PostPatientNoShowModelResponse.class);
                    if(postPatientNoShowModelResponse!=null){
                        int noShowStatus = postPatientNoShowModelResponse.getStatus();
                        String noShowStatusMsg = postPatientNoShowModelResponse.getMsg();
                        switch (noShowStatus){
                            case 1:
                                showSuccessDialog(noShowStatusMsg);
                                break;
                            case 2:
                                showErrorDialog(noShowStatusMsg);
                                break;
                            case 3:
                                showErrorDialog(noShowStatusMsg);
                                break;
                            case 4:
                                showErrorDialog(noShowStatusMsg);
                                break;
                            case 5:
                                showErrorDialog(noShowStatusMsg);
                                break;
                            default:
                                showErrorDialog("Something went wrong");

                        }
                    }
                } else {
                    showErrorDialog("Something went wrong");
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
        alertDialog = new AlertDialog.Builder(QueueStatusActivity.this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Failure");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
            }
        });
        ad = alertDialog.show();
    }
    private void showSuccessDialog(String msg) {
        alertDialog = new AlertDialog.Builder(QueueStatusActivity.this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getQueueData();
                ad.dismiss();
            }
        });
        ad = alertDialog.show();
    }

    private void postActivityTrackerFromAPI(String source) {
        int slotAppId = 0;
        int actTransMasterId = 0;
        int patMrNo = 0;
        if (source.equals("start_opd")) {
            slotAppId = 0;
            actTransMasterId = EnumType.ActTransactMasterEnum.Start_OPD.getActTransactMasterTypeId();
            patMrNo = 0;
        } else if (source.equals("end_opd")) {
            slotAppId = 0;
            actTransMasterId = EnumType.ActTransactMasterEnum.End_OPD.getActTransactMasterTypeId();
            patMrNo = 0;
        } else if (source.equals("no_show")) {
            slotAppId = qStatus.getAppId();
            actTransMasterId = EnumType.ActTransactMasterEnum.No_Show.getActTransactMasterTypeId();
            patMrNo = qStatus.getMRNo();
        } else if (source.equals("msg_broadcast")) {
            slotAppId = 0;
            actTransMasterId = EnumType.ActTransactMasterEnum.Msg_Broadcast.getActTransactMasterTypeId();
            patMrNo = 0;
        }
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("actBuildVersion", Helper.getBuildVersion(QueueStatusActivity.this));
        obj.addProperty("delivered", 0);
        obj.addProperty("actUserRoleId", user_role_id);
        obj.addProperty("actSubTenantId", subTanentId);
        obj.addProperty("actScheduleId", scheduleId);
        obj.addProperty("actAppId", slotAppId);
        obj.addProperty("actUserMediumId", 4);
        obj.addProperty("drUserRoleId", user_role_id);
        obj.addProperty("actRemarks", "");
        obj.addProperty("actTransMasterId", actTransMasterId);
        obj.addProperty("patMrno", patMrNo);
        obj.addProperty("actOthers", "");

        mCuraApplication.getInstance().mCuraEndPoint.postActivityTracker(obj, new Callback<PostActivityTrackerModel>() {
            @Override
            public void success(PostActivityTrackerModel postActivityTrackerModel, Response response) {
                getQueueData();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                getQueueData();
                dismissLoadingDialog();
            }
        });
    }

    public void showConfirmPopup(final int tokenStatusId) {
        String msg="";
        if(tokenStatusId==1 || tokenStatusId==2){
            msg = "Do you want to cancel token number " + qStatus.getTokenNo() + " for " + qStatus.getPatName();
        }else if(tokenStatusId == 6){
            msg = "Do you want to No Show token number " + qStatus.getTokenNo() + " for " + qStatus.getPatName();
        }
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confimation");
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(tokenStatusId==1 || tokenStatusId==2){
                    cancelToken(mrno);
                }else if(tokenStatusId == 6){
                    postPatientNoShowApi();
                }
                ad.dismiss();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
            }
        });
        ad = alertDialog.show();
    }

    public void getAllAvailableTokenPopup() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.available_Token_List(user_role_id, subTanentId, scheduleId, completeDate, new Callback<AvailableTokenList[]>() {
            @Override
            public void success(AvailableTokenList[] availableTokenLists, Response response) {
                availableTokenListsArray = availableTokenLists;
                if (availableTokenListsArray.length > 0) {
                    showAllAvailableTokenPopup();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void showAllAvailableTokenPopup() {
        alertDialog = new AlertDialog.Builder(QueueStatusActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.search_patient_row_layout, null);
        alertDialog.setView(convertView);
        //alertDialog.setTitle("List");
        ListView lv = (ListView) convertView.findViewById(R.id.search_patient_listview);
        availableTokenAdapter = new AvailableTokenAdapter(QueueStatusActivity.this,
                android.R.layout.simple_spinner_item,
                availableTokenListsArray);
        lv.setAdapter(availableTokenAdapter);
        ad = alertDialog.show();
        lv.setOnItemClickListener(this);

    }

    public void cancelToken(int mrno) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.cancel_Token_List(mrno, user_role_id, subTanentId, scheduleId, completeDate, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                int status = generateTokenResultModel.getStatus();
                String msg = generateTokenResultModel.getMsg();
                if (status == 1) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    getQueueData();
                } else if (status == 4) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
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
        int id = v.getId();
        switch (id) {
            case R.id.btn_refresh:
                getQueueData();
                break;
            case R.id.btn_rupee:
                Intent intent = new Intent(QueueStatusActivity.this, CentralizedBillingActivity.class);
                startActivity(intent);
                break;
            /*case R.id.queue_current_status_tab:
                Intent intent=new Intent(Queue_status.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.queue_checkIn:
                Intent in=new Intent(Queue_status.this,CheckInActivity.class);
                startActivity(in);
                break;
            case R.id.queue_visit_entry:
                Intent intent1=new Intent(Queue_status.this,VisitActivity.class);
                startActivity(intent1);
                break;*/
            case R.id.start_opd_btn:
                postStartOPD();
                break;
            case R.id.tv_end_opd:
                showEndOpdConfirmationDialog();
                //postEndOpd();
                break;
            case R.id.logout:
                SharedPreferences.Editor editor = mSharedPreference.edit();
                editor.remove(Constant.USER_ROLE_ID_KEY);
                editor.remove(Constant.SUB_TANENT_IMAGE_PATH);
                editor.apply();
                Intent intentLogout = new Intent(QueueStatusActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                break;
            case R.id.chk_in:
                startActivity(new Intent(QueueStatusActivity.this, CheckInActivity.class));
                break;
            case R.id.visiting:
                startActivity(new Intent(QueueStatusActivity.this, VisitingActivity.class));
                break;
            case R.id.appointment:
                startActivity(new Intent(QueueStatusActivity.this, CalendarActivity.class));
                break;
            case R.id.queue_mgmt:
                /*startActivity(new Intent(QueueStatusActivity.this, QueueStatusActivity.class));*/
                break;
            case R.id.billing:
                startActivity(new Intent(QueueStatusActivity.this, CentralizedBillingActivity.class));
                break;
            case R.id.tv_fillcard:
                startActivity(new Intent(QueueStatusActivity.this, FillCashCardActivity.class));
                break;
            case R.id.mail_btn:
                //Toast.makeText(QueueStatusActivity.this,"Hello",Toast.LENGTH_LONG).show();
                alertDialog = new AlertDialog.Builder(QueueStatusActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = inflater.inflate(R.layout.message_dialog_layout, null);
                alertDialog.setView(convertView);
                final EditText message = (EditText) convertView.findViewById(R.id.message);
                TextView send_message = (TextView) convertView.findViewById(R.id.alert_submit);
                TextView cancel_message = (TextView) convertView.findViewById(R.id.alert_cancel);
                cancel_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                });
                send_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (message.getText().toString().length() > 0) {
                            JsonObject obj = new JsonObject();
                            obj.addProperty("UserRoleId", user_role_id);
                            obj.addProperty("SubTenantId", subTanentId);
                            obj.addProperty("ScheduleId", scheduleId);
                            obj.addProperty("message", message.getText().toString());
                            obj.addProperty("status", 1);
                            showLoadingDialog();
                            mCuraApplication.getInstance().mCuraEndPoint.addDrMessage(obj, new Callback<String>() {
                                @Override
                                public void success(String s, Response response) {
                                    if (s.equals("1")) {
                                        postActivityTrackerFromAPI("msg_broadcast");
                                        Toast.makeText(QueueStatusActivity.this, "Message Successfully Send", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(QueueStatusActivity.this, "Message Failed", Toast.LENGTH_LONG).show();
                                    }
                                    ad.dismiss();
                                    dismissLoadingDialog();
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    ad.dismiss();
                                    dismissLoadingDialog();
                                }
                            });
                        } else {
                            message.setError("Put message to send");
                        }

                    }
                });
                ad = alertDialog.show();
                break;
        }
    }

    private void showEndOpdConfirmationDialog() {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(QueueStatusActivity.this);

        // Set the message show for the Alert time
        String alert1 = "OPD cannot be re-started, if it has ended.";
        String alert2 = "Do you want to proceed further with ending the OPD?";
        builder.setMessage(alert1 + "\n" + alert2);

        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                postEndOpd();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    private void postEndOpd() {
        JsonObject obj = new JsonObject();
        obj.addProperty("UserRoleId", user_role_id);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("Date", completeDate);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.end_OPD(obj, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                String msg = null;
                msg = generateTokenResultModel.getMsg();
                Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                int endChartGenerateStatus = generateTokenResultModel.getStatus();
                if (endChartGenerateStatus == 1) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    postActivityTrackerFromAPI("end_opd");
                    //getQueueData();
                } else if (endChartGenerateStatus == 2) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    getQueueData();
                } else if (endChartGenerateStatus == 3) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                } else if (endChartGenerateStatus == 4) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                } else {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void postStartOPD() {
        JsonObject obj = new JsonObject();
        obj.addProperty("UserRoleId", user_role_id);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("ScheduleId", scheduleId);
        obj.addProperty("Date", completeDate);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.generate_Token_Chart(obj, new Callback<GenerateTokenResultModel>() {

            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                String msg = null;
                msg = generateTokenResultModel.getMsg();
                chartGenerateStatus = generateTokenResultModel.getStatus();
                if (chartGenerateStatus == 1) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    postActivityTrackerFromAPI("start_opd");
                    //getQueueData();
                } else if (chartGenerateStatus == 2) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    getQueueData();
                } else if (chartGenerateStatus == 3) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                } else if (chartGenerateStatus == 4) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                } else if (chartGenerateStatus == 5) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                } else if (chartGenerateStatus == 6) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    getQueueData();
                } else {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                }
                //dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
                Toast.makeText(QueueStatusActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getQueueData() {
        queueStatusArray = new QueueStatus[]{};
        list.clear();
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.tokenViewOther(user_role_id, subTanentId, scheduleId, completeDate, new Callback<QueueStatus[]>() {
            @Override
            public void success(QueueStatus[] queueStatus, Response response) {
                if (queueStatus != null) {
                    queueStatusArray = queueStatus;
                    if (queueStatusArray.length > 0) {
                        tv_opd_msg.setVisibility(View.GONE);
                        queueList.setVisibility(View.VISIBLE);
                        for (int i = 0; i < queueStatusArray.length; i++) {
                            list.add(queueStatusArray[i]);
                        }
                        adapter = new Queue_Adapter(QueueStatusActivity.this, list, scheduleId);
                        queueList.setAdapter(adapter);
                        Collections.sort(list, new Comparator<QueueStatus>() {
                            @Override
                            public int compare(QueueStatus lhs, QueueStatus rhs) {
                                return Integer.valueOf(lhs.getTokenNo()).compareTo(rhs.getTokenNo());
                            }
                        });
                        dismissLoadingDialog();
                    } else if (queueStatusArray == null) {
                        Toast.makeText(QueueStatusActivity.this, "NoShowData Not Available", Toast.LENGTH_LONG).show();
                        dismissLoadingDialog();
                    } else {
                        queueList.setVisibility(View.GONE);
                        tv_opd_msg.setVisibility(View.VISIBLE);
                        //Toast.makeText(QueueStatusActivity.this, "NoShowData Not Available", Toast.LENGTH_LONG).show();
                        dismissLoadingDialog();
                    }
                } else {
                    Toast.makeText(QueueStatusActivity.this, "NoShowData Not Available", Toast.LENGTH_LONG).show();
                    dismissLoadingDialog();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(QueueStatusActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        });
    }

    /**
     *
     */
    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(QueueStatusActivity.this);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        token_number = availableTokenAdapter.getItem(position).getTokenNo();
        ad.dismiss();
        moveTokenList();
    }

    public void moveTokenList() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.move_Token_List(mrno, user_role_id, subTanentId, scheduleId, token_number, completeDate, new Callback<GenerateTokenResultModel>() {
            @Override
            public void success(GenerateTokenResultModel generateTokenResultModel, Response response) {
                int status = generateTokenResultModel.getStatus();
                String msg = generateTokenResultModel.getMsg();
                if (status == 1) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
                    getQueueData();
                } else if (status == 4) {
                    Toast.makeText(QueueStatusActivity.this, msg, Toast.LENGTH_LONG).show();
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
    public void onBackPressed() {
        // Do as you please
    }

    public void setBlockApi() {
        JsonObject blockPatient = new JsonObject();
        blockPatient.addProperty("appointmentid", appId);
        blockPatient.addProperty("UserRoleID", user_role_id);
        blockPatient.addProperty("isDelete_Unblock", isDelete_Unblock);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.updateAppointment_Block(blockPatient, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Toast.makeText(getApplicationContext(), "Response-->" + s, Toast.LENGTH_LONG).show();
                if (isDelete_Unblock.equals("true")) {
                    Intent in = new Intent(QueueStatusActivity.this, CheckInActivity.class).putExtra("TokenNo", tokenNo).putExtra("appId", appId);
                    startActivity(in);
                } else if (isDelete_Unblock.equals("false")) {
                    getQueueData();
                }

                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }
}




