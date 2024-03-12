package com.example.furnishings.models;

public class Lighting extends ProductWithoutSize{
    private String lightColour;
    private String watt;


    public Lighting(){
        super();
    }

    public Lighting(String name, Double price, String material, String description,
                    String category, String popular, Boolean favourite, String brand,
                    String lightColour, String watt) {
        super(name, price, material, description, category, popular, favourite, brand);
        this.lightColour = lightColour;
        this.watt = watt;
    }

    public String getLightColour(){
        return lightColour;
    }
    public String getWatt(){
        return watt;
    }

    @Override
    public String getSpecification() {
        String specification = "";
        specification += "Light Colour: " + this.getLightColour()
                +"\nBrand: " + this.getBrand()
                +"\nMaterial: " + this.getMaterial()
                +"\nWatt: " + this.getWatt();
        return specification;
    }


}
