/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.text.SimpleDateFormat;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Customer;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Invoice;
import sk.stu.fiit.Model.Product;

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
     * Metóda vymaže zo všetkých TextFieldoch text. Nastaví celkovú cenu na 0.00€
     * a premaže celú tabuľku s produktami.
     */
    protected void clearNewInvoice() {
        mainWindow.setTfName("");
        mainWindow.setTfAddress("");
        mainWindow.setTfNumber("");
        mainWindow.setTfTown("");
        mainWindow.setTfPSC("");
        mainWindow.setFtfDate("");
        mainWindow.setLbPrice("0,00 €");
        mainWindow.getTbProductsModel().setRowCount(0);
        addRow();
    }
    
     /**
     * Metóda vymaže zo všetkých TextFieldoch text.
     */
    protected void clearNewCustomer() {
        mainWindow.setTfName1("");
        mainWindow.setTfAddress1("");
        mainWindow.setTfNumber1("");
        mainWindow.setTfTown1("");
        mainWindow.setTfPSC1("");
    }
    
    /**
     * Metóda vymaže zo všetkých TextFieldoch text.
     */
    protected void clearNewProduct() {
        mainWindow.setTfName2("");
        mainWindow.setTaDescription("");
        mainWindow.setTfPrice("");
    }
    
    /**
     * Metóda vymaže zo všetkých TextFieldoch text.
     */
    protected void clearEditCustomer() {
        mainWindow.setTfName3("");
        mainWindow.setTfAddress2("");
        mainWindow.setTfNumber2("");
        mainWindow.setTfTown2("");
        mainWindow.setTfPSC2("");
    }
    
    /**
     * Metóda vymaže zo všetkých TextFieldoch text.
     */
    protected void clearEditProduct() {
        mainWindow.setTfName5("");
        mainWindow.setTaDescription1("");
        mainWindow.setTfPrice1("");
    }
    
    /**
     * Metóda vymaže zo všetkých TextFieldoch text.
     */
    protected void clearViewInvoice() {
        mainWindow.setLbInvoiceName("");
        mainWindow.setLbInvoiceAddress("");
        mainWindow.setLbInvoiceDate("");
        mainWindow.setLbInvoiceNumber("");
        mainWindow.setLbInvoicePSC("");
        mainWindow.setLbInvoicePrice("");
        mainWindow.setLbInvoiceTown("");
        mainWindow.getTbProductsModel3().setRowCount(0);
    }
    
    /**
     * Metóda vymaže zo všetkých TextFieldoch text.
     */
    protected void clearAll() {
        clearEditCustomer();
        clearEditProduct();
        clearNewCustomer();
        clearNewInvoice();
        clearNewProduct();
        clearViewInvoice();
    }
   
    /**
     * Pridanie prázdneho riadku do tabuľky s produktami v paneli pre vytvorenie
     * novej faktúry.
     */
    protected void addRow() {
        Object[] row = {null, null, null, null};
        mainWindow.getTbProductsModel().addRow(row);
    }
    
    /**
     * Naplnenie tabuľky zákazníkov všetkými zákazníkmi z databázy.
     */
    protected void fillCustomerTable() {
        mainWindow.getTbCustomersModel().setRowCount(0);
//        zobrazenie databázy
        for (Customer customer : database.getCustomers()) {
            Object[] row = new Object[5];
            row[0] = customer.getName();
            row[1] = customer.getAddress();
            row[2] = customer.getNumber();
            row[3] = customer.getTown();
            row[4] = customer.getPsc();
            mainWindow.getTbCustomersModel().addRow(row);
        }
    }
    
    /**
     * Naplnenie tabuľky produktov všetkými produktami z databázy.
     */
    protected void fillProductsTable() {
         mainWindow.getTbProducts2Model().setRowCount(0);
//        zobrazenie databázy
        for (Product product : database.getProducts()) {
            Object[] row = new Object[3];
            row[0] = product.getName();
            row[1] = product.getDescription();
            row[2] = product.getPrice();
            mainWindow.getTbProducts2Model().addRow(row);
        }
    }
    
    /**
     * Naplnenie tabuľky faktúr všetkými faktúrami z databázy.
     */
    protected void fillInvoices() {
        mainWindow.getTbInvoicesModel().setRowCount(0);
//        zobrazenie databázy
        for (Invoice invoice : database.getInvoices()) {
            Object[] row = new Object[2];
            row[0] = new SimpleDateFormat("dd.MM.yyyy").format(invoice.getDate());
            row[1] = invoice.getPrice();
            mainWindow.getTbInvoicesModel().addRow(row);
        }
    }
    
}
