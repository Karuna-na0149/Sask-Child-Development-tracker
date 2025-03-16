package com.sask.tracker.repository;

import com.sask.tracker.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    //  Find Milestones for an exact age (can be multiple)
    List<Milestone> findByAge(int age);

    //  Find Milestones by category
    List<Milestone> findByCategory(String category);

    //  Find Milestones by age AND category
    List<Milestone> findByAgeAndCategory(int age, String category);

    //  Find the highest existing ID in the table
    @Query("SELECT MAX(m.id) FROM Milestone m")
    Long findHighestId();
    
    @Query("SELECT DISTINCT m.age FROM Milestone m ORDER BY m.age ASC")
    List<Integer> findDistinctAges();
}
