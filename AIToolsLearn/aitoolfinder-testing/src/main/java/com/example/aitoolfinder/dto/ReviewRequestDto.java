package com.example.aitoolfinder.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReviewRequestDto {

    @NotNull
    private Long toolId;

    @Min(1)
    @Max(5)
    private int rating;

    private String comment;

    public Long getToolId() {
        return toolId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
