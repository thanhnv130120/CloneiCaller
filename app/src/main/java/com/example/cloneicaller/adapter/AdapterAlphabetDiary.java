package com.example.cloneicaller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.R;
import com.example.cloneicaller.item.ItemGroup;
import com.example.cloneicaller.item.ItemPerson;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AdapterAlphabetDiary extends RecyclerView.Adapter<AdapterAlphabetDiary.AlphaHolder> {
    private Context context;
    private ArrayList<ItemGroup> arrayListGroup;
    private ArrayList<String>itemPeople;

    public AdapterAlphabetDiary(Context context, ArrayList<ItemGroup> arrayListGroup) {
        this.context = context;
        this.arrayListGroup = arrayListGroup;
    }
    @NonNull
    @Override
    public AlphaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_diary,parent,false);
        return new AlphaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlphaHolder holder, int position) {
        holder.tvFirstCharacter.setText(arrayListGroup.get(position).getName());
        AdapterPersonDiary personAdapter = new AdapterPersonDiary(context,arrayListGroup.get(position).getPerson());
        holder.rclListGroup.setAdapter(personAdapter);
    }

    @Override
    public int getItemCount() {
        return arrayListGroup.size();
    }

    public static class AlphaHolder extends RecyclerView.ViewHolder{
        private TextView tvFirstCharacter;
        private RecyclerView rclListGroup;
        public AlphaHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstCharacter = itemView.findViewById(R.id.tv_first_alphabet);
            rclListGroup = itemView.findViewById(R.id.rcl_item_diary);
        }
    }
}
