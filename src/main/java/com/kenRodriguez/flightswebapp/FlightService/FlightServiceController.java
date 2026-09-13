package com.kenRodriguez.flightswebapp.FlightService;


import com.kenRodriguez.flightswebapp.BookingService.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

// Enable this class as a Spring framework controller. This'll help us actually pass data through the search form.
@Controller
public class FlightServiceController {

    private final FlightService flightService;
    private final BookingService bookingService;

    public FlightServiceController(FlightService flightService, BookingService bookingService) {

        this.flightService = flightService;
        this.bookingService = bookingService;
    }


    // map our target URL subdomain. search results? output to search. easy!
    @PostMapping("/search")
    public String searchFlights (
            // pull params from our HTML form
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam LocalDate departureDate,
            @RequestParam LocalDate arrivalDate,
            @RequestParam String travelers,
            Model model
    ){
        // add our params to our Spring framework model
        model.addAttribute("departure", departure);
        model.addAttribute("arrival", arrival);
        model.addAttribute("departureDate", departureDate);
        model.addAttribute("arrivalDate", arrivalDate);
        model.addAttribute("travelers", travelers);

        // Add our flights list
        List<Flight> flights =
                flightService.searchFlights(departure, arrival, departureDate, arrivalDate);
        model.addAttribute("flights", flights);

        return "results";
    }

    // View all flights in the event of an error OR the user presses the "flights" button.
    @GetMapping("/flights")
    public String viewAllFlights(Model model) {
        List<Flight> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);

        return "results";
    }

    // Allow users to book flights.
    @PostMapping("/book")
    public String bookFlight(
            @RequestParam int flightId,
            HttpSession session,
            Model model
    ){
        // Get the current user (if logged in)
        String username = (String) session.getAttribute("username");

        // if the user isn't logged in? send them to the login page.
        // Ideally, this shouldn't trigger; the booking button shouldn't be visible. but you never know.
        if (username == null) {
            return "redirect:/login";
        }

        boolean booked = bookingService.bookFlight(username, flightId);

        // If a flight could not successfully book, let the user know.
        if (!booked){
            model.addAttribute("error", "Unable to book flight");
            return "results";
        }

        // Otherwise, book successfully and let the user know.
        model.addAttribute("message", "Flight booked successfully");
        return "book";
    }
}
