/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Database;

/**
 *
 * @author Ivan Vykopal
 */
public abstract class Controller {
    protected final MainWindow mainWindow;
    protected final Database database;
    
    protected Controller(Database database, MainWindow mainWindow) {
        this.database = database;
        this.mainWindow = mainWindow;
    }
    
    abstract void initController();
    
    /**
     * Metóda pre premazanie všetkých komponentov.
     */
    protected void clearAll() {
        clearAddProgrammer();
        clearAddSpecialist();
        clearAddAdminsitrator();
        clearAddEmployer();
        clearCreateRent();
        clearRemoveRent();
        clearViewJob();
        clearAddJob();
    }
    
    /**
     * Metóda pre premazanie všetkých komponentov panelu AddProgrammer.
     */
    protected void clearAddProgrammer() {
        mainWindow.setTfProgrammerMD("");
        mainWindow.setTfProgrammerPrax("");
        mainWindow.getCbProgrammerEducation().setSelectedIndex(0);
        mainWindow.getTbProgrammerType().setRowCount(0);
        addRow(mainWindow.getTbProgrammerType());
        mainWindow.getTbProgrammerCertificate().setRowCount(0);
    }
    
    /**
     * Metóda pre premazanie všetkých komponentov panelu AddSpecialist.
     */
    protected void clearAddSpecialist() {
        mainWindow.setTfSpecialistMD("");
        mainWindow.setTfSpecialistPrax("");
        mainWindow.getCbSpecialistEducation().setSelectedIndex(0);
        mainWindow.getTbSpecialistCertificates().setRowCount(0);
        mainWindow.getChbAuditor().setSelected(false);
    }
    
    /**
     * Metóda pre premazanie všetkých komponentov panelu AddAdministrator.
     */
    protected void clearAddAdminsitrator() {
        mainWindow.setTfAdministratorMD("");
        mainWindow.setTfAdministratorPrax("");
        mainWindow.setTfAdministratorPlatform("");
        mainWindow.getCbAdministratorEducation().setSelectedIndex(0);
        mainWindow.getTbAdmTypes().setRowCount(0);
        addRow(mainWindow.getTbAdmTypes());
        mainWindow.getTbAdmCertificate().setRowCount(0);
    }
    
    /**
     * Metóda pre premazanie všetkých komponentov panelu AddEmployer.
     */
    protected void clearAddEmployer() {
        mainWindow.setTfName("");
        mainWindow.setTfField("");
        mainWindow.setTfEmployeesCount("");
        mainWindow.setLbAddEmployerImage(null);
    }
    
    /**
     * Metóda pre premazanie všetkých komponentov panelu CreateRent.
     */
    protected void clearCreateRent() {
        mainWindow.setLbNameEmployer("");
        mainWindow.setLbFieldEmployer("");
        mainWindow.setLbCountEmployer("");
        mainWindow.setLbLogoEmployer(null);
        mainWindow.getCbSpecialist().setSelectedIndex(0);
    }
    
    /**
     * Metóda pre premazanie všetkých komponentov panelu RemoveRent.
     */
    protected void clearRemoveRent() {
        mainWindow.getTbChoosedSpecialistEmp().setRowCount(0);
    }
    
    /**
     * Metóda pre premazanie všetkých komponentov panelu ViewJob.
     */
    protected void clearViewJob() {
        mainWindow.getTbJobsModel().setRowCount(0);
        mainWindow.setLbEmployerJob("Zamestnávateľ:");
        mainWindow.setLbNameJob("Názov ponuky:");
        mainWindow.setLbManDayJob("Cena za deň:");
    }
    
    /**
     * Metóda pre premazanie všetkých komponentov panelu AddJob.
     */
    protected void clearAddJob() {
        mainWindow.setTfJobManDay("");
        mainWindow.setTfNameJob("");
        mainWindow.setTfEmployerNameJob("");
    }
    
    /**
     * Metóda pre pridanie riadka do tabuľky.
     * @param model model tabuľky, do ktorej sa riadok pridá
     */
    protected void addRow(DefaultTableModel model) {
        Object[] row = {null};
        model.addRow(row);
    }
    
    /**
     * Metóda pre odobratie riadka z tabuľky.
     * @param model model tabuľky, z ktorej sa riadok odoberie
     * @param testOne informácia o tom, či tabuľka musí obsahovať minimálne jeden riadok
     */
    protected void removeRow(DefaultTableModel model, boolean testOne) {
        int indexRow = model.getRowCount() - 1;
        if (testOne) {
            if (indexRow > 0) {
                model.removeRow(indexRow);
            }
        } else {
            if (indexRow >= 0) {
                model.removeRow(indexRow);
            }
        }
    }
    
     /**
     * Metóda pre skrytie všetkých panelov.
     */
    protected void hideAll() {
        mainWindow.getpAddEmployer().setVisible(false);
        mainWindow.getpAddSpecialist().setVisible(false);
        mainWindow.getSpCreateRent().setVisible(false);
        mainWindow.getpRemoveRent().setVisible(false);
        mainWindow.getpViewJobs().setVisible(false);
        mainWindow.getpAddJob().setVisible(false);
    }
    
    /**
     * Metóda pre nastavenie fontu všetkých labelov na plain.
     */
    protected void clearFont() {
        mainWindow.setLbAddEmployerFont(new Font("Segoe UI", 0, 20));
        mainWindow.setLbAddJobFont(new Font("Segoe UI", 0, 20));
        mainWindow.setLbAddRentFont(new Font("Segoe UI", 0, 20));
        mainWindow.setLbAddSpecialistFont(new Font("Segoe UI", 0, 20));
        mainWindow.setLbRemoveRentFont(new Font("Segoe UI", 0, 20));
        mainWindow.setLbJobsFont(new Font("Segoe UI", 0, 20));
    }
}
