package com.example.cloneicaller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.R;
import com.example.cloneicaller.databinding.ItemBlockListBinding;
import com.example.cloneicaller.item.BlockTypeItem;
import com.example.cloneicaller.item.BlockerPersonItem;

import java.util.ArrayList;
import java.util.List;

public class BlockListItemAdapter extends RecyclerView.Adapter<BlockListItemAdapter.BlockSetHolder> {
    private List<BlockerPersonItem> blockTypeItems;
    private Context context;

    public BlockListItemAdapter(List<BlockerPersonItem> blockTypeItems, Context context) {
        this.blockTypeItems = blockTypeItems;
        this.context = context;
    }

    @NonNull
    @Override
    public BlockSetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BlockSetHolder(ItemBlockListBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BlockSetHolder holder, int position) {
        holder.itemBlockListBinding.tvNameContactBlock.setText(blockTypeItems.get(position).getName());
        holder.itemBlockListBinding.tvTypeBlock.setText(blockTypeItems.get(position).getType());
        holder.itemBlockListBinding.imgTypeBlock.setImageResource(blockTypeItems.get(position).getImage());
    }
    @Override
    public int getItemCount() {
        return blockTypeItems.size();
    }

    public static class BlockSetHolder extends RecyclerView.ViewHolder{
        ItemBlockListBinding itemBlockListBinding;
        public BlockSetHolder(@NonNull ItemBlockListBinding itemBlockListBinding) {
            super(itemBlockListBinding.getRoot());
            this.itemBlockListBinding = itemBlockListBinding;
        }
    }
}
