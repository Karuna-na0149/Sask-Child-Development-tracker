package com.sask.tracker.controller;

import com.sask.tracker.model.Milestone;
import com.sask.tracker.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/milestone")
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    //  Show all milestones
    @GetMapping
    public String listMilestones(@RequestParam(required = false) String category, Model model) {
        List<Milestone> milestones;

        if (category != null && !category.isEmpty()) {
            milestones = milestoneService.getMilestonesByCategory(category);
        } else {
        	milestones = milestoneService.getAllMilestones();
        }

        model.addAttribute("milestones", milestones);
        model.addAttribute("categories", milestoneService.getAllCategories()); // Get unique categories for the dropdown
        return "milestones";
    }

    //  Show form for adding a new milestones
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("milestone", new Milestone());
        model.addAttribute("categories", milestoneService.getAllCategories()); // Populate categories in the form
        return "recommendation_form";
    }

    //  Save or update a milestones
    @PostMapping("/save")
    public String saveRecommendation(@ModelAttribute Milestone milestone, Model model) {
        System.out.println("Received milestone Data:");
        System.out.println("ID: " + milestone.getId()); // Should be NULL for new entries
        System.out.println("Age: " + milestone.getAge());
        System.out.println("Recommendation: " + milestone.getDevelopmentMilestone());
        System.out.println("Category: " + milestone.getCategory());

        // Save the milestones
        milestoneService.saveMilestone(milestone);

        //  Pass a success message to the UI
        model.addAttribute("successMessage", "Milestone saved successfully!");

        Long nextId= milestoneService.getNextMilestoneId();
        Milestone newMilestone = new Milestone();
        newMilestone.setId(nextId); 
        
        //  Reset the form by passing a new milestones object
        model.addAttribute("milestone", newMilestone);

        //  Pass categories again for dropdown
        List<String> categories = Arrays.asList(
            "Physical Growth and Development",
            "Thinking and Reasoning (Cognitive Development)",
            "Emotional and Social Development",
            "Language Development",
            "Sensory and Motor Development"
        );
        model.addAttribute("categories", categories);

        return "milestone_form"; //  Return to the same form
    }


    //  Show edit form for a milestones
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Milestone> milestone = milestoneService.getMilestoneById(id);
        model.addAttribute("milestone", milestone.orElse(new Milestone()));
        model.addAttribute("categories", milestoneService.getAllCategories()); // Populate categories in the edit form
        return "recommendation_form";
    }

    //  Delete a milestones
    @GetMapping("/delete/{id}")
    public String deleteRecommendation(@PathVariable Long id) {
    	milestoneService.deleteMilestone(id);
        return "redirect:/view/milestone";
    }

    
}
