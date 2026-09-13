package com.kenRodriguez.flightswebapp.BookingService;

import com.kenRodriguez.flightswebapp.FlightService.Flight;
import jakarta.persistence.*;

import java.time.LocalDate;

// interface with our bookings table to track user bookings.
// Don't know if this is completely necessary, but you never know.
@Entity
@Table(name="bookings")
public class Booking {
    // generate a unique ID for our bookings.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    // Ensure that many bookings can point to a single flight object in relation to our SQL DB.
    @ManyToOne
    // prepare our booking for a JOIN query by naming our FK (idflights)
    @JoinColumn(name = "idflights")
    private Flight flight;

    private LocalDate bookingDate;

    // Accessors and Mutators to help Spring do its thing.
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Flight getFlight() {
        return flight;
    }
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}
