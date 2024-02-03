package com.example.taxiBooking.repository;

import com.example.taxiBooking.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi,Long> {

}
