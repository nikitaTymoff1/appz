package com.coursework.demo.service;

import com.coursework.demo.entity.Bus;
import com.coursework.demo.repository.BusRepository;
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

import static com.coursework.demo.TestData.getBus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BusServiceImplTest {

    @MockBean
    private BusRepository busRepository;

    @Autowired
    private BusService busService;

    @Test
    public void testGetById() {
        final Bus bus = getBus();

        when(busRepository.findById(anyLong())).thenReturn(Optional.of(bus));

        final Bus result = busService.getById(1L);

        assertEquals(bus, result);
        verify(busRepository).findById(anyLong());
    }

    @Test
    public void testGetAll() {
        final Bus bus = getBus();
        final List<Bus> buses = Collections.singletonList(bus);
        final Pageable pageable = PageRequest.of(0, 5);
        final Page<Bus> busPage = new PageImpl<>(buses, pageable, 5);

        when(busRepository.findAll(pageable)).thenReturn(busPage);

        final List<Bus> result = busService.getAll(pageable);

        assertEquals(buses, result);
        verify(busRepository).findAll(pageable);
    }

    @Test
    public void testSave() {
        final Bus bus = getBus();

        when(busRepository.save(bus)).thenReturn(bus);

        final Bus result = busService.save(bus);

        assertEquals(bus, result);
        verify(busRepository).save(bus);
    }

    @Test
    public void testDelete() {
        final Bus bus = getBus();

        doNothing().when(busRepository).delete(bus);

        final Bus result = busService.delete(bus);

        assertEquals(bus, result);
        verify(busRepository).delete(bus);
    }
}
