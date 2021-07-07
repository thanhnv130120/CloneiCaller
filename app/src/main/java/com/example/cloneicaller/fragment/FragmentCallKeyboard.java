package com.example.cloneicaller.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.cloneicaller.R;
import com.example.cloneicaller.databinding.FragmentCallKeyboardBinding;

import java.util.ArrayList;

public class FragmentCallKeyboard extends Fragment implements View.OnClickListener {

    private String numberDislayed = "";

    FragmentCallKeyboardBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCallKeyboardBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }

        binding.btnNo0.setOnClickListener(this);
        binding.btnNo1.setOnClickListener(this);
        binding.btnNo2.setOnClickListener(this);
        binding.btnNo3.setOnClickListener(this);
        binding.btnNo4.setOnClickListener(this);
        binding.btnNo5.setOnClickListener(this);
        binding.btnNo6.setOnClickListener(this);
        binding.btnNo7.setOnClickListener(this);
        binding.btnNo8.setOnClickListener(this);
        binding.btnNo9.setOnClickListener(this);
        binding.btnNonum1.setOnClickListener(this);
        binding.btnNonum2.setOnClickListener(this);
        binding.imgCall.setOnClickListener(this);
        binding.imgDelete.setOnClickListener(this);
        return v;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_no0:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 0";
                }else {
                    numberDislayed = numberDislayed + "0";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_no1:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 1";
                }else {
                    numberDislayed = numberDislayed + "1";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_no2:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 2";
                }else {
                    numberDislayed = numberDislayed + "2";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_no3:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 3";
                }else {
                    numberDislayed = numberDislayed + "3";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_no4:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 4";
                }else {
                    numberDislayed = numberDislayed + "4";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_no5:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 5";
                }else {
                    numberDislayed = numberDislayed + "5";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_no6:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 6";
                }else {
                    numberDislayed = numberDislayed + "6";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_no7:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 7";
                }else {
                    numberDislayed = numberDislayed + "7";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_no8:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 8";
                }else {
                    numberDislayed = numberDislayed + "8";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_no9:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 9";
                }else {
                    numberDislayed = numberDislayed + "9";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_nonum1:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " #";
                }else {
                    numberDislayed = numberDislayed + "#";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.btn_nonum2:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " *";
                }else {
                    numberDislayed = numberDislayed + "*";
                }
                binding.edtNumber.setText(numberDislayed);
                break;
            case R.id.img_delete:
                if(numberDislayed.length()>=1) {
                    char text[] = numberDislayed.toCharArray();
                    int n = text.length;
                    int i = n - 1;
                    if (numberDislayed.charAt(i) != ' ') {
                        numberDislayed = removeCharAtString(numberDislayed, i);
                    } else {
                        numberDislayed = removeCharAtString(numberDislayed, i);
                        numberDislayed = removeCharAtString(numberDislayed, i - 1);
                    }
                    binding.edtNumber.setText(numberDislayed);
                }
                break;
            case R.id.img_call:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+numberDislayed.trim()));
                startActivity(intent);
                break;
        }
    }
    public static String removeCharAtString(String text, int p){
        if(text.length()<1){
            return "";
        }
        return text.substring(0,p)+ text.substring(p+1);
    }
    private boolean checkFormat(String text){
        boolean check = false;
        char textString[] = text.toCharArray();
        if (text.length()==3||text.length()==7||text.length()==10){
            check = true;
        }
        return check;
    }

}
