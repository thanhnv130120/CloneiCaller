package com.example.cloneicaller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailDiaryActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvNameDetail;
    private TextView tvPhoneDetail;
    private TextView tvPhoneNumberDetail;
    private ImageView imgBtnBack;
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent1 = getIntent();
        String name = intent1.getExtras().get("name").toString();
        String number = intent1.getExtras().get("number").toString();
        tvNameDetail.setText(name);
        tvPhoneDetail.setText(number);
        tvPhoneNumberDetail.setText(number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_diary);
        tvNameDetail = findViewById(R.id.tv_name_detail);
        tvPhoneDetail = findViewById(R.id.tv_phone_detail);
        tvPhoneNumberDetail = findViewById(R.id.tv_phone_number_detail);
        imgBtnBack = findViewById(R.id.img_btn_back_detail);
        imgBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_back_detail:
                finish();
                break;
        }
    }
}