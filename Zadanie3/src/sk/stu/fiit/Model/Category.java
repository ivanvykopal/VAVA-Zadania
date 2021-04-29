/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.io.Serializable;

/**
 *
 * @author Ivan Vykopal
 */
public class Category implements Serializable {
    private String description;
    private double price;
    
    public Category() {}

    public Category(String description, double price) {
        this.description = description;
        this.price = price;
    }
    
    public boolean hasEmptyAttribute() {
        return description.equals("") || price == 0;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        Category cat = (Category) obj;
        return description.equals(cat.getDescription()) && price == cat.getPrice();
    }
    
    
    
}