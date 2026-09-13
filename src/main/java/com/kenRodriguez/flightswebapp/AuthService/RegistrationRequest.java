package com.kenRodriguez.flightswebapp.AuthService;

// Get credentials for Jackson to turn into a tidy structure
public class RegistrationRequest {
    private String username;
    private String password;

    // Constructors
    RegistrationRequest() {

    }
    RegistrationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Accessors/mutators
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
