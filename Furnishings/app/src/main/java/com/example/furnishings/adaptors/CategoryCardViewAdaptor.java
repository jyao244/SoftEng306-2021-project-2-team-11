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

public class CategoryCardViewAdaptor extends RecyclerView.Adapter<CategoryCardViewAdaptor.ViewHolder> {
    // To make your view item clickable ensure that the view holder class implements View.OnClickListener and it has the onClick(View v) method.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Declare objects of all the views to be manipulated in item_contact.xml
        public TextView category;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            // Initialize the view objects
            category = v.findViewById(R.id.category_title);
            image = v.findViewById(R.id.image_background);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    // Declare the data collection object that holds the data to be populated in the RecyclerView
    private Context context;
    private String[] category_Names;

    private OnItemClickListener onItemClickListener;

    // Pass in the contact array object into the constructor
    public CategoryCardViewAdaptor(List<Furniture> contacts) {
        // The contacts object is sent via the activity that creates this adaptor
//        this.items = contacts;
    }

    public CategoryCardViewAdaptor(String[] categories) {
        this.category_Names=categories;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public CategoryCardViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.category_item, parent, false);

        // Return a new holder instance
        CategoryCardViewAdaptor.ViewHolder holder = new CategoryCardViewAdaptor.ViewHolder(view);

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
    public void onBindViewHolder(@NonNull CategoryCardViewAdaptor.ViewHolder holder, int position) {
        //set names for each category card
        holder.category.setText(category_Names[position]);

        //set images for each category card
        String imgLocation = ("Category Images/"+category_Names[position]+".jpg");
        ImageLoader loader = new ImageLoader();
        loader.loadImageView(holder.image, imgLocation);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return category_Names.length;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
