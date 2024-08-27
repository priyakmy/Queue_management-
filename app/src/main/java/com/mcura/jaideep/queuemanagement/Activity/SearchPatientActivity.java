package com.mcura.jaideep.queuemanagement.Activity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcura.jaideep.queuemanagement.Adapter.SearchPatientAdapter_v1;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.GetMedicalRecordNatureModel;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.PostPatMedRecord;
import com.mcura.jaideep.queuemanagement.Model.RecNatureListAdapter;
import com.mcura.jaideep.queuemanagement.Model.SearchHospital;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.Utils.Constants;
import com.mcura.jaideep.queuemanagement.Utils.NatureEnum;
import com.mcura.jaideep.queuemanagement.helper.EnumType;
import com.mcura.jaideep.queuemanagement.helper.Helper;
import com.mcura.jaideep.queuemanagement.view.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchPatientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener, SearchView.OnQueryTextListener,OnItemClickListener{
    private Spinner spHospitalName;
    private ProgressDialog progressDialog;
    private String pdfDate;
    private ListView recNatureListView;
    private TextView tvListNatureId;

    private GetMedicalRecordNatureModel[] medicalRecordNatureModelArray;
    public static final int RequestPermissionCode = 4;
    private String mCameraFileName;
    public static int natureId = 0;
    private static final int REQUESTCODE_PICK_PDF = 3;
    final int REQUEST_CAMERA = 2;
    final int REQUEST_GALLARY = 1;
    String query = "", lastQuery = "";
    public MCuraApplication mCuraApplication;
    SearchHospital searchHospitalArray[];
    HashMap<Integer, String> hmHospitalName = new HashMap<>();
    List<String> hospitalNameList;
    ArrayAdapter hospitalAdapter;
    int userRoleId;
    private SearchView selectPatientSearchview;

    private RecNatureListAdapter recNatureListAdapter;
    public PatientSearchModel[] patientSearchModelsArray;
    private ListView patientListview;
    int hospitalSubtanentId;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SharedPreferences mSharedPreference;
    int sub_tanent_id;
    SearchPatientModel patientSearchModel;
    private AlertDialog dialog;
    private ArrayList<GetMedicalRecordNatureModel> recordNatureModelArrayList;

    private String pdfPick;
    int firstIndex = 2;
    List<Datum> dataList;
    private CheckBox cb_mrno, cb_mobile, cb_patname, cb_hospitalid;
    private String strSearchBy;
    OnItemClickListener onItemClickListener;
    public static int mr_no;

    private String pdfName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        String subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        sub_tanent_id = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        userRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
//
//        userRoleId = mSharedPreference.getInt(Constants.ROLEID,0);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView hospital_logo = (ImageView) mToolbar.findViewById(R.id.hospital_logo);
        ImageView add_patient = (ImageView) mToolbar.findViewById(R.id.add_patient);
        if(sub_tanent_id==576 || sub_tanent_id==587){
            add_patient.setVisibility(View.GONE);
        }
        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchPatientActivity.this, AddNewAppointment.class).putExtra("appNatureId", 1).putExtra("updateStatus", "add_new_patient").putExtra("registerStatus", "search").putExtra("actTransactionId", EnumType.ActTransactMasterEnum.Register_Patient_From_Top_Panel.getActTransactMasterTypeId()));
            }
        });
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setSubtitle("");
            Picasso.with(SearchPatientActivity.this).load(subtanentImagePath).into(hospital_logo);
            /*if(!subtanentImagePath.equals("default")){
                Picasso.with(SearchPatientActivity.this).load(subtanentImagePath).into(hospital_logo);
            }else{
                Picasso.with(SearchPatientActivity.this).load(R.drawable.kims_logo).into(hospital_logo);
            }*/
            /*if(sub_tanent_id == 500){
                getSupportActionBar().setIcon(R.drawable.blk_logo);
            }else if(sub_tanent_id == 243){
                getSupportActionBar().setIcon(R.drawable.sgrh);
            }*/
        }


        //userRoleId = getIntent().getIntExtra("userroleid",0);

        cb_mrno = (CheckBox) findViewById(R.id.cb_mrno);
        cb_mobile = (CheckBox) findViewById(R.id.cb_mobile);
        cb_patname = (CheckBox) findViewById(R.id.cb_patname);
        cb_hospitalid = (CheckBox) findViewById(R.id.cb_hospitalid);

        spHospitalName = (Spinner) findViewById(R.id.select_hospital_spinner);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerViewSearchPatient);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        selectPatientSearchview = (SearchView) findViewById(R.id.select_patient_searchview);
        selectPatientSearchview.setIconified(false);
        selectPatientSearchview.setIconifiedByDefault(false);
        selectPatientSearchview.setQueryHint("Search Patient");
        spHospitalName.setOnItemSelectedListener(this);
        selectPatientSearchview.setOnQueryTextListener(this);


        onItemClickListener=new OnItemClickListener() {
            @Override
            public void onItemClick(int position, String s) {
                mr_no = Integer.parseInt(s);

                selectImageDialog();

            }


        };

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItem = mLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastItem == patientSearchModel.getData().size() - 1) {
                    getPatientSearchDetail_v2(firstIndex, query);
                    firstIndex++;
                }
            }
        });
        getHospitalFromAPI();


    }

    public void selectImageDialog() {
        final Dialog dialog = new Dialog(this, R.style.MyDialogTheme);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.upload_image_list_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width * 0.9);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 1f; // Transparency

        dialogWindow.setAttributes(lp);
        dialog.show();
        TextView tv_capture_from_camera = (TextView) dialog.findViewById(R.id.tv_capture_from_camera);
        TextView tv_capture_from_gallary = (TextView) dialog.findViewById(R.id.tv_capture_from_gallary);
        TextView tv_upload_pdf = (TextView) dialog.findViewById(R.id.tv_upload_pdf);
        TextView tv_upload_video = (TextView) dialog.findViewById(R.id.tv_upload_video);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);

        tv_upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPdf();
                dialog.dismiss();
            }


        });
        tv_capture_from_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent intent = new Intent();
                    // intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                    File destination = new File(Environment.getExternalStorageDirectory(),
                            System.currentTimeMillis() + ".png");

                    mCameraFileName = destination.toString();
                    Log.d("mCameraFileName",mCameraFileName);
                    Uri outuri = Uri.fromFile(destination);

                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, REQUEST_CAMERA);


//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
//                    startActivityForResult(intent, REQUEST_CAMERA);





                } else {
                    requestPermission();
                }
                dialog.dismiss();
            }
        });

        tv_capture_from_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    galleryIntent();
                } else {
                    requestPermission();
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                CAMERA);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, RequestPermissionCode);
    }
    private void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);

        startActivityForResult(Intent.createChooser(galleryIntent, "Select File"), REQUEST_GALLARY);

    }

    private void pickPdf() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");   //XML file only
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"),
                    REQUESTCODE_PICK_PDF);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dataList.clear();
        getPatientSearchDetail(1, this.query);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.select_hospital_spinner:
                String hospitalName = parent.getItemAtPosition(position).toString();
                for (Integer hospitalId : hmHospitalName.keySet()) {
                    if (hmHospitalName.get(hospitalId).equals(hospitalName)) {
                        hospitalSubtanentId = hospitalId;
                    }
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*private void getPatientSearchDetail(String searchKey){
        patientSearchModelsArray=new PatientSearchModel[]{};
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getSearchPatient(userRoleId, searchKey, hospitalSubtanentId, new Callback<PatientSearchModel[]>() {
            @Override
            public void success(PatientSearchModel[] patientSearchModels, Response response) {
                patientSearchModelsArray = patientSearchModels;

                Toast.makeText(SearchPatientActivity.this, "patientSearchModels = " + patientSearchModelsArray.length,
                        Toast.LENGTH_SHORT).show();
                dismissLoadingDialog();
                mAdapter = new SearchPatientLoadNFCAdapter(SearchPatientActivity.this, patientSearchModelsArray, userRoleId, hospitalSubtanentId);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }*/
    private void getPatientSearchDetail(int firstIndex, String searchKey) {
        patientSearchModelsArray = new PatientSearchModel[]{};
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.searchPatient_v1(firstIndex, 10, userRoleId, strSearchBy, searchKey, hospitalSubtanentId, new Callback<SearchPatientModel>() {
            @Override
            public void success(SearchPatientModel patientSearchModels, Response response) {
                //patientSearchModel = patientSearchModels;
                Log.d("list_size", patientSearchModels.getData().size() + "");
                if (patientSearchModels.getData().size() > 0) {
                    for (int i = 0; i < patientSearchModels.getData().size(); i++) {
                        dataList.add(patientSearchModels.getData().get(i));
                    }
                    patientSearchModel.setData(dataList);
                    patientSearchModel.setStatus(patientSearchModels.getStatus());
                    patientSearchModel.setTotalResultCount(patientSearchModels.getTotalResultCount());
                    mAdapter = new SearchPatientAdapter_v1(SearchPatientActivity.this, patientSearchModel, userRoleId, hospitalSubtanentId,onItemClickListener);
                    mRecyclerView.setAdapter(mAdapter );



                } else {
                    Toast.makeText(SearchPatientActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }




    private void getPatientSearchDetail_v2(int firstIndex, String searchKey) {
        patientSearchModelsArray = new PatientSearchModel[]{};
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.searchPatient_v1(firstIndex, 10, userRoleId, strSearchBy, searchKey, hospitalSubtanentId, new Callback<SearchPatientModel>() {
            @Override
            public void success(SearchPatientModel patientSearchModels, Response response) {
                if (patientSearchModels.getData().size() > 0) {
                    for (int i = 0; i < patientSearchModels.getData().size(); i++) {
                        dataList.add(patientSearchModels.getData().get(i));
                    }
                    patientSearchModel.setData(dataList);
                    Log.d("size", patientSearchModel.getData().size() + "");
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SearchPatientActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void getHospitalFromAPI() {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getHospital(userRoleId, new Callback<SearchHospital[]>() {
            @Override
            public void success(SearchHospital[] searchHospitals, Response response) {
                searchHospitalArray = searchHospitals;
                setHospitalName();
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    public void setHospitalName() {
        for (int i = 0; i < searchHospitalArray.length; i++) {
            hmHospitalName.put(searchHospitalArray[i].getSubTenantId(), searchHospitalArray[i].getSubTenantName());
        }
        hospitalNameList = new ArrayList<>(hmHospitalName.values());
        hospitalAdapter = new ArrayAdapter<String>(this, R.layout.search_patient_row_text, hospitalNameList);
        spHospitalName.setAdapter(hospitalAdapter);
    }

    /**
     *
     */
    public void showLoadingDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(SearchPatientActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getString(R.string.loading_message));
        }
        progressDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_CAMERA) {

            if (data != null) {


                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                startActivity(new Intent(this, UploadImageActivity.class).
                        putExtra("resultCode","REQUEST_CAMERA").
                        putExtra("image",thumbnail).
                        putExtra("mr_no",mr_no).
                        putExtra("rec_nature_list",recordNatureModelArrayList));
                this.overridePendingTransition(R.anim.layout_slide_in_up, R.anim.stay);
//

            }


//                Uri  selectedImageUri = data.getData();
//                if (null != selectedImageUri) {
//                    // Get the path from the Uri
//                    String path = getPathFromURI(selectedImageUri);
//                    Log.i(TAG, "Image Path : " + path);
//
//                    startActivity(new Intent(this, UploadImageActivity.class).
//                            putExtra("resultCode",REQUEST_GALLARY).
//                            putExtra("imagePathUri",path).
//                            putExtra("rec_nature_list",recordNatureModelArrayList));
//                    this.overridePendingTransition(R.anim.layout_slide_in_up, R.anim.stay);
//                    // Set the image in ImageView
//
//                }
            // camera_image.setImageURI(selectedImageUri);







            //   onCaptureImageResult(data);



        } else if (requestCode == REQUEST_GALLARY) {


            if (data != null) {
                Uri  selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);


                    startActivity(new Intent(this, UploadImageActivity.class).
                            putExtra("resultCode","REQUEST_GALLARY").
                            putExtra("imagePathUri",path).
                            putExtra("mr_no",mr_no).
                            putExtra("rec_nature_list",recordNatureModelArrayList));
                    this.overridePendingTransition(R.anim.layout_slide_in_up, R.anim.stay);
                    // Set the image in ImageView

                }
                // camera_image.setImageURI(selectedImageUri);

            }






                /*uploadImageViaRecNature(REQUEST_GALLARY);
                onSelectFromGalleryResult(data);*/
        } else if (requestCode == REQUESTCODE_PICK_PDF) {
            onSelectPdfFromStorage(data);
            if (data != null) {
                String pdfFilePath = getFilePathForN(getApplicationContext(), data.getData());
                Log.d("pdfFilePath",pdfFilePath);
                pdfName = pdfFilePath.substring(pdfFilePath.lastIndexOf('/')+1);
                Log.d("pdfFilePath",pdfName);
                File file = new File(pdfFilePath);
                try {
                    fileTOBase64(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

        pdfPick = Base64.encodeToString(bytes,Base64.NO_WRAP);
        showRecNatureDialog(pdfPick);

        return pdfPick;
    }

    private void showRecNatureDialog(final String pdfPick) {
        natureId = 0;
        final Dialog dialog = new Dialog(SearchPatientActivity.this, R.style.MyDialogTheme);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_rec_nature_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width * 0.9);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 1f; // Transparency

        dialogWindow.setAttributes(lp);

        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        TextView tv_submit = (TextView) dialog.findViewById(R.id.tv_submit);

        final TextView tv_cal = (TextView) dialog.findViewById(R.id.tv_cal);
        tv_cal.setText(Helper.getCompleteDateyyMMdd());
        pdfDate = tv_cal.getText().toString();
        TextView tv_show_lab_report = (TextView) dialog.findViewById(R.id.tv_show_lab_report);
        TextView tv_show_current_visit = (TextView) dialog.findViewById(R.id.tv_show_current_visit);
        TextView tv_show_visit_summary = (TextView) dialog.findViewById(R.id.tv_show_visit_summary);
        TextView tvPdfName = dialog.findViewById(R.id.tvPdfName);
        tvPdfName.setText("File Name: "+pdfName);
        tvListNatureId = dialog.findViewById(R.id.tvListNatureId);
        tvListNatureId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(medicalRecordNatureModelArray!=null){
                    if(medicalRecordNatureModelArray.length>0){
                        showMedicalRecordNatureDialog(2);
                    }else{
                        getMedicalRecordNature(2);
                    }
                }else{
                    getMedicalRecordNature(2);
                }
            }
        });
        tv_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(SearchPatientActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yy, int mm, int dd) {

                        String date = yy + "-" + (mm + 1) + "-" + dd;
                        tv_cal.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();

            }
        });
        tv_show_lab_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                natureId = NatureEnum.mLabReport.natureId();
                fileUploadPDF(pdfPick);
                dialog.dismiss();
            }
        });
        tv_show_current_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                natureId = NatureEnum.mCurrentVisitImage.natureId();
                fileUploadPDF(pdfPick);
                dialog.dismiss();
            }
        });
        tv_show_visit_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                natureId = NatureEnum.mVisitSummary.natureId();
                Log.d("natureId", natureId + "");
                fileUploadPDF(pdfPick);
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                natureId = 0;
                 dialog.dismiss();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (natureId != 0) {
                    fileUploadPDF(pdfPick);
                }else{
                    Toast.makeText(getApplicationContext(), "Please Choose Nature", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getMedicalRecordNature(final int show) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getMedicalRecordNature(sub_tanent_id,0, new Callback<GetMedicalRecordNatureModel[]>() {
            @Override
            public void success(GetMedicalRecordNatureModel[] getMedicalRecordNatureModels, Response response) {
                if(getMedicalRecordNatureModels!=null){
                    if(getMedicalRecordNatureModels.length>0){
                        medicalRecordNatureModelArray = getMedicalRecordNatureModels;
                        if(show==1){
                            showMedicalRecordNatureDialog(medicalRecordNatureModelArray);
                        }else if(show==2){
                            showMedicalRecordNatureDialog(2);
                        }

                    }else {
                        Toast.makeText(SearchPatientActivity.this,"No responce!",Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(SearchPatientActivity.this,"No responce received!",Toast.LENGTH_LONG).show();

                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }


    private void showMedicalRecordNatureDialog(int show) {
                recordNatureModelArrayList = new ArrayList();
        for(int i=0;i<medicalRecordNatureModelArray.length;i++){
            if(medicalRecordNatureModelArray[i].getRecNatureId()!=6) {
                recordNatureModelArrayList.add(medicalRecordNatureModelArray[i]);
            }
        }
//        recordNatureModelArrayList = new ArrayList<>(Arrays.asList(medicalRecordNatureModelArray));
        GetMedicalRecordNatureModel recNature = new GetMedicalRecordNatureModel();
//        recNature.setRecNatureId(0);
//        recNature.setRecNatureProperty("Select Nature");
//        recordNatureModelArrayList.set(0,recNature);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SearchPatientActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.medical_record_nature_dialog, null);
        builder.setView(convertView);
        SearchView filterRecNature = convertView.findViewById(R.id.filterRecNature);
        InputMethodManager imm = (InputMethodManager) SearchPatientActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(filterRecNature.getWindowToken(), 0);
        filterRecNature.setIconified(true);
        filterRecNature.setIconifiedByDefault(true);
        filterRecNature.setOnQueryTextListener(this);
        filterRecNature.setQueryHint("Search Here");
        recNatureListView = (ListView) convertView.findViewById(R.id.recNatureList);
        recNatureListView.setTextFilterEnabled(true);
        recNatureListAdapter = new RecNatureListAdapter(SearchPatientActivity.this,
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
                tvListNatureId.setText(recNatureListAdapter.getItem(position).getRecNatureProperty()+"");
                dialog.dismiss();
            }
        });
    }

    private void showMedicalRecordNatureDialog(GetMedicalRecordNatureModel[] getMedicalRecordNatureModels) {
        recordNatureModelArrayList = new ArrayList<>(Arrays.asList(getMedicalRecordNatureModels));
        GetMedicalRecordNatureModel recNature = new GetMedicalRecordNatureModel();
        recNature.setRecNatureId(0);
        recNature.setRecNatureProperty("Show all Nature");
        recordNatureModelArrayList.set(0,recNature);

        AlertDialog.Builder builder = new AlertDialog.Builder(SearchPatientActivity.this);
        LayoutInflater inflater = SearchPatientActivity.this.getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.medical_record_nature_dialog, null);
        builder.setView(convertView);
        SearchView filterRecNature = convertView.findViewById(R.id.filterRecNature);
        filterRecNature.setIconified(false);
        filterRecNature.setIconifiedByDefault(false);
        filterRecNature.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        filterRecNature.setQueryHint("Search Here");
        recNatureListView = (ListView) convertView.findViewById(R.id.recNatureList);
        recNatureListView.setTextFilterEnabled(true);
        recNatureListAdapter = new RecNatureListAdapter(SearchPatientActivity.this,
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

    /*    recNatureListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                natureId = recNatureListAdapter.getItem(position).getRecNatureId();
                et_filter.setText(recNatureListAdapter.getItem(position).getRecNatureProperty()+"");
                dialog.dismiss();
            }
        });*/
    }


    private void fileUploadPDF(String takephoto_img) {
        JsonObject obj = new JsonObject();
        obj.addProperty("fileStream", takephoto_img);
        obj.addProperty("type", "document");
        obj.addProperty("extension", "pdf");
        System.out.println("===pdfbase64" + takephoto_img);
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.fileUploadPDF(obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showLoadingDialog();
                postPDFPatMedRecordAPI(s);
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        });
    }

    private void postPDFPatMedRecordAPI(String pdfPathId) {
        String sessionKey = "SES" + Helper.getCurrentTimestamp();

        showLoadingDialog();
        JsonObject obj = new JsonObject();
        obj.addProperty("UserRoleID", userRoleId);
        obj.addProperty("RecNatureId", natureId);
        obj.addProperty("MRNo", mr_no);
        obj.addProperty("date", pdfDate);
        obj.addProperty("datatype", 0);
        obj.addProperty("SubTenantId", sub_tanent_id);
        obj.addProperty("sessionkey", sessionKey);
        JsonArray jsonArrayFilename = new JsonArray();
        JsonObject jsonfile = new JsonObject();
        jsonfile.addProperty("laterDate", "");
        jsonfile.addProperty("description", pdfPathId);
        jsonArrayFilename.add(jsonfile);
        obj.add("fileName", jsonArrayFilename);

        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postPat_Med_Record(obj, new Callback<PostPatMedRecord>() {
            @Override
            public void success(PostPatMedRecord postPatMedRecord, Response response) {
                natureId = postPatMedRecord.getIds().get(0);
                Toast.makeText(getApplicationContext(),"Pdf upload success", Toast.LENGTH_SHORT).show();


                //   postEndUserTrackingAPI(postPatMedRecord.getIds().get(0));
                /*if (postPatMedRecord.getStatus()) {
                    showSuccessDialog("postPatMedRecord.getMsg()");
                } else {
                    showErrorDialog(postPatMedRecord.getMsg());
                }*/
                dismissLoadingDialog();
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                dismissLoadingDialog();
            }
        });
    }


    private void onSelectPdfFromStorage(Intent data) {
        Uri pdfURI = data.getData();
        if (pdfURI.getLastPathSegment().endsWith("pdf")) {

            String docId = pdfURI.getLastPathSegment();
            final String[] split = docId.split(":");
            final String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                docId = split[1];
            }


            final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/", docId);
            Log.d("pdf_path", file.getAbsolutePath());
            try {
                fileTOBase64(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static String getFilePathForN(Context context, Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getFilesDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }



    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();

        return res;
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
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        ArrayList<Integer> searchByList = new ArrayList<>();
        strSearchBy = "";
        if (cb_mrno.isChecked()) {
            searchByList.add(1);
        }
        if (cb_mobile.isChecked()) {
            searchByList.add(2);
        }
        if (cb_patname.isChecked()) {
            searchByList.add(3);
        }
        if (cb_hospitalid.isChecked()) {
            searchByList.add(4);
        }
        strSearchBy = searchByList.toString();
        strSearchBy = strSearchBy.replace("[","");
        strSearchBy = strSearchBy.replace("]","");

        this.query = query;
        if(!TextUtils.isEmpty(query)){
            if(!TextUtils.isEmpty(strSearchBy)){
                patientSearchModel = new SearchPatientModel();
                dataList = new ArrayList<>();
                getPatientSearchDetail(1, this.query);
            }else{
                Toast.makeText(SearchPatientActivity.this,"Please choose search by option",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(SearchPatientActivity.this,"Please Enter Search Patient Key",Toast.LENGTH_LONG).show();
        }

        //getPatientSearchDetail(this.query);
        return true;

    }


    @Override
    public boolean onQueryTextChange(String query) {

        return false;
    }

    @Override
    public void onItemClick(int position, String s) {
        Toast.makeText(this, "Item Clicked", Toast.LENGTH_LONG).show();

    }
}
