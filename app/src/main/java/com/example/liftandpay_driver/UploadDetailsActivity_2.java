package com.example.liftandpay_driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.liftandpay_driver.fastClass.SingleActionForAllClass;

public class UploadDetailsActivity_2 extends AppCompatActivity {

    SingleActionForAllClass singleActionForAllClass = new SingleActionForAllClass();
    private View headerView;
    private View footerView;
    private LinearLayout headerLayout;
    private LinearLayout footerLayout;
    private ImageButton proceedImgBtn;

    private AppCompatImageView backwardButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_details_2);

        headerLayout = findViewById(R.id.headerView);
        footerLayout = findViewById(R.id.footerView);

        headerView = getLayoutInflater().inflate(R.layout.header_view, null);
        footerView = getLayoutInflater().inflate(R.layout.footer_view, null);
        headerLayout.addView(headerView);
        footerLayout.addView(footerView);

        proceedImgBtn = findViewById(R.id.btn_proceed_id);
        backwardButton = findViewById(R.id.btn_backward_id);


        proceedImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadDetailsActivity_2.this , UploadDetailsActivity_3.class));
                overridePendingTransition(singleActionForAllClass.ENTRY_ANIMATION_FOR_ACTIVITY, singleActionForAllClass.EXIT_ANIMATION_FOR_ACTIVITY);
            }
        });

        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadDetailsActivity_2.this , UploadDetailsActivity_1.class));
                overridePendingTransition(singleActionForAllClass.ENTRY_ANIMATION_FOR_ACTIVITY, singleActionForAllClass.EXIT_ANIMATION_FOR_ACTIVITY);
                finish();
            }
        });
    }
}