/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.SerializationClass;
import sk.stu.fiit.Model.Service;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class CreateServiceController extends Controller {
    private static final Logger logger = Logger.getLogger(CreateServiceController.class);

    private CreateServiceController(Database database, MainWindow window) {
        super(database, window);
        
        window.getpCreateService().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new CreateServiceController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnCreateService().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                createService();
            }
        });
    }
    
    /**
     * Metóda pre vytvorenie služby.
     */
    private void createService() {
        Service service = new Service();
        service.setDescription(window.getTaServiceDescription());
        service.setName(window.getTfServiceName());
        service.setPrice(window.getTfServicePrice());
        
        if (service.getPrice() == -1) {
            JOptionPane.showMessageDialog(window, "Zlý formát pre cenu! Využíva sa . namiesto ,!");
            return;
        }
        
        if (service.hasEmptyAttribute()) {
            JOptionPane.showMessageDialog(window, "Je potrebné vyplniť všetky polia!");
            logger.warn("Neboli vyplnené všetky polia!");
            return;
        }
        
        if (database.checkService(service)) {
            JOptionPane.showMessageDialog(window, "Zadaná služba sa už nachádza v systéme!");
            logger.warn("Služba už v systéme existuje!");
            return;
        }
        
        database.addService(service);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Služba bola pridaná!");
        logger.info("Služba bola pridaná!");
        clearCreateService();
    }
}
