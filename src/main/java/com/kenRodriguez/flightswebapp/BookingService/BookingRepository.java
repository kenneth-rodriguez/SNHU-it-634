package com.kenRodriguez.flightswebapp.BookingService;

import org.springframework.data.jpa.repository.JpaRepository;

// Another "backend" of an API that hooks up our booking repo to our database.
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
