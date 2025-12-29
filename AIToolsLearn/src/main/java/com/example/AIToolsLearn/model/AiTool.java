package com.example.AIToolsLearn.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.example.AIToolsLearn.model.Admin;

import java.util.List;

@Entity
@Table(name = "ai_tools")
public class AiTool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "usecases")
    private String usecases;

    @Column(name = "category")
    private String category;

    @Column(name = "pricingtype")
    private String pricingtype;

    @Column(name = "rating")
    private double rating;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "aiTool")
    private List<Review> reviews;

    public AiTool(Long id, String name, String description, String usecases, String category, String pricingtype, double rating, Admin admin, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.usecases = usecases;
        this.category = category;
        this.pricingtype = pricingtype;
        this.rating = rating;
        this.admin = admin;
        this.reviews = reviews;
    }

    public AiTool() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsecases() {
        return usecases;
    }

    public void setUsecases(String usecases) {
        this.usecases = usecases;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPricingtype() {
        return pricingtype;
    }

    public void setPricingtype(String pricingtype) {
        this.pricingtype = pricingtype;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
