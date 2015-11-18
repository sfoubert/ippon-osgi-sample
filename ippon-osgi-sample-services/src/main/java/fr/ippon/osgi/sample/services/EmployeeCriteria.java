/*
 * © 1996-2014 Sopra HR Software. All rights reserved
 */
package fr.ippon.osgi.sample.services;

import fr.ippon.osgi.sample.model.Job;
import java.util.Set;

/**
 *
 * @author sfoubert
 */
public class EmployeeCriteria {

    private String firstnameLike;

    private String lastnameLike;

    private Set<Job> jobs;

    public String getFirstnameLike() {
        return firstnameLike;
    }

    public void setFirstnameLike(String firstnameLike) {
        this.firstnameLike = firstnameLike;
    }

    public String getLastnameLike() {
        return lastnameLike;
    }

    public void setLastnameLike(String lastnameLike) {
        this.lastnameLike = lastnameLike;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

}
