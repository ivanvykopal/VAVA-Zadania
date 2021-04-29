/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import sk.stu.fiit.Model.Database;
import sk.stu.fiit.GUI.About;
import sk.stu.fiit.GUI.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public class MainWindowController extends Controller {

    public MainWindowController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        setNewInvoice();
        
        mainWindow.setVisible(true);
    }
    
    @Override
    public void initController() {
        mainWindow.getMiEnd().addActionListener(e -> System.exit(0));
        mainWindow.getMiAbout().addActionListener(e -> new About().setVisible(true));
        mainWindow.getMiNewInvoice().addActionListener(e -> setNewInvoice());
        mainWindow.getMiNewCustomer().addActionListener(e -> setNewCustomer());
        mainWindow.getMiNewProduct().addActionListener(e -> setNewProduct());
        mainWindow.getMiEditProduct().addActionListener(e -> setEditProduct());
        mainWindow.getMiEditCustomer().addActionListener(e -> setEditCustomer());
        mainWindow.getMiViewInvoices().addActionListener(e -> setViewInvoices());
    }
    
    /**
     * Metóda nastaví zobrazenie panelu pre pridávanie zákazníka.
     */
    private void setNewCustomer() {
        mainWindow.removeListeners();
        clearAll();
        hideAll();
        mainWindow.getSpNewCustomer().setVisible(true);
        new NewCustomerController(database, mainWindow);
    }
    
    /**
     * Metóda nastaví zobrazenie panelu pre pridávanie faktúry.
     */
    private void setNewInvoice() {
        mainWindow.removeListeners();
        clearAll();
        hideAll();
        mainWindow.getSpNewInvoice().setVisible(true);
        new NewInvoiceController(database, mainWindow);
    }
    
    /**
     * Metóda nastaví zobrazenie panelu pre pridávanie produktu.
     */
    private void setNewProduct() {
        mainWindow.removeListeners();
        clearAll();
        hideAll();
        mainWindow.getSpNewProduct().setVisible(true);
        new NewProductController(database, mainWindow);
    }
    
    /**
     * Metóda nastaví zobrazenie panelu pre upravovanie produktu.
     */
    private void setEditProduct() {
        mainWindow.removeListeners();
        clearAll();
        fillProductsTable();
        hideAll();
        mainWindow.getSpEditProduct().setVisible(true);
        new EditProductController(database, mainWindow);
    }

    /**
     * Metóda nastaví zobrazenie panelu pre upravovanie zákazníka.
     */
    private void setEditCustomer() {
        mainWindow.removeListeners();
        clearAll();
        fillCustomerTable();
        hideAll();
        mainWindow.getSpEditCustomer().setVisible(true);
        new EditCustomerController(database, mainWindow);
    }
  
    /**
     * Metóda nastaví zobrazenie panelu pre zobrazenie faktúr.
     */
    private void setViewInvoices() {
        mainWindow.removeListeners();
        clearAll();
        fillInvoices();
        hideAll();
        mainWindow.getSpViewInvoice().setVisible(true);
        new ViewInvoiceController(database, mainWindow);
    }
    
    /**
     * Metóda nastaví všetky panely ako neviditeľné.
     */
    private void hideAll() {
        mainWindow.getSpNewCustomer().setVisible(false);
        mainWindow.getSpNewInvoice().setVisible(false);
        mainWindow.getSpNewProduct().setVisible(false);
        mainWindow.getSpEditCustomer().setVisible(false);
        mainWindow.getSpEditProduct().setVisible(false);
        mainWindow.getSpViewInvoice().setVisible(false);
    }
}
