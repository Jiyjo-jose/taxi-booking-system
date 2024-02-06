package com.example.taxiBooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "taxi_id")
    private Taxi taxi;

    private String pickUpLocation;

    private  String dropOffLocation;
    private double fare;
    private LocalDateTime bookingTime;

    private  boolean bookingStatus;

    private boolean rideStatus;

    public  boolean  getBookingStatus() {

        return  bookingStatus;
    }
    public  boolean  getRideStatus() {

        return  rideStatus;
    }

    public void setBookingStatus(boolean status) {
        this.bookingStatus = status;
    }

    public void setRideStatus(boolean status) {this.rideStatus = status;
    }

    public boolean isRideStatus(boolean b) {
        this.rideStatus =b;
        return b;
    }


}
