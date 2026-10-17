package com.diplomatura.fintech.service;

import com.diplomatura.fintech.entity.Account;
import com.diplomatura.fintech.entity.IndividualUser;
import com.diplomatura.fintech.entity.User;
import com.diplomatura.fintech.enums.AccountStatus;
import com.diplomatura.fintech.exception.AccountNotFoundException;
import com.diplomatura.fintech.exception.UserNotFoundException;
import com.diplomatura.fintech.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountService accountService;

    @Test
    void findAllReturnsRepositoryAccounts() {
        // Given
        User user = new IndividualUser(
                "Ana",
                "Pérez",
                "ana@mail.com",
                "12345678"
        );

        Account account = new Account(
                "1234567890123456789012",
                "ana.fintech",
                user
        );

        when(accountRepository.findAll())
                .thenReturn(List.of(account));

        // When
        List<Account> result = accountService.findAll();

        // Then
        assertEquals(1, result.size());
        assertSame(account, result.get(0));

        verify(accountRepository).findAll();
    }

    @Test
    void findByIdReturnsExistingAccount() {
        // Given
        User user = new IndividualUser(
                "Ana",
                "Pérez",
                "ana@mail.com",
                "12345678"
        );

        Account account = new Account(
                "1234567890123456789012",
                "ana.fintech",
                user
        );

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        // When
        Account result = accountService.findById(1L);

        // Then
        assertSame(account, result);

        verify(accountRepository).findById(1L);
    }

    @Test
    void findByIdThrowsWhenAccountDoesNotExist() {
        // Given
        when(accountRepository.findById(999L))
                .thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                AccountNotFoundException.class,
                () -> accountService.findById(999L)
        );

        verify(accountRepository).findById(999L);
    }

    @Test
    void createBuildsAndSavesAccount() {
        // Given
        User user = new IndividualUser(
                "Ana",
                "Pérez",
                "ana@mail.com",
                "12345678"
        );

        when(userService.findById(1L))
                .thenReturn(user);

        when(accountRepository.save(any(Account.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Account result = accountService.create(
                "1234567890123456789012",
                "ana.fintech",
                1L
        );

        // Then
        assertEquals("1234567890123456789012", result.getCbu());
        assertEquals("ana.fintech", result.getAlias());
        assertEquals(BigDecimal.ZERO, result.getBalance());
        assertEquals(AccountStatus.ACTIVE, result.getStatus());
        assertSame(user, result.getUser());

        verify(userService).findById(1L);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void createDoesNotSaveWhenUserDoesNotExist() {
        // Given
        when(userService.findById(999L))
                .thenThrow(new UserNotFoundException(999L));

        // When / Then
        assertThrows(
                UserNotFoundException.class,
                () -> accountService.create(
                        "1234567890123456789012",
                        "usuario.inexistente",
                        999L
                )
        );

        verify(userService).findById(999L);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void softDeleteByIdChangesStatusToDeactivated() {
        // Given
        User user = new IndividualUser(
                "Ana",
                "Pérez",
                "ana@mail.com",
                "12345678"
        );

        Account account = new Account(
                "1234567890123456789012",
                "ana.fintech",
                user
        );

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        // When
        accountService.softDeleteById(1L);

        // Then
        assertEquals(AccountStatus.DEACTIVATED, account.getStatus());
        verify(accountRepository).findById(1L);
        verify(accountRepository).save(account);
    }

    @Test
    void deleteByIdDeletesExistingAccount() {
        // Given
        User user = new IndividualUser(
                "Ana",
                "Pérez",
                "ana@mail.com",
                "12345678"
        );

        Account account = new Account(
                "1234567890123456789012",
                "ana.fintech",
                user
        );

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        // When
        accountService.deleteById(1L);

        // Then
        verify(accountRepository).findById(1L);
        verify(accountRepository).delete(account);
    }

    @Test
    void deleteByIdDoesNotDeleteWhenAccountDoesNotExist() {
        // Given
        when(accountRepository.findById(999L))
                .thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                AccountNotFoundException.class,
                () -> accountService.deleteById(999L)
        );

        verify(accountRepository).findById(999L);
        verify(accountRepository, never()).delete(any(Account.class));
    }
}
