package com.example.project3.Controller;

import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get-all-customers")
    public ResponseEntity getAllCustomers() {
        return ResponseEntity.status(200).body(customerService.getAllCustomers());
    }

    @GetMapping("/get-my-customers")
    public ResponseEntity getMyCustomers(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(customerService.getMyCustomers(user.getId()));
    }

    @PostMapping("/add-customer")
    public ResponseEntity addCustomer(@AuthenticationPrincipal User user,
                                      @RequestBody CustomerDTO customerDTO) {
        customerService.addCustomer(user.getId(), customerDTO);
        return ResponseEntity.status(200).body("Customer added successfully");
    }

    @PutMapping("/update-customer/{customerId}")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user,
                                         @PathVariable Integer customerId,
                                         @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(user.getId(), customerId, customerDTO);
        return ResponseEntity.status(200).body("Customer updated successfully");
    }

    @DeleteMapping("/delete-customer/{customerId}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user,
                                         @PathVariable Integer customerId) {
        customerService.deleteCustomer(user.getId(), customerId);
        return ResponseEntity.status(200).body("Customer deleted successfully");
    }
}
