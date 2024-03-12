package com.example.furnishings.models;

public abstract class ProductWithoutSize extends Furniture{

    public ProductWithoutSize() {

    }


    public ProductWithoutSize(String name, Double price, String material, String description,
                     String category, String popular, Boolean favourite, String brand){
        super(name, price, material, description, category, popular, favourite, brand);
    }
}
