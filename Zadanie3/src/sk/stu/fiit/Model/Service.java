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
public class Service implements Serializable {
    private String name;
    private String description;
    private double price;
    
    public Service() {}

    public Service(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    public boolean hasEmptyAttribute() {
        return name.equals("") || description.equals("") || price == 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Service ser = (Service) obj;
        return name.equals(ser.getName()) && description.equals(ser.getDescription()) && price == ser.getPrice();
    }

    
}
