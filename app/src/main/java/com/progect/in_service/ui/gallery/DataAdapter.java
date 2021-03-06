package com.progect.in_service.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progect.in_service.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {

    Myclick myclick;
    Context context;
    ArrayList<DataList> list;


    public DataAdapter() {
    }

    public DataAdapter(Myclick myclick, Context context, ArrayList<DataList> list) {
        this.myclick = myclick;
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(context).inflate(R.layout.list_options, parent, false);
        final DataViewHolder dataViewHolder = new DataViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myclick.onMyClick(view, dataViewHolder.getPosition());
            }
        });
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        DataList current = list.get(position);

        holder.Name.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}