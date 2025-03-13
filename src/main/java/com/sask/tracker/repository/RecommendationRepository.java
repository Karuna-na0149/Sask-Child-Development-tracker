package com.sask.tracker.repository;

import com.sask.tracker.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    //  Get recommendations by age
    List<Recommendation> findByAge(int age);
    
    @Query("SELECT r FROM Recommendation r WHERE r.age = :age AND r.category = :category")
    List<Recommendation> findByAgeAndCategory(int age, String category);
}
