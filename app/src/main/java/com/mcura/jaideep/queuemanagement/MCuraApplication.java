package com.mcura.jaideep.queuemanagement;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.mcura.jaideep.queuemanagement.Model.LocalBillModel;
import com.mcura.jaideep.queuemanagement.SqliteHelper.SqlLiteDbHelper;
import com.mcura.jaideep.queuemanagement.Utils.Constant;
import com.mcura.jaideep.queuemanagement.helper.Helper;
import com.mcura.jaideep.queuemanagement.helper.SharedPreferenceHelper;
import com.mcura.jaideep.queuemanagement.retrofit.MCuraEndPointInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcura.jaideep.queuemanagement.singlton.MySingleTon;
import com.squareup.okhttp.OkHttpClient;

import io.fabric.sdk.android.Fabric;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;


/**
 * Created by mcura on 11/5/2015.
 */
public class MCuraApplication extends Application {
    private static Context mContext;
    private static MCuraApplication instance;
    public RestAdapter.Builder mRestBuilder, mRestBuilderPostPayment, mRestBuilderTest,mRestBuilderConsumer;
    public RestAdapter mRestAdapter, mRestAdapterPostPayment, mRestAdapterTest,mRestAdapterConsumer;
    public MCuraEndPointInterface mCuraEndPoint, mCuraEndPointPostPayment, mCuraEndPointTest,mCuraEndPointConsumer;
    private BroadcastReceiver receiver;
    SqlLiteDbHelper dbHelper;
    Runnable m_Runnable;
    List<LocalBillModel> localBillModelList;
    Handler mHandler;

    public MCuraApplication() {
        super();
        //registerReceiver(receiver, new IntentFilter("NEW_SINGLE_MESSAGE"));
    }

    public static MCuraApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences mSharedPreference = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE);
        Fabric.with(this, new Crashlytics());
        Crashlytics.setUserName(mSharedPreference.getString(Constant.LOGIN_NAME_KEY, "Default"));
        mContext = getApplicationContext();
        instance = this;


        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory()) // This is the important line ;)
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5, TimeUnit.MINUTES);
        okHttpClient.setConnectTimeout(5, TimeUnit.MINUTES);

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                Log.d("encryptKey",Helper.getAESCryptEncodeString());
                request.addHeader("authkeytoken", Helper.getAESCryptEncodeString());
            }
        };

        //Creating of REST adapter
        mRestBuilder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BuildConfig.API_URL)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson));

        mRestBuilderPostPayment = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BuildConfig.API_URL)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(getRequestHeader()));
        // Create an instance of API interface.

        //creating test adpater
        mRestBuilderTest = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BuildConfig.MCURA_API_URL)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(getRequestHeader()));


        mRestAdapter = mRestBuilder.build();
        mRestAdapterTest = mRestBuilderTest.build();
        mRestAdapterPostPayment = mRestBuilderPostPayment.build();

        mCuraEndPoint = mRestAdapter.create(MCuraEndPointInterface.class);
        mCuraEndPointTest = mRestAdapterTest.create(MCuraEndPointInterface.class);
        mCuraEndPointPostPayment = mRestAdapterPostPayment.create(MCuraEndPointInterface.class);


        //Creating of REST adapter for Consumer
        mRestBuilderConsumer = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BuildConfig.CONSUMER_URL)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson));

        // Create an instance of API interface.

        mRestAdapterConsumer = mRestBuilderConsumer.build();

        mCuraEndPointConsumer = mRestAdapterConsumer.create(MCuraEndPointInterface.class);


        SharedPreferenceHelper.initialize(this);

        /*MySingleTon tmp = MySingleTon.getInstance();
        tmp.getSomeThing();*/
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //unregisterReceiver(receiver);
    }

    private OkHttpClient getRequestHeader() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(5, TimeUnit.SECONDS);
        httpClient.setReadTimeout(5, TimeUnit.SECONDS);
        return httpClient;
    }
}
