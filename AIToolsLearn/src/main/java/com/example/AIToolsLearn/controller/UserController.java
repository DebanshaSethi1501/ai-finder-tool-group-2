package com.example.AIToolsLearn.controller;

import com.example.AIToolsLearn.model.AiTool;
import com.example.AIToolsLearn.model.Review;
import com.example.AIToolsLearn.repository.AiToolsRepo;
import com.example.AIToolsLearn.repository.ReviewRepo;
import com.example.AIToolsLearn.services.AiToolsServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final AiToolsRepo aiToolsRepo;
    private final AiToolsServices aiToolsServices;
    private final ReviewRepo reviewRepo;
    public UserController(AiToolsRepo aiToolsRepo,AiToolsServices aiToolsServices , ReviewRepo reviewRepo) {
        this.aiToolsRepo = aiToolsRepo;
        this.aiToolsServices=aiToolsServices;
        this.reviewRepo=reviewRepo;
    }
    @GetMapping("/getAll")
    public List<AiTool> getAllAiTools(){
        return aiToolsRepo.findAll();
    }

    @GetMapping("/tools")
    public ResponseEntity<?> getTools(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) Double rating
    ) {
        List<AiTool> tools = aiToolsServices.getFilteredTools(category, rating, price);

        if (tools.isEmpty()) {
            return ResponseEntity.ok("No tools available for your Requirements");
        }

        return ResponseEntity.ok(tools);
    }

    @GetMapping("/user/reviews/approved/{id}")
    public List<Review> getAllApprovedReveiwsById(@PathVariable Long id){
        return reviewRepo.findApprovedReviewsByAiToolId(id);
    }

    @PostMapping("/user/reviews/add/{id}")
    public ResponseEntity<Map<String,String>> addReview(@RequestBody Review review, @PathVariable Long id){
        Map<String,String> map = new HashMap<>();
        review.setAiTool(aiToolsRepo.findById(id).orElseThrow(()-> new RuntimeException("AI Tool not found")));
        review.setStatus(Review.ReviewStatus.PENDING);
        reviewRepo.save(review);
        map.put("status","Review submitted successfully. It will be visible after admin approval.");
        return ResponseEntity.ok(map);
    }
}
