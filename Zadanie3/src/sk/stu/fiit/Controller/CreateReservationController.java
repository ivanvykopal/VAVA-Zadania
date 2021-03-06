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
     * Met??da pre vytvorenie rezerv??cie pre z??kazn??ka.
     */
    private void createReservation() {
        Reservation reservation = new Reservation();

        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfReservationFrom());
            dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfReservationTo());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, "Chybn?? form??t d??tumu!");
            logger.warn("Chybn?? form??t d??tumu!", ex);
            return;
        }

        reservation.setFrom(dateFrom);
        reservation.setTo(dateTo);
        reservation.setPrice(window.getTfReservationPrice());
        
        if (reservation.getPrice() == -1) {
            JOptionPane.showMessageDialog(window, "Zl?? form??t ceny! Vyu????va sa . namiesto");
            return;
        }

        String customerName = window.getTfReservationName();
        String phoneNumber = window.getTfReservationPhone();

        Customer customer = database.getCustomer(customerName, phoneNumber);
        if (customer == null) {
            JOptionPane.showMessageDialog(window, "Zadan?? z??kazn??k nie je v syst??me! Je potrebn?? najsk??r vytvori?? z??zkazn??ka!");
            logger.warn("Z??kazn??k nebol v syst??me!");
            return;
        }
        reservation.setCustomer(customer);

        if (room == null) {
            JOptionPane.showMessageDialog(window, "Nie je vybran?? ??iadna izba!");
            logger.warn("Nebola vybran?? ??iadna izba!");
            return;
        }
        reservation.setRoom(room);

        if (reservation.hasEmptyAttribute()) {
            JOptionPane.showMessageDialog(window, "Je potrebn?? vyplni?? v??etky polia!");
            logger.warn("Neboli vyplnen?? v??etky polia!");
            return;
        }
        
        if (reservation.hasDateProblem()) {
            JOptionPane.showMessageDialog(window, "D??tum za??iatku ubytovania je pred alebo rovn?? d??tumu konca ubytovania!");
            logger.warn("Probl??m s d??tumom!");
            return;
        }

        if (database.checkReservation(reservation)) {
            JOptionPane.showMessageDialog(window, "Rezerv??cia na dan?? izbu u?? existuje v rovnakom ??ase.");
            logger.warn("Rezerv??cia na dan?? izbu u?? existuje v rovnakom ??ase.");
            return;
        }

        database.addReservation(reservation);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Rezerv??cia bola pridan??!");
        logger.info("Rezerv??cia bola pridan??!");
        clearCreateReservation();
    }

    /**
     * Met??da pre n??jdenie vo??n??ch izieb v po??adovanom term??ne.
     */
    private void findFreeRooms() {
        window.getTbReservationsRoomsModel().setRowCount(0);
        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfReservationFrom());
            dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse(window.getFtfReservationTo());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, "Chybn?? form??t d??tumu!");
            logger.warn("Chybn?? form??t d??tumu!",ex);
            return;
        }
        
        if (dateTo.before(dateFrom) || dateTo.equals(dateFrom)) {
            JOptionPane.showMessageDialog(window, "D??tum za??iatku ubytovania je pred alebo rovn?? d??tumu konca ubytovania!");
            logger.warn("Probl??m s d??tumom!");
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
     * Met??da na zistenie vybranej izby z tabu??ky.
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
                JOptionPane.showMessageDialog(window, "Chybn?? form??t d??tumu!");
                logger.warn("Chybn?? form??t d??tumu!",ex);
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
