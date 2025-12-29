package com.example.AIToolsLearn.model;


import com.example.AIToolsLearn.model.AiTool;
import com.example.AIToolsLearn.model.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ✅ wrapper

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "admin")
    List<AiTool> aiTools;

    public Admin() {
    }

    public Admin(Long id, String name, String password, List<AiTool> aiTools) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.aiTools = aiTools;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AiTool> getAiTools() {
        return aiTools;
    }

    public void setAiTools(List<AiTool> aiTools) {
        this.aiTools = aiTools;
    }
}