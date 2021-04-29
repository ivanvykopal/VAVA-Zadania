/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Trieda predstavujúca databázu pre uchovávanie zoznamu zákazníkov, faktúr a 
 * tovarov
 * @author Ivan Vykopal
 */
public class Database {
    private ArrayList<Customer> customers;
    private ArrayList<Invoice> invoices;
    private ArrayList<Product> products;

    /**
     * Konštruktor na nastavenie zoznamu zákazníkov, faktúr a tovarov.
     * @param customers - zoznam zákazníkov
     * @param invoices - zoznam faktúr
     * @param products - zoznam produktov
     */
    public Database(ArrayList<Customer> customers, ArrayList<Invoice> invoices, 
            ArrayList<Product> products) {
        this.customers = customers;
        this.invoices = invoices;
        this.products = products;
    }
    
    /**
     * Metóda na zistenie zozanmu zákazníkov.
     * @return zoznam zákazníkov
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Metóda na nastavenie zozanmu zákazníkov.
     * @param customers - zoznam zákazníkov
     */
    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    /**     
     * Metóda na zistenie zozanmu faktúr.
     * @return zoznam faktúr
     */
    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    /**
     * Metóda na nastavenie zozanmu faktúr.
     * @param invoices - zoznam faktúr
     */
    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    /**
     * Metóda na zistenie zozanmu tovarov.
     * @return zoznam tovarov
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * Metóda na nastavenie zozanmu tovarov.
     * @param products - zoznam tovarov
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    
    /**
     * 
     * @param customer
     * @return 
     */
    public Customer addCutomer(Customer customer) {
        Customer cus = findCustomer(customer);
        if (cus == null) {
            customers.add(customer);
            return customer;
        }
        return null;
    }
    
    /**
     * 
     * @param product
     * @return 
     */
    public Product addProduct(Product product) {
        Product prod = findProduct(product);
        if (prod == null) {
            products.add(product);
            return product;
        }
        return null;
    }
    
    /**
     * 
     * @param invoice 
     */
    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }
    
    /**
     * 
     * @param customer
     * @return 
     */
    public Customer findCustomer(Customer customer) {
        String name = customer.getName();
        String address = customer.getAddress();
        String number = customer.getNumber();
        String town = customer.getTown();
        String psc = customer.getPsc();
        for (Customer cus: customers) {
            if (cus.getName().equals(name) && cus.getAddress().equals(address) &&
                    cus.getNumber().equals(number) && cus.getTown().equals(town) &&
                    cus.getPsc().equals(psc)) {
                return customer;
            }
        }
        return null;
    }
    
    /**
     * 
     * @return product
     */
    public Product findProduct(Product product) {
        String name = product.getName();
        String description = product.getDescription();
        double price = product.getPrice();
        for (Product prod: products) {
            if (prod.getName().equals(name) && prod.getDescription().equals(description) 
                    && prod.getPrice() == price) {
                return product;
            }
        }
        return null;
    }
    
    public Invoice findInvoice(String dateString, double price) {
        Date date;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        } catch (ParseException ex) {
            return null;
        }
        for (Invoice invoice: invoices) {
            if (invoice.getDate().equals(date) && invoice.getPrice() == price) {
                return invoice;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param index
     * @param customer
     * @return 
     */
    public Customer editCustomer(int index, Customer customer) {
        Customer cus = findCustomer(customer);
        if (cus == null) {
            customers.set(index, customer);
            return customer;
        }
        return null;
    }
    
    /**
     * 
     * @param index
     * @param product
     * @return 
     */
    public Product editProduct(int index, Product product) {
        Product prod = findProduct(product);
        if (prod == null) {
            products.set(index, product);
            return product;
        }
        return null;
    }
}
