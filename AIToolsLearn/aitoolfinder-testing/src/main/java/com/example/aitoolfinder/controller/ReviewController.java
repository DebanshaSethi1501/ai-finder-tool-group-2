package com.example.aitoolfinder.controller;

import com.example.aitoolfinder.dto.ReviewRequestDto;
import com.example.aitoolfinder.model.Review;
import com.example.aitoolfinder.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // POST /reviews
    @PostMapping
    public ResponseEntity<Review> submitReview(
            @Valid @RequestBody ReviewRequestDto dto) {

        Review review = reviewService.submitReview(dto);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
}
