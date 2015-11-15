package fr.ippon.osgi.sample.services;

import fr.ippon.osgi.sample.model.Employee;
import java.util.List;

/**
 *
 * @author sfoubert
 */
public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee findEmployee(String employeeId);

    void addEmployee(Employee employee);

    void removeEmployee(String employeeId);

}