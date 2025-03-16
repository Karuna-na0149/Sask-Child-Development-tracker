package com.sask.tracker.repository;

import com.sask.tracker.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    
    @Query(value = """
    	    SELECT m.* 
    	    FROM sask.milestone m
    	    INNER JOIN sask.child_profile_milestone cpm ON cpm.milestone_id = m.id
    	    INNER JOIN sask.child_profile cp ON cpm.child_profile_id = cp.id
    	    WHERE cp.id = :childId
    	    """, nativeQuery = true)
    	List<Milestone> findMilestonesByChildProfileId(@Param("childId") Long childId);
}
