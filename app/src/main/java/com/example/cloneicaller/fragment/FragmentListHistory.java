package com.example.cloneicaller.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import com.example.cloneicaller.ListItem;
import com.example.cloneicaller.Models.Contact;
import com.example.cloneicaller.Models.DateItem;
import com.example.cloneicaller.Models.GeneralItem;
import com.example.cloneicaller.PermissionActivity;
import com.example.cloneicaller.R;
import com.example.cloneicaller.Room.BlockItemDatabase;
import com.example.cloneicaller.SwipeHelper;
import com.example.cloneicaller.adapter.ItemPersonAdapter;
import com.example.cloneicaller.adapter.ListHistoryAdapter;
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.custom.ChoosePlanViewSwipeDiary;
import com.example.cloneicaller.custom.ChoosePlanViewSwipeHistory;
import com.example.cloneicaller.databinding.DialogBlockReportBinding;
import com.example.cloneicaller.databinding.DialogCompleteUnblockedBinding;
import com.example.cloneicaller.databinding.FragmentHistoryBinding;
import com.example.cloneicaller.item.BlockerPersonItem;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Pulse;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FragmentListHistory extends Fragment {
    private ChoosePlanViewSwipeHistory choosePlanViewSwipeHistory;
    private List<Contact> contactList = new ArrayList<>();
    private List<ListItem> consolidatedList = new ArrayList<>();
    private ListHistoryAdapter listHistoryAdapter;
    private FragmentHistoryBinding binding;
    private int image;
    private String name;
    private String number;
    private String type="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactList = Common.getCallDetails(getContext());

        HashMap<String, List<Contact>> groupedHashMap = Common.groupDataIntoHashMap(contactList);


        for (String date : groupedHashMap.keySet()) {
            DateItem dateItem = new DateItem();
            dateItem.setDate(date);
            consolidatedList.add(dateItem);


            for (Contact contact : groupedHashMap.get(date)) {
                GeneralItem generalItem = new GeneralItem();
                generalItem.setContact(contact);
                consolidatedList.add(generalItem);
            }
        }

        if (contactList.size() == 0) {
            binding.tvNoneDataHistory.setText(getString(R.string.none_data));
            binding.tvNoneDataHistory.setVisibility(View.VISIBLE);
            binding.rcListHistory.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());

        binding.rcListHistory.setLayoutManager(linearLayoutManager);

        listHistoryAdapter = new ListHistoryAdapter(getContext(), consolidatedList);
        binding.rcListHistory.setAdapter(listHistoryAdapter);

//        SwipeHelper swipeHelper = new SwipeHelper(getContext(), binding.rcListHistory) {
//            @Override
//            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
//                underlayButtons.add(new SwipeHelper.UnderlayButton("CHẶN", 1, Color.parseColor("#A90939"), new SwipeHelper.UnderlayButtonClickListener() {
//                    @Override
//                    public void onClick(int pos) {
//                        Toast.makeText(getContext(), "ABC", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                ));
//            }
//        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rcListHistory);
    }

    GeneralItem item = null;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    item = (GeneralItem) consolidatedList.get(position);
                    Contact contact = item.getContact();
                    name = contact.getName();
                    number = contact.getNumber();
                    listHistoryAdapter.notifyItemRemoved(position);
                    if (!Common.checkBlock(getContext(),number)) {
                        Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
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
                                if (type.equals("") || type.equals(null)) {
                                    binding.spinKit.setVisibility(View.VISIBLE);
                                    Toast.makeText(v.getContext(), "Please select kind of spam", Toast.LENGTH_LONG).show();
                                    return;
                                } else {
                                    binding.spinKit.setVisibility(View.GONE);
                                    if (binding.edtNameEdit.getText() != null) {
                                        String rename = binding.edtNameEdit.getText().toString();
                                        name = rename;
                                    }
                                    BlockerPersonItem blockerPersonItem = new BlockerPersonItem(name, type, number, image, -1);
                                    Common.insertAll(blockerPersonItem, v.getContext());
                                    dialog.dismiss();
                                    Dialog dialog1 = new Dialog(getContext());
                                    DialogCompleteUnblockedBinding binding;
                                    binding = DialogCompleteUnblockedBinding.inflate(getLayoutInflater());
                                    View view1 = binding.getRoot();
                                    new Thread() {
                                        public void run() {
                                            try {
                                                Thread.sleep(2000);
                                            } catch (Exception e) {
                                                Log.e("tag", e.getMessage());
                                            }
                                            // dismiss the progress dialog
                                            dialog1.dismiss();
                                        }
                                    }.start();
                                    dialog1.setContentView(view1);
                                    dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    dialog1.show();
                                }
                            }
                        });
                        dialog.setContentView(bottomView);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        break;
                    }
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed))
                    .addSwipeLeftActionIcon(R.drawable.block_white2)
                    .create()
                    .decorate();
            View itemView = viewHolder.itemView;
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}


