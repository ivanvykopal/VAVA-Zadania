/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Employer;

/**
 *
 * @author Ivan Vykopal
 */
public final class AddEmployerController extends Controller {
    private String imagePath;

    public AddEmployerController(Database database, MainWindow mainWindow) {
        super(database, mainWindow);
        mainWindow.removeListeners();
        mainWindow.setLbAddEmployerImage(null);
        
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnAddEmployer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addEmployer();
            }
        });
        
        mainWindow.getBtnChooseImage().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                chooseImage();
            }
        });
    }
    
    /**
     * Metóda na pridanie zamestnávateľa do databázy.
     */
    private void addEmployer() {
        String name = mainWindow.getTfName();
        String field = mainWindow.getTfField();
        
        int count = mainWindow.getTfEmployeesCount();
        if (count == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Zlý formát!");
            return;
        }
        
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainWindow, "Chyba súboru!");
            return;
        }
        
        if (name.equals("") || field.equals("") || count == 0 || image == null) {
            JOptionPane.showMessageDialog(mainWindow, "Nie sú vyplnené všetky polia!");
            return;
        }
        
        Employer emp = database.addEmployer(new Employer(name, field, count, image));
        if (emp == null) {
            JOptionPane.showMessageDialog(null, "Zadaný zamestnávateľ sa už v systéme nachádza!");
            return;
        }
        JOptionPane.showMessageDialog(mainWindow, "Zamestnávateľ bol pridaný.");
        imagePath = "";
        clearAll();
    }        
    
    /**
     * Meóda pre výber obrázka.
     */
    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Obrázky", ImageIO.getReaderFileSuffixes()));
        
        int result = fileChooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainWindow, "Chyba súboru!");
                return;
            }
            mainWindow.setLbAddEmployerImage(new ImageIcon(image));
        }
    }
}
