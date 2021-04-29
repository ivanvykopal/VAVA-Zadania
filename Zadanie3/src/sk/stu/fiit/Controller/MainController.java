/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.SerializationClass;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class MainController extends Controller {

    private MainController(Database database, MainWindow window) {
        super(database, window);

        if (window.getLocale().getLanguage().equals("sk")) {
            window.getCbLanguage().setSelectedIndex(0);
        } else if (window.getLocale().getLanguage().equals("en")) {
            window.getCbLanguage().setSelectedIndex(1);
        }

        callculateDateTime();

        window.setVisible(true);
        hideAll();
        HomePageController.createController(database, window);

        initController();
    }

    public static void createController(Database database, MainWindow window) {
        new MainController(database, window);
    }

    @Override
    protected void initController() {

        window.getMiAccWithReservation().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            AddAcc1Controller.createController(database, window);
        });

        window.getMiAccWithoutReservation().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            AddAcc2Controller.createController(database, window);
        });

        window.getMiAddServiceUsage().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            AddServiceUsageController.createController(database, window);
        });

        window.getMiCancelReservation().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            CancelReservationController.createController(database, window);
        });

        window.getMiChangeDate().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            SetDateController.createController(database, window);
        });

        window.getMiCreateCategory().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            CreateCategoryController.createController(database, window);
        });

        window.getMiCreateCustomer().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            CreateCustomerController.createController(database, window);
        });

        window.getMiCreateReservation().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            CreateReservationController.createController(database, window);
        });

        window.getMiCreateRoom().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            CreateRoomController.createController(database, window);
        });

        window.getMiCreateService().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            CreateServiceController.createController(database, window);
        });

        window.getMiCustomerHistory().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            CustomerHistoryController.createController(database, window);
        });

        window.getMiEditRoom().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            EditRoomController.createController(database, window);
        });

        window.getMiPayment().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            CreatePaymentController.createController(database, window);
        });

        window.getMiViewEnded().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            ViewEndedAccController.createController(database, window);
        });

        window.getMiRoomHistory().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            RoomHistoryController.createController(database, window);
        });

        window.getMiViewHomePage().addActionListener(e -> {
            hideAll();
            window.removeListeners();
            clearAll();
            HomePageController.createController(database, window);
        });

        window.getCbLanguage().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    changeLanguage();
                }
            }
        });

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SerializationClass.serialize(database);
            }
        });
    }

    /**
     * Metóda pre zmenu jazyka.
     */
    private void changeLanguage() {
        int language = window.getCbLanguage().getSelectedIndex();
        MainWindow newWindow;
        switch (language) {
            case 0:
                newWindow = new MainWindow();
                newWindow.setDate(window.getDate());
                newWindow.setTime(window.getTime());
                newWindow.setBundle("sk/stu/fiit/View/Bundle_SK", new Locale("SK"));
                MainController.createController(database, newWindow);
                window.dispose();
                break;
            case 1:
                newWindow = new MainWindow();
                newWindow.setDate(window.getDate());
                newWindow.setTime(window.getTime());
                newWindow.setBundle("sk/stu/fiit/View/Bundle_EN", new Locale("EN"));
                MainController.createController(database, newWindow);
                window.dispose();
                break;
        }
    }

    /**
     * Metóda pre aktualizáciu dátumu.
     */
    private void callculateDateTime() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int day = window.getDate().getDayOfMonth();
                int month = window.getDate().getMonthValue();
                int year = window.getDate().getYear();
                int hour = window.getTime().getHour();
                int minute = window.getTime().getMinute();
                int seconds = window.getTime().getSecond();
                window.setLbTime(String.format("%02d:%02d:%02d", hour, minute, seconds));
                if (window.getLocale().getLanguage().equals("sk")) {
                    window.setLbDate(String.format(window.getBundle().getString("DATE_LB"), day, month, year));
                } else if (window.getLocale().getLanguage().equals("en")) {
                    window.setLbDate(String.format(window.getBundle().getString("DATE_LB"), month, day, year));
                }

                if (hour == 23 && minute == 59 && seconds == 59) {
                    LocalDate newDate = window.getDate().plusDays(1);
                    window.setDate(newDate);
                    window.setTime(LocalTime.of(0, 0, 0));
                } else {
                    LocalTime newTime = window.getTime().plusSeconds(1);
                    window.setTime(newTime);
                }
            }
        }, 0, 1000);
    }

}
