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
public class Programmer extends Freelancer  implements Serializable {
    private ArrayList<String> types;
    
    public Programmer(double manDay, int experience, String education, ArrayList<String> certificates, ArrayList<String> types) {
        super(manDay, experience, education, certificates);
        this.types = types;
    }
    
    public Programmer(int id, double manDay, int experience, String education, ArrayList<String> certificates, ArrayList<String> types) {
        super(manDay, experience, education, certificates);
        this.id = id;
        this.types = types;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> type) {
        this.types = type;
    }
    
    
    
}
