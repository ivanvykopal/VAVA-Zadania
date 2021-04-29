/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.io.Serializable;

/**
 *
 * @author Ivan Vykopal
 */
public class Customer implements Serializable {
    private String name;
    private String address;
    private String number;
    private String town;
    private String psc; 
    private String phone;
    private String email;
    
    public Customer() {}

    public Customer(String name, String address, String number, String town, String psc, String phone, String email) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.town = town;
        this.psc = psc;
        this.phone = phone;
        this.email = email;
    }
    
    public boolean hasEmptyAttribute(){
        return name.equals("") || address.equals("") || 
                number.equals("") || town.equals("") || 
                psc.equals("") || phone.equals("") || 
                email.equals("");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean equals(Customer cus) {
        return name.equals(cus.getName()) && address.equals(cus.getAddress()) &&
                number.equals(cus.getNumber()) && town.equals(cus.getTown()) &&
                psc.equals(cus.getPsc()) && phone.equals(cus.getPhone()) &&
                email.equals(cus.getEmail());
    }
    
    
    
}