package org.example.clientprocessing.service;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.model.User;
import org.example.clientprocessing.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalStateException("Пользователь с таким логином уже существует");
        }

        return userRepository.save(user);
    }
}
