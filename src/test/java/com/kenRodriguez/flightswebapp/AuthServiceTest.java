package com.kenRodriguez.flightswebapp;

import com.kenRodriguez.flightswebapp.AuthService.AuthService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthServiceTest {

    // validate a valid username and password
    @Test
    public void authenticateUser() {
        AuthService authService = new AuthService();
        assertTrue(authService.authenticate("jacques", "pepin"));
    }

    // Test an invalid password on a valid user
    @Test
    public void wrongPassword() {
        AuthService authService = new AuthService();
        assertFalse(authService.authenticate("jacques", "bourdain"));
    }

    // Test a nonexistent user
    @Test
    public void nonexistentUser() {
        AuthService authService = new AuthService();
        assertFalse(authService.authenticate("emeril", "lagasse"));
    }

    // Test valid username registration
    // While not best practice, the user has to be manually removed from the XML data sheet between tests.
    @Test
    public void registerUser() {
        AuthService authService = new AuthService();
        assertTrue(authService.register("alton", "brown"));
    }

    // test existing username registration
    @Test
    public void registerExistingUser(){
        AuthService authService = new AuthService();
        assertFalse(authService.register("jacques", "pepin"));
    }
}
