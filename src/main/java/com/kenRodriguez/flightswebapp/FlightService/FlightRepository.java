package com.kenRodriguez.flightswebapp.FlightService;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// The "backend" of our API that produces SQL results based on our
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    // Leverage Spring to execute what amounts to a really long SQL SELECT query.
    // Each field in this list's name is mandatory for assignment requirements.
    List<Flight> findByDepartureDestinationAndArrivalDestinationAndDepartureDateAndArrivalDate(
            String departureDestination,
            String arrivalDestination,
            LocalDate departureDate,
            LocalDate arrivalDate
    );
}