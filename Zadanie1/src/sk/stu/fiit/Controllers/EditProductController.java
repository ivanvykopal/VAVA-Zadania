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
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Product;

/**
 *
 * @author Ivan Vykopal
 */
public class EditProductController extends Controller {
    private int index = -1;
    
    public EditProductController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnEditProduct().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editProduct();
            }
        });
        
        mainWindow.getBtnFindProduct().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                findProduct();
            }
        });
    }
    
    /**
     * Metóda na nájdenie produktu v databáze a následné predvyplnenie TefFieldov.
     */
    private void findProduct() {
        DefaultTableModel model = mainWindow.getTbProducts2Model();
        index = mainWindow.getTbProducts2Table().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nie je vybraný žiaden záznam!");
            return;
        }
        String name = model.getValueAt(index, 0).toString();
        String description = model.getValueAt(index, 1).toString();
        String price = model.getValueAt(index, 2).toString();
        
        mainWindow.setTfName5(name);
        mainWindow.setTaDescription1(description);
        mainWindow.setTfPrice1(price);
    }
    
    /**
     * Metóda na úpravu informácií vybraného produktu.
     */
    private void editProduct() {
        String newName = mainWindow.getTfName5();
        String description = mainWindow.getTaDescription1();
        double price = mainWindow.getTfPrice1();
        if (price == -1) {
            return;
        }
        if (newName.equals("") || description.equals("") || price == 0) {
            JOptionPane.showMessageDialog(mainWindow, "Je potrebné vyplniť všetky polia!");
            return;
        }
        Product product = new Product(newName, description, price);
        if (index == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nie je vybraný žiaden záznam!");
            return;
        }
        product = database.editProduct(index, product);
        if (product == null) {
            JOptionPane.showMessageDialog(mainWindow, "Daný tovar už existuje!");
            return;
        }
        
        clearEditProduct();
        JOptionPane.showMessageDialog(mainWindow, "Tovar bol upravený!");
        fillProductsTable();
        index = -1;
    }
    
}
