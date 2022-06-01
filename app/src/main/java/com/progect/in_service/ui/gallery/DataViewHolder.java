package com.progect.in_service.ui.gallery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progect.in_service.R;

public class DataViewHolder extends RecyclerView.ViewHolder {

    TextView Name;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);

        Name = itemView.findViewById(R.id.name);
    }
}