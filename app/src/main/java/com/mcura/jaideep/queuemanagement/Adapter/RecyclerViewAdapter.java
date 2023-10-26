package com.mcura.jaideep.queuemanagement.Adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.mcura.jaideep.queuemanagement.Activity.CalendarActivity;
import com.mcura.jaideep.queuemanagement.Activity.CalendraScheduleBookedDetailActivity;
import com.mcura.jaideep.queuemanagement.Activity.NewAppointmentActivity;
import com.mcura.jaideep.queuemanagement.Activity.SendMail;
import com.mcura.jaideep.queuemanagement.BuildConfig;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.MainActivity;
import com.mcura.jaideep.queuemanagement.Model.Appbookings;
import com.mcura.jaideep.queuemanagement.Model.AppointmentModel;
import com.mcura.jaideep.queuemanagement.Model.MainModel;
import com.mcura.jaideep.queuemanagement.Model.PatdemographicsModel;
import com.mcura.jaideep.queuemanagement.Model.PostActivityTrackerModel.PostActivityTrackerModel;
import com.mcura.jaideep.queuemanagement.Model.ScheduleModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.Utils.CustomVolleyRequestQueue;
import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.mcura.jaideep.queuemanagement.helper.Helper;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final String departmentName;
    private final String docName;
    private final int frontOfficeUserRoleId;
    public MCuraApplication mCuraApplication;

    //private String[] mDataset={"6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm","6:00 pm To 9:00 pm"};
    //private String[] name={"Amit Dubey","Amit Dubey","Amit Dubey","Amit Dubey","Amit Dubey","Amit Dubey","Amit Dubey","Amit Dubey","Amit Dubey","Amit Dubey"};
    //private String[] age={"39/M","39/M","39/M","39/M","39/M","39/M","39/M","39/M","39/M","39/M"};
    //private String[] mTime={"6:00 pm","6:00 pm","6:00 pm","6:00 pm","6:00 pm","6:00 pm","6:00 pm","6:00 pm","6:00 pm","6:00 pm"};
    ScheduleModel[] mScheduleArray;
    String currDate;
    private ImageLoader mImageLoader;
    String type = "";
    Context context;
    int appointmentid;
    int total_section;
    ProgressDialog progressDialog;
    int length;
    int pos;
    String toTime, fromTime, opdTime;
    String patientEmail;
    String callPatient;
    int avlStatusId;
    SharedPreferences mSharedPreference;
    String ageS;
    private int userRoleId,subTanentId;
    List<AppointmentModel> appointments, appointmentsBooked,appointmentsBlocked;
    List<PatdemographicsModel> patDemoGraphicsesList;
    AppointmentModel appointmentModel, appointmentModelBooked,appointmentModelBlocked;
    PatdemographicsModel patDemoGraphics;
    ImageView ivPrintBookedPatient;
    private WebView myWebView;
    private String htmlString;
    String subtanentImagePath;
    private String buildVersionName;
    private int actTransactId;
    private android.support.v7.app.AlertDialog successAlertDialog;
    private android.support.v7.app.AlertDialog errorAlertDialog;
    private String txtReason="";
    private int patMrno=0;
    /*@Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.fix:
                Toast.makeText(context.getApplicationContext(),pos+"",Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context.getApplicationContext(), NewAppointmentActivity.class));
                ((Activity)context).finish();
        }
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time, name, age, mrno;
        public ImageButton fix, block, mail, contact, close;
        public ImageView profileImage;
        public RelativeLayout rlFree;
        public ImageButton unBlock;
        public LinearLayout llFixBlock;
        public TextView emailTo,callPat;
        public ViewHolder(View v, String viewType) {
            super(v);
            if (viewType.equals("free")) {
                rlFree = (RelativeLayout) v.findViewById(R.id.rlFree);
                time = (TextView) v.findViewById(R.id.time);
                fix = (ImageButton) v.findViewById(R.id.fix);
                llFixBlock= (LinearLayout) v.findViewById(R.id.ll_fix_block);

                fix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppointmentModel appModel = appointments.get(getPosition());
                        //Toast.makeText(v.getContext(), "position = " + getPosition()+" text = "+time.getText().toString(), Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor edit = mSharedPreference.edit();
                        edit.putString("timekey",time.getText().toString()).commit();
                        edit.putInt("appId", appModel.getAppId());
                        //Toast.makeText(context, "appId=" + appModel.getAppId(), Toast.LENGTH_LONG).show();
                        edit.putInt("avlstatusid",appModel.getAvlStatusId());
                        edit.commit();
                        context.startActivity(new Intent(context.getApplicationContext(), NewAppointmentActivity.class).putExtra("time",currDate));
                        ((Activity) context).finish();
                    }
                });
                block = (ImageButton) v.findViewById(R.id.block);
                block.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AppointmentModel appModel = appointments.get(getPosition());
                        appointmentid = appModel.getAppId();
                        avlStatusId = appModel.getAvlStatusId();
                        actTransactId = EnumType.ActTransactMasterEnum.Block_APT.getActTransactMasterTypeId();
                        setBlockApi();
                    }
                });
                unBlock= (ImageButton) v.findViewById(R.id.unblock);
                unBlock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppointmentModel appModel = appointments.get(getPosition());
                        appointmentid = appModel.getAppId();
                        avlStatusId = appModel.getAvlStatusId();
                        actTransactId = EnumType.ActTransactMasterEnum.Unblock_APT.getActTransactMasterTypeId();
                        setBlockApi();
                    }
                });
            } else if (viewType.equals("booked")) {
                time = (TextView) v.findViewById(R.id.time);
                name = (TextView) v.findViewById(R.id.name);
                age = (TextView) v.findViewById(R.id.age);
                mail = (ImageButton) v.findViewById(R.id.mail);
                contact = (ImageButton) v.findViewById(R.id.contact);
                close = (ImageButton) v.findViewById(R.id.booked_close);
                profileImage = (ImageView) v.findViewById(R.id.profile_image);
                emailTo= (TextView) v.findViewById(R.id.emailto);
                callPat= (TextView) v.findViewById(R.id.call_patient);
                mrno= (TextView) v.findViewById(R.id.mrno);
                /*v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int mrnoValue=(int)Double.parseDouble(mrno.getText().toString().trim());
                        context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class).putExtra("mrnoValue", mrnoValue).putExtra("subTanentId", subTanentId));
                    }
                });*/
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
// ...Irrelevant code for customizing the buttons and title
                        LayoutInflater inflater = ((CalendraScheduleBookedDetailActivity)context).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.activity_cancel, null);
                        dialogBuilder.setView(dialogView);

                        ImageButton activitycancel_submit = (ImageButton) dialogView.findViewById(R.id.activitycancel_submit);
                        ImageButton activitycancel_cancel = (ImageButton) dialogView.findViewById(R.id.activitycancel_cancel);
                        final AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.show();
                        activitycancel_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppointmentModel bookModel = appointmentsBooked.get(getPosition());
                                patMrno = Integer.parseInt(mrno.getText().toString().trim());
                                appointmentid = bookModel.getAppId();
                                avlStatusId = bookModel.getAvlStatusId();
                                actTransactId = EnumType.ActTransactMasterEnum.CANCEL_APT.getActTransactMasterTypeId();
                                showCancelReasonDialog();
                            }
                        });
                        activitycancel_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });


                    }
                });
                mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context.getApplicationContext(), SendMail.class).putExtra("emailTo", emailTo.getText().toString()));
                    }
                });
                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);

                        callIntent.setData(Uri.parse("tel:"+extractPhoneNumber( "tel:"+callPat.getText().toString())));

                        try{
                            context.startActivity(callIntent);
                        }

                        catch (android.content.ActivityNotFoundException ex){
                            Toast.makeText(context.getApplicationContext(),"your Activity is not founded",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
    }

    public RecyclerViewAdapter(final Context context, String type, ScheduleModel[] mScheduleArray, String currDate, ImageView ivPrintBookedPatient) {
        /*mSharedPreference = context.getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE);*/
        this.ivPrintBookedPatient = ivPrintBookedPatient;
        this.opdTime=opdTime;
        this.currDate = currDate;
        this.mScheduleArray = mScheduleArray;
        this.type = type;
        this.context = context;
        appointments = new ArrayList<AppointmentModel>();
        appointmentsBooked = new ArrayList<AppointmentModel>();
        patDemoGraphicsesList=new ArrayList<PatdemographicsModel>();
        length = mScheduleArray[0].getAppointments().size();
        mSharedPreference = context.getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences=context.getSharedPreferences("myPref", MainActivity.MODE_PRIVATE);
        buildVersionName = mSharedPreference.getString(Constant.BUILD_VERSION_NAME, "");
        subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        subTanentId=sharedPreferences.getInt(Constant.SUB_TANENT_ID_KEY,0);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID_KEY, 0);
        departmentName = mSharedPreference.getString(Constant.DEPT_NAME, "");
        docName = mSharedPreference.getString(Constant.LOGIN_NAME_KEY, "Undefine");
        userRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        for (int j = 0; j < mScheduleArray.length; j++) {
            String jsonValue = mScheduleArray[j].getDate();
            String timestamp = jsonValue.split("\\(")[1].split("\\+")[0];
            Date createdOn = Helper.JsonDateToDate(jsonValue);
//            Date createdOn = new Date();
//            try {
//                createdOn  = new Date(Long.parseLong(timestamp));
//            }catch (NumberFormatException  numberFormatException){
//            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDate = sdf.format(createdOn);

            Date requestDate = null, responseDate = null;

            try {
                requestDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                        .parse(currDate);
                responseDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                        .parse(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (requestDate.compareTo(responseDate) == 0) {
                if (checkAppointmentAvail(j)) {
                    final int finalJ = j;
                    for (int i = 0; i < total_section; i++) {

                        int availStatus = mScheduleArray[finalJ].getAppointments().get(i).getAvlStatusId();
                        if (availStatus == 1) {
                            appointmentModel = new AppointmentModel();
                            appointmentModel.setAppId(mScheduleArray[finalJ].getAppointments().get(i).getAppId());
                            appointmentModel.setAvlStatusId(mScheduleArray[finalJ].getAppointments().get(i).getAvlStatusId());
                            appointmentModel.setFromTime(mScheduleArray[finalJ].getAppointments().get(i).getFromTime());
                            appointmentModel.setToTime(mScheduleArray[finalJ].getAppointments().get(i).getToTime());
                            appointmentModel.setAppbookings(mScheduleArray[finalJ].getAppointments().get(i).getAppbookings());
                            appointments.add(appointmentModel);
                        } else if (availStatus == 2) {
                            appointmentModelBooked = new AppointmentModel();
                            patDemoGraphics = new PatdemographicsModel();
                            Log.d("eeeeeeeee=====",mScheduleArray[finalJ].getAppointments().get(i).getAvlStatusId()+"");
                            appointmentModelBooked.setAppId(mScheduleArray[finalJ].getAppointments().get(i).getAppId());
                            appointmentModelBooked.setAvlStatusId(mScheduleArray[finalJ].getAppointments().get(i).getAvlStatusId());
                            appointmentModelBooked.setFromTime(mScheduleArray[finalJ].getAppointments().get(i).getFromTime());
                            appointmentModelBooked.setToTime(mScheduleArray[finalJ].getAppointments().get(i).getToTime());
                            appointmentModelBooked.setPatdemographics(mScheduleArray[finalJ].getAppointments().get(i).getPatdemographics());
                            appointmentModelBooked.setAppbookings(mScheduleArray[finalJ].getAppointments().get(i).getAppbookings());
                            //patDemoGraphics.setPatname(mScheduleArray[finalJ].getAppointments().get(i).getPatdemographics().getPatname());
                            //patDemoGraphics.setGenderId(mScheduleArray[finalJ].getAppointments().get(i).getPatdemographics().getGenderId());
                            //patDemoGraphicsesList.add(patDemoGraphics);
                            //appointmentModel.setPatdemographics(patDemoGraphics);
                            appointmentsBooked.add(appointmentModelBooked);

                        }
                        else if(availStatus == 3){
                            appointmentModel=new AppointmentModel();
                            appointmentModel.setAppId(mScheduleArray[finalJ].getAppointments().get(i).getAppId());
                            appointmentModel.setAvlStatusId(mScheduleArray[finalJ].getAppointments().get(i).getAvlStatusId());
                            appointmentModel.setFromTime(mScheduleArray[finalJ].getAppointments().get(i).getFromTime());
                            appointmentModel.setToTime(mScheduleArray[finalJ].getAppointments().get(i).getToTime());
                            appointments.add(appointmentModel);
                        }

                    }
                }
            }

        }
        ivPrintBookedPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("count",appointmentsBooked.size()+"");
                if(appointmentsBooked.size()>0){
                    generatePatAppointmentPDF(appointmentsBooked);
                }else{
                    Toast.makeText(context,"No Booked Patient found",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void generatePatAppointmentPDF(List<AppointmentModel> appointmentsBooked) {
        WebView webView = new WebView(context);
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

        String htmlPatAppointmentDocument = "";
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open("bookedPatient.html");
            try {
                htmlPatAppointmentDocument = convertStreamToString(is);
                htmlPatAppointmentDocument = htmlPatAppointmentDocument.replace("HOSPITAL_IMAGE", subtanentImagePath);
                htmlPatAppointmentDocument = htmlPatAppointmentDocument.replace("DOCTOR_NAME", docName);
                htmlPatAppointmentDocument = htmlPatAppointmentDocument.replace("DOCTOR_DEPT", departmentName);
                htmlPatAppointmentDocument = htmlPatAppointmentDocument.replace("DATE", Helper.changeDateFormat(currDate));

                if (appointmentsBooked.size()> 0) {
                    ArrayList<String> arr = new ArrayList<String>();
                    for (int i = 0; i < appointmentsBooked.size(); i++) {
                        arr.add("<tr>");
                        arr.add("<td class=\"text-center\">"+(i+1)+"</td>");
                        arr.add("<td>");
                        arr.add("<div class=\"patientDetails\">");
                        arr.add("<strong>"+appointmentsBooked.get(i).getPatdemographics().getPatname()+"</strong>");

                        arr.add("</div>");
                        arr.add("</td>");
                        String dobEncode = appointmentsBooked.get(i).getPatdemographics().getDob();
                        //System.out.println("patName-->" + dobEncode);
                        //String dob= getDob(dobEncode);
//                        String timestamp = dobEncode.split("\\(")[1].split("\\+")[0];
//                        Date createdOn = new Date();
//                        try {
//                            createdOn  = new Date(Long.parseLong(timestamp));
//                        }catch (NumberFormatException  numberFormatException){
//                        }
                        Date createdOn = Helper.JsonDateToDate(dobEncode);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
                        String formattedDate = sdf.format(createdOn);
                        System.out.println("formattedDate-->" + formattedDate);
                        String[] out = formattedDate.split(",");
                        System.out.println("Year = " + out[2]);
                        System.out.println("Month = " + out[0]);
                        System.out.println("Day = " + out[1]);

                        int year = Integer.parseInt(out[2]);
                        int month = Integer.parseInt(out[0]);
                        int day = Integer.parseInt(out[1]);
                        String ageGender = Helper.getAge(year,month,day);

                        int gender = appointmentsBooked.get(i).getPatdemographics().getGenderId();
                        if (gender==1) {
                            ageGender = ageGender+" | M";
                        } else {
                            ageGender = ageGender+" | F";
                        }
                        arr.add("<td class=\"text-center\">"+ageGender+"</td>");
                        arr.add("<td class=\"text-center\">"+appointmentsBooked.get(i).getPatdemographics().getMobile().trim()+"</td>");
                        arr.add("<td class=\"text-center\">"+Helper.Convert24to12(appointmentsBooked.get(i).getFromTime())+"</td>");
                        arr.add("</tr>");
                    }
                    htmlString = android.text.TextUtils.join("", arr);
                }

                htmlPatAppointmentDocument = htmlPatAppointmentDocument.replace("BOOKED_PATIENT_LIST", htmlString);
                webView.loadDataWithBaseURL("file:///android_asset", htmlPatAppointmentDocument, "text/html", "utf-8", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        myWebView = webView;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) context
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = context.getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }


    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v;
        ViewHolder vh = null;
        if (type.equals("free")) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.free_row_layout, null);
            vh = new ViewHolder(v, "free");
        } else if (type.equals("booked")) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_row_layout, null);
            vh = new ViewHolder(v, "booked");
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        pos = position;

        if (type.equals("free")) {
            AppointmentModel model = appointments.get(position);
            System.out.println("*mCura*"+model.getAvlStatusId());
            holder.time.setText(model.getFromTime() + " To " + model.getToTime());
            String time=currDate+" "+model.getFromTime().toString().trim();
            //Toast.makeText(context.getApplicationContext(),"time"+time.toString(),Toast.LENGTH_LONG).show();
            if(model.getAvlStatusId()==1){
                if(CheckDates(time)){
                    holder.unBlock.setVisibility(View.GONE);
                    holder.llFixBlock.setVisibility(View.GONE);
                }
                else{
                    holder.unBlock.setVisibility(View.GONE);
                    holder.llFixBlock.setVisibility(View.VISIBLE);
                }

            }
            else if(model.getAvlStatusId()==3){

                holder.unBlock.setVisibility(View.VISIBLE);
                holder.llFixBlock.setVisibility(View.GONE);
            }
                /*int appId=model.getAppId();
                int avlStatusId=model.getAvlStatusId();
                String appNatureId=model.getAppbookings().toString();
                String appBooking[]=new String[6];
                if(appNatureId!=null){
                    String split[]=appNatureId.split(",");
                    for(int i=0;i<split.length;i++){
                        String val[]=split[i].split("=");
                        String value=val[i].replace("}","");
                        appBooking[i]=value;
                        System.out.println("appBooking[i]==>"+appBooking[i]);
                    }
                }*/

            //holder.fix.setOnClickListener(this);
        } else if (type.equals("booked")) {
            try{
                AppointmentModel model = appointmentsBooked.get(position);
                Log.d("patname", model.getPatdemographics().getPatname());
                //if (availStatusId==2){
                // patDemoGraphicsStringFormat = null;
                //String appBooking = null;
                holder.time.setText(model.getFromTime() + " To " + model.getToTime());

                PatdemographicsModel patDemoGraphicsString = model.getPatdemographics();
                Appbookings appBooking=model.getAppbookings();

            /*System.out.println("appBooking-->" + appBooking);

            String values[] = new String[6];
            String format[] = patDemoGraphicsString.split(",");

            String valueAppBooking[]=new String[6];
            String formatAppBooking[]=appBooking.split(",");*/

                //String manipulation for patDemoGraphics
            /*for (int i = 0; i < format.length; i++) {
                String val[] = format[i].split("=");
                String value = val[1].replace("}", "");
                values[i] = value;
                System.out.println(values[i]);
            }*/

                //String Manipulation for appbooking
            /*for (int i = 0; i < formatAppBooking.length; i++) {
                String val[] = formatAppBooking[i].split("=");
                String value = val[1].replace("}", "");
                valueAppBooking[i] = value;
                System.out.println(valueAppBooking[i]);
            }*/
                //System.out.println("patName-->" + patDemo);
                String patName = patDemoGraphicsString.getPatname();
                //System.out.println("patName-->" + patName);
                String dobEncode = patDemoGraphicsString.getDob();
                //System.out.println("patName-->" + dobEncode);
                //String dob= getDob(dobEncode);
//                String timestamp = dobEncode.split("\\(")[1].split("\\+")[0];
//                Date createdOn = new Date();
//                try {
//                    createdOn  = new Date(Long.parseLong(timestamp));
//                }catch (NumberFormatException  numberFormatException){
//                }
                Date createdOn = Helper.JsonDateToDate(dobEncode);
                SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
                String formattedDate = sdf.format(createdOn);
                System.out.println("formattedDate-->" + formattedDate);
                String[] out = formattedDate.split(",");
                System.out.println("Year = " + out[2]);
                System.out.println("Month = " + out[0]);
                System.out.println("Day = " + out[1]);

                int year = Integer.parseInt(out[2]);
                int month = Integer.parseInt(out[0]);
                int day = Integer.parseInt(out[1]);

                //String splitmrno[]=formatAppBooking[4].split("=");
                holder.mrno.setText(appBooking.getMrno()+"");
                int mr=(int)Double.parseDouble(appBooking.getMrno()+"");
                String imagePath= BuildConfig.BASE_URL+patDemoGraphicsString.getPatPic();
                patientEmail=patDemoGraphicsString.getEmail().trim();
                callPatient=patDemoGraphicsString.getMobile().trim();
                holder.emailTo.setText(patientEmail);
                holder.callPat.setText("Mobile : "+callPatient);
                Picasso.with(context).load(imagePath).placeholder(R.drawable.booked_patient_img).into(holder.profileImage);
                //Double d=new Double(mr);
                //int m=d.intValue();
                //showLoadingDialog();
                /*mCuraApplication.getInstance().mCuraEndPoint.getPatient(mr, userRoleId, subTanentId, new Callback<MainModel>() {
                    @Override
                    public void success(MainModel mainModel, Response response) {
                        final String imagePath= BuildConfig.BASE_URL+mainModel.getImagepath();
                        patientEmail=mainModel.getPatientContactDetails().getEmail();
                        callPatient=mainModel.getPatientContactDetails().getMobile();
                        holder.emailTo.setText(patientEmail);
                        holder.callPat.setText("Mobile : "+callPatient);
                        mImageLoader = CustomVolleyRequestQueue.getInstance(context)
                                .getImageLoader();

                        //holder.profileImage.setImageUrl(imagePath,mImageLoader);
                        //dismissLoadingDialog();
                        //Toast.makeText(context.getApplicationContext(),"imagePath"+imagePath,Toast.LENGTH_LONG).show();
                        //showLoadingDialog();

                        new Thread() {
                            public void run() {
                                try {
                                    URL url = new URL(imagePath);
                                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                    connection.setDoInput(true);
                                    connection.connect();
                                    InputStream input = connection.getInputStream();
                                    final Bitmap myBitmap = BitmapFactory.decodeStream(input);
                                    ((CalendraScheduleBookedDetailActivity)context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            holder.profileImage.setImageBitmap(myBitmap);
                                            dismissLoadingDialog();
                                        }
                                    });
                                }catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //dismissLoadingDialog();
                    }
                });*/
                String patAge = getAge(year, month, day);
                int gender = patDemoGraphicsString.getGenderId();
                holder.name.setText(patName);
                if (gender==1) {
                    holder.age.setText(patAge + "Years/Male");
                } else {
                    holder.age.setText(patAge + "Years/Female");
                }
            }catch (NullPointerException e){

            }

        }

                /*int genderId=model.getPatDemoGraphicses().get(position).getGenderId();
                if(genderId==1){
                    holder.age.setText("M");
                }
                else{
                    holder.age.setText("F");
                }*/
        //}


    }


    @Override
    public int getItemCount() {
        if (type.equals("free")) {
            length = appointments.size();

        } else if (type.equals("booked")) {
            length = appointmentsBooked.size();
        }
        return length;
    }

    private boolean checkAppointmentAvail(int finalJ) {
        int appointAvail = 0;
        total_section = mScheduleArray[finalJ].getAppointments().size();

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

    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        ageS = ageInt.toString();
        System.out.println("age-->" + age);
        return ageS;
    }

    private String getDob(String dob) {
        Date createdOn = Helper.JsonDateToDate(dob);
//        String timestamp = dob.split("\\(")[1].split("\\+")[0];
//        Date createdOn = new Date();
//        try {
//            createdOn  = new Date(Long.parseLong(timestamp));
//        }catch (NumberFormatException  numberFormatException){
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
        String formattedDate = sdf.format(createdOn);
        System.out.println("formattedDate-->" + formattedDate);


        return formattedDate;
    }

    public void setBlockApi() {
        String isDelete_Unblock = null;
        if (avlStatusId == 1) {
            isDelete_Unblock = "false";
        } else if (avlStatusId == 3) {
            isDelete_Unblock = "true";
        } else if(avlStatusId == 2){
            isDelete_Unblock = "true";
        }

        JsonObject blockPatient = new JsonObject();
        blockPatient.addProperty("appointmentid", appointmentid);
        blockPatient.addProperty("UserRoleID", userRoleId);
        blockPatient.addProperty("isDelete_Unblock", isDelete_Unblock);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.updateAppointment_Block(blockPatient, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                if (s.equals("true")) {
                    if (avlStatusId == 1) {
                        showSuccessDialog("Appointment Blocked Successfully.");
                    }else if(avlStatusId == 2){
                        showSuccessDialog("Appointment Cancelled Successfully.");
                    }else if(avlStatusId == 3){
                        showSuccessDialog("Appointment Unblocked Successfully.");
                    }
                } else {
                    if (avlStatusId == 1) {
                        showErrorDialog("Appointment Blocked Unsuccessfully.");
                    } else if (avlStatusId == 2) {
                        showErrorDialog("Appointment Cancelled Unsuccessfully.");
                    } else if (avlStatusId == 3) {
                        showErrorDialog("Appointment Unblocked Unsuccessfully.");
                    }
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });

    }
    private void showSuccessDialog(String msg) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Success");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (avlStatusId == 1) {
                    postActivityTrackerFromAPI(0);
                }else if(avlStatusId == 2){
                    postActivityTrackerFromAPI(0);
                }else if(avlStatusId == 3){
                    postActivityTrackerFromAPI(0);
                }
                successAlertDialog.dismiss();
            }
        });
        successAlertDialog = builder.show();
    }
    private void showErrorDialog(String msg) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Failure");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                errorAlertDialog.dismiss();
            }
        });
        errorAlertDialog = builder.show();
    }

    /**
     *
     */
    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(context.getString(R.string.loading_message));
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
    public static boolean CheckDates(String startDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String currentDateandTime = dfDate.format(new Date());

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(currentDateandTime))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(currentDateandTime))) {
                b = true;  // If two dates are equal.
            } else {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }
    class SetImageFromServer extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {

            return null;
        }
    }
    private void showCancelReasonDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((CalendraScheduleBookedDetailActivity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.cancel_reason_dialog_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        TextView tvAlertSubmit =  dialogView.findViewById(R.id.tvAlertSubmit);
        TextView tvAlertCancel =  dialogView.findViewById(R.id.tvAlertCancel);
        final EditText etReason = dialogView.findViewById(R.id.etReason);
        final AlertDialog alertDialog = dialogBuilder.show();
        tvAlertSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtReason = etReason.getText().toString().trim();
                if(!TextUtils.isEmpty(txtReason)){
                    setBlockApi();
                    alertDialog.dismiss();
                }else{
                    etReason.setError("Please enter reason.");
                }

            }
        });
        tvAlertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    private void postActivityTrackerFromAPI(int deliveryStatus) {
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("actBuildVersion",buildVersionName);
        obj.addProperty("delivered",deliveryStatus);
        obj.addProperty("actUserRoleId",frontOfficeUserRoleId);
        obj.addProperty("actSubTenantId",subTanentId);
        obj.addProperty("actScheduleId",mScheduleArray[0].getScheduleId());
        obj.addProperty("actAppId",appointmentid);
        obj.addProperty("actUserMediumId",9);
        obj.addProperty("drUserRoleId",userRoleId);
        obj.addProperty("actRemarks",txtReason);
        obj.addProperty("actTransMasterId",actTransactId);
        obj.addProperty("patMrno",patMrno);
        obj.addProperty("actOthers","");

        mCuraApplication.getInstance().mCuraEndPoint.postActivityTracker(obj, new Callback<PostActivityTrackerModel>() {
            @Override
            public void success(PostActivityTrackerModel postActivityTrackerModel, Response response) {
                context.startActivity(new Intent(context.getApplicationContext(), CalendarActivity.class));
                dismissLoadingDialog();
            }
            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public static String extractPhoneNumber(String phoneNumberWithPrefix) {
        // Check if the string contains "tel:Mobile : " prefix
        if (phoneNumberWithPrefix.startsWith("tel:Mobile : ")) {
            // Remove the prefix from the phone number
            return phoneNumberWithPrefix.replace("tel:Mobile : ", "");
        } else {
            // If no prefix is found, return the original string as it is
            return phoneNumberWithPrefix;
        }
    }

}
