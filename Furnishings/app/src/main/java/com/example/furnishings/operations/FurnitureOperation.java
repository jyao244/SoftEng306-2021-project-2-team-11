package com.example.furnishings.operations;

import java.util.List;

public interface FurnitureOperation {
    public abstract String getName();
    public abstract Double getPrice();

    public abstract String getMaterial();

    public abstract String getDescription();
    public abstract String getSpecification();
    //public abstract List<String> getImage();

    public abstract String getCategory();

    public abstract String getPopular();
    public abstract boolean getFavourite();

    public abstract String getBrand();
}
