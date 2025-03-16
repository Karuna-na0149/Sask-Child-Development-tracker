package com.sask.tracker.controller;

import com.sask.tracker.model.Milestone;
import com.sask.tracker.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

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


    //  Populate the form during update based on age and category
    @GetMapping("/edit")
    public String showEditForm(@RequestParam(required = false) Integer age,
                               @RequestParam(required = false) String category,
                               Model model) {

        Milestone milestone = milestoneService.getMilestonesByAgeAndCategory(age, category)
                .stream()
                .findFirst()
                .orElse(new Milestone());

        model.addAttribute("milestone", milestone);
        model.addAttribute("categories", milestoneService.getAllCategories());
        model.addAttribute("ages", milestoneService.getAllAges());

        return "milestone";
    }

    @GetMapping("/delete/{id}")
    public String deleteMilestone(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {
        milestoneService.deleteMilestone(id);
        redirectAttributes.addFlashAttribute("successMessage", "Milestone deleted successfully!");
        return "redirect:/view/milestone";
    }
    
    @PostMapping("/save/{id}")
    public String saveMilestone(
            @PathVariable Long id,
            @ModelAttribute Milestone milestone,
            RedirectAttributes redirectAttributes) {

        //  Assign ID manually since it comes from URL
        milestone.setId(id);
        milestoneService.saveMilestone(milestone);

        redirectAttributes.addFlashAttribute("successMessage", "Milestone updated successfully!");

        //  Redirect back to the same form with age and category pre-selected
        return "redirect:/view/milestone?age=" + milestone.getAge() + "&category=" + milestone.getCategory();
    }
    
    @PostMapping("/saveAll")
    public String saveAllMilestones(
            @ModelAttribute("milestones") List<Milestone> milestones,
            RedirectAttributes redirectAttributes) {

        milestoneService.saveAllMilestones(milestones);
        redirectAttributes.addFlashAttribute("successMessage", "All milestones updated successfully!");

        //  Redirect back to the milestone form after saving
        return "redirect:/view/milestone";
    }
    
}
