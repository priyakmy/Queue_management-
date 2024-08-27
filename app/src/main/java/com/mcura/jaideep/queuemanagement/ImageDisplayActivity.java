package com.mcura.jaideep.queuemanagement;// ImageDisplayActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        ImageView imageView = findViewById(R.id.imageView);
        TextView close = findViewById(R.id.close);
        Intent intent = getIntent();
        if (intent != null) {
            String imagePath = intent.getStringExtra("imagePath");
            Glide.with(this).load(imagePath).into(imageView);
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
