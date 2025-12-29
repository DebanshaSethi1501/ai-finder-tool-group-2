package com.example.AIToolsLearn.controller;


import com.example.AIToolsLearn.model.Admin;
import com.example.AIToolsLearn.repository.AdminRepo;
import com.example.AIToolsLearn.services.CustomUserDetailsService;
import com.example.AIToolsLearn.services.JWTService;
import com.example.AIToolsLearn.userdetails.CustomUserDetails;
import io.jsonwebtoken.security.Password;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/admin")
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepo adminRepo;
    private  final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTService jwtService;

    public AdminController(PasswordEncoder passwordEncoder, AdminRepo adminRepo ,AuthenticationManager authenticationManager , CustomUserDetailsService customUserDetailsService , JWTService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepo=adminRepo;
        this.authenticationManager=authenticationManager;
        this.customUserDetailsService =customUserDetailsService;
        this.jwtService=jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String ,String>> registerAdmin(@RequestBody Admin admin){
        Map <String, String> map = new HashMap<>();
        Admin existingAdmin = adminRepo.findByName(admin.getName());
        if(existingAdmin!=null){
            map.put("Error","UserName already Exists");
            return ResponseEntity.status(400).body(map);
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepo.save(admin);

        map.put("status","created user");
        return ResponseEntity.status(200).body(map);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String ,String>> loginAdmin(@RequestBody Admin admin){
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(admin.getName(),admin.getPassword()));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(admin.getName());
        String token = jwtService.generateToken(userDetails);
//        Admin authenticatedAdmin = adminS.getAdminByName(admin.getName());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Login successful");
        response.put("username", admin.getName());
//        response.put("adminId", authenticatedAdmin.getId().toString());

        return ResponseEntity.ok(response);
    }

}
