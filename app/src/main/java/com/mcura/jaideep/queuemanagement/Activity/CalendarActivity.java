package com.mcura.jaideep.queuemanagement.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mcura.jaideep.queuemanagement.Adapter.CalenderDoctorAdapter;
import com.mcura.jaideep.queuemanagement.Adapter.CalenderScheduleAdapter;
import com.mcura.jaideep.queuemanagement.BuildConfig;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.DoctorListModel;
import com.mcura.jaideep.queuemanagement.Model.GetUserPhotoModel;
import com.mcura.jaideep.queuemanagement.Model.HospitalName;
import com.mcura.jaideep.queuemanagement.Model.ScheduleModel;
import com.mcura.jaideep.queuemanagement.Model.ScheduleModelResult;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.Helper;
import com.squareup.picasso.Picasso;

import org.jsoup.helper.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {
    CalenderScheduleAdapter scheduleSpinnerAdapter;
    CalenderDoctorAdapter doctorSpinnerAdapter;
    //private Spinner doctor_name,doctor_schedule;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor,edit;
    String  Assistat_Roll_id ="";
    String  AssistantUserName ="";
    String  AssistantUserrollid ="";
    ImageView chat;
    ImageButton ibSearchPatient,load_nfc;
    private static String CALENDAR_TAG = CalendarActivity.class.getName();
    public MCuraApplication mCuraApplication;
    DoctorListModel[] doctorArray;
    int weekDaysCount = 0;
    private Toolbar mToolbar;
    private ImageView ivDoctorProfile,doctor_info;
    private String[] nextPreWeekday;
    private TextView tvSunday;
    private TextView tvMonday;
    private TextView tvTuesday;
    private TextView tvWednesday;
    private TextView tvThursday;
    private TextView tvFriday;
    private TextView tvSaturday;
    private ImageView ivPreviousWeek;
    private ImageView ivNextWeek,calendar_account;
    private TextView doctorName;
    ImageButton doctor;
    private int mCurrentWeek = 1;
    private SharedPreferences mSharedPreference;
    private int userRoleId;
    private ProgressDialog progressDialog;
    private int mScreenWidth;
    ImageView logout;
    private int mScreenHeight;
    private int mScheduleHeight;
    private int mAppointmentViewHeight;
    private int mMidViewWidth;
    private int mCustomViewWidth;
    private LinearLayout mAppointmentView;
    private HospitalName hospitalNameOne;
    private ScheduleModel[] mScheduleArray;
    private TextView tv_monthyear;
    String[] days = null;
    //int month;
    TextView tvHospitalName;
    String toTime,fromTime,from_time,to_time;
    String scheduleName,hospitalNameStr;
    int subTanentId,scheduleId,sub_tanent_id;

    String year,month,day;
    //String day = "";
    String reqDate;
    String currDate;
    ScheduleModelResult scheduleModelResult;
    List<ScheduleModel> scheduleModelList;
    String monthArray[] = {"JANUARY","FEBUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
    ImageButton checkin, visit;
    EditText search;
    TextView queue;
    String docName;
    GetUserPhotoModel getUserPhotoModelObject;
    String docProfilePic;
    private TextView billing,tv_fillcard;
    private String version;
    private Helper helper;

    /***
     * Method to convert week days
     *
     * @param date
     * @return
     */
    public static String convertWeekDays(String date) {
        String formattedDate = null;
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd");
            Date date12 = originalFormat.parse(date);
            formattedDate = targetFormat.format(date12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;

    }
    public void getUserPhotoFromAPi(){
       helper.showLoadingDialog(CalendarActivity.this);
        mCuraApplication.getInstance().mCuraEndPoint.getUser_Photo(userRoleId, new Callback<GetUserPhotoModel>() {
            @Override
            public void success(GetUserPhotoModel getUserPhotoModel, Response response) {
                getUserPhotoModelObject = getUserPhotoModel;
                if (getUserPhotoModelObject != null) {
                    docProfilePic = getUserPhotoModelObject.getMobilePhoto().toString();
                    if(docProfilePic.equals("Image Not Available")){
                        edit.remove(Constant.USER_PROFILE_PIC);
                        edit.commit();
                    }else{
                        docProfilePic = BuildConfig.BASE_URL + getUserPhotoModelObject.getMobilePhoto();
                        edit.putString(Constant.USER_PROFILE_PIC, docProfilePic);
                        Picasso.with(CalendarActivity.this).load(docProfilePic).into(ivDoctorProfile);
                        edit.commit();
                    }

                }
                helper.dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                helper.dismissLoadingDialog();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        //getSupportActionBar().hide();
        helper = new Helper();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        initView();
        sharedPreferences=getSharedPreferences("myPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getBuildVersion();
    }
    private void getBuildVersion() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            SharedPreferences.Editor mEdit = mSharedPreference.edit();
            mEdit.putString(Constant.BUILD_VERSION_NAME, version);
            mEdit.commit();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        if(StringUtil.isBlank(currDate) && userRoleId != 0) {
            getScheduleData(userRoleId, currDate);
        }

    }

    private void initView() {

        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        String subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        sub_tanent_id = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        userRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);


        Intent intent = getIntent();

           AssistantUserrollid = intent.getStringExtra("AssistantUserrollid");

        if (AssistantUserrollid != null && !AssistantUserrollid.isEmpty()) {
            try {
                userRoleId = Integer.parseInt(AssistantUserrollid);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Handle the exception, maybe log an error or show a message to the user
            }
        }

        docName=mSharedPreference.getString(Constant.LOGIN_NAME_KEY, "Undefine");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;


        /*String docProfilePic = "http://test.tn.mcura.com/"+getUserPhotoModelObject.getMobilePhoto();
        editor.putString(Constant.USER_PROFILE_PIC,docProfilePic);
        editor.commit();*/

        Log.e(CALENDAR_TAG, "height===> " + mScreenHeight + " Width===> " + mScreenWidth);


        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setSubtitle("");
        }
        //ibSearchPatient = (ImageButton) findViewById(R.id.ib_search_patient);
        mAppointmentView = (LinearLayout) findViewById(R.id.activity_calendar_rl_appointment);
        //calendar_account = (ImageView) findViewById(R.id.calendar_account);
        //calendar_account.setOnClickListener(this);
        ivPreviousWeek = (ImageView) findViewById(R.id.activity_calendar_iv_pre);
        ivNextWeek = (ImageView) findViewById(R.id.activity_calendar_iv_next);
        ivPreviousWeek.setOnClickListener(this);
        ivNextWeek.setOnClickListener(this);
        ivDoctorProfile = (ImageView) findViewById(R.id.activity_calendar_iv_doctor);
        doctorName= (TextView) findViewById(R.id.docname);
        //set hospital logo
        ImageView hospital_logo = (ImageView) findViewById(R.id.hospital_logo);
        Picasso.with(CalendarActivity.this).load(subtanentImagePath).into(hospital_logo);

        getUserPhotoFromAPi();

           AssistantUserName = intent.getStringExtra("AssistantUserName");

        if (AssistantUserName != null && !AssistantUserName.isEmpty()) {
            doctorName.setText(AssistantUserName);
        }else {
            doctorName.setText(docName);
        }



        tvSunday = (TextView) findViewById(R.id.activity_calendar_tv_sun);
        tvMonday = (TextView) findViewById(R.id.activity_calendar_tv_mon);
        tvTuesday = (TextView) findViewById(R.id.activity_calendar_tv_tue);
        tvWednesday = (TextView) findViewById(R.id.activity_calendar_tv_wed);
        tvThursday = (TextView) findViewById(R.id.activity_calendar_tv_thu);
        tvFriday = (TextView) findViewById(R.id.activity_calendar_tv_fri);
        tvSaturday = (TextView) findViewById(R.id.activity_calendar_tv_sat);
        doctor_info = (ImageView) findViewById(R.id.doctor_info);
        doctor_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalendarActivity.this,DoctorInfoActivity.class));
            }
        });
        checkin = (ImageButton) findViewById(R.id.chk_in);
        logout = (ImageView) mToolbar.findViewById(R.id.logout);
        doctor = (ImageButton) findViewById(R.id.doctor);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalendarActivity.this,DoctorScheduleActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mSharedPreference.edit();
                editor.remove(Constant.USER_ROLE_ID_KEY);
                editor.remove(Constant.SUB_TANENT_IMAGE_PATH);
                editor.apply();
                Intent intentLogout = new Intent(CalendarActivity.this, LoginActivity.class);
                startActivity(intentLogout);
            }
        });
        visit = (ImageButton) findViewById(R.id.visiting);
        search = (EditText) findViewById(R.id.et_search_key);
        queue=(TextView)findViewById(R.id.queue_mgmt);
        billing=(TextView)findViewById(R.id.billing);
        tv_fillcard=(TextView)findViewById(R.id.tv_fillcard);
        load_nfc = (ImageButton) findViewById(R.id.load_nfc);
        /*doctor_name = (Spinner) findViewById(R.id.doctor_name);
        doctor_schedule = (Spinner) findViewById(R.id.doctor_sechedule);
        doctor_name.setOnItemSelectedListener(this);
        doctor_schedule.setOnItemSelectedListener(this);*/
        load_nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalendarActivity.this, SearchPatientActivity.class).putExtra("userroleid",userRoleId));
            }
        });
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CalendarActivity.this, CheckInActivity.class);
                startActivity(in);
            }
        });
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CalendarActivity.this, VisitingActivity.class);
                startActivity(in);
            }
        });
        queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CalendarActivity.this, QueueStatusActivity.class);
                in.putExtra("Assistat_Roll_id",Assistat_Roll_id);
                in.putExtra("AssistantUserName",AssistantUserName);
                in.putExtra("AssistantUserrollid",AssistantUserrollid);

                startActivity(in);
            }
        });
        tv_fillcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CalendarActivity.this, FillCashCardActivity.class);
                startActivity(in);
            }
        });




        Assistat_Roll_id = intent.getStringExtra("Assistat_Roll_id");
        if ("17".equals(Assistat_Roll_id)){

            billing.setVisibility(View.GONE);
            tv_fillcard.setVisibility(View.GONE);

        }else {
            billing.setVisibility(View.VISIBLE);
            tv_fillcard.setVisibility(View.VISIBLE);

        }
        billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CalendarActivity.this, CentralizedBillingActivity.class);
                startActivity(in);
            }
        });

        //tv_monthyear = (TextView) findViewById(R.id.tv_monthyear);

        //logout= (ImageView) findViewById(R.id.calendar_logout);
        //chat= (ImageView) findViewById(R.id.chat);

        //mAppointmentView.setOnClickListener(this);
        //logout.setOnClickListener(this);
        //ibSearchPatient.setOnClickListener(this);
        /*Picasso.with(this)
                .load("http://i9.dainikbhaskar.com/thumbnail/680x588/web2images/www.fashion101.in/2016/01/18/byomkesh-bakshy_145310027.jpg")
                .transform(new CircleTransform()).into(ivDoctorProfile);*/


        nextPreWeekday = getWeekDay();


        setCalendarDays();


        Calendar mCalToday = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        currDate = df.format(mCalToday.getTime());
        System.out.println("==currDate==" + currDate);
        getScheduleData(userRoleId, currDate);

        tvSunday.setOnClickListener(this);
        tvMonday.setOnClickListener(this);
        tvTuesday.setOnClickListener(this);
        tvWednesday.setOnClickListener(this);
        tvThursday.setOnClickListener(this);
        tvFriday.setOnClickListener(this);
        tvSaturday.setOnClickListener(this);
        //chat.setOnClickListener(this);
    }

    /*@Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent i = new Intent(CalendarActivity.this, AccountSettingActivity.class);
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
                Intent intentLogout = new Intent(CalendarActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                break;

        }
        return true;
    }*/
    /***
     * Method to get all days of current week.
     *
     * @return
     */
    public String[] getWeekDay() {
        weekDaysCount = 0;
        Calendar now = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1;
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(now.getTime());
            System.out.println("==days==" + days[i]);
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }

    /***
     * Method to get all days of next week.
     *
     * @return
     */
    public String[] getWeekDayNext() {

        weekDaysCount++;
        Calendar now1 = Calendar.getInstance();
        Calendar now = (Calendar) now1.clone();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1;
        now.add(Calendar.WEEK_OF_YEAR, weekDaysCount);
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        for(int i=0;i<days.length;i++){
            System.out.println("days==>" + days[i]);
        }
        return days;
    }

    /***
     * Method to get all days of previous week.
     *
     * @return
     */
    public String[] getWeekDayPrev() {

        weekDaysCount--;
        Calendar now1 = Calendar.getInstance();
        Calendar now = (Calendar) now1.clone();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1;
        now.add(Calendar.WEEK_OF_YEAR, weekDaysCount);
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        for(int i=0;i<days.length;i++){
            System.out.println("days==>" + days[i]);
        }
        return days;
    }

    @Override
    public void onClick(View v) {
        Calendar c;


        switch (v.getId()) {
            /*case R.id.ib_search_patient:
                startActivity(new Intent(CalendarActivity.this, SearchPatientActivity.class).putExtra("userroleid",userRoleId));
            break;*/
            case R.id.activity_calendar_iv_pre:
                if (mCurrentWeek == 1) {
                    mCurrentWeek = -1;
                    nextPreWeekday = getWeekDayPrev();

                    setCalendarDays();
                } else if (mCurrentWeek == -2) {
                    mCurrentWeek = 1;
                    nextPreWeekday = getWeekDay();

                    setCalendarDays();
                }
                break;

            case R.id.activity_calendar_iv_next:
                if (mCurrentWeek == 1) {
                    mCurrentWeek = -2;
                    nextPreWeekday = getWeekDayNext();
                    setCalendarDays();
                } else if (mCurrentWeek == -1) {
                    mCurrentWeek = 1;
                    nextPreWeekday = getWeekDay();
                    setCalendarDays();
                }
                break;
            case R.id.activity_calendar_tv_sun:
                tvSunday.setBackgroundResource(R.drawable.calender_shape);
                tvMonday.setBackgroundResource(0);
                tvTuesday.setBackgroundResource(0);
                tvWednesday.setBackgroundResource(0);
                tvThursday.setBackgroundResource(0);
                tvFriday.setBackgroundResource(0);
                tvSaturday.setBackgroundResource(0);
                mAppointmentView.removeAllViews();
                //Toast.makeText(CalendarActivity.this,"sun = "+days[0],Toast.LENGTH_LONG).show();
                getCurrentMonthYear(days[0]);

                //day = tvSunday.getText().toString();
                //System.out.print("==day==" + day);
                //System.out.print("==day==" + day.substring(0, 2));
                currDate=month + "/" + day + "/" + year;
                getScheduleData(userRoleId,currDate );
                break;
            case R.id.activity_calendar_tv_mon:
                tvSunday.setBackgroundResource(0);
                tvMonday.setBackgroundResource(R.drawable.calender_shape);
                tvTuesday.setBackgroundResource(0);
                tvWednesday.setBackgroundResource(0);
                tvThursday.setBackgroundResource(0);
                tvFriday.setBackgroundResource(0);
                tvSaturday.setBackgroundResource(0);
                mAppointmentView.removeAllViews();
                //Toast.makeText(CalendarActivity.this,"mon = "+days[1],Toast.LENGTH_LONG).show();
                getCurrentMonthYear(days[1]);

                //day = tvMonday.getText().toString();
                //System.out.print("==day==" + day);
                //System.out.print("==day==" + day.substring(0, 2));
                currDate=month + "/" + day + "/" + year;
                getScheduleData(userRoleId, currDate);
                break;
            case R.id.activity_calendar_tv_tue:
                tvSunday.setBackgroundResource(0);
                tvMonday.setBackgroundResource(0);
                tvTuesday.setBackgroundResource(R.drawable.calender_shape);
                tvWednesday.setBackgroundResource(0);
                tvThursday.setBackgroundResource(0);
                tvFriday.setBackgroundResource(0);
                tvSaturday.setBackgroundResource(0);
                mAppointmentView.removeAllViews();
                //Toast.makeText(CalendarActivity.this,"tue = "+days[2],Toast.LENGTH_LONG).show();
                getCurrentMonthYear(days[2]);

                //day = tvTuesday.getText().toString();
                //System.out.print("==day==" + day);
                //System.out.print("==day==" + day.substring(0, 2));
                currDate=month + "/" + day + "/" + year;
                getScheduleData(userRoleId, currDate);
                break;
            case R.id.activity_calendar_tv_wed:
                tvSunday.setBackgroundResource(0);
                tvMonday.setBackgroundResource(0);
                tvTuesday.setBackgroundResource(0);
                tvWednesday.setBackgroundResource(R.drawable.calender_shape);
                tvThursday.setBackgroundResource(0);
                tvFriday.setBackgroundResource(0);
                tvSaturday.setBackgroundResource(0);
                mAppointmentView.removeAllViews();
                //Toast.makeText(CalendarActivity.this,"wed = "+days[3],Toast.LENGTH_LONG).show();
                getCurrentMonthYear(days[3]);

                //day = tvWednesday.getText().toString();
                //System.out.print("==day==" + day);
                //System.out.print("==day==" + day.substring(0, 2));
                currDate=month + "/" + day + "/" + year;
                getScheduleData(userRoleId, currDate);
                break;
            case R.id.activity_calendar_tv_thu:
                tvSunday.setBackgroundResource(0);
                tvMonday.setBackgroundResource(0);
                tvTuesday.setBackgroundResource(0);
                tvWednesday.setBackgroundResource(0);
                tvThursday.setBackgroundResource(R.drawable.calender_shape);
                tvFriday.setBackgroundResource(0);
                tvSaturday.setBackgroundResource(0);
                mAppointmentView.removeAllViews();
                //Toast.makeText(CalendarActivity.this,"thu = "+days[4],Toast.LENGTH_LONG).show();
                getCurrentMonthYear(days[4]);

                //day = tvThursday.getText().toString();
                //System.out.print("==day==" + day);
                //System.out.print("==day==" + day.substring(0, 2));
                currDate=month + "/" + day + "/" + year;
                getScheduleData(userRoleId, currDate);
                break;
            case R.id.activity_calendar_tv_fri:
                tvSunday.setBackgroundResource(0);
                tvMonday.setBackgroundResource(0);
                tvTuesday.setBackgroundResource(0);
                tvWednesday.setBackgroundResource(0);
                tvThursday.setBackgroundResource(0);
                tvFriday.setBackgroundResource(R.drawable.calender_shape);
                tvSaturday.setBackgroundResource(0);
                mAppointmentView.removeAllViews();
                //Toast.makeText(CalendarActivity.this,"fri = "+days[5],Toast.LENGTH_LONG).show();
                getCurrentMonthYear(days[5]);

                //day = tvFriday.getText().toString();
                //System.out.print("==day==" + day);
                //System.out.print("==day==" + day.substring(0, 2));
                currDate=month + "/" + day + "/" + year;
                //Toast.makeText(CalendarActivity.this,"currDate = "+currDate,Toast.LENGTH_LONG).show();
                getScheduleData(userRoleId,currDate);
                break;
            case R.id.activity_calendar_tv_sat:
                tvSunday.setBackgroundResource(0);
                tvMonday.setBackgroundResource(0);
                tvTuesday.setBackgroundResource(0);
                tvWednesday.setBackgroundResource(0);
                tvThursday.setBackgroundResource(0);
                tvFriday.setBackgroundResource(0);
                tvSaturday.setBackgroundResource(R.drawable.calender_shape);
                mAppointmentView.removeAllViews();
                //Toast.makeText(CalendarActivity.this,"sat = "+days[6],Toast.LENGTH_LONG).show();
                getCurrentMonthYear(days[6]);

                //day = tvSaturday.getText().toString();
                //System.out.print("==day==" + day);
                //System.out.print("==day==" + day.substring(0, 2));
                currDate=month + "/" + day + "/" + year;
                //Toast.makeText(CalendarActivity.this,"currDate = "+currDate,Toast.LENGTH_LONG).show();
                getScheduleData(userRoleId, currDate);
                break;
            case R.id.activity_calendar_rl_appointment:
                Log.d("mScheduleArray", scheduleModelResult+"");
                Bundle bundle = new Bundle();
                Intent intent=new Intent(CalendarActivity.this, CalendraScheduleBookedDetailActivity.class);
                bundle.putSerializable("mScheduleArray", scheduleModelResult);
                intent.putExtra("currDate", currDate);
                intent.putExtra("hospitalNameStr", hospitalNameStr);
                intent.putExtras(bundle);
                //intent.putExtra("fromTime", fromTime);
                //intent.putExtra("toTime",toTime);
                //intent.putExtra("scheduleName",scheduleName);
                startActivity(intent);
                break;
            /*case R.id.calendar_logout:
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
                Intent intentLogout = new Intent(CalendarActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                break;*/
            /*case R.id.calendar_account:
                startActivity(new Intent(CalendarActivity.this, BillDetailActivity.class));
                break;*/
        }
    }

    /**
     * Method for set calendar days.
     */
    private void setCalendarDays() {
        tvSunday.setText(convertWeekDays(nextPreWeekday[0]) + "\nSun");
        tvMonday.setText(convertWeekDays(nextPreWeekday[1]) + "\nMon");
        tvTuesday.setText(convertWeekDays(nextPreWeekday[2]) + "\nTue");
        tvWednesday.setText(convertWeekDays(nextPreWeekday[3]) + "\nWed");
        tvThursday.setText(convertWeekDays(nextPreWeekday[4]) + "\nThu");
        tvFriday.setText(convertWeekDays(nextPreWeekday[5]) + "\nFri");
        tvSaturday.setText(convertWeekDays(nextPreWeekday[6]) + "\nSat");
    }

    /**
     * Method for getting schedule of date.
     *
     * @param userRoleId
     * @param currDate
     */
    private void getScheduleData(int userRoleId, String currDate) {
        String d[] = currDate.split("/");
        //tv_monthyear.setText(monthArray[Integer.parseInt(d[0])-1]+" "+d[2]);
        reqDate = currDate;
        edit = mSharedPreference.edit();
        edit.putString("currDate",currDate);
        edit.commit();
        System.out.print("==jai==" + reqDate);
        //Toast.makeText(CalendarActivity.this,"reqDate = "+reqDate,Toast.LENGTH_LONG).show();
        //mScheduleArray = new ScheduleModel[]{};
        scheduleModelResult = new ScheduleModelResult();
       helper.showLoadingDialog(CalendarActivity.this);
        mCuraApplication.getInstance().mCuraEndPoint.getSchedule_Day(userRoleId, reqDate, new Callback<ScheduleModel[]>() {
            @Override
            public void success(ScheduleModel[] scheduleModels, Response response) {
                scheduleModelList = new ArrayList<>();
                mScheduleArray = scheduleModels;
                for(int i=0;i<mScheduleArray.length;i++){
                    scheduleModelList.add(mScheduleArray[i]);
                }
                //scheduleModelList.add(mScheduleArray);
                //scheduleModelResult.setList(scheduleModelList);
                setScheduleLayout(reqDate);
                helper.dismissLoadingDialog();

            }

            @Override
            public void failure(RetrofitError error) {
                //Toast.makeText(CalendarActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                helper.dismissLoadingDialog();
            }
        });
    }
    private void setScheduleLayout(String reqDate) {
        mScheduleHeight = (mScreenWidth / 320) * 110;
        mMidViewWidth = mScreenWidth - (mScreenWidth / 320) * 10;
        mAppointmentViewHeight = (mScheduleHeight / 320) * 320;


        for (int j = 0; j < mScheduleArray.length; j++) {


            //System.out.println("==mScheduleArray.length=="+mScheduleArray.length);
            String jsonValue = mScheduleArray[j].getDate();
            Date createdOn = Helper.JsonDateToDate(jsonValue);
//            String timestamp = jsonValue.split("\\(")[1].split("\\+")[0];
//            Date createdOn = new Date();
//            try {
//                createdOn  = new Date(Long.parseLong(timestamp));
//            }catch (NumberFormatException  numberFormatException){
//            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDate = sdf.format(createdOn);
            System.out.print("formattedDate-->" + formattedDate);
            //Toast.makeText(CalendarActivity.this,"formattedDate = "+formattedDate,Toast.LENGTH_LONG).show();
            Date requestDate = null, responseDate = null;
            try {
                requestDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                        .parse(reqDate);
                responseDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                        .parse(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (requestDate.compareTo(responseDate) == 0) {
                setSchedule(j);
            }
        }
    }
private void setSchedule(final int j){
    {
        //System.out.println("==comparison1==" + comparison);
        if (checkAppointmentAvail(j)) {
            //hospitalNameOne=new HospitalName();
            subTanentId=mScheduleArray[j].getSubTenantId();
            editor.putInt(Constant.SUB_TANENT_ID_KEY, subTanentId);
            //int comparison = requestDate.compareTo(responseDate);



            final LinearLayout row_view = new LinearLayout(this);

            LinearLayout.LayoutParams l_params = new LinearLayout.LayoutParams(mMidViewWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            //l_params.setMargins(0, j * mScheduleHeight + (mScreenWidth / 320) * 5, 0, 0);
            l_params.setMargins(0, 20, 0, 0);

            l_params.gravity = Gravity.CENTER_HORIZONTAL;
            row_view.setLayoutParams(l_params);
            row_view.setOrientation(LinearLayout.VERTICAL);
            row_view.setBackgroundResource(R.color.white);
            row_view.setPadding(0, 0, 0, 0);
            mAppointmentView.addView(row_view);

            RelativeLayout ll_date_time_layout = new RelativeLayout(this);
            RelativeLayout.LayoutParams l_date_time_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            ll_date_time_layout.setLayoutParams(l_date_time_params);
            ll_date_time_layout.setBackgroundResource(R.color.main_layout_background);

            TextView tvTimeSlot = new TextView(this);
            ViewGroup.LayoutParams tvTime_params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            tvTimeSlot.setLayoutParams(tvTime_params);
            tvTimeSlot.setBackgroundResource(R.color.main_layout_background);
            tvTimeSlot.setTypeface(null, Typeface.BOLD);
            fromTime=mScheduleArray[0].getFromTime();
            String fromTime1=mScheduleArray[j].getFromTime();
            editor.putString("fromTime",fromTime);
            toTime=mScheduleArray[j].getToTime();
            editor.putString("toTime", toTime);
            tvTimeSlot.setText(fromTime1 + " To " + toTime);
            tvTimeSlot.setTextSize(14);
            tvTimeSlot.setPadding(10, 5, 10, 5);
            ll_date_time_layout.addView(tvTimeSlot);

            TextView tvDateSlot = new TextView(this);
            RelativeLayout.LayoutParams tvDate_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            tvDate_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tvDateSlot.setLayoutParams(tvDate_params);
            tvDateSlot.setBackgroundResource(R.color.main_layout_background);
            tvDateSlot.setTypeface(null, Typeface.BOLD);
            tvDateSlot.setText(currDate);
            tvDateSlot.setTextSize(14);
            tvDateSlot.setPadding(10, 5, 10, 5);
            ll_date_time_layout.addView(tvDateSlot, tvDate_params);
            row_view.addView(ll_date_time_layout);

            scheduleName=mScheduleArray[j].getScheduleName();
            editor.putString("scheduleName", scheduleName);
            TextView tvMedSlot = new TextView(this);
            tvMedSlot.setText(scheduleName);
            tvMedSlot.setTextSize(14);
            tvMedSlot.setPadding(10, 5, 10, 0);
            tvMedSlot.setTypeface(null, Typeface.BOLD);
            tvMedSlot.setGravity(Gravity.CENTER);
            row_view.addView(tvMedSlot);

            getHospitalName(subTanentId, userRoleId);
            tvHospitalName = new TextView(this);
            tvHospitalName.setText(hospitalNameStr);
            tvHospitalName.setEllipsize(TextUtils.TruncateAt.END);
            tvHospitalName.setGravity(Gravity.CENTER);
            tvHospitalName.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            tvHospitalName.setTextSize(18);
            tvHospitalName.setTypeface(null, Typeface.BOLD);
            tvHospitalName.setPadding(10, 5, 10, 5);
            row_view.addView(tvHospitalName);

            final LinearLayout row = new LinearLayout(this);
            row.setId(j + 1);
            final int index = j;
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("mScheduleArray", scheduleModelList.get(j)+"");
                    Bundle bundle = new Bundle();
                    Intent intent=new Intent(CalendarActivity.this, CalendraScheduleBookedDetailActivity.class);
                    bundle.putSerializable("mScheduleArray", scheduleModelList.get(j));
                    intent.putExtra("currDate", currDate);
                    intent.putExtra("hospitalNameStr", hospitalNameStr);
                    intent.putExtras(bundle);
                    //intent.putExtra("fromTime", fromTime);
                    //intent.putExtra("toTime",toTime);
                    //intent.putExtra("scheduleName",scheduleName);
                    startActivity(intent);
                }
            });
            LinearLayout.LayoutParams row_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            row_params.leftMargin = 10;
            row_params.rightMargin = 10;
            row_params.gravity = Gravity.CENTER_HORIZONTAL;
            row.setLayoutParams(row_params);
            row_view.addView(row);

            editor.commit();
            final int finalJ = j;
            row.post(new Runnable() {
                public void run() {
                    mCustomViewWidth = row.getMeasuredWidth();

                    int total_section = mScheduleArray[finalJ].getAppointments().size();
                    int section_width = (mCustomViewWidth / total_section);
                    // int section_width = (int) (mCustomViewWidth/ total_section+ 0.5d );

                    for (int i = 0; i < total_section; i++) {

                        LinearLayout mLinearView = new LinearLayout(CalendarActivity.this);
                        //LinearLayout.LayoutParams v_params = new LinearLayout.LayoutParams(section_width,
                        //       mAppointmentViewHeight / 4);
                        LinearLayout.LayoutParams v_params = new LinearLayout.LayoutParams(section_width,
                                50);
                        v_params.gravity = Gravity.CENTER_HORIZONTAL;
                        mLinearView.setLayoutParams(v_params);
                        if (i == 0) {
                            if (mScheduleArray[finalJ].getAppointments().get(i).getAvlStatusId() == 2) {
                                mLinearView.setBackgroundResource(R.drawable.four_corner_green);
                            } else if (mScheduleArray[finalJ].getAppointments().get(i).getAvlStatusId() == 3) {
                                mLinearView.setBackgroundResource(R.drawable.four_corner_grey);
                            } else {
                                mLinearView.setBackgroundResource(R.drawable.four_corner_white);
                            }
                        } else {
                            if (mScheduleArray[finalJ].getAppointments().get(i).getAvlStatusId() == 2) {
                                mLinearView.setBackgroundResource(R.drawable.three_corner_green);
                            } else if (mScheduleArray[finalJ].getAppointments().get(i).getAvlStatusId() == 3) {
                                mLinearView.setBackgroundResource(R.drawable.three_corner_grey);
                            } else {
                                mLinearView.setBackgroundResource(R.drawable.three_corner_white);
                            }
                        }
                        row.addView(mLinearView);
                    }

                }
            });
        }
    }
}

    private boolean checkAppointmentAvail(int finalJ) {
        int appointAvail = 0;
        int total_section = mScheduleArray[finalJ].getAppointments().size();

        for (int j = 0; j < total_section; j++) {
            //if (mScheduleArray[finalJ].getAppointments().get(j).getAppbookings() != null) {
                appointAvail++;
            //}

            if (j == (total_section - 1)) {
                if (appointAvail > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private void getCurrentMonthYear(String date) {
        //Toast.makeText(CalendarActivity.this,"date = "+date,Toast.LENGTH_LONG).show();
        String dt[]=date.split("-");
        year = dt[0];
        month = dt[1];
        day = dt[2];
    }
    public void getHospitalName(int subTanentId,int userRoleId){
       helper.showLoadingDialog(CalendarActivity.this);
        mCuraApplication.getInstance().mCuraEndPoint.getSubTenantOne(subTanentId, userRoleId, new Callback<HospitalName>() {
            @Override
            public void success(HospitalName hospitalName, Response response) {
                hospitalNameStr = hospitalName.getSubTenantName();
                tvHospitalName.setText(hospitalNameStr);

                helper.dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                helper.dismissLoadingDialog();
            }
        });
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.doctor_name:
                userRoleId = doctorSpinnerAdapter.getItem(position).getUserRoleId();
                editor.putString(Constant.LOGIN_NAME_KEY, doctorSpinnerAdapter.getItem(position).getUserName());
                //Toast.makeText(LoginActivity.this,userRoleId+"===userroleid",Toast.LENGTH_LONG).show();
                //editor.putInt(Constant.ADDRESS_ID_KEY, doctorSpinnerAdapter.getItem(position).getAddressId());
                // editor.putInt(Constant.CONTACT_ID_KEY, doctorSpinnerAdapter.getItem(position).getContactId());
                //editor.putString(Constant.DOB_KEY, doctorSpinnerAdapter.getItem(position).getDob());
                //editor.putInt(Constant.GENDER_ID_KEY, doctorSpinnerAdapter.getItem(position).getGenderId());
                //editor.putString(Constant.UNAME_KEY, doctorSpinnerAdapter.getItem(position).getUname());
                editor.putInt(Constant.USER_ROLE_ID, doctorSpinnerAdapter.getItem(position).getUserRoleId());
                editor.apply();
                getScheduleData();
                break;
            case R.id.doctor_sechedule:
                scheduleId = scheduleSpinnerAdapter.getItem(position).getScheduleId();
                from_time = scheduleSpinnerAdapter.getItem(position).getFromTime();
                to_time = scheduleSpinnerAdapter.getItem(position).getToTime();

                //Toast.makeText(LoginActivity.this,scheduleId+"===scheduleId",Toast.LENGTH_LONG).show();
                editor.putInt(Constant.SCHEDULE_ID, scheduleId);
                editor.putString("from_time", from_time);
                editor.putString("to_time", to_time);
                editor.apply();
                if(scheduleId!=0){
                    getScheduleData(userRoleId,currDate);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void getDoctorDetail(){
       helper.showLoadingDialog(CalendarActivity.this);
        mCuraApplication.getInstance().mCuraEndPoint.list_DoctorsBySubTenantId(sub_tanent_id, new Callback<DoctorListModel[]>() {
            @Override
            public void success(DoctorListModel[] doctors, Response response) {
                doctorArray = doctors;
                for (int i = 0; i < doctors.length; i++) {
                    Log.d("doctor name", doctors[i].getUserName());
                }
                doctorSpinnerAdapter = new CalenderDoctorAdapter(CalendarActivity.this,
                        android.R.layout.simple_spinner_item,
                        doctorArray);
                doctor_name.setAdapter(doctorSpinnerAdapter);
                helper.dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                helper.dismissLoadingDialog();
            }
        });
    }
    private void getScheduleData() {
       helper.showLoadingDialog(CalendarActivity.this);
        mCuraApplication.getInstance().mCuraEndPoint.getSchedule_Day(userRoleId, currDate, new Callback<ScheduleModel[]>() {
            @Override
            public void success(ScheduleModel[] scheduleModels, Response response) {

                mScheduleArray = scheduleModels;
                for (int i = 0; i < mScheduleArray.length; i++) {
                    Log.d("time", mScheduleArray[i].getFromTime() + "..." + mScheduleArray[i].getToTime());
                }
                scheduleSpinnerAdapter = new CalenderScheduleAdapter(CalendarActivity.this,
                        android.R.layout.simple_spinner_item,
                        mScheduleArray);
                doctor_schedule.setAdapter(scheduleSpinnerAdapter);
                helper.dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CalendarActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                helper.dismissLoadingDialog();
            }
        });
    }*/
}
