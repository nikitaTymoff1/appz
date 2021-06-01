package com.coursework.demo.repository;

import com.coursework.demo.entity.Passenger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.coursework.demo.TestData.getExpectedPassengersList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class PassengerRepositoryTest {

    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    @Sql("/getPassengersByRoute.sql")
    public void testGetPassengersByRoute() {
        List<Passenger> result = passengerRepository.getPassengersByRoute(1L);

        assertFalse(result.isEmpty());
        assertEquals(getExpectedPassengersList(), result);
    }
}
