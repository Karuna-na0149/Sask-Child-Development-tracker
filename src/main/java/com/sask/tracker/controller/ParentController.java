package com.sask.tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParentController {

    @GetMapping("/parent/dashboard")
    public String parentDashboard(Model model) {
        model.addAttribute("message", "Welcome to the Parent Dashboard");
        return "parent_dashboard";
    }
}
