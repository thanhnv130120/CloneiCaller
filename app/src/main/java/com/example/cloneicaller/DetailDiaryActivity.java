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
        if (intent1.getExtras().get("name")!=null){
            String name = intent1.getExtras().get("name").toString();
            tvNameDetail.setText(name);
        }
        if (intent1.getExtras().get("number")!=null){
            String number = intent1.getExtras().get("number").toString();
            tvPhoneDetail.setText(number);
            tvPhoneDetail.setText(number);
            tvPhoneNumberDetail.setText(number);
        }
        Intent intent2 = getIntent();
        if (intent2.getExtras().get("name1")!=null){
            String name1 = intent2.getExtras().get("name1").toString();
            tvNameDetail.setText(name1);
        }
        if (intent2.getExtras().get("number1")!=null){
            String number1 = intent2.getExtras().get("number1").toString();
            tvPhoneDetail.setText(number1);
            tvPhoneDetail.setText(number1);
            tvPhoneNumberDetail.setText(number1);
        }
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