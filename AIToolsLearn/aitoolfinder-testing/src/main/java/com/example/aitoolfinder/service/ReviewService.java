package com.example.aitoolfinder.service;

import com.example.aitoolfinder.dto.ReviewRequestDto;
import com.example.aitoolfinder.model.Review;

public interface ReviewService {

    Review submitReview(ReviewRequestDto dto);

    Review approveReview(Long reviewId, boolean approve);
}
