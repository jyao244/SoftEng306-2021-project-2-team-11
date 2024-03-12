package com.example.furnishings.firebaseOperations;

import com.example.furnishings.models.*;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Class use to deal with polymorphism of data get from Firestore and correctly handle it
 * into corresponding classes and provides methods to provide specific values
 */
public class ProductProvider {

    private String category;
    private DocumentSnapshot snapshot;

    private String name;
    private Double price;
    private String description;
    private String specification;
    private Boolean isfvt;

    public ProductProvider(String category, DocumentSnapshot snapshot) {
        this.category = category;
        this.snapshot = snapshot;
    }

    public void setProduct() {
        if (category.equals("Table")) {
            Table product = snapshot.toObject(Table.class);
            name = product.getName();
            price = product.getPrice();
            description = product.getDescription();
            specification = product.getSpecification();
            isfvt = product.getFavourite();
        }else if(category.equals("Sofa")){
            Sofa product = snapshot.toObject(Sofa.class);
            name = product.getName();
            price = product.getPrice();
            description = product.getDescription();
            specification = product.getSpecification();
            isfvt = product.getFavourite();
        }else if(category.equals("Chair")){
            Chair product = snapshot.toObject(Chair.class);
            name = product.getName();
            price = product.getPrice();
            description = product.getDescription();
            specification = product.getSpecification();
            isfvt = product.getFavourite();
        }
        else if(category.equals("Bed")){
            Bed product = snapshot.toObject(Bed.class);
            name = product.getName();
            price = product.getPrice();
            description = product.getDescription();
            specification = product.getSpecification();
            isfvt = product.getFavourite();
        }
        else if(category.equals("Lighting")){
            Lighting product = snapshot.toObject(Lighting.class);
            name = product.getName();
            price = product.getPrice();
            description = product.getDescription();
            specification = product.getSpecification();
            isfvt = product.getFavourite();
        }
    }
    public String getImgLocation1() {
        String imgLocation1 = ("Product Images/"+category+"/"+name+"/1.jpg");
        return imgLocation1;
    }

    public String getImgLocation2() {
        String imgLocation2 = ("Product Images/"+category+"/"+name+"/2.jpg");
        return imgLocation2;
    }

    public String getImgLocation3() {
        String imgLocation3 = ("Product Images/"+category+"/"+name+"/3.jpg");
        return imgLocation3;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getSpecification() {
        return specification;
    }

    public boolean getFavourite() {
        return isfvt;
    }
}


