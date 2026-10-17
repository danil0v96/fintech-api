package com.diplomatura.fintech.service;

import com.diplomatura.fintech.entity.IndividualUser;
import com.diplomatura.fintech.entity.LegalEntityUser;
import com.diplomatura.fintech.entity.User;
import com.diplomatura.fintech.enums.UserType;
import com.diplomatura.fintech.exception.UserNotFoundException;
import com.diplomatura.fintech.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findAllReturnsRepositoryUsers() {
        // Given
        User user = new IndividualUser(
                "Ana",
                "Pérez",
                "ana@mail.com",
                "12345678"
        );

        when(userRepository.findAll())
                .thenReturn(List.of(user));

        // When
        List<User> result = userService.findAll();

        // Then
        assertEquals(1, result.size());
        assertSame(user, result.get(0));

        verify(userRepository).findAll();
    }

    @Test
    void findAllByTypeReturnsIndividualUsers() {
        // Given
        User user = new IndividualUser(
                "Ana",
                "Pérez",
                "ana@mail.com",
                "12345678"
        );

        when(userRepository.findAllIndividualUsers())
                .thenReturn(List.of(user));

        // When
        List<User> result = userService.findAllByType(UserType.INDIVIDUAL);

        // Then
        assertEquals(1, result.size());
        assertSame(user, result.get(0));
        verify(userRepository).findAllIndividualUsers();
    }

    @Test
    void findAllByTypeReturnsLegalEntityUsers() {
        // Given
        User user = new LegalEntityUser(
                "Fintech SA",
                "contacto@fintech.com",
                "30123456789"
        );

        when(userRepository.findAllLegalEntityUsers())
                .thenReturn(List.of(user));

        // When
        List<User> result = userService.findAllByType(UserType.LEGAL_ENTITY);

        // Then
        assertEquals(1, result.size());
        assertSame(user, result.get(0));
        verify(userRepository).findAllLegalEntityUsers();
    }

    @Test
    void findByIdReturnsExistingUser() {
        // Given
        User user = new IndividualUser(
                "Ana",
                "Pérez",
                "ana@mail.com",
                "12345678"
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        // When
        User result = userService.findById(1L);

        // Then
        assertSame(user, result);
        verify(userRepository).findById(1L);
    }

    @Test
    void findByIdThrowsWhenUserDoesNotExist() {
        // Given
        when(userRepository.findById(999L))
                .thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                UserNotFoundException.class,
                () -> userService.findById(999L)
        );

        verify(userRepository).findById(999L);
    }

    @Test
    void createSavesAndReturnsUser() {
        // Given
        User user = new IndividualUser(
                "Bruno",
                "Díaz",
                "bruno@mail.com",
                "23456789"
        );

        when(userRepository.save(user))
                .thenReturn(user);

        // When
        User result = userService.create(user);

        // Then
        assertSame(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void createSavesAndReturnsLegalEntityUser() {
        // Given
        User user = new LegalEntityUser(
                "Fintech SA",
                "contacto@fintech.com",
                "30123456789"
        );

        when(userRepository.save(user))
                .thenReturn(user);

        // When
        User result = userService.create(user);

        // Then
        assertSame(user, result);
        assertInstanceOf(LegalEntityUser.class, result);
        verify(userRepository).save(user);
    }
}
