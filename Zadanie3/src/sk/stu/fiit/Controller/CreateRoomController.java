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
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sk.stu.fiit.Model.Category;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Room;
import sk.stu.fiit.Model.SerializationClass;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class CreateRoomController extends Controller {
    private static final Logger logger = Logger.getLogger(CreateRoomController.class);

    private CreateRoomController(Database database, MainWindow window) {
        super(database, window);
        
        logger.setLevel(Level.INFO);
        fillCategoryTable();
        window.getspCreateRoom().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new CreateRoomController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnCreateRoom().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                createRoom();
            }
        });
    }
    
    /**
     * Metóda pre vytvorenie novej izby.
     */
    private void createRoom() {
        Room room = new Room();
        room.setDescription(window.getTaRoomDescription());
        room.setLabel(window.getTfRoomLabel());
        
        int index = window.getTbCategoriesTable().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(window, "Nie je vybraný žiadna kategória!");
            return;
        }
        
        String description = (String) window.getTbCategoriesModel().getValueAt(index, 0);
        double price = ((BigDecimal) window.getTbCategoriesModel().getValueAt(index, 1)).doubleValue();
        
        Category category = database.findCategory(description, price);
        
        if (category == null) {
            JOptionPane.showMessageDialog(window, "Chyba pri kategórii!");
            logger.warn("Chyba pri kategórii!");
            return;
        }
        
        room.setCategory(category);
        
        if (room.hasEmptyAttribute()) {
            JOptionPane.showMessageDialog(window, "Je potrebné vyplniť všetky polia!");
            logger.warn("Je potrebné vyplniť všetky polia!");
            return;
        }
        
        if (database.checkRoom(room)) {
            JOptionPane.showMessageDialog(window, "Daná izba sa už nachádza v systéme!");
            logger.warn("Daná izba sa už nachádza v systéme!");
            return;
        }
        
        database.addRoom(room);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Izba bola pridaná!");
        logger.info("Izba bola pridaná!");
        clearCreateRoom();
    }
    
    /**
     * Metóda pre naplnenie tabuľky s kategótiami.
     */
    private void fillCategoryTable() {
        window.getTbCategoriesModel().setRowCount(0);
        for (Category category: database.getCategories()) {
            Object[] row = new Object[2];
            row[0] = category.getDescription();
            row[1] = new BigDecimal(category.getPrice()).setScale(2, RoundingMode.HALF_UP);
            window.getTbCategoriesModel().addRow(row);
        }
    }
    
}
