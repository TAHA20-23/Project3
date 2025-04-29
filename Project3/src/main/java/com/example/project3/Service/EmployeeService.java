package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }



    public void registerEmployee(EmployeeDTO employeeDTO) {

        employeeDTO.setRole("EMPLOYEE");
        String hashPassword= new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        User user= new User(null,employeeDTO.getUsername(),hashPassword,employeeDTO.getName(),employeeDTO.getEmail(),
                employeeDTO.getRole(),null,null);
        Employee employee=new Employee(null,employeeDTO.getPosition(),employeeDTO.getSalary(),user);

        authRepository.save(user);
        employeeRepository.save(employee);




    }

    public void updateEmployee(User user, Integer employeeId, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findEmployeesById(employeeId);

        if (employee == null) {
            throw new ApiException("Employee not found");
        }

        if (!employee.getUser().getId().equals(user.getId())) {
            throw new ApiException("You are not allowed to update another employee's data");
        }

        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());

        employeeRepository.save(employee);
    }


    public void deleteEmployee(User user, Integer employeeId) {
        Employee employee = employeeRepository.findEmployeesById(employeeId);


        if (employee == null) {
            throw new ApiException("Employee not found");
        }

        if (!employee.getUser().getId().equals(user.getId())) {
            throw new ApiException("You are not allowed to delete another employee's data");
        }

        employeeRepository.delete(employee);
    }
}
