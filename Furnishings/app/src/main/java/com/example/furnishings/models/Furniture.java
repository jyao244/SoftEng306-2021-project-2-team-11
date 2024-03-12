package com.example.furnishings.models;

import com.example.furnishings.operations.FurnitureOperation;

public abstract class Furniture implements FurnitureOperation {
    private String name;
    private Double price;
    private String material;
    private String description;
    //private List<String> imageURL;
    private String category;
    private String popular;
    private boolean favourite;
    private String brand;


    public Furniture(String name, Double price, String material, String description,
                     String category, String popular, Boolean favourite, String brand){
        this.name = name;
        this.price = price;
        this.material = material;
        this.description = description;
        this.category = category;
        this.popular = popular;
        this.favourite = favourite;
        this.brand = brand;
    }

    public Furniture() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public String getMaterial(){
        return material;
    }

    @Override
    public String getDescription() {
        return description;
    }

//    @Override
//    public List<String> getImage() {
//        return imageURL;
//    }

    @Override
    public String getCategory(){
        return category;
    }

    @Override
    public String getPopular() {
        return popular;
    }

    @Override
    public boolean getFavourite() {
        return favourite;
    }

    @Override
    public String getBrand(){
        return brand;
    }
}

