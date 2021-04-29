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
import sk.stu.fiit.Model.Customer;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.SerializationClass;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class CreateCustomerController extends Controller {
    private static final Logger logger = Logger.getLogger(CreateCustomerController.class);

    private CreateCustomerController(Database database, MainWindow window) {
        super(database, window);
        
        logger.setLevel(Level.INFO);
        window.getspCreateCustomer().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new CreateCustomerController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnCreateCustomer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                createCustomer();
            }
        });
    }
    
    /**
     * Metóda pre vytvorenie zákazníka.
     */
    private void createCustomer() {
        Customer customer = new Customer();
        customer.setAddress(window.getTfAddress());
        customer.setEmail(window.getTfEmail());
        customer.setName(window.getTfName());
        customer.setNumber(window.getTfName());
        customer.setPhone(window.getTfPhone());
        customer.setPsc(window.getTfPSC());
        customer.setTown(window.getTfTown());
        
        if (customer.hasEmptyAttribute()) {
            JOptionPane.showMessageDialog(window, "Je potrebné vyplniť všetky polia!");
            logger.warn("Neboli vyplnené všetky polia!");
            return;
        }
        
        if (database.checkCustomer(customer)) {
            JOptionPane.showMessageDialog(window, "Zákazník sa v databáze nachádza!");
            logger.warn("Zákazník sa v databáze nachádza!");
            return;
        }
        
        database.addCustomer(customer);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Zákazník bol pridaný!");
        logger.info("Zákazník bol pridaný!");
        clearCreateCustomer();
    }
    
}
