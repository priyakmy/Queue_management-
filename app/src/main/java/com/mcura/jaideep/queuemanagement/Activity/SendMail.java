package com.mcura.jaideep.queuemanagement.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mcura.jaideep.queuemanagement.R;


public class SendMail extends AppCompatActivity implements View.OnClickListener {
private EditText etTo, etSubject, etMessege;
    private ImageButton iBtnSend,iBtnCancel;
    String email;
    private ImageView mailClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.90);
        int screenHeight = (int) (metrics.heightPixels * 0.80);
        setContentView(R.layout.activity_send_mail);
        //getSupportActionBar().hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        getWindow().setLayout(screenWidth, screenHeight);
        email=getIntent().getStringExtra("emailTo");
        etTo= (EditText) findViewById(R.id.et_to);
        etTo.setText(email.toString());
        //etTo.setEnabled(false);
        etSubject= (EditText) findViewById(R.id.et_subject);
        etMessege= (EditText) findViewById(R.id.et_messege);
        iBtnSend= (ImageButton) findViewById(R.id.mail_send);
        iBtnCancel= (ImageButton) findViewById(R.id.mail_cancel);
        mailClose= (ImageView) findViewById(R.id.mail_close);
        iBtnSend.setOnClickListener(this);
        mailClose.setOnClickListener(this);
        iBtnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.mail_send:
                String to = etTo.getText().toString();
                String subject = etSubject.getText().toString();
                String message = etMessege.getText().toString();
                if(to.length()>0 && isValidEmail(to)){
                    if(subject.length()>0){
                        if(message.length()>0){
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.setData(Uri.parse("mailto:"));
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                            email.putExtra(Intent.EXTRA_SUBJECT, subject);
                            email.putExtra(Intent.EXTRA_TEXT, message);
                            email.setType("text/plain");

                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                            finish();
                        }
                        else{
                            etMessege.setError("Messege box cannot be blank");
                        }
                    }
                    else{
                        etSubject.setError("Subject box cannot be blank");
                    }
                }
                else{
                    etTo.setError("recipient box cannot be blank");
                }

                break;
            case R.id.mail_cancel:
                finish();
                break;
            case R.id.mail_close:
                finish();
                break;
        }

    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
