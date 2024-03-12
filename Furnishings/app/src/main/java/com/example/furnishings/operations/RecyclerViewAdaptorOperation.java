package com.example.furnishings.operations;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerViewAdaptorOperation {
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
    public abstract int getItemCount();
}
