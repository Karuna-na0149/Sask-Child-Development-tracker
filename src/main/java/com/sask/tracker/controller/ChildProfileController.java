package com.sask.tracker.controller;

import com.sask.tracker.model.ChildProfile;
import com.sask.tracker.model.Milestone;
import com.sask.tracker.model.User;
import com.sask.tracker.service.ChildProfileService;
import com.sask.tracker.service.EmailService;
import com.sask.tracker.service.MilestoneService;
import com.sask.tracker.service.RecommendationService;
import com.sask.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/child")
public class ChildProfileController {

    @Autowired
    private ChildProfileService childProfileService;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private RecommendationService recommendationService;
    
    @Autowired
    private EmailService emailService;

    //  Load form with milestones grouped by category
    @GetMapping("/new")
    public String newProfileForm(Model model) {
        ChildProfile profile = new ChildProfile();

        //  Get logged-in user details from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();
        User loggedInUser = userService.getUserByEmail(loggedInEmail);

        if (loggedInUser != null) {
            profile.setParentId(loggedInUser.getId());
            profile.setParentEmail(loggedInUser.getEmail());
        }

        model.addAttribute("childProfile", profile);
        return "childProfileForm";
    }

    //  Load milestones grouped by category based on age
    @GetMapping("/milestones/{age}")
    @ResponseBody
    public Map<String, List<Milestone>> getMilestonesByAge(@PathVariable int age) {
        List<Milestone> milestones = milestoneService.getMilestoneForProfile(age);

        //  Group milestones by category
        return milestones.stream()
                .collect(Collectors.groupingBy(Milestone::getCategory));
    }

    @PostMapping("/save")
    public String saveProfile(@ModelAttribute ChildProfile profile,
                              @RequestParam("milestoneIds") List<Long> milestoneIds) {

        //  Save selected milestones
        Set<Milestone> selectedMilestones = new HashSet<>(milestoneService.findByIds(milestoneIds));
        profile.setMilestones(selectedMilestones);
        childProfileService.saveProfile(profile);

        //  Get all possible milestones for the given age
        List<Milestone> allMilestones = milestoneService.getMilestoneForProfile(profile.getAge());

        //  Group milestones by category
        Map<String, List<Milestone>> milestonesByCategory = allMilestones.stream()
                .collect(Collectors.groupingBy(Milestone::getCategory));

        //  Group selected milestones by category
        Map<String, List<Milestone>> selectedMilestonesByCategory = selectedMilestones.stream()
                .collect(Collectors.groupingBy(Milestone::getCategory));

        //  Collect recommendations
        StringBuilder recommendations = new StringBuilder();

        for (Map.Entry<String, List<Milestone>> entry : milestonesByCategory.entrySet()) {
            String category = entry.getKey();
            List<Milestone> allMilestonesInCategory = entry.getValue();

            //  Get selected milestones for this category (or empty if none selected)
            List<Milestone> selectedMilestonesInCategory =
                    selectedMilestonesByCategory.getOrDefault(category, Collections.emptyList());

            //  If not all milestones in the category are selected → Add to recommendations
            if (selectedMilestonesInCategory.size() < allMilestonesInCategory.size()) {
                String recommendation = recommendationService.getRecommendationForAgeAndCategory(profile.getAge(), category);

                if (recommendation != null) {
                    recommendations.append("**Category:** ").append(category).append("\n");
                    recommendations.append(recommendation).append("\n\n");
                }
            }
        }
        
        if (recommendations.length() > 0) {
            String subject = "Development Recommendations for " + profile.getName();
            String body = "Dear Parent,\n\n"
                    + "We have observed that your child **" + profile.getName() + "** (Age " + profile.getAge() + ") "
                    + "may have missed some important developmental milestones. To support your child's growth and development, "
                    + "we recommend the following actions:\n\n"
                    + recommendations.toString()
                    + "We hope you find these suggestions helpful in guiding your child's development.\n\n"
                    + "Thank you for trusting the Sask Child Development Tracker.\n\n"
                    + "Best regards,\nSask Child Development Tracker Team";
        

            emailService.sendNotification(profile.getParentEmail(), subject, body);
        }

        return "redirect:/child/new?success"; //  Redirect with success message
    }
}
