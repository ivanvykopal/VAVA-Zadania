/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

/**
 * Trieda obsahujúca informácie o zákazníkovi.
 * @author Ivan Vykopal
 */
public class Customer {
    private String name;
    private String address;
    private String number;
    private String town;
    private String psc;

    /**
     * Konštruktor na nastevanie mena, adresy, čísla, mest a a PSČ zákazníka.
     * @param name - meno zákazníka
     * @param address - adresa zákazníka
     * @param number - číslo adresy zákazníka
     * @param town - mesto bydliska zákazníka
     * @param psc - poštové smerové číslo
     */
    public Customer(String name, String address, String number, String town, String psc) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.town = town;
        this.psc = psc;
    }

    /**
     * Metóda na zistenie mena zákazníka. 
     * @return meno zákazníka
     */
    public String getName() {
        return name;
    }

    /**
     * Metóda na nastavenia mena zákazníka.
     * @param name - meno zákazníka
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metóda na zistenie adresy zákazníka. 
     * @return adresa zákazníka
     */
    public String getAddress() {
        return address;
    }
    /**
     * Metóda na nastavenia adresy zákazníka.
     * @param address - adresa zákazníka
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Metóda na zistenie čísla bydliska zákazníka. 
     * @return číslo bydliska zákazníka
     */
    public String getNumber() {
        return number;
    }

    /**
     * Metóda na nastavenia čísla bydliska zákazníka.
     * @param number - číslo bydliska zákazníka
     */
    public void setNumber(String number) {
        this.number = number;
    }
    /**
     * Metóda na zistenie mesta zákazníka. 
     * @return mesto zákazníka
     */
    public String getTown() {
        return town;
    }

    /**
     * Metóda na nastavenia mesta zákazníka.
     * @param town - mesto zákazníka
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     * Metóda na zistenie PSČ zákazníka. 
     * @return 
     */
    public String getPsc() {
        return psc;
    }

    /**
     * Metóda na nastavenia PSČ zákazníka.
     * @param psc 
     */
    public void setPsc(String psc) {
        this.psc = psc;
    }
    
}
