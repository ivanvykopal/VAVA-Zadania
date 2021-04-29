/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.SecurityConsultant;

/**
 *
 * @author Ivan Vykopal
 */
public final class AddSpecialistController extends Controller {

    public AddSpecialistController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        mainWindow.removeListeners();
        
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnAddSpecialist().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addSpecialist();
            }
        });
        
        mainWindow.getBtnSpecAddCertificate().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addRow(mainWindow.getTbSpecialistCertificates());
            }
        });
        
        mainWindow.getBtnSpecRemoveCertificate().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                removeRow(mainWindow.getTbSpecialistCertificates(), false);
            }
        });
    }
    
    /**
     * Metóda na pridanie bezpečnosteného konzultanta do databázy.
     */
    private void addSpecialist() {
        double manDay = mainWindow.getTfSpecialistMD();
        int prax = mainWindow.getTfSpecialistPrax();
        if(manDay == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Zlý formát! Využíva sa . namiesto ,!");
            return;
        }
        if (prax == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Zlý formát!");
            return;
        }
        
        String education = (String) mainWindow.getCbSpecialistEducation().getSelectedItem();
        boolean auditor = mainWindow.getChbAuditor().isSelected();
        
        ArrayList<String> certificates = new ArrayList<>();
        int rowsCertificate = mainWindow.getTbSpecialistCertificates().getRowCount();
        
        for (int i = 0; i < rowsCertificate; i++) {
            if (mainWindow.getTbSpecialistCertificates().getValueAt(i, 0) == null) {
                JOptionPane.showMessageDialog(mainWindow, "Nevyplnené pole!");
                return;
            }
            certificates.add((String) mainWindow.getTbSpecialistCertificates().getValueAt(i, 0));
        }
        
        if (manDay == 0 || prax == 0 || education.equals("Vyberte vzdelanie")) {
            JOptionPane.showMessageDialog(mainWindow, "Nie sú vyplnené všetky polia!");
            return;
        }
        
        database.addFreelancer(new SecurityConsultant(manDay, prax, education, certificates, auditor));
        JOptionPane.showMessageDialog(mainWindow, "Bezpečnostný špecialista bol pridaný.");
        clearAll();
    }
    
}
