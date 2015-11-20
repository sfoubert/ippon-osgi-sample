package fr.ippon.osgi.sample.services;

import fr.ippon.osgi.sample.model.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;

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
    public List<Employee> getAllEmployees(EmployeeCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Employee.class);
        Root<Employee> employee = cq.from(Employee.class);
        cq.select(employee);

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (criteria.getJobs() != null && !criteria.getJobs().isEmpty()) {
            predicates.add(employee.get("job").in(criteria.getJobs()));
        }

        if (StringUtils.isNotBlank(criteria.getLastnameLike())) {
            predicates.add(cb.like(cb.upper(employee.<String>get("lastname")), cb.upper(cb.literal("%" + criteria.
                    getLastnameLike() + "%"))));
        }

        if (StringUtils.isNotBlank(criteria.getFirstnameLike())) {
            predicates.add(cb.like(cb.upper(employee.<String>get("firstname")), cb.upper(cb.literal("%" + criteria.
                    getFirstnameLike() + "%"))));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        TypedQuery<Employee> query = entityManager.createQuery(cq);
        return query.getResultList();
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
