package com.sask.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sask.tracker.model.User;
import com.sask.tracker.service.UserService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @Autowired
    private UserService userService;
    @RequestMapping("/error")
    public String handleError(@RequestParam(value = "errorMessage", required = false) String errorMessage,
                              HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == 404) {
                errorMessage = "The page you are looking for cannot be found.";
            } else if (statusCode == 500) {
                errorMessage = "Internal Server Error. Please try again later.";
            }
        }

        //  Check for custom error message passed as Flash Attribute
        if (errorMessage == null) {
            errorMessage = "Oops! Something went wrong. Please try again later.";
        }

        model.addAttribute("errorMessage", errorMessage);

        //  Redirect to appropriate dashboard
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String loggedInEmail = authentication.getName();
            User loggedInUser = userService.getUserByEmail(loggedInEmail);

            if (loggedInUser != null) {
                if (loggedInUser.getRole().equalsIgnoreCase("ADMIN")) {
                    model.addAttribute("dashboardLink", "/admin/dashboard");
                    model.addAttribute("dashboardText", "Go to Admin Dashboard");
                } else {
                    model.addAttribute("dashboardLink", "/parent/dashboard");
                    model.addAttribute("dashboardText", "Go to Parent Dashboard");
                }
            }
        } else {
            model.addAttribute("dashboardLink", "/login");
            model.addAttribute("dashboardText", "Go to Login");
        }

        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}
