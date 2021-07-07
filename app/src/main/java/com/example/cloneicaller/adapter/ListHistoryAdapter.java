package com.example.cloneicaller.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.DetailDiaryActivity;
import com.example.cloneicaller.Holder.HeaderListHistory;
import com.example.cloneicaller.Holder.ListHistoryHolder;
import com.example.cloneicaller.ListItem;
import com.example.cloneicaller.Models.Contact;
import com.example.cloneicaller.Models.DateItem;
import com.example.cloneicaller.Models.GeneralItem;
import com.example.cloneicaller.R;
import com.example.cloneicaller.SwipeHelper;
import com.example.cloneicaller.common.Common;

import java.util.List;

public class ListHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Thanhnv

    Context context;
    List<ListItem> listItemList;
    public static String outgoingNumber = "";
    SharedPreferences sharedCheckoutDiary;
    SharedPreferences sharedCheckoutLieOwn;
    SharedPreferences sharedCheckoutAdvertise;
    SharedPreferences sharedCheckoutForeign;

    public ListHistoryAdapter(Context context, List<ListItem> listItemList) {
        this.context = context;
        this.listItemList = listItemList;
        sharedCheckoutDiary = context.getSharedPreferences("blockUnknownCall", Context.MODE_PRIVATE);
        sharedCheckoutLieOwn = context.getSharedPreferences("blockLieCall", Context.MODE_PRIVATE);
        sharedCheckoutAdvertise = context.getSharedPreferences("blockAdvertiseCall", Context.MODE_PRIVATE);
        sharedCheckoutForeign = context.getSharedPreferences("blockForeign", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case ListItem.TYPE_GENERAL:
                View v1 = inflater.inflate(R.layout.item_contact, parent,
                        false);
                viewHolder = new ListHistoryHolder(v1);
                break;

            case ListItem.TYPE_DATE:
                View v2 = inflater.inflate(R.layout.item_sortbydate, parent, false);
                viewHolder = new HeaderListHistory(v2);
                break;
        }

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {

            case ListItem.TYPE_GENERAL:

                GeneralItem generalItem = (GeneralItem) listItemList.get(position);
                ListHistoryHolder listHistoryHolder = (ListHistoryHolder) holder;
                String number = generalItem.getContact().getNumber();
                String duration = generalItem.getContact().getDuration();
                String date = generalItem.getContact().getDate();
                String type = generalItem.getContact().getType();
                String name = generalItem.getContact().getName();
                String country = generalItem.getContact().getCountry();
                String numberType = generalItem.getContact().getNetwork();
                if (generalItem.getContact().getName() == null) {
                    listHistoryHolder.tvNameContact.setText(number + "");
                } else {
                    listHistoryHolder.tvNameContact.setText(name);
                }
                if (type == "OUTGOING") {
                    listHistoryHolder.imgCall.setImageResource(R.drawable.ic_out_call);
                } else if (type == "INCOMING") {
                    listHistoryHolder.imgCall.setImageResource(R.drawable.ic_in_call);
                } else {
                    listHistoryHolder.imgCall.setImageResource(R.drawable.ic_miss_call);
                }

                listHistoryHolder.tvTimeCall.setText(duration);
                listHistoryHolder.imgDetailCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DetailDiaryActivity.class);
                        intent.putExtra("number", number);
                        intent.putExtra("duration", duration);
                        intent.putExtra("date", date);
                        intent.putExtra("type", type);
                        intent.putExtra("name", name);
                        intent.putExtra("country", country);
                        intent.putExtra("numbertype", numberType);
                        context.startActivity(intent);
                    }
                });
//                listHistoryHolder.tvNameContact.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
                String num = (number.charAt(0) == '0') ? number.replaceFirst("0", "+84").replaceAll("\\s", "") : number.replaceAll("\\s", "");
                if (sharedCheckoutDiary.getBoolean("checked", false) == true && !Common.checkUnknown(num, context)) {
                    listHistoryHolder.lnWarning.setVisibility(View.VISIBLE);
                }
                else {
                    listHistoryHolder.lnWarning.setVisibility(View.GONE);
                }
                if(Common.getAd(context,num)==true && sharedCheckoutAdvertise.getBoolean("checked",false)==true||
                        Common.getLie(context,num)==true&&sharedCheckoutLieOwn.getBoolean("checked1",false)==true){
                    listHistoryHolder.imgBlock.setVisibility(View.VISIBLE);
                }
                else {
                    listHistoryHolder.imgBlock.setVisibility(View.GONE);
                }
//                if(sharedCheckoutAdvertise.getBoolean("checked",false)==true && Common.getCheckOutComming(context,num).equals("QUẢNG CÁO")
//                || sharedCheckoutLieOwn.getBoolean("checked",false )==true &&Common.getCheckOutComming(context,num).equals("LỪA ĐẢO")){
//                    listHistoryHolder.imgBlock.setVisibility(View.VISIBLE);
//                }else {
//                    listHistoryHolder.imgBlock.setVisibility(View.GONE);
//                }
//                Log.e("CheckTab",Common.getAd(context,num)+"Ad**************");
//                Log.e("CheckTab",Common.getLie(context,num)+"Lie");
//                    }
//                });

                listHistoryHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + generalItem.getContact().getNumber() + ""));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        }
                        outgoingNumber = generalItem.getContact().getNumber();
                        Log.e("ABC", outgoingNumber);
                        context.startActivity(intent);
                    }
                });


                break;

            case ListItem.TYPE_DATE:
                DateItem dateItem = (DateItem) listItemList.get(position);
                HeaderListHistory headerListHistory = (HeaderListHistory) holder;

                headerListHistory.tvDate.setText(dateItem.getDate());

                break;
        }

    }


    @Override
    public int getItemCount() {
        return listItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listItemList.get(position).getType();

    }

}
