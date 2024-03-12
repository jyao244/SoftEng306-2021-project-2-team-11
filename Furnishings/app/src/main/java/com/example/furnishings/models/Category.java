package com.example.furnishings.models;

public class Category {
    private String Category;
    private String SubTitle;

    public Category() {
        //public no-arg constructor needed for firestore custom object
    }

    public Category(String category, String subTitle) {
        this.Category = category;
        this.SubTitle = subTitle;
    }

    public String getCategory() {
        return Category;
    }

    public String getSubTitle() {
        return SubTitle;
    }
}