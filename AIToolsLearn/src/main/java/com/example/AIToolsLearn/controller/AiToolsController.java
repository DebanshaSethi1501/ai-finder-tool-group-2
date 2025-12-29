package com.example.AIToolsLearn.controller;


import com.example.AIToolsLearn.model.Admin;
import com.example.AIToolsLearn.model.AiTool;
import com.example.AIToolsLearn.repository.AdminRepo;
import com.example.AIToolsLearn.repository.AiToolsRepo;
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
@RequestMapping("admin/aitools")
public class AiToolsController {

    private  final AiToolsRepo aiToolsRepo;
    private  final AdminRepo adminRepo;
    private final JWTService jwtService;
    private final AiToolsServices aiToolsServices;
    private final ReveiwService reveiwService;

    public AiToolsController(AiToolsRepo aiToolsRepo, AdminRepo adminRepo, JWTService jwtService, AiToolsServices aiToolsServices, ReveiwService reveiwService) {
        this.aiToolsRepo = aiToolsRepo;
        this.adminRepo = adminRepo;
        this.jwtService = jwtService;
        this.aiToolsServices = aiToolsServices;
        this.reveiwService = reveiwService;
    }

    public Admin getAuthenticatedAdmin(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String adminName = userDetails.getUsername();
        return adminRepo.findByName(adminName);
    }

    @GetMapping
    public List<AiTool> getAllAiTools(){
        return aiToolsRepo.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAiTool(@RequestBody AiTool aiTool, Authentication authentication){
        System.out.println("Received AiTool:");
        System.out.println("Name: " + aiTool.getName());
        System.out.println("Description: " + aiTool.getDescription());
        System.out.println("Usecases: " + aiTool.getUsecases());
        System.out.println("Category: " + aiTool.getCategory());
        System.out.println("PricingType: " + aiTool.getPricingtype());
        System.out.println("Rating: " + aiTool.getRating());

        aiTool.setAdmin(getAuthenticatedAdmin(authentication));
        aiTool.setRating(0.0);
        AiTool savedTool = aiToolsRepo.save(aiTool);
        return ResponseEntity.status(200).body(savedTool);
    }


//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Map<String,String>> deleteAiTool(@PathVariable Long id, Authentication authentication) {
//        AiTool aiTool = aiToolsRepo.findById(id).orElseThrow(()-> new RuntimeException("AI Tool not found"));
//        if(!aiTool.getAdmin().getId().equals(getAuthenticatedAdmin(authentication).getId()))
//        {
//            Map<String,String> map = new HashMap<>();
//            map.put("Error","You are not authorized to delete this AI Tool");
//            return ResponseEntity.status(403).body(map);
//        } else {
//            aiToolsRepo.deleteById(id);
//            Map<String,String> map = new HashMap<>();
//            map.put("status","Ai Tool deleted successfully");
//            return ResponseEntity.status(200).body(map);
//        }
//
//    }
}
