package com.example.AIToolsLearn.repository;

import com.example.AIToolsLearn.model.AiTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AiToolsRepo extends JpaRepository<AiTool,Long> {
    @Query("""
		    SELECT t FROM AiTool t
		    WHERE (:category IS NULL OR LOWER(t.category) = LOWER(:category))
		      AND (:pricingtype IS NULL OR LOWER(t.pricingtype) = LOWER(:pricingtype))
		      AND (:rating IS NULL OR t.rating >= :rating)
		""")
    List<AiTool> filterTools(
            @Param("category") String category,
            @Param("pricingtype") String pricingtype,
            @Param("rating") Double rating
    );
}
