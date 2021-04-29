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
public class Job implements Serializable {
    private String employerName;
    private String name;
    private double manDay;

    public Job(String employerName, String name, double manDay) {
        this.employerName = employerName;
        this.manDay = manDay;
        this.name = name;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getManDay() {
        return manDay;
    }

    public void setManDay(double manDay) {
        this.manDay = manDay;
    }

    
    
}
