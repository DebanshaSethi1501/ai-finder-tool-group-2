package com.example.aitoolfinder.dto;

import jakarta.validation.constraints.NotBlank;

public class ToolRequestDto {

    @NotBlank(message = "Tool name is required")
    private String name;

    @NotBlank(message = "Use case is required")
    private String useCase;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Pricing type is required")
    private String pricingType;

    public ToolRequestDto() {
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
}
