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
import sk.stu.fiit.Model.Reservation;
import sk.stu.fiit.Model.Room;
import sk.stu.fiit.Model.SerializationClass;
import sk.stu.fiit.View.MainWindow;


/**
 *
 * @author Ivan Vykopal
 */
public final class AddAcc1Controller extends Controller {
    private Reservation reservation = null;
    private static final Logger logger = Logger.getLogger(AddAcc1Controller.class);
    
    private AddAcc1Controller(Database database, MainWindow window) {
        super(database, window);
        
        logger.setLevel(Level.INFO);
        
        fillTable();
        window.getspAddAccommodation1().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new AddAcc1Controller(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnAddAcc1().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addAccommodation();
            }
        });
        
        window.getTbAccReservationsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int index = window.getTbAccReservationsTable().rowAtPoint(e.getPoint());
                window.getTbAccReservationsTable().setRowSelectionInterval(index, index);
                chooseReservation(e.getPoint());
            }
        });

    }
    
    /**
     * Met??da pre vytvorenie ubytovania z??kazn??ka na z??klade rezerv??cie!
     */
    private void addAccommodation() {
        if (reservation == null) {
            JOptionPane.showMessageDialog(window, "Nebola vybran?? ??iadna rezerv??cia!");
            logger.warn("Nebola vybran?? ??iadna rezerv??cia!");
            return;
        }
        
        Accommodation accommodation = new Accommodation(reservation);
        database.removeReservation(reservation);
        database.addAccommodation(accommodation);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Ubytovanie bolo pridan??!");
        logger.info("Ubytovanie bolo pridan??!");
        reservation = null;
        clearAddAccommodation1();
        fillTable();
    }
    
    /**
     * Met??da pre zistenie vybranej rezerv??cie z tabu??ky rezerv??ci??!
     */
    private void chooseReservation(Point point) {
        int index = window.getTbAccReservationsTable().rowAtPoint(point);
        if (index == -1) {
            reservation = null;
        } else {
            Reservation res = new Reservation();
            DefaultTableModel model = window.getTbAccReservationsModel();
            Date dateFrom;
            Date dateTo;
            try {
                dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse((String) model.getValueAt(index, 2));
                dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse((String) model.getValueAt(index, 3));
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(window, "Chybn?? form??t d??tumu!");
                logger.warn("Chybn?? form??t d??tumu!", ex);
                return;
            }
            
            res.setFrom(dateFrom);
            res.setTo(dateTo);
            res.setPrice(((BigDecimal) model.getValueAt(index, 5)).doubleValue());
            
            String customerName = (String) model.getValueAt(index, 0);
            String phoneNumber = (String) model.getValueAt(index, 1);
            Customer customer = database.getCustomer(customerName, phoneNumber);
            res.setCustomer(customer);
            
            Room room = database.getRoom((String) model.getValueAt(index, 4));
            res.setRoom(room);
            
            reservation = database.findReservation(res);
            if (reservation == null) {
                return;
            }
            
            window.setLbAccFrom1(window.getBundle().getString("DATE_FROM") + " " + new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(reservation.getFrom()));
            window.setLbAccName1(window.getBundle().getString("CUSTOMER_NAME") + " " + reservation.getCustomer().getName());
            window.setLbAccPrice1(window.getBundle().getString("PRICE") + " " + new BigDecimal(reservation.getPrice()).setScale(2, RoundingMode.HALF_UP) + " ???");
            window.setLbAccRoom1(window.getBundle().getString("ROOM") + " " + reservation.getRoom().getLabel());
            window.setLbAccTo1(window.getBundle().getString("DATE_TO") + " " + new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(reservation.getTo()));
        }
    }    
    
    /**
     * Met??da pre naplnenie tabu??ky s rezerv??ciami.
     */
    private void fillTable() {
        window.getTbAccReservationsModel().setRowCount(0);
        for (Reservation res: database.getReservations()) {
            Object[] row = new Object[6];
            row[0] = res.getCustomer().getName();
            row[1] = res.getCustomer().getPhone();
            row[2] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(res.getFrom());
            row[3] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(res.getTo());
            row[4] = res.getRoom().getLabel();
            row[5] = new BigDecimal(res.getPrice()).setScale(2, RoundingMode.HALF_UP);
            window.getTbAccReservationsModel().addRow(row);
        }
    }
}
