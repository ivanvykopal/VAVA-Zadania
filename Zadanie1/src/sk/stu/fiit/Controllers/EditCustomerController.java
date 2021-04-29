/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Customer;
import sk.stu.fiit.Model.Database;

/**
 *
 * @author Ivan Vykopal
 */
public class EditCustomerController extends Controller {
    private int index = -1;

    public EditCustomerController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnEditCustomer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editCustomer();
            }
        });
        
        mainWindow.getBtnFindCustomer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                findCustomer();
            }
        });
    }
    
    /**
     * Metóda na nájdenie zákazníka v databáze a následné predvyplnenie TefFieldov.
     */
    private void findCustomer() {
        DefaultTableModel model = mainWindow.getTbCustomersModel();
        index = mainWindow.getTbCustomersTable().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nie je vybraný žiaden záznam!");
            return;
        }
        String name = model.getValueAt(index, 0).toString();
        String address = model.getValueAt(index, 1).toString();
        String number = model.getValueAt(index, 2).toString();
        String town = model.getValueAt(index, 3).toString();
        String psc = model.getValueAt(index, 4).toString();
       
        mainWindow.setTfName3(name);
        mainWindow.setTfAddress2(address);
        mainWindow.setTfNumber2(number);
        mainWindow.setTfTown2(town);
        mainWindow.setTfPSC2(psc);
    }
    
    /**
     * Metóda na úpravu informácií vybraného zákazníka.
     */
    private void editCustomer() {
        String newName = mainWindow.getTfName3();
        String address = mainWindow.getTfAddress2();
        String number = mainWindow.getTfNumber2();
        String town = mainWindow.getTfTown2();
        String psc = mainWindow.getTfPSC2();
        if (newName.equals("") || address.equals("") || number.equals("") 
                || town.equals("") || psc.equals("")) {
            JOptionPane.showMessageDialog(mainWindow, "Je potrebné vyplniť všetky polia!");
            return;
        }
        Customer customer = new Customer(newName, address, number, town, psc);
        if (index == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nie je vybraný žiaden záznam!");
            return;
        }
        customer = database.editCustomer(index, customer);
        if (customer == null) {
            JOptionPane.showMessageDialog(mainWindow, "Daný používateľ už existuje!");
            return;
        }
        
        clearEditCustomer();
        JOptionPane.showMessageDialog(mainWindow, "Používateľ bol upravený!");
        fillCustomerTable();
        index = -1;
    }
    
}
