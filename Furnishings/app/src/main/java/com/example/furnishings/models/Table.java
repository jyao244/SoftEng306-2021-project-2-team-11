package com.example.furnishings.models;

public class Table extends ProductWithSize{
    private String colour;
    private String shape;
    private int applicableNum;
    private String usage;

    public Table(){
        super();
        //public no-arg constructor needed
    }
    public Table(String name, Double price, String material, String description,
                           String category, String popular, Boolean favourite, String brand,
                           String width, String length, String height, String colour,
                 String shape, int applicableNum, String usage){
        super(name, price, material, description, category, popular, favourite, brand, width, length, height);
        this.colour = colour;
        this.shape = shape;
        this.applicableNum = applicableNum;
        this.usage = usage;
    }

    public String getColour(){
        return colour;
    }
    public String getShape(){
        return shape;
    }
    public int getApplicableNum(){
        return applicableNum;
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
                +"\nShape: " + this.getShape()
                +"\nApplicableNumber: " + this.getApplicableNum()
                +"\nUsage: " + this.getUsage();
        return specification;
    }
}
