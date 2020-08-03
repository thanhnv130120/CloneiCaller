package com.example.cloneicaller.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.DetailContact;
import com.example.cloneicaller.DetailDiaryActivity;
import com.example.cloneicaller.R;
import com.example.cloneicaller.adapter.AdapterAlphabetDiary;
import com.example.cloneicaller.adapter.AdapterPersonDiary;
import com.example.cloneicaller.adapter.ItemPersonAdapter;
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.databinding.FragmentDiaryBinding;
import com.example.cloneicaller.item.ItemGroup;
import com.example.cloneicaller.item.ItemPerson;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class FragmentDiary extends Fragment implements View.OnClickListener, ItemPersonAdapter.PersonItemListener{
    private RecyclerView rclList;
    private FloatingActionButton btnAdd;
    private ArrayList<ItemPerson>people = new ArrayList<>();
    private FragmentDiaryListner listener;
//    private ArrayList<ItemGroup> groupAlphabet = new ArrayList<>();
//    private ArrayList<String> person = new ArrayList<>();
//    private ArrayList<String>persons = new ArrayList<>();

    FragmentDiaryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentDiaryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.floatingBtnDiary.setOnClickListener(this);
        fetchContact();
        return view;
    }

    private void fetchContact() {
//        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//        String selection = null;
//        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
//        String[] selectionArgs = null;
//        String sortOrder = null;
//        ContentResolver resolver = getContext().getContentResolver();
//        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
//        while (cursor.moveToNext()){
//            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
////            person.add(name);
//            people.add(new ItemPerson(name,-1,num));
//        }
        people = Common.resolverArrayList(null,getContext());
        people = Common.sortList(people);
        people = Common.addAlphabet(people);
        listener.onPutListDiarySent(people);
        ItemPersonAdapter adapter = new ItemPersonAdapter(getContext(),people);
        binding.rclDiary.setAdapter(adapter);
        adapter.setListener(this);
//        for (String itemPerson:sortArrayList(person)) {
//            persons.add(itemPerson);
//        }
//        ArrayList<String>alphabet = new ArrayList<>();
//        for (String personz:persons) {
//            alphabet.add(Character.toString(personz.charAt(0)));
//        }
//        Set<String> set = new HashSet<>(alphabet);
//        alphabet.clear();
//        alphabet.addAll(set);
//        for (int i = 0; i < alphabet.size(); i++) {
//            ArrayList<String>collection = new ArrayList<>();
//            for (int j = 0; j < persons.size(); j++) {
//                if(Character.toString(persons.get(j).charAt(0)).equals(alphabet.get(i))){
//                    collection.add(persons.get(j));
//                }
//            }
//            groupAlphabet.add(new ItemGroup(collection,alphabet.get(i)));
//        }
//        for (String itemPerson:person) {
//            groupAlphabet.add(new ItemGroup(persons,Character.toString(itemPerson.charAt(0))));
//        }
//        AdapterAlphabetDiary adapterAlphabetDiary = new AdapterAlphabetDiary(getContext(),groupAlphabet);
//        rclList.setAdapter(adapterAlphabetDiary);
    }
    private ArrayList<String> sortArrayList(ArrayList<String>item){
        Collections.sort(item, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        return item;
    }
    private ArrayList<String> getAlList(ArrayList<String>list){
        ArrayList<String>arrayList = new ArrayList<>();
        //String temp;
        String [] strings = list.toArray(new String [0]);
        int n = strings.length;
        String []al = new String[n];
        for (int i = 0; i < n; i++) {
//            for (int j = i+1; j < n; j++) {
//                if(strings[i].compareTo(strings[j])>0){
//                    temp = strings[i];
//                    strings[i]= strings[j];
//                    strings[j] = temp;
//                }
//            }
            al[i]=Character.toString(strings[i].charAt(0));
        }

//        ArrayList<String>characterAlpha = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            for (int j = i+1; j < n; j++) {
//                if(strings[i].charAt(0)!=strings[j].charAt(0)){
//                    characterAlpha.add(Character.toString(strings[i].charAt(0)));
//                }
//            }
//        }
        ArrayList asList = (ArrayList) Arrays.asList(al);
        return arrayList;
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "");
        startActivity(intent);
    }
    @Override
    public void onClickPerson(int position) {
        Intent intent = new Intent(getActivity(), DetailContact.class);
        Bundle bundle = new Bundle();
        bundle.putString("name",people.get(position).getName());
        bundle.putString("number",people.get(position).getNumber());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public interface FragmentDiaryListner{
        void onPutListDiarySent(ArrayList<ItemPerson>people);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentDiaryListner){
            listener = (FragmentDiaryListner)context;
        }else {
            throw new RuntimeException(context.toString() + " must implement FragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
