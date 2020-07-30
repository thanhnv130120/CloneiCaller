package com.example.cloneicaller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailDiaryActivity extends AppCompatActivity {
    private ImageView imgBtnBackDetail, imgEditDetail, imgBlockWhite, imgSaveContact, imgMessageWhite, imgCallWhite, imgType;
    private CircleImageView imgPersonDetail;
    private TextView tvNameDetail, tvDateDetail, tvNumberDetail, tvDuration, tvNumberDetail2, tv_phone_number_detail, tvNetwork, tvNation;
    private LinearLayout layoutSaveContact;

    String name, duration, date, number, type, country, numbertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_diary);

        imgBtnBackDetail = (ImageView) findViewById(R.id.img_btn_back_detail);
        imgPersonDetail = (CircleImageView) findViewById(R.id.img_person_detail);
        tvNameDetail = (TextView) findViewById(R.id.tv_name_detail);
        imgCallWhite = (ImageView) findViewById(R.id.img_call_white);
        imgMessageWhite = (ImageView) findViewById(R.id.img_message_white);
        imgSaveContact = (ImageView) findViewById(R.id.img_save_contact);
        imgBlockWhite = (ImageView) findViewById(R.id.img_block_white);
        tvNation = (TextView) findViewById(R.id.tv_nation);
        tvNetwork = (TextView) findViewById(R.id.tv_network);
        layoutSaveContact = findViewById(R.id.layoutSaveContact);
        tvDateDetail = findViewById(R.id.tvDateDetail);
        tvNumberDetail = findViewById(R.id.tvNumberDetail);
        imgType = findViewById(R.id.imgType);
        tvDuration = findViewById(R.id.tvDuration);
        tvNumberDetail2 = findViewById(R.id.tvNumberDetail2);
        tv_phone_number_detail = findViewById(R.id.tv_phone_number_detail);
        imgEditDetail = findViewById(R.id.imgEditDetail);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        duration = intent.getStringExtra("duration");
        date = intent.getStringExtra("date");
        number = intent.getStringExtra("number");
        type = intent.getStringExtra("type");
        country = intent.getStringExtra("country");
        numbertype = intent.getStringExtra("numbertype");


        //Nếu là số lạ thì hiển thị chi tiết là số không thì hiển thị tên
        if (null == name) {
            tvNameDetail.setText(number);
            layoutSaveContact.setVisibility(View.VISIBLE);
        } else {
            tvNameDetail.setText(name);
        }

        //Lưu số lạ
        imgSaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
                startActivity(intent);
            }
        });

        tvDateDetail.setText(date);

        tvNumberDetail.setText(number);

        if (type.equals("OUTGOING")) {
            imgType.setImageResource(R.drawable.ic_out_call);
        } else if (type.equals("INCOMING")) {
            imgType.setImageResource(R.drawable.ic_in_call);
        } else {
            imgType.setImageResource(R.drawable.ic_miss_call);
        }


        imgCallWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(DetailDiaryActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) DetailDiaryActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                startActivity(intent);
            }
        });

        imgMessageWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", number);
                smsIntent.putExtra("sms_body", "");
                startActivity(smsIntent);
            }
        });

        imgEditDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvDuration.setText(duration);

        tvNumberDetail2.setText(number);

        tv_phone_number_detail.setText(number);

        tvNation.setText(country);

        tvNetwork.setText(numbertype);

        imgBtnBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}