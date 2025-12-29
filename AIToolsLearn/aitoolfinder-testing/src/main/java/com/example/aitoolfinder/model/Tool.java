package com.example.aitoolfinder.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tools")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String useCase;
    private String category;
    private String pricingType;
    private Double averageRating;

    public Tool() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUseCase() {
        return useCase;
    }

    public String getCategory() {
        return category;
    }

    public String getPricingType() {
        return pricingType;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPricingType(String pricingType) {
        this.pricingType = pricingType;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
