package com.example.furnishings.adaptors;

import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView textView;
    private ListView listView;
    private ImageView imageView;
    private ScrollView scrollView;
    private TextView name;
    private TextView price;
    private TextView category;

    public ViewHolder(@NonNull View itemView, TextView name, TextView price, TextView category) {
        super(itemView);
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public ViewHolder(@NonNull View itemView, TextView textView, ListView listView, ImageView imageView, ScrollView scrollView) {
        super(itemView);
        this.textView = textView;
        this.listView = listView;
        this.imageView = imageView;
        this.scrollView = scrollView;
    }

    @Override
    public void onClick(View v) {

    }
}
