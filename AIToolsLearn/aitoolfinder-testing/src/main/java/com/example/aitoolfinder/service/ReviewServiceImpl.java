package com.example.aitoolfinder.service;

import com.example.aitoolfinder.dto.ReviewRequestDto;
import com.example.aitoolfinder.model.Review;
import com.example.aitoolfinder.model.ReviewStatus;
import com.example.aitoolfinder.model.Tool;
import com.example.aitoolfinder.repository.ReviewRepository;
import com.example.aitoolfinder.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ToolRepository toolRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ToolRepository toolRepository) {
        this.reviewRepository = reviewRepository;
        this.toolRepository = toolRepository;
    }

    @Override
    public Review submitReview(ReviewRequestDto dto) {

        Tool tool = toolRepository.findById(dto.getToolId())
                .orElseThrow(() -> new RuntimeException("Tool not found"));

        Review review = new Review();
        review.setTool(tool);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setStatus(ReviewStatus.PENDING);

        return reviewRepository.save(review);
    }

    @Override
    public Review approveReview(Long reviewId, boolean approve) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setStatus(approve ? ReviewStatus.APPROVED : ReviewStatus.REJECTED);
        Review savedReview = reviewRepository.save(review);

        if (approve) {
            recalculateRating(review.getTool());
        }

        return savedReview;
    }

    private void recalculateRating(Tool tool) {

        List<Review> approvedReviews =
                reviewRepository.findByToolIdAndStatus(
                        tool.getId(), ReviewStatus.APPROVED);

        double avg = approvedReviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        tool.setAverageRating(avg);
        toolRepository.save(tool);
    }
}
