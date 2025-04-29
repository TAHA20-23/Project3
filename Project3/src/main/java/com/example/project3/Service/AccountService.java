package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.CustomerRepository;
import com.example.project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public void createAccount(Integer userId, Account account) {
        User user = authRepository.findUfserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null) {
            throw new ApiException("Customer profile not found");
        }

        account.setCustomer(customer);
        account.setIsActive(false);
        accountRepository.save(account);
    }

    public Account getAccountDetails(Integer userId, Integer accountId) {
        Account account = validateAccountOwnership(userId, accountId);
        return account;
    }

    public List<Account> getCustomerAccounts(Integer userId) {
        User user = authRepository.findUsersById(userId);
        if (user == null) throw new ApiException("User not found");

        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null) throw new ApiException("Customer not found");

        return accountRepository.findAllByCustomer(customer);
    }

    public void deposit(Integer userId, Integer accountId, Double amount) {
        Account account = validateAccountOwnership(userId, accountId);

        if (!account.getIsActive()) {
            throw new ApiException("Account is not active");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void withdraw(Integer userId, Integer accountId, Double amount) {
        Account account = validateAccountOwnership(userId, accountId);

        if (!account.getIsActive()) {
            throw new ApiException("Account is not active");
        }

        if (account.getBalance() < amount) {
            throw new ApiException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void transfer(Integer userId, Integer fromAccountId, Integer toAccountId, Double amount) {
        Account fromAccount = validateAccountOwnership(userId, fromAccountId);
        Account toAccount = accountRepository.findAccountById(toAccountId);

        if (toAccount == null) {
            throw new ApiException("Destination account not found");
        }

        if (!fromAccount.getIsActive() || !toAccount.getIsActive()) {
            throw new ApiException("One of the accounts is not active");
        }

        if (fromAccount.getBalance() < amount) {
            throw new ApiException("Insufficient balance to transfer");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public void activateAccount(Integer userId, Integer accountId) {
        Account account = validateAccountOwnership(userId, accountId);
        account.setIsActive(true);
        accountRepository.save(account);
    }

    public void blockAccount(Integer userId, Integer accountId) {
        Account account = validateAccountOwnership(userId, accountId);
        account.setIsActive(false);
        accountRepository.save(account);
    }

    private Account validateAccountOwnership(Integer userId, Integer accountId) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account not found");
        }

        User user = authRepository.findUsersById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null || !account.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("This account does not belong to the user");
        }

        return account;
    }
}
