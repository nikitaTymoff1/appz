package com.coursework.demo.service;

import com.coursework.demo.entity.Passenger;
import com.coursework.demo.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.coursework.demo.TestData.getPassenger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PassengerServiceImplTest {

    @MockBean
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerService passengerService;

    @Test
    public void testGetById() {
        final Passenger passenger = getPassenger();

        when(passengerRepository.findById(anyLong())).thenReturn(Optional.of(passenger));

        final Passenger result = passengerService.getById(1L);

        assertEquals(passenger, result);
        verify(passengerRepository).findById(anyLong());
    }

    @Test
    public void testGetPassengersByRoute() {
        final List<Passenger> passengers = Collections.singletonList(getPassenger());

        when(passengerRepository.getPassengersByRoute(anyLong())).thenReturn((passengers));

        final List<Passenger> result = passengerService.getPassengersByRoute(1L);

        assertEquals(passengers, result);
        verify(passengerRepository).getPassengersByRoute(anyLong());
    }

    @Test
    public void testGetAll() {
        final Passenger passenger = getPassenger();
        final List<Passenger> passengers = Collections.singletonList(passenger);
        final Pageable pageable = PageRequest.of(0, 5);
        final Page<Passenger> passengerPage = new PageImpl<>(passengers, pageable, 5);

        when(passengerRepository.findAll(pageable)).thenReturn(passengerPage);

        final List<Passenger> result = passengerService.getAll(pageable);

        assertEquals(passengers, result);
        verify(passengerRepository).findAll(pageable);
    }

    @Test
    public void testSave() {
        final Passenger passenger = getPassenger();

        when(passengerRepository.save(passenger)).thenReturn(passenger);

        final Passenger result = passengerService.save(passenger);

        assertEquals(passenger, result);
        verify(passengerRepository).save(passenger);
    }

    @Test
    public void testDelete() {
        final Passenger passenger = getPassenger();

        doNothing().when(passengerRepository).delete(passenger);

        final Passenger result = passengerService.delete(passenger);

        assertEquals(passenger, result);
        verify(passengerRepository).delete(passenger);
    }
}
