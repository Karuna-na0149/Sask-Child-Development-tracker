package com.sask.tracker.service;

import com.sask.tracker.model.Milestone;
import com.sask.tracker.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneChartService {

	@Autowired
	private MilestoneRepository milestoneRepository;

	public List<Milestone> getMilestonesByChildProfileId(Long childId) {
		return milestoneRepository.findMilestonesByChildProfileId(childId);
	}
}
