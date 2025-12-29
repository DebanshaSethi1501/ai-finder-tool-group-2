package com.example.AIToolsLearn.controller;


import com.example.AIToolsLearn.model.Admin;
import com.example.AIToolsLearn.model.AiTool;
import com.example.AIToolsLearn.model.Review;
import com.example.AIToolsLearn.repository.AdminRepo;
import com.example.AIToolsLearn.repository.AiToolsRepo;
import com.example.AIToolsLearn.repository.ReviewRepo;
import com.example.AIToolsLearn.services.AiToolsServices;
import com.example.AIToolsLearn.services.JWTService;
import com.example.AIToolsLearn.services.ReveiwService;
import com.example.AIToolsLearn.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReviewController {

    private  final AiToolsRepo aiToolsRepo;
    private  final AdminRepo adminRepo;
    private final ReviewRepo reviewRepo;
    private final JWTService jwtService;
    private final AiToolsServices aiToolsServices;
    private final ReveiwService reveiwService;
//    private final Authentication authentication;

    public ReviewController(AiToolsRepo aiToolsRepo, AdminRepo adminRepo, JWTService jwtService, AiToolsServices aiToolsServices, ReveiwService reveiwService , ReviewRepo reviewRepo) {
        this.aiToolsRepo = aiToolsRepo;
        this.adminRepo = adminRepo;
        this.jwtService = jwtService;
        this.aiToolsServices = aiToolsServices;
        this.reveiwService = reveiwService;
//        this.authentication = authentication;
        this.reviewRepo = reviewRepo;
    }

    public Admin getAuthenticatedAdmin(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String adminName = userDetails.getUsername();
        return adminRepo.findByName(adminName);
    }

//    @GetMapping("user/getAll")
//    public List<AiTool> getAllAiTools(){
//        return aiToolsRepo.findAll();
//    }

    @GetMapping("admin/reviews/pending/{id}")
    public List<Review> getAllPendingReveiwsById(@PathVariable Long id){
        return reviewRepo.findPendingReviewsByAiToolId(id);
    }

//    @PostMapping("/user/reviews/add/{id}")
//    public ResponseEntity<Map<String,String>> addReview(@RequestBody Review review, @PathVariable Long id){
//        Map<String,String> map = new HashMap<>();
//        review.setAiTool(aiToolsRepo.findById(id).orElseThrow(()-> new RuntimeException("AI Tool not found")));
//        review.setStatus(Review.ReviewStatus.PENDING);
//        reviewRepo.save(review);
//        map.put("status","Review submitted successfully. It will be visible after admin approval.");
//        return ResponseEntity.ok(map);
//    }

    @PutMapping("/admin/reviews/approve/{id}")
    public ResponseEntity<Map<String,String>> approveReview(@PathVariable Long id, Authentication authentication){
        Review existingReview = reviewRepo.findById(id).orElseThrow(()-> new RuntimeException("Review not found"));
        if (existingReview.getStatus()== Review.ReviewStatus.APPROVED){
            throw new RuntimeException("Review is already approved");
        }
        Admin authenticatedAdmin = getAuthenticatedAdmin(authentication);
        Admin aiToolAdmin = existingReview.getAiTool().getAdmin();

        if (!authenticatedAdmin.getId().equals(aiToolAdmin.getId())){
            throw new RuntimeException("You are not authorized to approve this review");
        }
        existingReview.setStatus(Review.ReviewStatus.APPROVED);
        reviewRepo.save(existingReview);

        aiToolsServices.updateAiToolRating(existingReview.getAiTool().getId());

        Map<String,String> map = new HashMap<>();
        map.put("status","Review approved successfully and AI tool rating updated");
        return ResponseEntity.status(200).body(map);
    }

    @PutMapping("/admin/aitools/{id}/recalculate-rating")
    public ResponseEntity<Map<String,String>> recalculateAiToolRating(@PathVariable Long id, Authentication authentication){
        aiToolsRepo.findById(id).orElseThrow(()-> new RuntimeException("AI Tool not found"));

        aiToolsServices.updateAiToolRating(id);

        Map<String,String> map = new HashMap<>();
        map.put("status","AI tool rating recalculated successfully");
        return ResponseEntity.status(200).body(map);
    }

}
