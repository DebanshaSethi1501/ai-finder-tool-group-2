package com.example.AIToolsLearn.repository;

import com.example.AIToolsLearn.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review,Long> {

    @Query("""
            Select r from Review r where r.aiTool.id = :aiToolId and r.status = 'APPROVED'
            """)
    List<Review> findApprovedReviewsByAiToolId(Long aiToolId);
    @Query("""
            Select r from Review r where r.aiTool.id = :aiToolId and r.status = 'PENDING'
            """)
    List<Review> findPendingReviewsByAiToolId(Long aiToolId);
}
