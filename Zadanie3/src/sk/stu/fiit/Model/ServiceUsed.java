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
public class ServiceUsed implements Serializable {
    private Service service;
    private Date date;
    private int quantity;
    
    public ServiceUsed() {}

    public ServiceUsed(Service service, Date date, int quantity) {
        this.service = service;
        this.date = date;
        this.quantity = quantity;
    }
    
    public boolean hasEmptyAttribute() {
        return service == null || date == null || quantity == 0;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
