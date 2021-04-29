/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Ivan Vykopal
 */
public class Room implements Serializable {
    private Category category;
    private String label;
    private String description;
    private ArrayList<ImageIcon> images;
    
    public Room() {
     this.images = new ArrayList<>();
    }

    public Room(Category category, String label, String description) {
        this.label = label;
        this.description = description;
        this.images = new ArrayList<>();
    }
    
    public boolean hasEmptyAttribute() {
        return category == null || label.equals("") || description.equals("");
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<ImageIcon> getImages() {
        return images;
    }
    
    public void addImage(ImageIcon image) {
        this.images.add(image);
    }

    public void setImages(ArrayList<ImageIcon> images) {
        this.images = images;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
}
