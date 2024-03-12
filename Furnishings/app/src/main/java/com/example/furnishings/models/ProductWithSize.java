package com.example.furnishings.models;

public abstract class ProductWithSize extends Furniture{
    private String width;
    private String length;
    private String height;

    public ProductWithSize(String name, Double price, String material, String description,
                     String category, String popular, Boolean favourite, String brand,
                           String width, String length, String height){
        super(name, price, material, description, category, popular, favourite, brand);
        this.width = width;
        this.length = length;
        this.height = height;
    }

    public ProductWithSize() {
        super();
    }

    public String getWidth() {
        return width;
    }
    public String getLength(){
        return length;
    }
    public String getHeight(){
        return height;
    }
}
