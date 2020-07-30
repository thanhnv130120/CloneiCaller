package com.example.cloneicaller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.BlockActivity;
import com.example.cloneicaller.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FragmentBlackDiary extends Fragment {

    RecyclerView rcBlockList;
    FloatingActionButton btnFloatingAddToBlock;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_black_diary,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcBlockList = view.findViewById(R.id.rcBlockList);
        btnFloatingAddToBlock = view.findViewById(R.id.btnFloatingAddToBlock);

        btnFloatingAddToBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), BlockActivity.class));

            }
        });
    }
}
