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
import sk.stu.fiit.Model.Invoice;
import sk.stu.fiit.Model.Record;

/**
 *
 * @author Ivan Vykopal
 */
public class ViewInvoiceController extends Controller {
    private int index = -1;

    public ViewInvoiceController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnChooseInvocie().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewInvoice();
            }
        });
    }
    
    /**
     * Metóda na zobrazenie vybranej faktúry.
     */
    private void viewInvoice() {
        DefaultTableModel model = mainWindow.getTbInvoicesModel();
        index = mainWindow.getTbInvoices().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nie je vybraný žiaden záznam!");
            return;
        }
        String date = model.getValueAt(index, 0).toString();
        double price = (double) model.getValueAt(index, 1);
        
        Invoice invoice = database.findInvoice(date, price);
        if (invoice == null) {
            JOptionPane.showMessageDialog(mainWindow, "Chybný formát dátumu!");
            return;
        }
        fillInvoiceProductTable(invoice);
        
        mainWindow.setLbInvoiceName(invoice.getCustomer().getName());
        mainWindow.setLbInvoiceAddress(invoice.getCustomer().getAddress());
        mainWindow.setLbInvoiceDate(date);
        mainWindow.setLbInvoiceNumber(invoice.getCustomer().getNumber());
        mainWindow.setLbInvoicePSC(invoice.getCustomer().getPsc());
        mainWindow.setLbInvoicePrice("" + invoice.getPrice() + " €");
        mainWindow.setLbInvoiceTown(invoice.getCustomer().getTown());
        JOptionPane.showMessageDialog(mainWindow, "Faktúra bola zobrazená!");
    }
    
    /**
     * Metóda na naplnenie tabuľky produktov produktami z faktúry.
     * @param invoice vybraná faktúra
     */
    private void fillInvoiceProductTable(Invoice invoice) {
        mainWindow.getTbProductsModel3().setRowCount(0);
//        zobrazenie databázy
        for (Record record : invoice.getProducts()) {
            Object[] row = new Object[4];
            row[0] = record.getProduct().getName();
            row[1] = record.getProduct().getDescription();
            row[2] = record.getProduct().getPrice();
            row[3] = record.getQuantity();
            mainWindow.getTbProductsModel3().addRow(row);
        }
    }
    
}
