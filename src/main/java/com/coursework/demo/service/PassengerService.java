package com.coursework.demo.service;

import com.coursework.demo.entity.Passenger;

import java.util.List;

public interface PassengerService extends BasicService<Passenger, Long> {
    List<Passenger> getPassengersByRoute(Long id);

}
