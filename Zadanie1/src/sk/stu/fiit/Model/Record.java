/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

/**
 * Trieda predstavujúca položku zoznamu tovarov vo faktúre.
 * @author Ivan Vykopal
 */
public class Record {
    private Product product;
    private int quantity;

    /**
     * Konštruktor na nastavenie tovaru a jeho množstva. 
     * @param product - tovar
     * @param quantity - množstvo tovaru
     */
    public Record(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Metóda na zistenie tovaru.
     * @return tovar
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Metóda na nastavenie tovaru.
     * @param product - tovar
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Metóda na zistenie množstva tovaru.
     * @return množstvo tovaru
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Metóda na nastavenie množstva tovaru.
     * @param quantity - množstvo tovaru
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
