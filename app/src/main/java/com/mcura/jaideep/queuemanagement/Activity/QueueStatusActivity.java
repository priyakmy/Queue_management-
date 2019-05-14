package com.mcura.jaideep.queuemanagement.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mcura.jaideep.queuemanagement.Adapter.Queue_Adapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.AvailableTokenAdapter;
import com.mcura.jaideep.queuemanagement.Model.AvailableTokenList;
import com.mcura.jaideep.queuemanagement.Model.GenerateTokenResultModel;
import com.mcura.jaideep.queuemanagement.Model.QueueStatus;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class QueueStatusActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    String isDelete_Unblock = null;
    int appId;
    SwipeMenuItem item1;
    QueueStatus qStatus;
    private ToggleButton queueStatusToggle;
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
    ImageButton start_opd_btn, load_nfc;
    ImageView queue_current_status, queue_queue_status, queue_checkIn, queue_visting_entry, logout;
    TextView appointment, queue_mgmt, doctorName;
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
        start_opd_btn = (ImageButton) findViewById(R.id.start_opd_btn);
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
        queueStatusToggle = (ToggleButton) findViewById(R.id.queue_status_toggle);
        /*if(MainActivity.queueStatus==true){
            queueStatusToggle.setChecked(MainActivity.queueStatus);
        }
        else{
            queueStatusToggle.setChecked(MainActivity.queueStatus);
        }*/
        //MainActivity.queueStatus = queueStatusToggle.isChecked();
        logout.setOnClickListener(this);
        queueStatusToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //MainActivity.queueStatus = queueStatusToggle.isChecked();
            }
        });

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
                            if (mrno > 0) {
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
                            }
                            break;
                        case 1:
                            if (mrno > 0) {
                                if (tokenStatus.equals("CHECK_IN")) {
                                    showConfirmPopup();
                                } else if (tokenStatus.equals("PRE_BOOKED")) {
                                    showConfirmPopup();
                                } else {
                                    Toast.makeText(QueueStatusActivity.this, "Sorry! You cannot cancel", Toast.LENGTH_LONG).show();
                                }
                            } else if (tokenStatus == "BLOCKED") {
                                Toast.makeText(QueueStatusActivity.this, "This Slot is blocked", Toast.LENGTH_LONG).show();
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

    public void showConfirmPopup() {
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Do you want to cancel token number " + qStatus.getTokenNo() + " for " + qStatus.getPatName());
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelToken(mrno);
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
                    getQueueData();
                } else if (endChartGenerateStatus == 5) {
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
                    getQueueData();
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
                if(queueStatus!=null){
                    queueStatusArray = queueStatus;
                    if (queueStatusArray.length > 0) {
                        tv_opd_msg.setVisibility(View.GONE);
                        queueList.setVisibility(View.VISIBLE);
                        for (int i = 0; i < queueStatusArray.length; i++) {
                            list.add(queueStatusArray[i]);
                        }
                        adapter = new Queue_Adapter(QueueStatusActivity.this, list,scheduleId);
                        queueList.setAdapter(adapter);
                        Collections.sort(list, new Comparator<QueueStatus>() {
                            @Override
                            public int compare(QueueStatus lhs, QueueStatus rhs) {
                                return Integer.valueOf(lhs.getTokenNo()).compareTo(rhs.getTokenNo());
                            }
                        });
                        dismissLoadingDialog();
                    } else if(queueStatusArray==null){
                        Toast.makeText(QueueStatusActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
                        dismissLoadingDialog();
                    }else {
                        queueList.setVisibility(View.GONE);
                        tv_opd_msg.setVisibility(View.VISIBLE);
                        //Toast.makeText(QueueStatusActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
                        dismissLoadingDialog();
                    }
                }else{
                    Toast.makeText(QueueStatusActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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




