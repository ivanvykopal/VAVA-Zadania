/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public abstract class Controller {
    protected final Database database;
    protected final MainWindow window;
    
    public Controller(Database database, MainWindow window) {
        this.database = database;
        this.window = window;
    }
      
    protected abstract void initController();
    
    protected void hideAll() {
        window.getspCreateCustomer().setVisible(false);
        window.getpCreateCategory().setVisible(false);
        window.getspCreateRoom().setVisible(false);
        window.getpCreateService().setVisible(false);
        window.getspCreateReservation().setVisible(false);
        window.getspCancelReservation().setVisible(false);
        window.getspEndedAccommodations().setVisible(false);
        window.getpSetDate().setVisible(false);
        window.getspAddServiceUsage().setVisible(false);
        window.getspEditRoom().setVisible(false);
        window.getspAddAccommodation1().setVisible(false);
        window.getspAddAccommodation2().setVisible(false);
        window.getspCreatePayment().setVisible(false);
        window.getspViewCustomerHistory().setVisible(false);
         window.getspRoomHistory().setVisible(false);
         window.getpHomePage().setVisible(false);

    }
    
    protected void clearAll() {
        clearCreateCustomer();
        clearCreateCategory();
        clearCreateRoom();
        clearCreateService();
        clearCreateReservation();
        clearCancelReservation();
        clearViewEndedAcc();
        clearAddServiceUsage();
        clearCustomerHistory();
        clearEditRoom();
        clearAddAccommodation1();
        clearAddAccommodation2();
        clearCreatePayment();
        clearRoomHistory();
    }
    
    protected void clearCreateCustomer() {
        window.setTfAddress("");
        window.setTfEmail("");
        window.setTfName("");
        window.setTfNumber("");
        window.setTfPSC("");
        window.setTfPhone("");
        window.setTfTown("");
    }
    
    protected void clearCreateCategory() {
        window.setTfCategoryPrice("");
        window.setTaCategoryDescription("");
    }
    
    protected void clearCreateRoom() {
        window.setTfRoomLabel("");
        window.setTaRoomDescription("");
    }
    
    protected void clearCreateService() {
        window.setTfServiceName("");
        window.setTfServicePrice("");
        window.setTaServiceDescription("");
    }
    
    protected void clearCreateReservation() {
        window.getTbReservationsRoomsModel().setRowCount(0);
        window.setLbResRoom(window.getBundle().getString("CHOOSED_ROOM"));
        window.setTfReservationName("");
        window.setTfReservationPhone("");
        window.setTfReservationPrice("");
        window.setFtfReservationTo("");
        window.setFtfReservationFrom("");
    }
    
    protected void clearCancelReservation() {
        window.setLbCancelResFrom(window.getBundle().getString("DATE_FROM"));
        window.setLbCancelResName(window.getBundle().getString("CUSTOMER_NAME"));
        window.setLbCancelResPrice(window.getBundle().getString("PRICE"));
        window.setLbCancelResRoom(window.getBundle().getString("ROOM"));
        window.setLbCancelResTo(window.getBundle().getString("DATE_TO"));
    }
    
    protected void clearViewEndedAcc() {
        window.setLbEndedFrom(window.getBundle().getString("DATE_FROM"));
        window.setLbEndedName(window.getBundle().getString("CUSTOMER_NAME"));
        window.setLbEndedPrice(window.getBundle().getString("PRICE"));
        window.setLbEndedRoom(window.getBundle().getString("ROOM"));
        window.setLbEndedTo(window.getBundle().getString("DATE_TO"));
    }
    
    protected void clearAddServiceUsage() {
        window.setTfQuantity("");
        window.setTfAddUsageRoom("");
    }
    
    protected void clearCustomerHistory() {
        window.getTbCustomerAccModel().setRowCount(0);
        window.getTbUsedServicesModel().setRowCount(0);
    }
    
    protected Date convertLocalDate(LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    protected void clearEditRoom() {
        window.setTfEditName("");
        window.setTaEditDescription("");
        window.setLbPhoto(null);
    }
    
    protected void clearAddAccommodation1() {
        window.setLbAccName1(window.getBundle().getString("CUSTOMER_NAME"));
        window.setLbAccFrom1(window.getBundle().getString("DATE_FROM"));
        window.setLbAccPrice1(window.getBundle().getString("PRICE"));
        window.setLbAccRoom1(window.getBundle().getString("ROOM"));
        window.setLbAccTo1(window.getBundle().getString("DATE_TO"));
    }
    
    protected void clearAddAccommodation2() {
        window.setTfAccName2("");
        window.setTfAccPhone2("");
        window.setTfAccPrice2("");
        window.setFtfAccFrom2("");
        window.setFtfAccTo2("");
        window.setLbAccRoom2(window.getBundle().getString("CHOOSED_ROOM"));
        window.getTbAccRoomsModel().setRowCount(0);
    }
    
    protected void clearCreatePayment() {
        window.setLbPaymentFrom(window.getBundle().getString("DATE_FROM"));
        window.setLbPaymentName(window.getBundle().getString("CUSTOMER_NAME"));
        window.setLbPaymentPrice(window.getBundle().getString("PRICE"));
        window.setLbPaymentTo(window.getBundle().getString("DATE_TO"));
        window.setLbPaymnetRoom(window.getBundle().getString("ROOM"));
        window.getCbPaymentType().setSelectedIndex(0);
    }
    
    protected void clearRoomHistory() {
        window.getTbAccHistoryModel().setRowCount(0);
    }
}
