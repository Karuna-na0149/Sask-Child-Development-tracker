package com.sask.tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sask.tracker.model.ChildProfile;

public interface ChildProfileRepository extends JpaRepository<ChildProfile, Long> {
	
	 // Find all child profiles for logged-in parent
    List<ChildProfile> findByParentId(Long parentId);

    Optional<ChildProfile> findById(Long id);  //  This method is standard in JpaRepository
    
    // Find specific child profile for editing by parent ID
    Optional<ChildProfile> findByIdAndParentId(Long id, Long parentId);
}