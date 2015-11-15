package fr.ippon.osgi.sample.command;

import fr.ippon.osgi.sample.model.Employee;
import fr.ippon.osgi.sample.services.EmployeeService;
import java.util.List;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.karaf.shell.support.table.Col;
import org.apache.karaf.shell.support.table.ShellTable;

@Command(scope = "ippon", name = "list-employees", description = "Liste les employees de la societe")
@Service
public class ListEmployees implements Action {

    @Option(name = "-o", aliases = {"--option"}, description = "An option to the command", required = false,
            multiValued = false)
    private String option;

    @Argument(name = "argument", description = "Argument to the command", required = false, multiValued = false)
    private String argument;

    @Reference
    private EmployeeService employeeService;

    @Override
    public Object execute() throws Exception {
        System.out.println("Liste des employes :");

        List<Employee> employees = employeeService.getAllEmployees();

        ShellTable table = new ShellTable();
        table.column(new Col("Id"));
        table.column(new Col("Nom"));
        table.column(new Col("Prenom"));
        table.column(new Col("Date de naissance"));
        for (Employee employee : employees) {
            table.addRow().addContent(employee.getEmployeeId(), employee.getLastname(), employee.getFirstname(),
                    employee.getBirthDate());
        }
        table.print(System.out, true);

        return null;
    }

}
