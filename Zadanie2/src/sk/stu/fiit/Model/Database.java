/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ivan Vykopal
 */
public class Database implements Serializable {
    private int freelancerIdGenerator = 1;
    private int rentIdGenerator = 1;
    private ArrayList<Freelancer> freelancers;
    private ArrayList<Employer> employers;
    private final ArrayList<Rent> rents = new ArrayList<>();
    private ArrayList<Job> jobs;

    public Database(ArrayList<Freelancer> freelancers, ArrayList<Employer> employers, ArrayList<Job> jobs) {
        this.freelancers = freelancers;
        this.employers = employers;
        this.jobs = jobs;
    }

    public ArrayList<Freelancer> getFreelancers() {
        return new ArrayList<>(freelancers);
    }

    public void setFreelancers(ArrayList<Freelancer> freelancers) {
        this.freelancers = freelancers;
    }

    public ArrayList<Employer> getEmployers() {
        return new ArrayList<>(employers);
    }

    public void setEmployers(ArrayList<Employer> employers) {
        this.employers = employers;
    }
    
    public void addFreelancer(Freelancer freelancer) {
        freelancer.setId(freelancerIdGenerator);
        freelancerIdGenerator++;
        this.freelancers.add(freelancer);
    }
    
    public Employer addEmployer(Employer employer) {
        boolean exist = false;
        for (Employer emp : this.employers) {
            if (emp.getName().equals(employer.getName())) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            this.employers.add(employer);
            return employer;
        } else {
            return null;
        }
    }
    
    public Employer getEmployer(String name) {
        for (Employer emp : employers) {
            if (emp.getName().equals(name)) {
                return emp;
            }
        }
        return null;
    }
    
    public void setFreelancer(int id, boolean rented) {
        for (int i = 0; i < freelancers.size(); i++) {
            if (freelancers.get(i).getId() == id) {
                Freelancer freelancer = freelancers.get(i);
                freelancer.setRented(rented);
                freelancers.set(i, freelancer);
                break;
            }
        }
    }
    
    public Freelancer getFreelancer(int id) {
        for (Freelancer freelancer: freelancers) {
            if (freelancer.getId() == id) {
                return freelancer;
            }
        }
        return null;
    }

    public ArrayList<Rent> getRents() {
        return new ArrayList<>(rents);
    }

    public void addRent(Rent rent) {
        rent.setId(rentIdGenerator);
        rentIdGenerator++;
        this.rents.add(rent);
    }
    
    public Rent findRent(int id) {
        for (Rent rent : rents) {
            if (rent.getId() == id) {
                return rent;
            }
        }
        return null;
    }
    
    public void removeRent(int id) {
        for (Rent rent : rents) {
            if (rent.getId() == id) {
                for (Freelancer freelancer: rent.getFreelancers()) {
                    setFreelancer(freelancer.getId(), false);
                }
                rents.remove(rent);
                break;
            }
        }
    }

    public ArrayList<Job> getJobs() {
        return new ArrayList<>(jobs);
    }
    
    public void addJob(Job job) {
        this.jobs.add(job);
    }
    
    public Job getJob(int index) {
        return jobs.get(index);
    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }
    
    public void removeJob(Job job) {
        this.jobs.remove(job);
    }
    
}
