package com.example.furnishings.models;

public class Chair extends ProductWithSize{
    private String colour;
    private String usage;

    public Chair(){
        super();
        //public no-arg constructor needed
    }

    public Chair(String name, Double price, String material, String description,
                 String category, String popular, Boolean favourite, String brand,
                 String width, String length, String height, String colour, String usage) {
        super(name, price, material, description, category, popular, favourite, brand, width, length, height);
        this.colour=colour;
        this.usage=usage;
    }

    public String getColour(){
        return colour;
    }
    public String getUsage(){
        return usage;
    }

    @Override
    public String getSpecification() {
        String specification = "";
        specification += "Length: " + this.getLength()
                +"\nWidth: " + this.getWidth()
                +"\nHeight: " + this.getHeight()
                +"\nBrand: " + this.getBrand()
                +"\nMaterial: " + this.getMaterial()
                +"\nColour: " + this.getColour()
                +"\nUsage: " + this.getUsage();
        return specification;
    }
}
