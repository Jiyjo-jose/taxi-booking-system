package com.example.taxiBooking.repository;

import com.example.taxiBooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//   Optional<User> findByEmail(String email);
//boolean existsByEmail(String email);
//  User findByEmail(String email);


}
