package com.ohmydog.user.service;

import com.ohmydog.user.entity.User;
import com.ohmydog.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        user.setId(null);
        Optional<User> userAlreadyExist = this.userRepository.findByEmail(user.getEmail());
        if (userAlreadyExist.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("É necessário informar o código");
        }
        findById(user.getId());
        return userRepository.saveAndFlush(user);
    }

    public void deleteUser(UUID uuid) {
        User user = findById(uuid);
        userRepository.delete(user);
    }
}
