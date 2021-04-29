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
import sk.stu.fiit.Model.Product;

/**
 *
 * @author Ivan Vykopal
 */
public class NewProductController extends Controller {

    public NewProductController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnCreateProduct().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createNewProduct();
            }
        });
    }
    
    /**
     * Metóda na vytvorenie nového produktu.
     */
    private void createNewProduct() {
        String name = mainWindow.getTfName2();
        String description = mainWindow.getTaDescription();
        double price = mainWindow.getTfPrice();
        if (price == -1) {
            return;
        }
        if (name.equals("") || description.equals("") || price == 0) {
            JOptionPane.showMessageDialog(mainWindow, "Je potrebné vyplniť všetky polia!");
            return;
        }
        Product product = new Product(name, description, price);
        product = database.addProduct(product);
        if (product == null) {
            JOptionPane.showMessageDialog(mainWindow, "Zadaný produkt už existuje!");
            return;
        }
        
        clearNewProduct();
        JOptionPane.showMessageDialog(mainWindow, "Nový tovar bol vytvorený!");
    }
    
}
