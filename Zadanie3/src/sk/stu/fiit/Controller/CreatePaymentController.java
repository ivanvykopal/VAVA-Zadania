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
import sk.stu.fiit.Model.PaymentType;
import sk.stu.fiit.Model.Room;
import sk.stu.fiit.Model.SerializationClass;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class CreatePaymentController extends Controller {
    private Accommodation accommodation = null;
    private static final Logger logger = Logger.getLogger(CreatePaymentController.class);

    private CreatePaymentController(Database database, MainWindow window) {
        super(database, window);
        
        logger.setLevel(Level.INFO);
        fillTable();
        window.getspCreatePayment().setVisible(true);

        initController();
    }

    public static void createController(Database database, MainWindow window) {
        new CreatePaymentController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnCreatePayment().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                createPayment();
            }
        });

        window.getTbPaymentAccTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int index = window.getTbPaymentAccTable().rowAtPoint(e.getPoint());
                window.getTbPaymentAccTable().setRowSelectionInterval(index, index);
                chooseAccommodation(e.getPoint());
            }
        });
    }

    /**
     * Metóda pre zaznamenanie platby pre vybrané ubytovanie.
     */
    private void createPayment() {
        if (accommodation == null) {
            JOptionPane.showMessageDialog(window, "Nie je vybrané žiadne ubytovanie!");
            logger.warn("Nebolo vybrané žiadne ubytovanie!");
            return;
        }
        
        String paymentType = (String) window.getCbPaymentType().getSelectedItem();
        PaymentType type;
        
        if (paymentType.equals(window.getBundle().getString("CHOOSE_PAYMENT_TYPE"))) {
            JOptionPane.showMessageDialog(window, "Je potrebné vyplniť všetky polia!");
                logger.warn("Neboli vyplnené všetky polia!");
                return;
        } else if (paymentType.equals(window.getBundle().getString("CASH"))){
            type = PaymentType.CASH;
        } else if(paymentType.equals(window.getBundle().getString("CARD"))) {
            type = PaymentType.CARD;
        } else {
            return;
        }
        
        accommodation.setPaid(true);
        Date now = convertLocalDate(window.getDate());
        Payment payment = new Payment(now, accommodation, type);
        database.setAccommodation(accommodation);
        database.addPayment(payment);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Platba bola zaznamenaná!");
        logger.info("Platba bola zaznamenaná!");
        clearCreatePayment();
        fillTable();
    }

    /**
     * Metóda pre výber ubytovania z tabuľky ubytovaní.
     */
    private void chooseAccommodation(Point point) {
        int index = window.getTbPaymentAccTable().rowAtPoint(point);
        if (index == -1) {
            accommodation = null;
            clearCreatePayment();
            return;
        }

        DefaultTableModel model = window.getTbPaymentAccModel();
        String customerName = (String) model.getValueAt(index, 0);
        String phoneNumber = (String) model.getValueAt(index, 1);
        Customer customer = database.getCustomer(customerName, phoneNumber);

        if (customer == null) {
            JOptionPane.showMessageDialog(window, "Chyba pri hľadní zákazníka!");
            logger.warn("Chyba pri hľadní zákazníka!");
            return;
        }

        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse((String) model.getValueAt(index, 2));
            dateTo = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).parse((String) model.getValueAt(index, 3));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, "Chybný formát dátumu!");
            logger.warn("Chybný formát dátumu!", ex);
            return;
        }
        accommodation = new Accommodation();
        accommodation.setCustomer(customer);
        accommodation.setFrom(dateFrom);
        accommodation.setTo(dateTo);
        Room room = database.getRoom((String) model.getValueAt(index, 4));
        if (room == null) {
            JOptionPane.showMessageDialog(window, "Chyba pri hľadní izby!");
            logger.warn("Chyba pri hľadní izby!");
            return;
        }
        accommodation.setRoom(room);
        accommodation.setPrice(((BigDecimal) model.getValueAt(index, 5)).doubleValue());
        
        accommodation = database.findAccommodation(accommodation);
        if (accommodation == null) {
            JOptionPane.showMessageDialog(window, "Ubytovanie nebolo nájdené!");
            logger.warn("Ubytovanie nebolo nájdené!");
            return;
        }
        
        window.setLbPaymentFrom(window.getBundle().getString("DATE_FROM") + " " + new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(accommodation.getFrom()));
        window.setLbPaymentName(window.getBundle().getString("CUSTOMER_NAME") + " " + accommodation.getCustomer().getName());
        window.setLbPaymentPrice(window.getBundle().getString("PRICE") + " " + new BigDecimal(accommodation.getPrice()).setScale(2, RoundingMode.HALF_UP) + " €");
        window.setLbPaymentTo(window.getBundle().getString("DATE_TO") + " " + new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(accommodation.getTo()));
        window.setLbPaymnetRoom(window.getBundle().getString("ROOM") + " " + accommodation.getRoom().getLabel());
    }
    
    /**
     * Metóda pre naplnenie tabuľky s ubytovaniami.
     */
    private void fillTable() {
        window.getTbPaymentAccModel().setRowCount(0);
        for (Accommodation acc : database.getAccommodations()) {
            if (!acc.isPaid()) {
                Object[] row = new Object[6];
                row[0] = acc.getCustomer().getName();
                row[1] = acc.getCustomer().getPhone();
                row[2] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(acc.getFrom());
                row[3] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(acc.getTo());
                row[4] = acc.getRoom().getLabel();
                row[5] = new BigDecimal(acc.getPrice()).setScale(2, RoundingMode.HALF_UP);
                window.getTbPaymentAccModel().addRow(row);
            }
        }
    }

}
