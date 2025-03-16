package com.sask.tracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sask.tracker.model.ChartData;
import com.sask.tracker.model.Milestone;
import com.sask.tracker.model.ChildProfile;
import com.sask.tracker.service.MilestoneChartService;
import com.sask.tracker.service.ChildProfileService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MilestoneChartController {

    private final MilestoneChartService milestoneChartService;
    private final ChildProfileService childProfileService;
    private final ObjectMapper objectMapper;

    public MilestoneChartController(MilestoneChartService milestoneChartService, 
                                    ChildProfileService childProfileService,
                                    ObjectMapper objectMapper) {
        this.milestoneChartService = milestoneChartService;
        this.childProfileService = childProfileService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/child/insights/{id}")
    public String showMilestoneInsights(@PathVariable Long id, Model model) {
        ChildProfile childProfile = childProfileService.getProfile(id);
        // Load milestones for the selected child profile
        List<Milestone> completedMilestones = milestoneChartService.getMilestonesByChildProfileId(id);

        // Prepare data for Chart.js (Category-based data)
        List<ChartData> chartData = completedMilestones.stream()
                .collect(Collectors.groupingBy(
                        Milestone::getCategory,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> new ChartData(entry.getKey(), entry.getValue().intValue()))
                .collect(Collectors.toList());

        // Convert chart data to JSON string for JS parsing
        ObjectMapper objectMapper = new ObjectMapper();
        String chartDataJson = "";
        try {
            chartDataJson = objectMapper.writeValueAsString(chartData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("childProfile", childProfile);
        model.addAttribute("completedMilestones", completedMilestones);
        model.addAttribute("chartDataJson", chartDataJson);

        return "milestone_chart";
    }

}
