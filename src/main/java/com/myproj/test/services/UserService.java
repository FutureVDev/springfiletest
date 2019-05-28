package com.myproj.test.services;


import com.myproj.test.entities.SystemUser;
import com.myproj.test.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
    boolean save(SystemUser systemUser);
}
