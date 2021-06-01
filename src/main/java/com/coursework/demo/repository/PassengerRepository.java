package com.coursework.demo.repository;

import com.coursework.demo.entity.Passenger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PassengerRepository extends PagingAndSortingRepository<Passenger, Long> {

    @Query("SELECT p FROM Ticket t JOIN t.passenger p JOIN t.route r WHERE r.id = :id")
    List<Passenger> getPassengersByRoute(@Param("id") Long id);
}
