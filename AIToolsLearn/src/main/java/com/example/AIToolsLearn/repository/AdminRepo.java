package com.example.AIToolsLearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.AIToolsLearn.model.Admin;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin,Long> {
    Admin findByName(String name);
}
