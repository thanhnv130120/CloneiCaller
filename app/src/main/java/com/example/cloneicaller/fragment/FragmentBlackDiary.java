package com.example.cloneicaller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.BlockActivity;
import com.example.cloneicaller.HomeActivity;
import com.example.cloneicaller.MainActivity;
import com.example.cloneicaller.R;
import com.example.cloneicaller.databinding.FragmentBlackDiaryBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FragmentBlackDiary extends Fragment {

    FragmentBlackDiaryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentBlackDiaryBinding.inflate(inflater, container, false);

        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnFloatingAddToBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), BlockActivity.class));
            }
        });

        binding.btnUpdateListBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
