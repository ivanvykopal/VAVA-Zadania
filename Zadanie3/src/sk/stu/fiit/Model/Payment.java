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
public class Payment implements Serializable {
    private Date date;
    private Accommodation accomodation;
    private PaymentType paymentType;

    public Payment(Date date, Accommodation accomodation, PaymentType pymentType) {
        this.date = date;
        this.accomodation = accomodation;
        this.paymentType = pymentType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Accommodation getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(Accommodation accomodation) {
        this.accomodation = accomodation;
    }

    public PaymentType getPymentType() {
        return paymentType;
    }

    public void setPymentType(PaymentType pymentType) {
        this.paymentType = pymentType;
    }
             
}
