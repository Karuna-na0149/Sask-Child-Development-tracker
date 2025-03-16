package com.sask.tracker.service;

import com.sask.tracker.model.Milestone;
import com.sask.tracker.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    //  Get all Milestones
    public List<Milestone> getAllMilestones() {
        return milestoneRepository.findAll();
    }

    //  Get Milestone by ID
    public Optional<Milestone> getMilestoneById(Long id) {
        return milestoneRepository.findById(id);
    }

    //  Get all Milestones by Category
    public List<Milestone> getMilestonesByCategory(String category) {
        return milestoneRepository.findByCategory(category);
    }

    //  Get all unique categories (for dropdown)
    public List<String> getAllCategories() {
        return milestoneRepository.findAll()
                .stream()
                .map(Milestone::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    //  Get Milestones by Age (can be multiple)
    public List<Milestone> getMilestoneForProfile(int age) {
        return milestoneRepository.findByAge(age);
    }
    
    //  Get all distinct ages (for dropdown)
    public List<Integer> getAllAges() {
        return milestoneRepository.findAll()
                .stream()
                .map(Milestone::getAge)
                .distinct()
                .collect(Collectors.toList());
    }

    //  Get Milestones by Age and Category
    public List<Milestone> getMilestonesByAgeAndCategory(int age, String category) {
        return milestoneRepository.findByAgeAndCategory(age, category);
    }

    //  Find Milestones by IDs — Needed for saving in ChildProfile
    public List<Milestone> findByIds(List<Long> ids) {
        return milestoneRepository.findAllById(ids);
    }

    //  Save or Update Milestone
    public Milestone saveMilestone(Milestone milestone) {
        // If no ID is provided, auto-generate the next available ID
        if (milestone.getId() == null) {
            milestone.setId(getNextMilestoneId());
        }
        return milestoneRepository.save(milestone);
    }

    //  Delete Milestone by ID
    public void deleteMilestone(Long id) {
        milestoneRepository.deleteById(id);
    }

    //  Get Next Available ID — Highest ID + 1
    public Long getNextMilestoneId() {
        Long highestId = milestoneRepository.findHighestId();
        return (highestId != null) ? highestId + 1 : 1;
    }
    
 //  Get all unique ages from the milestone table
    public List<Integer> getDistinctAges() {
        return milestoneRepository.findDistinctAges();
    }
    
    public void saveAllMilestones(List<Milestone> milestones) {
        milestoneRepository.saveAll(milestones);
    }
}
