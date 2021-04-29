/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sk.stu.fiit.Model.Category;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.SerializationClass;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class CreateCategoryController extends Controller {
    private static final Logger logger = Logger.getLogger(CreateCategoryController.class);

    private CreateCategoryController(Database database, MainWindow window) {
        super(database, window);
        
        logger.setLevel(Level.INFO);
        window.getpCreateCategory().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new CreateCategoryController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnAddCategory().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                createCategory();
            }
        });
    }
    
    /**
     * Metóda pre vytvorenie novej kategórie izeb.
     */
    private void createCategory() {
        Category category = new Category();
        category.setDescription(window.getTaCategoryDescription());
        category.setPrice(window.getTfCategoryPrice());
        
        if (category.getPrice() == -1) {
            JOptionPane.showMessageDialog(window, "Zlý formát pre cenu, využíva sa . namiesto ,!");
            return;
        }
        
        if (category.hasEmptyAttribute()) {
            JOptionPane.showMessageDialog(window, "Je potrebné vyplniť všetky polia!");
            logger.warn("Neboli vyplnené všetky polia!");
            return;
        }
        
        if (database.checkCategory(category)) {
            JOptionPane.showMessageDialog(window, "Kategória sa v databáza nachádza!");
            logger.warn("Kategória sa v databáza nachádza!");
            return;
        }
        
        database.addCategory(category);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Kategória bola pridaná!");
        logger.info("Kategória bola pridaná!");
        clearCreateCategory();
    }
    
}
