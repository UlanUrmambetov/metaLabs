package service;

import DTO.RegisterRequest;
import models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    void register(RegisterRequest registerRequest);
}
