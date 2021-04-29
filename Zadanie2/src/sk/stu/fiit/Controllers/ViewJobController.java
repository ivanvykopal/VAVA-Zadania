/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Job;

/**
 *
 * @author Ivan Vykopal
 */
public final class ViewJobController extends Controller {

    public ViewJobController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        fillJobsTable();
        
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnViewJob().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                viewJob();
            }
        });
        
        mainWindow.getBtnChooseJob().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                chooseJob();
            }
        });
    }
    
    /**
     * Metóda pre zobrazenie vybranej pracovnej ponuky.
     */
    private void viewJob() {
        int index = mainWindow.getTbJobsTable().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nebol vybraný žiaden záznam!");
            return;
        }
        
        Job job = database.getJob(index);
        
        mainWindow.setLbEmployerJob("Zamestnávateľ: " +job.getEmployerName());
        mainWindow.setLbNameJob("Názov ponuky: " + job.getName());
        mainWindow.setLbManDayJob("Cena za deň: " + job.getManDay());
    }
    
     /**
     * Metóda pre vytvorenie prenájmu pre danú ponuku.
     */
    private void chooseJob() {
        int index = mainWindow.getTbJobsTable().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nebol vybraný žiaden záznam!");
            return;
        }
        
        Job job = database.getJob(index);
        
        mainWindow.removeListeners();
        clearAll();
        hideAll();
        clearFont();
        mainWindow.setLbAddRentFont(new Font("Segoe UI", 1, 20));
        mainWindow.getSpCreateRent().setVisible(true);
        new CreateRentController(database, mainWindow, job);
        
    }
    
    /**
     * Metóda pre naplnenie tabuľky pracovných ponúk.
     */
    private void fillJobsTable() {
        mainWindow.getTbJobsModel().setRowCount(0);
        for (Job job: database.getJobs()) {
            Object[] row = new Object[3];
            row[0] = job.getEmployerName();
            row[1] = job.getName();
            row[2] = job.getManDay();
            mainWindow.getTbJobsModel().addRow(row);
        }
    }
    
}
