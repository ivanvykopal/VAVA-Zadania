/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Room;
import sk.stu.fiit.Model.SerializationClass;
import sk.stu.fiit.View.MainWindow;

/**
 *
 * @author Ivan Vykopal
 */
public final class EditRoomController extends Controller {
    private Room room = null;
    private int index = 0;
    private ArrayList<ImageIcon> images;
    private static final Logger logger = Logger.getLogger(EditRoomController.class);

    private EditRoomController(Database database, MainWindow window) {
        super(database, window);
        images = new ArrayList<>();
        logger.setLevel(Level.INFO);
        window.getspEditRoom().setVisible(true);
        
        initController();
    }
    
    public static void createController(Database database, MainWindow window) {
        new EditRoomController(database, window);
    }

    @Override
    protected void initController() {
        window.getBtnEditNext().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                next();
            }
        });
        
        window.getBtnEditPrev().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                prev();
            }
        });
        
        window.getBtnEditRoom().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                editRoom();
            }
        });
        
        window.getBtnAddPhoto().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addPhoto();
            }
        });
        
        window.getBtnEditFill().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                fillRoom();
            }
        });
    }
    
    /**
     * Met??da pre zobrazenie nasleduj??ceho obr??zku.
     */
    private void next() {
        index++;
        if (index >= images.size()) {
            index--;
            JOptionPane.showMessageDialog(window, "Nie s?? ??iadne ??al??ie obr??zky!");
        } else {
            window.setLbPhoto(images.get(index));
        }
    }
    
    /**
     * Met??da pre zobrazenie predch??dzaj??ceho obr??zku.
     */
    private void prev() {
        index--;
        if (index < 0) {
            index++;
            JOptionPane.showMessageDialog(window, "Ste na prvom obr??zku!");
        } else {
            window.setLbPhoto(images.get(index));
        }
    }
    
    /**
     * Met??da pre ??pravu inform??ci?? o izbe.
     */
    private void editRoom() {
        String description = window.getTaEditDescription();
        if (room == null) {
            JOptionPane.showMessageDialog(window, "Nie je vybran?? ??iadna izba!");
            logger.warn("Nebola vybran?? ??iadna izba!");
            return;
        }
        
        room.setDescription(description);
        room.setImages(images);
        
        database.setRoom(room.getLabel(), room);
        SerializationClass.serialize(database);
        JOptionPane.showMessageDialog(window, "Izba bola upraven??!");
        logger.info("Izba bola upraven??!");
        clearEditRoom();
        images = new ArrayList<>();
        index = 0;
    }
    
    /**
     * Met??da pre pridanie obr??zku k izbe.
     */
    private void addPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Obr??zky", ImageIO.getReaderFileSuffixes()));
        
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            ImageIcon image = new ImageIcon(imagePath);
            images.add(image);
            index = images.size() - 1;
            window.setLbPhoto(images.get(index));
        }
    }
    
    /**
     * Met??da pre naplnenie inform??cii o vybranej izbe v okne.
     */
    private void fillRoom() {
        String name = window.getTfEditName();
        
        room = database.getRoom(name);
        if (room == null) {
            JOptionPane.showMessageDialog(window, "Zadan?? izba nie je v syst??me!");
            logger.warn("Zadan?? izba nie je v syst??me!");
            clearEditRoom();
            images = new ArrayList<>();
            index = 0;
            return;
        }
        images = new ArrayList<>(room.getImages());
        
        window.setTaEditDescription(room.getDescription());
        if (room.getImages().size() > 0) {
            index = 0;
            window.setLbPhoto(images.get(index));
        } else {
            window.setLbPhoto(null);
            index = 0;
        }
    }

}
