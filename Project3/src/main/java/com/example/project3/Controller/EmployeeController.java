package com.example.project3.Controller;

import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get-all-employees")
    public ResponseEntity getAllEmployees() {
        return ResponseEntity.status(200).body(employeeService.getAllEmployees());
    }

    @GetMapping("/get-my-employees")
    public ResponseEntity getMyEmployees(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(employeeService.getMyEmployees(user.getId()));
    }

    @PostMapping("/add-employee")
    public ResponseEntity addEmployee(@AuthenticationPrincipal User user,@RequestBody EmployeeDTO employeeDTO) {
        employeeService.addEmployee(user.getId(), employeeDTO);
        return ResponseEntity.status(200).body("Employee added successfully");
    }

    @PutMapping("/update-employee/{employeeId}")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal User user, @PathVariable Integer employeeId, @RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(user.getId(), employeeId, employeeDTO);
        return ResponseEntity.status(200).body("Employee updated successfully");
    }

    @DeleteMapping("/delete-employee/{employeeId}")
    public ResponseEntity deleteEmployee(@AuthenticationPrincipal User user,
                                         @PathVariable Integer employeeId) {
        employeeService.deleteEmployee(user.getId(), employeeId);
        return ResponseEntity.status(200).body("Employee deleted successfully");
    }
}
