package com.example.furnishings.operations;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public interface ViewPagerAdaptorOperation {
    public abstract int getCount();
    public abstract boolean isViewFromObject(@NonNull View view, @NonNull Object object);
    public abstract void destroyItem(ViewGroup viewGroup, int position, Object object);
    public abstract Object instantiateItem(ViewGroup viewGroup, int position);
}
