package com.example.furnishings.operations;

import android.view.View;
import android.view.ViewGroup;

public interface ViewArrayAdaptorOperation {
    public abstract View getView(int position, View view, ViewGroup viewGroup);
}
