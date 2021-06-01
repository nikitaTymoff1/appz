package com.coursework.demo.service.impl;

import com.coursework.demo.entity.Passenger;
import com.coursework.demo.repository.PassengerRepository;
import com.coursework.demo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class PassengerServiceImpl implements PassengerService {

    private PassengerRepository passengerRepository;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Passenger getById(Long id) {
        return passengerRepository.findById(id).get();
    }

    @Override
    public List<Passenger> getAll(Pageable pageable) {
        return passengerRepository.findAll(pageable).getContent();
    }

    @Override
    public Passenger save(Passenger object) {
        return passengerRepository.save(object);
    }

    @Override
    public Passenger delete(Passenger object) {
        passengerRepository.delete(object);
        return object;
    }

    @Override
    public List<Passenger> getPassengersByRoute(Long id) {
        return passengerRepository.getPassengersByRoute(id);
    }
}
