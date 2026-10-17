package com.diplomatura.fintech.service;

import com.diplomatura.fintech.entity.User;
import com.diplomatura.fintech.enums.UserType;
import com.diplomatura.fintech.exception.UserNotFoundException;
import com.diplomatura.fintech.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllByType(UserType type) {
        return switch (type) {
            case INDIVIDUAL -> userRepository.findAllIndividualUsers();
            case LEGAL_ENTITY -> userRepository.findAllLegalEntityUsers();
        };
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User create(User user) {
        return userRepository.save(user);
    }
}
