package com.example.furnishings.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furnishings.R;
import com.example.furnishings.firebaseOperations.ImageLoader;
import com.example.furnishings.models.Furniture;
import com.example.furnishings.operations.OnItemClickListener;

import java.util.List;

public class ItemRecyclerViewAdaptor extends RecyclerView.Adapter<ItemRecyclerViewAdaptor.ViewHolder> {
    // To make your view item clickable ensure that the view holder class implements View.OnClickListener and it has the onClick(View v) method.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Declare objects of all the views to be manipulated in item_contact.xml
        public TextView name;
        public TextView price;
        public TextView description;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            // Initialize the view objects
            name = v.findViewById(R.id.title);
            price = v.findViewById(R.id.price);
            description = v.findViewById(R.id.desc);
            image = v.findViewById(R.id.image);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    // Declare the data collection object that holds the data to be populated in the RecyclerView
//    private List<Furniture> items;
    private Context context;

    private String[] names;
    private String[] prices;
    private String[] shortDescriptions;
    private String[] categories;
    private OnItemClickListener onItemClickListener;

    public ItemRecyclerViewAdaptor(List<Furniture> contacts) {
        // The contacts object is sent via the activity that creates this adaptor
//        this.items = contacts;
    }

    public ItemRecyclerViewAdaptor(String[] names, String[] prices, String[] shortDescriptions, String[] categories) {
        this.names=names;
        this.prices=prices;
        this.shortDescriptions=shortDescriptions;
        this.categories=categories;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ItemRecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.card_item, parent, false);

        // Return a new holder instance
        ItemRecyclerViewAdaptor.ViewHolder holder = new ItemRecyclerViewAdaptor.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, (int) v.getTag());
                }

            }
        });

        return holder;
    }


    // This method populates the data from mContacts to the view items
    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerViewAdaptor.ViewHolder holder, int position) {
        holder.name.setText(names[position]);
        holder.price.setText(prices[position]);
        holder.description.setText(shortDescriptions[position]);

        //set images for each popular item
        String imgLocation = ("Product Images/"+categories[position]+"/"+names[position]+"/1.jpg");
        ImageLoader loader = new ImageLoader();
        loader.loadImageView(holder.image, imgLocation);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
