/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Database;

/**
 *
 * @author Ivan Vykopal
 */
public final class AddFreelancerController extends Controller{

    public AddFreelancerController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        
        hideOthers();
        mainWindow.getRbProgrammer().setSelected(true);
        mainWindow.getpProgrammer().setVisible(true);
        new AddProgrammerController(database, mainWindow);
        
        initController();
    }

    @Override
    void initController() {
        mainWindow.getRbAdministrator().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    clearAll();
                    hideOthers();
                    mainWindow.getpAdministrator().setVisible(true);
                    new AddAdministratorController(database, mainWindow);
                }
            }
        });
        
        mainWindow.getRbProgrammer().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    clearAll();
                    hideOthers();
                    mainWindow.getpProgrammer().setVisible(true);
                    new AddProgrammerController(database, mainWindow);
                }
            }
        });
        
        mainWindow.getRbSpecialist().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    clearAll();
                    hideOthers();
                    mainWindow.getpSpecialist().setVisible(true);
                    new AddSpecialistController(database, mainWindow);
                }
            }
        });
    }
    
    /**
     * Metóda pre skrytie panelov.
     */
    private void hideOthers() {
        mainWindow.getpAdministrator().setVisible(false);
        mainWindow.getpSpecialist().setVisible(false);
        mainWindow.getpProgrammer().setVisible(false);
    }
    
}
