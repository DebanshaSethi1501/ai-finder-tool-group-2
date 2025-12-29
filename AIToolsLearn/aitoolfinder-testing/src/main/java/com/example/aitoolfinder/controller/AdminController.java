package com.example.aitoolfinder.controller;

import com.example.aitoolfinder.model.Review;
import com.example.aitoolfinder.service.ReviewService;
import com.example.aitoolfinder.service.ToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ToolService toolService;
    private final ReviewService reviewService;

    public AdminController(ToolService toolService,
                           ReviewService reviewService) {
        this.toolService = toolService;
        this.reviewService = reviewService;
    }

    // DELETE /admin/tools/{id}
    @DeleteMapping("/tools/{id}")
    public ResponseEntity<String> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return ResponseEntity.ok("Tool deleted successfully");
    }

    // PUT /admin/reviews/{id}?approve=true
    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> approveOrRejectReview(
            @PathVariable Long id,
            @RequestParam boolean approve) {

        Review review = reviewService.approveReview(id, approve);
        return ResponseEntity.ok(review);
    }
}
