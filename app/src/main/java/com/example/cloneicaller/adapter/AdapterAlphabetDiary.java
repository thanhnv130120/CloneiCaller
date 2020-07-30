package com.example.cloneicaller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.R;
import com.example.cloneicaller.item.ItemGroup;
import com.example.cloneicaller.item.ItemPerson;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AdapterAlphabetDiary extends RecyclerView.Adapter<AdapterAlphabetDiary.AlphaHolder> implements AdapterPersonDiary.ItemClicker{
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
        for (int i = 0; i < arrayListGroup.get(position).getPerson().size(); i++) {

        }
        AdapterPersonDiary personAdapter = new AdapterPersonDiary(context,itemPeople);
        holder.rclListGroup.setAdapter(personAdapter);
        personAdapter.setListener(this);
    }

    @Override
    public int getItemCount() {
        return arrayListGroup.size();
    }

    @Override
    public void onClickListener(int position) {
        Toast.makeText(context,itemPeople.get(position),Toast.LENGTH_LONG).show();
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
    public interface GroupOnClick{
        void onGroupClickListener(int position);
    }
}
