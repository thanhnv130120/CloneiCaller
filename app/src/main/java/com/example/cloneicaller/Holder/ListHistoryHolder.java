package com.example.cloneicaller.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneicaller.R;

public class ListHistoryHolder extends RecyclerView.ViewHolder {

    public TextView tvNameContact, tvTimeCall;
    public ImageView imgAvatar,imgCall, imgDetailCall, imgBlock;
    public LinearLayout lnWarning;
    public ListHistoryHolder(@NonNull View itemView) {
        super(itemView);
        tvNameContact = itemView.findViewById(R.id.tvNameContact);
        tvTimeCall = itemView.findViewById(R.id.tvTimeCall);
        imgAvatar = itemView.findViewById(R.id.imgAvatar);
        imgCall = itemView.findViewById(R.id.imgCall);
        imgDetailCall = itemView.findViewById(R.id.imgDetailCall);
        lnWarning = itemView.findViewById(R.id.ln_warning);
        imgBlock = itemView.findViewById(R.id.imgBlock);
    }
}
