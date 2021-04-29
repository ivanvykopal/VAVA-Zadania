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
public class SecurityConsultant extends Freelancer implements Serializable {
     private boolean auditor;
     
    public SecurityConsultant(double manDay, int experience, String education, ArrayList<String> certificates, boolean auditor) {
        super(manDay, experience, education, certificates);
        this.auditor = auditor;
    }
    
    public SecurityConsultant(int id, double manDay, int experience, String education, ArrayList<String> certificates, boolean auditor) {
        super(manDay, experience, education, certificates);
        this.id = id;
        this.auditor = auditor;
    }

    public boolean isAuditor() {
        return auditor;
    }

    public void setAuditor(boolean auditor) {
        this.auditor = auditor;
    }
    
    
    
}
