package com.example.AIToolsLearn.services;

import com.example.AIToolsLearn.model.Admin;
import com.example.AIToolsLearn.userdetails.CustomUserDetails;
import com.example.AIToolsLearn.repository.AdminRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepo adminRepo;

    public CustomUserDetailsService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByName(username);
        return new CustomUserDetails(admin);
    }
}
