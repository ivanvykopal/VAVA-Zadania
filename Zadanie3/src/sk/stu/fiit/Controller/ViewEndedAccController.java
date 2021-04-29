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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import sk.stu.fiit.Model.Accommodation;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class ViewEndedAccController extends Controller {

    private ViewEndedAccController(Database database, MainWindow window) {
        super(database, window);
        
        fillTable();
        window.getspEndedAccommodations().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new ViewEndedAccController(database, window);
    }

    @Override
    protected void initController() {
        window.getTbEndedAccommodationsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = window.getTbEndedAccommodationsTable().rowAtPoint(e.getPoint());
                window.getTbEndedAccommodationsTable().setRowSelectionInterval(index, index);
                chooseAccommodations(e.getPoint());
            }
        });
    }
    
    /**
     * Metóda pre výber ubytovania, ktoré chceme zobraziť.
     */
    private void chooseAccommodations(Point point) {
        int index = window.getTbEndedAccommodationsTable().rowAtPoint(point);
        
        if (index == -1) {
            clearViewEndedAcc();
            return;
        }
        DefaultTableModel model = window.getTbEndedAccommodationsModel();
        
        window.setLbEndedFrom(window.getBundle().getString("DATE_FROM") + " " + model.getValueAt(index, 2));
        window.setLbEndedName(window.getBundle().getString("CUSTOMER_NAME") + " " + model.getValueAt(index, 0));
        window.setLbEndedPrice(window.getBundle().getString("PRICE") + " " + model.getValueAt(index, 5) + " €");
        window.setLbEndedRoom(window.getBundle().getString("ROOM") + " " + model.getValueAt(index, 4));
        window.setLbEndedTo(window.getBundle().getString("DATE_TO") + " " + model.getValueAt(index, 3));
    }
    
    /**
     * Metóda pre naplnenie tabuľky s ukončenými ubytovaniami, ktoré neboli zaplatené.
     */
    private void fillTable() {
        window.getTbEndedAccommodationsModel().setRowCount(0);
        for (Accommodation acc : database.getAccommodations()) {
            Date now = convertLocalDate(window.getDate());
            if (!acc.isPaid() && (acc.getTo().before(now) || (acc.getTo().equals(now) && window.getTime().getHour() >= 10))) {
                Object[] row = new Object[6];
                row[0] = acc.getCustomer().getName();
                row[1] = acc.getCustomer().getPhone();
                row[2] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(acc.getFrom());
                row[3] = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT")).format(acc.getTo());
                row[4] = acc.getRoom().getLabel();
                row[5] = new BigDecimal(acc.getPrice()).setScale(2, RoundingMode.HALF_UP);
                window.getTbEndedAccommodationsModel().addRow(row);
            }
        }
    }
    
}
