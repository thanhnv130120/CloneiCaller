package com.example.cloneicaller.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.cloneicaller.LoginActivity;
import com.example.cloneicaller.R;

public class FragmentSetting extends Fragment {
    private CardView btnIntroduce;
    private CardView btnTermsSec;
    private CardView btnTermsUse;
    private CardView btnQuestion;
    private CardView btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        btnIntroduce = (CardView) view.findViewById(R.id.btnIntroduce);
        btnTermsSec = (CardView) view.findViewById(R.id.btnTermsSec);
        btnTermsUse = (CardView) view.findViewById(R.id.btnTermsUse);
        btnQuestion = (CardView) view.findViewById(R.id.btnQuestion);
        btnLogout = (CardView) view.findViewById(R.id.btnLogout);

        btnIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "âsdasda", Toast.LENGTH_SHORT).show();
            }
        });

        btnTermsSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnTermsUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("toke", Context.MODE_PRIVATE);
                SharedPreferences.Editor spE = sharedPreferences.edit();
                spE.clear();
                spE.apply();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return view;
    }

}
