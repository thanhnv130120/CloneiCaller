package com.example.cloneicaller.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.TelephonyManager;
import android.util.Log;
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
import androidx.room.Room;

import com.example.cloneicaller.BlockActivity;
import com.example.cloneicaller.CallStateReceiver;
import com.example.cloneicaller.DetailContact;
import com.example.cloneicaller.R;
import com.example.cloneicaller.Room.BlockItemDatabase;
import com.example.cloneicaller.adapter.BlockListItemAdapter;
import com.example.cloneicaller.common.AppConstants;
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.custom.ChoosePlanViewSwipe;
import com.example.cloneicaller.databinding.DialogCompleteUnblockedBinding;
import com.example.cloneicaller.databinding.DialogDeleteContactBinding;
import com.example.cloneicaller.databinding.FragmentBlackDiaryBinding;
import com.example.cloneicaller.item.BlockerPersonItem;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FragmentBlackDiary extends Fragment implements BlockListItemAdapter.BlockerItemListener, AppConstants {
    FragmentBlackDiaryBinding binding;
    SharedPreferences preferencesBlockCall;
    SharedPreferences preferencesLie;
    SharedPreferences preferencesBlockAdvertise;
    SharedPreferences preferencesBlockForeign;
    private String number;
    private List<BlockerPersonItem>items;
    BlockItemDatabase database;
    private CallStateReceiver blockUnknownReceiver;
    private BlockerPersonItem blockerPersonItem;
    private final int REQUEST_READ_PHONE_STATE = 101;
    ChoosePlanViewSwipe choosePlanView;
    private BlockListItemAdapter adapter;
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
        preferencesLie = getContext().getSharedPreferences("blockLieCall",Context.MODE_PRIVATE);
        preferencesBlockAdvertise = getContext().getSharedPreferences("blockAdvertiseCall",Context.MODE_PRIVATE);
        preferencesBlockCall = getContext().getSharedPreferences("blockUnknownCall",Context.MODE_PRIVATE);
        preferencesBlockForeign = getContext().getSharedPreferences("blockForeign",Context.MODE_PRIVATE);
        binding.swUnknown.setChecked(preferencesBlockCall.getBoolean("checked",false));
        binding.swLieOwe.setChecked(preferencesLie.getBoolean("checked1",false));
        binding.swAdvertise.setChecked(preferencesBlockAdvertise.getBoolean("checked",false));
        binding.swNation.setChecked(preferencesBlockForeign.getBoolean("checked",false));
        choosePlanView = new ChoosePlanViewSwipe(getContext());
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
        binding.swLieOwe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.swLieOwe.isChecked()){
                    SharedPreferences.Editor editor = preferencesLie.edit();
                    editor.putBoolean("checked1",true);
                    editor.commit();
                }else if(!binding.swLieOwe.isChecked()){
                    SharedPreferences.Editor editor = preferencesLie.edit();
                    editor.remove("checked1");
                    editor.commit();
                }
            }
        });
        binding.swUnknown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.swUnknown.isChecked()){
                    SharedPreferences.Editor editor = preferencesBlockCall.edit();
                    editor.putBoolean("checked",true);
                    editor.commit();
                }else if(!binding.swUnknown.isChecked()){
                    SharedPreferences.Editor editor = preferencesBlockCall.edit();
                    editor.remove("checked");
                    editor.commit();
                }
            }
        });
        binding.swAdvertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.swAdvertise.isChecked()){
                    SharedPreferences.Editor editor = preferencesBlockAdvertise.edit();
                    editor.putBoolean("checked",true);
                    editor.commit();
                }else if(!binding.swAdvertise.isChecked()){
                    SharedPreferences.Editor editor = preferencesBlockAdvertise.edit();
                    editor.remove("checked");
                    editor.commit();
                }
            }
        });
        binding.swNation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.swNation.isChecked()){
                    SharedPreferences.Editor editor = preferencesBlockForeign.edit();
                    editor.putBoolean("checked",true);
                    editor.commit();
                }else if(!binding.swNation.isChecked()){
                    SharedPreferences.Editor editor = preferencesBlockForeign.edit();
                    editor.remove("checked");
                    editor.commit();
                }
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rcBlockList);
         database = Room.databaseBuilder(getContext().getApplicationContext(),BlockItemDatabase.class,
                "blockItems")
                .allowMainThreadQueries()
                .build();
        updateTask();
    }
    public void updateTask(){
        items = database.getItemDao().getItems();
        if (items.size()>1) {
            items = Common.sortBlockList(items);
            items = Common.addAlphabetBlocker(items);
        }
        adapter = new BlockListItemAdapter(items,getContext());
        binding.rcBlockList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setListener(this);
    }
    BlockerPersonItem blockRequest = null;
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
                    //Toast.makeText(getContext(),items.get(position).getName(),Toast.LENGTH_LONG).show();
                    blockRequest = items.get(position);
                    items.remove(position);
//                    adapter.notifyItemRemoved(position);
                    database.getItemDao().deleteAll(blockRequest);
                    updateTask();
                    Dialog dialog = new Dialog(getContext());
                    DialogCompleteUnblockedBinding binding;
                    binding = DialogCompleteUnblockedBinding.inflate(getLayoutInflater());
                    View view1 = binding.getRoot();
                    new Thread() {
                        public void run() {
                            try{
                                Thread.sleep(2000);
                            }
                            catch (Exception e) {
                                Log.e("tag", e.getMessage());
                            }
                            // dismiss the progress dialog
                            dialog.dismiss();
                        }
                    }.start();
                    dialog.setContentView(view1);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();
                    break;
            }
        }
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPurpleBG))
                    .addSwipeLeftActionIcon(R.drawable.ic_unblock)
//                    .addSwipeLeftLabel("Unblock")
                    .create()
                    .decorate();
            View itemView = viewHolder.itemView;
//            if(dX < 0){
//                choosePlanView.invalidate();
//                //choosePlanView.setBackgroundResource(R.color.delete_red);
//                choosePlanView.measure(itemView.getWidth(), itemView.getHeight());
//                choosePlanView.layout(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                c.save();
//                c.translate(choosePlanView.getRight() + (int) dX, viewHolder.getAdapterPosition()*itemView.getHeight());
//
//                choosePlanView.draw(c);
//                c.restore();
//            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
//    private BroadcastReceiver blockUnknownReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String state =  intent.getStringExtra(TelephonyManager.EXTRA_STATE);
//            Log.e("AAAA", "state");
//            String incomingNumber =intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//                List<BlockerPersonItem>blockerItem = Common.checkRealDialer(items);
//                if ((incomingNumber != null)) {
//                    if(!Common.checkUnknown(incomingNumber,getContext())&& binding.swUnknown.isChecked()){
//                        Log.e("Broadcast","Unknown?Checked!");
//                        Toast.makeText(getContext(),"Unknown?Checked !",Toast.LENGTH_LONG).show();
//                        CallStateReceiver.endCall(getContext());
//                    }
//                    if(Common.checkInside(incomingNumber,Common.checkLier(blockerItem))&&binding.swLieOwe.isChecked()){
//                        Log.e("Broadcast","Lie or Owe?Checked!");
//                        Toast.makeText(getContext(),"Lie or Owe?Checked !",Toast.LENGTH_LONG).show();
//                        CallStateReceiver.endCall(getContext());
//                    }
//                    if (Common.checkInside(incomingNumber,Common.checkAdvertise(blockerItem))&&binding.swAdvertise.isChecked()){
//                        Log.e("Broadcast","Advertised?Checked!");
//                        Toast.makeText(getContext(),"Advertised?Checked !",Toast.LENGTH_LONG).show();
//                        CallStateReceiver.endCall(getContext());
//                    }
//                    if (Common.notForeignNumber(incomingNumber)==false){
//                        Log.e("Broadcast","Foreign number?Checked!");
//                        Toast.makeText(getContext(),"Foreign number?Checked!",Toast.LENGTH_LONG).show();
//                        CallStateReceiver.endCall(getContext());
//                    }
//                }
//            }
//        }
//    };

    @Override
    public void onStart() {
        super.onStart();
//        if(binding.swUnknown.isChecked()){
////            if(Common.checkUnknown(number,getContext())){
//                Log.e("FragmentBlack","check faint");
//                endCall(getContext());
////            }
//        }
//        blockUnknownReceiver = new CallStateReceiver();
//        IntentFilter intentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
//        intentFilter.setPriority(100);
//        getActivity().registerReceiver(blockUnknownReceiver,intentFilter);
        updateTask();
    }

//    private void init() {
//        receiver = new CallStateReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.intent.action.PHONE_STATE");
//        getActivity().registerReceiver(receiver,filter);
//    }

    @Override
    public void onStop() {
        super.onStop();
//        getActivity().unregisterReceiver(blockUnknownReceiver);
    }

    @Override
    public void onClickBlocker(int position) {
        Intent intent = new Intent(getActivity(), DetailContact.class);
//        Bundle bundle = new Bundle();
        blockerPersonItem = items.get(position);
        intent.putExtra(INTENT_NAME,blockerPersonItem.getName());
        intent.putExtra(INTENT_NUMBER,blockerPersonItem.getNumber());
        intent.putExtra(INTENT_BLOCK,true);
        intent.putExtra(INTENT_BLOCK_TYPE,blockerPersonItem.getType());
        intent.putExtra(INTENT_IMAGE,blockerPersonItem.getType());
        intent.putExtra(INTENT_TYPE_ARRANGE,blockerPersonItem.getTypeArrange());
//        bundle.putString(INTENT_NAME,items.get(position).getName());
//        bundle.putString(INTENT_NUMBER,items.get(position).getNumber());
//        bundle.putEx;
//        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE&&resultCode== Activity.RESULT_OK){
//            if (data.hasExtra(INTENT_DELETE)){
//                Log.e("FragmentBlackDiary","delete accepted!");
//                BlockerPersonItem blockerPersonItem = new BlockerPersonItem(
//                        data.getStringExtra(INTENT_NAME),
//                        data.getStringExtra(INTENT_BLOCK_TYPE),
//                        data.getStringExtra(INTENT_NUMBER),
//                        data.getIntExtra(INTENT_IMAGE,1),
//                        data.getIntExtra(INTENT_TYPE_ARRANGE,-1));
//                database.getItemDao().deleteAll(blockerPersonItem);
            database.getItemDao().deleteAll(blockerPersonItem);
            updateTask();
            }
        }
}
