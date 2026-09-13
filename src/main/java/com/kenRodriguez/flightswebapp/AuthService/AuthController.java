package com.kenRodriguez.flightswebapp.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// API Authentication service. append our endpoints to the authentication API!
// Take data, verify or register new users, and ensure that POSTs can produce actionable data.
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService = new AuthService();

    // Open a POST route to our login page. send data in, verify its efficacy.
    @PostMapping("/login")
    // Use our Login Java object to pass username and password credentials through to the API.
    public ResponseEntity<Map<String, String>> login(@RequestBody Login login){

        // Boolean value. if authenticated, true, if authentication fails, false.
        // Pull from our authentication service to make this simple.
        boolean authenticated = authService.authenticate(
                login.getUsername(),
                login.getPassword()
        );

        // If boolean authenticated is true, return that status.
        if (authenticated){
            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "auth successful"
                    )
            );
        }

        // Else, return authentication failure.
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        Map.of(
                                "status", "failure",
                                "message", "Invalid credentials"
                        )
                );
    }

    // Similarly, open a POST route to our registration page, and verify users don't already exist.
    @PostMapping("/register")
    // Use our RegistrationRequest Java object to pass our username and password to the API.
    public ResponseEntity<Map<String, String>> register(@RequestBody RegistrationRequest request){

        // Use our authentication service to check if our user already exists; receive a T/F response.
        boolean registered = authService.register(
                request.getUsername(),
                request.getPassword()
        );

        // IF the user is successfully registered (true), return that status.
        if (registered){
            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "User registered successfully"
                    )
            );
        }

        // If the username already exists within users.xml, return a failure.
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        Map.of(
                                "status", "failure",
                                "message", "Username already exists"
                        )
                );
    }
}
