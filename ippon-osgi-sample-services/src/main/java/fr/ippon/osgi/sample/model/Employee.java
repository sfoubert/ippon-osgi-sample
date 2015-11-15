package fr.ippon.osgi.sample.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author sfoubert
 */
@Entity
public class Employee implements Serializable {

    @Id
    private String employeeId;

    private String firstname;

    private String lastname;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    public Employee() {
    }

    public Employee(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Employee(String firstname, String lastname, Date birthDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
