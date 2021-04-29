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
public class Rent implements Serializable {
    private int id;
    private Employer employer;
    private ArrayList<Freelancer> freelancers;
    private Job job;

    public Rent(Employer employer, ArrayList<Freelancer> freelancers, Job job) {
        this.employer = employer;
        this.freelancers = freelancers;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public ArrayList<Freelancer> getFreelancers() {
        return freelancers;
    }

    public void setFreelancers(ArrayList<Freelancer> freelancers) {
        this.freelancers = freelancers;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    
}
