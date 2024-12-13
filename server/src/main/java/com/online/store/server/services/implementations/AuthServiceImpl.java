package com.online.store.server.services.implementations;

import com.online.store.server.exceptions.AuthenticationException;
import com.online.store.server.exceptions.DuplicateElementException;
import com.online.store.server.exceptions.ResourceNotFoundException;
import com.online.store.server.models.Role;
import com.online.store.server.models.User;
import com.online.store.server.payload.request.LoginRequest;
import com.online.store.server.payload.request.RegisterRequest;
import com.online.store.server.repositories.UserRepository;
import com.online.store.server.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;

    @Override
    public User register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateElementException(String.format("User with email %s already exists", registerRequest.getEmail()));
        }
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getById(1));
        return userRepository.save(User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .roles(roles)
                .build());
    }

    @Override
    public User login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with email %s not found", loginRequest.getEmail()))
        );
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }
        return user;
    }
}
