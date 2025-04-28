package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;

    public void addCustomer(Integer userId, CustomerDTO customerDTO) {
        User user = authRepository.findUsersById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("CUSTOMER")) {
            throw new ApiException("User is not a CUSTOMER");
        }

        Customer customer = new Customer();
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setUser(user);

        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getMyCustomers(Integer userId) {
        User user = authRepository.findUfserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        return customerRepository.findAllByUser(user);
    }

    public void updateCustomer(Integer userId, Integer customerId, CustomerDTO customerDTO) {
        User user = authRepository.findUsersById(userId);
        Customer customer = customerRepository.findCustomerById(customerId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        if (!customer.getUser().getId().equals(userId)) {
            throw new ApiException("Customer does not belong to the user");
        }

        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customerRepository.save(customer);
    }

    public void deleteCustomer(Integer userId, Integer customerId) {
        User user = authRepository.findUsersById(userId);
        Customer customer = customerRepository.findCustomerById(customerId);

        if (user == null) {
            throw new ApiException("User not found");
        }

        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        if (!customer.getUser().getId().equals(userId)) {
            throw new ApiException("Customer does not belong to the user");
        }

        customerRepository.delete(customer);
    }
}
