package com.sask.tracker.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sask.tracker.model.ChildProfile;
import com.sask.tracker.model.Milestone;
import com.sask.tracker.service.ChildProfileService;
import com.sask.tracker.service.MilestoneService;

@Controller
public class UIController {

    @Autowired
    private ChildProfileService childProfileService;
    
    @Autowired
    private MilestoneService milestoneService;

    //  Home page
    @GetMapping("/")
    public String index() {
        return "index"; // Resolves to templates/index.html
    }

    //  Display Child Profile form
    @GetMapping("/childProfile")
    public String showChildProfileForm(Model model, @RequestParam(value = "id", required = false) Long id) {
        ChildProfile profile = (id != null) ? childProfileService.getProfile(id) : new ChildProfile();
        model.addAttribute("childProfile", profile);
        return "childProfile"; // Resolves to templates/childProfile.html
    }
    
    //  Display list of all milestones
    @GetMapping("/view/milestone")
    public String listRecommendations(Model model) {
        model.addAttribute("recommendations", milestoneService.getAllMilestones());
        return "milestone"; // Resolves to templates/milestone.html
    }

    //  Display form to add/edit a milestone
    @GetMapping("/edit/milestoneForm")
    public String showMilestoneForm(Model model, @RequestParam(value = "id", required = false) Long id) {
        Milestone milestone;

        if (id != null) {
            //  Editing an existing recommendation
        	milestone = milestoneService.getMilestoneById(id).orElse(new Milestone());
        } else {
            //  Creating a new recommendation, set ID as highest + 1
            Long newId = milestoneService.getNextMilestoneId();
            milestone = new Milestone();
            milestone.setId(newId);
        }

        //  Define predefined categories
        List<String> categories = Arrays.asList(
            "Physical Growth and Development",
            "Thinking and Reasoning (Cognitive Development)",
            "Emotional and Social Development",
            "Language Development",
            "Sensory and Motor Development"
        );

        model.addAttribute("milestone", milestone);
        model.addAttribute("categories", categories);
        return "milestone_form"; // Resolves to templates/milestone_form.html
    }
}
