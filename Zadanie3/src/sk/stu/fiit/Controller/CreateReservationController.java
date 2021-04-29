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
public final class CreateReservationController extends Controller {
    private Room room = null;
    private static final Logger logger = Logger.getLogger(CreateReservationController.class);

    private CreateReservationController(Database database, MainWindow window) {
        super(database, window);

        logger.setLevel(Level.INFO);
        window.getspCreateReservation().setVisible(true);

        initController();
    }

    public static void createController(Database database, MainWindow window) {
        new CreateReservationController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnCreateReservation().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                createReservation();
            }
        });

        window.getBtnResFindRoom().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                findFreeRooms();
            }
        });

        window.getTbReservationsRoomsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = window.getTbReservationsRoomsTable().rowAtPoint(e.getPoint());
                window.getTbReservationsRoomsTable().setRowSelectionInterval(index, index);
                chooseRoom(e.getPoint());
            }
            
        });
    }

    /**
     * Metóda pre vytvorenie rezervácie pre zákazníka.
     */
    private void createReservation() {
        Reservation reservation = new Reservation();

        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfReservationFrom());
            dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfReservationTo());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, "Chybný formát dátumu!");
            logger.warn("Chybný formát dátumu!", ex);
            return;
        }

        reservation.setFrom(dateFrom);
        reservation.setTo(dateTo);
        reservation.setPrice(window.getTfReservationPrice());
        
        if (reservation.getPrice() == -1) {
            JOptionPane.showMessageDialog(window, "Zlý formát ceny! Využíva sa . namiesto");
            return;
        }

        String customerName = window.getTfReservationName();
        String phoneNumber = window.getTfReservationPhone();

        Customer customer = database.getCustomer(customerName, phoneNumber);
        if (customer == null) {
            JOptionPane.showMessageDialog(window, "Zadaný zákazník nie je v systéme! Je potrebné najskôr vytvoriť zázkazníka!");
            logger.warn("Zákazník nebol v systéme!");
            return;
        }
        reservation.setCustomer(customer);

        if (room == null) {
            JOptionPane.showMessageDialog(window, "Nie je vybraná žiadna izba!");
            logger.warn("Nebola vybraná žiadna izba!");
            return;
        }
        reservation.setRoom(room);

        if (reservation.hasEmptyAttribute()) {
            JOptionPane.showMessageDialog(window, "Je potrebné vyplniť všetky polia!");
            logger.warn("Neboli vyplnené všetky polia!");
            return;
        }
        
        if (reservation.hasDateProblem()) {
            JOptionPane.showMessageDialog(window, "Dátum začiatku ubytovania je pred alebo rovný dátumu konca ubytovania!");
            logger.warn("Problém s dátumom!");
            return;
        }

        if (database.checkReservation(reservation)) {
            JOptionPane.showMessageDialog(window, "Rezervácia na danú izbu už existuje v rovnakom čase.");
            logger.warn("Rezervácia na danú izbu už existuje v rovnakom čase.");
            return;
        }

        database.addReservation(reservation);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Rezervácia bola pridaná!");
        logger.info("Rezervácia bola pridaná!");
        clearCreateReservation();
    }

    /**
     * Metóda pre nájdenie voľných izieb v požadovanom termíne.
     */
    private void findFreeRooms() {
        window.getTbReservationsRoomsModel().setRowCount(0);
        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfReservationFrom());
            dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfReservationTo());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, "Chybný formát dátumu!");
            logger.warn("Chybný formát dátumu!",ex);
            return;
        }
        
        if (dateTo.before(dateFrom) || dateTo.equals(dateFrom)) {
            JOptionPane.showMessageDialog(window, "Dátum začiatku ubytovania je pred alebo rovný dátumu konca ubytovania!");
            logger.warn("Problém s dátumom!");
            return;
        }

        for (Room freeRoom : database.findFreeRooms(dateFrom, dateTo)) {
            Object[] row = new Object[3];
            row[0] = freeRoom.getLabel();
            row[1] = freeRoom.getDescription();
            row[2] = new BigDecimal(freeRoom.getCategory().getPrice()).setScale(2, RoundingMode.HALF_UP);
            window.getTbReservationsRoomsModel().addRow(row);
        }
    }

    /**
     * Metóda na zistenie vybranej izby z tabuľky.
     */
    private void chooseRoom(Point point) {
        int index = window.getTbReservationsRoomsTable().rowAtPoint(point);
        if (index == -1) {
            room = null;
            window.setLbResRoom(window.getBundle().getString("CHOOSED_ROOM"));
            window.setTfReservationPrice("");
        } else {
            room = database.getRoom((String) window.getTbReservationsRoomsModel().getValueAt(index, 0));
            window.setLbResRoom(window.getBundle().getString("CHOOSED_ROOM") + " " + room.getLabel());

            Date dateFrom;
            Date dateTo;
            try {
                dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfReservationFrom());
                dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfReservationTo());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(window, "Chybný formát dátumu!");
                logger.warn("Chybný formát dátumu!",ex);
                return;
            }
            
            long milliseconds = dateTo.getTime() - dateFrom.getTime();
            long diff = TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
            double roomPrice = diff * room.getCategory().getPrice();
            if (diff > 10) {
                roomPrice *= 0.90;
            }
            window.setTfReservationPrice("" + new BigDecimal(roomPrice).setScale(2, RoundingMode.HALF_UP));
        }
    }

}
