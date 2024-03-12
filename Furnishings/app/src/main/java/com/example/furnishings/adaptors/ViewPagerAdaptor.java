package com.example.furnishings.adaptors;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.furnishings.models.Furniture;
import com.example.furnishings.operations.ViewPagerAdaptorOperation;

import java.util.List;

public class ViewPagerAdaptor extends PagerAdapter implements ViewPagerAdaptorOperation {
    private Furniture item;
    private Context context;
    private List<ImageView> list;

    public ViewPagerAdaptor(List<ImageView> list) {
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(list.get(position));
    }
}
