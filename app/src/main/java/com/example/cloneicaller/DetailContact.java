package com.example.cloneicaller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloneicaller.common.AppConstants;
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.databinding.ActivityDetailContactBinding;
import com.example.cloneicaller.databinding.DialogBlockReportBinding;
import com.example.cloneicaller.item.BlockerPersonItem;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Pulse;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailContact extends AppCompatActivity implements AppConstants {
    private String name;
    private String number;
    private String type;
    private boolean typeCheck;
    private int image;
    private int alphabetCheck;
    ActivityDetailContactBinding binding;

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
            binding.imgDetailContactBlock.setImageResource(R.drawable.ic_block_white);
            binding.tvStatus.setText("CHẶN");
        }else {
            binding.imgPersonDetail.setBorderColorResource(R.color.colorRed);
            binding.imgEditDetailContact.setVisibility(View.GONE);
            binding.lnearDetail.setBackgroundResource(R.drawable.bg_data_update);
            binding.lnearSave.setVisibility(View.VISIBLE);
            binding.rltBlockType.setVisibility(View.VISIBLE);
            binding.tvBlockType.setText(getIntent().getStringExtra(INTENT_BLOCK_TYPE));
            binding.imgDetailContactBlock.setImageResource(R.drawable.ic_unblock);
            binding.tvStatus.setText("BỎ CHẶN");
        }
        name = getIntent().getStringExtra(INTENT_NAME);
        number = getIntent().getStringExtra(INTENT_NUMBER);
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
        binding.lnBlock.setOnClickListener(new View.OnClickListener() {
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
                    DialogBlockReportBinding binding;
                    binding = DialogBlockReportBinding.inflate(getLayoutInflater());
                    View bottomView = binding.getRoot();
                    Sprite pulse = new Pulse();
                    binding.spinKit.setIndeterminateDrawable(pulse);
                    binding.spinKit.setVisibility(View.GONE);
                    binding.edtNameEdit.setVisibility(View.GONE);

                    binding.imgCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            type = "";
                        }
                    });

                    binding.imgWhiteAdvertise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.imgWhiteAdvertise.setImageResource(R.drawable.ic_block_setting_advertising);
                            binding.imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular);
                            binding.imgWhiteEstate.setImageResource(R.drawable.ic_white_real_estate);
                            binding.imgWhiteEstate.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteLoan.setImageResource(R.drawable.ic_white_loan_collection);
                            binding.imgWhiteLoan.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteScam.setImageResource(R.drawable.ic_white_scam);
                            binding.imgWhiteScam.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteOther.setImageResource(R.drawable.ic_white_other);
                            binding.imgWhiteOther.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteServiceFinance.setImageResource(R.drawable.ic_white_financial_service);
                            binding.imgWhiteServiceFinance.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_block_setting_advertising;
                            type = "QUẢNG CÁO";
                            binding.edtNameEdit.setVisibility(View.VISIBLE);
                            binding.spinKit.setVisibility(View.GONE);
                            binding.edtNameEdit.setText(name);
                        }
                    });
                    binding.imgWhiteEstate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.imgWhiteEstate.setImageResource(R.drawable.ic_red_real_estate);
                            binding.imgWhiteEstate.setBackgroundResource(R.drawable.background_circular);
                            binding.imgWhiteLoan.setImageResource(R.drawable.ic_white_loan_collection);
                            binding.imgWhiteLoan.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteAdvertise.setImageResource(R.drawable.ic_white_advertising);
                            binding.imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteScam.setImageResource(R.drawable.ic_white_scam);
                            binding.imgWhiteScam.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteOther.setImageResource(R.drawable.ic_white_other);
                            binding.imgWhiteOther.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteServiceFinance.setImageResource(R.drawable.ic_white_financial_service);
                            binding.imgWhiteServiceFinance.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_red_real_estate;
                            type = "BẤT ĐỘNG SẢN";
                            binding.spinKit.setVisibility(View.GONE);
                            binding.edtNameEdit.setVisibility(View.VISIBLE);
                            binding.edtNameEdit.setText(name);
                        }
                    });
                    binding.imgWhiteLoan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.imgWhiteLoan.setImageResource(R.drawable.ic_red_loan_collection);
                            binding.imgWhiteLoan.setBackgroundResource(R.drawable.background_circular);
                            binding.imgWhiteEstate.setImageResource(R.drawable.ic_white_real_estate);
                            binding.imgWhiteEstate.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteScam.setImageResource(R.drawable.ic_white_scam);
                            binding.imgWhiteScam.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteOther.setImageResource(R.drawable.ic_white_other);
                            binding.imgWhiteOther.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteServiceFinance.setImageResource(R.drawable.ic_white_financial_service);
                            binding.imgWhiteServiceFinance.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteAdvertise.setImageResource(R.drawable.ic_white_advertising);
                            binding.imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_red_loan_collection;
                            type = "ĐÒI NỢ";
                            binding.spinKit.setVisibility(View.GONE);
                            binding.edtNameEdit.setVisibility(View.VISIBLE);
                            binding.edtNameEdit.setText(name);
                        }
                    });
                    binding.imgWhiteOther.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.imgWhiteOther.setImageResource(R.drawable.ic_red_other);
                            binding.imgWhiteOther.setBackgroundResource(R.drawable.background_circular);
                            binding.imgWhiteLoan.setImageResource(R.drawable.ic_white_loan_collection);
                            binding.imgWhiteLoan.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteEstate.setImageResource(R.drawable.ic_white_real_estate);
                            binding.imgWhiteEstate.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteScam.setImageResource(R.drawable.ic_white_scam);
                            binding.imgWhiteScam.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteServiceFinance.setImageResource(R.drawable.ic_white_financial_service);
                            binding.imgWhiteServiceFinance.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteAdvertise.setImageResource(R.drawable.ic_white_advertising);
                            binding.imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_red_other;
                            type = "KHÁC";
                            binding.spinKit.setVisibility(View.GONE);
                            binding.edtNameEdit.setVisibility(View.VISIBLE);
                            binding.edtNameEdit.setText(name);
                        }
                    });
                    binding.imgWhiteServiceFinance.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.imgWhiteServiceFinance.setImageResource(R.drawable.ic_red_financial_service);
                            binding.imgWhiteServiceFinance.setBackgroundResource(R.drawable.background_circular);
                            binding.imgWhiteOther.setImageResource(R.drawable.ic_white_other);
                            binding.imgWhiteOther.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteLoan.setImageResource(R.drawable.ic_white_loan_collection);
                            binding.imgWhiteLoan.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteEstate.setImageResource(R.drawable.ic_white_real_estate);
                            binding.imgWhiteEstate.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteScam.setImageResource(R.drawable.ic_white_scam);
                            binding.imgWhiteScam.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteAdvertise.setImageResource(R.drawable.ic_white_advertising);
                            binding.imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_red_financial_service;
                            type = "CHO VAY";
                            binding.spinKit.setVisibility(View.GONE);
                            binding.edtNameEdit.setVisibility(View.VISIBLE);
                            binding.edtNameEdit.setText(name);
                        }
                    });
                    binding.imgWhiteScam.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.imgWhiteScam.setImageResource(R.drawable.ic_red_scam);
                            binding.imgWhiteScam.setBackgroundResource(R.drawable.background_circular);
                            binding.imgWhiteServiceFinance.setImageResource(R.drawable.ic_white_financial_service);
                            binding.imgWhiteServiceFinance.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteOther.setImageResource(R.drawable.ic_white_other);
                            binding.imgWhiteOther.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteLoan.setImageResource(R.drawable.ic_white_loan_collection);
                            binding.imgWhiteLoan.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteEstate.setImageResource(R.drawable.ic_white_real_estate);
                            binding.imgWhiteEstate.setBackgroundResource(R.drawable.background_circular_red);
                            binding.imgWhiteAdvertise.setImageResource(R.drawable.ic_white_advertising);
                            binding.imgWhiteAdvertise.setBackgroundResource(R.drawable.background_circular_red);
                            image = R.drawable.ic_red_scam;
                            type = "LỪA ĐẢO";
                            binding.spinKit.setVisibility(View.GONE);
                            binding.edtNameEdit.setVisibility(View.VISIBLE);
                            binding.edtNameEdit.setText(name);
                        }
                    });
                    binding.tvNameReport.setText(name);
                    binding.tvPhoneReport.setText(number);
                    binding.btnReport.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (type.equals("")||type.equals(null)) {
                                binding.spinKit.setVisibility(View.VISIBLE);
                                Toast.makeText(v.getContext(),"Please select kind of spam", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                binding.spinKit.setVisibility(View.GONE);
                                if (binding.edtNameEdit.getText() != null) {
                                    String rename = binding.edtNameEdit.getText().toString();
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
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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