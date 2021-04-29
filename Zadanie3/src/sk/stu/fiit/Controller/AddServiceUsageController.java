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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.SerializationClass;
import sk.stu.fiit.Model.Service;
import sk.stu.fiit.Model.ServiceUsed;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykoal
 */
public final class AddServiceUsageController extends Controller {
    private static final Logger logger = Logger.getLogger(AddServiceUsageController.class);

    private AddServiceUsageController(Database database, MainWindow window) {
        super(database, window);
        
        logger.setLevel(Level.INFO);
        fillTable();
        window.getspAddServiceUsage().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new AddServiceUsageController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnAddServiceUsage().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addServiceUsage();
            }
        });
    }
    
    /**
     * Metóda na pridanie využitia služby pre ubytovania na základe izby.
     */
    private void addServiceUsage() {
        ServiceUsed usage = new ServiceUsed();
        usage.setDate(convertLocalDate(window.getDate()));
        usage.setQuantity(window.getTfQuantity());
        String room = window.getTfAddUsageRoom();
        
        if (usage.getQuantity() == -1) {
            JOptionPane.showMessageDialog(window, "Zlý formát. Množstvo je celé číslo!");
            return;
        }
        
        int index = window.getTbServicesTable().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(window, "Nebola vybraná žiadna služba!");
            logger.warn("Nebola vybraná žiadna služba!");
            return;
        } 
        
        DefaultTableModel model = window.getTbServicesModel();
        Service service = new Service();
        service.setName((String) model.getValueAt(index, 0));
        service.setDescription((String) model.getValueAt(index, 1));
        service.setPrice(((BigDecimal) model.getValueAt(index, 2)).doubleValue());
        
        if (service.hasEmptyAttribute()) {
            JOptionPane.showMessageDialog(window, "Je potrebné vyplniť všetky polia!");
            logger.warn("Neboli vyplnené všetky polia!");
            return;
        }
        
        service = database.findService(service);
        if (service == null) {
            JOptionPane.showMessageDialog(window, "Problém pri načítaní služby!");
            logger.warn("Problém pri načítaní služby!");
            return;
        }
        usage.setService(service);
        
        if (database.addServiceUsage(room, usage, window.getTime())) {
            SerializationClass.serialize(database);
            JOptionPane.showMessageDialog(window, "Služba bola zaevidovaná!");
            logger.info("Služba bola zaevidovaná pre ubytovanie!");
            clearAddServiceUsage();
        } else {
            JOptionPane.showMessageDialog(window, "Služba nebola zaevidovaná! Na izbe nie je aktuálne žiadne ubytovanie.");
            logger.info("Služba nebola zaevidovaná!");
        }
    }
    
    /**
     * Metóda pre naplnenie tabuľky službami.
     */
    private void fillTable() {
        window.getTbServicesModel().setRowCount(0);
        for (Service service : database.getServices()) {
            Object[] row = new Object[3];
            row[0] = service.getName();
            row[1] = service.getDescription();
            row[2] = new BigDecimal(service.getPrice()).setScale(2, RoundingMode.HALF_UP);
            window.getTbServicesModel().addRow(row);
        }
    }
    
}
