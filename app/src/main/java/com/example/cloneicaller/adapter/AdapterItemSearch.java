package com.example.cloneicaller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.R;
import com.example.cloneicaller.item.ItemPerson;

import java.util.ArrayList;

public class AdapterItemSearch extends RecyclerView.Adapter<AdapterItemSearch.PersonSearchItem> {
    private Context context;
    private ArrayList<ItemPerson>people;
    private SearchClickListener listener;
    public AdapterItemSearch(Context context, ArrayList<ItemPerson> people) {
        this.context = context;
        this.people = people;
    }

    @NonNull
    @Override
    public PersonSearchItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.person_item,parent,false);
        return new PersonSearchItem(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonSearchItem holder, int position) {
        holder.tvSearchName.setText(people.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onSearchClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public static class PersonSearchItem extends RecyclerView.ViewHolder {
        private TextView tvSearchName;
        public PersonSearchItem(@NonNull View itemView) {
            super(itemView);
            tvSearchName = (TextView) itemView.findViewById(R.id.tv_person_title);
        }
    }
    public interface SearchClickListener{
        void onSearchClickListener(int position);
    }
    public void filterList(ArrayList<ItemPerson>arrayList){
        people = arrayList;
        notifyDataSetChanged();
    }
}
