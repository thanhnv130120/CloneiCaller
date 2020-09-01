package com.example.cloneicaller.fragment;

import android.app.Dialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.DetailContact;
import com.example.cloneicaller.DetailDiaryActivity;
import com.example.cloneicaller.R;
import com.example.cloneicaller.SwipeHelper;
import com.example.cloneicaller.adapter.AdapterAlphabetDiary;
import com.example.cloneicaller.adapter.AdapterPersonDiary;
import com.example.cloneicaller.adapter.ItemPersonAdapter;
import com.example.cloneicaller.common.AppConstants;
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.custom.ChoosePlanViewSwipe;
import com.example.cloneicaller.custom.ChoosePlanViewSwipeDiary;
import com.example.cloneicaller.databinding.DialogDeleteContactBinding;
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
import java.util.List;
import java.util.Set;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FragmentDiary extends Fragment implements View.OnClickListener, ItemPersonAdapter.PersonItemListener, AppConstants {
    private RecyclerView rclList;
    private FloatingActionButton btnAdd;
    private ArrayList<ItemPerson>people = new ArrayList<>();
    private FragmentDiaryListner listener;
    ItemPersonAdapter adapter;
    FragmentDiaryBinding binding;
    ChoosePlanViewSwipeDiary choosePlanViewSwipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDiaryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.floatingBtnDiary.setOnClickListener(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rclDiary);
        choosePlanViewSwipe = new ChoosePlanViewSwipeDiary(getContext());
        fetchContact();
    }

    ItemPerson person = null;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    person = people.get(position);
                    String number = person.getNumber();
                    people.remove(position);
                    adapter.notifyItemRemoved(position);
                    Dialog dialog = new Dialog(getContext());
                    DialogDeleteContactBinding binding;
                    binding = DialogDeleteContactBinding.inflate(getLayoutInflater());
                    View view2 = binding.getRoot();
                    binding.tvNameSwipe.setText(person.getName());
                    binding.btnRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ContentResolver resolver = getContext().getContentResolver();
                            Common.deleteContact(resolver,number);
                            dialog.dismiss();
                        }
                    });

                    binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           people.add(person);
                           adapter.notifyDataSetChanged();
                           dialog.dismiss();
                        }
                    });
                    dialog.setContentView(view2);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
//                    .addSwipeLeftLabel("Unblock")
                    .create()
                    .decorate();
            View itemView = viewHolder.itemView;
//            if(dX < 0){
//                choosePlanViewSwipe.invalidate();
//                //choosePlanView.setBackgroundResource(R.color.delete_red);
//                choosePlanViewSwipe.measure(itemView.getWidth(), itemView.getHeight());
//                choosePlanViewSwipe.layout(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                c.save();
//                c.translate(choosePlanViewSwipe.getRight() + (int) dX, viewHolder.getAdapterPosition()*itemView.getHeight());
//
//                choosePlanViewSwipe.draw(c);
//                c.restore();
//            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    private void fetchContact() {
        try {
            people = Common.resolverArrayList(null, getContext());
            people = Common.sortList(people);
            people = Common.addAlphabet(people);
            listener.onPutListDiarySent(people);
            adapter = new ItemPersonAdapter(getContext(), people);
            binding.rclDiary.setAdapter(adapter);
            adapter.setListener(this);
        } catch (Exception e) {
            Log.e("abc", e.getMessage());
        }
    }

    private ArrayList<String> sortArrayList(ArrayList<String> item) {
        Collections.sort(item, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        return item;
    }

    private ArrayList<String> getAlList(ArrayList<String> list) {
        ArrayList<String> arrayList = new ArrayList<>();

        String[] strings = list.toArray(new String[0]);
        int n = strings.length;
        String[] al = new String[n];
        for (int i = 0; i < n; i++) {

            al[i] = Character.toString(strings[i].charAt(0));
        }

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
        ItemPerson itemPerson = people.get(position);
        intent.putExtra(INTENT_NAME, itemPerson.getName());
        intent.putExtra(INTENT_NUMBER, itemPerson.getNumber());
        intent.putExtra(INTENT_BLOCK, false);
        intent.putExtra(INTENT_BLOCK_TYPE, "");
        intent.putExtra(INTENT_TYPE_ARRANGE, itemPerson.getViewType());
        startActivity(intent);
    }

    public interface FragmentDiaryListner {
        void onPutListDiarySent(ArrayList<ItemPerson> people);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentDiaryListner) {
            listener = (FragmentDiaryListner) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
