package com.kenRodriguez.flightswebapp;

import com.kenRodriguez.flightswebapp.FlightService.Flight;
import com.kenRodriguez.flightswebapp.FlightService.FlightRepository;
import com.kenRodriguez.flightswebapp.FlightService.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Test the FlightService method (SQL database interactions) with the Mockito extension to fake having a MySQL DB.
@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    // Create a mock (w/ Mockito) flight repository DB w/o SQL
    @Mock
    private FlightRepository flightRepository;

    // Make a FlightService object where our fake repo will live.
    @InjectMocks
    private FlightService flightService;

    // test returning all flights in the DB
    @Test
    void getAllFlightsTest()
    {
        // Create flights
        Flight flight1 = new Flight();
        Flight flight2 = new Flight();

        List<Flight> testFlights = List.of(flight1, flight2);

        // WHEN findAll is called, return our array of test flights
        when(flightRepository.findAll()).thenReturn(testFlights);

        // ACTUALLY return all flights to a list of flights
        List<Flight> result = flightService.getAllFlights();

        // ensure that the list of test flights matches the result
        assertEquals(testFlights, result);

        // ensure the findAll() method is working as intended
        verify(flightRepository).findAll();
    }

    // test searching for specific flights
    @Test
    void getFlightTest(){
        // Populate departure and arrival date variables for consistency.
        // Using the date of my honeymoon flights because I can remember them.
        LocalDate departureDate = LocalDate.of(2027, 3, 15);
        LocalDate arrivalDate = LocalDate.of(2027, 3, 16);

        // Create a test flight to call, then populate it within the list of flights
        Flight flight = new Flight();

        List<Flight> testFlights = List.of(flight);

        // WHEN SELECT Departure Destination, Arrival Destination, Departure Date, and Arrival Date: return our array to the test flights array.
        when(flightRepository.findByDepartureDestinationAndArrivalDestinationAndDepartureDateAndArrivalDate(
                "Chicago",
                "Tokyo",
                departureDate,
                arrivalDate
        ))
        .thenReturn(testFlights);

        // Add a result flight that *should* match our test flight repository data.
        List<Flight> result = flightService.searchFlights(
                "Chicago",
                "Tokyo",
                departureDate,
                arrivalDate
        );

        // make sure our array of test flights match our result array
        assertEquals(testFlights, result);

        // use Mockito to ensure the commands are actually executed as intended.
        verify(flightRepository)
                .findByDepartureDestinationAndArrivalDestinationAndDepartureDateAndArrivalDate(
                        "Chicago",
                        "Tokyo",
                        departureDate,
                        arrivalDate
                );
    }
}
