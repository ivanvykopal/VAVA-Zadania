/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class SetDateController extends Controller {
    private static final Logger logger = Logger.getLogger(SetDateController.class);

    private SetDateController(Database database, MainWindow window) {
        super(database, window);
        
        logger.setLevel(Level.INFO);
        fillComboboxes();
        window.getpSetDate().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new SetDateController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnSetTime().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setDateTime();
            }
        });
    }
    
    /**
     * Metóda pre zmenu dátumu a času v aplikácii.
     */
    private void setDateTime() {
        int day = window.getCbDay().getSelectedIndex();
        int month = window.getCbMonth().getSelectedIndex();
        int year = window.getCbYear().getSelectedIndex() + window.getDate().getYear() - 1;
        int hour = window.getCbHour().getSelectedIndex() - 1;
        int minute = window.getCbMinutes().getSelectedIndex() - 1;
        String date;
        
        if (window.getLocale().getLanguage().equals("sk")) {
            date = String.format(window.getBundle().getString("DATE_LB"), day, month, year);
        } else if (window.getLocale().getLanguage().equals("en")) {
            date = String.format(window.getBundle().getString("DATE_LB"), month, day, year);
        } else {
            return;
        }
        
        // Kontrola existencie dátumu
        try {
            SimpleDateFormat format = new SimpleDateFormat(window.getBundle().getString("DATE_FORMAT"));
            format.setLenient(false);
            format.parse(date);
        } catch (ParseException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(window, "Zadaný dátum neexistuje!");
            logger.warn("Zadaný dátum neexistuje!", e);
            return;
        }
        LocalDate newDate = LocalDate.of(year, month, day);
        window.setDate(newDate);
        
        LocalTime newTime = LocalTime.of(hour, minute);
        window.setTime(newTime);
        JOptionPane.showMessageDialog(window, "Dátum a čas bol zmenené!");
        logger.info("Dátum a čas bol zmenené!");
        fillComboboxes();
    }
    
    /**
     * Metóda pre naplnenie výberových polí a zároveň nastavuje vybrané prvky
     * vo výberových poliach na "aktuálny dátum a čas".
     */
    private void fillComboboxes() {
        window.getCbDay().removeAllItems();
        window.getCbHour().removeAllItems();
        window.getCbMinutes().removeAllItems();
        window.getCbYear().removeAllItems();
        
        window.getCbDay().addItem(window.getBundle().getString("DAY"));
        for (int i = 1; i <= 31; i++) {
            window.getCbDay().addItem(String.valueOf(i));
        }
        
        window.getCbHour().addItem(window.getBundle().getString("HOURS"));
        for (int i = 0; i < 24; i++) {
            window.getCbHour().addItem(String.valueOf(i));
        }
        
        window.getCbMinutes().addItem(window.getBundle().getString("MINUTES"));
        for (int i = 0; i < 60; i++) {
            window.getCbMinutes().addItem(String.valueOf(i));
        }
        
        window.getCbYear().addItem(window.getBundle().getString("YEAR"));
        int year = window.getDate().getYear();
        for (int i = year; i < year + 50; i++) {
            window.getCbYear().addItem(String.valueOf(i));
        }
        int day = window.getDate().getDayOfMonth();
        window.getCbDay().setSelectedIndex(day);
        
        int hours = window.getTime().getHour();
        window.getCbHour().setSelectedIndex(hours + 1);
        
        int minutes = window.getTime().getMinute();
        window.getCbMinutes().setSelectedIndex(minutes + 1);
        
        int month = window.getDate().getMonthValue();
        window.getCbMonth().setSelectedIndex(month);
        
        window.getCbYear().setSelectedIndex(1);
    }
    
}
