package com.sask.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sask.tracker.model.ChildProfile;

public interface ChildProfileRepository extends JpaRepository<ChildProfile, Long> {
}