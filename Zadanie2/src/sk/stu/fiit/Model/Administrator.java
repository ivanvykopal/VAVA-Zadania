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
public class Administrator extends Freelancer implements Serializable {
    private ArrayList<String> types;
    private String preferredPlatform;
    
    public Administrator(double manDay, int experience, String education, ArrayList<String> certificates, ArrayList<String> types, String platform) {
        super(manDay, experience, education, certificates);
        this.types = types;
        this.preferredPlatform = platform;
    }
    
    public Administrator(int id, double manDay, int experience, String education, ArrayList<String> certificates, ArrayList<String> types, String platform) {
        super(manDay, experience, education, certificates);
        this.id = id;
        this.types = types;
        this.preferredPlatform = platform;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public String getPreferredPlatform() {
        return preferredPlatform;
    }

    public void setPreferredPlatform(String preferredPlatform) {
        this.preferredPlatform = preferredPlatform;
    }
    
    
    
}
