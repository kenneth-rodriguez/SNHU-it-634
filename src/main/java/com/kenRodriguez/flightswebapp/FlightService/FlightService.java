package com.kenRodriguez.flightswebapp.FlightService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
// The "logic" layer of our API; route data to different FlightRepository commands based on what the RestController tells it.
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // Return a list of every flight in our SQL DB. Like a SELECT *
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // based on the departure & arrival destinations, return specific flights.
    // this will expand per required field. it's going to be huge.
    public List<Flight> searchFlights(String departure, String arrival, LocalDate departureDate, LocalDate arrivalDate) {
        return flightRepository
                // SELECT by Departure Destination, Arrival Destination, Departure Date, and Arrival Date.
                .findByDepartureDestinationAndArrivalDestinationAndDepartureDateAndArrivalDate(
                        departure,
                        arrival,
                        departureDate,
                        arrivalDate
                );
    }
}
