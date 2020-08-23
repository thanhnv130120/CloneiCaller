package com.example.cloneicaller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloneicaller.Room.BlockItemDatabase;
import com.example.cloneicaller.common.AppConstants;
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.databinding.ActivityDetailContactBinding;
import com.example.cloneicaller.item.BlockerPersonItem;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Pulse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailContact extends AppCompatActivity implements AppConstants{
    private String name;
    private String number;
    private String type;
    private boolean typeCheck;
    private int image;
    private int alphabetCheck;
    ActivityDetailContactBinding binding;
    private TextView tvName;
    private TextView tvNumber;
    private CircleImageView imgWhiteOther;
    private CircleImageView imgWhiteAdvertise;
    private CircleImageView imgWhiteScam;
    private CircleImageView imgWhiteService;
    private CircleImageView imgWhiteLoan;
    private CircleImageView imgWhiteEstate;
    private EditText edtRenameBlock;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailContactBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        name = intent.getStringExtra(INTENT_NAME);
        number = intent.getStringExtra(INTENT_NUMBER);
        typeCheck = intent.getBooleanExtra(INTENT_BLOCK,false);
        type = intent.getStringExtra(INTENT_BLOCK_TYPE);
        image = intent.getIntExtra(INTENT_IMAGE, 1);
        alphabetCheck = intent.getIntExtra(INTENT_TYPE_ARRANGE,-1);
        if (typeCheck ==false ) {
            binding.imgPersonDetail.setBorderColorResource(R.color.colorPurpleBG);
            binding.lnearDetail.setBackgroundResource(R.drawable.bg_update_data_success);
            binding.lnearSave.setVisibility(View.GONE);
            binding.imgEditDetailContact.setVisibility(View.VISIBLE);
            binding.rltBlockType.setVisibility(View.GONE);
            binding.tvStatus.setText("CHẶN");
        }else {
            binding.imgPersonDetail.setBorderColorResource(R.color.colorRed);
            binding.imgEditDetailContact.setVisibility(View.GONE);
            binding.lnearDetail.setBackgroundResource(R.drawable.bg_data_update);
            binding.lnearSave.setVisibility(View.VISIBLE);
            binding.rltBlockType.setVisibility(View.VISIBLE);
            binding.tvBlockType.setText(getIntent().getStringExtra(INTENT_BLOCK_TYPE));
            binding.tvStatus.setText("BỎ CHẶN");
        }

        binding.tvNameDetailContact.setText(name);
        binding.tvNumberDetailContact.setText(number);
        binding.tvDetailContactPhoneNum.setText(number);

        //Gọi điện
        binding.imgDetailContactCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(DetailContact.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) DetailContact.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                startActivity(intent);
            }
        });

        //Gửi tin nhắn
        binding.imgDetailContactMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", number);
                smsIntent.putExtra("sms_body", "");
                startActivity(smsIntent);
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //setResult(Activity.RESULT_CANCELED);
            }
        });
        binding.imgDetailContactBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                if (intent.getBooleanExtra(INTENT_BLOCK,false)==false ){
//                    BottomSheetDialog dialog = new BottomSheetDialog(
//                            DetailContact.this,R.style.BottomSheetDialogTheme
//                    );
//                    View bottomView = LayoutInflater.from(getApplicationContext()).inflate(
//                            R.layout.dialog_block_report,
//                            (LinearLayout)findViewById(R.id.bottom_sheet_container)
//                    );
                    Dialog dialog = new Dialog(v.getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
//                    View bottomView = dialog.setContentView(R.layout.dialog_block_report);
                    View bottomView = LayoutInflater.from(getApplicationContext()).inflate(
                            R.layout.dialog_block_report,
                            (LinearLayout)findViewById(R.id.bottom_sheet_container)
                    );
                    tvName = bottomView.findViewById(R.id.tv_name_report);
                    progressBar = (ProgressBar)bottomView.findViewById(R.id.spin_kit);
                    Sprite pulse = new Pulse();
                    progressBar.setIndeterminateDrawable(pulse);
                    progressBar.setVisibility(View.GONE);
                    tvNumber = bottomView.findViewById(R.id.tv_phone_report);
                    bottomView.findViewById(R.id.tv_nation_report);
                    bottomView.findViewById(R.id.tv_network_report);
                    imgWhiteAdvertise = bottomView.findViewById(R.id.img_white_advertise);
                    imgWhiteEstate = bottomView.findViewById(R.id.img_white_estate);
                    imgWhiteLoan = bottomView.findViewById(R.id.img_white_loan);
                    imgWhiteOther = bottomView.findViewById(R.id.img_white_other);
                    imgWhiteService = bottomView.findViewById(R.id.img_white_service_finance);
                    imgWhiteScam = bottomView.findViewById(R.id.img_white_scam);
                    edtRenameBlock = bottomView.findViewById(R.id.edt_name_edit);
                    edtRenameBlock.setVisibility(View.GONE);

                    bottomView.findViewById(R.id.img_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            type = "";
                        }
                    });

                    imgWhiteAdvertise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imgWhiteAdvertise.setImageResource(R.drawable.ic_block_setting_advertising);
                            imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular);
                            imgWhiteEstate.setImageResource(R.drawable.ic_white_real_estate);
                            imgWhiteEstate.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteLoan.setImageResource(R.drawable.ic_white_loan_collection);
                            imgWhiteLoan.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteScam.setImageResource(R.drawable.ic_white_scam);
                            imgWhiteScam.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteOther.setImageResource(R.drawable.ic_white_other);
                            imgWhiteOther.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteService.setImageResource(R.drawable.ic_white_financial_service);
                            imgWhiteService.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_block_setting_advertising;
                            type = "QUẢNG CÁO";
                            edtRenameBlock.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            edtRenameBlock.setText(name);
                        }
                    });
                    imgWhiteEstate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imgWhiteEstate.setImageResource(R.drawable.ic_red_real_estate);
                            imgWhiteEstate.setBackgroundResource(R.drawable.background_circular);
                            imgWhiteLoan.setImageResource(R.drawable.ic_white_loan_collection);
                            imgWhiteLoan.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteAdvertise.setImageResource(R.drawable.ic_white_advertising);
                            imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteScam.setImageResource(R.drawable.ic_white_scam);
                            imgWhiteScam.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteOther.setImageResource(R.drawable.ic_white_other);
                            imgWhiteOther.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteService.setImageResource(R.drawable.ic_white_financial_service);
                            imgWhiteService.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_red_real_estate;
                            type = "BẤT ĐỘNG SẢN";
                            progressBar.setVisibility(View.GONE);
                            edtRenameBlock.setVisibility(View.VISIBLE);
                            edtRenameBlock.setText(name);
                        }
                    });
                    imgWhiteLoan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imgWhiteLoan.setImageResource(R.drawable.ic_red_loan_collection);
                            imgWhiteLoan.setBackgroundResource(R.drawable.background_circular);
                            imgWhiteEstate.setImageResource(R.drawable.ic_white_real_estate);
                            imgWhiteEstate.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteScam.setImageResource(R.drawable.ic_white_scam);
                            imgWhiteScam.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteOther.setImageResource(R.drawable.ic_white_other);
                            imgWhiteOther.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteService.setImageResource(R.drawable.ic_white_financial_service);
                            imgWhiteService.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteAdvertise.setImageResource(R.drawable.ic_white_advertising);
                            imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_red_loan_collection;
                            type = "ĐÒI NỢ";
                            progressBar.setVisibility(View.GONE);
                            edtRenameBlock.setVisibility(View.VISIBLE);
                            edtRenameBlock.setText(name);
                        }
                    });
                    imgWhiteOther.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imgWhiteOther.setImageResource(R.drawable.ic_red_other);
                            imgWhiteOther.setBackgroundResource(R.drawable.background_circular);
                            imgWhiteLoan.setImageResource(R.drawable.ic_white_loan_collection);
                            imgWhiteLoan.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteEstate.setImageResource(R.drawable.ic_white_real_estate);
                            imgWhiteEstate.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteScam.setImageResource(R.drawable.ic_white_scam);
                            imgWhiteScam.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteService.setImageResource(R.drawable.ic_white_financial_service);
                            imgWhiteService.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteAdvertise.setImageResource(R.drawable.ic_white_advertising);
                            imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_red_other;
                            type = "KHÁC";
                            progressBar.setVisibility(View.GONE);
                            edtRenameBlock.setVisibility(View.VISIBLE);
                            edtRenameBlock.setText(name);
                        }
                    });
                    imgWhiteService.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imgWhiteService.setImageResource(R.drawable.ic_red_financial_service);
                            imgWhiteService.setBackgroundResource(R.drawable.background_circular);
                            imgWhiteOther.setImageResource(R.drawable.ic_white_other);
                            imgWhiteOther.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteLoan.setImageResource(R.drawable.ic_white_loan_collection);
                            imgWhiteLoan.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteEstate.setImageResource(R.drawable.ic_white_real_estate);
                            imgWhiteEstate.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteScam.setImageResource(R.drawable.ic_white_scam);
                            imgWhiteScam.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteAdvertise.setImageResource(R.drawable.ic_white_advertising);
                            imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_red_financial_service;
                            type = "CHO VAY";
                            progressBar.setVisibility(View.GONE);
                            edtRenameBlock.setVisibility(View.VISIBLE);
                            edtRenameBlock.setText(name);
                        }
                    });
                    imgWhiteScam.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imgWhiteScam.setImageResource(R.drawable.ic_red_scam);
                            imgWhiteScam.setBackgroundResource(R.drawable.background_circular);
                            imgWhiteService.setImageResource(R.drawable.ic_white_financial_service);
                            imgWhiteService.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteOther.setImageResource(R.drawable.ic_white_other);
                            imgWhiteOther.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteLoan.setImageResource(R.drawable.ic_white_loan_collection);
                            imgWhiteLoan.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteEstate.setImageResource(R.drawable.ic_white_real_estate);
                            imgWhiteEstate.setBackgroundResource(R.drawable.background_circular_red);
                            imgWhiteAdvertise.setImageResource(R.drawable.ic_white_advertising);
                            imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_red_scam;
                            type = "LỪA ĐẢO";
                            progressBar.setVisibility(View.GONE);
                            edtRenameBlock.setVisibility(View.VISIBLE);
                            edtRenameBlock.setText(name);
                        }
                    });
                    tvName.setText(name);
                    tvNumber.setText(number);
                    bottomView.findViewById(R.id.btn_report).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (type.equals("")) {
                                progressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(v.getContext(),"Please select kind of spam",Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                progressBar.setVisibility(View.GONE);
                                if (edtRenameBlock.getText() != null) {
                                    String rename = edtRenameBlock.getText().toString();
                                    name = rename;
                                }
                                BlockerPersonItem blockerPersonItem = new BlockerPersonItem(name, type, number, image, alphabetCheck);
                                Common.insertAll(blockerPersonItem, getApplicationContext());
                                finish();
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.setContentView(bottomView);
                    dialog.show();
                }else {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
        binding.imgDetailContactSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, "");
                startActivity(intent);
                finish();
            }
        });
    }

}