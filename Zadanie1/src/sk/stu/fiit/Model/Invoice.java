/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Trieda obsahujúca informácie o faktúre.
 * @author Ivan Vykopal
 */
public class Invoice {
    
    private Date date;
    private Customer customer;
    private ArrayList<Record> products;
    private double price = 0.f;

    /**
     * Konštruktor na nastevenie dátumu vystavenia faktúry, zákazníka a zoznamu
     * položiek
     * @param date - dátum vystavenia faktúry
     * @param customer - zákazník, ktorému je faktúra vystavená
     * @param products - zoznam produktov faktúry
     */
    public Invoice(Date date, Customer customer, ArrayList<Record> products) {
        this.date = date;
        this.customer = customer;
        this.products = products;
        getSummaryPrice();
    }
    
    /**
     * Metóda na výpočet výslednej ceny faktúry.
     * @return celková suma fakturovaných tovarov
     */
    private void getSummaryPrice() {
        for (Record rec : products) {
            price += rec.getQuantity() * rec.getProduct().getPrice();
        }
    }

    /**
     * Metóda na zistenie dátumu vystavenia faktúry.
     * @return dátum
     */
    public Date getDate() {
        return date;
    }

    /**
     * Metóda na nastavenie dátumu vystavenia faktúry.
     * @param date - dátum vystavenie faktúry
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Metóda na zistenie zákazníka.
     * @return zákazník
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Metóda na nastavenie zákazníka.
     * @param customer - zákazník, ktorému je určená faktúra
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Metóda na zistenie zoznamu fakturovaných položiek.
     * @return zoznam položiek
     */
    public ArrayList<Record> getProducts() {
        return products;
    }

    /**
     * Metóda na nastavenie zoznamu fakturovaných položiek.
     * @param products - zoznam fakturovaných položiek
     */
    public void setProducts(ArrayList<Record> products) {
        this.products = products;
    }

    /**
     * Metóda na zistenie celkovej sumy faktúry.
     * @return celková suma faktúry
     */
    public double getPrice() {
        return price;
    }

    /**
     * 
     * @param price 
     */
    public void setPrice(double price) {
        this.price = price;
    }
   
}
