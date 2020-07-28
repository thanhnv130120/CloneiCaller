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
import com.example.cloneicaller.common.Common;
import com.example.cloneicaller.item.ItemPerson;

import java.util.List;

public class ItemPersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ItemPerson>personList;
    private PersonItemListener listener;

    public ItemPersonAdapter(Context context, List<ItemPerson> personList) {
        this.context = context;
        this.personList = personList;
    }

    public void setListener(PersonItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (viewType == Common.VIEW_TYPE_GROUP){
            ViewGroup group = (ViewGroup)layoutInflater.inflate(R.layout.group_item,parent,false);
            GroupHolder groupHolder = new GroupHolder(group);
            return groupHolder;
        }
        else if(viewType == Common.VIEW_TYPE_PERSON){
            ViewGroup person = (ViewGroup)layoutInflater.inflate(R.layout.person_item,parent,false);
            PersonHolder personHolder = new PersonHolder(person);
            return personHolder;
        }
        else{
            ViewGroup group = (ViewGroup)layoutInflater.inflate(R.layout.group_item,parent,false);
            GroupHolder groupHolder = new GroupHolder(group);
            return groupHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupHolder){
            GroupHolder groupHolder = (GroupHolder)holder;
            groupHolder.tvGroupList.setText(personList.get(position).getName());
        }
        else if(holder instanceof PersonHolder){
            PersonHolder personHolder = (PersonHolder) holder;
            personHolder.tvPersonList.setText(personList.get(position).getName());
            personHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onClickPerson(position);
                    }
                    //Toast.makeText(context,""+personList.get(position).getName() +":"+personList.get(position).getNumber(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class PersonHolder extends RecyclerView.ViewHolder{
        private TextView tvPersonList;
        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            tvPersonList = (TextView)itemView.findViewById(R.id.tv_person_title);
        }
    }
    public class GroupHolder extends RecyclerView.ViewHolder{
        private TextView tvGroupList;
        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupList = (TextView)itemView.findViewById(R.id.tv_group_list);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return personList.get(position).getViewType();
    }
    public interface PersonItemListener{
        void onClickPerson(int position);
    }
}
