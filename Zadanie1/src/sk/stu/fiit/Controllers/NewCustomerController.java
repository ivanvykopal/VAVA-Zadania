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
import sk.stu.fiit.Model.Customer;
import sk.stu.fiit.Model.Database;

/**
 *
 * @author Ivan Vykopal
 */
public class NewCustomerController extends Controller {

    public NewCustomerController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnCreateCustomer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createNewCustomer();
            }
        });
    }
    
    /**
     * Metóda na vytvorenie nového zákazníka.
     */
    private void createNewCustomer() {
        String name = mainWindow.getTfName1();
        String address = mainWindow.getTfAddress1();
        String number = mainWindow.getTfNumber1();
        String town = mainWindow.getTfTown1();
        String psc = mainWindow.getTfPSC1();
        if (name.equals("") || address.equals("") || number.equals("") 
                || town.equals("") || psc.equals("")) {
            JOptionPane.showMessageDialog(mainWindow, "Je potrebné vyplniť všetky polia!");
            return;
        }
        Customer customer = new Customer(name, address, number, town, psc);
        
        customer = database.addCutomer(customer);
        if (customer == null) {
            JOptionPane.showMessageDialog(mainWindow, "Zadaný používateľ už existuje!");
            return;
        }
        
        clearNewCustomer();
        JOptionPane.showMessageDialog(mainWindow, "Nový použivateľ bol vytvorený!");
    }
    
}
