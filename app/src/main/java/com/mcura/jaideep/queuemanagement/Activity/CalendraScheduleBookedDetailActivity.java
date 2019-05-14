package com.mcura.jaideep.queuemanagement.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mcura.jaideep.queuemanagement.Adapter.RecyclerViewAdapter;
import com.mcura.jaideep.queuemanagement.Model.ScheduleModel;
import com.mcura.jaideep.queuemanagement.Model.ScheduleModelResult;
import com.mcura.jaideep.queuemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class CalendraScheduleBookedDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton booked,free;
    private TextView opdTime,dateDay,tvhospitalName;
    ImageView close;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout heading;
    ScheduleModel[] mScheduleArray;
    ScheduleModel scheduleModel;
    ScheduleModelResult scheduleModelResult;
    String currDate,fromTime,toTime,scheduleName,hospitalName;
    List<ScheduleModel[]> scheduleModelList = new ArrayList<>();
    private View view3;
    private ImageView ivPrintBookedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //DisplayMetrics metrics = getResources().getDisplayMetrics();
        //int screenWidth = (int) (metrics.widthPixels * 0.80);
        //int screenHeight = (int) (metrics.heightPixels * 0.65);
        setContentView(R.layout.activity_calendra_schedule_booked_detail);
        //getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        mScheduleArray = new ScheduleModel[1];
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", CalendraScheduleBookedDetailActivity.MODE_PRIVATE);
        scheduleModel = (ScheduleModel) getIntent().getSerializableExtra("mScheduleArray");
        mScheduleArray[0] = scheduleModel;
        /*scheduleModelList = scheduleModelResult.getList();
        for(int i=0;i<scheduleModelList.size();i++){
            mScheduleArray=scheduleModelList.get(i);
        }*/

        //Log.d("scheduleModelResult",scheduleModelResult+"");

        //scheduleModelList = scheduleModelResult.getList();
        //mScheduleArray=scheduleModelList.get(0);
        //Log.d("scheduleModelResult",mScheduleArray[0].getScheduleName()+"");
        hospitalName = getIntent().getStringExtra("hospitalNameStr");
        currDate=getIntent().getStringExtra("currDate");
        fromTime=sharedPreferences.getString("fromTime", "6:00");
        toTime=sharedPreferences.getString("toTime", "9:00");
        scheduleName=sharedPreferences.getString("scheduleName", "default OPD");
        //toTime=getIntent().getStringExtra("toTime");
        //scheduleName=getIntent().getStringExtra("scheduleName");
        view3 =(View) findViewById(R.id.view_bottom);
        ivPrintBookedPatient =(ImageView)  findViewById(R.id.ivPrintBookedPatient);
        ivPrintBookedPatient.setVisibility(View.GONE);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        close= (ImageView) findViewById(R.id.close);
        booked= (ImageButton) findViewById(R.id.booked);
        free= (ImageButton) findViewById(R.id.free);
        tvhospitalName= (TextView) findViewById(R.id.hospital_name);
        tvhospitalName.setText(hospitalName);
        opdTime= (TextView) findViewById(R.id.opd_time);
        dateDay= (TextView) findViewById(R.id.date_day);
        heading= (LinearLayout) findViewById(R.id.heading);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(CalendraScheduleBookedDetailActivity.this,"free",mScheduleArray,currDate,ivPrintBookedPatient);
        mRecyclerView.setAdapter(mAdapter);
        heading.setVisibility(View.VISIBLE);
        view3.setVisibility(View.VISIBLE);
        opdTime.setText(scheduleModel.getScheduleName() + "\n" + scheduleModel.getFromTime() + " To " + scheduleModel.getToTime());
        dateDay.setText(currDate);
        booked.setOnClickListener(this);
        free.setOnClickListener(this);
        close.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.booked:
                booked.setBackgroundDrawable(getResources().getDrawable(R.drawable.booked_select));
                free.setBackgroundDrawable(getResources().getDrawable(R.drawable.free_unselect));
                mAdapter = new RecyclerViewAdapter(CalendraScheduleBookedDetailActivity.this,"booked",mScheduleArray,currDate,ivPrintBookedPatient);
                mRecyclerView.setAdapter(mAdapter);
                heading.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                ivPrintBookedPatient.setVisibility(View.VISIBLE);
                break;
            case R.id.free:
                free.setBackgroundDrawable(getResources().getDrawable(R.drawable.free_select));
                booked.setBackgroundDrawable(getResources().getDrawable(R.drawable.booked_unselect));
                mAdapter = new RecyclerViewAdapter(CalendraScheduleBookedDetailActivity.this,"free",mScheduleArray,currDate,ivPrintBookedPatient);
                mRecyclerView.setAdapter(mAdapter);
                heading.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                //opdTime.setText(scheduleName+"\n"+fromTime+" To "+toTime);
                dateDay.setText(currDate);
                ivPrintBookedPatient.setVisibility(View.GONE);
                break;
            case R.id.close:
                CalendraScheduleBookedDetailActivity.this.finish();
                break;
        }
    }

}
