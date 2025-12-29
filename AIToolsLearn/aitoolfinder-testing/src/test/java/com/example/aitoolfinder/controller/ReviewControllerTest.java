package com.example.aitoolfinder.controller;

import com.example.aitoolfinder.dto.ReviewRequestDto;
import com.example.aitoolfinder.model.Review;
import com.example.aitoolfinder.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSubmitReview() throws Exception {

        Review review = new Review();
        review.setId(1L);

        when(reviewService.submitReview(any(ReviewRequestDto.class)))
                .thenReturn(review);

        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setToolId(1L);
        dto.setRating(5);
        dto.setComment("Excellent tool");

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
