package com.diplomatura.fintech.service;

import com.diplomatura.fintech.entity.Account;
import com.diplomatura.fintech.entity.User;
import com.diplomatura.fintech.enums.AccountStatus;
import com.diplomatura.fintech.exception.AccountNotFoundException;
import com.diplomatura.fintech.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    public AccountService(AccountRepository accountRepository,UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Account create(String cbu, String alias, Long userId) {
        User user = userService.findById(userId);

        Account account = new Account(cbu, alias, user);

        return accountRepository.save(account);
    }

    public void softDeleteById(Long id) {
        Account account = findById(id);
        account.setStatus(AccountStatus.DEACTIVATED);
        accountRepository.save(account);
    }

    public void deleteById(Long id) {
        Account account = findById(id);
        accountRepository.delete(account);
    }
}
