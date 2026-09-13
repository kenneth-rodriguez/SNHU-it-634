package com.kenRodriguez.flightswebapp.AuthService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Manage the webpage traffic for our authorization controller.
@Controller
public class AuthPageController {

    private final AuthService authService = new AuthService();

    // Use Spring to send traffic to our login page
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    // Use Spring to send traffic to our registration page.
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    // Handle new user registration through the web form.
    @PostMapping("/register")
    public String register(
            // Collect parameters from the page. Username/password/password confirmation.
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String passConfirm,
            Model model) {

        // Verify that the inputted passwords match before attempting registration.
        if (!password.equals(passConfirm)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        // Attempt to register the user through the AuthService.
        // Check to see if the username already exists.
        if (!authService.register(username, password)) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        // On successful registration, send user to the login page.
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(
            // Get our username/password from the form, and ensure session persistence between pages.
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model){

        // Check users.xml to make sure our submitted username and password exist
        // If not, return an error.
        if (!authService.authenticate(username, password)) {
            model.addAttribute("error", "Incorrect username or password");
            return "login";
        }

        // Store username in our session to ensure persistence between pages.
        session.setAttribute("username", username);

        // Send the user back to the main page on successful login.
        return "redirect:/index";
    }

    // Let users logout.
    @GetMapping("/logout")
    public String logout(HttpSession session){
        // Invalidate our session and return to the homepage.
        session.invalidate();
        return "redirect:/index";
    }
}
