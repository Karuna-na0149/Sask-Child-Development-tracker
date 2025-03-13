package com.sask.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sask.tracker.model.ChildProfile;
import com.sask.tracker.repository.ChildProfileRepository;

@Service
public class ChildProfileService {

	@Autowired
	private ChildProfileRepository childProfileRepository;

	public ChildProfile saveProfile(ChildProfile profile) {
		// Optionally, add recommendation logic before saving
		return childProfileRepository.save(profile);
	}

	public ChildProfile getProfile(Long id) {
		return childProfileRepository.findById(id).orElse(null);
	}
}