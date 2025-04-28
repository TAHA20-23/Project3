package com.example.project3.Controller;

import com.example.project3.Model.Account;
import com.example.project3.Model.User;
import com.example.project3.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity createAccount(@AuthenticationPrincipal User user,
                                        @RequestBody Account account) {
        accountService.createAccount(user.getId(), account);
        return ResponseEntity.status(200).body("Account created successfully");
    }

    @GetMapping("/my-accounts")
    public ResponseEntity getMyAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.getCustomerAccounts(user.getId()));
    }

    @GetMapping("/details/{accountId}")
    public ResponseEntity getAccountDetails(@AuthenticationPrincipal User user,
                                            @PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(accountService.getAccountDetails(user.getId(), accountId));
    }

    @PutMapping("/activate/{accountId}")
    public ResponseEntity activateAccount(@AuthenticationPrincipal User user,@PathVariable Integer accountId) {
        accountService.activateAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body("Account activated successfully");
    }

    @PutMapping("/block/{accountId}")
    public ResponseEntity blockAccount(@AuthenticationPrincipal User user,@PathVariable Integer accountId) {
        accountService.blockAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body("Account blocked successfully");
    }

    @PutMapping("/deposit/{accountId}")
    public ResponseEntity deposit(@AuthenticationPrincipal User user,@PathVariable Integer accountId,@RequestParam Double amount) {
        accountService.deposit(user.getId(), accountId, amount);
        return ResponseEntity.status(200).body("Deposit successful");
    }

    @PutMapping("/withdraw/{accountId}")
    public ResponseEntity withdraw(@AuthenticationPrincipal User user,@PathVariable Integer accountId,
                                   @RequestParam Double amount) {
        accountService.withdraw(user.getId(), accountId, amount);
        return ResponseEntity.status(200).body("Withdrawal successful");
    }

    @PutMapping("/transfer")
    public ResponseEntity transfer(@AuthenticationPrincipal User user,@RequestParam Integer fromAccountId,
                                   @RequestParam Integer toAccountId,@RequestParam Double amount) {
        accountService.transfer(user.getId(), fromAccountId, toAccountId, amount);
        return ResponseEntity.status(200).body("Transfer completed successfully");
    }
}
