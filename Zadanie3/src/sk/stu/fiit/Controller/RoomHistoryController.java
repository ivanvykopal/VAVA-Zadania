/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sk.stu.fiit.Model.Accommodation;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Room;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class RoomHistoryController extends Controller {
    private static final Logger logger = Logger.getLogger(RoomHistoryController.class);

    private RoomHistoryController(Database database, MainWindow window) {
        super(database, window);
        
        logger.setLevel(Level.INFO);
        fillTable();
        window.getspRoomHistory().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new RoomHistoryController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnViewRoomHistory().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                viewHistory();
            }
        });
    }
    
    /**
     * Metóda pre zvolenie izby z tabuľky izieb.
     */
    private void viewHistory() {
        int index = window.getTbRoomsHistoryTable().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(window, "Nie je vybraná žiadna izba!");
            logger.warn("Nebola vybraná žiadna izba!");
            return;
        }
        
        String label = (String) window.getTbRoomsHistoryModel().getValueAt(index, 0);
        Room room = database.getRoom(label);
        if (room == null) {
            JOptionPane.showMessageDialog(window, "Problém pri hľadaní izby!");
            logger.warn("Problém pri hľadaní izby!");
            return;
        }
        
        fillAccommodationTable(room);
    }
    
    /**
     * Metóda pre zobrazenie ubytovaní pre zvolenú izbu
     * 
     * @param room izba, pre ktorú zobrazujeme históriu ubytovaní 
     */
    private void fillAccommodationTable(Room room) {
        Date now = convertLocalDate(window.getDate());
        window.getTbAccHistoryModel().setRowCount(0);
        for (Accommodation acc : database.getAccommodations()) {
            if (acc.getRoom().getLabel().equals(room.getLabel()) && (acc.getTo().before(now) || (acc.getTo().equals(now) && window.getTime().getHour() >= 10))) {
                Object[] row = new Object[4];
                row[0] = acc.getCustomer().getName();
                row[1] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(acc.getFrom());
                row[2] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(acc.getTo());
                row[3] = new BigDecimal(acc.getPrice()).setScale(2, RoundingMode.HALF_UP);
                window.getTbAccHistoryModel().addRow(row);
            }
        }
    }
    
    /**
     * Metóda pre naplnenie tabuľky s izbami.
     */
    private void fillTable() {
         window.getTbRoomsHistoryModel().setRowCount(0);
        for (Room room : database.getRooms()) {
            Object[] row = new Object[3];
            row[0] = room.getLabel();
            row[1] = room.getDescription();
            row[2] = new BigDecimal(room.getCategory().getPrice()).setScale(2, RoundingMode.HALF_UP);
            window.getTbRoomsHistoryModel().addRow(row);
        }
    }
    
}
