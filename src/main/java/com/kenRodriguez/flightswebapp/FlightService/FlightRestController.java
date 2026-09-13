package com.kenRodriguez.flightswebapp.FlightService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
// The "Frontend" of our API. Cool!
public class FlightRestController {
    private final FlightService flightService;

    public FlightRestController(FlightService flightService) {
        this.flightService = flightService;
    }

    // Map our search inputs.
    @GetMapping
    public List<Flight> searchFlights(
        // Take departure and arrival destinations as arguments
        @RequestParam(required = false) String departure,
        @RequestParam(required = false) String arrival,
        @RequestParam(required = false) LocalDate departureDate,
        @RequestParam(required = false) LocalDate arrivalDate)
    {
        // if our arguments are not null, search for specific flights through the FlightService. These *should* be required
        if (departure != null && arrival != null && departureDate != null && arrivalDate != null) {
            return flightService.searchFlights(departure, arrival, departureDate, arrivalDate);
        }

        // If our arguments are insufficient, return *all* flights.
        return flightService.getAllFlights();
    }
}
