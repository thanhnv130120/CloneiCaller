package com.example.cloneicaller.fragment;

import android.content.Intent;
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
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.cloneicaller.DetailDiaryActivity;
import com.example.cloneicaller.HomeActivity;
import com.example.cloneicaller.R;
import com.example.cloneicaller.adapter.AdapterItemSearch;
import com.example.cloneicaller.adapter.ItemPersonAdapter;
import com.example.cloneicaller.adapter.PageBlockerAdapter;
import com.example.cloneicaller.adapter.PagerAdapter;
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.item.ItemPerson;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FragmentListBlock extends Fragment implements View.OnClickListener, HomeActivity.DataReceiverListener, AdapterItemSearch.SearchClickListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView imgSearch;
    private LinearLayout lnShow;
    private LinearLayout lnFilterSearch;
    private LinearLayout lnFilterRcl;
    private ImageView imgBack;
    private EditText edtSearch;
    private TextView tvHeader;
    private RecyclerView rclFilter;
    private TextView tvDisplayedDiary;
    private TextView tvDisplayedBlocked;
    private AdapterItemSearch adapterPerson;
    private ArrayList<ItemPerson>persons = new ArrayList<>();

//        @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            setArrayListPerson((ArrayList<ItemPerson>) getArguments().getSerializable("ModelList"));
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((HomeActivity)getActivity()).setListener(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocking,container,false);
        tvHeader = view.findViewById(R.id.tv_header);
        tabLayout = view.findViewById(R.id.tab_block);
        viewPager = view.findViewById(R.id.view);
        PageBlockerAdapter adapter = new PageBlockerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        imgSearch = view.findViewById(R.id.img_btn_search);
        imgBack = view.findViewById(R.id.img_btn_back);
        lnShow = view.findViewById(R.id.lnear_filter);
        lnFilterSearch = view.findViewById(R.id.lnear_filter_search);
        lnFilterRcl = view.findViewById(R.id.lnear_rcl_filter);
        edtSearch = view.findViewById(R.id.edt_search);
        tvDisplayedBlocked = view.findViewById(R.id.tv_displayed_block);
        tvDisplayedDiary = view.findViewById(R.id.tv_displayed_diary);
        rclFilter = view.findViewById(R.id.rcl_filter);
//        if (getArguments()!=null){
//            setArrayListPerson((ArrayList<ItemPerson>) getArguments().getSerializable("ModelList"));
//        }
        persons = Common.resolverArrayList(null,getContext());
        persons = Common.sortList(persons);
        adapterPerson = new AdapterItemSearch(getContext(),persons);
        rclFilter.setAdapter(adapterPerson);
        imgBack.setOnClickListener(this);
        lnShow.setVisibility(View.GONE);
        lnFilterRcl.setVisibility(View.GONE);
        lnFilterSearch.setVisibility(View.GONE);
        imgSearch.setOnClickListener(this);
        tvDisplayedDiary.setOnClickListener(this);
        tvDisplayedBlocked.setOnClickListener(this);
        tvDisplayedDiary.setTextColor(Color.RED);
        tvDisplayedBlocked.setTextColor(Color.GRAY);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int potion = tab.getPosition();
                tvHeader.setText(adapter.getPageTitle(potion));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        ArrayList<ItemPerson>filerList = new ArrayList<>();
        for (ItemPerson person :persons) {
            if (person.getName().toLowerCase().contains(toString.toLowerCase())){
                filerList.add(person);
            }
        }
        int count = filerList.size();
        if(count>0){
            lnFilterSearch.setVisibility(View.VISIBLE);
            tvDisplayedDiary.setText("DANH BẠ ("+filerList.size()+")");
        }
        adapterPerson.filterList(filerList);
    }
    public void setArrayListPerson(ArrayList<ItemPerson>listPerson){
        for (int i = 0; i < listPerson.size(); i++) {
            persons.add(listPerson.get(i));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_search:
                lnShow.setVisibility(View.VISIBLE);
                break;
            case R.id.img_btn_back:
                lnShow.setVisibility(View.GONE);
                lnFilterRcl.setVisibility(View.GONE);
                lnFilterSearch.setVisibility(View.GONE);
                break;
            case R.id.tv_displayed_diary:
                tvDisplayedDiary.setTextColor(Color.RED);
                tvDisplayedBlocked.setTextColor(Color.GRAY);
                lnFilterRcl.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_displayed_block:
                tvDisplayedDiary.setTextColor(Color.GRAY);
                tvDisplayedBlocked.setTextColor(Color.RED);
                lnFilterRcl.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onReceived(int requestCode, int resultCode, Intent data) {

         setArrayListPerson((ArrayList<ItemPerson>) data.getExtras().getSerializable("ModelList"));
    }
    @Override
    public void onSearchClickListener(int position) {
        Intent intent = new Intent(getActivity(), DetailDiaryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name1",persons.get(position).getName());
        bundle.putString("number1",persons.get(position).getNumber());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
