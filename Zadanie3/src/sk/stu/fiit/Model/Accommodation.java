/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ivan Vykopal
 */
public class Accommodation implements Serializable {

    private Reservation reservation;
    private Customer customer;
    private Room room;
    private Date from = null;
    private Date to = null;
    private double price;
    private ArrayList<ServiceUsed> services = new ArrayList<>();
    private boolean paid = false;
    
    public Accommodation() { }

    public Accommodation(Reservation reservation) {
        this.reservation = reservation;
        this.customer = reservation.getCustomer();
        this.room = reservation.getRoom();
        this.from = reservation.getFrom();
        this.to = reservation.getTo();
        this.price = reservation.getPrice();
        updatePrice();
    }

    public Accommodation(Customer customer, Room room, Date from, Date to, double price) {
        this.reservation = null;
        this.customer = customer;
        this.room = room;
        this.from = from;
        this.to = to;
        this.price = price;
        updatePrice();
    }
    
    public boolean hasEmptyAttribute() {
        return customer == null || room == null || from == null || to == null || price == 0;
    }
    
    public boolean hasDateProblem() {
        if (to.before(from) || to.equals(from)) {
            return true;
        }
        return false;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        updatePrice();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        updatePrice();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
        updatePrice();
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
        updatePrice();
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
        updatePrice();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        updatePrice();
    }

    public ArrayList<ServiceUsed> getServices() {
        return services;
    }

    public void addService(ServiceUsed service) {
        this.services.add(service);
        updatePrice();
    }

    public void setServices(ArrayList<ServiceUsed> services) {
        this.services = services;
        updatePrice();
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    private void updatePrice() {
        if (from == null || to == null || room == null) {
            return;
        }
        long milliseconds = to.getTime() - from.getTime();
        long diff = TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
        double roomPrice = diff * room.getCategory().getPrice();
        if (diff > 10) {
                roomPrice *= 0.90;
        }
        double servicePrice = 0;
        if (services != null) {
            for (ServiceUsed service : services) {
                servicePrice += service.getQuantity() * service.getService().getPrice();
            }
        }

        price = roomPrice + servicePrice;
    }

    public boolean intersect(Date objFrom, Date objTo, String label) {
        if (room.getLabel().equals(label)) {
            return intersect(objFrom, objTo);
        }
        return false;
    }

    public boolean intersect(Date objFrom, Date objTo) {
        if ((objFrom.after(from) || objFrom.equals(from)) && objFrom.before(to)) {
            return true;
        }
        if (objTo.after(from) && (objTo.before(to) || objTo.equals(to))) {
            return true;
        }
        if ((objFrom.before(from) || objFrom.equals(from)) && (objTo.after(to) || objTo.equals(to))) {
            return true;
        }
        return false;
    }

    public boolean equals(Accommodation obj) {
        return obj.getRoom().getLabel().equals(room.getLabel()) && 
                customer.equals(obj.getCustomer()) && from.equals(obj.getFrom()) &&
                to.equals(obj.getTo());
    }
    
}
