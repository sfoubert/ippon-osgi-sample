/*
 * © 1996-2014 Sopra HR Software. All rights reserved
 */
package fr.ippon.osgi.sample.command;

import fr.ippon.osgi.sample.model.Employee;
import fr.ippon.osgi.sample.model.Job;
import fr.ippon.osgi.sample.services.EmployeeService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;

/**
 *
 * @author sfoubert
 */
@Command(scope = "ippon", name = "add-employee", description = "Ajoute un employee a la societe")
@Service
public class AddEmployee implements Action {

    @Argument(index = 0, name = "job", description = "Job de l'employee (DEV, TECHLEAD, ARCHITECT, MANAGER, HR, CEO)", required = true, multiValued = false)
    private String job;

    @Argument(index = 1, name = "lastname", description = "Nom", required = true, multiValued = false)
    private String lastname;

    @Argument(index = 2, name = "firstname", description = "Nom", required = true, multiValued = false)
    private String firstname;

    @Argument(index = 3, name = "birthDate", description = "Date de naissance (au format YYYY-mm-dd)", required = true,
              multiValued = false)
    private String birthDate;

    @Reference
    private EmployeeService employeeService;

    @Override
    public Object execute() throws Exception {
        System.out.println("Ajoute le salarie : " + lastname + " " + firstname);

        Employee employee = new Employee();
        employee.setJob(Job.valueOf(job));
        employee.setLastname(lastname);
        employee.setFirstname(firstname);
        employee.setBirthDate(parseDate(birthDate));

        employeeService.addEmployee(employee);

        return null;
    }

    private Date parseDate(String birthDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-dd");
        return sdf.parse(birthDate);
    }

}
