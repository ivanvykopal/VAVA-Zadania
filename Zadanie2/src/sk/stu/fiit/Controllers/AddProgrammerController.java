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
import sk.stu.fiit.Model.Programmer;

/**
 *
 * @author Ivan Vykopal
 */
public final class AddProgrammerController extends Controller {

    public AddProgrammerController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        mainWindow.removeListeners();
        
        initController();
    }
    
    @Override
    void initController() {
        mainWindow.getBtnAddProgrammer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addProgrammer();
            }
        });
        
        mainWindow.getBtnProgAddCertificate().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addRow(mainWindow.getTbProgrammerCertificate());
            }
        });
        
        mainWindow.getBtnProgAddType().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addRow(mainWindow.getTbProgrammerType());
            }
        });
        
        mainWindow.getBtnProgRemoveCertificate().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                removeRow(mainWindow.getTbProgrammerCertificate(), false);
            }
        });
        
        mainWindow.getBtnProgRemoveType().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                removeRow(mainWindow.getTbProgrammerType(), true);
            }
        });
    }
    
    /**
     * Metóda na pridanie programátora do databázy.
     */
    private void addProgrammer() {
        double manDay = mainWindow.getTfProgrammerMD();
        int prax = mainWindow.getTfProgrammerPrax();
        if(manDay == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Zlý formát! Využíva sa . namiesto ,!");
            return;
        }
        if (prax == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Zlý formát!");
            return;
        }
        
        String education = (String) mainWindow.getCbProgrammerEducation().getSelectedItem();
        
        int rowsCertificate = mainWindow.getTbProgrammerCertificate().getRowCount();
        int rowsType = mainWindow.getTbProgrammerType().getRowCount();
        ArrayList<String> certificates = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        
        for (int i = 0; i < rowsType; i++) {
            if (mainWindow.getTbProgrammerType().getValueAt(i, 0) == null) {
                JOptionPane.showMessageDialog(mainWindow, "Nevyplnené pole!");
                return;
            }
            types.add((String) mainWindow.getTbProgrammerType().getValueAt(i, 0));
        }
        
        for (int i = 0; i < rowsCertificate; i++) {
            if (mainWindow.getTbProgrammerCertificate().getValueAt(i, 0) == null) {
                JOptionPane.showMessageDialog(mainWindow, "Nevyplnené pole!");
                return;
            }
            certificates.add((String) mainWindow.getTbProgrammerCertificate().getValueAt(i, 0));
        }
        
        if (manDay == 0 || prax == 0 || education.equals("Vyberte vzdelanie") || types.isEmpty()) {
            JOptionPane.showMessageDialog(mainWindow, "Nie sú vyplnené všetky polia!");
            return;
        }
        
        database.addFreelancer(new Programmer(manDay, prax, education, certificates, types));
        JOptionPane.showMessageDialog(mainWindow, "Programátor bol pridaný.");
        clearAll();
    }
 
}
