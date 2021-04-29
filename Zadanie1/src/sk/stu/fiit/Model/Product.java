/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

/**
 * Trieda obsahujúca informácie o tovare.
 * @author Ivan Vykopal
 */
public class Product {
    
    private String name;
    private String description;
    private double price;

    /**
     * Konštruktor na nastavenie názvu, opisu a ceny tovaru.
     * @param name - názov tovaru
     * @param description - opis tovaru
     * @param price - cena tovaru
     */
    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Metóda na zistenie názvu tovaru.
     * @return názov tovaru
     */
    public String getName() {
        return name;
    }

    /**
     * Metóda na nastavenie názvu tovaru.
     * @param name - názov tovaru
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metóda na zistenie opisu tovaru.
     * @return opis tovaru
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metóda na nastavenie opisu tovaru.
     * @param description - opis tovaru
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Metóda na zistenie ceny tovaru.
     * @return cena tovaru
     */
    public double getPrice() {
        return price;
    }

    /**
     * Metóda na nastavenie ceny tovaru.
     * @param price - cena tovaru
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
