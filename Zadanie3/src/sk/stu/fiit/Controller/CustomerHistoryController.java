/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sk.stu.fiit.Model.Accommodation;
import sk.stu.fiit.Model.Customer;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Payment;
import sk.stu.fiit.Model.ServiceUsed;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class CustomerHistoryController extends Controller {
    private Customer customer = null;
    private static final Logger logger = Logger.getLogger(CustomerHistoryController.class);
   
    private CustomerHistoryController(Database database, MainWindow window) {
        super(database, window);
        
        logger.setLevel(Level.INFO);
        fillTable();
        window.getspViewCustomerHistory().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new CustomerHistoryController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnViewCustomerHistory().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                viewHistory();
            }
        });
        
        window.getTbCustomerAccTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int index = window.getTbCustomerAccTable().rowAtPoint(e.getPoint());
                window.getTbCustomerAccTable().setRowSelectionInterval(index, index);
                viewServices(e.getPoint());
            }
        });
    }
    
    /**
     * Metóda pre zobrazenie histórie ubytovaní zvoleného zákazníka.
     */
    private void viewHistory() {
        clearCustomerHistory();
        int index = window.getTbCustomersTable().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(window, "Nie je vybraný žiaden zákazník!");
            return;
        }
        
        DefaultTableModel model = window.getTbCustomersModel();
        String name = (String) model.getValueAt(index, 0);
        String phone = (String) model.getValueAt(index, 2);
        customer = database.getCustomer(name, phone);
        if (customer == null) {
            JOptionPane.showMessageDialog(window, "Zákazník nebol nájdený!");
            logger.warn("Zákazník nebol nájdený!");
            return;
        }
        
        fillAccomodations();
    }
    
    /**
     * Metóda pre naplnenie tabuľky so zákazníkmi.
     */
    private void fillTable() {
        window.getTbCustomersModel().setRowCount(0);
        for (Customer cus : database.getCustomers()) {
            Object[] row = new Object[4];
            row[0] = cus.getName();
            row[1] = cus.getTown();
            row[2] = cus.getPhone();
            row[3] = cus.getEmail();
            window.getTbCustomersModel().addRow(row);
        }
    }
    
    /**
     * Metóda pre naplnenie tabuľky uytovania pre vybraného zázkaníka.
     */
    private void fillAccomodations() {
        Date now = convertLocalDate(window.getDate());
        for (Accommodation acc: database.getAccommodations()) {
            if (acc.getCustomer().equals(customer) && (acc.getTo().before(now) || (acc.getTo().equals(now) && window.getTime().getHour() >= 10))) {
                Object[] row = new Object[6];
                row[0] = acc.getRoom().getLabel();
                row[1] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(acc.getFrom());
                row[2] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(acc.getTo());
                row[3] = new BigDecimal(acc.getPrice()).setScale(2, RoundingMode.HALF_UP);
                Payment payment = database.findAccPayment(acc);
                if (payment == null) {
                    row[4] = window.getBundle().getString("NOT_PAYED");
                    row[5] = "-";
                } else {
                    row[4] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(payment.getDate());
                    switch(payment.getPymentType()) {
                        case CASH: 
                            row[5] = window.getBundle().getString("CASH");
                            break;
                        case CARD: 
                            row[5] = window.getBundle().getString("CARD");
                            break;
                    }
                }
                window.getTbCustomerAccModel().addRow(row);
            }
        }
    }
    
    /**
     * Metóda pre vypísanie využitých služieb pre zvolené ubytovani zákazníka.
     */
    private void viewServices(Point point) {
        int index = window.getTbCustomerAccTable().rowAtPoint(point);
        if (index == -1) {
            window.getTbCustomerAccModel().setRowCount(0);
            return;
        }
        DefaultTableModel model = window.getTbCustomerAccModel();
        
        Accommodation acc = new Accommodation();
        acc.setRoom(database.getRoom((String) model.getValueAt(index, 0)));
        acc.setCustomer(customer);
        
        Date dateFrom;
            Date dateTo;
            try {
                dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse((String) model.getValueAt(index, 1));
                dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse((String) model.getValueAt(index, 2));
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(window, "Chybný formát dátumu!");
                logger.warn("Chybný formát dátumu!",ex);
                return;
            }
        acc.setFrom(dateFrom);
        acc.setTo(dateTo);
        acc.setPrice(((BigDecimal) model.getValueAt(index, 3)).doubleValue());
        acc = database.findAccommodation(acc);
        if (acc == null) {
            window.getTbUsedServicesModel().setRowCount(0);
            return;
        }
        
        window.getTbUsedServicesModel().setRowCount(0);
        for(ServiceUsed service : acc.getServices()) {
            Object[] row = new Object[5];
            row[0] = service.getService().getName();
            row[1] = service.getService().getDescription();
            row[2] = service.getQuantity();
            row[3] = new BigDecimal(service.getService().getPrice()).setScale(2, RoundingMode.HALF_UP);
            row[4] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(service.getDate());
            window.getTbUsedServicesModel().addRow(row);
        }
    }
    
}
