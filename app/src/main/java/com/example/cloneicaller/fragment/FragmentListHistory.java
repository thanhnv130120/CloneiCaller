package com.example.cloneicaller.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.cloneicaller.ListItem;
import com.example.cloneicaller.Models.Contact;
import com.example.cloneicaller.Models.DateItem;
import com.example.cloneicaller.Models.GeneralItem;
import com.example.cloneicaller.PermissionActivity;
import com.example.cloneicaller.R;
import com.example.cloneicaller.adapter.ListHistoryAdapter;
import com.example.cloneicaller.databinding.FragmentHistoryBinding;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FragmentListHistory extends Fragment {

    private List<Contact> contactList = new ArrayList<>();
    List<ListItem> consolidatedList = new ArrayList<>();

    FragmentHistoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        contactList = getCallDetails();

        HashMap<String, List<Contact>> groupedHashMap = groupDataIntoHashMap(contactList);


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

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());

        binding.rcListHistory.setLayoutManager(linearLayoutManager);

        ListHistoryAdapter listHistoryAdapter = new ListHistoryAdapter(getContext(), consolidatedList);
        binding.rcListHistory.setAdapter(listHistoryAdapter);

        return view;
    }

    private List<Contact> getCallDetails() {

        List<Contact> contactList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }
        Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int country = cursor.getColumnIndex(CallLog.Calls.COUNTRY_ISO);
        int network = cursor.getColumnIndex(CallLog.Calls.CACHED_NUMBER_TYPE);


        cursor.moveToFirst();
        while (cursor.moveToNext()) {

            String phoneNum = cursor.getString(number);
            String dur = cursor.getString(duration);
            String callDate = cursor.getString(date);
            String nameCon = cursor.getString(name);
            String callType = cursor.getString(type);
            String countryiso = cursor.getString(country);
            String networkname = cursor.getString(network);
            String dir = null;

            int dirCode = Integer.parseInt(callType);

            switch (dirCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }

            long seconds = Long.parseLong(callDate);
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String dateString = formatter.format(new Date(seconds));

            SimpleDateFormat format = new SimpleDateFormat("hh:mm");
            String time = format.format(new Time(seconds));

            contactList.add(new Contact(phoneNum, time, dateString, nameCon, dir, countryiso, networkname));

        }
        return contactList;
    }

    private HashMap<String, List<Contact>> groupDataIntoHashMap(List<Contact> contactList) {

        HashMap<String, List<Contact>> groupedHashMap = new HashMap<>();

        for (Contact contact : contactList) {

            String hashMapKey = contact.getDate();

            if (groupedHashMap.containsKey(hashMapKey)) {
                groupedHashMap.get(hashMapKey).add(contact);
            } else {
                List<Contact> list = new ArrayList<>();
                list.add(contact);
                groupedHashMap.put(hashMapKey, list);
            }
        }
        return groupedHashMap;
    }
}
