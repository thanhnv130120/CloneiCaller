package com.example.cloneicaller.fragment;

import android.content.Intent;
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
import androidx.fragment.app.Fragment;

import com.example.cloneicaller.R;

import java.util.ArrayList;

public class FragmentCallKeyboard extends Fragment implements View.OnClickListener {
    private TextView edtShowNumb;
    private Button btnNum1, btnNum2, btnNum3,btnNum4, btnNum5, btnNum6, btnNum7, btnNum8, btnNum9, btnNum0, btnSym1, btnSym2;
    private ImageView imgDelete, imgCall;
    private String numberDislayed = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_call_keyboard,container,false);
        edtShowNumb = v.findViewById(R.id.edt_number);
        btnNum0 = v.findViewById(R.id.btn_no0);
        btnNum1 = v.findViewById(R.id.btn_no1);
        btnNum2 = v.findViewById(R.id.btn_no2);
        btnNum3 = v.findViewById(R.id.btn_no3);
        btnNum4 = v.findViewById(R.id.btn_no4);
        btnNum5 = v.findViewById(R.id.btn_no5);
        btnNum6 = v.findViewById(R.id.btn_no6);
        btnNum7 = v.findViewById(R.id.btn_no7);
        btnNum8 = v.findViewById(R.id.btn_no8);
        btnNum9 = v.findViewById(R.id.btn_no9);
        btnSym1 = v.findViewById(R.id.btn_nonum1);
        btnSym2 = v.findViewById(R.id.btn_nonum2);
        imgCall = v.findViewById(R.id.img_call);
        imgDelete = v.findViewById(R.id.img_delete);
        btnNum0.setOnClickListener(this);
        btnNum1.setOnClickListener(this);
        btnNum2.setOnClickListener(this);
        btnNum3.setOnClickListener(this);
        btnNum4.setOnClickListener(this);
        btnNum5.setOnClickListener(this);
        btnNum6.setOnClickListener(this);
        btnNum7.setOnClickListener(this);
        btnNum8.setOnClickListener(this);
        btnNum9.setOnClickListener(this);
        btnSym1.setOnClickListener(this);
        btnSym2.setOnClickListener(this);
        imgCall.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
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
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_no1:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 1";
                }else {
                    numberDislayed = numberDislayed + "1";
                }
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_no2:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 2";
                }else {
                    numberDislayed = numberDislayed + "2";
                }
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_no3:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 3";
                }else {
                    numberDislayed = numberDislayed + "3";
                }
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_no4:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 4";
                }else {
                    numberDislayed = numberDislayed + "4";
                }
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_no5:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 5";
                }else {
                    numberDislayed = numberDislayed + "5";
                }
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_no6:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 6";
                }else {
                    numberDislayed = numberDislayed + "6";
                }
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_no7:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 7";
                }else {
                    numberDislayed = numberDislayed + "7";
                }
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_no8:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 8";
                }else {
                    numberDislayed = numberDislayed + "8";
                }
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_no9:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " 9";
                }else {
                    numberDislayed = numberDislayed + "9";
                }
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_nonum1:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " #";
                }else {
                    numberDislayed = numberDislayed + "#";
                }
                edtShowNumb.setText(numberDislayed);
                break;
            case R.id.btn_nonum2:
                if(checkFormat(numberDislayed)){
                    numberDislayed = numberDislayed + " *";
                }else {
                    numberDislayed = numberDislayed + "*";
                }
                edtShowNumb.setText(numberDislayed);
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
                    edtShowNumb.setText(numberDislayed);
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
