package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Employee> getMyEmployees(Integer userId) {
        User user = authRepository.findUsersById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        return employeeRepository.findAllByUser(user);
    }

    public void addEmployee(Integer userId, EmployeeDTO employeeDTO) {
        User user = authRepository.findUfserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("EMPLOYEE")) {
            throw new ApiException("User is not an EMPLOYEE");
        }

        Employee employee = new Employee();
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employee.setUser(user);

        employeeRepository.save(employee);
    }

    public void updateEmployee(Integer userId, Integer employeeId, EmployeeDTO employeeDTO) {
        User user = authRepository.findUsersById(userId);
        Employee employee = employeeRepository.findEmployeesById(employeeId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        if (employee == null) {
            throw new ApiException("Employee not found");
        }

        if (!employee.getUser().getId().equals(userId)) {
            throw new ApiException("This employee does not belong to the user");
        }

        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());

        employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer userId, Integer employeeId) {
        User user = authRepository.findUsersById(userId);
        Employee employee = employeeRepository.findEmployeesById(employeeId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        if (employee == null) {
            throw new ApiException("Employee not found");
        }

        if (!employee.getUser().getId().equals(userId)) {
            throw new ApiException("This employee does not belong to the user");
        }

        employeeRepository.delete(employee);
    }
}
