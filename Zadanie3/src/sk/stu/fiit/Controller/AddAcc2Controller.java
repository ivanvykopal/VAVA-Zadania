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
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sk.stu.fiit.Model.Accommodation;
import sk.stu.fiit.Model.Customer;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Room;
import sk.stu.fiit.Model.SerializationClass;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class AddAcc2Controller extends Controller {
    private Room room = null;
    private static final Logger logger = Logger.getLogger(AddAcc2Controller.class);

    private AddAcc2Controller(Database database, MainWindow window) {
        super(database, window);
        
        logger.setLevel(Level.INFO);
        window.getspAddAccommodation2().setVisible(true);

        initController();
    }

    public static void createController(Database database, MainWindow window) {
        new AddAcc2Controller(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnAddAcc2().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addAccommodation();
            }
        });

        window.getBtnAccFindRoom().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                findFreeRooms();
            }
        });

        window.getTbAccRoomsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int index = window.getTbAccRoomsTable().rowAtPoint(e.getPoint());
                window.getTbAccRoomsTable().setRowSelectionInterval(index, index);
                chooseRoom(e.getPoint());
            }
        });
    }

    private void addAccommodation() {
        if (room == null) {
            JOptionPane.showMessageDialog(window, "Nie je vybraná žiadna izba!");
            logger.warn("Nebola vybraná žiadna izba!");
            return;
        }

        String customerName = window.getTfAccName2();
        String phoneNumber = window.getTfAccPhone2();
        Customer customer = database.getCustomer(customerName, phoneNumber);
        if (customer == null) {
            JOptionPane.showMessageDialog(window, "Zadaný zákazník nie je v systéme!");
            logger.warn("Zadaný zákazník nie je v systéme!");
            return;
        }

        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfAccFrom2());
            dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfAccTo2());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, "Chybný formát dátumu!");
            logger.warn("Chybný formát dátumu!", ex);
            return;
        }

        double price = window.getTfAccPrice2();
        if (price == -1) {
            JOptionPane.showMessageDialog(window, "Zlý formát ceny!");
            return;
        }

        Accommodation acc = new Accommodation(customer, room, dateFrom, dateTo, price);

        if (acc.hasEmptyAttribute()) {
            JOptionPane.showMessageDialog(window, "Je potrebné vyplniť všetky polia!");
            logger.warn("Neboli vyplnené všetky polia!");
            return;
        }
        
        if (acc.hasDateProblem()) {
            JOptionPane.showMessageDialog(window, "Dátum začiatku ubytovania je pred alebo rovný dátumu konca ubytovania!");
            logger.warn("Problém s dátumom!");
            return;
        }

        database.addAccommodation(acc);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Ubytovanie bolo pridané!");
        logger.info("Ubytovanie bolo pridané!");
        clearAddAccommodation2();
    }

    /**
     * Metóda pre nájdenie voľných izieb na základe zvoleného termínu.
     */
    private void findFreeRooms() {
        window.getTbAccRoomsModel().setRowCount(0);
        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfAccFrom2());
            dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfAccTo2());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, "Chybný formát dátumu!");
            logger.warn("Chybný formát dátumu!", ex);
            return;
        }

        for (Room room : database.findFreeRooms(dateFrom, dateTo)) {
            Object[] row = new Object[3];
            row[0] = room.getLabel();
            row[1] = room.getDescription();
            row[2] = new BigDecimal(room.getCategory().getPrice()).setScale(2, RoundingMode.HALF_UP);
            window.getTbAccRoomsModel().addRow(row);
        }
    }

    /**
     * Metóda pre zistenie vybranej izby.
     */
    private void chooseRoom(Point point) {
        int index = window.getTbAccRoomsTable().rowAtPoint(point);
        if (index == -1) {
            room = null;
            window.setLbAccRoom2(window.getBundle().getString("CHOOSED_ROOM"));
            window.setTfAccPrice2("");
        } else {
            room = database.getRoom((String) window.getTbAccRoomsModel().getValueAt(index, 0));
            window.setLbAccRoom2(window.getBundle().getString("CHOOSED_ROOM") + " " + room.getLabel());

            Date dateFrom;
            Date dateTo;
            try {
                dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfAccFrom2());
                dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfAccTo2());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(window, "Chybný formát dátumu!");
                logger.warn("Chybný formát dátumu!", ex);
                return;
            }

            long milliseconds = dateTo.getTime() - dateFrom.getTime();
            long diff = TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
            double roomPrice = diff * room.getCategory().getPrice();
            if (diff > 10) {
                roomPrice *= 0.90;
            }
            window.setTfAccPrice2("" + new BigDecimal(roomPrice).setScale(2, RoundingMode.HALF_UP));
        }
    }

}
