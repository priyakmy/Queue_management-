package com.mcura.jaideep.queuemanagement.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;


import com.mcura.jaideep.queuemanagement.Adapter.SearchPatientAdapter_v1;
import com.mcura.jaideep.queuemanagement.Adapter.SearchPatientLoadNFCAdapter;
import com.mcura.jaideep.queuemanagement.MCuraApplication;
import com.mcura.jaideep.queuemanagement.Model.Datum;
import com.mcura.jaideep.queuemanagement.Model.PatientSearchModel;
import com.mcura.jaideep.queuemanagement.Model.SearchHospital;
import com.mcura.jaideep.queuemanagement.Model.SearchPatientModel;
import com.mcura.jaideep.queuemanagement.R;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.view.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchPatientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener, SearchView.OnQueryTextListener {
    private Spinner spHospitalName;
    private ProgressDialog progressDialog;
    String query = "", lastQuery = "";
    public MCuraApplication mCuraApplication;
    SearchHospital searchHospitalArray[];
    HashMap<Integer, String> hmHospitalName = new HashMap<>();
    List<String> hospitalNameList;
    ArrayAdapter hospitalAdapter;
    int userRoleId;
    private SearchView selectPatientSearchview;
    public PatientSearchModel[] patientSearchModelsArray;
    private ListView patientListview;
    int hospitalSubtanentId;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SharedPreferences mSharedPreference;
    int sub_tanent_id;
    SearchPatientModel patientSearchModel;
    int firstIndex = 2;
    List<Datum> dataList;
    private CheckBox cb_mrno, cb_mobile, cb_patname, cb_hospitalid;
    private String strSearchBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);
        mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        String subtanentImagePath = mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "default");
        sub_tanent_id = mSharedPreference.getInt(Constant.SUB_TANENT_ID_KEY, 0);
        userRoleId = mSharedPreference.getInt(Constant.USER_ROLE_ID, 0);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView hospital_logo = (ImageView) mToolbar.findViewById(R.id.hospital_logo);
        ImageView add_patient = (ImageView) mToolbar.findViewById(R.id.add_patient);
        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchPatientActivity.this, AddNewAppointment.class).putExtra("appNatureId", 1).putExtra("updateStatus", "add_new_patient").putExtra("registerStatus", "search"));
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

    /*@Override
    protected void onRestart() {
        super.onRestart();
        getHospitalFromAPI();
    }*/

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
                    mAdapter = new SearchPatientAdapter_v1(SearchPatientActivity.this, patientSearchModel, userRoleId, hospitalSubtanentId);
                    mRecyclerView.setAdapter(mAdapter);
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
}
