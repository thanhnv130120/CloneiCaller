package com.example.cloneicaller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.Holder.BlockListHolder;
import com.example.cloneicaller.R;

public class BlockListAdapter extends RecyclerView.Adapter<BlockListHolder> {

    Context context;

    @NonNull
    @Override
    public BlockListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_block_list,parent,false);
        BlockListHolder blockListHolder = new BlockListHolder(v);
        return blockListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BlockListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
