package com.coursework.demo.repository;

import com.coursework.demo.entity.Bus;
import com.coursework.demo.entity.Route;
import com.coursework.demo.entity.enums.BusCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.coursework.demo.repository.specification.RouteSpecification.getData;
import static com.coursework.demo.repository.specification.RouteSpecification.getDeparture;
import static com.coursework.demo.repository.specification.RouteSpecification.getDestination;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.jpa.domain.Specification.where;

@DataJpaTest
public class RouteSpecificationTest {

    @Autowired
    RouteRepository repository;

    @Test
    @Sql("/specificationRoute.sql")
    public void testSpecificationFromCheck() {
        final Pageable pageable = PageRequest.of(0, 5);
        String from = "Kyiv";
        String to = "";
        LocalDate date = LocalDate.MAX;

        Page<Route> routes = repository.findAll(where(getDeparture(from)).and(getDestination(to).and(getData(date))),
                pageable);

        assertEquals(Collections.singletonList(getExpectedRoutesList().get(0)), routes.getContent());
    }

    @Test
    @Sql("/specificationRoute.sql")
    public void testSpecificationToCheck() {
        final Pageable pageable = PageRequest.of(0, 5);
        String from = "";
        String to = "Tokyo";
        LocalDate date = LocalDate.MAX;

        Page<Route> routes = repository.findAll(where(getDeparture(from)).and(getDestination(to).and(getData(date))),
                pageable);

        assertEquals(Collections.singletonList(getExpectedRoutesList().get(1)), routes.getContent());
    }

    @Test
    @Sql("/specificationRoute.sql")
    public void testSpecificationDateCheck() {
        final Pageable pageable = PageRequest.of(0, 5);
        String from = "";
        String to = "";
        LocalDate date = LocalDate.of(2021, 3, 29);

        Page<Route> routes = repository.findAll(where(getDeparture(from)).and(getDestination(to).and(getData(date))),
                pageable);

        assertEquals(Collections.singletonList(getExpectedRoutesList().get(2)), routes.getContent());
    }

    private List<Route> getExpectedRoutesList() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Bus bus = Bus.builder().id(1L).name("B23").capacity(50).status(BusCondition.ACTIVE).build();
        return Arrays.asList(
                Route.builder()
                        .id(1L)
                        .departure("Kyiv")
                        .departureDate(LocalDateTime.parse("2021-03-28 15:39:49", dtf))
                        .destination("Wroclaw")
                        .destinationDate(LocalDateTime.parse("2021-03-29 15:39:58", dtf))
                        .bus(bus)
                        .build(),
                Route.builder()
                        .id(2L)
                        .departure("Paris")
                        .departureDate(LocalDateTime.parse("2021-03-28 17:35:32", dtf))
                        .destination("Tokyo")
                        .destinationDate(LocalDateTime.parse("2021-03-30 17:35:41", dtf))
                        .bus(bus)
                        .build(),
                Route.builder()
                        .id(3L)
                        .departure("Dubai")
                        .departureDate(LocalDateTime.parse("2021-03-29 17:36:01", dtf))
                        .destination("Barcelona")
                        .destinationDate(LocalDateTime.parse("2021-04-28 17:36:13", dtf))
                        .bus(bus)
                        .build()
        );
    }
}
