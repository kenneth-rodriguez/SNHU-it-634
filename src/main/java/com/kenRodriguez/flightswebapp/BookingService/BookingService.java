package com.kenRodriguez.flightswebapp.BookingService;

import com.kenRodriguez.flightswebapp.FlightService.Flight;
import com.kenRodriguez.flightswebapp.FlightService.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    // Constructor
    public BookingService(
            BookingRepository bookingRepository,
            FlightRepository flightRepository){
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }

    //Booking Logic
    public boolean bookFlight(String username, int flightId){
        // Find the flight in the flightRepo DB
        Flight flight = flightRepository.findById(flightId).orElse(null);

        // If flight doesn't exist, cancel booking
        if (flight == null){
            return false;
        }

        // If successful, create a new booking for the currently logged-in user.
        Booking booking = new Booking();
        booking.setUsername(username);
        booking.setFlight(flight);
        booking.setBookingDate(LocalDate.now());

        // save booking to our database
        bookingRepository.save(booking);

        return true;
    }
}
