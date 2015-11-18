/*
 * © 1996-2014 Sopra HR Software. All rights reserved
 */
package fr.ippon.osgi.sample.model;

/**
 *
 * @author sfoubert
 */
public enum Job {

    DEV, TECHLEAD, ARCHITECT, MANAGER, HR, CEO;

    public static Job fromValue(String value) {
        for (Job job : Job.values()) {
            if (job.name().equals(value)) {
                return job;
            }
        }
        return null;
    }
}
