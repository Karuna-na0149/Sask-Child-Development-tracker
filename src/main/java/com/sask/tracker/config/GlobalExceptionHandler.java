package com.sask.tracker.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sask.tracker.exception.EmailDeliveryException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailDeliveryException.class)
    public String handleEmailDeliveryException(EmailDeliveryException ex, RedirectAttributes redirectAttributes) {
        //  Pass error message using Flash Attributes (direct redirect)
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, RedirectAttributes redirectAttributes) {
        //  Handle other exceptions globally
        redirectAttributes.addFlashAttribute("errorMessage", "Oops! Something went wrong. Please try again later.");
        return "redirect:/error";
    }
}

