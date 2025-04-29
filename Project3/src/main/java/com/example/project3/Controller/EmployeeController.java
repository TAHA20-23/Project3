package com.example.project3.Controller;

import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
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



    @PostMapping("/add-employee")
    public ResponseEntity registerEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.status(200).body("Employee registered successfully");
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal User user,@PathVariable Integer employeeId,@RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(user, employeeId, employeeDTO);
        return ResponseEntity.status(200).body("Employee updated successfully");
    }


    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity deleteEmployee(@AuthenticationPrincipal User user,@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(user, employeeId);
        return ResponseEntity.status(200).body("Employee deleted successfully");
    }
}
