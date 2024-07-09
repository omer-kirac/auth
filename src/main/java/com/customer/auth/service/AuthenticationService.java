package com.customer.auth.service;

import com.customer.auth.model.User;
import com.customer.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public String registerUser(String username,String password){
        if (userRepository.getUserById(username) == null){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userRepository.createUser(user);
            return loginUser(username,password);
        }
        return null;
    }

    public String loginUser(String username,String password){
        User user = userRepository.getUserById(username);
        if (user.getUsername() != null && user.getPassword() != null && user.getPassword().equals(password)){
            String token = generateToken();
            tokenService.saveToken(token, username);
            return token;
        }
        return null;
    }

    private String generateToken(){
        return UUID.randomUUID().toString();
    }

    private boolean verifyToken(String token){
        return tokenService.getUsernameByToken(token) != null;
    }

}
