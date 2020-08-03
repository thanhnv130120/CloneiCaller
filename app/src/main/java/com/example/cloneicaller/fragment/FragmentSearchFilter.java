package com.example.cloneicaller.fragment;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.R;
import com.example.cloneicaller.adapter.AdapterItemSearch;
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.item.ItemPerson;

import java.util.ArrayList;

public class FragmentSearchFilter extends Fragment implements View.OnClickListener {
    private LinearLayout lnFilter, lnFilterSearch, lnFilterRecycler;
    private ImageView imgBtnBack;
    private EditText edtSearch;
    private RecyclerView rclFilter;
    private TextView tvDisplayedDiary;
    private TextView tvDisplayedBlocked;
    private ArrayList<ItemPerson>personArrayList = new ArrayList<>();
    private AdapterItemSearch adapterPerson;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_filter,container,false);
        lnFilter = v.findViewById(R.id.lnear_filter);
        lnFilterSearch = v.findViewById(R.id.lnear_filter_search);
        lnFilterRecycler = v.findViewById(R.id.lnear_rcl_filter);
        imgBtnBack = v.findViewById(R.id.img_btn_back);
        edtSearch = v.findViewById(R.id.edt_search);
        lnFilterRecycler.setVisibility(View.GONE);
        lnFilterSearch.setVisibility(View.GONE);
        tvDisplayedDiary = v.findViewById(R.id.tv_displayed_diary);
        tvDisplayedBlocked = v.findViewById(R.id.tv_displayed_block);
        rclFilter = v.findViewById(R.id.rcl_filter);
        personArrayList = Common.resolverArrayList(null,getContext());
        personArrayList = Common.sortList(personArrayList);
        adapterPerson = new AdapterItemSearch(getContext(),personArrayList);
        rclFilter.setAdapter(adapterPerson);
        tvDisplayedDiary.setOnClickListener(this);
        tvDisplayedBlocked.setOnClickListener(this);
        imgBtnBack.setOnClickListener(this);
        edtSearch.setFocusable(true);
                edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }
    private void filter(String toString) {
        ArrayList<ItemPerson>filterList = new ArrayList<>();
        for (ItemPerson person :personArrayList) {
            if (person.getName().trim().toLowerCase().startsWith(toString.trim().toLowerCase())){
                filterList.add(person);
            }
        }
        int count;
        count = filterList.size();
        if(count > 0){
            lnFilterSearch.setVisibility(View.VISIBLE);
            tvDisplayedDiary.setText("DANH BẠ ("+filterList.size()+")");
        }
        adapterPerson.filterList(filterList);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_back:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_view,
                        new FragmentListBlock()).commit();
                break;
            case R.id.tv_displayed_diary:
                lnFilterRecycler.setVisibility(View.VISIBLE);
                tvDisplayedDiary.setTextColor(Color.RED);
                tvDisplayedBlocked.setTextColor(Color.GRAY);
                break;
            case R.id.tv_displayed_block:
                tvDisplayedDiary.setTextColor(Color.GRAY);
                tvDisplayedBlocked.setTextColor(Color.RED);
                break;
        }
    }
}
