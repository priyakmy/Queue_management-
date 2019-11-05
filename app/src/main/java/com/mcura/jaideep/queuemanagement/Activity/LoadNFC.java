package com.mcura.jaideep.queuemanagement.Activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mcura.jaideep.queuemanagement.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LoadNFC extends AppCompatActivity {
    public static int mr_no;
    public static int sub_tenant_id;
    NfcAdapter adapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag mytag;
    Context ctx;
    String nfcData="";
    String hospital_id;
    private static final int REQUEST_DIRECT_REGISTRATION = 2;
    private static final int REQUEST_APPOINTMENT_REGISTRATION = 3;
    private static final int REQUEST_UPDATE_REGISTRATION = 4;
    private static final int REQUEST_QUEUE_REGISTRATION = 5;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.90);
        int screenHeight = (int) (metrics.heightPixels * 0.80);*/
        setContentView(R.layout.activity_load_nfc);
        //getWindow().setLayout(screenWidth, screenHeight);

        ctx = this;
        id = getIntent().getIntExtra("id",0);
        mr_no = getIntent().getIntExtra("mrnoValue", 0);
        sub_tenant_id = getIntent().getIntExtra("subTanentId", 0);
        hospital_id = getIntent().getStringExtra("hospital_id");
        ImageButton load_nfc_ok = (ImageButton) findViewById(R.id.load_nfc_ok);
        ImageButton load_nfc_cancel = (ImageButton) findViewById(R.id.load_nfc_cancel);
        load_nfc_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadNFC.this.finish();
            }
        });
        JSONObject obj = new JSONObject();
        try {
            obj.put("mr_no",mr_no);
            obj.put("sub_tenant_id",sub_tenant_id);
            obj.put("hospital_id",hospital_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nfcData = obj.toString();
        //Toast.makeText(LoadNFC.this, nfcData, Toast.LENGTH_LONG).show();
        load_nfc_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mytag == null) {
                        Toast.makeText(ctx, ctx.getString(R.string.error_detected), Toast.LENGTH_LONG).show();
                    } else {
                        write(nfcData, mytag);
                        Toast.makeText(ctx, ctx.getString(R.string.ok_writing), Toast.LENGTH_LONG).show();
                        Intent intent=new Intent();
                        if(id == REQUEST_APPOINTMENT_REGISTRATION){
                            setResult(REQUEST_APPOINTMENT_REGISTRATION,intent);
                        }else if(id == REQUEST_DIRECT_REGISTRATION){
                            setResult(REQUEST_DIRECT_REGISTRATION,intent);
                        }else if(id == REQUEST_UPDATE_REGISTRATION){
                            setResult(REQUEST_UPDATE_REGISTRATION,intent);
                        }else if(id == REQUEST_QUEUE_REGISTRATION){
                            setResult(REQUEST_QUEUE_REGISTRATION,intent);
                        }
                        LoadNFC.this.finish();
                    }
                } catch (IOException e) {
                    Toast.makeText(ctx, ctx.getString(R.string.error_writing), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (FormatException e) {
                    Toast.makeText(ctx, ctx.getString(R.string.error_writing), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        NfcManager manager = (NfcManager) this.getSystemService(Context.NFC_SERVICE);
        try{
            adapter = NfcAdapter.getDefaultAdapter(this);
        }
        catch (NullPointerException ex){

        }

        //nextAvailableToken();
        if (adapter != null && adapter.isEnabled()) {

            //Yes NFC available
        }else{
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            //Your device doesn't support NFC
        }

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};
    }
    private void write(String text, Tag tag) throws IOException, FormatException {
        /*JSONObject obj = new JSONObject();
        try {
            obj.put("mr_no","477");
            obj.put("sub_tenant_id","203");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        text = obj.toString();*/
        Ndef ndef = null;
        try {
            NdefRecord[] records = {createRecord(text)};
            NdefMessage message = new NdefMessage(records);
            // Get an instance of Ndef for the tag.
            ndef = Ndef.get(tag);


            // Enable I/O
            ndef.connect();
            // Write the message
            ndef.writeNdefMessage(message);
            ndef.close();
            LoadNFC.this.finish();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }


    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            //Toast.makeText(this, this.getString(R.string.ok_detection) + mytag.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        WriteModeOn();
    }

    private void WriteModeOn() {
        writeMode = true;
        try{
            adapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
        }catch (NullPointerException ex){}

    }

    private void WriteModeOff() {
        writeMode = false;
        try{
            adapter.disableForegroundDispatch(this);
        }catch (NullPointerException ex){}

    }


}
