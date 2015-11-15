package fr.ippon.osgi.sample.services;

import fr.ippon.osgi.sample.model.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sfoubert
 */
public class EmployeeServiceImpl implements EmployeeService {

    @PersistenceContext(unitName = "ippon-pu")
    private EntityManager entityManager;

    @Override
    public List<Employee> getAllEmployees() {
        return entityManager.createQuery("SELECT e FROM Employee e").getResultList();
    }

    @Override
    public Employee findEmployee(String employeeId) {
        return entityManager.find(Employee.class, employeeId);
    }

    @Override
    public void addEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public void removeEmployee(String employeeId) {
        Employee employee = findEmployee(employeeId);
        entityManager.remove(employee);
    }

}
