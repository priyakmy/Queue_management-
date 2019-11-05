package com.mcura.jaideep.queuemanagement.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mcura.jaideep.queuemanagement.Activity.AddNewAppointment;
import com.mcura.jaideep.queuemanagement.Activity.LoadNFC;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.PatientContactDetails;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mCURA1 on 2/23/2017.
 */

public class SearchPatientAdapter_v1 extends RecyclerView.Adapter<SearchPatientAdapter_v1.ViewHolder> {
    private ImageLoader mImageLoader;
    public MCuraApplication mCuraApplication;
    ProgressDialog progressDialog;
    Context context;
    String ageS;
    int userRoleId;
    int hospitalSubtanentId;
    SearchPatientModel patientSearchModelsArray[];
    SearchPatientModel patientSearchModel;
    PatientContactDetails patientContactDetails;
    int subTanentId;
    List<PatientSearchModel> patientSearchModelsList = new ArrayList<>();

    public SearchPatientAdapter_v1(Context context, SearchPatientModel patientSearchModelsArray, int userRoleId, int hospitalSubtanentId) {
        this.hospitalSubtanentId = hospitalSubtanentId;
        this.userRoleId = userRoleId;
        this.context = context;
        this.patientSearchModel = patientSearchModelsArray;
        //this.patientSearchModelsArray = patientSearchModelsArray;
        /*List<Datum> dataList = new ArrayList<Datum>();
        Datum[] data = new Datum[patientSearchModelsArray.getNoShowData().size()];
        dataList = patientSearchModelsArray.getNoShowData();*/
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        subTanentId = sharedPreferences.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        /*for(int i=0;i<patientSearchModelsArray.length;i++){
            patientSearchModel = new PatientSearchModel();
            patientContactDetails = new PatientContactDetails();
            patientSearchModel.setPatname(patientSearchModelsArray[i].getPatname());
            patientContactDetails.setMobile(patientSearchModelsArray[i].getPatientContactDetails().getMobile());
            patientSearchModel.setPatientContactDetails(patientContactDetails);
            patientSearchModel.setMRNO(patientSearchModelsArray[i].getMRNO());
            patientSearchModel.setDob(patientSearchModelsArray[i].getDob());
            patientSearchModel.setImagepath(patientSearchModelsArray[i].getImagepath());
            patientSearchModel.setGenderId(patientSearchModelsArray[i].getGenderId());
            patientSearchModel.setHospitalNo(patientSearchModelsArray[i].getHospitalNo());
            patientSearchModelsList.add(patientSearchModel);
        }*/
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, age, mrno, hospital_id;
        public ImageView profileImage, load_card, edit_patient;
        public TextView callPat;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            age = (TextView) v.findViewById(R.id.age);
            profileImage = (ImageView) v.findViewById(R.id.profile_image);
            load_card = (ImageView) v.findViewById(R.id.load_card);
            edit_patient = (ImageView) v.findViewById(R.id.edit_pat);
            callPat = (TextView) v.findViewById(R.id.call_patient);
            mrno = (TextView) v.findViewById(R.id.mrno);
            hospital_id = (TextView) v.findViewById(R.id.hospital_id);
            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class).putExtra("mrnoValue", Integer.parseInt(mrno.getText().toString())).putExtra("subTanentId", hospitalSubtanentId));
                }
            });*/
            load_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context.getApplicationContext(), LoadNFC.class).putExtra("mrnoValue", Integer.parseInt(mrno.getText().toString())).putExtra("subTanentId", hospitalSubtanentId).putExtra("hospital_id", hospital_id.getText().toString()));
                }
            });
            edit_patient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Datum model = patientSearchModel.getData().get(getPosition());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PatientSearchModel",  model);
                    context.startActivity(new Intent(context, AddNewAppointment.class).putExtra("updateStatus", "update_patient").putExtras(bundle).putExtra("actTransactionId", EnumType.ActTransactMasterEnum.Edit_Patient_From_Top_Panel.getActTransactMasterTypeId()));
                    //context.this.finish();
                }
            });
        }
    }

    @Override
    public SearchPatientAdapter_v1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        ViewHolder vh = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_patient_activity_row_layout, null);
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SearchPatientAdapter_v1.ViewHolder holder, int position) {

        Datum model = patientSearchModel.getData().get(position);
        Log.d("patname",model.getPatName());
        String dobEncode = model.getDob();
        String timestamp = dobEncode.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date(Long.parseLong(timestamp));
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

        String patAge = getAge(year, month, day);
        int gender = model.getGenderId();
        if (gender == 1) {
            holder.age.setText(patAge + "/M");
        } else {
            holder.age.setText(patAge + "/F");
        }
        holder.name.setText(model.getPatName().toString());
        holder.mrno.setText(model.getMrNo().toString());
        holder.callPat.setText(model.getMobileNo().toString());
        holder.hospital_id.setText(model.getHospitalNo().toString());
        //showLoadingDialog();
        final String imagePath = "http://test.tn.mcura.com/" + model.getImagePath();
        Picasso.with(context).load(imagePath).placeholder(R.drawable.patient_image).into(holder.profileImage);
        /*new Thread() {
            public void run() {
                try {
                    URL url = new URL(imagePath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    final Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    ((SearchPatientActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.profileImage.setImageBitmap(myBitmap);
                            //dismissLoadingDialog();
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();*/

    }

    @Override
    public int getItemCount() {
        return patientSearchModel.getData().size();
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
}

