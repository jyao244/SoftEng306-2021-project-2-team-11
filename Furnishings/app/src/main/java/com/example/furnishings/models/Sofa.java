package com.example.furnishings.models;

public class Sofa extends ProductWithoutSize{
    private String usage;
    private int applicableNum;

    public Sofa(){
        super();
    }

    public Sofa(String name, Double price, String material, String description, String category,
                String popular, Boolean favourite, String brand, String usage, int applicableNum) {
        super(name, price, material, description, category, popular, favourite, brand);
        this.usage = usage;
        this.applicableNum = applicableNum;
    }

    public String getUsage(){
        return usage;
    }
    public int getApplicableNum(){
        return applicableNum;
    }

    @Override
    public String getSpecification() {
        String specification = "";
        specification += "Brand: " + this.getBrand()
                +"\nMaterial: " + this.getMaterial()
                +"\nApplicableNumber: " + this.getApplicableNum()
                +"\nUsage: " + this.getUsage();
        return specification;
    }
}
