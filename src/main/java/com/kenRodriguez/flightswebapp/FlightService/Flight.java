package com.kenRodriguez.flightswebapp.FlightService;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

// Flight object to hold data from our SQL database to "talk" with Spring
@Entity
@Table(name= "flights")
public class Flight {
    // Help Jackson with the distinction between snake case (MySQL) and CamelCase (Java)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idflights")
    private int idFlights;
    private String airline;
    private String departureDestination;
    private String arrivalDestination;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Integer availableSeats;

    // Insulate against floating point-type errors with BigDecimal
    private BigDecimal price;
    private int flightNumber;


    //Accessors + Mutators so Jackson plays well with the DB. Didn't know Jackson needed these.
    public int getIdFlights() {
        return idFlights;
    }
    public void setIdFlights(int idFlights) {
        this.idFlights = idFlights;
    }

    public String getAirline() {
        return airline;
    }
    public void setAirline(String airline) {
        this.airline = airline;
    }
    public String getDepartureDestination(){
        return departureDestination;
    }
    public void setDepartureDestination(String departureDestination){
        this.departureDestination = departureDestination;
    }
    public String getArrivalDestination(){
        return arrivalDestination;
    }
    public void setArrivalDestination(String arrivalDestination){
        this.arrivalDestination = arrivalDestination;
    }
    public LocalDate getDepartureDate(){
        return departureDate;
    }
    public void setDepartureDate(LocalDate departureDate){
        this.departureDate = departureDate;
    }
    public LocalDate getArrivalDate(){
        return arrivalDate;
    }
    public void setArrivalDate(LocalDate arrivalDate){
        this.arrivalDate = arrivalDate;
    }
    public LocalTime getDepartureTime(){
        return departureTime;
    }
    public void setDepartureTime(LocalTime departureTime){
        this.departureTime = departureTime;
    }
    public LocalTime getArrivalTime(){
        return arrivalTime;
    }
    public void setArrivalTime(LocalTime arrivalTime){
        this.arrivalTime = arrivalTime;
    }
    public Integer getAvailableSeats(){
        return availableSeats;
    }
    public void setAvailableSeats(Integer availableSeats){
        this.availableSeats = availableSeats;
    }
    public BigDecimal getPrice(){
        return price;
    }
    public void setPrice(BigDecimal price){
        this.price = price;
    }
    public int getFlightNumber(){
        return flightNumber;
    }
    public void setFlightNumber(int flightNumber){
        this.flightNumber = flightNumber;
    }
}
