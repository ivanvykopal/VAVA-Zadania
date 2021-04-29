/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ivan Vykopal
 */
public class Database implements Serializable {
    private ArrayList<Accommodation> accommodations;
    private ArrayList<Category> categories;
    private ArrayList<Customer> customers;
    private ArrayList<Payment> payments;
    private ArrayList<Reservation> reservations;
    private ArrayList<Room> rooms;
    private ArrayList<Service> services;

    private Database() {
        this.accommodations = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.services = new ArrayList<>();
    }
    
    public static Database createDatabase() {
        return new Database();
    }

    public ArrayList<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(ArrayList<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
    
    public boolean checkCustomer(Customer customer) {
        for (Customer cus : customers) {
            if (cus.equals(customer)) {
                return true;
            }
        }
        return false;
    }
    
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }
    
    public boolean checkCategory(Category category) {
        for (Category cat : categories) {
            if (cat.equals(category)) {
                return true;
            }
        }
        return false;
    }
    
    public void addCategory(Category category) {
        this.categories.add(category);
    }
    
    public Category findCategory(String description, double price) {
        for(Category category: categories) {
            if (category.getDescription().equals(description) && category.getPrice() == price) {
                return category;
            }
        }
        return null;
    }
    
    public boolean checkRoom(Room room) {
        for (Room r : rooms) {
            if (r.getLabel().equals(room.getLabel())) {
                return true;
            }
        }
        return false;
    }
    
    public void addRoom(Room room) {
        this.rooms.add(room);
    }
    
    public boolean checkService(Service service) {
        for (Service ser : services) {
            if (ser.equals(service)) {
                return true;
            }
        }
        return false;
    }
    
    public Service findService(Service service) {
        for (Service ser : services) {
            if (ser.equals(service)) {
                return ser;
            }
        }
        return null;
    }
    
    public void addService(Service service) {
        this.services.add(service);
    }
    
    public Room getRoom(String label) {
        for (Room room : rooms) {
            if (room.getLabel().equals(label)) {
                return room;
            }
        }
        return null;
    }
    
    public void setRoom(String label, Room room) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getLabel().equals(label)) {
                rooms.set(i, room);
                break;
            }
        }
    }
    
    public Customer getCustomer(String name, String phone) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name) && customer.getPhone().equals(phone)) {
                return customer;
            }
        }
        return null;
    }
    
    public boolean checkReservation(Reservation reservation) {
        for (Reservation res : reservations) {
           if (res.intersect(reservation.getFrom(), reservation.getTo(), reservation.getRoom().getLabel())) {
               return true;
           }
        }
        for (Accommodation acc : accommodations) {
            if (acc.intersect(reservation.getFrom(), reservation.getTo(), reservation.getRoom().getLabel())) {
                return true;
            }
        }
        return false;
    }
    
    public Reservation findReservation(Reservation reservation) {
        for (Reservation res : reservations) {
           if (res.equals(reservation)) {
               return res;
           }
        }
        return null;
    }
    
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
    
    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }
    
    public boolean addServiceUsage(String room, ServiceUsed service, LocalTime time) {
        for (Accommodation acc: accommodations) {
            if (acc.getRoom().getLabel().equals(room) && isCurrentAccomodation(acc.getFrom(), acc.getTo(), service.getDate(), time)) {
                acc.addService(service);
                return true;
            }
        }
        return false;
    }
    
    private boolean isCurrentAccomodation(Date from, Date to, Date now, LocalTime time) {
        if (now.compareTo(from) == 0 && time.getHour() >= 14) {
            return true;
        }
        if (now.compareTo(to) == 0 && time.getHour() < 10) {
            return true;
        }
        return now.after(from) && now.before(to);
    }
    
    public void addAccommodation(Accommodation acc) {
        this.accommodations.add(acc);
    }
    
    public Accommodation findAccommodation(Accommodation accommodation) {
        for (Accommodation acc : accommodations) {
            if (acc.equals(accommodation)) {
                return acc;
            }
        }
        return null;
    }
    
    public void setAccommodation(Accommodation accommodation) {
        for (int i = 0; i < accommodations.size(); i++) {
            if (accommodations.get(i).equals(accommodation)) {
                accommodations.set(i, accommodation);
                break;
            }
        }
    }
    
    public ArrayList<Room> findFreeRooms(Date start, Date end) {
        Set<Room> roomSet = new HashSet<Room>();
        for (Accommodation acc : accommodations) {
            if (acc.intersect(start, end)) {
                roomSet.add(acc.getRoom());
            }
        }
        for (Reservation res : reservations) {
           if (res.intersect(start, end)) {
               roomSet.add(res.getRoom());
           }
        }
        ArrayList<Room> freeRooms = new ArrayList<>(rooms);
        freeRooms.removeAll(roomSet);
        return freeRooms;   
    }
    
    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }
    
    public Payment findAccPayment(Accommodation acc) {
        for (Payment pay : payments) {
            if (pay.getAccomodation().equals(acc)) {
                return pay; 
            }
        }
        return null;
    }
    
    public int getAccEnd(Date date) {
        int count = 0;
        for (Accommodation acc : accommodations) {
            if (acc.getTo().equals(date)) {
                count++;
            }
        }
        return count;
    }
    
    public int getAccStart(Date date) {
        int count = 0;
        for (Reservation res : reservations) {
            if (res.getFrom().equals(date)) {
                count++;
            }
        }
        return count;
    }
}