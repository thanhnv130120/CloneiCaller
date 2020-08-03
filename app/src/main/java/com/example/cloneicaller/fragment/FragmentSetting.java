package com.example.cloneicaller.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.cloneicaller.IntroduceActivity;
import com.example.cloneicaller.LoginActivity;
import com.example.cloneicaller.QuestionActivity;
import com.example.cloneicaller.R;
import com.example.cloneicaller.databinding.FragmentSettingBinding;

public class FragmentSetting extends Fragment {
    private CardView btnIntroduce;
    private CardView btnTermsSec;
    private CardView btnTermsUse;
    private CardView btnQuestion;
    private CardView btnLogout;

    FragmentSettingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), IntroduceActivity.class));
            }
        });

        binding.btnTermsSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btnTermsUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), QuestionActivity.class));
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
                SharedPreferences.Editor spE = sharedPreferences.edit();
                spE.clear();
                spE.apply();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return view;
    }

}
