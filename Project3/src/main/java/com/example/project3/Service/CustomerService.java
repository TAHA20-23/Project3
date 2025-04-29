package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;

    public void registerCustomer(CustomerDTO customerDTO) {

        customerDTO.setRole("CUSTOMER");
        String hashPassword= new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        User user= new User(null,customerDTO.getUsername(),hashPassword,customerDTO.getName(),customerDTO.getEmail(),customerDTO.getRole(),null,null);
        Customer customer= new Customer(null,customerDTO.getPhoneNumber(),user,null);
        authRepository.save(user);
        customerRepository.save(customer);

    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void updateCustomer(User user, Integer customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        if (!customer.getUser().getId().equals(user.getId())) {
            throw new ApiException("You are not allowed to update another user's customer");
        }

        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customerRepository.save(customer);
    }

    public void deleteCustomer(User user, Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        if (!customer.getUser().getId().equals(user.getId())) {
            throw new ApiException("You are not allowed to delete another user's customer");
        }

        customerRepository.delete(customer);
    }

}
