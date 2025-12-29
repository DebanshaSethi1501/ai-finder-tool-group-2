package com.example.AIToolsLearn.model;


import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private double rating;

    public enum ReviewStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status = ReviewStatus.PENDING;

    @ManyToOne
    @JoinColumn(name="ai_tool_id")
    private AiTool aiTool;

    public Review(Long id, String content, double rating, ReviewStatus status, AiTool aiTool) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.status = status;
        this.aiTool = aiTool;
    }

    public Review() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public AiTool getAiTool() {
        return aiTool;
    }

    public void setAiTool(AiTool aiTool) {
        this.aiTool = aiTool;
    }
}
