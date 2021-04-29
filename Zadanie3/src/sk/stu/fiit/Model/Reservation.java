/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ivan Vykopal
 */
public class Reservation implements Serializable {

    private Customer customer;
    private Room room;
    private Date from;
    private Date to;
    private double price;

    public Reservation() {
    }

    public Reservation(Customer customer, Room room, Date from, Date to, double price) {
        this.customer = customer;
        this.room = room;
        this.from = from;
        this.to = to;
        this.price = price;
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
    
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    @Override
    public boolean equals(Object obj) {
        Reservation reservation = (Reservation) obj;
        return from.compareTo(reservation.getFrom()) == 0 && to.compareTo(reservation.getTo()) == 0
                && room.getLabel().equals(reservation.getRoom().getLabel()) && customer.getName().equals(reservation.getCustomer().getName()) && customer.getPhone().equals(reservation.getCustomer().getPhone());
    }

}
