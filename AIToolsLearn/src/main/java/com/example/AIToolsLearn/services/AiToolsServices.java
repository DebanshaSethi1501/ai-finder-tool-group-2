package com.example.AIToolsLearn.services;


import com.example.AIToolsLearn.model.AiTool;
import com.example.AIToolsLearn.model.Review;
import com.example.AIToolsLearn.repository.AiToolsRepo;
import com.example.AIToolsLearn.repository.ReviewRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiToolsServices {

    private final AiToolsRepo aiToolsRepo;
    private final ReviewRepo reviewRepo;

    public AiToolsServices(AiToolsRepo aiToolsRepo, ReviewRepo reviewRepo) {
        this.aiToolsRepo = aiToolsRepo;
        this.reviewRepo = reviewRepo;
    }
    public void updateAiToolRating(Long aiToolId) {
        AiTool aiTool = aiToolsRepo.findById(aiToolId)
                .orElseThrow(() -> new RuntimeException("AI Tool not found"));

        List<Review> approvedReviews = reviewRepo.findApprovedReviewsByAiToolId(aiToolId);

        if (approvedReviews.isEmpty()) {
            aiTool.setRating(0.0);
        } else {
            double averageRating = approvedReviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
            averageRating = Math.round(averageRating * 10.0) / 10.0;

            aiTool.setRating(averageRating);
        }
        aiToolsRepo.save(aiTool);
    }
    public List<AiTool> getFilteredTools(String category,Double rating, String price){
        return aiToolsRepo.filterTools(category,price,rating);
    }
}
