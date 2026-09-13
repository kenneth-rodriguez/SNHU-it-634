package com.kenRodriguez.flightswebapp;

import com.kenRodriguez.flightswebapp.FlightService.Flight;
import com.kenRodriguez.flightswebapp.FlightService.FlightRestController;
import com.kenRodriguez.flightswebapp.FlightService.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// Test the Rest layer of our API.
// To refresh my memory, this takes data from our form and passes it to the FlightService layer to execute SQL DB commands.
@WebMvcTest(FlightRestController.class)
public class FlightRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FlightService flightService;

    // Test searching for specific flights.
    @Test
    void searchFlightsTest() throws Exception {

        // Populate departure and arrival date w/ my honeymoon dates for consistency
        LocalDate departureDate = LocalDate.of(2027, 3, 15);
        LocalDate arrivalDate = LocalDate.of(2027, 3, 16);

        Flight flight = new  Flight();

        // Create a flight from Chicago to Tokyo w/ our departure and arrival dates.
        // store the result in a List of flights
        when(flightService.searchFlights(
                "Chicago",
                "Tokyo",
                departureDate,
                arrivalDate))
                .thenReturn(List.of(flight)
        );

        // Compare our params with the flights array we just initialized, basically executing our form.
        // assign variables to departure/arrival/departuredate/arrivaldate, then compare with the stored result.
        // If our results match the expected outcome and we don't receive egregious error codes, we are golden.
        mockMvc.perform(get("/api/flights")
                .param("departure", "Chicago")
                .param("arrival", "Tokyo")
                .param("departureDate", "2027-03-15")
                .param("arrivalDate", "2027-03-16"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1)
        );
    }

    // Test searching with an incorrect number of parameters.
    // Not likely, the form will not take less than these four, but worth testing.
    @Test
    void incompleteAllFlightSearchTest() throws Exception {
        // Create a flight to verify our list has a flight.
        Flight flight = new  Flight();

        // Interrupt our getAllFlights() method, returning our Flight "flight" instead of the MySQL DB when getAllFlights() is called.
        when(flightService.getAllFlights())
                .thenReturn(List.of(flight));

        // simulate a GET from the /api/flights endpoint.
        // Given that we're not passing params through, this should return our "flight" from earlier.
        mockMvc.perform(get("/api/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
