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
            JOptionPane.showMessageDialog(window, "Nie je vybran?? ??iadna izba!");
            logger.warn("Nebola vybran?? ??iadna izba!");
            return;
        }

        String customerName = window.getTfAccName2();
        String phoneNumber = window.getTfAccPhone2();
        Customer customer = database.getCustomer(customerName, phoneNumber);
        if (customer == null) {
            JOptionPane.showMessageDialog(window, "Zadan?? z??kazn??k nie je v syst??me!");
            logger.warn("Zadan?? z??kazn??k nie je v syst??me!");
            return;
        }

        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfAccFrom2());
            dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfAccTo2());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, "Chybn?? form??t d??tumu!");
            logger.warn("Chybn?? form??t d??tumu!", ex);
            return;
        }

        double price = window.getTfAccPrice2();
        if (price == -1) {
            JOptionPane.showMessageDialog(window, "Zl?? form??t ceny!");
            return;
        }

        Accommodation acc = new Accommodation(customer, room, dateFrom, dateTo, price);

        if (acc.hasEmptyAttribute()) {
            JOptionPane.showMessageDialog(window, "Je potrebn?? vyplni?? v??etky polia!");
            logger.warn("Neboli vyplnen?? v??etky polia!");
            return;
        }
        
        if (acc.hasDateProblem()) {
            JOptionPane.showMessageDialog(window, "D??tum za??iatku ubytovania je pred alebo rovn?? d??tumu konca ubytovania!");
            logger.warn("Probl??m s d??tumom!");
            return;
        }

        database.addAccommodation(acc);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Ubytovanie bolo pridan??!");
        logger.info("Ubytovanie bolo pridan??!");
        clearAddAccommodation2();
    }

    /**
     * Met??da pre n??jdenie vo??n??ch izieb na z??klade zvolen??ho term??nu.
     */
    private void findFreeRooms() {
        window.getTbAccRoomsModel().setRowCount(0);
        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfAccFrom2());
            dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfAccTo2());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, "Chybn?? form??t d??tumu!");
            logger.warn("Chybn?? form??t d??tumu!", ex);
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
     * Met??da pre zistenie vybranej izby.
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
                JOptionPane.showMessageDialog(window, "Chybn?? form??t d??tumu!");
                logger.warn("Chybn?? form??t d??tumu!", ex);
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
