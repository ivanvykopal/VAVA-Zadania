/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Database;

/**
 *
 * @author Ivan Vykopal
 */
public class MainController extends Controller {

    public MainController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        
        mainWindow.setVisible(true);
        hideAll();
        clearFont();
        mainWindow.setLbJobsFont(new Font("Segoe UI", 1, 20));
        mainWindow.getpViewJobs().setVisible(true);
        new ViewJobController(database, mainWindow);
    }

    @Override
    public void initController() {
        mainWindow.getLbAddEmployer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mainWindow.removeListeners();
                clearAll();
                hideAll();
                clearFont();
                mainWindow.setLbAddEmployerFont(new Font("Segoe UI", 1, 20));
                mainWindow.getpAddEmployer().setVisible(true);
                new AddEmployerController(database, mainWindow);
            }
        });
        
        mainWindow.getLbAddRent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mainWindow.removeListeners();
                clearAll();
                hideAll();
                clearFont();
                mainWindow.setLbAddRentFont(new Font("Segoe UI", 1, 20));
                mainWindow.getSpCreateRent().setVisible(true);
                new CreateRentController(database, mainWindow, null);
            }
        });
        
        mainWindow.getLbAddSpecialist().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mainWindow.removeListeners();
                clearAll();
                hideAll();
                clearFont();
                mainWindow.setLbAddSpecialistFont(new Font("Segoe UI", 1, 20));
                mainWindow.getpAddSpecialist().setVisible(true);
                new AddFreelancerController(database, mainWindow);
            }
        });
        mainWindow.getLbJobs().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mainWindow.removeListeners();
                clearAll();
                hideAll();
                clearFont();
                mainWindow.setLbJobsFont(new Font("Segoe UI", 1, 20));
                mainWindow.getpViewJobs().setVisible(true);
                new ViewJobController(database, mainWindow);
            }
        });
        
        mainWindow.getLbRemoveRent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mainWindow.removeListeners();
                clearAll();
                hideAll();
                clearFont();
                mainWindow.setLbRemoveRentFont(new Font("Segoe UI", 1, 20));
                mainWindow.getpRemoveRent().setVisible(true);
                new RemoveRentController(database, mainWindow);
            }
        });
        
        mainWindow.getLbAddJob().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mainWindow.removeListeners();
                clearAll();
                hideAll();
                clearFont();
                mainWindow.setLbAddJobFont(new Font("Segoe UI", 1, 20));
                mainWindow.getpAddJob().setVisible(true);
                new AddJobController(database, mainWindow);
            }
        });
        
    }
    
}
