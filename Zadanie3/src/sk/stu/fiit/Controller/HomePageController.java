/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controller;

import java.util.Date;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class HomePageController extends Controller {

    private HomePageController(Database database, MainWindow window) {
        super(database, window);
        
        Date now = convertLocalDate(window.getDate());
        
        window.setLbAccEnd(window.getBundle().getString("TODAY_END") + " " + database.getAccEnd(now) + " " + window.getBundle().getString("HOME_PAGE_INFO"));
        window.setLbAccStart(window.getBundle().getString("TODAY_START") + " " + database.getAccStart(now) + " " + window.getBundle().getString("HOME_PAGE_INFO"));
        
        window.getpHomePage().setVisible(true);
    }
    
    public static void createController(Database database, MainWindow window) {
        new HomePageController(database, window);
    }

    @Override
    protected void initController() {
    }
    
}
