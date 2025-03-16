package com.sask.tracker.service;

import com.sask.tracker.model.ChildProfile;
import com.sask.tracker.repository.ChildProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildProfileService {

    @Autowired
    private ChildProfileRepository childProfileRepository;

 //  Add this method to ChildProfileService
    public ChildProfile getProfile(Long id) {
        return childProfileRepository.findById(id).orElse(null);
    }

    public ChildProfile saveProfile(ChildProfile profile) {
        if (profile.getId() != null) {
            //  Check if the profile exists before saving
            ChildProfile existingProfile = childProfileRepository.findById(profile.getId()).orElse(null);
            if (existingProfile != null) {
                existingProfile.setName(profile.getName());
                existingProfile.setAge(profile.getAge());
                existingProfile.setMilestones(profile.getMilestones());
                existingProfile.setParentId(profile.getParentId());
                existingProfile.setParentEmail(profile.getParentEmail());
                return childProfileRepository.save(existingProfile); //  Update existing profile
            }
        }
        //  If no ID → Create new profile
        return childProfileRepository.save(profile);
    }

    //  Get all profiles for logged-in parent
    public List<ChildProfile> getProfilesForParent(Long parentId) {
        return childProfileRepository.findByParentId(parentId);
    }

    //  Get a specific profile by parent ID and profile ID
    public ChildProfile getProfileForEdit(Long parentId, Long profileId) {
        return childProfileRepository.findByIdAndParentId(profileId, parentId).orElse(null);
    }

    //  Delete profile by ID
    public void deleteProfile(Long profileId) {
        childProfileRepository.deleteById(profileId);
    }
}
