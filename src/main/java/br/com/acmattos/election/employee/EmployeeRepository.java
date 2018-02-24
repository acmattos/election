package br.com.acmattos.election.employee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Employee's repository.
 *
 * @author acmattos
 * @since 18/01/2018
 * TODO JAVADOC, CHECK, UNIT TEST
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
}
