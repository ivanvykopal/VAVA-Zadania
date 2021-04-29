/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author Ivan Vykopal
 */
public class Employer implements Serializable {
    private String name;
    private String businessArea;
    private int employeesCount;
    private BufferedImage logo;

    public Employer(String name, String businessArea, int employeesCount, BufferedImage logo) {
        this.name = name;
        this.businessArea = businessArea;
        this.employeesCount = employeesCount;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public int getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(int employeesCount) {
        this.employeesCount = employeesCount;
    }

    public BufferedImage getLogo() {
        return logo;
    }

    public void setLogo(BufferedImage logo) {
        this.logo = logo;
    }
    
    
}
