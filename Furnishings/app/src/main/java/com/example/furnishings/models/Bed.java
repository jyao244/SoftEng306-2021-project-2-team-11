package com.example.furnishings.models;

public class Bed extends ProductWithSize{

    public Bed(String name, Double price, String material, String description,
                           String category, String popular, Boolean favourite, String brand,
                           String width, String length, String height) {
        super(name, price, material, description, category, popular, favourite, brand, width, length, height);
    }

    public Bed(){
        super();
    }
    @Override
    public String getSpecification() {
        String specification = "";
        specification += "Length: " + this.getLength()
                +"\nWidth: " + this.getWidth()
                +"\nHeight: " + this.getHeight()
                +"\nBrand: " + this.getBrand()
                +"\nMaterial: " + this.getMaterial();
        return specification;
    }
}
