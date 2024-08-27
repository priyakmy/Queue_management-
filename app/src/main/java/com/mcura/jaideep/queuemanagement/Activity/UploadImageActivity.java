package com.mcura.jaideep.queuemanagement.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.GlideImageLoader;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.GetMedicalRecordNatureModel;
import com.mcura.jaideep.queuemanagement.Model.PatientVerificationModel.PatVerificationResponseModel;
import com.mcura.jaideep.queuemanagement.Model.PostPatMedRecord;
import com.mcura.jaideep.queuemanagement.Model.RecNatureListAdapter;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.Utils.Constants;
import com.mcura.jaideep.queuemanagement.helper.EnumType;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UploadImageActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private TextView tv_cal;

    Bitmap bitmap;
    public int user_role_id;
    String resultBitmap;
    private ImageView iv_image;
    private VideoView videoPreview;
    private int natureId;
    private String imagePathUri;
    private String imageType;
    final int REQUEST_CAMERA = 1;
    final int REQUEST_GALLARY = 2;
    private final int REQUEST_VIDEO = 5;
    private String completeDate;
    private MCuraApplication mCuraApplication;
    private ProgressDialog progressDialog;
    private String ImagePathId;
    private int patUserRoleId;
    private int patMrno;


    private AlertDialog dialog;
    private long imageLength;
    private ArrayList<GetMedicalRecordNatureModel> recordNatureModelArrayList;
    private ListView recNatureListView;
    private RecNatureListAdapter recNatureListAdapter;
    private TextView sp_listnatureId;
    private int subTanentId;
    private int roleId;
    private int actTransactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        imageType = getIntent().getStringExtra("resultCode");

        patMrno  = getIntent().getIntExtra("mr_no",0);
        actTransactId = EnumType.ActTransactMasterEnum.Patient_Record_Upload.getActTransactMasterTypeId();
        imagePathUri = getIntent().getStringExtra("imagePathUri");
        Log.d("imageType", imageType + "");
        Log.d("imagePath", imagePathUri + "");
        SharedPreferences mSharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        user_role_id = mSharedPreferences.getInt(Constant.USER_ROLE_ID, 0);
        patUserRoleId = mSharedPreferences.getInt(Constants.PATUSERROLEID, 0);
      //  patMrno = mSharedPreferences.getString(Constants.PATMRNO, "0");
        subTanentId = mSharedPreferences.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        roleId = mSharedPreferences.getInt(Constants.ROLEID,0);
        recordNatureModelArrayList = (ArrayList<GetMedicalRecordNatureModel>) getIntent().getSerializableExtra("rec_nature_list");
      //  imagePath = getIntent().getStringExtra("imagePath");

//        File file = new File(imagePath);
//        imageLength = file.length();
//        imageLength = imageLength / 1024;
//        Log.d("image_size", imageLength + "");
        initView();
        //new TakePhotoTask().execute();
    }

    private void initView() {
        tv_cal = findViewById(R.id.tv_cal);
        tv_cal.setText(Helper.getCompleteDate());
        iv_image = findViewById(R.id.iv_image);
        videoPreview = findViewById(R.id.videoPreview);
        if (imageType.equals("REQUEST_GALLARY") ) {
            iv_image.setVisibility(View.VISIBLE);
            GlideImageLoader.loadImage(iv_image,imagePathUri,UploadImageActivity.this);
            videoPreview.setVisibility(View.GONE);
        }else if (imageType.equals("REQUEST_CAMERA")){

            iv_image.setVisibility(View.VISIBLE);


            bitmap = (Bitmap) this.getIntent().getParcelableExtra("image");
            iv_image.setImageBitmap(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            resultBitmap = Base64.encodeToString(byteArray, Base64.DEFAULT);



        }






      //  TypedFile typedFile = new TypedFile("image/*", new File(imagePath));

     //   GlideImageLoader.loadImage(iv_image,imagePathUri,UploadImageActivity.this);
        TextView tv_img_upload = findViewById(R.id.tv_img_upload);
        TextView tv_cancel = findViewById(R.id.tv_cancel);
        sp_listnatureId = findViewById(R.id.sp_listnatureId);
        sp_listnatureId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordNatureModelArrayList!=null){
                    if(recordNatureModelArrayList.size()>0){
                        showMedicalRecordNatureDialog();
                    }else{
                        getMedicalRecordNature();
                    }
                }else{
                    getMedicalRecordNature();
                }
            }
        });
        List<String> list = new ArrayList<String>();
        list.add("----Select Record----");
        list.add("Lab Report");
        list.add("Current Visit Image");
        list.add("Visit Summary");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, R.layout.dropdown_item, list);

        dataAdapter.setDropDownViewResource
                (R.layout.dropdown_item);

        /*sp_listnatureId.setAdapter(dataAdapter);
        sp_listnatureId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("===position" + position);
                switch (position) {
                    case 0:
                        natureId = 0;
                        break;
                    case 1:
                        natureId = Helper.RecordNatureEnum.Lab_Report.getNatureId();
                        break;
                    case 2:
                        natureId = Helper.RecordNatureEnum.Current_Visit_Image.getNatureId();
                        break;
                    case 3:
                        natureId = Helper.RecordNatureEnum.Visit_Summary.getNatureId();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        tv_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(UploadImageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yy, int mm, int dd) {

                        String date = yy + "-" + (mm + 1) + "-" + dd;
                        tv_cal.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeDate = tv_cal.getText().toString();
                Log.d("completeDate", completeDate);
                if (!completeDate.equals("Enter Date")) {
                    if (natureId != 0) {
                        if (imageType.equals("REQUEST_GALLARY") ) {

                            File file = new File(imagePathUri);
                            try {
                                fileTOBase64(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }





                        } else if (imageType.equals("REQUEST_CAMERA")) {
                            uploadOrderImageApi(resultBitmap);

                        }
                    } else {
                        Toast.makeText(UploadImageActivity.this, "Please Choose Nature", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tv_cal.setError("Choose date");
                }
            }
        });
    }

    private String fileTOBase64(File yourFile) throws IOException{
        int size = (int) yourFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       String pdfPick = Base64.encodeToString(bytes,Base64.NO_WRAP);
        uploadOrderImageApi(pdfPick);
        Log.d("imagepathhh",pdfPick);

        return pdfPick;
    }


    private void getMedicalRecordNature() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getMedicalRecordNature(subTanentId,roleId, new Callback<GetMedicalRecordNatureModel[]>() {
            @Override
            public void success(GetMedicalRecordNatureModel[] getMedicalRecordNatureModels, Response response) {
                if(getMedicalRecordNatureModels!=null){
                    if(getMedicalRecordNatureModels.length>0){
                                recordNatureModelArrayList = new ArrayList();
        for(int i=0;i<getMedicalRecordNatureModels.length;i++){
            if(getMedicalRecordNatureModels[i].getRecNatureId()!=6) {
                recordNatureModelArrayList.add(getMedicalRecordNatureModels[i]);
            }
        }
//                        recordNatureModelArrayList = new ArrayList<>(Arrays.asList(getMedicalRecordNatureModels));
                        showMedicalRecordNatureDialog();
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
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Filter filter = recNatureListAdapter.getFilter();
        if (TextUtils.isEmpty(newText)) {
            filter.filter("");
        } else
            filter.filter(newText);
        return true;
    }


    private void uploadOrderImageApi(String imagePathUri) {
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("fileStream",imagePathUri );
        obj.addProperty("type", "image");
        obj.addProperty("extension", "jpg");
        mCuraApplication.getInstance().mCuraEndPoint.uploadOrderImage(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
               // ImagePathId = s;
                postPatMedRecordAPI(s);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(UploadImageActivity.this, "Error" + error.getMessage(), Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        });
    }

    private void postPatMedRecordAPI(String s) {
        String sessionKey = "SES" + Helper.getCurrentTimestamp();

        showLoadingDialog();
        JsonObject obj = new JsonObject();

        obj.addProperty("UserRoleID", user_role_id);
        obj.addProperty("RecNatureId", "13");
        obj.addProperty("MRNo", patMrno);
        obj.addProperty("date", completeDate);
        obj.addProperty("datatype", 0);
        obj.addProperty("SubTenantId", subTanentId);
        obj.addProperty("sessionkey", sessionKey);
        JsonArray jsonArrayFilename = new JsonArray();
        JsonObject jsonfile = new JsonObject();
        jsonfile.addProperty("laterDate", "");
        jsonfile.addProperty("description", s);
        jsonArrayFilename.add(jsonfile);
        obj.add("fileName", jsonArrayFilename);


        mCuraApplication.getInstance().mCuraEndPoint.postPat_Med_Record(obj, new Callback<PostPatMedRecord>() {
            @Override
            public void success(PostPatMedRecord postPatMedRecord, Response response) {
                Toast.makeText(getApplicationContext(),"Image upload success", Toast.LENGTH_SHORT).show();

                dismissLoadingDialog();
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(UploadImageActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        });
    }
    private void postEndUserTrackingAPI(int recordId) {
        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("actSubTenantId", subTanentId);
        obj.addProperty("actUserRoleId", patUserRoleId);
        obj.addProperty("actUserMediumId", 7);
        obj.addProperty("patMrno", patMrno);
        obj.addProperty("actTransMasterId", actTransactId);
        obj.addProperty("actRemarks", "Record Upload");
        obj.addProperty("actBuildVersion", Helper.getBuildVersion(this));
        obj.addProperty("RecordId", recordId);
        obj.addProperty("delivered", 0);
        mCuraApplication.getInstance().mCuraEndPoint.postEndUserTracking(obj, new Callback<PatVerificationResponseModel>() {
            @Override
            public void success(PatVerificationResponseModel patVerificationResponseModel, Response response) {
                if(patVerificationResponseModel.getStatus()==1){
                    showSuccessDialog("Fetched Patient detail successfully.");
                }else{
                    showSuccessDialog("Unable to Fetch Patient detail successfully.");
                    //Toast.makeText(getActivity(),"Some error occur please try again",Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
                //Toast.makeText(getActivity(),"Some error occur please try again",Toast.LENGTH_LONG).show();
                showSuccessDialog("Fetched Patient detail successfully.");
            }
        });
    }

    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
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

    public String getPathFromURI(Uri contentUri) {
        if (contentUri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        cursor.close();
        return null;
    }

    private void showSuccessDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Success");
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });

        dialog = builder.show();
    }

    private void showErrorDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });

        dialog = builder.show();
    }
    private void showMedicalRecordNatureDialog() {
        GetMedicalRecordNatureModel recNature = new GetMedicalRecordNatureModel();
//        recNature.setRecNatureId(0);
//        recNature.setRecNatureProperty("Select Nature");
//        recordNatureModelArrayList.set(0,recNature);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.medical_record_nature_dialog, null);
        builder.setView(convertView);
        SearchView filterRecNature = convertView.findViewById(R.id.filterRecNature);
        filterRecNature.setIconified(false);
        filterRecNature.setIconifiedByDefault(false);
        filterRecNature.setOnQueryTextListener(this);
        filterRecNature.setQueryHint("Search Here");
        recNatureListView = (ListView) convertView.findViewById(R.id.recNatureList);
        recNatureListView.setTextFilterEnabled(true);
        recNatureListAdapter = new RecNatureListAdapter(UploadImageActivity.this,
                android.R.layout.simple_spinner_item, recordNatureModelArrayList);
        recNatureListView.setAdapter(recNatureListAdapter);
        dialog = builder.show();
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width * 0.9);
        lp.height = (int) (height * 0.5);
        lp.alpha = 1f; // Transparency

        dialogWindow.setAttributes(lp);

        recNatureListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                natureId = recNatureListAdapter.getItem(position).getRecNatureId();
                sp_listnatureId.setText(recNatureListAdapter.getItem(position).getRecNatureProperty()+"");
                dialog.dismiss();
            }
        });
    }

}
