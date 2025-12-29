package com.example.aitoolfinder.service;

import com.example.aitoolfinder.dto.ReviewRequestDto;
import com.example.aitoolfinder.model.Review;
import com.example.aitoolfinder.model.ReviewStatus;
import com.example.aitoolfinder.model.Tool;
import com.example.aitoolfinder.repository.ReviewRepository;
import com.example.aitoolfinder.repository.ToolRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void shouldSubmitReviewSuccessfully() {

        Tool tool = new Tool();
        tool.setId(1L);

        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setToolId(1L);
        dto.setRating(5);
        dto.setComment("Excellent");

        when(toolRepository.findById(1L)).thenReturn(Optional.of(tool));
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0));

        Review review = reviewService.submitReview(dto);

        assertNotNull(review);
        assertEquals(ReviewStatus.PENDING, review.getStatus());
        assertEquals(5, review.getRating());

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void shouldApproveReviewAndRecalculateRating() {

        Tool tool = new Tool();
        tool.setId(1L);

        Review review = new Review();
        review.setId(10L);
        review.setTool(tool);
        review.setRating(4);
        review.setStatus(ReviewStatus.PENDING);

        when(reviewRepository.findById(10L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewRepository.findByToolIdAndStatus(1L, ReviewStatus.APPROVED))
                .thenReturn(List.of(review));

        Review approved = reviewService.approveReview(10L, true);

        assertEquals(ReviewStatus.APPROVED, approved.getStatus());
        verify(toolRepository, times(1)).save(any(Tool.class));
    }
}
