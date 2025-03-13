package com.sask.tracker.controller;
import com.sask.tracker.model.User;
import com.sask.tracker.repository.UserRepository;
import com.sask.tracker.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @GetMapping("/user-login")
    public String loginForm() {
        return "login"; // This will return login.html
    }

    @PostMapping("/user-login")
    public String login(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        System.out.println("Inside login method for email: " + email);
        
        User user = userService.validateUser(email, password);
        if (user != null) {
            //Create authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword(),
                            Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
                    );
            
            // Set authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //  Store authentication in session so it persists across requests
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            if ("ADMIN".equals(user.getRole())) {
                System.out.println("Admin login success");
                return "redirect:/admin/dashboard";
            } else {
                System.out.println("Parent login success");
                return "redirect:/parent/dashboard";
            }
        }

        System.out.println("Login failed");
        return "redirect:/user-login?error";
    }
    
    @PostMapping("/signup")
    public String signup(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String secretQuestion,
                         @RequestParam String secretAnswer,
                         RedirectAttributes redirectAttributes) {

        if (userRepository.findByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("error", "Email already exists!");
            return "redirect:/signup";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("PARENT");
        user.setSecretQuestion(secretQuestion);
        user.setSecretAnswer(secretAnswer);

        userRepository.save(user);

        //  Use flash attribute for success message
        redirectAttributes.addFlashAttribute("successMessage", "Account created successfully!");
        return "redirect:/user-login"; // Redirect to login page
    }
    
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot_password";
    }
    
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String secretAnswer,
                                @RequestParam String newPassword,
                                RedirectAttributes redirectAttributes) {

        User user = userRepository.findByEmail(email);

        if (user != null && user.getSecretAnswer().equals(secretAnswer)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("successMessage", "Password reset successfully!");
            return "redirect:/user-login";
        }

        redirectAttributes.addFlashAttribute("error", "Invalid security answer!");
        return "redirect:/forgot-password";
    }
    
    @PostMapping("/forgot-password")
    public String loadSecurityQuestion(@RequestParam String email, Model model) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            model.addAttribute("email", email); //  Keep email in model
            model.addAttribute("securityQuestion", user.getSecretQuestion());
            return "forgot_password"; // Reload the form with the security question
        }

        model.addAttribute("email", email); //  Keep the entered email even if not found
        model.addAttribute("error", "Email not found!");
        return "forgot_password";
    }
    
 // Show Signup Form (GET request)
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup"; // This will resolve to signup.html
    }
    
 

}