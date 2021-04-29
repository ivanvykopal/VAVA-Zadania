/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ivan Vykopal
 */
public abstract class Freelancer implements Serializable {
    protected int id;
    protected double manDay;
    protected int experience;
    protected String education;
    protected ArrayList<String> certificates;
    protected boolean rented = false;

    public Freelancer( double manDay, int experience, String education, ArrayList<String> certificates) {
        this.manDay = manDay;
        this.experience = experience;
        this.education = education;
        this.certificates = certificates;
    }

    public double getManDay() {
        return manDay;
    }

    public void setManDay(double manDay) {
        this.manDay = manDay;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public ArrayList<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(ArrayList<String> certificates) {
        this.certificates = certificates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
    
    
    
}
