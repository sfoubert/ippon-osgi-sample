package fr.ippon.osgi.sample.command;

import fr.ippon.osgi.sample.command.completer.JobCompleter;
import fr.ippon.osgi.sample.model.Employee;
import fr.ippon.osgi.sample.model.Job;
import fr.ippon.osgi.sample.services.EmployeeCriteria;
import fr.ippon.osgi.sample.services.EmployeeService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Completion;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.karaf.shell.support.table.Col;
import org.apache.karaf.shell.support.table.ShellTable;

@Command(scope = "ippon", name = "list-employees", description = "Liste les employees de la societe")
@Service
public class ListEmployees implements Action {

    @Option(name = "-j", aliases = {"--job"}, description = "Liste de jobs", required = false,
            multiValued = false)
    @Completion(JobCompleter.class)
    private String jobsParam;

    @Option(name = "-n", aliases = {"--lastname"}, description = "Nom du salarie", required = false,
            multiValued = false)
    private String lastnameParam;

    @Option(name = "-f", aliases = {"--firstname"}, description = "Prenom du salarie", required = false,
            multiValued = false)
    private String firstnameParam;

    @Reference
    private EmployeeService employeeService;

    @Override
    public Object execute() throws Exception {
        System.out.println("Liste des employes :");

        EmployeeCriteria criteria = new EmployeeCriteria();

        if (StringUtils.isNotBlank(jobsParam)) {

            Set<Job> jobSet = new HashSet<Job>();
            List<String> jobList = Arrays.asList(jobsParam.split(","));
            for (String strJob : jobList) {
                Job job = Job.fromValue(strJob);
                if (job != null) {
                    jobSet.add(job);
                } else {
//                    logger.warn("Job " + strJob + " not found");
                }
            }
            criteria.setJobs(jobSet);
        }

        if (StringUtils.isNotBlank(lastnameParam)) {
            criteria.setLastnameLike(lastnameParam);
        }

        if (StringUtils.isNotBlank(firstnameParam)) {
            criteria.setFirstnameLike(firstnameParam);
        }

        List<Employee> employees = employeeService.getAllEmployees(criteria);

        if (employees != null) {
            ShellTable table = new ShellTable();
            table.column(new Col("Id"));
            table.column(new Col("Nom"));
            table.column(new Col("Prenom"));
            table.column(new Col("Date de naissance"));
            table.column(new Col("Fonction"));
            for (Employee employee : employees) {
                table.addRow().addContent(employee.getEmployeeId(), employee.getLastname(), employee.getFirstname(),
                        employee.getBirthDate(), employee.getJob().name());
            }
            table.print(System.out, true);
        } else {
            System.out.println("Aucun salarie trouve");
        }

        return null;
    }

}
