package com.example.project3.Controller;

import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
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



    @PostMapping("/register-customer")
    public ResponseEntity registerCustomer(@RequestBody @Valid CustomerDTO customerDTO){
        customerService.registerCustomer(customerDTO);
        return ResponseEntity.status(200).body("Customer register");
    }


    @PutMapping("/update/{customerId}")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user,@PathVariable Integer customerId,@RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(user, customerId, customerDTO);
        return ResponseEntity.status(200).body("Customer updated successfully");
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user,@PathVariable Integer customerId) {
        customerService.deleteCustomer(user, customerId);
        return ResponseEntity.status(200).body("Customer deleted successfully");
    }

}
