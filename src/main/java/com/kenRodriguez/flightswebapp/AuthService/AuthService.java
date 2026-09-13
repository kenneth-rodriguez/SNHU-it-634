package com.kenRodriguez.flightswebapp.AuthService;

// Our Authentication service!
public class AuthService
{
    private final UserRepository userRepository = new UserRepository();

    // Return whether authentication is successful or not.
    public boolean authenticate(String username, String password){
        // pull up our user list from our XML file, then search for their username.
        User user = userRepository.findByUsername(username);

        // if the user can't be found, return false.
        if (user == null){
            return false;
        }
        return user.getPassword().equals(password);
    }

    // Return whether user registration is successful or not.
    public boolean register(String username, String password){
        // Check to see if our user already exists
        if (userRepository.findByUsername(username) != null){
            return false;
        }

        // if credentials don't already exist, create new user and save to our users.xml
        User newUser = new User(username, password);
        userRepository.addUser(newUser);

        return true;
    }
}
