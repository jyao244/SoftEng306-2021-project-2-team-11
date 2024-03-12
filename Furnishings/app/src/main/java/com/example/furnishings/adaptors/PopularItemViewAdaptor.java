package com.example.furnishings.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furnishings.R;
import com.example.furnishings.firebaseOperations.ImageLoader;
import com.example.furnishings.models.Furniture;
import com.example.furnishings.operations.OnItemClickListener;

import java.util.List;

public class PopularItemViewAdaptor extends RecyclerView.Adapter<PopularItemViewAdaptor.ViewHolder> {
    // To make your view item clickable ensure that the view holder class implements View.OnClickListener and it has the onClick(View v) method.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Declare objects of all the views to be manipulated in item_contact.xml
        public TextView name;
        public TextView price;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            // Initialize the view objects
            name = v.findViewById(R.id.item_name);
            price = v.findViewById(R.id.item_price);
            image = v.findViewById(R.id.item_image);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    // Declare the data collection object that holds the data to be populated in the RecyclerView
    private Context context;

    private String[] names;
    private String[] prices;
    private String[] categories;
    private OnItemClickListener onItemClickListener;

    // Pass in the contact array object into the constructor
    public PopularItemViewAdaptor(List<Furniture> contacts) {
        // The contacts object is sent via the activity that creates this adaptor
//        this.items = contacts;
    }

    public PopularItemViewAdaptor(String[] names, String[] prices, String[] categories) {
        this.names=names;
        this.prices=prices;
        this.categories=categories;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public PopularItemViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.popular_item, parent, false);

        // Return a new holder instance
        ViewHolder holder = new ViewHolder(view);

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
    public void onBindViewHolder(@NonNull PopularItemViewAdaptor.ViewHolder holder, int position) {
        // Get the data object for the item view in this position
//        Contact thisContact = mContacts.get(position);

        holder.name.setText(names[position]);
        holder.price.setText(prices[position]);

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
