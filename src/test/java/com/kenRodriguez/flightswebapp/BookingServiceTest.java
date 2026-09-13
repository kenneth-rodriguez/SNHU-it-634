package com.kenRodriguez.flightswebapp;

import com.kenRodriguez.flightswebapp.BookingService.Booking;
import com.kenRodriguez.flightswebapp.BookingService.BookingRepository;
import com.kenRodriguez.flightswebapp.BookingService.BookingService;
import com.kenRodriguez.flightswebapp.FlightService.Flight;
import com.kenRodriguez.flightswebapp.FlightService.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Test with Mockito to make DB testing a little easier and safer.
@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private BookingService bookingService;

    // Test with an existing flight
    @Test
    public void createBooking() throws  Exception{
        Flight flight = new Flight();
        flight.setIdFlights(1);

        when(flightRepository.findById(1)).thenReturn(Optional.of(flight));

        boolean result = bookingService.bookFlight("jacques", 1);

        assertTrue(result);
        verify(bookingRepository).save(any(Booking.class));

    }

    // Test with a flight that does not exist
    @Test
    public void bookNonExistentFlight() throws  Exception{
        // Set to an impossible ID, then get the result to see if it's in the Mockito "database"
        when(flightRepository.findById(999)).thenReturn(Optional.empty());

        boolean result = bookingService.bookFlight("jacques", 999);

        assertFalse(result);

        // Verify the save function is never called, ensuring the test is complete.
        verify(bookingRepository, never()).save(any(Booking.class));
    }
}
