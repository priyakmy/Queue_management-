package com.mcura.jaideep.queuemanagement;

import android.support.v7.app.AppCompatActivity;

import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

class PaymentActivity extends AppCompatActivity implements PaymentResultWithDataListener {


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }
}
