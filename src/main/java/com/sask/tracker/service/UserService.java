package com.sask.tracker.service;

import com.sask.tracker.model.User;
import com.sask.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    //  Validate user credentials
    public User validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    //  Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //  Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    //  Save or update user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    //  Create Parent Account (refactored)
    public void createParentAccount(String email, String password, String secretQuestion, String secretAnswer) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists!");
        }
        
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("PARENT");
        user.setSecretQuestion(secretQuestion);
        user.setSecretAnswer(secretAnswer);

        userRepository.save(user);
    }

    //  Reset Password (improved)
    public boolean resetPassword(String email, String secretQuestion, String secretAnswer, String newPassword) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("No user found with this email!");
        }

        if (!user.getSecretQuestion().equals(secretQuestion)) {
            throw new RuntimeException("Secret question is incorrect!");
        }

        if (!user.getSecretAnswer().equals(secretAnswer)) {
            throw new RuntimeException("Secret answer is incorrect!");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
}
