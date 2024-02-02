package com.example.taxiBooking.repository;

import com.example.taxiBooking.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiRepository extends JpaRepository<Taxi,Long> {
    Object availableTaxi(String s);
}
