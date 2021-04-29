/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 *
 * @author Ivan Vykopal
 */
public final class SerializationClass {
    private static final Logger logger = Logger.getLogger(SerializationClass.class);
    
    public static void serialize(Database database) {
        logger.setLevel(Level.INFO);
        try {
            FileOutputStream file = new FileOutputStream("database.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(database);
            out.close();
            file.close();
        } catch(IOException ex) {
            logger.warn("Chyba pri serializácií databázy!", ex);
        }
    }
    
    public static Database deserialize() {
        logger.setLevel(Level.INFO);
        Database database = null;
        try {
            FileInputStream file = new FileInputStream("database.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            database = (Database) in.readObject();
            in.close();
            file.close();
            return database;
        } catch (IOException ex) {
            logger.warn("Chyb pri načítaní serializovaných dát!",ex);
            return Database.createDatabase();
        } catch (ClassNotFoundException ex) {
            logger.warn("Nebol nájdený súbor!", ex);
            return Database.createDatabase();
        }
    }
    
}
