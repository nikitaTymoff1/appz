package com.coursework.demo.service;

import com.coursework.demo.entity.Route;
import com.coursework.demo.repository.RouteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.coursework.demo.TestData.getRoute;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RouteServiceImplTest {

    @MockBean
    private RouteRepository routeRepository;

    @Autowired
    private RouteService routeService;

    @Test
    public void testGetById() {
        final Route route = getRoute();

        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(route));

        final Route result = routeService.getById(1L);

        assertEquals(route, result);
        verify(routeRepository).findById(anyLong());
    }

    @Test
    public void testGetAll() {
        String from = "Kyiv";
        String to = "Wroclaw";
        LocalDate date = LocalDate.now();
        final Route route = getRoute();
        final List<Route> routeList = Collections.singletonList(route);
        final Pageable pageable = PageRequest.of(0, 5);
        final Page<Route> routes = new PageImpl<>(routeList, pageable, 5);

        when(routeRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(routes);

        final List<Route> result = routeService.getAll(from, to, date, pageable);

        assertEquals(routeList, result);
        verify(routeRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    public void testSave() {
        final Route route = getRoute();

        when(routeRepository.save(route)).thenReturn(route);

        final Route result = routeService.save(route);

        assertEquals(route, result);
        verify(routeRepository).save(route);
    }

    @Test
    public void testDelete() {
        final Route route = getRoute();

        doNothing().when(routeRepository).delete(route);

        final Route result = routeService.delete(route);

        assertEquals(route, result);
        verify(routeRepository).delete(route);
    }
}
