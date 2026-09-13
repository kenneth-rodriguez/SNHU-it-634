package com.kenRodriguez.flightswebapp.AuthService;

// user data structure. for Java objects, specifically.
// Fields may be added and removed as needed; just building a framework.
public class User {
    String username;
    String password;

    // Constructor
    public User(String username, String password) {
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
