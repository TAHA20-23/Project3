package com.example.project3.Repository;


import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findEmployeesById(Integer id);

    List<Employee> findAllByUser(User user);
    
}
