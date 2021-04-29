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
import sk.stu.fiit.Model.Administrator;
import sk.stu.fiit.Model.Database;

/**
 *
 * @author Ivan Vykopal
 */
public final class AddAdministratorController extends Controller {

    public AddAdministratorController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        mainWindow.removeListeners();
        
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnAddAdministrator().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addAdministrator();
            }
        });
        
        mainWindow.getBtnAdmAddCertificate().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addRow(mainWindow.getTbAdmCertificate());
            }
        });
        
        mainWindow.getBtnAdmAddType().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addRow(mainWindow.getTbAdmTypes());
            }
        });
        
        mainWindow.getBtnAdmRemoveCertificate().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                removeRow(mainWindow.getTbAdmCertificate(), false);
            }
        });
        
        mainWindow.getBtnAdmRemoveType().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                removeRow(mainWindow.getTbAdmTypes(), true);
            }
        });

    }
    
    /**
     * Metóda na pridanie adminsitrátora do databázy.
     */
    private void addAdministrator() {
        double manDay = mainWindow.getTfAdministratorMD();
        int prax = mainWindow.getTfAdministratorPrax();
        if(manDay == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Zlý formát! Využíva sa . namiesto ,!");
            return;
        }
        if (prax == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Zlý formát!");
            return;
        }
        
        String education = (String) mainWindow.getCbAdministratorEducation().getSelectedItem();
        String platform = mainWindow.getTfAdministratorPlatform();
        
        int rowsCertificate = mainWindow.getTbAdmCertificate().getRowCount();
        int rowsType = mainWindow.getTbAdmTypes().getRowCount();
        ArrayList<String> certificates = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        
        for (int i = 0; i < rowsType; i++) {
            if (mainWindow.getTbAdmTypes().getValueAt(i, 0) == null) {
                JOptionPane.showMessageDialog(mainWindow, "Nevyplnené pole!");
                return;
            }
            types.add((String) mainWindow.getTbAdmTypes().getValueAt(i, 0));
        }
        
        for (int i = 0; i < rowsCertificate; i++) {
            if (mainWindow.getTbAdmCertificate().getValueAt(i, 0) == null) {
                JOptionPane.showMessageDialog(mainWindow, "Nevyplnené pole!");
                return;
            }
            certificates.add((String) mainWindow.getTbAdmCertificate().getValueAt(i, 0));
        }
        
        if (manDay == 0 || prax == 0 || education.equals("Vyberte vzdelanie") || platform.equals("") 
              || types.isEmpty()) {
            JOptionPane.showMessageDialog(mainWindow, "Nie sú vyplnené všetky polia!");
            return;
        }
        
        database.addFreelancer(new Administrator(manDay, prax, education, certificates, types, platform));
        JOptionPane.showMessageDialog(mainWindow, "Administrátor bol pridaný.");
        clearAll();
    }
    
}
