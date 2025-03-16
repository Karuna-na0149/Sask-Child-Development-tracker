package com.sask.tracker.controller;

import java.util.ArrayList;
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
        return "login"; // Resolves to templates/index.html
    }

    //  Display Child Profile form
    @GetMapping("/childProfile")
    public String showChildProfileForm(Model model, @RequestParam(value = "id", required = false) Long id) {
        ChildProfile profile = (id != null) ? childProfileService.getProfile(id) : new ChildProfile();
        model.addAttribute("childProfile", profile);
        return "childProfile"; // Resolves to templates/childProfile.html
    }
    @GetMapping("/milestone/list")
    public String listMilestones(
            @RequestParam(value = "age", required = false) Integer age,
            @RequestParam(value = "category", required = false) String category,
            Model model) {

        List<Milestone> milestones = new ArrayList<>();
        if (age != null && category != null) {
            milestones = milestoneService.getMilestonesByAgeAndCategory(age, category);
        }

        List<Integer> ages = milestoneService.getDistinctAges();
        List<String> categories = milestoneService.getAllCategories();

        model.addAttribute("milestones", milestones);
        model.addAttribute("ages", ages);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedAge", age);
        model.addAttribute("selectedCategory", category);

        return "list_milestone";
    }

    @GetMapping("/milestone/edit/{id}")
    public String editMilestone(@PathVariable Long id, Model model) {
        Milestone milestone = milestoneService.getMilestoneById(id).orElse(new Milestone());
        model.addAttribute("milestone", milestone);
        return "milestone";
    }


    //  Display form to add/edit a milestone
    @GetMapping("/add/milestoneForm")
    public String showMilestoneForm(Model model, @RequestParam(value = "id", required = false) Long id) {
        Milestone milestone;

        if (id != null) {
            //  Editing an existing milestone
        	milestone = milestoneService.getMilestoneById(id).orElse(new Milestone());
        } else {
            //  Creating a new milestone, set ID as highest + 1
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
