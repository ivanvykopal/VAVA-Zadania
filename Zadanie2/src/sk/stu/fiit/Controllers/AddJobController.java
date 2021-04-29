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
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Employer;
import sk.stu.fiit.Model.Job;

/**
 *
 * @author Ivan Vykopal
 */
public final class AddJobController extends Controller {

    public AddJobController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnAddJob().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addJob();
            }
        });
    }
    
    /**
     * Metóda na pridanie pracovnej ponuky do databázy.
     */
    private void addJob() {
        String name = (String) mainWindow.getTfNameJob();
        double manDay = mainWindow.getTfJobManDay();
        String employer = (String) mainWindow.getTfEmployerNameJob();
        
        if (manDay == 0  || name.equals("") || employer.equals("")) {
            JOptionPane.showMessageDialog(mainWindow, "Nie sú vyplnené všetky polia!");
            return;
        }
        
        if(manDay == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Zlý formát! Využíva sa . namiesto ,!");
            return;
        }
        
        Employer emp = database.getEmployer(employer);
        if (emp == null) {
            JOptionPane.showMessageDialog(mainWindow, "Zadaný zamestnávateľ nie je v databáze.");
            return;
        }
        
        database.addJob(new Job(employer, name, manDay));
        JOptionPane.showMessageDialog(mainWindow, "Pracovná ponuka bol pridaná.");
        clearAll();
    }
    
}
