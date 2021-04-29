/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Administrator;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Freelancer;
import sk.stu.fiit.Model.Programmer;
import sk.stu.fiit.Model.Rent;
import sk.stu.fiit.Model.SecurityConsultant;

/**
 *
 * @author Ivan Vykopal
 */
public final class RemoveRentController extends Controller {
    private int rentId = -1;

    public RemoveRentController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        
        fillRents();
        
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnRemoveRent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                removeRent();
            }
        });
        
        mainWindow.getBtnChooseRent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                chooseRent();
            }
        });
    }

    /**
     * Metóda pre vymazanie prenájmu z databázy a uvoľnenie špecialistov.
     */
    private void removeRent() {
        if (rentId == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nie je vybraný žiaden záznam!");
            return;
        }
        
        database.removeRent(rentId);
        JOptionPane.showMessageDialog(mainWindow, "Záznam prenájmu bol vymazaný!");
        mainWindow.getTbChoosedSpecialistEmp().setRowCount(0);
        fillRents();
        rentId = -1;
        clearAll();
        
    }
    
    /**
     * Metóda pre výber prenájmu z tabuľky prenájmov.
     */
    private void chooseRent() {
        mainWindow.getTbChoosedSpecialistEmp().setRowCount(0);
        int index = mainWindow.getTbRentsTable().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nie je vybraný žiaden záznam!");
            return;
        }
        
        rentId = (int) mainWindow.getTbRentsModel().getValueAt(index, 0);
        Rent rent = database.findRent(rentId);
        
        for (Freelancer freelancer: rent.getFreelancers()) {
            Object[] row = new Object[8];
            if (freelancer instanceof Programmer) {
                row[0] = "Programátor";
                row[4] = String.join(", ", ((Programmer) freelancer).getTypes());
                row[6]= "-";
                row[7] = "-";
            } else if (freelancer instanceof Administrator) {
                row[0] = "Administrátor";
                row[4] = String.join(", ", ((Administrator) freelancer).getTypes());
                row[6]= "-";
                row[7] = ((Administrator) freelancer).getPreferredPlatform();
            } else {
                row[0] = "Bezpečnostný konzultant";
                row[4] = "-";
                if (((SecurityConsultant) freelancer).isAuditor()) {
                    row[6] = "Áno";
                } else {
                    row[6] = "Nie";
                }
                row[7] = "-";
            }
            row[1] = freelancer.getManDay();
            row[2] = freelancer.getExperience();
            row[3] = freelancer.getEducation();
            row[5] = String.join(", ", freelancer.getCertificates());
            mainWindow.getTbChoosedSpecialistEmp().addRow(row);
        }
    }
    
    /**
     * Metód pre naplnenie tabuľky aktuálnych prenájmov.
     */
    private void fillRents() {
        mainWindow.getTbRentsModel().setRowCount(0);
        
        for (Rent rent : database.getRents()) {
            Object[] row = new Object[2];
            row[0] = rent.getId();
            row[1] = rent.getEmployer().getName();
            mainWindow.getTbRentsModel().addRow(row);
        }
    }
    
}
