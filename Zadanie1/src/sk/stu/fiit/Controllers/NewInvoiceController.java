/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Customer;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Invoice;
import sk.stu.fiit.Model.Product;
import sk.stu.fiit.Model.Record;

/**
 *
 * @author Ivan Vykopal
 */
public class NewInvoiceController extends Controller {
    
    public NewInvoiceController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnCreateInvoice().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createNewinvoice();
            }
        });
        
        mainWindow.getBtnAddRow().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addRow();
            }
        });
        
        mainWindow.getBtnRemoveRow().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeRow();
            }
        });
        
        mainWindow.getTbProductsModel().addTableModelListener((TableModelEvent e) -> {
            if (e.getColumn() == 2 || e.getColumn() == 3) {
                DefaultTableModel model = mainWindow.getTbProductsModel();
                int rows = mainWindow.getTbProductsModel().getRowCount();
                double price = 0.0;
                for (int i = 0; i < rows; i++) {
                    if ( model.getValueAt(i, 2) != null && model.getValueAt(i, 3) != null) {
                        price += getRowPrice((int) model.getValueAt(i, 2), (double) model.getValueAt(i, 3), i, 4);
                    }
                }
                mainWindow.setLbPrice(new DecimalFormat("#.00").format(price) + " ???");
            }
        });
    }
    
    /**
     * Met??da na vytvorenie novej fakt??ry, pri??om s fakt??rou sa vytv??raj?? aj
     * z??kazn??k aj produkty, ak e??te neexistuj??.
     */
    private void createNewinvoice() {
        String name = mainWindow.getTfName();
        String address = mainWindow.getTfAddress();
        String number = mainWindow.getTfNumber();
        String town = mainWindow.getTfTown();
        String psc = mainWindow.getTfPSC();
        if (name.equals("") || address.equals("") || number.equals("") 
                || town.equals("") || psc.equals("")) {
            JOptionPane.showMessageDialog(mainWindow, "Je potrebn?? vyplni?? v??etky polia!");
            return;
        }
        Customer customer = new Customer(name, address, number, town, psc);
        Date date;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy").parse(mainWindow.getFtfDate());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(mainWindow, "Chybn?? form??t d??tumu!");
            return;
        }
        
        int rows = mainWindow.getTbProductsModel().getRowCount();
        String nameProduct;
        String description;
        int count;
        double price;
        DefaultTableModel model = mainWindow.getTbProductsModel();
        ArrayList<Record> productRecords = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            if (model.getValueAt(i, 0) == null || model.getValueAt(i, 1) == null
                    || model.getValueAt(i, 2) == null || model.getValueAt(i, 3) == null) {
                JOptionPane.showMessageDialog(mainWindow, "Pre tovar ch??ba vyplnen?? pole!");
                return;
            }
            nameProduct = (String) model.getValueAt(i, 0);
            description = (String) model.getValueAt(i, 1);
       
            count = (int) model.getValueAt(i, 2);
            price = (double) model.getValueAt(i, 3);
            if (price == -1) {
                return;
            }
            if (nameProduct.equals("") || description.equals("") || count == 0 || price == 0) {
                JOptionPane.showConfirmDialog(mainWindow, "Je potrebn?? vyplni?? v??etky polia!");
                return;
            }
            Product product = new Product(nameProduct, description, price);
            products.add(product);
            productRecords.add(new Record(product, count));
        }
        
        for (Product product: products) {
            database.addProduct(product);
        }
        
        Invoice invoice = new Invoice(date, customer, productRecords);
        database.addCutomer(customer);
        database.addInvoice(invoice);
        
        clearNewInvoice();
        JOptionPane.showMessageDialog(mainWindow, "Nov?? fakt??ra bola vytvoren??!");
    }
 
    /**
     * Met??da na v??po??et ceny pre dan?? riadok a jej n??sledn?? nastavenie v tabu??ke.
     * @param quantity mno??stvo tovaru
     * @param price cena za 1ks
     * @param row riadok v tabu??ke
     * @param column st??pec v tabu??ke
     * @return 
     */
    private double getRowPrice(int quantity, double price, int row, int column) {
        double sum = quantity * price;
        mainWindow.getTbProductsModel().setValueAt(sum, row, column);
        return sum;
    }
  
    /**
     * Met??da na vymazanie riadku z tabu??ky produktov.
     */
    private void removeRow() {
        int indexRow = mainWindow.getTbProductsModel().getRowCount() - 1;
        if (indexRow > 0) {
            mainWindow.getTbProductsModel().removeRow(indexRow);
        }
    }
    
}
