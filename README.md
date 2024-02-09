# Taxi-booking-system
## Overview

This is a Taxi Booking and Billing System using Spring Boot, which allows users to book taxis, calculate fares, and handle billing.


### Installation

* Clone the repository:https://github.com/Jiyjo-jose/taxi-booking-system

* Open the project.

* Ensure you have Java and Maven installed.
### Endpoints

#### User

#### Register
* Endpoint: /v1/registration

* Method: POST

* Description: Register a new user.

#### UpdateAccountBalance

* Endpoint: /v1/{id}/updateAccountBalance

* Method: PUT

* Description:Update AccountBalance.

#### RideCompleted

* Endpoint: /v1/rideComplete

* Method: PUT

* Description: mark ride as completed and this is where payment is done from accountBalance.

### Booking

#### Book

* Endpoint: /v2/booking

* Method: POST

* Description: make a booking.

#### View Booking

* Endpoint: /v2/{id}/viewBooking

* Method: GET

* Description: View Booking.

#### Cancel Booking

* Endpoint: v2/{id}/cancel

* Method: PUT

* Description:Cancel a booking.

#### SearchTaxi

* Endpoint: /v2/searchTaxi

* Method: GET

* Description: search for nearby taxi.

### Taxi

#### AddTaxi

* Endpoint: /v3/addTaxi

* Method: POST

* Description: add a new taxi.

##### Swagger: http://localhost:8080/swagger-ui/index.html
