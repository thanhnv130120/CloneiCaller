package com.example.cloneicaller.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.cloneicaller.R;
import com.example.cloneicaller.adapter.PageBlockerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FragmentListBlock extends Fragment implements View.OnClickListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView imgSearch;
    private LinearLayout lnShow;
    private ImageView imgBack;
    private EditText edtSearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocking,container,false);
        tabLayout = view.findViewById(R.id.tab_block);
        viewPager = view.findViewById(R.id.view);
        viewPager.setAdapter(new PageBlockerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        imgSearch = view.findViewById(R.id.img_btn_search);
        imgBack = view.findViewById(R.id.img_btn_back);
        lnShow = view.findViewById(R.id.lnear_filter);
        edtSearch = view.findViewById(R.id.edt_search);
        imgBack.setOnClickListener(this);
        lnShow.setVisibility(View.GONE);
        imgSearch.setOnClickListener(this);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        return view;
    }

    private void filter(String toString) {
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_search:
                lnShow.setVisibility(View.VISIBLE);
                break;
            case R.id.img_btn_back:
                lnShow.setVisibility(View.GONE);
                break;
        }
    }
}
