/*
 * © 1996-2014 Sopra HR Software. All rights reserved
 */

package fr.ippon.osgi.sample.command;

import fr.ippon.osgi.sample.services.EmployeeService;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;

/**
 *
 * @author sfoubert
 */
@Command(scope = "ippon", name = "remove-employee", description = "Supprime un salarie de la societe")
@Service
public class RemoveEmployee implements Action {

    @Argument(index = 0, name = "Id", description = "Identifiant du salarie", required = true, multiValued = false)
    private String employeeId;

    @Reference
    private EmployeeService employeeService;

    @Override
    public Object execute() throws Exception {
        System.out.println("Suppression du salarie : " + employeeId);

        employeeService.removeEmployee(employeeId);

        return null;
    }
}
