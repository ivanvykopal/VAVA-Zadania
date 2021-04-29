/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Controllers;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sk.stu.fiit.GUI.MainWindow;
import sk.stu.fiit.Model.Administrator;
import sk.stu.fiit.Model.Database;
import sk.stu.fiit.Model.Employer;
import sk.stu.fiit.Model.Freelancer;
import sk.stu.fiit.Model.Job;
import sk.stu.fiit.Model.Programmer;
import sk.stu.fiit.Model.Rent;
import sk.stu.fiit.Model.SecurityConsultant;

/**
 *
 * @author Ivan Vykopal
 */
public final class CreateRentController extends Controller {
    private HashMap<String, ArrayList<Freelancer>> table = new HashMap<>();
    private int index = -1;
    private Job job;
    
    public CreateRentController(Database database, MainWindow mainWindow, Job job) {
        super(database, mainWindow);
        this.job = job;
        
        checkJob();
        
        mainWindow.getTbChoosedSpecialistsModel().setRowCount(0);
        
        fillHashTable();
        fillSpecialists();
        fillEmployers();
        
        initController();
    }

    @Override
    void initController() {
        mainWindow.getBtnChooseEmployerRent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                chooseEmployer();
            }
        });
        
        mainWindow.getBtnChooseSpecialist().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                chooseSpecialist();
            }
        });
        
        mainWindow.getBtnCreateRent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                createRent();
            }
        });
        
        mainWindow.getCbSpecialist().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    fillSpecialists();
                }
            }
        });
        
        mainWindow.getBtnRemoveSpecialist().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                removeSpecialist();
            }
        });
    }

    /**
     * Metóda na výber zamestnávateľa z tabuľky a následné naplnenie labelov.
     */
    private void chooseEmployer() {
        index = mainWindow.getTbEmployersRentTable().getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nebol vybraný žiaden zamestnávateľ!");
            return;
        }
        
        Employer employer = database.getEmployers().get(index);
        if (job != null && !employer.getName().equals(job.getName())) {
            job = null;
        }
        mainWindow.setLbNameEmployer(employer.getName());
        mainWindow.setLbFieldEmployer(employer.getBusinessArea());
        mainWindow.setLbCountEmployer("" + employer.getEmployeesCount());
        mainWindow.setLbLogoEmployer(new ImageIcon(employer.getLogo()));
    }
    
    /**
     * Metóda na kontrolu, či bola vybraná pracovná ponuka. 
     */
    private void checkJob() {
        if (job == null) {
            return;
        }
        
        Employer employer = database.getEmployer(job.getEmployerName());
        
        if (employer == null) {
            return;
        }
        
        mainWindow.setLbNameEmployer(employer.getName());
        mainWindow.setLbFieldEmployer(employer.getBusinessArea());
        mainWindow.setLbCountEmployer("" + employer.getEmployeesCount());
        mainWindow.setLbLogoEmployer(new ImageIcon(employer.getLogo()));
    }
    
    /**
     * Metóda na výber špecialistu z tabuľky špecialistov a jeho presun do tabuľky
     * vybraných špecialistov.
     */
    private void chooseSpecialist() {
        String item = (String) mainWindow.getCbSpecialist().getSelectedItem();
        int rowsCount = mainWindow.getTbSpecialistTable().getSelectedRowCount();
        if (rowsCount == 0) {
            JOptionPane.showMessageDialog(mainWindow, "Nebol vybraný žiaden špecialista!");
            return;
        }
        int[] indexes = mainWindow.getTbSpecialistTable().getSelectedRows();
        ArrayList<Freelancer> freelancers;
        ArrayList<Freelancer> newList;
        switch (item) {
            case "Programátor":
                freelancers = table.get("programmer");
                for (int i = 0; i < rowsCount; i++) {
                    Object[] row = new Object[9];
                    row[0] = freelancers.get(indexes[i]).getId();
                    row[1] = "Programátor";
                    row[2] = freelancers.get(indexes[i]).getManDay();
                    row[3] = freelancers.get(indexes[i]).getExperience();
                    row[4] = freelancers.get(indexes[i]).getEducation();
                    row[5] = String.join(", ",((Programmer) freelancers.get(indexes[i])).getTypes());
                    row[6] = String.join(", " , freelancers.get(indexes[i]).getCertificates());
                    row[7] = "-";
                    row[8] = "-";
                    mainWindow.getTbChoosedSpecialistsModel().addRow(row);
                }
                newList = new ArrayList<>(freelancers);
                for (int i = 0; i < rowsCount; i++) {
                    newList.remove(freelancers.get(indexes[i]));
                }
                table.put("programmer", newList);
                fillSpecialists();
                break;
            case "Administrátor":
                freelancers = table.get("administrator");
                for (int i = 0; i < rowsCount; i++) {
                    Object[] row = new Object[9];
                    row[0] = freelancers.get(indexes[i]).getId();
                    row[1] = "Administrátor";
                    row[2] = freelancers.get(indexes[i]).getManDay();
                    row[3] = freelancers.get(indexes[i]).getExperience();
                    row[4] = freelancers.get(indexes[i]).getEducation();
                    row[5] = String.join(", ",((Administrator) freelancers.get(indexes[i])).getTypes());
                    row[6] = String.join(", ", freelancers.get(indexes[i]).getCertificates());
                    row[7] = "-";
                    row[8] = ((Administrator) freelancers.get(indexes[i])).getPreferredPlatform();
                    mainWindow.getTbChoosedSpecialistsModel().addRow(row);
                }
                newList = new ArrayList<>(freelancers);
                for (int i = 0; i < rowsCount; i++) {
                    newList.remove(freelancers.get(indexes[i]));
                }
                table.put("administrator", newList);
                fillSpecialists();
                break;
            default:
                freelancers = table.get("specialist");
                for (int i = 0; i < rowsCount; i++) {
                    Object[] row = new Object[9];
                    row[0] = freelancers.get(indexes[i]).getId();
                    row[1] = "Bezpečnostný konzultant";
                    row[2] = freelancers.get(indexes[i]).getManDay();
                    row[3] = freelancers.get(indexes[i]).getExperience();
                    row[4] = freelancers.get(indexes[i]).getEducation();
                    row[5] = "-";
                    row[6] = String.join(", ", freelancers.get(indexes[i]).getCertificates());
                    if (((SecurityConsultant) freelancers.get(indexes[i])).isAuditor()) {
                        row[7] = "Áno";
                    } else {
                        row[7] = "Nie";
                    }
                    row[8] = "-";
                    mainWindow.getTbChoosedSpecialistsModel().addRow(row);
                }
                newList = new ArrayList<>(freelancers);
                for (int i = 0; i < rowsCount; i++) {
                    newList.remove(freelancers.get(indexes[i]));
                }
                table.put("specialist", newList);
                fillSpecialists();
                break;
        }
    }
    
    /**
     * Metóda na vytvorenie prenájmu.
     */
    private void createRent() {
        if (index == -1 && job == null) {
            JOptionPane.showMessageDialog(mainWindow, "Nebol vybraný žiaden zamestnávateľ!");
            return;
        }
        
        Employer employer;
        if (index != -1) {
            employer = database.getEmployers().get(index);
        } else {
            employer = database.getEmployer(job.getEmployerName());
        }
        
        ArrayList<Freelancer> employees = new ArrayList<>();
        DefaultTableModel model = mainWindow.getTbChoosedSpecialistsModel();
        int rows = model.getRowCount();
        if (rows == 0) {
            JOptionPane.showMessageDialog(mainWindow, "Nebol vybraný žiaden špecialista!");
            return;
        }
        for (int i = 0; i < rows; i++) {
            int id = (int) model.getValueAt(i, 0);
            database.setFreelancer(id, true);
            employees.add(database.getFreelancer(id));
        }
        
        database.addRent(new Rent(employer, employees, job));
        database.removeJob(job);
        JOptionPane.showMessageDialog(mainWindow, "Záznam o prenájme bol vytvorený!");
        mainWindow.getTbChoosedSpecialistsModel().setRowCount(0);
        index = -1;
        clearAll();
    }
    
    /**
     * Metóda pre naplnenie hash tabuľky.
     * Hastabuľka obsahuje pre každý druh špecialistu zoznam daných špecialistov.
     */
    private void fillHashTable() {
        ArrayList<Freelancer> freelancers = database.getFreelancers();
        for (Freelancer freelancer : freelancers) {
            if (freelancer.isRented()) {
                continue;
            }
            if (freelancer instanceof Programmer) {
                ArrayList<Freelancer> tab = table.get("programmer");
                if (tab == null) {
                    tab = new ArrayList<>();
                }
                tab.add(freelancer);
                table.put("programmer", tab);
            } else if (freelancer instanceof Administrator) {
                ArrayList<Freelancer> tab = table.get("administrator");
                if (tab == null) {
                    tab = new ArrayList<>();
                }
                tab.add(freelancer);
                table.put("administrator", tab);
            } else {
                ArrayList<Freelancer> tab = table.get("specialist");
                if (tab == null) {
                    tab = new ArrayList<>();
                }
                tab.add(freelancer);
                table.put("specialist", tab);
            }
        }
    }
    
    /**
     * Metóda pre naplnenie tabuľky zamestnávateľov.
     */
    private void fillEmployers() {
        mainWindow.getTbEmployersRentModel().setRowCount(0);
        
        for (Employer employer : database.getEmployers()) {
            Object[] row = new Object[3];
            row[0] = employer.getName();
            row[1] = employer.getBusinessArea();
            row[2] = employer.getEmployeesCount();
            mainWindow.getTbEmployersRentModel().addRow(row);
        }
    }
    
    /**
     * Metóda pre naplnenie tabuľky špecialistov.
     */
    private void fillSpecialists() {
        mainWindow.getTbSpecialistsModel().setRowCount(0);
        String item = (String) mainWindow.getCbSpecialist().getSelectedItem();
        ArrayList<Freelancer> freelancers;
        switch (item) {
            case "Programátor":
                freelancers = table.get("programmer");
                if (freelancers == null) {
                    return;
                }
                for (Freelancer freelancer : freelancers) {
                    Object[] row = new Object[8];
                    row[0] = freelancer.getId();
                    row[1] = freelancer.getManDay();
                    row[2] = freelancer.getExperience();
                    row[3] = freelancer.getEducation();
                    row[4] = String.join(", ",((Programmer) freelancer).getTypes());
                    row[5] = String.join(", " ,freelancer.getCertificates());
                    row[6] = "-";
                    row[7] = "-";
                    mainWindow.getTbSpecialistsModel().addRow(row);
                }
                break;
            case "Administrátor":
                freelancers = table.get("administrator");
                if (freelancers == null) {
                    return;
                }
                for (Freelancer freelancer : freelancers) {
                    Object[] row = new Object[8];
                    row[0] = freelancer.getId();
                    row[1] = freelancer.getManDay();
                    row[2] = freelancer.getExperience();
                    row[3] = freelancer.getEducation();
                    row[4] = String.join(", ",((Administrator) freelancer).getTypes());
                    row[5] = String.join(", ", freelancer.getCertificates());
                    row[6] = "-";
                    row[7] = ((Administrator) freelancer).getPreferredPlatform();
                    mainWindow.getTbSpecialistsModel().addRow(row);
                }
                break;
            default:
                freelancers = table.get("specialist");
                if (freelancers == null) {
                    return;
                }
                for (Freelancer freelancer : freelancers) {
                    Object[] row = new Object[8];
                    row[0] = freelancer.getId();
                    row[1] = freelancer.getManDay();
                    row[2] = freelancer.getExperience();
                    row[3] = freelancer.getEducation();
                    row[4] = "-";
                    row[5] = String.join(", ", freelancer.getCertificates());
                    if (((SecurityConsultant) freelancer).isAuditor()) {
                        row[6] = "Áno";
                    } else {
                        row[6] = "Nie";
                    }
                    row[7] = "-";
                    mainWindow.getTbSpecialistsModel().addRow(row);
                }
                break;
        }
    }
    
    /**
     * Metóda pre odobratie vybraného špecialistu zo zoznamu už vybraných špecialistov.
     */
    private void removeSpecialist() {
        int indexSpecialist = mainWindow.getTbChoosedSpecialistsTable().getSelectedRow();
        if (indexSpecialist == -1) {
            JOptionPane.showMessageDialog(mainWindow, "Nebol vybraný žiaden špecialista!");
            return;
        }
        String typeCategory = (String) mainWindow.getTbChoosedSpecialistsModel().getValueAt(indexSpecialist, 1);
        int id = (int) mainWindow.getTbChoosedSpecialistsModel().getValueAt(indexSpecialist, 0);
         ArrayList<Freelancer> freelancers;
        switch(typeCategory) {
            case "Programátor": 
                freelancers = table.get("programmer");
                freelancers.add(database.getFreelancer(id));
                table.replace("programmer", freelancers);
                
                break;
            case "Administrátor": 
                freelancers = table.get("administrator");
                freelancers.add(database.getFreelancer(id));
                table.replace("administrator", freelancers);
                break;
            default: 
                freelancers = table.get("specialist");
                freelancers.add(database.getFreelancer(id));
                table.replace("specialist", freelancers);
                break;
        }
        mainWindow.getTbChoosedSpecialistsModel().removeRow(indexSpecialist);
        fillSpecialists();
    }
}
